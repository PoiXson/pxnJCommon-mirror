package com.poixson.plugins.loaders;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import com.poixson.exceptions.IORuntimeException;
import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.plugins.xJavaPlugin;
import com.poixson.plugins.xPluginDefines;
import com.poixson.plugins.xPluginManager;
import com.poixson.utils.FileUtils;
import com.poixson.utils.Utils;


// load all plugins from dir
public class xPluginLoader_Dir<T extends xJavaPlugin> extends xPluginLoader_File<T> {

	protected final CopyOnWriteArraySet<File> paths = new CopyOnWriteArraySet<File>();



	public xPluginLoader_Dir(final xPluginManager<T> manager, final String mainClassKey) {
		super(manager, mainClassKey);
	}



	// -------------------------------------------------------------------------------
	// dir paths



	public void addDir(final String pathStr) {
		this.addDir(
			new File(pathStr)
		);
	}
	public void addDir(final File path) {
		if (path == null)        throw new RequiredArgumentException("path");
		if (!path.isDirectory()) throw new IORuntimeException("Path not found: "+path.getPath());
		this.paths.add(path);
	}
	public File[] getPaths() {
		return this.paths.toArray(new File[0]);
	}



	// -------------------------------------------------------------------------------
	// load plugins



	// load()
	@Override
	public int load() {
		int count = super.load();
		final File[] paths = this.getPaths();
		// use default path
		if (paths.length == 0) {
			if (Utils.isEmpty(xPluginDefines.DEFAULT_PLUGINS_DIR))
				throw new RequiredArgumentException("path");
			final int c =
				this.loadJarsFromDir(
					new File(xPluginDefines.DEFAULT_PLUGINS_DIR)
				);
			if (c > 0)
				count += c;
			return count;
		}
		// load plugin paths
		//PATHS_LOOP:
		for (final File path : paths) {
			final int c =
				this.loadJarsFromDir(path);
			if (c > 0)
				count += c;
		} // end PATHS_LOOP
		return count;
	}



	// load(str)
	public int loadJarsFromDir(final String pathStr) {
		if (Utils.isEmpty(pathStr))
			return this.loadJarsFromDir( (File) null );
		return this.loadJarsFromDir( new File(pathStr) );
	}



	// load(file)
	public int loadJarsFromDir(final File path) {
		final File dir;
		if (path == null) {
			if (Utils.isEmpty(xPluginDefines.DEFAULT_PLUGINS_DIR))
				throw new RequiredArgumentException("path");
			dir = new File( xPluginDefines.DEFAULT_PLUGINS_DIR );
		} else {
			dir = path;
		}
		// create plugins dir if not existing
		if ( ! dir.isDirectory() ) {
			if ( ! dir.mkdirs() ) {
				this.log().trace(
					new IOException("Failed to create plugins directory: "+dir.getName())
				);
			}
			this.log().info("Created directory:", dir.getName());
		}
		// list dir contents
		final File[] files =
			FileUtils.ListDirContents(
				dir,
				".jar"
			);
		if (files == null) throw new RuntimeException("Failed to list plugins directory!");
		// no plugins found
		if (files.length == 0) {
			this.log().fine("No plugins found to load.");
			return 0;
		}
		// load found jars
		int count = 0;
		FILES_LOOP:
		for (final File f : files) {
			if (f == null)
				continue FILES_LOOP;
			// new plugin instance
			final int c =
				super.load(f);
			if (c > 0)
				count += c;
		} // end FILES_LOOP
		if (count <= 0) {
			this.log().fine("No plugins found to load.");
		} else {
			this.log()
				.fine(
					"Found [ {} ] plugin{} from dir: {}",
					count,
					(count == 1 ? "" : "s"),
					dir.getPath()
				);
		}
		return count;
	}



}
