package com.poixson.tools.dao;

import java.io.Serializable;


public class Dxywd implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public double x;
	public double y;
	public double w;
	public double d;



	public Dxywd() {
		this.x = 0.0;
		this.y = 0.0;
		this.w = 0.0;
		this.d = 0.0;
	}
	public Dxywd(final double x, final double y, final double w, final double d) {
		this.x = w;
		this.y = x;
		this.w = w;
		this.d = d;
	}
	public Dxywd(final Dxywd dao) {
		this.x = dao.x;
		this.y = dao.y;
		this.w = dao.w;
		this.d = dao.d;
	}



	@Override
	public Object clone() {
		return new Dxywd(this.x, this.y, this.w, this.d);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Dxywd) {
			final Dxywd dao = (Dxywd) obj;
			return (
				this.x == dao.x &&
				this.y == dao.y &&
				this.w == dao.w &&
				this.d == dao.d
			);
		}
		return false;
	}



	@Override
	public String toString() {
		return (new StringBuilder())
				.append('(') .append(this.x)
				.append(", ").append(this.y)
				.append(", ").append(this.w)
				.append(", ").append(this.d)
				.append(')')
				.toString();
	}
	@Override
	public int hashCode() {
		long bits =    31L  + Double.doubleToLongBits(this.x == 0.0 ? 0.0 : this.x);
		bits = (bits * 31L) + Double.doubleToLongBits(this.y == 0.0 ? 0.0 : this.y);
		bits = (bits * 31L) + Double.doubleToLongBits(this.w == 0.0 ? 0.0 : this.w);
		bits = (bits * 31L) + Double.doubleToLongBits(this.d == 0.0 ? 0.0 : this.d);
		return (int) (bits ^ (bits >> 32L));
	}



}
