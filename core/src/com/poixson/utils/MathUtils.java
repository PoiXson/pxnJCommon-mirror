package com.poixson.utils;

import static com.poixson.utils.Utils.IsEmpty;

import java.awt.Color;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneOffset;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.tools.Keeper;


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



	// parse number
	public static Integer ToInteger(final String value) {
		if (IsEmpty(value)) return null;
		try {
			return Integer.valueOf( Integer.parseInt(value) );
		} catch (NumberFormatException ignore) {}
		return null;
	}
	public static int ToInteger(final String value, final int def) {
		if (value == null) return def;
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException ignore) {}
		return def;
	}
	public static Integer CastInteger(final Object obj) {
		if (obj == null) return null;
		if (obj instanceof Integer)
			return (Integer) obj;
		throw new IllegalArgumentException("Invalid integer value: "+obj.toString());
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
		if (value == null) return def;
		try {
			return Byte.parseByte(value);
		} catch (NumberFormatException ignore) {}
		return def;
	}
	public static Byte CastByte(final Object obj) {
		if (obj == null) return null;
		if (obj instanceof Byte)
			return (Byte) obj;
		throw new IllegalArgumentException("Invalid byte value: "+obj.toString());
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
		if (value == null) return def;
		try {
			return Short.parseShort(value);
		} catch (NumberFormatException ignore) {}
		return def;
	}
	public static Short CastShort(final Object obj) {
		if (obj == null) return null;
		if (obj instanceof Short)
			return (Short) obj;
		throw new IllegalArgumentException("Invalid short value: "+obj.toString());
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
		if (value == null) return def;
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException ignore) {}
		return def;
	}
	public static Long CastLong(final Object obj) {
		if (obj == null) return null;
		if (obj instanceof Long)
			return (Long) obj;
		throw new IllegalArgumentException("Invalid long value: "+obj.toString());
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
		if (value == null) return def;
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException ignore) {}
		return def;
	}
	public static Double CastDouble(final Object obj) {
		if (obj == null) return null;
		if (obj instanceof Double)
			return (Double) obj;
		throw new IllegalArgumentException("Invalid double value: "+obj.toString());
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
		if (value == null) return def;
		try {
			return Float.parseFloat(value);
		} catch (NumberFormatException ignore) {}
		return def;
	}
	public static Float CastFloat(final Object obj) {
		if (obj == null) return null;
		if (obj instanceof Float)
			return (Float) obj;
		throw new IllegalArgumentException("Invalid float value: "+obj.toString());
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



	public static double Distance2D(
			final int x1, final int z1,
			final int x2, final int z2) {
		return Math.sqrt(
			(double) Square(x1 - x2) +
			(double) Square(z1 - z2)
		);
	}
	public static double Distance2D(
			final long x1, final long z1,
			final long x2, final long z2) {
		return Math.sqrt(
			(double) Square(x1 - x2) +
			(double) Square(z1 - z2)
		);
	}
	public static double Distance2D(
			final double x1, final double z1,
			final double x2, final double z2) {
		return Math.sqrt(
			Square(x1 - x2) +
			Square(z1 - z2)
		);
	}

	public static double Distance3D(
			final int x1, final int y1, final int z1,
			final int x2, final int y2, final int z2) {
		return Math.sqrt(
			(double) Square(x1 - x2) +
			(double) Square(y1 - y2) +
			(double) Square(z1 - z2)
		);
	}
	public static double Distance3D(
			final long x1, final long y1, final long z1,
			final long x2, final long y2, final long z2) {
		return Math.sqrt(
			(double) Square(x1 - x2) +
			(double) Square(y1 - y2) +
			(double) Square(z1 - z2)
		);
	}
	public static double Distance3D(
			final double x1, final double y1, final double z1,
			final double x2, final double y2, final double z2) {
		return Math.sqrt(
			Square(x1 - x2) +
			Square(y1 - y2) +
			Square(z1 - z2)
		);
	}



	public static double DistanceFast2D(
			final int x1, final int z1,
			final int x2, final int z2) {
		return Max(
			(double) Math.abs(x1 - x2),
			(double) Math.abs(z1 - z2)
		);
	}
	public static double DistanceFast2D(
			final long x1, final long z1,
			final long x2, final long z2) {
		return Max(
			(double) Math.abs(x1 - x2),
			(double) Math.abs(z1 - z2)
		);
	}
	public static double DistanceFast2D(
			final double x1, final double z1,
			final double x2, final double z2) {
		return Max(
			Math.abs(x1 - x2),
			Math.abs(z1 - z2)
		);
	}

	public static double DistanceFast3D(
			final int x1, final int y1, final int z1,
			final int x2, final int y2, final int z2) {
		return Max(
			(double) Math.abs(x1 - x2),
			(double) Math.abs(y1 - y2),
			(double) Math.abs(z1 - z2)
		);
	}
	public static double DistanceFast3D(
			final long x1, final long y1, final long z1,
			final long x2, final long y2, final long z2) {
		return Max(
			(double) Math.abs(x1 - x2),
			(double) Math.abs(y1 - y2),
			(double) Math.abs(z1 - z2)
		);
	}
	public static double DistanceFast3D(
			final double x1, final double y1, final double z1,
			final double x2, final double y2, final double z2) {
		return Max(
			Math.abs(x1 - x2),
			Math.abs(y1 - y2),
			Math.abs(z1 - z2)
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



	public static double RotateX(final double x, final double y, final double angle) {
		final double ang = Math.PI * angle;
		return (Math.sin(ang) * y) - (Math.cos(ang) * x);
	}
	public static double RotateY(final double x, final double y, final double angle) {
		final double ang = Math.PI * angle;
		return (Math.sin(ang) * x) + (Math.cos(ang) * y);
	}

	public static float RotateX(final float x, final float y, final float angle) {
		final float ang = (float)( Math.PI * ((double)angle) );
		return (float) ((Math.sin(ang) * y) - (Math.cos(ang) * x));
	}
	public static float RotateY(final float x, final float y, final float angle) {
		final float ang = (float)( Math.PI * ((double)angle) );
		return (float) ((Math.sin(ang) * x) + (Math.cos(ang) * y));
	}



	// -------------------------------------------------------------------------------
	// remap number between ranges



	public static int Remap(
			final int lowA, final int highA,
			final int lowB, final int highB,
			final int value) {
		final double LA = (double) lowA;
		final double HA = (double) highA;
		final double LB = (double) lowB;
		final double HB = (double) highB;
		double result = (HB - LB) / (HA - LA);
		result *= (value - LA);
		result += LB;
		return (int) Math.round(result);
	}
	public static long Remap(
			final long lowA, final long highA,
			final long lowB, final long highB,
			final long value) {
		final double LA = (double) lowA;
		final double HA = (double) highA;
		final double LB = (double) lowB;
		final double HB = (double) highB;
		double result = (HB - LB) / (HA - LA);
		result *= (value - LA);
		result += LB;
		return (long) Math.round(result);
	}
	public static double Remap(
			final double lowA, final double highA,
			final double lowB, final double highB,
			final double value) {
		final double LA = (double) lowA;
		final double HA = (double) highA;
		final double LB = (double) lowB;
		final double HB = (double) highB;
		double result = (HB - LB) / (HA - LA);
		result *= (value - LA);
		result += LB;
		return result;
	}



	public static int Remap(final int low, final int high, final double percent) {
		final double result = ((double)(high - low)) * percent;
		return (int) Math.round(result) + low;
	}
	public static long Remap(final long low, final long high, final double percent) {
		final double result = ((double)(high - low)) * percent;
		return (long) Math.round(result) + low;
	}
	public static double Remap(final double low, final double high, final double percent) {
		final double result = ((double)(high - low)) * percent;
		return Math.round(result) + low;
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
