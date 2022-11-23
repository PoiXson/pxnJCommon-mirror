package com.poixson.logger;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.tools.StdIO;
import com.poixson.utils.ReflectUtils;
import com.poixson.utils.Utils;


public abstract class xLog {
	public static final xLevel DEFAULT_LEVEL = xLevel.ALL;

	// root logger
	protected static final AtomicReference<xLog> root = new AtomicReference<xLog>(null);
	// child-loggers
	protected final ConcurrentHashMap<String, SoftReference<xLog>> loggers = new ConcurrentHashMap<String, SoftReference<xLog>>();

	public final xLog parent;
	public final String logName;

	protected final AtomicReference<xLevel> level = new AtomicReference<xLevel>(null);

	protected final CopyOnWriteArraySet<xLogHandler> handlers = new CopyOnWriteArraySet<xLogHandler>();

	protected final AtomicReference<SoftReference<String[]>> cachedNameTree = new AtomicReference<SoftReference<String[]>>(null);



	// initialize root logger
	public static void init() {
		ReflectUtils.GetClass("com.poixson.logger.xLogRoot");
	}



	// root logger
	public static xLog Get() {
		return root.get();
	}
	// child logger
	public static xLog Get(final String logName) {
		final xLog root = Get();
		if (root == null) throw new RuntimeException("Root logger not initialized");
		return root.get(logName);
	}



	public xLog get(final String logName) {
		if (Utils.isEmpty(logName))
			return Get();
		// existing logger instance
		{
			final SoftReference<xLog> ref = this.loggers.get(logName);
			if (ref != null) {
				final xLog log = ref.get();
				if (log != null)
					return log;
			}
		}
		// new logger instance
		{
			final xLog log = (this.parent==null ? Get() : this.parent).create(logName);
			final SoftReference<xLog> ref = new SoftReference<xLog>(log);
			final SoftReference<xLog> existing = this.loggers.putIfAbsent(logName, ref);
			if (existing != null) {
				final xLog lg = existing.get();
				if (lg != null)
					return lg;
			}
			return log;
		}
	}



	// new instance (weak reference)
	public xLog getWeak(final String logName) {
		if (Utils.isEmpty(logName))
			return this.clone();
		return this.create(logName);
	}
	// clone instance (weak reference)
	@Override
	public xLog clone() {
		return this.create(this.logName);
	}



	// root logger
	protected xLog() {
		StdIO.init();
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
	// logger factory
	protected abstract xLog create(final String logName);



	// -------------------------------------------------------------------------------
	// log level



	public xLevel getLevel() {
		final xLevel level = this.level.get();
		if (level == null)
			return DEFAULT_LEVEL;
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
		if (Utils.notEmpty(this.logName))
			list.add(this.logName);
	}



	// -------------------------------------------------------------------------------
	// handlers



	public xLogHandler[] getHandlers() {
		return this.handlers.toArray(new xLogHandler[0]);
	}

	public void addHandler(final xLogHandler handler) {
		if (handler == null) throw new RequiredArgumentException("handler");
		this.handlers.add(handler);
	}
	public void replaceHandler(final Class<? extends xLogHandler> remove, final xLogHandler add) {
		if (remove == null) throw new RequiredArgumentException("remove");
		if (add    == null) throw new RequiredArgumentException("add");
//TODO: publish lock
		final List<xLogHandler> removing = new ArrayList<xLogHandler>();
		final Iterator<xLogHandler> it = this.handlers.iterator();
		while (it.hasNext()) {
			final xLogHandler h = it.next();
			if (h.getClass().isInstance(remove))
				removing.add(h);
		}
		for (final xLogHandler h : removing) {
			this.handlers.remove(h);
		}
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
			if (h.getClass().isInstance(clss))
				return true;
		}
		return false;
	}
	public boolean hasHandler() {
		return ! this.handlers.isEmpty();
	}



	// -------------------------------------------------------------------------------
	// publish



	public abstract void flush();
	public abstract void clearScreen();
	public abstract void beep();



	public void publish() {
		this.publish( (xLogRecord)null );
	}

	public abstract void publish(final String msg);
	public abstract void publish(final String[] msg);
	public abstract void publish(final xLogRecord record);

	public abstract void stdout(final String msg);
	public abstract void stderr(final String msg);

	public abstract void trace(final Throwable e);
	public abstract void trace(final Throwable e, final String msg, final Object...args);

	public abstract void title  (final String msg, final Object...args);
	public abstract void detail (final String msg, final Object...args);
	public abstract void finest (final String msg, final Object...args);
	public abstract void finer  (final String msg, final Object...args);
	public abstract void fine   (final String msg, final Object...args);
	public abstract void stats  (final String msg, final Object...args);
	public abstract void info   (final String msg, final Object...args);
	public abstract void warning(final String msg, final Object...args);
	public abstract void notice (final String msg, final Object...args);
	public abstract void severe (final String msg, final Object...args);
	public abstract void fatal  (final String msg, final Object...args);



}
