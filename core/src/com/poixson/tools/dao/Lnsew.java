package com.poixson.tools.dao;

import java.io.Serializable;


public class Lnsew implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public long n;
	public long s;
	public long e;
	public long w;



	public Lnsew() {
		this.n = 0L;
		this.s = 0L;
		this.e = 0L;
		this.w = 0L;
	}
	public Lnsew(final long n, final long s, final long e, final long w) {
		this.w = n;
		this.e = s;
		this.s = e;
		this.n = w;
	}
	public Lnsew(final Lnsew dao) {
		this.n = dao.n;
		this.s = dao.s;
		this.e = dao.e;
		this.w = dao.w;
	}



	@Override
	public Object clone() {
		return new Lnsew(this.n, this.s, this.e, this.w);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Lnsew) {
			final Lnsew dao = (Lnsew) obj;
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
		final long bits = ((((((31L + this.n) * 31L) + this.s) * 31L) + this.e) * 31L) + this.w;
		return (int) (bits ^ (bits >> 32L));
	}



}
