package com.poixson.utils;

import static com.poixson.tools.Assertions.AssertEquals;
import static com.poixson.tools.Assertions.AssertFalse;
import static com.poixson.tools.Assertions.AssertTrue;
import static com.poixson.utils.MathUtils.CastBoolean;
import static com.poixson.utils.MathUtils.CastByte;
import static com.poixson.utils.MathUtils.CastDouble;
import static com.poixson.utils.MathUtils.CastFloat;
import static com.poixson.utils.MathUtils.CastInteger;
import static com.poixson.utils.MathUtils.CastLong;
import static com.poixson.utils.MathUtils.CastShort;
import static com.poixson.utils.MathUtils.DistanceAxial;
import static com.poixson.utils.MathUtils.DistanceHybrid2D;
import static com.poixson.utils.MathUtils.DistanceHybrid3D;
import static com.poixson.utils.MathUtils.DistanceLinear;
import static com.poixson.utils.MathUtils.DistanceRadial;
import static com.poixson.utils.MathUtils.DistanceVectorial;
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

import java.awt.Color;

import org.junit.jupiter.api.Test;


public class Test_MathUtils {

	private static final double DELTA = MathUtils.DELTA;



	// -------------------------------------------------------------------------------
	// equals



	@Test
	public void testEqualsExact() {
		// int
		AssertTrue(  EqualsExact( Integer.valueOf(123), Integer.valueOf(123) ) );
		AssertFalse( EqualsExact( Integer.valueOf(123), Integer.valueOf(42)  ) );
		AssertFalse( EqualsExact( Integer.valueOf(123), (Integer) null       ) );
		AssertFalse( EqualsExact( (Integer) null,       Integer.valueOf(123) ) );
		AssertTrue(  EqualsExact( (Integer) null,       (Integer) null       ) );
		// boolean
		AssertTrue(  EqualsExact( Boolean.valueOf(true),  Boolean.valueOf(true)  ) );
		AssertTrue(  EqualsExact( Boolean.valueOf(false), Boolean.valueOf(false) ) );
		AssertFalse( EqualsExact( Boolean.valueOf(true),  Boolean.valueOf(false) ) );
		AssertFalse( EqualsExact( Boolean.valueOf(true),  (Boolean) null         ) );
		AssertFalse( EqualsExact( Boolean.valueOf(false), (Boolean) null         ) );
		AssertFalse( EqualsExact( (Boolean) null,         Boolean.valueOf(true)  ) );
		AssertFalse( EqualsExact( (Boolean) null,         Boolean.valueOf(false) ) );
		AssertTrue(  EqualsExact( (Boolean) null,         (Boolean) null         ) );
		// byte
		AssertTrue(  EqualsExact( Byte.valueOf((byte)123), Byte.valueOf((byte)123) ) );
		AssertFalse( EqualsExact( Byte.valueOf((byte)123), Byte.valueOf((byte)42)  ) );
		AssertFalse( EqualsExact( Byte.valueOf((byte)123), null                    ) );
		AssertFalse( EqualsExact( null,                    Byte.valueOf((byte)123) ) );
		AssertTrue(  EqualsExact( (Byte) null,             (Byte) null             ) );
		// short
		AssertTrue(  EqualsExact( Short.valueOf((short)123), Short.valueOf((short)123) ) );
		AssertFalse( EqualsExact( Short.valueOf((short)123), Short.valueOf((short)42)  ) );
		AssertFalse( EqualsExact( Short.valueOf((short)123), (Short) null              ) );
		AssertFalse( EqualsExact( (Short) null,              Short.valueOf((short)123) ) );
		AssertTrue(  EqualsExact( (Short) null,              (Short) null              ) );
		// long
		AssertTrue(  EqualsExact( Long.valueOf((short)123), Long.valueOf((short)123) ) );
		AssertFalse( EqualsExact( Long.valueOf((short)123), Long.valueOf((short)42)  ) );
		AssertFalse( EqualsExact( Long.valueOf((short)123), (Long) null              ) );
		AssertFalse( EqualsExact( (Long) null,              Long.valueOf((short)123) ) );
		AssertTrue(  EqualsExact( (Long) null,              (Long) null              ) );
		// double
		AssertTrue(  EqualsExact( Double.valueOf(12.3d), Double.valueOf(12.3d) ) );
		AssertFalse( EqualsExact( Double.valueOf(12.3d), Double.valueOf(42.1d) ) );
		AssertFalse( EqualsExact( Double.valueOf(12.3d), (Double) null         ) );
		AssertFalse( EqualsExact( (Double) null,         Double.valueOf(12.3d) ) );
		AssertTrue(  EqualsExact( (Double) null,         (Double) null         ) );
		// float
		AssertTrue(  EqualsExact( Float.valueOf(12.3f), Float.valueOf(12.3f) ) );
		AssertFalse( EqualsExact( Float.valueOf(12.3f), Float.valueOf(42.1f) ) );
		AssertFalse( EqualsExact( Float.valueOf(12.3f), (Float) null         ) );
		AssertFalse( EqualsExact( (Float) null,         Float.valueOf(12.3f) ) );
		AssertTrue(  EqualsExact( (Float) null,         (Float) null         ) );
	}



