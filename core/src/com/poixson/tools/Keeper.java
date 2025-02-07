package com.poixson.tools;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.RequiredArgumentException;


public class Keeper {

	private static final AtomicReference<Keeper> instance =
			new AtomicReference<Keeper>(null);

	private static final CopyOnWriteArraySet<Object> holder =
			new CopyOnWriteArraySet<Object>();



	public static Keeper Get() {
		if (instance.get() == null) {
			// new instance
			final Keeper keeper = new Keeper();
			if (instance.compareAndSet(null, keeper))
				return keeper;
		}
		return instance.get();
	}



	public static void Add(final Object obj) {
		if (obj == null) throw new RequiredArgumentException("obj");
		holder.add(obj);
	}



	public static void Remove(final Object obj) {
		if (obj == null) throw new RequiredArgumentException("obj");
		holder.remove(obj);
	}
	@Deprecated
	public static void RemoveAll() {
		holder.clear();
	}
	@Deprecated
	public static int RemoveAll(final Class<? extends Object> clss) {
		if (holder.isEmpty())
			return 0;
		int count = 0;
		final String expect = clss.getName();
		final Iterator<Object> it = holder.iterator();
		while (it.hasNext()) {
			final Object obj = it.next();
			final String actual = obj.getClass().getName();
			if (expect.equals(actual)) {
				count++;
				Remove(obj);
			}
		}
		return count;
	}



}
