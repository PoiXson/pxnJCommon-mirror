package com.poixson.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.tools.Keeper;


public final class StringUtils {
	private StringUtils() {}
	static { Keeper.add(new StringUtils()); }



	public static final Charset CHARSET_UTF8  = StandardCharsets.UTF_8;
	public static final Charset CHARSET_ASCII = StandardCharsets.US_ASCII;
	public static final Charset DEFAULT_CHARSET = CHARSET_UTF8;



	// ------------------------------------------------------------------------------- //
	// convert string



	// object to string
	public static String ToString(final Object obj) {
		// null
		if (obj == null) return null;
		// array
		if (obj.getClass().isArray()) {
			final StringBuilder result = new StringBuilder();
			int count = 0;
			for (final Object o : (Object[]) obj) {
				if (o == null) continue;
				if (count > 0)
					result.append(' ');
				count++;
				result.append('{').append( ToString(o) ).append('}');
			}
			return result.toString();
		}
		// string
		if (obj instanceof String)
			return (String) obj;
		// boolean
		if (obj instanceof Boolean)
			return ( ((Boolean)obj).booleanValue() ? "TRUE" : "false" );
		// int
		if (obj instanceof Integer)
			return ((Integer) obj).toString();
		// long
		if (obj instanceof Long)
			return ((Long) obj).toString();
		// double
		if (obj instanceof Double)
			return ((Double) obj).toString();
		// float
		if (obj instanceof Float)
			return ((Float) obj).toString();
		// exception
		if (obj instanceof Exception)
			return ExceptionToString((Exception) obj);
		// unknown object
		return obj.toString();
	}
	// exception to string
	public static String ExceptionToString(final Throwable e) {
		if (e == null) return null;
		final StringWriter writer = new StringWriter(256);
		e.printStackTrace(new PrintWriter(writer));
		return writer.toString().trim();
	}



	// decode string
	public static String decode(final String raw) {
		return decode(raw, null, null);
	}
	public static String decodeDef(final String raw, final String defaultStr) {
		return decode(raw, defaultStr, null);
	}
	public static String decodeCh(final String raw, final String charset) {
		return decode(raw, null, charset);
	}
	public static String decode(final String raw, final String defaultStr, final String charset) {
		if (charset == null) {
			return decode(raw, defaultStr, DEFAULT_CHARSET.name());
		}
		try {
			return URLDecoder.decode(raw, charset);
		} catch (UnsupportedEncodingException ignore) {}
		return defaultStr;
	}



	public static String[] StringToArray(final String str) {
		if (Utils.isEmpty(str)) return null;
		return new String[] { str };
	}



	public static String[] SplitLines(final String lines[]) {
		if (lines == null) return null;
		if (lines.length == 0) return new String[0];
		final List<String> result = new ArrayList<String>(lines.length);
		for (final String line : lines) {
			if (!line.contains("\n")) {
				result.add(line);
				continue;
			}
			final String[] split = line.split("\n");
			if (Utils.notEmpty(split)) {
				for (final String str : split) {
					result.add(str);
				}
			}
		}
		return result.toArray(new String[0]);
	}



	// ------------------------------------------------------------------------------- //
	// check value



