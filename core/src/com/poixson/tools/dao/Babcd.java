package com.poixson.tools.dao;

import static com.poixson.utils.MathUtils.ToBoolean;
import static com.poixson.utils.Utils.IsEmpty;

import java.io.Serializable;


public class Babcd implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public boolean a;
	public boolean b;
	public boolean c;
	public boolean d;



	public Babcd() {
		this.a = false;
		this.b = false;
		this.c = false;
		this.d = false;
	}
	public Babcd(final boolean a, final boolean b, final boolean c, final boolean d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	public Babcd(final Babcd dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
		this.d = dao.d;
	}



	@Override
	public Object clone() {
		return new Babcd(this.a, this.b, this.c, this.d);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Babcd dao) {
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
			.append(this.a ? "true" : "false").append(", ")
			.append(this.b ? "true" : "false").append(", ")
			.append(this.c ? "true" : "false").append(", ")
			.append(this.d ? "true" : "false")
			.toString();
	}
	public static Babcd FromString(final String str) {
		if (!IsEmpty(str)) {
			final String[] parts = str.split(",");
			if (parts.length == 4) {
				final Boolean bool0 = ToBoolean(parts[0]); if (bool0 == null) return null;
				final Boolean bool1 = ToBoolean(parts[1]); if (bool1 == null) return null;
				final Boolean bool2 = ToBoolean(parts[2]); if (bool2 == null) return null;
				final Boolean bool3 = ToBoolean(parts[3]); if (bool3 == null) return null;
				return new Babcd(
					bool0.booleanValue(),
					bool1.booleanValue(),
					bool2.booleanValue(),
					bool3.booleanValue()
				);
			}
		}
		return null;
	}
	@Override
	public int hashCode() {
		return (
			(this.a ? 1 : 0) +
			(this.b ? 2 : 0) +
			(this.c ? 4 : 0) +
			(this.d ? 8 : 0)
		);
	}



}
