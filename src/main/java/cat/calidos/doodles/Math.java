/*
 *    Copyright 2016 Daniel Giribet
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package cat.calidos.doodles;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
* @author daniel giribet
*//////////////////////////////////////////////////////////////////////////////
public class Math {


public static int multiplyWithPlus(int a, int b) {
	return IntStream.range(0, b)
			    .map(n -> a)
			    .sum();
}

public static int divideWithSubstract(double a, double b) {
	if (b==0) {
		throw new ArithmeticException("Cannot divide by zero");
	}
	int d = 0;
	while (a>=b) {
		a -= b;
		d++;
	}

	return d;

}


//8.1 triple step
//a child is running up a staircase with n steps, can hop 1, 2 or 3 steps
//how many possible ways can the child go up the stairs?

//step(n) = 	n==0 → 0
//		n==1 → 1
//		n==2 → 2
//		n==3 → [1,1,1],[1,2],[2,1],[3]:4 
//		n>3  → 1+step(n-1)+1+step(n-2)+1+step(n-3)

//we can do this recursively but cache the results so we do not recalculate everything

public static int steps(int n) {
	return _steps(n, new HashMap<Integer, Integer>(n));
}


private static int _steps(int n, Map<Integer, Integer> cache) {

	int steps;

	if (n==0) {
		steps = 0;
	} else if (n==1) {
		steps = 1;
	} else if (n==2) {
		steps = 2;
	} else if (n==3) {
		steps = 4;
	} else {
		steps = 3+_getStepsCached(n-1, cache)+_getStepsCached(n-2, cache)+_getStepsCached(n-3, cache);
	}

	return steps;

}


private static int _getStepsCached(int n, Map<Integer, Integer> cache) {

	int steps;

	if (cache.containsKey(n)) {
		steps = cache.get(n);		// O(k)
	} else {
		steps = _steps(n, cache);	// O(n)
		cache.put(n, steps);		// O(k)
	}

	return steps;

}


}