	public static boolean IsAlpha(final String str) {
		if (str == null) return false;
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (!Character.isLetter(str.charAt(i)))
				return false;
		}
		return true;
	}
	public static boolean IsAlphaSpace(final String str) {
		if (str == null) return false;
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			final char chr = str.charAt(i);
			if (!Character.isLetter(chr)) {
				if (!Character.isSpaceChar(chr))
					return false;
			}
		}
		return true;
	}
	public static boolean IsAlphaNum(final String str) {
		if (str == null) return false;
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			final char chr = str.charAt(i);
			if (!Character.isLetterOrDigit(chr))
				return false;
		}
		return true;
	}
	public static boolean IsAlphaNumSpace(final String str) {
		if (str == null) return false;
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			final char chr = str.charAt(i);
			if (!Character.isLetterOrDigit(chr)) {
				if (!Character.isSpaceChar(chr))
					return false;
			}
		}
		return true;
	}



	// string equals
	public static boolean StrEquals(final String a, final String b) {
		final boolean aEmpty = Utils.isEmpty(a);
		final boolean bEmpty = Utils.isEmpty(b);
		if (aEmpty && bEmpty) return true;
		if (aEmpty || bEmpty) return false;
		return a.equals(b);
	}
	public static boolean StrEqualsIgnoreCase(final String a, final String b) {
		final boolean aEmpty = Utils.isEmpty(a);
		final boolean bEmpty = Utils.isEmpty(b);
		if (aEmpty && bEmpty) return true;
		if (aEmpty || bEmpty) return false;
		return a.equalsIgnoreCase(b);
	}
	public static boolean StrEqualsExact(final String a, final String b) {
		if (a == null && b == null) return true;
		if (a == null || b == null) return false;
		return a.equals(b);
	}
	public static boolean StrEqualsExactIgnoreCase(final String a, final String b) {
		if (a == null && b == null) return true;
		if (a == null || b == null) return false;
		return a.equalsIgnoreCase(b);
	}



	// ------------------------------------------------------------------------------- //
	// trim



	public static String TrimToNull(final String str, final String...strip) {
		if (Utils.isEmpty(str)) return null;
		//                            front end   case
		final String result = doTrim( true, true, false, str, strip );
		if (Utils.isEmpty(result)) return null;
		return result;
	}
	public static String TrimToNull(final String str, final char...strip) {
		if (Utils.isEmpty(str)) return null;
		//                            front end   case
		final String result = doTrim( true, true, false, str, strip );
		if (Utils.isEmpty(result)) return null;
		return result;
	}



	// trim front/end
	public static String Trim(final String str, final char...strip) {
		//             front end   case
		return doTrim( true, true, false, str, strip );
	}
	public static String iTrim(final String str, final char...strip) {
		//             front end   case
		return doTrim( true, true, true, str, strip );
	}
	public static String Trim(final String str, final String...strip) {
		//             front end   case
		return doTrim( true, true, false, str, strip );
	}
	public static String iTrim(final String str, final String...strip) {
		//             front end   case
		return doTrim( true, true, true, str, strip );
	}



	// trim front
	public static String TrimFront(final String str, final char...strip) {
		//             front end    case
		return doTrim( true, false, false, str, strip );
	}
	public static String iTrimFront(final String str, final char...strip) {
		//             front end    case
		return doTrim( true, false, true, str, strip );
	}
	public static String TrimFront(final String str, final String...strip) {
		//             front end    case
		return doTrim( true, false, false, str, strip );
	}
	public static String iTrimFront(final String str, final String...strip) {
		//             front end    case
		return doTrim( true, false, true, str, strip );
	}



	// trim end
	public static String TrimEnd(final String str, final char...strip) {
		//             front end    case
		return doTrim( false, true, false, str, strip );
	}
	public static String iTrimEnd(final String str, final char...strip) {
		//             front end    case
		return doTrim( false, true, true, str, strip );
	}
	public static String TrimEnd(final String str, final String...strip) {
		//             front end    case
		return doTrim( false, true, false, str, strip );
	}
	public static String iTrimEnd(final String str, final String...strip) {
		//             front end    case
		return doTrim( false, true, true, str, strip );
	}



	private static String doTrim(
			final boolean trimFront, final boolean trimEnd,
			final boolean ignoreCase,
			final String str, final char...strip) {
		if (!trimFront && !trimEnd) return str;
		if (Utils.isEmpty(str))     return str;
		if (Utils.isEmpty(strip))   return str;
		final int stripCount = strip.length;
		final char[] stripChars;
		final String strPrep;
		if (ignoreCase) {
			stripChars = new char[stripCount];
			for (int i=0; i<stripCount; i++) {
				stripChars[i] = Character.toLowerCase(strip[i]);
			}
			strPrep = str.toLowerCase();
		} else {
			stripChars = strip;
			strPrep = str;
		}
		final int size = str.length();
		int leftIndex  = 0;
		int rightIndex = 0;
		boolean changed = true;
		//CHANGE_LOOP:
		while (changed) {
			changed = false;
			//STRIP_LOOP:
			for (int index=0; index<stripCount; index++) {
				if (trimFront) {
					INNER_LOOP_FRONT:
					while (true) {
						if (leftIndex + rightIndex >= size)
							return "";
						if (strPrep.charAt(leftIndex) != stripChars[index])
							break INNER_LOOP_FRONT;
						leftIndex++;
						changed = true;
					}
				}
				if (trimEnd) {
					INNER_LOOP_END:
					while (true) {
						if (leftIndex + rightIndex >= size)
							return "";
						if (strPrep.charAt(size-(rightIndex+1)) != stripChars[index])
							break INNER_LOOP_END;
						rightIndex++;
						changed = true;
					}
				}
			} // end STRIP_LOOP
		} // end CHANGE_LOOP
		return str.substring( leftIndex, size - rightIndex );
	}
	private static String doTrim(
			final boolean trimFront, final boolean trimEnd,
			final boolean ignoreCase,
			final String str, final String...strip) {
		if (!trimFront && !trimEnd) return str;
		if (Utils.isEmpty(str))     return str;
		if (Utils.isEmpty(strip))   return str;
		final int stripCount = strip.length;
		final String[] stripStrings;
		final String strPrep;
		final int[] stripLen = new int[stripCount];
		if (ignoreCase) {
			stripStrings = new String[stripCount];
			for (int i=0; i<stripCount; i++) {
				stripStrings[i] = strip[i].toLowerCase();
				stripLen[i] = stripStrings[i].length();
			}
			strPrep = str.toLowerCase();
		} else {
			stripStrings = strip;
			for (int i=0; i<stripCount; i++) {
				stripLen[i] = stripStrings[i].length();
			}
			strPrep = str;
		}
		final int size = str.length();
		int leftIndex  = 0;
		int rightIndex = 0;
		boolean changed = true;
		//CHANGE_LOOP:
		while (changed) {
			changed = false;
			//STRIP_LOOP:
			for (int index=0; index<stripCount; index++) {
				if (trimFront) {
					INNER_LOOP_FRONT:
					while (true) {
						if (leftIndex + rightIndex >= size) return "";
						if (!strPrep.substring(leftIndex, leftIndex + stripLen[index]).equals(stripStrings[index]))
							break INNER_LOOP_FRONT;
						leftIndex += stripLen[index];
						changed = true;
					}
				}
				if (trimEnd) {
					INNER_LOOP_END:
					while (true) {
						if (leftIndex + rightIndex >= size) return "";
						final int pos = size - (rightIndex + stripLen[index]);
						if (pos < 0) break INNER_LOOP_END;
						if (leftIndex + rightIndex + stripLen[index] > size) break INNER_LOOP_END;
						if (!strPrep.substring( pos, pos + stripLen[index]).equals(stripStrings[index]))
							break INNER_LOOP_END;
						rightIndex += stripLen[index];
						changed = true;
					}
				}
			} // end STRIP_LOOP
		} // end CHANGE_LOOP
		return str.substring( leftIndex, size - rightIndex );
	}



	// ------------------------------------------------------------------------------- //
	// modify string



	public static String RemoveFromString(final String str, final String...strip) {
		if (Utils.isEmpty(str))   return str;
		if (Utils.isEmpty(strip)) return str;
		String result = str;
		boolean changed = true;
		while (changed) {
			changed = false;
			for (final String s : strip) {
				if (result.contains(s)) {
					result = result.replace(s, "");
					changed = true;
				}
			}
		}
		return result;
	}



	// ensure starts with
	public static String ForceStarts(final String start, final String data) {
		if (data == null)           return null;
		if (data.startsWith(start)) return data;
		return (new StringBuilder( start.length() + data.length() ))
				.append(start).append(data).toString();
	}
	// ensure ends with
	public static String ForceEnds(final String end, final String data) {
		if (data == null)       return null;
		if (data.endsWith(end)) return data;
		return (new StringBuilder( data.length() + end.length() ))
				.append(data).append(end).toString();
	}



	// increment and append string_# until unique
	public static String UniqueKey(final Collection<String> collect, final String key) {
		if (collect == null)    throw new RequiredArgumentException("collect");
		if (Utils.isEmpty(key)) throw new RequiredArgumentException("key");
		if (collect.isEmpty())
			return key;
		if ( ! collect.contains(key) )
			return key;
		int index = 0;
		while (true) {
			index++;
			final String str =
				(new StringBuilder())
					.append( key   )
					.append( "_"   )
					.append( index )
					.toString();
			if (!collect.contains(str)) {
				return str;
			}
		}
	}
	// collection
	public static String AddUnique(final Collection<String> collect, final String key) {
		if (collect == null)    throw new RequiredArgumentException("collect");
		if (Utils.isEmpty(key)) throw new RequiredArgumentException("key");
		if (collect.isEmpty()) {
			collect.add(key);
			return key;
		}
		if ( ! collect.contains(key) ) {
			collect.add(key);
			return key;
		}
		int index = 0;
		while (true) {
			index++;
			final String str =
				(new StringBuilder())
					.append( key   )
					.append( "_"   )
					.append( index )
					.toString();
			if (!collect.contains(str)) {
				collect.add(str);
				return str;
			}
		}
	}
	// concurrent hash map
	public static <T> String PutUnique(final ConcurrentHashMap<String, T> map, final String key, final T value) {
		if (map == null)        throw new RequiredArgumentException("map");
		if (Utils.isEmpty(key)) throw new RequiredArgumentException("key");
		if (value == null)      throw new RequiredArgumentException("value");
		if (map.putIfAbsent(key, value) == null)
			return key;
		int index = 0;
		while (true) {
			index++;
			final String str =
				(new StringBuilder())
					.append( key   )
					.append( "_"   )
					.append( index )
					.toString();
			if (map.putIfAbsent(str, value) == null)
				return str;
		}
	}
	// map
	public static <T> String PutUnique(final Map<String, T> map, final String key, final T value) {
		if (map == null)        throw new RequiredArgumentException("map");
		if (Utils.isEmpty(key)) throw new RequiredArgumentException("key");
		if (value == null)      throw new RequiredArgumentException("value");
		if (map.isEmpty()) {
			map.put(key, value);
			return key;
		}
		if ( ! map.containsKey(key) ) {
			map.put(key, value);
			return key;
		}
		int index = 0;
		while (true) {
			index++;
			final String str =
				(new StringBuilder())
					.append( key   )
					.append( "_"   )
					.append( index )
					.toString();
			if (!map.containsKey(str)) {
				map.put(str, value);
				return str;
			}
		}
	}



	// ------------------------------------------------------------------------------- //
	// build string



	// add strings with delimiter
	public static String MergeStrings(final String delim, final String...addThis) {
		if (Utils.isEmpty(addThis)) throw new RequiredArgumentException("addThis");
		final String dlm = Utils.ifEmpty(delim, null);
		final StringBuilder buf = new StringBuilder();
		boolean b = false;
		for (final String line : addThis) {
			if (Utils.isEmpty(line)) continue;
			if (b && dlm != null) {
				buf.append(dlm);
			}
			buf.append(line);
			if (!b && buf.length() > 0) {
				b = true;
			}
		}
		return buf.toString();
	}
	public static String MergeStrings(final char delim, final String...addThis) {
		if (Utils.isEmpty(addThis)) throw new RequiredArgumentException("addThis");
		final StringBuilder buf = new StringBuilder();
		boolean first = true;
		for (final String line : addThis) {
			if (Utils.isEmpty(line)) continue;
			if (!first) {
				buf.append(delim);
			}
			buf.append(line);
			if (first && buf.length() > 0) {
				first = false;
			}
		}
		return buf.toString();
	}



	// add objects to string with delimiter
	public static String MergeObjects(final String delim, final Object...addThis) {
		if (Utils.isEmpty(addThis)) throw new RequiredArgumentException("addThis");
		String[] addStrings = new String[ addThis.length ];
		int index = 0;
		for (final Object obj : addThis) {
			addStrings[index] = ToString(obj);
			index++;
		}
		return MergeStrings(delim, addStrings);
	}
	public static String MergeObjects(final char delim, final Object...addThis) {
		if (Utils.isEmpty(addThis)) throw new RequiredArgumentException("addThis");
		String[] addStrings = new String[ addThis.length ];
		int index = 0;
		for (final Object obj : addThis) {
			addStrings[index] = ToString(obj);
			index++;
		}
		return MergeStrings(delim, addStrings);
	}



	// generate regex from string with wildcard *
	public static String WildcardToRegex(final String wildcard) {
		if (Utils.isEmpty(wildcard)) return wildcard;
		final StringBuilder buf = new StringBuilder(wildcard.length());
		buf.append('^');
		final int len = wildcard.length();
		for (int i = 0; i < len; i++) {
			char c = wildcard.charAt(i);
			switch (c) {
			case '*':
				buf.append(".*");
				break;
			case '?':
				buf.append(".");
				break;
			case '(':
			case ')':
			case '[':
			case ']':
			case '$':
			case '^':
			case '.':
			case '{':
			case '}':
			case '|':
			case '\\':
				buf.append('\\').append(c);
				break;
			default:
				buf.append(c);
				break;
			}
		}
		buf.append('$');
		return buf.toString();
	}



	// ------------------------------------------------------------------------------- //
	// find position



	// index of (many delims)
	public static int IndexOf(final String string, final int fromIndex, final char...delims) {
		if (Utils.isEmpty(string)) return -1;
		int pos = Integer.MAX_VALUE;
		for (final char delim : delims) {
			final int p = string.indexOf(delim, fromIndex);
			// delim not found
			if (p == -1) continue;
			// earlier delim
			if (p < pos) {
				pos = p;
				if (p == 0) return 0;
			}
		}
		return (pos == Integer.MAX_VALUE ? -1 : pos);
	}
	public static int IndexOf(final String string, final char...delims) {
		return IndexOf(string, 0, delims);
	}



	public static int IndexOf(final String string, final int fromIndex, final String...delims) {
		if (Utils.isEmpty(string)) return -1;
		int pos = Integer.MAX_VALUE;
		for (final String delim : delims) {
			if (Utils.isEmpty(delim)) continue;
			final int p = string.indexOf(delim, fromIndex);
			// delim not found
			if (p == -1) continue;
			// earlier delim
			if (p < pos) {
				pos = p;
				if (p == 0) return 0;
			}
		}
		return (pos == Integer.MAX_VALUE ? -1 : pos);
	}
	public static int IndexOf(final String string, final String...delims) {
		return IndexOf(string, 0, delims);
	}



	// last index of (many delims)
	public static int IndexOfLast(final String string, final char...delims) {
		if (Utils.isEmpty(string)) return -1;
		int pos = Integer.MIN_VALUE;
		for (final char delim : delims) {
			final int p = string.lastIndexOf(delim);
			// delim not found
			if (p == -1) continue;
			// later delim
			if (p > pos) {
				pos = p;
			}
		}
		return (pos == Integer.MIN_VALUE ? -1 : pos);
	}
	public static int IndexOfLast(final String string, final String...delims) {
		if (Utils.isEmpty(string)) return -1;
		int pos = Integer.MIN_VALUE;
		for (final String delim : delims) {
			if (Utils.isEmpty(delim)) continue;
			final int p = string.lastIndexOf(delim);
			// delim not found
			if (p == -1) continue;
			// later delim
			if (p > pos) {
				pos = p;
			}
		}
		return (pos == Integer.MIN_VALUE ? -1 : pos);
	}


	// find longest line
	public static int FindLongestLine(final String[] lines) {
		if (Utils.isEmpty(lines)) return -1;
		int len = 0;
		for (final String line : lines) {
			if (line == null) continue;
			if (line.length() > len) {
				len = line.length();
			}
		}
		return len;
	}



	// ------------------------------------------------------------------------------- //
	// replace within string



	// replace range
	public static String ReplaceStringRange(
			final String str, final String chunk,
			final int startPos, final int endPos) {
		if (str == null) throw new RequiredArgumentException("str");
		if (str.length() == 0) return chunk;
		final StringBuilder result = new StringBuilder();
		if (startPos > 0) {
			result.append( str.substring(0, startPos) );
		}
		if (Utils.notEmpty(chunk)) {
			result.append(chunk);
		}
		if (endPos < str.length()) {
			result.append( str.substring(endPos) );
		}
		return result.toString();
	}



	// replace with array
	public static String ReplaceWith(final String data,
			final String replaceWhat, final String[] withWhat) {
		if (Utils.isEmpty(data))        return data;
		if (Utils.isEmpty(replaceWhat)) return data;
		if (Utils.isEmpty(withWhat))    return data;
		final StringBuilder result = new StringBuilder();
		final int count = withWhat.length;
		int currentPos = 0;
		for (int i = 0; i < count; i++) {
			final int thisPos = data.indexOf(replaceWhat, currentPos);
			if (thisPos > 0) {
				result.append(data.substring(currentPos, thisPos));
				result.append(withWhat[i]);
				currentPos = thisPos + replaceWhat.length();
			}
		}
		if (data.length() > currentPos) {
			result.append( data.substring(currentPos) );
		}
		return result.toString();
	}



	public static String ReplaceEnd(final String data,
			final char replaceWhat, final char replaceWith) {
		if (Utils.isEmpty(data))        return data;
		if (Utils.isEmpty(replaceWhat)) return data;
		if (Utils.isEmpty(replaceWith)) return data;
		final int size = data.length();
		int index = 0;
		while (true) {
			final char chr = data.charAt((size - index) - 1);
			if (chr != replaceWhat)
				return data.substring(0, size - index) + Repeat(index, replaceWith);
			index++;
			if (size == index)
				return Repeat(size, replaceWith);
		}
	}



	// ------------------------------------------------------------------------------- //
	// generate string



	/**
	 * Generate a random string hash.
	 * @param length Number of characters to generate
	 * @return The generated hash string
	 */
	public static String RandomString(final int length) {
		if (length < 1) return null;
		final StringBuilder buf = new StringBuilder(length);
		while (buf.length() < length) {
			final String str = UUID.randomUUID().toString();
			if (str == null) throw new RequiredArgumentException("str");
			buf.append(str);
		}
		return buf.toString().substring( 0, NumberUtils.MinMax(length, 0, buf.length()) );
	}



	// repeat string with deliminator
	public static String Repeat(final int count, final String str) {
		return Repeat(count, str, null);
	}
	public static String Repeat(final int count, final String str, final String delim) {
		if (Utils.isEmpty(str)) throw new RequiredArgumentException("str");
		if (count < 1) return "";
		final StringBuilder result = new StringBuilder();
		// repeat string
		if (Utils.isEmpty(delim)) {
			for (int i = 0; i < count; i++) {
				result.append(str);
			}
		} else {
			// repeat string with delim
			boolean b = false;
			for (int i = 0; i < count; i++) {
				if (b) result.append(delim);
				b = true;
				result.append(str);
			}
		}
		return result.toString();
	}
	public static String Repeat(final int count, final char chr) {
		if (count < 1) return "";
		final StringBuilder result = new StringBuilder();
		// repeat string
		for (int i = 0; i < count; i++) {
			result.append(chr);
		}
		return result.toString();
	}



	// ------------------------------------------------------------------------------- //
	// pad string



	public static String PadFront(final int width, final String text, final char padding) {
		if (width < 1) return null;
		final int count = width - text.length();
		if (count < 1) return text;
		return
			(new StringBuilder(width))
				.append( Repeat(count, padding) )
				.append( text                   )
				.toString();
	}
	public static String PadEnd(final int width, final String text, final char padding) {
		if (width < 1) return null;
		final int count = width - text.length();
		if (count < 1) return text;
		return
			(new StringBuilder(width))
				.append( text                   )
				.append( Repeat(count, padding) )
				.toString();
	}
	public static String PadCenter(final int width, final String text, final char padding) {
		if (width < 1) return null;
		if (Utils.isEmpty(text)) {
			return Repeat(width, padding);
		}
		final double count = ( ((double) width) - ((double) text.length()) ) / 2.0;
		if (Math.ceil(count) < 1.0) return text;
		return
			(new StringBuilder(width))
				.append( Repeat( (int)Math.floor(count), padding) )
				.append( text                                     )
				.append( Repeat( (int)Math.ceil(count), padding)  )
				.toString();
	}



	public static String PadFront(final int width, final int value) {
		return PadFront(  width, Integer.toString(value), '0' );
	}
	public static String PadEnd(final int width, final int value) {
		return PadEnd(    width, Integer.toString(value), '0' );
	}

	public static String PadFront(final int width, final long value) {
		return PadFront(  width, Long.toString(value), '0' );
	}
	public static String PadEnd(final int width, final long value) {
		return PadEnd(    width, Long.toString(value), '0' );
	}



	// ------------------------------------------------------------------------------- //
	// replace {} tags



	// replace {} or {#} tags
	public static String ReplaceTags(final String msg, final Object...args) {
		if (Utils.isEmpty(msg))  return msg;
		if (Utils.isEmpty(args)) return msg;
		final StringBuilder result = new StringBuilder(msg);
		ARG_LOOP:
		for (int index=0; index<args.length; index++) {
			final Object obj = args[index];
			final String str = ( obj == null ? "<null>" : ToString(obj) );
			// {#}
			{
				final String tag = (new StringBuilder()).append('{').append(index + 1).append('}').toString();
				boolean found = false;
				REPLACE_LOOP:
				while (true) {
					final int pos = result.indexOf(tag);
					if (pos == -1) break REPLACE_LOOP;
					result.replace(pos, pos + tag.length(), str);
					found = true;
				} // end REPLACE_LOOP
				if (found) continue ARG_LOOP;
			}
			// {}
			{
				final int pos = result.indexOf("{}");
				if (pos >= 0) {
					result.replace(pos, pos + 2, str);
					continue ARG_LOOP;
				}
			}
			// append
			result
				.append(' ')
				.append(str);
		} // end ARG_LOOP
		return result.toString();
	}



	// replace {} or {#} tags (in multiple lines)
	public static String[] ReplaceTags(final String[] msgs, final Object...args) {
		if (Utils.isEmpty(msgs)) return msgs;
		if (Utils.isEmpty(args)) return msgs;
		String[] result = Arrays.copyOf(msgs, msgs.length);
		final StringBuilder extras = new StringBuilder();
		ARG_LOOP:
		for (int argIndex=0; argIndex<args.length; argIndex++) {
			final String str = ( args[argIndex] == null ? "<null>" : ToString(args[argIndex]) );
			// {#} - all instances
			{
				final String tag = (new StringBuilder()).append('{').append(argIndex + 1).append('}').toString();
				boolean found = false;
				LINE_LOOP:
				for (int lineIndex=0; lineIndex<msgs.length; lineIndex++) {
					if (Utils.isEmpty( result[lineIndex] ))
						continue LINE_LOOP;
					//REPLACE_LOOP:
					while (true) {
						final int pos = result[lineIndex].indexOf(tag);
						if (pos == -1) continue LINE_LOOP;
						result[lineIndex] = ReplaceStringRange( result[lineIndex], str, pos, pos + tag.length() );
						found = true;
					} // end REPLACE_LOOP
				} // end LINE_LOOP
				if (found) continue ARG_LOOP;
			}
			// {} - first found
			{
				LINE_LOOP:
				for (int lineIndex=0; lineIndex<msgs.length; lineIndex++) {
					if (Utils.isEmpty( result[lineIndex] ))
						continue LINE_LOOP;
					final int pos = result[lineIndex].indexOf("{}");
					if (pos == -1) continue LINE_LOOP;
					result[lineIndex] = ReplaceStringRange( result[lineIndex], str, pos, pos + 2 );
					continue ARG_LOOP;
				} // end LINE_LOOP
			}
			// append to end
			{
				if ( extras.length() != 0 )
					extras.append(' ');
				extras.append(str);
			}
		} // end ARG_LOOP
		if ( extras.length() != 0 ) {
			if (result.length == 1) {
				result[0] =
					(new StringBuilder())
						.append( result[0] )
						.append( ' '       )
						.append( extras    )
						.toString();
				return result;
			}
			String[] newResult = new String[ result.length + 1 ];
			newResult[result.length] = extras.toString();
			return newResult;
		}
		return result;
	}



	// replace {key} tags
	public static String ReplaceTags(final String msg, final Map<String, Object> args) {
		if (Utils.isEmpty(msg))  return msg;
		if (Utils.isEmpty(args)) return msg;
		final StringBuilder result = new StringBuilder(msg);
		ARG_LOOP:
		for (final String key : args.keySet()) {
			final Object obj = args.get(key);
			final String str = ( obj == null ? "<null>" : ToString(obj) );
			// {key}
			{
				final String tag = (new StringBuilder()).append('{').append(key).append('}').toString();
				boolean found = false;
				REPLACE_LOOP:
				while (true) {
					final int pos = result.indexOf(tag);
					if (pos == -1) break REPLACE_LOOP;
					result.replace(pos, pos + tag.length(), str);
					found = true;
				} // end REPLACE_LOOP
				if (found) continue ARG_LOOP;
			}
			// {}
			{
				final int pos = result.indexOf("{}");
				if (pos != -1) {
					result.replace(pos, pos + 2, str);
				}
			}
			// don't append
		} // end ARG_LOOP
		return result.toString();
	}



	// replace {key} tags (in multiple lines)
	public static String[] ReplaceTags(final String[] msgs, final Map<String, Object> args) {
		if (Utils.isEmpty(msgs)) return msgs;
		if (Utils.isEmpty(args)) return msgs;
		String[] result = Arrays.copyOf(msgs, msgs.length);
		ARG_LOOP:
		for (final String key : args.keySet()) {
			final Object obj = args.get(key);
			final String str = ( obj == null ? "<null>" : ToString(obj) );
			// {key}
			{
				final String tag = (new StringBuilder()).append('{').append(key).append('}').toString();
				boolean found = false;
				LINE_LOOP:
				for (int lineIndex=0; lineIndex<msgs.length; lineIndex++) {
					if (Utils.isEmpty( result[lineIndex] ))
						continue LINE_LOOP;
					//REPLACE_LOOP:
					while (true) {
						final int pos = result[lineIndex].indexOf(tag);
						if (pos == -1) continue LINE_LOOP;
						result[lineIndex] = ReplaceStringRange( result[lineIndex], str, pos, pos + tag.length() );
						found = true;
					} // end REPLACE_LOOP
				} // end LINE_LOOP
				if (found)
					continue ARG_LOOP;
			}
			// {}
			{
				LINE_LOOP:
				for (int lineIndex=0; lineIndex<msgs.length; lineIndex++) {
					if (Utils.isEmpty( result[lineIndex] ))
						continue LINE_LOOP;
					final int pos = result[lineIndex].indexOf("{}");
					if (pos != -1) {
						result[lineIndex] = ReplaceStringRange( result[lineIndex], str, pos, pos + 2 );
						continue ARG_LOOP;
					}
				} // end LINE_LOOP
			}
		}
		return result;
	}



}
