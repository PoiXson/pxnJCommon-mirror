package com.poixson.tools.dao;

import java.io.Serializable;


public class Ixy implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public int x;
	public int y;



	public Ixy() {
		this.x = 0;
		this.y = 0;
	}
	public Ixy(final int x, final int y) {
		this.x = x;
		this.y = y;
	}
	public Ixy(final Ixy dao) {
		this.x = dao.x;
		this.y = dao.y;
	}



	@Override
	public Object clone() {
		return new Ixy(this.x, this.y);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Ixy) {
			final Ixy dao = (Ixy) obj;
			return (this.x == dao.x && this.y == dao.y);
		}
		return false;
	}



	@Override
	public String toString() {
		return (new StringBuilder())
				.append('(')
				.append(this.x)
				.append(", ")
				.append(this.y)
				.append(')')
				.toString();
	}
	@Override
	public int hashCode() {
		long bits =    31L  + ((long)this.x);
		bits = (bits * 31L) + ((long)this.y);
		return (int) (bits ^ (bits >> 32L));
	}



}
