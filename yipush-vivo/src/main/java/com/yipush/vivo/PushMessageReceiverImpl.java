package com.yipush.vivo;

import android.content.Context;

import com.yipush.core.YiPushPlatform;
import com.yipush.core.YiPushClient;
import com.yipush.core.YiPushHandler;
import com.yipush.core.YiPushMessage;
import com.vivo.push.model.UPSNotificationMessage;
import com.vivo.push.sdk.OpenClientPushMessageReceiver;

public class PushMessageReceiverImpl extends OpenClientPushMessageReceiver {
    YiPushHandler handler = YiPushClient.getInstance().getHandler();

    @Override
    public void onNotificationMessageClicked(Context context, UPSNotificationMessage message) {
        YiPushMessage pushMessage = new YiPushMessage();
        pushMessage.setPlatform(VivoPushProvider.VIVO);
//        pushMessage.setMsgId(String.valueOf(message.getMsgId()));
        pushMessage.setTitle(message.getTitle());
        pushMessage.setDescription(message.getContent());
        pushMessage.setPayload(message.getSkipContent());
        handler.getPushReceiver().onNotificationMessageClicked(context, pushMessage);
    }


    @Override
    public void onReceiveRegId(Context context, String regId) {
        YiPushPlatform yiPushPlatform = new YiPushPlatform(VivoPushProvider.VIVO, regId);
        handler.getPushReceiver().onRegisterSucceed(context, yiPushPlatform);
    }
}
