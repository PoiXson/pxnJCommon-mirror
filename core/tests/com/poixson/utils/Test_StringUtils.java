package com.poixson.utils;

import static com.poixson.tools.Assertions.AssertArrayEquals;
import static com.poixson.tools.Assertions.AssertEquals;
import static com.poixson.tools.Assertions.AssertTrue;
import static com.poixson.utils.ArrayUtils.MatchMaps;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.poixson.tools.Assertions;


@ExtendWith(Assertions.class)
public class Test_StringUtils {



	@Test
	public void testSafeString() {
		AssertEquals("",    StringUtils.SafeString(null ));
		AssertEquals("",    StringUtils.SafeString(""   ));
		AssertEquals("Abc", StringUtils.SafeString("Abc"));
		AssertEquals("123", StringUtils.SafeString("123"));
		AssertEquals("\n",  StringUtils.SafeString("\n" ));
	}

	@Test
	public void testNullNorm() {
		AssertEquals(null,  StringUtils.NullNorm(null ));
		AssertEquals(null,  StringUtils.NullNorm(""   ));
		AssertEquals("Abc", StringUtils.NullNorm("Abc"));
		AssertEquals("123", StringUtils.NullNorm("123"));
		AssertEquals("\n",  StringUtils.NullNorm("\n" ));
	}



	@Test
	public void testToUpper() {
		AssertEquals(null,  StringUtils.ToUpper(null ));
		AssertEquals("",    StringUtils.ToUpper(""   ));
		AssertEquals("ABC", StringUtils.ToUpper("Abc"));
		AssertEquals("123", StringUtils.ToUpper("123"));
		AssertEquals("\n",  StringUtils.ToUpper("\n" ));
	}
	@Test
	public void testToLower() {
		AssertEquals(null,  StringUtils.ToLower(null ));
		AssertEquals("",    StringUtils.ToLower(""   ));
		AssertEquals("abc", StringUtils.ToLower("Abc"));
		AssertEquals("123", StringUtils.ToLower("123"));
		AssertEquals("\n",  StringUtils.ToLower("\n" ));
	}



	@Test
	public void testToString() {
		AssertEquals( "Abcdef",  StringUtils.ToString("Abcdef"         ) );
		AssertEquals( "Abcdef",  StringUtils.ToString((Object) "Abcdef") );
		AssertEquals( "<TRUE>",  StringUtils.ToString(true             ) );
		AssertEquals( "<false>", StringUtils.ToString(false            ) );
		AssertEquals( "11",      StringUtils.ToString(11               ) );
		AssertEquals( "11",      StringUtils.ToString(11L              ) );
		AssertEquals( "1.23",    StringUtils.ToString(1.23             ) );
		AssertEquals( "1.23",    StringUtils.ToString(1.23F            ) );
		AssertEquals( null,      StringUtils.ToString(null             ) );
		AssertEquals( "",        StringUtils.ToString(""               ) );
		// array to string
		AssertEquals(
			"{A} {null} {b} {c}",
			StringUtils.ToString( new String[] {"A", null, "b", "c"} )
		);
		// object to string
		{
			final String result = StringUtils.ToString(new Object());
			AssertTrue(result.startsWith("java.lang.Object@"));
		}
		// exception to string
		{
			final String result = StringUtils.ToString(new Exception("Abcdef"));
			AssertTrue(result.startsWith("java.lang.Exception: Abcdef"));
			AssertEquals(null, StringUtils.ExceptionToString(null));
		}
	}



	// -------------------------------------------------------------------------------
	// string equals



	@Test
	public void testMatchString() {
		AssertEquals( true,  StringUtils.MatchString("Abc", "Abc") );
		AssertEquals( false, StringUtils.MatchString("Abc", "abc") );
		AssertEquals( false, StringUtils.MatchString("Abc", ""   ) );
		AssertEquals( false, StringUtils.MatchString("Abc", null ) );
		AssertEquals( true,  StringUtils.MatchString(null,  null ) );
		AssertEquals( true,  StringUtils.MatchString("",    ""   ) );
		AssertEquals( true,  StringUtils.MatchString("",    null ) );
		AssertEquals( true,  StringUtils.MatchString(null,  ""   ) );
	}
	@Test
	public void testMatchStringIgnoreCase() {
		AssertEquals( true,  StringUtils.MatchStringIgnoreCase("Abc", "Abc") );
		AssertEquals( true,  StringUtils.MatchStringIgnoreCase("Abc", "abc") );
		AssertEquals( false, StringUtils.MatchStringIgnoreCase("Abc", ""   ) );
		AssertEquals( false, StringUtils.MatchStringIgnoreCase("Abc", null ) );
		AssertEquals( true,  StringUtils.MatchStringIgnoreCase(null,  null ) );
		AssertEquals( true,  StringUtils.MatchStringIgnoreCase("",    ""   ) );
		AssertEquals( true,  StringUtils.MatchStringIgnoreCase("",    null ) );
		AssertEquals( true,  StringUtils.MatchStringIgnoreCase(null,  ""   ) );
	}
	@Test
	public void testMatchStringExact() {
		AssertEquals( true,  StringUtils.MatchStringExact("Abc", "Abc") );
		AssertEquals( false, StringUtils.MatchStringExact("Abc", "abc") );
		AssertEquals( false, StringUtils.MatchStringExact("Abc", ""   ) );
		AssertEquals( false, StringUtils.MatchStringExact("Abc", null ) );
		AssertEquals( true,  StringUtils.MatchStringExact(null,  null ) );
		AssertEquals( true,  StringUtils.MatchStringExact("",    ""   ) );
		AssertEquals( false, StringUtils.MatchStringExact("",    null ) );
		AssertEquals( false, StringUtils.MatchStringExact(null,  ""   ) );
	}



