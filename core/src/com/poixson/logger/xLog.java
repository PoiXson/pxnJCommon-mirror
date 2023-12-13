package com.poixson.logger;

import static com.poixson.utils.Utils.IsEmpty;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.records.xLogRecord;
import com.poixson.logger.records.xLogRecord_Msg;
import com.poixson.tools.StdIO;
import com.poixson.utils.ReflectUtils;
import com.poixson.utils.StringUtils;


//simplest - uncached
/*
	public static xLog log() {
		return xLog.Get();
	}
*/


//local cached
/*
	private final AtomicReference<SoftReference<xLog>> _log = new AtomicReference<SoftReference<xLog>>(null);
	public xLog log() {
		// cached
		{
			final SoftReference<xLog> ref = this._log.get();
			if (ref != null) {
				final xLog log = ref.get();
				if (log == null) this._log.set(null);
				else             return log;
			}
		}
		// new instance
		{
			final xLog log = this._log();
			final SoftReference<xLog> ref = new SoftReference<xLog>(log);
			if (this._log.compareAndSet(null, ref))
				return log;
		}
		return this.log();
	}
	protected xLog _log() {
		return xLog.Get(this.type.toString());
	}
*/


//TODO: expire old loggers?
public abstract class xLog {

	public static final xLevel DEFAULT_LEVEL = xLevel.INFO;

	// root logger
	protected static final AtomicReference<xLog> root = new AtomicReference<xLog>(null);
	protected static final AtomicBoolean inited = new AtomicBoolean(false);
	// child-loggers
	protected final ConcurrentHashMap<String, xLog> loggers = new ConcurrentHashMap<String, xLog>();

	public final xLog parent;
	public final String log_name;

	protected final AtomicReference<xLevel> level = new AtomicReference<xLevel>(null);

	protected final AtomicReference<xLogHandler> defaultHandler = new AtomicReference<xLogHandler>(null);
	protected final CopyOnWriteArraySet<xLogHandler> handlers = new CopyOnWriteArraySet<xLogHandler>();
	protected final AtomicReference<SoftReference<String[]>> cachedNameTree = new AtomicReference<SoftReference<String[]>>(null);

	protected final AtomicReference<xConsole> console = new AtomicReference<xConsole>(null);



	// root logger
	public static xLog Get() {
		InitRoot();
		return root.get();
	}

	// child logger
	public static xLog Get(final String log_name) {
		InitRoot();
		final xLog root = Get();
		if (root == null) throw new RuntimeException("Root logger not initialized");
		return root.get(log_name);
	}

	// child logger from instance
	public xLog get(final String log_name) {
		InitRoot();
		if (IsEmpty(log_name))
			return Get();
		// existing logger instance
		{
			final xLog log = this.loggers.get(log_name);
			if (log != null)
				return log;
		}
		// new logger instance
		{
			final xLog log = (this.parent==null ? Get() : this.parent).create(log_name);
			final xLog existing = this.loggers.putIfAbsent(log_name, log);
			return (existing == null ? log : existing);
		}
	}

	public xLog[] getChildren() {
		return this.loggers.values().toArray(new xLog[0]);
	}

	// new instance (weak reference)
	public xLog getWeak(final String log_name) {
		InitRoot();
		if (IsEmpty(log_name))
			return this.clone();
		return this.create(log_name);
	}

	// clone instance (weak reference)
	@Override
	public xLog clone() {
		return this.create(this.log_name);
	}



	// initialize root logger
	public static void InitRoot() {
		if (inited.compareAndSet(false, true))
			ReflectUtils.InvokeMethod("com.poixson.logger.xLogRoot", "InitRoot");
	}



	// root logger
	protected xLog() {
		StdIO.Init();
		this.parent  = null;
		this.log_name = null;
	}
	// child logger
	protected xLog(final xLog parent, final String log_name) {
		if (parent == null)          throw new RequiredArgumentException("parent");
		if (IsEmpty(log_name)) throw new RequiredArgumentException("log_name");
		this.parent  = parent;
		this.log_name = log_name;
	}

	// logger factory
	protected abstract xLog create(final String log_name);



	// -------------------------------------------------------------------------------
	// log level



	public xLevel getLevel() {
		final xLevel level = this.level.get();
		if (this.isRoot()) {
			if (level == null)
				return DEFAULT_LEVEL;
		}
		return level;
	}
	public void setLevel(final xLevel level) {
		this.level.set(level);
	}



	public boolean isLoggable(final xLevel level) {
		if (level == null) return true;
		// check local
		final xLevel current = this.getLevel();
		if (current != null) {
			if (current.notLoggable(level))
				return false;
		}
		// check parent level
		if (this.parent != null) {
			final boolean loggable = this.parent.isLoggable(level);
			return loggable;
		}
		return true;
	}
	public boolean notLoggable(final xLevel level) {
		return ! this.isLoggable(level);
	}



	public boolean isRoot() {
		return false;
	}



	// -------------------------------------------------------------------------------
	// log name tree



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
	protected void buildNameTree(final LinkedList<String> list) {
		if (this.isRoot())       return;
		if (this.parent == null) return;
		this.parent.buildNameTree(list);
		if (!IsEmpty(this.log_name))
			list.add(this.log_name);
	}



	// -------------------------------------------------------------------------------
	// publish



	public abstract void stdout(final String msg);
	public abstract void stderr(final String msg);

	public void publish() {
		this.publish( (xLogRecord)null );
	}
	public abstract void publish(final String msg);
	public abstract void publish(final xLogRecord record);

