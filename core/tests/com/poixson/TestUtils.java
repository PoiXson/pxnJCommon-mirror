package com.poixson;

import static com.poixson.utils.Utils.IsEmpty;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import com.poixson.utils.StringUtils;


public final class TestUtils {
	private TestUtils() {}



	public static void AssertArrayContains(final Object[] expect, final Object[] actual) {
		if (expect == null && actual == null) return;
		if (expect == null) { Assert.fail(StringUtils.ToString(actual)); return; }
		if (actual == null) { Assert.fail(StringUtils.ToString(expect)); return; }
		final List<Object> actualList = new ArrayList<Object>();
		for (int index=0; index<actual.length; index++)
			actualList.add(actual[index]);
		final List<Object> foundList = new ArrayList<Object>();
		for (int index=0; index<expect.length; index++) {
			final Object obj = expect[index];
			if (!actualList.contains(obj)) {
				Assert.fail("Entry not in actual array: "+StringUtils.ToString(obj));
				return;
			}
			foundList.add(obj);
		}
		for (final Object obj : foundList)
			actualList.remove(obj);
		if (!IsEmpty(actualList))
			Assert.fail("Entries not in expected array: "+StringUtils.MergeObjects(", ", actualList));
	}



}
