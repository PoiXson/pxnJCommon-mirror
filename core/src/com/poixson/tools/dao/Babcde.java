package com.poixson.tools.dao;

import java.io.Serializable;


public class Babcde implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public boolean a;
	public boolean b;
	public boolean c;
	public boolean d;
	public boolean e;



	public Babcde() {
		this.a = false;
		this.b = false;
		this.c = false;
		this.d = false;
		this.e = false;
	}
	public Babcde(final boolean a, final boolean b, final boolean c, final boolean d, final boolean e) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
	}
	public Babcde(final Babcde dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
		this.d = dao.d;
		this.e = dao.e;
	}



	@Override
	public Object clone() {
		return new Babcde(this.a, this.b, this.c, this.d, this.e);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Babcde) {
			final Babcde dao = (Babcde) obj;
			return (
				this.a == dao.a &&
				this.b == dao.b &&
				this.c == dao.c &&
				this.d == dao.d &&
				this.e == dao.e
			);
		}
		return false;
	}



	public String toString() {
		return (new StringBuilder())
			.append(this.a ? "true" : "false").append(", ")
			.append(this.b ? "true" : "false").append(", ")
			.append(this.c ? "true" : "false").append(", ")
			.append(this.d ? "true" : "false").append(", ")
			.append(this.e ? "true" : "false")
			.toString();
	}
	@Override
	public int hashCode() {
		return (
			(this.a ?  1 : 0) +
			(this.b ?  2 : 0) +
			(this.c ?  4 : 0) +
			(this.d ?  8 : 0) +
			(this.e ? 16 : 0)
		);
	}



}
