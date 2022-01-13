package com.poixson.tools.dao;

import com.poixson.utils.NumberUtils;


public class Tuple4I extends Iwxyz {
	private static final long serialVersionUID = 1L;



	public Tuple4I() {
		this.w = 0;
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	public Tuple4I(final int w, final int x, final int y, final int z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Tuple4I(final Tuple4I tup) {
		this.w = tup.w;
		this.x = tup.x;
		this.y = tup.y;
		this.z = tup.z;
	}



	@Override
	public Object clone() {
		return new Tuple4I(this.w, this.x, this.y, this.z);
	}



	public void get(final Tuple4I tup) {
		tup.w = this.w;
		tup.x = this.x;
		tup.y = this.y;
		tup.z = this.z;
	}



	public void set(final int w, final int x, final int y, final int z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public void set(final Tuple4I tup) {
		this.w = tup.w;
		this.x = tup.x;
		this.y = tup.y;
		this.z = tup.z;
	}
	public void setW(final int w) {
		this.w = w;
	}
	public void setX(final int x) {
		this.x = x;
	}
	public void setY(final int y) {
		this.y = y;
	}
	public void setZ(final int z) {
		this.z = z;
	}



	public void add(final int w, final int x, final int y, final int z) {
		this.w += w;
		this.x += x;
		this.y += y;
		this.z += z;
	}
	public void add(final Tuple4I tup) {
		this.w += tup.w;
		this.x += tup.x;
		this.y += tup.y;
		this.z += tup.z;
	}
	public void add(final Tuple4I tupA, final Tuple4I tupB) {
		this.w = tupA.w + tupB.w;
		this.x = tupA.x + tupB.x;
		this.y = tupA.y + tupB.y;
		this.z = tupA.z + tupB.z;
	}



	public void sub(final int w, final int x, final int y, final int z) {
		this.w -= w;
		this.x -= x;
		this.y -= y;
		this.z -= z;
	}
	public void sub(final Tuple4I tup) {
		this.w -= tup.w;
		this.x -= tup.x;
		this.y -= tup.y;
		this.z -= tup.z;
	}
	public void sub(final Tuple4I tupA, final Tuple4I tupB) {
		this.w = tupA.w - tupB.w;
		this.x = tupA.x - tupB.x;
		this.y = tupA.y - tupB.y;
		this.z = tupA.z - tupB.z;
	}



	public void abs() {
		this.w = Math.abs(this.w);
		this.x = Math.abs(this.x);
		this.y = Math.abs(this.y);
		this.z = Math.abs(this.z);
	}



	public void neg(final Tuple4I tup) {
		this.w = 0 - tup.w;
		this.x = 0 - tup.x;
		this.y = 0 - tup.y;
		this.z = 0 - tup.z;
	}
	public void neg() {
		this.w = 0 - this.w;
		this.x = 0 - this.x;
		this.y = 0 - this.y;
		this.z = 0 - this.z;
	}



	public void scale(final int scale) {
		this.w *= scale;
		this.x *= scale;
		this.y *= scale;
		this.z *= scale;
	}
	public void scale(final double scale) {
		this.w = (int) ( ((double)this.w) * scale );
		this.x = (int) ( ((double)this.x) * scale );
		this.y = (int) ( ((double)this.y) * scale );
		this.z = (int) ( ((double)this.z) * scale );
	}
	public void scale(final float scale) {
		this.w = (int) ( ((float)this.w) * scale );
		this.x = (int) ( ((float)this.x) * scale );
		this.y = (int) ( ((float)this.y) * scale );
		this.z = (int) ( ((float)this.z) * scale );
	}



	public void clamp(final int min, final int max) {
		this.w = NumberUtils.MinMax(this.w, min, max);
		this.x = NumberUtils.MinMax(this.x, min, max);
		this.y = NumberUtils.MinMax(this.y, min, max);
		this.z = NumberUtils.MinMax(this.z, min, max);
	}
	public void clampMin(final int min) {
		if (this.w < min) this.w = min;
		if (this.x < min) this.x = min;
		if (this.y < min) this.y = min;
		if (this.z < min) this.z = min;
	}
	public void clampMax(final int max) {
		if (this.w > max) this.w = max;
		if (this.x > max) this.x = max;
		if (this.y > max) this.y = max;
		if (this.z > max) this.z = max;
	}



	public double vectorLength() {
		final double w = (double) this.w;
		final double x = (double) this.x;
		final double y = (double) this.y;
		final double z = (double) this.z;
		return Math.sqrt( (w*w) + (x*x) + (y*y) + (z*z) );
	}



}
