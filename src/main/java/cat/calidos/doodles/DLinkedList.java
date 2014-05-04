package cat.calidos.doodles;


public class DLinkedList<T> {

public T data;
private DLinkedList<T> prev;
private DLinkedList<T> next;

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


public void add(T d) {
	add(new DLinkedList<T>(d));
}


public void add(DLinkedList<T> e) {
	e.prev = this;
	if (next==null) {
		next = e;
	} else {
		// behaves as insertion
		e.next = next;
		next = e;
	}
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


}

