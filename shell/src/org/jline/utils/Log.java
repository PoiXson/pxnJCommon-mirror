/*
package org.jline.utils;

import java.util.function.Supplier;

import com.poixson.logger.xLog;


public final class Log {

	public static final boolean enableLogging = false;



	public static void trace(final Object...messages) {
		if (enableLogging) {
			xLog.GetRoot()
				.trace(null, "", messages);
		}
	}
	public static void trace(Supplier<String> supplier) {
		if (enableLogging) {
			xLog.GetRoot()
				.trace(null, supplier.get());
		}
	}



	public static void debug(final Object...messages) {
		if (enableLogging) {
			xLog.GetRoot()
				.fine("", messages);
		}
	}
	public static void debug(Supplier<String> supplier) {
		if (enableLogging) {
			xLog.GetRoot()
				.fine(supplier.get());
		}
	}



	public static void info(final Object...messages) {
		if (enableLogging) {
			xLog.GetRoot()
				.info("", messages);
		}
	}



	public static void warn(final Object...messages) {
		if (enableLogging) {
			xLog.GetRoot()
				.warning("", messages);
		}
	}



	public static void error(final Object...messages) {
		if (enableLogging) {
			xLog.GetRoot()
				.severe("", messages);
		}
	}



	public static boolean isDebugEnabled() {
		if (enableLogging)
			xLog.GetRoot()
				.isDetailLoggable();
		return false;
	}



}
*/
