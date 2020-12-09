package com.yipush.oppo;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.yipush.core.YiPushClient;
import com.yipush.core.YiPushMessage;

public class OppoMessageReceiveActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Uri data = getIntent().getData();
        this.finish();
        if (data != null) {
            YiPushMessage message = new YiPushMessage();
            message.setPlatform(OppoPushProvider.OPPO);
            message.setTitle(data.getQueryParameter("title"));
            message.setDescription(data.getQueryParameter("description"));
            message.setPayload(data.getQueryParameter("payload"));
            YiPushClient.getInstance().getHandler().getLogger().log(OppoPushProvider.TAG, "url is " + data.toString());
            YiPushClient.getInstance().getHandler().getPushReceiver().onNotificationMessageClicked(this.getApplicationContext(), message);
        } else {
//            MixPushClient.getInstance().getHandler().getLogger().log(OppoPushProvider.TAG, "url is null");
            YiPushClient.getInstance().openApp(this);
        }
        // mixpush://com.mixpush.oppo/message?title=title&description=description&payload=%7b%22url%22%3a%22http%3a%2f%2fsoso.com%22%7d
    }
}