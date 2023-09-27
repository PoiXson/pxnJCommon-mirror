/*
package com.poixson.logger;

import java.util.concurrent.atomic.AtomicBoolean;

import com.poixson.logger.proxies.LoggerToXLog;
import com.poixson.tools.Keeper;
import com.poixson.tools.StdIO;
import com.poixson.tools.abstractions.OutputStreamLineRemapper;


public class xLogRoot extends xLogger {

	protected static final AtomicBoolean rootInited = new AtomicBoolean(false);



	// init root logger
	public static void init() {
		if (!rootInited.compareAndSet(false, true))
			throw new RuntimeException("Logger root already initialized");
		// override stdio
		StdIO.init();
		// root logger
		final xLog log = new xLogRoot();
		if (!root.compareAndSet(null, log))
			throw new RuntimeException("Logger root already initialized");
		Keeper.add(log);
		// proxy
		LoggerToXLog.init();
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
						xLog.Get()
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
						xLog.Get()
							.stderr(line);
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
*/
