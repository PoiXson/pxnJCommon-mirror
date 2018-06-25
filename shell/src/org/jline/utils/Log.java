package org.jline.utils;

import java.util.function.Supplier;

import com.poixson.logger.xLogRoot;


public final class Log {

	public static final boolean enableLogging = false;



	public static void trace(final Object... messages) {
		if (enableLogging) {
			xLogRoot.Get()
				.trace(null, "", messages);
		}
	}
	public static void trace(Supplier<String> supplier) {
		if (enableLogging) {
			xLogRoot.Get()
				.trace(null, supplier.get());
		}
	}



	public static void debug(final Object... messages) {
		if (enableLogging) {
			xLogRoot.Get()
				.fine("", messages);
		}
	}
	public static void debug(Supplier<String> supplier) {
		if (enableLogging) {
			xLogRoot.Get()
				.fine(supplier.get());
		}
	}



	public static void info(final Object... messages) {
		if (enableLogging) {
			xLogRoot.Get()
				.info("", messages);
		}
	}



	public static void warn(final Object... messages) {
		if (enableLogging) {
			xLogRoot.Get()
				.warning("", messages);
		}
	}



	public static void error(final Object... messages) {
		if (enableLogging) {
			xLogRoot.Get()
				.severe("", messages);
		}
	}



	public static boolean isDebugEnabled() {
		if (enableLogging)
			return xLogRoot.Get()
				.isDetailLoggable();
		return false;
	}



}
