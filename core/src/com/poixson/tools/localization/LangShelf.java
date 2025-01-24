package com.poixson.tools.localization;

import static com.poixson.utils.ArrayUtils.ssArrayToMap;
import static com.poixson.utils.FileUtils.MergePaths;
import static com.poixson.utils.FileUtils.OpenLocalOrResource;
import static com.poixson.utils.FileUtils.SearchLocalOrResource;
import static com.poixson.utils.StringUtils.ssReplaceTags;
import static com.poixson.utils.Utils.IsEmpty;
import static com.poixson.utils.Utils.SafeClose;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.tools.abstractions.Tuple;
import com.poixson.tools.indexselect.IndexSelect;


public class LangShelf {

	public static final String DEFAULT_GLOBAL_LANG = "en";

	protected final AtomicReference<String> path_loc = new AtomicReference<String>("./languages");
	protected final AtomicReference<String> path_res = new AtomicReference<String>("/languages");

	protected final ConcurrentHashMap<LangToken, LangBook> books =
			new ConcurrentHashMap<LangToken, LangBook>();

	protected final LangShelf parent;

	protected final AtomicReference<LangToken> lang_default = new AtomicReference<LangToken>(null);



	public LangShelf(final LangShelf parent) {
		this.parent = parent;
	}



	public void reloadAll() {
		this.books.clear();
	}
	public void reload(final String lang) {
		this.reload(new LangToken(lang));
	}
	public void reload(final LangToken lang) {
		this.books.remove(lang);
	}



	public boolean isLangExisting(final String lang) {
		return this.isLangExisting(new LangToken(lang));
	}
	public boolean isLangExisting(final LangToken lang) {
		final String filename = (new StringBuilder()).append(lang).append(".json").toString();
		final String file_loc = MergePaths(this.path_loc.get(), filename);
		final String file_res = MergePaths(this.path_res.get(), filename);
		try {
			SearchLocalOrResource(file_loc, file_res);
			return true;
		} catch (FileNotFoundException ignore) {}
		return false;
	}



	// -------------------------------------------------------------------------------
	// parameters



	// local path
	public LangShelf pathLocal(final String path) {
		this.path_loc.set(path);
		return this;
	}
	public String getLocalPath() {
		return this.path_loc.get();
	}



	// resource path
	public LangShelf pathResource(final String path) {
		this.path_res.set(path);
		return this;
	}
	public String getResourcePath() {
		return this.path_res.get();
	}



	// default language
	public LangShelf setDefLang(final String lang) {
		return this.setDefLang(new LangToken(lang));
	}
	public LangShelf setDefLang(final LangToken lang) {
		this.lang_default.set(lang);
		return this;
	}
	public LangToken getDefLang() {
		final LangToken lang = this.lang_default.get();
		return (lang==null ? new LangToken(DEFAULT_GLOBAL_LANG) : lang);
	}



	// -------------------------------------------------------------------------------
	// language phrases



	// phrase
	public String getPhrase(final String lang, final String key, final String...args) {
		return this.getPhrase(new LangToken(lang), key, args);
	}
	public String getPhrase(final LangToken lang, final String key, final String...args) {
		return GetPhrase(this.getPhrases(lang, key), args);
	}

	// default phrase
	public String getDefaultPhrase(final String key, final String...args) {
		return GetPhrase(this.getDefaultPhrases(key), args);
	}



	// phrases
	public Tuple<String[], IndexSelect> getPhrases(final String lang, final String key) {
		return this.getPhrases(new LangToken(lang), key);
	}
	public Tuple<String[], IndexSelect> getPhrases(final LangToken lang, final String key) {
		// lang from book
		{
			final LangBook book = this.getBook(lang);
			if (book != null) {
				final Tuple<String[], IndexSelect> tup = book.getPhrases(key);
				if (tup != null)
					return tup;
			}
		}
		// lang from parent
		if (this.parent != null) {
			final Tuple<String[], IndexSelect> tup = this.parent.getPhrases(lang, key);
			if (tup != null)
				return tup;
		}
		// default language
		return this.getDefaultPhrasesIfNot(key, lang);
	}

	// default phrases
	public Tuple<String[], IndexSelect> getDefaultPhrases(final String key) {
		final LangToken lang_def = this.getDefLang();
		return (lang_def==null ? null : this.getPhrases(lang_def, key));
	}
	public Tuple<String[], IndexSelect> getDefaultPhrasesIfNot(final String key, final LangToken lang) {
		final LangToken lang_def = this.getDefLang();
		return (
			lang_def==null || lang_def.equals(lang)
			? null
			: this.getPhrases(lang_def, key)
		);
	}



	public LangBook getBook(final String lang) {
		return this.getBook(new LangToken(lang));
	}
	public LangBook getBook(final LangToken lang) {
		// existing book
		{
			final LangBook book = this.books.get(lang);
			if (book != null)
				return book;
		}
		// load book
		{
			final String filename = (new StringBuilder()).append(lang).append(".json").toString();
			final String file_loc = MergePaths(this.path_loc.get(), filename);
			final String file_res = MergePaths(this.path_res.get(), filename);
			InputStream in = null;
			try {
				in = OpenLocalOrResource(file_loc, file_res);
				final LangBook book = new LangBook(lang, in);
				this.books.put(lang, book);
				return book;
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				SafeClose(in);
			}
		}
		return null;
	}



	// -------------------------------------------------------------------------------
	// utils



	public static String GetPhrase(final Tuple<String[], IndexSelect> tup, String...args) {
		return (
			IsEmpty(args)
			? GetRawPhrase(tup)
			: ssReplaceTags(GetRawPhrase(tup), ssArrayToMap(args))
		);
	}
	public static String GetRawPhrase(final Tuple<String[], IndexSelect> tup) {
		if (tup == null) return null;
		final String[]   phrases = tup.key;
		final IndexSelect select = tup.val;
		final int num = phrases.length;
		if (num == 1) {
			return phrases[0];
		} else
		if (num > 1) {
			final int index = select.next(phrases.length);
			return phrases[index];
		}
		return null;
	}



}