	@Test
	public void testWildcardToRegex() {
		AssertEquals( "^ab.*c$",  StringUtils.WildcardToRegex("ab*c") );
		AssertEquals( "^ab.c$",   StringUtils.WildcardToRegex("ab?c") );
		AssertEquals( "^ab\\$c$", StringUtils.WildcardToRegex("ab$c") );
		AssertEquals( "",         StringUtils.WildcardToRegex(""    ) );
		AssertEquals( null,       StringUtils.WildcardToRegex(null  ) );
	}



	// -------------------------------------------------------------------------------
	// check value



	@Test
	public void testIsAlpha() {
		AssertEquals( true,  StringUtils.IsAlpha("Abc"    ) );
		AssertEquals( false, StringUtils.IsAlpha("A b c"  ) );
		AssertEquals( false, StringUtils.IsAlpha("123"    ) );
		AssertEquals( false, StringUtils.IsAlpha("Abc123" ) );
		AssertEquals( false, StringUtils.IsAlpha("Abc123!") );
		AssertEquals( false, StringUtils.IsAlpha(null     ) );
	}
	@Test
	public void testIsAlphaSpace() {
		AssertEquals( true,  StringUtils.IsAlphaSpace("Abc"    ) );
		AssertEquals( true,  StringUtils.IsAlphaSpace("A b c"  ) );
		AssertEquals( false, StringUtils.IsAlphaSpace("123"    ) );
		AssertEquals( false, StringUtils.IsAlphaSpace("Abc123" ) );
		AssertEquals( false, StringUtils.IsAlphaSpace("Abc123!") );
		AssertEquals( false, StringUtils.IsAlphaSpace(null     ) );
	}
	@Test
	public void testIsAlphaNum() {
		AssertEquals( true,  StringUtils.IsAlphaNum("Abc"    ) );
		AssertEquals( false, StringUtils.IsAlphaNum("A b c"  ) );
		AssertEquals( true,  StringUtils.IsAlphaNum("123"    ) );
		AssertEquals( true,  StringUtils.IsAlphaNum("Abc123" ) );
		AssertEquals( false, StringUtils.IsAlphaNum("Abc123!") );
		AssertEquals( false, StringUtils.IsAlphaNum(null     ) );
	}
	@Test
	public void testIsAlphaNumSpace() {
		AssertEquals( true,  StringUtils.IsAlphaNumSpace("Abc"    ) );
		AssertEquals( true,  StringUtils.IsAlphaNumSpace("A b c"  ) );
		AssertEquals( true,  StringUtils.IsAlphaNumSpace("123"    ) );
		AssertEquals( true,  StringUtils.IsAlphaNumSpace("Abc123" ) );
		AssertEquals( false, StringUtils.IsAlphaNumSpace("Abc123!") );
		AssertEquals( false, StringUtils.IsAlphaNumSpace(null     ) );
	}



	// -------------------------------------------------------------------------------
	// compare versions



	@Test
	public void testCompareVersions() {
		AssertEquals(       0.0, StringUtils.CompareVersions("1.2.3", "1.2.3"), 0.0);
		AssertEquals(       1.0, StringUtils.CompareVersions("1.2.3", "1.2.4"), 0.0);
		AssertEquals(      -1.0, StringUtils.CompareVersions("1.2.3", "1.2.2"), 0.0);
		AssertEquals(    1000.0, StringUtils.CompareVersions("1.2.0", "1.3.0"), 0.0);
		AssertEquals(   -1000.0, StringUtils.CompareVersions("1.2.0", "1.1.0"), 0.0);
		AssertEquals( 2000000.0, StringUtils.CompareVersions("1.2.3", "3.2.3"), 0.0);
		AssertEquals(-1000000.0, StringUtils.CompareVersions("1.2.3", "0.2.3"), 0.0);
		AssertEquals(      -3.0, StringUtils.CompareVersions("1.2.3", "1.2"  ), 0.0);
		AssertEquals(       3.0, StringUtils.CompareVersions("1.2",   "1.2.3"), 0.0);
		AssertEquals(    -997.0, StringUtils.CompareVersions("1.3",   "1.2.3"), 0.0);
		AssertEquals(    2003.0, StringUtils.CompareVersions("1",     "1.2.3"), 0.0);
		AssertEquals(       0.5, StringUtils.CompareVersions("1.2.3", "1.2.3-SNAPSHOT"), 0.0);
		AssertEquals(      -0.5, StringUtils.CompareVersions("1.2.3-SNAPSHOT", "1.2.3"), 0.0);
		AssertEquals(      -0.5, StringUtils.CompareVersions("1.2.4", "1.2.3-SNAPSHOT"), 0.0);
		AssertEquals(       0.5, StringUtils.CompareVersions("1.2.3", "1.2.3-R0.1-SNAPSHOT"), 0.0);
	}



	// -------------------------------------------------------------------------------
	// trim



