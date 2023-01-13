package com.poixson.tools.abstractions;

import java.util.concurrent.atomic.AtomicLong;


public class AtomicDouble {

	protected final AtomicLong value;



	public AtomicDouble(final double initialValue) {
		this.value = new AtomicLong( Double.doubleToLongBits(initialValue) );
	}
	public AtomicDouble() {
		this.value = new AtomicLong();
	}



	public double get() {
		return Double.longBitsToDouble( this.value.get() );
	}
	public void set(final double value) {
		this.value.set( Double.doubleToLongBits(value) );
	}



	public void lazySet(final double value) {
		this.value.lazySet( Double.doubleToLongBits(value) );
	}
	public double getAndSet(final double value) {
		return
			Double.longBitsToDouble(
				this.value.getAndSet( Double.doubleToLongBits(value) )
			);
	}



	public boolean compareAndSet(final double expect, final double update) {
		return
			this.value.compareAndSet(
				Double.doubleToLongBits(expect),
				Double.doubleToLongBits(update)
			);
	}



	public double getAndIncrement() {
		while (true) {
			final double value = this.get();
			final boolean result =
				this.value.compareAndSet(
					Double.doubleToLongBits(value),
					Double.doubleToLongBits(value + 1.0)
				);
			if (result)
				return value;
		}
	}
	public double getAndDecrement() {
		while (true) {
			final double value = this.get();
			final boolean result =
				this.value.compareAndSet(
					Double.doubleToLongBits(value),
					Double.doubleToLongBits(value - 1.0)
				);
			if (result)
				return value;
		}
	}



	public double incrementAndGet() {
		while (true) {
			final double value = this.get();
			final boolean result =
				this.value.compareAndSet(
					Double.doubleToLongBits(value),
					Double.doubleToLongBits(value + 1.0)
				);
			if (result)
				return value + 1.0;
		}
	}
	public double decrementAndGet() {
		while (true) {
			final double value = this.get();
			final boolean result =
				this.value.compareAndSet(
					Double.doubleToLongBits(value),
					Double.doubleToLongBits(value - 1.0)
				);
			if (result)
				return value - 1.0;
		}
	}



	public double getAndAdd(final double delta) {
		while (true) {
			final double value = this.get();
			final boolean result =
				this.value.compareAndSet(
					Double.doubleToLongBits(value),
					Double.doubleToLongBits(value + 1.0)
				);
			if (result)
				return value;
		}
	}
	public double addAndGet(final double delta) {
		while (true) {
			final double value = this.get();
			final boolean result =
				this.value.compareAndSet(
					Double.doubleToLongBits(value),
					Double.doubleToLongBits(value + delta)
				);
			if (result)
				return value + delta;
		}
	}



	@Override
	public String toString() {
		return Double.toString( this.get() );
	}



}
