package com.ly.yipush;

import android.content.Context;
import android.util.Log;

import com.yipush.core.GetRegisterIdCallback;
import com.yipush.core.MixPushClient;
import com.yipush.core.MixPushPlatform;

/**
 * Created by ly on 11/30/20.
 * Describe:LY
 */
public class YiPushManager {

    /**在application 初始化
     * @param context Application
     */
    public static void init(Context context) {
        // 开启日志
        //MixPush.getInstance().setLogger(new PushLogger(){});
        MixPushClient.getInstance().setPushReceiver(new YIPushReceiver());
        // 默认初始化5个推送平台（小米推送、华为推送、魅族推送、OPPO推送、VIVO推送），以小米推荐作为默认平台
        MixPushClient.getInstance().register(context);
    }


    /**获取regId，建议在首页的onCreate调用,并上报regId给服务端
     * @param context
     */
    public static void getRegisterId(Context context){
        MixPushClient.getInstance().getRegisterId(context, new GetRegisterIdCallback() {
            public void callback(MixPushPlatform platform) {
                Log.e("GetRegisterIdCallback", "platform=null");
                if (platform != null) {
                    Log.e("GetRegisterIdCallback", platform.toString());
                    // TODO 上报regId给服务端
                }
            }
        });
    }
}

