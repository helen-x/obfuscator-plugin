package com.helen.plugin.obfuscator.util

import com.google.common.base.Charsets
import org.gradle.api.Project
import org.gradle.api.Task

/**
 * 混淆注入类
 *
 * @author 菜刀文
 * @version 17/1/16
 */
public class ObfuscatorUtil {

    public static addProguardConfig(project, variants) {

        project.android.buildTypes.all { buildType ->

            String obfuscatorConfigFile = getObfuscatorFilePath(project);
            buildType.proguardFile(obfuscatorConfigFile);
            LogUtil.info(project, "add obfuscator configure file for build:" + buildType.name)
        }

        variants.all { variant ->

            def Task processResourcesTask = variant.outputs[0].processResources
            def addObfuscatorTask = project.task("addObfuscator${variant.buildType.name}") {
                createObfuscatorConfigFile(project);
                dependsOn processResourcesTask
            }
            javaCompile.dependsOn addObfuscatorTask
            LogUtil.info(project, variant.buildType.name + "-->proguard files:" + variant.buildType.getProguardFiles())
        }

    }

    /*****************************
     * 混淆相关代码start
     ******************************/
    /**
     * 生成Obfuscator的混淆配置文件.
     * @param project
     * @return 配置文件的路径
     */
    private static String createObfuscatorConfigFile(Project project) {

        final String obfuscator_path = getObfuscatorFilePath(project);

        LogUtil.info(project, "create obfuscator configure file:" + obfuscator_path);

        try {
            File file = new File(obfuscator_path);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            file.write("""${getObfuscatorConfigure(project)}""", Charsets.UTF_8.toString());
        } catch (e) {
            LogUtil.error(project, "create proguard file failed:" + e.toString());
            return null;
        }
        return obfuscator_path;
    }

    private static String getObfuscatorFilePath(Project project) {
        final String obfuscatorFile = 'obfuscator_proguard_generator_by_plugin.pro';
        final String obfuscatorPath = project.getBuildDir().toString() + File.separator + obfuscatorFile;
        LogUtil.info(project, "get obfuscator file path:" + obfuscatorPath);
        return obfuscatorPath;
    }


    private final static String OBFUSCATOR_PKG_NAME = "com.helen.obfuscator";

    private static String getObfuscatorConfigure(Project project) {

        LogUtil.info(project, "get obfuscator configure");
        String configs = "\n" +
                "#[Start----]obfuscate.helper 自定义混淆声明  \n" +
                "\n" +
                "-keep class " + OBFUSCATOR_PKG_NAME + ".* { *; }\n" +
                "\n" +
                "#类中所有成员不混淆(不包括内部类)\n" +
                "#对应声明 ObfuscateKeepAll\n" +
                "-keep @" + OBFUSCATOR_PKG_NAME + ".ObfuscateKeepAll class * { *; }\n" +
                "\n" +
                "#类中所有public成员不混淆(不包括内部类)\n" +
                "#对应声明ObfuscateKeepPublic\n" +
                "-keep @" + OBFUSCATOR_PKG_NAME + ".ObfuscateKeepPublic class * {   \n" +
                "  public <fields>;\n" +
                "  public <methods>;\n" +
                "}\n" +
                "\n" +
                "#保留带注释的成员,适用于类和内部类\n" +
                "#对应声明ObfuscateKeepThisField\n" +
                "-keepclassmembers class * {\n" +
                "@" + OBFUSCATOR_PKG_NAME + ".ObfuscateKeepThisField * ;\n" +
                "}\n" +
                "\n" +
                "#保留类中set/get/is函数\n" +
                "#对应声明ObfuscateKeepSetterGetter\n" +
                "-keepclassmembers @" + OBFUSCATOR_PKG_NAME + ".ObfuscateKeepSetterGetter class * {\n" +
                "    void set*(***);\n" +
                "    boolean is*(); \n" +
                "    *** get*();\n" +
                "}\n" +
                "#保留所有实现IObfuscateKeepAll接口的类,(注意接口有传递性,他的子类也会被keep)(内部类如果没有继承此接口会被混淆)\n" +
                "#对应接口 IObfuscateKeepAll\n" +
                "-keep class * implements " + OBFUSCATOR_PKG_NAME + ".IObfuscateKeepAll {\n" +
                "    *;\n" +
                "}\n" +
                " #[-----end]obfuscate.helper 自定义混淆声明   ";
        return configs;
    };
    /*****************************
     * 混淆相关代码end
     ******************************/

}
