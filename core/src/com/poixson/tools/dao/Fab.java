package com.poixson.tools.dao;

import java.io.Serializable;


public class Fab implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public float a;
	public float b;



	public Fab() {
		this.a = 0.0F;
		this.b = 0.0F;
	}
	public Fab(final float a, final float b) {
		this.a = a;
		this.b = b;
	}
	public Fab(final Fab dao) {
		this.a = dao.a;
		this.b = dao.b;
	}



	@Override
	public Object clone() {
		return new Fab(this.a, this.b);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Fab) {
			final Fab dao = (Fab) obj;
			return (
				this.a == dao.a &&
				this.b == dao.b
			);
		}
		return false;
	}



	@Override
	public String toString() {
		return (new StringBuilder())
			.append(this.a).append(", ")
			.append(this.b)
			.toString();
	}
	@Override
	public int hashCode() {
		long bits =    31L  + Float.floatToIntBits(this.a);
		bits = (bits * 31L) + Float.floatToIntBits(this.b);
		return (int) (bits ^ (bits >> 32L));
	}



}
