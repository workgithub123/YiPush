package com.ly.yipush;

import android.content.Context;

import com.yipush.core.YiPushMessage;
import com.yipush.core.YiPushPlatform;

/**
 * Created by ly on 12/8/20.
 * Describe:LY
 */
public class YiPushPassThroughReceiver implements com.yipush.core.YiPushPassThroughReceiver {
    @Override
    public void onRegisterSucceed(Context context, YiPushPlatform platform) {

    }

    @Override
    public void onReceiveMessage(Context context, YiPushMessage message) {

    }
}
