package com.poixson.tools.dao;

import static com.poixson.utils.MathUtils.MinMax;


public class Tuple4Y extends Yabcd {
	private static final long serialVersionUID = 1L;



	public Tuple4Y() {
		super();
	}
	public Tuple4Y(final byte a, final byte b, final byte c, final byte d) {
		super(a, b, c, d);
	}
	public Tuple4Y(final Tuple4Y tup) {
		super(tup);
	}



	@Override
	public Object clone() {
		return new Tuple4Y(this.a, this.b, this.c, this.d);
	}



	public void get(final Tuple4Y tup) {
		tup.a = this.a;
		tup.b = this.b;
		tup.c = this.c;
		tup.d = this.d;
	}



	public void set(final byte a, final byte b, final byte c, final byte d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	public void set(final Tuple4Y tup) {
		this.a = tup.a;
		this.b = tup.b;
		this.c = tup.c;
		this.d = tup.d;
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
	public void setD(final byte d) {
		this.d = d;
	}



	public void add(final byte a, final byte b, final byte c, final byte d) {
		this.a += a;
		this.b += b;
		this.c += c;
		this.d += d;
	}
	public void add(final Tuple4Y tup) {
		this.a += tup.a;
		this.b += tup.b;
		this.c += tup.c;
		this.d += tup.d;
	}
	public void add(final Tuple4Y tupA, final Tuple4Y tupB) {
		this.a = (byte) (tupA.a + tupB.a);
		this.b = (byte) (tupA.b + tupB.b);
		this.c = (byte) (tupA.c + tupB.c);
		this.d = (byte) (tupA.d + tupB.d);
	}



	public void sub(final byte a, final byte b, final byte c, final byte d) {
		this.a -= a;
		this.b -= b;
		this.c -= c;
		this.d -= d;
	}
	public void sub(final Tuple4Y tup) {
		this.a -= tup.a;
		this.b -= tup.b;
		this.c -= tup.c;
		this.d -= tup.d;
	}
	public void sub(final Tuple4Y tupA, final Tuple4Y tupB) {
		this.a = (byte) (tupA.a - tupB.a);
		this.b = (byte) (tupA.b - tupB.b);
		this.c = (byte) (tupA.c - tupB.c);
		this.d = (byte) (tupA.d - tupB.d);
	}



	public void abs() {
		this.a = (byte) Math.abs(this.a);
		this.b = (byte) Math.abs(this.b);
		this.c = (byte) Math.abs(this.c);
		this.d = (byte) Math.abs(this.d);
	}



	public void neg(final Tuple4Y tup) {
		this.a = (byte) (0x0 - tup.a);
		this.b = (byte) (0x0 - tup.b);
		this.c = (byte) (0x0 - tup.c);
		this.d = (byte) (0x0 - tup.d);
	}
	public void neg() {
		this.a = (byte) (0x0 - this.a);
		this.b = (byte) (0x0 - this.b);
		this.c = (byte) (0x0 - this.c);
		this.d = (byte) (0x0 - this.d);
	}



	public void scale(final byte scale) {
		this.a *= scale;
		this.b *= scale;
		this.c *= scale;
		this.d *= scale;
	}
	public void scale(final double scale) {
		this.a = (byte) ( ((double)this.a) * scale );
		this.b = (byte) ( ((double)this.b) * scale );
		this.c = (byte) ( ((double)this.c) * scale );
		this.d = (byte) ( ((double)this.d) * scale );
	}
	public void scale(final float scale) {
		this.a = (byte) ( ((float)this.a) * scale );
		this.b = (byte) ( ((float)this.b) * scale );
		this.c = (byte) ( ((float)this.c) * scale );
		this.d = (byte) ( ((float)this.d) * scale );
	}



	public void clamp(final byte min, final byte max) {
		this.a = MinMax(this.a, min, max);
		this.b = MinMax(this.b, min, max);
		this.c = MinMax(this.c, min, max);
		this.d = MinMax(this.d, min, max);
	}
	public void clampMin(final byte min) {
		if (this.a < min) this.a = min;
		if (this.b < min) this.b = min;
		if (this.c < min) this.c = min;
		if (this.d < min) this.d = min;
	}
	public void clampMax(final byte max) {
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
