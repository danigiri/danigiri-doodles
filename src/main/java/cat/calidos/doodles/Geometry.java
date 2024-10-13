package cat.calidos.doodles;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class Geometry {

// 16.14 given a two dimensional graph with points on it, find a line which passes the most
// number of points

/*
 * 
 * formula for point belonging to a line line formula, with vX and vY guaranteed to be a vector, so
 * >0 xL = pX+vX*N (N: -inifinity, +infinity) xY = pY+vY*N
 * 
 * (xL-pX)/vX=N (xY-pY)/vY=N
 * 
 * point belongs to a line means this equation has a solution
 * 
 * (pointX-linePX)/vX = (pointY-linePY)/vY
 * 
 * 
 * OK, what we can do is find all the normalised vectors between any two points (N^2) vectors that
 * face in opposite directions need to be normalised so they all point in the same direction you
 * only need an instance of vectors that have two points solve the equation for the sorted list
 * starting from the top, get the max
 */

public static Line lineWithMostPoints(Graph<Point> g) {

	Map<Point, List<Pair<Point, Point>>> vectors = normalisedVectors(g);
	Map<Point, Integer> vectorCounts = new HashMap<Point, Integer>(vectors.size());
	vectors.entrySet().forEach(e -> vectorCounts.put(e.getKey(), e.getValue().size()));

	Line max = null;
	int maxIntersections = 0;
	for (Entry<Point, List<Pair<Point, Point>>> vc : vectors.entrySet()) {
		if (maxIntersections < vc.getValue().size()) {
			Iterator<Pair<Point, Point>> pointsWithThisVector = vectors.get(vc.getKey()).iterator();
			int belongCounter = 0;
			Line line = new Line(pointsWithThisVector.next().left, vc.getKey());
			while (pointsWithThisVector.hasNext()) {
				Pair<Point, Point> currentPair = pointsWithThisVector.next();
				if (pointInLine(line, currentPair.left)) {
					belongCounter++;
				}
			}
			if (belongCounter > maxIntersections) {
				maxIntersections = belongCounter;
				max = line;
			}
		}
	}

	return max; // max is empty if graph has zero or one node

}


private static Map<Point, List<Pair<Point, Point>>> normalisedVectors(Graph<Point> g) {

	Map<Point, List<Pair<Point, Point>>> vectors = new HashMap<Point, List<Pair<Point, Point>>>();
	Set<Point> vertices = g.getVertices();
	Set<Pair<Point, Point>> handled = new HashSet<Pair<Point, Point>>(vertices.size() * 2);

	vertices.forEach(v -> {

		vertices.stream().filter(v2 -> v != v2).forEach(v2 -> {
			// careful now, we do not want to process a,b, and b,a
			Pair<Point, Point> pair = new Pair<Point, Point>(v, v2);
			if (!handled.contains(pair)) {
				handled.add(pair);
				handled.add(new Pair<Point, Point>(v2, v));
				// now calculate and add the vector
				Point vector = vectorFrom(v, v2);
				Point alignedVector = alignVector(vector);
				List<Pair<Point, Point>> pointList;
				// Optional<Point> containsVector = vectors.keySet().stream().filter(vec ->
				// vec.equals(alignedVector)).findAny();
				if (vectors.containsKey(alignedVector)) {
					pointList = vectors.get(alignedVector);
				} else {
					pointList = new ArrayList<Pair<Point, Point>>();
				}
				pointList.add(pair);
				vectors.put(alignedVector, pointList);
			}
		});

	});

	return vectors;
}


public static Point vectorFrom(	Point a,
								Point b) {

	float x = b.x - a.x;
	float y = b.y - a.y;
	float mag = (float) Math.sqrt(x * x + y * y);

	return new Point(x / mag, y / mag); // we assume we're good with whatever precision

}


private static Point alignVector(Point v) {

	// positive, positive, leave alone
	// negative, negative, make both positive
	// negative, positive, leave alone
	// positive, negative, -x, -y

	if (v.x >= 0.0 && v.y >= 0.0) {
		return v;
	}
	if (v.x <= 0.0 && v.y <= 0.0) {
		return new Point(-v.x, -v.y);
	}
	if (v.x <= 0.0 && v.y >= 0.0) {
		return v;
	}
	// if (v.x>=0.0 && v.y<=0.0) {
	return new Point(-v.x, -v.y);
	// }

}


private static boolean pointInLine(	Line l,
									Point p) {

	boolean isInLine = false;
	if (l.v.x == 0) {
		isInLine = (p.x - l.p.x) == 0;
	} else if (l.v.y == 0) {
		isInLine = (p.x - l.p.x) == 0;
	} else {
		isInLine = (p.x - l.p.x) / l.v.x == (p.y - l.p.y) / l.v.y;
	}

	return isInLine;

}

/*
 * 
 * Given a number of points on a plane, find two points with the smallest distance between them in
 * linearithmic O(n log n) time.
 * 
 * option 1: we calculate distance with existing points, keep min, this works but is N^2
 * 
 * option 2: induction solution if no existing candidate, pair is smallest distance
 * 
 * A ---- B d(A,B)
 * 
 * option 3:
 * 
 * i create a space tree, calculate min and max, or split on the fly
 * 
 * 
 * option 4:
 * 
 * i create two sorted heaps of pairs, by their distance 2 NxlogN unsorted x: [A,B:0], [B,C:3],
 * [C,D:1], [D,E:0], [E,F:1], [F,G:0] unsorted y: [A,D:1], [D,F:1], [F,C:1], [C,E:2], [E,B:1],
 * [B,G:1] sort them x: [A,B:0], [D,E:0], [F,G: 0], [C,D:1], [E,F:1], [B,C:3] y: [A,D:1], [D,F:1],
 * [F,C:1], [E,B:1], [B,G:1], [C,E:2]
 * 
 * take current top of each if the same vertex in either direction, return otherwise
 * 
 * 
 * 
 * option 5: i keep the smallest triangle
 * 
 * A, B, C --> B,C, E --> C,D,E --> C,D,F
 * 
 * once I am at the end, I check which pair has smaller distance and return that one?
 * 
 * 
 */

class Triangle {

public Point	p1;
public Point	p2;
public Point	p3;

Triangle(Point p1, Point p2, Point p3) {
	this.p1 = p1;
	this.p2 = p2;
	this.p3 = p3;
}


float area() {
	// 0.5*|(x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1)|
	return 0.5f * (float) Math.abs((p2.x - p1.x) * (p3.y - p1.y) - (p3.x - p1.x) * (p2.y - p1.y));
}


public Triangle smallestTriangle(List<Triangle> triangles) {
	int minIndex = IntStream
			.range(0, triangles.size())
			.boxed()
			.min(Comparator.comparing(i -> triangles.get(i).area()))
			.get();
	return triangles.get(minIndex);
}


public Triangle triangleWithShorterVertices(List<Triangle> triangles) {
	int minIndex = IntStream
			.range(0, triangles.size())
			.boxed()
			.min(Comparator.comparing(i -> triangles.get(i).vertexSum()))
			.get();
	return triangles.get(minIndex);
}


public List<Point> shortestVertex() {
	float d12 = distanceBetween(p1, p2);
	float d23 = distanceBetween(p2, p3);
	float d31 = distanceBetween(p3, p1);
	float min = Stream.of(d12, d23, d31).min(Float::compare).get();
	if (min == d12) {
		return List.of(p1, p2);
	} else if (min == d23) {
		return List.of(p2, p3);
	} else {
		return List.of(p3, p1);
	}
}


@Override
public String toString() {
	return "(" + p1 + ";" + p2 + ";" + p3 + "){" + String.format("%.2f", area()) + ","
			+ String.format("%.2f", vertexSum()) + "}";
}


private float distanceBetween(	Point a,
								Point b) {
	return (float) Math.sqrt(Math.pow(a.x * b.x, 2) + Math.pow(a.y * b.y, 2));
}


private float vertexSum() {
	float d12 = distanceBetween(p1, p2);
	float d23 = distanceBetween(p2, p3);
	float d31 = distanceBetween(p3, p1);
	return Stream
			.of(d12, d23, d31)
			.reduce(
					0f,
					(	a,
						b) -> a + b);
}

}

public static List<Point> closestPair(List<Point> points) {
	int n = points.size();
	if (n < 2) {
		return List.of();
	}
	if (n == 2) {
		return List.of(points.get(0), points.get(1));
	}
	var k = new Geometry();
	Triangle t = k.new Triangle(points.get(0), points.get(1), points.get(2));
	System.out.println(t);
	for (var i = 3; i < n; i++) {
		Point p = points.get(i);
		var t12p = k.new Triangle(t.p1, t.p2, p);
		var t23p = k.new Triangle(t.p2, t.p3, p);
		var t31p = k.new Triangle(t.p3, t.p1, p);
		t = t.triangleWithShorterVertices(List.of(t12p, t23p, t31p, t));
		System.out.println(t);
	}

	List<Point> solution = t.shortestVertex();
	var out = new ArrayList<Point>(2);
	out.addAll(solution);
	return out;
}

}

/**
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
