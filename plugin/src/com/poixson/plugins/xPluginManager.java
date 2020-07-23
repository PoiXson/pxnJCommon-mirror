package com.poixson.plugins;

import static com.poixson.logger.xLog.XLog;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.abstractions.xStartable;
import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLevel;
import com.poixson.logger.xLog;
import com.poixson.plugins.loaders.xPluginLoader;
import com.poixson.threadpool.types.xThreadPool_Main;
import com.poixson.utils.Utils;


public abstract class xPluginManager<T extends xJavaPlugin> implements xStartable, Runnable {
	private static final String DEFAULT_LOG_NAME = "xPluginManager";

	protected final String logName;

	// loaders
	protected final CopyOnWriteArraySet<xPluginLoader<T>> loaders =
			new CopyOnWriteArraySet<xPluginLoader<T>>();
	// unloaders
//	protected final AtomicReference<xPluginUnloader> unloader =
//			new AtomicReference<xPluginUnloader>(null);

	// loaded plugins
	protected final ConcurrentHashMap<String, T> plugins =
			new ConcurrentHashMap<String, T>();

	// state queue
	protected final Map<xPluginState, Set<T>> queues =
			new HashMap<xPluginState, Set<T>>();
	protected AtomicInteger countCache = new AtomicInteger(0);

	protected final AtomicBoolean stopping = new AtomicBoolean(false);
	protected final AtomicBoolean restartAfterUnload = new AtomicBoolean(false);

	static {
		xLogRoot.Get("jcl")
			.setLevel(xLevel.INFO);
	}



	public xPluginManager(final String logName) {
		if (Utils.isEmpty(logName)) throw new RequiredArgumentException("logName");
		this.logName = ( Utils.isEmpty(logName) ? DEFAULT_LOG_NAME : logName );
		this.queues.put( xPluginState.INIT,   new HashSet<T>() );
		this.queues.put( xPluginState.START,  new HashSet<T>() );
		this.queues.put( xPluginState.STOP,   new HashSet<T>() );
		this.queues.put( xPluginState.UNLOAD, new HashSet<T>() );
	}
	public xPluginManager() {
		this(null);
	}



	// ------------------------------------------------------------------------------- //
	// startup/shutdown plugins



	@Override
	public void start() {
		// only run in main thread
		if ( xThreadPool_Main.get().force(this, "start") ) return;
		synchronized (this.queues) {
			// run plugin loaders
			{
				final Iterator<xPluginLoader<T>> it = this.loaders.iterator();
				//LOAD_LOOP:
				while (it.hasNext()) {
//					if ( ! xPluginState.INIT.equals(this.state.get()) ) {
//						this.log().warning( "Plugin loading interrupted by state:", this.state.get().toString() );
//						return;
//					}
					final xPluginLoader<T> loader = it.next();
					loader.run();
				} // end LOAD_LOOP
			}
			// count loaded plugins
			{
				int count = 0;
				final Iterator<T> it = this.plugins.values().iterator();
				final Set<T> initQueue = this.queues.get(xPluginState.INIT);
				//COUNT_LOOP:
				while (it.hasNext()) {
					final T plugin = it.next();
					if (plugin.isState(null)) {
						count++;
						initQueue.add(plugin);
					}
				} // end COUNT_LOOP
				this.countCache.set(count);
				if (count == 0) {
					this.log().warning("No plugins found to load");
				} else {
					this.log().info( "Loading [ {} ] plugin{}..", count, (count == 1 ? "" : "s") );
					this.queueRun("Init-Plugins");
				}
			}
		} // end sync
	}



	@Override
	public void stop() {
		// only run in main thread
		if ( xThreadPool_Main.get().force(this, "stop") ) return;
		if ( ! this.stopping.compareAndSet(false, true) )
			return;
//TODO:
//		// unload plugins
//		synchronized (this.plugins) {
//			final Collection<T> list = this.plugins.values();
//			if ( ! list.isEmpty() ) {
//				this.log().info("Unloading [ {} ] plugins..", list.size());
//				final Iterator<T> it = this.plugins.values().iterator();
//				while (it.hasNext()) {
//					final T plugin = it.next();
//					this.unload(plugin);
//				}
//			}
//		}
	}



