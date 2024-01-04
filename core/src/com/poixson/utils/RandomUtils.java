package com.poixson.utils;

import com.poixson.tools.xRand;


public final class RandomUtils {
	private RandomUtils() {}



	@Deprecated
	public static int GetRandom(final int minNumber, final int maxNumber) {
		return xRand.Get(minNumber, maxNumber).nextInt();
	}
	@Deprecated
	public static int GetRandom(final int minNumber, final int maxNumber, final int seed) {
		final xRand rnd = xRand.Get(minNumber, maxNumber);
		rnd.seed(seed);
		return rnd.nextInt();
	}
	@Deprecated
	public static int GetNewRandom(final int minNumber, final int maxNumber, final int oldNumber) {
		return xRand.Get(minNumber, maxNumber).nextInt(oldNumber);
	}



}
