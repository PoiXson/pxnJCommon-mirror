package com.poixson.plugins;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;
import com.poixson.plugins.loaders.xPluginLoader;
import com.poixson.utils.Utils;


public abstract class xPluginManager<T extends xJavaPlugin> {

	protected final CopyOnWriteArraySet<xPluginLoader<T>> loaders = new CopyOnWriteArraySet<xPluginLoader<T>>();

	// loaded plugins
	protected final ConcurrentHashMap<String, T> plugins = new ConcurrentHashMap<String, T>();

	// state queue
	protected final LinkedList<T> queue_init  = new LinkedList<T>();
	protected final LinkedList<T> queue_term  = new LinkedList<T>();
	protected final LinkedList<T> queue_start = new LinkedList<T>();
	protected final LinkedList<T> queue_stop  = new LinkedList<T>();



	public xPluginManager() {
	}



	// -------------------------------------------------------------------------------
	// startup/shutdown plugins



	public int load() {
		// plugin loaders
		int count = 0;
		final Iterator<xPluginLoader<T>> it = this.loaders.iterator();
		while (it.hasNext()) {
			final xPluginLoader<T> loader = it.next();
			try {
				count += loader.load();
			} catch (Exception e) {
				this.log().trace(e);
			}
		}
		if (count > 0) this.log().fine("Found %d plugin%s", Integer.valueOf(count), count==0?"":"s");
		else           this.log().notice("No plugins found to load");
		return count;
	}
//TODO
//	public int unload() {
//return 0;
//	}



	public boolean run() {
		// stop plugins
		STOP_BLOCK:
		{
			final T plugin;
			try { plugin = this.queue_stop.removeFirst();
			} catch (NoSuchElementException ignore) { break STOP_BLOCK; }
			if (plugin == null) break STOP_BLOCK;
			try {
				this.log().finer("Stop plugin: %s", plugin.getPluginName());
				plugin.setState(xPluginState.STOPPING);
				plugin.stop();
				plugin.setState(xPluginState.STOPPED);
			} catch (Exception e) {
				this.log().trace(e);
				plugin.fail(e);
			}
			return false;
		}
		// terminate plugins
		TERM_BLOCK:
		{
			final T plugin;
			try { plugin = this.queue_term.removeFirst();
			} catch (NoSuchElementException ignore) { break TERM_BLOCK; }
			if (plugin == null) break TERM_BLOCK;
			try {
				this.log().finer("Terminate plugin: %s", plugin.getPluginName());
				plugin.setState(xPluginState.TERMINATING);
				plugin.term();
				plugin.setState(null);
			} catch (Exception e) {
				this.log().trace(e);
				plugin.fail(e);
			}
			return false;
		}
		// init plugins
		INIT_BLOCK:
		{
			final T plugin;
			try { plugin = this.queue_init.removeFirst();
			} catch (NoSuchElementException ignore) { break INIT_BLOCK; }
			if (plugin == null)     break INIT_BLOCK;
			if (plugin.isFailed()) break INIT_BLOCK;
			try {
				this.log().finer("Init plugin: %s", plugin.getPluginName());
				plugin.setState(xPluginState.INITING);
				plugin.init();
				plugin.setState(xPluginState.INITED);
			} catch (Exception e) {
				this.log().trace(e);
				plugin.fail(e);
			}
			return false;
		}
		// start plugins
		START_BLOCK:
		{
			final T plugin;
			try { plugin = this.queue_start.removeFirst();
			} catch (NoSuchElementException ignore) { break START_BLOCK; }
			if (plugin == null)     break START_BLOCK;
			if (plugin.isFailed()) break START_BLOCK;
			try {
				this.log().finer("Start plugin: %s", plugin.getPluginName());
				plugin.setState(xPluginState.STARTING);
				plugin.start();
				plugin.setState(xPluginState.RUNNING);
			} catch (Exception e) {
				this.log().trace(e);
				plugin.fail(e);
			}
			return false;
		}
		// finished plugin queues
		return true;
	}



	public int initAll() {
		int count = 0;
		final Iterator<T> it = this.plugins.values().iterator();
		while (it.hasNext()) {
			final T plugin = it.next();
			this.queue_init.addLast(plugin);
			count++;
		}
		return count;
	}
	public int termAll() {
		int count = 0;
		final Iterator<T> it = this.plugins.values().iterator();
		while (it.hasNext()) {
			final T plugin = it.next();
			this.queue_term.addLast(plugin);
			count++;
		}
		return count;
	}

	public int startAll() {
		int count = 0;
		final Iterator<T> it = this.plugins.values().iterator();
		while (it.hasNext()) {
			final T plugin = it.next();
			this.queue_start.addLast(plugin);
			count++;
		}
		return count;
	}
	public int stopAll() {
		int count = 0;
		final Iterator<T> it = this.plugins.values().iterator();
		while (it.hasNext()) {
			final T plugin = it.next();
			this.queue_stop.addLast(plugin);
			count++;
		}
		return count;
	}



	public void init(final T plugin) {
		this.queue_init.addLast(plugin);
	}
	public void term(final T plugin) {
		this.queue_term.addLast(plugin);
	}

	public void start(final T plugin) {
		this.queue_start.addLast(plugin);
	}
	public void stop(final T plugin) {
		this.queue_stop.addLast(plugin);
	}



	// -------------------------------------------------------------------------------
	// loaders and plugins



	public void addLoader(final xPluginLoader<T> loader) {
		this.loaders.add(loader);
	}
	public boolean removeLoader(final xPluginLoader<T> loader) {
		return this.loaders.remove(loader);
	}
	public int removeAllLoaders() {
		int count = 0;
		final Iterator<xPluginLoader<T>> it = this.loaders.iterator();
		while (it.hasNext()) {
			it.remove();
			count++;
		}
		return count;
	}



	public void register(final T plugin) {
		final String name = plugin.getPluginName();
		this.plugins.put(name, plugin);
	}

	public T getPlugin(final String pluginName) {
		if (Utils.isEmpty(pluginName))
			return null;
		return this.plugins.get(pluginName);
	}



	public boolean isPluginLoaded(final String plugin_name) {
		if (Utils.isEmpty(plugin_name)) throw new RequiredArgumentException("plugin_name");
		return this.plugins.containsKey(plugin_name);
	}
	public boolean isPluginLoaded(final T plugin) {
		if (plugin == null) throw new RequiredArgumentException("plugin");
		return this.isPluginLoaded( plugin.getPluginName() );
	}



	public int getPluginCount(final xPluginState state) {
		int count = 0;
		final Iterator<T> it = this.plugins.values().iterator();
		while (it.hasNext()) {
			final T plugin = it.next();
			if (plugin.isState(state))
				count++;
		}
		return count;
	}
	public int getPluginsRunning() {
		return this.getPluginCount(xPluginState.RUNNING);
	}
	public int getPluginCount() {
		return this.plugins.size();
	}



	// -------------------------------------------------------------------------------
	// logger



	private final AtomicReference<xLog> _log = new AtomicReference<xLog>(null);

	public xLog log() {
		if (this._log.get() == null) {
			final xLog log = this._log();
			if (this._log.compareAndSet(null, log))
				return log;
		}
		return this._log.get();
	}
	protected xLog _log() {
		return xLog.Get("plugin");
	}



}