	@Override
	public void run() {
		synchronized (this.queues) {
			// init next plugin
			INIT_BLOCK:
			{
				final T plugin = this.findNextToRun(xPluginState.INIT);
				if (plugin == null)
					break INIT_BLOCK;
				final xLog log = plugin.log();
				try {
					log.finest("Init plugin:", plugin.getPluginName());
					plugin.init();
					if (plugin.notFailed()) {
						if (plugin.setState( null, xPluginState.INIT )) {
							this.queues.get(xPluginState.START)
								.add(plugin);
						}
					}
				} catch (Exception e) {
					final xPluginState previousState =
						plugin.setState(xPluginState.FAILED);
					log.trace(e);
					if ( ! xPluginState.FAILED.equals(previousState) ) {
						try {
							plugin.failed();
						} catch (Exception e2) {
							log.trace(e2);
						}
					}
				} // end catch e
				// queue run again
				this.queueRun("Start-Plugins");
				return;
			}
			// start next plugin
			START_BLOCK:
			{
				final T plugin = this.findNextToRun(xPluginState.START);
				if (plugin == null)
					break START_BLOCK;
				final xLog log = plugin.log();
				try {
					log.finest("Starting plugin:", plugin.getPluginName());
					if (plugin.setState( xPluginState.INIT, xPluginState.START)) {
						plugin.start();
						if (plugin.notFailed()) {
							plugin.setState( xPluginState.INIT, xPluginState.RUNNING);
						}
					}
				} catch (Exception e) {
					final xPluginState previousState =
						plugin.setState(xPluginState.FAILED);
					log.trace(e);
					if ( ! xPluginState.FAILED.equals(previousState) ) {
						try {
							plugin.failed();
						} catch (Exception e2) {
							log.trace(e2);
						}
					}
				} // end catch e
				// queue run again
				this.queueRun("Finished-Loading-Plugins");
				return;
			}
			// finished loading plugins
			{
				final int count = this.countCache.getAndSet(0);
				this.log().info( "Loaded [ {} ] plugin{}", count, (count == 1 ? "" : "s") );
			}
		}
	}
	protected void queueRun(final String name) {
		// queue run again
		xThreadPool_Main.get()
			.runTaskLater(name, this);
	}
	protected T findNextToRun(final xPluginState state) {
		if (state == null) throw new RequiredArgumentException("state");
		synchronized (this.queues) {
			final xPluginState previousState = state.previous();
			final Set<T> plugins = this.queues.get(state);
			final Iterator<T> it = plugins.iterator();
			PLUGINS_LOOP:
			while (it.hasNext()) {
				final T plugin = it.next();
				// failed
				if (plugin.isFailed())
					continue PLUGINS_LOOP;
				if (plugin.isState(previousState)) {
					it.remove();
					return plugin;
				}
			} // end PLUGINS_LOOP
		} // end sync
		return null;
	}



	// ------------------------------------------------------------------------------- //
	// loaders/unloaders



	public void addLoader(final xPluginLoader<T> loader) {
		if (loader == null) throw new RequiredArgumentException("loader");
		this.loaders.add(loader);
	}
	public boolean removeLoader(final xPluginLoader<T> loader) {
		if (loader == null) throw new RequiredArgumentException("loader");
		return this.loaders.remove(loader);
	}



	// ------------------------------------------------------------------------------- //
	// plugins



	public boolean isPluginLoaded(final String pluginName) {
		if (Utils.isBlank(pluginName)) throw new RequiredArgumentException("pluginName");
		return this.plugins
				.containsKey(pluginName);
	}
	public boolean isPluginLoaded(final T plugin) {
		if (plugin == null) throw new RequiredArgumentException("plugin");
		return
			this.isPluginLoaded(
				plugin.getPluginName()
			);
	}



	public T getPlugin(final String pluginName) {
		if (Utils.isEmpty(pluginName))
			return null;
		return this.plugins.get(pluginName);
	}



	/**
	 * Adds a plugin to the manager.
	 * @param plugin The new plugin instance to manage.
	 * @return true if successful, false if plugin already registered.
	 */
	public boolean register(final T plugin) {
		if (plugin == null) throw new RuntimeException("Missing plugin instance!");
		final String pluginName = plugin.getPluginName();
		final T existing =
			this.plugins.putIfAbsent(pluginName, plugin);
		if (existing != null) {
			plugin.log().warning("Plugin already loaded:", pluginName);
			return false;
		}
		return true;
	}



//TODO:
/*
	public boolean unregister(final String pluginName) {
		if (Utils.isBlank(pluginName))
			return false;
		final T existing = this.plugins.remove(pluginName);
		if (existing != null) {
			existing.doUnload();
			return true;
		}
		return false;
	}
	public boolean unregister(final T plugin) {
		if (plugin == null)
			return false;
		final String pluginName = plugin.getPluginName();
		return this.unregister(pluginName);
	}
	public void unloadAll() {
		final Iterator<Entry<String, T>> it = this.plugins.entrySet().iterator();
		while (it.hasNext()) {
			final Entry<String, T> entry = it.next();
			final T plugin = entry.getValue();
			this.disable(plugin);
			this.unregister(plugin);
		}
	}
*/



	// ------------------------------------------------------------------------------- //
	// state



	@Override
	public boolean isRunning() {
		if (this.stopping.get())
			return false;
		return ! this.plugins.isEmpty();
	}
	@Override
	public boolean isStopping() {
		return this.stopping.get();
	}



	public String[] getPluginNames() {
		final Set<String> names = new HashSet<String>();
		final Iterator<T> it = this.plugins.values().iterator();
		while (it.hasNext()) {
			final T plugin = it.next();
			if (plugin.isRunning())
				names.add( plugin.getPluginName() );
		}
		return names.toArray(new String[0]);
	}
	public String[] getPluginTitles() {
		final Set<String> titles = new HashSet<String>();
		final Iterator<T> it = this.plugins.values().iterator();
		while (it.hasNext()) {
			final T plugin = it.next();
			if (plugin.isRunning())
				titles.add( plugin.getPluginName() );
		}
		return titles.toArray(new String[0]);
	}



	// ------------------------------------------------------------------------------- //
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
		return XLog("plugin");
	}



}
