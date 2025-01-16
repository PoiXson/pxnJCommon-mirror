package com.poixson.tools.localization;

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
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poixson.exceptions.DevIdiotException;
import com.poixson.exceptions.LangTableLoadException;
import com.poixson.tools.abstractions.Tuple;
import com.poixson.tools.atomic.AtomicModularInteger;
import com.poixson.tools.gson.GsonAdapter_LangTable;


public class LangTable {
	public static final String DEFAULT_LANG = "en";

	protected static final CopyOnWriteArraySet<LangTable> tables = new CopyOnWriteArraySet<LangTable>();

	protected final AtomicReference<String> path_loc = new AtomicReference<String>("languages");
	protected final AtomicReference<String> path_res = new AtomicReference<String>("languages");

	protected final ConcurrentHashMap<String, Tuple<String[], AtomicModularInteger>> phrase_book =
			new ConcurrentHashMap<String, Tuple<String[], AtomicModularInteger>>();
	protected final AtomicReference<String> lg = new AtomicReference<String>(null);



	public static LangTable Create() {
		final LangTable table = new LangTable();
		tables.add(table);
		return table;
	}
	protected LangTable() {
	}



	// -------------------------------------------------------------------------------
	// load



	public void load() {
		final String lg = this.lg();
		if (!ValidateLang(lg)) throw new IllegalArgumentException("Invalid language: "+lg);
		// load lang file
		final String file_res = ForceEnds(".json", MergePaths(       "languages",     lg));
		final String file_loc = ForceEnds(".json", MergePaths(cwd(), "testresources", lg));
		final Map<String, String[]> map;
		try {
			final InputStream in = OpenLocalOrResource(this.getClass(), file_loc, file_res);
			if (in == null) throw new LangTableLoadException("Language file not found or failed to load: "+file_res);
			final String json = ReadInputStream(in);
			if (IsEmpty(json)) throw new LangTableLoadException("Failed to parse json for language: "+lg);
			final Type token = new TypeToken<Map<String, String[]>>() {}.getType();
			final Gson gson = GSON(
				token, new GsonAdapter_LangTable()
			);
			map = gson.fromJson(json, token);
		} catch (IOException e) {
			throw new LangTableLoadException(e);
		}
		// copy into this.phrases
		final LinkedList<String> found = new LinkedList<String>();
		for (final Entry<String, String[]> entry : map.entrySet()) {
			final String   key     = entry.getKey();
			final String[] phrases = entry.getValue();
			final String key_norm = NormKey(key);
			found.addLast(key_norm);
			final int len = phrases.length;
			final AtomicModularInteger index = (len>1 ? new AtomicModularInteger(0, 0, len-1) : null);
			final Tuple<String[], AtomicModularInteger> tup = new Tuple<String[], AtomicModularInteger>(phrases, index);
			this.phrase_book.put(key_norm, tup);
		}
		// check for entries to remove
		final LinkedList<String> remove = new LinkedList<String>();
		for (final String key : this.phrase_book.keySet()) {
			// key not found in new phrases
			if (!found.remove(key))
				remove.addLast(key);
		}
		// remove unloaded phrases
		for (final String key : remove)
			this.phrase_book.remove(key);
	}



	public void reload() {
		this.phrase_book.clear();
	}



	// -------------------------------------------------------------------------------
	// get



	public static String NormKey(final String key) {
		if (IsEmpty(key)) return key;
		return key
			.replace('-', '_')
			.replace('.', '_');
	}



	public String getPhrase(final String key, final String...args) {
		if (IsEmpty(key)) return null;
		final String phrase = this.getPhrase(key);
		return ssReplaceTags(phrase, ssArrayToMap(args));
	}
	public String getPhrase(final String key) {
		final Tuple<String[], AtomicModularInteger> tup = this.getPhrases(key);
		if (tup == null) return null;
		final String[] phrases = tup.key;
		final int len = phrases.length;
		if (len > 1) {
			final AtomicModularInteger index = tup.val;
			final int idx = (index==null ? 0 : index.getAndIncrement());
			return phrases[idx];
		}
		return phrases[0];
	}
	public Tuple<String[], AtomicModularInteger> getPhrases(final String key) {
		// try 1
		{
			if (this.phrase_book.isEmpty())
				this.load();
			final Tuple<String[], AtomicModularInteger> tup = this.phrase_book.get(key);
			if (tup != null)
				return tup;
		}
		// try 2
		{
			if (this.phrase_book.isEmpty())
				this.load();
			final Tuple<String[], AtomicModularInteger> tup = this.phrase_book.get(key);
			if (tup != null)
				return tup;
		}
		return null;
	}



	public String getPhrase(final Object key, final String...args) {
		return this.getPhrase(key.toString(), args);
	}
	public String getPhrase(final Object key) {
		return this.getPhrase(key.toString());
	}
	public Tuple<String[], AtomicModularInteger> getPhrases(final Object key) {
		return this.getPhrases(key.toString());
	}



	// -------------------------------------------------------------------------------
	// properties



	public static boolean ValidateLang(final String lg) {
		if (IsEmpty(lg)                      ) return false;
		if (lg.length() != 2                 ) return false;
		if (!IsMinMax(lg.charAt(0), 'a', 'z')) return false;
		if (!IsMinMax(lg.charAt(1), 'a', 'z')) return false;
		return true;
	}



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



}
