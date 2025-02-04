// MATH TEST . JAVA
package cat.calidos.doodles;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;


public class MathsTest {

@Test @DisplayName("multiply with additions test")
public void multiplyWithPlusTest() {

	int v = Maths.multiplyWithPlus(0, 0);
	assertEquals(0, v);

	v = Maths.multiplyWithPlus(1, 0);
	assertEquals(0, v);

	v = Maths.multiplyWithPlus(0, 1);
	assertEquals(0, v);

	v = Maths.multiplyWithPlus(1, 1);
	assertEquals(1, v);

	v = Maths.multiplyWithPlus(1, 5);
	assertEquals(5, v);

	v = Maths.multiplyWithPlus(5, 1);
	assertEquals(5, v);

	v = Maths.multiplyWithPlus(5, 5);
	assertEquals(25, v);

	v = Maths.multiplyWithPlus(2, 5);
	assertEquals(10, v);

}


@Test @DisplayName("divide with substractions test")
public void divideWithSubstractTest() {

	int v = Maths.divideWithSubstract(0.0, 1.0);
	assertEquals(0, v);

	v = Maths.divideWithSubstract(1.0, 0.5);
	assertEquals(2, v);

	v = Maths.divideWithSubstract(0.5, 1.0);
	assertEquals(0, v);

}


@Test @DisplayName("multiply with additions test")
public void stepTest() {

	assertEquals(0, Maths.steps(0));
	assertEquals(1, Maths.steps(1));
	assertEquals(2, Maths.steps(2));
	assertEquals(4, Maths.steps(3));

	// 4= 1+steps(3):4+1+steps(2):2+1+steps(1):1 = 10
	assertEquals(10, Maths.steps(4));

}


@Test @DisplayName("small numbers factorial test")
public void factorialTest() {

	assertEquals(0, Maths.trailingZerosFactorialSmall(3));
	assertEquals(0, Maths.trailingZerosFactorialSmall(4));
	assertEquals(1, Maths.trailingZerosFactorialSmall(5));
	assertEquals(1, Maths.trailingZerosFactorialSmall(6));
	assertEquals(1, Maths.trailingZerosFactorialSmall(7));
	assertEquals(1, Maths.trailingZerosFactorialSmall(8));
	assertEquals(1, Maths.trailingZerosFactorialSmall(9));
	assertEquals(2, Maths.trailingZerosFactorialSmall(10));

}


@Test @DisplayName("big numbers factorial test")
public void factorialTestBig() {

	assertEquals(0, Maths.trailingZeros(1));
	assertEquals(0, Maths.trailingZeros(2));
	assertEquals(0, Maths.trailingZeros(3));
	assertEquals(0, Maths.trailingZeros(4));

	assertEquals(1, Maths.trailingZeros(5));
	assertEquals(1, Maths.trailingZeros(6));
	assertEquals(1, Maths.trailingZeros(7));
	assertEquals(1, Maths.trailingZeros(8));
	assertEquals(1, Maths.trailingZeros(9));

	assertEquals(2, Maths.trailingZeros(10));
	assertEquals(2, Maths.trailingZeros(11));
	assertEquals(2, Maths.trailingZeros(12));
	assertEquals(2, Maths.trailingZeros(13));
	assertEquals(2, Maths.trailingZeros(14));

	assertEquals(3, Maths.trailingZeros(15));
	assertEquals(3, Maths.trailingZeros(16));

	assertEquals(4, Maths.trailingZeros(21));
	assertEquals(4, Maths.trailingZeros(22));
	assertEquals(4, Maths.trailingZeros(23));
	assertEquals(4, Maths.trailingZeros(24));

	assertEquals(6, Maths.trailingZeros(25));

	assertEquals(12, Maths.trailingZeros(50));

}


@Test @DisplayName("turn int into a list")
public void intToListTest() {

	java.util.LinkedList<Integer> expected = new java.util.LinkedList<Integer>();
	expected.add(0);
	assertEquals(expected, Maths.intToList(0));

	expected = new java.util.LinkedList<Integer>();
	expected.add(1);
	assertEquals(expected, Maths.intToList(1));

	expected.add(2);
	assertEquals(expected, Maths.intToList(21));

	expected.add(7);
	assertEquals(expected, Maths.intToList(721));

}


@Test @DisplayName("multiply int list by <10")
public void multiplyTest() {

	java.util.LinkedList<Integer> expected = Maths.intToList(0);
	assertEquals(expected, Maths.multiply(0, Maths.intToList(21)));

	expected = Maths.intToList(1);
	assertEquals(expected, Maths.multiply(1, Maths.intToList(1)));

	expected = Maths.intToList(8);
	assertEquals(expected, Maths.multiply(2, Maths.intToList(4)));

	expected = Maths.intToList(16);
	assertEquals(expected, Maths.multiply(4, Maths.intToList(4)));

	expected = Maths.intToList(30);
	assertEquals(expected, Maths.multiply(5, Maths.intToList(6)));

}


@Test @DisplayName("sum <10 to int list")
public void sumTest() {

	java.util.LinkedList<Integer> expected = Maths.intToList(1);
	assertEquals(expected, Maths.sum(Maths.intToList(0), Maths.intToList(1)));

	expected = Maths.intToList(2);
	assertEquals(expected, Maths.sum(Maths.intToList(1), Maths.intToList(1)));

	expected = Maths.intToList(7);
	assertEquals(expected, Maths.sum(Maths.intToList(1), Maths.intToList(6)));
	assertEquals(expected, Maths.sum(Maths.intToList(6), Maths.intToList(1)));

	expected = Maths.intToList(13);
	assertEquals(expected, Maths.sum(Maths.intToList(6), Maths.intToList(7)));

	expected = Maths.intToList(23);
	assertEquals(expected, Maths.sum(Maths.intToList(12), Maths.intToList(11)));
	assertEquals(expected, Maths.sum(Maths.intToList(11), Maths.intToList(12)));

	expected = Maths.intToList(10000);
	assertEquals(expected, Maths.sum(Maths.intToList(9999), Maths.intToList(1)));

}


@Test @DisplayName("substract <10 to int list")
public void substractTest() {

	java.util.LinkedList<Integer> expected = Maths.intToList(0);
	assertEquals(expected, Maths.substract(Maths.intToList(1), 1));

	expected = Maths.intToList(-1);
	assertEquals(expected, Maths.substract(Maths.intToList(1), 2));

	expected = Maths.intToList(7);
	assertEquals(expected, Maths.substract(Maths.intToList(10), 3));

	expected = Maths.intToList(13);
	assertEquals(expected, Maths.substract(Maths.intToList(15), 2));

	expected = Maths.intToList(23);
	assertEquals(expected, Maths.substract(Maths.intToList(30), 7));

	expected = Maths.intToList(999);
	assertEquals(expected, Maths.substract(Maths.intToList(1000), 1));

}


@Test @DisplayName("multiply int lists")
public void multiplyBigTest() {

	java.util.LinkedList<Integer> expected = Maths.intToList(0);
	assertEquals(expected, Maths.multiplyBig(Maths.intToList(2), Maths.intToList(0)));

	expected = Maths.intToList(2);
	assertEquals(expected, Maths.multiplyBig(Maths.intToList(2), Maths.intToList(1)));

	expected = Maths.intToList(8);
	assertEquals(expected, Maths.multiplyBig(Maths.intToList(4), Maths.intToList(2)));
	assertEquals(expected, Maths.multiplyBig(Maths.intToList(2), Maths.intToList(4)));

	expected = Maths.intToList(32);
	assertEquals(expected, Maths.multiplyBig(Maths.intToList(4), Maths.intToList(8)));
	assertEquals(expected, Maths.multiplyBig(Maths.intToList(8), Maths.intToList(4)));

	expected = Maths.intToList(20);
	assertEquals(expected, Maths.multiplyBig(Maths.intToList(10), Maths.intToList(2)));
	assertEquals(expected, Maths.multiplyBig(Maths.intToList(2), Maths.intToList(10)));

	expected = Maths.intToList(24);
	assertEquals(expected, Maths.multiplyBig(Maths.intToList(12), Maths.intToList(2)));
	assertEquals(expected, Maths.multiplyBig(Maths.intToList(2), Maths.intToList(12)));

	expected = Maths.intToList(100);
	assertEquals(expected, Maths.multiplyBig(Maths.intToList(10), Maths.intToList(10)));

	expected = Maths.intToList(111);
	assertEquals(expected, Maths.multiplyBig(Maths.intToList(3), Maths.intToList(37)));
	assertEquals(expected, Maths.multiplyBig(Maths.intToList(37), Maths.intToList(3)));

	expected = Maths.intToList(650);
	assertEquals(expected, Maths.multiplyBig(Maths.intToList(25), Maths.intToList(26)));
	assertEquals(expected, Maths.multiplyBig(Maths.intToList(26), Maths.intToList(25)));

}


@Test @DisplayName("integer into English")
public void intToEnglishTest() {

	assertEquals("zero", Maths.toEnglish(0));

	assertEquals("one", Maths.toEnglish(1));
	assertEquals("ten", Maths.toEnglish(10));

	assertEquals("twenty one", Maths.toEnglish(21));
	assertEquals("sixty nine", Maths.toEnglish(69));

	assertEquals("six hundred", Maths.toEnglish(600));
	assertEquals("six hundred and six", Maths.toEnglish(606));
	assertEquals("six hundred and fifty two", Maths.toEnglish(652));

	assertEquals("one thousand", Maths.toEnglish(1000));
	assertEquals("one thousand, one", Maths.toEnglish(1001));
	assertEquals("one thousand, twenty two", Maths.toEnglish(1022));
	assertEquals("one thousand, three hundred and twenty two", Maths.toEnglish(1322));

	assertEquals("one million", Maths.toEnglish(1000000));
	assertEquals("one million, one", Maths.toEnglish(1000001));
	assertEquals("one million, twenty two", Maths.toEnglish(1000022));
	assertEquals("one million, three hundred and twenty two", Maths.toEnglish(1000322));
	assertEquals("one million, one thousand", Maths.toEnglish(1001000));
	assertEquals("one million, one thousand, three", Maths.toEnglish(1001003));
	assertEquals("two million, one thousand, twenty two", Maths.toEnglish(2001022));
	assertEquals("twenty million, two thousand, three hundred and twenty two", Maths.toEnglish(20002322));

}


@Test @DisplayName("integer into English, negative")
public void intToEnglishNegativeTest() {

	assertEquals("minus twenty one", Maths.toEnglish(-21));
	assertEquals("minus sixty nine", Maths.toEnglish(-69));

	assertEquals("minus six hundred", Maths.toEnglish(-600));
	assertEquals("minus six hundred and six", Maths.toEnglish(-606));
	assertEquals("minus six hundred and fifty two", Maths.toEnglish(-652));
	
}


@Test @DisplayName("Multiply without a * test")
public void sumRecTest() {

	assertEquals(0, Maths.multiply(0, 12));
	assertEquals(12, Maths.multiply(3, 4));

	assertEquals(3*17, Maths.multiply(3, 17));
	assertEquals(3*17, Maths.multiply(17, 3));
	assertEquals(13*17, Maths.multiply(13, 17));
	assertEquals(13*17, Maths.multiply(17, 13));

	assertEquals(113*27, Maths.multiply(113, 27));
	assertEquals(113*27, Maths.multiply(27, 113));

}

@Test @DisplayName("Add test")
public void addTest() {

	assertEquals(0, Maths.add(0, 0));
	assertEquals(12, Maths.add(0, 12));
	assertEquals(-1, Maths.add(0, -1));
	assertEquals(0, Maths.add(1, -1));
	assertEquals(14, Maths.add(7, 7));
	assertEquals(-14, Maths.add(-7, -7));
	assertEquals(111, Maths.add(133, -22));

}


@Test @DisplayName("Substract test")
public void substrac2tTest() {

	assertEquals(0, Maths.subtract2(0, 0));
	assertEquals(1, Maths.subtract2(3, 2));
	assertEquals(-3, Maths.subtract2(1, 4));

}


@Test @DisplayName("Multiply without a * test (2)")
public void multiply2Test() {

	assertEquals(0, Maths.multiply2(0, 0));
	assertEquals(0, Maths.multiply2(0, 12));
	assertEquals(12, Maths.multiply2(3, 4));

	assertEquals(3*17, Maths.multiply2(3, 17));
	assertEquals(3*17, Maths.multiply2(17, 3));
	assertEquals(13*17, Maths.multiply2(13, 17));
	assertEquals(13*17, Maths.multiply2(17, 13));

	assertEquals(113*27, Maths.multiply2(113, 27));
	assertEquals(113*27, Maths.multiply2(27, 113));

}


@Test @DisplayName("Divide without a / test")
public void divideTest() {
	assertThrows(ArithmeticException.class, () -> Maths.divide(10, 0));
	assertThrows(ArithmeticException.class, () -> Maths.divide(0, 0));
	assertEquals(1,Maths.divide(1, 1));
	assertEquals(2,Maths.divide(2, 1));
	assertEquals(2,Maths.divide(7, 3));
	assertEquals(2,Maths.divide(100, 50));

}


@Test @DisplayName("Mathematical expression test")
public void expressionTest() {

	assertEquals(1, Maths.evaluateExpression("1"));
	assertEquals(2, Maths.evaluateExpression("1+1"));
	assertEquals(0, Maths.evaluateExpression("1-1"));
	assertEquals(6, Maths.evaluateExpression("1+2+3"));

	assertEquals(1, Maths.evaluateExpression("(1)"));
	assertEquals(3, Maths.evaluateExpression("(1)+2"));
	assertEquals(9, Maths.evaluateExpression("(1+2)*3"));

	assertEquals(1, Maths.evaluateExpression("((1))"));
	assertEquals(18, Maths.evaluateExpression("((1+2)*2)*3"));
	assertEquals(13, Maths.evaluateExpression("1+((2*2)*3)"));

}


@Test @DisplayName("Sum of digits test")
public void sumOfDigitsTest() {
	assertEquals(0, Maths.sumOfDigits(0));
	assertEquals(7, Maths.sumOfDigits(7));
	assertEquals(1, Maths.sumOfDigits(10));
	assertEquals(2, Maths.sumOfDigits(128));
}


@Test @DisplayName("89 = 8^1+9^2 test")
public void sumDigitPowTest() {
	List<Long> v = Maths.sumDigitPow(1, 10);
	List<Long> e =java.util.Arrays.stream(new long[] {1, 2, 3, 4, 5, 6, 7, 8, 9}).boxed().collect(Collectors.toList());
	assertEquals(e, v);
	v = Maths.sumDigitPow(10, 150);
	e =java.util.Arrays.stream(new long[] {89,135}).boxed().collect(Collectors.toList());
}


@Test @DisplayName("to romannumerals test")
public void toRomanNumeralsTest() {
	assertEquals("I", Maths.toRomanNumberals(1));
	assertEquals("IV", Maths.toRomanNumberals(4));
	assertEquals("VI", Maths.toRomanNumberals(6));
	assertEquals("LXXXIX", Maths.toRomanNumberals(89));
	assertEquals("CDXCVIII", Maths.toRomanNumberals(498));
	assertEquals("CMLXXXIV", Maths.toRomanNumberals(984));
}



@Test
@DisplayName("bouncing ball test")
public void test1() {
	assertEquals(3, Maths.bouncingBall(3.0, 0.66, 1.5));
	assertEquals(15, Maths.bouncingBall(30.0, 0.66, 1.5));
}


@Test
@DisplayName("time readable test")
public void makeTimeReadableTest() {
	assertEquals("00:00:00", Maths.makeTimeReadable(0));
	assertEquals("00:00:05", Maths.makeTimeReadable(5));
	assertEquals("00:01:00", Maths.makeTimeReadable(60));
	assertEquals("23:59:59", Maths.makeTimeReadable(86399));
	assertEquals("99:59:59", Maths.makeTimeReadable(359999));
}


@Test
public void parseWordsToIntTest() {
	assertEquals(1, Maths.parseWordsToInt("one"));
	assertEquals(20, Maths.parseWordsToInt("twenty"));
	assertEquals(21, Maths.parseWordsToInt("twenty-one"));
	assertEquals(246, Maths.parseWordsToInt("two hundred and forty-six"));
	assertEquals(200001, Maths.parseWordsToInt("two hundred thousand and one"));
	assertEquals(783919, Maths.parseWordsToInt("seven hundred eighty-three thousand nine hundred and nineteen"));
}


@Test
public void josephusSurvivorTest() {
	assertEquals(4, Maths.josephusSurvivor(7, 3));
	// 1 2 3 4 5 6 7
	/*
	n=7, k = 10
	[1*,2,3,4,5,6,7] --> del 3
	[1,2,4*,5,6,7] --> del 7
	[1*,2,4,5,6] --> del 6
	[1*,2,4,5] --> del 2 
	[1,4*,5] --> del 4 
	[1,5*] --> del 1 
	[5]
	*/ 
	assertEquals(5, Maths.josephusSurvivor(7, 10));
	
	/*
	n=6, k=8
	[1*,2,3,4,5,6] -> del 2 (c=0, pos = 1) (8%6=2), r=6 --> 1 hop	, 6%8
	[1,3*,4,5,6] -> del 5   (c=1, pos = 3) (8%5=3), r=5 --> 2 hops
	[1,3,4,6*] -> del 4     (c=3, pos = 2) (8%4=0), r=4 --> 3 hops
	[1,3,6*] -> del 1       
	[3*, 6] -> del 6        
	[3]
	 */
	assertEquals(3, Maths.josephusSurvivor(6, 8));
	assertEquals(10, Maths.josephusSurvivor(11, 19));
	assertEquals(28, Maths.josephusSurvivor(40, 3));
	assertEquals(13, Maths.josephusSurvivor(14, 2));
	assertEquals(10, Maths.josephusSurvivor(10, 1));
	assertEquals(15, Maths.josephusSurvivor(344, 4756));
}


@Test
public void doubleEveryOtherTest() {
	assertArrayEquals(new int[] { 1, 4, 3, 8 }, Maths.doubleEveryOther(new int[] { 1, 2, 3, 4 }));
	assertArrayEquals(new int[] { 1, 38, 6, 4, 12, -6 }, Maths.doubleEveryOther(new int[] { 1, 19, 6, 2, 12, -3 }));
	assertArrayEquals(new int[] {-1000, 3306, 210, 0, 1}, Maths.doubleEveryOther(new int[] {-1000, 1653, 210, 0, 1}));
}


@Test
public void isInterestingNumberTest() {
	
	assertEquals(1, Maths.isInteresting(3208, new int[] { 1337, 256 }));
	assertEquals(2, Maths.isInteresting(3210, new int[] { 1337, 256 }));
	assertEquals(1, Maths.isInteresting(109, new int[] { 1337, 256 }));
	assertEquals(1, Maths.isInteresting(1232, new int[] { 1337, 256 }));
	assertEquals(2, Maths.isInteresting(4567, new int[] { 1337, 256 }));
	assertEquals(2, Maths.isInteresting(121, new int[] { 1337, 256 }));
	assertEquals(2, Maths.isInteresting(1221, new int[] { 1337, 256 }));
	assertEquals(2, Maths.isInteresting(1234567890, new int[] { 1337, 256 }));
	assertEquals(2, Maths.isInteresting(901, new int[] { 1337, 256 }));
	assertEquals(2, Maths.isInteresting(1234, new int[] { 1337, 256 }));
	assertEquals(2, Maths.isInteresting(123, new int[] { 1337, 256 }));
	assertEquals(0, Maths.isInteresting(3, new int[] { 1337, 256 }));
	assertEquals(1, Maths.isInteresting(1336, new int[] { 1337, 256 }));
	assertEquals(2, Maths.isInteresting(1337, new int[] { 1337, 256 }));
	assertEquals(0, Maths.isInteresting(11208, new int[] { 1337, 256 }));
	assertEquals(1, Maths.isInteresting(11209, new int[] { 1337, 256 }));
	assertEquals(2, Maths.isInteresting(11211, new int[] { 1337, 256 }));

}


@Test
public void test_0_10() {
  test(0, 10, 2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
}


private void test(int skip, int limit, int... expect) {
	int[] found = Maths.primeStream().skip(skip).limit(limit).toArray();
	assertArrayEquals(expect, found);
}


@Test
public void testChooseBestSum() {
	var ts = new ArrayList<>(java.util.Arrays.asList(50, 55, 56, 57, 58));
	int n = Maths.chooseBestSum(163, 3, ts);
	assertEquals(163, n);
	ts = new ArrayList<>(java.util.Arrays.asList(50));
	Integer m = Maths.chooseBestSum(163, 3, ts);
	assertEquals(null, m);
	ts = new ArrayList<>(java.util.Arrays.asList(91, 74, 73, 85, 73, 81, 87));
	n = Maths.chooseBestSum(230, 3, ts);
	assertEquals(228, n);
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
