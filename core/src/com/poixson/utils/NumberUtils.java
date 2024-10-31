package com.poixson.utils;

import static com.poixson.utils.Utils.IsEmpty;

import java.text.DecimalFormat;

import com.poixson.tools.Keeper;


public final class NumberUtils {
	private NumberUtils() {}
	static { Keeper.add(new NumberUtils()); }

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
		return Math.round( value / product ) * product;
	}
	public static double Floor(final double value, final double product) {
		return Math.floor( value / product ) * product;
	}
	public static double Ceil(final double value, final double product) {
		return Math.ceil( value / product ) * product;
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
		return (int) MathUtils.MinMax(value, (long)Integer.MIN_VALUE, (long)Integer.MAX_VALUE);
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



}
