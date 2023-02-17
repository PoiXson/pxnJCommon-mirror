package com.poixson.tests.utils;

import org.junit.Assert;
import org.junit.Test;

import com.poixson.utils.NumberUtils;


public class Test_NumberUtils {



	// -------------------------------------------------------------------------------
	// equals



	@Test
	public void testEqualsExact() {
		// int
		Assert.assertTrue(  NumberUtils.EqualsExact( Integer.valueOf(123), Integer.valueOf(123) ) );
		Assert.assertFalse( NumberUtils.EqualsExact( Integer.valueOf(123), Integer.valueOf(42)  ) );
		Assert.assertFalse( NumberUtils.EqualsExact( Integer.valueOf(123), (Integer) null       ) );
		Assert.assertFalse( NumberUtils.EqualsExact( (Integer) null,       Integer.valueOf(123) ) );
		Assert.assertTrue(  NumberUtils.EqualsExact( (Integer) null,       (Integer) null       ) );
		// boolean
		Assert.assertTrue(  NumberUtils.EqualsExact( Boolean.valueOf(true),  Boolean.valueOf(true)  ) );
		Assert.assertTrue(  NumberUtils.EqualsExact( Boolean.valueOf(false), Boolean.valueOf(false) ) );
		Assert.assertFalse( NumberUtils.EqualsExact( Boolean.valueOf(true),  Boolean.valueOf(false) ) );
		Assert.assertFalse( NumberUtils.EqualsExact( Boolean.valueOf(true),  (Boolean) null         ) );
		Assert.assertFalse( NumberUtils.EqualsExact( Boolean.valueOf(false), (Boolean) null         ) );
		Assert.assertFalse( NumberUtils.EqualsExact( (Boolean) null,         Boolean.valueOf(true)  ) );
		Assert.assertFalse( NumberUtils.EqualsExact( (Boolean) null,         Boolean.valueOf(false) ) );
		Assert.assertTrue(  NumberUtils.EqualsExact( (Boolean) null,         (Boolean) null         ) );
		// byte
		Assert.assertTrue(  NumberUtils.EqualsExact( Byte.valueOf((byte)123), Byte.valueOf((byte)123) ) );
		Assert.assertFalse( NumberUtils.EqualsExact( Byte.valueOf((byte)123), Byte.valueOf((byte)42)  ) );
		Assert.assertFalse( NumberUtils.EqualsExact( Byte.valueOf((byte)123), null                    ) );
		Assert.assertFalse( NumberUtils.EqualsExact( null,                    Byte.valueOf((byte)123) ) );
		Assert.assertTrue(  NumberUtils.EqualsExact( (Byte) null,             (Byte) null             ) );
		// short
		Assert.assertTrue(  NumberUtils.EqualsExact( Short.valueOf((short)123), Short.valueOf((short)123) ) );
		Assert.assertFalse( NumberUtils.EqualsExact( Short.valueOf((short)123), Short.valueOf((short)42)  ) );
		Assert.assertFalse( NumberUtils.EqualsExact( Short.valueOf((short)123), (Short) null              ) );
		Assert.assertFalse( NumberUtils.EqualsExact( (Short) null,              Short.valueOf((short)123) ) );
		Assert.assertTrue(  NumberUtils.EqualsExact( (Short) null,              (Short) null              ) );
		// long
		Assert.assertTrue(  NumberUtils.EqualsExact( Long.valueOf((short)123), Long.valueOf((short)123) ) );
		Assert.assertFalse( NumberUtils.EqualsExact( Long.valueOf((short)123), Long.valueOf((short)42)  ) );
		Assert.assertFalse( NumberUtils.EqualsExact( Long.valueOf((short)123), (Long) null              ) );
		Assert.assertFalse( NumberUtils.EqualsExact( (Long) null,              Long.valueOf((short)123) ) );
		Assert.assertTrue(  NumberUtils.EqualsExact( (Long) null,              (Long) null              ) );
		// double
		Assert.assertTrue(  NumberUtils.EqualsExact( Double.valueOf(12.3d), Double.valueOf(12.3d) ) );
		Assert.assertFalse( NumberUtils.EqualsExact( Double.valueOf(12.3d), Double.valueOf(42.1d) ) );
		Assert.assertFalse( NumberUtils.EqualsExact( Double.valueOf(12.3d), (Double) null         ) );
		Assert.assertFalse( NumberUtils.EqualsExact( (Double) null,         Double.valueOf(12.3d) ) );
		Assert.assertTrue(  NumberUtils.EqualsExact( (Double) null,         (Double) null         ) );
		// float
		Assert.assertTrue(  NumberUtils.EqualsExact( Float.valueOf(12.3f), Float.valueOf(12.3f) ) );
		Assert.assertFalse( NumberUtils.EqualsExact( Float.valueOf(12.3f), Float.valueOf(42.1f) ) );
		Assert.assertFalse( NumberUtils.EqualsExact( Float.valueOf(12.3f), (Float) null         ) );
		Assert.assertFalse( NumberUtils.EqualsExact( (Float) null,         Float.valueOf(12.3f) ) );
		Assert.assertTrue(  NumberUtils.EqualsExact( (Float) null,         (Float) null         ) );
	}



