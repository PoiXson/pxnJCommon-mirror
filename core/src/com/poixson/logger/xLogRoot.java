package com.poixson.logger;

import java.util.concurrent.atomic.AtomicReference;

import com.poixson.logger.handlers.xLogHandler;
import com.poixson.logger.handlers.xLogHandler_Console;
import com.poixson.tools.Keeper;
import com.poixson.tools.StdIO;
import com.poixson.tools.abstractions.OutputStreamLineRemapper;
import com.poixson.utils.Utils;


public class xLogRoot extends xLog {

	// root logger
	private static final AtomicReference<xLogRoot> root = new AtomicReference<xLogRoot>(null);

	protected final AtomicReference<xLogHandler[]> defaultHandlers = new AtomicReference<xLogHandler[]>(null);



	// get root logger
	public static xLogRoot Get() {
		if (root.get() == null) {
			final xLogRoot log = new xLogRoot();
			if (root.compareAndSet(null, log))
				return log;
		}
		return root.get();
	}
	public static xLog Get(final String logName) {
		return Get().get(logName);
	}
	public static xLogRoot Peek() {
		return root.get();
	}



	protected xLogRoot() {
		super();
		// override stdio
		if (OVERRIDE_STDIO) {
			StdIO.init();
			// capture std-out
			System.setOut(
				OutputStreamLineRemapper.toPrintStream(
					new OutputStreamLineRemapper() {
						@Override
						public void line(final String line) {
							xLogRoot.Get()
								.stdout(line);
						}
					}
				)
			);
			// capture std-err
			System.setErr(
				OutputStreamLineRemapper.toPrintStream(
					new OutputStreamLineRemapper() {
						@Override
						public void line(final String line) {
							xLogRoot.Get()
								.stderr(line);
						}
					}
				)
			);
		}
		// keep in memory
		Keeper.add(this);
	}



	@Override
	public boolean isRoot() {
		return true;
	}



	// ------------------------------------------------------------------------------- //
	// log handlers



	@Override
	public xLogHandler[] getLogHandlers() {
		final xLogHandler[] handlers = super.getLogHandlers();
		if (Utils.isEmpty(handlers))
			return this.getDefaultHandlers();
		return handlers;
	}
	public xLogHandler[] getDefaultHandlers() {
		if (this.defaultHandlers.get() == null) {
			final xLogHandler[] handlers =
				new xLogHandler[] {
					new xLogHandler_Console()
				};
			if (this.defaultHandlers.compareAndSet(null, handlers))
				return handlers;
		}
		return this.defaultHandlers.get();
	}



}
