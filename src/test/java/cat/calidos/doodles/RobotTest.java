// ROBOT TEST . JAVA

package cat.calidos.doodles;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RobotTest {

	@Test
	@DisplayName("Test possible paths in grid")
	public void testPathsInSquareGrid() {

		assertEquals(0, Robot.pathsInSquareGrid(0));

		assertEquals(0, Robot.pathsInSquareGrid(1)); // FIXME: this should be 1

		assertEquals(4, Robot.pathsInSquareGrid(2));

		assertEquals(18, Robot.pathsInSquareGrid(3));

	}

	@Test
	@DisplayName("Test possible paths in grid with obstacles")
	public void testPathsInSquareGridWithObstacles() {

		boolean[][] o0 = { {}, {} };

		assertEquals(0, Robot.pathsInSquareGridWithObstacles(0, o0));

		Exception e = null;
		try {
			Robot.pathsInSquareGridWithObstacles(1, o0);
		} catch (RuntimeException e1) {
			e = e1;
		}

		assertNotNull(e);
		assertTrue(e instanceof RuntimeException);
		assertEquals("Mismatched obstacle structure differs in size from grid", e.getMessage());

		boolean[][] o1 = { { true } };
		assertEquals(0, Robot.pathsInSquareGridWithObstacles(1, o1));

		boolean[][] o2 = { { true, false }, { false, false } };
		assertEquals(0, Robot.pathsInSquareGridWithObstacles(2, o2));

		boolean[][] o3 = { { false, false }, { false, false } };
		assertEquals(4, Robot.pathsInSquareGridWithObstacles(2, o3));

		boolean[][] o4 = { { false, false }, { false, true } };
		assertEquals(2, Robot.pathsInSquareGridWithObstacles(2, o4));

		boolean[][] o5 = { { false, true }, { false, false } };
		assertEquals(2, Robot.pathsInSquareGridWithObstacles(2, o5));

		boolean[][] o6 = { { false, false, false }, { false, false, false }, { true, false, false } };
		assertEquals(15, Robot.pathsInSquareGridWithObstacles(3, o6));

	}

	@Test
	@DisplayName("Test possible non stop path in square grid")
	public void testNonStopPathsInSquareGrid() {

		assertEquals(0, Robot.nonStopPathsInSquareGrid(0));

		assertEquals(1, Robot.nonStopPathsInSquareGrid(1));

		assertEquals(2, Robot.nonStopPathsInSquareGrid(2));

		assertEquals(6, Robot.nonStopPathsInSquareGrid(3));

		// the six possible paths:
		// --- --. |.. |.. |.. --.
		// ..| .|. |.. --- -|. .|-
		// ..x .-x --x ..x .-x ..x

	}

	@Test
	@DisplayName("Test find path in grid with obstacles")
	public void testMaze() {

		boolean[][] m1 = { { false, false, false }, { false, true, false }, { false, true, false } }; // bottom left is
																										// a dead end

		java.util.LinkedList<String> path = Robot.mazePath(m1, m1.length, m1[0].length);

		java.util.LinkedList<String> expected = new java.util.LinkedList<String>();
		expected.add(Robot.RIGHT);
		expected.add(Robot.RIGHT);
		expected.add(Robot.DOWN);
		expected.add(Robot.DOWN);
		assertAll("path checks", () -> assertNotNull(path), () -> assertEquals(4, path.size()),
				() -> assertEquals(expected, path));

	}

	@Test
	@DisplayName("Test find path in grid with obstacles, but clear of them")
	public void testClearMaze() {

		boolean[][] m1 = { { false, false, false }, { false, false, false }, { false, false, false } }; // all clear

		java.util.LinkedList<String> path = Robot.mazePath(m1, m1.length, m1[0].length);

		java.util.LinkedList<String> expected = new java.util.LinkedList<String>();
		expected.add(Robot.DOWN);
		expected.add(Robot.DOWN);
		expected.add(Robot.RIGHT);
		expected.add(Robot.RIGHT);
		assertAll("path checks", () -> assertNotNull(path), () -> assertEquals(4, path.size()),
				() -> assertEquals(expected, path));

	}

	@Test
	@DisplayName("Test pond counter")
	public void testPonds() {

		int[][] land = { { 0, 2, 1, 0 }, { 0, 1, 0, 1 }, { 1, 1, 0, 1 }, { 0, 1, 0, 1 }, };

		List<Integer> ponds = Robot.ponds(land);
		assertAll("check pond sizes", 
			() -> assertNotNull(ponds),
			() -> assertEquals(3, ponds.size()),
			() -> assertTrue(ponds.contains(2)),
			() -> assertTrue(ponds.contains(4)),
			() -> assertTrue(ponds.contains(1))
		);

	}


	@Test
	@DisplayName("SImplify directions test")
	public void testSimpleDirReduc() {
		assertArrayEquals("\"NORTH\", \"SOUTH\", \"SOUTH\", \"EAST\", \"WEST\", \"NORTH\", \"WEST\"",
			new String[] { "WEST" },
			Robot.dirReduc(new String[] { "NORTH", "SOUTH", "SOUTH", "EAST", "WEST", "NORTH", "WEST" }));
		assertArrayEquals("\"NORTH\",\"SOUTH\",\"SOUTH\",\"EAST\",\"WEST\",\"NORTH\"", new String[] {},
			Robot.dirReduc(new String[] { "NORTH", "SOUTH", "SOUTH", "EAST", "WEST", "NORTH" }));
	}

	@Test
	@DisplayName("Snail walk test")
	public void testSnail() {
		int[][] array = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
		int[] r = { 1, 2, 3, 6, 9, 8, 7, 4, 5 };
		assertArrayEquals(r, Robot.snail(array));
		int[][] array2 = { { 1} };
		int[] r1 = { 1 };
		assertArrayEquals(r1, Robot.snail(array2));
	}

}

/**
 Copyright 2024 Daniel Giribet <dani - calidos.cat>

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
*/
