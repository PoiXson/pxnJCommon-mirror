/*
package com.poixson.scripting.loader;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;


public abstract class xScriptLoader {

	protected final AtomicReference<xScriptSourceDAO[]> sources = new AtomicReference<xScriptSourceDAO[]>(null);
	protected final AtomicReference<Map<String, String>>  flags = new AtomicReference<Map<String, String>>(null);
	protected final AtomicReference<String[]>           imports = new AtomicReference<String[]>(null);
	protected final AtomicReference<String[]>           exports = new AtomicReference<String[]>(null);



	public xScriptLoader() {
	}



	public void reload() {
		this.sources.set(null);
		try {
			this.getSources();
		} catch (IOException ignore) {}
	}



	public abstract boolean hasChanged();



	public abstract xScriptSourceDAO[] getSources() throws IOException;

	protected abstract void loadSources(final String filename,
			final LinkedList<xScriptSourceDAO> list,
			final Map<String, String> flags,
			final Set<String> imports, final Set<String> exports)
			throws IOException;

	protected void parseHeader(final String code,
			final LinkedList<xScriptSourceDAO> list,
			final Map<String, String> flags,
			final Set<String> imports, final Set<String> exports)
			throws IOException {
		String lines = code;
		String line;
		int pos;
		LOOP_LINES:
		while (true) {
			// blank line
			if (lines.startsWith("\n")) {
				lines = lines.substring(1);
				continue LOOP_LINES;
			}
			// block comment
			if (lines.startsWith("/"+"*")) {
				pos = lines.indexOf("*"+"/");
				if (pos == -1) return;
				lines = lines.substring(pos + 2);
				continue LOOP_LINES;
			}
			if (lines.startsWith("//#")) {
				pos = lines.indexOf('\n');
				if (pos == -1) {
					line  = lines.substring(3).trim();
					lines = "";
				} else {
					line  = lines.substring(3, pos).trim();
					lines = lines.substring(pos + 1);
				}
				if (line.length() == 0)
					break LOOP_LINES;
				pos = line.indexOf('=');
				// statement flag
				if (pos == -1) {
					flags.put(line, null);
				// key/value flag
				} else {
					final String key = line.substring(0, pos);
					final String val = line.substring(pos + 1);
					SWITCH_FLAG:
					switch (key) {
					//#include=file.js
					case "include": this.loadSources(val, list, flags, imports, exports); break SWITCH_FLAG;
					//#import=var
					case "import": imports.add(val); break SWITCH_FLAG;
					//#export=var
					case "export": exports.add(val); break SWITCH_FLAG;
					default:    flags.put(key, val); break SWITCH_FLAG;
					} // end SWITCH_FLAG
				}
				continue LOOP_LINES;
			} // end //#
			if (lines.startsWith("//")) {
				pos = lines.indexOf("\n");
				if (pos == -1) break LOOP_LINES;
				lines = lines.substring(pos + 1);
				continue LOOP_LINES;
			}
			break LOOP_LINES;
		} // end LOOP_LINES
	}



	// -------------------------------------------------------------------------------



	public abstract String getName();
	public abstract String getScriptFile();



	public boolean hasFlag(final String key) {
		final Map<String, String> flags = this.flags.get();
		return (flags == null ? false : flags.containsKey(key));
	}
	public String getFlag(final String key) {
		final Map<String, String> flags = this.flags.get();
		return (flags == null ? null : flags.get(key));
	}



	public Map<String, String> getFlags() {
		return this.flags.get();
	}

	public String[] getImports() {
		final String[] imports = this.imports.get();
		return (imports == null ? new String[0] : imports);
	}
	public String[] getExports() {
		final String[] exports = this.exports.get();
		return (exports == null ? new String[0] : exports);
	}

	public boolean hasImport(final String key) {
		final String[] imports = this.imports.get();
		for (final String k : imports) {
			if (key.equals(k))
				return true;
		}
		return false;
	}
	public boolean hasExport(final String key) {
		final String[] exports = this.exports.get();
		for (final String k : exports) {
			if (key.equals(k))
				return true;
		}
		return false;
	}



}
*/
