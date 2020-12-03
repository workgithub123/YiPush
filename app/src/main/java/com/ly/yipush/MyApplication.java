package com.ly.yipush;

import android.app.Application;

import com.yipush.core.utils.YiPushManager;

/**
 * Created by ly on 11/30/20.
 * Describe:LY
 */
public class MyApplication extends Application {


    private static MyApplication instante;

    public synchronized static MyApplication getInstance() {

        return instante;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instante = this;
        YiPushManager.init(this,new YIPushReceiver());
    }
}
