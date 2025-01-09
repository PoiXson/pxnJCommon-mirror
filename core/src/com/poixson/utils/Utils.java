package com.poixson.utils;

import java.awt.Color;
import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import com.poixson.logger.xLog;
import com.poixson.tools.Keeper;


public final class Utils {
	private Utils() {}
	static { Keeper.add(new Utils()); }



//TODO
//	private static final AtomicInteger jLineVersion = new AtomicInteger(-1);
//	private static final AtomicInteger RxTxVersion  = new AtomicInteger(-1);



	// -------------------------------------------------------------------------------
	// is empty



	// string
	public static boolean IsEmpty(final String str) {
		if (str == null) return true;
		return (str.length() == 0);
	}
	public static String IfEmpty(final String value, final String def) {
		return (IsEmpty(value) ? def : value);
	}



	// string builder
	public static boolean IsEmpty(final StringBuilder str) {
		if (str == null) return true;
		return (str.length() == 0);
	}
	public static StringBuilder IfEmpty(final StringBuilder value, final String def) {
		return (IsEmpty(value) ? (new StringBuilder()).append(def) : value);
	}



	// string array
	public static boolean IsEmpty(final String[] array) {
		if (array == null) return true;
		return (array.length == 0);
	}
	public static String[] IfEmpty(final String[] array, final String[] def) {
		return (IsEmpty(array) ? def : array);
	}



	// object
	public static <T extends Object> T IfEmpty(final T obj, final T def) {
		return (obj == null ? def : obj);
	}



	// object array
	public static boolean IsEmpty(final Object[] array) {
		if (array == null) return true;
		return (array.length == 0);
	}
	public static <T extends Object> T[] IfEmpty(final T[] array, final T[] def) {
		return (IsEmpty(array) ? def : array);
	}



	// collection
	public static boolean IsEmpty(final Collection<?> collect) {
		if (collect == null) return true;
		return (collect.size() == 0);
	}
	public static Collection<?> IfEmpty(final Collection<?> collect, final Collection<?> def) {
		return (IsEmpty(collect) ? def : collect);
	}



	// map
	public static boolean IsEmpty(final Map<?, ?> map) {
		if (map == null) return true;
		return (map.size() == 0);
	}
	public static Map<?, ?> IfEmpty(final Map<?, ?> map, final Map<?, ?> def) {
		return (IsEmpty(map) ? def : map);
	}



	// byte
	public static Byte IfEmpty(final Byte byt, final Byte def) {
		return (byt == null ? def : byt);
	}



	// byte array
	public static boolean IsEmpty(final byte[] bytes) {
		if (bytes == null) return true;
		return (bytes.length == 0);
	}
	public static byte[] IfEmpty(final byte[] bytes, final byte[] def) {
		return (IsEmpty(bytes) ? def : bytes);
	}



	// char
	public static char IfEmpty(final char chr, final char def) {
		return (chr == 0 ? def : chr);
	}



	// char array
	public static boolean IsEmpty(final char[] chars) {
		if (chars == null) return true;
		return (chars.length == 0);
	}
	public static char[] IfEmpty(final char[] chars, final char[] def) {
		return (IsEmpty(chars) ? def : chars);
	}



	// character
	public static boolean IsEmpty(final Character chr) {
		return (chr == null);
	}
	public static Character IfEmpty(final Character chr, final Character def) {
		return (IsEmpty(chr) ? def : chr);
	}



	// character array
	public static boolean IsEmpty(final Character[] chars) {
		if (chars == null) return true;
		return (chars.length == 0);
	}
	public static Character[] IfEmpty(final Character[] chars, final Character[] def) {
		return (IsEmpty(chars) ? def : chars);
	}



	// short
	public static Short IfEmpty(final Short value, final Short def) {
		return (value == null ? def : value);
	}



	// short array
	public static boolean IsEmpty(final short[] shorts) {
		if (shorts == null) return true;
		return (shorts.length == 0);
	}
	public static short[] IfEmpty(final short[] shorts, final short[] def) {
		return (IsEmpty(shorts) ? def : shorts);
	}



