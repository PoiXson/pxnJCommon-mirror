package com.poixson.tools.dao;

import java.io.Serializable;


public class Lxy implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public long x;
	public long y;



	public Lxy() {
		this.x = 0L;
		this.y = 0L;
	}
	public Lxy(final long x, final long y) {
		this.x = x;
		this.y = y;
	}
	public Lxy(final Lxy dao) {
		this.x = dao.x;
		this.y = dao.y;
	}



	@Override
	public Object clone() {
		return new Lxy(this.x, this.y);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Lxy) {
			final Lxy dao = (Lxy) obj;
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
		final long bits = ((31L + this.x) * 31L) + this.y;
		return (int) (bits ^ (bits >> 32L));
	}



}
