package com.poixson.tools;

import java.security.SecureRandom;
import java.util.concurrent.ConcurrentHashMap;

import com.poixson.tools.abstractions.Tuple;


//TODO: some way to remove stale instances
public class xRand {

	protected static final ConcurrentHashMap<Tuple<Integer, Integer>, xRand> instances =
			new ConcurrentHashMap<Tuple<Integer, Integer>, xRand>();

	protected final int min;
	protected final int max;

	protected int seed = 0;

	protected final SecureRandom rnd;

	static {
		Keeper.add(new xRand());
	}



	public static xRand Get(final int min, final int max) {
		final Tuple<Integer, Integer> tup = new Tuple<Integer, Integer>(min, max);
		// existing instance
		{
			final xRand rnd = instances.get(tup);
			if (rnd != null)
				return rnd;
		}
		// new instance
		{
			final xRand rnd = new xRand(min, max);
			final xRand previous = instances.putIfAbsent(tup, rnd);
			return (previous==null ? rnd : previous);
		}
	}



	protected xRand(final int min, final int max) {
		this.min = min;
		this.max = max;
		this.rnd = new SecureRandom();
	}
	protected xRand() {
		this.min = 0;
		this.max = 0;
		this.rnd = null;
	}



	public int nextInt() {
		final int value = this.rnd.nextInt(this.min, this.max);
		this.seed += value;
		this.rnd.setSeed(this.seed);
		return value;
	}
	public int nextInt(final int last) {
		for (int i=0; i<100; i++) {
			final int value = this.nextInt();
			if (value != last)
				return value;
		}
		return last;
	}



	public int seed(final int seed) {
		return this.seed += seed;
	}



}
