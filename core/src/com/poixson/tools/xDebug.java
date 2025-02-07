package com.poixson.tools;

import static com.poixson.utils.FileUtils.SearchLocalFile;
import static com.poixson.utils.ProcUtils.IsDebugWireEnabled;
import static com.poixson.utils.Utils.IsEmpty;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.logger.xLevel;
import com.poixson.logger.xLog;


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
			Keeper.Add(new xDebug());
			// debug in ide
			if (IsDebugWireEnabled()) {
				SetDebug(true);
				final xLog log = xLog.Get();
				if (log != null)
					log.fine("Detected IDE");
			} else {
				SetDebug(DEFAULT_DEBUG);
			}
			// search for .debug file
			if (!IsDebug()) {
				if (!IsEmpty(SEARCH_DEBUG_FILES)) {
					final String result =
						SearchLocalFile(
							SEARCH_DEBUG_FILES,
							SEARCH_DEBUG_PARENTS
						);
					if (!IsEmpty(result))
						SetDebug(true);
				}
			}
			if (IsDebug())
				xLog.Get().setLevel(xLevel.ALL);
		}
	}

	private xDebug() {
	}



	public static boolean Debug() {
		final Boolean value = debugValue.get();
		if (value == null)
			return DEFAULT_DEBUG;
		return value.booleanValue();
	}
	@Deprecated
	public static boolean IsDebug() {
		return Debug();
	}



	public static void SetDebug() {
		Debug(true);
	}
	@Deprecated
	public static void SetDebug(final boolean value) {
		Debug(value);
	}
	public static void Debug(final boolean value) {
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
