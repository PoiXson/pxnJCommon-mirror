package com.poixson.tools.sequences;

import org.junit.Assert;
import org.junit.Test;

import com.poixson.tools.dao.Iab;


public class Test_OuterToInnerToOuterSquareXZ {



	@Test
	public void testOuterToInnerXZ() {
		// size 0
		{
			final OuterToInnerSquareXZ it = new OuterToInnerSquareXZ(0);
			Assert.assertEquals(1, it.half); Assert.assertEquals(1, it.size);
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(0, 0), it.next()); // 0
			Assert.assertFalse(it.hasNext());
		}
		// size 1
		{
			final OuterToInnerSquareXZ it = new OuterToInnerSquareXZ(1);
			Assert.assertEquals(1, it.half); Assert.assertEquals(1, it.size);
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(0, 0), it.next()); // 0
			Assert.assertFalse(it.hasNext());
		}
		// size 2
		{
			final OuterToInnerSquareXZ it = new OuterToInnerSquareXZ(2);
			Assert.assertEquals(2, it.half); Assert.assertEquals(3, it.size);
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-1, -1), it.next()); // 0
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 0, -1), it.next()); // 1
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 1, -1), it.next()); // 2
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 1,  0), it.next()); // 3
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 1,  1), it.next()); // 4
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 0,  1), it.next()); // 5
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-1,  1), it.next()); // 6
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-1,  0), it.next()); // 7
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 0,  0), it.next()); // 8
			Assert.assertFalse(it.hasNext());
		}
		// size 3
		{
			final OuterToInnerSquareXZ it = new OuterToInnerSquareXZ(3);
			Assert.assertEquals(2, it.half); Assert.assertEquals(3, it.size);
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-1, -1), it.next()); // 0
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 0, -1), it.next()); // 1
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 1, -1), it.next()); // 2
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 1,  0), it.next()); // 3
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 1,  1), it.next()); // 4
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 0,  1), it.next()); // 5
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-1,  1), it.next()); // 6
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-1,  0), it.next()); // 7
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 0,  0), it.next()); // 8
			Assert.assertFalse(it.hasNext());
		}
		// size 5
		{
			final OuterToInnerSquareXZ it = new OuterToInnerSquareXZ(5);
			Assert.assertEquals(3, it.half); Assert.assertEquals(5, it.size);
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-2, -2), it.next()); // 0
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-1, -2), it.next()); // 1
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 0, -2), it.next()); // 2
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 1, -2), it.next()); // 3
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 2, -2), it.next()); // 4
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 2, -1), it.next()); // 5
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 2,  0), it.next()); // 6
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 2,  1), it.next()); // 7
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 2,  2), it.next()); // 8
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 1,  2), it.next()); // 9
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 0,  2), it.next()); // 10
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-1,  2), it.next()); // 11
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-2,  2), it.next()); // 12
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-2,  1), it.next()); // 13
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-2,  0), it.next()); // 14
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-2, -1), it.next()); // 15
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-1, -1), it.next()); // 16
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 0, -1), it.next()); // 17
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 1, -1), it.next()); // 18
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 1,  0), it.next()); // 19
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 1,  1), it.next()); // 20
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 0,  1), it.next()); // 21
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-1,  1), it.next()); // 22
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-1,  0), it.next()); // 23
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 0,  0), it.next()); // 24
			Assert.assertFalse(it.hasNext());
		}
	}



	@Test
	public void testInnerToOuterXZ() {
		// size 0
		{
			final InnerToOuterSquareXZ it = new InnerToOuterSquareXZ(0);
			Assert.assertEquals(1, it.half); Assert.assertEquals(1, it.size);
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(0, 0), it.next()); // 0
			Assert.assertFalse(it.hasNext());
		}
		// size 1
		{
			final InnerToOuterSquareXZ it = new InnerToOuterSquareXZ(1);
			Assert.assertEquals(1, it.half); Assert.assertEquals(1, it.size);
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(0, 0), it.next()); // 0
			Assert.assertFalse(it.hasNext());
		}
		// size 2
		{
			final InnerToOuterSquareXZ it = new InnerToOuterSquareXZ(2);
			Assert.assertEquals(2, it.half); Assert.assertEquals(3, it.size);
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 0,  0), it.next()); // 0
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-1, -1), it.next()); // 1
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 0, -1), it.next()); // 2
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 1, -1), it.next()); // 3
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 1,  0), it.next()); // 4
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 1,  1), it.next()); // 5
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 0,  1), it.next()); // 6
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-1,  1), it.next()); // 7
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-1,  0), it.next()); // 8
			Assert.assertFalse(it.hasNext());
		}
		// size 3
		{
			final InnerToOuterSquareXZ it = new InnerToOuterSquareXZ(3);
			Assert.assertEquals(2, it.half); Assert.assertEquals(3, it.size);
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 0,  0), it.next()); // 0
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-1, -1), it.next()); // 1
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 0, -1), it.next()); // 2
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 1, -1), it.next()); // 3
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 1,  0), it.next()); // 4
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 1,  1), it.next()); // 5
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 0,  1), it.next()); // 6
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-1,  1), it.next()); // 7
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-1,  0), it.next()); // 8
			Assert.assertFalse(it.hasNext());
		}
		// size 5
		{
			final InnerToOuterSquareXZ it = new InnerToOuterSquareXZ(5);
			Assert.assertEquals(3, it.half); Assert.assertEquals(5, it.size);
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 0,  0), it.next()); // 0
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-1, -1), it.next()); // 1
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 0, -1), it.next()); // 2
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 1, -1), it.next()); // 3
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 1,  0), it.next()); // 4
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 1,  1), it.next()); // 5
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 0,  1), it.next()); // 6
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-1,  1), it.next()); // 7
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-1,  0), it.next()); // 8
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-2, -2), it.next()); // 9
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-1, -2), it.next()); // 10
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 0, -2), it.next()); // 11
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 1, -2), it.next()); // 12
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 2, -2), it.next()); // 13
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 2, -1), it.next()); // 14
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 2,  0), it.next()); // 15
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 2,  1), it.next()); // 16
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 2,  2), it.next()); // 17
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 1,  2), it.next()); // 18
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab( 0,  2), it.next()); // 19
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-1,  2), it.next()); // 20
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-2,  2), it.next()); // 21
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-2,  1), it.next()); // 22
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-2,  0), it.next()); // 23
			Assert.assertTrue(it.hasNext()); Assert.assertEquals(new Iab(-2, -1), it.next()); // 24
			Assert.assertFalse(it.hasNext());
		}
	}



}
