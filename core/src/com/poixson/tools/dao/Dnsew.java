package com.poixson.tools.dao;

import java.io.Serializable;


public class Dnsew implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public double n;
	public double s;
	public double e;
	public double w;



	public Dnsew() {
		this.n = 0.0;
		this.s = 0.0;
		this.e = 0.0;
		this.w = 0.0;
	}
	public Dnsew(final double w, final double x, final double y, final double z) {
		this.n = w;
		this.s = x;
		this.e = y;
		this.w = z;
	}
	public Dnsew(final Dnsew dao) {
		this.n = dao.n;
		this.s = dao.s;
		this.e = dao.e;
		this.w = dao.w;
	}



	@Override
	public Object clone() {
		return new Dnsew(this.n, this.s, this.e, this.w);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Dnsew) {
			final Dnsew dao = (Dnsew) obj;
			return (
				this.n == dao.n &&
				this.s == dao.s &&
				this.e == dao.e &&
				this.w == dao.w
			);
		}
		return false;
	}



	@Override
	public String toString() {
		return (new StringBuilder())
				.append('(') .append(this.n)
				.append(", ").append(this.s)
				.append(", ").append(this.e)
				.append(", ").append(this.w)
				.append(')')
				.toString();
	}
	@Override
	public int hashCode() {
		long bits =    31L  + Double.doubleToLongBits(this.n == 0.0 ? 0.0 : this.n);
		bits = (bits * 31L) + Double.doubleToLongBits(this.s == 0.0 ? 0.0 : this.s);
		bits = (bits * 31L) + Double.doubleToLongBits(this.e == 0.0 ? 0.0 : this.e);
		bits = (bits * 31L) + Double.doubleToLongBits(this.w == 0.0 ? 0.0 : this.w);
		return (int) (bits ^ (bits >> 32L));
	}



}
