package com.poixson.utils;

import java.io.Closeable;
import java.util.Collection;
import java.util.Map;

import com.poixson.tools.Keeper;


public final class Utils {
	private Utils() {}
	static { Keeper.add(new Utils()); }



//	private static final AtomicInteger jLineVersion = new AtomicInteger(-1);
//	private static final AtomicInteger RxTxVersion  = new AtomicInteger(-1);



	// ------------------------------------------------------------------------------- //
	// is empty



	// string
	public static boolean isEmpty(final String value) {
		if (value == null)
			return true;
		return (value.length() == 0);
	}
	public static boolean notEmpty(final String value) {
		if (value == null)
			return false;
		return (value.length() > 0);
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



	// character
	public static boolean isEmpty(final Character chr) {
		return (chr == null);
	}
	public static boolean notEmpty(final Character chr) {
		return (chr != null);
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



	/**
	 * Current system time in milliseconds.
	 * @return
	 */
	public static long getSystemMillis() {
		return System.currentTimeMillis();
	}



}
