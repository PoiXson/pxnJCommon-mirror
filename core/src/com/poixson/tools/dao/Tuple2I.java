package com.poixson.tools.dao;

import com.poixson.utils.NumberUtils;


public class Tuple2I extends Ixy {
	private static final long serialVersionUID = 1L;



	public Tuple2I() {
		super();
	}
	public Tuple2I(final int x, final int y) {
		super(x, y);
	}
	public Tuple2I(final Tuple2I tup) {
		super(tup);
	}



	@Override
	public Object clone() {
		return new Tuple2I(this.x, this.y);
	}



	public void get(final Tuple2I tup) {
		tup.x = this.x;
		tup.y = this.y;
	}



	public void set(final int x, final int y) {
		this.x = x;
		this.y = y;
	}
	public void set(final Tuple2I tup) {
		this.x = tup.x;
		this.y = tup.y;
	}
	public void setX(final int x) {
		this.x = x;
	}
	public void setY(final int y) {
		this.y = y;
	}



	public void add(final int x, final int y) {
		this.x += x;
		this.y += y;
	}
	public void add(final Tuple2I tup) {
		this.x += tup.x;
		this.y += tup.y;
	}
	public void add(final Tuple2I tupA, final Tuple2I tupB) {
		this.x = tupA.x + tupB.x;
		this.y = tupA.y + tupB.y;
	}



	public void sub(final int x, final int y) {
		this.x -= x;
		this.y -= y;
	}
	public void sub(final Tuple2I tup) {
		this.x -= tup.x;
		this.y -= tup.y;
	}
	public void sub(final Tuple2I tupA, final Tuple2I tupB) {
		this.x = tupA.x - tupB.x;
		this.y = tupA.y - tupB.y;
	}



	public void abs() {
		this.x = Math.abs(this.x);
		this.y = Math.abs(this.y);
	}



	public void neg(final Tuple2I tup) {
		this.x = 0 - tup.x;
		this.y = 0 - tup.y;
	}
	public void neg() {
		this.x = 0 - this.x;
		this.y = 0 - this.y;
	}



	public void scale(final int scale) {
		this.x *= scale;
		this.y *= scale;
	}
	public void scale(final double scale) {
		this.x = (int) ( ((double)this.x) * scale );
		this.y = (int) ( ((double)this.y) * scale );
	}
	public void scale(final float scale) {
		this.x = (int) ( ((float)this.x) * scale );
		this.y = (int) ( ((float)this.y) * scale );
	}



	public void clamp(final int min, final int max) {
		this.x = NumberUtils.MinMax(this.x, min, max);
		this.y = NumberUtils.MinMax(this.y, min, max);
	}
	public void clampMin(final int min) {
		if (this.x < min) this.x = min;
		if (this.y < min) this.y = min;
	}
	public void clampMax(final int max) {
		if (this.x > max) this.x = max;
		if (this.y > max) this.y = max;
	}



	public double vectorLength() {
		final double x = (double) this.x;
		final double y = (double) this.y;
		return Math.sqrt( (x*x) + (y*y) );
	}



}
