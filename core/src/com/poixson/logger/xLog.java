package com.poixson.logger;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.handlers.xLogHandler;
import com.poixson.logger.records.xLogRecord;
import com.poixson.logger.records.xLogRecord_Msg;
import com.poixson.tools.StdIO;
import com.poixson.utils.StringUtils;
import com.poixson.utils.Utils;


public class xLog implements xLogInterface {
	public static final xLevel DEFAULT_LEVEL = xLevel.ALL;
	public static final boolean OVERRIDE_STDIO = true;

	// child-loggers
	private final ConcurrentMap<String, xLog> loggers = new ConcurrentHashMap<String, xLog>();

	public final xLog parent;
	public final String logName;
	protected final AtomicReference<xLevel> level = new AtomicReference<xLevel>(null);

	protected final AtomicReference<SoftReference<String[]>> cachedNameTree =
			new AtomicReference<SoftReference<String[]>>(null);

	// print handlers
	protected final AtomicReference<xLogHandler[]> handlers = new AtomicReference<xLogHandler[]>(null);



	// get logger
	public xLog get(final String logName) {
		if (Utils.isEmpty(logName))
			return xLogRoot.Get();
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



	// ------------------------------------------------------------------------------- //



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
			final List<String> list = new ArrayList<String>();
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



	// ------------------------------------------------------------------------------- //
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



	// ------------------------------------------------------------------------------- //
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



	// ------------------------------------------------------------------------------- //
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
	public void publish(final String line) {
		final xLogHandler[] handlers = this.getLogHandlers();
		if (Utils.notEmpty(handlers)) {
			for (final xLogHandler handler : handlers) {
				handler.publish(line);
			}
		}
		if (this.parent != null) {
			this.parent.publish(line);
		}
	}
	@Override
	public void publish(final String[] lines) {
		final xLogHandler[] handlers = this.getLogHandlers();
		if (Utils.notEmpty(handlers)) {
			for (final xLogHandler handler : handlers) {
				handler.publish(lines);
			}
		}
		if (this.parent != null) {
			this.parent.publish(lines);
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



//TODO
//	public void getPublishLock() {
//		throw new UnsupportedOperationException();
//	}
//	public void releasePublishLock() {
//		throw new UnsupportedOperationException();
//	}



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



	// ------------------------------------------------------------------------------- //
	// publish helpers



	// title
	@Override
	public void title(final String line, final Object...args) {
		this.publish(
			new xLogRecord_Msg(xLevel.TITLE, line, args)
		);
	}
	@Override
	public void title(final String[] lines, final Object...args) {
		this.publish(
			new xLogRecord_Msg(xLevel.TITLE, lines, args)
		);
	}



	// trace exception
	@Override
	public void trace(final Throwable e) {
		this.trace(e, null);
	}
	@Override
	public void trace(final Throwable e, final String line, final Object...args) {
		final StringBuilder str = new StringBuilder();
		if (Utils.notEmpty(line)) {
			str.append(line)
				.append(" - ");
		}
		str.append(
			StringUtils.ExceptionToString(e)
		);
		this.publish(
			new xLogRecord_Msg(
				xLevel.SEVERE,
				StringUtils.StringToArray(str.toString()),
				args
			)
		);
	}



	// std out
	@Override
	public void stdout(final String...lines) {
//TODO
this.publish(lines);
	}



	// std err
	@Override
	public void stderr(final String...lines) {
//TODO
this.publish(lines);
	}



	// detail
	@Override
	public void detail(final String line, final Object...args) {
		this.publish(
			new xLogRecord_Msg(xLevel.DETAIL, line, args)
		);
	}
	@Override
	public void detail(final String[] lines, final Object...args) {
		this.publish(
			new xLogRecord_Msg(xLevel.DETAIL, lines, args)
		);
	}



	// finest
	@Override
	public void finest(final String line, final Object...args) {
		this.publish(
			new xLogRecord_Msg(xLevel.FINEST, line, args)
		);
	}
	@Override
	public void finest(final String[] lines, final Object...args) {
		this.publish(
			new xLogRecord_Msg(xLevel.FINEST, lines, args)
		);
	}



	// finer
	@Override
	public void finer(final String line, final Object...args) {
		this.publish(
			new xLogRecord_Msg(xLevel.FINER, line, args)
		);
	}
	@Override
	public void finer(final String[] lines, final Object...args) {
		this.publish(
			new xLogRecord_Msg(xLevel.FINER, lines, args)
		);
	}



	// fine
	@Override
	public void fine(final String line, final Object...args) {
		this.publish(
			new xLogRecord_Msg(xLevel.FINE, line, args)
		);
	}
	@Override
	public void fine(final String[] lines, final Object...args) {
		this.publish(
			new xLogRecord_Msg(xLevel.FINE, lines, args)
		);
	}



	// info
	@Override
	public void info(final String line, final Object...args) {
		this.publish(
			new xLogRecord_Msg(xLevel.INFO, line, args)
		);
	}
	@Override
	public void info(final String[] lines, final Object...args) {
		this.publish(
			new xLogRecord_Msg(xLevel.INFO, lines, args)
		);
	}



	// stats
	@Override
	public void stats(final String line, final Object...args) {
		this.publish(
			new xLogRecord_Msg(xLevel.STATS, line, args)
		);
	}
	@Override
	public void stats(final String[] lines, final Object...args) {
		this.publish(
			new xLogRecord_Msg(xLevel.STATS, lines, args)
		);
	}



	// warning
	@Override
	public void warning(final String line, final Object...args) {
		this.publish(
			new xLogRecord_Msg(xLevel.WARNING, line, args)
		);
	}
	@Override
	public void warning(final String[] lines, final Object...args) {
		this.publish(
			new xLogRecord_Msg(xLevel.WARNING, lines, args)
		);
	}



	// notice
	@Override
	public void notice(final String line, final Object...args) {
		this.publish(
			new xLogRecord_Msg(xLevel.NOTICE, line, args)
		);
	}
	@Override
	public void notice(final String[] lines, final Object...args) {
		this.publish(
			new xLogRecord_Msg(xLevel.NOTICE, lines, args)
		);
	}



	// severe
	@Override
	public void severe(final String line, final Object...args) {
		this.publish(
			new xLogRecord_Msg(xLevel.SEVERE, line, args)
		);
	}
	@Override
	public void severe(final String[] lines, final Object...args) {
		this.publish(
			new xLogRecord_Msg(xLevel.SEVERE, lines, args)
		);
	}



	// fatal
	@Override
	public void fatal(final String line, final Object...args) {
		this.publish(
			new xLogRecord_Msg(xLevel.FATAL, line, args)
		);
	}
	@Override
	public void fatal(final String[] lines, final Object...args) {
		this.publish(
			new xLogRecord_Msg(xLevel.FATAL, lines, args)
		);
	}



}
