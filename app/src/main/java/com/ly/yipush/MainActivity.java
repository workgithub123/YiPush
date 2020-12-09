package com.ly.yipush;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yipush.core.net.YiPushManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TextView)findViewById(R.id.rid)).setText(YiPushManager.getRegId());
    }


    public void unregisterDevice(View v){

        try {
            YiPushManager.unregisterDevice();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void imActive(View v){
        try {
            YiPushManager.imActive();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}