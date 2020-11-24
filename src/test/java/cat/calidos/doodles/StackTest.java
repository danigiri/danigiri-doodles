
package cat.calidos.doodles;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class StackTest {


@Test
@DisplayName("Basic stack structure test")
public void stackTest() {

	Stack<String> s = new Stack<String>();
	assertEquals(0, s.height());
	assertTrue(s.isEmpty());

	s.push("a");
	assertEquals(1, s.height());
	assertEquals("a", s.peek());
	assertEquals("a", s.pop());
	assertEquals(0, s.height());

	s.push("a");
	s.push("b");
	assertEquals("b", s.peek());
	assertEquals(2, s.height());

}


@Test
@DisplayName("Basic stack structure test")
public void balancedParentheses() {

	assertTrue(Stack.areParenthesesBalanced(""));
	assertTrue(Stack.areParenthesesBalanced("()"));
	assertTrue(Stack.areParenthesesBalanced("(())"));
	assertTrue(Stack.areParenthesesBalanced("(()()(()))"));

	assertFalse(Stack.areParenthesesBalanced("(()))"));
	assertFalse(Stack.areParenthesesBalanced("(())("));

}


}

/**
Copyright 2014 Daniel Giribet <dani - calidos.cat>

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