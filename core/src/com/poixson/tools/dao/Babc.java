package com.poixson.tools.dao;

import static com.poixson.utils.MathUtils.ToBoolean;
import static com.poixson.utils.Utils.IsEmpty;

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
	public static Babc FromString(final String str) {
		if (!IsEmpty(str)) {
			final String[] parts = str.split(",");
			if (parts.length == 3) {
				final Boolean bool0 = ToBoolean(parts[0]); if (bool0 == null) return null;
				final Boolean bool1 = ToBoolean(parts[1]); if (bool1 == null) return null;
				final Boolean bool2 = ToBoolean(parts[2]); if (bool2 == null) return null;
				return new Babc(
					bool0.booleanValue(),
					bool1.booleanValue(),
					bool2.booleanValue()
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
			(this.c ? 4 : 0)
		);
	}



}
