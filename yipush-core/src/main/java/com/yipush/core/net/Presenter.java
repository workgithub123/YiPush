package com.yipush.core.net;

import android.util.ArrayMap;

import com.yipush.core.BuildConfig;
import com.yipush.core.utils.JSONUtil;
import com.yipush.core.utils.MyShared;
import com.yipush.core.utils.SignUtil;

import java.util.Map;

/**
 * Created by ly on 12/7/20.
 * Describe:LY
 */
public class Presenter {

    private static String BASE_URL;
    private static final String BASE_URL_RELEASE = "https://push.fade-live.com/frontend/v1/appsdk/";
    private static final String BASE_URL_DEBUG = "https://dev.fadefeet.com/push-server/frontend/v1/appsdk/";
    private static final String registerDevice = "registerDevice";
    private static final String imActive = "imActive";
    private static final String unregisterDevice = "unregisterDevice";
    private static final String reportMessageReceived = "reportMessageReceived";


    static {
//        if (BuildConfig.DEBUG) {
//            BASE_URL = BASE_URL_DEBUG;
//        } else {
//            BASE_URL = BASE_URL_RELEASE;
//        }
        BASE_URL = BASE_URL_RELEASE;
    }

    static void registerDevice(String regId, String platform,String packageName) throws Exception {
        Map<String, String> map = new ArrayMap<>();
        map.put("regId", regId);
        map.put("platform", platform);
        map.put("bundle", packageName);
        map.put("nonceStr", SignUtil.generateNonceStr());
        String jsonReq = SignUtil.generateSignedJson(map, YiPushManager.APP_SECRET);
        NohttpRequest.urlPost(1, BASE_URL + registerDevice, jsonReq, false, ""
                , new BaseResponseWrapper<BaseResponseEntity<Object>>() {
                    @Override
                    public void onSucceed(BaseResponseEntity<Object> baseResponseEntity) {
                        Logg.e("Presenter", "token=" + baseResponseEntity.getData().toString());
                        MyShared.saveData(MyShared.TOKEN_YIPUSH, baseResponseEntity.getData().toString());
                    }
                });

    }

    static void unregisterDevice(String token) throws Exception {
        Map<String, String> map = new ArrayMap<>();
        map.put("token", token);
        map.put("nonceStr", SignUtil.generateNonceStr());
        String jsonReq = SignUtil.generateSignedJson(map, YiPushManager.APP_SECRET);
        NohttpRequest.urlPost(1, BASE_URL + unregisterDevice, jsonReq,
                false, "", new BaseResponseWrapper<BaseResponseEntity<String>>() {
                    @Override
                    public void onSucceed(BaseResponseEntity<String> baseResponseEntity) {
                        MyShared.saveData(MyShared.TOKEN_YIPUSH, "");
                    }
                });

    }

    static void imActive(String token) throws Exception {
        Map<String, String> map = new ArrayMap<>();
        map.put("token", token);
        map.put("nonceStr", SignUtil.generateNonceStr());
        String jsonReq = SignUtil.generateSignedJson(map, YiPushManager.APP_SECRET);
        NohttpRequest.urlPost(1, BASE_URL + imActive, jsonReq,
                false, "", new BaseResponseWrapper<BaseResponseEntity<String>>() {
                    @Override
                    public void onSucceed(BaseResponseEntity<String> baseResponseEntity) {

                    }
                });

    }

    public static void reportMessageReceived() throws Exception {
        Map map = new ArrayMap<>();
        map.put("count", 1 + "");
        map.put("nonceStr", SignUtil.generateNonceStr());
        map.put("sign", SignUtil.generateSignature(map, YiPushManager.APP_SECRET, SignUtil.SignType.MD5));
        map.put("count", 1);
        String jsonReq = JSONUtil.marshal(map);
        NohttpRequest.urlPost(1, BASE_URL + reportMessageReceived, jsonReq,
                false, "", new BaseResponseWrapper<BaseResponseEntity<String>>() {
                    @Override
                    public void onSucceed(BaseResponseEntity<String> baseResponseEntity) {

                    }
                });

    }
}
