# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


-keep class com.yipush.mi.MiPushProvider {*;}
-keep class com.yipush.meizu.MeizuPushProvider {*;}
-keep class com.yipush.huawei.HuaweiPushProvider {*;}
-keep class com.yipush.oppo.OppoPushProvider {*;}
-keep class com.yipush.vivo.VivoPushProvider {*;}

-keep class com.yipush.mi.MiPushProvider {*;}

-keep class com.yipush.meizu.MeizuPushProvider {*;}

-keep class com.yipush.huawei.HuaweiPushProvider {*;}

-keep class com.yipush.oppo.OppoPushProvider {*;}

-keep class com.yipush.vivo.VivoPushProvider {*;}

-keep class com.yipush.core.net.YiPushManager {*;}


#华为推送

-keep class com.hianalytics.android.**{*;}

-keep class com.huawei.updatesdk.**{*;}

-keep class com.huawei.hms.**{*;}

#小米推送

-keep class com.xiaomi.**{*;}


#OPPO

-keep public class * extends android.app.Service

-keep class com.heytap.msp.** { *;}


#VIVO

-dontwarn com.vivo.push.**

-keep class com.vivo.push.**{*; }

-keep class com.vivo.vms.**{*; }

#魅族

-keep class com.meizu.**{*;}