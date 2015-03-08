/**
 Copyright 2015 Daniel Giribet <dani - calidos.cat>

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

package cat.calidos.doodles;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;


public class RobotTest {

@Test
public void testPathsInSquareGrid() {

	assertEquals(0, Robot.pathsInSquareGrid(0));

	assertEquals(0, Robot.pathsInSquareGrid(1));	// FIXME: this should be 1

	assertEquals(4, Robot.pathsInSquareGrid(2));

	assertEquals(18, Robot.pathsInSquareGrid(3));

}


@Test
public void testPathsInSquareGridWithObstacles() {
	
	boolean[][] o0 = {{}, {}};
	
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
	
	boolean[][] o1 = {{true}};
	assertEquals(0, Robot.pathsInSquareGridWithObstacles(1, o1));

	boolean[][] o2 = {{true, false}, 
					  {false, false}};	
	assertEquals(0, Robot.pathsInSquareGridWithObstacles(2, o2));
	
	boolean[][] o3 = {{false, false}, 
			  		  {false, false}};	
	assertEquals(4, Robot.pathsInSquareGridWithObstacles(2, o3));
	

	boolean[][] o4 = {{false, false}, 
	  		  		  {false, true}};	
	assertEquals(2, Robot.pathsInSquareGridWithObstacles(2, o4));

	boolean[][] o5 = {{false, true}, 
	  		  		  {false, false}};	
	assertEquals(2, Robot.pathsInSquareGridWithObstacles(2, o5));


	boolean[][] o6 = {{false, false, false}, 
	  		  		  {false, false, false},
	  		  		  {true, false, false}};	
	assertEquals(15, Robot.pathsInSquareGridWithObstacles(3, o6));

}


@Test
public void testNonStopPathsInSquareGrid() {
	
	assertEquals(0, Robot.nonStopPathsInSquareGrid(0));

	assertEquals(1, Robot.nonStopPathsInSquareGrid(1));

	assertEquals(2, Robot.nonStopPathsInSquareGrid(2));

	assertEquals(6, Robot.nonStopPathsInSquareGrid(3));
	
	// the six possible paths:
	// ---	--.	|..	|..	|..	--.
	// ..|	.|.	|..	---	-|.	.|-
	// ..x	.-x	--x	..x	.-x	..x

}


}
