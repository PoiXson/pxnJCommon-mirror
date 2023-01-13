package com.poixson.tools;

import java.util.LinkedList;


public class RollingList<E> extends LinkedList<E> {
	private static final long serialVersionUID = 1L;

	private final int maxSize;

	private final boolean autoPrune;



	public RollingList(final int size) {
		this(
			size,
			true
		);
	}
	public RollingList(final int size, final boolean autoPrune) {
		if (size <= 0) throw new IllegalArgumentException("size argument must be greater than zero.");
		this.maxSize = size;
		this.autoPrune = autoPrune;
	}



	@Override
	public boolean add(final E entry) {
		final boolean result = super.add(entry);
		if (this.autoPrune)
			this.prune();
		return result;
	}



	public int prune() {
		final int maxSize = this.maxSize;
		final int curSize = this.size();
		final int remSize = curSize - maxSize;
		if (remSize <= 0)
			return 0;
		this.removeRange(0, remSize);
		return remSize;
	}



}
