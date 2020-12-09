package com.yipush.core;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.yipush.core.net.Logg;

public class YiPushHandler {
    private final YiPushLogger logger;
    private final YiPushReceiver pushReceiver;
    private final YiPushPassThroughReceiver passThroughReceiver;
    public YiPushLogger callLogger;
    public YiPushReceiver callPushReceiver;
    public YiPushPassThroughReceiver callPassThroughReceiver;

    public YiPushHandler() {
        logger = new DefaultYiPushLogger(this);
        pushReceiver = new DefaultYiPushReceiver(this, logger);
        passThroughReceiver = new DefaultPassThroughReceiver(this, logger);
    }

    public YiPushLogger getLogger() {
        return logger;
    }

    public YiPushReceiver getPushReceiver() {
        return pushReceiver;
    }

    public YiPushPassThroughReceiver getPassThroughReceiver() {
        return passThroughReceiver;
    }
}

class DefaultPassThroughReceiver implements YiPushPassThroughReceiver {
    private final YiPushLogger logger;
    private YiPushHandler handler;
    public static String TAG = "UnifiedPush";
    static YiPushPlatform passThroughPlatform = null;

    public DefaultPassThroughReceiver(YiPushHandler handler, YiPushLogger logger) {
        this.handler = handler;
        this.logger = logger;
    }

    @Override
    public void onRegisterSucceed(final Context context, final YiPushPlatform pushPlatform) {
        if (passThroughPlatform != null) {
            Logg.e(TAG, "已经响应onRegisterSucceed,不再重复调用");
            return;
        }
        passThroughPlatform = pushPlatform;
        Logg.e(TAG, "onRegisterSucceed " + pushPlatform.toString());
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            // 在异步进程回调,避免阻塞主进程
            new Thread(new Runnable() {
                @Override
                public void run() {
                    handler.callPassThroughReceiver.onRegisterSucceed(context, pushPlatform);
                }
            }).start();
        } else {
            handler.callPassThroughReceiver.onRegisterSucceed(context, pushPlatform);
        }
    }

    @Override
    public void onReceiveMessage(Context context, YiPushMessage message) {
        Logg.e(TAG, "PassThroughReceiver.onReceiveMessage " + message.toString());
        handler.callPassThroughReceiver.onReceiveMessage(context, message);
    }
}

class DefaultYiPushReceiver extends YiPushReceiver {
    private final YiPushLogger logger;
    private YiPushHandler handler;
    public static String TAG = "UnifiedPush";
    static YiPushPlatform notificationPlatform = null;

    public DefaultYiPushReceiver(YiPushHandler handler, YiPushLogger logger) {
        this.handler = handler;
        this.logger = logger;
    }

    @Override
    public void onRegisterSucceed(final Context context, final YiPushPlatform yiPushPlatform) {
        if (notificationPlatform != null) {
            Logg.e(TAG, "已经响应onRegisterSucceed,不再重复调用");
            return;
        }
        notificationPlatform = yiPushPlatform;
        Logg.e(TAG, "onRegisterSucceed " + yiPushPlatform.toString());
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            // 在异步进程回调,避免阻塞主进程
            new Thread(new Runnable() {
                @Override
                public void run() {
                    handler.callPushReceiver.onRegisterSucceed(context, yiPushPlatform);
                }
            }).start();
        } else {
            handler.callPushReceiver.onRegisterSucceed(context, yiPushPlatform);
        }
    }

    @Override
    public void onNotificationMessageClicked(Context context, YiPushMessage message) {
        Logg.e(TAG, "onNotificationMessageClicked " + message.toString());
        if (message.getPayload() == null || message.getPayload().length() < 5) {
            YiPushClient.getInstance().openApp(context);
            handler.callPushReceiver.openAppCallback(context);
        } else {
            handler.callPushReceiver.onNotificationMessageClicked(context, message);
        }
    }


    @Override
    public void onNotificationMessageArrived(Context context, YiPushMessage message) {
        Logg.e(TAG, "onNotificationMessageArrived " + message.toString());
        handler.callPushReceiver.onNotificationMessageArrived(context, message);
    }
}

class DefaultYiPushLogger implements YiPushLogger {

    private YiPushHandler handler;

    public DefaultYiPushLogger(YiPushHandler handler) {
        this.handler = handler;
    }

    @Override
    public void log(String tag, String content, Throwable throwable) {
        if (!tag.contains(YiPushClient.TAG)) {
            tag = YiPushClient.TAG + "-" + tag;
        }
        if (handler.callLogger != null) {
            handler.callLogger.log(tag, content, throwable);
        } else if (YiPushClient.debug) {
            Log.e(tag, content);
            if (throwable != null) {
                throwable.printStackTrace();
            }
        }
    }

    @Override
    public void log(String tag, String content) {
        if (!tag.contains(YiPushClient.TAG)) {
            tag = YiPushClient.TAG + "-" + tag;
        }
        if (handler.callLogger != null) {
            handler.callLogger.log(tag, content);
        } else if (YiPushClient.debug) {
            Log.e(tag, content);
        }
    }
}
