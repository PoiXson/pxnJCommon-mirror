package com.poixson.tools;

import static com.poixson.utils.Utils.GetMS;

import java.security.SecureRandom;


public class xRand {

	protected final SecureRandom rnd;

	protected long seed = 0;



	public xRand() {
		this.rnd = new SecureRandom();
	}



	public xRand seed_time() {
		return this.seed(
			this.seed += (GetMS() % ((long)Integer.MAX_VALUE))
		);
	}
	public xRand seed(final int seed) {
		return this.seed( (long)seed );
	}
	public xRand seed(final long seed) {
		this.seed += seed;
		return this;
	}
	public long getSeed() {
		return this.seed;
	}



	// int
	public int nextInt(final int min, final int max) {
		if (min > max) throw new IllegalArgumentException("min greater than max");
		if (min == max) return min;
		final int value = this.rnd.nextInt(min, max);
		this.seed += value;
		this.rnd.setSeed(this.seed);
		return value;
	}
	public int nextInt(final int min, final int max, final int last) {
		if (min > max) throw new IllegalArgumentException("min greater than max");
		if (min == max) return min;
		for (int i=0; i<100; i++) {
			final int value = this.nextInt(min, max);
			if (value != last)
				return value;
		}
		return last;
	}



	// long
	public long nextLong(final long min, final long max) {
		if (min > max) throw new IllegalArgumentException("min greater than max");
		if (min == max) return min;
		final long value = this.rnd.nextLong(min, max);
		this.seed += value;
		this.rnd.setSeed(this.seed);
		return value;
	}
	public long nextLong(final long min, final long max, final long last) {
		if (min > max) throw new IllegalArgumentException("min greater than max");
		if (min == max) return min;
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



	// float
	public float nextFlt(final float min, final float max) {
		if (min > max) throw new IllegalArgumentException("min greater than max");
		if (min == max) return min;
		final float value = this.rnd.nextFloat(min, max);
		this.seed += (long) Math.ceil(value) + Math.ceil(value * 1000000000000000.0f);
		this.rnd.setSeed(this.seed);
		return value;
	}
	public float nextFlt(final float min, final float max, final float last) {
		if (min > max) throw new IllegalArgumentException("min greater than max");
		if (min == max) return min;
		for (int i=0; i<100; i++) {
			final float value = this.nextFlt(min, max);
			if (value != last)
				return value;
		}
		return last;
	}



	// double
	public double nextDbl(final double min, final double max) {
		if (min > max) throw new IllegalArgumentException("min greater than max");
		if (min == max) return min;
		final double value = this.rnd.nextDouble(min, max);
		this.seed += (long) Math.ceil(value * 1000.0);
		this.rnd.setSeed(this.seed);
		return value;
	}
	public double nextDlb(final double min, final double max, final double last) {
		if (min > max) throw new IllegalArgumentException("min greater than max");
		if (min == max) return min;
		for (int i=0; i<100; i++) {
			final double value = this.nextDbl(min, max);
			if (value != last)
				return value;
		}
		return last;
	}



}
