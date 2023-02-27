package com.poixson.tools.dao;

import java.io.Serializable;


public class Ixyz implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public int x;
	public int y;
	public int z;



	public Ixyz() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	public Ixyz(final int x, final int y, final int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Ixyz(final Ixyz dao) {
		this.x = dao.x;
		this.y = dao.y;
		this.z = dao.z;
	}



	@Override
	public Object clone() {
		return new Ixyz(this.x, this.y, this.z);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Ixyz) {
			final Ixyz dao = (Ixyz) obj;
			return (
				this.x == dao.x &&
				this.y == dao.y &&
				this.z == dao.z
			);
		}
		return false;
	}



	@Override
	public String toString() {
		return (new StringBuilder())
				.append('(') .append(this.x)
				.append(", ").append(this.y)
				.append(", ").append(this.z)
				.append(')')
				.toString();
	}
	@Override
	public int hashCode() {
		long bits =    31L  + ((long)this.x);
		bits = (bits * 31L) + ((long)this.y);
		bits = (bits * 31L) + ((long)this.z);
		return (int) (bits ^ (bits >> 32L));
	}



}
