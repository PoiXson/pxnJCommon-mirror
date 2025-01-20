package com.poixson.tools;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions.TimeoutFailureFactory;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.function.ThrowingSupplier;
import org.opentest4j.MultipleFailuresError;


public final class Assertions implements AfterAllCallback {

	protected static final AtomicInteger count_asserts       = new AtomicInteger(0);
	protected static final AtomicInteger count_asserts_total = new AtomicInteger(0);
	protected static final AtomicBoolean count_over_ninet    = new AtomicBoolean(false);



	public Assertions() {
	}



	@Override
	public void afterAll(final ExtensionContext context) {
		final int c_asserts = count_asserts      .getAndSet(0);
		final int c_total   = count_asserts_total.intValue();
		System.out.println(String.format(
			"[%s%s%s] Assertions: %s+%d [%d]%s",
			"\033[1;34m", // light blue
			context.getDisplayName(),
			"\033[0m", // reset
			"\033[1;32m", // green/bold
			Integer.valueOf(c_asserts),
			Integer.valueOf(c_total),
			"\033[0m" // reset
		));
		if (c_total > 9000) {
			if (count_over_ninet.compareAndSet(false, true))
				System.out.println("\033[1;32mOver 9000 asserts!!!\033[0m");
		}
	}



	public static int INC() {
		count_asserts_total.incrementAndGet();
		return count_asserts.incrementAndGet();
	}



	// -------------------------------------------------------------------------------
	// fail



	public static <V> V Fail() {
		INC(); return org.junit.jupiter.api.Assertions.fail();
	}
	public static <V> V Fail(final String msg) {
		INC(); return org.junit.jupiter.api.Assertions.fail(msg);
	}
	public static <V> V Fail(final String msg, final Throwable cause) {
		INC(); return org.junit.jupiter.api.Assertions.fail(msg, cause);
	}
	public static <V> V Fail(final Throwable cause) {
		INC(); return org.junit.jupiter.api.Assertions.fail(cause);
	}
	public static <V> V Fail(final Supplier<String> supplier_msg) {
		INC(); return org.junit.jupiter.api.Assertions.fail(supplier_msg);
	}



	// -------------------------------------------------------------------------------
	// assert true/false/null



	// assert true
	public static void AssertTrue(final boolean condition) {
		INC(); org.junit.jupiter.api.Assertions.assertTrue(condition);
	}
	public static void AssertTrue(final boolean condition, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertTrue(condition, supplier_msg);
	}
	public static void AssertTrue(final BooleanSupplier supplier_bool) {
		INC(); org.junit.jupiter.api.Assertions.assertTrue(supplier_bool);
	}
	public static void AssertTrue(final BooleanSupplier supplier_bool, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertTrue(supplier_bool, msg);
	}
	public static void AssertTrue(final boolean condition, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertTrue(condition, msg);
	}
	public static void AssertTrue(final BooleanSupplier supplier_bool, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertTrue(supplier_bool, supplier_msg);
	}



	// assert false
	public static void AssertFalse(final boolean condition) {
		INC(); org.junit.jupiter.api.Assertions.assertFalse(condition);
	}
	public static void AssertFalse(final boolean condition, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertFalse(condition, msg);
	}
	public static void AssertFalse(final boolean condition, Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertFalse(condition, supplier_msg);
	}
	public static void AssertFalse(final BooleanSupplier supplier_bool) {
		INC(); org.junit.jupiter.api.Assertions.assertFalse(supplier_bool);
	}
	public static void AssertFalse(final BooleanSupplier supplier_bool, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertFalse(supplier_bool, msg);
	}
	public static void AssertFalse(final BooleanSupplier supplier_bool, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertFalse(supplier_bool, supplier_msg);
	}



	// assert null
	public static void AssertNull(final Object actual) {
		INC(); org.junit.jupiter.api.Assertions.assertNull(actual);
	}
	public static void AssertNull(final Object actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNull(actual, msg);
	}
	public static void AssertNull(final Object actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNull(actual, supplier_msg);
	}



	// assert not null
	public static void AssertNotNull(final Object actual) {
		INC(); org.junit.jupiter.api.Assertions.assertNotNull(actual);
	}
	public static void AssertNotNull(final Object actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotNull(actual, msg);
	}
	public static void AssertNotNull(final Object actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotNull(actual, supplier_msg);
	}



	// -------------------------------------------------------------------------------
	// assert equals



