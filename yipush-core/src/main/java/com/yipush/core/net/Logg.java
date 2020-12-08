package com.yipush.core.net;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by ly on 12/7/20.
 * Describe:LY
 */
public class Logg {


    private static final String DEFAULT_TAG = "LY_LOGGER";
    //规定每段显示的长度
    private static int LOG_MAXLENGTH = 4000;
    public static void e(Object tag,String message){
        if (TextUtils.isEmpty(message)) return;
        if (YiPushManager.isDEBUG()) {
            if (tag instanceof String) {
                int strLength = message.length();
                int start = 0;
                int end = LOG_MAXLENGTH;
                for (int i = 0; i < 100; i++) {
                    //剩下的文本还是大于规定长度则继续重复截取并输出
                    if (strLength > end) {
                        Log.e((String) tag, message.substring(start, end));
                        start = end;
                        end = end + LOG_MAXLENGTH;
                    } else {
                        Log.e((String) tag, getTAG() + "---" + message.substring(start, strLength));
                        break;
                    }
                }
                return;
            }
            String t = tag == null ? DEFAULT_TAG : tag.getClass()
                    .getSimpleName();


            int strLength = message.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                //剩下的文本还是大于规定长度则继续重复截取并输出
                if (strLength > end) {
                    Log.e(t, message.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.e(t, getTAG() + "---" + message.substring(start, strLength));
                    break;
                }
            }
        }
    }


    /**
     * 获取所在父对象及行号
     *
     * @return
     */
    public static String getTAG() {
        try {
            StackTraceElement stacks[] = Thread.currentThread().getStackTrace();
            StringBuilder sb = new StringBuilder();
            if (stacks != null) {
                StackTraceElement ste = stacks[4];
                sb.append(ste.getFileName().subSequence(0,
                        ste.getFileName().length() - 5)
                        + "." + ste.getMethodName() + "#" + ste.getLineNumber());
            }
            return sb.toString();
        } catch (NullPointerException e) {
            return "PROGUARDED";
        }
    }
}
