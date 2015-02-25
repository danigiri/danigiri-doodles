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

import static org.junit.Assert.*;

import org.junit.Test;


public class FibonacciTest {

@Test
public void testFibonacci() {

	assertEquals(1, Fibonacci.fib(0));
	
	assertEquals(1, Fibonacci.fib(1));
	
	assertEquals(2, Fibonacci.fib(2));
	
	assertEquals(3, Fibonacci.fib(3));

	assertEquals(5, Fibonacci.fib(4));

	assertEquals(8, Fibonacci.fib(5));
	
	assertEquals(Fibonacci.fib(9)+Fibonacci.fib(10), Fibonacci.fib(11));
	
}

}