	// short
	public static void AssertEquals(final short expect, final short actual) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual);
	}
	public static void AssertEquals(final Short expect, final Short actual) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual);
	}
	public static void AssertEquals(final short expect, final short actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, msg);
	}
	public static void AssertEquals(final Short expect, final Short actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, msg);
	}
	public static void AssertEquals(final short expect, final short actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, supplier_msg);
	}
	public static void AssertEquals(final Short expect, final Short actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, supplier_msg);
	}



	// byte
	public static void AssertEquals(final byte expect, final byte actual) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual);
	}
	public static void AssertEquals(final Byte expect, final Byte actual) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual);
	}
	public static void AssertEquals(final byte expect, final byte actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, msg);
	}
	public static void AssertEquals(final Byte expect, final Byte actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, msg);
	}
	public static void AssertEquals(final byte expect, final byte actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, supplier_msg);
	}
	public static void AssertEquals(final Byte expect, final Byte actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, supplier_msg);
	}



	// int
	public static void AssertEquals(final int expect, final int actual) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual);
	}
	public static void AssertEquals(final Integer expect, final Integer actual) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual);
	}
	public static void AssertEquals(final int expect, final int actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, msg);
	}
	public static void AssertEquals(final Integer expect, final Integer actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, msg);
	}
	public static void AssertEquals(final int expect, final int actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, supplier_msg);
	}
	public static void AssertEquals(final Integer expect, final Integer actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, supplier_msg);
	}



	// long
	public static void AssertEquals(final long expect, final long actual) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual);
	}
	public static void AssertEquals(final Long expect, final Long actual) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual);
	}
	public static void AssertEquals(final long expect, final long actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, msg);
	}
	public static void AssertEquals(final Long expect, final Long actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, msg);
	}
	public static void AssertEquals(final long expect, final long actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, supplier_msg);
	}
	public static void AssertEquals(final Long expect, final Long actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, supplier_msg);
	}



	// float
	public static void AssertEquals(final float expect, final float actual) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual);
	}
	public static void AssertEquals(final Float expect, final Float actual) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual);
	}
	public static void AssertEquals(final float expect, final float actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, msg);
	}
	public static void AssertEquals(final Float expect, final Float actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, msg);
	}
	public static void AssertEquals(final float expect, final float actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, supplier_msg);
	}
	public static void AssertEquals(final Float expect, final Float actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, supplier_msg);
	}
	public static void AssertEquals(final float expect, final float actual, final float delta) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, delta);
	}
	public static void AssertEquals(final float expect, final float actual, final float delta, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, delta, msg);
	}
	public static void AssertEquals(final float expect, final float actual, final float delta, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, delta, supplier_msg);
	}



	// double
	public static void AssertEquals(final double expect, final double actual) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual);
	}
	public static void AssertEquals(final Double expect, final Double actual) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual);
	}
	public static void AssertEquals(final double expect, final double actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, msg);
	}
	public static void AssertEquals(final Double expect, final Double actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, msg);
	}
	public static void AssertEquals(final double expect, final double actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, supplier_msg);
	}
	public static void AssertEquals(final Double expect, final Double actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, supplier_msg);
	}
	public static void AssertEquals(final double expect, final double actual, final double delta) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, delta);
	}
	public static void AssertEquals(final double expect, final double actual, final double delta, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, delta, msg);
	}
	public static void AssertEquals(final double expect, final double actual, final double delta, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, delta, supplier_msg);
	}



	// char
	public static void AssertEquals(final char expect, final char actual) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual);
	}
	public static void AssertEquals(final Character expect, final Character actual) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual);
	}
	public static void AssertEquals(final char expect, final char actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, msg);
	}
	public static void AssertEquals(final Character expect, final Character actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, msg);
	}
	public static void AssertEquals(final char expect, final char actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, supplier_msg);
	}
	public static void AssertEquals(final Character expect, final Character actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, supplier_msg);
	}



	// object
	public static void AssertEquals(final Object expect, final Object actual) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual);
	}
	public static void AssertEquals(final Object expect, final Object actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, msg);
	}
	public static void AssertEquals(final Object expect, final Object actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertEquals(expect, actual, supplier_msg);
	}



	// -------------------------------------------------------------------------------
	// assert not equals



	// byte
	public static void AssertNotEquals(final byte unexpect, final byte actual) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual);
	}
	public static void AssertNotEquals(final Byte unexpect, final Byte actual) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual);
	}
	public static void AssertNotEquals(final byte unexpect, final byte actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, msg);
	}
	public static void AssertNotEquals(final Byte unexpect, final Byte actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, msg);
	}
	public static void AssertNotEquals(final byte unexpect, final byte actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, supplier_msg);
	}
	public static void AssertNotEquals(final Byte unexpect, final Byte actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, supplier_msg);
	}



	// short
	public static void AssertNotEquals(final short unexpect, final short actual) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual);
	}
	public static void AssertNotEquals(final Short unexpect, final Short actual) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual);
	}
	public static void AssertNotEquals(final short unexpect, final short actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, msg);
	}
	public static void AssertNotEquals(final Short unexpect, final Short actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, msg);
	}
	public static void AssertNotEquals(final short unexpect, final short actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, supplier_msg);
	}
	public static void AssertNotEquals(final Short unexpect, final Short actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, supplier_msg);
	}



	// int
	public static void AssertNotEquals(final int unexpect, final int actual) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual);
	}
	public static void AssertNotEquals(final Integer unexpect, final Integer actual) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual);
	}
	public static void AssertNotEquals(final int unexpect, final int actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, msg);
	}
	public static void AssertNotEquals(final Integer unexpect, final Integer actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, msg);
	}
	public static void AssertNotEquals(final int unexpect, final int actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, supplier_msg);
	}
	public static void AssertNotEquals(final Integer unexpect, final Integer actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, supplier_msg);
	}



	// long
	public static void AssertNotEquals(final long unexpect, final long actual) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual);
	}
	public static void AssertNotEquals(final Long unexpect, final Long actual) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual);
	}
	public static void AssertNotEquals(final long unexpect, final long actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, msg);
	}
	public static void AssertNotEquals(final Long unexpect, final Long actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, msg);
	}
	public static void AssertNotEquals(final long unexpect, final long actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, supplier_msg);
	}
	public static void AssertNotEquals(final Long unexpect, final Long actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, supplier_msg);
	}



	// float
	public static void AssertNotEquals(final float unexpect, final float actual) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual);
	}
	public static void AssertNotEquals(final Float unexpect, final Float actual) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual);
	}
	public static void AssertNotEquals(final float unexpect, final float actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, msg);
	}
	public static void AssertNotEquals(final Float unexpect, final Float actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, msg);
	}
	public static void AssertNotEquals(final float unexpect, final float actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, supplier_msg);
	}
	public static void AssertNotEquals(final Float unexpect, final Float actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, supplier_msg);
	}
	public static void AssertNotEquals(final float unexpect, final float actual, float delta) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, delta);
	}



	// double
	public static void AssertNotEquals(final double unexpect, final double actual) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual);
	}
	public static void AssertNotEquals(final double unexpect, final double actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, msg);
	}
	public static void AssertNotEquals(final Double unexpect, final Double actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, msg);
	}
	public static void AssertNotEquals(final double unexpect, final double actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, supplier_msg);
	}
	public static void AssertNotEquals(final Double unexpect, final Double actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, supplier_msg);
	}
	public static void AssertNotEquals(final double unexpect, final double actual, final double delta) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, delta);
	}
	public static void AssertNotEquals(final double unexpect, final double actual, final double delta, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, delta, msg);
	}
	public static void AssertNotEquals(final double unexpect, final double actual, final double delta, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, delta, supplier_msg);
	}



	// char
	public static void AssertNotEquals(final char unexpect, final char actual) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual);
	}
	public static void AssertNotEquals(final Character unexpect, final Character actual) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual);
	}
	public static void AssertNotEquals(final char unexpect, final char actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, msg);
	}
	public static void AssertNotEquals(final Character unexpect, final Character actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, msg);
	}
	public static void AssertNotEquals(final char unexpect, final char actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, supplier_msg);
	}
	public static void AssertNotEquals(final Character unexpect, final Character actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, supplier_msg);
	}



	// object
	public static void AssertNotEquals(final Object unexpect, final Object actual) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual);
	}
	public static void AssertNotEquals(final Object unexpect, final Object actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, msg);
	}
	public static void AssertNotEquals(final Object unexpect, final Object actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotEquals(unexpect, actual, supplier_msg);
	}



	// -------------------------------------------------------------------------------
	// assert array equals



	// boolean array equals
	public static void AssertArrayEquals(final boolean[] expect, final boolean[] actual) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual);
	}
	public static void AssertArrayEquals(final boolean[] expect, final boolean[] actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual, msg);
	}
	public static void AssertArrayEquals(final boolean[] expect, final boolean[] actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual, supplier_msg);
	}


	// char array equals
	public static void AssertArrayEquals(final char[] expect, final char[] actual) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual);
	}
	public static void AssertArrayEquals(final char[] expect, final char[] actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual, msg);
	}
	public static void AssertArrayEquals(final char[] expect, final char[] actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual, supplier_msg);
	}



	// byte array equals
	public static void AssertArrayEquals(final byte[] expect, final byte[] actual) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual);
	}
	public static void AssertArrayEquals(final byte[] expect, final byte[] actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual, msg);
	}
	public static void AssertArrayEquals(final byte[] expect, final byte[] actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual, supplier_msg);
	}



	// short array equals
	public static void AssertArrayEquals(final short[] expect, final short[] actual) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual);
	}
	public static void AssertArrayEquals(final short[] expect, final short[] actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual, msg);
	}
	public static void AssertArrayEquals(final short[] expect, final short[] actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual, supplier_msg);
	}



	// int array equals
	public static void AssertArrayEquals(final int[] expect, final int[] actual) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual);
	}
	public static void AssertArrayEquals(final int[] expect, final int[] actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual, msg);
	}
	public static void AssertArrayEquals(final int[] expect, final int[] actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual, supplier_msg);
	}



	// long array equals
	public static void AssertArrayEquals(final long[] expect, final long[] actual) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual);
	}
	public static void AssertArrayEquals(final long[] expect, final long[] actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual, msg);
	}
	public static void AssertArrayEquals(final long[] expect, final long[] actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual, supplier_msg);
	}



	// float array equals
	public static void AssertArrayEquals(final float[] expect, final float[] actual) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual);
	}
	public static void AssertArrayEquals(final float[] expect, final float[] actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual, msg);
	}
	public static void AssertArrayEquals(final float[] expect, final float[] actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual, supplier_msg);
	}
	public static void AssertArrayEquals(final float[] expect, final float[] actual, final float delta) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual, delta);
	}
	public static void AssertArrayEquals(final float[] expect, final float[] actual, final float delta, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual, delta, msg);
	}
	public static void AssertArrayEquals(final float[] expect, final float[] actual, final float delta, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual, delta, supplier_msg);
	}



	// double array equals
	public static void AssertArrayEquals(final double[] expect, final double[] actual) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual);
	}
	public static void AssertArrayEquals(final double[] expect, final double[] actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual, msg);
	}
	public static void AssertArrayEquals(final double[] expect, final double[] actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual, supplier_msg);
	}
	public static void AssertArrayEquals(final double[] expect, final double[] actual, final double delta) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual, delta);
	}
	public static void AssertArrayEquals(final double[] expect, final double[] actual, final double delta, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual, delta, msg);
	}
	public static void AssertArrayEquals(final double[] expect, final double[] actual, final double delta, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual, delta, supplier_msg);
	}



	// object array equals
	public static void AssertArrayEquals(final Object[] expect, final Object[] actual) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual);
	}
	public static void AssertArrayEquals(final Object[] expect, final Object[] actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual, msg);
	}
	public static void AssertArrayEquals(final Object[] expect, final Object[] actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertArrayEquals(expect, actual, supplier_msg);
	}



	// -------------------------------------------------------------------------------



	// iterable equals
	public static void AssertIterableEquals(final Iterable<?> expect, final Iterable<?> actual) {
		INC(); org.junit.jupiter.api.Assertions.assertIterableEquals(expect, actual);
	}
	public static void AssertIterableEquals(final Iterable<?> expect, final Iterable<?> actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertIterableEquals(expect, actual, msg);
	}
	public static void AssertIterableEquals(final Iterable<?> expect, final Iterable<?> actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertIterableEquals(expect, actual, supplier_msg);
	}



	// assert lines match
	public static void AssertLinesMatch(final List<String> expectLines, final List<String> actualLines) {
		INC(); org.junit.jupiter.api.Assertions.assertLinesMatch(expectLines, actualLines);
	}
	public static void AssertLinesMatch(final List<String> expectLines, final List<String> actualLines, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertLinesMatch(expectLines, actualLines, msg);
	}
	public static void AssertLinesMatch(final List<String> expectLines, final List<String> actualLines, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertLinesMatch(expectLines, actualLines, supplier_msg);
	}
	public static void AssertLinesMatch(final Stream<String> expectLines, final Stream<String> actualLines) {
		INC(); org.junit.jupiter.api.Assertions.assertLinesMatch(expectLines, actualLines);
	}
	public static void AssertLinesMatch(final Stream<String> expectLines, final Stream<String> actualLines, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertLinesMatch(expectLines, actualLines, msg);
	}
	public static void AssertLinesMatch(final Stream<String> expectLines, final Stream<String> actualLines, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertLinesMatch(expectLines, actualLines, supplier_msg);
	}



	// assert same
	public static void AssertSame(final Object expect, final Object actual) {
		INC(); org.junit.jupiter.api.Assertions.assertSame(expect, actual);
	}
	public static void AssertSame(final Object expect, final Object actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertSame(expect, actual, msg);
	}
	public static void AssertSame(final Object expect, final Object actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertSame(expect, actual, supplier_msg);
	}



	// assert not same
	public static void AssertNotSame(final Object unexpect, final Object actual) {
		INC(); org.junit.jupiter.api.Assertions.assertNotSame(unexpect, actual);
	}
	public static void AssertNotSame(final Object unexpect, final Object actual, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotSame(unexpect, actual, msg);
	}
	public static void AssertNotSame(final Object unexpect, final Object actual, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertNotSame(unexpect, actual, supplier_msg);
	}



	//assert all
	public static void AssertAll(final Executable...exes) throws MultipleFailuresError {
		INC(); org.junit.jupiter.api.Assertions.assertAll(exes);
	}
	public static void AssertAll(final String heading, final Executable...exes) throws MultipleFailuresError {
		INC(); org.junit.jupiter.api.Assertions.assertAll(heading, exes);
	}
	public static void AssertAll(final Collection<Executable> exes) throws MultipleFailuresError {
		INC(); org.junit.jupiter.api.Assertions.assertAll(exes);
	}
	public static void AssertAll(final String heading, final Collection<Executable> exes) throws MultipleFailuresError {
		INC(); org.junit.jupiter.api.Assertions.assertAll(heading, exes);
	}
	public static void AssertAll(final Stream<Executable> exes) throws MultipleFailuresError {
		INC(); org.junit.jupiter.api.Assertions.assertAll(exes);
	}
	public static void AssertAll(final String heading, final Stream<Executable> exes) throws MultipleFailuresError {
		INC(); org.junit.jupiter.api.Assertions.assertAll(heading, exes);
	}



	// -------------------------------------------------------------------------------
	// assert exceptions



	// exe
	public static <T extends Throwable> T AssertThrowsExactly(final Class<T> expectType, final Executable exe) {
		INC(); return org.junit.jupiter.api.Assertions.assertThrowsExactly(expectType, exe);
	}
	public static <T extends Throwable> T AssertThrowsExactly(final Class<T> expectType, final Executable exe, final String msg) {
		INC(); return org.junit.jupiter.api.Assertions.assertThrowsExactly(expectType, exe, msg);
	}
	public static <T extends Throwable> T AssertThrowsExactly(final Class<T> expectType, final Executable exe, final Supplier<String> supplier_msg) {
		INC(); return org.junit.jupiter.api.Assertions.assertThrowsExactly(expectType, exe, supplier_msg);
	}
	public static <T extends Throwable> T AssertThrows(final Class<T> expectType, final Executable exe) {
		INC(); return org.junit.jupiter.api.Assertions.assertThrows(expectType, exe);
	}
	public static <T extends Throwable> T AssertThrows(final Class<T> expectType, final Executable exe, final String msg) {
		INC(); return org.junit.jupiter.api.Assertions.assertThrows(expectType, exe, msg);
	}
	public static <T extends Throwable> T AssertThrows(final Class<T> expectType, final Executable exe, final Supplier<String> supplier_msg) {
		INC(); return org.junit.jupiter.api.Assertions.assertThrows(expectType, exe, supplier_msg);
	}



	// exe
	public static void AssertDoesNotThrow(final Executable exe) {
		INC(); org.junit.jupiter.api.Assertions.assertDoesNotThrow(exe);
	}
	public static void AssertDoesNotThrow(final Executable exe, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertDoesNotThrow(exe, msg);
	}
	public static void AssertDoesNotThrow(final Executable exe, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertDoesNotThrow(exe, supplier_msg);
	}



	// supplier
	public static <T> T AssertDoesNotThrow(final ThrowingSupplier<T> supplier) {
		INC(); return org.junit.jupiter.api.Assertions.assertDoesNotThrow(supplier);
	}
	public static <T> T AssertDoesNotThrow(final ThrowingSupplier<T> supplier, final String msg) {
		INC(); return org.junit.jupiter.api.Assertions.assertDoesNotThrow(supplier, msg);
	}
	public static <T> T AssertDoesNotThrow(final ThrowingSupplier<T> supplier, final Supplier<String> supplier_msg) {
		INC(); return org.junit.jupiter.api.Assertions.assertDoesNotThrow(supplier, supplier_msg);
	}



	// -------------------------------------------------------------------------------
	// assert timeout



	// exe
	public static void AssertTimeout(final Duration timeout, final Executable exe) {
		INC(); org.junit.jupiter.api.Assertions.assertTimeout(timeout, exe);
	}
	public static void AssertTimeout(final Duration timeout, final Executable exe, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertTimeout(timeout, exe, msg);
	}
	public static void AssertTimeout(final Duration timeout, final Executable exe, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertTimeout(timeout, exe, supplier_msg);
	}



	// supplier
	public static <T> T AssertTimeout(final Duration timeout, final ThrowingSupplier<T> supplier) {
		INC(); return org.junit.jupiter.api.Assertions.assertTimeout(timeout, supplier);
	}
	public static <T> T AssertTimeout(final Duration timeout, final ThrowingSupplier<T> supplier, final String msg) {
		INC(); return org.junit.jupiter.api.Assertions.assertTimeout(timeout, supplier, msg);
	}
	public static <T> T AssertTimeout(final Duration timeout, final ThrowingSupplier<T> supplier, final Supplier<String> supplier_msg) {
		INC(); return org.junit.jupiter.api.Assertions.assertTimeout(timeout, supplier, supplier_msg);
	}



	// exe - preemptively
	public static void AssertTimeoutPreemptively(final Duration timeout, final Executable exe) {
		INC(); org.junit.jupiter.api.Assertions.assertTimeoutPreemptively(timeout, exe);
	}
	public static void AssertTimeoutPreemptively(final Duration timeout, final Executable exe, final String msg) {
		INC(); org.junit.jupiter.api.Assertions.assertTimeoutPreemptively(timeout, exe, msg);
	}
	public static void AssertTimeoutPreemptively(final Duration timeout, final Executable exe, final Supplier<String> supplier_msg) {
		INC(); org.junit.jupiter.api.Assertions.assertTimeoutPreemptively(timeout, exe, supplier_msg);
	}



	// supplier - preemptively
	public static <T> T AssertTimeoutPreemptively(final Duration timeout, final ThrowingSupplier<T> supplier) {
		INC(); return org.junit.jupiter.api.Assertions.assertTimeoutPreemptively(timeout, supplier);
	}
	public static <T> T AssertTimeoutPreemptively(final Duration timeout, final ThrowingSupplier<T> supplier, final String msg) {
		INC(); return org.junit.jupiter.api.Assertions.assertTimeoutPreemptively(timeout, supplier, msg);
	}
	public static <T> T AssertTimeoutPreemptively(final Duration timeout, final ThrowingSupplier<T> supplier, final Supplier<String> supplier_msg) {
		INC(); return org.junit.jupiter.api.Assertions.assertTimeoutPreemptively(timeout, supplier, supplier_msg);
	}
	public static <T, E extends Throwable> T AssertTimeoutPreemptively(final Duration timeout, final ThrowingSupplier<T> supplier,
			final Supplier<String> supplier_msg, final TimeoutFailureFactory<E> failureFactory) throws E {
		INC(); return org.junit.jupiter.api.Assertions.assertTimeoutPreemptively(timeout, supplier, supplier_msg, failureFactory);
	}



	// -------------------------------------------------------------------------------
	// assert instance-of



	public static <T> T AssertInstanceOf(final Class<T> expect_type, final Object actual) {
		INC(); return org.junit.jupiter.api.Assertions.assertInstanceOf(expect_type, actual);
	}
	public static <T> T AssertInstanceOf(final Class<T> expect_type, final Object actual, final String msg) {
		INC(); return org.junit.jupiter.api.Assertions.assertInstanceOf(expect_type, actual, msg);
	}
	public static <T> T AssertInstanceOf(final Class<T> expect_type, final Object actual, final Supplier<String> supplier_msg) {
		INC(); return org.junit.jupiter.api.Assertions.assertInstanceOf(expect_type, actual, supplier_msg);
	}



}
