package com.poixson.utils;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import com.poixson.tools.Keeper;


public final class RandomUtils {
	private RandomUtils() {}
	static { Keeper.add(new RandomUtils()); }

	protected static final AtomicInteger Last10K = new AtomicInteger(0);



//TODO: does this replace HistoryRND class?
	public static int GetRandom(final int minNumber, final int maxNumber) {
		return GetRandom(minNumber, maxNumber, 1L);
	}
	public static int GetRandom(final int minNumber, final int maxNumber, final int seed) {
		return GetRandom(minNumber, maxNumber, ((long)seed));
	}
	public static int GetRandom(final int minNumber, final int maxNumber, final long seed) {
		if (minNumber >  maxNumber) return minNumber;
		if (minNumber == maxNumber) return minNumber;
		if (seed == 0L)
			return GetRandom(minNumber, maxNumber, 1L);
		final Random gen = new Random(Utils.GetMS() * seed);
		return gen.nextInt(maxNumber - minNumber) + minNumber;
	}

	public static int GetNewRandom(final int minNumber, final int maxNumber, final int oldNumber) {
		if (minNumber >  maxNumber) return minNumber;
		if (minNumber == maxNumber) return minNumber;
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



	@Deprecated
	public static int Rnd10K() {
		final int rnd = GetNewRandom(0, 9999, Last10K.get());
		Last10K.set(rnd);
		return rnd;
	}



}
