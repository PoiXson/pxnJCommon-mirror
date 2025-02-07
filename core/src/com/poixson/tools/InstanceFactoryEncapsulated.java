package com.poixson.tools;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public class InstanceFactoryEncapsulated {

	protected final ClassLoader classloader;
	protected final String      classpath;



	public static InstanceFactoryEncapsulated Create(final ClassLoader classloader, final String classpath)
			throws ClassNotFoundException,
			NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		final Class<?> clss = classloader.loadClass(InstanceFactoryEncapsulated.class.getName());
		final Constructor<?> construct = clss.getConstructor(ClassLoader.class, String.class);
		return (InstanceFactoryEncapsulated) construct.newInstance(classloader, classpath);
	}



	public InstanceFactoryEncapsulated(final ClassLoader classloader, final String classpath) {
		this.classloader = classloader;
		this.classpath   = classpath;
	}



	public Object create() throws ClassNotFoundException,
			NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		final Class<?> clss = Class.forName(this.classpath, true, this.classloader);
		final Constructor<?> construct = clss.getConstructor();
		return construct.newInstance();
	}



}
