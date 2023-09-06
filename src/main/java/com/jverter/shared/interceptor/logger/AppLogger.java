package com.jverter.shared.interceptor.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLogger {

	private static final Logger jsonLogger = LoggerFactory.getLogger("JSON");

	public static void info(String message) {
		getLogger().info(message);
	}

	public static void warn(String message) {
		getLogger().warn(message);
	}

	public static void debug(String message) {
		getLogger().debug(message);
	}

	public static void error(String message) {
		getLogger().error(message);
	}

	public static void error(String message, Exception ex) {
		getLogger().error(message, ex);
	}

	public static void jsonReq(String json) {
		jsonLogger.info("Request  : " + json);
	}

	public static void jsonRes(String json) {
		jsonLogger.info("Response  : " + json);
	}

	private static Logger getLogger() {
		return LoggerFactory.getLogger(getCallerClassName());
	}
	
	private static String getCallerClassName() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		if (stackTrace.length >= 3) {
			return stackTrace[2].getClassName();
		} else {
			return "Unknown";
		}
	}
}
