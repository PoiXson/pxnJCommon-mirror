package com.poixson.tools.indexselect;

import com.poixson.tools.xRand;


public class IndexSelectRandom implements IndexSelect {

	public final int min, max;

	public final xRand rnd = new xRand();



	public IndexSelectRandom() {
		this(0, 0);
	}
	public IndexSelectRandom(final int max) {
		this(0, max);
	}
	public IndexSelectRandom(final int min, final int max) {
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
		return this.rnd.nextInt(min, max);
	}



}