	// -------------------------------------------------------------------------------
	// convert



	@Test
	public void testSafeLongToInt() {
		AssertEquals( 11, SafeLongToInt(11L) );
		AssertEquals( Integer.MAX_VALUE, SafeLongToInt( ((long)Integer.MAX_VALUE) + 9L ) );
		AssertEquals( Integer.MIN_VALUE, SafeLongToInt( ((long)Integer.MIN_VALUE) - 9L ) );
	}



	@Test
	public void testToNumber() {
		// number
		AssertEquals( Integer.valueOf(123),      ToInteger("123"  ) );
		AssertEquals( Byte.valueOf((byte)123),   ToByte(   "123"  ) );
		AssertEquals( Short.valueOf((short)123), ToShort(  "123"  ) );
		AssertEquals( Long.valueOf(123),         ToLong(   "123"  ) );
		AssertEquals( Double.valueOf(123),       ToDouble( "123"  ) );
		AssertEquals( Float.valueOf(123),        ToFloat(  "123"  ) );
		AssertEquals( Boolean.valueOf(true),     ToBoolean("true" ) );
		AssertEquals( Boolean.valueOf(false),    ToBoolean("false") );
	}
	@Test
	public void testToNumberOrDefault() {
		// int
		AssertTrue( 123 == ToInteger("123", 42) );
		AssertTrue( 42  == ToInteger(null,  42) );
		AssertTrue( 42  == ToInteger("3xy", 42) );
		AssertTrue( 42  == ToInteger("xy3", 42) );
		// byte
		AssertTrue( ((byte)123) == ToByte("123", (byte)42) );
		AssertTrue( ((byte)42)  == ToByte(null,  (byte)42) );
		AssertTrue( ((byte)42)  == ToByte("3xy", (byte)42) );
		AssertTrue( ((byte)42)  == ToByte("xy3", (byte)42) );
		// short
		AssertTrue( ((short)123) == ToShort("123", (short)42) );
		AssertTrue( ((short)42)  == ToShort(null,  (short)42) );
		AssertTrue( ((short)42)  == ToShort("3xy", (short)42) );
		AssertTrue( ((short)42)  == ToShort("xy3", (short)42) );
		// long
		AssertTrue( 123L == ToLong("123", 42L) );
		AssertTrue( 42L  == ToLong(null,  42L) );
		AssertTrue( 42L  == ToLong("3xy", 42L) );
		AssertTrue( 42L  == ToLong("xy3", 42L) );
		// double
		AssertTrue( 12.3d == ToDouble("12.3", 42.1d) );
		AssertTrue( 42.1d == ToDouble(null,   42.1d) );
		AssertTrue( 42.1d == ToDouble("3xy",  42.1d) );
		AssertTrue( 42.1d == ToDouble("xy3",  42.1d) );
		// float
		AssertTrue( 12.3f == ToFloat("12.3",  42.1f) );
		AssertTrue( 42.1f == ToFloat(null,    42.1f) );
		AssertTrue( 42.1f == ToFloat("1.2xy", 42.1f) );
		AssertTrue( 42.1f == ToFloat("xy1.2", 42.1f) );
		// boolean
		AssertEquals( Boolean.valueOf(true),  ToBoolean("true")     );
		AssertEquals( Boolean.valueOf(true),  ToBoolean("en")       );
		AssertEquals( Boolean.valueOf(true),  ToBoolean("enable")   );
		AssertEquals( Boolean.valueOf(true),  ToBoolean("enabled")  );
		AssertEquals( Boolean.valueOf(true),  ToBoolean("yes")      );
		AssertEquals( Boolean.valueOf(true),  ToBoolean("on")       );
		AssertEquals( Boolean.valueOf(false), ToBoolean("false")    );
		AssertEquals( Boolean.valueOf(false), ToBoolean("dis")      );
		AssertEquals( Boolean.valueOf(false), ToBoolean("disable")  );
		AssertEquals( Boolean.valueOf(false), ToBoolean("disabled") );
		AssertEquals( Boolean.valueOf(false), ToBoolean("no")       );
		AssertEquals( Boolean.valueOf(false), ToBoolean("off")      );
		AssertEquals( Boolean.valueOf(true),  ToBoolean("1")        );
		AssertEquals( Boolean.valueOf(true),  ToBoolean("t")        );
		AssertEquals( Boolean.valueOf(true),  ToBoolean("i")        );
		AssertEquals( Boolean.valueOf(true),  ToBoolean("e")        );
		AssertEquals( Boolean.valueOf(true),  ToBoolean("y")        );
		AssertEquals( Boolean.valueOf(false), ToBoolean("0")        );
		AssertEquals( Boolean.valueOf(false), ToBoolean("f")        );
		AssertEquals( Boolean.valueOf(false), ToBoolean("o")        );
		AssertEquals( Boolean.valueOf(false), ToBoolean("d")        );
		AssertEquals( Boolean.valueOf(false), ToBoolean("n")        );
	}
	@Test
	public void testObjectToNumber() {
		AssertEquals( Integer.valueOf(123),       CastInteger( (Object) Integer.valueOf(123)       ) );
		AssertEquals( Byte.valueOf(  (byte)123),  CastByte(    (Object) Byte.valueOf(  (byte)123)  ) );
		AssertEquals( Short.valueOf( (short)123), CastShort(   (Object) Short.valueOf( (short)123) ) );
		AssertEquals( Long.valueOf(  123L),       CastLong(    (Object) Long.valueOf(123L)         ) );
		AssertEquals( Double.valueOf(12.3d),      CastDouble(  (Object) Double.valueOf(12.3d)      ) );
		AssertEquals( Float.valueOf( 12.3f),      CastFloat(   (Object) Float.valueOf(12.3f)       ) );
		AssertEquals( Boolean.TRUE,               CastBoolean( (Object) Boolean.valueOf(true)      ) );
		AssertEquals( Boolean.FALSE,              CastBoolean( (Object) Boolean.valueOf(false)     ) );
	}



