package com.jverter.shared.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLogger {

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


	private static String getCallerClassName() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		if (stackTrace.length >= 3) {
			return stackTrace[2].getClassName();
		} else {
			return "Unknown";
		}
	}

	private static Logger getLogger() {
		return LoggerFactory.getLogger(getCallerClassName());
	}

}
