package com.poixson.tools.dao;

import java.io.Serializable;


public class Fxy implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public float x;
	public float y;



	public Fxy() {
		this.x = 0.0F;
		this.y = 0.0F;
	}
	public Fxy(final float x, final float y) {
		this.x = x;
		this.y = y;
	}
	public Fxy(final Fxy dao) {
		this.x = dao.x;
		this.y = dao.y;
	}



	@Override
	public Object clone() {
		return new Fxy(this.x, this.y);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Fxy) {
			final Fxy dao = (Fxy) obj;
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
		long bits =    31L  + Float.floatToIntBits(this.x);
		bits = (bits * 31L) + Float.floatToIntBits(this.y);
		return (int) (bits ^ (bits >> 32L));
	}



}