	public void publish(final String[] lines) {
		if (IsEmpty(lines)) return;
		for (final String line : lines)
			this.publish(line);
	}

	public abstract void flush();
	public abstract void clearScreen();
	public abstract void beep();



	// title
	public void title(final String msg, final Object...args) {
		this.publish( new xLogRecord_Msg(this, xLevel.TITLE, msg, args) );
	}



	// trace exception
	public void trace(final Throwable e) {
		this.trace(e, null);
	}
	public void trace(final Throwable e, final String msg, final Object...args) {
		final StringBuilder str = new StringBuilder();
		if (!IsEmpty(msg)) {
			str.append(msg)
				.append(" - ");
		}
		str.append( StringUtils.ExceptionToString(e) );
		this.publish( new xLogRecord_Msg(this, xLevel.SEVERE, str.toString(), args) );
	}



	// detail
	public void detail(final String msg, final Object...args) {
		this.publish( new xLogRecord_Msg(this, xLevel.DETAIL, msg, args) );
	}
	// finest
	public void finest(final String msg, final Object...args) {
		this.publish( new xLogRecord_Msg(this, xLevel.FINEST, msg, args) );
	}
	// finer
	public void finer(final String msg, final Object...args) {
		this.publish( new xLogRecord_Msg(this, xLevel.FINER, msg, args) );
	}
	// fine
	public void fine(final String msg, final Object...args) {
		this.publish( new xLogRecord_Msg(this, xLevel.FINE, msg, args) );
	}
	// stats
	public void stats(final String msg, final Object...args) {
		this.publish( new xLogRecord_Msg(this, xLevel.STATS, msg, args) );
	}
	// info
	public void info(final String msg, final Object...args) {
		this.publish( new xLogRecord_Msg(this, xLevel.INFO, msg, args) );
	}
	// warning
	public void warning(final String msg, final Object...args) {
		this.publish( new xLogRecord_Msg(this, xLevel.WARNING, msg, args) );
	}
	// notice
	public void notice(final String msg, final Object...args) {
		this.publish( new xLogRecord_Msg(this, xLevel.NOTICE, msg, args) );
	}
	// severe
	public void severe(final String msg, final Object...args) {
		this.publish( new xLogRecord_Msg(this, xLevel.SEVERE, msg, args) );
	}
	// fatal
	public void fatal(final String msg, final Object...args) {
		this.publish( new xLogRecord_Msg(this, xLevel.FATAL, msg, args) );
	}



	// -------------------------------------------------------------------------------
	// handlers



	public abstract xLogHandler getDefaultHandler();

	public xLogHandler setDefaultHandler(final xLogHandler handler) {
		return this.defaultHandler.getAndSet(handler);
	}
	public void setDefaultHandlerIfEmpty(final xLogHandler handler) {
		this.defaultHandler.compareAndSet(null, handler);
	}



	public xLogHandler[] getHandlersOrDefault() {
		// defined handlers
		{
			final xLogHandler[] hands = this.handlers.toArray(new xLogHandler[0]);
			if (!IsEmpty(hands))
				return hands;
		}
		// default handler
		{
			final xLogHandler handler = this.getDefaultHandler();
			if (handler != null)
				return new xLogHandler[] { handler };
		}
		return null;
	}
	public xLogHandler getHandler(final Class<? extends xLogHandler> clss) {
		if (clss == null) throw new RequiredArgumentException("clss");
		final Iterator<xLogHandler> it = this.handlers.iterator();
		while (it.hasNext()) {
			final xLogHandler h = it.next();
			if (clss.isInstance(h))
				return h;
		}
		return null;
	}

	public void addHandler(final xLogHandler handler) {
		if (handler == null) throw new RequiredArgumentException("handler");
		this.handlers.add(handler);
	}

	public void removeHandler(final Class<? extends xLogHandler> remove) {
		if (remove == null) throw new RequiredArgumentException("remove");
		final List<xLogHandler> removing = new ArrayList<xLogHandler>();
		final Iterator<xLogHandler> it = this.handlers.iterator();
		while (it.hasNext()) {
			final xLogHandler h = it.next();
			if (remove.isInstance(h))
				removing.add(h);
		}
		for (final xLogHandler h : removing)
			this.handlers.remove(h);
	}

	public void replaceHandler(final Class<? extends xLogHandler> remove, final xLogHandler add) {
		if (remove == null) throw new RequiredArgumentException("remove");
		if (add    == null) throw new RequiredArgumentException("add");
//TODO: publish lock
		this.removeHandler(remove);
		this.handlers.add(add);
//TODO: release lock
	}
	public void replaceHandler(final xLogHandler remove, final xLogHandler add) {
		if (remove == null) throw new RequiredArgumentException("remove");
		if (add    == null) throw new RequiredArgumentException("add");
//TODO: publish lock
		this.handlers.remove(remove);
		this.handlers.add(add);
//TODO: release lock
	}



	public int getHandlerCount() {
		return this.handlers.size();
	}
	public boolean hasHandler(final Class<? extends xLogHandler> clss) {
		for (final xLogHandler h : this.handlers) {
			if (clss.isInstance(h))
				return true;
		}
		return false;
	}
	public boolean hasHandler() {
		return ! this.handlers.isEmpty();
	}
	public boolean hasOnlyHandler(final Class<? extends xLogHandler> clss) {
		if (this.handlers.size() != 1) return false;
		if (!this.hasHandler(clss)   ) return false;
		return true;
	}



	public xConsole getConsole() {
		return this.console.get();
	}
	public xConsole setConsole(final xConsole console) {
		return this.console.getAndSet(console);
	}



}
