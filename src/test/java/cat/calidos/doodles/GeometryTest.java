// GEOMETRY TEST . JAVA

package cat.calidos.doodles;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class GeometryTest {

private static final double FLOAT_SENSITIVTY = 0.000001;

@Test @DisplayName("Line with most points in a two dimensional graph")
public void lineWithMostPointsTest() {

	/*

//@formatter:off
 A				B
 1,0---------->1,1
  ^				^
  |				|
  |				|
 0,0--0,0.5--->0,1
 C		D		E
//@formatter:on
 * 
 Line should be
 p:0,0;v=0,1
 
 */

	Graph<Point> g = new Graph<Point>();
	Point a = new Point((float) 1.0, (float) 0.0);
	g.addVertex(a);
	Point b = new Point((float) 1.0, (float) 1.0);
	g.addVertex(b);
	Point c = new Point((float) 0.0, (float) 0.0);
	g.addVertex(c);
	Point d = new Point((float) 0.0, (float) 0.5);
	g.addVertex(d);
	Point e = new Point((float) 0.0, (float) 1.0);
	g.addVertex(e);

	g.addEdge(e, b);
	g.addEdge(c, a);
	g.addEdge(c, d);
	g.addEdge(d, e);
	g.addEdge(e, b);

	Line line = Geometry.lineWithMostPoints(g);
	// 0,0--0,0.5--->0,1
	assertEquals(0.0, line.p.x);
	assertEquals(0.0, line.p.y);
	assertEquals(0.0, line.v.x);
	assertEquals(1.0, line.v.y);

	// now let's add more diagonal nodes so the diagonal wins
	Point f = new Point((float) 1.1, (float) 1.1);
	g.addVertex(f);
	Point h = new Point((float) 2.0, (float) 2.0);
	g.addVertex(h);
	Point i = new Point((float) 3.0, (float) 3.0);
	g.addVertex(i);
	line = Geometry.lineWithMostPoints(g);
	assertTrue(Math.abs(1.1 - line.p.x) < FLOAT_SENSITIVTY);
	assertTrue(Math.abs(1.1 - line.p.y) < FLOAT_SENSITIVTY);
	Point expected = Geometry
			.vectorFrom(new Point((float) 0.0, (float) 0.0), new Point((float) 1.0, (float) 1.0));
	assertEquals(expected.x, line.v.x);
	assertEquals(expected.y, line.v.y);
}


@Test
public void test01_Example() {

	/*

  // @formatter:off
    0 1 2 3 4 5 6 7 8
  0                 
  1                 
  2     A            
  3             D   
  4               F 
  5           C      
  6                 
  7             E   
  8     B          
  9               G
  // @formatter:on

*/

	List<Point> points = List
			.of(
					new Point(2, 2, "A"), // A
					new Point(2, 8, "B"), // B
					new Point(5, 5, "C"), // C
					new Point(6, 3, "D"), // D
					new Point(6, 7, "E"), // E
					new Point(7, 4, "F"), // F
					new Point(7, 9, "G") // G
			);

	List<Point> result = Geometry.closestPair(points);
	List<Point> expected = new ArrayList<Point>(2);
	expected.addAll(List.of(new Point(6, 3), new Point(7, 4)));
	// verify(expected, result);
}



private void verify(List<Point> expected,
					List<Point> actual) {
	Comparator<Point> comparer = Comparator.<Point> comparingDouble(p -> p.x);

	expected.sort(comparer);
	actual.sort(comparer);
	boolean eq = expected.get(0).x == actual.get(0).x && expected.get(0).y == actual.get(0).y
			&& expected.get(1).x == actual.get(1).x && expected.get(1).y == actual.get(1).y;
	assertTrue(
			eq,
			String.format("Expected: %s, Actual: %s", expected.toString(), actual.toString()));
}

}

/*
 * Copyright 2024 Daniel Giribet
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
