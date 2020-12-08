package com.yipush.core.net;

import android.util.Log;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SimpleResponseListener;
import com.yipush.core.utils.GsonTools;

/**
 * Created by ly on 17/8/29.
 */

public class NohttpRequest {

    public static final String TAG = "NoHttp_TAG";
    /**
     * 成功
     */
    public static final int E_OK = 0;
    /**
     * 通用错误
     */
    public static final int E_GENERIC = -1;
    /**
     * 服务器内部错误
     */
    public static final int E_SERVER = -2;
    /**
     * 客户请求错误
     */
    public static final int E_CLIENT = -3;
    /**
     * 请求参数错误
     */
    public static final int E_PARAM = -4;


    /**
     * @param what
     * @param url
     * @param isCache
     * @param requestTag 依据标签取消请求cancelPendingRequests（用于按TAG取消请求.1.如果传Presenter类名 会随着unRegisterView（页面关闭会调用,
     *                   注:如果用的是PublicPresenter 那么会中断所有用PublicPresenter的请求）而取消请求
     *                   ,如果多个页面调用同一个Presenter类,关闭一个页面其他页面符合上述条件的请求也会关闭2.如果传当前页面的className，那么会随着页面关闭而关闭请。
     *                   3.非上述情况 需按标记取消请求presenter.cancelVolley(tag)
     *                   4.传空 不做标记强求 只能通过presenter.cancelAllVolley 取消APP所有请求）
     */
    public static <T extends BaseResponseEntity> void urlPost(final int what, final String url
            , String json
            , final boolean isCache
            , final String requestTag, BaseResponseWrapper<T> responseWrapper) {
        com.yanzhenjie.nohttp.Logger.setDebug(YiPushManager.isDEBUG());

        NoHttpHelper helper = YiPushManager.noHttpHelper;

        if (helper == null) {
            Log.e("", "please instantiate with YiPushManager.init");
            return;
        }
        Request<String> request = NoHttp.createStringRequest(url, RequestMethod.POST);
        if (isCache) {
            request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);//当请求服务器失败的时候，读取缓存 请求服务器成功则返回服务器数据，如果请求服务器失败，读取缓存数据返回

        } else {
            request.setCacheMode(CacheMode.ONLY_REQUEST_NETWORK);//仅仅请求网络 无论如何也只会请求网络，也不支持http 304这种默认行为。
        }
        Logg.e(TAG, json);
        request.setDefineRequestBodyForJson(json);
        helper.addRequestQueue(what, requestTag, request, new SimpleResponseListener<String>() {
            @Override
            public void onStart(int what) {
                super.onStart(what);
                responseWrapper.onRequestStart();
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                super.onSucceed(what, response);
                Logg.e(TAG, url + ":" + response.get());
                try {
                    T t = (T) GsonTools.getGson(
                            response.get(),BaseResponseEntity.class);

                    if (t.success()) {
                        responseWrapper.onSucceed(t);
                    }else {
                        responseWrapper.onFailing(t);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Logg.e(TAG, e.toString());
                    responseWrapper.onError(e);
                }
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                super.onFailed(what, response);
                Logg.e(TAG, response.getException().getMessage());
                responseWrapper.onError(response.getException());
            }

            @Override
            public void onFinish(int what) {
                super.onFinish(what);
                responseWrapper.onRequestStart();
            }
        });

    }
}
