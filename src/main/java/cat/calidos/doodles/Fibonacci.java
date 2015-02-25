/**
 Copyright 2015 Daniel Giribet <dani - calidos.cat>

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
package cat.calidos.doodles;


/**
 * @author daniel giribet
 */
/////////////////////////////////////////////////////////////////////////////
public class Fibonacci {

public static int fib(int n) {

	if (n<0) { // base cases
		throw new RuntimeException("Cannot calculate fibonacci of a negative nth position ("+n+")");
	} else if (n<2) {
		return 1;
	} else {	// recursive case (n>=2)
		return fib(n-2)+fib(n-1);
	}

}

}
