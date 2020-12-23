package com.ly.yipush;

import android.app.Application;

import com.yipush.core.net.YiPushManager;

/**
 * Created by ly on 11/30/20.
 * Describe:LY
 */
public class MyApplication extends Application {


    private static MyApplication instante;

    public static final  String  key = "0823a2d92fef421db7559c2907089232";
    public static final  String  secret = "5811426b33e84439b07a30a2dbec4418";
    public synchronized static MyApplication getInstance() {

        return instante;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instante = this;
        YiPushManager.setDEBUG(true);
        YiPushManager.init(this
                ,key
                ,secret
                , new MyPushReceiver(),new MyPushPassThroughReceiver());
    }
}