	@Test
	public void testTrimToNull() {
		// strip strings
		AssertEquals("Abc", StringUtils.TrimToNull("-=Abc=-", "=", "-"));
		AssertEquals(null,  StringUtils.TrimToNull("",        "=", "-"));
		AssertEquals(null,  StringUtils.TrimToNull(null,      "=", "-"));
		// strip chars
		AssertEquals("Abc", StringUtils.TrimToNull("-=Abc=-", '=', '-'));
		AssertEquals(null,  StringUtils.TrimToNull("",        '=', '-'));
		AssertEquals(null,  StringUtils.TrimToNull(null,      '=', '-'));
	}



	@Test
	public void testTrim() {
		// strip strings
		AssertEquals("Abc",    StringUtils.sTrim("-=Abc=-", "=", "-"));
		AssertEquals("abc",    StringUtils.sTrim("abcdef",  "de","f"));
		AssertEquals("abcdef", StringUtils.sTrim("abcdef",  "DE","F"));
		AssertEquals("abc",    StringUtils.sTrim("abc",    "abcdefg"));
		AssertEquals("",       StringUtils.sTrim("-=-=-",   "=", "-"));
		AssertEquals("",       StringUtils.sTrim("",        "=", "-"));
		AssertEquals(null,     StringUtils.sTrim(null,      "=", "-"));
		// strip chars
		AssertEquals("Abc",    StringUtils.cTrim("-=Abc=-", '=', '-'     ));
		AssertEquals("abc",    StringUtils.cTrim("abcdef",  'd', 'e', 'f'));
		AssertEquals("abcdef", StringUtils.cTrim("abcdef",  'D', 'E', 'F'));
		AssertEquals("",       StringUtils.cTrim("-=-=-",   '=', '-'     ));
		AssertEquals("",       StringUtils.cTrim("",        '=', '-'     ));
		AssertEquals(null,     StringUtils.cTrim(null,      '=', '-'     ));
	}
	@Test
	public void testITrim() {
		// strip strings
		AssertEquals("Abc", StringUtils.siTrim("-=Abc=-", "=", "-"));
		AssertEquals("abc", StringUtils.siTrim("abcdef",  "de","f"));
		AssertEquals("abc", StringUtils.siTrim("abcdef",  "DE","F"));
		AssertEquals("abc", StringUtils.siTrim("abc",    "abcdefg"));
		AssertEquals("",    StringUtils.siTrim("-=-=-",   "=", "-"));
		AssertEquals("",    StringUtils.siTrim("",        "=", "-"));
		AssertEquals(null,  StringUtils.siTrim(null,      "=", "-"));
		// strip chars
		AssertEquals("Abc", StringUtils.ciTrim("-=Abc=-", '=', '-'     ));
		AssertEquals("abc", StringUtils.ciTrim("abcDEF",  'd', 'e', 'f'));
		AssertEquals("abc", StringUtils.ciTrim("abcDEF",  'D', 'E', 'F'));
		AssertEquals("",    StringUtils.ciTrim("-=-=-",   '=', '-'     ));
		AssertEquals("",    StringUtils.ciTrim("",        '=', '-'     ));
		AssertEquals(null,  StringUtils.ciTrim(null,      '=', '-'     ));
	}



	@Test
	public void testTrimFront() {
		// strip strings
		AssertEquals("Abc=-",  StringUtils.sfTrim("-=Abc=-", "=", "-"));
		AssertEquals("def",    StringUtils.sfTrim("abcdef",  "ab","c"));
		AssertEquals("abcdef", StringUtils.sfTrim("abcdef",  "AB","C"));
		AssertEquals("abc",    StringUtils.sfTrim("abc",    "abcdefg"));
		AssertEquals("",       StringUtils.sfTrim("-=-=-",   "=", "-"));
		AssertEquals("",       StringUtils.sfTrim("",        "=", "-"));
		AssertEquals(null,     StringUtils.sfTrim(null,      "=", "-"));
		// strip chars
		AssertEquals("Abc=-",  StringUtils.cfTrim("-=Abc=-", '=', '-'     ));
		AssertEquals("def",    StringUtils.cfTrim("abcdef",  'a', 'b', 'c'));
		AssertEquals("abcdef", StringUtils.cfTrim("abcdef",  'A', 'B', 'C'));
		AssertEquals("",       StringUtils.cfTrim("-=-=-",   '=', '-'     ));
		AssertEquals("",       StringUtils.cfTrim("",        '=', '-'     ));
		AssertEquals(null,     StringUtils.cfTrim(null,      '=', '-'     ));
	}



	@Test
	public void testTrimEnd() {
		// strip strings
		AssertEquals("-=Abc",  StringUtils.seTrim("-=Abc=-", "=", "-"));
		AssertEquals("abc",    StringUtils.seTrim("abcdef",  "de","f"));
		AssertEquals("abcdef", StringUtils.seTrim("abcdef",  "DE","F"));
		AssertEquals("abc",    StringUtils.seTrim("abc",    "abcdefg"));
		AssertEquals("",       StringUtils.seTrim("-=-=-",   "=", "-"));
		AssertEquals("",       StringUtils.seTrim("",        "=", "-"));
		AssertEquals(null,     StringUtils.seTrim(null,      "=", "-"));
		// strip chars
		AssertEquals("-=Abc",  StringUtils.ceTrim("-=Abc=-", '=', '-'     ));
		AssertEquals("abc",    StringUtils.ceTrim("abcdef",  'd', 'e', 'f'));
		AssertEquals("abcdef", StringUtils.ceTrim("abcdef",  'D', 'E', 'F'));
		AssertEquals("",       StringUtils.ceTrim("-=-=-",   '=', '-'     ));
		AssertEquals("",       StringUtils.ceTrim("",        '=', '-'     ));
		AssertEquals(null,     StringUtils.ceTrim(null,      '=', '-'     ));
	}



