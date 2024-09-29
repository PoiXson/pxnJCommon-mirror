package com.poixson.tools.dao;

import java.io.Serializable;


public class Babc implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public boolean a;
	public boolean b;
	public boolean c;



	public Babc() {
		this.a = false;
		this.b = false;
		this.c = false;
	}
	public Babc(final boolean a, final boolean b, final boolean c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	public Babc(final Babc dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
	}



	@Override
	public Object clone() {
		return new Babc(this.a, this.b, this.c);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Babc) {
			final Babc dao = (Babc) obj;
			return (
				this.a == dao.a &&
				this.b == dao.b &&
				this.c == dao.c
			);
		}
		return false;
	}



	public String toString() {
		return (new StringBuilder())
			.append(this.a ? "true" : "false").append(", ")
			.append(this.b ? "true" : "false").append(", ")
			.append(this.c ? "true" : "false")
			.toString();
	}
	@Override
	public int hashCode() {
		return (
			(this.a ? 1 : 0) +
			(this.b ? 2 : 0) +
			(this.c ? 4 : 0)
		);
	}



}
