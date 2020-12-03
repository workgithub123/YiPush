package com.yipush.core.utils;

import android.content.Context;
import android.util.Log;

import com.yipush.core.GetRegisterIdCallback;
import com.yipush.core.MixPushClient;
import com.yipush.core.MixPushPlatform;
import com.yipush.core.MixPushReceiver;
import com.yipush.core.net.HttpsClient;

import java.io.IOException;

/**
 * Created by ly on 11/30/20.
 * Describe:LY
 */
public class YiPushManager {


    /**
     * 在application 初始化
     *
     * @param context Application
     */
    public static void init(Context context, MixPushReceiver mixPushReceiver) {
        // 开启日志
        //MixPush.getInstance().setLogger(new PushLogger(){});
        MixPushClient.getInstance().setPushReceiver(mixPushReceiver);
        // 默认初始化5个推送平台（小米推送、华为推送、魅族推送、OPPO推送、VIVO推送），以小米推荐作为默认平台
        MixPushClient.getInstance().register(context);

    }


    /**
     * 推送注册，建议在首页的onCreate调用
     *
     * @param context
     */
    public static void regist(Context context, String appKey, String scrt) {
        MixPushClient.getInstance().getRegisterId(context, new GetRegisterIdCallback() {
            public void callback(MixPushPlatform platform) {
                if (platform != null) {
                    net(5);
                }
            }
        });
    }

    private static void net(int repnum) {
        if (repnum==0) return;
        repnum--;
        try {
            Log.e("YiPushManager-regist", HttpsClient.getInstance().doPost("https://csj.chushijie.vip/api/v1/index/", ""));
        } catch (IOException e) {
            e.printStackTrace();
            net(repnum);
        }
    }
}

