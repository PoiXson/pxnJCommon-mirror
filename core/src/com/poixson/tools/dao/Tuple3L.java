package com.poixson.tools.dao;

import static com.poixson.utils.MathUtils.MinMax;


public class Tuple3L extends Labc {
	private static final long serialVersionUID = 1L;



	public Tuple3L() {
		this.a = 0;
		this.b = 0;
		this.c = 0;
	}
	public Tuple3L(final long a, final long b, final long c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	public Tuple3L(final Tuple3L tup) {
		this.a = tup.a;
		this.b = tup.b;
		this.c = tup.c;
	}



	@Override
	public Object clone() {
		return new Tuple3L(this.a, this.b, this.c);
	}



	public void get(final Tuple3L tup) {
		tup.a = this.a;
		tup.b = this.b;
		tup.c = this.c;
	}



	public void set(final long a, final long b, final long c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	public void set(final Tuple3L tup) {
		this.a = tup.a;
		this.b = tup.b;
		this.c = tup.c;
	}
	public void setX(final long a) {
		this.a = a;
	}
	public void setY(final long b) {
		this.b = b;
	}
	public void setZ(final long c) {
		this.c = c;
	}



	public void add(final long a, final long b, final long c) {
		this.a += a;
		this.b += b;
		this.c += c;
	}
	public void add(final Tuple3L tup) {
		this.a += tup.a;
		this.b += tup.b;
		this.c += tup.c;
	}
	public void add(final Tuple3L tupA, final Tuple3L tupB) {
		this.a = tupA.a + tupB.a;
		this.b = tupA.b + tupB.b;
		this.c = tupA.c + tupB.c;
	}



	public void sub(final long a, final long b, final long c) {
		this.a -= a;
		this.b -= b;
		this.c -= c;
	}
	public void sub(final Tuple3L tup) {
		this.a -= tup.a;
		this.b -= tup.b;
		this.c -= tup.c;
	}
	public void sub(final Tuple3L tupA, final Tuple3L tupB) {
		this.a = tupA.a - tupB.a;
		this.b = tupA.b - tupB.b;
		this.c = tupA.c - tupB.c;
	}



	public void abs() {
		this.a = Math.abs(this.a);
		this.b = Math.abs(this.b);
		this.c = Math.abs(this.c);
	}



	public void neg(final Tuple3L tup) {
		this.a = 0 - tup.a;
		this.b = 0 - tup.b;
		this.c = 0 - tup.c;
	}
	public void neg() {
		this.a = 0 - this.a;
		this.b = 0 - this.b;
		this.c = 0 - this.c;
	}



	public void scale(final long scale) {
		this.a *= scale;
		this.b *= scale;
		this.c *= scale;
	}
	public void scale(final double scale) {
		this.a = (long) ( ((double)this.a) * scale );
		this.b = (long) ( ((double)this.b) * scale );
		this.c = (long) ( ((double)this.c) * scale );
	}
	public void scale(final float scale) {
		this.a = (long) ( ((float)this.a) * scale );
		this.b = (long) ( ((float)this.b) * scale );
		this.c = (long) ( ((float)this.c) * scale );
	}



	public void clamp(final long min, final long max) {
		this.a = MinMax(this.a, min, max);
		this.b = MinMax(this.b, min, max);
		this.c = MinMax(this.c, min, max);
	}
	public void clampMin(final long min) {
		if (this.a < min) this.a = min;
		if (this.b < min) this.b = min;
		if (this.c < min) this.c = min;
	}
	public void clampMax(final long max) {
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
