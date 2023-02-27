package com.poixson.tools.dao;

import java.io.Serializable;


public class Dxy implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public double x;
	public double y;



	public Dxy() {
		this.x = 0.0;
		this.y = 0.0;
	}
	public Dxy(final double x, final double y) {
		this.x = x;
		this.y = y;
	}
	public Dxy(final Dxy dao) {
		this.x = dao.x;
		this.y = dao.y;
	}



	@Override
	public Object clone() {
		return new Dxy(this.x, this.y);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Dxy) {
			final Dxy dao = (Dxy) obj;
			return (
				this.x == dao.x &&
				this.y == dao.y
			);
		}
		return false;
	}



	@Override
	public String toString() {
		return (new StringBuilder())
				.append('(') .append(this.x)
				.append(", ").append(this.y)
				.append(')')
				.toString();
	}
	@Override
	public int hashCode() {
		long bits =    31L  + Double.doubleToLongBits(this.x == 0.0 ? 0.0 : this.x);
		bits = (bits * 31L) + Double.doubleToLongBits(this.y == 0.0 ? 0.0 : this.y);
		return (int) (bits ^ (bits >> 32L));
	}



}
