package com.yipush.core.net;

import android.util.ArrayMap;

import com.yipush.core.utils.MyShared;
import com.yipush.core.utils.SignUtil;
import com.yipush.core.utils.YiPushManager;

import java.util.Map;

/**
 * Created by ly on 12/7/20.
 * Describe:LY
 */
public class Presenter {

    private static final String BASE_URL = "http://192.168.8.74:10191/";
    private static final String registerDevice = "v1/frontend/appsdk/registerDevice";
    private static final String imActive = "v1/frontend/appsdk/ImActive";
    private static final String unregisterDevice = "v1/frontend/appsdk/unregisterDevice";
    public static void registerDevice(String  regId) throws Exception {
        Map<String,String> map = new ArrayMap<>();
        map.put("regId",regId);
        map.put("platform","android");
        map.put("nonceStr",SignUtil.generateNonceStr());
        String jsonReq = SignUtil.generateSignedJson(map,YiPushManager.APP_SECRET);
        NohttpRequest.urlPost(1, BASE_URL+registerDevice, jsonReq,
                Registwrapper.class, false, "", new NohttpRequest.MsgCallBackListener<Registwrapper>() {
                    @Override
                    public void onSucceed(Registwrapper registwrapper) {

                        MyShared.saveData(MyShared.TOKEN_YIPUSH, registwrapper.getData());
                    }

                    @Override
                    public void onfaild(Registwrapper registwrapper) {


                    }
                });

    }
    public static void unregisterDevice(String  token) throws Exception {
        Map<String,String> map = new ArrayMap<>();
        map.put("token",token);
        map.put("nonceStr",SignUtil.generateNonceStr());
        String jsonReq = SignUtil.generateSignedJson(map,YiPushManager.APP_SECRET);
        NohttpRequest.urlPost(1, BASE_URL+unregisterDevice, jsonReq,
                Registwrapper.class, false, "", new NohttpRequest.MsgCallBackListener<Registwrapper>() {
                    @Override
                    public void onSucceed(Registwrapper registwrapper) {
                    }
                    @Override
                    public void onfaild(Registwrapper registwrapper) {
                    }
                });

    }
    public static void imActive(String  token) throws Exception {
        Map<String,String> map = new ArrayMap<>();
        map.put("token",token);
        map.put("nonceStr",SignUtil.generateNonceStr());
        String jsonReq = SignUtil.generateSignedJson(map,YiPushManager.APP_SECRET);
        NohttpRequest.urlPost(1, BASE_URL+imActive, jsonReq,
                Registwrapper.class, false, "", new NohttpRequest.MsgCallBackListener<Registwrapper>() {
                    @Override
                    public void onSucceed(Registwrapper registwrapper) {

                    }

                    @Override
                    public void onfaild(Registwrapper registwrapper) {

                    }
                });

    }
}
