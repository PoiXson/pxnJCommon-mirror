package com.poixson.utils;

import static com.poixson.utils.Utils.IsEmpty;

import java.awt.Color;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneOffset;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.tools.Keeper;
import com.poixson.tools.dao.Dab;
import com.poixson.tools.dao.Dabc;
import com.poixson.tools.dao.Fab;
import com.poixson.tools.dao.Fabc;


public final class MathUtils {
	private MathUtils() {}
	static { Keeper.add(new MathUtils()); }



	/**
	 * Max valid tcp/udp port number.
	 */
	public static final int MAX_PORT = 65535;

	public static final double DELTA = 0.0000000000000005;

	// true;
	public static final String[] TRUE_VALUES = new String[] {
		"true",
		"en",
		"enable",
		"enabled",
		"yes",
		"on",
	};
	public static final char[] T_VALUES = new char[] {
		'1',
		't',
		'i',
		'e',
		'y'
	};
	// false
	public static final String[] FALSE_VALUES = new String[] {
		"false",
		"dis",
		"disable",
		"disabled",
		"no",
		"off"
	};
	public static final char[] F_VALUES = new char[] {
		'0',
		'f',
		'o',
		'd',
		'n'
	};



	// -------------------------------------------------------------------------------
	// equals



	public static boolean EqualsExact(final Integer a, final Integer b) {
		if (a == null && b == null) return true;
		if (a == null || b == null) return false;
		return a.intValue() == b.intValue();
	}
	public static boolean EqualsExact(final Boolean a, final Boolean b) {
		if (a == null && b == null) return true;
		if (a == null || b == null) return false;
		return ( a.booleanValue() == b.booleanValue() );
	}
	public static boolean EqualsExact(final Byte a, final Byte b) {
		if (a == null && b == null) return true;
		if (a == null || b == null) return false;
		return ( a.byteValue() == b.byteValue() );
	}
	public static boolean EqualsExact(final Short a, final Short b) {
		if (a == null && b == null) return true;
		if (a == null || b == null) return false;
		return ( a.shortValue() == b.shortValue() );
	}
	public static boolean EqualsExact(final Long a, final Long b) {
		if (a == null && b == null) return true;
		if (a == null || b == null) return false;
		return ( a.longValue() == b.longValue() );
	}
	public static boolean EqualsExact(final Double a, final Double b) {
		if (a == null && b == null) return true;
		if (a == null || b == null) return false;
		return ( a.doubleValue() == b.doubleValue() );
	}
	public static boolean EqualsExact(final Float a, final Float b) {
		if (a == null && b == null) return true;
		if (a == null || b == null) return false;
		return ( a.floatValue() == b.floatValue() );
	}



	// -------------------------------------------------------------------------------
	// convert



	public static int BitValue(final int bit) {
		int result = 1;
		for (int i=0; i<bit; i++)
			result *= 2;
		return result;
	}



	public static int SafeLongToInt(final long value) {
		return (int) MinMax(value, (long)Integer.MIN_VALUE, (long)Integer.MAX_VALUE);
	}



	// is number
	public static boolean IsNumeric(final String value) {
		if (IsEmpty(value)) return false;
		return (ToLong(value) != null);
	}
	// is boolean
	public static boolean IsBoolean(final String value) {
		return (ToBoolean(value) != null);
	}



