package com.poixson.utils;

import static com.poixson.utils.MathUtils.Min;
import static com.poixson.utils.MathUtils.Max;
import static com.poixson.utils.MathUtils.Distance2D;
import static com.poixson.utils.MathUtils.Distance3D;
import static com.poixson.utils.MathUtils.DistanceFast2D;
import static com.poixson.utils.MathUtils.DistanceFast3D;

//import static com.poixson.utils.MathUtils.CubicInterpolate;
//import static com.poixson.utils.MathUtils.CubicInterpolateCircularValues;
//import static com.poixson.utils.MathUtils.CubicInterpolateLoopedValues;
//import static com.poixson.utils.MathUtils.CubicInterpolateValues;

import java.awt.Color;

import org.junit.Assert;
import org.junit.Test;


public class Test_MathUtils {

//	private static final double DELTA = NumberUtils.DELTA;



	@Test
	public void testMinMax() {
		Assert.assertEquals( 1, Min( 1, 2, 3      ));
		Assert.assertEquals(-5, Min(-5, 0, 5      ));
		Assert.assertEquals( 5, Min( 9, 8, 7, 5, 6));
		Assert.assertEquals( 3, Max( 1, 2, 3      ));
		Assert.assertEquals( 5, Max(-5, 0, 5      ));
		Assert.assertEquals( 9, Max( 9, 8, 7, 5, 6));
	}



	@Test
	public void testDistance() {
		final double delta = 0.001;
		final double x1 =   0.0; final double y1 = x1; final double z1 = x1;
		final double x2 =   1.0; final double y2 = x2; final double z2 = x2;
		final double x3 =   5.0; final double y3 = x3; final double z3 = x3;
		final double x4 =  90.0; final double y4 = x4; final double z4 = x4;
		final double x5 = 999.9; final double y5 = x5; final double z5 = x5;
		// 2d distance
		Assert.assertEquals(   0.0,   Distance2D(x1, z1, x1, z1), delta);
		Assert.assertEquals(   1.414, Distance2D(x1, z1, x2, z2), delta);
		Assert.assertEquals(   7.071, Distance2D(x1, z1, x3, z3), delta);
		Assert.assertEquals(   5.656, Distance2D(x2, z2, x3, z3), delta);
		Assert.assertEquals( 120.208, Distance2D(x3, z3, x4, z4), delta);
		Assert.assertEquals(1407.001, Distance2D(x3, z3, x5, z5), delta);
		Assert.assertEquals(1414.072, Distance2D(x1, z1, x5, z5), delta);
		Assert.assertEquals(   1.0,   Distance2D(x1, z1, x2, z1), delta);
		Assert.assertEquals(   1.0,   Distance2D(x1, z1, x1, z2), delta);
		Assert.assertEquals(   1.414, Distance2D(x2, z2, x1, z1), delta);
		// 3d distance
		Assert.assertEquals(   0.0,   Distance3D(x1, y1, z1, x1, y1, z1), delta);
		Assert.assertEquals(   1.732, Distance3D(x1, y1, z1, x2, y2, z2), delta);
		Assert.assertEquals(   8.66,  Distance3D(x1, y1, z1, x3, y3, z3), delta);
		Assert.assertEquals(   6.928, Distance3D(x2, y2, z2, x3, y3, z3), delta);
		Assert.assertEquals( 147.224, Distance3D(x3, y3, z3, x4, y4, z4), delta);
		Assert.assertEquals(1723.217, Distance3D(x3, y3, z3, x5, y5, z5), delta);
		Assert.assertEquals(1731.877, Distance3D(x1, y1, z1, x5, y5, z5), delta);
		Assert.assertEquals(   1.0,   Distance3D(x1, y1, z1, x2, y1, z1), delta);
		Assert.assertEquals(   1.0,   Distance3D(x1, y1, z1, x1, y2, z1), delta);
		Assert.assertEquals(   1.0,   Distance3D(x1, y1, z1, x1, y1, z2), delta);
		Assert.assertEquals(   1.0,   Distance3D(x1, y1, z1, x1, y2, z1), delta);
		Assert.assertEquals(   1.732, Distance3D(x2, y2, z2, x1, y1, z1), delta);
		// 2d fast distance
		Assert.assertEquals(  0.0, DistanceFast2D(x1, z1, x1, z1), delta);
		Assert.assertEquals(  1.0, DistanceFast2D(x1, z1, x2, z2), delta);
		Assert.assertEquals(  5.0, DistanceFast2D(x1, z1, x3, z3), delta);
		Assert.assertEquals(  4.0, DistanceFast2D(x2, z2, x3, z3), delta);
		Assert.assertEquals( 85.0, DistanceFast2D(x3, z3, x4, z4), delta);
		Assert.assertEquals(994.9, DistanceFast2D(x3, z3, x5, z5), delta);
		Assert.assertEquals(999.9, DistanceFast2D(x1, z1, x5, z5), delta);
		Assert.assertEquals(  1.0, DistanceFast2D(x1, z1, x2, z1), delta);
		Assert.assertEquals(  1.0, DistanceFast2D(x1, z1, x1, z2), delta);
		Assert.assertEquals(  1.0, DistanceFast2D(x2, z2, x1, z1), delta);
		// 3d fast distance
		Assert.assertEquals(  0.0, DistanceFast3D(x1, y1, z1, x1, y1, z1), delta);
		Assert.assertEquals(  1.0, DistanceFast3D(x1, y1, z1, x2, y2, z2), delta);
		Assert.assertEquals(  5.0, DistanceFast3D(x1, y1, z1, x3, y3, z3), delta);
		Assert.assertEquals(  4.0, DistanceFast3D(x2, y2, z2, x3, y3, z3), delta);
		Assert.assertEquals( 85.0, DistanceFast3D(x3, y3, z3, x4, y4, z4), delta);
		Assert.assertEquals(994.9, DistanceFast3D(x3, y3, z3, x5, y5, z5), delta);
		Assert.assertEquals(999.9, DistanceFast3D(x1, y1, z1, x5, y5, z5), delta);
		Assert.assertEquals(  1.0, DistanceFast3D(x1, y1, z1, x2, y1, z1), delta);
		Assert.assertEquals(  1.0, DistanceFast3D(x1, y1, z1, x1, y2, z1), delta);
		Assert.assertEquals(  1.0, DistanceFast3D(x1, y1, z1, x1, y1, z2), delta);
		Assert.assertEquals(  1.0, DistanceFast3D(x1, y1, z1, x1, y2, z1), delta);
		Assert.assertEquals(  1.0, DistanceFast3D(x2, y2, z2, x1, y1, z1), delta);
	}



/*
	@Test
	public void testCeilDiv() {
		Assert.assertEquals(4, MathUtils.ceilDiv(10, 3));
		Assert.assertEquals(2, MathUtils.ceilDiv( 3, 2));
	}
*/



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



/*
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
*/



}
