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


public class Queue<T> {

protected LinkedList<T> first;
protected LinkedList<T> last;

public Queue() {
	first = null;
	last = null;
}

public Queue(T d) {
	first = null;
	last = null;
	insert(d);
}


public boolean isEmpty() {
	return first==null;
}

public int length() {
	if (isEmpty()) {
		return 0;
	}
	return first.length();
}

public void insert(T d) {
	LinkedList<T> e = new LinkedList<T>(d);
	e.next = first;
	first = e;
	if (last==null) {	// first time insertion
		last = e;
	}
}

public void enqueue(T d) {
	if (isEmpty()) {
		insert(d);
	} else {
		LinkedList<T> e = new LinkedList<T>(d);
		last.next = e;
		last = e;
	}
}

public T head() {
	if (isEmpty()) {
		throw new IndexOutOfBoundsException("Can’t check head of empty list");
	}
	return first.data;
}

public T tail() {
	if (isEmpty()) {
		throw new IndexOutOfBoundsException("Can’t check tail of empty list");
	}
	return last.data;
}

public void dequeue() {
	if (isEmpty()) {
		throw new IndexOutOfBoundsException("Can’t dequeue empty list");
	}
	first = first.next;
	if (first==null) {
		last = null;
	}
}

public void removeTail() {
	if (isEmpty()) {
		throw new IndexOutOfBoundsException("Cant remove tail of empty list");
	}
	LinkedList<T> nextToLast = first;
	while (nextToLast.next!=last) {	// O(N)
		nextToLast = nextToLast.next;
	}
	last = nextToLast;
	last.next = null;
}

}