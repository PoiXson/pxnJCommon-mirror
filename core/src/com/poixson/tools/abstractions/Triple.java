package com.poixson.tools.abstractions;


public class Triple<K, V, E> {

	public final K key;
	public final V val;
	public final E ent;



	public Triple(final K key, final V val, final E ent) {
		this.key = key;
		this.val = val;
		this.ent = ent;
	}



//TODO: fix this and unit test
	@Override
	public boolean equals(final Object match) {
		if (this == match)             return true;
		if (!(match instanceof Triple)) return false;
		@SuppressWarnings("unchecked")
		final Triple<K, V, E> trip = (Triple<K, V, E>) match;
		return (this.hashCode() == trip.hashCode());
	}



	@Override
	public String toString() {
		return (new StringBuilder())
			.append('[').append(this.key)
			.append(',').append(this.val)
			.append(',').append(this.ent)
			.append(']').toString();
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = (result * 31) + (this.key == null ? 0 : this.key.hashCode());
		result = (result * 31) + (this.val == null ? 0 : this.val.hashCode());
		result = (result * 31) + (this.ent == null ? 0 : this.ent.hashCode());
		return result;
	}



}
