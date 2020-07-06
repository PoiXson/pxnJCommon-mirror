package com.poixson.tests.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.poixson.utils.StringUtils;
import com.poixson.utils.TestUtils;


public class Test_StringUtils {



	// ------------------------------------------------------------------------------- //
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
	public void testStringToArray() {
		Assert.assertArrayEquals( new String[] { "Abc" }, StringUtils.StringToArray( "Abc" ) );
		Assert.assertArrayEquals( null, StringUtils.StringToArray( null ) );
	}



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



	// ------------------------------------------------------------------------------- //
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
	public void testStrEquals() {
		Assert.assertEquals( true,  StringUtils.StrEquals("Abc", "Abc") );
		Assert.assertEquals( false, StringUtils.StrEquals("Abc", "abc") );
		Assert.assertEquals( false, StringUtils.StrEquals("Abc", ""   ) );
		Assert.assertEquals( false, StringUtils.StrEquals("Abc", null ) );
		Assert.assertEquals( true,  StringUtils.StrEquals(null,  null ) );
		Assert.assertEquals( true,  StringUtils.StrEquals("",    ""   ) );
		Assert.assertEquals( true,  StringUtils.StrEquals("",    null ) );
		Assert.assertEquals( true,  StringUtils.StrEquals(null,  ""   ) );
	}
	@Test
	public void testEqualsIgnoreCase() {
		Assert.assertEquals( true,  StringUtils.StrEqualsIgnoreCase("Abc", "Abc") );
		Assert.assertEquals( true,  StringUtils.StrEqualsIgnoreCase("Abc", "abc") );
		Assert.assertEquals( false, StringUtils.StrEqualsIgnoreCase("Abc", ""   ) );
		Assert.assertEquals( false, StringUtils.StrEqualsIgnoreCase("Abc", null ) );
		Assert.assertEquals( true,  StringUtils.StrEqualsIgnoreCase(null,  null ) );
		Assert.assertEquals( true,  StringUtils.StrEqualsIgnoreCase("",    ""   ) );
		Assert.assertEquals( true,  StringUtils.StrEqualsIgnoreCase("",    null ) );
		Assert.assertEquals( true,  StringUtils.StrEqualsIgnoreCase(null,  ""   ) );
	}
	@Test
	public void testStrEqualsExact() {
		Assert.assertEquals( true,  StringUtils.StrEqualsExact("Abc", "Abc") );
		Assert.assertEquals( false, StringUtils.StrEqualsExact("Abc", "abc") );
		Assert.assertEquals( false, StringUtils.StrEqualsExact("Abc", ""   ) );
		Assert.assertEquals( false, StringUtils.StrEqualsExact("Abc", null ) );
		Assert.assertEquals( true,  StringUtils.StrEqualsExact(null,  null ) );
		Assert.assertEquals( true,  StringUtils.StrEqualsExact("",    ""   ) );
		Assert.assertEquals( false, StringUtils.StrEqualsExact("",    null ) );
		Assert.assertEquals( false, StringUtils.StrEqualsExact(null,  ""   ) );
	}
	@Test
	public void testStrEqualsExactIgnoreCase() {
		Assert.assertEquals( true,  StringUtils.StrEqualsExactIgnoreCase("Abc", "Abc") );
		Assert.assertEquals( true,  StringUtils.StrEqualsExactIgnoreCase("Abc", "abc") );
		Assert.assertEquals( false, StringUtils.StrEqualsExactIgnoreCase("Abc", ""   ) );
		Assert.assertEquals( false, StringUtils.StrEqualsExactIgnoreCase("Abc", null ) );
		Assert.assertEquals( true,  StringUtils.StrEqualsExactIgnoreCase(null,  null ) );
		Assert.assertEquals( true,  StringUtils.StrEqualsExactIgnoreCase("",    ""   ) );
		Assert.assertEquals( false, StringUtils.StrEqualsExactIgnoreCase("",    null ) );
		Assert.assertEquals( false, StringUtils.StrEqualsExactIgnoreCase(null,  ""   ) );
	}



