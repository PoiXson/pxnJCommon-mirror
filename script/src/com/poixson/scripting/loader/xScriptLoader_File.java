package com.poixson.scripting.loader;

import static com.poixson.utils.FileUtils.MergePaths;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;


public class xScriptLoader_File extends xScriptLoader {

	protected final Class<?> clss;

	protected final String path_local;
	protected final String path_resource;
	protected final String filename;



	public xScriptLoader_File(final Class<?> clss,
			final String path_local, final String path_resource,
			final String filename) {
		super();
		this.clss          = clss;
		this.path_local    = path_local;
		this.path_resource = path_resource;
		this.filename      = filename;
	}



//TODO: use this
	@Override
	public boolean hasChanged() {
		final xScriptSourceDAO[] sources = this.sources.get();
		if (sources != null) {
			for (final xScriptSourceDAO src : sources) {
				if (src.hasFileChanged())
					return true;
			}
		}
		return false;
	}



	// -------------------------------------------------------------------------------



	@Override
	public xScriptSourceDAO[] getSources()
			throws IOException {
		// existing sources
		{
			final xScriptSourceDAO[] sources = this.sources.get();
			if (sources != null)
				return sources;
		}
		// load sources
		{
			final LinkedList<xScriptSourceDAO> list = new LinkedList<xScriptSourceDAO>();
			final Map<String, String>         flags = new HashMap<String, String>();
			final Set<String>               imports = new HashSet<String>();
			final Set<String>               exports = new HashSet<String>();
			// load prepend.js
			try {
				this.loadSources("prepend.js", list, flags, imports, exports);
			} catch (FileNotFoundException ignore) {}
			// load script
			this.loadSources(this.filename, list, flags, imports, exports);
			final xScriptSourceDAO[] sources = list.toArray(new xScriptSourceDAO[0]);
			if (this.sources.compareAndSet(null, sources)) {
				this.flags.set(Collections.unmodifiableMap(flags));
				this.imports.set(imports.toArray(new String[0]));
				this.exports.set(exports.toArray(new String[0]));
				return sources;
			}
		}
		return this.sources.get();
	}



	// load sources recursively
	@Override
	protected void loadSources(final String filename,
			final LinkedList<xScriptSourceDAO> list, final Map<String, String> flags,
			final Set<String> imports, final Set<String> exports)
			throws IOException {
		// find local or resource file
		final xScriptSourceDAO dao =
			xScriptSourceDAO.Find(
				this.clss,
				this.path_local,
				this.path_resource,
				filename
			);
		if (dao == null) throw new FileNotFoundException(filename);
		this.parseHeader(dao.code, list, flags, imports, exports);
		list.add(dao);
	}



	// -------------------------------------------------------------------------------



	@Override
	public String getName() {
		return this.filename;
	}



	@Override
	public String getScriptFile() {
		return MergePaths(this.path_local, this.filename);
	}



}
