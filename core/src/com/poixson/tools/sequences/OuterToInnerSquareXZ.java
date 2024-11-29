package com.poixson.tools.sequences;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import com.poixson.tools.dao.Iab;


public class OuterToInnerSquareXZ implements Iterator<Iab> {

	public final int size;
	public final int half;
	public final int total;

	public final AtomicInteger index = new AtomicInteger(0);



	public OuterToInnerSquareXZ(final int size) {
		this.half = Math.ceilDiv(size+1, 2);
		this.size = (this.half * 2) - 1;
		this.total = this.size * this.size;
	}



	@Override
	public Iab next() {
		return this.getIndex(this.index.getAndIncrement());
	}
	@Override
	public boolean hasNext() {
		return (this.index.get() < this.total);
	}



	public Iab getIndex(final int index) {
		int remaining = index;
		int layer     = this.half - 1;
		// find layer
		LOOP_LAYER:
		while (layer > 0) {
			final int perim = layer * 8;
			if (remaining < perim)
				break LOOP_LAYER;
			remaining -= perim;
			layer--;
		}
		// relative to current layer
		final int max = layer;
		final int min = 0 - max;
		final int side = (max * 2) + 1;
		int x = 0;
		int z = 0;
		// top edge, left to right
		if (remaining < side) {
			x = min + remaining;
			z = min;
		} else
		// right edge, top to bottom
		if (remaining < (side * 2) - 1) {
			remaining -= side;
			x = max;
			z = min + remaining + 1;
		} else
		// bottom edge, right to left
		if (remaining < (side * 3) - 2) {
			remaining -= (side * 2) - 1;
			x = max - (remaining + 1);
			z = max;
		// left edge, bottom to top
		} else {
			remaining -= (side * 3) - 2;
			x = min;
			z = max - (remaining + 1);
		}
		return new Iab(x, z);
	}



}
