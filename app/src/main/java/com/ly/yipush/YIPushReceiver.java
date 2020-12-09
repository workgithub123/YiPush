package com.ly.yipush;

import android.content.Context;
import android.util.Log;

import com.yipush.core.YiPushMessage;
import com.yipush.core.YiPushPlatform;
import com.yipush.core.YiPushReceiver;

/**
 * Created by ly on 11/30/20.
 * Describe:LY
 */
public class YIPushReceiver extends YiPushReceiver {
    /**
     * 通知栏推送SDK注册成功回调
     */
    @Override
    public void onRegisterSucceed(Context context, YiPushPlatform yiPushPlatform) {

    }
    /**
     * 通知栏消息被点击回调
     */
    @Override
    public void onNotificationMessageClicked(Context context, YiPushMessage message) {
        // TODO 通知栏消息点击触发，实现打开具体页面，打开浏览器等。
        Log.e("MessageClicked","onNotificationMessageClicked");

    }
    /**
     * 通知栏消息消息到达回调
     */
    @Override
    public void onNotificationMessageArrived(Context context, YiPushMessage message) {
        super.onNotificationMessageArrived(context, message);
        Log.e("YIPushReceiver",message.toString());
    }
    /**
     * 打开APP回调,可以在这里增加统计功能
     */
    @Override
    public void openAppCallback(Context context) {
        super.openAppCallback(context);
    }
}
