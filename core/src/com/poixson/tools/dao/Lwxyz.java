package com.poixson.tools.dao;

import java.io.Serializable;


public class Lwxyz implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public long w;
	public long x;
	public long y;
	public long z;



	public Lwxyz() {
		this.w = 0L;
		this.x = 0L;
		this.y = 0L;
		this.z = 0L;
	}
	public Lwxyz(final long w, final long x, final long y, final long z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Lwxyz(final Lwxyz dao) {
		this.w = dao.w;
		this.x = dao.x;
		this.y = dao.y;
		this.z = dao.z;
	}



	@Override
	public Object clone() {
		return new Lwxyz(this.w, this.x, this.y, this.z);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Lwxyz) {
			final Lwxyz dao = (Lwxyz) obj;
			return (
				this.w == dao.w &&
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
				.append('(') .append(this.w)
				.append(", ").append(this.x)
				.append(", ").append(this.y)
				.append(", ").append(this.z)
				.append(')')
				.toString();
	}
	@Override
	public int hashCode() {
		final long bits = ((((((31L + this.w) * 31L) + this.x) * 31L) + this.y) * 31L) + this.z;
		return (int) (bits ^ (bits >> 32L));
	}



}
