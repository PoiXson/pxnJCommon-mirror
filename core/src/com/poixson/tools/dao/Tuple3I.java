package com.poixson.tools.dao;

import static com.poixson.utils.MathUtils.MinMax;


public class Tuple3I extends Iabc {
	private static final long serialVersionUID = 1L;



	public Tuple3I() {
		this.a = 0;
		this.b = 0;
		this.c = 0;
	}
	public Tuple3I(final int a, final int b, final int c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	public Tuple3I(final Tuple3I tup) {
		this.a = tup.a;
		this.b = tup.b;
		this.c = tup.c;
	}



	@Override
	public Object clone() {
		return new Tuple3I(this.a, this.b, this.c);
	}



	public void get(final Tuple3I tup) {
		tup.a = this.a;
		tup.b = this.b;
		tup.c = this.c;
	}



	public void set(final int a, final int b, final int c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	public void set(final Tuple3I tup) {
		this.a = tup.a;
		this.b = tup.b;
		this.c = tup.c;
	}
	public void setA(final int a) {
		this.a = a;
	}
	public void setB(final int b) {
		this.b = b;
	}
	public void setC(final int c) {
		this.c = c;
	}



	public void add(final int a, final int b, final int c) {
		this.a += a;
		this.b += b;
		this.c += c;
	}
	public void add(final Tuple3I tup) {
		this.a += tup.a;
		this.b += tup.b;
		this.c += tup.c;
	}
	public void add(final Tuple3I tupA, final Tuple3I tupB) {
		this.a = tupA.a + tupB.a;
		this.b = tupA.b + tupB.b;
		this.c = tupA.c + tupB.c;
	}



	public void sub(final int a, final int b, final int c) {
		this.a -= a;
		this.b -= b;
		this.c -= c;
	}
	public void sub(final Tuple3I tup) {
		this.a -= tup.a;
		this.b -= tup.b;
		this.c -= tup.c;
	}
	public void sub(final Tuple3I tupA, final Tuple3I tupB) {
		this.a = tupA.a - tupB.a;
		this.b = tupA.b - tupB.b;
		this.c = tupA.c - tupB.c;
	}



	public void abs() {
		this.a = Math.abs(this.a);
		this.b = Math.abs(this.b);
		this.c = Math.abs(this.c);
	}



	public void neg(final Tuple3I tup) {
		this.a = 0 - tup.a;
		this.b = 0 - tup.b;
		this.c = 0 - tup.c;
	}
	public void neg() {
		this.a = 0 - this.a;
		this.b = 0 - this.b;
		this.c = 0 - this.c;
	}



	public void scale(final int scale) {
		this.a *= scale;
		this.b *= scale;
		this.c *= scale;
	}
	public void scale(final double scale) {
		this.a = (int) ( ((double)this.a) * scale );
		this.b = (int) ( ((double)this.b) * scale );
		this.c = (int) ( ((double)this.c) * scale );
	}
	public void scale(final float scale) {
		this.a = (int) ( ((float)this.a) * scale );
		this.b = (int) ( ((float)this.b) * scale );
		this.c = (int) ( ((float)this.c) * scale );
	}



	public void clamp(final int min, final int max) {
		this.a = MinMax(this.a, min, max);
		this.b = MinMax(this.b, min, max);
		this.c = MinMax(this.c, min, max);
	}
	public void clampMin(final int min) {
		if (this.a < min) this.a = min;
		if (this.b < min) this.b = min;
		if (this.c < min) this.c = min;
	}
	public void clampMax(final int max) {
		if (this.a > max) this.a = max;
		if (this.b > max) this.b = max;
		if (this.c > max) this.c = max;
	}



	public double vectorLength() {
		final double a = (double) this.a;
		final double b = (double) this.b;
		final double c = (double) this.c;
		return Math.sqrt( (a*a) + (b*b) + (c*c) );
	}



}
