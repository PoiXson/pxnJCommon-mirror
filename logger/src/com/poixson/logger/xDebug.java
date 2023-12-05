package com.poixson.logger;

import java.util.concurrent.atomic.AtomicReference;

import com.poixson.tools.Keeper;
import com.poixson.utils.ProcUtils;


public final class xDebug {
	static { Keeper.add(new xDebug()); }

	public static final boolean DEFAULT_DEBUG = false;
	public static final int SEARCH_DEBUG_PARENTS = 2;
	public static final String[] SEARCH_DEBUG_FILES =
		new String[] {
			".debug",
			"debug"
		};

	private static final AtomicReference<Boolean> debugValue = new AtomicReference<Boolean>(null);



	public static void Init() {}

	static {
		boolean isDefault = true;
		if (ProcUtils.isDebugWireEnabled()) {
			setDebug(true);
			final xLog log = xLog.Get();
			if (log != null) {
				log.fine("Detected IDE");
			}
			isDefault = false;
		}
		if (isDefault) {
			setDebug(DEFAULT_DEBUG);
		}
	}

	private xDebug() {
		xDebug.Init();
	}



	public static boolean isDebug() {
		final Boolean value = debugValue.get();
		if (value == null)
			return DEFAULT_DEBUG;
		return value.booleanValue();
	}



	public static void setDebug() {
		setDebug(true);
	}
	public static void setDebug(final boolean value) {
		final Boolean current = debugValue.get();
		if (current == null) {
			if (debugValue.compareAndSet(null, Boolean.valueOf(value)))
				return;
		}
		if (current.booleanValue() == value) return;
		final boolean previous = debugValue.getAndSet(Boolean.valueOf(value));
		if (previous == value) return;
		// update debug mode
		debugEnableDisable(value);
	}



	private static void debugEnableDisable(final boolean value) {
		final xLog log = xLog.Get();
		if (log != null) {
			if (value) {
				log.setLevel(xLevel.ALL);
			}
			log.info("Debug mode enabled");
		}
	}



}
