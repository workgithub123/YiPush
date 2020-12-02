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


2.implementation 'com.github.workgithub123:YiPush:tag'//tag改成版本号

