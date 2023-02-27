package com.poixson.tools.dao;

import java.io.Serializable;


public class Iabc implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public int a;
	public int b;
	public int c;



	public Iabc() {
		this.a = 0;
		this.b = 0;
		this.c = 0;
	}
	public Iabc(final int a, final int b, final int c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	public Iabc(final Iabc dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
	}



	@Override
	public Object clone() {
		return new Iabc(this.a, this.b, this.c);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Iabc) {
			final Iabc dao = (Iabc) obj;
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
				.append('(') .append(this.a)
				.append(", ").append(this.b)
				.append(", ").append(this.c)
				.append(')')
				.toString();
	}
	@Override
	public int hashCode() {
		long bits =    31L  + ((long)this.a);
		bits = (bits * 31L) + ((long)this.b);
		bits = (bits * 31L) + ((long)this.c);
		return (int) (bits ^ (bits >> 32L));
	}



}
