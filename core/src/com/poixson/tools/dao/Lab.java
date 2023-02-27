package com.poixson.tools.dao;

import java.io.Serializable;


public class Lab implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public long a;
	public long b;



	public Lab() {
		this.a = 0L;
		this.b = 0L;
	}
	public Lab(final long a, final long b) {
		this.a = a;
		this.b = b;
	}
	public Lab(final Lab dao) {
		this.a = dao.a;
		this.b = dao.b;
	}



	@Override
	public Object clone() {
		return new Lab(this.a, this.b);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Lab) {
			final Lab dao = (Lab) obj;
			return (
				this.a == dao.a &&
				this.b == dao.b
			);
		}
		return false;
	}



	@Override
	public String toString() {
		return (new StringBuilder())
				.append('(') .append(this.a)
				.append(", ").append(this.b)
				.append(')')
				.toString();
	}
	@Override
	public int hashCode() {
		final long bits = ((31L + this.a) * 31L) + this.b;
		return (int) (bits ^ (bits >> 32L));
	}



}
