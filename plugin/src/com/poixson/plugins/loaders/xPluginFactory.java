package com.poixson.plugins.loaders;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.poixson.plugins.xJavaPlugin;
import com.poixson.plugins.xPluginManager;
import com.poixson.plugins.xPluginYML;


public class xPluginFactory<T extends xJavaPlugin> {



	public xPluginFactory() {
	}



	public T build(final xPluginManager<T> manager, final xPluginYML yml,
			final Class<T> clss, final String class_main) throws IOException {
		final Constructor<T> construct;
		try {
			construct = this.getConstruct(clss);
		} catch (NoSuchMethodException e) {
			throw new IOException(e);
		} catch (SecurityException e) {
			throw new IOException(e);
		}
		if (construct == null)
			throw new IOException("Failed to get instance constructor: " + class_main);
		final T plugin;
		try {
			plugin = construct.newInstance(manager, yml);
		} catch (InstantiationException    e) { throw new IOException(e);
		} catch (IllegalAccessException    e) { throw new IOException(e);
		} catch (IllegalArgumentException  e) { throw new IOException(e);
		} catch (InvocationTargetException e) { throw new IOException(e); }
		if (plugin == null)
			throw new IOException("Failed to create new instance of plugin class: " + class_main);
		return plugin;
	}



	protected Constructor<T> getConstruct(final Class<T> clss)
			throws NoSuchMethodException, SecurityException {
		return clss.getConstructor(xPluginManager.class, xPluginYML.class);
	}

	protected T getPlugin(final Constructor<T> construct,
			final xPluginManager<T> manager, final xPluginYML yml)
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		return construct.newInstance(manager, yml);
	}



}
