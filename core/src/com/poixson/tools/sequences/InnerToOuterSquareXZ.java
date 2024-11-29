package com.poixson.tools.sequences;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import com.poixson.tools.dao.Iab;


public class InnerToOuterSquareXZ implements Iterator<Iab> {

	public final int size;
	public final int half;

	public final AtomicInteger index = new AtomicInteger(0);



	public InnerToOuterSquareXZ(final int size) {
		this.half = Math.ceilDiv(size+1, 2);
		this.size = (this.half * 2) - 1;
	}



	@Override
	public Iab next() {
		return this.getIndex(this.index.getAndIncrement());
	}
	@Override
	public boolean hasNext() {
		return (this.index.get() < this.size * this.size);
	}



	public Iab getIndex(final int index) {
		if (index == 0)
			return new Iab(0, 0);
		int remaining = index - 1;
		int layer     = 0;
		int current   = 0;
		// find layer
		LOOP_LAYER:
		while (layer <= this.half) {
			final int perim = layer * 8;
			if (remaining < current + perim)
				break LOOP_LAYER;
			current += perim;
			layer++;
		}
		// relative to current layer
		remaining -= current;
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