	// -------------------------------------------------------------------------------
	// pad string



	@Test
	public void testPad() {
		// pad front
		AssertEquals( "--abc", StringUtils.PadFront(5, "abc", '-') );
		AssertEquals( "abc",   StringUtils.PadFront(3, "abc", '-') );
		AssertEquals( "abc",   StringUtils.PadFront(1, "abc", '-') );
		// pad end
		AssertEquals( "abc--", StringUtils.PadEnd(5, "abc", '-') );
		AssertEquals( "abc",   StringUtils.PadEnd(3, "abc", '-') );
		AssertEquals( "abc",   StringUtils.PadEnd(1, "abc", '-') );
		// pad center
		AssertEquals( "-abc-", StringUtils.PadCenter(5, "abc", '-') );
		AssertEquals( "abc",   StringUtils.PadCenter(3, "abc", '-') );
		AssertEquals( "abc",   StringUtils.PadCenter(1, "abc", '-') );
	}
	@Test
	public void testPadZero_Int() {
		// pad front
		AssertEquals( null,  StringUtils.PadFront(0, 1) );
		AssertEquals( "1",   StringUtils.PadFront(1, 1) );
		AssertEquals( "01",  StringUtils.PadFront(2, 1) );
		AssertEquals( "001", StringUtils.PadFront(3, 1) );
		// pad end
		AssertEquals( null,  StringUtils.PadEnd(0, 1) );
		AssertEquals( "1",   StringUtils.PadEnd(1, 1) );
		AssertEquals( "10",  StringUtils.PadEnd(2, 1) );
		AssertEquals( "100", StringUtils.PadEnd(3, 1) );
	}
	@Test
	public void testPadZero_Long() {
		// pad front
		AssertEquals( null,  StringUtils.PadFront(0, 1L) );
		AssertEquals( "1",   StringUtils.PadFront(1, 1L) );
		AssertEquals( "01",  StringUtils.PadFront(2, 1L) );
		AssertEquals( "001", StringUtils.PadFront(3, 1L) );
		// pad end
		AssertEquals( null,  StringUtils.PadEnd(0, 1L) );
		AssertEquals( "1",   StringUtils.PadEnd(1, 1L) );
		AssertEquals( "10",  StringUtils.PadEnd(2, 1L) );
		AssertEquals( "100", StringUtils.PadEnd(3, 1L) );
	}



	// -------------------------------------------------------------------------------
	// modify string



	@Test
	public void testRemoveFromString() {
		AssertEquals( "Abf",    StringUtils.RemoveFromString("Abcdef", "cd", "e") );
		AssertEquals( "Abcdef", StringUtils.RemoveFromString("Abcdef", "CD", "E") );
		AssertEquals( "",       StringUtils.RemoveFromString("",       "=",  "-") );
		AssertEquals( null,     StringUtils.RemoveFromString(null,     "=",  "-") );
	}



	@Test
	public void testForceStarts() {
		// string
		AssertEquals( "aAbc", StringUtils.ForceStarts("a", "Abc") );
		AssertEquals( "abc",  StringUtils.ForceStarts("a", "abc") );
		AssertEquals( "a",    StringUtils.ForceStarts("a", ""   ) );
		AssertEquals( null,   StringUtils.ForceStarts("a", null ) );
		// char
		AssertEquals( "aAbc", StringUtils.ForceStarts('a', "Abc") );
		AssertEquals( "abc",  StringUtils.ForceStarts('a', "abc") );
		AssertEquals( "a",    StringUtils.ForceStarts('a', ""   ) );
		AssertEquals( null,   StringUtils.ForceStarts('a', null ) );
	}
	@Test
	public void testForceEnds() {
		// string
		AssertEquals( "abcd", StringUtils.ForceEnds("d", "abc") );
		AssertEquals( "abc",  StringUtils.ForceEnds("c", "abc") );
		AssertEquals( "c",    StringUtils.ForceEnds("c", ""   ) );
		AssertEquals( null,   StringUtils.ForceEnds("c", null ) );
		// char
		AssertEquals( "abcd", StringUtils.ForceEnds('d', "abc") );
		AssertEquals( "abc",  StringUtils.ForceEnds('c', "abc") );
		AssertEquals( "c",    StringUtils.ForceEnds('c', ""   ) );
		AssertEquals( null,   StringUtils.ForceEnds('c', null ) );
	}



	// unique key
	@Test
	public void testUniqueKey() {
		{
			final List<String> list = new ArrayList<String>();
			list.add("a");
			AssertEquals( "a_1", StringUtils.UniqueKey(list, "a") );
			AssertEquals( "b",   StringUtils.UniqueKey(list, "b") );
			AssertArrayEquals(
				new String[] { "a" },
				list.toArray(new String[0])
			);
		}
	}
	// collection
	@Test
	public void testAddUnique_Collection() {
		{
			final List<String> list = new ArrayList<String>();
			list.add("a");
			AssertEquals( "a_1", StringUtils.AddUnique(list, "a") );
			AssertEquals( "b",   StringUtils.AddUnique(list, "b") );
			AssertArrayEquals(
				new String[] { "a", "a_1", "b" },
				list.toArray(new String[0])
			);
		}
	}
	// concurrent hash map
	@Test
	public void testPutUnique_ConcurrentHashMap() {
		{
			final ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<String, Object>();
			map.put("a", new Object());
			AssertEquals( "a_1", StringUtils.PutUnique(map, "a", new Object()) );
			AssertEquals( "b",   StringUtils.PutUnique(map, "b", new Object()) );
			AssertArrayEquals(
				new String[] { "a", "a_1", "b" },
				map.keySet().toArray(new String[0])
			);
		}
	}
	// map
	@Test
	public void testPutUnique_Map() {
		{
			final Map<String, Object> map = new HashMap<String, Object>();
			map.put("a", new Object());
			AssertEquals( "a_1", StringUtils.PutUnique(map, "a", new Object()) );
			AssertEquals( "b",   StringUtils.PutUnique(map, "b", new Object()) );
			AssertArrayEquals(
				new String[] { "a", "a_1", "b" },
				map.keySet().toArray(new String[0])
			);
		}
	}



