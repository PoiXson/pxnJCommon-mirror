/*
package com.poixson.plugins.loaders;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.xeustechnologies.jcl.JarClassLoader;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.logger.xLog;
import com.poixson.plugins.xJavaPlugin;
import com.poixson.plugins.xPluginDefines;
import com.poixson.plugins.xPluginManager;
import com.poixson.plugins.xPluginYML;
import com.poixson.utils.ConfigUtils;
import com.poixson.utils.Utils;


public abstract class xPluginLoader<T extends xJavaPlugin> implements Runnable {

	protected final xPluginManager<T> manager;

	protected final String mainClassKey;



	public xPluginLoader(final xPluginManager<T> manager, final String mainClassKey) {
		if (manager == null) throw new RequiredArgumentException("manager");
		this.manager = manager;
		this.mainClassKey = Utils.ifEmpty(mainClassKey, xPluginDefines.DEFAULT_PLUGIN_CLASS_KEY);
	}



	// -------------------------------------------------------------------------------
	// load plugin



	protected abstract int load();



	@Override
	public void run() {
		final int count = this.load();
		if (count > 0) {
			this.log()
				.info(
					"Found [ %d ] plugin%s",
					count,
					(count == 1 ? "" : "s")
				);
		}
	}



	// load plugin jar file
	protected T loadJarFile(final File file) {
		if (file == null) throw new RequiredArgumentException("file");
		if ( ! file.exists() ) {
			this.log().warning("Plugin file not found:", file.getPath());
			return null;
		}
		final String jarFileStr = file.getName();
		final String resFileStr = xPluginDefines.PLUGIN_YML_FILE;
		// jar class loader
		this.log().finest("Loading plugin jar:", file.getName());
		final JarClassLoader jcl = new JarClassLoader();
		try {
			jcl.add(
				new FileInputStream(file)
			);
			//jcl.add( file.toURI().toURL() );
		} catch (IOException e) {
			this.log().trace(e, "Failed to load plugin jar file");
			return null;
		}
		// load plugin.yml file
		final xPluginYML yml =
			this.loadPluginYML(
				jcl,
				jarFileStr,
				resFileStr
			);
		if (yml == null)
			return null;
		// load plugin instance
		final T plugin =
			this.loadPluginClass(
				jcl,
				yml
			);
		if (plugin == null)
			return null;
		return plugin;
	}



	// load plugin.yml file
	protected xPluginYML loadPluginYML(final JarClassLoader jcl,
			final String jarFileStr, final String resFileStr) {
		if (jcl == null) throw new RequiredArgumentException("jcl");
		if (Utils.isEmpty(jarFileStr)) throw new RequiredArgumentException("jarFileStr");
		if (Utils.isEmpty(resFileStr)) throw new RequiredArgumentException("resFileStr");
		InputStream in = null;
		try {
			in = jcl.getResourceAsStream(resFileStr);
		} finally {
			Utils.safeClose(in);
		}
		if (in == null) {
			this.log().warning("Failed to load yaml file %s from jar:", resFileStr, jarFileStr);
			return null;
		}
		final Map<String, Object> datamap = ConfigUtils.LoadYamlFromStream(in);
		if (datamap == null) {
			this.log().warning("Failed to parse yaml file %s%s from jar:", resFileStr, jarFileStr);
			return null;
		}
		final xPluginYML yml =
			new xPluginYML(
				datamap,
				this.mainClassKey
			);
		// validate yml key values
		if (Utils.isEmpty(yml.getPluginName())) {
			this.log().warning("Plugin name not set in %s of %s", resFileStr, jarFileStr);
			return null;
		}
		if (Utils.isEmpty(yml.getMainClass())) {
			this.log().warning("'%s' not set in %s of %s", this.mainClassKey, resFileStr, jarFileStr);
			return null;
		}
		return yml;
	}



	// load plugin instance
	protected T loadPluginClass(final JarClassLoader jcl, final xPluginYML yml) {
		if (jcl == null) throw new RequiredArgumentException("jcl");
		if (yml == null) throw new RequiredArgumentException("yml");
		final String pluginName = yml.getPluginName();
		this.log().info("Loading plugin:", yml.getPluginTitle());
		// check plugin already loaded
		if ( this.manager.isPluginLoaded(pluginName) ) {
			this.log().warning("Plugin already loaded:", pluginName);
			return null;
		}
		// find plugin class
		final String classStr = yml.getMainClass();
		if (Utils.isEmpty(classStr))
			return null;
		// load plugin class
		Class<T> clss = null;
		try {
			this.log().finest("Attempting to load plugin main class:", classStr);
			clss = this.castPluginClass(
				jcl.loadClass(classStr)
			);
		} catch (ClassNotFoundException e) {
			this.log().trace(e);
		}
		if (clss == null)
			return null;
		// get plugin class constructor
		Constructor<T> construct = null;
		try {
			construct = clss.getConstructor();
		} catch (NoSuchMethodException e) {
			this.log().trace(e);
		} catch (SecurityException e) {
			this.log().trace(e);
		}
		if (construct == null)
			return null;
		// new plugin class instance
		T plugin = null;
		try {
			plugin = construct.newInstance();
		} catch (InstantiationException e) {
			this.log().trace(e);
		} catch (IllegalAccessException e) {
			this.log().trace(e);
		} catch (IllegalArgumentException e) {
			this.log().trace(e);
		} catch (InvocationTargetException e) {
			this.log().trace(e);
		}
		if (plugin == null)
			return null;
		if (plugin.isFailed())
			return null;
		plugin.setVars(
			jcl,
			this.manager,
			yml
		);
		// register with plugin manager
		this.manager.register(plugin);
		return plugin;
	}
	@SuppressWarnings("unchecked")
	protected Class<T> castPluginClass(final Class<?> clss) {
		return (Class<T>) clss;
	}
//TODO: plugins can depend on other plugins
//		// check required libraries
//		final Set<String> required;
//		try {
//			required = cfg.getStringSet("Requires");
//		} catch (Exception e) {
//			this.log().trace(e);
//			return null;
//		}
//		// check plugin dependencies
//		if (Utils.notEmpty(required)) {
//			for (final String libPath : required) {
//				if (!Utils.isLibAvailable(libPath)) {
//					this.log()
//						.fatal("Plugin requires library:", libPath);
//					return null;
//				}
//			}
//		}



	// logger
	public xLog log() {
		return this.manager.log();
	}



}
*/
