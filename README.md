# 混淆小助手的使用方式  

## 一. 引入插件   
### 1. 在build.gradle配置依赖

 
```java  
buildscript {
    repositories {
        jcenter()
        //1. 加入Jitpack仓库
        maven { url 'https://jitpack.io' }
    }
    dependencies {
        ...
        //2. 加入obfuscator-plugin 插件  
        classpath 'com.github.helen-x:obfuscator-plugin:1.0'
    }
}
```   

### 2. build.gradle 使用plugin 

```java  
//使用混淆插件  
apply plugin: 'obfuscator-plugin'
```

引入完毕后,就可以直接使用啦.
 
## 二. 使用方法:

**场景1**:类中所有public成员不混淆(不包括内部类)  

```java   
@ObfuscateKeepPublic
public class TestAnnotationKeepPublic{
}
```

**场景2**:类中所有成员不混淆(不包括内部类)(内部类如果不需要混淆也要加入此注释)  

```java  
@ObfuscateKeepAll
public class TestAnnotationKeepAll {
}
```

**场景3**:保留所有实现IObfuscateKeepAll接口的类,(注意接口有传递性,他的子类也会被keep)(内部类如果没有继承此接口会被混淆)  

```java  
public class TestInterfaceKeep implements IObfuscateKeepAll{
}
``` 

**场景4**:保留带注释的成员,适用于类和内部类  

```java  
public class TestAnnotationKeepThisField {
    
    public String sName;
    public static String sSName;
    private int iValue;
    
    @ObfuscateKeepThisField
    private static int iSValue;
    
    @ObfuscateKeepThisField
    private void tFunc(){
    }
}
```  

**场景5**:保留类中set/get/is函数  

```java  
@ObfuscateKeepSetterGetter
public class TestAnnotationKeepSetterGetter {}
```  

