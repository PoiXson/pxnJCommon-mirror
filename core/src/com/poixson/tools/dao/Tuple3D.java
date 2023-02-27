package com.poixson.tools.dao;

import com.poixson.utils.NumberUtils;


public class Tuple3D extends Dabc {
	private static final long serialVersionUID = 1L;



	public Tuple3D() {
		super();
	}
	public Tuple3D(final double a, final double b, final double c) {
		super(a, b, c);
	}
	public Tuple3D(final Tuple3D tup) {
		super(tup);
	}



	@Override
	public Object clone() {
		return new Tuple3D(this.a, this.b, this.c);
	}



	public void get(final Tuple3D tup) {
		tup.a = this.a;
		tup.b = this.b;
		tup.c = this.c;
	}



	public void set(final double a, final double b, final double c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	public void set(final Tuple3D tup) {
		this.a = tup.a;
		this.b = tup.b;
		this.c = tup.c;
	}
	public void setX(final double a) {
		this.a = a;
	}
	public void setY(final double b) {
		this.b = b;
	}
	public void setZ(final double c) {
		this.c = c;
	}



	public void add(final double a, final double b, final double c) {
		this.a += a;
		this.b += b;
		this.c += c;
	}
	public void add(final Tuple3D tup) {
		this.a += tup.a;
		this.b += tup.b;
		this.c += tup.c;
	}
	public void add(final Tuple3D tupA, final Tuple3D tupB) {
		this.a = tupA.a + tupB.a;
		this.b = tupA.b + tupB.b;
		this.c = tupA.c + tupB.c;
	}



	public void sub(final double a, final double b, final double c) {
		this.a -= a;
		this.b -= b;
		this.c -= c;
	}
	public void sub(final Tuple3D tup) {
		this.a -= tup.a;
		this.b -= tup.b;
		this.c -= tup.c;
	}
	public void sub(final Tuple3D tupA, final Tuple3D tupB) {
		this.a = tupA.a - tupB.a;
		this.b = tupA.b - tupB.b;
		this.c = tupA.c - tupB.c;
	}



	public void abs() {
		this.a = Math.abs(this.a);
		this.b = Math.abs(this.b);
		this.c = Math.abs(this.c);
	}



	public void neg(final Tuple3D tup) {
		this.a = 0.0 - tup.a;
		this.b = 0.0 - tup.b;
		this.c = 0.0 - tup.c;
	}
	public void neg() {
		this.a = 0.0 - this.a;
		this.b = 0.0 - this.b;
		this.c = 0.0 - this.c;
	}



	public void scale(final double scale) {
		this.a *= scale;
		this.b *= scale;
		this.c *= scale;
	}



	public void clamp(final double min, final double max) {
		this.a = NumberUtils.MinMax(this.a, min, max);
		this.b = NumberUtils.MinMax(this.b, min, max);
		this.c = NumberUtils.MinMax(this.c, min, max);
	}
	public void clampMin(final double min) {
		if (this.a < min) this.a = min;
		if (this.b < min) this.b = min;
		if (this.c < min) this.c = min;
	}
	public void clampMax(final double max) {
		if (this.a > max) this.a = max;
		if (this.b > max) this.b = max;
		if (this.c > max) this.c = max;
	}



	public void normalize(final Tuple3D tup) {
		this.set(tup);
		this.normalize();
	}
	public void normalize() {
		final double norm = 1.0 / this.vectorLength();
		this.a *= norm;
		this.b *= norm;
		this.c *= norm;
	}



	public double vectorLength() {
		return Math.sqrt( (this.a*this.a) + (this.b*this.b) + (this.c*this.c) );
	}



	public void interpolate(final Tuple3D tup, final double alpha) {
		this.a = ((1.0-alpha)*this.a) + (alpha*tup.a);
		this.b = ((1.0-alpha)*this.b) + (alpha*tup.b);
		this.c = ((1.0-alpha)*this.c) + (alpha*tup.c);
	}
	public void interpolate(final Tuple3D tupA, final Tuple3D tupB, final double alpha) {
		this.a = ((1.0-alpha)*tupA.a) + (alpha*tupB.a);
		this.b = ((1.0-alpha)*tupA.b) + (alpha*tupB.b);
		this.c = ((1.0-alpha)*tupA.c) + (alpha*tupB.c);
	}



	public boolean epsilon(final Tuple3D tup, final double epsilon) {
		double dif = this.a - tup.a;
		if (Double.isNaN(dif))       return false;
		if (Math.abs(dif) > epsilon) return false;
		dif = this.b - tup.b;
		if (Double.isNaN(dif))       return false;
		if (Math.abs(dif) > epsilon) return false;
		dif = this.c - tup.c;
		if (Double.isNaN(dif))       return false;
		if (Math.abs(dif) > epsilon) return false;
		return true;
	}



}
