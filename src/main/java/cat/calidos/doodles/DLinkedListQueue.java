package cat.calidos.doodles;

import java.util.Iterator;

public class DLinkedListQueue<T> implements Iterable<T> {


protected DLinkedList<T>	first	= null;
protected DLinkedList<T>	last	= null;

public boolean isEmpty() {
	return first==null;
}


public DLinkedListQueue<T> addFirst(T d) {
	var head = new DLinkedList<T>(d);
	if (isEmpty()) {
		last = head;		
	} else {
		first.prev = head;
	}
	first = head;
	return this;
}

public DLinkedListQueue<T> removeFirst(T d) {
	first = first.next;
	return this;
}

public DLinkedListQueue<T> enqueue(T d) {
	var tail = new DLinkedList<T>(d);
	if (isEmpty()) {
		first = tail;
	} else {
		last.next = tail;
	}
	last = tail;
	return this;
}

public DLinkedListQueue<T> dequeue() {
	last = last.prev;
	return this;
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



@Override
public Iterator<T> iterator() {
	return new Iterator<T>() {
		DLinkedList<T> current = first;
		@Override
		public boolean hasNext() {
			return current.next !=null;
		}

		@Override
		public T next() {
			T v = current.data;
			current = current.next;
			return v;
		}

	};
}


}

