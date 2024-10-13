package cat.calidos.doodles;


public class Geometry2 {

class Point {

public float	x;
public float	y;

public Point(float x, float y) {
	this.x = x;
	this.y = y;
}


public Point(int x, int y) {
	this((float) x, (float) y);
}


public Point rotate(int deg) {
	float r = (float) Math.toRadians(360 - deg);
	
	var newX = this.x * (float) Math.cos(r) - this.y * (float) Math.sin(r);
	var newY = this.y * (float) Math.cos(r) + this.x * (float) Math.sin(r);
	this.x = newX;
	this.y = newY;
	return this;
}
}

class Line {

public float	x1;
public float	y1;
public float	x2;
public float	y2;


public Line(Point p1, Point p2) {
	this.x1 = p1.x;
	this.y1 = p1.y;
	this.x2 = p2.x;
	this.y2 = p2.y;
}


public float cartesianProduct(int x, int y) {
	return cartesianProduct((float) x, (float) y);
}


public float cartesianProduct(float x, float y) {
	return (x2 - x1) * (y - y1) - (y2 - y1) * (x - x1);
}


@Override
public String toString() {
	return "("+x1+","+y2+")-->("+x2+","+y2+")";
}



}


/*
 * A rectangle with sides equal to even integers a and b is drawn on the Cartesian plane. Its center
 * (the intersection point of its diagonals) coincides with the point (0, 0), but the sides of the
 * rectangle are not parallel to the axes; instead, they are forming 45 degree angles with the axes.
 * 
 * How many points with integer coordinates are located inside the given rectangle (including on its
 * sides)?
 * 
 * Example
 * 
 * For a = 6 and b = 4, the output should be 23
 */
static int rectangleRotation(int a, int b) {
	var newA = b; // annoyingly, y is passed first, so doing a swap
	var newB = a;
	a = newA;
	b = newB;
	var s = new Geometry2();
	var lines = new Line[] { s.new Line(s.new Point(-a / 2, b / 2).rotate(45), s.new Point(a / 2, b / 2).rotate(45)),
			s.new Line(s.new Point(a / 2, b / 2).rotate(45), s.new Point(a / 2, -b / 2).rotate(45)),
			s.new Line(s.new Point(a / 2, -b / 2).rotate(45), s.new Point(-a / 2, -b / 2).rotate(45)),
			s.new Line(s.new Point(-a / 2, -b / 2).rotate(45), s.new Point(-a / 2, b / 2).rotate(45)), };

	int startY = (int) Math.ceil((lines[3].y2));
	int endY = (int) Math.ceil(lines[1].y2);
	int startX = 1;
	int endX = (int) Math.ceil(lines[0].x2);
	var count = findPointsInsideFast(lines, startY, endY, startX, endX, false)*2;

	startX = 0; // now we only need the ones in the x=0 axis
	endX = 0;
	count += findPointsInside(lines, startY, endY, startX, endX, true);
	return count;
}


private static int findPointsInsideFast(Line[] lines, int startY, int endY, int startX, int endX, boolean zero) {
	return findPointsInside(lines, startY, endY, startX, endX, zero);
}

private static int findPointsInside(Line[] lines, int startY, int endY, int startX, int endX, boolean zero) {
	var count = 0;
	for (var y = startY; y >= endY; y--) {
		var rightOfRectangle = false;
		var countInRow = 0;
		for (var x = startX; x <= endX && !rightOfRectangle; x++) {
			var i = 0;
			var inside = true;
			while (inside && i < lines.length) {
				var pc = lines[i].cartesianProduct(x, y);
				inside = zero ? pc <= 0 : pc < 0;
				i++;
			}
			count = inside ? count + 1 : count;
			if (inside) {
				System.out.print("["+x+","+y+"]\t");
				countInRow++;
			} else {
				if (countInRow>0) {
					rightOfRectangle = true;
				}
				System.out.print(" "+x+","+y+" \t");
			}
		}
		System.out.println(""+count);
	}
	return count;
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