	// -------------------------------------------------------------------------------
	// index of - find position



	@Test
	public void testIndexOf() {
		// of strings
		AssertEquals( 1, StringUtils.IndexOf("abcdef", 0, "b", "c") );
		AssertEquals(-1, StringUtils.IndexOf("abcdef", 3, "b", "c") );
		AssertEquals(-1, StringUtils.IndexOf("",          "b", "c") );
		AssertEquals(-1, StringUtils.IndexOf(null,        "b", "c") );
		// of chars
		AssertEquals( 1, StringUtils.IndexOf("abcdef", 0, 'b', 'c') );
		AssertEquals(-1, StringUtils.IndexOf("abcdef", 3, 'b', 'c') );
		AssertEquals(-1, StringUtils.IndexOf("",          'b', 'c') );
		AssertEquals(-1, StringUtils.IndexOf(null,        'b', 'c') );
	}



	@Test
	public void testIndexOfLast() {
		// of strings
		AssertEquals( 2, StringUtils.IndexOfLast("abcdef", "b", "c") );
		AssertEquals(-1, StringUtils.IndexOfLast("",       "b", "c") );
		AssertEquals(-1, StringUtils.IndexOfLast(null,     "b", "c") );
		// of chars
		AssertEquals( 2, StringUtils.IndexOfLast("abcdef", 'b', 'c') );
		AssertEquals(-1, StringUtils.IndexOfLast("",       'b', 'c') );
		AssertEquals(-1, StringUtils.IndexOfLast(null,     'b', 'c') );
	}



	@Test
	public void testFindLongestLine() {
		AssertEquals( 5, StringUtils.FindLongestLine(new String[] { "abc", "a", "12345", "ab" }) );
		AssertEquals(-1, StringUtils.FindLongestLine(new String[0]));
		AssertEquals(-1, StringUtils.FindLongestLine(null));
	}



	@Test
	public void testFirstPart() {
		// char
		AssertEquals( "abc", StringUtils.FirstPart("abc,def,ghi",   ',') );
		AssertEquals( "",    StringUtils.FirstPart(",abc,def,ghi,", ',') );
		AssertEquals( "abc", StringUtils.FirstPart("abc",           ',') );
		AssertEquals( "",    StringUtils.FirstPart("",              ',') );
		AssertEquals( "",    StringUtils.FirstPart(null,            ',') );
		// string
		AssertEquals( "abc", StringUtils.FirstPart("abc,def,ghi",   ",") );
		AssertEquals( "",    StringUtils.FirstPart(",abc,def,ghi,", ",") );
		AssertEquals( "abc", StringUtils.FirstPart("abc",           ",") );
		AssertEquals( "",    StringUtils.FirstPart("",              ",") );
		AssertEquals( "",    StringUtils.FirstPart(null,            ",") );
	}
	public void testLastPart() {
		// char
		AssertEquals( "ghi", StringUtils.LastPart("abc,def,ghi",   ',') );
		AssertEquals( "",    StringUtils.LastPart(",abc,def,ghi,", ',') );
		AssertEquals( "abc", StringUtils.LastPart("abc",           ',') );
		AssertEquals( "",    StringUtils.LastPart("",              ',') );
		AssertEquals( "",    StringUtils.LastPart(null,            ',') );
		// string
		AssertEquals( "ghi", StringUtils.LastPart("abc,def,ghi",   ",") );
		AssertEquals( "",    StringUtils.LastPart(",abc,def,ghi,", ",") );
		AssertEquals( "abc", StringUtils.LastPart("abc",           ",") );
		AssertEquals( "",    StringUtils.LastPart("",              ",") );
		AssertEquals( "",    StringUtils.LastPart(null,            ",") );
	}



	// -------------------------------------------------------------------------------
	// substring



	@Test
	public void testSubString() {
		AssertEquals(null,  StringUtils.SubString(null,  0, 0));
		AssertEquals("",    StringUtils.SubString("",    0, 0));
		AssertEquals("",    StringUtils.SubString("abc", 0, 0));
		AssertEquals("a",   StringUtils.SubString("abc", 0, 1));
		AssertEquals("ab",  StringUtils.SubString("abc", 0, 2));
		AssertEquals("abc", StringUtils.SubString("abc", 0, 3));
		AssertEquals("abc", StringUtils.SubString("abc", 0, 4));
		AssertEquals( "",   StringUtils.SubString("abc", 1, 0));
		AssertEquals( "b",  StringUtils.SubString("abc", 1, 1));
		AssertEquals( "bc", StringUtils.SubString("abc", 1, 2));
		AssertEquals( "bc", StringUtils.SubString("abc", 1, 3));
		AssertEquals( "bc", StringUtils.SubString("abc", 1, 4));
		AssertEquals(  "",  StringUtils.SubString("abc", 2, 0));
		AssertEquals(  "c", StringUtils.SubString("abc", 2, 1));
		AssertEquals(  "c", StringUtils.SubString("abc", 2, 2));
		AssertEquals(  "c", StringUtils.SubString("abc", 2, 3));
		AssertEquals(  "c", StringUtils.SubString("abc", 2, 4));
	}



