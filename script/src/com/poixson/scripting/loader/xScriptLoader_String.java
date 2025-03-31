/*
package com.poixson.scripting.loader;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class xScriptLoader_String extends xScriptLoader_File {

	protected final String code;



	public xScriptLoader_String(final Class<?> clss, final String code) {
		super(clss, null, null, null);
		this.code = code;
	}



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
			final Map<String, String>        flags = new HashMap<String, String>();
			final Set<String>              imports = new HashSet<String>();
			final Set<String>              exports = new HashSet<String>();
			this.loadSourceString(this.code, list, flags, imports, exports);
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



	// load script from string
	protected void loadSourceString(final String code,
			final LinkedList<xScriptSourceDAO> list, final Map<String, String> flags,
			final Set<String> imports, final Set<String> exports)
			throws IOException {
		final xScriptSourceDAO dao = new xScriptSourceDAO(false, null, null, null, code);
		this.parseHeader(dao.code, list, flags, imports, exports);
		list.add(dao);
	}



}
*/
