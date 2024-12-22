package com.poixson.utils;

import static com.poixson.utils.ArrayUtils.MatchMaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Assert;
import org.junit.Test;


public class Test_StringUtils {



	// -------------------------------------------------------------------------------
	// convert string



	@Test
	public void testToString() {
		Assert.assertEquals( "Abcdef", StringUtils.ToString("Abcdef"         ) );
		Assert.assertEquals( "Abcdef", StringUtils.ToString((Object) "Abcdef") );
		Assert.assertEquals( "TRUE",   StringUtils.ToString(true             ) );
		Assert.assertEquals( "false",  StringUtils.ToString(false            ) );
		Assert.assertEquals( "11",     StringUtils.ToString(11               ) );
		Assert.assertEquals( "11",     StringUtils.ToString(11L              ) );
		Assert.assertEquals( "1.23",   StringUtils.ToString(1.23             ) );
		Assert.assertEquals( "1.23",   StringUtils.ToString(1.23F            ) );
		Assert.assertEquals( null,     StringUtils.ToString(null             ) );
		Assert.assertEquals( "",       StringUtils.ToString(""               ) );
		// array to string
		Assert.assertEquals(
			"{A} {b} {c}",
			StringUtils.ToString( new String[] {"A", null, "b", "c"} )
		);
		// object to string
		{
			final String result = StringUtils.ToString(new Object());
			Assert.assertTrue(result.startsWith("java.lang.Object@"));
		}
		// exception to string
		{
			final String result = StringUtils.ToString(new Exception("Abcdef"));
			Assert.assertTrue(result.startsWith("java.lang.Exception: Abcdef"));
			Assert.assertEquals(null, StringUtils.ExceptionToString(null));
		}
	}



//TODO
//	@Test
//	public void testDecode() {
//	}



	@Test
	public void testSplitLines() {
		Assert.assertArrayEquals( new String[] { "Abc"                       }, StringUtils.SplitLines(new String[] { "Abc"                   }) );
		Assert.assertArrayEquals( new String[] { "A", "b", "c"               }, StringUtils.SplitLines(new String[] { "A\nb\nc"               }) );
		Assert.assertArrayEquals( new String[] { "Abc", "def", "ghi"         }, StringUtils.SplitLines(new String[] { "Abc", "def", "ghi"     }) );
		Assert.assertArrayEquals( new String[] { "Abc", "d", "e", "f", "ghi" }, StringUtils.SplitLines(new String[] { "Abc", "d\ne\nf", "ghi" }) );
		Assert.assertArrayEquals( new String[0], StringUtils.SplitLines(new String[] { "\n\n" }) );
		Assert.assertArrayEquals( new String[0], StringUtils.SplitLines(new String[0]          ) );
		Assert.assertArrayEquals( null,          StringUtils.SplitLines(null                   ) );
	}



	@Test
	public void testSplit() {
		// char delims
		Assert.assertArrayEquals( new String[] { "Abc", "def", "ghi" }, StringUtils.Split("Abc,def,ghi", ',') );
		Assert.assertArrayEquals( new String[] { "Abc", "def"        }, StringUtils.Split(",Abc,,def,",  ',') );
		Assert.assertArrayEquals( new String[] { "Abc",              }, StringUtils.Split(",Abc,",       ',') );
		Assert.assertArrayEquals( new String[] { "Abc",              }, StringUtils.Split("Abc",         ',') );
		// string delims
		Assert.assertArrayEquals( new String[] { "Abc", "def", "ghi" }, StringUtils.Split("Abc,,def,,ghi", ",,") );
		Assert.assertArrayEquals( new String[] { "Abc", "def"        }, StringUtils.Split("Abc,,,,def",    ",,") );
		Assert.assertArrayEquals( new String[] { "Abc",              }, StringUtils.Split(",,Abc,,",       ",,") );
		Assert.assertArrayEquals( new String[] { "Abc",              }, StringUtils.Split("Abc",           ",,") );
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
		Assert.assertTrue(MatchMaps(mapA, StringUtils.SplitKeyVal("--Abc==def~~ghi",   "--", "==", "~~")));
		Assert.assertTrue(MatchMaps(mapB, StringUtils.SplitKeyVal(  "Abc--def==ghi~~", "--", "==", "~~")));
		Assert.assertTrue(MatchMaps(mapC, StringUtils.SplitKeyVal("--==~~",            "--", "==", "~~")));
	}



	// -------------------------------------------------------------------------------
	// check value



