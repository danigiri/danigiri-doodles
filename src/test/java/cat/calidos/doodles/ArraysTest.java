package cat.calidos.doodles;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cat.calidos.doodles.builders.ArrayFrom;

public class ArraysTest {


@Test @DisplayName("Rotate in place")
public void testRotateInPlace() {

	List<Integer> a = ArrayFrom.ints(1, 2, 3, 4);
	Arrays.rotateInPlace(ArrayFrom.ints(1, 2, 3, 4), 0);
	assertEquals(a, a);

	a = ArrayFrom.ints(1, 2, 3, 4);
	Arrays.rotateInPlace(a, 1);
	assertEquals(ArrayFrom.ints(2, 3, 4, 1), a);

	a = ArrayFrom.ints(1, 2, 3, 4);
	Arrays.rotateInPlace(a, 4);
	assertEquals(ArrayFrom.ints(1, 2, 3, 4),  a);

	a = ArrayFrom.ints(1, 2, 3, 4);
	Arrays.rotateInPlace(a, 2);
	assertEquals(ArrayFrom.ints(3, 4, 1, 2), a);

}


@Test @DisplayName("Tic tac toe winner")
public void testTicTacToe() {

	Character[][] board = {	{null, null, null},
							{null, null, null},
							{null, null, null}
	};
	assertTrue(Arrays.ticTacToeWinner(board).isEmpty());


	Character[][] board2 = {{'a', null, null},
							{null,'b',  null},
							{null, null, 'a'}
	};
	assertTrue(Arrays.ticTacToeWinner(board2).isEmpty());

	Character[][] board3 = {{'a', null, null},
							{'b', 'b',  'b'},
							{null,null, 'a'}
	};
	assertEquals('b', Arrays.ticTacToeWinner(board3).get());

	Character[][] board4 = {{'a', null, 'b'},
							{'a', 'b',  'b'},
							{null,null, 'b'}
	};
	assertEquals('b', Arrays.ticTacToeWinner(board4).get());

}


@Test @DisplayName("Tic tac toe diagonals winner")
public void testTicTacToeDiagonals() {

	Character[][] board = {	{'b', null, 'b'},
							{null, 'b', null},
							{null, 'a', 'b'}
	};
	assertEquals('b', Arrays.ticTacToeWinner(board).get());


	Character[][] board2 = {	{'a', null, 'b'},
								{null, 'b', null},
								{'b', null, 'a'}
	};
	assertEquals('b', Arrays.ticTacToeWinner(board2).get());

}


@Test @DisplayName("Min distance test")
public void minDistanceTest() {

	List<Integer> a = ArrayFrom.ints(1, 2, 3, 4, 66);
	List<Integer> b = ArrayFrom.ints(9, 6 ,7);
	assertEquals(2, Arrays.smallestDiff(a, b));

	a = ArrayFrom.ints(1);
	b = ArrayFrom.ints(1);
	assertEquals(0, Arrays.smallestDiff(a, b));

	a = ArrayFrom.ints(1);
	b = ArrayFrom.ints(2);
	assertEquals(1, Arrays.smallestDiff(a, b));

	a = ArrayFrom.ints(1, 1, 1, 1, 1, 1);
	b = ArrayFrom.ints(2);
	assertEquals(1, Arrays.smallestDiff(a, b));

	a = ArrayFrom.ints(5, 4, 3, 1, 66);
	b = ArrayFrom.ints(55, 2);
	assertEquals(1, Arrays.smallestDiff(a, b));

}


@Test @DisplayName("Magic index test")
public void magicIndexTest() {

	List<Integer> a = ArrayFrom.ints(0);
	assertEquals(0, Arrays.magicIndex(a).get());

	a = ArrayFrom.ints(-1);
	assertTrue(Arrays.magicIndex(a).isEmpty());

	a = ArrayFrom.ints(-1, 0);
	assertTrue(Arrays.magicIndex(a).isEmpty());

	a = ArrayFrom.ints(-1, 0, 1);
	assertTrue(Arrays.magicIndex(a).isEmpty());

	a = ArrayFrom.ints(-1, 0, 1, 3);
	assertEquals(3, Arrays.magicIndex(a).get());

	a = ArrayFrom.ints(0, 5, 6, 7);
	assertEquals(0, Arrays.magicIndex(a).get());

}


@Test @DisplayName("Find element in rotated sorted array")
public void findInRotatedArrayTest() {

	List<Integer> a = ArrayFrom.ints(3, 4, 5, 6, 7, 9, 11, 1, 2);

	assertTrue(Arrays.findInRotatedArray(a, 8).isEmpty());
	
}


}

/*
 *    Copyright 2020 Daniel Giribet
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

