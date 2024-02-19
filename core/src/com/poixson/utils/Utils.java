package com.poixson.utils;

import java.awt.Color;
import java.io.Closeable;
import java.util.Collection;
import java.util.Iterator;
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
	// is type



	public static boolean IsArray(final Object obj) {
		if (obj == null) return false;
		return obj.getClass().isArray();
	}



	// -------------------------------------------------------------------------------
	// to primitive array



	// boolean
	public static boolean[] BoolToPrimArray(final Collection<Boolean> collect) {
		if (collect == null)   return null;
		if (collect.isEmpty()) return new boolean[0];
		final int size = collect.size();
		final boolean[] result = new boolean[size];
		final Iterator<Boolean> it = collect.iterator();
		int index = 0;
		while (it.hasNext()) {
			result[index++] = it.next().booleanValue();
		}
		return result;
	}
	// integer
	public static int[] IntToPrimArray(final Collection<Integer> collect) {
		if (collect == null)   return null;
		if (collect.isEmpty()) return new int[0];
		final int size = collect.size();
		final int[] result = new int[size];
		final Iterator<Integer> it = collect.iterator();
		int index = 0;
		while (it.hasNext()) {
			result[index] = it.next().intValue();
			index++;
		}
		return result;
	}
	// byte
	public static byte[] ByteToPrimArray(final Collection<Byte> collect) {
		if (collect == null)   return null;
		if (collect.isEmpty()) return new byte[0];
		final int size = collect.size();
		final byte[] result = new byte[size];
		final Iterator<Byte> it = collect.iterator();
		int index = 0;
		while (it.hasNext()) {
			result[index] = it.next().byteValue();
			index++;
		}
		return result;
	}
	// short
	public static short[] ShortToPrimArray(final Collection<Short> collect) {
		if (collect == null)   return null;
		if (collect.isEmpty()) return new short[0];
		final int size = collect.size();
		final short[] result = new short[size];
		final Iterator<Short> it = collect.iterator();
		int index = 0;
		while (it.hasNext()) {
			result[index] = it.next().shortValue();
			index++;
		}
		return result;
	}
	// long
	public static long[] LongToPrimArray(final Collection<Long> collect) {
		if (collect == null)   return null;
		if (collect.isEmpty()) return new long[0];
		final int size = collect.size();
		final long[] result = new long[size];
		final Iterator<Long> it = collect.iterator();
		int index = 0;
		while (it.hasNext()) {
			result[index] = it.next().longValue();
			index++;
		}
		return result;
	}
	// double
	public static double[] DoubleToPrimArray(final Collection<Double> collect) {
		if (collect == null)   return null;
		if (collect.isEmpty()) return new double[0];
		final int size = collect.size();
		final double[] result = new double[size];
		final Iterator<Double> it = collect.iterator();
		int index = 0;
		while (it.hasNext()) {
			result[index] = it.next().doubleValue();
			index++;
		}
		return result;
	}
	// float
	public static float[] FloatToPrimArray(final Collection<Float> collect) {
		if (collect == null)   return null;
		if (collect.isEmpty()) return new float[0];
		final int size = collect.size();
		final float[] result = new float[size];
		final Iterator<Float> it = collect.iterator();
		int index = 0;
		while (it.hasNext()) {
			result[index] = it.next().floatValue();
			index++;
		}
		return result;
	}



	// -------------------------------------------------------------------------------
	// close safely



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



	// -------------------------------------------------------------------------------
	// exceptions



	public static Throwable RootCause(final Throwable e) {
		final Throwable cause = e.getCause();
		if (cause == null)
			return e;
		return RootCause(cause);
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
