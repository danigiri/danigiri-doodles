package cat.calidos.doodles;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class DijkstraTest {


@Test @DisplayName("to weighted graph test")
public void weightedGraphTest() {
	int[][] test0 = new int[][] { { 75 } };
	//assertEquals(75, Dijkstra.longestSlideDown(test0));

	int[][] test1 = new int[][] { 	{ 75 },
									{ 95, 64 } };
	//assertEquals(75+95, Dijkstra.longestSlideDown(test1));

	/*
	 *   /3/
	 *  \7\ 4
	 *  2 \4\ 6 
	 * 8 5 \9\ 3 
	 */
	int[][] test2 = new int[][] { 	{ 3 },
									{ 7, 4 },
									{ 2, 4, 6},
									{ 8, 5, 9, 3}};
	assertEquals( 3 + 7 + 4 + 9, Dijkstra.longestSlideDown(test2));

	/*
	    \11\
  		12  \13\
		14 /15/  16
		/90/  18  19  20
	 */
	int[][] test3 = new int[][] { 	{ 11 },					// 0x0
									{ 12, 13 },				// 1x0, 1x1
									{ 14, 15, 16},			// 2x0, 2x1, 2x2
									{ 90, 18, 19, 20}};		// 3x0, 3x1, 3x2, 3x3
	assertEquals(90+15+13+11, Dijkstra.longestSlideDown(test3));

	int[][] test = new int[][]{
								{75},
								{95, 64},
								{17, 47, 82},
								{18, 35, 87, 10},
								{20, 4, 82, 47, 65},
								{19, 1, 23, 75, 3, 34},
								{88, 2, 77, 73, 7, 63, 67},
								{99, 65, 4, 28, 6, 16, 70, 92},
								{41, 41, 26, 56, 83, 40, 80, 70, 33},
								{41, 48, 72, 33, 47, 32, 37, 16, 94, 29},
								{53, 71, 44, 65, 25, 43, 91, 52, 97, 51, 14},
								{70, 11, 33, 28, 77, 73, 17, 78, 39, 68, 17, 57},
								{91, 71, 52, 38, 17, 14, 91, 43, 58, 50, 27, 29, 48},
								{63, 66, 4, 68, 89, 53, 67, 30, 73, 16, 69, 87, 40, 31},
								{4, 62, 98, 27, 23, 9, 70, 98, 73, 93, 38, 53, 60, 4, 23},
							};
	// assertEquals(1074, Dijkstra.longestSlideDown(test));

}


}

/*
 * Copyright 2024 Daniel Giribet <dani - calidos.cat>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
