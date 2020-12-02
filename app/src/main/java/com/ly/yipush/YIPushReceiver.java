package com.ly.yipush;

import android.content.Context;
import android.util.Log;

import com.yipush.core.MixPushMessage;
import com.yipush.core.MixPushPlatform;
import com.yipush.core.MixPushReceiver;

/**
 * Created by ly on 11/30/20.
 * Describe:LY
 */
public class YIPushReceiver extends MixPushReceiver {
    /**
     * 通知栏推送SDK注册成功回调
     */
    @Override
    public void onRegisterSucceed(Context context, MixPushPlatform mixPushPlatform) {
        // 这里需要实现上传regId和推送平台信息到服务端保存，
        //也可以通过MixPushClient.getInstance().getRegisterId的方式实现

    }
    /**
     * 通知栏消息被点击回调
     */
    @Override
    public void onNotificationMessageClicked(Context context, MixPushMessage message) {
        // TODO 通知栏消息点击触发，实现打开具体页面，打开浏览器等。
        Log.e("MessageClicked","onNotificationMessageClicked");

    }
    /**
     * 通知栏消息消息到达回调
     */
    @Override
    public void onNotificationMessageArrived(Context context, MixPushMessage message) {
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
