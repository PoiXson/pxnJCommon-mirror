package com.poixson.tools.atomic;

import static com.poixson.utils.MathUtils.IsMinMax;
import static com.poixson.utils.MathUtils.SafeLongToInt;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.tools.abstractions.Tuple;


public class AtomicModularLong extends Number implements Serializable {
	private static final long serialVersionUID = 1L;

	public final AtomicLong atomic;
	public final AtomicReference<Tuple<Long, Long>> minmax =
			new AtomicReference<Tuple<Long, Long>>(null);



	public AtomicModularLong() {
		this(0L);
	}
	public AtomicModularLong(final long value) {
		this(value, Long.MIN_VALUE, Long.MAX_VALUE);
	}
	public AtomicModularLong(final long value, final long min, final long max) {
		super();
		this.atomic = new AtomicLong(value);
		this.minmax.set(new Tuple<Long, Long>(Long.valueOf(min), Long.valueOf(max)));
	}



	public long normalize(final long value) {
		final Tuple<Long, Long> tup = this.minmax.get();
		final long min = (tup==null ? Long.MIN_VALUE : tup.key);
		final long max = (tup==null ? Long.MAX_VALUE : tup.val);
		if (IsMinMax(value, min, max))
			return value;
		final long range = (max - min) + 1L;
		final long mod = (value - min) % range;
		return mod + min + (mod<0 ? range : 0L);
	}



	public Tuple<Long, Long> getMinMax() {
		return this.minmax.get();
	}
	public void setMinMax(final long min, final long max) {
		this.minmax.set(new Tuple<Long, Long>(Long.valueOf(min), Long.valueOf(max)));
	}

	public long getMin() {
		final Tuple<Long, Long> tup = this.minmax.get();
		return (tup==null ? Long.MIN_VALUE : tup.key);
	}
	public long getMax() {
		final Tuple<Long, Long> tup = this.minmax.get();
		return (tup==null ? Long.MAX_VALUE : tup.val);
	}

	public void setMin(final long min) {
		while (true) {
			final Tuple<Long, Long> tup = this.minmax.get();
			if (this.minmax.compareAndSet(tup,
					new Tuple<Long, Long>(Long.valueOf(min), Long.valueOf(tup.val))))
				return;
		}
	}
	public void setMax(final long max) {
		while (true) {
			final Tuple<Long, Long> tup = this.minmax.get();
			if (this.minmax.compareAndSet(tup,
					new Tuple<Long, Long>(Long.valueOf(tup.key), Long.valueOf(max))))
				return;
		}
	}



	@Override
	public String toString() {
		return Long.toString(this.atomic.get());
	}



	public long get() {
		return this.normalize(this.atomic.get());
	}
	public void set(final long value) {
		this.atomic.set(value);
	}
	public void lazySet(final long value) {
		this.atomic.lazySet(value);
	}



	@Override
	public int intValue() {
		return SafeLongToInt(this.get());
	}
	@Override
	public long longValue() {
		return this.get();
	}
	@Override
	public float floatValue() {
		return (float) this.get();
	}
	@Override
	public double doubleValue() {
		return (double) this.get();
	}



	public long getAndSet(final long value) {
		return this.normalize(this.getAndSet(value));
	}



	public long getAndIncrement() {
		final long result = this.atomic.getAndIncrement();
		final long norm = this.normalize(result);
		if (result != norm)
			this.atomic.compareAndSet(result, norm+1L);
		return norm;
	}
	public long incrementAndGet() {
		final long result = this.atomic.incrementAndGet();
		final long norm = this.normalize(result);
		if (result != norm)
			this.atomic.compareAndSet(result, norm);
		return norm;
	}



	public long getAndDecrement() {
		final long result = this.atomic.getAndDecrement();
		final long norm = this.normalize(result);
		if (result != norm)
			this.atomic.compareAndSet(result, norm-1L);
		return norm;
	}
	public long decrementAndGet() {
		final long result = this.atomic.decrementAndGet();
		final long norm = this.normalize(result);
		if (result != norm)
			this.atomic.compareAndSet(result, norm);
		return norm;
	}



	public long getAndAdd(final long add) {
		final long result = this.atomic.getAndAdd(add);
		final long norm = this.normalize(result);
		if (result != norm)
			this.atomic.compareAndSet(result, norm+add);
		return norm;
	}
	public long addAndGet(final long add) {
		final long result = this.atomic.addAndGet(add);
		final long norm = this.normalize(result);
		if (result != norm)
			this.atomic.compareAndSet(result, norm);
		return norm;
	}



}
