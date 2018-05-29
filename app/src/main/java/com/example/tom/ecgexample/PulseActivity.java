package com.example.tom.ecgexample;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.widget.TextView;
import android.widget.Toast;

import com.androidplot.xy.XYPlot;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class PulseActivity extends Activity {

    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static String address = "20:16:03:08:53:65";
    private boolean keepRunning = true;
    private InputStream mmInStream;
    private OutputStream outStream;
    private float inByte = 0;
    private TextView pulseText;
    String str;

    @Override
    public void onCreate(Bundle SavedInstance) {
        super.onCreate(SavedInstance);
        setContentView(R.layout.pulse_view);

        btAdapter = BluetoothAdapter.getDefaultAdapter();
        checkBTState();
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            errorExit("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
        }
        btAdapter.cancelDiscovery();

        try {
            btSocket.connect();
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
            }
        }
        InputStream tmpIn = null;
        try {

            outStream = btSocket.getOutputStream();
            tmpIn = btSocket.getInputStream();

        } catch (IOException e) {
            errorExit("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
        }

        mmInStream = tmpIn;
        pulseText = (TextView) findViewById(R.id.PulseView);

    }

    public void toPulse(String _str){
        char [] qweqwe = _str.toCharArray();
        for(int i=0; i <= qweqwe.length ; i++){

        }
    }

    public class MyThread extends Thread {
        @Override
        public void run() {
            try {

                while (keepRunning) {
                    try {
                        outStream.write(0);
                        Thread.sleep(500);
                        byte[] buffer = new byte[5];
                        int bytes = mmInStream.read(buffer);
                        str = new String(buffer, StandardCharsets.UTF_8);
                        String[] parts = str.split(System.getProperty("line.separator"));
                        str = parts[0];
                        System.out.println(parts[0]);



                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pulseText.setText(str);
                            }
                        });

                        Thread.sleep(1500);

                    }catch (IOException e) {
                        break;
                    }
                }
            } catch (InterruptedException e) {
                keepRunning = false;
            }
        }
    }


    private void checkBTState() {

        if(btAdapter==null) {
            errorExit("Fatal Error", "Bluetooth not support");
        } else {
            if (btAdapter.isEnabled()) {

                Toast.makeText(this, "BtOn", Toast.LENGTH_SHORT);
            } else {

                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    private void errorExit(String title, String message){
        Toast.makeText(getBaseContext(), title + " - " + message, Toast.LENGTH_LONG).show();
        finish();
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        try {
            final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
            return (BluetoothSocket) m.invoke(device, MY_UUID);
        } catch (Exception e) {

        }

        return  device.createRfcommSocketToServiceRecord(MY_UUID);
    }

    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, PulseActivity.class);
        return intent;
    }

    @Override
    public void onResume(){
        super.onResume();
        MyThread thread = new MyThread();
        thread.start();
    }
}