	// ------------------------------------------------------------------------------- //
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
		Assert.assertEquals( "Abc",    StringUtils.Trim("-=Abc=-", "=", "-") );
		Assert.assertEquals( "abc",    StringUtils.Trim("abcdef",  "de","f") );
		Assert.assertEquals( "abcdef", StringUtils.Trim("abcdef",  "DE","F") );
		Assert.assertEquals( "",       StringUtils.Trim("-=-=-",   "=", "-") );
		Assert.assertEquals( "",       StringUtils.Trim("",        "=", "-") );
		Assert.assertEquals( null,     StringUtils.Trim(null,      "=", "-") );
		// strip chars
		Assert.assertEquals( "Abc",    StringUtils.Trim("-=Abc=-", '=', '-'     ) );
		Assert.assertEquals( "abc",    StringUtils.Trim("abcdef",  'd', 'e', 'f') );
		Assert.assertEquals( "abcdef", StringUtils.Trim("abcdef",  'D', 'E', 'F') );
		Assert.assertEquals( "",       StringUtils.Trim("-=-=-",   '=', '-'     ) );
		Assert.assertEquals( "",       StringUtils.Trim("",        '=', '-'     ) );
		Assert.assertEquals( null,     StringUtils.Trim(null,      '=', '-'     ) );
	}



	@Test
	public void testITrim() {
		// strip strings
		Assert.assertEquals( "Abc", StringUtils.iTrim("-=Abc=-", "=", "-") );
		Assert.assertEquals( "abc", StringUtils.iTrim("abcdef",  "de","f") );
		Assert.assertEquals( "abc", StringUtils.iTrim("abcdef",  "DE","F") );
		Assert.assertEquals( "",    StringUtils.iTrim("-=-=-",   "=", "-") );
		Assert.assertEquals( "",    StringUtils.iTrim("",        "=", "-") );
		Assert.assertEquals( null,  StringUtils.iTrim(null,      "=", "-") );
		// strip chars
		Assert.assertEquals( "Abc", StringUtils.iTrim("-=Abc=-", '=', '-'     ) );
		Assert.assertEquals( "abc", StringUtils.iTrim("abcDEF",  'd', 'e', 'f') );
		Assert.assertEquals( "abc", StringUtils.iTrim("abcDEF",  'D', 'E', 'F') );
		Assert.assertEquals( "",    StringUtils.iTrim("-=-=-",   '=', '-'     ) );
		Assert.assertEquals( "",    StringUtils.iTrim("",        '=', '-'     ) );
		Assert.assertEquals( null,  StringUtils.iTrim(null,      '=', '-'     ) );
	}



	@Test
	public void testTrimFront() {
		// strip strings
		Assert.assertEquals( "Abc=-",  StringUtils.TrimFront("-=Abc=-", "=", "-") );
		Assert.assertEquals( "def",    StringUtils.TrimFront("abcdef",  "ab","c") );
		Assert.assertEquals( "abcdef", StringUtils.TrimFront("abcdef",  "AB","C") );
		Assert.assertEquals( "",       StringUtils.TrimFront("-=-=-",   "=", "-") );
		Assert.assertEquals( "",       StringUtils.TrimFront("",        "=", "-") );
		Assert.assertEquals( null,     StringUtils.TrimFront(null,      "=", "-") );
		// strip chars
		Assert.assertEquals( "Abc=-",  StringUtils.TrimFront("-=Abc=-", '=', '-'     ) );
		Assert.assertEquals( "def",    StringUtils.TrimFront("abcdef",  'a', 'b', 'c') );
		Assert.assertEquals( "abcdef", StringUtils.TrimFront("abcdef",  'A', 'B', 'C') );
		Assert.assertEquals( "",       StringUtils.TrimFront("-=-=-",   '=', '-'     ) );
		Assert.assertEquals( "",       StringUtils.TrimFront("",        '=', '-'     ) );
		Assert.assertEquals( null,     StringUtils.TrimFront(null,      '=', '-'     ) );
	}



	@Test
	public void testTrimEnd() {
		// strip strings
		Assert.assertEquals( "-=Abc",  StringUtils.TrimEnd("-=Abc=-", "=", "-") );
		Assert.assertEquals( "abc",    StringUtils.TrimEnd("abcdef",  "de","f") );
		Assert.assertEquals( "abcdef", StringUtils.TrimEnd("abcdef",  "DE","F") );
		Assert.assertEquals( "",       StringUtils.TrimEnd("-=-=-",   "=", "-") );
		Assert.assertEquals( "",       StringUtils.TrimEnd("",        "=", "-") );
		Assert.assertEquals( null,     StringUtils.TrimEnd(null,      "=", "-") );
		// strip chars
		Assert.assertEquals( "-=Abc",  StringUtils.TrimEnd("-=Abc=-", '=', '-'     ) );
		Assert.assertEquals( "abc",    StringUtils.TrimEnd("abcdef",  'd', 'e', 'f') );
		Assert.assertEquals( "abcdef", StringUtils.TrimEnd("abcdef",  'D', 'E', 'F') );
		Assert.assertEquals( "",       StringUtils.TrimEnd("-=-=-",   '=', '-'     ) );
		Assert.assertEquals( "",       StringUtils.TrimEnd("",        '=', '-'     ) );
		Assert.assertEquals( null,     StringUtils.TrimEnd(null,      '=', '-'     ) );
	}



	// ------------------------------------------------------------------------------- //
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
		Assert.assertEquals( "aAbc", StringUtils.ForceStarts("a", "Abc") );
		Assert.assertEquals( "abc",  StringUtils.ForceStarts("a", "abc") );
		Assert.assertEquals( "a",    StringUtils.ForceStarts("a", ""   ) );
		Assert.assertEquals( null,   StringUtils.ForceStarts("a", null ) );
	}
	@Test
	public void testForceEnds() {
		Assert.assertEquals( "abcd", StringUtils.ForceEnds("d", "abc") );
		Assert.assertEquals( "abc",  StringUtils.ForceEnds("c", "abc") );
		Assert.assertEquals( "c",    StringUtils.ForceEnds("c", ""   ) );
		Assert.assertEquals( null,   StringUtils.ForceEnds("c", null ) );
	}



	@Test
	public void testForceUnique() {
		{
			final List<String> list = new ArrayList<String>();
			list.add("a");
			Assert.assertEquals( "a_1", StringUtils.UniqueKey(list, "a") );
			Assert.assertEquals( "b",   StringUtils.UniqueKey(list, "b") );
			Assert.assertArrayEquals(
				new String[] { "a", "a_1", "b" },
				list.toArray(new String[0])
			);
		}
	}



	// ------------------------------------------------------------------------------- //
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



	// ------------------------------------------------------------------------------- //
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



	// ------------------------------------------------------------------------------- //
	// replace within string



	@Test
	public void testReplaceStringRange() {
		Assert.assertEquals( "ab12ef", StringUtils.ReplaceStringRange("abcdef", "12", 2, 4) );
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



	// ------------------------------------------------------------------------------- //
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



	// ------------------------------------------------------------------------------- //
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



	// ------------------------------------------------------------------------------- //
	// replace {} tags



	@Test
	public void testReplaceTags() {
		Assert.assertEquals( "a1b2c",   StringUtils.ReplaceTags("a{}b{}c",   "1", "2"     ) );
		Assert.assertEquals( "a1b2c 3", StringUtils.ReplaceTags("a{}b{}c",   "1", "2", "3") );
		Assert.assertEquals( "a2b1c 3", StringUtils.ReplaceTags("a{2}b{1}c", "1", "2", "3") );
		Assert.assertEquals( "abc 1 2", StringUtils.ReplaceTags("abc",       "1", "2"     ) );
		Assert.assertEquals( "a{}b{}c", StringUtils.ReplaceTags("a{}b{}c"                 ) );
		Assert.assertEquals( "abc",     StringUtils.ReplaceTags("abc"                     ) );
		Assert.assertEquals( "",        StringUtils.ReplaceTags("",          "1", "2", "3") );
		Assert.assertEquals( null,      StringUtils.ReplaceTags((String)null,"1", "2", "3") );
		Assert.assertEquals( "a1b2c",   StringUtils.ReplaceTags("a{}b{}c",   1,   2       ) );
		Assert.assertEquals( "a1b2c 3", StringUtils.ReplaceTags("a{}b{}c",   1,   2,   3  ) );
		Assert.assertEquals( "a2b1c 3", StringUtils.ReplaceTags("a{2}b{1}c", 1,   2,   3  ) );
		Assert.assertEquals( "abc 1 2", StringUtils.ReplaceTags("abc",       1,   2       ) );
		Assert.assertEquals( "",        StringUtils.ReplaceTags("",          1,   2,   3  ) );
		Assert.assertEquals( null,      StringUtils.ReplaceTags((String)null,1,   2,   3  ) );
		Assert.assertEquals( "abc TRUE false", StringUtils.ReplaceTags("abc", true, false ) );
		final HashMap<String, Object> tags = new HashMap<String, Object>();
		tags.put("letters", "abc");
		tags.put("numbers", 123);
		tags.put("boolean", true);
		Assert.assertEquals( "",             StringUtils.ReplaceTags("", tags) );
		Assert.assertEquals( "TRUE 123 abc", StringUtils.ReplaceTags("{boolean} {numbers} {letters}", tags) );
		Assert.assertEquals( "{a}-{b}-{c}",  StringUtils.ReplaceTags("{a}-{b}-{c}", new HashMap<String, Object>()) );
		Assert.assertEquals( "",             StringUtils.ReplaceTags("", new HashMap<String, Object>()) );
		Assert.assertEquals( null,           StringUtils.ReplaceTags((String)null, tags) );
		TestUtils.AssertArrayContains(
			new String[] { "abc", "123", "TRUE" },
			StringUtils.ReplaceTags( new String[] {"{}", "{}", "{}"}, tags )
		);
	}



}
