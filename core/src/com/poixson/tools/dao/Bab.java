package com.poixson.tools.dao;

import static com.poixson.utils.MathUtils.ToBoolean;
import static com.poixson.utils.Utils.IsEmpty;

import java.io.Serializable;


public class Bab implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public boolean a;
	public boolean b;



	public Bab() {
		this.a = false;
		this.b = false;
	}
	public Bab(final boolean a, final boolean b) {
		this.a = a;
		this.b = b;
	}
	public Bab(final Bab dao) {
		this.a = dao.a;
		this.b = dao.b;
	}



	@Override
	public Object clone() {
		return new Bab(this.a, this.b);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Bab) {
			final Bab dao = (Bab) obj;
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
			.append(this.a ? "true" : "false").append(", ")
			.append(this.b ? "true" : "false")
			.toString();
	}
	public static Bab FromString(final String str) {
		if (!IsEmpty(str)) {
			final String[] parts = str.split(",");
			if (parts.length == 2) {
				final Boolean bool0 = ToBoolean(parts[0]); if (bool0 == null) return null;
				final Boolean bool1 = ToBoolean(parts[1]); if (bool1 == null) return null;
				return new Bab(
					bool0.booleanValue(),
					bool1.booleanValue()
				);
			}
		}
		return null;
	}
	@Override
	public int hashCode() {
		return (
			(this.a ? 1 : 0) +
			(this.b ? 2 : 0)
		);
	}



}
