// MATH . JAVA

package cat.calidos.doodles;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @author daniel giribet
 *//////////////////////////////////////////////////////////////////////////////
public class Maths {


public static int multiplyWithPlus(int a, int b) {
	return IntStream.range(0, b).map(n -> a).sum();
}


public static int divideWithSubstract(double a, double b) {

	if (b == 0) {
		throw new ArithmeticException("Cannot divide by zero");
	}
	int d = 0;
	while (a >= b) {
		a -= b;
		d++;
	}

	return d;

}


// 8.1 triple step
// a child is running up a staircase with n steps, can hop 1, 2 or 3 steps
// how many possible ways can the child go up the stairs?

// step(n) = n==0 → 0
// n==1 → 1
// n==2 → 2
// n==3 → [1,1,1],[1,2],[2,1],[3]:4
// n>3 → 1+step(n-1)+1+step(n-2)+1+step(n-3)

// we can do this recursively but cache the results so we do not recalculate everything


public static int steps(int n) {
	return _steps(n, new HashMap<Integer, Integer>(n));
}


private static int _steps(int n, Map<Integer, Integer> cache) {

	int steps;

	if (n == 0) {
		steps = 0;
	} else if (n == 1) {
		steps = 1;
	} else if (n == 2) {
		steps = 2;
	} else if (n == 3) {
		steps = 4;
	} else {
		steps = 3 + _getStepsCached(n - 1, cache) + _getStepsCached(n - 2, cache) + _getStepsCached(n - 3, cache);
	}

	return steps;

}


private static int _getStepsCached(int n, Map<Integer, Integer> cache) {

	int steps;

	if (cache.containsKey(n)) {
		steps = cache.get(n); // O(k)
	} else {
		steps = _steps(n, cache); // O(n)
		cache.put(n, steps); // O(k)
	}

	return steps;

}


// 16.5 write and algorithm that computes the number of trailing zeros in n factorial

// f(n) =n*f(n-1)

// naive implementation: compute factorial and then do an iteration of divisions and modulus
//


private static int factorial(int n) {

	if (n == 1) {
		return 1;
	}

	int f = n * factorial(n - 1);

	return f;

}


public static int trailingZerosFactorialSmall(int n) {

	int f = factorial(n);

	int zeros = 0;
	while ((f % 10) == 0) {
		zeros++;
		f = f / 10;
	}

	return zeros;

}

// it works but we run into int/long limits quickly
// f(10) = 10*9*8*7*6*5*4*3*2*1 = f(9)*10 = 10*10*[9*8*7*6*4*3]
/*
 * trailing(10) 2+trailing(9*8*7*6*4*3) f(n) = f<=10 → factorial(10) f>10 → n*f(n-1)
 * 
 * f(11) = (1+10)*f(9) = f(9)+10*f(9)
 * 
 * 
 * f(15)= 15*14*13*12*11*10*9*8*7*6*5*4*3*2*1 f(15) = (10+5)*(10+4)*(10+3)*(10+2)*(10+1)*f(10) f(15)
 * = (10)*f(14)+5*f(14) f(2) =
 * 
 * f(n) = n<=10 → factorial(10) // precomputed n>10 → [factorial(n-1), 0] +(n % 10)*factorial(n-1)
 * // recursive + precomputed
 */


public static int trailingZeros(int n) {

	java.util.LinkedList<Integer> f = factorialBig2(n);
	// System.err.println(f);
	int zeros = 0;
	while (!f.isEmpty() && f.pollFirst() == 0) {
		zeros++;
	}

	return zeros;

}


private static int factorialTen(int n) {
	int f = 1;
	for (int i = n; i > 0; i--) {
		f *= i;
	}

	return f;

}


public static java.util.LinkedList<Integer> intToList(int n) {

	java.util.LinkedList<Integer> list = new java.util.LinkedList<Integer>();

	if (n == 0) {
		list.add(0);
	}

	while (n != 0) {
		list.add(n % 10);
		n /= 10;
	}

	return list;

}


private static java.util.LinkedList<Integer> factorialBig(int n) {

	java.util.LinkedList<Integer> f;
	if (n <= 10) {
		f = intToList(factorialTen(n));
	} else {
		java.util.LinkedList<Integer> first = factorialBig(n - 1);
		first.addFirst(0);
		java.util.LinkedList<Integer> second = multiply(n % 10, first);
		f = sum(first, second);
	}

	return Maths.clone(f);

}


private static java.util.LinkedList<Integer> factorialBig2(int n) {

	return (n == 1) ? Maths.intToList(1) : Maths.multiplyBig(intToList(n), factorialBig2(n - 1));

}


private static java.util.LinkedList<Integer> clone(java.util.LinkedList<Integer> l) {
	return l.stream().collect(LinkedList::new, LinkedList::add, LinkedList::addAll);

}


public static java.util.LinkedList<Integer> multiply(int n, java.util.LinkedList<Integer> l) {


	// n is guaranteed to be <10
	java.util.LinkedList<Integer> r = new java.util.LinkedList<Integer>();

	if (n == 0) {
		r.add(0);
		return r;
	}

	int carryOver = 0;
	while (!l.isEmpty()) {
		int mult = (l.pollFirst() * n) + carryOver;
		r.add(mult % 10);
		carryOver = mult / 10;
	}
	;

	if (carryOver > 0) {
		r.add(carryOver);
	}

	return r;

}


public static LinkedList<Integer> multiplyBig(java.util.LinkedList<Integer> a, java.util.LinkedList<Integer> b) {

	java.util.LinkedList<Integer> result = new java.util.LinkedList<Integer>();
	int shift = 0;

	while (!a.isEmpty()) {

		int current = a.pollFirst();
		LinkedList<Integer> m = current != 0 ? multiply(current, Maths.clone(b)) : intToList(0);
		for (int s = 0; s < shift; s++) { // shift op
			m.addFirst(0);
		}
		result = sum(result, m);
		shift++;
	}

	return result;

}


// [3, 2] * 5
// 3*5 = 15, co = 1, c = 5, list=[5]
// 2*5+1 = 11, co = 1, c=1, list=[5,1]
// list=[5,1,1]


public static java.util.LinkedList<Integer> sum(java.util.LinkedList<Integer> a, java.util.LinkedList<Integer> b) {

	java.util.LinkedList<Integer> s = new java.util.LinkedList<Integer>();
	int carryOver = 0;
	while (!a.isEmpty() || !b.isEmpty()) {
		int currentA = !a.isEmpty() ? a.pollFirst() : 0;
		int currentB = !b.isEmpty() ? b.pollFirst() : 0;
		int current = currentA + currentB + carryOver;
		s.add(current % 10);
		carryOver = current / 10;
	}

	if (carryOver > 0) {
		s.add(carryOver);
	}

	return s;

}


public static java.util.LinkedList<Integer> substract(java.util.LinkedList<Integer> a, int n) {

	java.util.LinkedList<Integer> result = new java.util.LinkedList<Integer>();

	java.util.LinkedList<Integer> a2 = clone(a);

	int carryOver = n;
	while (!a2.isEmpty() && carryOver != 0) {
		int current = a2.pollFirst() - carryOver;
		if (current <= 0 && a2.isEmpty() && result.isEmpty()) { // one digit, add
			result.add(current);
		} else if (current == 0 && a2.isEmpty() && !result.isEmpty()) { // not add zero, noop
		} else if (current > 0) { // no carry over, clone rest of list
			result.add(current);
			a2.stream().forEachOrdered(e -> result.add(e));
			carryOver = 0; // stops loop
		} else { // carry over, continue
			current = current + 10;
			result.add(current);
			carryOver = 1;
		}
	}

	return result;

}


// 16.8 given any integer, write a function that prints the description of the integer in english
// terms. 1034 → "One thousand, thirty four"
// we will go up to a million, but billions and trillions should follow the same approach


private static int		MILLION			= 1000000;
private static String	MILLION_ENG		= " million";
private static int		THOUSAND		= 1000;
private static String	THOUSAND_ENG	= " thousand";
private static int		HUNDRED			= 100;
private static String	HUNDRED_ENG		= " hundred";
private static String	AND				= " and";
private static String	COMMA			= ",";
private static int		TWENTY			= 20;
private static int		TEN				= 10;
private static String	underTwenty[]	= { "", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
		"ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen" };
private static String	tens[]			= { null, null, "twenty", "thirty", "forty", "fifty", "sixty", "seventy",
		"eighty", "ninety" };

public static String toEnglish(int n) {

	if (n == 0) {
		return "zero";
	}
	String s = "";
	if (n < 0) {
		s = "minus ";
		n = -n;
	}

	s += toEnglishBase(n);

	return s;

}


private static String toEnglishBase(int n) {

	String s = "";
	if (n == 0) { // base cases
		// no op
	} else if (n < TWENTY) {
		return underTwenty[n];
	} else if (n < HUNDRED) { // recursive case 1
		s = tens[n / TEN];
		String rest = toEnglishBase(n % TEN);
		s = rest.length() > 0 ? s + " " + rest : s;
	} else { // recursive case 2
		int currentBase = 1;
		String currentBaseString = "";
		if (n / MILLION > 0) {
			currentBase = MILLION;
			currentBaseString = MILLION_ENG;
		} else if (n / THOUSAND > 0) {
			currentBase = THOUSAND;
			currentBaseString = THOUSAND_ENG;
		} else if (n / HUNDRED > 0) {
			currentBase = HUNDRED;
			currentBaseString = HUNDRED_ENG;
		}
		s = toEnglishBase(n / currentBase) + currentBaseString;
		String rest = toEnglishBase(n % currentBase);
		if (rest.length() > 0) {
			if (currentBase == HUNDRED) {
				s += AND;
			} else if (currentBase == MILLION || currentBase == THOUSAND) {
				s += COMMA;
			}
			s += " " + rest;
		}

	}

	return s;

}

// 4.8 recursive multiply – write a function to multiply two positive integers without using *
// you can use the other arithmetic operations: add, subtract, shift but minimise their usage

// 23 * 11 = eleven times +23, too many +'s
// times(n, k) == 2*times(n/2, k) + if (n%2==1) +k


public static int multiply(int a, int b) {
	return (a < b) ? sumTimes(b, a) : sumTimes(a, b);
}


private static int sumTimes(int k, int times) {

	if (times == 0) {
		return 0;
	}
	if (times == 1) {
		return k;
	}
	// recursive case
	int half = times >> 1;
	int halfSum = sumTimes(half, k);
	int sum = halfSum << 1;
	// now find out if we have to add k
	int odd = times << 31;
	if (odd != 0) {
		sum += k;
	}
	return sum;
}


/*
 * implement multiply, divide and subtract operation for integers, with only addition, and no
 * substraction
 */


public static int add(int a, int b) {
	if (a > 0 && b > 0) {
		return a + b;
	} else if (a < 0 && b < 0) {
		return -(Math.abs(a) + Math.abs(a));
	} else if (a >= 0 && b < 0) {
		return subtract2(a, -b);
	} else {
		return subtract2(b, -a);
	}
}

/*
 * p=0 n=-1
 * 
 */


public static int subtract2(int p, int n) {
	// p is positive, n is negative
	var s = 0;
	if (p == n) {
	} else if (p > n) {
		for (int i = n; i < p; i++) {
			s++;
		}
	} else {
		for (int i = p; i < n; i++) {
			s++;
		}
		s = -s;
	}
	return s;
}


public static int multiply2(int a, int b) {

	if (a == 0 || b == 0) {
		return 0;
	}
	var m = 0;
	boolean isNegative = (a < 0 || b < 0);
	a = Math.abs(a);
	b = Math.abs(b);
	for (int i = 0; i < b; i++) {
		m = add(m, a);
	}
	return isNegative ? -m : m;
}


public static int divide(int n, int d) {
	if (d == 0) {
		throw new ArithmeticException("Cannot divide by zero");
	}
	boolean isNegative = (n <= 0 && d > 0) || (n > 0 && d < 0);
	n = Math.abs(n);
	d = Math.abs(d);
	var div = 0;
	while (n >= d) {
		n = subtract2(n, d);
		div++;
	}
	return isNegative ? -div : div;
}


/*
 * 
 * given an arithmetic expression '(' '+', '-', and '*', calculate the outcome
 * 
 * /—-------| | | V | [digit] -/ | | <stack digit on operand stack> V [operation]
 * 
 * 
 * if digit → add digit to operand stack if operand -> add operand to stack if ( → stack and rec(,
 * add return to stack if ) -> consume stack until finding (, consume it and return
 * 
 * 
 * (1-2)*3
 * 
 * 2 - 1 ( —---, —---
 * 
 * consume stack
 * 
 * 3 -1 * —---, —---
 * 
 * ((1-2)*3)+1
 * 
 * 
 * - 3 ( 1 ( —---, —---
 * 
 * 
 * 
 * 
 */


public static int evaluateExpression(String e) {
	var operands = new Stack<Integer>();
	var ops = new Stack<Character>();
	evaluateExpression(e, 0, operands, ops);
	return operands.pop();
}


public static int evaluateExpression(String e, int i, Stack<Integer> operands, Stack<Character> ops) {

	while (i < e.length()) {
		char c = e.charAt(i);
		if (c == '(') {
			ops.push(c);
			i = evaluateExpression(e, i + 1, operands, ops);
		} else if (c == ')') {
			// this means we have a complete evaluable expression in the stack until we find a
			// stacked '('
			consumeExpression(operands, ops);
			ops.pop(); // remove the '('
			return i;
		} else if (c == '+' || c == '-' || c == '*') {
			ops.push(c);
		} else {
			var val = 0;
			while (i < e.length() && "0123456789".contains(String.valueOf(e.charAt(i)))) {
				val = val * 10 + Integer.parseInt(String.valueOf(e.charAt(i++)));
			}
			operands.push(val);
			i--;
		}
		i++;
	}
	consumeExpression(operands, ops);
	return i;
}


public static void consumeExpression(Stack<Integer> operands, Stack<Character> ops) {
	// we consume until we finish the stack or find a "(", which we pop out
	while (!ops.isEmpty() && !ops.peek().equals('(')) {
		var val = 0;
		char op = ops.pop();
		int left = operands.pop();
		int right = operands.pop();
		if (op == '+') {
			val = left + right;
		} else if (op == '-') {
			val = left - right;
		} else if (op == '*') {
			val = left * right;
		} else {
			throw new UnsupportedOperationException("Incorrect operation");
		}
		operands.push(val);
	}
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
