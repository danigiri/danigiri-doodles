
package cat.calidos.doodles;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class StackTest {


@Test @DisplayName("Basic stack structure test")
public void stackTest() {

	Stack<String> s = new Stack<String>();
	assertEquals(0, s.height());
	assertTrue(s.isEmpty());

	s.push("a");
	assertEquals(1, s.height());
	assertEquals("a", s.peek());
	assertEquals("a", s.pop());
	assertEquals(0, s.height());

	s.push("a"	);
	s.push("b");
	assertEquals("b", s.peek());
	assertEquals(2, s.height());

}


@Test @DisplayName("Basic stack structure test")
public void balancedParentheses() {

	assertTrue(Stack.areParenthesesBalanced(""));
	assertTrue(Stack.areParenthesesBalanced("()"));
	assertTrue(Stack.areParenthesesBalanced("(())"));
	assertTrue(Stack.areParenthesesBalanced("(()()(()))"));

	assertFalse(Stack.areParenthesesBalanced("(()))"));
	assertFalse(Stack.areParenthesesBalanced("(())("));

}


@Test @DisplayName("Check equals")
public void equalsTest() {

	Stack<String> s0 = new Stack<String>();
	s0.push("a");
	s0.push("b");
	s0.push("a");
	s0.push("c");
	Stack<String> s1 = new Stack<String>();
	s1.push("a");
	s1.push("b");
	s1.push("a");
	s1.push("c");
	assertTrue(s0.equals(s1));

	Stack<String> s2 = new Stack<String>();
	Stack<String> s3 = new Stack<String>();
	assertTrue(s2.equals(s3));

	Stack<String> s4 = new Stack<String>();
	s4.push("a");
	s4.push("b");
	s4.push("a");
	s4.push("c");
	Stack<String> s5 = new Stack<String>();
	s5.push("a");
	s5.push("b");
	s5.push("a");
	assertFalse(s4.equals(s5));

	Stack<String> s6 = new Stack<String>();
	s4.push("a");
	Stack<String> s7 = new Stack<String>();
	s5.push("a");
	assertTrue(s6.equals(s7));
}


@Test @DisplayName("Check toString override")
public void toStringTest() {

	Stack<String> empty = new Stack<String>();
	String expected = "[]";
	assertEquals(expected, empty.toString());

	Stack<String> s0 = new Stack<String>();
	s0.push("a");
	s0.push("b");
	s0.push("c");
	s0.push("d");
	expected = "[top][d,c,b,a]";
	assertEquals(expected, s0.toString());

}


@Test @DisplayName("Check reverse stack")
public void testReverse() {

	Stack<String> s0 = new Stack<String>();
	s0.push("a");
	s0.push("b");
	s0.push("c");
	s0.push("d");
	Stack<String> expected = new Stack<String>();
	expected.push("d");
	expected.push("c");
	expected.push("b");
	expected.push("a");
	assertEquals(expected, Stack.reverse(s0));

	Stack<String> s1 = new Stack<String>();
	expected = new Stack<String>();
	assertEquals(expected, Stack.reverse(s1));

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