	// -------------------------------------------------------------------------------
	// replace within string



	@Test
	public void testReplaceInString() {
		// String
		AssertEquals( "ab12efg", StringUtils.ReplaceInString("abcdefg", "12", 2) );
		AssertEquals( "ab12",    StringUtils.ReplaceInString("abcd",    "12", 2) );
		AssertEquals( "ab12",    StringUtils.ReplaceInString("abc",     "12", 2) );
		AssertEquals( "ab12",    StringUtils.ReplaceInString("ab",      "12", 2) );
		AssertEquals( "a12",     StringUtils.ReplaceInString("a",       "12", 2) );
		// StringBuilder
		final StringBuilder str = new StringBuilder();
		str.setLength(0); str.append("abcdefg"); StringUtils.ReplaceInString(str, "12", 2); AssertEquals( "ab12efg", str.toString());
		str.setLength(0); str.append("abcd");    StringUtils.ReplaceInString(str, "12", 2); AssertEquals( "ab12",    str.toString());
		str.setLength(0); str.append("abc");     StringUtils.ReplaceInString(str, "12", 2); AssertEquals( "ab12",    str.toString());
		str.setLength(0); str.append("ab");      StringUtils.ReplaceInString(str, "12", 2); AssertEquals( "ab12",    str.toString());
		str.setLength(0); str.append("a");       StringUtils.ReplaceInString(str, "12", 2); AssertEquals( "a12",     str.toString());
	}



	@Test
	public void testReplaceWith() {
		final String[] with = new String[] { "1", "2" };
		AssertEquals( "ab1c2d-ef", StringUtils.ReplaceWith("ab-c-d-ef", "-", with) );
		AssertEquals( "",          StringUtils.ReplaceWith("",          "-", with) );
		AssertEquals( null,        StringUtils.ReplaceWith(null,        "-", with) );
	}



	@Test
	public void testReplaceEnd() {
		AssertEquals( "Abcdef---", StringUtils.ReplaceEnd("Abcdef...", '.', '-') );
		AssertEquals( "Abcdef-",   StringUtils.ReplaceEnd("Abcdef.",   '.', '-') );
		AssertEquals( "Abcdef",    StringUtils.ReplaceEnd("Abcdef",    '.', '-') );
		AssertEquals( "---",       StringUtils.ReplaceEnd("...",       '.', '-') );
		AssertEquals( "",          StringUtils.ReplaceEnd("",          '.', '-') );
		AssertEquals( null,        StringUtils.ReplaceEnd(null,        '.', '-') );
	}



	// -------------------------------------------------------------------------------
	// replace tags



