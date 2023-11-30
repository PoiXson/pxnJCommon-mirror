package com.poixson.plugins.loaders;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLevel;
import com.poixson.logger.xLog;
import com.poixson.plugins.xJavaPlugin;
import com.poixson.plugins.xPluginManager;
import com.poixson.plugins.xPluginYML;
import com.poixson.utils.Utils;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.Resource;
import io.github.classgraph.ResourceList;
import io.github.classgraph.ScanResult;


//TODO: plugins can depend on other plugins
public abstract class xPluginLoader<T extends xJavaPlugin> {
	public static final String DEFAULT_KEY_CLASS_MAIN  = "Main Class";
	public static final String DEFAULT_PLUGIN_YML_FILE = "plugin.yml";
	public static final boolean VERBOSE_CLASSGRAPH = false;

	protected final xPluginManager<T> manager;
	protected final xPluginFactory<T> factory;

	protected final String keyClassMain;



	public xPluginLoader(final xPluginManager<T> manager,
			final xPluginFactory<T> factory, final String keyClassMain) {
		this.manager      = manager;
		this.keyClassMain = Utils.ifEmpty(keyClassMain, DEFAULT_KEY_CLASS_MAIN);
		this.factory = factory;
	}



	public abstract int load() throws IOException;

	// load plugin jar file
	public T load(final File file) throws IOException{
		if (file == null) throw new RequiredArgumentException("file");
		if (!file.exists()) throw new IOException("Plugin file not found: " + file.getName());
		this.log().finer("Loading plugin jar: %s", file.getName());
		// load jar
		final ScanResult classgraph =
			(new ClassGraph())
				.verbose(this.log().isLoggable(xLevel.FINEST) && VERBOSE_CLASSGRAPH)
				.overrideClasspath(file)
				.scan();
		// load plugin.yml
		final xPluginYML yml;
		{
			final ResourceList resources = classgraph.getResourcesWithLeafName(DEFAULT_PLUGIN_YML_FILE);
			final int resource_size = resources.size();
			if (resource_size == 0)
				throw new IOException(DEFAULT_PLUGIN_YML_FILE + " file not found in plugin: " + file.getName());
			if (resource_size > 1)
				throw new IOException("Multiple " + DEFAULT_PLUGIN_YML_FILE + " files found in plugin: " + file.getName());
			final Resource res = resources.get(0);
			yml = this.loadPluginYML(res);
			if (Utils.isEmpty(yml.getMainClass())) {
				this.log().warning("'%s' not set in %s of %s", this.keyClassMain, DEFAULT_PLUGIN_YML_FILE, file.getName());
				return null;
			}
		}
		// check plugin already loaded
		final String plugin_name = yml.getPluginName();
		if (this.manager.isPluginLoaded(plugin_name))
			throw new IOException("Plugin already loaded: " + plugin_name);
		// load plugin instance
		final String class_main = yml.getMainClass();
		if (Utils.isEmpty(class_main)) {
			throw new IOException(String.format(
				"Class key: %s not found in plugin: %s",
				this.keyClassMain,
				file.getName()
			));
		}
		this.log().finest("Attempting to load plugin main class: %s", class_main);
		final Class<T> clss = this.castPluginClass(
			classgraph.loadClass(class_main, true)
		);
		if (clss == null) throw new IOException("Plugin class not found: " + class_main);
		return this.factory.build(this.manager, yml, clss, class_main);
	}



	@SuppressWarnings("unchecked")
	protected Class<T> castPluginClass(final Class<?> clss) {
		return (Class<T>) clss;
	}



	// load plugin.yml file
	public xPluginYML loadPluginYML(final Resource res) throws IOException {
		if (res == null) return null;
		return this.loadPluginYML(res.getContentAsString());
	}
	public xPluginYML loadPluginYML(final String data) {
		if (Utils.isEmpty(data)) return null;
		final Yaml yml = new Yaml();
		final Map<String, Object> datamap = yml.load(data);
		return this.loadPluginYML(datamap);
	}
	public xPluginYML loadPluginYML(final Map<String, Object> datamap) {
		if (Utils.isEmpty(datamap)) return null;
		return new xPluginYML(datamap, this.keyClassMain);
	}



	// logger
	public xLog log() {
		return this.manager.log();
	}



}
