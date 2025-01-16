package com.poixson.utils;

import static com.poixson.tools.Assertions.Fail;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public final class AssertUtils {
	private AssertUtils() {}



	public static void AssertArrayContains(final Object[] expect, final Object[] actual) {
		if (expect == null && actual == null) return;
		if (expect == null) { Fail("actual is null"); return; }
		if (actual == null) { Fail("expect is null"); return; }
		final List<Object> actual_list = new ArrayList<Object>();
		for (int index=0; index<actual.length; index++)
			actual_list.add(actual[index]);
		final List<Object> foundList = new ArrayList<Object>();
		for (int index=0; index<expect.length; index++) {
			final Object obj = expect[index];
			if (!actual_list.contains(obj)) {
				Fail("Entry not in actual array: "+ToString(obj));
				return;
			}
			foundList.add(obj);
		}
		for (final Object obj : foundList)
			actual_list.remove(obj);
		if (!IsEmpty(actual_list))
			Fail("Entries not in expected array: "+MergeObjects(", ", actual_list));
	}



	// -------------------------------------------------------------------------------



	public static boolean IsEmpty(final Object[] array) {
		if (array == null) return true;
		return (array.length == 0);
	}
	public static boolean IsEmpty(final Collection<?> collect) {
		if (collect == null) return true;
		return (collect.size() == 0);
	}



	public static String ToString(final Object obj) {
		if (obj == null) return null;
		if (obj.getClass().isArray()) {
			final StringBuilder result = new StringBuilder();
			for (final Object o : (Object[]) obj) {
				if (!result.isEmpty())
					result.append(' ');
				result.append('{').append( ToString(o) ).append('}');
			}
			return result.toString();
		}
		if (obj instanceof Boolean  ) return ( ((Boolean)obj).booleanValue() ? "<TRUE>" : "<false>" );
		if (obj instanceof String   ) return (String) obj;
		if (obj instanceof Integer  ) return ((Integer) obj).toString();
		if (obj instanceof Long     ) return ((Long)    obj).toString();
		if (obj instanceof Double   ) return ((Double)  obj).toString();
		if (obj instanceof Float    ) return ((Float)   obj).toString();
		if (obj instanceof Exception) return ExceptionToString((Exception) obj);
		// unknown object
		return obj.toString();
	}
	public static String ExceptionToString(final Throwable e) {
		if (e == null) return null;
		final StringWriter writer = new StringWriter(256);
		e.printStackTrace(new PrintWriter(writer));
		try {
			return writer.toString().trim();
		} finally {
			try {
				writer.close();
			} catch (Exception ignore) {}
		}
	}



	public static String MergeObjects(final String delim, final Object...array) {
		final StringBuilder buf = new StringBuilder();
		// merge with delim
		for (final Object entry : array) {
			if (entry != null) {
				if (!buf.isEmpty())
					buf.append(delim);
				buf.append(ToString(entry));
			}
		}
		return buf.toString();
	}



}
