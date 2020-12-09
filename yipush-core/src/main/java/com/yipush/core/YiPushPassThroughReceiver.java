package com.yipush.core;

import android.content.Context;

public interface YiPushPassThroughReceiver {

    /**
     * 透传推送SDK注册成功回调
     */
  public   void onRegisterSucceed(Context context, YiPushPlatform platform);

    /**
     * 透传消息到达回调
     */
  public   void onReceiveMessage(Context context, YiPushMessage message);
}
