package com.poixson.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.poixson.exceptions.RequiredArgumentException;


public final class ReflectUtils {
	private ReflectUtils() {}



	// ------------------------------------------------------------------------------- //
	// classes



	public static String GetClassName(final Class<?> clss) {
		if (clss == null) return null;
		return clss.getSimpleName();
	}



	// ------------------------------------------------------------------------------- //
	// methods



	public static Method getMethodByName(final Object container,
			final String methodName, final Class<?>...args) {
		if (container == null)         throw new IllegalArgumentException("container");
		if (Utils.isEmpty(methodName)) throw new IllegalArgumentException("methodName");
		final Class<?> clss = ( container instanceof Class ? (Class<?>) container : container.getClass() );
		if (clss == null) return null;
		try {
			return clss.getMethod( methodName, args);
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("Invalid method: "+methodName, e);
		} catch (SecurityException e) {
			throw new IllegalArgumentException("Error accessing method: "+methodName, e);
		}
	}
	public static Object InvokeMethod(final Object container,
			final String methodName, final Object...args) {
		return InvokeMethod(
			container,
			getMethodByName(
				container,
				methodName,
				ArgsToClasses(args)
			),
			args
		);
	}
	public static Object InvokeMethod(final Object container,
			final Method method, final Object...args) {
		if (container == null) throw new IllegalArgumentException("container");
		if (method == null)    throw new IllegalArgumentException("method");
		try {
			return method.invoke(container, args);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException("Failed to call method: "+method.getName(), e);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Failed to call method: "+method.getName(), e);
		} catch (InvocationTargetException e) {
			throw new IllegalArgumentException("Failed to call method: "+method.getName(), e);
		}
	}



	// ------------------------------------------------------------------------------- //
	// arguments



	public static Class<?>[] ArgsToClasses(final Object...args) {
		if (Utils.isEmpty(args)) return null;
		final Class<?>[] classes = new Class[args.length];
		for (int i=0; i<args.length; i++) {
			classes[i] = ( args[i] instanceof Class ? (Class<?>) args[i] : args[i].getClass() );
		}
		return classes;
	}



	// ------------------------------------------------------------------------------- //
	// variables



	public static String getStaticString(final Class<?> clss, final String name) {
		if (clss == null)        throw new RequiredArgumentException("clss");
		if (Utils.isEmpty(name)) throw new RequiredArgumentException("name");
		final Field field;
		final String value;
		try {
			field = clss.getField(name);
			final Object o = field.get(null);
			value = (String) o;
		} catch (NoSuchFieldException e) {
			throw new IllegalArgumentException("Invalid field: "+name, e);
		} catch (SecurityException e) {
			throw new IllegalArgumentException("Error accessing field: "+name, e);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Failed to get field: "+name, e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException("Error accessing field: "+name, e);
		}
		return value;
	}



}