	@Test
	public void testReplaceTags() {
		// array tags
		{
			// string array tags
			AssertEquals( null,        StringUtils.sReplaceTags(null                ) );
			AssertEquals( "",          StringUtils.sReplaceTags(""                  ) );
			AssertEquals( "abc",       StringUtils.sReplaceTags("abc"               ) );
			AssertEquals( "1 2 3",     StringUtils.sReplaceTags(null,  "1", "2", "3") );
			AssertEquals( "1 2 3",     StringUtils.sReplaceTags("",    "1", "2", "3") );
			AssertEquals( "abc 1",     StringUtils.sReplaceTags("abc", "1"          ) );
			AssertEquals( "abc 1 2",   StringUtils.sReplaceTags("abc", "1", "2"     ) );
			AssertEquals( "abc 1 2 3", StringUtils.sReplaceTags("abc", "1", "2", "3") );
			AssertEquals( "a{}b{}c",   StringUtils.sReplaceTags("a{}b{}c"           ) );
			// object array tags
			AssertEquals( null,        StringUtils.oReplaceTags(null                ) );
			AssertEquals( "",          StringUtils.oReplaceTags(""                  ) );
			AssertEquals( "abc",       StringUtils.oReplaceTags("abc"               ) );
			AssertEquals( "1 2 3",     StringUtils.oReplaceTags(null,        1, 2, 3) );
			AssertEquals( "1 2 3",     StringUtils.oReplaceTags("",          1, 2, 3) );
			AssertEquals( "abc 1",     StringUtils.oReplaceTags("abc",       1      ) );
			AssertEquals( "abc 1 2",   StringUtils.oReplaceTags("abc",       1, 2   ) );
			AssertEquals( "abc 1 2 3", StringUtils.oReplaceTags("abc",       1, 2, 3) );
			AssertEquals( "a{}b{}c",   StringUtils.oReplaceTags("a{}b{}c"           ) );
			AssertEquals( "a1b{}c",    StringUtils.oReplaceTags("a{}b{}c",   1      ) );
			AssertEquals( "a1b2c",     StringUtils.oReplaceTags("a{}b{}c",   1, 2   ) );
			AssertEquals( "a1b2c 3",   StringUtils.oReplaceTags("a{}b{}c",   1, 2, 3) );
			AssertEquals( "a{1}b{2}c", StringUtils.oReplaceTags("a{1}b{2}c"         ) );
			AssertEquals( "a1b{2}c",   StringUtils.oReplaceTags("a{1}b{2}c", 1      ) );
			AssertEquals( "a1b2c",     StringUtils.oReplaceTags("a{1}b{2}c", 1, 2   ) );
			AssertEquals( "a1b2c 3",   StringUtils.oReplaceTags("a{1}b{2}c", 1, 2, 3) );
			AssertEquals( "a{2}b{1}c", StringUtils.oReplaceTags("a{2}b{1}c"         ) );
			AssertEquals( "a{2}b1c",   StringUtils.oReplaceTags("a{2}b{1}c", 1      ) );
			AssertEquals( "a2b1c",     StringUtils.oReplaceTags("a{2}b{1}c", 1, 2   ) );
			AssertEquals( "a2b1c 3",   StringUtils.oReplaceTags("a{2}b{1}c", 1, 2, 3) );
			AssertEquals( "a1b1c 2 3", StringUtils.oReplaceTags("a{1}b{}c",  1, 2, 3) );
			AssertEquals( "a3b1c",     StringUtils.oReplaceTags("a{3}b{}c",  1, 2, 3) );
			AssertEquals( "a2b1c 3",   StringUtils.oReplaceTags("a{2}b{}c",  1, 2, 3) );
			AssertEquals( "a1b1c 2 3", StringUtils.oReplaceTags("a{}b{1}c",  1, 2, 3) );
			AssertEquals( "a1b2c 3",   StringUtils.oReplaceTags("a{}b{2}c",  1, 2, 3) );
			AssertEquals( "a1bc 2 3",  StringUtils.oReplaceTags("a{}bc",     1, 2, 3) );
			AssertEquals( "a1bc 2 3",  StringUtils.oReplaceTags("a{1}bc",    1, 2, 3) );
			AssertEquals( "a2bc 3",    StringUtils.oReplaceTags("a{2}bc",    1, 2, 3) );
			AssertEquals( "a3bc",      StringUtils.oReplaceTags("a{3}bc",    1, 2, 3) );
			AssertEquals( "ad3fb2c",   StringUtils.oReplaceTags("a{}b{}c", "d{3}f", 2, 3 ) );
			AssertEquals( "ad2fbc 3",  StringUtils.oReplaceTags("a{}bc",   "d{2}f", 2, 3 ) );
			AssertEquals( "a123b123.456c 123.456", StringUtils.oReplaceTags("a{1}b{2}c", 123L, 123.456f, 123.456) );
			AssertEquals( "abc <TRUE> <false>",    StringUtils.oReplaceTags("abc",       true, false            ) );
			AssertEquals( "a<1>b<2>c", StringUtils.o_ReplaceTags("<%s>", "a<1>b<2>c"         ) );
			AssertEquals( "a1b<2>c",   StringUtils.o_ReplaceTags("<%s>", "a<1>b<2>c", 1      ) );
			AssertEquals( "a1b2c",     StringUtils.o_ReplaceTags("<%s>", "a<1>b<2>c", 1, 2   ) );
			AssertEquals( "a1b2c 3",   StringUtils.o_ReplaceTags("<%s>", "a<1>b<2>c", 1, 2, 3) );
		}
		// map tags
		{
			final HashMap<String, Object> tags = new HashMap<String, Object>();
			tags.put("let",  "abc");
			tags.put("num",  123  );
			tags.put("bool", true );
			AssertEquals( null,               StringUtils.soReplaceTags(null,                                     tags) );
			AssertEquals( "",                 StringUtils.soReplaceTags("",                                       tags) );
			AssertEquals( null,               StringUtils.soReplaceTags(null,            new HashMap<String, Object>()) );
			AssertEquals( "",                 StringUtils.soReplaceTags("",              new HashMap<String, Object>()) );
			AssertEquals( "-{}-{}-{}-",       StringUtils.soReplaceTags("-{}-{}-{}-",    new HashMap<String, Object>()) );
			AssertEquals( "-{1}-{2}-{3}-",    StringUtils.soReplaceTags("-{1}-{2}-{3}-", new HashMap<String, Object>()) );
			AssertEquals( "-{a}-{b}-{c}-",    StringUtils.soReplaceTags("-{a}-{b}-{c}-", new HashMap<String, Object>()) );
			AssertEquals( "-<TRUE>-123-abc-", StringUtils.soReplaceTags("-{bool}-{num}-{let}-",                   tags) );
			AssertEquals( "-<TRUE>-123-abc-",StringUtils.so_ReplaceTags("<%s>", "-<bool>-<num>-<let>-",           tags) );
		}
		{
			final HashMap<String, String> tags = new HashMap<String, String>();
			tags.put("abc", "def");
			tags.put("ghi", "jkl");
			AssertEquals( null,          StringUtils.ssReplaceTags(null,                                   tags) );
			AssertEquals( "",            StringUtils.ssReplaceTags("",                                     tags) );
			AssertEquals( null,          StringUtils.ssReplaceTags(null,          new HashMap<String, String>()) );
			AssertEquals( "",            StringUtils.ssReplaceTags("",            new HashMap<String, String>()) );
			AssertEquals( "{}-{}-{}",    StringUtils.ssReplaceTags("{}-{}-{}",    new HashMap<String, String>()) );
			AssertEquals( "{1}-{2}-{3}", StringUtils.ssReplaceTags("{1}-{2}-{3}", new HashMap<String, String>()) );
			AssertEquals( "{a}-{b}-{c}", StringUtils.ssReplaceTags("{a}-{b}-{c}", new HashMap<String, String>()) );
			AssertEquals( "def-jkl",     StringUtils.ssReplaceTags("{abc}-{ghi}",                          tags) );
			AssertEquals( "def-jkl",    StringUtils.ss_ReplaceTags("<%s>", "<abc>-<ghi>",                  tags) );
		}
	}



