package com.yipush.core.net;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.yanzhenjie.nohttp.Logger;
import com.yipush.core.GetRegisterIdCallback;
import com.yipush.core.YiPushClient;
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

    public static String getRegId(){
        if (isDEBUG()){
            return regId;
        }
        return "debug mode required";
    }
    /**
     * 初始化
     * 放在Application 里
     *
     * @param application                必须 Application
     * @param appkey                     appkey
     * @param appkey                     appsecret
     * @param mixPushReceiver            通知栏注册回调
     * @param mixPushPassThroughReceiver 穿透注册回调
     */
    public static void init(Context application
            , String appkey, String secret, YiPushReceiver mixPushReceiver
            , YiPushPassThroughReceiver mixPushPassThroughReceiver) {
        APPKEY = appkey;
        APP_SECRET = secret;
        content = application;
        YiPushClient.getInstance().setPushReceiver(mixPushReceiver);
        YiPushClient.getInstance().setPassThroughReceiver(mixPushPassThroughReceiver);
        YiPushClient.getInstance().register(application);
        getNoHttpHelper(application);
        regist(application);
    }

    public static synchronized NoHttpHelper getNoHttpHelper(Context context) {
        if (noHttpHelper == null) {
            noHttpHelper = new NoHttpHelper(context);
        }
        return noHttpHelper;
    }

    /**
     * 推送注册
     * 可用于重注册
     *
     * @param context
     */
    public static void regist(Context context) {
        YiPushClient.getInstance().getRegisterId(context, new GetRegisterIdCallback() {
            public void callback(YiPushPlatform platform) {
                if (platform != null) {
                    try {
                        regId=platform.getRegId();
                        Logg.e(TAG,"RegId="+platform.getRegId()+" ; platformName="+platform.getPlatformName());
                        net(platform.getRegId(),platform.getPlatformName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Logg.e(TAG, "platform of getRegisterId is null");
                }
            }
        });
    }


    /**
     * 设备注销
     */
    public static void unregisterDevice() throws Exception {
        Presenter.unregisterDevice(MyShared.getString(MyShared.TOKEN_YIPUSH, ""));
    }

    /**
     * 设备保活  建议每次APP启动时调用
     * @throws Exception
     */
    public static void imActive() throws Exception {
        Presenter.imActive(MyShared.getString(MyShared.TOKEN_YIPUSH, ""));
    }


    private static void net(String rgid,String platform) throws Exception {
        Presenter.registerDevice(rgid,platform);
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

