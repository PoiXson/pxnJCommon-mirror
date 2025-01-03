package com.poixson.tools.lang;

import static com.poixson.utils.ArrayUtils.ssArrayToMap;
import static com.poixson.utils.FileUtils.MergePaths;
import static com.poixson.utils.FileUtils.OpenLocalOrResource;
import static com.poixson.utils.FileUtils.ReadInputStream;
import static com.poixson.utils.FileUtils.cwd;
import static com.poixson.utils.GsonProvider.GSON;
import static com.poixson.utils.MathUtils.IsMinMax;
import static com.poixson.utils.StringUtils.ForceEnds;
import static com.poixson.utils.StringUtils.ssReplaceTags;
import static com.poixson.utils.Utils.IsEmpty;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;

import com.google.gson.reflect.TypeToken;
import com.poixson.exceptions.DevIdiotException;
import com.poixson.exceptions.LangTableLoadException;


public class LangTable {
	public static final String DEFAULT_LANG = "en";

	protected static final CopyOnWriteArraySet<LangTable> tables = new CopyOnWriteArraySet<LangTable>();

	protected final AtomicReference<String> path_loc = new AtomicReference<String>("languages");
	protected final AtomicReference<String> path_res = new AtomicReference<String>("languages");

	protected final ConcurrentHashMap<String, String> phrases = new ConcurrentHashMap<String, String>();
	protected final AtomicReference<String> lg = new AtomicReference<String>(null);



	public static LangTable Create() {
		final LangTable table = new LangTable();
		tables.add(table);
		return table;
	}
	protected LangTable() {
	}



	public void reload() {
		this.phrases.clear();
	}



	public String msg(final String key, final String...args) {
		if (IsEmpty(key)) return null;
		final String phrase = this.phrase(key);
		return ssReplaceTags(phrase, ssArrayToMap(args));
	}
	public String phrase(final String key) {
		for (int i=0; i<2; i++) {
			if (this.phrases.isEmpty())
				this.load();
			final String phrase = this.phrases.get(key);
			if (!IsEmpty(phrase))
				return phrase;
		}
		return null;
	}



	// -------------------------------------------------------------------------------
	// load



	public void load() {
		final String lg = this.lg();
		if (!ValidateLang(lg)) throw new IllegalArgumentException("Invalid language: "+lg);
		// load lang file
		final String file_res = ForceEnds(".json", MergePaths(       "languages",     lg));
		final String file_loc = ForceEnds(".json", MergePaths(cwd(), "testresources", lg));
		final Map<String, String> map;
		try {
			final InputStream in = OpenLocalOrResource(this.getClass(), file_loc, file_res);
			if (in == null) throw new LangTableLoadException("Failed to load language: "+lg);
			final String json = ReadInputStream(in);
			if (IsEmpty(json)) throw new LangTableLoadException("Failed to parse json for language: "+lg);
			final Type token = new TypeToken<HashMap<String, String>>() {}.getType();
			map = GSON().fromJson(json, token);
		} catch (IOException e) {
			throw new LangTableLoadException(e);
		}
		// copy into this.phrases
		final LinkedList<String> found = new LinkedList<String>();
		for (final Entry<String, String> entry : map.entrySet()) {
			found.addLast(entry.getKey());
			this.phrases.put(entry.getKey(), entry.getValue());
		}
		// check for entries to remove
		final LinkedList<String> remove = new LinkedList<String>();
		for (final String key : this.phrases.keySet()) {
			// key not found in new phrases
			if (!found.remove(key))
				remove.addLast(key);
		}
		// remove unloaded phrases
		for (final String key : remove)
			this.phrases.remove(key);
	}



	// -------------------------------------------------------------------------------
	// properties



	public LangTable lg(final String lg) {
		this.lg.set(lg);
		this.reload();
		return this;
	}
	public String lg() {
		final String lg = this.lg.get();
		if (IsEmpty(lg)) {
			if (IsEmpty(DEFAULT_LANG)) throw new DevIdiotException();
			this.lg.compareAndSet(lg, DEFAULT_LANG);
			return this.lg();
		}
		if (!ValidateLang(lg)) {
			this.lg.compareAndSet(lg, null);
			return this.lg();
		}
		return lg;
	}

	public LangTable lang(final String lang) {
		return this.lg(lang);
	}
	public String lang() {
		return this.lg();
	}



	public LangTable path_loc(final String path) {
		this.path_loc.set(path);
		return this;
	}
	public String getPathLoc() {
		return this.path_loc.get();
	}



	public LangTable path_res(final String path) {
		this.path_res.set(path);
		return this;
	}
	public String getPathRes() {
		return this.path_res.get();
	}



	public static boolean ValidateLang(final String lg) {
		if (IsEmpty(lg))      return false;
		if (lg.length() != 2) return false;
		if (!IsMinMax(lg.charAt(0), 'a', 'z')) return false;
		if (!IsMinMax(lg.charAt(1), 'a', 'z')) return false;
		return true;
	}



}
