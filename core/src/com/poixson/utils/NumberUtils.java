package com.poixson.utils;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.Random;

import com.poixson.tools.Keeper;


public final class NumberUtils {
	private NumberUtils() {}
	static { Keeper.add(new NumberUtils()); }

	/**
	 * Max valid tcp/udp port number.
	 */
	public static final int MAX_PORT = 65535;



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



	public static int BitValue(final int bit) {
		int result = 1;
		for (int i=0; i<bit; i++)
			result *= 2;
		return result;
	}



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
	// round



	// double
	public static double Round(final double value, final double product) {
		final double val = Math.round( value / product );
		return val * product;
	}
	public static double Floor(final double value, final double product) {
		final double val = Math.floor( value / product );
		return val * product;
	}
	public static double Ceil(final double value, final double product) {
		final double val = Math.ceil( value / product );
		return val * product;
	}



	// integer
	public static int Round(final int value, final int product) {
		return (int) Round( (double) value, (double) product );
	}
	public static int Floor(final int value, final int product) {
		return (int) Floor( (double) value, (double) product );
	}
	public static int Ceil(final int value, final int product) {
		return (int) Ceil( (double) value, (double) product );
	}



	// long
	public static long Round(final long value, final int product) {
		return (long) Round( (double) value, (double) product );
	}
	public static long Floor(final long value, final int product) {
		return (long) Floor( (double) value, (double) product );
	}
	public static long Ceil(final long value, final int product) {
		return (long) Ceil( (double) value, (double) product );
	}



	// -------------------------------------------------------------------------------
	// convert



	public static int SafeLongToInt(final long value) {
		// < int min
		if ( value < ((long)Integer.MIN_VALUE) )
			return Integer.MIN_VALUE;
		// > int max
		if ( value > ((long)Integer.MAX_VALUE) )
			return Integer.MAX_VALUE;
		// within int range
		return (int) value;
	}



	// is number
	public static boolean IsNumeric(final String value) {
		if (Utils.isEmpty(value)) return false;
		return (ToLong(value) != null);
	}
	// is boolean
	public static boolean IsBoolean(final String value) {
		return (ToBoolean(value) != null);
	}



	// parse number
	public static Integer ToInteger(final String value) {
		if (Utils.isEmpty(value)) return null;
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
		if (Utils.isEmpty(value)) return null;
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
		if (Utils.isEmpty(value)) return null;
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
		if (Utils.isEmpty(value)) return null;
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
		if (Utils.isEmpty(value)) return null;
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
		if (Utils.isEmpty(value)) return null;
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
		if (Utils.isEmpty(value)) return null;
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
			return ( ((Integer)obj).intValue() != 0 );
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



	// -------------------------------------------------------------------------------
	// min/max



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
		return (value == MinMax(value, min, max));
	}
	public static boolean IsMinMax(final byte value, final byte min, final byte max) {
		return (value == MinMax(value, min, max));
	}
	public static boolean IsMinMax(final short value, final short min, final short max) {
		return (value == MinMax(value, min, max));
	}
	public static boolean IsMinMax(final long value, final long min, final long max) {
		return (value == MinMax(value, min, max));
	}
	public static boolean IsMinMax(final double value, final double min, final double max) {
		return (value == MinMax(value, min, max));
	}
	public static boolean IsMinMax(final float value, final float min, final float max) {
		return (value == MinMax(value, min, max));
	}



	// -------------------------------------------------------------------------------
	// remap number between ranges



	public static int Remap(
			final int lowA, final int highA,
			final int lowB, final int highB,
			final int value) {
		final double lA = (double) lowA;
		final double hA = (double) highA;
		final double lB = (double) lowB;
		final double hB = (double) highB;
		double result = (hB - lB) / (hA - lA);
		result *= (value - lA);
		result += lB;
		return (int) result;
	}



	public static int Remap(final int low, final int high, final double percent) {
		double result = ((double)(high - low)) * percent;
		return ((int)result) + low;
	}



	public static Color Remap(final Color colorA, final Color colorB, final double percent) {
		return
			new Color(
				NumberUtils.MinMax(Remap(colorA.getRed(),   colorB.getRed(),   percent), 0, 255),
				NumberUtils.MinMax(Remap(colorA.getGreen(), colorB.getGreen(), percent), 0, 255),
				NumberUtils.MinMax(Remap(colorA.getBlue(),  colorB.getBlue(),  percent), 0, 255)
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
	// random number



//TODO: does this replace HistoryRND class?
	public static int GetRandom(final int minNumber, final int maxNumber) {
		return GetRandom(minNumber, maxNumber, 1L);
	}
	public static int GetRandom(final int minNumber, final int maxNumber, final int seed) {
		return GetRandom(minNumber, maxNumber, ((long)seed));
	}
	public static int GetRandom(final int minNumber, final int maxNumber, final long seed) {
		if (seed == 0L)
			return GetRandom(minNumber, maxNumber, 1L);
		final Random gen = new Random(Utils.GetMS() * seed);
		return gen.nextInt(maxNumber - minNumber) + minNumber;
	}

	public static int GetNewRandom(final int minNumber, final int maxNumber, final int oldNumber) {
		if (minNumber == maxNumber)
			return minNumber;
		if ((maxNumber - minNumber) == 1)
			return (oldNumber == minNumber ? maxNumber : minNumber);
		int newNumber;
		int seed = oldNumber;
		for (int i=0; i<100; i++) {
			newNumber = GetRandom(minNumber, maxNumber, seed);
			if (newNumber != oldNumber)
				return newNumber;
			seed += i;
		}
		throw new IllegalAccessError("Failed to generate a random number");
	}



}
