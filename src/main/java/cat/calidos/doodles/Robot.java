package cat.calidos.doodles;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class Robot {


/**	Given a grid of nxn, and a robot that can go right and down, compute how many
*	different paths the robot can take, taking into account the robot can stop at
*	any time (right and down in absolute coordinates and not from the POV of the
*	robot, otherwise it's a completely different algorithm)
* 	@param n size of the grid
* 	@return number of possible paths
*///////////////////////////////////////////////////////////////////////////////
public static int pathsInSquareGrid(int n) {

	// base cases
	if (n<0) {
		throw new IndexOutOfBoundsException("Cannot calculate paths for negative grid ("+n+")");
	} else if (n==0) {
		return 0;
	} else {
		return pathsInGrid(n-1, n-1);
	}

}

private static int pathsInGrid(int w, int h) {
	// preconditions: n>=0, m>=0
	
	// recursive cases (base cases are implict in the empty elses)
	int p = 0;
	if (w>0) {	// we can go right
		p = 1+pathsInGrid(w-1, h);
	}
	if (h>0) {	// we can go down
		p += 1+pathsInGrid(w, h-1);
	}
	return p;
	
}
 

public static int pathsInSquareGridWithObstacles(int n, boolean[][] obstacles) {

	// error checking
	if (n<0) {
		throw new IndexOutOfBoundsException("Cannot calculate paths for negative grid ("+n+")");
	} else if (n==0) {
		return 0;
	} else if (obstacles.length!=n || obstacles[0].length!=n) {
		throw new RuntimeException("Mismatched obstacle structure differs in size from grid");
	} else if (obstacles[0][0]) {
		return 0;
	} else {
		return pathsInGridWithObstacles(0, 0, n, obstacles);
	}

}

private static int pathsInGridWithObstacles(int i, int j, int n, boolean[][] obstacles) {
	// preconditions: n>=i>=0, n>=j>=0, w>0, h>0, sizeof(obstacles)==n*n,

	// base case
	if (obstacles[i][j]) {	// we hit an obstacle, backtrack
		return -1;			// could be optimised to check before call but this is
	}						// a bit more elegant and the collision logic is in one place

	// recursive cases, which are implicitly dealing with base cases on the empty else's
	int p = 0;
	if (i+1<n) {
		p = 1+pathsInGridWithObstacles(i+1, j, n, obstacles);
	}
	if (j+1<n) {
		p += 1+pathsInGridWithObstacles(i, j+1, n, obstacles);
	}
	return p;
}

/**	Given a grid of nxn, and a robot that can go right and down, compute how many
*	different paths the robot can take, *the robot only stops when has nowhere 
*	else to go (is at the bottom)*
* 	@param n size of the grid
* 	@return number of possible paths
*///////////////////////////////////////////////////////////////////////////////
public static int nonStopPathsInSquareGrid(int n) {
	// error checking
	if (n<0) {
		throw new IndexOutOfBoundsException("Cannot calculate nonstop paths for negative grid ("+n+")");
	} else if (n==0) {
		return 0;
	}
	return nonStopPathsInSquareGrid(0, 0, n);
}




private static int nonStopPathsInSquareGrid(int i, int j, int n) {
	// preconditions: 0<=i<n, 0<=j<n, n>0

	// base case (at the bottom, that’s one trip)
	// note that once we're at the bottow we can go down any more on this and
	// any possible paths, which means we go right to the end and count a +1
	if (j+1==n) {
		return 1;
	}
	
	int p = 0;
	
	// going right on all the row and on each cell we go down
	for (int iNext = i; iNext<n; iNext++) {
		p += nonStopPathsInSquareGrid(iNext, j+1, n);
	} 
	
	return p;
}

//8.2 imagine a robot in a grid, sitting in the left upper corner with r rows and c cols,
//robot can only move in right and down directions, but some cells are ‘off limits’
//design an algorithm to find a path to the bottom right
//		0        c-1
//		###########
//0		#x........#
//		#......#..#
//		#..##....z#
//r-1	###########

//create a stack/list of steps []
//create a list of depth first candidate positions, add 0,0
//create a matrix of boolean explored row cols
//while not finished and remaining candidates
//take first
//if we are at the end → finished
//otherwise, look at each candidate for next step (right and down):
//	x) if out of bounds, skip
//	a) if it has been explored, skip
//	b) if it has not been explored:
//		b.1) if it is not accessible: skip
//		b.2) if it is accessible:, add next steps to candidates
//			 add next step to explored, add to steps
//	if no steps can be made, remove top from the stack (backtracking)
//continue while
//
//if finished list is the steps
//if not finished, the maze has no solution

public static String RIGHT = "RIGHT";
public static String DOWN = "DOWN";


/**	@param maze cells are true if there is an obstacle
 * @return list of steps, [] if maze is not solvable
// */
//public static LinkedList<String> mazePath(boolean[][] maze, int r, int c) {
//
//	if (maze == null || r < 1 || c < 1) {
//		throw new IndexOutOfBoundsException("Invalid maze");
//	}
//
//	LinkedList<String> steps = new LinkedList<String>();
//	if (r == 1 && c == 1) {
//		return steps;
//	}
//	LinkedList<Integer> pendingR = new LinkedList<Integer>();
//	LinkedList<Integer> pendingC = new LinkedList<Integer>();
//	LinkedList<String> pendingSteps = new LinkedList<String>();
//
//	boolean[][] explored = new boolean[r][c];
//	for (int i = 0; i < r; i++) {
//		for (int j = 0; j < c; j++) {
//			explored[i][j] = false;
//		}
//	}
//
//	pendingR.add(1);
//	pendingC.add(0);
//	pendingSteps.add(DOWN);
//	pendingR.add(0);
//	pendingC.add(1);
//	pendingSteps.add(RIGHT);
//
//	boolean finished = false;
//	while (!finished && pendingSteps.size() > 0) {
//
//		int currentR = pendingR.poll();
//		int currentC = pendingC.poll();
//		String step = pendingSteps.poll();
//		steps.add(step);
//		explored[currentR][currentC] = true;
//		if (currentR == r - 1 && currentC == c - 1) {
//			finished = true;
//			continue;
//		}
//
//		boolean nextSteps = false;
//		// DOWN first and then right
//		int nextR = currentR + 1;
//		if (isValidStep(nextR, currentC, r, c, explored, maze)) {
//			nextSteps = true;
//			pendingR.addFirst(nextR);
//			pendingC.addFirst(currentC);
//			pendingSteps.addFirst(DOWN);
//		}
//		int nextC = currentC + 1;
//		if (isValidStep(currentR, nextC, r, c, explored, maze)) {
//			nextSteps = true;
//			pendingR.addFirst(currentR);
//			pendingC.addFirst(nextC);
//			pendingSteps.addFirst(RIGHT);
//		}
//		if (!nextSteps) {
//			steps.pollLast(); // this step was a dead end, backtrack
//		}
//	}
//
//	return steps; // if not finished, backtracking should clear the list and we return empty
//
//}


//recursive attempt
//current position
//list of steps *taken so far*, last step is the step to reach this position
//matrix of explored positions
//base cases:
//is it the end? return list of steps 
//we will work with pre-work to recursive call
//check DOWN step:
//is it valid?
//create a list of 1 step [DOWN]
//recursive call with down position and 1 step
//if empty list is returned, this was a dead end
//if list is not empty, return current list concatenated with returned list, (skip RIGHT)
//check RIGHT step with the same algorithm
//if none of the two were good, return []

public static LinkedList<String> mazePath(boolean[][] maze, int r, int c) {

	if (maze == null || r < 1 || c < 1) {
		throw new IndexOutOfBoundsException("Invalid maze");
	}

	boolean[][] explored = new boolean[r][c];
	for (int i = 0; i < r; i++) {
		for (int j = 0; j < c; j++) {
			explored[i][j] = false;
		}
	}

	return _mazePath(0, 0, r, c, maze, explored);

}


private static LinkedList<String> _mazePath(int x, int y, int r, int c, boolean[][] maze, boolean[][] explored) {

	LinkedList<String> steps = new LinkedList<String>();
	explored[x][y] = true;

	// recursive cases, if none are valid, we will return the empty list
	int nextR = x + 1;
	int nextC = y + 1;
	if (isValidStep(nextR, y, r, c, explored, maze)) {
		if (nextR==r-1 && y==c-1) {	// base case, we are finished
			steps.add(DOWN);
		} else {
			LinkedList<String> path = _mazePath(nextR, y, r, c, maze, explored);
			if (path.size() > 0) {	// by induction, non empty means valid path
				path.addFirst(DOWN);
				steps = path;
			}
		}
	}

	if (steps.size() == 0 && isValidStep(x, nextC, r, c, explored, maze)) {
		if (x==r-1 && nextC==c-1) {	// base case, we are finished
			steps.add(RIGHT);
		} else {
			LinkedList<String> path = _mazePath(x, nextC, r, c, maze, explored);
			if (path.size() > 0) {	// also by induction
				path.addFirst(RIGHT);
				steps = path;
			}
		}
	}

	return steps;

}


// return true if it is a valid step
private static boolean isValidStep(int x, int y, int r, int c, boolean[][] explored, boolean[][] maze) {
	return x < r && y < c && !explored[x][y] && !maze[x][y];
}

// 16.9 pond sizes, integer matrix representing a plot of land, value of zero is water
// a pond is a region connected vertically, horizontally or diagonally
// count the number of ponds

// we need a map of all possible locations, O(k) access time
// we also want a list of all possible locations, while possible locations not empty:
// pop first element
// is it possible locations?
// no? skip
// remove from possible locations
// is it land?, skip
// is it pond? move to recursive pond finder
// recurse, while updating possible locations

public static List<Integer> ponds(int[][] land) {

	var ponds = new ArrayList<Integer>();
	var possible = new HashSet<Pair<Integer, Integer>>();
	var remaining = new LinkedList<Pair<Integer, Integer>>();
	for (int i = 0; i < land.length; i++) {
		for (int j = 0; j < land[i].length; j++) {
			var p = new Pair<Integer, Integer>(i, j);
			possible.add(p); // O(k)
			remaining.push(p); // O(k)
		}
	}

	while (!possible.isEmpty()) {
		var c = remaining.pop(); // guaranteed to have at least one item
		if (possible.contains(c)) {
			if (land[c.left][c.right] == 0) {
				int size = mapWater(land, c, possible); // it will be removed by this
				ponds.add(size);
			} else {
				possible.remove(c);
			}
		}
	}

return ponds;

}


private static int mapWater(int[][] land, Pair<Integer, Integer> x, Set<Pair<Integer, Integer>> p) {
	
	if (!p.contains(x)) {
		return 0;
	}
	p.remove(x);
	if (land[x.left][x.right]>0) {
			return 0;
	}
	int size = 1;
	size += mapWater(land, new Pair<Integer, Integer>(x.left-1, x.right-1), p);
	size += mapWater(land, new Pair<Integer, Integer>(x.left-1, x.right), p);
	size += mapWater(land, new Pair<Integer, Integer>(x.left-1, x.right+1), p);
	size += mapWater(land, new Pair<Integer, Integer>(x.left, x.right-1), p);
	size += mapWater(land, new Pair<Integer, Integer>(x.left, x.right+1), p);
	size += mapWater(land, new Pair<Integer, Integer>(x.left+1, x.right-1), p);
	size += mapWater(land, new Pair<Integer, Integer>(x.left+1, x.right), p);
	size += mapWater(land, new Pair<Integer, Integer>(x.left+1, x.right+1), p);

	return size;

}


/*
You live in the city of Cartesia where all roads are laid out in a perfect grid. 
You arrived ten minutes too early to an appointment, so you decided to take the
opportunity to go for a short walk. The city provides its citizens with a Walk 
Generating App on their phones -- everytime you press the button it sends you an
array of one-letter strings representing directions to walk (eg. ['n', 's', 'w',
'e']). You always walk only a single block for each letter (direction) and you 
know it takes you one minute to traverse one city block, so create a function 
that will return true if the walk the app gives you will take you exactly ten 
minutes (you don't want to be early or late!) and will, of course, return you to 
your starting point. Return false otherwise.

-- strategy
we can assume that we are a pair of coordinates, starting at 0,0
each walk increments or decrements it by one, we need to know if we are at 0,0
at the end or not

*/

public static boolean isTenMinWalk(char[] walk) {

	int n = walk.length;
	if (n != 10) {
		return false;
	}

	var x = 0;
	var y = 0;
	for (int i = 0; i < n; i++) {
		switch (walk[i]) {
		case 'n':
			y++;
			break;
		case 's':
			y--;
			break;
		case 'w':
			x++;
			break;
		case 'e':
			x--;
			break;
		}
	}

	return x == 0 && y == 0;
}

}

/*
 *    Copyright 2024 Daniel Giribet
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
