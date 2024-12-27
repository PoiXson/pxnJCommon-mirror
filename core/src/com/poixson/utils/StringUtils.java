package com.poixson.utils;

import static com.poixson.utils.MathUtils.MinMax;
import static com.poixson.utils.Utils.IsEmpty;
import static com.poixson.utils.Utils.SafeClose;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

	public static final String DEFAULT_TAG_FORMAT = "{%s}";



	// -------------------------------------------------------------------------------
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
		if (obj instanceof String   ) return (String) obj;
		if (obj instanceof Boolean  ) return ( ((Boolean)obj).booleanValue() ? "TRUE" : "false" );
		if (obj instanceof Integer  ) return ((Integer) obj).toString();
		if (obj instanceof Long     ) return ((Long) obj).toString();
		if (obj instanceof Double   ) return ((Double) obj).toString();
		if (obj instanceof Float    ) return ((Float) obj).toString();
		if (obj instanceof Exception) return ExceptionToString((Exception) obj);
		// unknown object
		return obj.toString();
	}
	// exception to string
	public static String ExceptionToString(final Throwable e) {
		if (e == null) return null;
		final StringWriter writer = new StringWriter(256);
		e.printStackTrace(new PrintWriter(writer));
		try {
			return writer.toString().trim();
		} finally {
			SafeClose(writer);
		}
	}



	// decode string
	public static String Decode(final String raw) {
		return Decode(raw, null, null);
	}
	public static String DecodeDef(final String raw, final String defaultStr) {
		return Decode(raw, defaultStr, null);
	}
	public static String DecodeCh(final String raw, final String charset) {
		return Decode(raw, null, charset);
	}
	public static String Decode(final String raw, final String defaultStr, final String charset) {
		if (charset == null)
			return Decode(raw, defaultStr, DEFAULT_CHARSET.name());
		try {
			return URLDecoder.decode(raw, charset);
		} catch (UnsupportedEncodingException ignore) {}
		return defaultStr;
	}



	public static boolean ArrayContains(final String match, final String[] array) {
		if (match == null || match.isEmpty())   return false;
		if (array == null || array.length == 0) return false;
		for (final String entry : array) {
			if (MatchString(match, entry))
				return true;
		}
		return false;
	}



	public static String[] RemoveTruncatedDuplicates(final String[] array) {
		final ArrayList<String> result = new ArrayList<String>();
		for (final String entry : array) {
			if (entry.isEmpty()) continue;
			boolean dup = false;
			for (final String match : array) {
				if (match.isEmpty()) continue;
				// match with self
				if (entry.equals(match)) continue;
				if (match.startsWith(entry)) {
					dup = true;
					break;
				}
			}
			if (!dup)
				result.add(entry);
		}
		return result.toArray(new String[0]);
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
			if (!IsEmpty(split)) {
				for (final String str : split)
					result.add(str);
			}
		}
		return result.toArray(new String[0]);
	}



	public static String[] Split(final String line, final char...delims) {
		final int len = line.length();
		final List<String> result = new ArrayList<String>();
		int next;
		int last = 0;
		LOOP_SPLIT:
		while (true) {
			// find next delim
			next = len;
			LOOP_DELIM:
			for (final char chr : delims) {
				final int pos = line.indexOf(chr, last);
				if (pos == -1) continue LOOP_DELIM;
				if (next > pos)
					next = pos;
				if (pos == last)   break LOOP_DELIM;
				if (pos == last+1) break LOOP_DELIM;
			} // end LOOP_DELIM
			// delim not found
			if (next == len) {
				if (last < len)
					result.add(line.substring(last));
				break LOOP_SPLIT;
			}
			// delim found
			if (last != next)
				result.add(line.substring(last, next));
			last = next + 1;
		} // end LOOP_SPLIT
		return result.toArray(new String[0]);
	}
	public static String[] Split(final String line, final String...delims) {
		final int lenLine = line.length();
		final List<String> result = new ArrayList<String>();
		int next;
		int lenNext;
		int last = 0;
		LOOP_SPLIT:
		while (true) {
			// find next delim
			next = lenLine;
			lenNext = 0;
			LOOP_DELIM:
			for (final String str : delims) {
				final int pos = line.indexOf(str, last);
				if (pos > -1) {
					if (next == pos) {
						if (str.length() > lenNext)
							lenNext = str.length();
					} else
					if (next > pos) {
						next = pos;
						lenNext = str.length();
					}
					if (pos == last)   break LOOP_DELIM;
					if (pos == last+1) break LOOP_DELIM;
				}
			} // end LOOP_DELIM
			// delim not found
			if (next == lenLine) {
				if (last < lenLine)
					result.add(line.substring(last));
				break LOOP_SPLIT;
			}
			// delim found
			if (last != next)
				result.add(line.substring(last, next));
			last = next + lenNext;
		} // end LOOP_SPLIT
		return result.toArray(new String[0]);
	}

	public static Map<String, String> SplitKeyVal(final String input, final String...delims) {
		final int len_input = input.length();
		final Map<String, String> result = new HashMap<>();
		int pos_next;
		int len_next;
		int pos_last = 0;
		String last_key = null;
		LOOP_SPLIT:
		while (true) {
			// find next delimiter
			pos_next = len_input;
			len_next = 0;
			String current_delim = null;
			//LOOP_DELIM:
			for (final String delim : delims) {
				final int pos = input.indexOf(delim, pos_last);
				if (pos > -1) {
					if (pos_next > pos
					|| (pos_next ==pos && delim.length() > len_next)) {
						pos_next = pos;
						len_next = delim.length();
						current_delim = delim;
					}
				}
			} // end LOOP_DELIM
			// handle remaining string
			if (current_delim == null) {
				result.put(last_key, input.substring(pos_last));
				break LOOP_SPLIT;
			}
			if (last_key == null) {
				if (pos_last != pos_next)
					result.put("", input.substring(pos_last, pos_next));
			} else {
				result.put(last_key, input.substring(pos_last, pos_next));
			}
			last_key = current_delim;
			pos_last = pos_next + len_next;
		} // end LOOP_SPLIT
		return result;
	}



	// -------------------------------------------------------------------------------
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



	public static boolean IsWhiteSpace(final String str) {
		final int len = str.length();
		for (int i=0; i<len; i++) {
			if (!IsWhiteSpace(str.charAt(i)))
				return false;
		}
		return true;
	}
	public static boolean IsWhiteSpace(final char chr) {
		switch (chr) {
		case ' ':
		case '\t':
		case '\r':
		case '\n':
			return true;
		default: return false;
		}
	}



	// -------------------------------------------------------------------------------
	// string equals



	public static boolean MatchString(final String expect, final String actual) {
		final boolean expectEmpty = (expect == null || expect.isEmpty());
		final boolean actualEmpty = (actual == null || actual.isEmpty());
		if (expectEmpty || actualEmpty)
			return (expectEmpty == actualEmpty);
		return expect.equals(actual);
	}
	public static boolean MatchStringIgnoreCase(final String expect, final String actual) {
		final boolean expectEmpty = (expect == null || expect.isEmpty());
		final boolean actualEmpty = (actual == null || actual.isEmpty());
		if (expectEmpty || actualEmpty)
			return (expectEmpty == actualEmpty);
		return expect.equalsIgnoreCase(actual);
	}
	public static boolean MatchStringExact(final String expect, final String actual) {
		if (expect == null && actual == null) return true;
		if (expect == null || actual == null) return false;
		return expect.equals(actual);
	}



	// -------------------------------------------------------------------------------
	// trim



	// c | char
	// s | string
	// i | ignore case
	// f | trim front
	// e | trim end



	public static String TrimToNull(final String str) {
		if (IsEmpty(str)) return null;
		//                            front end   case
		final String result = doTrim( true, true, false, str, new char[0]);
		if (IsEmpty(result)) return null;
		return result;
	}
	public static String TrimToNull(final String str, final String...strip) {
		if (IsEmpty(str)) return null;
		//                            front end   case
		final String result = doTrim( true, true, false, str, strip );
		if (IsEmpty(result)) return null;
		return result;
	}
	public static String TrimToNull(final String str, final char...strip) {
		if (IsEmpty(str)) return null;
		//                            front end   case
		final String result = doTrim( true, true, false, str, strip );
		if (IsEmpty(result)) return null;
		return result;
	}



	// trim front/end
	public static String cTrim(final String str, final char...strip) {
		//             front end   case
		return doTrim( true, true, false, str, strip );
	}
	public static String ciTrim(final String str, final char...strip) {
		//             front end   case
		return doTrim( true, true, true, str, strip );
	}
	public static String sTrim(final String str, final String...strip) {
		//             front end   case
		return doTrim( true, true, false, str, strip );
	}
	public static String siTrim(final String str, final String...strip) {
		//             front end   case
		return doTrim( true, true, true, str, strip );
	}



	// trim front
	public static String cfTrim(final String str, final char...strip) {
		//             front end    case
		return doTrim( true, false, false, str, strip );
	}
	public static String cifTrim(final String str, final char...strip) {
		//             front end    case
		return doTrim( true, false, true, str, strip );
	}
	public static String sfTrim(final String str, final String...strip) {
		//             front end    case
		return doTrim( true, false, false, str, strip );
	}
	public static String sifTrim(final String str, final String...strip) {
		//             front end    case
		return doTrim( true, false, true, str, strip );
	}



	// trim end
	public static String ceTrim(final String str, final char...strip) {
		//             front end    case
		return doTrim( false, true, false, str, strip );
	}
	public static String cieTrim(final String str, final char...strip) {
		//             front end    case
		return doTrim( false, true, true, str, strip );
	}
	public static String seTrim(final String str, final String...strip) {
		//             front end    case
		return doTrim( false, true, false, str, strip );
	}
	public static String sieTrim(final String str, final String...strip) {
		//             front end    case
		return doTrim( false, true, true, str, strip );
	}



	private static String doTrim(
			final boolean trimFront, final boolean trimEnd,
			final boolean ignoreCase,
			final String str, final char...strip) {
		if (!trimFront && !trimEnd) return str;
		if (IsEmpty(str))           return str;
		if (IsEmpty(strip))         return str;
		final int stripCount = strip.length;
		final char[] stripChars;
		final String strPrep;
		if (ignoreCase) {
			stripChars = new char[stripCount];
			for (int i=0; i<stripCount; i++)
				stripChars[i] = Character.toLowerCase(strip[i]);
			strPrep = str.toLowerCase();
		} else {
			stripChars = strip;
			strPrep = str;
		}
		final int size = str.length();
		int leftIndex  = 0;
		int rightIndex = 0;
		boolean changed = true;
		//LOOP_CHANGE:
		while (changed) {
			changed = false;
			//LOOP_STRIP:
			for (int index=0; index<stripCount; index++) {
				if (trimFront) {
					LOOP_INNER_FRONT:
					while (true) {
						if (leftIndex + rightIndex >= size)
							return "";
						if (strPrep.charAt(leftIndex) != stripChars[index])
							break LOOP_INNER_FRONT;
						leftIndex++;
						changed = true;
					}
				}
				if (trimEnd) {
					LOOP_INNER_END:
					while (true) {
						if (leftIndex + rightIndex >= size)
							return "";
						if (strPrep.charAt(size-(rightIndex+1)) != stripChars[index])
							break LOOP_INNER_END;
						rightIndex++;
						changed = true;
					}
				}
			} // end LOOP_STRIP
		} // end LOOP_CHANGE
		return str.substring( leftIndex, size - rightIndex );
	}
	private static String doTrim(
			final boolean trimFront, final boolean trimEnd,
			final boolean ignoreCase,
			final String str, final String...strip) {
		if (!trimFront && !trimEnd) return str;
		if (IsEmpty(str))           return str;
		if (IsEmpty(strip))         return str;
		final int stripCount = strip.length;
		// default strip
		if (stripCount == 0) {
			final String[] strip_def = Arrays.asList(
				" ",
				"\t",
				"\r",
				"\n"
			).toArray(new String[0]);
			return doTrim(trimFront, trimEnd, ignoreCase, str, strip_def);
		}
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
		//LOOP_CHANGE:
		while (changed) {
			changed = false;
			//LOOP_STRIP:
			for (int index=0; index<stripCount; index++) {
				if (trimFront) {
					LOOP_INNER_FRONT:
					while (true) {
						if (leftIndex + rightIndex >= size) return "";
						if (!strPrep.substring(leftIndex, leftIndex + stripLen[index]).equals(stripStrings[index]))
							break LOOP_INNER_FRONT;
						leftIndex += stripLen[index];
						changed = true;
					}
				}
				if (trimEnd) {
					LOOP_INNER_END:
					while (true) {
						if (leftIndex + rightIndex >= size) return "";
						final int pos = size - (rightIndex + stripLen[index]);
						if (pos < 0) break LOOP_INNER_END;
						if (leftIndex + rightIndex + stripLen[index] > size) break LOOP_INNER_END;
						if (!strPrep.substring( pos, pos + stripLen[index]).equals(stripStrings[index]))
							break LOOP_INNER_END;
						rightIndex += stripLen[index];
						changed = true;
					}
				}
			} // end LOOP_STRIP
		} // end LOOP_CHANGE
		return str.substring( leftIndex, size - rightIndex );
	}



	// -------------------------------------------------------------------------------
	// modify string



	public static String RemoveFromString(final String str, final String...strip) {
		if (IsEmpty(str))   return str;
		if (IsEmpty(strip)) return str;
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



	public static String ForceStarts(final char start, final String data) {
		if (data == null)            return null;
		if (data.isEmpty())          return Character.toString(start);
		if (data.charAt(0) == start) return data;
		return (new StringBuilder( data.length() + 1 ))
				.append(start).append(data).toString();
	}
	public static String ForceEnds(final char end, final String data) {
		if (data == null)   return null;
		if (data.isEmpty()) return Character.toString(end);
		final int len = data.length();
		if (data.charAt(len-1) == end) return data;
		return (new StringBuilder( data.length() + 1 ))
				.append(data).append(end).toString();
	}
	public static String ForceStartsEnds(final char start, final char end, final String data) {
		return ForceStarts(start, ForceEnds(end, data));
	}

	public static String ForceStarts(final String start, final String data) {
		if (data == null)           return null;
		if (data.startsWith(start)) return data;
		return (new StringBuilder( start.length() + data.length() ))
				.append(start).append(data).toString();
	}
	public static String ForceEnds(final String end, final String data) {
		if (data == null)       return null;
		if (data.endsWith(end)) return data;
		return (new StringBuilder( data.length() + end.length() ))
				.append(data).append(end).toString();
	}
	public static String ForceStartsEnds(final String start, final String end, final String data) {
		return ForceStarts(start, ForceEnds(end, data));
	}



	// increment and append string_# until unique
	public static String UniqueKey(final Collection<String> collect, final String key) {
		if (collect == null) throw new RequiredArgumentException("collect");
		if (IsEmpty(key))    throw new RequiredArgumentException("key");
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
		if (collect == null) throw new RequiredArgumentException("collect");
		if (IsEmpty(key))    throw new RequiredArgumentException("key");
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
		if (map == null)   throw new RequiredArgumentException("map");
		if (IsEmpty(key))  throw new RequiredArgumentException("key");
		if (value == null) throw new RequiredArgumentException("value");
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
		if (map == null)   throw new RequiredArgumentException("map");
		if (IsEmpty(key))  throw new RequiredArgumentException("key");
		if (value == null) throw new RequiredArgumentException("value");
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



	// -------------------------------------------------------------------------------
	// build string



	// add strings with delimiter
	public static String MergeStrings(final String delim, final String...addThis) {
		if (IsEmpty(addThis)) return "";
		final String dlm = (IsEmpty(delim) ? null : delim);
		final StringBuilder buf = new StringBuilder();
		// no delim
		if (dlm == null) {
			for (final String part : addThis)
				buf.append(part);
			return buf.toString();
		}
		// merge with delim
		boolean first = true;
		for (final String part : addThis) {
			if (IsEmpty(part)) continue;
			if (first) first = false;
			else       buf.append(dlm);
			buf.append(part);
		}
		return buf.toString();
	}
	public static String MergeStrings(final char delim, final String...addThis) {
		if (IsEmpty(addThis)) throw new RequiredArgumentException("addThis");
		final StringBuilder buf = new StringBuilder();
		boolean first = true;
		for (final String line : addThis) {
			if (IsEmpty(line)) continue;
			if (!first)
				buf.append(delim);
			buf.append(line);
			if (first && buf.length() > 0)
				first = false;
		}
		return buf.toString();
	}

	public static String MergeStrings(final String delim, final char...addThis) {
		if (IsEmpty(addThis)) return "";
		final String dlm = (IsEmpty(delim) ? null : delim);
		final StringBuilder buf = new StringBuilder();
		// no delim
		if (dlm == null) {
			for (final char part : addThis)
				buf.append(part);
			return buf.toString();
		}
		// merge with delim
		boolean first = true;
		for (final char part : addThis) {
			if (IsEmpty(part)) continue;
			if (first) first = false;
			else       buf.append(dlm);
			buf.append(part);
		}
		return buf.toString();
	}
	public static String MergeStrings(final char delim, final char...addThis) {
		if (IsEmpty(addThis)) throw new RequiredArgumentException("addThis");
		final StringBuilder buf = new StringBuilder();
		boolean first = true;
		for (final char line : addThis) {
			if (IsEmpty(line)) continue;
			if (!first)
				buf.append(delim);
			buf.append(line);
			if (first && buf.length() > 0)
				first = false;
		}
		return buf.toString();
	}



	// add objects to string with delimiter
	public static String MergeObjects(final String delim, final Object...addThis) {
		if (IsEmpty(addThis)) throw new RequiredArgumentException("addThis");
		String[] addStrings = new String[ addThis.length ];
		int index = 0;
		for (final Object obj : addThis) {
			addStrings[index] = ToString(obj);
			index++;
		}
		return MergeStrings(delim, addStrings);
	}
	public static String MergeObjects(final char delim, final Object...addThis) {
		if (IsEmpty(addThis)) throw new RequiredArgumentException("addThis");
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
		if (IsEmpty(wildcard)) return wildcard;
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
				buf.append('.');
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



	// -------------------------------------------------------------------------------
	// find position



	// index of (many delims)
	public static int IndexOf(final String data, final int fromIndex, final char...delims) {
		if (IsEmpty(data)) return -1;
		int pos = Integer.MAX_VALUE;
		for (final char delim : delims) {
			final int p = data.indexOf(delim, fromIndex);
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
	public static int IndexOf(final String data, final char...delims) {
		return IndexOf(data, 0, delims);
	}



	public static int IndexOf(final String data, final int fromIndex, final String...delims) {
		if (IsEmpty(data)) return -1;
		int pos = Integer.MAX_VALUE;
		for (final String delim : delims) {
			if (IsEmpty(delim)) continue;
			final int p = data.indexOf(delim, fromIndex);
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
	public static int IndexOf(final String data, final String...delims) {
		return IndexOf(data, 0, delims);
	}



	// last index of (many delims)
	public static int IndexOfLast(final String data, final char...delims) {
		if (IsEmpty(data)) return -1;
		int pos = Integer.MIN_VALUE;
		for (final char delim : delims) {
			final int p = data.lastIndexOf(delim);
			// delim not found
			if (p == -1) continue;
			// later delim
			if (p > pos)
				pos = p;
		}
		return (pos == Integer.MIN_VALUE ? -1 : pos);
	}
	public static int IndexOfLast(final String data, final String...delims) {
		if (IsEmpty(data)) return -1;
		int pos = Integer.MIN_VALUE;
		for (final String delim : delims) {
			if (IsEmpty(delim)) continue;
			final int p = data.lastIndexOf(delim);
			// delim not found
			if (p == -1) continue;
			// later delim
			if (p > pos)
				pos = p;
		}
		return (pos == Integer.MIN_VALUE ? -1 : pos);
	}



	// find longest line
	public static int FindLongestLine(final String[] lines) {
		if (IsEmpty(lines)) return -1;
		int len = 0;
		for (final String line : lines) {
			if (line == null) continue;
			if (line.length() > len)
				len = line.length();
		}
		return len;
	}



	public static String FirstPart(final String data, final char...delims) {
		if (IsEmpty(data)) return "";
		if (delims.length == 0) throw new RequiredArgumentException("delims");
		final int pos = IndexOf(data, delims);
		if (pos == -1)
			return data;
		return data.substring(0, pos);
	}
	public static String FirstPart(final String data, final String...delims) {
		if (IsEmpty(data)) return "";
		if (delims.length == 0) throw new RequiredArgumentException("delims");
		final int pos = IndexOf(data, delims);
		if (pos == -1)
			return data;
		return data.substring(0, pos);
	}



	public static String LastPart(final String data, final char...delims) {
		if (IsEmpty(data)) return "";
		if (delims.length == 0) throw new RequiredArgumentException("delims");
		final int pos = IndexOfLast(data, delims);
		if (pos == -1)
			return data;
		return data.substring(pos + 1);
	}
	public static String LastPart(final String data, final String...delims) {
		if (IsEmpty(data)) return "";
		if (delims.length == 0) throw new RequiredArgumentException("delims");
		final int pos = IndexOfLast(data, delims);
		if (pos == -1)
			return data;
		int delimLen = 0;
		for (final String delim : delims) {
			if (IsEmpty(delim)) continue;
			final int len = delim.length();
			if (len <= delimLen) continue;
			if (data.substring(pos, len).equals(delim))
				delimLen = len;
		}
		if (delimLen == 0) throw new RuntimeException("delim not found second pass");
		return data.substring(pos + delimLen);
	}



	// -------------------------------------------------------------------------------
	// replace within string



	public static String ReplaceInString(final String str, final String chunk, final int pos) {
		if (str == null) throw new RequiredArgumentException("str");
		if (str.length() == 0) return chunk;
		final StringBuilder result = new StringBuilder();
		result.append(str);
		ReplaceInString(result, chunk, pos);
		return result.toString();
	}
	public static void ReplaceInString(final StringBuilder str, final String chunk, final int pos) {
		if (str == null) throw new RequiredArgumentException("str");
		if (str.length() == 0) return;
		final int len_str   = str.length();
		final int len_chunk = chunk.length();
		for (int i=0; i<len_chunk; i++) {
			if (pos+i >= len_str) {
				str.append(chunk.substring(i));
				return;
			}
			str.setCharAt(pos+i, chunk.charAt(i));
		}
	}



	// replace with array
	public static String ReplaceWith(final String str,
			final String replaceWhat, final String[] withWhat) {
		if (IsEmpty(str))         return str;
		if (IsEmpty(replaceWhat)) return str;
		if (IsEmpty(withWhat))    return str;
		final StringBuilder result = new StringBuilder();
		final int count = withWhat.length;
		int currentPos = 0;
		for (int i = 0; i < count; i++) {
			final int thisPos = str.indexOf(replaceWhat, currentPos);
			if (thisPos > 0) {
				result.append(str.substring(currentPos, thisPos));
				result.append(withWhat[i]);
				currentPos = thisPos + replaceWhat.length();
			}
		}
		if (str.length() > currentPos)
			result.append( str.substring(currentPos) );
		return result.toString();
	}
	public static void ReplaceWith(final StringBuilder str,
			final char replaceWhat, final char withWhat) {
		final int len = str.length();
		for (int i=0; i<len; i++) {
			if (str.charAt(i) == replaceWhat)
				str.setCharAt(i, withWhat);
		}
	}



	public static String ReplaceEnd(final String data,
			final char replaceWhat, final char replaceWith) {
		if (IsEmpty(data)) return data;
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



	// -------------------------------------------------------------------------------
	// replace tags



	// replace {} or {#} tags
	public static String oReplaceTags(final String line, final Object...args) {
		return o_ReplaceTags(DEFAULT_TAG_FORMAT, line, args);
	}
	public static String sReplaceTags(final String line, final String...args) {
		return s_ReplaceTags(DEFAULT_TAG_FORMAT, line, args);
	}

	public static String o_ReplaceTags(final String format,
			final String line, final Object...args) {
		final String[] array = new String[args.length];
		final int len = args.length;
		for (int index=0; index<len; index++)
			array[index] = ToString(args[index]);
		return s_ReplaceTags(format, line, array);
	}
	public static String s_ReplaceTags(final String format,
			final String line, final String...args) {
		if (IsEmpty(args)) return line;
		if (IsEmpty(line)) return MergeStrings(' ', args);
		final StringBuilder result = new StringBuilder(line);
		final int num_args = args.length;
		int used = 0;
		// {}
		{
			LOOP_ARGS:
			for (final String arg : args) {
				final int pos = result.indexOf(String.format(format, ""));
				if (pos == -1) break LOOP_ARGS;
				final String val = (arg==null ? "<null>" : arg);
				result.replace(pos, pos + 2, val);
				used++;
			} // end LOOP_ARGS
		}
		// {n}
		boolean found = true;
		//LOOP_CHANGES:
		while (found) {
			found = false;
			//LOOP_ARGS:
			for (int index=0; index<num_args; index++) {
				final String val = (args[index]==null ? "<null>" : args[index]);
				final String tag = String.format(String.format(format, "%d"), Integer.valueOf(index+1));
				LOOP_REPLACE:
				while (true) {
					final int pos = result.indexOf(tag);
					if (pos == -1) break LOOP_REPLACE;
					result.replace(pos, pos+tag.length(), val);
					found = true;
					if (used < index + 1)
						used = index + 1;
				} // end LOOP_REPLACE:
			} // end LOOP_ARGS
		} // end LOOP_CHANGES
		if (num_args > used) {
			for (int index=used; index<num_args; index++) {
				final String val = (args[index]==null ? "<null>" : args[index]);
				result.append(' ').append(val);
			}
		}
		return result.toString();
	}



	// replace {key} tags
	public static String soReplaceTags(final String line, final Map<String, Object> args) {
		return so_ReplaceTags(DEFAULT_TAG_FORMAT, line, args);
	}
	public static String ssReplaceTags(final String line, final Map<String, String> args) {
		return ss_ReplaceTags(DEFAULT_TAG_FORMAT, line, args);
	}

	public static String so_ReplaceTags(final String format,
			final String line, final Map<String, Object> args) {
		final Map<String, String> map = new HashMap<String, String>();
		for (final Entry<String, Object> entry : args.entrySet())
			map.put(entry.getKey(), ToString(entry.getValue()));
		return ss_ReplaceTags(format, line, map);
	}
	public static String ss_ReplaceTags(final String format,
			final String line, final Map<String, String> args) {
		if (IsEmpty(line)) return line;
		if (IsEmpty(args)) return line;
		final StringBuilder result = new StringBuilder(line);
		// {key}
		{
			boolean found = true;
			//LOOP_CHANGES:
			while (found) {
				found = false;
				//LOOP_ARGS:
				for (final Entry<String, String> entry : args.entrySet()) {
					final String arg = entry.getValue();
					final String val = (arg==null ? "<null>" : arg);
					final String tag = String.format(format, entry.getKey());
					LOOP_REPLACE:
					while (true) {
						final int pos = result.indexOf(tag);
						if (pos == -1) break LOOP_REPLACE;
						result.replace(pos, pos + tag.length(), val);
						found = true;
					} // end LOOP_REPLACE
				} // end LOOP_ARGS
			} // end LOOP_CHANGES
		}
		return result.toString();
	}



	// -------------------------------------------------------------------------------
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
		return buf.toString().substring( 0, MinMax(length, 0, buf.length()) );
	}



	// repeat string with deliminator
	public static String Repeat(final int count, final String str) {
		return Repeat(count, str, null);
	}
	public static String Repeat(final int count, final String str, final String delim) {
		if (IsEmpty(str)) throw new RequiredArgumentException("str");
		if (count < 1) return "";
		final StringBuilder result = new StringBuilder();
		// repeat string
		if (IsEmpty(delim)) {
			for (int i = 0; i < count; i++)
				result.append(str);
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
		for (int i = 0; i < count; i++)
			result.append(chr);
		return result.toString();
	}



	// -------------------------------------------------------------------------------
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
		if (IsEmpty(text))
			return Repeat(width, padding);
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



	// -------------------------------------------------------------------------------
	// version strings



	//   = | a = b
	//   + | a < b
	//   - | a > b
	// +.5 | a = b-snap
	// -.5 | a-snap = b
	public static double CompareVersions(final String versionA, final String versionB) {
		if (versionA.endsWith("-SNAPSHOT")) return CompareVersions(versionA.substring(0, versionA.length()-9), versionB) - 0.5;
		if (versionB.endsWith("-SNAPSHOT")) return CompareVersions(versionA, versionB.substring(0, versionB.length()-9)) + 0.5;
		{
			int pos;
			pos = versionA.indexOf('-'); if (pos > 0) return CompareVersions(versionA.substring(0, pos), versionB);
			pos = versionB.indexOf('-'); if (pos > 0) return CompareVersions(versionA, versionB.substring(0, pos));
		}
		{
			final String[] partsA = versionA.split("[.]");
			final String[] partsB = versionB.split("[.]");
			final int num_parts = Math.max(partsA.length, partsB.length);
			int sizeA = partsA.length;
			int sizeB = partsB.length;
			int valA, valB, pow;
			int diff = 0;
			for (int i=0; i<num_parts; i++) {
				pow = (int) Math.pow(10.0, (num_parts-i-1) * 3.0);
				valA = (i < sizeA ? Integer.parseInt(partsA[i]) : 0);
				valB = (i < sizeB ? Integer.parseInt(partsB[i]) : 0);
				if (valA > valB
				||  valA < valB) { diff += (valB - valA) * pow; continue; }
			}
			return (double) diff;
		}
	}



}
