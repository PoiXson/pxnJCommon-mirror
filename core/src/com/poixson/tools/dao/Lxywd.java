package com.poixson.tools.dao;

import java.io.Serializable;


public class Lxywd implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public long x;
	public long y;
	public long w;
	public long d;



	public Lxywd() {
		this.x = 0L;
		this.y = 0L;
		this.w = 0L;
		this.d = 0L;
	}
	public Lxywd(final long x, final long y, final long w, final long d) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.d = d;
	}
	public Lxywd(final Lxywd dao) {
		this.x = dao.x;
		this.y = dao.y;
		this.w = dao.w;
		this.d = dao.d;
	}



	@Override
	public Object clone() {
		return new Lxywd(this.x, this.y, this.w, this.d);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Lxywd) {
			final Lxywd dao = (Lxywd) obj;
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
		final long bits = ((((((31L + this.x) * 31L) + this.y) * 31L) + this.w) * 31L) + this.d;
		return (int) (bits ^ (bits >> 32L));
	}



}
