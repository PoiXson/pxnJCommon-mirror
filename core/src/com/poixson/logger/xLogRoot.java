package com.poixson.logger;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.logger.formatters.xLogFormat_Tagged;
import com.poixson.logger.handlers.xLogHandler;
import com.poixson.logger.handlers.xLogHandler_StdIO;
import com.poixson.tools.Keeper;
import com.poixson.tools.StdIO;
import com.poixson.tools.localization.LangShelf;


public class xLogRoot extends xLog {

	protected static final AtomicBoolean inited = new AtomicBoolean(false);

	protected static final AtomicReference<LangShelf> lang = new AtomicReference<LangShelf>(null);



	public static void InitRoot() {
		if (inited.compareAndSet(false, true)) {
			StdIO.Init();
			final xLogRoot log = new xLogRoot();
			if (!root.compareAndSet(null, log))
				throw new RuntimeException("Logger root already initialized");
		}
	}



	public xLogRoot() {
		super();
		if (root.get() != null)
			throw new RuntimeException("Logger root already initialized");
		Keeper.Add(this);
	}



	@Override
	public boolean isRoot() {
		return true;
	}



	@Override
	protected xLogHandler createDefaultHandler() {
		final xLogHandler handler = new xLogHandler_StdIO();
		handler.setFormat(new xLogFormat_Tagged());
		return handler;
	}



	public static LangShelf GetLangShelf() {
		// existing
		{
			final LangShelf shelf = lang.get();
			if (shelf != null)
				return shelf;
		}
		// new instance
		{
			final LangShelf shelf = new LangShelf(null);
			if (lang.compareAndSet(null, shelf))
				return shelf.ref(xLogRoot.class);
		}
		return lang.get();
	}
	public static void SetLangShelf(final LangShelf shelf) {
		lang.set(shelf);
	}



}
/*
		// capture std-out
		System.setOut(
			OutputStreamLineRemapper.toPrintStream(
				new OutputStreamLineRemapper() {
					@Override
					public void print(final String line) {
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
					public void print(final String line) {
						final xLog log = xLog.Get();
						if (log == null) StdIO.OriginalErr().println(line);
						else             log.stderr(line);
					}
				}
			)
		);
	}
*/
