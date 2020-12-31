package com.yipush.core.net;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Looper;

import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yipush.core.GetRegisterIdCallback;
import com.yipush.core.YiPushClient;
import com.yipush.core.YiPushMessage;
import com.yipush.core.YiPushPassThroughReceiver;
import com.yipush.core.YiPushPlatform;
import com.yipush.core.YiPushReceiver;
import com.yipush.core.utils.MyShared;

/**
 * Created by ly on 11/30/20.
 * Describe:LY
 */
public class YiPushManager {
    public static String APPKEY = "";
    public static String APP_SECRET = "";
    private static boolean DEBUG = false;
    public static NoHttpHelper noHttpHelper;
    public static Context content;
    private static String TAG = "YiPush-Manager";
    private static String regId;
    private static String platformName;
    public static String REGIST_INTENT = "com.ly.yipush.testMsgBroadcastFilterRegist";

    public static boolean isDEBUG() {
        return DEBUG;
    }

    /**
     * 默认关闭
     *
     * @param DEBUG
     */
    public static void setDEBUG(boolean DEBUG) {
        YiPushManager.DEBUG = DEBUG;
        Logger.setDebug(YiPushManager.DEBUG);
    }

    public static String getRegId() {
        return regId;
    }

    /**
     * 初始化
     * 放在Application 里
     *
     * @param application               必须 Application
     * @param appkey                    appkey
     * @param appkey                    appsecret
     * @param yiPushReceiver            通知栏注册回调 不能为null
     * @param yiPushPassThroughReceiver 穿透注册回调 如果用不上穿透模式 可null
     */
    public static void init(Context application
            , String appkey, String secret, YiPushReceiver yiPushReceiver
            , YiPushPassThroughReceiver yiPushPassThroughReceiver) {
        APPKEY = appkey;
        APP_SECRET = secret;
        content = application;
        getNoHttpHelper(application);
        regist(application,application.getPackageName(), yiPushReceiver, yiPushPassThroughReceiver);

    }

    private static void regist(Context application,String packegeName, YiPushReceiver yiPushReceiver, YiPushPassThroughReceiver yiPushPassThroughReceiver) {
        YiPushClient.getInstance().setPushReceiver(new YiPushReceiver() {
            @Override
            public void onRegisterSucceed(Context context, YiPushPlatform platform) {
                if (platform != null) {
                    try {
                        regId = platform.getRegId();
                        platformName = platform.getPlatformName();
                        Intent intent = new Intent();
                        intent.setAction(REGIST_INTENT);
                        context.sendBroadcast(intent);
                        String hd = Looper.myLooper() != Looper.getMainLooper() ? "子线程回调：" : "主线程回调：";
                        Logg.e(TAG, hd + "RegId=" + platform.getRegId() + " ; platformName=" + platform.getPlatformName());
                        register(regId, platformName,packegeName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Logg.e(TAG, "platform of getRegisterId is null");
                }
                yiPushReceiver.onRegisterSucceed(context, platform);
            }

            @Override
            public void onNotificationMessageClicked(Context context, YiPushMessage message) {
                yiPushReceiver.onNotificationMessageClicked(context, message);
            }

            @Override
            public void onNotificationMessageArrived(Context context, YiPushMessage message) {
                super.onNotificationMessageArrived(context, message);
                yiPushReceiver.onNotificationMessageArrived(context, message);
            }

            @Override
            public void openAppCallback(Context context) {
                super.openAppCallback(context);
                yiPushReceiver.openAppCallback(context);
            }
        });
        YiPushClient.getInstance().setPassThroughReceiver(new YiPushPassThroughReceiver() {
            @Override
            public void onRegisterSucceed(Context context, YiPushPlatform platform) {
                if (yiPushPassThroughReceiver != null)
                    yiPushPassThroughReceiver.onRegisterSucceed(context, platform);
            }

            @Override
            public void onReceiveMessage(Context context, YiPushMessage message) {
                if (yiPushPassThroughReceiver != null)
                    yiPushPassThroughReceiver.onReceiveMessage(context, message);
            }

        });
        YiPushClient.getInstance().register(application);
    }

    private static synchronized NoHttpHelper getNoHttpHelper(Context context) {
        if (noHttpHelper == null) {
            noHttpHelper = new NoHttpHelper(context);
        }
        return noHttpHelper;
    }

    /**
     * 获取regId，建议在首页的onCreate调用 需要在init成功后
     * 子线程回调
     *
     * @param context
     * @return
     */
    public static void getRegisterId(Context context, GetRegisterIdCallback registerIdCallback) {
        YiPushClient.getInstance().getRegisterId(context, registerIdCallback);
    }


    /**
     * 推送服务器注销 regId还在 只是服务器不在推送该regId
     * * 需在init实例化注册成功后
     */
    public static void unRegister() throws Exception {
        Presenter.unregisterDevice(MyShared.getString(MyShared.TOKEN_YIPUSH, ""));
    }

    /**
     * 对应unRegister 重新在推送服务器注册
     * 需在init实例化成功后
     *
     * @throws Exception
     */
    public static void register() throws Exception {
        register(regId, platformName,content.getPackageName());
    }

    /**
     * 设备和当前regId解开绑定  解绑后需要重新init注册 regId也会重新生成
     *
     * @throws Exception
     */
    public static void unBindDevice(Context content) {
        YiPushClient.getInstance().unregist(content);
        try {
            unRegister();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设备保活  建议每次APP启动时调用
     *
     * @throws Exception
     */
    public static void imActive() throws Exception {
        Presenter.imActive(MyShared.getString(MyShared.TOKEN_YIPUSH, ""));
    }


    private static void register(String rgid, String platform,String packegeName) throws Exception {
        Presenter.registerDevice(rgid, platform,packegeName);
    }


    /**
     * @param context
     * @param name
     * @param description
     * @param CHANNEL_ID
     */
    private void createNotificationChannel(Context context, String name, String description, String CHANNEL_ID) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * @param context
     * @param channelId The id of the channel.
     */
    private void delete(Context context, String channelId) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.deleteNotificationChannel(channelId);
        }
    }


}

