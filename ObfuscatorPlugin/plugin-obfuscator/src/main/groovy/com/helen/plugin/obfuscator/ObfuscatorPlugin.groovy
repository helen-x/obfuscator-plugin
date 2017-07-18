package com.helen.plugin.obfuscator

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import com.helen.plugin.obfuscator.util.LogUtil
import com.helen.plugin.obfuscator.util.ObfuscatorUtil
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 *
 * 用于混淆的插件,可以单独依赖
 * author: 菜刀文 <br/>
 * date:15/11/11
 */
public class ObfuscatorPlugin implements Plugin<Project> {

    Project project;
    final String OBFUSCATOR_SDK_VERSION = 'com.github.helen-x:obfuscator:1.0';

    @Override
    void apply(Project project) {

        this.project = project;

        if (!hasAppOrLib()) {
            throw new IllegalStateException("'android' or 'android-library' plugin required.")
        }

        final def variants

        if (hasApp()) {
            variants = project.android.applicationVariants
        } else {
            variants = project.android.libraryVariants
        }

        project.repositories{
            maven { url 'https://jitpack.io' }
        }

        project.dependencies {
            LogUtil.info(project, "obfuscator version:" + OBFUSCATOR_SDK_VERSION);
            compile OBFUSCATOR_SDK_VERSION
        }

        ObfuscatorUtil.addProguardConfig(project, variants)
    }

    /**
     * 是否含有 Android application plugin
     * @return true:有
     */
    boolean hasApp() {
        return project.plugins.withType(AppPlugin);
    }

    /**
     * 是否含有 Android lib plugin
     * @return true:有
     */
    boolean hasLib() {
        return project.plugins.withType(LibraryPlugin);
    }

    /**
     * 是否含有Android application 或者 lib plugin
     * @return true:有
     */
    boolean hasAppOrLib() {
        return hasApp() || hasLib();
    }
}