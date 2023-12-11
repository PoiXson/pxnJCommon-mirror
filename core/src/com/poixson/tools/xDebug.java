package com.poixson.tools;

import static com.poixson.utils.Utils.IsEmpty;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.logger.xLevel;
import com.poixson.logger.xLog;
import com.poixson.utils.FileUtils;
import com.poixson.utils.ProcUtils;


public final class xDebug {

	public static final boolean DEFAULT_DEBUG = false;
	public static final int SEARCH_DEBUG_PARENTS = 2;
	public static final String[] SEARCH_DEBUG_FILES =
		new String[] {
			".debug",
			"debug"
		};

	private static final AtomicBoolean inited = new AtomicBoolean(false);

	private static final AtomicReference<Boolean> debugValue = new AtomicReference<Boolean>(null);



	public static void Init() {
		if (inited.compareAndSet(false, true)) {
			Keeper.add(new xDebug());
			// debug in ide
			if (ProcUtils.isDebugWireEnabled()) {
				SetDebug(true);
				final xLog log = xLog.Get();
				if (log != null)
					log.fine("Detected IDE");
			} else {
				SetDebug(DEFAULT_DEBUG);
			}
			// search for .debug file
			if (!isDebug()) {
				if (!IsEmpty(SEARCH_DEBUG_FILES)) {
					final String result =
						FileUtils.SearchLocalFile(
							SEARCH_DEBUG_FILES,
							SEARCH_DEBUG_PARENTS
						);
					if (!IsEmpty(result))
						SetDebug(true);
				}
			}
			if (isDebug())
				xLog.Get().setLevel(xLevel.ALL);
		}
	}

	private xDebug() {
	}



	public static boolean isDebug() {
		final Boolean value = debugValue.get();
		if (value == null)
			return DEFAULT_DEBUG;
		return value.booleanValue();
	}



	public static void SetDebug() {
		SetDebug(true);
	}
	public static void SetDebug(final boolean value) {
		final Boolean current = debugValue.get();
		if (current == null) {
			if (debugValue.compareAndSet(null, Boolean.valueOf(value)))
				return;
		}
		if (current.booleanValue() == value) return;
		final boolean previous = debugValue.getAndSet(Boolean.valueOf(value));
		if (previous == value) return;
		// update debug mode
		_DebugEnableDisable(value);
	}



	private static void _DebugEnableDisable(final boolean value) {
		final xLog log = xLog.Get();
		if (log != null) {
			if (value) {
				log.setLevel(xLevel.ALL);
			}
			log.info("Debug mode enabled");
		}
	}



}
