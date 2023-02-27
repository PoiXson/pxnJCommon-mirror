package com.poixson.tools.dao;

import java.io.Serializable;


public class Iwxyz implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public int w;
	public int x;
	public int y;
	public int z;



	public Iwxyz() {
		this.w = 0;
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	public Iwxyz(final int w, final int x, final int y, final int z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Iwxyz(final Iwxyz dao) {
		this.w = dao.w;
		this.x = dao.x;
		this.y = dao.y;
		this.z = dao.z;
	}



	@Override
	public Object clone() {
		return new Iwxyz(this.w, this.x, this.y, this.z);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Iwxyz) {
			final Iwxyz dao = (Iwxyz) obj;
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
		long bits =    31L  + ((long)this.w);
		bits = (bits * 31L) + ((long)this.x);
		bits = (bits * 31L) + ((long)this.y);
		bits = (bits * 31L) + ((long)this.z);
		return (int) (bits ^ (bits >> 32L));
	}



}
