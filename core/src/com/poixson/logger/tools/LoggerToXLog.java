package com.poixson.logger.tools;

import static com.poixson.utils.Utils.IsEmpty;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.poixson.logger.xLevel;
import com.poixson.logger.xLog;
import com.poixson.logger.records.xLogRecord_Msg;
import com.poixson.tools.Keeper;


public class LoggerToXLog extends Handler {

	protected static final AtomicBoolean inited = new AtomicBoolean(false);



	public static void Init() {
		if (inited.compareAndSet(false, true)) {
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
				final LoggerToXLog log = new LoggerToXLog();
				logger.addHandler(log);
				Keeper.Add(log);
			}
			// default log levels
			xLog.Get("mqtt" ).setLevel(xLevel.INFO);
			xLog.Get("jline").setLevel(xLevel.INFO);
			xLog.Get("netty").setLevel(xLevel.INFO);
			xLog.Get("jcl"  ).setLevel(xLevel.INFO);
			xLog.Get("GUI"  ).setLevel(xLevel.INFO);
		}
	}



	protected LoggerToXLog() {
	}



	public static String AliasFor(final String name) {
		if (IsEmpty(name)) return null;
		if (name.startsWith("org.eclipse.paho.client")) return "mqtt";
		if (name.equals(    "org.jline"   )) return "jline";
		if (name.startsWith("io.netty"    )) return "netty";
		if (name.startsWith("java.awt."   )) return "GUI";
		if (name.startsWith("javax.swing.")) return "GUI";
		if (name.startsWith("sun.awt."    )) return "GUI";
		if (name.equals("java.lang.ProcessBuilder")) return "exec";
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