	// -------------------------------------------------------------------------------
	// arrays



	@Test
	public void testMergeStrings() {
		AssertEquals( "a-b-c", StringUtils.MergeStrings("-",  "a", "b", "c") );
		AssertEquals( "a-b-c", StringUtils.MergeStrings('-',  "a", "b", "c") );
		AssertEquals( "abc",   StringUtils.MergeStrings("",   "a", "b", "c") );
		AssertEquals( "abc",   StringUtils.MergeStrings(null, "a", "b", "c") );
	}



	@Test
	public void testMergeObjects() {
		AssertEquals( "a-b-c", StringUtils.MergeObjects("-",  "a", "b", "c") );
		AssertEquals( "a-b-c", StringUtils.MergeObjects('-',  "a", "b", "c") );
		AssertEquals( "abc",   StringUtils.MergeObjects("",   "a", "b", "c") );
		AssertEquals( "abc",   StringUtils.MergeObjects(null, "a", "b", "c") );
	}



	@Test
	public void testSplitLines() {
		AssertArrayEquals( new String[] { "Abc"                       }, StringUtils.SplitByLines(new String[] { "Abc"                   }) );
		AssertArrayEquals( new String[] { "A", "b", "c"               }, StringUtils.SplitByLines(new String[] { "A\nb\nc"               }) );
		AssertArrayEquals( new String[] { "Abc", "def", "ghi"         }, StringUtils.SplitByLines(new String[] { "Abc", "def", "ghi"     }) );
		AssertArrayEquals( new String[] { "Abc", "d", "e", "f", "ghi" }, StringUtils.SplitByLines(new String[] { "Abc", "d\ne\nf", "ghi" }) );
		AssertArrayEquals( new String[0],                                StringUtils.SplitByLines(new String[] { "\n\n"                  }) );
		AssertArrayEquals( new String[0],                                StringUtils.SplitByLines(new String[0]                           ) );
		AssertArrayEquals( null,                                         StringUtils.SplitByLines(null                                    ) );
	}



	@Test
	public void testSplit() {
		// char delims
		AssertArrayEquals( new String[] { "Abc", "def", "ghi" }, StringUtils.SplitByChars("Abc,def,ghi", ',') );
		AssertArrayEquals( new String[] { "Abc", "def"        }, StringUtils.SplitByChars(",Abc,,def,",  ',') );
		AssertArrayEquals( new String[] { "Abc",              }, StringUtils.SplitByChars(",Abc,",       ',') );
		AssertArrayEquals( new String[] { "Abc",              }, StringUtils.SplitByChars("Abc",         ',') );
		// string delims
		AssertArrayEquals( new String[] { "Abc", "def", "ghi" }, StringUtils.SplitByStrings("Abc,,def,,ghi", ",,") );
		AssertArrayEquals( new String[] { "Abc", "def"        }, StringUtils.SplitByStrings("Abc,,,,def",    ",,") );
		AssertArrayEquals( new String[] { "Abc",              }, StringUtils.SplitByStrings(",,Abc,,",       ",,") );
		AssertArrayEquals( new String[] { "Abc",              }, StringUtils.SplitByStrings("Abc",           ",,") );
	}
	@Test
	public void testSplitKeyVal() {
		// key/val delims
		final Map<String, String> mapA = new HashMap<String, String>();
		final Map<String, String> mapB = new HashMap<String, String>();
		final Map<String, String> mapC = new HashMap<String, String>();
		mapA.put("--", "Abc"); mapA.put("==", "def"); mapA.put("~~", "ghi");
		mapB.put("",   "Abc"); mapB.put("--", "def"); mapB.put("==", "ghi"); mapB.put("~~", "");
		mapC.put("~~", ""   ); mapC.put("--", ""   ); mapC.put("==", ""   );
		assertTrue(MatchMaps(mapA, StringUtils.SplitByKeyVal("--Abc==def~~ghi",   "--", "==", "~~")));
		assertTrue(MatchMaps(mapB, StringUtils.SplitByKeyVal(  "Abc--def==ghi~~", "--", "==", "~~")));
		assertTrue(MatchMaps(mapC, StringUtils.SplitByKeyVal("--==~~",            "--", "==", "~~")));
	}



	// -------------------------------------------------------------------------------
	// convert string



//TODO
//	@Test
//	public void testDecode() {
//	}



	// -------------------------------------------------------------------------------
	// generate string



	@Test
	public void testRandomString() {
		final String rnd = StringUtils.RandomString(5);
		AssertEquals(5, rnd.length());
		assertTrue(StringUtils.IsAlphaNum(rnd), rnd);
		assertNotEquals(rnd, StringUtils.RandomString(5));
	}



	@Test
	public void testRepeat() {
		AssertEquals( "-----",    StringUtils.Repeat(5, "-") );
		AssertEquals( "",         StringUtils.Repeat(0, "-") );
		AssertEquals( "12-12-12", StringUtils.Repeat(3, "12", "-") );
	}



}
