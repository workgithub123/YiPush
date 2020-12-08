package com.yipush.core.net;

/**
 * Created by ly on 12/8/20.
 * Describe:LY
 */
public abstract class BaseResponseWrapper<T extends BaseResponseEntity> {


    /**
     * 网络请求成功并返回正确值
     *
     * @param t
     */
    public abstract void onSucceed(T t);

    /**
     * 网络请求成功但是返回值是错误的
     *
     * @param t
     */
    public void onFailing(T t) {
    }

    /**
     * 请求失败 包含解析失败
     *
     * @param t
     */
    public void onError(Throwable t) {
    }

    public void onRequestEnd() {
    }

    public void onRequestStart() {
    }
}
