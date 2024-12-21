package com.poixson.utils;

import static com.poixson.utils.MathUtils.CastBoolean;
import static com.poixson.utils.MathUtils.CastByte;
import static com.poixson.utils.MathUtils.CastDouble;
import static com.poixson.utils.MathUtils.CastFloat;
import static com.poixson.utils.MathUtils.CastInteger;
import static com.poixson.utils.MathUtils.CastLong;
import static com.poixson.utils.MathUtils.CastShort;
import static com.poixson.utils.MathUtils.DistanceAxial;
import static com.poixson.utils.MathUtils.DistanceRadial;
import static com.poixson.utils.MathUtils.EqualsExact;
import static com.poixson.utils.MathUtils.Max;
import static com.poixson.utils.MathUtils.Min;
import static com.poixson.utils.MathUtils.SafeLongToInt;
import static com.poixson.utils.MathUtils.ToBoolean;
import static com.poixson.utils.MathUtils.ToByte;
import static com.poixson.utils.MathUtils.ToDouble;
import static com.poixson.utils.MathUtils.ToFloat;
import static com.poixson.utils.MathUtils.ToInteger;
import static com.poixson.utils.MathUtils.ToLong;
import static com.poixson.utils.MathUtils.ToShort;

//import static com.poixson.utils.MathUtils.CubicInterpolate;
//import static com.poixson.utils.MathUtils.CubicInterpolateCircularValues;
//import static com.poixson.utils.MathUtils.CubicInterpolateLoopedValues;
//import static com.poixson.utils.MathUtils.CubicInterpolateValues;

import java.awt.Color;

import org.junit.Assert;
import org.junit.Test;


public class Test_MathUtils {

	private static final double DELTA = MathUtils.DELTA;



	// -------------------------------------------------------------------------------
	// equals



	@Test
	public void testEqualsExact() {
		// int
		Assert.assertTrue(  EqualsExact( Integer.valueOf(123), Integer.valueOf(123) ) );
		Assert.assertFalse( EqualsExact( Integer.valueOf(123), Integer.valueOf(42)  ) );
		Assert.assertFalse( EqualsExact( Integer.valueOf(123), (Integer) null       ) );
		Assert.assertFalse( EqualsExact( (Integer) null,       Integer.valueOf(123) ) );
		Assert.assertTrue(  EqualsExact( (Integer) null,       (Integer) null       ) );
		// boolean
		Assert.assertTrue(  EqualsExact( Boolean.valueOf(true),  Boolean.valueOf(true)  ) );
		Assert.assertTrue(  EqualsExact( Boolean.valueOf(false), Boolean.valueOf(false) ) );
		Assert.assertFalse( EqualsExact( Boolean.valueOf(true),  Boolean.valueOf(false) ) );
		Assert.assertFalse( EqualsExact( Boolean.valueOf(true),  (Boolean) null         ) );
		Assert.assertFalse( EqualsExact( Boolean.valueOf(false), (Boolean) null         ) );
		Assert.assertFalse( EqualsExact( (Boolean) null,         Boolean.valueOf(true)  ) );
		Assert.assertFalse( EqualsExact( (Boolean) null,         Boolean.valueOf(false) ) );
		Assert.assertTrue(  EqualsExact( (Boolean) null,         (Boolean) null         ) );
		// byte
		Assert.assertTrue(  EqualsExact( Byte.valueOf((byte)123), Byte.valueOf((byte)123) ) );
		Assert.assertFalse( EqualsExact( Byte.valueOf((byte)123), Byte.valueOf((byte)42)  ) );
		Assert.assertFalse( EqualsExact( Byte.valueOf((byte)123), null                    ) );
		Assert.assertFalse( EqualsExact( null,                    Byte.valueOf((byte)123) ) );
		Assert.assertTrue(  EqualsExact( (Byte) null,             (Byte) null             ) );
		// short
		Assert.assertTrue(  EqualsExact( Short.valueOf((short)123), Short.valueOf((short)123) ) );
		Assert.assertFalse( EqualsExact( Short.valueOf((short)123), Short.valueOf((short)42)  ) );
		Assert.assertFalse( EqualsExact( Short.valueOf((short)123), (Short) null              ) );
		Assert.assertFalse( EqualsExact( (Short) null,              Short.valueOf((short)123) ) );
		Assert.assertTrue(  EqualsExact( (Short) null,              (Short) null              ) );
		// long
		Assert.assertTrue(  EqualsExact( Long.valueOf((short)123), Long.valueOf((short)123) ) );
		Assert.assertFalse( EqualsExact( Long.valueOf((short)123), Long.valueOf((short)42)  ) );
		Assert.assertFalse( EqualsExact( Long.valueOf((short)123), (Long) null              ) );
		Assert.assertFalse( EqualsExact( (Long) null,              Long.valueOf((short)123) ) );
		Assert.assertTrue(  EqualsExact( (Long) null,              (Long) null              ) );
		// double
		Assert.assertTrue(  EqualsExact( Double.valueOf(12.3d), Double.valueOf(12.3d) ) );
		Assert.assertFalse( EqualsExact( Double.valueOf(12.3d), Double.valueOf(42.1d) ) );
		Assert.assertFalse( EqualsExact( Double.valueOf(12.3d), (Double) null         ) );
		Assert.assertFalse( EqualsExact( (Double) null,         Double.valueOf(12.3d) ) );
		Assert.assertTrue(  EqualsExact( (Double) null,         (Double) null         ) );
		// float
		Assert.assertTrue(  EqualsExact( Float.valueOf(12.3f), Float.valueOf(12.3f) ) );
		Assert.assertFalse( EqualsExact( Float.valueOf(12.3f), Float.valueOf(42.1f) ) );
		Assert.assertFalse( EqualsExact( Float.valueOf(12.3f), (Float) null         ) );
		Assert.assertFalse( EqualsExact( (Float) null,         Float.valueOf(12.3f) ) );
		Assert.assertTrue(  EqualsExact( (Float) null,         (Float) null         ) );
	}



