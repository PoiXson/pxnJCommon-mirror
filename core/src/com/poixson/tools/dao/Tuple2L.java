package com.poixson.tools.dao;

import com.poixson.utils.NumberUtils;


public class Tuple2L extends Lxy {
	private static final long serialVersionUID = 1L;



	public Tuple2L() {
		super();
	}
	public Tuple2L(final long x, final long y) {
		super(x, y);
	}
	public Tuple2L(final Tuple2L tup) {
		super(tup);
	}



	@Override
	public Object clone() {
		return new Tuple2L(this.x, this.y);
	}



	public void get(final Tuple2L tup) {
		tup.x = this.x;
		tup.y = this.y;
	}



	public void set(final long x, final long y) {
		this.x = x;
		this.y = y;
	}
	public void set(final Tuple2L tup) {
		this.x = tup.x;
		this.y = tup.y;
	}
	public void setX(final long x) {
		this.x = x;
	}
	public void setY(final long y) {
		this.y = y;
	}



	public void add(final long x, final long y) {
		this.x += x;
		this.y += y;
	}
	public void add(final Tuple2L tup) {
		this.x += tup.x;
		this.y += tup.y;
	}
	public void add(final Tuple2L tupA, final Tuple2L tupB) {
		this.x = tupA.x + tupB.x;
		this.y = tupA.y + tupB.y;
	}



	public void sub(final long x, final long y) {
		this.x -= x;
		this.y -= y;
	}
	public void sub(final Tuple2L tup) {
		this.x -= tup.x;
		this.y -= tup.y;
	}
	public void sub(final Tuple2L tupA, final Tuple2L tupB) {
		this.x = tupA.x - tupB.x;
		this.y = tupA.y - tupB.y;
	}



	public void abs() {
		this.x = Math.abs(this.x);
		this.y = Math.abs(this.y);
	}



	public void neg(final Tuple2L tup) {
		this.x = 0 - tup.x;
		this.y = 0 - tup.y;
	}
	public void neg() {
		this.x = 0 - this.x;
		this.y = 0 - this.y;
	}



	public void scale(final long scale) {
		this.x *= scale;
		this.y *= scale;
	}
	public void scale(final double scale) {
		this.x = (long) ( ((double)this.x) * scale );
		this.y = (long) ( ((double)this.y) * scale );
	}
	public void scale(final float scale) {
		this.x = (long) ( ((float)this.x) * scale );
		this.y = (long) ( ((float)this.y) * scale );
	}



	public void clamp(final long min, final long max) {
		this.x = NumberUtils.MinMax(this.x, min, max);
		this.y = NumberUtils.MinMax(this.y, min, max);
	}
	public void clampMin(final long min) {
		if (this.x < min) this.x = min;
		if (this.y < min) this.y = min;
	}
	public void clampMax(final long max) {
		if (this.x > max) this.x = max;
		if (this.y > max) this.y = max;
	}



	public double vectorLength() {
		final double x = (double) this.x;
		final double y = (double) this.y;
		return Math.sqrt( (x*x) + (y*y) );
	}



}
