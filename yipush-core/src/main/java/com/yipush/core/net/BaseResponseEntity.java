package com.yipush.core.net;



import java.io.Serializable;

public class BaseResponseEntity<R>  implements Serializable {
    private int code;
    private String message;
    //数据部分
    private R data;
    /**
     * 数据返回root时间
     */
    private String time;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return message;
    }

    public R getData() {
        return data;
    }

    public String getTime() {
        return time;
    }

    /**
     * 请求成功返回
     */
    public boolean success(){
        return NohttpRequest.E_OK == code;
    }
}
