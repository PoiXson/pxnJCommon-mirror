package com.poixson.logger;

import java.util.concurrent.atomic.AtomicBoolean;

import com.poixson.logger.handlers.xLogHandler_Console;
import com.poixson.logger.proxies.LoggerToXLog;
import com.poixson.tools.Keeper;
import com.poixson.tools.StdIO;
import com.poixson.tools.abstractions.OutputStreamLineRemapper;


public class xLogRoot extends xLogger {

	protected static final AtomicBoolean rootInited = new AtomicBoolean(false);



	static {
		InitRoot();
	}



	// init root logger
	public static void InitRoot() {
		if (!rootInited.compareAndSet(false, true))
			return;
		// override stdio
		StdIO.Init();
		// root logger
		final xLog log = new xLogRoot();
//TODO: remove this
		// default log to console
		log.setDefaultHandlerIfEmpty(
			new xLogHandler_Console()
		);
		if (!root.compareAndSet(null, log))
			throw new RuntimeException("Logger root already initialized");
		Keeper.add(log);
		// proxy
		LoggerToXLog.Init();
	}



	protected xLogRoot() {
		super();
		if (root.get() != null)
			throw new RuntimeException("Logger root already initialized");
		// capture std-out
		System.setOut(
			OutputStreamLineRemapper.toPrintStream(
				new OutputStreamLineRemapper() {
					@Override
					public void line(final String line) {
						final xLog log = xLog.Get();
						if (log == null) StdIO.OriginalOut().println(line);
						else             log.stdout(line);
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
						final xLog log = xLog.Get();
						if (log == null) StdIO.OriginalErr().println(line);
						else             log.stderr(line);
					}
				}
			)
		);
	}



	@Override
	public boolean isRoot() {
		return true;
	}



}
