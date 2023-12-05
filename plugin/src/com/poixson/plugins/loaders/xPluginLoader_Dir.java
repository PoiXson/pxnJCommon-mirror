package com.poixson.plugins.loaders;

import static com.poixson.utils.Utils.IfEmpty;

import java.io.File;
import java.io.IOException;

import com.poixson.plugins.xJavaPlugin;
import com.poixson.plugins.xPluginManager;
import com.poixson.utils.FileUtils;


// load all plugins from dir
public class xPluginLoader_Dir<T extends xJavaPlugin> extends xPluginLoader<T> {
	public static final String DEFAULT_PLUGINS_DIR = "plugins/";

	protected final String dir;



	public xPluginLoader_Dir(final xPluginManager<T> manager,
			final xPluginFactory<T> factory, final String keyClassMain, final String dir) {
		super(manager, factory, keyClassMain);
		this.dir = IfEmpty(dir, DEFAULT_PLUGINS_DIR);
	}



	@Override
	public int load() throws IOException {
		final File dir = new File(this.dir);
		this.log().finer("Loading plugins from: %s/", dir.getName());
		// create dir
		if (!dir.isDirectory()) {
			this.log().info("Creating plugins directory: %s/", dir);
			if (!dir.mkdirs())
				throw new IOException("Failed to create plugins directory: "+dir.getName());
		}
		// list dir contents
		final File[] files = FileUtils.ListDirContents(dir, ".jar");
		if (files == null) throw new RuntimeException("Failed to list plugins directory: "+dir.getName());
		// no plugins found
		if (files.length == 0) {
			this.log().fine("No plugins found to load in dir: %s/", dir.getName());
			return 0;
		}
		// load found jars
		int count = 0;
		FILES_LOOP:
		for (final File f : files) {
			if (f == null)
				continue FILES_LOOP;
			try {
				final T plugin = super.load(f);
				if (plugin != null) {
					count++;
					this.manager.register(plugin);
				}
			} catch (Exception e) {
				this.log().trace(e);
			}
		} // end FILES_LOOP
		return count;
	}



}
