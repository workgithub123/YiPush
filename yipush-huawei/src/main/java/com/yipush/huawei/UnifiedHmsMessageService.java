package com.yipush.huawei;

import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;
import com.yipush.core.YiPushPlatform;
import com.yipush.core.YiPushClient;
import com.yipush.core.YiPushHandler;
import com.yipush.core.YiPushMessage;

public class UnifiedHmsMessageService extends HmsMessageService {
    public static final String TAG = "HuaweiPushProvider";
    YiPushHandler handler = YiPushClient.getInstance().getHandler();

    /**
     * 接收透传消息方法。
     */
    @Override
    public void onMessageReceived(RemoteMessage message) {
        YiPushMessage yiPushMessage = new YiPushMessage();
        yiPushMessage.setPlatform(HuaweiPushProvider.HUAWEI);
        if (message.getNotification() != null) {
            yiPushMessage.setTitle(message.getNotification().getTitle());
            yiPushMessage.setDescription(message.getNotification().getBody());
        }
        yiPushMessage.setPayload(message.getData());
        yiPushMessage.setPassThrough(message.getNotification() == null);
        handler.getPassThroughReceiver().onReceiveMessage(this, yiPushMessage);
    }

    /**
     * 服务端更新token回调方法。
     * APP调用getToken接口向服务端申请token，如果服务端当次没有返回token值，后续服务端返回token通过此接口返回。主要包含如下三种场景：
     * 1、申请Token如果当次调用失败，PUSH会自动重试申请，成功后则以onNewToken接口返回。
     * 2、如果服务端识别token过期，服务端刷新token也会以onNewToken方式返回。
     * 3、华为设备上EMUI版本低于10.0申请token时，以onNewToken方式返回。
     */
    @Override
    public void onNewToken(String token) {
        YiPushPlatform yiPushPlatform = new YiPushPlatform(HuaweiPushProvider.HUAWEI, token);
        handler.getPushReceiver().onRegisterSucceed(this, yiPushPlatform);
    }

    /**
     * 申请token失败回调方法。
     */
    @Override
    public void onTokenError(Exception exception) {
        handler.getLogger().log(TAG, "申请token失败", exception);
    }

    /**
     *
     */
    @Override
    public void onDeletedMessages() {
    }

    /**
     * 发送上行消息成功回调方法。
     */
    @Override
    public void onMessageSent(String msgId) {
    }

    /**
     * 发送上行消息时如果使用了消息回执能力，消息到达App服务器后，App服务器的应答消息通过本方法回调给应用。
     */
    @Override
    public void onMessageDelivered(String s, Exception e) {
    }

    /**
     * 发送上行消息失败回调方法。
     */
    @Override
    public void onSendError(String msgId, Exception exception) {
    }


}
