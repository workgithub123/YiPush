package com.yipush.core;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

public class MixPushHandler {
    private final MixPushLogger logger;
    private final MixPushReceiver pushReceiver;
    private final MixPushPassThroughReceiver passThroughReceiver;
    public MixPushLogger callLogger;
    public MixPushReceiver callPushReceiver;
    public MixPushPassThroughReceiver callPassThroughReceiver;

    public MixPushHandler() {
        logger = new DefaultMixPushLogger(this);
        pushReceiver = new DefaultMixPushReceiver(this, logger);
        passThroughReceiver = new DefaultPassThroughReceiver(this, logger);
    }

    public MixPushLogger getLogger() {
        return logger;
    }

    public MixPushReceiver getPushReceiver() {
        return pushReceiver;
    }

    public MixPushPassThroughReceiver getPassThroughReceiver() {
        return passThroughReceiver;
    }
}

class DefaultPassThroughReceiver implements MixPushPassThroughReceiver {
    private final MixPushLogger logger;
    private MixPushHandler handler;
    public static String TAG = "UnifiedPush";
    static MixPushPlatform passThroughPlatform = null;

    public DefaultPassThroughReceiver(MixPushHandler handler, MixPushLogger logger) {
        this.handler = handler;
        this.logger = logger;
    }

    @Override
    public void onRegisterSucceed(final Context context, final MixPushPlatform pushPlatform) {
        if (passThroughPlatform != null) {
            logger.log(TAG, "已经响应onRegisterSucceed,不再重复调用");
            return;
        }
        passThroughPlatform = pushPlatform;
        logger.log(TAG, "onRegisterSucceed " + pushPlatform.toString());
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
    public void onReceiveMessage(Context context, MixPushMessage message) {
        logger.log(TAG, "PassThroughReceiver.onReceiveMessage " + message.toString());
        handler.callPassThroughReceiver.onReceiveMessage(context, message);
    }
}

class DefaultMixPushReceiver extends MixPushReceiver {
    private final MixPushLogger logger;
    private MixPushHandler handler;
    public static String TAG = "UnifiedPush";
    static MixPushPlatform notificationPlatform = null;

    public DefaultMixPushReceiver(MixPushHandler handler, MixPushLogger logger) {
        this.handler = handler;
        this.logger = logger;
    }

    @Override
    public void onRegisterSucceed(final Context context, final MixPushPlatform mixPushPlatform) {
        if (notificationPlatform != null) {
            logger.log(TAG, "已经响应onRegisterSucceed,不再重复调用");
            return;
        }
        notificationPlatform = mixPushPlatform;
        logger.log(TAG, "onRegisterSucceed " + mixPushPlatform.toString());
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            // 在异步进程回调,避免阻塞主进程
            new Thread(new Runnable() {
                @Override
                public void run() {
                    handler.callPushReceiver.onRegisterSucceed(context, mixPushPlatform);
                }
            }).start();
        } else {
            handler.callPushReceiver.onRegisterSucceed(context, mixPushPlatform);
        }
    }

    @Override
    public void onNotificationMessageClicked(Context context, MixPushMessage message) {
        logger.log(TAG, "onNotificationMessageClicked " + message.toString());
        if (message.getPayload() == null || message.getPayload().length() < 5) {
            MixPushClient.getInstance().openApp(context);
            handler.callPushReceiver.openAppCallback(context);
        } else {
            handler.callPushReceiver.onNotificationMessageClicked(context, message);
        }
    }


    @Override
    public void onNotificationMessageArrived(Context context, MixPushMessage message) {
        logger.log(TAG, "onNotificationMessageArrived " + message.toString());
        handler.callPushReceiver.onNotificationMessageArrived(context, message);
    }
}

class DefaultMixPushLogger implements MixPushLogger {

    private MixPushHandler handler;

    public DefaultMixPushLogger(MixPushHandler handler) {
        this.handler = handler;
    }

    @Override
    public void log(String tag, String content, Throwable throwable) {
        if (!tag.contains(MixPushClient.TAG)) {
            tag = MixPushClient.TAG + "-" + tag;
        }
        if (handler.callLogger != null) {
            handler.callLogger.log(tag, content, throwable);
        } else if (MixPushClient.debug) {
            Log.e(tag, content);
            if (throwable != null) {
                throwable.printStackTrace();
            }
        }
    }

    @Override
    public void log(String tag, String content) {
        if (!tag.contains(MixPushClient.TAG)) {
            tag = MixPushClient.TAG + "-" + tag;
        }
        if (handler.callLogger != null) {
            handler.callLogger.log(tag, content);
        } else if (MixPushClient.debug) {
            Log.e(tag, content);
        }
    }
}
