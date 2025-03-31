/*
package com.poixson.utils;

import static com.poixson.tools.Assertions.AssertArrayEquals;
import static com.poixson.tools.Assertions.AssertEquals;
import static com.poixson.tools.Assertions.AssertFalse;
import static com.poixson.tools.Assertions.AssertNull;
import static com.poixson.tools.Assertions.AssertTrue;
import static com.poixson.utils.ArrayUtils.MatchMaps;
import static com.poixson.utils.MathUtils.DELTA;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.poixson.tools.Assertions;


@ExtendWith(Assertions.class)
public class Test_ArrayUtils {



	@Test
	public void testArrayUtils() {
		// EmptyArray()
		AssertArrayEquals(new String[0],               ArrayUtils.EmptyArray(String.class));
		// NewArray()
		AssertArrayEquals(new String[0],               ArrayUtils.NewArray(0, String.class));
		AssertArrayEquals(new String[] { null       }, ArrayUtils.NewArray(1, String.class));
		AssertArrayEquals(new String[] { null, null }, ArrayUtils.NewArray(2, String.class));
		// SafeArray()
		AssertArrayEquals(new String[0],               ArrayUtils.SafeArray(null,                   String.class));
		AssertArrayEquals(new String[0],               ArrayUtils.SafeArray(new String[0],          String.class));
		AssertArrayEquals(new String[] { "Abc" },      ArrayUtils.SafeArray(new String[] { "Abc" }, String.class));
		// NullNormArray()
		AssertNull(                                    ArrayUtils.NullNormArray(null                  ));
		AssertNull(                                    ArrayUtils.NullNormArray(new String[0]         ));
		AssertArrayEquals(new String[] { "Abc" },      ArrayUtils.NullNormArray(new String[] { "Abc" }));
	}



	@Test
	public void testDropFirst() {
		AssertArrayEquals(new String[0],                 ArrayUtils.DropFirst(new String[0]                       ));
		AssertArrayEquals(new String[0],                 ArrayUtils.DropFirst(new String[] { "Abc"               }));
		AssertArrayEquals(new String[] { "def"        }, ArrayUtils.DropFirst(new String[] { "Abc", "def"        }));
		AssertArrayEquals(new String[] { "def", "ghi" }, ArrayUtils.DropFirst(new String[] { "Abc", "def", "ghi" }));
	}



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
		AssertEquals( 0.0, ArrayUtils.GetSafeOrZero(array,-2), DELTA);
		AssertEquals( 0.0, ArrayUtils.GetSafeOrZero(array,-1), DELTA);
		AssertEquals(-1.1, ArrayUtils.GetSafeOrZero(array, 0), DELTA);
		AssertEquals( 0.0, ArrayUtils.GetSafeOrZero(array, 1), DELTA);
		AssertEquals( 3.3, ArrayUtils.GetSafeOrZero(array, 4), DELTA);
		AssertEquals( 0.0, ArrayUtils.GetSafeOrZero(array, 5), DELTA);
		AssertEquals( 0.0, ArrayUtils.GetSafeOrZero(array, 6), DELTA);
		// GetSafeOrNear
		AssertEquals(-1.1, ArrayUtils.GetSafeOrNear(array,-2), DELTA);
		AssertEquals(-1.1, ArrayUtils.GetSafeOrNear(array,-1), DELTA);
		AssertEquals(-1.1, ArrayUtils.GetSafeOrNear(array, 0), DELTA);
		AssertEquals( 0.0, ArrayUtils.GetSafeOrNear(array, 1), DELTA);
		AssertEquals( 3.3, ArrayUtils.GetSafeOrNear(array, 4), DELTA);
		AssertEquals( 3.3, ArrayUtils.GetSafeOrNear(array, 5), DELTA);
		AssertEquals( 3.3, ArrayUtils.GetSafeOrNear(array, 6), DELTA);
		// SafeGetLooped
		AssertEquals( 2.2, ArrayUtils.GetSafeLooped(array,-2), DELTA);
		AssertEquals( 3.3, ArrayUtils.GetSafeLooped(array,-1), DELTA);
		AssertEquals(-1.1, ArrayUtils.GetSafeLooped(array, 0), DELTA);
		AssertEquals( 0.0, ArrayUtils.GetSafeLooped(array, 1), DELTA);
		AssertEquals( 3.3, ArrayUtils.GetSafeLooped(array, 4), DELTA);
		AssertEquals(-1.1, ArrayUtils.GetSafeLooped(array, 5), DELTA);
		AssertEquals( 0.0, ArrayUtils.GetSafeLooped(array, 6), DELTA);
		// SafeGetLoopExtend
		AssertEquals( 0.0, ArrayUtils.GetSafeLoopExtend(array,-2), DELTA);
		AssertEquals( 1.1, ArrayUtils.GetSafeLoopExtend(array,-1), DELTA);
		AssertEquals(-1.1, ArrayUtils.GetSafeLoopExtend(array, 0), DELTA);
		AssertEquals( 0.0, ArrayUtils.GetSafeLoopExtend(array, 1), DELTA);
		AssertEquals( 3.3, ArrayUtils.GetSafeLoopExtend(array, 4), DELTA);
		AssertEquals( 2.2, ArrayUtils.GetSafeLoopExtend(array, 5), DELTA);
		AssertEquals(-1.1, ArrayUtils.GetSafeLoopExtend(array, 6), DELTA);
	}



	@Test
	public void testArrayToMap() {
		final String[] arrayA = new String[] {
			"abc", "123",
			"def", "456",
			"ghi", "789",
		};
		final Map<String, String> mapA = new HashMap<String, String>();
		mapA.put("abc", "123");
		mapA.put("def", "456");
		mapA.put("ghi", "789");
		AssertTrue(MatchMaps(mapA, ArrayUtils.ssArrayToMap(arrayA)));
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
		AssertTrue (MatchMaps(mapA, mapB));
		AssertFalse(MatchMaps(mapA, mapC));
		AssertFalse(MatchMaps(mapA, mapD));
		AssertFalse(MatchMaps(mapA, mapE));
		AssertFalse(MatchMaps(mapA, mapF));
	}



}
*/
