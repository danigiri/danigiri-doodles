// MATH . JAVA

package cat.calidos.doodles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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


/*

given a positive integer, return the integer that is the sum of its digits, down to a single digit
  128 --> 11 --> 2 
  0 --> 0
  10 --> 1

  while result is >9, call
  we can do recursive, if current is <=9 --> return
  if not aggregate and call

 */


 public static int sumOfDigits(int n) {
  if (n<=9) {
    return n;
  }
  var c = 0;
  while (n!=0) {
    c += n % 10;
    n /= 10;
  }
  return sumOfDigits(c);
 }


 /*
 The number  89 is the first integer with more than one digit that fulfills the propertyof this sum returning
 the same number:  89=8^1+9^2
 
 The next number in having this property is 135
 See this property again: 135=1^1+3^2+5^3

do a while between the two ranges
have an expoential starting with number of digits
divide / 10
decrement exponential


*/
 

	public static List<Long> sumDigitPow(long a, long b) { // 1 10
		var out = new ArrayList<Long>(); // []
		for (long i = a; i <= b; i++) { // i=1
			var current = i;
			var exp = numberOfDigits(current);
			var sum = 0;
			while (current > 0) {
				sum += Math.pow(current % 10, exp);
				current = current / 10;
				exp--;
			}
			if (sum == i) {
				out.add(i);
			}
		}
		return out;
	}


	private static int numberOfDigits(long n) { // 1
		var num = 0;
		while (n != 0) { // 1 +
			num++;
			n = n / 10;
		}
		return num;
	}
 
	/*
	 * Create a function taking a positive integer between 1 and 3999 (both included) as its
	 * parameter and returning a string containing the Roman Numeral representation of that integer.
	 * Modern Roman numerals are written by expressing each digit separately starting with the
	 * leftmost digit and skipping any digit with a value of zero. There cannot be more than 3
	 * identical symbols in a row.
	 * 
	 * In Roman numerals:
	 * 
	 * 1990 is rendered: 1000=M + 900=CM + 90=XC; resulting in MCMXC. 2008 is written as 2000=MM,
	 * 8=VIII; or MMVIII. 1666 uses each Roman symbol in descending order: MDCLXVI.
	 * 
	 * Symbol Value I 1 V 5 X 10 L 50 C 100 D 500 M 1,000
	 * 
	 * let's try a recursive approach first get a stack of digits starting from the right get number
	 * at the top substract values from the table until it's zero append those values pop and
	 * recurse
	 */


	public static String toRomanNumberals(int n) { // 4
		var digits = new Stack<Integer>();
		var base = 0;
		while (n > 0) { // 4>0
			var c = n % 10; // c = 4
			c = base == 0 ? c : c * base; // c = 10
			digits.push(c); // [1, 10]
			n = n / 10; // n = 1, 0
			base = base == 0 ? 10 : base * 10; // 100
		}
		return toRomanNumeralsR(new StringBuffer(), digits).toString();
	}


	private static StringBuffer toRomanNumeralsR(StringBuffer b, Stack<Integer> digits) {
		if (digits.isEmpty()) { // [1,10]
			return b;
		}
		int current = digits.pop(); // 10
		toRomanNumeralsTable(b, current);
		return toRomanNumeralsR(b, digits);
	}


	private static void toRomanNumeralsTable(StringBuffer out, int digit) {
		// this is a single digit, which is guaranteed to end in zero
		// order is important
		while (digit > 0) {
			if (digit < 4) {
				out.append("I");
				digit--;
			} else if (digit == 4) { // exception
				out.append("IV");
				digit = 0;
			} else if (digit == 9) {
				out.append("IX");
				digit = 0;
			} else if (digit < 10) {
				out.append("V");
				digit -= 5;
			} else if (digit >= 40 && digit < 50) { // exception
				out.append("XL");
				digit -= 40;
			} else if (digit < 50) {
				out.append("X");
				digit -= 10;
			} else if (digit >= 90 && digit < 100) { // exception
				out.append("XC");
				digit -= 90;
			} else if (digit < 100) {
				out.append("L");
				digit -= 50;
			} else if (digit >= 400 && digit < 500) { // exception
				out.append("CD");
				digit -= 400;
			} else if (digit < 500) {
				out.append("C");
				digit -= 100;
			} else if (digit >= 900 && digit < 1000) {
				out.append("CM");
				digit -= 900;
			} else if (digit < 1000) {
				out.append("D");
				digit -= 500;
			} else {
				out.append("M");
				digit -= 1000;
			}
		}
	}
	
	
	/*
	 * 
	 * A child is playing with a ball on the nth floor of a tall building. The height of this floor
	 * above ground level, h, is known. He drops the ball out of the window. The ball bounces (for
	 * example), to two-thirds of its height (a bounce of 0.66). His mother looks out of a window
	 * 1.5 meters from the ground.
	 * 
	 * How many times will the mother see the ball pass in front of her window (including when it's
	 * falling and bouncing)?
	 * 
	 * Three conditions must be met for a valid experiment: - Float parameter "h" in meters must be
	 * greater than 0 - Float parameter "bounce" must be greater than 0 and less than 1 - Float
	 * parameter "window" must be less than h. If all three conditions above are fulfilled, return a
	 * positive integer, otherwise return -1.
	 * 
	 * Note: The ball can only be seen if the height of the rebounding ball is strictly greater than
	 * the window parameter.
	 * 
	 * Examples: - h = 3, bounce = 0.66, window = 1.5, result is 3
	 * 
	 * - h = 3, bounce = 1, window = 1.5, result is -1
	 * 
	 * (Condition 2) not fulfilled).
	 * 
	 * first we check the conditions secondly we calculate we can recurse by changing the h, namely,
	 * the bounce parameter is the new max height we need to take into account that after the first
	 * bounce, we do not return -1
	 * 
	 */


	public static int bouncingBall(double h, double bounce, double window) { // h=3, b=0.66, w=1.5
		// check parameters first
		if (h < 0 || bounce <= 0 || bounce >= 1 || window >= h) {
			return -1;
		}
		return 1 + bouncingBallR(h, bounce, window); // first drop 1+(2+(0))
	}


	private static int bouncingBallR(double h, double bounce, double window) {// h=3,1.98 b=0.66,
																				// w=1.5
		var bounceHeight = h * bounce; // 1.98, 1.3
		if (bounceHeight > window) {
			return 2 + bouncingBallR(bounceHeight, bounce, window);
		}
		return 0;
	}

	/*
	 * takes a non-negative integer (seconds) as input and returns the time in a human-readable
	 * format (HH:MM:SS) HH = hours, padded to 2 digits, range: 00 - 99 MM = minutes, padded to 2
	 * digits, range: 00 - 59 SS = seconds, padded to 2 digits, range: 00 - 59 The maximum time
	 * never exceeds 359999 (99:59:59)
	 */


	public static String makeTimeReadable(int seconds) {
		var h = seconds / 3600;
		var m = (seconds - h * 3600) / 60;
		var s = seconds - (h * 3600) - (m * 60);
		return (h > 9 ? "" : "0") + Integer.toString(h) + ":" + (m > 9 ? "" : "0") + Integer.toString(m) + ":"
				+ (s > 9 ? "" : "0") + Integer.toString(s);
	}
	
	/*
	 * 
	 * convert a string into an integer. The strings simply represent the numbers in words.
	 * Examples:
	 * 
	 * "one" => 1 "twenty" => 20 "two hundred forty-six" => 246
	 * "seven hundred eighty-three thousand nine hundred and nineteen" => 783919 Additional Notes:
	 * 
	 * The minimum number is "zero" (inclusively) The maximum number, which must be supported is 1
	 * million (inclusively) The "and" in e.g. "one hundred and twenty-four" is optional, in some
	 * cases it's present and in others it's not All tested numbers are valid, you don't need to
	 * validate them
	 * 
	 * val = 0 cases, start from right digit, mag=X --> val+=digit*mag newmag, newmag>mag -->
	 * mag=100 newmag, newmag<=mag --> newmag*100
	 * 
	 */


	public static int parseWordsToInt(String numStr) {

		var digits = new HashMap<String, Integer>();
		digits.put("zero", 0);
		digits.put("one", 1);
		digits.put("two", 2);
		digits.put("three", 3);
		digits.put("four", 4);
		digits.put("five", 5);
		digits.put("six", 6);
		digits.put("seven", 7);
		digits.put("eight", 8);
		digits.put("nine", 9);
		digits.put("ten", 10);
		digits.put("eleven", 11);
		digits.put("twelve", 12);
		digits.put("thirteen", 13);
		digits.put("fourteen", 14);
		digits.put("fifteen", 15);
		digits.put("sixteen", 16);
		digits.put("seventeen", 17);
		digits.put("eighteen", 18);
		digits.put("nineteen", 19);
		digits.put("twenty", 20);
		digits.put("thirty", 30);
		digits.put("forty", 40);
		digits.put("fifty", 50);
		digits.put("sixty", 60);
		digits.put("seventy", 70);
		digits.put("eighty", 80);
		digits.put("ninety", 90);
		digits.put("hundred", 100);
		digits.put("thousand", 1000);
		digits.put("million", 1000000);

		var value = 0;
		var mag = 1;
		List<String> words = stringToWords(numStr);
		for (var i = words.size() - 1; i >= 0; i--) {
			var w = words.get(i);
			if (w.equals("and")) {
				continue;
			}
			var current = wordToNumber(w, digits);
			if (current < 100) {
				value += current * mag;
			} else if (current > mag) {
				mag = current;
			} else { // hundred thousand
				mag = mag * current;
			}
		}

		return value;
	}


	public static List<String> stringToWords(String s) {
		var words = new ArrayList<String>();
		StringBuffer w = null;
		for (int i = 0; i < s.length(); i++) {
			var c = s.charAt(i);
			if (c == ' ') {
				if (w != null) {
					words.add(w.toString());
					w = null;
				}
			} else {
				if (w == null) {
					w = new StringBuffer();
				}
				w.append(c);
			}
		}
		if (w != null) { // last word
			words.add(w.toString());
		}
		return words;
	}


	public static int wordToNumber(String s, Map<String, Integer> digits) {
		int i = s.indexOf("-");
		return (i >= 0) ? digits.get(s.substring(0, i)) + digits.get(s.substring(i + 1)) : digits.get(s);
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
