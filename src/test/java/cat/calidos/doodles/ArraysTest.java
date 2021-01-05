package cat.calidos.doodles;

import static org.junit.jupiter.api.Assertions.*;

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


	Character[][] board2 = {	{'a', null, null},
								{null, 'b', null},
								{null, null, 'a'}
	};
	assertTrue(Arrays.ticTacToeWinner(board2).isEmpty());

	Character[][] board3 = {	{'a', null, null},
								{'b', 'b', 'b'},
								{null, null, 'a'}
	};
	assertEquals('b', Arrays.ticTacToeWinner(board3).get());

	Character[][] board4 = {	{'a', null, 'b'},
								{'a', 'b', 'b'},
								{null, null, 'b'}
	};
	assertEquals('b', Arrays.ticTacToeWinner(board3).get());
}


}


