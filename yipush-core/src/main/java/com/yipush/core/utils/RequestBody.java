package com.yipush.core.utils;

/**
 * Created by ly on 12/7/20.
 * Describe:LY
 */
class RequestBody {

    /**
     * 填入随机字符串  32
     */
    private String nonceStr;
    /**
     * 填入签名
     */
    private String sign;

    public RequestBody(String nonceStr, String sign) {
        this.nonceStr = nonceStr;
        this.sign = sign;
    }

    public RequestBody(String nonceStr) {
        this.nonceStr = nonceStr;
    }
}
