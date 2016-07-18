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
package cat.calidos.doodles;

import static org.junit.Assert.*;

import org.junit.Test;


public class LinkedListTest {


@Test
public void testLinkedList() {
	
	LinkedList<String> l0 = new LinkedList<String>("a");
	assertEquals("a", l0.data);
	
	l0.next = new LinkedList<String>("b");
	assertEquals(2, l0.length());

	l0.next.next = new LinkedList<String>("c");
	assertEquals(3, l0.length());

}


@Test
public void nthToLastTest() {
	
	LinkedList<String> l = new LinkedList<String>("a");

	assertNull(LinkedList.nthToLast(1, l));
	assertEquals("a", LinkedList.nthToLast(0, l).data);
	
	l.next = new LinkedList<String>("b");
	assertEquals("b", LinkedList.nthToLast(0, l).data);
	assertEquals("a", LinkedList.nthToLast(1, l).data);
	assertNull(LinkedList.nthToLast(2, l));

	l.next.next = new LinkedList<String>("c");
	l.next.next.next = new LinkedList<String>("d");
	
	// [ a, b, c, d ]
	assertEquals("d", LinkedList.nthToLast(0, l).data);
	assertEquals("c", LinkedList.nthToLast(1, l).data);
	assertEquals("b", LinkedList.nthToLast(2, l).data);
	assertEquals("a", LinkedList.nthToLast(3, l).data);
	assertNull(LinkedList.nthToLast(4, l));
	
}

@Test
public void removeDupesTest() {
	
	LinkedList<String> l = new LinkedList<String>("a");
	l.next = new LinkedList<String>("b");
	l.next.next = new LinkedList<String>("b");
	l.next.next.next = new LinkedList<String>("c");

	LinkedList<String> l2 = new LinkedList<String>("a");
	l2.next = new LinkedList<String>("b");
	l2.next.next = new LinkedList<String>("c");

	assertEquals(l2, LinkedList.removeDupes(l));
}

}