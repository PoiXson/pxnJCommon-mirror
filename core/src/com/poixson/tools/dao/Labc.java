package com.poixson.tools.dao;

import java.io.Serializable;


public class Lxyz implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public long x;
	public long y;
	public long z;



	public Lxyz() {
		this.x = 0L;
		this.y = 0L;
		this.z = 0L;
	}
	public Lxyz(final long x, final long y, final long z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Lxyz(final Lxyz dao) {
		this.x = dao.x;
		this.y = dao.y;
		this.z = dao.z;
	}



	@Override
	public Object clone() {
		return new Lxyz(this.x, this.y, this.z);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Lxyz) {
			final Lxyz dao = (Lxyz) obj;
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
		final long bits = ((((31L + this.x) * 31L) + this.y) * 31L) + this.z;
		return (int) (bits ^ (bits >> 32L));
	}



}
