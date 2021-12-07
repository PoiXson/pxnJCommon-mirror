package com.poixson.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.poixson.exceptions.RequiredArgumentException;


public final class ReflectUtils {
	private ReflectUtils() {}



	// -------------------------------------------------------------------------------
	// classes



	public static String GetClassName(final Object obj) {
		if (obj == null) return null;
		if (obj instanceof Class)
			return ((Class<?>) obj).getSimpleName();
		return obj.getClass().getSimpleName();
	}



	public static <T> Class<T> GetClass(final String classStr) {
		try {
			@SuppressWarnings("unchecked")
			final Class<T> clss = (Class<T>) Class.forName(classStr);
			return clss;
		} catch (ClassNotFoundException ignore) {}
		return null;
	}



	public static <T> T NewInstance(final Class<T> clss, final Object...args) {
		final Constructor<T> construct;
		try {
			if (args.length == 0) {
				construct = clss.getConstructor();
			} else {
				construct = clss.getConstructor( ArgsToClasses(args) );
			}
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException(e);
		} catch (SecurityException e) {
			throw new IllegalArgumentException(e);
		}
		final T instance;
		try {
			if (args.length == 0) {
				instance = construct.newInstance();
			} else {
				instance = construct.newInstance(args);
			}
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		} catch (InvocationTargetException e) {
			throw new IllegalArgumentException(e);
		}
		return instance;
	}



	// -------------------------------------------------------------------------------
	// methods



	public static Method getMethodByName(final String className,
			final String methodName, final Class<?>...args) {
		final Class<?> clss;
		try {
			clss = Class.forName(className);
		} catch (ClassNotFoundException ignore) {
			return null;
		}
		if (clss == null) return null;
		return getMethodByName(clss, methodName, args);
	}
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



	public static Object InvokeMethod(final String className,
			final String methodName, final Object...args) {
		final Class<?> clss;
		try {
			clss = Class.forName(className);
		} catch (ClassNotFoundException ignore) {
			return null;
		}
		if (clss == null) return null;
		return InvokeMethod(clss, methodName, args);
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



	// -------------------------------------------------------------------------------
	// arguments



	public static Class<?>[] ArgsToClasses(final Object...args) {
		if (Utils.isEmpty(args)) return null;
		final Class<?>[] classes = new Class[args.length];
		for (int i=0; i<args.length; i++) {
			classes[i] = ( args[i] instanceof Class ? (Class<?>) args[i] : args[i].getClass() );
		}
		return classes;
	}



	// -------------------------------------------------------------------------------
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