	@Test
	public void testIsAlpha() {
		Assert.assertEquals( true,  StringUtils.IsAlpha("Abc"    ) );
		Assert.assertEquals( false, StringUtils.IsAlpha("A b c"  ) );
		Assert.assertEquals( false, StringUtils.IsAlpha("123"    ) );
		Assert.assertEquals( false, StringUtils.IsAlpha("Abc123" ) );
		Assert.assertEquals( false, StringUtils.IsAlpha("Abc123!") );
		Assert.assertEquals( false, StringUtils.IsAlpha(null     ) );
	}
	@Test
	public void testIsAlphaSpace() {
		Assert.assertEquals( true,  StringUtils.IsAlphaSpace("Abc"    ) );
		Assert.assertEquals( true,  StringUtils.IsAlphaSpace("A b c"  ) );
		Assert.assertEquals( false, StringUtils.IsAlphaSpace("123"    ) );
		Assert.assertEquals( false, StringUtils.IsAlphaSpace("Abc123" ) );
		Assert.assertEquals( false, StringUtils.IsAlphaSpace("Abc123!") );
		Assert.assertEquals( false, StringUtils.IsAlphaSpace(null     ) );
	}
	@Test
	public void testIsAlphaNum() {
		Assert.assertEquals( true,  StringUtils.IsAlphaNum("Abc"    ) );
		Assert.assertEquals( false, StringUtils.IsAlphaNum("A b c"  ) );
		Assert.assertEquals( true,  StringUtils.IsAlphaNum("123"    ) );
		Assert.assertEquals( true,  StringUtils.IsAlphaNum("Abc123" ) );
		Assert.assertEquals( false, StringUtils.IsAlphaNum("Abc123!") );
		Assert.assertEquals( false, StringUtils.IsAlphaNum(null     ) );
	}
	@Test
	public void testIsAlphaNumSpace() {
		Assert.assertEquals( true,  StringUtils.IsAlphaNumSpace("Abc"    ) );
		Assert.assertEquals( true,  StringUtils.IsAlphaNumSpace("A b c"  ) );
		Assert.assertEquals( true,  StringUtils.IsAlphaNumSpace("123"    ) );
		Assert.assertEquals( true,  StringUtils.IsAlphaNumSpace("Abc123" ) );
		Assert.assertEquals( false, StringUtils.IsAlphaNumSpace("Abc123!") );
		Assert.assertEquals( false, StringUtils.IsAlphaNumSpace(null     ) );
	}



	@Test
	public void testMatchString() {
		Assert.assertEquals( true,  StringUtils.MatchString("Abc", "Abc") );
		Assert.assertEquals( false, StringUtils.MatchString("Abc", "abc") );
		Assert.assertEquals( false, StringUtils.MatchString("Abc", ""   ) );
		Assert.assertEquals( false, StringUtils.MatchString("Abc", null ) );
		Assert.assertEquals( true,  StringUtils.MatchString(null,  null ) );
		Assert.assertEquals( true,  StringUtils.MatchString("",    ""   ) );
		Assert.assertEquals( true,  StringUtils.MatchString("",    null ) );
		Assert.assertEquals( true,  StringUtils.MatchString(null,  ""   ) );
	}
	@Test
	public void testMatchStringIgnoreCase() {
		Assert.assertEquals( true,  StringUtils.MatchStringIgnoreCase("Abc", "Abc") );
		Assert.assertEquals( true,  StringUtils.MatchStringIgnoreCase("Abc", "abc") );
		Assert.assertEquals( false, StringUtils.MatchStringIgnoreCase("Abc", ""   ) );
		Assert.assertEquals( false, StringUtils.MatchStringIgnoreCase("Abc", null ) );
		Assert.assertEquals( true,  StringUtils.MatchStringIgnoreCase(null,  null ) );
		Assert.assertEquals( true,  StringUtils.MatchStringIgnoreCase("",    ""   ) );
		Assert.assertEquals( true,  StringUtils.MatchStringIgnoreCase("",    null ) );
		Assert.assertEquals( true,  StringUtils.MatchStringIgnoreCase(null,  ""   ) );
	}
	@Test
	public void testMatchStringExact() {
		Assert.assertEquals( true,  StringUtils.MatchStringExact("Abc", "Abc") );
		Assert.assertEquals( false, StringUtils.MatchStringExact("Abc", "abc") );
		Assert.assertEquals( false, StringUtils.MatchStringExact("Abc", ""   ) );
		Assert.assertEquals( false, StringUtils.MatchStringExact("Abc", null ) );
		Assert.assertEquals( true,  StringUtils.MatchStringExact(null,  null ) );
		Assert.assertEquals( true,  StringUtils.MatchStringExact("",    ""   ) );
		Assert.assertEquals( false, StringUtils.MatchStringExact("",    null ) );
		Assert.assertEquals( false, StringUtils.MatchStringExact(null,  ""   ) );
	}



	// -------------------------------------------------------------------------------
	// trim



