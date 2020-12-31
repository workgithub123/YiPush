package com.ly.yipush;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.yipush.core.net.Presenter;
import com.yipush.core.net.YiPushManager;

import static com.ly.yipush.MyApplication.key;
import static com.ly.yipush.MyApplication.secret;

public class MainActivity extends AppCompatActivity {

    private MyBroadcastReceiver rec;
    private TextView msg;
    private TextView rid;
    private MyBroadcastReceiverR recr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        YiPushManager.regist(MyApplication.getInstance()
//                ,"0823a2d92fef421db7559c2907089232"
//                ,"5811426b33e84439b07a30a2dbec4418");
        rid = ((TextView) findViewById(R.id.rid));
        rid.setText(YiPushManager.getRegId());
        msg = ((TextView) findViewById(R.id.msg));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.ly.yipush.testMsgBroadcastFilter");
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction(YiPushManager.REGIST_INTENT);
        rec = new MyBroadcastReceiver();
        recr = new MyBroadcastReceiverR();
        registerReceiver(rec, intentFilter);
        registerReceiver(recr, intentFilter2);
    }


    private class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (msg != null) {
                msg.setText(intent.getStringExtra("data"));
            }
        }
    }

    private class MyBroadcastReceiverR extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (rid != null) {
                rid.setText(YiPushManager.getRegId());
            }

        }
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(rec);
        unregisterReceiver(recr);
        super.onDestroy();
    }

    public void unregisterDevice(View v) {
        try {
            YiPushManager.unBindDevice(MyApplication.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registDevice(View v) {
        try {
            YiPushManager.init(this
                    , key
                    , secret
                    , new MyPushReceiver(), new MyPushPassThroughReceiver());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void imActive(View v) {
        try {
            YiPushManager.imActive();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Presenter.reportMessageReceived();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void jishu(View v) {
        try {
            Presenter.reportMessageReceived();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}