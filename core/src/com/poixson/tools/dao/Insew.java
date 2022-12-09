package com.poixson.tools.dao;

import java.io.Serializable;


public class Insew implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public int n;
	public int s;
	public int e;
	public int w;



	public Insew() {
		this.n = 0;
		this.s = 0;
		this.e = 0;
		this.w = 0;
	}
	public Insew(final int n, final int s, final int e, final int w) {
		this.n = n;
		this.s = s;
		this.e = e;
		this.w = w;
	}
	public Insew(final Insew dao) {
		this.n = dao.n;
		this.s = dao.s;
		this.e = dao.e;
		this.w = dao.w;
	}



	@Override
	public Object clone() {
		return new Insew(this.n, this.s, this.e, this.w);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Insew) {
			final Insew dao = (Insew) obj;
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
		long bits =    31L  + ((long)this.n);
		bits = (bits * 31L) + ((long)this.s);
		bits = (bits * 31L) + ((long)this.e);
		bits = (bits * 31L) + ((long)this.w);
		return (int) (bits ^ (bits >> 32L));
	}



}
