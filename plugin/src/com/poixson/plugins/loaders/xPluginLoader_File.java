package com.poixson.plugins.loaders;

import java.io.File;
import java.util.concurrent.CopyOnWriteArraySet;

import com.poixson.exceptions.IORuntimeException;
import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.plugins.xJavaPlugin;
import com.poixson.plugins.xPluginManager;
import com.poixson.utils.Utils;


// load a plugin from jar file
public class xPluginLoader_File<T extends xJavaPlugin> extends xPluginLoader<T> {

	protected final CopyOnWriteArraySet<File> files = new CopyOnWriteArraySet<File>();



	public xPluginLoader_File(final xPluginManager<T> manager, final String mainClassKey) {
		super(manager, mainClassKey);
	}



	// ------------------------------------------------------------------------------- //
	// file paths



	public void addFile(final String fileStr) {
		if (Utils.isEmpty(fileStr)) throw new RequiredArgumentException("fileStr");
		this.addFile(
			new File(fileStr)
		);
	}
	public void addFile(final File file) {
		if (file == null)   throw new RequiredArgumentException("file");
		if (!file.isFile()) throw new IORuntimeException("File not found: "+file.getPath());
		this.files.add(file);
	}
	public File[] getFiles() {
		return this.files.toArray(new File[0]);
	}



	// ------------------------------------------------------------------------------- //
	// load plugins



	// load()
	@Override
	public int load() {
		final File[] files = this.getFiles();
		int count = 0;
		//FILES_LOOP:
		for (final File file : files) {
			final int c =
				this.load(file);
			if (c > 0)
				count += c;
		} // end FILES_LOOP
		return count;
	}



	// load(str)
	public int load(final String fileStr) {
		if (Utils.isEmpty(fileStr)) throw new RequiredArgumentException("fileStr");
		return this.load( new File(fileStr) );
	}



	// load(file)
	public int load(final File file) {
		if (file == null) throw new RequiredArgumentException("file");
		try {
			// new plugin instance
			final T plugin = this.loadJarFile(file);
			if (plugin != null)
				return 1;
		} catch (Exception e) {
			this.log().trace(e);
		}
		return -1;
	}



}
