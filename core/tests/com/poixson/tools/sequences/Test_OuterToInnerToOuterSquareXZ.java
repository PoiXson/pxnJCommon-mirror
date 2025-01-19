package com.poixson.tools.sequences;

import static com.poixson.tools.Assertions.AssertEquals;
import static com.poixson.tools.Assertions.AssertFalse;
import static com.poixson.tools.Assertions.AssertTrue;

import org.junit.jupiter.api.Test;

import com.poixson.tools.dao.Iab;


public class Test_OuterToInnerToOuterSquareXZ {



	@Test
	public void testOuterToInnerXZ() {
		// size 0
		{
			final OuterToInnerSquareXZ it = new OuterToInnerSquareXZ(0);
			AssertEquals(1, it.half); AssertEquals(1, it.size);
			AssertTrue(it.hasNext()); AssertEquals(new Iab(0, 0), it.next()); // 0
			AssertFalse(it.hasNext());
		}
		// size 1
		{
			final OuterToInnerSquareXZ it = new OuterToInnerSquareXZ(1);
			AssertEquals(1, it.half); AssertEquals(1, it.size);
			AssertTrue(it.hasNext()); AssertEquals(new Iab(0, 0), it.next()); // 0
			AssertFalse(it.hasNext());
		}
		// size 2
		{
			final OuterToInnerSquareXZ it = new OuterToInnerSquareXZ(2);
			AssertEquals(2, it.half); AssertEquals(3, it.size);
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-1, -1), it.next()); // 0
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 0, -1), it.next()); // 1
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 1, -1), it.next()); // 2
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 1,  0), it.next()); // 3
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 1,  1), it.next()); // 4
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 0,  1), it.next()); // 5
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-1,  1), it.next()); // 6
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-1,  0), it.next()); // 7
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 0,  0), it.next()); // 8
			AssertFalse(it.hasNext());
		}
		// size 3
		{
			final OuterToInnerSquareXZ it = new OuterToInnerSquareXZ(3);
			AssertEquals(2, it.half); AssertEquals(3, it.size);
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-1, -1), it.next()); // 0
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 0, -1), it.next()); // 1
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 1, -1), it.next()); // 2
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 1,  0), it.next()); // 3
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 1,  1), it.next()); // 4
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 0,  1), it.next()); // 5
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-1,  1), it.next()); // 6
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-1,  0), it.next()); // 7
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 0,  0), it.next()); // 8
			AssertFalse(it.hasNext());
		}
		// size 5
		{
			final OuterToInnerSquareXZ it = new OuterToInnerSquareXZ(5);
			AssertEquals(3, it.half); AssertEquals(5, it.size);
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-2, -2), it.next()); // 0
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-1, -2), it.next()); // 1
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 0, -2), it.next()); // 2
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 1, -2), it.next()); // 3
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 2, -2), it.next()); // 4
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 2, -1), it.next()); // 5
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 2,  0), it.next()); // 6
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 2,  1), it.next()); // 7
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 2,  2), it.next()); // 8
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 1,  2), it.next()); // 9
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 0,  2), it.next()); // 10
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-1,  2), it.next()); // 11
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-2,  2), it.next()); // 12
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-2,  1), it.next()); // 13
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-2,  0), it.next()); // 14
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-2, -1), it.next()); // 15
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-1, -1), it.next()); // 16
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 0, -1), it.next()); // 17
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 1, -1), it.next()); // 18
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 1,  0), it.next()); // 19
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 1,  1), it.next()); // 20
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 0,  1), it.next()); // 21
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-1,  1), it.next()); // 22
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-1,  0), it.next()); // 23
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 0,  0), it.next()); // 24
			AssertFalse(it.hasNext());
		}
	}



	@Test
	public void testInnerToOuterXZ() {
		// size 0
		{
			final InnerToOuterSquareXZ it = new InnerToOuterSquareXZ(0);
			AssertEquals(1, it.half); AssertEquals(1, it.size);
			AssertTrue(it.hasNext()); AssertEquals(new Iab(0, 0), it.next()); // 0
			AssertFalse(it.hasNext());
		}
		// size 1
		{
			final InnerToOuterSquareXZ it = new InnerToOuterSquareXZ(1);
			AssertEquals(1, it.half); AssertEquals(1, it.size);
			AssertTrue(it.hasNext()); AssertEquals(new Iab(0, 0), it.next()); // 0
			AssertFalse(it.hasNext());
		}
		// size 2
		{
			final InnerToOuterSquareXZ it = new InnerToOuterSquareXZ(2);
			AssertEquals(2, it.half); AssertEquals(3, it.size);
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 0,  0), it.next()); // 0
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-1, -1), it.next()); // 1
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 0, -1), it.next()); // 2
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 1, -1), it.next()); // 3
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 1,  0), it.next()); // 4
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 1,  1), it.next()); // 5
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 0,  1), it.next()); // 6
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-1,  1), it.next()); // 7
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-1,  0), it.next()); // 8
			AssertFalse(it.hasNext());
		}
		// size 3
		{
			final InnerToOuterSquareXZ it = new InnerToOuterSquareXZ(3);
			AssertEquals(2, it.half); AssertEquals(3, it.size);
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 0,  0), it.next()); // 0
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-1, -1), it.next()); // 1
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 0, -1), it.next()); // 2
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 1, -1), it.next()); // 3
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 1,  0), it.next()); // 4
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 1,  1), it.next()); // 5
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 0,  1), it.next()); // 6
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-1,  1), it.next()); // 7
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-1,  0), it.next()); // 8
			AssertFalse(it.hasNext());
		}
		// size 5
		{
			final InnerToOuterSquareXZ it = new InnerToOuterSquareXZ(5);
			AssertEquals(3, it.half); AssertEquals(5, it.size);
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 0,  0), it.next()); // 0
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-1, -1), it.next()); // 1
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 0, -1), it.next()); // 2
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 1, -1), it.next()); // 3
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 1,  0), it.next()); // 4
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 1,  1), it.next()); // 5
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 0,  1), it.next()); // 6
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-1,  1), it.next()); // 7
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-1,  0), it.next()); // 8
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-2, -2), it.next()); // 9
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-1, -2), it.next()); // 10
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 0, -2), it.next()); // 11
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 1, -2), it.next()); // 12
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 2, -2), it.next()); // 13
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 2, -1), it.next()); // 14
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 2,  0), it.next()); // 15
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 2,  1), it.next()); // 16
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 2,  2), it.next()); // 17
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 1,  2), it.next()); // 18
			AssertTrue(it.hasNext()); AssertEquals(new Iab( 0,  2), it.next()); // 19
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-1,  2), it.next()); // 20
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-2,  2), it.next()); // 21
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-2,  1), it.next()); // 22
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-2,  0), it.next()); // 23
			AssertTrue(it.hasNext()); AssertEquals(new Iab(-2, -1), it.next()); // 24
			AssertFalse(it.hasNext());
		}
	}



}
