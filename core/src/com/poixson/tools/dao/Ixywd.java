package com.poixson.tools.dao;

import java.io.Serializable;


public class Ixywd implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public int x;
	public int y;
	public int w;
	public int d;



	public Ixywd() {
		this.x = 0;
		this.y = 0;
		this.w = 0;
		this.d = 0;
	}
	public Ixywd(final int x, final int y, final int w, final int d) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.d = d;
	}
	public Ixywd(final Ixywd dao) {
		this.x = dao.x;
		this.y = dao.y;
		this.w = dao.w;
		this.d = dao.d;
	}



	@Override
	public Object clone() {
		return new Ixywd(this.x, this.y, this.w, this.d);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Ixywd) {
			final Ixywd dao = (Ixywd) obj;
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
				.append("; ") .append(this.w)
				.append(", ").append(this.d)
				.append(')')
				.toString();
	}
	@Override
	public int hashCode() {
		long bits =    31L  + ((long)this.x);
		bits = (bits * 31L) + ((long)this.y);
		bits = (bits * 31L) + ((long)this.w);
		bits = (bits * 31L) + ((long)this.d);
		return (int) (bits ^ (bits >> 32L));
	}



}