	// parse integer
	public static Integer ToInteger(final String value) {
		if (IsEmpty(value)) return null;
		try {
			return Integer.valueOf( Integer.parseInt(value) );
		} catch (NumberFormatException ignore) {}
		return null;
	}
	public static int ToInteger(final String value, final int def) {
		if (value != null) {
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException ignore) {}
		}
		return def;
	}
	public static Integer CastInteger(final Object obj) {
		if (obj == null) return null;
		if (obj instanceof Integer) return (Integer) obj;
		if (obj instanceof Long   ) return Integer.valueOf( (int)Math.round( ((Long  )obj).longValue()   ) );
		if (obj instanceof Double ) return Integer.valueOf( (int)Math.round( ((Double)obj).doubleValue() ) );
		if (obj instanceof Float  ) return Integer.valueOf( (int)Math.round( ((Float )obj).floatValue()  ) );
		if (obj instanceof Short  ) return Integer.valueOf( (int)Math.round( ((Short )obj).shortValue()  ) );
		if (obj instanceof Byte   ) return Integer.valueOf( (int)Math.round( ((Byte  )obj).byteValue()   ) );
		throw new IllegalArgumentException("Invalid integer value: "+obj.toString());
	}
	public static int CastInt(final Object obj, final int def) {
		final Integer result = CastInteger(obj);
		return (result==null ? def : result.intValue());
	}



	// parse byte
	public static Byte ToByte(final String value) {
		if (IsEmpty(value)) return null;
		try {
			return Byte.valueOf( Byte.parseByte(value) );
		} catch (NumberFormatException ignore) {}
		return null;
	}
	public static byte ToByte(final String value, final byte def) {
		if (value != null) {
			try {
				return Byte.parseByte(value);
			} catch (NumberFormatException ignore) {}
		}
		return def;
	}
	public static Byte CastByte(final Object obj) {
		if (obj == null) return null;
		if (obj instanceof Byte   ) return (Byte) obj;
		if (obj instanceof Integer) return Byte.valueOf( (byte)Math.round( ((Integer)obj).intValue()    ) );
		if (obj instanceof Long   ) return Byte.valueOf( (byte)Math.round( ((Long   )obj).longValue()   ) );
		if (obj instanceof Double ) return Byte.valueOf( (byte)Math.round( ((Double )obj).doubleValue() ) );
		if (obj instanceof Float  ) return Byte.valueOf( (byte)Math.round( ((Float  )obj).floatValue()  ) );
		if (obj instanceof Short  ) return Byte.valueOf( (byte)Math.round( ((Short  )obj).shortValue()  ) );
		throw new IllegalArgumentException("Invalid byte value: "+obj.toString());
	}
	public static byte CastByt(final Object obj, final byte def) {
		final Byte result = CastByte(obj);
		return (result==null ? def : result.byteValue());
	}



	// parse short
	public static Short ToShort(final String value) {
		if (IsEmpty(value)) return null;
		try {
			return Short.valueOf( Short.parseShort(value) );
		} catch (NumberFormatException ignore) {}
		return null;
	}
	public static short ToShort(final String value, final short def) {
		if (value != null) {
			try {
				return Short.parseShort(value);
			} catch (NumberFormatException ignore) {}
		}
		return def;
	}
	public static Short CastShort(final Object obj) {
		if (obj == null) return null;
		if (obj instanceof Short  ) return (Short) obj;
		if (obj instanceof Integer) return Short.valueOf( (short)Math.round( ((Integer)obj).intValue()    ) );
		if (obj instanceof Long   ) return Short.valueOf( (short)Math.round( ((Long   )obj).longValue()   ) );
		if (obj instanceof Double ) return Short.valueOf( (short)Math.round( ((Double )obj).doubleValue() ) );
		if (obj instanceof Float  ) return Short.valueOf( (short)Math.round( ((Float  )obj).floatValue()  ) );
		if (obj instanceof Byte   ) return Short.valueOf( (short)Math.round( ((Byte   )obj).byteValue()   ) );
		throw new IllegalArgumentException("Invalid short value: "+obj.toString());
	}
	public static short CastShrt(final Object obj, final short def) {
		final Short result = CastShort(obj);
		return (result==null ? def : result.shortValue());
	}



	// parse long
	public static Long ToLong(final String value) {
		if (IsEmpty(value)) return null;
		try {
			return Long.valueOf( Long.parseLong(value) );
		} catch (NumberFormatException ignore) {}
		return null;
	}
	public static long ToLong(final String value, final long def) {
		if (value != null) {
			try {
				return Long.parseLong(value);
			} catch (NumberFormatException ignore) {}
		}
		return def;
	}
	public static Long CastLong(final Object obj) {
		if (obj == null) return null;
		if (obj instanceof Long) return (Long) obj;
		if (obj instanceof Integer) return Long.valueOf( (long)Math.round( ((Integer)obj).intValue()    ) );
		if (obj instanceof Double ) return Long.valueOf( (long)Math.round( ((Double )obj).doubleValue() ) );
		if (obj instanceof Float  ) return Long.valueOf( (long)Math.round( ((Float  )obj).floatValue()  ) );
		if (obj instanceof Short  ) return Long.valueOf( (long)Math.round( ((Short  )obj).shortValue()  ) );
		if (obj instanceof Byte   ) return Long.valueOf( (long)Math.round( ((Byte   )obj).byteValue()   ) );
		throw new IllegalArgumentException("Invalid long value: "+obj.toString());
	}
	public static long CastLng(final Object obj, final long def) {
		final Long result = CastLong(obj);
		return (result==null ? def : result.longValue());
	}



	// parse double
	public static Double ToDouble(final String value) {
		if (IsEmpty(value)) return null;
		try {
			return Double.valueOf( Double.parseDouble(value) );
		} catch (NumberFormatException ignore) {}
		return null;
	}
	public static double ToDouble(final String value, final double def) {
		if (value != null) {
			try {
				return Double.parseDouble(value);
			} catch (NumberFormatException ignore) {}
		}
		return def;
	}
	public static Double CastDouble(final Object obj) {
		if (obj == null) return null;
		if (obj instanceof Double ) return (Double) obj;
		if (obj instanceof Integer) return Double.valueOf( (double)Math.round( ((Integer)obj).intValue()    ) );
		if (obj instanceof Long   ) return Double.valueOf( (double)Math.round( ((Long   )obj).longValue()   ) );
		if (obj instanceof Float  ) return Double.valueOf( (double)Math.round( ((Float  )obj).floatValue()  ) );
		if (obj instanceof Short  ) return Double.valueOf( (double)Math.round( ((Short  )obj).shortValue()  ) );
		if (obj instanceof Byte   ) return Double.valueOf( (double)Math.round( ((Byte   )obj).byteValue()   ) );
		throw new IllegalArgumentException("Invalid double value: "+obj.toString());
	}
	public static double CastDbl(final Object obj, final double def) {
		final Double result = CastDouble(obj);
		return (result==null ? def : result.doubleValue());
	}



	// parse float
	public static Float ToFloat(final String value) {
		if (IsEmpty(value)) return null;
		try {
			return Float.valueOf( Float.parseFloat(value) );
		} catch (NumberFormatException ignore) {}
		return null;
	}
	public static float ToFloat(final String value, final float def) {
		if (value != null) {
			try {
				return Float.parseFloat(value);
			} catch (NumberFormatException ignore) {}
		}
		return def;
	}
	public static Float CastFloat(final Object obj) {
		if (obj == null) return null;
		if (obj instanceof Float) return (Float) obj;
		if (obj instanceof Integer) return Float.valueOf( (float)Math.round( ((Integer)obj).intValue()    ) );
		if (obj instanceof Long   ) return Float.valueOf( (float)Math.round( ((Long   )obj).longValue()   ) );
		if (obj instanceof Double ) return Float.valueOf( (float)Math.round( ((Double )obj).doubleValue() ) );
		if (obj instanceof Short  ) return Float.valueOf( (float)Math.round( ((Short  )obj).shortValue()  ) );
		if (obj instanceof Byte   ) return Float.valueOf( (float)Math.round( ((Byte   )obj).byteValue()   ) );
		throw new IllegalArgumentException("Invalid float value: "+obj.toString());
	}
	public static float CastFlt(final Object obj, final float def) {
		final Float result = CastFloat(obj);
		return (result==null ? def : result.floatValue());
	}



	// parse boolean
	public static Boolean ToBoolean(final String value) {
		if (IsEmpty(value)) return null;
		final String val = value.trim().toLowerCase();
		for (final String v : TRUE_VALUES) {
			if (val.equals(v))
				return Boolean.TRUE;
		}
		for (final String v : FALSE_VALUES) {
			if (val.equals(v))
				return Boolean.FALSE;
		}
		final char chr = val.charAt(0);
		for (final char c : T_VALUES) {
			if (chr == c)
				return Boolean.TRUE;
		}
		for (final char c : F_VALUES) {
			if (chr == c)
				return Boolean.FALSE;
		}
		return null;
	}
	public static boolean ToBoolean(final String value, final boolean def) {
		if (value == null) return def;
		final Boolean bool = ToBoolean(value);
		if (bool == null) return def;
		return bool.booleanValue();
	}
	public static Boolean CastBoolean(final Object obj) {
		if (obj == null)
			return null;
		if (obj instanceof Boolean)
			return (Boolean) obj;
		if (obj instanceof Integer)
			return Boolean.valueOf( ((Integer)obj).intValue() != 0 );
		if (obj instanceof String) {
			final String str = (String) obj;
			for (final String entry : TRUE_VALUES) {
				if (entry.equalsIgnoreCase(str))
					return Boolean.TRUE;
			}
			for (final String entry : FALSE_VALUES) {
				if (entry.equalsIgnoreCase(str))
					return Boolean.FALSE;
			}
			final char first = str.charAt(0);
			for (final char chr : T_VALUES) {
				if (chr == first)
					return Boolean.TRUE;
			}
			for (final char chr : F_VALUES) {
				if (chr == first)
					return Boolean.FALSE;
			}
		}
		throw new IllegalArgumentException("Invalid boolean value: "+obj.toString());
	}
	public static boolean CastBool(final Object obj, final boolean def) {
		final Boolean result = CastBoolean(obj);
		return (result==null ? def : result.booleanValue());
	}



	// -------------------------------------------------------------------------------
	// format



	// formatDecimal("0.00", double)
	public static String FormatDecimal(final String format, final double value) {
		return (new DecimalFormat(format)
				.format(value));
	}
	// formatDecimal("0.00", float)
	public static String FormatDecimal(final String format, final float value) {
		return (new DecimalFormat(format)
				.format(value));
	}



	public static String PadZeros(final int value, final int len) {
		final String str = Integer.toString(value);
		final int zeros = len - str.length();
		if (zeros < 1)
			return str;
		return StringUtils.Repeat(zeros, '0');
	}



	// -------------------------------------------------------------------------------
	// min/max



	public static int Min(final int...values) {
		final int num = values.length;
		if (num == 0) throw new RequiredArgumentException("values");
		if (num == 1) return values[0];
		int min = values[0];
		for (int i=1; i<num; i++) {
			if (min > values[i])
				min = values[i];
		}
		return min;
	}
	public static long Min(final long...values) {
		final int num = values.length;
		if (num == 0) throw new RequiredArgumentException("values");
		if (num == 1) return values[0];
		long min = values[0];
		for (int i=1; i<num; i++) {
			if (min > values[i])
				min = values[i];
		}
		return min;
	}
	public static float Min(final float...values) {
		final int num = values.length;
		if (num == 0) throw new RequiredArgumentException("values");
		if (num == 1) return values[0];
		float min = values[0];
		for (int i=1; i<num; i++) {
			if (min > values[i])
				min = values[i];
		}
		return min;
	}
	public static double Min(final double...values) {
		final int num = values.length;
		if (num == 0) throw new RequiredArgumentException("values");
		if (num == 1) return values[0];
		double min = values[0];
		for (int i=1; i<num; i++) {
			if (min > values[i])
				min = values[i];
		}
		return min;
	}



	public static int Max(final int...values) {
		final int num = values.length;
		if (num == 0) throw new RequiredArgumentException("values");
		if (num == 1) return values[0];
		int max = values[0];
		for (int i=1; i<num; i++) {
			if (max < values[i])
				max = values[i];
		}
		return max;
	}
	public static long Max(final long...values) {
		final int num = values.length;
		if (num == 0) throw new RequiredArgumentException("values");
		if (num == 1) return values[0];
		long max = values[0];
		for (int i=1; i<num; i++) {
			if (max < values[i])
				max = values[i];
		}
		return max;
	}
	public static float Max(final float...values) {
		final int num = values.length;
		if (num == 0) throw new RequiredArgumentException("values");
		if (num == 1) return values[0];
		float max = values[0];
		for (int i=1; i<num; i++) {
			if (max < values[i])
				max = values[i];
		}
		return max;
	}
	public static double Max(final double...values) {
		final int num = values.length;
		if (num == 0) throw new RequiredArgumentException("values");
		if (num == 1) return values[0];
		double max = values[0];
		for (int i=1; i<num; i++) {
			if (max < values[i])
				max = values[i];
		}
		return max;
	}



	public static int MinMax(final int value, final int min, final int max) {
		if (min   > max) throw new IllegalArgumentException("min cannot be greater than max");
		if (value < min) return min;
		if (value > max) return max;
		return value;
	}
	public static byte MinMax(final byte value, final byte min, final byte max) {
		if (min   > max) throw new IllegalArgumentException("min cannot be greater than max");
		if (value < min) return min;
		if (value > max) return max;
		return value;
	}
	public static short MinMax(final short value, final short min, final short max) {
		if (min   > max) throw new IllegalArgumentException("min cannot be greater than max");
		if (value < min) return min;
		if (value > max) return max;
		return value;
	}
	public static long MinMax(final long value, final long min, final long max) {
		if (min   > max) throw new IllegalArgumentException("min cannot be greater than max");
		if (value < min) return min;
		if (value > max) return max;
		return value;
	}
	public static double MinMax(final double value, final double min, final double max) {
		if (min   > max) throw new IllegalArgumentException("min cannot be greater than max");
		if (value < min) return min;
		if (value > max) return max;
		return value;
	}
	public static float MinMax(final float value, final float min, final float max) {
		if (min   > max) throw new IllegalArgumentException("min cannot be greater than max");
		if (value < min) return min;
		if (value > max) return max;
		return value;
	}



	// is within range
	public static boolean IsMinMax(final int value, final int min, final int max) {
		return (value >= min && value <= max);
	}
	public static boolean IsMinMax(final byte value, final byte min, final byte max) {
		return (value >= min && value <= max);
	}
	public static boolean IsMinMax(final short value, final short min, final short max) {
		return (value >= min && value <= max);
	}
	public static boolean IsMinMax(final long value, final long min, final long max) {
		return (value >= min && value <= max);
	}
	public static boolean IsMinMax(final double value, final double min, final double max) {
		return (value >= min && value <= max);
	}
	public static boolean IsMinMax(final float value, final float min, final float max) {
		return (value >= min && value <= max);
	}



	// -------------------------------------------------------------------------------
	// round



	// double
	public static double RoundNormal(final double value, final double product) {
		return Math.round( value / product ) * product;
	}
	public static double FloorNormal(final double value, final double product) {
		return Math.floor( value / product ) * product;
	}
	public static double CeilNormal(final double value, final double product) {
		return Math.ceil( value / product ) * product;
	}



	// integer
	public static int RoundNormal(final int value, final int product) {
		return (int) RoundNormal( (double) value, (double) product );
	}
	public static int FloorNormal(final int value, final int product) {
		return (int) FloorNormal( (double) value, (double) product );
	}
	public static int CeilNormal(final int value, final int product) {
		return (int) CeilNormal( (double) value, (double) product );
	}



	// long
	public static long RoundNormal(final long value, final int product) {
		return (long) RoundNormal( (double) value, (double) product );
	}
	public static long FloorNormal(final long value, final int product) {
		return (long) FloorNormal( (double) value, (double) product );
	}
	public static long CeilNormal(final long value, final int product) {
		return (long) CeilNormal( (double) value, (double) product );
	}



	// -------------------------------------------------------------------------------
	// time



	public static boolean IsDayTimeBetween(final long current, final long start, final long stop, final int zone) {
		return _IsDayTimeBetween(
			TimestampSinceMidnight(current, zone),
			MinMax(start, 0, 86399999),
			MinMax(stop,  0, 86399999)
		);
	}
	protected static boolean _IsDayTimeBetween(final long current, final long start, final long stop) {
		if (start < stop) return (current >= start) && (current < stop); // normal case
		else              return (current >= start) || (current < stop); // crossing midnight
	}



	public static long TimestampSinceMidnight(final long timestamp, final int zone) {
		final Instant   instant = Instant.ofEpochMilli(timestamp);
		final LocalTime current = instant.atZone(ZoneOffset.ofHours(zone)).toLocalTime();
		return current.toNanoOfDay() / 1000000L;
	}



	// -------------------------------------------------------------------------------
	// distance

	//        |   2D   |   3D
	//-----------------------------
	// circle | Radial | Vectorial
	// square | Linear | Axial
	// hybrid | Hybrid | Hybrid



	// circular 2D
	public static double DistanceRadial(
			final int x1, final int z1,
			final int x2, final int z2) {
		return Math.sqrt(
			(double) Square(x1 - x2) +
			(double) Square(z1 - z2)
		);
	}
	public static double DistanceRadial(
			final long x1, final long z1,
			final long x2, final long z2) {
		return Math.sqrt(
			(double) Square(x1 - x2) +
			(double) Square(z1 - z2)
		);
	}
	public static float DistanceRadial(
			final float x1, final float z1,
			final float x2, final float z2) {
		return (float)Math.sqrt(
			Square(x1 - x2) +
			Square(z1 - z2)
		);
	}
	public static double DistanceRadial(
			final double x1, final double z1,
			final double x2, final double z2) {
		return Math.sqrt(
			Square(x1 - x2) +
			Square(z1 - z2)
		);
	}

	// circular 3D
	public static double DistanceVectorial(
			final int x1, final int y1, final int z1,
			final int x2, final int y2, final int z2) {
		return Math.sqrt(
			(double) Square(x1 - x2) +
			(double) Square(y1 - y2) +
			(double) Square(z1 - z2)
		);
	}
	public static double DistanceVectorial(
			final long x1, final long y1, final long z1,
			final long x2, final long y2, final long z2) {
		return Math.sqrt(
			(double) Square(x1 - x2) +
			(double) Square(y1 - y2) +
			(double) Square(z1 - z2)
		);
	}
	public static float DistanceVectorial(
			final float x1, final float y1, final float z1,
			final float x2, final float y2, final float z2) {
		return (float)Math.sqrt(
			Square(x1 - x2) +
			Square(y1 - y2) +
			Square(z1 - z2)
		);
	}
	public static double DistanceVectorial(
			final double x1, final double y1, final double z1,
			final double x2, final double y2, final double z2) {
		return Math.sqrt(
			Square(x1 - x2) +
			Square(y1 - y2) +
			Square(z1 - z2)
		);
	}



	// square 2D
	public static double DistanceLinear(
			final int x1, final int z1,
			final int x2, final int z2) {
		return Max(
			(double) Math.abs(x1 - x2),
			(double) Math.abs(z1 - z2)
		);
	}
	public static double DistanceLinear(
			final long x1, final long z1,
			final long x2, final long z2) {
		return Max(
			(double) Math.abs(x1 - x2),
			(double) Math.abs(z1 - z2)
		);
	}
	public static float DistanceLinear(
			final float x1, final float z1,
			final float x2, final float z2) {
		return Max(
			Math.abs(x1 - x2),
			Math.abs(z1 - z2)
		);
	}
	public static double DistanceLinear(
			final double x1, final double z1,
			final double x2, final double z2) {
		return Max(
			Math.abs(x1 - x2),
			Math.abs(z1 - z2)
		);
	}

	// square 3D
	public static double DistanceAxial(
			final int x1, final int y1, final int z1,
			final int x2, final int y2, final int z2) {
		return Max(
			(double) Math.abs(x1 - x2),
			(double) Math.abs(y1 - y2),
			(double) Math.abs(z1 - z2)
		);
	}
	public static double DistanceAxial(
			final long x1, final long y1, final long z1,
			final long x2, final long y2, final long z2) {
		return Max(
			(double) Math.abs(x1 - x2),
			(double) Math.abs(y1 - y2),
			(double) Math.abs(z1 - z2)
		);
	}
	public static float DistanceAxial(
			final float x1, final float y1, final float z1,
			final float x2, final float y2, final float z2) {
		return Max(
			Math.abs(x1 - x2),
			Math.abs(y1 - y2),
			Math.abs(z1 - z2)
		);
	}
	public static double DistanceAxial(
			final double x1, final double y1, final double z1,
			final double x2, final double y2, final double z2) {
		return Max(
			Math.abs(x1 - x2),
			Math.abs(y1 - y2),
			Math.abs(z1 - z2)
		);
	}



	// hybrid 2D
	public static double DistanceHybrid2D(final double shape,
			final int x1, final int z1,
			final int x2, final int z2) {
		return Remap(
			DistanceLinear(x1, z1, x2, z2),
			DistanceRadial(x1, z1, x2, z2),
			shape
		);
	}
	public static double DistanceHybrid2D(final double shape,
			final long x1, final long z1,
			final long x2, final long z2) {
		return Remap(
			DistanceLinear(x1, z1, x2, z2),
			DistanceRadial(x1, z1, x2, z2),
			shape
		);
	}
	public static float DistanceHybrid2D(final float shape,
			final float x1, final float z1,
			final float x2, final float z2) {
		return Remap(
			DistanceLinear(x1, z1, x2, z2),
			DistanceRadial(x1, z1, x2, z2),
			shape
		);
	}
	public static double DistanceHybrid2D(final double shape,
			final double x1, final double z1,
			final double x2, final double z2) {
		return Remap(
			DistanceLinear(x1, z1, x2, z2),
			DistanceRadial(x1, z1, x2, z2),
			shape
		);
	}

	// hybrid 3D
	public static double DistanceHybrid3D(final double shape,
			final int x1, final int y1, final int z1,
			final int x2, final int y2, final int z2) {
		return Remap(
			DistanceAxial(    x1, y1, z1, x2, y2, z2),
			DistanceVectorial(x1, y1, z1, x2, y2, z2),
			shape
		);
	}
	public static double DistanceHybrid3D(final double shape,
			final long x1, final long y1, final long z1,
			final long x2, final long y2, final long z2) {
		return Remap(
			DistanceAxial(    x1, y1, z1, x2, y2, z2),
			DistanceVectorial(x1, y1, z1, x2, y2, z2),
			shape
		);
	}
	public static float DistanceHybrid3D(final float shape,
			final float x1, final float y1, final float z1,
			final float x2, final float y2, final float z2) {
		return Remap(
			DistanceAxial(    x1, y1, z1, x2, y2, z2),
			DistanceVectorial(x1, y1, z1, x2, y2, z2),
			shape
		);
	}
	public static double DistanceHybrid3D(final double shape,
			final double x1, final double y1, final double z1,
			final double x2, final double y2, final double z2) {
		return Remap(
			DistanceAxial(    x1, y1, z1, x2, y2, z2),
			DistanceVectorial(x1, y1, z1, x2, y2, z2),
			shape
		);
	}



	// -------------------------------------------------------------------------------
	// square/cube



	public static int Square(final int value) {
		return value * value;
	}
	public static long Square(final long value) {
		return value * value;
	}
	public static float Square(final float value) {
		return value * value;
	}
	public static double Square(final double value) {
		return value * value;
	}



	public static int Cube(final int value) {
		return value * value * value;
	}
	public static long Cube(final long value) {
		return value * value * value;
	}
	public static float Cube(final float value) {
		return value * value * value;
	}
	public static double Cube(final double value) {
		return value * value * value;
	}



	// -------------------------------------------------------------------------------
	// rotation



	public static Dab Rotate2D(final double x, final double y,
			final double angle) {
		return Rotate2D(x, y, 0, 0, angle);
	}
	public static Dab Rotate2D(final double x, final double y,
			final double center_x, final double center_y,
			final double angle) {
		final double tx = x - center_x;
		final double ty = y - center_y;
		final double ang = Math.PI * angle;
		final double sin = Math.sin(ang);
		final double cos = Math.cos(ang);
		final double rot_x = (cos * tx) - (sin * ty);
		final double rot_y = (sin * tx) + (cos * ty);
		return new Dab(
			rot_x + center_x,
			rot_y + center_y
		);
	}

	public static Dabc Rotate3D(final double x, final double y, final double z,
			final double yawing, final double pitch) {
		return Rotate3D(x, y, z, 0, 0, 0, yawing, pitch);
	}
	public static Dabc Rotate3D(final double x, final double y, final double z,
			final double center_x, final double center_y, final double center_z,
			final double yawing, final double pitch) {
		final double xx = x - center_x;
		final double yy = y - center_y;
		final double zz = z - center_z;
		final double yaw = Math.PI * yawing;
		final double pit = Math.PI * pitch;
		final double sin_yaw = Math.sin(yaw); final double cos_yaw = Math.cos(yaw);
		final double sin_pit = Math.sin(pit); final double cos_pit = Math.cos(pit);
		double rot_x, rot_y, rot_z, tmp_x;
		// pitch - z axis
		tmp_x = (cos_pit * xx) - (sin_pit * yy);
		rot_y = (sin_pit * xx) + (cos_pit * yy);
		// yaw - y axis
		rot_x = (cos_yaw * tmp_x) + (sin_yaw * zz);
		rot_z = (cos_yaw * zz   ) - (sin_yaw * tmp_x);
		return new Dabc(
			rot_x + center_x,
			rot_y + center_y,
			rot_z + center_z
		);
	}



	public static Fab Rotate2D(final float x, final float y,
			final float angle) {
		return Fab.From(Rotate2D(
			(double) x, (double) y,
			(double) angle
		));
	}
	public static Fab Rotate2D(final float x, final float y,
			final float center_x, final float center_y,
			final float angle) {
		return Fab.From(Rotate2D(
			(double) x, (double) y,
			(double) center_x, (double) center_y,
			(double) angle
		));
	}

	public static Fabc Rotate3D(final float x, final float y, final float z,
			final float yawing, final float pitch) {
		return Fabc.From(Rotate3D(
			(double) x, (double) y, (double) z,
			(double) yawing, (double) pitch
		));
	}
	public static Fabc Rotate3D(final float x, final float y, final float z,
			final float center_x, final float center_y, final float center_z,
			final float yawing, final float pitch) {
		return Fabc.From(Rotate3D(
			(double) x, (double) y, (double) z,
			(double) center_x, (double) center_y, (double) center_z,
			(double) yawing, (double) pitch
		));
	}



	// -------------------------------------------------------------------------------
	// remap number between ranges



	public static int Remap(
			final int lowA, final int highA,
			final int lowB, final int highB,
			final int value) {
		return Remap(lowA, highA, lowB, highB, value, 1.0);
	}
	public static long Remap(
			final long lowA, final long highA,
			final long lowB, final long highB,
			final long value) {
		return Remap(lowA, highA, lowB, highB, value, 1.0);
	}
	public static float Remap(
			final float lowA, final float highA,
			final float lowB, final float highB,
			final float value) {
		return Remap(lowA, highA, lowB, highB, value, 1.0f);
	}
	public static double Remap(
			final double lowA, final double highA,
			final double lowB, final double highB,
			final double value) {
		return Remap(lowA, highA, lowB, highB, value, 1.0);
	}

	public static int Remap(
			final int lowA, final int highA,
			final int lowB, final int highB,
			final int value, final double weight) {
		final double normal = ((double)(value-lowA)) / ((double)(highA-lowA));
		return ((int)(Math.pow(normal, 1.0/weight) * (highB-lowB))) + lowB;
	}
	public static long Remap(
			final long lowA, final long highA,
			final long lowB, final long highB,
			final long value, final double weight) {
		final double normal = ((double)(value-lowA)) / ((double)(highA-lowA));
		return ((long)(Math.pow(normal, 1.0/weight) * (highB-lowB))) + lowB;
	}
	public static float Remap(
			final float lowA, final float highA,
			final float lowB, final float highB,
			final float value, final float weight) {
		final float normal = (value-lowA) / (highA-lowA);
		return ( ((float)Math.pow(normal, 1.0f/weight)) * (highB-lowB) ) + lowB;
	}
	public static double Remap(
			final double lowA, final double highA,
			final double lowB, final double highB,
			final double value, final double weight) {
		final double normal = (value-lowA) / (highA-lowA);
		return (Math.pow(normal, 1.0/weight) * (highB-lowB)) + lowB;
	}



	public static int Remap(final int low, final int high, final double percent) {
		return Remap(low, high, percent, 1.0);
	}
	public static long Remap(final long low, final long high, final double percent) {
		return Remap(low, high, percent, 1.0);
	}
	public static float Remap(final float low, final float high, final float percent) {
		return Remap(low, high, percent, 1.0f);
	}
	public static double Remap(final double low, final double high, final double percent) {
		return Remap(low, high, percent, 1.0);
	}

	public static int Remap(final int low, final int high, final double percent, final double weight) {
		return (int) Math.round(
			((double)(high - low)) * Math.pow(percent, 1.0/weight)
		) + low;
	}
	public static long Remap(final long low, final long high, final double percent, final double weight) {
		return (long) Math.round(
			((double)(high - low)) * Math.pow(percent, 1.0/weight)
		) + low;
	}
	public static float Remap(final float low, final float high, final float percent, final float weight) {
		return (high - low) * ((float)Math.pow(percent, 1.0f/weight)) + low;
	}
	public static double Remap(final double low, final double high, final double percent, final double weight) {
		return (high - low) * Math.pow(percent, 1.0/weight) + low;
	}



	public static Color Remap(final Color colorA, final Color colorB, final double percent) {
		return
			new Color(
				MinMax(Remap(colorA.getRed(),   colorB.getRed(),   percent), 0, 255),
				MinMax(Remap(colorA.getGreen(), colorB.getGreen(), percent), 0, 255),
				MinMax(Remap(colorA.getBlue(),  colorB.getBlue(),  percent), 0, 255)
			);
	}



	// rrrgggbb
	public static Color Remap8BitColor(final int value) {
		final int r = (value & 0B00000111);
		final int g = (value & 0B00111000) >> 3;
		final int b = (value & 0B11000000) >> 6;
		return
			new Color(
				MinMax( Remap(0, 7, 0, 255, r), 0, 255),
				MinMax( Remap(0, 7, 0, 255, g), 0, 255),
				MinMax( Remap(0, 3, 0, 255, b), 0, 255)
			);
	}



	// -------------------------------------------------------------------------------
	// interleave



	public static int ZOrderInterleave(final int value, final int minor, final int major) {
		final int total = minor * major;
		return
			(Math.floorDiv(value, total) * total)   + // total offset
			Math.floorDiv( (value % total), minor ) + // group offset
			((value % minor) * major);                // step offset
	}



	// -------------------------------------------------------------------------------
	// interpolation



/*
	public static double CubicInterpolate(final double p,
			final double a, final double b, final double c, final double d) {
		final double e = (d - c - a) + b;
		final double f = a - b - e;
		final double g = c - a;
		return (e * p*p*p) + (f * p*p) + (g * p) + b;
	}

	public static double CubicInterpolateValues(final double[] values, final int index, final double p) {
		return CubicInterpolate(
			p,
			GetSafeOrNear(values, index-1),
			GetSafeOrNear(values, index  ),
			GetSafeOrNear(values, index+1),
			GetSafeOrNear(values, index+2)
		);
	}
	public static double CubicInterpolateLoopedValues(final double[] values, final int index, final double p) {
		return CubicInterpolate(
			p,
			GetSafeLooped(values, index-1),
			GetSafeLooped(values, index  ),
			GetSafeLooped(values, index+1),
			GetSafeLooped(values, index+2)
		);
	}
	public static double CubicInterpolateCircularValues(final double[] values, final int index, final double p) {
		return CubicInterpolate(
			p,
			GetSafeLoopExtend(values, index-1),
			GetSafeLoopExtend(values, index  ),
			GetSafeLoopExtend(values, index+1),
			GetSafeLoopExtend(values, index+2)
		);
	}
*/



}
