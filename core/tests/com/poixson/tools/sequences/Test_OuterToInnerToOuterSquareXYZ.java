package com.poixson.tools.sequences;

import org.junit.Assert;
import org.junit.Test;

import com.poixson.tools.dao.Iabc;


public class Test_OuterToInnerToOuterSquareXYZ {



	@Test
	public void testOuterToInnerXYZ() {
		// size 0
		{
			final OuterToInnerSquareXYZ it = new OuterToInnerSquareXYZ(0, 0);
			Assert.assertEquals(1, it.half_xz); Assert.assertEquals(1, it.size_xz); Assert.assertEquals(1, it.size_y);
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(0, 0, 0), it.next()); // 0
			Assert.assertFalse(it.hasNext());
		}
		// size 1
		{
			final OuterToInnerSquareXYZ it = new OuterToInnerSquareXYZ(1, 2);
			Assert.assertEquals(1, it.half_xz); Assert.assertEquals(1, it.size_xz); Assert.assertEquals(2, it.size_y);
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(0, 0, 0), it.next()); // 0
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(0, 1, 0), it.next()); // 0
			Assert.assertFalse(it.hasNext());
		}
		// size 2
		{
			final OuterToInnerSquareXYZ it = new OuterToInnerSquareXYZ(2, 2);
			Assert.assertEquals(2, it.half_xz); Assert.assertEquals(3, it.size_xz); Assert.assertEquals(2, it.size_y);
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 0, -1), it.next()); // 0
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 1, -1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 0, -1), it.next()); // 1
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 1, -1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 0, -1), it.next()); // 2
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 1, -1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 0,  0), it.next()); // 3
			it.nextXZ();
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 0,  1), it.next()); // 4
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 1,  1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 0,  1), it.next()); // 5
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 1,  1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 0,  1), it.next()); // 6
			it.nextXZ();
			it.nextXZ();
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 0,  0), it.next()); // 8
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 1,  0), it.next());
			Assert.assertFalse(it.hasNext());
		}
		// size 3
		{
			final OuterToInnerSquareXYZ it = new OuterToInnerSquareXYZ(3, 2);
			Assert.assertEquals(2, it.half_xz); Assert.assertEquals(3, it.size_xz); Assert.assertEquals(2, it.size_y);
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 0, -1), it.next()); // 0
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 1, -1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 0, -1), it.next()); // 1
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 1, -1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 0, -1), it.next()); // 2
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 1, -1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 0,  0), it.next()); // 3
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 1,  0), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 0,  1), it.next()); // 4
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 1,  1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 0,  1), it.next()); // 5
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 1,  1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 0,  1), it.next()); // 6
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 1,  1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 0,  0), it.next()); // 7
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 1,  0), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 0,  0), it.next()); // 8
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 1,  0), it.next());
			Assert.assertFalse(it.hasNext());
		}
	}



	@Test
	public void testInnerToOuterXYZ() {
		// size 0
		{
			final InnerToOuterSquareXYZ it = new InnerToOuterSquareXYZ(0, 0);
			Assert.assertEquals(1, it.half_xz); Assert.assertEquals(1, it.size_xz); Assert.assertEquals(1, it.size_y);
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(0, 0, 0), it.next()); // 0
			Assert.assertFalse(it.hasNext());
		}
		// size 1
		{
			final InnerToOuterSquareXYZ it = new InnerToOuterSquareXYZ(1, 2);
			Assert.assertEquals(1, it.half_xz); Assert.assertEquals(1, it.size_xz); Assert.assertEquals(2, it.size_y);
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(0, 0, 0), it.next()); // 0
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(0, 1, 0), it.next());
			Assert.assertFalse(it.hasNext());
		}
		// size 2
		{
			final InnerToOuterSquareXYZ it = new InnerToOuterSquareXYZ(2, 2);
			Assert.assertEquals(2, it.half_xz); Assert.assertEquals(3, it.size_xz); Assert.assertEquals(2, it.size_y);
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 0,  0), it.next()); // 0
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 1,  0), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 0, -1), it.next()); // 1
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 1, -1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 0, -1), it.next()); // 2
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 1, -1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 0, -1), it.next()); // 3
			it.nextXZ();
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 0,  0), it.next()); // 4
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 1,  0), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 0,  1), it.next()); // 5
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 1,  1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 0,  1), it.next()); // 6
			it.nextXZ();
			it.nextXZ();
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 0,  0), it.next()); // 8
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 1,  0), it.next());
			Assert.assertFalse(it.hasNext());
		}
		// size 3
		{
			final InnerToOuterSquareXYZ it = new InnerToOuterSquareXYZ(3, 2);
			Assert.assertEquals(2, it.half_xz); Assert.assertEquals(3, it.size_xz); Assert.assertEquals(2, it.size_y);
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 0,  0), it.next()); // 0
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 1,  0), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 0, -1), it.next()); // 1
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 1, -1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 0, -1), it.next()); // 2
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 1, -1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 0, -1), it.next()); // 3
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 1, -1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 0,  0), it.next()); // 4
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 1,  0), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 0,  1), it.next()); // 5
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 1,  1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 0,  1), it.next()); // 6
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 1,  1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 0,  1), it.next()); // 7
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 1,  1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 0,  0), it.next()); // 8
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 1,  0), it.next());
			Assert.assertFalse(it.hasNext());
		}
		// size 5
		{
			final InnerToOuterSquareXYZ it = new InnerToOuterSquareXYZ(5, 2);
			Assert.assertEquals(3, it.half_xz); Assert.assertEquals(5, it.size_xz); Assert.assertEquals(2, it.size_y);
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 0,  0), it.next()); // 0
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 1,  0), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 0, -1), it.next()); // 1
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 1, -1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 0, -1), it.next()); // 2
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 1, -1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 0, -1), it.next()); // 3
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 1, -1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 0,  0), it.next()); // 4
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 1,  0), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 0,  1), it.next()); // 5
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 1,  1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 0,  1), it.next()); // 6
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 1,  1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 0,  1), it.next()); // 7
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 1,  1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 0,  0), it.next()); // 8
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 1,  0), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-2, 0, -2), it.next()); // 9
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-2, 1, -2), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 0, -2), it.next()); // 10
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 1, -2), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 0, -2), it.next()); // 11
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 1, -2), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 0, -2), it.next()); // 12
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 1, -2), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 2, 0, -2), it.next()); // 13
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 2, 1, -2), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 2, 0, -1), it.next()); // 14
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 2, 1, -1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 2, 0,  0), it.next()); // 15
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 2, 1,  0), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 2, 0,  1), it.next()); // 16
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 2, 1,  1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 2, 0,  2), it.next()); // 17
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 2, 1,  2), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 0,  2), it.next()); // 18
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 1, 1,  2), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 0,  2), it.next()); // 19
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc( 0, 1,  2), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 0,  2), it.next()); // 20
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-1, 1,  2), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-2, 0,  2), it.next()); // 21
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-2, 1,  2), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-2, 0,  1), it.next()); // 22
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-2, 1,  1), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-2, 0,  0), it.next()); // 23
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-2, 1,  0), it.next());
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-2, 0, -1), it.next()); // 24
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iabc(-2, 1, -1), it.next());
			Assert.assertFalse(it.hasNext());
		}
	}



}