	// -------------------------------------------------------------------------------
	// convert



	@Test
	public void testSafeLongToInt() {
		Assert.assertEquals( 11, SafeLongToInt(11L) );
		Assert.assertEquals( Integer.MAX_VALUE, SafeLongToInt( ((long)Integer.MAX_VALUE) + 9L ) );
		Assert.assertEquals( Integer.MIN_VALUE, SafeLongToInt( ((long)Integer.MIN_VALUE) - 9L ) );
	}



	@Test
	public void testToNumber() {
		// number
		Assert.assertEquals( Integer.valueOf(123),      ToInteger("123"  ) );
		Assert.assertEquals( Byte.valueOf((byte)123),   ToByte(   "123"  ) );
		Assert.assertEquals( Short.valueOf((short)123), ToShort(  "123"  ) );
		Assert.assertEquals( Long.valueOf(123),         ToLong(   "123"  ) );
		Assert.assertEquals( Double.valueOf(123),       ToDouble( "123"  ) );
		Assert.assertEquals( Float.valueOf(123),        ToFloat(  "123"  ) );
		Assert.assertEquals( Boolean.valueOf(true),     ToBoolean("true" ) );
		Assert.assertEquals( Boolean.valueOf(false),    ToBoolean("false") );
	}
	@Test
	public void testToNumberOrDefault() {
		// int
		Assert.assertTrue( 123 == ToInteger("123", 42) );
		Assert.assertTrue( 42  == ToInteger(null,  42) );
		Assert.assertTrue( 42  == ToInteger("3xy", 42) );
		Assert.assertTrue( 42  == ToInteger("xy3", 42) );
		// byte
		Assert.assertTrue( ((byte)123) == ToByte("123", (byte)42) );
		Assert.assertTrue( ((byte)42)  == ToByte(null,  (byte)42) );
		Assert.assertTrue( ((byte)42)  == ToByte("3xy", (byte)42) );
		Assert.assertTrue( ((byte)42)  == ToByte("xy3", (byte)42) );
		// short
		Assert.assertTrue( ((short)123) == ToShort("123", (short)42) );
		Assert.assertTrue( ((short)42)  == ToShort(null,  (short)42) );
		Assert.assertTrue( ((short)42)  == ToShort("3xy", (short)42) );
		Assert.assertTrue( ((short)42)  == ToShort("xy3", (short)42) );
		// long
		Assert.assertTrue( 123L == ToLong("123", 42L) );
		Assert.assertTrue( 42L  == ToLong(null,  42L) );
		Assert.assertTrue( 42L  == ToLong("3xy", 42L) );
		Assert.assertTrue( 42L  == ToLong("xy3", 42L) );
		// double
		Assert.assertTrue( 12.3d == ToDouble("12.3", 42.1d) );
		Assert.assertTrue( 42.1d == ToDouble(null,   42.1d) );
		Assert.assertTrue( 42.1d == ToDouble("3xy",  42.1d) );
		Assert.assertTrue( 42.1d == ToDouble("xy3",  42.1d) );
		// float
		Assert.assertTrue( 12.3f == ToFloat("12.3",  42.1f) );
		Assert.assertTrue( 42.1f == ToFloat(null,    42.1f) );
		Assert.assertTrue( 42.1f == ToFloat("1.2xy", 42.1f) );
		Assert.assertTrue( 42.1f == ToFloat("xy1.2", 42.1f) );
		// boolean
		Assert.assertEquals( Boolean.valueOf(true),  ToBoolean("true")     );
		Assert.assertEquals( Boolean.valueOf(true),  ToBoolean("en")       );
		Assert.assertEquals( Boolean.valueOf(true),  ToBoolean("enable")   );
		Assert.assertEquals( Boolean.valueOf(true),  ToBoolean("enabled")  );
		Assert.assertEquals( Boolean.valueOf(true),  ToBoolean("yes")      );
		Assert.assertEquals( Boolean.valueOf(true),  ToBoolean("on")       );
		Assert.assertEquals( Boolean.valueOf(false), ToBoolean("false")    );
		Assert.assertEquals( Boolean.valueOf(false), ToBoolean("dis")      );
		Assert.assertEquals( Boolean.valueOf(false), ToBoolean("disable")  );
		Assert.assertEquals( Boolean.valueOf(false), ToBoolean("disabled") );
		Assert.assertEquals( Boolean.valueOf(false), ToBoolean("no")       );
		Assert.assertEquals( Boolean.valueOf(false), ToBoolean("off")      );
		Assert.assertEquals( Boolean.valueOf(true),  ToBoolean("1")        );
		Assert.assertEquals( Boolean.valueOf(true),  ToBoolean("t")        );
		Assert.assertEquals( Boolean.valueOf(true),  ToBoolean("i")        );
		Assert.assertEquals( Boolean.valueOf(true),  ToBoolean("e")        );
		Assert.assertEquals( Boolean.valueOf(true),  ToBoolean("y")        );
		Assert.assertEquals( Boolean.valueOf(false), ToBoolean("0")        );
		Assert.assertEquals( Boolean.valueOf(false), ToBoolean("f")        );
		Assert.assertEquals( Boolean.valueOf(false), ToBoolean("o")        );
		Assert.assertEquals( Boolean.valueOf(false), ToBoolean("d")        );
		Assert.assertEquals( Boolean.valueOf(false), ToBoolean("n")        );
	}
	@Test
	public void testObjectToNumber() {
		Assert.assertEquals( Integer.valueOf(123),       CastInteger( (Object) Integer.valueOf(123)       ) );
		Assert.assertEquals( Byte.valueOf(  (byte)123),  CastByte(    (Object) Byte.valueOf(  (byte)123)  ) );
		Assert.assertEquals( Short.valueOf( (short)123), CastShort(   (Object) Short.valueOf( (short)123) ) );
		Assert.assertEquals( Long.valueOf(  123L),       CastLong(    (Object) Long.valueOf(123L)         ) );
		Assert.assertEquals( Double.valueOf(12.3d),      CastDouble(  (Object) Double.valueOf(12.3d)      ) );
		Assert.assertEquals( Float.valueOf( 12.3f),      CastFloat(   (Object) Float.valueOf(12.3f)       ) );
		Assert.assertEquals( Boolean.TRUE,               CastBoolean( (Object) Boolean.valueOf(true)      ) );
		Assert.assertEquals( Boolean.FALSE,              CastBoolean( (Object) Boolean.valueOf(false)     ) );
	}



