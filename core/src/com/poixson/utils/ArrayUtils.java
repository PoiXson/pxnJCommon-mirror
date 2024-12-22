package com.poixson.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.poixson.tools.Keeper;


public final class ArrayUtils {
	private ArrayUtils() {}
	static { Keeper.add(new ArrayUtils()); }



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
