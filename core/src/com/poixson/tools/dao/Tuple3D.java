package com.poixson.tools.dao;

import com.poixson.utils.NumberUtils;


public class Tuple3D extends Dxyz {
	private static final long serialVersionUID = 1L;



	public Tuple3D() {
		super();
	}
	public Tuple3D(final double x, final double y, final double z) {
		super(x, y, z);
	}
	public Tuple3D(final Tuple3D tup) {
		super(tup);
	}



	@Override
	public Object clone() {
		return new Tuple3D(this.x, this.y, this.z);
	}



	public void get(final Tuple3D tup) {
		tup.x = this.x;
		tup.y = this.y;
		tup.z = this.z;
	}



	public void set(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public void set(final Tuple3D tup) {
		this.x = tup.x;
		this.y = tup.y;
		this.z = tup.z;
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



	public void add(final double x, final double y, final double z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}
	public void add(final Tuple3D tup) {
		this.x += tup.x;
		this.y += tup.y;
		this.z += tup.z;
	}
	public void add(final Tuple3D tupA, final Tuple3D tupB) {
		this.x = tupA.x + tupB.x;
		this.y = tupA.y + tupB.y;
		this.z = tupA.z + tupB.z;
	}



	public void sub(final double x, final double y, final double z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
	}
	public void sub(final Tuple3D tup) {
		this.x -= tup.x;
		this.y -= tup.y;
		this.z -= tup.z;
	}
	public void sub(final Tuple3D tupA, final Tuple3D tupB) {
		this.x = tupA.x - tupB.x;
		this.y = tupA.y - tupB.y;
		this.z = tupA.z - tupB.z;
	}



	public void abs() {
		this.x = Math.abs(this.x);
		this.y = Math.abs(this.y);
		this.z = Math.abs(this.z);
	}



	public void neg(final Tuple3D tup) {
		this.x = 0.0 - tup.x;
		this.y = 0.0 - tup.y;
		this.z = 0.0 - tup.z;
	}
	public void neg() {
		this.x = 0.0 - this.x;
		this.y = 0.0 - this.y;
		this.z = 0.0 - this.z;
	}



	public void scale(final double scale) {
		this.x *= scale;
		this.y *= scale;
		this.z *= scale;
	}



	public void clamp(final double min, final double max) {
		this.x = NumberUtils.MinMax(this.x, min, max);
		this.y = NumberUtils.MinMax(this.y, min, max);
		this.z = NumberUtils.MinMax(this.z, min, max);
	}
	public void clampMin(final double min) {
		if (this.x < min) this.x = min;
		if (this.y < min) this.y = min;
		if (this.z < min) this.z = min;
	}
	public void clampMax(final double max) {
		if (this.x > max) this.x = max;
		if (this.y > max) this.y = max;
		if (this.z > max) this.z = max;
	}



	public void normalize(final Tuple3D tup) {
		this.set(tup);
		this.normalize();
	}
	public void normalize() {
		final double norm = 1.0 / this.vectorLength();
		this.x *= norm;
		this.y *= norm;
		this.z *= norm;
	}



	public double vectorLength() {
		return Math.sqrt( (this.x*this.x) + (this.y*this.y) + (this.z*this.z) );
	}



	public void interpolate(final Tuple3D tup, final double alpha) {
		this.x = ((1.0-alpha)*this.x) + (alpha*tup.x);
		this.y = ((1.0-alpha)*this.y) + (alpha*tup.y);
		this.z = ((1.0-alpha)*this.z) + (alpha*tup.z);
	}
	public void interpolate(final Tuple3D tupA, final Tuple3D tupB, final double alpha) {
		this.x = ((1.0-alpha)*tupA.x) + (alpha*tupB.x);
		this.y = ((1.0-alpha)*tupA.y) + (alpha*tupB.y);
		this.z = ((1.0-alpha)*tupA.z) + (alpha*tupB.z);
	}



	public boolean epsilon(final Tuple3D tup, final double epsilon) {
		double dif = this.x - tup.x;
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
