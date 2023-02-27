package com.poixson.tools.dao;

import com.poixson.utils.NumberUtils;


public class Tuple2I extends Iab {
	private static final long serialVersionUID = 1L;



	public Tuple2I() {
		super();
	}
	public Tuple2I(final int a, final int b) {
		super(a, b);
	}
	public Tuple2I(final Tuple2I tup) {
		super(tup);
	}



	@Override
	public Object clone() {
		return new Tuple2I(this.a, this.b);
	}



	public void get(final Tuple2I tup) {
		tup.a = this.a;
		tup.b = this.b;
	}



	public void set(final int a, final int b) {
		this.a = a;
		this.b = b;
	}
	public void set(final Tuple2I tup) {
		this.a = tup.a;
		this.b = tup.b;
	}
	public void setX(final int a) {
		this.a = a;
	}
	public void setY(final int b) {
		this.b = b;
	}



	public void add(final int a, final int b) {
		this.a += a;
		this.b += b;
	}
	public void add(final Tuple2I tup) {
		this.a += tup.a;
		this.b += tup.b;
	}
	public void add(final Tuple2I tupA, final Tuple2I tupB) {
		this.a = tupA.a + tupB.a;
		this.b = tupA.b + tupB.b;
	}



	public void sub(final int a, final int b) {
		this.a -= a;
		this.b -= b;
	}
	public void sub(final Tuple2I tup) {
		this.a -= tup.a;
		this.b -= tup.b;
	}
	public void sub(final Tuple2I tupA, final Tuple2I tupB) {
		this.a = tupA.a - tupB.a;
		this.b = tupA.b - tupB.b;
	}



	public void abs() {
		this.a = Math.abs(this.a);
		this.b = Math.abs(this.b);
	}



	public void neg(final Tuple2I tup) {
		this.a = 0 - tup.a;
		this.b = 0 - tup.b;
	}
	public void neg() {
		this.a = 0 - this.a;
		this.b = 0 - this.b;
	}



	public void scale(final int scale) {
		this.a *= scale;
		this.b *= scale;
	}
	public void scale(final double scale) {
		this.a = (int) ( ((double)this.a) * scale );
		this.b = (int) ( ((double)this.b) * scale );
	}
	public void scale(final float scale) {
		this.a = (int) ( ((float)this.a) * scale );
		this.b = (int) ( ((float)this.b) * scale );
	}



	public void clamp(final int min, final int max) {
		this.a = NumberUtils.MinMax(this.a, min, max);
		this.b = NumberUtils.MinMax(this.b, min, max);
	}
	public void clampMin(final int min) {
		if (this.a < min) this.a = min;
		if (this.b < min) this.b = min;
	}
	public void clampMax(final int max) {
		if (this.a > max) this.a = max;
		if (this.b > max) this.b = max;
	}



	public double vectorLength() {
		final double a = (double) this.a;
		final double b = (double) this.b;
		return Math.sqrt( (a*a) + (b*b) );
	}



}
