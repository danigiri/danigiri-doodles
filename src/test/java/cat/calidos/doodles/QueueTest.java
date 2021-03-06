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


public class QueueTest {


@Test
public void queueTest() {
	
	Queue<String> q = new Queue<String>();
	assertTrue(q.isEmpty());
	
	q.enqueue("a");
	assertFalse(q.isEmpty());
	assertEquals(1, q.length());
	assertEquals("a", q.head());
	assertEquals("a", q.tail());
	
	q.enqueue("b");
	assertEquals(2, q.length());
	assertEquals("a", q.head());
	assertEquals("b", q.tail());

	q.dequeue();
	assertEquals(1, q.length());
	assertEquals("b", q.head());
	assertEquals("b", q.tail());

	q.enqueue("c");
	q.enqueue("d");
	assertEquals(3, q.length());
	assertEquals("d", q.tail());

	q.removeTail();
	assertEquals("c", q.tail());
	assertEquals(2, q.length());

	q.insert("x");
	assertEquals(3, q.length());
	assertEquals("x", q.head());
	assertEquals("c", q.tail());
	
}

@Test
public void reverseTest() {
	
	Queue<String> q = new Queue<String>();
	Queue.reverse(q);
	assertTrue(q.isEmpty());
	
	
	q = new Queue<String>();
	q.enqueue("a");

	Queue.reverse(q);
	assertEquals(1, q.length());
	assertEquals("a", q.head());
	assertEquals("a", q.tail());

	q.enqueue("b");
	Queue.reverse(q);
	assertEquals(2, q.length());
	assertEquals("b", q.head());
	assertEquals("a", q.tail());

	q.insert("c");
	Queue.reverse(q);
	assertEquals(3, q.length());
	assertEquals("a", q.head());
	assertEquals("c", q.tail());

}
//if equals was destructive of b, we would notice// if equals was destructive of b, we would notice
@Test
public void equalsTest() {
	
	Queue<String> a = new Queue<String>();
	Queue<String> b = new Queue<String>();
	assertTrue(a.equals(b));
	
	a.enqueue("a");
	assertFalse(a.equals(b));

	b.enqueue("a");
	assertTrue(a.equals(b));
	b = new Queue<String>("b");
	assertFalse(a.equals(b));

	b = new Queue<String>("a");
	a.enqueue("b");
	a.enqueue("c");
	b.enqueue("b");
	b.enqueue("c");
	assertTrue(a.equals(b));
	assertEquals(3, b.length());	// if equals was destructive of b...
	assertTrue(a.equals(b));	
	
}

}
