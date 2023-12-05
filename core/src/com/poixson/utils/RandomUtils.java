package com.poixson.utils;

import static com.poixson.utils.Utils.GetMS;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import com.poixson.tools.Keeper;


public final class RandomUtils {
	private RandomUtils() {}
	static { Keeper.add(new RandomUtils()); }

	protected static final AtomicInteger LastRND = new AtomicInteger(0);



	public static int GetRandom(final int minNumber, final int maxNumber) {
		return GetRandom(minNumber, maxNumber, LastRND.get());
	}
	public static int GetRandom(final int minNumber, final int maxNumber, final int seed) {
		return GetRandom(minNumber, maxNumber, ((long)seed));
	}
	public static int GetRandom(final int minNumber, final int maxNumber, final long seed) {
		if (minNumber >  maxNumber) return minNumber;
		if (minNumber == maxNumber) return minNumber;
		if (seed == 0L)
			return GetRandom(minNumber, maxNumber, 1L);
		final Random rnd = new Random(GetMS() * seed);
		final int num = rnd.nextInt(maxNumber - minNumber) + minNumber;
		LastRND.set(num * LastRND.get());
		return num;
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
			if (newNumber != oldNumber) {
				LastRND.set(newNumber * LastRND.get());
				return newNumber;
			}
			seed += i;
		}
		throw new IllegalAccessError("Failed to generate a random number");
	}



}
