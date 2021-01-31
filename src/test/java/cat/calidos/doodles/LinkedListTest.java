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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cat.calidos.doodles.builders.LinkedListFrom;

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


@Test @DisplayName("Linked list kth element test")
public void kthElementTest() {

	assertTrue(LinkedList.kthElement(null, 0).isEmpty());

	LinkedList<String> l = new LinkedList<String>("a");
	assertTrue(LinkedList.kthElement(l, 1).isEmpty());
	assertEquals("a", LinkedList.kthElement(l, 0).get());
	assertTrue(LinkedList.kthElement(l, 5).isEmpty());

	l.next = new LinkedList<String>("b");
	assertEquals("b", LinkedList.kthElement(l, 0).get());
	assertEquals("a", LinkedList.kthElement(l, 1).get());
	assertTrue(LinkedList.kthElement(l, 5).isEmpty());

	l.next.next = new LinkedList<String>("c");
	l.next.next.next = new LinkedList<String>("d");
	l.next.next.next.next = new LinkedList<String>("e");
	assertEquals("e", LinkedList.kthElement(l, 0).get());
	assertEquals("c", LinkedList.kthElement(l, 2).get());
	assertEquals("a", LinkedList.kthElement(l, 4).get());
	assertTrue(LinkedList.kthElement(l, 15).isEmpty());

}


@Test @DisplayName("Reverse linked list test")
public void reverseTest() {

	LinkedList<String> l = new LinkedList<String>("a");
	l.next = new LinkedList<String>("b");
	l.next.next = new LinkedList<String>("c");

	LinkedList<String> expected = new LinkedList<String>("c");
	expected.next = new LinkedList<String>("b");
	expected.next.next = new LinkedList<String>("a");

	assertEquals(expected, l.reverse());
}


@Test @DisplayName("Linked list partition list test")
public void partitionListTest() {


	LinkedList<Integer> l = LinkedListFrom.ints(3, 5, 8, 5, 10, 2, 1);
	LinkedList<Integer> expected = LinkedListFrom.ints(1,2, 3, 10, 5, 8, 5);
	assertEquals(expected, LinkedList.partition(l, 5));

	l = LinkedListFrom.ints(5, 8, 5, 10);
	expected = LinkedListFrom.ints(10, 5, 8, 5);
	assertEquals(expected, LinkedList.partition(l, 5));


}


}