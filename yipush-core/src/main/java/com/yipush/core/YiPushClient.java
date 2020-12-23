package com.yipush.core;

import android.content.Context;
import android.content.Intent;

import com.yipush.core.utils.ProcessUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class YiPushClient {
    public static String TAG = "UnifiedPush";
    public static final String MI = "mi";
    public static boolean debug = true;

    protected Map<String, BaseYiPushProvider> pushManagerMap = new HashMap<>();

    protected static volatile YiPushClient yiPushClient;
    protected YiPushHandler handler = new YiPushHandler();
    protected BaseYiPushProvider notificationPushProvider;
    protected BaseYiPushProvider passThroughPushProvider;


    public static YiPushClient getInstance() {
        if (yiPushClient == null) {
            synchronized (YiPushClient.class) {
                if (yiPushClient == null) {
                    yiPushClient = new YiPushClient();
                }
            }
        }
        return yiPushClient;
    }

    public YiPushClient() {

    }
    public void  unregist(Context c){
        if (notificationPushProvider!=null) notificationPushProvider.unRegister(c);
        if (passThroughPushProvider!=null) passThroughPushProvider.unRegister(c);
    }

    public void addPlatformProvider(BaseYiPushProvider provider) {
        String platformName = provider.getPlatformName();
        if (pushManagerMap.containsKey(platformName)) {
            return;
        }
        pushManagerMap.put(platformName, provider);
    }

    protected void addPlatformProviderByClassName(String className) {
        try {
            Class<?> pushManager = Class.forName(className);
            addPlatformProvider((BaseYiPushProvider) pushManager.newInstance());
        } catch (Exception e) {
            handler.getLogger().log(TAG, "addPlatformProviderByClassName", e);
        }
    }

    /**
     * 默认初始化方式
     * 1. 根据用户的手机型号优先注册厂家的推送平台。
     * 2. 不支持手机厂商推送平台的手机使用小米推送。
     * 3. 全平台使用小米实现透传功能。
     */
    public void register(Context context) {
        register(context, MI, null);
    }

    public void register(Context context, String defaultPlatform) {
        register(context, defaultPlatform, null);
    }

    /**
     * @param defaultPlatform 默认的推送平台
     */
    public void register(Context context, String defaultPlatform, String passThroughPlatform) {
        if (!ProcessUtils.isMainProcess(context)) {
            handler.getLogger().log(TAG, "只允许在主进程初始化");
            return;
        }
        addPlatformProviderByClassName("com.yipush.mi.MiPushProvider");
        addPlatformProviderByClassName("com.yipush.meizu.MeizuPushProvider");
        addPlatformProviderByClassName("com.yipush.huawei.HuaweiPushProvider");
        addPlatformProviderByClassName("com.yipush.oppo.OppoPushProvider");
        addPlatformProviderByClassName("com.yipush.vivo.VivoPushProvider");

        BaseYiPushProvider pushProvider = null;
        // 获取厂商推送
        Set<String> keys = pushManagerMap.keySet();
        for (String key : keys) {
            // 除开默认的推送
            if (!key.equals(defaultPlatform)) {
                BaseYiPushProvider tmp = pushManagerMap.get(key);
                if (tmp != null && tmp.isSupport(context)) {
                    pushProvider = tmp;
                }
            }
        }
        BaseYiPushProvider defaultProvider = pushManagerMap.get(defaultPlatform);
        if (defaultProvider == null) {
            handler.getLogger().log(TAG, "no support push sdk", new Exception("no support push sdk"));
            return;
        }
        if (pushProvider == null) {
            handler.getLogger().log(TAG, "register all " + defaultProvider.getPlatformName());
            if (defaultPlatform.equals(passThroughPlatform)) {
                defaultProvider.register(context, RegisterType.all);
                passThroughPushProvider = defaultProvider;
            } else {
                defaultProvider.register(context, RegisterType.notification);
            }
            notificationPushProvider = defaultProvider;
        } else {
            handler.getLogger().log(TAG, "register notification " + pushProvider.getPlatformName());
            pushProvider.register(context, RegisterType.notification);
            notificationPushProvider = pushProvider;

        }
        if (passThroughPushProvider == null && passThroughPlatform != null) {
            passThroughPushProvider = pushManagerMap.get(passThroughPlatform);
            handler.getLogger().log(TAG, "register passThrough " + passThroughPushProvider.getPlatformName());
            passThroughPushProvider.register(context, RegisterType.passThrough);
        }
    }

    public void setLogger(YiPushLogger logger) {
        handler.callLogger = logger;
    }

    public void setPushReceiver(YiPushReceiver receiver) {
        handler.callPushReceiver = receiver;
    }

    public void setPassThroughReceiver(YiPushPassThroughReceiver receiver) {
        handler.callPassThroughReceiver = receiver;
    }

    public YiPushHandler getHandler() {
        return handler;
    }

    /**
     * 120秒超时
     */
    public void getRegisterId(Context context, final GetRegisterIdCallback callback) {
        getRegisterId(context, callback, false);
    }

    /**
     * 120秒超时
     */
    public void getRegisterId(Context context, final GetRegisterIdCallback callback, boolean isPassThrough) {
        final Context appContext = context.getApplicationContext();
        final BaseYiPushProvider pushProvider;
        if (isPassThrough) {
            pushProvider = passThroughPushProvider;
        } else {
            pushProvider = notificationPushProvider;
        }
        if (pushProvider != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int checkCount = 0;
                    while (true) {
                        String regId = pushProvider.getRegisterId(appContext);
                        if (regId != null && !regId.isEmpty()) {
                            callback.callback(new YiPushPlatform(pushProvider.getPlatformName(), regId));
                            break;
                        }
                        checkCount++;
                        if (checkCount == 60) {
                            callback.callback(null);
                            break;
                        }
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } else {
            callback.callback(null);
        }
    }

    public void openApp(Context context) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        context.startActivity(intent);
    }

}