	// -------------------------------------------------------------------------------
	// min/max



	@Test
	public void testMinMax() {
		AssertEquals( 1, Min( 1, 2, 3      ));
		AssertEquals(-5, Min(-5, 0, 5      ));
		AssertEquals( 5, Min( 9, 8, 7, 5, 6));
		AssertEquals( 3, Max( 1, 2, 3      ));
		AssertEquals( 5, Max(-5, 0, 5      ));
		AssertEquals( 9, Max( 9, 8, 7, 5, 6));
	}



	// -------------------------------------------------------------------------------
	// min/max

	//        |   2D   |   3D
	//-----------------------------
	// circle | Radial | Vectorial
	// square | Linear | Axial
	// hybrid | Hybrid | Hybrid



	@Test
	public void testDistance() {
		final double delta = 0.001;
		// 2d circular distance
		AssertEquals(   0.0,   DistanceRadial(0.0, 0.0,   0.0,   0.0), delta);
		AssertEquals(   1.414, DistanceRadial(0.0, 0.0,   1.0,   1.0), delta);
		AssertEquals(   7.071, DistanceRadial(0.0, 0.0,   5.0,   5.0), delta);
		AssertEquals(   5.656, DistanceRadial(1.0, 1.0,   5.0,   5.0), delta);
		AssertEquals( 120.208, DistanceRadial(5.0, 5.0,  90.0,  90.0), delta);
		AssertEquals(1407.001, DistanceRadial(5.0, 5.0, 999.9, 999.9), delta);
		AssertEquals(1414.072, DistanceRadial(0.0, 0.0, 999.9, 999.9), delta);
		AssertEquals(   1.0,   DistanceRadial(0.0, 0.0,   1.0,   0.0), delta);
		AssertEquals(   1.0,   DistanceRadial(0.0, 0.0,   0.0,   1.0), delta);
		AssertEquals(   1.414, DistanceRadial(1.0, 1.0,   0.0,   0.0), delta);
		// 3d circular distance
		AssertEquals(   0.0,   DistanceVectorial(0.0, 0.0, 0.0,   0.0,   0.0,   0.0), delta);
		AssertEquals(   1.732, DistanceVectorial(0.0, 0.0, 0.0,   1.0,   1.0,   1.0), delta);
		AssertEquals(   8.66,  DistanceVectorial(0.0, 0.0, 0.0,   5.0,   5.0,   5.0), delta);
		AssertEquals(   6.928, DistanceVectorial(1.0, 1.0, 1.0,   5.0,   5.0,   5.0), delta);
		AssertEquals( 147.224, DistanceVectorial(5.0, 5.0, 5.0,  90.0,  90.0,  90.0), delta);
		AssertEquals(1723.217, DistanceVectorial(5.0, 5.0, 5.0, 999.9, 999.9, 999.9), delta);
		AssertEquals(1731.877, DistanceVectorial(0.0, 0.0, 0.0, 999.9, 999.9, 999.9), delta);
		AssertEquals(   1.0,   DistanceVectorial(0.0, 0.0, 0.0,   1.0,   0.0,   0.0), delta);
		AssertEquals(   1.0,   DistanceVectorial(0.0, 0.0, 0.0,   0.0,   1.0,   0.0), delta);
		AssertEquals(   1.0,   DistanceVectorial(0.0, 0.0, 0.0,   0.0,   0.0,   1.0), delta);
		AssertEquals(   1.0,   DistanceVectorial(0.0, 0.0, 0.0,   0.0,   1.0,   0.0), delta);
		AssertEquals(   1.732, DistanceVectorial(1.0, 1.0, 1.0,   0.0,   0.0,   0.0), delta);
		// 2d square distance
		AssertEquals(  0.0, DistanceLinear(0.0, 0.0,   0.0,   0.0), delta);
		AssertEquals(  1.0, DistanceLinear(0.0, 0.0,   1.0,   1.0), delta);
		AssertEquals(  5.0, DistanceLinear(0.0, 0.0,   5.0,   5.0), delta);
		AssertEquals(  4.0, DistanceLinear(1.0, 1.0,   5.0,   5.0), delta);
		AssertEquals( 85.0, DistanceLinear(5.0, 5.0,  90.0,  90.0), delta);
		AssertEquals(994.9, DistanceLinear(5.0, 5.0, 999.9, 999.9), delta);
		AssertEquals(999.9, DistanceLinear(0.0, 0.0, 999.9, 999.9), delta);
		AssertEquals(  1.0, DistanceLinear(0.0, 0.0,   1.0,   0.0), delta);
		AssertEquals(  1.0, DistanceLinear(0.0, 0.0,   0.0,   1.0), delta);
		AssertEquals(  1.0, DistanceLinear(1.0, 1.0,   0.0,   0.0), delta);
		// 3d square distance
		AssertEquals(  0.0, DistanceAxial(0.0, 0.0, 0.0,   0.0,   0.0,   0.0), delta);
		AssertEquals(  1.0, DistanceAxial(0.0, 0.0, 0.0,   1.0,   1.0,   1.0), delta);
		AssertEquals(  5.0, DistanceAxial(0.0, 0.0, 0.0,   5.0,   5.0,   5.0), delta);
		AssertEquals(  4.0, DistanceAxial(1.0, 1.0, 1.0,   5.0,   5.0,   5.0), delta);
		AssertEquals( 85.0, DistanceAxial(5.0, 5.0, 5.0,  90.0,  90.0,  90.0), delta);
		AssertEquals(994.9, DistanceAxial(5.0, 5.0, 5.0, 999.9, 999.9, 999.9), delta);
		AssertEquals(999.9, DistanceAxial(0.0, 0.0, 0.0, 999.9, 999.9, 999.9), delta);
		AssertEquals(  1.0, DistanceAxial(0.0, 0.0, 0.0,   1.0,   0.0,   0.0), delta);
		AssertEquals(  1.0, DistanceAxial(0.0, 0.0, 0.0,   0.0,   1.0,   0.0), delta);
		AssertEquals(  1.0, DistanceAxial(0.0, 0.0, 0.0,   0.0,   0.0,   1.0), delta);
		AssertEquals(  1.0, DistanceAxial(0.0, 0.0, 0.0,   0.0,   1.0,   0.0), delta);
		AssertEquals(  1.0, DistanceAxial(1.0, 1.0, 1.0,   0.0,   0.0,   0.0), delta);
		// 2d hybrid distance
		AssertEquals(0.0, DistanceHybrid2D(0.0, 0.0, 0.0, 0.0, 0.0), delta);
			AssertEquals(0.0, DistanceHybrid2D(0.1, 0.0, 0.0, 0.0, 0.0), delta);
			AssertEquals(0.0, DistanceHybrid2D(0.5, 0.0, 0.0, 0.0, 0.0), delta);
			AssertEquals(0.0, DistanceHybrid2D(0.7, 0.0, 0.0, 0.0, 0.0), delta);
			AssertEquals(0.0, DistanceHybrid2D(1.0, 0.0, 0.0, 0.0, 0.0), delta);
		AssertEquals(1.0, DistanceHybrid2D(0.0, 0.0, 0.0, 1.0, 1.0), delta);
			AssertEquals(1.041, DistanceHybrid2D(0.1, 0.0, 0.0, 1.0, 1.0), delta);
			AssertEquals(1.207, DistanceHybrid2D(0.5, 0.0, 0.0, 1.0, 1.0), delta);
			AssertEquals(1.289, DistanceHybrid2D(0.7, 0.0, 0.0, 1.0, 1.0), delta);
			AssertEquals(1.414, DistanceHybrid2D(1.0, 0.0, 0.0, 1.0, 1.0), delta);
		AssertEquals(5.0, DistanceHybrid2D(0.0, 0.0, 0.0, 5.0, 5.0), delta);
			AssertEquals(5.207, DistanceHybrid2D(0.1, 0.0, 0.0, 5.0, 5.0), delta);
			AssertEquals(6.035, DistanceHybrid2D(0.5, 0.0, 0.0, 5.0, 5.0), delta);
			AssertEquals(6.449, DistanceHybrid2D(0.7, 0.0, 0.0, 5.0, 5.0), delta);
			AssertEquals(7.071, DistanceHybrid2D(1.0, 0.0, 0.0, 5.0, 5.0), delta);
		AssertEquals(  4.0, DistanceHybrid2D(0.0, 1.0, 1.0, 5.0, 5.0), delta);
			AssertEquals(  4.165, DistanceHybrid2D(0.1, 1.0, 1.0, 5.0, 5.0), delta);
			AssertEquals(  4.828, DistanceHybrid2D(0.5, 1.0, 1.0, 5.0, 5.0), delta);
			AssertEquals(  5.159, DistanceHybrid2D(0.7, 1.0, 1.0, 5.0, 5.0), delta);
			AssertEquals(  5.656, DistanceHybrid2D(1.0, 1.0, 1.0, 5.0, 5.0), delta);
		AssertEquals( 85.0, DistanceHybrid2D(0.0, 5.0, 5.0, 90.0, 90.0), delta);
			AssertEquals(  88.52,  DistanceHybrid2D(0.1, 5.0, 5.0, 90.0, 90.0), delta);
			AssertEquals( 102.604, DistanceHybrid2D(0.5, 5.0, 5.0, 90.0, 90.0), delta);
			AssertEquals( 109.645, DistanceHybrid2D(0.7, 5.0, 5.0, 90.0, 90.0), delta);
			AssertEquals( 120.209, DistanceHybrid2D(1.0, 5.0, 5.0, 90.0, 90.0), delta);
		AssertEquals(994.9, DistanceHybrid2D(0.0, 5.0, 5.0, 999.9, 999.9), delta);
			AssertEquals(1036.11,  DistanceHybrid2D(0.1, 5.0, 5.0, 999.9, 999.9), delta);
			AssertEquals(1200.95,  DistanceHybrid2D(0.5, 5.0, 5.0, 999.9, 999.9), delta);
			AssertEquals(1283.37,  DistanceHybrid2D(0.7, 5.0, 5.0, 999.9, 999.9), delta);
			AssertEquals(1407.001, DistanceHybrid2D(1.0, 5.0, 5.0, 999.9, 999.9), delta);
		AssertEquals(999.9, DistanceHybrid2D(0.0, 0.0, 0.0, 999.9, 999.9), delta);
			AssertEquals(1041.317, DistanceHybrid2D(0.1, 0.0, 0.0, 999.9, 999.9), delta);
			AssertEquals(1206.986, DistanceHybrid2D(0.5, 0.0, 0.0, 999.9, 999.9), delta);
			AssertEquals(1289.82,  DistanceHybrid2D(0.7, 0.0, 0.0, 999.9, 999.9), delta);
			AssertEquals(1414.072, DistanceHybrid2D(1.0, 0.0, 0.0, 999.9, 999.9), delta);
		AssertEquals(1.0, DistanceHybrid2D(0.0, 0.0, 0.0, 1.0, 0.0), delta);
			AssertEquals(1.0, DistanceHybrid2D(0.1, 0.0, 0.0, 1.0, 0.0), delta);
			AssertEquals(1.0, DistanceHybrid2D(0.5, 0.0, 0.0, 1.0, 0.0), delta);
			AssertEquals(1.0, DistanceHybrid2D(0.7, 0.0, 0.0, 1.0, 0.0), delta);
			AssertEquals(1.0, DistanceHybrid2D(1.0, 0.0, 0.0, 1.0, 0.0), delta);
		AssertEquals(1.0, DistanceHybrid2D(0.0, 0.0, 0.0, 0.0, 1.0), delta);
			AssertEquals(1.0, DistanceHybrid2D(0.1, 0.0, 0.0, 0.0, 1.0), delta);
			AssertEquals(1.0, DistanceHybrid2D(0.5, 0.0, 0.0, 0.0, 1.0), delta);
			AssertEquals(1.0, DistanceHybrid2D(0.7, 0.0, 0.0, 0.0, 1.0), delta);
			AssertEquals(1.0, DistanceHybrid2D(1.0, 0.0, 0.0, 0.0, 1.0), delta);
		AssertEquals(1.0, DistanceHybrid2D(0.0, 1.0, 1.0, 0.0, 0.0), delta);
			AssertEquals(1.041, DistanceHybrid2D(0.1, 1.0, 1.0, 0.0, 0.0), delta);
			AssertEquals(1.207, DistanceHybrid2D(0.5, 1.0, 1.0, 0.0, 0.0), delta);
			AssertEquals(1.289, DistanceHybrid2D(0.7, 1.0, 1.0, 0.0, 0.0), delta);
			AssertEquals(1.414, DistanceHybrid2D(1.0, 1.0, 1.0, 0.0, 0.0), delta);
		// 3d hybrid distance
		AssertEquals(0.0, DistanceHybrid3D(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0), delta);
			AssertEquals(0.0, DistanceHybrid3D(0.1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0), delta);
			AssertEquals(0.0, DistanceHybrid3D(0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0), delta);
			AssertEquals(0.0, DistanceHybrid3D(0.7, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0), delta);
			AssertEquals(0.0, DistanceHybrid3D(1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0), delta);
		AssertEquals(1.0, DistanceHybrid3D(0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0), delta);
			AssertEquals(1.073, DistanceHybrid3D(0.1, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0), delta);
			AssertEquals(1.366, DistanceHybrid3D(0.5, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0), delta);
			AssertEquals(1.512, DistanceHybrid3D(0.7, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0), delta);
			AssertEquals(1.732, DistanceHybrid3D(1.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0), delta);
		AssertEquals(5.0, DistanceHybrid3D(0.0, 0.0, 0.0, 0.0, 5.0, 5.0, 5.0), delta);
			AssertEquals(5.366, DistanceHybrid3D(0.1, 0.0, 0.0, 0.0, 5.0, 5.0, 5.0), delta);
			AssertEquals(6.83,  DistanceHybrid3D(0.5, 0.0, 0.0, 0.0, 5.0, 5.0, 5.0), delta);
			AssertEquals(7.562, DistanceHybrid3D(0.7, 0.0, 0.0, 0.0, 5.0, 5.0, 5.0), delta);
			AssertEquals(8.66,  DistanceHybrid3D(1.0, 0.0, 0.0, 0.0, 5.0, 5.0, 5.0), delta);
		AssertEquals(4.0, DistanceHybrid3D(0.0, 1.0, 1.0, 1.0, 5.0, 5.0, 5.0), delta);
			AssertEquals(4.292, DistanceHybrid3D(0.1, 1.0, 1.0, 1.0, 5.0, 5.0, 5.0), delta);
			AssertEquals(5.464, DistanceHybrid3D(0.5, 1.0, 1.0, 1.0, 5.0, 5.0, 5.0), delta);
			AssertEquals(6.049, DistanceHybrid3D(0.7, 1.0, 1.0, 1.0, 5.0, 5.0, 5.0), delta);
			AssertEquals(6.928, DistanceHybrid3D(1.0, 1.0, 1.0, 1.0, 5.0, 5.0, 5.0), delta);
		AssertEquals(85.0, DistanceHybrid3D(0.0, 5.0, 5.0, 5.0, 90.0, 90.0, 90.0), delta);
			AssertEquals( 91.222, DistanceHybrid3D(0.1, 5.0, 5.0, 5.0, 90.0, 90.0, 90.0), delta);
			AssertEquals(116.112, DistanceHybrid3D(0.5, 5.0, 5.0, 5.0, 90.0, 90.0, 90.0), delta);
			AssertEquals(128.557, DistanceHybrid3D(0.7, 5.0, 5.0, 5.0, 90.0, 90.0, 90.0), delta);
			AssertEquals(147.224, DistanceHybrid3D(1.0, 5.0, 5.0, 5.0, 90.0, 90.0, 90.0), delta);
		AssertEquals(994.9, DistanceHybrid3D(0.0, 5.0, 5.0, 5.0, 999.9, 999.9, 999.9), delta);
			AssertEquals(1067.731, DistanceHybrid3D(0.1, 5.0, 5.0, 5.0, 999.9, 999.9, 999.9), delta);
			AssertEquals(1359.058, DistanceHybrid3D(0.5, 5.0, 5.0, 5.0, 999.9, 999.9, 999.9), delta);
			AssertEquals(1504.722, DistanceHybrid3D(0.7, 5.0, 5.0, 5.0, 999.9, 999.9, 999.9), delta);
			AssertEquals(1723.217, DistanceHybrid3D(1.0, 5.0, 5.0, 5.0, 999.9, 999.9, 999.9), delta);
		AssertEquals(999.9, DistanceHybrid3D(0.0, 0.0, 0.0, 0.0, 999.9, 999.9, 999.9), delta);
			AssertEquals(1073.097, DistanceHybrid3D(0.1, 0.0, 0.0, 0.0, 999.9, 999.9, 999.9), delta);
			AssertEquals(1365.888, DistanceHybrid3D(0.5, 0.0, 0.0, 0.0, 999.9, 999.9, 999.9), delta);
			AssertEquals(1512.284, DistanceHybrid3D(0.7, 0.0, 0.0, 0.0, 999.9, 999.9, 999.9), delta);
			AssertEquals(1731.877, DistanceHybrid3D(1.0, 0.0, 0.0, 0.0, 999.9, 999.9, 999.9), delta);
		AssertEquals(1.0, DistanceHybrid3D(0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0), delta);
			AssertEquals(1.0, DistanceHybrid3D(0.1, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0), delta);
			AssertEquals(1.0, DistanceHybrid3D(0.5, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0), delta);
			AssertEquals(1.0, DistanceHybrid3D(0.7, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0), delta);
			AssertEquals(1.0, DistanceHybrid3D(1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0), delta);
		AssertEquals(1.0, DistanceHybrid3D(0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0), delta);
			AssertEquals(1.0, DistanceHybrid3D(0.1, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0), delta);
			AssertEquals(1.0, DistanceHybrid3D(0.5, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0), delta);
			AssertEquals(1.0, DistanceHybrid3D(0.7, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0), delta);
			AssertEquals(1.0, DistanceHybrid3D(1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0), delta);
		AssertEquals(1.0, DistanceHybrid3D(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0), delta);
			AssertEquals(1.0, DistanceHybrid3D(0.1, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0), delta);
			AssertEquals(1.0, DistanceHybrid3D(0.5, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0), delta);
			AssertEquals(1.0, DistanceHybrid3D(0.7, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0), delta);
			AssertEquals(1.0, DistanceHybrid3D(1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0), delta);
		AssertEquals(1.0, DistanceHybrid3D(0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0), delta);
			AssertEquals(1.0, DistanceHybrid3D(0.1, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0), delta);
			AssertEquals(1.0, DistanceHybrid3D(0.5, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0), delta);
			AssertEquals(1.0, DistanceHybrid3D(0.7, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0), delta);
			AssertEquals(1.0, DistanceHybrid3D(1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0), delta);
		AssertEquals(1.0, DistanceHybrid3D(0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0), delta);
			AssertEquals(1.073, DistanceHybrid3D(0.1, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0), delta);
			AssertEquals(1.366, DistanceHybrid3D(0.5, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0), delta);
			AssertEquals(1.512, DistanceHybrid3D(0.7, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0), delta);
			AssertEquals(1.732, DistanceHybrid3D(1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0), delta);
	}



