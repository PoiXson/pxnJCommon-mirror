package com.poixson.tools.sequences;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import com.poixson.tools.dao.Iab;
import com.poixson.tools.dao.Iabc;


public class OuterToInnerSquareXYZ implements Iterator<Iabc> {

	public final int size_xz;
	public final int half_xz;
	public final int size_y;
	public final int total;

	public final OuterToInnerSquareXZ it_xz;
	public final AtomicInteger index = new AtomicInteger(0);



	public OuterToInnerSquareXYZ(final int size_xz, final int size_y) {
		this.it_xz = new OuterToInnerSquareXZ(size_xz);
		this.half_xz = this.it_xz.half;
		this.size_xz = this.it_xz.size;
		this.size_y  = Math.max(size_y, 1);
		this.total = this.size_xz * this.size_xz * this.size_y;
	}



	@Override
	public Iabc next() {
		return this.getIndex(this.index.getAndIncrement());
	}
	@Override
	public boolean hasNext() {
		return (this.index.get() < this.total);
	}



	public Iabc getIndex(final int index) {
		final int ind = Math.floorDiv(index, this.size_y);
		final Iab dao = this.it_xz.getIndex(ind);
		final int y = index % this.size_y;
		return new Iabc(dao.a, y, dao.b);
	}



}
