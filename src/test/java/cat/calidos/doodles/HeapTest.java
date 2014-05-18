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

import java.util.List;

import org.junit.Test;


public class HeapTest {

@Test
public void maxHeapTest() {
	
	List<Integer> l = ListFrom.ints();
	Heap<Integer> h = new Heap<Integer>();
	assertTrue(h.isEmpty());
	assertEquals(l, h.toList());
	
	l = ListFrom.ints(1);
	h = new Heap<Integer>(1);
	assertEquals(new Integer(1), h.peek());
	assertEquals(l, h.toList());
	
	h.addMax(2);
	l = ListFrom.ints(2, 1);
	assertEquals(l, h.toList());
	
	h.addMax(3);
	l = ListFrom.ints(3, 1, 2);
	assertEquals(l, h.toList());
	
	h.addMax(4);
	l = ListFrom.ints(4, 3, 2, 1);
	assertEquals(l, h.toList());

	h.addMax(5);
	l = ListFrom.ints(5, 4, 2, 1, 3);
	assertEquals(l, h.toList());

	assertEquals(new Integer(5), h.popMax());
	assertEquals(new Integer(4), h.popMax());
	assertEquals(new Integer(3), h.popMax());
	assertEquals(new Integer(2), h.popMax());
	assertEquals(new Integer(1), h.popMax());
	assertTrue(h.isEmpty());

}

@Test
public void heapSortTest() {
	
	List<Integer> l = ListFrom.ints(1, 2, 3, 4, 5);
	Queue<Integer>q = QueueFrom.ints(1, 2, 3, 4, 5);
	assertEquals(q, Heap.heapSort(l));
	
	l = ListFrom.ints(5, 4, 3, 2, 1);
	assertEquals(q, Heap.heapSort(l));

	l = ListFrom.ints(3, 5, 2, 4, 3, 1);
	q = QueueFrom.ints(1, 2, 3, 3, 4, 5);
	assertEquals(q, Heap.heapSort(l));

}

}
