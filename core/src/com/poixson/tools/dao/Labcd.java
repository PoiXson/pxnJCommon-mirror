package com.poixson.tools.dao;

import java.io.Serializable;


public class Labcd implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public long a;
	public long b;
	public long c;
	public long d;



	public Labcd() {
		this.a = 0L;
		this.b = 0L;
		this.c = 0L;
		this.d = 0L;
	}
	public Labcd(final long a, final long b, final long c, final long d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	public Labcd(final Labcd dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
		this.d = dao.d;
	}



	@Override
	public Object clone() {
		return new Labcd(this.a, this.b, this.c, this.d);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Labcd) {
			final Labcd dao = (Labcd) obj;
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
				.append('(') .append(this.a)
				.append(", ").append(this.b)
				.append(", ").append(this.c)
				.append(", ").append(this.d)
				.append(')')
				.toString();
	}
	@Override
	public int hashCode() {
		final long bits = ((((((31L + this.a) * 31L) + this.b) * 31L) + this.c) * 31L) + this.d;
		return (int) (bits ^ (bits >> 32L));
	}



}
