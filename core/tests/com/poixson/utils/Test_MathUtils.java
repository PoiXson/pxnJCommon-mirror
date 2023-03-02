package com.poixson.tests.utils;

import static com.poixson.utils.MathUtils.CubicInterpolate;
import static com.poixson.utils.MathUtils.CubicInterpolateValues;
import static com.poixson.utils.MathUtils.CubicInterpolateLoopedValues;
import static com.poixson.utils.MathUtils.CubicInterpolateCircularValues;

import java.awt.Color;

import org.junit.Assert;
import org.junit.Test;

import com.poixson.utils.MathUtils;
import com.poixson.utils.NumberUtils;


public class Test_MathUtils {

	private static final double DELTA = NumberUtils.DELTA;



	@Test
	public void testRemap() {
		// remap percent
		Assert.assertEquals(  0, MathUtils.Remap(0, 10, 0.0) );
		Assert.assertEquals( 10, MathUtils.Remap(0, 10, 1.0) );
		Assert.assertEquals(  5, MathUtils.Remap(1, 10, 0.5) );
		// remap range
		Assert.assertEquals( 25, MathUtils.Remap(1, 10, 21, 30,  5) );
		Assert.assertEquals( 30, MathUtils.Remap(1, 10, 21, 30, 10) );
		// remap 8 bit color
		Assert.assertEquals( Color.BLACK, MathUtils.Remap8BitColor(0B00000000) );
		Assert.assertEquals( Color.WHITE, MathUtils.Remap8BitColor(0B11111111) );
		Assert.assertEquals( Color.RED,   MathUtils.Remap8BitColor(0B00000111) );
		Assert.assertEquals( Color.GREEN, MathUtils.Remap8BitColor(0B00111000) );
		Assert.assertEquals( Color.BLUE,  MathUtils.Remap8BitColor(0B11000000) );
		Assert.assertEquals( 0B00000111,   7);
		Assert.assertEquals( 0B00111000,  56);
		Assert.assertEquals( 0B11000000, 192);
	}



	@Test
	public void testCubicInterpolate() {
		final double a = 1.0;
		final double b = 0.0;
		final double c = 2.0;
		final double d = 3.0;
		Assert.assertEquals(0.0,  CubicInterpolate( 0.0, a, b, c, d), DELTA);
		Assert.assertEquals(0.75, CubicInterpolate( 0.5, a, b, c, d), DELTA);
		Assert.assertEquals(2.0,  CubicInterpolate( 1.0, a, b, c, d), DELTA);
		Assert.assertEquals(0.0,  CubicInterpolate(-1.0, a, b, c, d), DELTA);
		Assert.assertEquals(6.0,  CubicInterpolate( 2.0, a, b, c, d), DELTA);
	}
	@Test
	public void testCubicInterpolateValues() {
		final double[] values = {
			1.0,
			0.0,
			2.0,
			3.0,
		};
//TODO: these are probably not right
		// CubicInterpolateValues
		Assert.assertEquals(1.0,  CubicInterpolateValues(values, 0, 0.0), DELTA);
		Assert.assertEquals(0.25, CubicInterpolateValues(values, 0, 0.5), DELTA);
		Assert.assertEquals(0.0,  CubicInterpolateValues(values, 0, 1.0), DELTA);
		Assert.assertEquals(0.0,  CubicInterpolateValues(values, 1, 0.0), DELTA);
		Assert.assertEquals(0.75, CubicInterpolateValues(values, 1, 0.5), DELTA);
		Assert.assertEquals(2.0,  CubicInterpolateValues(values, 1, 1.0), DELTA);
		Assert.assertEquals(1.0,  CubicInterpolateValues(values,-1, 1.0), DELTA);
		Assert.assertEquals(3.0,  CubicInterpolateValues(values, 4, 1.0), DELTA);
		// CubicInterpolateLoopedValues
		Assert.assertEquals(1.0,  CubicInterpolateLoopedValues(values, 0, 0.0), DELTA);
		Assert.assertEquals(0.0,  CubicInterpolateLoopedValues(values, 0, 0.5), DELTA);
		Assert.assertEquals(0.0,  CubicInterpolateLoopedValues(values, 0, 1.0), DELTA);
		Assert.assertEquals(0.0,  CubicInterpolateLoopedValues(values, 1, 0.0), DELTA);
		Assert.assertEquals(0.75, CubicInterpolateLoopedValues(values, 1, 0.5), DELTA);
		Assert.assertEquals(2.0,  CubicInterpolateLoopedValues(values, 1, 1.0), DELTA);
		Assert.assertEquals(1.0,  CubicInterpolateLoopedValues(values,-1, 1.0), DELTA);
		Assert.assertEquals(0.0,  CubicInterpolateLoopedValues(values, 4, 1.0), DELTA);
		// CubicInterpolateCircularValues
		Assert.assertEquals(1.0,  CubicInterpolateCircularValues(values, 0, 0.0), DELTA);
		Assert.assertEquals(0.0,  CubicInterpolateCircularValues(values, 0, 0.5), DELTA);
		Assert.assertEquals(0.0,  CubicInterpolateCircularValues(values, 0, 1.0), DELTA);
		Assert.assertEquals(0.0,  CubicInterpolateCircularValues(values, 1, 0.0), DELTA);
		Assert.assertEquals(0.75, CubicInterpolateCircularValues(values, 1, 0.5), DELTA);
		Assert.assertEquals(2.0,  CubicInterpolateCircularValues(values, 1, 1.0), DELTA);
		Assert.assertEquals(1.0,  CubicInterpolateCircularValues(values,-1, 1.0), DELTA);
		Assert.assertEquals(1.0,  CubicInterpolateCircularValues(values, 4, 1.0), DELTA);
	}



}