	@Test
	public void testTrimToNull() {
		// strip strings
		Assert.assertEquals( "Abc", StringUtils.TrimToNull("-=Abc=-", "=", "-") );
		Assert.assertEquals( null,  StringUtils.TrimToNull("",        "=", "-") );
		Assert.assertEquals( null,  StringUtils.TrimToNull(null,      "=", "-") );
		// strip chars
		Assert.assertEquals( "Abc", StringUtils.TrimToNull("-=Abc=-", '=', '-') );
		Assert.assertEquals( null,  StringUtils.TrimToNull("",        '=', '-') );
		Assert.assertEquals( null,  StringUtils.TrimToNull(null,      '=', '-') );
	}



	@Test
	public void testTrim() {
		// strip strings
		Assert.assertEquals( "Abc",    StringUtils.sTrim("-=Abc=-", "=", "-") );
		Assert.assertEquals( "abc",    StringUtils.sTrim("abcdef",  "de","f") );
		Assert.assertEquals( "abcdef", StringUtils.sTrim("abcdef",  "DE","F") );
		Assert.assertEquals( "",       StringUtils.sTrim("-=-=-",   "=", "-") );
		Assert.assertEquals( "",       StringUtils.sTrim("",        "=", "-") );
		Assert.assertEquals( null,     StringUtils.sTrim(null,      "=", "-") );
		// strip chars
		Assert.assertEquals( "Abc",    StringUtils.cTrim("-=Abc=-", '=', '-'     ) );
		Assert.assertEquals( "abc",    StringUtils.cTrim("abcdef",  'd', 'e', 'f') );
		Assert.assertEquals( "abcdef", StringUtils.cTrim("abcdef",  'D', 'E', 'F') );
		Assert.assertEquals( "",       StringUtils.cTrim("-=-=-",   '=', '-'     ) );
		Assert.assertEquals( "",       StringUtils.cTrim("",        '=', '-'     ) );
		Assert.assertEquals( null,     StringUtils.cTrim(null,      '=', '-'     ) );
	}



	@Test
	public void testITrim() {
		// strip strings
		Assert.assertEquals( "Abc", StringUtils.siTrim("-=Abc=-", "=", "-") );
		Assert.assertEquals( "abc", StringUtils.siTrim("abcdef",  "de","f") );
		Assert.assertEquals( "abc", StringUtils.siTrim("abcdef",  "DE","F") );
		Assert.assertEquals( "",    StringUtils.siTrim("-=-=-",   "=", "-") );
		Assert.assertEquals( "",    StringUtils.siTrim("",        "=", "-") );
		Assert.assertEquals( null,  StringUtils.siTrim(null,      "=", "-") );
		// strip chars
		Assert.assertEquals( "Abc", StringUtils.ciTrim("-=Abc=-", '=', '-'     ) );
		Assert.assertEquals( "abc", StringUtils.ciTrim("abcDEF",  'd', 'e', 'f') );
		Assert.assertEquals( "abc", StringUtils.ciTrim("abcDEF",  'D', 'E', 'F') );
		Assert.assertEquals( "",    StringUtils.ciTrim("-=-=-",   '=', '-'     ) );
		Assert.assertEquals( "",    StringUtils.ciTrim("",        '=', '-'     ) );
		Assert.assertEquals( null,  StringUtils.ciTrim(null,      '=', '-'     ) );
	}



	@Test
	public void testTrimFront() {
		// strip strings
		Assert.assertEquals( "Abc=-",  StringUtils.sfTrim("-=Abc=-", "=", "-") );
		Assert.assertEquals( "def",    StringUtils.sfTrim("abcdef",  "ab","c") );
		Assert.assertEquals( "abcdef", StringUtils.sfTrim("abcdef",  "AB","C") );
		Assert.assertEquals( "",       StringUtils.sfTrim("-=-=-",   "=", "-") );
		Assert.assertEquals( "",       StringUtils.sfTrim("",        "=", "-") );
		Assert.assertEquals( null,     StringUtils.sfTrim(null,      "=", "-") );
		// strip chars
		Assert.assertEquals( "Abc=-",  StringUtils.cfTrim("-=Abc=-", '=', '-'     ) );
		Assert.assertEquals( "def",    StringUtils.cfTrim("abcdef",  'a', 'b', 'c') );
		Assert.assertEquals( "abcdef", StringUtils.cfTrim("abcdef",  'A', 'B', 'C') );
		Assert.assertEquals( "",       StringUtils.cfTrim("-=-=-",   '=', '-'     ) );
		Assert.assertEquals( "",       StringUtils.cfTrim("",        '=', '-'     ) );
		Assert.assertEquals( null,     StringUtils.cfTrim(null,      '=', '-'     ) );
	}