	// integer
	public static Integer IfEmpty(final Integer value, final Integer def) {
		return (value == null ? def : value);
	}



	// integer array
	public static boolean IsEmpty(final int[] ints) {
		if (ints == null) return true;
		return (ints.length == 0);
	}
	public static int[] IfEmpty(final int[] ints, final int[] def) {
		return (IsEmpty(ints) ? def : ints);
	}



	// long
	public static Long IfEmpty(final Long value, final Long def) {
		return (value == null ? def : value);
	}



	// long array
	public static boolean IsEmpty(final long[] longs) {
		if (longs == null) return true;
		return (longs.length == 0);
	}
	public static long[] IfEmpty(final long[] longs, final long[] def) {
		return (IsEmpty(longs) ? def : longs);
	}



	// double
	public static Double IfEmpty(final Double value, final Double def) {
		return (value == null ? def : value);
	}



	// double array
	public static boolean IsEmpty(final double[] doubles) {
		if (doubles == null) return true;
		return (doubles.length == 0);
	}
	public static double[] IfEmpty(final double[] doubles, final double[] def) {
		return (IsEmpty(doubles) ? def : doubles);
	}



	// float
	public static Float IfEmpty(final Float value, final Float def) {
		return (value == null ? def : value);
	}



	// float array
	public static boolean IsEmpty(final float[] floats) {
		if (floats == null) return true;
		return (floats.length == 0);
	}
	public static float[] IfEmpty(final float[] floats, final float[] def) {
		return (IsEmpty(floats) ? def : floats);
	}



	// boolean
	public static Boolean IfEmpty(final Boolean bool, final Boolean def) {
		return (bool == null ? def : bool);
	}



	// boolean array
	public static boolean IsEmpty(final boolean[] bools) {
		if (bools == null) return true;
		return (bools.length == 0);
	}
	public static boolean[] IfEmpty(final boolean[] bools, final boolean[] def) {
		return (IsEmpty(bools) ? def : bools);
	}



	// -------------------------------------------------------------------------------
	// match



	public static boolean EqualsUUID(final UUID uuidA, final UUID uuidB) {
		if (uuidA == null || uuidB == null)
			return false;
		return (uuidA.compareTo(uuidB) == 0);
	}

	public static boolean EqualsColor(final Color colorA, final Color colorB) {
		if (colorA == null || colorB == null)
			return (colorA == null && colorB == null);
		return (colorA.getRGB() == colorB.getRGB());
	}



	// -------------------------------------------------------------------------------
	// close



	/**
	 * Close safely, ignoring errors.
	 */
	public static void SafeClose(final Closeable obj) {
		if (obj == null) return;
		try {
			obj.close();
		} catch (Exception ignore) {}
	}
	/**
	 * Close safely, ignoring errors.
	 */
	public static void SafeClose(final AutoCloseable obj) {
		if (obj == null) return;
		try {
			obj.close();
		} catch (Exception ignore) {}
	}



	public static void SafeCloseMany(final Closeable...objects) {
		for (final Closeable obj : objects) {
			try {
				obj.close();
			} catch (Exception ignore) {}
		}
	}
	public static void CloseMany(final Closeable...objects) throws IOException {
		IOException ex = null;
		for (final Closeable obj : objects) {
			try {
				obj.close();
			} catch (IOException e) {
				if (ex == null) ex = e;
				else            ex.addSuppressed(e);
			}
		}
		if (ex != null)
			throw ex;
	}



	// -------------------------------------------------------------------------------
	// exceptions



	public static Throwable RootCause(final Throwable e) {
		final Throwable cause = e.getCause();
		return (cause==null ? e : RootCause(cause));
	}



	// -------------------------------------------------------------------------------
	// time



	/**
	 * Current system time in milliseconds.
	 * @return
	 */
	public static long GetMS() {
		return System.currentTimeMillis();
	}



	// -------------------------------------------------------------------------------
	// logger



	public static xLog log() {
		return xLog.Get();
	}



}
