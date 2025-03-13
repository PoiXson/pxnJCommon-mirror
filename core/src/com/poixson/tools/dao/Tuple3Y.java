package com.poixson.tools.dao;

import static com.poixson.utils.MathUtils.MinMax;


public class Tuple3Y extends Yabc {
	private static final long serialVersionUID = 1L;



	public Tuple3Y() {
		super();
	}
	public Tuple3Y(final byte a, final byte b, final byte c) {
		super(a, b, c);
	}
	public Tuple3Y(final Tuple3Y tup) {
		super(tup);
	}



	@Override
	public Object clone() {
		return new Tuple3Y(this.a, this.b, this.c);
	}



	public void get(final Tuple3Y tup) {
		tup.a = this.a;
		tup.b = this.b;
		tup.c = this.c;
	}



	public void set(final byte a, final byte b, final byte c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	public void set(final Tuple3Y tup) {
		this.a = tup.a;
		this.b = tup.b;
		this.c = tup.c;
	}
	public void setA(final byte a) {
		this.a = a;
	}
	public void setB(final byte b) {
		this.b = b;
	}
	public void setC(final byte c) {
		this.c = c;
	}



	public void add(final byte a, final byte b, final byte c) {
		this.a += a;
		this.b += b;
		this.c += c;
	}
	public void add(final Tuple3Y tup) {
		this.a += tup.a;
		this.b += tup.b;
		this.c += tup.c;
	}
	public void add(final Tuple3Y tupA, final Tuple3Y tupB) {
		this.a = (byte) (tupA.a + tupB.a);
		this.b = (byte) (tupA.b + tupB.b);
		this.c = (byte) (tupA.c + tupB.c);
	}



	public void sub(final byte a, final byte b, final byte c) {
		this.a -= a;
		this.b -= b;
		this.c -= c;
	}
	public void sub(final Tuple3Y tup) {
		this.a -= tup.a;
		this.b -= tup.b;
		this.c -= tup.c;
	}
	public void sub(final Tuple3Y tupA, final Tuple3Y tupB) {
		this.a = (byte) (tupA.a - tupB.a);
		this.b = (byte) (tupA.b - tupB.b);
		this.c = (byte) (tupA.c - tupB.c);
	}



	public void abs() {
		this.a = (byte) Math.abs(this.a);
		this.b = (byte) Math.abs(this.b);
		this.c = (byte) Math.abs(this.c);
	}



	public void neg(final Tuple3Y tup) {
		this.a = (byte) (0x0 - tup.a);
		this.b = (byte) (0x0 - tup.b);
		this.c = (byte) (0x0 - tup.c);
	}
	public void neg() {
		this.a = (byte) (0x0 - this.a);
		this.b = (byte) (0x0 - this.b);
		this.c = (byte) (0x0 - this.c);
	}



	public void scale(final byte scale) {
		this.a *= scale;
		this.b *= scale;
		this.c *= scale;
	}
	public void scale(final double scale) {
		this.a = (byte) ( ((double)this.a) * scale );
		this.b = (byte) ( ((double)this.b) * scale );
		this.c = (byte) ( ((double)this.c) * scale );
	}
	public void scale(final float scale) {
		this.a = (byte) ( ((float)this.a) * scale );
		this.b = (byte) ( ((float)this.b) * scale );
		this.c = (byte) ( ((float)this.c) * scale );
	}



	public void clamp(final byte min, final byte max) {
		this.a = MinMax(this.a, min, max);
		this.b = MinMax(this.b, min, max);
		this.c = MinMax(this.c, min, max);
	}
	public void clampMin(final byte min) {
		if (this.a < min) this.a = min;
		if (this.b < min) this.b = min;
		if (this.c < min) this.c = min;
	}
	public void clampMax(final byte max) {
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
