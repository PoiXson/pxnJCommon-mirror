package com.poixson.utils;

import static com.poixson.utils.MathUtils.DELTA;

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



}
