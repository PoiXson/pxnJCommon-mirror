package com.poixson.utils;

import java.util.Set;

import com.poixson.tools.Keeper;


public final class ArrayUtils {
	private ArrayUtils() {}
	static { Keeper.add(new ArrayUtils()); }



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



	public static int[] SetToArray(final Set<Integer> set) {
		final Integer[] copy = set.toArray(new Integer[0]);
		final int size = copy.length;
		final int[] result = new int[size];
		for (int i=0; i<size; i++)
			result[i] = copy[i].intValue();
		return result;
	}



}
