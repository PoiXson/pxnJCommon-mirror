package com.poixson.tools.dao;

import static com.poixson.utils.MathUtils.MinMax;


public class Tuple4D extends Dabcd {
	private static final long serialVersionUID = 1L;



	public Tuple4D() {
		super();
	}
	public Tuple4D(final double a, final double b, final double c, final double d) {
		super(a, b, c, d);
	}
	public Tuple4D(final Tuple4D tup) {
		super(tup);
	}



	@Override
	public Object clone() {
		return new Tuple4D(this.a, this.b, this.c, this.d);
	}



	public void get(final Tuple4D tup) {
		tup.a = this.a;
		tup.b = this.b;
		tup.c = this.c;
		tup.d = this.d;
	}



	public void set(final double a, final double b, final double c, final double d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	public void set(final Tuple4D tup) {
		this.a = tup.a;
		this.b = tup.b;
		this.c = tup.c;
		this.d = tup.d;
	}
	public void setW(final double a) {
		this.a = a;
	}
	public void setX(final double b) {
		this.b = b;
	}
	public void setY(final double c) {
		this.c = c;
	}
	public void setZ(final double d) {
		this.d = d;
	}



	public void add(final double a, final double b, final double c, final double d) {
		this.a += a;
		this.b += b;
		this.c += c;
		this.d += d;
	}
	public void add(final Tuple4D tup) {
		this.a += tup.a;
		this.b += tup.b;
		this.c += tup.c;
		this.d += tup.d;
	}
	public void add(final Tuple4D tupA, final Tuple4D tupB) {
		this.a = tupA.a + tupB.a;
		this.b = tupA.b + tupB.b;
		this.c = tupA.c + tupB.c;
		this.d = tupA.d + tupB.d;
	}



	public void sub(final double a, final double b, final double c, final double d) {
		this.a -= a;
		this.b -= b;
		this.c -= c;
		this.d -= d;
	}
	public void sub(final Tuple4D tup) {
		this.a -= tup.a;
		this.b -= tup.b;
		this.c -= tup.c;
		this.d -= tup.d;
	}
	public void sub(final Tuple4D tupA, final Tuple4D tupB) {
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



	public void neg(final Tuple4D tup) {
		this.a = 0.0 - tup.a;
		this.b = 0.0 - tup.b;
		this.c = 0.0 - tup.c;
		this.d = 0.0 - tup.d;
	}
	public void neg() {
		this.a = 0.0 - this.a;
		this.b = 0.0 - this.b;
		this.c = 0.0 - this.c;
		this.d = 0.0 - this.d;
	}



	public void scale(final double scale) {
		this.a *= scale;
		this.b *= scale;
		this.c *= scale;
		this.d *= scale;
	}



	public void clamp(final double min, final double max) {
		this.a = MinMax(this.a, min, max);
		this.b = MinMax(this.b, min, max);
		this.c = MinMax(this.c, min, max);
		this.d = MinMax(this.d, min, max);
	}
	public void clampMin(final double min) {
		if (this.a < min) this.a = min;
		if (this.b < min) this.b = min;
		if (this.c < min) this.c = min;
		if (this.d < min) this.d = min;
	}
	public void clampMax(final double max) {
		if (this.a > max) this.a = max;
		if (this.b > max) this.b = max;
		if (this.c > max) this.c = max;
		if (this.d > max) this.d = max;
	}



	public void normalize(final Tuple4D tup) {
		this.set(tup);
		this.normalize();
	}
	public void normalize() {
		final double norm = 1.0 / this.vectorLength();
		this.a *= norm;
		this.b *= norm;
		this.c *= norm;
		this.d *= norm;
	}



	public double vectorLength() {
		return Math.sqrt( (this.a*this.a) + (this.b*this.b) + (this.c*this.c) + (this.d*this.d) );
	}



	public void interpolate(final Tuple4D tup, final double alpha) {
		this.a = ((1.0-alpha)*this.a) + (alpha*tup.a);
		this.b = ((1.0-alpha)*this.b) + (alpha*tup.b);
		this.c = ((1.0-alpha)*this.c) + (alpha*tup.c);
		this.d = ((1.0-alpha)*this.d) + (alpha*tup.d);
	}
	public void interpolate(final Tuple4D tupA, final Tuple4D tupB, final double alpha) {
		this.a = ((1.0-alpha)*tupA.a) + (alpha*tupB.a);
		this.b = ((1.0-alpha)*tupA.b) + (alpha*tupB.b);
		this.c = ((1.0-alpha)*tupA.c) + (alpha*tupB.c);
		this.d = ((1.0-alpha)*tupA.d) + (alpha*tupB.d);
	}



	public boolean epsilon(final Tuple4D tup, final double epsilon) {
		double dif = this.a - tup.a;
		if (Double.isNaN(dif))       return false;
		if (Math.abs(dif) > epsilon) return false;
		dif = this.b - tup.b;
		if (Double.isNaN(dif))       return false;
		if (Math.abs(dif) > epsilon) return false;
		dif = this.c - tup.c;
		if (Double.isNaN(dif))       return false;
		if (Math.abs(dif) > epsilon) return false;
		dif = this.d - tup.d;
		if (Double.isNaN(dif))       return false;
		if (Math.abs(dif) > epsilon) return false;
		return true;
	}



}
