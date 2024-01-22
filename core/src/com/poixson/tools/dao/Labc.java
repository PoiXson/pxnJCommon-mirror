package com.poixson.tools.dao;

import java.io.Serializable;


public class Labc implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public long a;
	public long b;
	public long c;



	public Labc() {
		this.a = 0L;
		this.b = 0L;
		this.c = 0L;
	}
	public Labc(final long a, final long b, final long c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	public Labc(final Labc dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
	}



	@Override
	public Object clone() {
		return new Labc(this.a, this.b, this.c);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Labc) {
			final Labc dao = (Labc) obj;
			return (
				this.a == dao.a &&
				this.b == dao.b &&
				this.c == dao.c
			);
		}
		return false;
	}



	@Override
	public String toString() {
		return (new StringBuilder())
			.append(this.a).append(", ")
			.append(this.b).append(", ")
			.append(this.c)
			.toString();
	}
	@Override
	public int hashCode() {
		long bits =    31L  + this.a;
		bits = (bits * 31L) + this.b;
		bits = (bits * 31L) + this.c;
		return (int) (bits ^ (bits >> 32L));
	}



}
