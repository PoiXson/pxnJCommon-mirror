/*
package com.poixson.tools;

import static com.poixson.tools.Assertions.AssertEquals;
import static com.poixson.utils.MathUtils.DELTA;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.poixson.tools.atomic.AtomicModularDouble;
import com.poixson.tools.atomic.AtomicModularInteger;
import com.poixson.tools.atomic.AtomicModularLong;


@ExtendWith(Assertions.class)
public class Test_AtomicModular {



	@Test
	public void testAtomicModular() {
		final AtomicModularInteger atomic_int = new AtomicModularInteger(0,   2,   5  );
		final AtomicModularLong    atomic_lng = new AtomicModularLong   (0L,  2L,  5L );
		final AtomicModularDouble  atomic_dbl = new AtomicModularDouble (0.0, 2.0, 5.0);
		AssertEquals(4, atomic_int.getAndIncrement()); AssertEquals(4L, atomic_lng.getAndIncrement()); AssertEquals(4.0, atomic_dbl.getAndIncrement(), DELTA);
		AssertEquals(5, atomic_int.getAndIncrement()); AssertEquals(5L, atomic_lng.getAndIncrement()); AssertEquals(5.0, atomic_dbl.getAndIncrement(), DELTA);
		AssertEquals(2, atomic_int.getAndIncrement()); AssertEquals(2L, atomic_lng.getAndIncrement()); AssertEquals(2.0, atomic_dbl.getAndIncrement(), DELTA);
		AssertEquals(3, atomic_int.getAndIncrement()); AssertEquals(3L, atomic_lng.getAndIncrement()); AssertEquals(3.0, atomic_dbl.getAndIncrement(), DELTA);
		AssertEquals(4, atomic_int.getAndIncrement()); AssertEquals(4L, atomic_lng.getAndIncrement()); AssertEquals(4.0, atomic_dbl.getAndIncrement(), DELTA);
		AssertEquals(5, atomic_int.getAndIncrement()); AssertEquals(5L, atomic_lng.getAndIncrement()); AssertEquals(5.0, atomic_dbl.getAndIncrement(), DELTA);
		AssertEquals(2, atomic_int.getAndIncrement()); AssertEquals(2L, atomic_lng.getAndIncrement()); AssertEquals(2.0, atomic_dbl.getAndIncrement(), DELTA);
	}



}
*/
