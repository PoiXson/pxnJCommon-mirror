package com.poixson.utils;

import static com.poixson.utils.ArrayUtils.MatchMaps;
import static com.poixson.utils.MathUtils.DELTA;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;


public class Test_ArrayUtils {



	@Test
	public void testGetSafe() {
		final double[] array = {
			-1.1,
			0.0,
			1.1,
			2.2,
			3.3,
		};
		// SafeGetOrZero
		Assert.assertEquals( 0.0, ArrayUtils.GetSafeOrZero(array,-2), DELTA);
		Assert.assertEquals( 0.0, ArrayUtils.GetSafeOrZero(array,-1), DELTA);
		Assert.assertEquals(-1.1, ArrayUtils.GetSafeOrZero(array, 0), DELTA);
		Assert.assertEquals( 0.0, ArrayUtils.GetSafeOrZero(array, 1), DELTA);
		Assert.assertEquals( 3.3, ArrayUtils.GetSafeOrZero(array, 4), DELTA);
		Assert.assertEquals( 0.0, ArrayUtils.GetSafeOrZero(array, 5), DELTA);
		Assert.assertEquals( 0.0, ArrayUtils.GetSafeOrZero(array, 6), DELTA);
		// GetSafeOrNear
		Assert.assertEquals(-1.1, ArrayUtils.GetSafeOrNear(array,-2), DELTA);
		Assert.assertEquals(-1.1, ArrayUtils.GetSafeOrNear(array,-1), DELTA);
		Assert.assertEquals(-1.1, ArrayUtils.GetSafeOrNear(array, 0), DELTA);
		Assert.assertEquals( 0.0, ArrayUtils.GetSafeOrNear(array, 1), DELTA);
		Assert.assertEquals( 3.3, ArrayUtils.GetSafeOrNear(array, 4), DELTA);
		Assert.assertEquals( 3.3, ArrayUtils.GetSafeOrNear(array, 5), DELTA);
		Assert.assertEquals( 3.3, ArrayUtils.GetSafeOrNear(array, 6), DELTA);
		// SafeGetLooped
		Assert.assertEquals( 2.2, ArrayUtils.GetSafeLooped(array,-2), DELTA);
		Assert.assertEquals( 3.3, ArrayUtils.GetSafeLooped(array,-1), DELTA);
		Assert.assertEquals(-1.1, ArrayUtils.GetSafeLooped(array, 0), DELTA);
		Assert.assertEquals( 0.0, ArrayUtils.GetSafeLooped(array, 1), DELTA);
		Assert.assertEquals( 3.3, ArrayUtils.GetSafeLooped(array, 4), DELTA);
		Assert.assertEquals(-1.1, ArrayUtils.GetSafeLooped(array, 5), DELTA);
		Assert.assertEquals( 0.0, ArrayUtils.GetSafeLooped(array, 6), DELTA);
		// SafeGetLoopExtend
		Assert.assertEquals( 0.0, ArrayUtils.GetSafeLoopExtend(array,-2), DELTA);
		Assert.assertEquals( 1.1, ArrayUtils.GetSafeLoopExtend(array,-1), DELTA);
		Assert.assertEquals(-1.1, ArrayUtils.GetSafeLoopExtend(array, 0), DELTA);
		Assert.assertEquals( 0.0, ArrayUtils.GetSafeLoopExtend(array, 1), DELTA);
		Assert.assertEquals( 3.3, ArrayUtils.GetSafeLoopExtend(array, 4), DELTA);
		Assert.assertEquals( 2.2, ArrayUtils.GetSafeLoopExtend(array, 5), DELTA);
		Assert.assertEquals(-1.1, ArrayUtils.GetSafeLoopExtend(array, 6), DELTA);
	}



	@Test
	public void testMatchMaps() {
		final Map<String, String> mapA = new HashMap<String, String>();
		final Map<String, String> mapB = new HashMap<String, String>();
		final Map<String, String> mapC = new HashMap<String, String>();
		final Map<String, String> mapD = new HashMap<String, String>();
		final Map<String, String> mapE = new HashMap<String, String>();
		final Map<String, String> mapF = new HashMap<String, String>();
		mapA.put("abc", "123"); mapA.put("def", "456"); mapA.put("ghi", "789");
		mapB.put("abc", "123"); mapB.put("def", "456"); mapB.put("ghi", "789");
		mapC.put("abc", "123");                         mapC.put("ghi", "789");
		mapD.put("abc", "123"); mapD.put("def", "555"); mapD.put("ghi", "789");
		mapE.put("abc", "123");                         mapE.put("ghi", "789"); mapE.put("eee", "456");
		mapF.put("abc", "123"); mapF.put("def", "456"); mapF.put("ghi", "789"); mapF.put("jkl", "147");
		Assert.assertTrue (MatchMaps(mapA, mapB));
		Assert.assertFalse(MatchMaps(mapA, mapC));
		Assert.assertFalse(MatchMaps(mapA, mapD));
		Assert.assertFalse(MatchMaps(mapA, mapE));
		Assert.assertFalse(MatchMaps(mapA, mapF));
	}



}
