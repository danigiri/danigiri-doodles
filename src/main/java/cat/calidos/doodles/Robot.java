package cat.calidos.doodles;


public class Robot {


/**	Given a grid of nxn, and a robot that can go right and down, compute how many
*	different paths the robot can take, taking into account the robot can stop at
*	any time
* 	@param n size of the grid
* 	@return number of possible paths
*////////////////////////////////////////////////////////////////////////////////
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
	
	// base case
	if (w==0 && h==0) {
		return 0;
	}

	// recursive cases
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



}