	// -------------------------------------------------------------------------------
	// convert



	@Test
	public void testSafeLongToInt() {
		Assert.assertEquals( 11, NumberUtils.SafeLongToInt(11L) );
		Assert.assertEquals( Integer.MAX_VALUE, NumberUtils.SafeLongToInt( ((long)Integer.MAX_VALUE) + 9L ) );
		Assert.assertEquals( Integer.MIN_VALUE, NumberUtils.SafeLongToInt( ((long)Integer.MIN_VALUE) - 9L ) );
	}



	@Test
	public void testToNumber() {
		// number
		Assert.assertEquals( Integer.valueOf(123),      NumberUtils.ToInteger("123") );
		Assert.assertEquals( Byte.valueOf((byte)123),   NumberUtils.ToByte(   "123") );
		Assert.assertEquals( Short.valueOf((short)123), NumberUtils.ToShort(  "123") );
		Assert.assertEquals( Long.valueOf(123),         NumberUtils.ToLong(   "123") );
		Assert.assertEquals( Double.valueOf(123),       NumberUtils.ToDouble( "123") );
		Assert.assertEquals( Float.valueOf(123),        NumberUtils.ToFloat(  "123") );
		Assert.assertEquals( Boolean.valueOf(true),  NumberUtils.ToBoolean("true")  );
		Assert.assertEquals( Boolean.valueOf(false), NumberUtils.ToBoolean("false") );
	}
	@Test
	public void testToNumberOrDefault() {
		// int
		Assert.assertTrue( 123 == NumberUtils.ToInteger("123", 42) );
		Assert.assertTrue( 42  == NumberUtils.ToInteger(null,  42) );
		Assert.assertTrue( 42  == NumberUtils.ToInteger("3xy", 42) );
		Assert.assertTrue( 42  == NumberUtils.ToInteger("xy3", 42) );
		// byte
		Assert.assertTrue( ((byte)123) == NumberUtils.ToByte("123", (byte)42) );
		Assert.assertTrue( ((byte)42)  == NumberUtils.ToByte(null,  (byte)42) );
		Assert.assertTrue( ((byte)42)  == NumberUtils.ToByte("3xy", (byte)42) );
		Assert.assertTrue( ((byte)42)  == NumberUtils.ToByte("xy3", (byte)42) );
		// short
		Assert.assertTrue( ((short)123) == NumberUtils.ToShort("123", (short)42) );
		Assert.assertTrue( ((short)42)  == NumberUtils.ToShort(null,  (short)42) );
		Assert.assertTrue( ((short)42)  == NumberUtils.ToShort("3xy", (short)42) );
		Assert.assertTrue( ((short)42)  == NumberUtils.ToShort("xy3", (short)42) );
		// long
		Assert.assertTrue( 123L == NumberUtils.ToLong("123", 42L) );
		Assert.assertTrue( 42L  == NumberUtils.ToLong(null,  42L) );
		Assert.assertTrue( 42L  == NumberUtils.ToLong("3xy", 42L) );
		Assert.assertTrue( 42L  == NumberUtils.ToLong("xy3", 42L) );
		// double
		Assert.assertTrue( 12.3d == NumberUtils.ToDouble("12.3", 42.1d) );
		Assert.assertTrue( 42.1d == NumberUtils.ToDouble(null,   42.1d) );
		Assert.assertTrue( 42.1d == NumberUtils.ToDouble("3xy",  42.1d) );
		Assert.assertTrue( 42.1d == NumberUtils.ToDouble("xy3",  42.1d) );
		// float
		Assert.assertTrue( 12.3f == NumberUtils.ToFloat("12.3",  42.1f) );
		Assert.assertTrue( 42.1f == NumberUtils.ToFloat(null,    42.1f) );
		Assert.assertTrue( 42.1f == NumberUtils.ToFloat("1.2xy", 42.1f) );
		Assert.assertTrue( 42.1f == NumberUtils.ToFloat("xy1.2", 42.1f) );
		// boolean
		Assert.assertEquals( Boolean.valueOf(true),  NumberUtils.ToBoolean("true")     );
		Assert.assertEquals( Boolean.valueOf(true),  NumberUtils.ToBoolean("en")       );
		Assert.assertEquals( Boolean.valueOf(true),  NumberUtils.ToBoolean("enable")   );
		Assert.assertEquals( Boolean.valueOf(true),  NumberUtils.ToBoolean("enabled")  );
		Assert.assertEquals( Boolean.valueOf(true),  NumberUtils.ToBoolean("yes")      );
		Assert.assertEquals( Boolean.valueOf(true),  NumberUtils.ToBoolean("on")       );
		Assert.assertEquals( Boolean.valueOf(false), NumberUtils.ToBoolean("false")    );
		Assert.assertEquals( Boolean.valueOf(false), NumberUtils.ToBoolean("dis")      );
		Assert.assertEquals( Boolean.valueOf(false), NumberUtils.ToBoolean("disable")  );
		Assert.assertEquals( Boolean.valueOf(false), NumberUtils.ToBoolean("disabled") );
		Assert.assertEquals( Boolean.valueOf(false), NumberUtils.ToBoolean("no")       );
		Assert.assertEquals( Boolean.valueOf(false), NumberUtils.ToBoolean("off")      );
		Assert.assertEquals( Boolean.valueOf(true),  NumberUtils.ToBoolean("1")        );
		Assert.assertEquals( Boolean.valueOf(true),  NumberUtils.ToBoolean("t")        );
		Assert.assertEquals( Boolean.valueOf(true),  NumberUtils.ToBoolean("i")        );
		Assert.assertEquals( Boolean.valueOf(true),  NumberUtils.ToBoolean("e")        );
		Assert.assertEquals( Boolean.valueOf(true),  NumberUtils.ToBoolean("y")        );
		Assert.assertEquals( Boolean.valueOf(false), NumberUtils.ToBoolean("0")        );
		Assert.assertEquals( Boolean.valueOf(false), NumberUtils.ToBoolean("f")        );
		Assert.assertEquals( Boolean.valueOf(false), NumberUtils.ToBoolean("o")        );
		Assert.assertEquals( Boolean.valueOf(false), NumberUtils.ToBoolean("d")        );
		Assert.assertEquals( Boolean.valueOf(false), NumberUtils.ToBoolean("n")        );
	}
	@Test
	public void testObjectToNumber() {
		Assert.assertEquals( Integer.valueOf(123),       NumberUtils.CastInteger( (Object) Integer.valueOf(123)       ) );
		Assert.assertEquals( Byte.valueOf(  (byte)123),  NumberUtils.CastByte(    (Object) Byte.valueOf(  (byte)123)  ) );
		Assert.assertEquals( Short.valueOf( (short)123), NumberUtils.CastShort(   (Object) Short.valueOf( (short)123) ) );
		Assert.assertEquals( Long.valueOf(  123L),       NumberUtils.CastLong(    (Object) Long.valueOf(123L)         ) );
		Assert.assertEquals( Double.valueOf(12.3d),      NumberUtils.CastDouble(  (Object) Double.valueOf(12.3d)      ) );
		Assert.assertEquals( Float.valueOf( 12.3f),      NumberUtils.CastFloat(   (Object) Float.valueOf(12.3f)       ) );
		Assert.assertEquals(Boolean.TRUE,  NumberUtils.CastBoolean( (Object) Boolean.valueOf(true)  ) );
		Assert.assertEquals(Boolean.FALSE, NumberUtils.CastBoolean( (Object) Boolean.valueOf(false) ) );
	}



}
