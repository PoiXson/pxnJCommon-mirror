package com.poixson.logger;

import static com.poixson.utils.ReflectUtils.InvokeMethod;
import static com.poixson.utils.StringUtils.ExceptionToString;
import static com.poixson.utils.Utils.IsEmpty;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.handlers.xLogHandler;
import com.poixson.logger.records.xLogRecord;
import com.poixson.logger.records.xLogRecord_Msg;
import com.poixson.logger.records.xLogRecord_Special;
import com.poixson.logger.records.xLogRecord_Special.SpecialType;
import com.poixson.shell.xConsole;
import com.poixson.tools.StdIO;


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


public class xLog {

	public static final xLevel DEFAULT_LEVEL = xLevel.INFO;

	protected static final AtomicReference<xLog> root = new AtomicReference<xLog>(null);
	protected final ConcurrentHashMap<String, xLog> loggers = new ConcurrentHashMap<String, xLog>();

	public final xLog parent;
	public final String name;

	protected final AtomicReference<xLevel> level = new AtomicReference<xLevel>(null);
	protected final AtomicReference<String[]> name_tree = new AtomicReference<String[]>(null);

	protected final CopyOnWriteArraySet<xLogHandler> handlers = new CopyOnWriteArraySet<xLogHandler>();
	protected final AtomicReference<xLogHandler> handler_default = new AtomicReference<xLogHandler>(null);
	protected final AtomicReference<xConsole> console = new AtomicReference<xConsole>(null);



	// root logger
	public static xLog Get() {
		InitRoot();
		return root.get();
	}



	// child logger
	public static xLog Get(final String name) {
		InitRoot();
		final xLog root = Get();
		if (root == null) throw new RuntimeException("Root logger not initialized");
		return root.get(name);
	}
	public xLog get(final String name) {
		InitRoot();
		if (IsEmpty(name))
			return this;
		// existing logger instance
		{
			final xLog log = this.loggers.get(name);
			if (log != null)
				return log;
		}
		// weak reference
		if (name.startsWith("_"))
			return this.getWeak(name.substring(1));
		// new logger instance
		{
			final xLog log = this.create(name);
			final xLog existing = this.loggers.putIfAbsent(name, log);
			return (existing==null ? log : existing);
		}
	}



	// weak reference
	public xLog getWeak(final String name) {
		InitRoot();
		if (IsEmpty(name))
			return this.clone();
		return this.create(name);
	}

	// weak reference
	@Override
	public xLog clone() {
		final xLog parent = (this.parent==null ? Get() : this.parent);
		final xLog log = parent.create(this.name);
		log.setLevel(this.level.get());
		log.addHandlers(this.getHandlers());
		return log;
	}



	public xLog[] getLoggers() {
		return this.loggers.values().toArray(new xLog[0]);
	}



	// initialize root logger
	public static void InitRoot() {
		if (root.get() == null)
			InvokeMethod("com.poixson.logger.xLogRoot", "InitRoot");
	}

	// logger factory
	protected xLog create(final String name) {
		return new xLog(this, name);
	}



	// root logger
	protected xLog() {
		StdIO.Init();
		this.parent = null;
		this.name   = null;
	}
	// child logger
	protected xLog(final xLog parent, final String name) {
		if (parent == null) throw new RequiredArgumentException("parent");
		if (IsEmpty(name))  throw new RequiredArgumentException("name");
		this.parent = parent;
		this.name   = name;
	}



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



	public boolean isRoot() {
		return false;
	}
	public boolean isNeverExpire() {
		if (this.isRoot())
			return true;
		return false;
	}



	public boolean isLoggable(final xLevel level) {
		if (level == null) return true;
		// check local
		final xLevel current = this.level.get();
		if (current != null) {
			if (current.isLoggable(level))
				return true;
		}
		// check parent level
		if (this.parent != null)
			return this.parent.isLoggable(level);
		return DEFAULT_LEVEL.isLoggable(level);
	}



	// -------------------------------------------------------------------------------
	// log name tree



	public String[] getNameTree() {
		if (this.isRoot())
			return new String[0];
		// cached name tree
		{
			final String[] array = this.name_tree.get();
			if (array != null)
				return array;
		}
		// build name tree
		{
			final LinkedList<String> list = new LinkedList<String>();
			this.buildNameTree(list);
			final String[] array = list.toArray(new String[0]);
			this.name_tree.set(array);
			return array;
		}
	}
	protected void buildNameTree(final LinkedList<String> list) {
		if (this.isRoot())       return;
		if (this.parent == null) return;
		this.parent.buildNameTree(list);
		if (!IsEmpty(this.name))
			list.add(this.name);
	}



