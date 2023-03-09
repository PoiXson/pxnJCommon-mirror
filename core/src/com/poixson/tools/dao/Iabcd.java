package com.poixson.tools.dao;

import java.io.Serializable;


public class Iabcd implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public int a;
	public int b;
	public int c;
	public int d;



	public Iabcd() {
		this.a = 0;
		this.b = 0;
		this.c = 0;
		this.d = 0;
	}
	public Iabcd(final int a, final int b, final int c, final int d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	public Iabcd(final Iabcd dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
		this.d = dao.d;
	}



	@Override
	public Object clone() {
		return new Iabcd(this.a, this.b, this.c, this.d);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Iabcd) {
			final Iabcd dao = (Iabcd) obj;
			return (
				this.a == dao.a &&
				this.b == dao.b &&
				this.c == dao.c &&
				this.d == dao.d
			);
		}
		return false;
	}



	@Override
	public String toString() {
		return (new StringBuilder())
			.append(this.a).append(", ")
			.append(this.b).append(", ")
			.append(this.c).append(", ")
			.append(this.d)
			.toString();
	}
	@Override
	public int hashCode() {
		long bits =    31L  + ((long)this.a);
		bits = (bits * 31L) + ((long)this.b);
		bits = (bits * 31L) + ((long)this.c);
		bits = (bits * 31L) + ((long)this.d);
		return (int) (bits ^ (bits >> 32L));
	}



}
