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

}