	// -------------------------------------------------------------------------------
	// handlers



	protected xLogHandler createDefaultHandler() {
		return null;
	}
	public xLogHandler getDefaultHandler() {
		// existing default
		{
			final xLogHandler handler = this.handler_default.get();
			if (handler != null)
				return handler;
		}
		// new default instance
		if (this.isRoot()) {
			final xLogHandler handler = this.createDefaultHandler();
			if (this.handler_default.compareAndSet(null, handler))
				return handler;
		}
		return null;
	}
	public xLogHandler setDefaultHandler(final xLogHandler handler) {
		return this.handler_default.getAndSet(handler);
	}
	public void setDefaultHandlerIfEmpty(final xLogHandler handler) {
		this.handler_default.compareAndSet(null, handler);
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



	public xLogHandler[] getHandlers() {
		return this.handlers.toArray(new xLogHandler[0]);
	}

	public xLogHandler getHandler(final Class<? extends xLogHandler> clss) {
		if (clss == null) throw new RequiredArgumentException("clss");
		if (this.handlers.isEmpty()) {
			final xLogHandler handler = this.getDefaultHandler();
			if (clss.isInstance(handler))
				return handler;
		} else {
			for (final xLogHandler handler : this.handlers) {
				if (clss.isInstance(handler))
					return handler;
			}
		}
		return null;
	}



	public void addHandlers(final xLogHandler[] handlers) {
		for (final xLogHandler handler : handlers)
			this.addHandler(handler);
	}
	public void addHandler(final xLogHandler handler) {
		if (handler == null) throw new RequiredArgumentException("handler");
		this.handlers.add(handler);
	}



	public void removeHandler(final Class<? extends xLogHandler> remove) {
		if (remove == null) throw new RequiredArgumentException("remove");
		final List<xLogHandler> removing = new ArrayList<xLogHandler>();
		for (final xLogHandler handler : this.handlers) {
			if (remove.isInstance(handler))
				removing.add(handler);
		}
		for (final xLogHandler handler : removing)
			this.handlers.remove(handler);
	}



	public void replaceHandler(final Class<? extends xLogHandler> remove, final xLogHandler add) {
		if (remove == null) throw new RequiredArgumentException("remove");
		if (add    == null) throw new RequiredArgumentException("add");
//TODO: publish lock
		this.removeHandler(remove);
		this.handlers.add(add);
	}
	public void replaceHandler(final xLogHandler remove, final xLogHandler add) {
		if (remove == null) throw new RequiredArgumentException("remove");
		if (add    == null) throw new RequiredArgumentException("add");
//TODO: publish lock
		this.handlers.remove(remove);
		this.handlers.add(add);
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
	public void setConsole(final xConsole console) {
		final xConsole existing = this.console.getAndSet(console);
		if (existing != null)
			existing.stop();
		console.start();
	}
	public boolean hasConsole() {
		return (this.console.get() != null);
	}



	// -------------------------------------------------------------------------------
	// publish



	// std out
	public void stdout(final String msg) {
		this.publish(msg);
	}
	// std err
	public void stderr(final String msg) {
		this.publish(msg);
	}



	public void publish() {
		this.publish( (xLogRecord)null );
	}
	public void publish(final String msg) {
		if (IsEmpty(msg)) {
			this.publish();
			return;
		}
		this.publish( new xLogRecord_Msg(this, (xLevel)null, msg, (Object[])null) );
	}
	public void publish(final xLogRecord record) {
		if (record != null) {
			if (!this.isLoggable(record.getLevel()))
				return;
		}
		{
			final xLogHandler[] handlers = this.getHandlersOrDefault();
			if (!IsEmpty(handlers)) {
				for (final xLogHandler handler : handlers) {
//StdIO.OriginalOut().println("HANDLER: "+handler.toString());
					try {
						handler.publish(record);
					} catch (Exception e) {
						e.printStackTrace(StdIO.OriginalErr());
					}
				}
			}
		}
		// parent
		if (this.parent != null)
			this.parent.publish(record);
	}
	public void publish(final String[] lines) {
		if (IsEmpty(lines)) return;
		for (final String line : lines)
			this.publish(line);
	}



	public void flush() {
		this.publish( new xLogRecord_Special(SpecialType.FLUSH) );
	}
	public void clearScreen() {
		this.publish( new xLogRecord_Special(SpecialType.CLEAR_SCREEN) );
	}
	public void beep() {
		this.publish( new xLogRecord_Special(SpecialType.BEEP) );
	}



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
		if (!IsEmpty(msg))
			str.append(msg).append(" - ");
		str.append( ExceptionToString(e) );
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



}
