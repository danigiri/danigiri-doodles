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

import java.util.List;


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

public T dequeue() {
	if (isEmpty()) {
		throw new IndexOutOfBoundsException("Can’t dequeue empty list");
	}
	T h = head();
	first = first.next;
	if (first==null) {
		last = null;
	}
	return h;
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

public static <T> void reverse(Queue<T> q) {
	if (q.isEmpty()) {
	} else {
		T e = q.dequeue();
		Queue.reverse(q);
		q.enqueue(e); 
	}
}


@Override
public boolean equals(Object obj) {		// O(N)

    if (obj == null) {
        return false;
    }
    if (obj == this) {
        return true;
    }
    if (!(obj instanceof Queue)) {
        return false;
    }
    
    @SuppressWarnings("unchecked")
    Queue<T> q = (Queue<T>)obj;
    
    if (q.length()!=length()) {
    	return false;
    }
    
    Queue<T> backup = new Queue<T>();
    boolean equals = true;
	LinkedList<T> p = first;
	while (p!=null && equals) {			// O(N)
		T e = q.dequeue();
		backup.enqueue(e);
		if (p.data==null) {
			equals = e==null;			
		} else {
			equals = p.data.equals(e);
		}
		p = p.next;
	}
	
	// if we insert the backup in reverse order, we restore the queue to its
	// original state
	Queue.reverse(backup);
	while (!backup.isEmpty()) {			// O(N)
		q.insert(backup.dequeue());
	}
	
	return equals;
}


@Override
public String toString(){
	StringBuffer s = new StringBuffer();
	s.append("[");
	LinkedList<T> p = first;
	while (p!=null) {
		s.append(p.data);
		p = p.next;
		if (p!=null) {
			s.append(", ");
		}
	}
	s.append("]");
	return s.toString();
}

}