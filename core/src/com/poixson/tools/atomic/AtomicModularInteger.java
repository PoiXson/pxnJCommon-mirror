/*
package com.poixson.tools.atomic;

import static com.poixson.utils.MathUtils.IsMinMax;
import static com.poixson.utils.MathUtils.SafeLongToInt;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.tools.abstractions.Tuple;


public class AtomicModularInteger extends Number implements Serializable {
	private static final long serialVersionUID = 1L;

	public final AtomicLong atomic = new AtomicLong(0L);
	public final AtomicReference<Tuple<Integer, Integer>> minmax =
			new AtomicReference<Tuple<Integer, Integer>>(null);



	public AtomicModularInteger() {
		this(0);
	}
	public AtomicModularInteger(final int value) {
		this(value, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	public AtomicModularInteger(final int value, final int min, final int max) {
		super();
		this.atomic.set((long)value);
		this.minmax.set(new Tuple<Integer, Integer>(Integer.valueOf(min), Integer.valueOf(max)));
	}



	public int normalize(final int value) {
		final Tuple<Integer, Integer> tup = this.minmax.get();
		final int min = (tup==null ? Integer.MIN_VALUE : tup.key);
		final int max = (tup==null ? Integer.MAX_VALUE : tup.val);
		if (IsMinMax(value, min, max))
			return value;
		final int range = (max - min) + 1;
		final int mod = (value - min) % range;
		return mod + min + (mod<0 ? range : 0);
	}



	public Tuple<Integer, Integer> getMinMax() {
		return this.minmax.get();
	}
	public void setMinMax(final int min, final int max) {
		this.minmax.set(new Tuple<Integer, Integer>(Integer.valueOf(min), Integer.valueOf(max)));
	}

	public int getMin() {
		final Tuple<Integer, Integer> tup = this.minmax.get();
		return (tup==null ? Integer.MIN_VALUE : tup.key);
	}
	public int getMax() {
		final Tuple<Integer, Integer> tup = this.minmax.get();
		return (tup==null ? Integer.MAX_VALUE : tup.val);
	}

	public void setMin(final int min) {
		while (true) {
			final Tuple<Integer, Integer> tup = this.minmax.get();
			if (this.minmax.compareAndSet(tup,
					new Tuple<Integer, Integer>(Integer.valueOf(min), Integer.valueOf(tup.val))))
				return;
		}
	}
	public void setMax(final int max) {
		while (true) {
			final Tuple<Integer, Integer> tup = this.minmax.get();
			if (this.minmax.compareAndSet(tup,
					new Tuple<Integer, Integer>(Integer.valueOf(tup.key), Integer.valueOf(max))))
				return;
		}
	}



	@Override
	public String toString() {
		return Integer.toString(this.get());
	}



	public int get() {
		return this.normalize(SafeLongToInt(this.atomic.get()));
	}
	public void set(final int value) {
		this.atomic.set(value);
	}
	public void lazySet(final int value) {
		this.atomic.lazySet(value);
	}



	@Override
	public int intValue() {
		return this.get();
	}
	@Override
	public long longValue() {
		return (long) this.get();
	}
	@Override
	public float floatValue() {
		return (float) this.get();
	}
	@Override
	public double doubleValue() {
		return (double) this.get();
	}



	public int getAndSet(final int value) {
		return this.normalize(this.getAndSet(value));
	}



	public int getAndIncrement() {
		final int result = SafeLongToInt(this.atomic.getAndIncrement());
		final int norm = this.normalize(result);
		if (result != norm)
			this.atomic.compareAndSet(result, norm+1);
		return norm;
	}
	public int incrementAndGet() {
		final int result = SafeLongToInt(this.atomic.incrementAndGet());
		final int norm = this.normalize(result);
		if (result != norm)
			this.atomic.compareAndSet(result, norm);
		return norm;
	}



	public int getAndDecrement() {
		final int result = SafeLongToInt(this.atomic.getAndDecrement());
		final int norm = this.normalize(result);
		if (result != norm)
			this.atomic.compareAndSet(result, norm-1);
		return norm;
	}
	public int decrementAndGet() {
		final int result = SafeLongToInt(this.atomic.decrementAndGet());
		final int norm = this.normalize(result);
		if (result != norm)
			this.atomic.compareAndSet(result, norm);
		return norm;
	}



	public int getAndAdd(final int add) {
		final int result = SafeLongToInt(this.atomic.getAndAdd(add));
		final int norm = this.normalize(result);
		if (result != norm)
			this.atomic.compareAndSet(result, norm+add);
		return norm;
	}
	public int addAndGet(final int add) {
		final int result = SafeLongToInt(this.atomic.addAndGet(add));
		final int norm = this.normalize(result);
		if (result != norm)
			this.atomic.compareAndSet(result, norm);
		return norm;
	}



}
*/
