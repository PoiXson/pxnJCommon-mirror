package com.poixson.tools.localization;

import static com.poixson.tools.localization.LangShelf.GetPhrase;
import static com.poixson.utils.FileUtils.ReadInputStream;
import static com.poixson.utils.SanUtils.AlphaNumSafe;
import static com.poixson.utils.Utils.IsEmpty;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.poixson.exceptions.LangBookLoadException;
import com.poixson.tools.abstractions.Tuple;
import com.poixson.tools.gson.GsonAdapter_LangBook;
import com.poixson.tools.indexselect.IndexSelect;
import com.poixson.tools.indexselect.IndexSelectCycle;


public class LangBook extends ConcurrentHashMap<String, String> {
	private static final long serialVersionUID = 1L;

	protected final ConcurrentHashMap<String, Tuple<String[], IndexSelect>> phrases =
			new ConcurrentHashMap<String, Tuple<String[], IndexSelect>>();

	final LangToken lang;



	public LangBook(final LangToken lang, final InputStream in) throws IOException {
		this(lang);
		this.load(in);
	}
	public LangBook(final LangToken lang) {
		super();
		this.lang = lang;
	}



	public void load(final InputStream in) throws IOException {
		if (in == null) throw new LangBookLoadException(new NullPointerException("Failed to find or load language: "+this.lang.toString()));
		final String json = ReadInputStream(in);
		if (IsEmpty(json)) throw new LangBookLoadException("Failed to parse json for language: "+this.lang.toString());
		@SuppressWarnings("unchecked")
		final Map<String, String[]> map = (Map<String, String[]>) GSON().fromJson(json, Map.class);
		this.load(map);
	}
	public void load(final Map<String, String[]> map) {
		if (IsEmpty(map)) throw new LangBookLoadException("Failed to load json for language: "+this.lang.toString());
		final LinkedList<String> found = new LinkedList<String>();
		for (final Entry<String, String[]> entry : map.entrySet()) {
			final String key = AlphaNumSafe(entry.getKey());
			final String[] phrases = entry.getValue();
			found.addLast(key);
			final IndexSelect select = new IndexSelectCycle(phrases.length);
			final Tuple<String[], IndexSelect> tup = new Tuple<String[], IndexSelect>(phrases, select);
			this.phrases.put(key, tup);
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
	// language phrases



	public String getPhrase(final String key, final String...args) {
		return GetPhrase(this.getPhrases(key), args);
	}



	public Tuple<String[], IndexSelect> getPhrases(final String key) {
		return this.phrases.get(key);
	}



	// -------------------------------------------------------------------------------
	// gson



	public static Gson GSON() {
		return com.poixson.utils.GsonProvider.GSON(
			Map.class, new GsonAdapter_LangBook()
		);
	}



	public static Type GetMapToken() {
		return (new TypeToken<Map<String, String[]>>() {}).getType();
	}



}
