package com.poixson.utils;

import java.io.Closeable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import com.poixson.tools.Keeper;


public final class Utils {
	private Utils() {}
	static { Keeper.add(new Utils()); }



//	private static final AtomicInteger jLineVersion = new AtomicInteger(-1);
//	private static final AtomicInteger RxTxVersion  = new AtomicInteger(-1);



	// ------------------------------------------------------------------------------- //
	// is empty



	// string
	public static boolean isEmpty(final String str) {
		if (str == null) return true;
		return (str.length() == 0);
	}
	public static boolean notEmpty(final String str) {
		if (str == null) return false;
		return (str.length() > 0);
	}
	public static String ifEmpty(final String value, final String def) {
		if (isEmpty(value)) return def;
		return value;
	}



	// string builder
	public static boolean isEmpty(final StringBuilder str) {
		if (str == null) return true;
		return (str.length() == 0);
	}
	public static boolean notEmpty(final StringBuilder str) {
		if (str == null) return false;
		return (str.length() > 0);
	}



	// string array
	public static boolean isEmpty(final String[] array) {
		if (array == null)
			return true;
		return (array.length == 0);
	}
	public static boolean notEmpty(final String[] array) {
		if (array == null)
			return false;
		return (array.length > 0);
	}
	public static String[] ifEmpty(final String[] array, final String[] def) {
		if (isEmpty(array))
			return def;
		return array;
	}



	// object
	public static <T extends Object> T ifEmpty(final T obj, final T def) {
		if (obj == null)
			return def;
		return obj;
	}



	// object array
	public static boolean isEmpty(final Object[] array) {
		if (array == null)
			return true;
		return (array.length == 0);
	}
	public static boolean notEmpty(final Object[] array) {
		if (array == null)
			return false;
		return (array.length > 0);
	}
	public static <T extends Object> T[] ifEmpty(final T[] array, final T[] def) {
		if (isEmpty(array))
			return def;
		return array;
	}



	// collection
	public static boolean isEmpty(final Collection<?> collect) {
		if (collect == null)
			return true;
		return (collect.size() == 0);
	}
	public static boolean notEmpty(final Collection<?> collect) {
		if (collect == null)
			return false;
		return (collect.size() > 0);
	}
	public static Collection<?> ifEmpty(final Collection<?> collect, final Collection<?> def) {
		if (isEmpty(collect))
			return def;
		return collect;
	}



	// map
	public static boolean isEmpty(final Map<?, ?> map) {
		if (map == null)
			return true;
		return (map.size() == 0);
	}
	public static boolean notEmpty(final Map<?, ?> map) {
		if (map == null)
			return false;
		return (map.size() > 0);
	}
	public static Map<?, ?> ifEmpty(final Map<?, ?> map, final Map<?, ?> def) {
		if (isEmpty(map))
			return def;
		return map;
	}



	// byte
	public static Byte ifEmpty(final Byte byt, final Byte def) {
		if (byt == null)
			return def;
		return byt;
	}



	// byte array
	public static boolean isEmpty(final byte[] bytes) {
		if (bytes == null)
			return true;
		return (bytes.length == 0);
	}
	public static boolean notEmpty(final byte[] bytes) {
		if (bytes == null)
			return false;
		return (bytes.length > 0);
	}
	public static byte[] ifEmpty(final byte[] bytes, final byte[] def) {
		if (isEmpty(bytes))
			return def;
		return bytes;
	}



	// char
	public static char ifEmpty(final char chr, final char def) {
		if (chr == 0)
			return def;
		return chr;
	}



	// char array
	public static boolean isEmpty(final char[] chars) {
		if (chars == null)
			return true;
		return (chars.length == 0);
	}
	public static boolean notEmpty(final char[] chars) {
		if (chars == null)
			return false;
		return (chars.length > 0);
	}
	public static char[] ifEmpty(final char[] chars, final char[] def) {
		if (isEmpty(chars))
			return def;
		return chars;
	}



	// character
	public static boolean isEmpty(final Character chr) {
		return (chr == null);
	}
	public static boolean notEmpty(final Character chr) {
		return (chr != null);
	}
	public static Character ifEmpty(final Character chr, final Character def) {
		if (isEmpty(chr))
			return def;
		return chr;
	}



	// character array
	public static boolean isEmpty(final Character[] chars) {
		if (chars == null)
			return true;
		return (chars.length == 0);
	}
	public static boolean notEmpty(final Character[] chars) {
		if (chars == null)
			return false;
		return (chars.length > 0);
	}
	public static Character[] ifEmpty(final Character[] chars, final Character[] def) {
		if (isEmpty(chars))
			return def;
		return chars;
	}



	// short
	public static Short ifEmpty(final Short value, final Short def) {
		if (value == null)
			return def;
		return value;
	}



