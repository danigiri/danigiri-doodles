// MATH TEST . JAVA
package cat.calidos.doodles;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;


public class MathTest {

@Test @DisplayName("multiply with additions test")
public void multiplyWithPlusTest() {

	int v = Math.multiplyWithPlus(0, 0);
	assertEquals(0, v);

	v = Math.multiplyWithPlus(1, 0);
	assertEquals(0, v);

	v = Math.multiplyWithPlus(0, 1);
	assertEquals(0, v);

	v = Math.multiplyWithPlus(1, 1);
	assertEquals(1, v);

	v = Math.multiplyWithPlus(1, 5);
	assertEquals(5, v);

	v = Math.multiplyWithPlus(5, 1);
	assertEquals(5, v);

	v = Math.multiplyWithPlus(5, 5);
	assertEquals(25, v);

	v = Math.multiplyWithPlus(2, 5);
	assertEquals(10, v);

}


@Test @DisplayName("divide with substractions test")
public void divideWithSubstractTest() {

	int v = Math.divideWithSubstract(0.0, 1.0);
	assertEquals(0, v);

	v = Math.divideWithSubstract(1.0, 0.5);
	assertEquals(2, v);

	v = Math.divideWithSubstract(0.5, 1.0);
	assertEquals(0, v);

}


@Test @DisplayName("multiply with additions test")
public void stepTest() {

	assertEquals(0, Math.steps(0));
	assertEquals(1, Math.steps(1));
	assertEquals(2, Math.steps(2));
	assertEquals(4, Math.steps(3));

	// 4= 1+steps(3):4+1+steps(2):2+1+steps(1):1 = 10
	assertEquals(10, Math.steps(4));

}


@Test @DisplayName("small numbers factorial test")
public void factorialTest() {

	assertEquals(0, Math.trailingZerosFactorialSmall(3));
	assertEquals(0, Math.trailingZerosFactorialSmall(4));
	assertEquals(1, Math.trailingZerosFactorialSmall(5));
	assertEquals(1, Math.trailingZerosFactorialSmall(6));
	assertEquals(1, Math.trailingZerosFactorialSmall(7));
	assertEquals(1, Math.trailingZerosFactorialSmall(8));
	assertEquals(1, Math.trailingZerosFactorialSmall(9));
	assertEquals(2, Math.trailingZerosFactorialSmall(10));

}


@Test @DisplayName("big numbers factorial test")
public void factorialTestBig() {

	assertEquals(0, Math.trailingZeros(1));
	assertEquals(0, Math.trailingZeros(2));
	assertEquals(0, Math.trailingZeros(3));
	assertEquals(0, Math.trailingZeros(4));

	assertEquals(1, Math.trailingZeros(5));
	assertEquals(1, Math.trailingZeros(6));
	assertEquals(1, Math.trailingZeros(7));
	assertEquals(1, Math.trailingZeros(8));
	assertEquals(1, Math.trailingZeros(9));

	assertEquals(2, Math.trailingZeros(10));
	assertEquals(2, Math.trailingZeros(11));
	assertEquals(2, Math.trailingZeros(12));
	assertEquals(2, Math.trailingZeros(13));
	assertEquals(2, Math.trailingZeros(14));

	assertEquals(3, Math.trailingZeros(15));
	assertEquals(3, Math.trailingZeros(16));

	assertEquals(4, Math.trailingZeros(21));
	assertEquals(4, Math.trailingZeros(22));
	assertEquals(4, Math.trailingZeros(23));
	assertEquals(4, Math.trailingZeros(24));

	assertEquals(6, Math.trailingZeros(25));

	assertEquals(12, Math.trailingZeros(50));

}


@Test @DisplayName("turn int into a list")
public void intToListTest() {

	java.util.LinkedList<Integer> expected = new java.util.LinkedList<Integer>();
	expected.add(0);
	assertEquals(expected, Math.intToList(0));

	expected = new java.util.LinkedList<Integer>();
	expected.add(1);
	assertEquals(expected, Math.intToList(1));

	expected.add(2);
	assertEquals(expected, Math.intToList(21));

	expected.add(7);
	assertEquals(expected, Math.intToList(721));

}


@Test @DisplayName("multiply int list by <10")
public void multiplyTest() {

	java.util.LinkedList<Integer> expected = Math.intToList(0);
	assertEquals(expected, Math.multiply(0, Math.intToList(21)));

	expected = Math.intToList(1);
	assertEquals(expected, Math.multiply(1, Math.intToList(1)));

	expected = Math.intToList(8);
	assertEquals(expected, Math.multiply(2, Math.intToList(4)));

	expected = Math.intToList(16);
	assertEquals(expected, Math.multiply(4, Math.intToList(4)));

	expected = Math.intToList(30);
	assertEquals(expected, Math.multiply(5, Math.intToList(6)));

}


@Test @DisplayName("sum <10 to int list")
public void sumTest() {

	java.util.LinkedList<Integer> expected = Math.intToList(1);
	assertEquals(expected, Math.sum(Math.intToList(0), Math.intToList(1)));

	expected = Math.intToList(2);
	assertEquals(expected, Math.sum(Math.intToList(1), Math.intToList(1)));

	expected = Math.intToList(7);
	assertEquals(expected, Math.sum(Math.intToList(1), Math.intToList(6)));
	assertEquals(expected, Math.sum(Math.intToList(6), Math.intToList(1)));

	expected = Math.intToList(13);
	assertEquals(expected, Math.sum(Math.intToList(6), Math.intToList(7)));

	expected = Math.intToList(23);
	assertEquals(expected, Math.sum(Math.intToList(12), Math.intToList(11)));
	assertEquals(expected, Math.sum(Math.intToList(11), Math.intToList(12)));

	expected = Math.intToList(10000);
	assertEquals(expected, Math.sum(Math.intToList(9999), Math.intToList(1)));

}


@Test @DisplayName("substract <10 to int list")
public void substractTest() {

	java.util.LinkedList<Integer> expected = Math.intToList(0);
	assertEquals(expected, Math.substract(Math.intToList(1), 1));

	expected = Math.intToList(-1);
	assertEquals(expected, Math.substract(Math.intToList(1), 2));

	expected = Math.intToList(7);
	assertEquals(expected, Math.substract(Math.intToList(10), 3));

	expected = Math.intToList(13);
	assertEquals(expected, Math.substract(Math.intToList(15), 2));

	expected = Math.intToList(23);
	assertEquals(expected, Math.substract(Math.intToList(30), 7));

	expected = Math.intToList(999);
	assertEquals(expected, Math.substract(Math.intToList(1000), 1));

}




@Test @DisplayName("multiply int lists")
public void multiplyBigTest() {

	java.util.LinkedList<Integer> expected = Math.intToList(0);
	assertEquals(expected, Math.multiplyBig(Math.intToList(2), Math.intToList(0)));

	expected = Math.intToList(2);
	assertEquals(expected, Math.multiplyBig(Math.intToList(2), Math.intToList(1)));

	expected = Math.intToList(8);
	assertEquals(expected, Math.multiplyBig(Math.intToList(4), Math.intToList(2)));
	assertEquals(expected, Math.multiplyBig(Math.intToList(2), Math.intToList(4)));

	expected = Math.intToList(32);
	assertEquals(expected, Math.multiplyBig(Math.intToList(4), Math.intToList(8)));
	assertEquals(expected, Math.multiplyBig(Math.intToList(8), Math.intToList(4)));

	expected = Math.intToList(20);
	assertEquals(expected, Math.multiplyBig(Math.intToList(10), Math.intToList(2)));
	assertEquals(expected, Math.multiplyBig(Math.intToList(2), Math.intToList(10)));

	expected = Math.intToList(24);
	assertEquals(expected, Math.multiplyBig(Math.intToList(12), Math.intToList(2)));
	assertEquals(expected, Math.multiplyBig(Math.intToList(2), Math.intToList(12)));

	expected = Math.intToList(100);
	assertEquals(expected, Math.multiplyBig(Math.intToList(10), Math.intToList(10)));

	expected = Math.intToList(111);
	assertEquals(expected, Math.multiplyBig(Math.intToList(3), Math.intToList(37)));
	assertEquals(expected, Math.multiplyBig(Math.intToList(37), Math.intToList(3)));

	expected = Math.intToList(650);
	assertEquals(expected, Math.multiplyBig(Math.intToList(25), Math.intToList(26)));
	assertEquals(expected, Math.multiplyBig(Math.intToList(26), Math.intToList(25)));

}


}
/**
Copyright 2016 Daniel Giribet <dani - calidos.cat>

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
