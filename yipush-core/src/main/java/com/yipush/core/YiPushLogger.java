package com.yipush.core;

public interface YiPushLogger {
    void log(String tag, String content, Throwable throwable);

    void log(String tag, String content);
}