	@Test
	public void testRemap() {
		// remap percent
		AssertEquals(  0,    MathUtils.Remap(0,    10,    0.0 )       );
		AssertEquals( 10,    MathUtils.Remap(0,    10,    1.0 )       );
		AssertEquals(  5,    MathUtils.Remap(0,    10,    0.5 )       );
		AssertEquals(  5,    MathUtils.Remap(0,     9,    0.5 )       );
		AssertEquals(  0L,   MathUtils.Remap(0L,   10L,   0.0 )       );
		AssertEquals( 10L,   MathUtils.Remap(0L,   10L,   1.0 )       );
		AssertEquals(  5L,   MathUtils.Remap(0L,    9L,   0.5 )       );
		AssertEquals(  0.0f, MathUtils.Remap(0.0f, 10.0f, 0.0f), DELTA);
		AssertEquals( 10.0f, MathUtils.Remap(0.0f, 10.0f, 1.0f), DELTA);
		AssertEquals(  4.5f, MathUtils.Remap(0.0f,  9.0f, 0.5f), DELTA);
		AssertEquals(  0.0,  MathUtils.Remap(0.0,  10.0,  0.0 ), DELTA);
		AssertEquals( 10.0,  MathUtils.Remap(0.0,  10.0,  1.0 ), DELTA);
		AssertEquals(  4.5,  MathUtils.Remap(0.0,   9.0,  0.5 ), DELTA);
		// remap percent weighted
		AssertEquals( 0.1,     MathUtils.Remap(0.1,  0.1,  0.5, 0.1), DELTA);
		AssertEquals( 2.5,     MathUtils.Remap(0.0, 10.0,  0.5, 0.5), DELTA);
		AssertEquals( 5.0,     MathUtils.Remap(0.0, 10.0,  0.5, 1.0), DELTA);
		AssertEquals( 0.03125, MathUtils.Remap(0.0, 32.0,  0.5, 0.1), DELTA);
		AssertEquals( 0.312,   MathUtils.Remap(0.31, 6.56, 0.2, 0.2), DELTA);
		AssertEquals( 0.712,   MathUtils.Remap(0.71, 6.96, 0.2, 0.2), DELTA);
		AssertEquals( 0.912,   MathUtils.Remap(0.91, 7.16, 0.2, 0.2), DELTA);
		AssertEquals( 1.012,   MathUtils.Remap(1.01, 7.26, 0.2, 0.2), DELTA);
		AssertEquals( 6.958,   MathUtils.Remap(6.96, 0.71, 0.2, 0.2), DELTA);
		AssertEquals( 7.058,   MathUtils.Remap(7.06, 0.81, 0.2, 0.2), DELTA);
		AssertEquals( 7.258,   MathUtils.Remap(7.26, 1.01, 0.2, 0.2), DELTA);
		// remap range
		AssertEquals( 25,    MathUtils.Remap(1,    10,    21,    30,     5   )       );
		AssertEquals( 30,    MathUtils.Remap(1,    10,    21,    30,    10   )       );
		AssertEquals( 25L,   MathUtils.Remap(1L,   10L,   21L,   30L,    5L  )       );
		AssertEquals( 30L,   MathUtils.Remap(1L,   10L,   21L,   30L,   10L  )       );
		AssertEquals( 25.0f, MathUtils.Remap(1.0f, 10.0f, 21.0f, 30.0f,  5.0f), DELTA);
		AssertEquals( 30.0f, MathUtils.Remap(1.0f, 10.0f, 21.0f, 30.0f, 10.0f), DELTA);
		AssertEquals( 25.0,  MathUtils.Remap(1.0,  10.0,  21.0,  30.0,   5.0 ), DELTA);
		AssertEquals( 30.0,  MathUtils.Remap(1.0,  10.0,  21.0,  30.0,  10.0 ), DELTA);
		// remap 8 bit color
		AssertEquals( Color.BLACK, MathUtils.Remap8BitColor(0B00000000) );
		AssertEquals( Color.WHITE, MathUtils.Remap8BitColor(0B11111111) );
		AssertEquals( Color.RED,   MathUtils.Remap8BitColor(0B00000111) );
		AssertEquals( Color.GREEN, MathUtils.Remap8BitColor(0B00111000) );
		AssertEquals( Color.BLUE,  MathUtils.Remap8BitColor(0B11000000) );
		AssertEquals( 0B00000111,   7);
		AssertEquals( 0B00111000,  56);
		AssertEquals( 0B11000000, 192);
	}



/*
	@Test
	public void testCubicInterpolate() {
		final double a = 1.0;
		final double b = 0.0;
		final double c = 2.0;
		final double d = 3.0;
		AssertEquals(0.0,  CubicInterpolate( 0.0, a, b, c, d), DELTA);
		AssertEquals(0.75, CubicInterpolate( 0.5, a, b, c, d), DELTA);
		AssertEquals(2.0,  CubicInterpolate( 1.0, a, b, c, d), DELTA);
		AssertEquals(0.0,  CubicInterpolate(-1.0, a, b, c, d), DELTA);
		AssertEquals(6.0,  CubicInterpolate( 2.0, a, b, c, d), DELTA);
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
		AssertEquals(1.0,  CubicInterpolateValues(values, 0, 0.0), DELTA);
		AssertEquals(0.25, CubicInterpolateValues(values, 0, 0.5), DELTA);
		AssertEquals(0.0,  CubicInterpolateValues(values, 0, 1.0), DELTA);
		AssertEquals(0.0,  CubicInterpolateValues(values, 1, 0.0), DELTA);
		AssertEquals(0.75, CubicInterpolateValues(values, 1, 0.5), DELTA);
		AssertEquals(2.0,  CubicInterpolateValues(values, 1, 1.0), DELTA);
		AssertEquals(1.0,  CubicInterpolateValues(values,-1, 1.0), DELTA);
		AssertEquals(3.0,  CubicInterpolateValues(values, 4, 1.0), DELTA);
		// CubicInterpolateLoopedValues
		AssertEquals(1.0,  CubicInterpolateLoopedValues(values, 0, 0.0), DELTA);
		AssertEquals(0.0,  CubicInterpolateLoopedValues(values, 0, 0.5), DELTA);
		AssertEquals(0.0,  CubicInterpolateLoopedValues(values, 0, 1.0), DELTA);
		AssertEquals(0.0,  CubicInterpolateLoopedValues(values, 1, 0.0), DELTA);
		AssertEquals(0.75, CubicInterpolateLoopedValues(values, 1, 0.5), DELTA);
		AssertEquals(2.0,  CubicInterpolateLoopedValues(values, 1, 1.0), DELTA);
		AssertEquals(1.0,  CubicInterpolateLoopedValues(values,-1, 1.0), DELTA);
		AssertEquals(0.0,  CubicInterpolateLoopedValues(values, 4, 1.0), DELTA);
		// CubicInterpolateCircularValues
		AssertEquals(1.0,  CubicInterpolateCircularValues(values, 0, 0.0), DELTA);
		AssertEquals(0.0,  CubicInterpolateCircularValues(values, 0, 0.5), DELTA);
		AssertEquals(0.0,  CubicInterpolateCircularValues(values, 0, 1.0), DELTA);
		AssertEquals(0.0,  CubicInterpolateCircularValues(values, 1, 0.0), DELTA);
		AssertEquals(0.75, CubicInterpolateCircularValues(values, 1, 0.5), DELTA);
		AssertEquals(2.0,  CubicInterpolateCircularValues(values, 1, 1.0), DELTA);
		AssertEquals(1.0,  CubicInterpolateCircularValues(values,-1, 1.0), DELTA);
		AssertEquals(1.0,  CubicInterpolateCircularValues(values, 4, 1.0), DELTA);
	}
*/



}
