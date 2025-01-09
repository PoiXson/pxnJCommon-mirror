package com.poixson.utils;

import static com.poixson.utils.Utils.IsEmpty;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.poixson.tools.Keeper;


public final class ArrayUtils {
	private ArrayUtils() {}
	static { Keeper.add(new ArrayUtils()); }



	public static <T> T[] EmptyArray(final Class<?> type) {
		return NewArray(0, type);
	}
	@SuppressWarnings("unchecked")
	public static <T> T[] NewArray(final int size, final Class<?> type) {
		return (T[]) Array.newInstance(type, size);
	}



	public static <T> T[] SafeArray(final T[] array, final Class<T> type) {
		return (IsEmpty(array) ? EmptyArray(type) : array);
	}
	public static <T> T[] NullNormArray(final T[] array) {
		return (IsEmpty(array) ? null : array);
	}



	public static boolean IsArray(final Object obj) {
		return (obj==null ? false : obj.getClass().isArray());
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
	// remove first entry



	public static <T> T[] DropFirst(final T[] array) {
		if (IsEmpty(array)) return array;
		final int len = array.length;
		@SuppressWarnings("unchecked")
		T[] result = (T[]) Array.newInstance(array[0].getClass(), len-1);
		if (len > 1) {
			for (int i=0; i<len-1; i++)
				result[i] = (T) array[i+1];
		}
		return result;
	}



	// -------------------------------------------------------------------------------
	// merge arrays



	public static <T> LinkedList<T> MergeArrays(final T[] arrayA) {
		final LinkedList<T> list = new LinkedList<T>();
		for (final T entry : arrayA) list.addLast(entry);
		return list;
	}
	public static <T> LinkedList<T> MergeArrays(
			final T[] arrayA, final T[] arrayB) {
		final LinkedList<T> list = new LinkedList<T>();
		for (final T entry : arrayA) list.addLast(entry);
		for (final T entry : arrayB) list.addLast(entry);
		return list;
	}
	public static <T> LinkedList<T> MergeArrays(
			final T[] arrayA, final T[] arrayB, final T[] arrayC) {
		final LinkedList<T> list = new LinkedList<T>();
		for (final T entry : arrayA) list.addLast(entry);
		for (final T entry : arrayB) list.addLast(entry);
		for (final T entry : arrayC) list.addLast(entry);
		return list;
	}
	public static <T> LinkedList<T> MergeArrays(
			final T[] arrayA, final T[] arrayB,
			final T[] arrayC, final T[] arrayD) {
		final LinkedList<T> list = new LinkedList<T>();
		for (final T entry : arrayA) list.addLast(entry);
		for (final T entry : arrayB) list.addLast(entry);
		for (final T entry : arrayC) list.addLast(entry);
		for (final T entry : arrayD) list.addLast(entry);
		return list;
	}



	public static <T> Set<T> MergeSets(
			final Set<T> setA, final List<T> setB) {
		final HashSet<T> result = new HashSet<T>();
		for (final T entry : setA) { if (entry != null) result.add(entry); }
		for (final T entry : setB) { if (entry != null) result.add(entry); }
		return result;
	}
	public static <T> Set<T> MergeSets(
			final Set<T> setA, final Set<T> setB, final Set<T> setC) {
		final HashSet<T> result = new HashSet<T>();
		for (final T entry : setA) { if (entry != null) result.add(entry); }
		for (final T entry : setB) { if (entry != null) result.add(entry); }
		for (final T entry : setC) { if (entry != null) result.add(entry); }
		return result;
	}
	public static <T> Set<T> MergeSets(
			final Set<T> setA, final Set<T> setB,
			final Set<T> setC, final Set<T> setD) {
		final HashSet<T> result = new HashSet<T>();
		for (final T entry : setA) { if (entry != null) result.add(entry); }
		for (final T entry : setB) { if (entry != null) result.add(entry); }
		for (final T entry : setC) { if (entry != null) result.add(entry); }
		for (final T entry : setD) { if (entry != null) result.add(entry); }
		return result;
	}



	public static <T> List<T> MergeLists(
			final List<T> listA, final List<T> listB) {
		final LinkedList<T> result = new LinkedList<T>();
		for (final T entry : listA) { if (entry != null) result.addLast(entry); }
		for (final T entry : listB) { if (entry != null) result.addLast(entry); }
		return result;
	}
	public static <T> List<T> MergeLists(
			final List<T> listA, final List<T> listB, final List<T> listC) {
		final LinkedList<T> result = new LinkedList<T>();
		for (final T entry : listA) { if (entry != null) result.addLast(entry); }
		for (final T entry : listB) { if (entry != null) result.addLast(entry); }
		for (final T entry : listC) { if (entry != null) result.addLast(entry); }
		return result;
	}
	public static <T> List<T> MergeLists(
			final List<T> listA, final List<T> listB,
			final List<T> listC, final List<T> listD) {
		final LinkedList<T> result = new LinkedList<T>();
		for (final T entry : listA) { if (entry != null) result.addLast(entry); }
		for (final T entry : listB) { if (entry != null) result.addLast(entry); }
		for (final T entry : listC) { if (entry != null) result.addLast(entry); }
		for (final T entry : listD) { if (entry != null) result.addLast(entry); }
		return result;
	}



	// -------------------------------------------------------------------------------
	// safe array



	public static double GetSafeOrZero(final double[] array, final int index) {
		if (index < 0           ) return 0;
		if (index >=array.length) return 0;
		return array[index];
	}
	public static double GetSafeOrNear(final double[] array, final int index) {
		if (index < 0) return array[0];
		if (index >=array.length)
			return GetSafeLooped(array, -1);
		return array[index];
	}
	public static double GetSafeLooped(final double[] array, final int index) {
		final int count = array.length;
		if (index < 0) {
			int i = index;
			while (i < 0)
				i += count;
			return array[i % count];
		}
		return array[index % count];
	}
	public static double GetSafeLoopExtend(final double[] array, final int index) {
		if (index < 0)
			return array[0] + GetSafeLooped(array, index-1);
		final int count = array.length;
		if (index >= count)
			return GetSafeLooped(array, index-1) + GetSafeLooped(array, index);
		return array[index];
	}



	// -------------------------------------------------------------------------------
	// set/list to primitive array



	public static int[] iSetToArray(final Set<Integer> set) {
		final Integer[] copy = set.toArray(new Integer[0]);
		final int size = copy.length;
		final int[] result = new int[size];
		for (int i=0; i<size; i++)
			result[i] = copy[i].intValue();
		return result;
	}



	public static Map<String, String> ssArrayToMap(final String[] array) {
		final int len = array.length;
		if (len % 2 != 0) throw new IllegalArgumentException("Invalid number of arguments");
		final Map<String, String> map = new HashMap<String, String>();
		for (int index=0; index<len; index+=2)
			map.put(array[index], array[index+1]);
		return map;
	}



	// -------------------------------------------------------------------------------
	// maps



	public static <K, V> boolean MatchMaps(final Map<K, V> expect, final Map<K, V> actual) {
		final Map<K, V> copy = new HashMap<K, V>();
		for (final Entry<K, V> entry : expect.entrySet())
			copy.put(entry.getKey(), entry.getValue());
		for (final Entry<K, V> entry : actual.entrySet())
			if (!copy.remove(entry.getKey(), entry.getValue()))
				return false;
		return copy.isEmpty();
	}



}
