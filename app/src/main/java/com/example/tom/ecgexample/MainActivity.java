/*
 * Copyright 2016 AndroidPlot.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.example.tom.ecgexample;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidplot.Plot;
import com.androidplot.util.Redrawer;
import com.androidplot.xy.*;
import com.example.tom.ecgexample.R;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.*;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.UUID;


public class MainActivity extends Activity {
    private Button mECGButton;
    private Button mPulseButon;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_main);
        mECGButton = findViewById(R.id.ECGButton);
        mECGButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = XYPlotActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
        mPulseButon = findViewById(R.id.PulseButton);
        mPulseButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = PulseActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }



}