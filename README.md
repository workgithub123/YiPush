
公司自用

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
    
    implementation 'com.github.workgithubly:YiPush:tag'//tag改成版本号
    
}



API介绍：

#YiPushManager接口

以下接口调用时，如有回调，均为异步执行，且回调不能为空。

#SDK注册

1、初始化SDK，关联云通道

 参数

 context应用上下文（必须ApplicationContext）

 appkey 

 appSecret

 YiPushReceiver 通知栏消息回调（非穿透消息）

 YiPushPassThroughReceiver 穿透消息到达回调

 YiPushManager.init(this,appkey,appscret,YiPushReceiver,YiPushPassThroughReceiver);
 

2、设置调试模式

 YiPushManager.setDEBUG(boolean);

 需在init前调用。

 true: 调试模式 所有内容将打印在日志里

 false:rlease模式 隐藏日志

3、获取唯一标识

 YiPushManager.getRegId() 需要在init成功后调用。

4、设备注销

 YiPushManager.unregisterDevice()
 
5、设备保活

YiPushManager.imActive()

6、动态注册

参数

application （必须ApplicationContext）

YiPushManager.regist(Context application, String appkey, String secret)
 
厂商推送平台介绍

推送平台	透传	全局推送	别名/标签	支持说明

小米推送	支持	 支持	     支持	      所有Android设备，小米设备支持系级别推送，其它设备支持普通推送

华为推送	支持	不支持	    不支持	     仅华为设备，部分EMUI4.0和4.1，及EMUI5.0及之后的华为设备。

OPPO推送	不支持	 支持	     支持	       仅OPPO和一加手机，支持ColorOS3.1及以上的系统。

VIVO推送	不支持	 支持	     支持	       仅VIVO手机，部分 Android 9.0，及 9.0 以上手机

魅族推送   不支持	支持	    支持	      仅魅族手机，Flyme系统全平台


 
建议

如果手机支持建厂商推送就使用厂商推送SDK，否则使用小米推送。

由于华为推送不支持别名和标签，所以建议所有的手机都统一通过regId进行推送。

由于多数的推送SDK不支持透传，如果APP需要支持透传，建议统一使用小米推送作为透传方案，但是如果使用小米作为所有Android手机的透传功能，那么小米推送就不再支持全局推送。

由于华为推送和APNs不支持全局推送，如果要推送给所有用户，请查询最近3个月有打开APP的用户，进行分组推送。因为多数的有效期都是三个月，就算推送用户也收不到，如果把所有历史的用户都查询出来，推送压力将会加倍。

 
 混淆配置
 
-keep class com.yipush.mi.MiPushProvider {*;}

-keep class com.yipush.meizu.MeizuPushProvider {*;}

-keep class com.yipush.huawei.HuaweiPushProvider {*;}

-keep class com.yipush.oppo.OppoPushProvider {*;}

-keep class com.yipush.vivo.VivoPushProvider {*;}

 
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


