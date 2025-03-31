/*
package com.poixson.tools.indexselect;

import java.util.concurrent.atomic.AtomicLong;


public class IndexSelectCycle implements IndexSelect {

	public final int min, max;

	public final AtomicLong index = new AtomicLong(0L);



	public IndexSelectCycle() {
		this(0, 0);
	}
	public IndexSelectCycle(final int max) {
		this(0, max);
	}
	public IndexSelectCycle(final int min, final int max) {
		this.min = min;
		this.max = max;
	}



	@Override
	public int next() {
		return this.next(this.min, this.max);
	}
	@Override
	public int next(final int max) {
		return this.next(this.min, max);
	}
	@Override
	public int next(final int min, final int max) {
		final long index = this.index.getAndIncrement();
		final long range = (long) (max - min);
		return ((int)(index % range)) + min;
	}



}
*/
