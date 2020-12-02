package com.yipush.huawei;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.common.ApiException;
import com.yipush.core.BaseMixPushProvider;
import com.yipush.core.MixPushPlatform;
import com.yipush.core.RegisterType;
import com.yipush.core.MixPushClient;
import com.yipush.core.MixPushHandler;

import java.lang.reflect.Method;

import static com.yipush.huawei.UnifiedHmsMessageService.TAG;

public class HuaweiPushProvider extends BaseMixPushProvider {
    public static final String HUAWEI = "huawei";
    public static String regId;

    MixPushHandler handler = MixPushClient.getInstance().getHandler();


    @Override
    public void register(Context context, RegisterType type) {
        syncGetToken(context);
    }

    @Override
    public void unRegister(Context context) {

    }

    @Override
    public boolean isSupport(Context context) {
        if (android.os.Build.VERSION.SDK_INT < 17) {
            return false;
        }
        String manufacturer = Build.MANUFACTURER.toLowerCase();
        if (manufacturer.equals("huawei")) {
            return canHuaWeiPush();
        }
        return false;
    }

    /**
     * 判断是否可以使用华为推送
     */
    public static Boolean canHuaWeiPush() {
        int emuiApiLevel = 0;
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            Method method = cls.getDeclaredMethod("get", new Class[]{String.class});
            emuiApiLevel = Integer.parseInt((String) method.invoke(cls, new Object[]{"ro.build.hw_emui_api_level"}));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return emuiApiLevel >= 5.0;
    }

    @Override
    public String getPlatformName() {
        return HuaweiPushProvider.HUAWEI;
    }

    @Override
    public String getRegisterId(Context context) {
        try {
            // read from agconnect-services.json
            String appId = AGConnectServicesConfig.fromContext(context).getString("client/app_id");
            regId = HmsInstanceId.getInstance(context).getToken(appId, "HCM");
            Log.e(TAG, "get token:" + regId);
            return regId;
        } catch (ApiException e) {
            handler.getLogger().log(TAG, "hms get token failed " + e.toString() + " https://developer.huawei.com/consumer/cn/doc/development/HMSCore-References-V5/tv-errorcode-0000001050145059-V5", e);
            e.printStackTrace();
        }
        return null;
    }


    private void syncGetToken(final Context context) {
        new Thread() {
            @Override
            public void run() {
                String regId = getRegisterId(context);
                if (!TextUtils.isEmpty(regId)) {
                    MixPushPlatform mixPushPlatform = new MixPushPlatform(HuaweiPushProvider.HUAWEI, regId);
                    MixPushClient.getInstance().getHandler().getPushReceiver().onRegisterSucceed(context, mixPushPlatform);
                }
            }
        }.start();
    }
}
