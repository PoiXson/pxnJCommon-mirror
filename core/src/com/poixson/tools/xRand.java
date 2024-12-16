package com.poixson.tools;

import static com.poixson.utils.Utils.GetMS;

import java.security.SecureRandom;


public class xRand {

	protected final SecureRandom rnd;

	protected long seed = 0;

	//  1.0 | no weight
	// <1.0 | favor lower numbers
	// >1.0 | favor higher numbers
	protected double weight = 1.0;



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



	public xRand weight(final double weight) {
		this.weight = weight;
		return this;
	}
	public double getWeight() {
		return this.weight;
	}



	// int
	public int nextInt(final int min, final int max) {
		if (min > max) throw new IllegalArgumentException("min greater than max");
		if (min == max) return min;
		final int result;
		if (this.weight == 1.0) {
			result = this.rnd.nextInt(min, max);
		} else {
			final double n = this.rnd.nextDouble(0.0, 1.0);
			final double w = 1.0 - Math.pow(n, this.weight);
			result = (int)Math.floor(w * ((max - min) + 1)) + min;
		}
		this.seed += (long)result;
		this.rnd.setSeed(this.seed);
		return result;
	}
	public int newInt(final int min, final int max, final int last) {
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
		final long result;
		if (this.weight == 1.0) {
			result = this.rnd.nextLong(min, max);
		} else {
			final double n = this.rnd.nextDouble(0.0, 1.0);
			final double w = Math.pow(n, 1.0/this.weight);
			result = (long)Math.floor(w * ((double)(max - min))) + min;
		}
		this.seed += result;
		return result;
	}
	public long newLong(final long min, final long max, final long last) {
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
	public float nextFloat(final float min, final float max) {
		if (min > max) throw new IllegalArgumentException("min greater than max");
		if (min == max) return min;
		final float result;
		if (this.weight == 1.0) {
			result = this.rnd.nextFloat(min, max);
		} else {
			final double n = this.rnd.nextDouble(0.0, 1.0);
			final double w = Math.pow(n, 1.0/this.weight);
			result = (float)(w * ((double)(max - min))) + min;
		}
		this.seed += (long) Math.ceil(result) + Math.ceil(result * 1000000.0f);
		this.rnd.setSeed(this.seed);
		return result;
	}
	public float newFloat(final float min, final float max, final float last) {
		for (int i=0; i<100; i++) {
			final float value = this.nextFloat(min, max);
			if (value != last)
				return value;
		}
		return last;
	}



	// double
	public double nextDouble(final double min, final double max) {
		if (min > max) throw new IllegalArgumentException("min greater than max");
		if (min == max) return min;
		final double result;
		if (this.weight == 1.0) {
			result = this.rnd.nextDouble(min, max);
		} else {
			final double n = this.rnd.nextDouble(0.0, 1.0);
			final double w = Math.pow(n, 1.0/this.weight);
			result = (w * (max - min)) + min;
		}
		this.seed += (long) Math.ceil(result) + Math.ceil(result * 1000000000000000.0f);
		this.rnd.setSeed(this.seed);
		return result;
	}
	public double newDouble(final double min, final double max, final double last) {
		for (int i=0; i<100; i++) {
			final double value = this.nextDouble(min, max);
			if (value != last)
				return value;
		}
		return last;
	}



}
