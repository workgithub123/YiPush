package com.yipush.core.net;



import java.io.Serializable;

public class EntityWrapperLy  implements Serializable {
    /**
     * 状态码（成功返回时）
     */
    private int code;
    /**
     * nohttp what
     */
    private int what;
    /**
     * 状态描述（成功返回时）
     */
    private String message;
    /**
     * 是否是缓存
     */
    private boolean isCache=false;
    /**
     * 是否出错
     */
    private boolean isError=false;
    /**
     * 发送请求的类名（包括包路劲）
     */
    private String className;
    /**
     * 自定义标记
     */
    private Object[] tagObjects=new Object[]{};
    private MyError myError;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Object[] getTagObjects() {
        return tagObjects;
    }

    public void setTagObjects(Object[] tagObjects) {
        this.tagObjects = tagObjects;
    }


    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }

    public boolean isCache() {
        return isCache;
    }

    public void setIsCache(boolean isCache) {
        this.isCache = isCache;
    }

    public boolean isError() {
        return isError;
    }

    public void setIsError(boolean isError) {
        this.isError = isError;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }
    public class MyError{

        private Exception exception;
        private String errorInfo;

        public String getErrorInfo() {
            return errorInfo;
        }

        public Exception getException() {
            return exception;
        }

        public void setException(Exception exception) {
            this.exception = exception;
        }

        public void setErrorInfo(String errorInfo) {
            this.errorInfo = errorInfo;
        }


    }
    public MyError getMyError() {
        if (myError==null) {
            myError = new MyError();
        }
        return myError;
    }

}
