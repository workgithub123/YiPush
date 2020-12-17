package com.ly.yipush;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;

import com.yipush.core.YiPushMessage;
import com.yipush.core.YiPushPlatform;
import com.yipush.core.net.Logg;

/**
 * Created by ly on 12/8/20.
 * Describe:LY
 */
public class MyPushPassThroughReceiver implements com.yipush.core.YiPushPassThroughReceiver {
    @Override
    public void onRegisterSucceed(Context context, YiPushPlatform platform) {
        Logg.e("MyPushPassThroughReceiver","onRegisterSucceed");
    }

    @Override
    public void onReceiveMessage(Context context, YiPushMessage message) {
        String hd =  Looper.myLooper() != Looper.getMainLooper()?"子线程回调：":"主线程回调：";
        Log.e("YiPushThroughReceiver"
                ,hd+message.toString());
        Intent intent = new Intent();
        intent.setAction("com.ly.yipush.testMsgBroadcastFilter");
        intent.putExtra("data",message.toString());
        context.sendBroadcast(intent);
    }
}
