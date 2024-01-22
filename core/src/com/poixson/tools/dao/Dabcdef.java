package com.poixson.tools.dao;

import java.io.Serializable;


public class Dabcdef implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public double a;
	public double b;
	public double c;
	public double d;
	public double e;
	public double f;



	public Dabcdef() {
		this.a = 0.0;
		this.b = 0.0;
		this.c = 0.0;
		this.d = 0.0;
		this.e = 0.0;
		this.f = 0.0;
	}
	public Dabcdef(final double a, final double b, final double c, final double d, final double e, final double f) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
	}
	public Dabcdef(final Dabcdef dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
		this.d = dao.d;
		this.e = dao.e;
		this.f = dao.f;
	}



	@Override
	public Object clone() {
		return new Dabcdef(this.a, this.b, this.c, this.d, this.e, this.f);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Dabcdef) {
			final Dabcdef dao = (Dabcdef) obj;
			return (
				this.a == dao.a &&
				this.b == dao.b &&
				this.c == dao.c &&
				this.d == dao.d &&
				this.e == dao.e &&
				this.f == dao.f
			);
		}
		return false;
	}



	@Override
	public String toString() {
		return (new StringBuilder())
			.append(this.a).append(", ")
			.append(this.b).append(", ")
			.append(this.c).append(", ")
			.append(this.d).append(", ")
			.append(this.e).append(", ")
			.append(this.f)
			.toString();
	}
	@Override
	public int hashCode() {
		long bits =    31L  + Double.doubleToLongBits(this.a == 0.0 ? 0.0 : this.a);
		bits = (bits * 31L) + Double.doubleToLongBits(this.b == 0.0 ? 0.0 : this.b);
		bits = (bits * 31L) + Double.doubleToLongBits(this.c == 0.0 ? 0.0 : this.c);
		bits = (bits * 31L) + Double.doubleToLongBits(this.d == 0.0 ? 0.0 : this.d);
		bits = (bits * 31L) + Double.doubleToLongBits(this.e == 0.0 ? 0.0 : this.e);
		bits = (bits * 31L) + Double.doubleToLongBits(this.f == 0.0 ? 0.0 : this.f);
		return (int) (bits ^ (bits >> 32L));
	}



}