	// short array
	public static boolean isEmpty(final short[] shorts) {
		if (shorts == null)
			return true;
		return (shorts.length == 0);
	}
	public static boolean notEmpty(final short[] shorts) {
		if (shorts == null)
			return false;
		return (shorts.length > 0);
	}
	public static short[] ifEmpty(final short[] shorts, final short[] def) {
		if (isEmpty(shorts))
			return def;
		return shorts;
	}



	// integer
	public static Integer ifEmpty(final Integer value, final Integer def) {
		if (value == null)
			return def;
		return value;
	}



	// integer array
	public static boolean isEmpty(final int[] ints) {
		if (ints == null)
			return true;
		return (ints.length == 0);
	}
	public static boolean notEmpty(final int[] ints) {
		if (ints == null)
			return false;
		return (ints.length > 0);
	}
	public static int[] ifEmpty(final int[] ints, final int[] def) {
		if (isEmpty(ints))
			return def;
		return ints;
	}



	// long
	public static Long ifEmpty(final Long value, final Long def) {
		if (value == null)
			return def;
		return value;
	}



	// long array
	public static boolean isEmpty(final long[] longs) {
		if (longs == null)
			return true;
		return (longs.length == 0);
	}
	public static boolean notEmpty(final long[] longs) {
		if (longs == null)
			return false;
		return (longs.length > 0);
	}
	public static long[] ifEmpty(final long[] longs, final long[] def) {
		if (isEmpty(longs))
			return def;
		return longs;
	}



	// double
	public static Double ifEmpty(final Double value, final Double def) {
		if (value == null)
			return def;
		return value;
	}



	// double array
	public static boolean isEmpty(final double[] doubles) {
		if (doubles == null)
			return true;
		return (doubles.length == 0);
	}
	public static boolean notEmpty(final double[] doubles) {
		if (doubles == null)
			return false;
		return (doubles.length > 0);
	}
	public static double[] ifEmpty(final double[] doubles, final double[] def) {
		if (isEmpty(doubles))
			return def;
		return doubles;
	}



	// float
	public static Float ifEmpty(final Float value, final Float def) {
		if (value == null)
			return def;
		return value;
	}



	// float array
	public static boolean isEmpty(final float[] floats) {
		if (floats == null)
			return true;
		return (floats.length == 0);
	}
	public static boolean notEmpty(final float[] floats) {
		if (floats == null)
			return false;
		return (floats.length > 0);
	}
	public static float[] ifEmpty(final float[] floats, final float[] def) {
		if (isEmpty(floats))
			return def;
		return floats;
	}



	// boolean
	public static Boolean ifEmpty(final Boolean bool, final Boolean def) {
		if (bool == null)
			return def;
		return bool;
	}



	// boolean array
	public static boolean isEmpty(final boolean[] bools) {
		if (bools == null)
			return true;
		return (bools.length == 0);
	}
	public static boolean notEmpty(final boolean[] bools) {
		if (bools == null)
			return false;
		return (bools.length > 0);
	}
	public static boolean[] ifEmpty(final boolean[] bools, final boolean[] def) {
		if (isEmpty(bools))
			return def;
		return bools;
	}



	// ------------------------------------------------------------------------------- //
	// match



	public static boolean MatchUUID(final UUID expect, final UUID actual) {
		if (expect == null || actual == null)
			return false;
		return (expect.compareTo(actual) == 0);
	}



	// ------------------------------------------------------------------------------- //
	// is type



	public static boolean isArray(final Object obj) {
		if (obj == null)
			return false;
		return obj.getClass().isArray();
	}
	public static boolean notArray(final Object obj) {
		if (obj == null)
			return true;
		return ! obj.getClass().isArray();
	}



	// ------------------------------------------------------------------------------- //
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
			result[index++] = it.next();
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
			result[index] = it.next();
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
			result[index] = it.next();
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
			result[index] = it.next();
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
			result[index] = it.next();
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
			result[index] = it.next();
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
			result[index] = it.next();
			index++;
		}
		return result;
	}



	// ------------------------------------------------------------------------------- //
	// close safely



	/**
	 * Close safely, ignoring errors.
	 */
	public static void safeClose(final Closeable obj) {
		if (obj == null) return;
		try {
			obj.close();
		} catch (Exception ignore) {}
	}
	/**
	 * Close safely, ignoring errors.
	 */
	public static void safeClose(final AutoCloseable obj) {
		if (obj == null) return;
		try {
			obj.close();
		} catch (Exception ignore) {}
	}



	// ------------------------------------------------------------------------------- //
	// exceptions



	public static Throwable RootCause(final Throwable e) {
		final Throwable cause = e.getCause();
		if (cause == null)
			return e;
		return RootCause(cause);
	}



	// ------------------------------------------------------------------------------- //
	// time



	/**
	 * Current system time in milliseconds.
	 * @return
	 */
	public static long getSystemMillis() {
		return System.currentTimeMillis();
	}



}
