package com.ly.yipush;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ly.yipush.thread.ThreadPool;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        YiPushManager.getRegisterId(MyApplication.getInstance());

    }
}