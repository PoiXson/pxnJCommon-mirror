package com.poixson.tools.dao;

import com.poixson.utils.NumberUtils;


public class Tuple2D extends Dxy {
	private static final long serialVersionUID = 1L;



	public Tuple2D() {
		super();
	}
	public Tuple2D(final double x, final double y) {
		super(x, y);
	}
	public Tuple2D(final Tuple2D tup) {
		super(tup);
	}



	@Override
	public Object clone() {
		return new Tuple2D(this.x, this.y);
	}



	public void get(final Tuple2D tup) {
		tup.x = this.x;
		tup.y = this.y;
	}



	public void set(final double x, final double y) {
		this.x = x;
		this.y = y;
	}
	public void set(final Tuple2D tup) {
		this.x = tup.x;
		this.y = tup.y;
	}
	public void setX(final double x) {
		this.x = x;
	}
	public void setY(final double y) {
		this.y = y;
	}



	public void add(final double x, final double y) {
		this.x += x;
		this.y += y;
	}
	public void add(final Tuple2D tup) {
		this.x += tup.x;
		this.y += tup.y;
	}
	public void add(final Tuple2D tupA, final Tuple2D tupB) {
		this.x = tupA.x + tupB.x;
		this.y = tupA.y + tupB.y;
	}



	public void sub(final double x, final double y) {
		this.x -= x;
		this.y -= y;
	}
	public void sub(final Tuple2D tup) {
		this.x -= tup.x;
		this.y -= tup.y;
	}
	public void sub(final Tuple2D tupA, final Tuple2D tupB) {
		this.x = tupA.x - tupB.x;
		this.y = tupA.y - tupB.y;
	}



	public void abs() {
		this.x = Math.abs(this.x);
		this.y = Math.abs(this.y);
	}



	public void neg(final Tuple2D tup) {
		this.x = 0.0 - tup.x;
		this.y = 0.0 - tup.y;
	}
	public void neg() {
		this.x = 0.0 - this.x;
		this.y = 0.0 - this.y;
	}



	public void scale(final double scale) {
		this.x *= scale;
		this.y *= scale;
	}



	public void clamp(final double min, final double max) {
		this.x = NumberUtils.MinMax(this.x, min, max);
		this.y = NumberUtils.MinMax(this.y, min, max);
	}
	public void clampMin(final double min) {
		if (this.x < min) this.x = min;
		if (this.y < min) this.y = min;
	}
	public void clampMax(final double max) {
		if (this.x > max) this.x = max;
		if (this.y > max) this.y = max;
	}



	public void normalize(final Tuple2D tup) {
		this.set(tup);
		this.normalize();
	}
	public void normalize() {
		final double norm = 1.0 / this.vectorLength();
		this.x *= norm;
		this.y *= norm;
	}



	public double vectorLength() {
		return Math.sqrt( (this.x*this.x) + (this.y*this.y) );
	}



	public void interpolate(final Tuple2D tup, final double alpha) {
		this.x = ((1.0-alpha)*this.x) + (alpha*tup.x);
		this.y = ((1.0-alpha)*this.y) + (alpha*tup.y);
	}
	public void interpolate(final Tuple2D tupA, final Tuple2D tupB, final double alpha) {
		this.x = ((1.0-alpha)*tupA.x) + (alpha*tupB.x);
		this.y = ((1.0-alpha)*tupA.y) + (alpha*tupB.y);
	}



	public boolean epsilon(final Tuple2D tup, final double epsilon) {
		double dif = this.x - tup.x;
		if (Double.isNaN(dif)) return false;
		if (Math.abs(dif) > epsilon) return false;
		dif = this.y - tup.y;
		if (Double.isNaN(dif)) return false;
		if (Math.abs(dif) > epsilon) return false;
		return true;
	}



}
