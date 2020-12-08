package com.yipush.core.net;

import android.content.Context;

import com.yanzhenjie.nohttp.InitializationConfig;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.cache.DBCacheStore;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.SimpleResponseListener;
import com.yipush.core.utils.YiPushManager;

/**
 * Created by lymac on 17/8/29.
 */

public class NoHttpHelper {
    private final int MY_SOCKET_TIMEOUT_MS = 30 * 1000;
    Context context;
    public static final String NoHttp_TAG = "NoHttp_TAG";
    private RequestQueue queue;


    public NoHttpHelper(Context context) {
        this.context = context;
        init();
        getRequestQueue();
    }

    public void init() {
        InitializationConfig config = InitializationConfig.newBuilder(context)
                // 全局连接服务器超时时间，单位毫秒，默认10s。
                .connectionTimeout(MY_SOCKET_TIMEOUT_MS)
                // 全局等待服务器响应超时时间，单位毫秒，默认10s。
                .readTimeout(30 * 1000)
                // 配置缓存，默认保存数据库DBCacheStore，保存到SD卡使用DiskCacheStore。
                .cacheStore(
                        // 如果不使用缓存，setEnable(false)禁用。
                        new DBCacheStore(context).setEnable(true)
                )
                // 配置Cookie，默认保存数据库DBCookieStore，开发者可以自己实现CookieStore接口。
                .cookieStore(
                        // 如果不维护cookie，setEnable(false)禁用。
                        new DBCookieStore(context).setEnable(true)
                )
                // 配置网络层，默认URLConnectionNetworkExecutor，如果想用OkHttp：OkHttpNetworkExecutor。
//                .networkExecutor(new )
                // 全局通用Header，add是添加，多次调用add不会覆盖上次add。
                .addHeader("x-app-key",  YiPushManager.APPKEY)
                // 全局通用Param，add是添加，多次调用add不会覆盖上次add。
//                .sslSocketFactory() // 全局SSLSocketFactory。
//                .hostnameVerifier() // 全局HostnameVerifier。
                .retry(2) // 全局重试次数，配置后每个请求失败都会重试x次。
                .build();

        NoHttp.initialize(config);

        com.yanzhenjie.nohttp.Logger.setDebug(YiPushManager.isDEBUG());
        com.yanzhenjie.nohttp.Logger.setTag(NoHttp_TAG);// 打印Log的tag。
    }

    public RequestQueue getRequestQueue() {


        if (queue == null) {
            queue = NoHttp.newRequestQueue(5);
        }

        return queue;
    }

    /**依据标签取消
     * @param what
     * @param sign     依据标签取消cancelPendingRequests
     * @param request
     * @param listener
     * @param <T>
     */
    public <T> void addRequestQueue(int what, Object sign, Request<T> request, SimpleResponseListener<T> listener) {
        request.setCancelSign(sign);
        getRequestQueue().add(what, request, listener);

    }

    /**只有通过取消所有取消cancelAllPendingRequests
     * @param what
     * @param request
     * @param listener
     * @param <T>
     */
    public <T> void addRequestQueue(int what, Request<T> request, SimpleResponseListener<T> listener) {
        getRequestQueue().add(what, request, listener);

    }

    public void cancelPendingRequests(Object tag) {
        if (queue != null) {
            queue.cancelBySign(tag);
        }
    }

    public void cancelAllPendingRequests() {
        if (queue != null) {
            queue.cancelAll();
        }

    }
}


