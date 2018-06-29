package com.poixson.plugins;

import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;
import com.poixson.logger.xLogRoot;
import com.poixson.utils.Utils;


public class xPluginManager<T extends xJavaPlugin> {
	private static final String LOG_NAME = "xPluginManager";

	private final ConcurrentMap<String, T> plugins =
		new ConcurrentHashMap<String, T>();



	public xPluginManager() {}



	/**
	 * Adds a plugin to the manager.
	 * @param plugin A new plugin instance to manage.
	 * @return true if successful, false if plugin already registered.
	 */
	public boolean register(final T plugin) {
		if (plugin == null) throw new RuntimeException("Missing plugin instance!");
		final String pluginName = plugin.getPluginName();
		final T existing =
			this.plugins.putIfAbsent(pluginName, plugin);
		if (existing != null) {
			this.log()
				.warning("Plugin already loaded: {}", pluginName);
			return false;
		}
		plugin.doInit();
		return true;
	}



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



	public boolean isPluginLoaded(final String pluginName) {
		if (Utils.isBlank(pluginName)) throw new RequiredArgumentException("pluginName");
		return
			this.plugins
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



	public void enableAll() {
		final int count = this.plugins.size();
		this.log()
			.info(
				"Starting [ {} ] plugin{}..",
				Integer.valueOf(count),
				(count == 1 ? "" : "s")
			);
		final Iterator<Entry<String, T>> it = this.plugins.entrySet().iterator();
		while (it.hasNext()) {
			final Entry<String, T> entry = it.next();
			final T plugin = entry.getValue();
			this.enable(plugin);
		}
	}
	public void enable(final String pluginName) {
		if (Utils.isEmpty(pluginName)) throw new RequiredArgumentException("pluginName");
		final T plugin = this.getPlugin(pluginName);
		if (plugin == null) throw new RuntimeException("Unknown plugin: "+pluginName);
		this.enable(plugin);
	}
	public void enable(final T plugin) {
		if (plugin == null) throw new RequiredArgumentException("plugin");
		plugin.doEnable();
	}



	public void disableAll() {
		final Iterator<Entry<String, T>> it = this.plugins.entrySet().iterator();
		while (it.hasNext()) {
			final Entry<String, T> entry = it.next();
			final T plugin = entry.getValue();
			this.disable(plugin);
		}
	}
	public void disable(final String pluginName) {
		if (Utils.isEmpty(pluginName)) throw new RequiredArgumentException("pluginName");
		final T plugin = this.getPlugin(pluginName);
		if (plugin == null) throw new RuntimeException("Unknown plugin: "+pluginName);
		this.disable(plugin);
	}
	public void disable(final T plugin) {
		if (plugin == null) throw new RequiredArgumentException("plugin");
		plugin.doDisable();
	}



	// ------------------------------------------------------------------------------- //
	// logger



	private final AtomicReference<SoftReference<xLog>> _log =
			new AtomicReference<SoftReference<xLog>>(null);

	public xLog log() {
		// cached logger
		final SoftReference<xLog> ref = this._log.get();
		if (ref != null) {
			final xLog log = ref.get();
			if (log != null)
				return log;
		}
		// get logger
		{
			final xLog log = xLogRoot.Get( this.logName );
			this._log.set(
				new SoftReference<xLog>( log )
			);
			return log;
		}
	}



}
