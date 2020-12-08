package com.ly.yipush;

import android.content.Context;

import com.yipush.core.MixPushMessage;
import com.yipush.core.MixPushPassThroughReceiver;
import com.yipush.core.MixPushPlatform;

/**
 * Created by ly on 12/8/20.
 * Describe:LY
 */
public class YiPushPassThroughReceiver implements MixPushPassThroughReceiver {
    @Override
    public void onRegisterSucceed(Context context, MixPushPlatform platform) {

    }

    @Override
    public void onReceiveMessage(Context context, MixPushMessage message) {

    }
}