	// -------------------------------------------------------------------------------
	// min/max



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
		Assert.assertEquals(   0.0,   DistanceRadial(x1, z1, x1, z1), delta);
		Assert.assertEquals(   1.414, DistanceRadial(x1, z1, x2, z2), delta);
		Assert.assertEquals(   7.071, DistanceRadial(x1, z1, x3, z3), delta);
		Assert.assertEquals(   5.656, DistanceRadial(x2, z2, x3, z3), delta);
		Assert.assertEquals( 120.208, DistanceRadial(x3, z3, x4, z4), delta);
		Assert.assertEquals(1407.001, DistanceRadial(x3, z3, x5, z5), delta);
		Assert.assertEquals(1414.072, DistanceRadial(x1, z1, x5, z5), delta);
		Assert.assertEquals(   1.0,   DistanceRadial(x1, z1, x2, z1), delta);
		Assert.assertEquals(   1.0,   DistanceRadial(x1, z1, x1, z2), delta);
		Assert.assertEquals(   1.414, DistanceRadial(x2, z2, x1, z1), delta);
		// 3d distance
		Assert.assertEquals(   0.0,   DistanceRadial(x1, y1, z1, x1, y1, z1), delta);
		Assert.assertEquals(   1.732, DistanceRadial(x1, y1, z1, x2, y2, z2), delta);
		Assert.assertEquals(   8.66,  DistanceRadial(x1, y1, z1, x3, y3, z3), delta);
		Assert.assertEquals(   6.928, DistanceRadial(x2, y2, z2, x3, y3, z3), delta);
		Assert.assertEquals( 147.224, DistanceRadial(x3, y3, z3, x4, y4, z4), delta);
		Assert.assertEquals(1723.217, DistanceRadial(x3, y3, z3, x5, y5, z5), delta);
		Assert.assertEquals(1731.877, DistanceRadial(x1, y1, z1, x5, y5, z5), delta);
		Assert.assertEquals(   1.0,   DistanceRadial(x1, y1, z1, x2, y1, z1), delta);
		Assert.assertEquals(   1.0,   DistanceRadial(x1, y1, z1, x1, y2, z1), delta);
		Assert.assertEquals(   1.0,   DistanceRadial(x1, y1, z1, x1, y1, z2), delta);
		Assert.assertEquals(   1.0,   DistanceRadial(x1, y1, z1, x1, y2, z1), delta);
		Assert.assertEquals(   1.732, DistanceRadial(x2, y2, z2, x1, y1, z1), delta);
		// 2d fast distance
		Assert.assertEquals(  0.0, DistanceAxial(x1, z1, x1, z1), delta);
		Assert.assertEquals(  1.0, DistanceAxial(x1, z1, x2, z2), delta);
		Assert.assertEquals(  5.0, DistanceAxial(x1, z1, x3, z3), delta);
		Assert.assertEquals(  4.0, DistanceAxial(x2, z2, x3, z3), delta);
		Assert.assertEquals( 85.0, DistanceAxial(x3, z3, x4, z4), delta);
		Assert.assertEquals(994.9, DistanceAxial(x3, z3, x5, z5), delta);
		Assert.assertEquals(999.9, DistanceAxial(x1, z1, x5, z5), delta);
		Assert.assertEquals(  1.0, DistanceAxial(x1, z1, x2, z1), delta);
		Assert.assertEquals(  1.0, DistanceAxial(x1, z1, x1, z2), delta);
		Assert.assertEquals(  1.0, DistanceAxial(x2, z2, x1, z1), delta);
		// 3d fast distance
		Assert.assertEquals(  0.0, DistanceAxial(x1, y1, z1, x1, y1, z1), delta);
		Assert.assertEquals(  1.0, DistanceAxial(x1, y1, z1, x2, y2, z2), delta);
		Assert.assertEquals(  5.0, DistanceAxial(x1, y1, z1, x3, y3, z3), delta);
		Assert.assertEquals(  4.0, DistanceAxial(x2, y2, z2, x3, y3, z3), delta);
		Assert.assertEquals( 85.0, DistanceAxial(x3, y3, z3, x4, y4, z4), delta);
		Assert.assertEquals(994.9, DistanceAxial(x3, y3, z3, x5, y5, z5), delta);
		Assert.assertEquals(999.9, DistanceAxial(x1, y1, z1, x5, y5, z5), delta);
		Assert.assertEquals(  1.0, DistanceAxial(x1, y1, z1, x2, y1, z1), delta);
		Assert.assertEquals(  1.0, DistanceAxial(x1, y1, z1, x1, y2, z1), delta);
		Assert.assertEquals(  1.0, DistanceAxial(x1, y1, z1, x1, y1, z2), delta);
		Assert.assertEquals(  1.0, DistanceAxial(x1, y1, z1, x1, y2, z1), delta);
		Assert.assertEquals(  1.0, DistanceAxial(x2, y2, z2, x1, y1, z1), delta);
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
		Assert.assertEquals(  0,   MathUtils.Remap(0,   10,   0.0) );
		Assert.assertEquals( 10,   MathUtils.Remap(0,   10,   1.0) );
		Assert.assertEquals(  5,   MathUtils.Remap(0,   10,   0.5) );
		Assert.assertEquals(  5,   MathUtils.Remap(0,    9,   0.5) );
		Assert.assertEquals(  0L,  MathUtils.Remap(0L,  10L,  0.0) );
		Assert.assertEquals( 10L,  MathUtils.Remap(0L,  10L,  1.0) );
		Assert.assertEquals(  5L,  MathUtils.Remap(0L,   9L,  0.5) );
		Assert.assertEquals(  0.0, MathUtils.Remap(0.0, 10.0, 0.0), DELTA);
		Assert.assertEquals( 10.0, MathUtils.Remap(0.0, 10.0, 1.0), DELTA);
		Assert.assertEquals(  4.5, MathUtils.Remap(0.0,  9.0, 0.5), DELTA);
		// remap percent weighted
		Assert.assertEquals( 0.1,     MathUtils.Remap(0.1,  0.1,  0.5, 0.1), DELTA);
		Assert.assertEquals( 2.5,     MathUtils.Remap(0.0, 10.0,  0.5, 0.5), DELTA);
		Assert.assertEquals( 5.0,     MathUtils.Remap(0.0, 10.0,  0.5, 1.0), DELTA);
		Assert.assertEquals( 0.03125, MathUtils.Remap(0.0, 32.0,  0.5, 0.1), DELTA);
		Assert.assertEquals( 0.312,   MathUtils.Remap(0.31, 6.56, 0.2, 0.2), DELTA);
		Assert.assertEquals( 0.712,   MathUtils.Remap(0.71, 6.96, 0.2, 0.2), DELTA);
		Assert.assertEquals( 0.912,   MathUtils.Remap(0.91, 7.16, 0.2, 0.2), DELTA);
		Assert.assertEquals( 1.012,   MathUtils.Remap(1.01, 7.26, 0.2, 0.2), DELTA);
		Assert.assertEquals( 6.958,   MathUtils.Remap(6.96, 0.71, 0.2, 0.2), DELTA);
		Assert.assertEquals( 7.058,   MathUtils.Remap(7.06, 0.81, 0.2, 0.2), DELTA);
		Assert.assertEquals( 7.258,   MathUtils.Remap(7.26, 1.01, 0.2, 0.2), DELTA);
		// remap range
		Assert.assertEquals( 25,   MathUtils.Remap(1,   10,   21,   30,    5  ) );
		Assert.assertEquals( 30,   MathUtils.Remap(1,   10,   21,   30,   10  ) );
		Assert.assertEquals( 25L,  MathUtils.Remap(1L,  10L,  21L,  30L,   5L ) );
		Assert.assertEquals( 30L,  MathUtils.Remap(1L,  10L,  21L,  30L,  10L ) );
		Assert.assertEquals( 25.0, MathUtils.Remap(1.0, 10.0, 21.0, 30.0,  5.0), DELTA);
		Assert.assertEquals( 30.0, MathUtils.Remap(1.0, 10.0, 21.0, 30.0, 10.0), DELTA);
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
