package com.yipush.core;

public class YiPushPlatform {
    private String platformName;
    private String regId;

    public YiPushPlatform(String platformName, String regId) {
        this.platformName = platformName;
        this.regId = regId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public String getRegId() {
        return regId;
    }

    @Override
    public String toString() {
        return "PushPlatform{" +
                "platformName='" + platformName + '\'' +
                ", regId='" + regId + '\'' +
                '}';
    }
}