	@Test
	public void testTrimEnd() {
		// strip strings
		Assert.assertEquals( "-=Abc",  StringUtils.seTrim("-=Abc=-", "=", "-") );
		Assert.assertEquals( "abc",    StringUtils.seTrim("abcdef",  "de","f") );
		Assert.assertEquals( "abcdef", StringUtils.seTrim("abcdef",  "DE","F") );
		Assert.assertEquals( "",       StringUtils.seTrim("-=-=-",   "=", "-") );
		Assert.assertEquals( "",       StringUtils.seTrim("",        "=", "-") );
		Assert.assertEquals( null,     StringUtils.seTrim(null,      "=", "-") );
		// strip chars
		Assert.assertEquals( "-=Abc",  StringUtils.ceTrim("-=Abc=-", '=', '-'     ) );
		Assert.assertEquals( "abc",    StringUtils.ceTrim("abcdef",  'd', 'e', 'f') );
		Assert.assertEquals( "abcdef", StringUtils.ceTrim("abcdef",  'D', 'E', 'F') );
		Assert.assertEquals( "",       StringUtils.ceTrim("-=-=-",   '=', '-'     ) );
		Assert.assertEquals( "",       StringUtils.ceTrim("",        '=', '-'     ) );
		Assert.assertEquals( null,     StringUtils.ceTrim(null,      '=', '-'     ) );
	}



	// -------------------------------------------------------------------------------
	// modify string



	@Test
	public void testRemoveFromString() {
		Assert.assertEquals( "Abf",    StringUtils.RemoveFromString("Abcdef", "cd", "e") );
		Assert.assertEquals( "Abcdef", StringUtils.RemoveFromString("Abcdef", "CD", "E") );
		Assert.assertEquals( "",       StringUtils.RemoveFromString("",       "=",  "-") );
		Assert.assertEquals( null,     StringUtils.RemoveFromString(null,     "=",  "-") );
	}



	@Test
	public void testForceStarts() {
		// string
		Assert.assertEquals( "aAbc", StringUtils.ForceStarts("a", "Abc") );
		Assert.assertEquals( "abc",  StringUtils.ForceStarts("a", "abc") );
		Assert.assertEquals( "a",    StringUtils.ForceStarts("a", ""   ) );
		Assert.assertEquals( null,   StringUtils.ForceStarts("a", null ) );
		// char
		Assert.assertEquals( "aAbc", StringUtils.ForceStarts('a', "Abc") );
		Assert.assertEquals( "abc",  StringUtils.ForceStarts('a', "abc") );
		Assert.assertEquals( "a",    StringUtils.ForceStarts('a', ""   ) );
		Assert.assertEquals( null,   StringUtils.ForceStarts('a', null ) );
	}
	@Test
	public void testForceEnds() {
		// string
		Assert.assertEquals( "abcd", StringUtils.ForceEnds("d", "abc") );
		Assert.assertEquals( "abc",  StringUtils.ForceEnds("c", "abc") );
		Assert.assertEquals( "c",    StringUtils.ForceEnds("c", ""   ) );
		Assert.assertEquals( null,   StringUtils.ForceEnds("c", null ) );
		// char
		Assert.assertEquals( "abcd", StringUtils.ForceEnds('d', "abc") );
		Assert.assertEquals( "abc",  StringUtils.ForceEnds('c', "abc") );
		Assert.assertEquals( "c",    StringUtils.ForceEnds('c', ""   ) );
		Assert.assertEquals( null,   StringUtils.ForceEnds('c', null ) );
	}



