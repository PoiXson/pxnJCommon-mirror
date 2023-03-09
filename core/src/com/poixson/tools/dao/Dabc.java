package com.poixson.tools.dao;

import java.io.Serializable;


public class Dabc implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public double a;
	public double b;
	public double c;



	public Dabc() {
		this.a = 0;
		this.b = 0;
		this.c = 0;
	}
	public Dabc(final double x, final double y, final double z) {
		this.a = x;
		this.b = y;
		this.c = z;
	}
	public Dabc(final Dabc dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
	}



	@Override
	public Object clone() {
		return new Dabc(this.a, this.b, this.c);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Dabc) {
			final Dabc dao = (Dabc) obj;
			return (
				this.a == dao.a &&
				this.b == dao.b &&
				this.c == dao.c
			);
		}
		return false;
	}



	@Override
	public String toString() {
		return (new StringBuilder())
			.append(this.a).append(", ")
			.append(this.b).append(", ")
			.append(this.c)
			.toString();
	}
	@Override
	public int hashCode() {
		long bits =    31L  + Double.doubleToLongBits(this.a == 0.0 ? 0.0 : this.a);
		bits = (bits * 31L) + Double.doubleToLongBits(this.b == 0.0 ? 0.0 : this.b);
		bits = (bits * 31L) + Double.doubleToLongBits(this.c == 0.0 ? 0.0 : this.c);
		return (int) (bits ^ (bits >> 32L));
	}



}
