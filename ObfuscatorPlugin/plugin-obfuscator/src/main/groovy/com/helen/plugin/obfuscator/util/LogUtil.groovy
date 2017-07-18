package com.helen.plugin.obfuscator.util

import org.gradle.api.Project

/**
 * Log类
 *
 * @author 菜刀文
 * @version 17/1/16
 */
public class LogUtil {

    public static void debug(Project project, String msg, Object... vars) {
        project.logger.debug(msg, vars)
    }

    public static void warn(Project project, String msg, Object... vars) {
        project.logger.warn(msg, vars)
    }

    public static void error(Project project, String msg) {
        project.logger.warn(msg)
    }

    public static void error(Project project, String msg, Throwable throwable) {
        project.logger.error(msg, throwable)
    }

    public static void info(Project project, String msg, Object... vars) {
        project.logger.info(msg, vars);
    }

}
