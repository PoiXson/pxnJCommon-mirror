/*
package com.poixson.logger;

import java.lang.ref.SoftReference;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.handlers.xLogHandler;
import com.poixson.logger.proxies.LoggerToXLog;
import com.poixson.logger.records.xLogRecord;
import com.poixson.logger.records.xLogRecord_Msg;
import com.poixson.tools.StdIO;
import com.poixson.utils.StringUtils;
import com.poixson.utils.Utils;


public class xLog implements xLogInterface {
	public static final xLevel DEFAULT_LEVEL = xLevel.ALL;
	public static final boolean OVERRIDE_STDIO = true;

	// root logger
	private static final AtomicReference<xLogRoot> root = new AtomicReference<xLogRoot>(null);
	// child-loggers
	private final ConcurrentMap<String, xLog> loggers = new ConcurrentHashMap<String, xLog>();

	public final xLog parent;
	public final String logName;

	protected final AtomicReference<xLevel> level = new AtomicReference<xLevel>(null);

	// print handlers
	protected final AtomicReference<xLogHandler[]> handlers = new AtomicReference<xLogHandler[]>(null);

	protected final AtomicReference<SoftReference<String[]>> cachedNameTree =
			new AtomicReference<SoftReference<String[]>>(null);



	// root logger
	public static xLogRoot GetRoot() {
		if (root.get() == null) {
			final xLogRoot log = new xLogRoot();
			if (root.compareAndSet(null, log)) {
				LoggerToXLog.init();
				return log;
			}
		}
		return root.get();
	}
	public static xLogRoot PeekRoot() {
		return root.get();
	}



	// get logger
	public static xLog Get(final String logName) {
		return GetRoot().get(logName);
	}
	public xLog get(final String logName) {
		if (Utils.isEmpty(logName))
			return GetRoot();
		// existing logger instance
		{
			final xLog log = this.loggers.get(logName);
			if (log != null)
				return log;
		}
		// new logger instance
		{
			final xLog log = new xLog(this, logName);
			final xLog existing = this.loggers.putIfAbsent(logName, log);
			if (existing != null)
				return existing;
			return log;
		}
	}
	// new instance (weak reference)
	public xLog getWeak(final String logName) {
		if (Utils.isEmpty(logName))
			return this.clone();
		return new xLog(this, logName);
	}
	// clone instance (weak reference)
	@Override
	public xLog clone() {
		return new xLog(this, this.logName);
	}



	// root logger
	protected xLog() {
		this.parent  = null;
		this.logName = null;
	}
	// child logger
	protected xLog(final xLog parent, final String logName) {
		if (parent == null)         throw new RequiredArgumentException("parent");
		if (Utils.isEmpty(logName)) throw new RequiredArgumentException("logName");
		this.parent  = parent;
		this.logName = logName;
	}



	// -------------------------------------------------------------------------------



	@Override
	public String[] getNameTree() {
		if (this.isRoot())
			return new String[0];
		// cached name tree
		{
			final SoftReference<String[]> soft = this.cachedNameTree.get();
			if (soft != null) {
				final String[] list = soft.get();
				if (list != null)
					return list;
			}
		}
		// build name tree
		{
			final LinkedList<String> list = new LinkedList<String>();
			this.buildNameTree(list);
			final String[] result = list.toArray(new String[0]);
			this.cachedNameTree.set(
				new SoftReference<String[]>(result)
			);
			return result;
		}
	}
	protected void buildNameTree(final List<String> list) {
		if (this.isRoot())       return;
		if (this.parent == null) return;
		this.parent.buildNameTree(list);
		if (Utils.notEmpty(this.logName))
			list.add(this.logName);
	}



	// -------------------------------------------------------------------------------
	// log handlers



	public xLogHandler[] getLogHandlers() {
		return this.handlers.get();
	}
	public void addHandler(final xLogHandler addHandler) {
		if (addHandler == null) throw new RequiredArgumentException("addHandler");
		synchronized (this.handlers) {
			final xLogHandler[] array = this.handlers.get();
			if (array == null) {
				this.handlers.set(new xLogHandler[] { addHandler });
			} else {
				final List<xLogHandler> list = Arrays.asList(array);
				list.add(addHandler);
				this.handlers.set( list.toArray(new xLogHandler[0]) );
			}
		}
	}
	public void replaceHandler(final Class<? extends xLogHandler> removeClass, final xLogHandler addHandler) {
		if (removeClass == null) throw new RequiredArgumentException("removeClass");
		if (addHandler  == null) throw new RequiredArgumentException("addHandler");
		synchronized (this.handlers) {
			final xLogHandler[] array = this.handlers.get();
			if (array == null) {
				this.handlers.set(new xLogHandler[] { addHandler });
			} else {
				final List<xLogHandler> list = Arrays.asList(array);
				final Iterator<xLogHandler> it = list.iterator();
				while (it.hasNext()) {
					final xLogHandler handler = it.next();
					if (handler.getClass().isInstance(removeClass)) {
						it.remove();
					}
				}
				list.add(addHandler);
				this.handlers.set( list.toArray(new xLogHandler[0]) );
			}
		}
	}
	public void replaceHandler(final xLogHandler removeHandler, final xLogHandler addHandler) {
		if (removeHandler == null) throw new RequiredArgumentException("removeHandler");
		if (addHandler    == null) throw new RequiredArgumentException("addHandler");
		synchronized (this.handlers) {
			final xLogHandler[] array = this.handlers.get();
			if (array == null) {
				this.handlers.set(new xLogHandler[] { addHandler });
			} else {
				final List<xLogHandler> list = Arrays.asList(array);
				list.remove(removeHandler);
				list.add(addHandler);
				this.handlers.set( list.toArray(new xLogHandler[0]) );
			}
		}
	}



	public int getHandlerCount() {
		final xLogHandler[] handlers = this.handlers.get();
		if (Utils.isEmpty(handlers))
			return 0;
		return handlers.length;
	}
	public boolean hasHandler(final Class<? extends xLogHandler> clss) {
		final xLogHandler[] handlers = this.handlers.get();
		if (handlers == null)
			return false;
		for (final xLogHandler handler : handlers) {
			if (handler.getClass().isInstance(clss))
				return true;
		}
		return false;
	}
	public boolean hasHandler() {
		return Utils.isEmpty(this.handlers.get());
	}



	// -------------------------------------------------------------------------------
	// log level



	@Override
	public xLevel getLevel() {
		final xLevel level = this.level.get();
		if (level == null)
			return DEFAULT_LEVEL;
		return level;
	}
	@Override
	public void setLevel(final xLevel level) {
		this.level.set(level);
	}



	@Override
	public boolean isLoggable(final xLevel level) {
		if (level == null) return true;
		// check local
		final xLevel currentLevel = this.getLevel();
		if (currentLevel != null) {
			if (currentLevel.notLoggable(level))
				return false;
		}
		// check parent level
		if (this.parent != null) {
			final boolean loggable = this.parent.isLoggable(level);
			return loggable;
		}
		return true;
	}
	@Override
	public boolean notLoggable(final xLevel level) {
		return ! this.isLoggable(level);
	}



	@Override
	public boolean isRoot() {
		return false;
	}



	// -------------------------------------------------------------------------------
	// publish



	@Override
	public void publish() {
		final xLogHandler[] handlers = this.getLogHandlers();
		if (Utils.notEmpty(handlers)) {
			for (final xLogHandler handler : handlers) {
				handler.publish();
			}
		}
		if (this.parent != null) {
			this.parent.publish();
		}
	}
	@Override
	public void publish(final String msg) {
		final xLogHandler[] handlers = this.getLogHandlers();
		if (Utils.notEmpty(handlers)) {
			for (final xLogHandler handler : handlers) {
				handler.publish(msg);
			}
		}
		if (this.parent != null) {
			this.parent.publish(msg);
		}
	}
	@Override
	public void publish(final xLogRecord record) {
		if (record == null) {
			this.publish();
			return;
		}
		if (this.notLoggable(record.getLevel()))
			return;
		final xLogHandler[] handlers = this.getLogHandlers();
		if (Utils.notEmpty(handlers)) {
			for (final xLogHandler handler : handlers) {
				try {
					handler.publish(record);
				} catch (Exception e) {
					e.printStackTrace(StdIO.OriginalErr);
				}
			}
		}
		if (this.parent != null) {
			this.parent.publish(record);
		}
	}



	@Override
	public void flush() {
		final xLogHandler[] handlers = this.getLogHandlers();
		if (Utils.notEmpty(handlers)) {
			for (final xLogHandler handler : handlers) {
				handler.flush();
			}
		}
		if (this.parent != null) {
			this.parent.flush();
		}
	}
	@Override
	public void clearScreen() {
		final xLogHandler[] handlers = this.getLogHandlers();
		if (Utils.notEmpty(handlers)) {
			for (final xLogHandler handler : handlers) {
				handler.clearScreen();
			}
		}
		if (this.parent != null) {
			this.parent.clearScreen();
		}
	}
	@Override
	public void beep() {
		final xLogHandler[] handlers = this.getLogHandlers();
		if (Utils.notEmpty(handlers)) {
			for (final xLogHandler handler : handlers) {
				handler.beep();
			}
		}
		if (this.parent != null) {
			this.parent.beep();
		}
	}



	// -------------------------------------------------------------------------------
	// publish helpers



	// title
	@Override
	public void title(final String msg, final Object...args) {
		this.publish(
			new xLogRecord_Msg(this, xLevel.TITLE, msg, args)
		);
	}



	// trace exception
	@Override
	public void trace(final Throwable e) {
		this.trace(e, null);
	}
	@Override
	public void trace(final Throwable e, final String msg, final Object...args) {
		final StringBuilder str = new StringBuilder();
		if (Utils.notEmpty(msg)) {
			str.append(msg)
				.append(" - ");
		}
		str.append(
			StringUtils.ExceptionToString(e)
		);
		this.publish(
			new xLogRecord_Msg(
				this,
				xLevel.SEVERE,
				str.toString(),
				args
			)
		);
	}



	// std out
	@Override
	public void stdout(final String msg) {
		this.publish(msg);
	}

	// std err
	@Override
	public void stderr(final String msg) {
		this.publish(msg);
	}

	// detail
	@Override
	public void detail(final String msg, final Object...args) {
		this.publish(
			new xLogRecord_Msg(this, xLevel.DETAIL, msg, args)
		);
	}

	// finest
	@Override
	public void finest(final String msg, final Object...args) {
		this.publish(
			new xLogRecord_Msg(this, xLevel.FINEST, msg, args)
		);
	}

	// finer
	@Override
	public void finer(final String msg, final Object...args) {
		this.publish(
			new xLogRecord_Msg(this, xLevel.FINER, msg, args)
		);
	}

	// fine
	@Override
	public void fine(final String msg, final Object...args) {
		this.publish(
			new xLogRecord_Msg(this, xLevel.FINE, msg, args)
		);
	}

	// stats
	@Override
	public void stats(final String msg, final Object...args) {
		this.publish(
			new xLogRecord_Msg(this, xLevel.STATS, msg, args)
		);
	}

	// info
	@Override
	public void info(final String msg, final Object...args) {
		this.publish(
			new xLogRecord_Msg(this, xLevel.INFO, msg, args)
		);
	}

	// warning
	@Override
	public void warning(final String msg, final Object...args) {
		this.publish(
			new xLogRecord_Msg(this, xLevel.WARNING, msg, args)
		);
	}

	// notice
	@Override
	public void notice(final String msg, final Object...args) {
		this.publish(
			new xLogRecord_Msg(this, xLevel.NOTICE, msg, args)
		);
	}

	// severe
	@Override
	public void severe(final String msg, final Object...args) {
		this.publish(
			new xLogRecord_Msg(this, xLevel.SEVERE, msg, args)
		);
	}

	// fatal
	@Override
	public void fatal(final String msg, final Object...args) {
		this.publish(
			new xLogRecord_Msg(this, xLevel.FATAL, msg, args)
		);
	}



}
*/