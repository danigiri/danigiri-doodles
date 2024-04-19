package cat.calidos.doodles;

import java.util.Iterator;

public class DLinkedList<T>{

public T data;
public DLinkedList<T> prev;
public DLinkedList<T> next;

public DLinkedList(T d) {
	data = d;
	prev = null;
	next = null;
}


public DLinkedList(T d, DLinkedList<T> p, DLinkedList<T> n) {
	this(d);
	prev = p;
	next = n;
}


public void append(T d) {
	append(new DLinkedList<T>(d));
}


public void append(DLinkedList<T> e) {
	DLinkedList<T> l = this;
	while (l.next!=null) {
		l = l.next;
	}
	l.next = e;
	e.prev = l;
}


public void insert(T d) {
	insert(new DLinkedList<T>(d));
}


public void insert(DLinkedList<T> e) {
	e.next = this;
	if (prev==null) {
		prev = e;
	} else {
		// behaves as insertion
		e.prev = prev;
		prev = e;
	}
}


// return previous or return next if removing head
public DLinkedList<T> remove() {
	DLinkedList<T> l = null;
	if (prev!=null) {
		this.prev.next = this.next;
		l = this.prev;
	} else {
		this.next.prev = null;
		l = this.next;
	}
	this.next = null;
	this.prev = null;
	return l;
}


public DLinkedList<T> removeLast() {
	DLinkedList<T> l = this;
	while (l.next!= null) {
		l = l.next;
	}
	if (l.prev==null) {
		throw new IllegalArgumentException("we cannot remove last from a single element list");
	}
	l = l.prev;
	l.next.prev = null;
	l.next = null;
	return l.prev;
}

public int length() {
	int length = 1;
		DLinkedList<T> p = this;
	while (p.prev!=null) {
		p = p.prev;
		length++;
	}
	DLinkedList<T> n = this;
	while (n.next!=null) {
		n = n.next;
		length++;
	}
	return length;
}


@Override
public String toString() {
	
	StringBuffer s = new StringBuffer();
	s.append("[");
	
	DLinkedList<T> l = this;
	while (l.prev!=null) {
		l = l.prev;		
	}
	
	while (l!=null) {
		if (l==this) {		
			s.append("*");
		}
		s.append(l.data);
		if (l==this) {		
			s.append("*");
		}
		if (l.next!=null) {
			s.append(",");
		}
		l = l.next;
	}
	s.append("]");

	return s.toString();

}

public Iterator<T> iterator() {
	DLinkedList<T> start = this;
	return new Iterator<T>() {

		private DLinkedList<T> l = start;
		@Override
		public boolean hasNext() {
			return l.next!=null;
		}

		@Override
		public T next() {
			T d = l.data;
			l = l.next;
			return d;
		}

	};
}

}

