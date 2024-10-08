package com.poixson.tools.dao;

import static com.poixson.utils.Utils.IsEmpty;

import java.io.Serializable;

import com.poixson.utils.NumberUtils;


public class Babcdef implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public boolean a;
	public boolean b;
	public boolean c;
	public boolean d;
	public boolean e;
	public boolean f;



	public Babcdef() {
		this.a = false;
		this.b = false;
		this.c = false;
		this.d = false;
		this.e = false;
		this.f = false;
	}
	public Babcdef(final boolean a, final boolean b, final boolean c, final boolean d, final boolean e, final boolean f) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
	}
	public Babcdef(final Babcdef dao) {
		this.a = dao.a;
		this.b = dao.b;
		this.c = dao.c;
		this.d = dao.d;
		this.e = dao.e;
		this.f = dao.f;
	}



	@Override
	public Object clone() {
		return new Babcdef(this.a, this.b, this.c, this.d, this.e, this.f);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Babcdef) {
			final Babcdef dao = (Babcdef) obj;
			return (
				this.a == dao.a &&
				this.b == dao.b &&
				this.c == dao.c &&
				this.d == dao.d &&
				this.e == dao.e &&
				this.f == dao.f
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
			.append(this.e ? "true" : "false").append(", ")
			.append(this.f ? "true" : "false")
			.toString();
	}
	public static Babcdef FromString(final String str) {
		if (!IsEmpty(str)) {
			final String[] parts = str.split(",");
			if (parts.length == 6) {
				final Boolean bool0 = NumberUtils.ToBoolean(parts[0]); if (bool0 == null) return null;
				final Boolean bool1 = NumberUtils.ToBoolean(parts[1]); if (bool1 == null) return null;
				final Boolean bool2 = NumberUtils.ToBoolean(parts[2]); if (bool2 == null) return null;
				final Boolean bool3 = NumberUtils.ToBoolean(parts[3]); if (bool3 == null) return null;
				final Boolean bool4 = NumberUtils.ToBoolean(parts[4]); if (bool4 == null) return null;
				final Boolean bool5 = NumberUtils.ToBoolean(parts[5]); if (bool5 == null) return null;
				return new Babcdef(
					bool0.booleanValue(),
					bool1.booleanValue(),
					bool2.booleanValue(),
					bool3.booleanValue(),
					bool4.booleanValue(),
					bool5.booleanValue()
				);
			}
		}
		return null;
	}
	@Override
	public int hashCode() {
		return (
			(this.a ?  1 : 0) +
			(this.b ?  2 : 0) +
			(this.c ?  4 : 0) +
			(this.d ?  8 : 0) +
			(this.e ? 16 : 0) +
			(this.f ? 32 : 0)
		);
	}



}
