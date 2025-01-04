package com.poixson.tools;

import static com.poixson.utils.MathUtils.DELTA;

import org.junit.Assert;
import org.junit.Test;

import com.poixson.tools.atomic.AtomicModularDouble;
import com.poixson.tools.atomic.AtomicModularInteger;
import com.poixson.tools.atomic.AtomicModularLong;



public class Test_AtomicModular {



	@Test
	public void testAtomicModular() {
		final AtomicModularInteger atomic_int = new AtomicModularInteger(0,   2,   5  );
		final AtomicModularLong    atomic_lng = new AtomicModularLong   (0L,  2L,  5L );
		final AtomicModularDouble  atomic_dbl = new AtomicModularDouble (0.0, 2.0, 5.0);
		Assert.assertEquals(4, atomic_int.getAndIncrement()); Assert.assertEquals(4L, atomic_lng.getAndIncrement()); Assert.assertEquals(4.0, atomic_dbl.getAndIncrement(), DELTA);
		Assert.assertEquals(5, atomic_int.getAndIncrement()); Assert.assertEquals(5L, atomic_lng.getAndIncrement()); Assert.assertEquals(5.0, atomic_dbl.getAndIncrement(), DELTA);
		Assert.assertEquals(2, atomic_int.getAndIncrement()); Assert.assertEquals(2L, atomic_lng.getAndIncrement()); Assert.assertEquals(2.0, atomic_dbl.getAndIncrement(), DELTA);
		Assert.assertEquals(3, atomic_int.getAndIncrement()); Assert.assertEquals(3L, atomic_lng.getAndIncrement()); Assert.assertEquals(3.0, atomic_dbl.getAndIncrement(), DELTA);
		Assert.assertEquals(4, atomic_int.getAndIncrement()); Assert.assertEquals(4L, atomic_lng.getAndIncrement()); Assert.assertEquals(4.0, atomic_dbl.getAndIncrement(), DELTA);
		Assert.assertEquals(5, atomic_int.getAndIncrement()); Assert.assertEquals(5L, atomic_lng.getAndIncrement()); Assert.assertEquals(5.0, atomic_dbl.getAndIncrement(), DELTA);
		Assert.assertEquals(2, atomic_int.getAndIncrement()); Assert.assertEquals(2L, atomic_lng.getAndIncrement()); Assert.assertEquals(2.0, atomic_dbl.getAndIncrement(), DELTA);
	}



}
