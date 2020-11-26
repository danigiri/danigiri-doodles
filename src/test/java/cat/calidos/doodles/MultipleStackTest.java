package cat.calidos.doodles;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


/**
*	@author daniel giribet
*///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class MultipleStackTest {


@Test
@DisplayName("Test edge cases")
void testEdgeCases() {

	assertThrows(IndexOutOfBoundsException.class, () -> new MultipleStack<Object>().pop0());
	assertThrows(IndexOutOfBoundsException.class, () -> new MultipleStack<Object>().pop1());
	assertThrows(IndexOutOfBoundsException.class, () -> new MultipleStack<Object>().pop2());

}


@Test
@DisplayName("Test basic cases")
void testMultipleStackBasic() {

	MultipleStack<String> multipleStack = new MultipleStack<String>();
	multipleStack.push0("0");
	assertThrows(IndexOutOfBoundsException.class, () -> multipleStack.pop1());
	assertThrows(IndexOutOfBoundsException.class, () -> multipleStack.pop2());

	multipleStack.push1("a");
	assertThrows(IndexOutOfBoundsException.class, () -> multipleStack.pop2());

	multipleStack.push2("x");
	assertEquals("x", multipleStack.pop2());
	assertThrows(IndexOutOfBoundsException.class, () -> multipleStack.pop2());

	assertEquals("a", multipleStack.pop1());
	assertThrows(IndexOutOfBoundsException.class, () -> multipleStack.pop1());

	assertEquals("0", multipleStack.pop0());
	assertThrows(IndexOutOfBoundsException.class, () -> multipleStack.pop0());

}


@Test
@DisplayName("Test complex cases")
void testMultipleStack() {

	MultipleStack<String> multipleStack = new MultipleStack<String>();
	multipleStack.push0("0");
	multipleStack.push0("1");
	multipleStack.push0("2");
	assertThrows(IndexOutOfBoundsException.class, () -> multipleStack.pop1());
	assertThrows(IndexOutOfBoundsException.class, () -> multipleStack.pop2());

	multipleStack.push1("a");
	multipleStack.push1("b");
	multipleStack.push1("c");
	assertThrows(IndexOutOfBoundsException.class, () -> multipleStack.pop2());

	multipleStack.push2("x");
	multipleStack.push2("y");
	multipleStack.push2("z");

	assertEquals("2", multipleStack.pop0());
	assertEquals("c", multipleStack.pop1());
	assertEquals("z", multipleStack.pop2());

	assertEquals("y", multipleStack.pop2());
	assertEquals("b", multipleStack.pop1());
	assertEquals("1", multipleStack.pop0());

	assertEquals("0", multipleStack.pop0());
	assertEquals("a", multipleStack.pop1());
	assertEquals("x", multipleStack.pop2());

	assertThrows(IndexOutOfBoundsException.class, () -> multipleStack.pop0());
	assertThrows(IndexOutOfBoundsException.class, () -> multipleStack.pop1());
	assertThrows(IndexOutOfBoundsException.class, () -> multipleStack.pop2());


}

}


/*
 *    Copyright 2020 Daniel Giribet
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

