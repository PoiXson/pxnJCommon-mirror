package com.poixson.tools.dao;

import static com.poixson.utils.MathUtils.MinMax;


public class Tuple4L extends Labcd {
	private static final long serialVersionUID = 1L;



	public Tuple4L() {
		this.a = 0;
		this.b = 0;
		this.c = 0;
		this.d = 0;
	}
	public Tuple4L(final long a, final long b, final long c, final long d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	public Tuple4L(final Tuple4L tup) {
		this.a = tup.a;
		this.b = tup.b;
		this.c = tup.c;
		this.d = tup.d;
	}



	@Override
	public Object clone() {
		return new Tuple4L(this.a, this.b, this.c, this.d);
	}



	public void get(final Tuple4L tup) {
		tup.a = this.a;
		tup.b = this.b;
		tup.c = this.c;
		tup.d = this.d;
	}



	public void set(final long a, final long b, final long c, final long d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	public void set(final Tuple4L tup) {
		this.a = tup.a;
		this.b = tup.b;
		this.c = tup.c;
		this.d = tup.d;
	}

	public void setA(final long a) {
		this.a = a;
	}
	public void setB(final long b) {
		this.b = b;
	}
	public void setC(final long c) {
		this.c = c;
	}
	public void setD(final long d) {
		this.d = d;
	}



	public void add(final long a, final long b, final long c, final long d) {
		this.a += a;
		this.b += b;
		this.c += c;
		this.d += d;
	}
	public void add(final Tuple4L tup) {
		this.a += tup.a;
		this.b += tup.b;
		this.c += tup.c;
		this.d += tup.d;
	}
	public void add(final Tuple4L tupA, final Tuple4L tupB) {
		this.a = tupA.a + tupB.a;
		this.b = tupA.b + tupB.b;
		this.c = tupA.c + tupB.c;
		this.d = tupA.d + tupB.d;
	}



	public void sub(final long a, final long b, final long c, final long d) {
		this.a -= a;
		this.b -= b;
		this.c -= c;
		this.d -= d;
	}
	public void sub(final Tuple4L tup) {
		this.a -= tup.a;
		this.b -= tup.b;
		this.c -= tup.c;
		this.d -= tup.d;
	}
	public void sub(final Tuple4L tupA, final Tuple4L tupB) {
		this.a = tupA.a - tupB.a;
		this.b = tupA.b - tupB.b;
		this.c = tupA.c - tupB.c;
		this.d = tupA.d - tupB.d;
	}



	public void abs() {
		this.a = Math.abs(this.a);
		this.b = Math.abs(this.b);
		this.c = Math.abs(this.c);
		this.d = Math.abs(this.d);
	}



	public void neg(final Tuple4L tup) {
		this.a = 0 - tup.a;
		this.b = 0 - tup.b;
		this.c = 0 - tup.c;
		this.d = 0 - tup.d;
	}
	public void neg() {
		this.a = 0 - this.a;
		this.b = 0 - this.b;
		this.c = 0 - this.c;
		this.d = 0 - this.d;
	}



	public void scale(final long scale) {
		this.a *= scale;
		this.b *= scale;
		this.c *= scale;
		this.d *= scale;
	}
	public void scale(final double scale) {
		this.a = (long) ( ((double)this.a) * scale );
		this.b = (long) ( ((double)this.b) * scale );
		this.c = (long) ( ((double)this.c) * scale );
		this.d = (long) ( ((double)this.d) * scale );
	}
	public void scale(final float scale) {
		this.a = (long) ( ((float)this.a) * scale );
		this.b = (long) ( ((float)this.b) * scale );
		this.c = (long) ( ((float)this.c) * scale );
		this.d = (long) ( ((float)this.d) * scale );
	}



	public void clamp(final long min, final long max) {
		this.a = MinMax(this.a, min, max);
		this.b = MinMax(this.b, min, max);
		this.c = MinMax(this.c, min, max);
		this.d = MinMax(this.d, min, max);
	}
	public void clampMin(final long min) {
		if (this.a < min) this.a = min;
		if (this.b < min) this.b = min;
		if (this.c < min) this.c = min;
		if (this.d < min) this.d = min;
	}
	public void clampMax(final long max) {
		if (this.a > max) this.a = max;
		if (this.b > max) this.b = max;
		if (this.c > max) this.c = max;
		if (this.d > max) this.d = max;
	}



	public double vectorLength() {
		final double a = (double) this.a;
		final double b = (double) this.b;
		final double c = (double) this.c;
		final double d = (double) this.d;
		return Math.sqrt( (a*a) + (b*b) + (c*c) + (d*d) );
	}



}
