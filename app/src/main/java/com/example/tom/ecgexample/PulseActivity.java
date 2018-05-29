package com.example.tom.ecgexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class PulseActivity extends Activity {

    @Override
    public void onCreate(Bundle SavedInstance) {

        super.onCreate(SavedInstance);
        setContentView(R.layout.pulse_view);
    }
    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, PulseActivity.class);
        return intent;
    }
}
