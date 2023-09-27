/*
package com.poixson.logger.proxies;

import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.poixson.logger.xLevel;
import com.poixson.logger.xLog;
import com.poixson.logger.records.xLogRecord_Msg;
import com.poixson.tools.Keeper;
import com.poixson.utils.Utils;


public class LoggerToXLog extends Handler {

	protected static final AtomicReference<LoggerToXLog> instance = new AtomicReference<LoggerToXLog>(null);



	public static void init() {
		Logger logger = Logger.getLogger("");
		logger.setLevel(Level.ALL);
		final Handler[] handlers = logger.getHandlers();
		boolean found = false;
		for (final Handler handler : handlers) {
			if (handler instanceof LoggerToXLog) {
				found = true;
				continue;
			}
			logger.removeHandler(handler);
		}
		if (!found) {
			logger.addHandler( Get() );
		}
		// default log levels
		xLog.Get("jline").setLevel(xLevel.INFO);
		xLog.Get("netty").setLevel(xLevel.INFO);
		xLog.Get("jcl"  ).setLevel(xLevel.INFO);
		xLog.Get("X11"  ).setLevel(xLevel.INFO);
	}



	protected static LoggerToXLog Get() {
		if (instance.get() == null) {
			final LoggerToXLog handler = new LoggerToXLog();
			if (instance.compareAndSet(null, handler))
				return handler;
		}
		return instance.get();
	}
	protected LoggerToXLog() {
		Keeper.add(this);
	}



	public static String AliasFor(final String name) {
		if (Utils.isEmpty(name)) return null;
		if (name.equals("org.jline"))        return "jline";
		if (name.startsWith("io.netty"))     return "netty";
//TODO: remove this?
//		if (name.startsWith("org.xeustechnologies.jcl")) return "jcl";
		if (name.startsWith("java.awt."))    return "X11";
		if (name.startsWith("javax.swing.")) return "X11";
		if (name.startsWith("sun.awt."))     return "X11";
		return name;
	}



	@Override
	public void publish(final LogRecord record) {
		final xLog log = this.log(record);
		final String msg = record.getMessage();
		final Level  lvl = record.getLevel();
		final xLevel level = xLevel.FromJavaLevel(lvl);
		log.publish(
			new xLogRecord_Msg(log, level, msg, null)
		);
	}



	@Override
	public void flush() {
		final xLog log = this.log();
		log.flush();
	}



	@Override
	public void close() throws SecurityException {
	}



	public xLog log(final LogRecord record) {
		final String name = AliasFor( record.getLoggerName() );
		return xLog.Get(name);
	}
	public xLog log() {
		return xLog.Get();
	}



}
*/
