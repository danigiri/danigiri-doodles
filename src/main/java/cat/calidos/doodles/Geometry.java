package cat.calidos.doodles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Geometry {

//16.14 given a two dimensional graph with points on it, find a line which passes the most
//number of points

/*

formula for point belonging to a line
line formula, with vX and vY guaranteed to be a vector, so >0
xL = pX+vX*N (N: -inifinity, +infinity)
xY = pY+vY*N

(xL-pX)/vX=N
(xY-pY)/vY=N

point belongs to a line means this equation has a solution

(pointX-linePX)/vX = (pointY-linePY)/vY


OK, what we can do is find all the normalised vectors between any two points (N^2)
vectors that face in opposite directions need to be normalised so they all point in the same direction 
you only need an instance of vectors that have two points
solve the equation for the sorted list starting from the top, get the max
*/

public static Line lineWithMostPoints(Graph<Point> g) {

	Map<Point, List<Pair<Point, Point>>> vectors = normalisedVectors(g);
	Map<Point, Integer> vectorCounts = new HashMap<Point, Integer>(vectors.size());
	vectors.entrySet().forEach(e -> vectorCounts.put(e.getKey(), e.getValue().size()));

Line max;
int maxIntersections = 0;
vectorCounts.entrySet().forEach(vc -> {
if (maxIntersections<vc.getValue()) {
	Iterator<Pair<Point, Point>> pointsWithThisVector = vectors.get(vc.getKey()).iterator();
	int belongCounter = 0;
	Line line = new Line(pointsWithThisVector.next().left, vc.getKey());
	while (pointsWithThisVector.hasNext()) {
		Pair<Point, Point> currentPair = pointsWithThisVector.next();
		if (pointInLine(line, currentPair.left)) {
			belongCounter++;
		}
}
if (belongCounter>maxIntersections) {
	max = line;
}
}
});

	return max; // max is empty if graph has zero or one nodes
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


private static Point vectorFrom(Point a, Point b) {
	
float x = b.x - a.x;
	float y = b.y - a.y;
	float mag = Math.sqrt(x*x+y*y);
	
	return new Point(x/mag, y/mag);	// we assume we're good with whatever precision

}

private static Point alignVector(Point v) {
	
	// positive, positive, leave alone
	// negative, negative, make both positive
	// negative, positive, leave alone
	// positive, negative, -x, -y

	if (v.x>=0.0 && v.y>=0.0) {
		return v;
}
if (v.x<=0.0 && v.y<=0.0) {
	return new Point(-v.x, -v.y);
}
if (v.x<=0.0 && v.y>=0.0) {
	return v;
}
if (v.x>=0.0 && v.y<=0.0) {
	return new Point(-v.x, -v.y);
}

}

private static boolean pointInLine(Line l, Point p) {
//(pointX-linePX)/vX = (pointY-linePY)/vY
	return (p.x-l.x)/l.v.x == (p.y-l.y)/l.v.y;
}



}



/**
Copyright 2014 Daniel Giribet <dani - calidos.cat>

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
