package com.poixson.tools.dao;

import java.io.Serializable;


public class Dxyz implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	public double x;
	public double y;
	public double z;



	public Dxyz() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	public Dxyz(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Dxyz(final Dxyz dao) {
		this.x = dao.x;
		this.y = dao.y;
		this.z = dao.z;
	}



	@Override
	public Object clone() {
		return new Dxyz(this.x, this.y, this.z);
	}



	@Override
	public boolean equals(final Object obj) {
		if (obj == null) return false;
		if (obj instanceof Dxyz) {
			final Dxyz dao = (Dxyz) obj;
			return (
				this.x == dao.x &&
				this.y == dao.y &&
				this.z == dao.z
			);
		}
		return false;
	}



	@Override
	public String toString() {
		return (new StringBuilder())
				.append('(') .append(this.x)
				.append(", ").append(this.y)
				.append(", ").append(this.z)
				.append(')')
				.toString();
	}
	@Override
	public int hashCode() {
		long bits =    31L  + Double.doubleToLongBits(this.x == 0.0 ? 0.0 : this.x);
		bits = (bits * 31L) + Double.doubleToLongBits(this.y == 0.0 ? 0.0 : this.y);
		bits = (bits * 31L) + Double.doubleToLongBits(this.z == 0.0 ? 0.0 : this.z);
		return (int) (bits ^ (bits >> 32L));
	}



}
