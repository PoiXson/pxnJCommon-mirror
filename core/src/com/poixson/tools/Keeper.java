package com.poixson.tools;

import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;

import com.poixson.exceptions.RequiredArgumentException;


public class Keeper {

	private static final AtomicReference<Keeper> instance = new AtomicReference<Keeper>(null);

	private static final CopyOnWriteArraySet<Object> holder = new CopyOnWriteArraySet<Object>();



	static {
		instance.compareAndSet(null, new Keeper());
	}

	private Keeper() {
	}



	public static Keeper Get() {
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



}
