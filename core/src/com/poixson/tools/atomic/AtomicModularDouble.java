package com.poixson.tools.atomic;

import static com.poixson.utils.MathUtils.IsMinMax;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.tools.abstractions.Tuple;


public class AtomicModularDouble extends Number implements Serializable {
	private static final long serialVersionUID = 1L;

	public final AtomicDouble atomic;
	public final AtomicReference<Tuple<Double, Double>> minmax =
			new AtomicReference<Tuple<Double, Double>>(null);



	public AtomicModularDouble() {
		this(0.0);
	}
	public AtomicModularDouble(final double value) {
		this(value, Double.MIN_VALUE, Double.MAX_VALUE);
	}
	public AtomicModularDouble(final double value, final double min, final double max) {
		super();
		this.atomic = new AtomicDouble(value);
		this.minmax.set(new Tuple<Double, Double>(Double.valueOf(min), Double.valueOf(max)));
	}



	public double normalize(final double value) {
		final Tuple<Double, Double> tup = this.minmax.get();
		final double min = (tup==null ? Double.MIN_VALUE : tup.key);
		final double max = (tup==null ? Double.MAX_VALUE : tup.val);
		if (IsMinMax(value, min, max))
			return value;
		final double range = (max - min) + 1L;
		final double mod = (value - min) % range;
		return mod + min + (mod<0 ? range : 0L);
	}



	public Tuple<Double, Double> getMinMax() {
		return this.minmax.get();
	}
	public void setMinMax(final double min, final double max) {
		this.minmax.set(new Tuple<Double, Double>(Double.valueOf(min), Double.valueOf(max)));
	}

	public double getMin() {
		final Tuple<Double, Double> tup = this.minmax.get();
		return (tup==null ? Double.MIN_VALUE : tup.key);
	}
	public double getMax() {
		final Tuple<Double, Double> tup = this.minmax.get();
		return (tup==null ? Double.MAX_VALUE : tup.val);
	}

	public void setMin(final double min) {
		while (true) {
			final Tuple<Double, Double> tup = this.minmax.get();
			if (this.minmax.compareAndSet(tup,
					new Tuple<Double, Double>(Double.valueOf(min), Double.valueOf(tup.val))))
				return;
		}
	}
	public void setMax(final double max) {
		while (true) {
			final Tuple<Double, Double> tup = this.minmax.get();
			if (this.minmax.compareAndSet(tup,
					new Tuple<Double, Double>(Double.valueOf(tup.key), Double.valueOf(max))))
				return;
		}
	}



	@Override
	public String toString() {
		return Double.toString(this.get());
	}



	public double get() {
		return this.normalize(this.atomic.get());
	}
	public void set(final double value) {
		this.atomic.set(value);
	}
	public void lazySet(final double value) {
		this.atomic.lazySet(value);
	}



	@Override
	public int intValue() {
		return (int) Math.round(this.get());
	}
	@Override
	public long longValue() {
		return (long) Math.round(this.get());
	}
	@Override
	public float floatValue() {
		return (float) this.get();
	}
	@Override
	public double doubleValue() {
		return this.get();
	}



	public double getAndSet(final double value) {
		return this.normalize(this.getAndSet(value));
	}



	public double getAndIncrement() {
		final double result = this.atomic.getAndIncrement();
		final double norm = this.normalize(result);
		if (result != norm)
			this.atomic.compareAndSet(result, norm+1L);
		return norm;
	}
	public double incrementAndGet() {
		final double result = this.atomic.incrementAndGet();
		final double norm = this.normalize(result);
		if (result != norm)
			this.atomic.compareAndSet(result, norm);
		return norm;
	}



	public double getAndDecrement() {
		final double result = this.atomic.getAndDecrement();
		final double norm = this.normalize(result);
		if (result != norm)
			this.atomic.compareAndSet(result, norm-1L);
		return norm;
	}
	public double decrementAndGet() {
		final double result = this.atomic.decrementAndGet();
		final double norm = this.normalize(result);
		if (result != norm)
			this.atomic.compareAndSet(result, norm);
		return norm;
	}



	public double getAndAdd(final double add) {
		final double result = this.atomic.getAndAdd(add);
		final double norm = this.normalize(result);
		if (result != norm)
			this.atomic.compareAndSet(result, norm+add);
		return norm;
	}
	public double addAndGet(final double add) {
		final double result = this.atomic.addAndGet(add);
		final double norm = this.normalize(result);
		if (result != norm)
			this.atomic.compareAndSet(result, norm);
		return norm;
	}



}
