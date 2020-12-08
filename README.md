# YiPush
1.0.0
公司自用
基于MixPush 网络后台配置各大厂商渠道推送  

1.根 gradle ：

buildscript {

    repositories {
    
        google()
        
        jcenter()
        
        //加入下面两行
        
        maven { url "https://jitpack.io" }
        
        maven { url 'http://developer.huawei.com/repo/'
        }
    }
    
    dependencies {
    
        //加入下面
        
        classpath 'com.huawei.agconnect:agcp:1.3.1.300'
        
    }
}


allprojects {

    repositories {
    
        google()
        
        jcenter()
        
        //加入下面两行
        
        maven { url "https://jitpack.io" }
        
        maven { url 'http://developer.huawei.com/repo/' }
    }
}

2. 修改 app 目录的 build.gradle

小米、VIVO和魅族需要在推送管理后台创建项目并且把对应的APP_ID和APP_KEY配置到文件中，OPPO比较特殊，是配置 APP_KEY 和 APP_SECRET。

apply plugin: 'com.huawei.agconnect'

 defaultConfig {
 
        ...
        
        manifestPlaceholders["VIVO_APP_ID"] = "<VIVO_APP_ID>"
        
        manifestPlaceholders["VIVO_APP_KEY"] = "<VIVO_APP_KEY>"
        
        manifestPlaceholders["MI_APP_ID"] = "<MI_APP_ID>"
        
        manifestPlaceholders["MI_APP_KEY"] = "<MI_APP_KEY>"
        
        manifestPlaceholders["OPPO_APP_KEY"] = "<OPPO_APP_KEY>"
        
        manifestPlaceholders["OPPO_APP_SECRET"] = "<OPPO_APP_SECRET>"
        
        manifestPlaceholders["MEIZU_APP_ID"] = "<MEIZU_APP_ID>"
        
        manifestPlaceholders["MEIZU_APP_KEY"] = "<MEIZU_APP_KEY>"
        
    }
    
    dependencies {
    
    implementation 'com.github.workgithub123:YiPush:tag'//tag改成版本号
    
}



实例化例子：

 YiPushManager.setDEBUG(true);
 
 YiPushManager.init(this,appkey,appscrent, new YIPushReceiver(),new YiPushPassThroughReceiver());
 
 
 
 混淆配置
 
# MixPush

-keep class com.mixpush.mi.MiPushProvider {*;}

-keep class com.mixpush.meizu.MeizuPushProvider {*;}

-keep class com.mixpush.huawei.HuaweiPushProvider {*;}

-keep class com.mixpush.oppo.OppoPushProvider {*;}

-keep class com.mixpush.vivo.VivoPushProvider {*;}

 
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


