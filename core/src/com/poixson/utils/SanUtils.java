package com.poixson.utils;

import static com.poixson.utils.MathUtils.IsMinMax;

import com.poixson.tools.Keeper;


public final class SanUtils {
	private SanUtils() {}
	static { Keeper.add(new SanUtils()); }



	// char
	public static boolean IsAlpha(final char chr) {
		return IsMinMax(chr, 'A', 'Z') || IsMinMax(chr, 'a', 'z');
	}
	public static boolean IsAlphaNum(final char chr) {
		return IsAlpha(chr) || IsMinMax(chr, '0', '9');
	}
	public static boolean IsAlphaNumSafe(final char chr) {
		return IsAlphaNum(chr) ||
			chr == '-' || chr == '_' || chr == '=' || chr == '+' ||
			chr == '.' || chr == '(' || chr == ')';
	}



	// string
	public static boolean IsAlpha(final String text) {
		if (text == null) throw new NullPointerException();
		final int len = text.length();
		if (len == 1) return IsAlpha(text.charAt(0));
		for (int i=0; i<len; i++) {
			if (!IsAlpha(text.charAt(i)))
				return false;
		}
		return true;
	}
	public static boolean IsAlphaNum(final String text) {
		if (text == null) throw new NullPointerException();
		final int len = text.length();
		if (len == 1) return IsAlphaNum(text.charAt(0));
		for (int i=0; i<len; i++) {
			if (!IsAlphaNum(text.charAt(i)))
				return false;
		}
		return true;
	}
	public static boolean IsAlphaNumSafe(final String text) {
		if (text == null) throw new NullPointerException();
		final int len = text.length();
		if (len == 1) return IsAlphaNumSafe(text.charAt(0));
		for (int i=0; i<len; i++) {
			if (!IsAlphaNumSafe(text.charAt(i)))
				return false;
		}
		return true;
	}



	// strip string
	public static String Alpha(final String text) {
		if (text == null) throw new NullPointerException();
		final int len = text.length();
		final StringBuilder result = new StringBuilder(len);
		for (int i=0; i<len; i++) {
			if (IsAlpha(text.charAt(i)))
				result.append(text.charAt(i));
		}
		return result.toString();
	}
	public static String AlphaNum(final String text) {
		if (text == null) throw new NullPointerException();
		final int len = text.length();
		final StringBuilder result = new StringBuilder(len);
		for (int i=0; i<len; i++) {
			if (IsAlphaNum(text.charAt(i)))
				result.append(text.charAt(i));
		}
		return result.toString();
	}
	public static String AlphaNumSafe(final String text) {
		if (text == null) throw new NullPointerException();
		final int len = text.length();
		final StringBuilder result = new StringBuilder(len);
		for (int i=0; i<len; i++) {
			if (IsAlphaNumSafe(text.charAt(i)))
				result.append(text.charAt(i));
		}
		return result.toString();
	}



}
