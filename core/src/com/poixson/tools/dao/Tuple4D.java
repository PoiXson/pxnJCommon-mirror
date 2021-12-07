package com.poixson.tools.dao;

import com.poixson.utils.NumberUtils;


public class Tuple4D extends Dwxyz {
	private static final long serialVersionUID = 1L;



	public Tuple4D() {
		super();
	}
	public Tuple4D(final double w, final double x, final double y, final double z) {
		super(w, x, y, z);
	}
	public Tuple4D(final Tuple4D tup) {
		super(tup);
	}



	@Override
	public Object clone() {
		return new Tuple4D(this.w, this.x, this.y, this.z);
	}



	public void get(final Tuple4D tup) {
		tup.w = this.w;
		tup.x = this.x;
		tup.y = this.y;
		tup.z = this.z;
	}



	public void set(final double w, final double x, final double y, final double z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public void set(final Tuple4D tup) {
		this.w = tup.w;
		this.x = tup.x;
		this.y = tup.y;
		this.z = tup.z;
	}
	public void setW(final double w) {
		this.w = w;
	}
	public void setX(final double x) {
		this.x = x;
	}
	public void setY(final double y) {
		this.y = y;
	}
	public void setZ(final double z) {
		this.z = z;
	}



	public void add(final double w, final double x, final double y, final double z) {
		this.w += w;
		this.x += x;
		this.y += y;
		this.z += z;
	}
	public void add(final Tuple4D tup) {
		this.w += tup.w;
		this.x += tup.x;
		this.y += tup.y;
		this.z += tup.z;
	}
	public void add(final Tuple4D tupA, final Tuple4D tupB) {
		this.w = tupA.w + tupB.w;
		this.x = tupA.x + tupB.x;
		this.y = tupA.y + tupB.y;
		this.z = tupA.z + tupB.z;
	}



	public void sub(final double w, final double x, final double y, final double z) {
		this.w -= w;
		this.x -= x;
		this.y -= y;
		this.z -= z;
	}
	public void sub(final Tuple4D tup) {
		this.w -= tup.w;
		this.x -= tup.x;
		this.y -= tup.y;
		this.z -= tup.z;
	}
	public void sub(final Tuple4D tupA, final Tuple4D tupB) {
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



	public void neg(final Tuple4D tup) {
		this.w = 0.0 - tup.w;
		this.x = 0.0 - tup.x;
		this.y = 0.0 - tup.y;
		this.z = 0.0 - tup.z;
	}
	public void neg() {
		this.w = 0.0 - this.w;
		this.x = 0.0 - this.x;
		this.y = 0.0 - this.y;
		this.z = 0.0 - this.z;
	}



	public void scale(final double scale) {
		this.w *= scale;
		this.x *= scale;
		this.y *= scale;
		this.z *= scale;
	}



	public void clamp(final double min, final double max) {
		this.w = NumberUtils.MinMax(this.w, min, max);
		this.x = NumberUtils.MinMax(this.x, min, max);
		this.y = NumberUtils.MinMax(this.y, min, max);
		this.z = NumberUtils.MinMax(this.z, min, max);
	}
	public void clampMin(final double min) {
		if (this.w < min) this.w = min;
		if (this.x < min) this.x = min;
		if (this.y < min) this.y = min;
		if (this.z < min) this.z = min;
	}
	public void clampMax(final double max) {
		if (this.w > max) this.w = max;
		if (this.x > max) this.x = max;
		if (this.y > max) this.y = max;
		if (this.z > max) this.z = max;
	}



	public void normalize(final Tuple4D tup) {
		this.set(tup);
		this.normalize();
	}
	public void normalize() {
		final double norm = 1.0 / this.vectorLength();
		this.w *= norm;
		this.x *= norm;
		this.y *= norm;
		this.z *= norm;
	}



	public double vectorLength() {
		return Math.sqrt( (this.w*this.w) + (this.x*this.x) + (this.y*this.y) + (this.z*this.z) );
	}



	public void interpolate(final Tuple4D tup, final double alpha) {
		this.w = ((1.0-alpha)*this.w) + (alpha*tup.w);
		this.x = ((1.0-alpha)*this.x) + (alpha*tup.x);
		this.y = ((1.0-alpha)*this.y) + (alpha*tup.y);
		this.z = ((1.0-alpha)*this.z) + (alpha*tup.z);
	}
	public void interpolate(final Tuple4D tupA, final Tuple4D tupB, final double alpha) {
		this.w = ((1.0-alpha)*tupA.w) + (alpha*tupB.w);
		this.x = ((1.0-alpha)*tupA.x) + (alpha*tupB.x);
		this.y = ((1.0-alpha)*tupA.y) + (alpha*tupB.y);
		this.z = ((1.0-alpha)*tupA.z) + (alpha*tupB.z);
	}



	public boolean epsilon(final Tuple4D tup, final double epsilon) {
		double dif = this.w - tup.w;
		if (Double.isNaN(dif)) return false;
		if (Math.abs(dif) > epsilon) return false;
		dif = this.x - tup.x;
		if (Double.isNaN(dif)) return false;
		if (Math.abs(dif) > epsilon) return false;
		dif = this.y - tup.y;
		if (Double.isNaN(dif)) return false;
		if (Math.abs(dif) > epsilon) return false;
		dif = this.z - tup.z;
		if (Double.isNaN(dif)) return false;
		if (Math.abs(dif) > epsilon) return false;
		return true;
	}



}
