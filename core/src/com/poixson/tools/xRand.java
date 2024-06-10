package com.poixson.tools;

import java.security.SecureRandom;


public class xRand {

	protected final SecureRandom rnd;

	protected long seed = 0;



	public xRand() {
		this.rnd = new SecureRandom();
	}



	public long seed(final int seed) {
		return this.seed += seed;
	}



	// int
	public int nextInt(final int min, final int max) {
		final int value = this.rnd.nextInt(min, max);
		this.seed += value;
		this.rnd.setSeed(this.seed);
		return value;
	}
	public int nextInt(final int min, final int max, final int last) {
		for (int i=0; i<100; i++) {
			final int value = this.nextInt(min, max);
			if (value != last)
				return value;
		}
		return last;
	}



	// long
	public long nextLong(final long min, final long max) {
		final long value = this.rnd.nextLong(min, max);
		this.seed += value;
		this.rnd.setSeed(this.seed);
		return value;
	}
	public long nextLong(final long min, final long max, final long last) {
		for (int i=0; i<100; i++) {
			final long value = this.rnd.nextLong(min, max);
			if (value != last) {
				this.seed += value;
				this.rnd.setSeed(this.seed);
				return value;
			}
		}
		return last;
	}



	// double
	public double nextDbl(final double min, final double max) {
		final double value = this.rnd.nextDouble(min, max);
		this.seed += (long) Math.ceil(value * 1000.0);
		this.rnd.setSeed(this.seed);
		return value;
	}
	public double nextDlb(final double min, final double max, final double last) {
		for (int i=0; i<100; i++) {
			final double value = this.nextDbl(min, max);
			if (value != last)
				return value;
		}
		return last;
	}



}
