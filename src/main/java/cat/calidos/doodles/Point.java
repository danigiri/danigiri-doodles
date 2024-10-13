package cat.calidos.doodles;

import java.util.Optional;

/**
 * @author daniel giribet
 *///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class Point implements Comparable<Point> {

public float	x;
public float	y;
public Optional<String> name;

Point(float x, float y) {

	this.x = x == -0.0 ? (float) 0.0 : x;
	this.y = y == -0.0 ? (float) 0.0 : y;
	this.name = Optional.empty();
}


Point(float x, float y, String name) {

	this(x, y);
	this.name = Optional.ofNullable(name);
}



@Override
public boolean equals(Object obj) {

	if (obj == null) {
		return false;
	}
	try {
		Point p = (Point) obj;
		return this.toString().equals(p.toString());
	} catch (ClassCastException e) {
		return false;
	}
}


@Override
public int compareTo(Point o) {
	int comparison = 0;
	if (x != o.x || y != o.y) {
		comparison = Math.sqrt(x * x + y * y) < Math.sqrt(o.x * o.x + o.y * o.y) ? -1 : 1;
	}

	return comparison;
}


@Override
public String toString() {
	return name.orElse("")+"[" + x + "," + y + "]";
}


@Override
public int hashCode() {
	return this.toString().hashCode();
}

}

/*
 * Copyright 2020 Daniel Giribet
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
