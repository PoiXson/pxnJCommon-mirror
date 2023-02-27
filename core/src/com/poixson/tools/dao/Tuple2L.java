package com.poixson.tools.dao;

import com.poixson.utils.NumberUtils;


public class Tuple2L extends Lab {
	private static final long serialVersionUID = 1L;



	public Tuple2L() {
		super();
	}
	public Tuple2L(final long a, final long b) {
		super(a, b);
	}
	public Tuple2L(final Tuple2L tup) {
		super(tup);
	}



	@Override
	public Object clone() {
		return new Tuple2L(this.a, this.b);
	}



	public void get(final Tuple2L tup) {
		tup.a = this.a;
		tup.b = this.b;
	}



	public void set(final long a, final long b) {
		this.a = a;
		this.b = b;
	}
	public void set(final Tuple2L tup) {
		this.a = tup.a;
		this.b = tup.b;
	}
	public void setX(final long a) {
		this.a = a;
	}
	public void setY(final long b) {
		this.b = b;
	}



	public void add(final long a, final long b) {
		this.a += a;
		this.b += b;
	}
	public void add(final Tuple2L tup) {
		this.a += tup.a;
		this.b += tup.b;
	}
	public void add(final Tuple2L tupA, final Tuple2L tupB) {
		this.a = tupA.a + tupB.a;
		this.b = tupA.b + tupB.b;
	}



	public void sub(final long a, final long b) {
		this.a -= a;
		this.b -= b;
	}
	public void sub(final Tuple2L tup) {
		this.a -= tup.a;
		this.b -= tup.b;
	}
	public void sub(final Tuple2L tupA, final Tuple2L tupB) {
		this.a = tupA.a - tupB.a;
		this.b = tupA.b - tupB.b;
	}



	public void abs() {
		this.a = Math.abs(this.a);
		this.b = Math.abs(this.b);
	}



	public void neg(final Tuple2L tup) {
		this.a = 0 - tup.a;
		this.b = 0 - tup.b;
	}
	public void neg() {
		this.a = 0 - this.a;
		this.b = 0 - this.b;
	}



	public void scale(final long scale) {
		this.a *= scale;
		this.b *= scale;
	}
	public void scale(final double scale) {
		this.a = (long) ( ((double)this.a) * scale );
		this.b = (long) ( ((double)this.b) * scale );
	}
	public void scale(final float scale) {
		this.a = (long) ( ((float)this.a) * scale );
		this.b = (long) ( ((float)this.b) * scale );
	}



	public void clamp(final long min, final long max) {
		this.a = NumberUtils.MinMax(this.a, min, max);
		this.b = NumberUtils.MinMax(this.b, min, max);
	}
	public void clampMin(final long min) {
		if (this.a < min) this.a = min;
		if (this.b < min) this.b = min;
	}
	public void clampMax(final long max) {
		if (this.a > max) this.a = max;
		if (this.b > max) this.b = max;
	}



	public double vectorLength() {
		final double a = (double) this.a;
		final double b = (double) this.b;
		return Math.sqrt( (a*a) + (b*b) );
	}



}
