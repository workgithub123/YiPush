package com.ly.yipush;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;

import com.yipush.core.YiPushMessage;
import com.yipush.core.YiPushPlatform;
import com.yipush.core.YiPushReceiver;
import com.yipush.core.net.Logg;

/**
 * Created by ly on 11/30/20.
 * Describe:LY
 */
public class MyPushReceiver extends YiPushReceiver {
    /**
     * 通知栏推送SDK注册成功回调
     */
    @Override
    public void onRegisterSucceed(Context context, YiPushPlatform yiPushPlatform) {
        Logg.e("MyPushReceiver","onRegisterSucceed");

    }
    /**
     * 通知栏消息被点击回调
     */
    @Override
    public void onNotificationMessageClicked(Context context, YiPushMessage message) {
        // TODO 通知栏消息点击触发，实现打开具体页面，打开浏览器等。
        Log.e("MessageClicked","onNotificationMessageClicked");
        Intent intent = new Intent();
        intent.setAction("com.ly.yipush.testMsgBroadcastFilter");
        intent.putExtra("data",message.toString());
        context.sendBroadcast(intent);

    }
    /**
     * 通知栏消息消息到达回调
     */
    @Override
    public void onNotificationMessageArrived(Context context, YiPushMessage message) {
        super.onNotificationMessageArrived(context, message);
        String hd = Looper.myLooper() != Looper.getMainLooper()?"子线程回调：":"主线程回调：";
        Log.e("YIPushReceiver"
                ,hd+message.toString());
        Intent intent = new Intent();
        intent.setAction("com.ly.yipush.testMsgBroadcastFilter");
        intent.putExtra("data",message.toString());
        context.sendBroadcast(intent);
    }
    /**
     * 打开APP回调,可以在这里增加统计功能
     */
    @Override
    public void openAppCallback(Context context) {
        super.openAppCallback(context);
    }
}
