package com.poixson.tools.abstractions;

import org.junit.Assert;
import org.junit.Test;


public class Test_HashMapTimeout {



	@Test
	public void testTimeout() {
		final HashMapTimeout<String, String> map = new HashMapTimeout<String, String>();
		map.setTimeoutTicks(5L);
		map.put("abc", "def");
		for (int i=0; i<10; i++) {
			if (i == 2) map.put("ghi", "jkl");
			if (i < 2) Assert.assertEquals("Index: "+Integer.toString(i), 1, map.size()); else
			if (i < 6) Assert.assertEquals("Index: "+Integer.toString(i), 2, map.size()); else
			if (i < 8) Assert.assertEquals("Index: "+Integer.toString(i), 1, map.size()); else
				Assert.assertEquals(0, map.size());
			map.run();
		}
		Assert.assertTrue(map.isEmpty());
	}



}