	// unique key
	@Test
	public void testUniqueKey() {
		{
			final List<String> list = new ArrayList<String>();
			list.add("a");
			Assert.assertEquals( "a_1", StringUtils.UniqueKey(list, "a") );
			Assert.assertEquals( "b",   StringUtils.UniqueKey(list, "b") );
			Assert.assertArrayEquals(
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
			Assert.assertEquals( "a_1", StringUtils.AddUnique(list, "a") );
			Assert.assertEquals( "b",   StringUtils.AddUnique(list, "b") );
			Assert.assertArrayEquals(
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
			Assert.assertEquals( "a_1", StringUtils.PutUnique(map, "a", new Object()) );
			Assert.assertEquals( "b",   StringUtils.PutUnique(map, "b", new Object()) );
			Assert.assertArrayEquals(
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
			Assert.assertEquals( "a_1", StringUtils.PutUnique(map, "a", new Object()) );
			Assert.assertEquals( "b",   StringUtils.PutUnique(map, "b", new Object()) );
			Assert.assertArrayEquals(
				new String[] { "a", "a_1", "b" },
				map.keySet().toArray(new String[0])
			);
		}
	}



	// -------------------------------------------------------------------------------
	// build string



	@Test
	public void testMergeStrings() {
		Assert.assertEquals( "a-b-c", StringUtils.MergeStrings("-",  "a", "b", "c") );
		Assert.assertEquals( "a-b-c", StringUtils.MergeStrings('-',  "a", "b", "c") );
		Assert.assertEquals( "abc",   StringUtils.MergeStrings("",   "a", "b", "c") );
		Assert.assertEquals( "abc",   StringUtils.MergeStrings(null, "a", "b", "c") );
	}



	@Test
	public void testMergeObjects() {
		Assert.assertEquals( "a-b-c", StringUtils.MergeObjects("-",  "a", "b", "c") );
		Assert.assertEquals( "a-b-c", StringUtils.MergeObjects('-',  "a", "b", "c") );
		Assert.assertEquals( "abc",   StringUtils.MergeObjects("",   "a", "b", "c") );
		Assert.assertEquals( "abc",   StringUtils.MergeObjects(null, "a", "b", "c") );
	}



	@Test
	public void testWildcardToRegex() {
		Assert.assertEquals( "^ab.*c$",  StringUtils.WildcardToRegex("ab*c") );
		Assert.assertEquals( "^ab.c$",   StringUtils.WildcardToRegex("ab?c") );
		Assert.assertEquals( "^ab\\$c$", StringUtils.WildcardToRegex("ab$c") );
		Assert.assertEquals( "",         StringUtils.WildcardToRegex(""    ) );
		Assert.assertEquals( null,       StringUtils.WildcardToRegex(null  ) );
	}



	// -------------------------------------------------------------------------------
	// find position



	@Test
	public void testIndexOf() {
		// of strings
		Assert.assertEquals( 1, StringUtils.IndexOf("abcdef", 0, "b", "c") );
		Assert.assertEquals(-1, StringUtils.IndexOf("abcdef", 3, "b", "c") );
		Assert.assertEquals(-1, StringUtils.IndexOf("",          "b", "c") );
		Assert.assertEquals(-1, StringUtils.IndexOf(null,        "b", "c") );
		// of chars
		Assert.assertEquals( 1, StringUtils.IndexOf("abcdef", 0, 'b', 'c') );
		Assert.assertEquals(-1, StringUtils.IndexOf("abcdef", 3, 'b', 'c') );
		Assert.assertEquals(-1, StringUtils.IndexOf("",          'b', 'c') );
		Assert.assertEquals(-1, StringUtils.IndexOf(null,        'b', 'c') );
	}



	@Test
	public void testIndexOfLast() {
		// of strings
		Assert.assertEquals( 2, StringUtils.IndexOfLast("abcdef", "b", "c") );
		Assert.assertEquals(-1, StringUtils.IndexOfLast("",       "b", "c") );
		Assert.assertEquals(-1, StringUtils.IndexOfLast(null,     "b", "c") );
		// of chars
		Assert.assertEquals( 2, StringUtils.IndexOfLast("abcdef", 'b', 'c') );
		Assert.assertEquals(-1, StringUtils.IndexOfLast("",       'b', 'c') );
		Assert.assertEquals(-1, StringUtils.IndexOfLast(null,     'b', 'c') );
	}



	@Test
	public void testFindLongestLine() {
		Assert.assertEquals( 5, StringUtils.FindLongestLine(new String[] { "abc", "a", "12345", "ab" }) );
		Assert.assertEquals(-1, StringUtils.FindLongestLine(new String[0]));
		Assert.assertEquals(-1, StringUtils.FindLongestLine(null));
	}



	@Test
	public void testFirstPart() {
		// char
		Assert.assertEquals( "abc", StringUtils.FirstPart("abc,def,ghi",   ',') );
		Assert.assertEquals( "",    StringUtils.FirstPart(",abc,def,ghi,", ',') );
		Assert.assertEquals( "abc", StringUtils.FirstPart("abc",           ',') );
		Assert.assertEquals( "",    StringUtils.FirstPart("",              ',') );
		Assert.assertEquals( "",    StringUtils.FirstPart(null,            ',') );
		// string
		Assert.assertEquals( "abc", StringUtils.FirstPart("abc,def,ghi",   ",") );
		Assert.assertEquals( "",    StringUtils.FirstPart(",abc,def,ghi,", ",") );
		Assert.assertEquals( "abc", StringUtils.FirstPart("abc",           ",") );
		Assert.assertEquals( "",    StringUtils.FirstPart("",              ",") );
		Assert.assertEquals( "",    StringUtils.FirstPart(null,            ",") );
	}
	public void testLastPart() {
		// char
		Assert.assertEquals( "ghi", StringUtils.LastPart("abc,def,ghi",   ',') );
		Assert.assertEquals( "",    StringUtils.LastPart(",abc,def,ghi,", ',') );
		Assert.assertEquals( "abc", StringUtils.LastPart("abc",           ',') );
		Assert.assertEquals( "",    StringUtils.LastPart("",              ',') );
		Assert.assertEquals( "",    StringUtils.LastPart(null,            ',') );
		// string
		Assert.assertEquals( "ghi", StringUtils.LastPart("abc,def,ghi",   ",") );
		Assert.assertEquals( "",    StringUtils.LastPart(",abc,def,ghi,", ",") );
		Assert.assertEquals( "abc", StringUtils.LastPart("abc",           ",") );
		Assert.assertEquals( "",    StringUtils.LastPart("",              ",") );
		Assert.assertEquals( "",    StringUtils.LastPart(null,            ",") );
	}



	// -------------------------------------------------------------------------------
	// replace within string



	@Test
	public void testReplaceInString() {
		// String
		Assert.assertEquals( "ab12efg", StringUtils.ReplaceInString("abcdefg", "12", 2) );
		Assert.assertEquals( "ab12",    StringUtils.ReplaceInString("abcd",    "12", 2) );
		Assert.assertEquals( "ab12",    StringUtils.ReplaceInString("abc",     "12", 2) );
		Assert.assertEquals( "ab12",    StringUtils.ReplaceInString("ab",      "12", 2) );
		Assert.assertEquals( "a12",     StringUtils.ReplaceInString("a",       "12", 2) );
		// StringBuilder
		final StringBuilder str = new StringBuilder();
		str.setLength(0); str.append("abcdefg"); StringUtils.ReplaceInString(str, "12", 2); Assert.assertEquals( "ab12efg", str.toString());
		str.setLength(0); str.append("abcd");    StringUtils.ReplaceInString(str, "12", 2); Assert.assertEquals( "ab12",    str.toString());
		str.setLength(0); str.append("abc");     StringUtils.ReplaceInString(str, "12", 2); Assert.assertEquals( "ab12",    str.toString());
		str.setLength(0); str.append("ab");      StringUtils.ReplaceInString(str, "12", 2); Assert.assertEquals( "ab12",    str.toString());
		str.setLength(0); str.append("a");       StringUtils.ReplaceInString(str, "12", 2); Assert.assertEquals( "a12",     str.toString());
	}



	@Test
	public void testReplaceWith() {
		final String[] with = new String[] { "1", "2" };
		Assert.assertEquals( "ab1c2d-ef", StringUtils.ReplaceWith("ab-c-d-ef", "-", with) );
		Assert.assertEquals( "",          StringUtils.ReplaceWith("",          "-", with) );
		Assert.assertEquals( null,        StringUtils.ReplaceWith(null,        "-", with) );
	}



	@Test
	public void testReplaceEnd() {
		Assert.assertEquals( "Abcdef---", StringUtils.ReplaceEnd("Abcdef...", '.', '-') );
		Assert.assertEquals( "Abcdef-",   StringUtils.ReplaceEnd("Abcdef.",   '.', '-') );
		Assert.assertEquals( "Abcdef",    StringUtils.ReplaceEnd("Abcdef",    '.', '-') );
		Assert.assertEquals( "---",       StringUtils.ReplaceEnd("...",       '.', '-') );
		Assert.assertEquals( "",          StringUtils.ReplaceEnd("",          '.', '-') );
		Assert.assertEquals( null,        StringUtils.ReplaceEnd(null,        '.', '-') );
	}



	// -------------------------------------------------------------------------------
	// replace tags



	@Test
	public void testReplaceTags() {
		// array tags
		{
			Assert.assertEquals( null,        StringUtils.oReplaceTags(null                ) );
			Assert.assertEquals( "",          StringUtils.oReplaceTags(""                  ) );
			Assert.assertEquals( "abc",       StringUtils.oReplaceTags("abc"               ) );
			Assert.assertEquals( "1 2 3",     StringUtils.oReplaceTags(null,        1, 2, 3) );
			Assert.assertEquals( "1 2 3",     StringUtils.oReplaceTags("",          1, 2, 3) );
			Assert.assertEquals( "abc 1",     StringUtils.oReplaceTags("abc",       1      ) );
			Assert.assertEquals( "abc 1 2",   StringUtils.oReplaceTags("abc",       1, 2   ) );
			Assert.assertEquals( "abc 1 2 3", StringUtils.oReplaceTags("abc",       1, 2, 3) );
			Assert.assertEquals( "a{}b{}c",   StringUtils.oReplaceTags("a{}b{}c"           ) );
			Assert.assertEquals( "a1b{}c",    StringUtils.oReplaceTags("a{}b{}c",   1      ) );
			Assert.assertEquals( "a1b2c",     StringUtils.oReplaceTags("a{}b{}c",   1, 2   ) );
			Assert.assertEquals( "a1b2c 3",   StringUtils.oReplaceTags("a{}b{}c",   1, 2, 3) );
			Assert.assertEquals( "a{1}b{2}c", StringUtils.oReplaceTags("a{1}b{2}c"         ) );
			Assert.assertEquals( "a1b{2}c",   StringUtils.oReplaceTags("a{1}b{2}c", 1      ) );
			Assert.assertEquals( "a1b2c",     StringUtils.oReplaceTags("a{1}b{2}c", 1, 2   ) );
			Assert.assertEquals( "a1b2c 3",   StringUtils.oReplaceTags("a{1}b{2}c", 1, 2, 3) );
			Assert.assertEquals( "a{2}b{1}c", StringUtils.oReplaceTags("a{2}b{1}c"         ) );
			Assert.assertEquals( "a{2}b1c",   StringUtils.oReplaceTags("a{2}b{1}c", 1      ) );
			Assert.assertEquals( "a2b1c",     StringUtils.oReplaceTags("a{2}b{1}c", 1, 2   ) );
			Assert.assertEquals( "a2b1c 3",   StringUtils.oReplaceTags("a{2}b{1}c", 1, 2, 3) );
			Assert.assertEquals( "a1b1c 2 3", StringUtils.oReplaceTags("a{1}b{}c",  1, 2, 3) );
			Assert.assertEquals( "a3b1c",     StringUtils.oReplaceTags("a{3}b{}c",  1, 2, 3) );
			Assert.assertEquals( "a2b1c 3",   StringUtils.oReplaceTags("a{2}b{}c",  1, 2, 3) );
			Assert.assertEquals( "a1b1c 2 3", StringUtils.oReplaceTags("a{}b{1}c",  1, 2, 3) );
			Assert.assertEquals( "a1b2c 3",   StringUtils.oReplaceTags("a{}b{2}c",  1, 2, 3) );
			Assert.assertEquals( "a1bc 2 3",  StringUtils.oReplaceTags("a{}bc",     1, 2, 3) );
			Assert.assertEquals( "a1bc 2 3",  StringUtils.oReplaceTags("a{1}bc",    1, 2, 3) );
			Assert.assertEquals( "a2bc 3",    StringUtils.oReplaceTags("a{2}bc",    1, 2, 3) );
			Assert.assertEquals( "a3bc",      StringUtils.oReplaceTags("a{3}bc",    1, 2, 3) );
			Assert.assertEquals( "ad3fb2c",   StringUtils.oReplaceTags("a{}b{}c", "d{3}f", 2, 3 ) );
			Assert.assertEquals( "ad2fbc 3",  StringUtils.oReplaceTags("a{}bc",   "d{2}f", 2, 3 ) );
			Assert.assertEquals( "a123b123.456c 123.456", StringUtils.oReplaceTags("a{1}b{2}c", 123L, 123.456f, 123.456) );
			Assert.assertEquals( "abc TRUE false",        StringUtils.oReplaceTags("abc",       true, false            ) );
			Assert.assertEquals( "a<1>b<2>c", StringUtils.o_ReplaceTags("<%s>", "a<1>b<2>c"         ) );
			Assert.assertEquals( "a1b<2>c",   StringUtils.o_ReplaceTags("<%s>", "a<1>b<2>c", 1      ) );
			Assert.assertEquals( "a1b2c",     StringUtils.o_ReplaceTags("<%s>", "a<1>b<2>c", 1, 2   ) );
			Assert.assertEquals( "a1b2c 3",   StringUtils.o_ReplaceTags("<%s>", "a<1>b<2>c", 1, 2, 3) );
		}
		// map tags
		{
			final HashMap<String, Object> tags = new HashMap<String, Object>();
			tags.put("letters", "abc");
			tags.put("numbers", 123  );
			tags.put("boolean", true );
			Assert.assertEquals( null,           StringUtils.soReplaceTags(null,                                   tags) );
			Assert.assertEquals( "",             StringUtils.soReplaceTags("",                                     tags) );
			Assert.assertEquals( null,           StringUtils.soReplaceTags(null,          new HashMap<String, Object>()) );
			Assert.assertEquals( "",             StringUtils.soReplaceTags("",            new HashMap<String, Object>()) );
			Assert.assertEquals( "{}-{}-{}",     StringUtils.soReplaceTags("{}-{}-{}",    new HashMap<String, Object>()) );
			Assert.assertEquals( "{1}-{2}-{3}",  StringUtils.soReplaceTags("{1}-{2}-{3}", new HashMap<String, Object>()) );
			Assert.assertEquals( "{a}-{b}-{c}",  StringUtils.soReplaceTags("{a}-{b}-{c}", new HashMap<String, Object>()) );
			Assert.assertEquals( "TRUE 123 abc", StringUtils.soReplaceTags("{boolean} {numbers} {letters}",        tags) );
		}
	}



	// -------------------------------------------------------------------------------
	// generate string



	@Test
	public void testRandomString() {
		final String rnd = StringUtils.RandomString(5);
		Assert.assertEquals(5, rnd.length());
		Assert.assertTrue(rnd, StringUtils.IsAlphaNum(rnd));
		Assert.assertNotEquals(rnd, StringUtils.RandomString(5));
	}



	@Test
	public void testRepeat() {
		Assert.assertEquals( "-----",    StringUtils.Repeat(5, "-") );
		Assert.assertEquals( "",         StringUtils.Repeat(0, "-") );
		Assert.assertEquals( "12-12-12", StringUtils.Repeat(3, "12", "-") );
	}



	// -------------------------------------------------------------------------------
	// pad string



	@Test
	public void testPad() {
		// pad front
		Assert.assertEquals( "--abc", StringUtils.PadFront(5, "abc", '-') );
		Assert.assertEquals( "abc",   StringUtils.PadFront(3, "abc", '-') );
		Assert.assertEquals( "abc",   StringUtils.PadFront(1, "abc", '-') );
		// pad end
		Assert.assertEquals( "abc--", StringUtils.PadEnd(5, "abc", '-') );
		Assert.assertEquals( "abc",   StringUtils.PadEnd(3, "abc", '-') );
		Assert.assertEquals( "abc",   StringUtils.PadEnd(1, "abc", '-') );
		// pad center
		Assert.assertEquals( "-abc-", StringUtils.PadCenter(5, "abc", '-') );
		Assert.assertEquals( "abc",   StringUtils.PadCenter(3, "abc", '-') );
		Assert.assertEquals( "abc",   StringUtils.PadCenter(1, "abc", '-') );
	}
	@Test
	public void testPadZero_Int() {
		// pad front
		Assert.assertEquals( null,  StringUtils.PadFront(0, 1) );
		Assert.assertEquals( "1",   StringUtils.PadFront(1, 1) );
		Assert.assertEquals( "01",  StringUtils.PadFront(2, 1) );
		Assert.assertEquals( "001", StringUtils.PadFront(3, 1) );
		// pad end
		Assert.assertEquals( null,  StringUtils.PadEnd(0, 1) );
		Assert.assertEquals( "1",   StringUtils.PadEnd(1, 1) );
		Assert.assertEquals( "10",  StringUtils.PadEnd(2, 1) );
		Assert.assertEquals( "100", StringUtils.PadEnd(3, 1) );
	}
	@Test
	public void testPadZero_Long() {
		// pad front
		Assert.assertEquals( null,  StringUtils.PadFront(0, 1L) );
		Assert.assertEquals( "1",   StringUtils.PadFront(1, 1L) );
		Assert.assertEquals( "01",  StringUtils.PadFront(2, 1L) );
		Assert.assertEquals( "001", StringUtils.PadFront(3, 1L) );
		// pad end
		Assert.assertEquals( null,  StringUtils.PadEnd(0, 1L) );
		Assert.assertEquals( "1",   StringUtils.PadEnd(1, 1L) );
		Assert.assertEquals( "10",  StringUtils.PadEnd(2, 1L) );
		Assert.assertEquals( "100", StringUtils.PadEnd(3, 1L) );
	}



	// -------------------------------------------------------------------------------
	// compare versions



	@Test
	public void testCompareVersions() {
		Assert.assertEquals(       0.0, StringUtils.CompareVersions("1.2.3", "1.2.3"), 0.0);
		Assert.assertEquals(       1.0, StringUtils.CompareVersions("1.2.3", "1.2.4"), 0.0);
		Assert.assertEquals(      -1.0, StringUtils.CompareVersions("1.2.3", "1.2.2"), 0.0);
		Assert.assertEquals(    1000.0, StringUtils.CompareVersions("1.2.0", "1.3.0"), 0.0);
		Assert.assertEquals(   -1000.0, StringUtils.CompareVersions("1.2.0", "1.1.0"), 0.0);
		Assert.assertEquals( 2000000.0, StringUtils.CompareVersions("1.2.3", "3.2.3"), 0.0);
		Assert.assertEquals(-1000000.0, StringUtils.CompareVersions("1.2.3", "0.2.3"), 0.0);
		Assert.assertEquals(      -3.0, StringUtils.CompareVersions("1.2.3", "1.2"  ), 0.0);
		Assert.assertEquals(       3.0, StringUtils.CompareVersions("1.2",   "1.2.3"), 0.0);
		Assert.assertEquals(    -997.0, StringUtils.CompareVersions("1.3",   "1.2.3"), 0.0);
		Assert.assertEquals(    2003.0, StringUtils.CompareVersions("1",     "1.2.3"), 0.0);
		Assert.assertEquals(       0.5, StringUtils.CompareVersions("1.2.3", "1.2.3-SNAPSHOT"), 0.0);
		Assert.assertEquals(      -0.5, StringUtils.CompareVersions("1.2.3-SNAPSHOT", "1.2.3"), 0.0);
		Assert.assertEquals(      -0.5, StringUtils.CompareVersions("1.2.4", "1.2.3-SNAPSHOT"), 0.0);
		Assert.assertEquals(       0.5, StringUtils.CompareVersions("1.2.3", "1.2.3-R0.1-SNAPSHOT"), 0.0);
	}



}
