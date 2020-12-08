package com.ly.yipush;

import android.app.Application;
import android.content.Context;

import com.yipush.core.MixPushMessage;
import com.yipush.core.MixPushPassThroughReceiver;
import com.yipush.core.MixPushPlatform;
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
        YiPushManager.setDEBUG(true);
        YiPushManager.init(this,"0823a2d92fef421db7559c2907089232","5811426b33e84439b07a30a2dbec4418"
                , new YIPushReceiver(),new YiPushPassThroughReceiver());
    }
}
