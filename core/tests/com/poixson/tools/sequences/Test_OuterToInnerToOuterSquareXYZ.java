package com.poixson.tools.sequences;

import static com.poixson.tools.Assertions.AssertEquals;
import static com.poixson.tools.Assertions.AssertFalse;
import static com.poixson.tools.Assertions.AssertTrue;

import org.junit.jupiter.api.Test;

import com.poixson.tools.dao.Iabc;


public class Test_OuterToInnerToOuterSquareXYZ {



	@Test
	public void testOuterToInnerXYZ() {
		// size 0
		{
			final OuterToInnerSquareXYZ it = new OuterToInnerSquareXYZ(0, 0);
			AssertEquals(1, it.half_xz); AssertEquals(1, it.size_xz); AssertEquals(1, it.size_y);
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(0, 0, 0), it.next()); // 0
			AssertFalse(it.hasNext());
		}
		// size 1
		{
			final OuterToInnerSquareXYZ it = new OuterToInnerSquareXYZ(1, 2);
			AssertEquals(1, it.half_xz); AssertEquals(1, it.size_xz); AssertEquals(2, it.size_y);
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(0, 0, 0), it.next()); // 0
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(0, 1, 0), it.next()); // 0
			AssertFalse(it.hasNext());
		}
		// size 2
		{
			final OuterToInnerSquareXYZ it = new OuterToInnerSquareXYZ(2, 2);
			AssertEquals(2, it.half_xz); AssertEquals(3, it.size_xz); AssertEquals(2, it.size_y);
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 0, -1), it.next()); // 0
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 1, -1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 0, -1), it.next()); // 1
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 1, -1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 0, -1), it.next()); // 2
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 1, -1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 0,  0), it.next()); // 3
			it.nextXZ();
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 0,  1), it.next()); // 4
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 1,  1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 0,  1), it.next()); // 5
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 1,  1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 0,  1), it.next()); // 6
			it.nextXZ();
			it.nextXZ();
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 0,  0), it.next()); // 8
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 1,  0), it.next());
			AssertFalse(it.hasNext());
		}
		// size 3
		{
			final OuterToInnerSquareXYZ it = new OuterToInnerSquareXYZ(3, 2);
			AssertEquals(2, it.half_xz); AssertEquals(3, it.size_xz); AssertEquals(2, it.size_y);
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 0, -1), it.next()); // 0
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 1, -1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 0, -1), it.next()); // 1
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 1, -1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 0, -1), it.next()); // 2
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 1, -1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 0,  0), it.next()); // 3
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 1,  0), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 0,  1), it.next()); // 4
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 1,  1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 0,  1), it.next()); // 5
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 1,  1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 0,  1), it.next()); // 6
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 1,  1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 0,  0), it.next()); // 7
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 1,  0), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 0,  0), it.next()); // 8
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 1,  0), it.next());
			AssertFalse(it.hasNext());
		}
	}



	@Test
	public void testInnerToOuterXYZ() {
		// size 0
		{
			final InnerToOuterSquareXYZ it = new InnerToOuterSquareXYZ(0, 0);
			AssertEquals(1, it.half_xz); AssertEquals(1, it.size_xz); AssertEquals(1, it.size_y);
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(0, 0, 0), it.next()); // 0
			AssertFalse(it.hasNext());
		}
		// size 1
		{
			final InnerToOuterSquareXYZ it = new InnerToOuterSquareXYZ(1, 2);
			AssertEquals(1, it.half_xz); AssertEquals(1, it.size_xz); AssertEquals(2, it.size_y);
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(0, 0, 0), it.next()); // 0
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(0, 1, 0), it.next());
			AssertFalse(it.hasNext());
		}
		// size 2
		{
			final InnerToOuterSquareXYZ it = new InnerToOuterSquareXYZ(2, 2);
			AssertEquals(2, it.half_xz); AssertEquals(3, it.size_xz); AssertEquals(2, it.size_y);
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 0,  0), it.next()); // 0
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 1,  0), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 0, -1), it.next()); // 1
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 1, -1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 0, -1), it.next()); // 2
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 1, -1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 0, -1), it.next()); // 3
			it.nextXZ();
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 0,  0), it.next()); // 4
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 1,  0), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 0,  1), it.next()); // 5
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 1,  1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 0,  1), it.next()); // 6
			it.nextXZ();
			it.nextXZ();
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 0,  0), it.next()); // 8
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 1,  0), it.next());
			AssertFalse(it.hasNext());
		}
		// size 3
		{
			final InnerToOuterSquareXYZ it = new InnerToOuterSquareXYZ(3, 2);
			AssertEquals(2, it.half_xz); AssertEquals(3, it.size_xz); AssertEquals(2, it.size_y);
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 0,  0), it.next()); // 0
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 1,  0), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 0, -1), it.next()); // 1
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 1, -1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 0, -1), it.next()); // 2
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 1, -1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 0, -1), it.next()); // 3
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 1, -1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 0,  0), it.next()); // 4
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 1,  0), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 0,  1), it.next()); // 5
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 1,  1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 0,  1), it.next()); // 6
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 1,  1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 0,  1), it.next()); // 7
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 1,  1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 0,  0), it.next()); // 8
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 1,  0), it.next());
			AssertFalse(it.hasNext());
		}
		// size 5
		{
			final InnerToOuterSquareXYZ it = new InnerToOuterSquareXYZ(5, 2);
			AssertEquals(3, it.half_xz); AssertEquals(5, it.size_xz); AssertEquals(2, it.size_y);
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 0,  0), it.next()); // 0
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 1,  0), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 0, -1), it.next()); // 1
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 1, -1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 0, -1), it.next()); // 2
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 1, -1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 0, -1), it.next()); // 3
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 1, -1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 0,  0), it.next()); // 4
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 1,  0), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 0,  1), it.next()); // 5
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 1,  1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 0,  1), it.next()); // 6
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 1,  1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 0,  1), it.next()); // 7
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 1,  1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 0,  0), it.next()); // 8
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 1,  0), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-2, 0, -2), it.next()); // 9
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-2, 1, -2), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 0, -2), it.next()); // 10
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 1, -2), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 0, -2), it.next()); // 11
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 1, -2), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 0, -2), it.next()); // 12
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 1, -2), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 2, 0, -2), it.next()); // 13
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 2, 1, -2), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 2, 0, -1), it.next()); // 14
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 2, 1, -1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 2, 0,  0), it.next()); // 15
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 2, 1,  0), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 2, 0,  1), it.next()); // 16
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 2, 1,  1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 2, 0,  2), it.next()); // 17
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 2, 1,  2), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 0,  2), it.next()); // 18
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 1, 1,  2), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 0,  2), it.next()); // 19
			AssertTrue(it.hasNext()); AssertEquals(new Iabc( 0, 1,  2), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 0,  2), it.next()); // 20
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-1, 1,  2), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-2, 0,  2), it.next()); // 21
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-2, 1,  2), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-2, 0,  1), it.next()); // 22
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-2, 1,  1), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-2, 0,  0), it.next()); // 23
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-2, 1,  0), it.next());
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-2, 0, -1), it.next()); // 24
			AssertTrue(it.hasNext()); AssertEquals(new Iabc(-2, 1, -1), it.next());
			AssertFalse(it.hasNext());
		}
	}



}
