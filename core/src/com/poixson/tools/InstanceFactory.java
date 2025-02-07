package com.poixson.tools;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public class InstanceFactory<T> {

	protected final Class<T> clss;



	public InstanceFactory(final Class<T> clss) {
		this.clss = clss;
	}



	public T create() throws
			NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		final Constructor<T> construct = this.clss.getConstructor();
		return construct.newInstance();
	}



}
