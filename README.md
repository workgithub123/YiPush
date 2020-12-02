# YiPush
1.0.0
基于MixPush(https://github.com/taoweiji/MixPush)  网络后台配置各大厂商渠道
1.根 gradle ：
buildscript {
    repositories {
        google()
        jcenter()
        //加入下面两行
        maven { url "https://jitpack.io" }
        maven { url 'http://developer.huawei.com/repo/' }
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


2.implementation 'com.github.workgithub123:YiPush:tag'//tag改成版本号

基于 [统一推送联盟](http://chinaupa.com/) 的思想，快速集成了六个厂商的推送平台，共享系统的厂商推送通道，避免APP需要长期在后台运行，杀死APP也能收到推送，大大提高推送到达率。接入有一定的开发成本，需要前后端一起参与才可以完成，如果遇到什么问题可以发Issue提问解答。

1. 开发者只需要少量代码即可集成 小米、华为、魅族、OPPO、VIVO，苹果的厂商推送；
2. 根据手机厂商推送的支持情况智能选择不同的推送；

3. 共享系统推送通道，杀死APP也能收到推送，推送到达率高达90%以上；

4. 提供服务端的Java代码，方便开发者快速实现服务端；

5. SDK已经为开发者考虑好各种问题，避免碰壁，从2人超一周开发时间压缩到只需要半天时间即可。
