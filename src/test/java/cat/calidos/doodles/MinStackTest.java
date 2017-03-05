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
package cat.calidos.doodles;

import static org.junit.Assert.*;

import org.junit.Test;


public class MinStackTest {

@Test
public void minStackTest() {
	
	MinStack<Integer> stack = new MinStack<Integer>(1);
	assertEquals(new Integer(1), stack.min());
	
	stack.push(3);
	assertEquals(new Integer(1), stack.min());
	stack.push(3);
	assertEquals(new Integer(1), stack.min());

	stack.push(0);
	assertEquals(new Integer(0), stack.min());

	stack.pop();
	assertEquals(new Integer(1), stack.min());
}

@Test
public void minStackMultipleMinimumsTest() {
	
	MinStack<Integer> stack = new MinStack<Integer>(1);
	
	stack.push(1);
	assertEquals(new Integer(1), stack.min());

	stack.pop();
	assertEquals(new Integer(1), stack.min());

	stack.push(2);
	assertEquals(new Integer(1), stack.min());

	stack.push(1);
	assertEquals(new Integer(1), stack.min());
	
	stack.pop();
	assertEquals(new Integer(1), stack.min());

}

}
