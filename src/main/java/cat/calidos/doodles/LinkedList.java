// LINKED LIST . JAVA

package cat.calidos.doodles;

import java.util.Optional;

public class LinkedList<T extends Comparable<? super T>> {

public T				data;
public LinkedList<T>	next;

public LinkedList(T d) {
	this.data = d;
	this.next = null;
}


public int length() {
	if (next == null) {
		return 1;
	}
	return 1 + next.length();
}


public LinkedList<T> add(T d) {
	if (this.next != null) {
		throw new IllegalArgumentException("should be adding to the end of a list");
	}
	this.next = new LinkedList<T>(d);
	return this;
}


public LinkedList<T> removeLast() {
	if (this.next == null) {
		throw new IllegalArgumentException("cannot remove last from a single item list");
	}
	LinkedList<T> p = this;
	LinkedList<T> i = p.next;
	while (i != null) {
		p = i;
		i = i.next;
	}
	p.next = null;
	return this;
}


@Override
public boolean equals(Object obj) {
	if (obj == null) {
		return false;
	}
	if (!(obj instanceof LinkedList<?>)) {
		return false;
	}
	boolean equals = true;
	LinkedList<T> l = this;
	@SuppressWarnings("unchecked")
	final LinkedList<T> o = (LinkedList<T>) obj;
	while (equals && l != null && o != null) {
		equals = l.data == null && o.data == null || l.data != null && l.data.equals(o.data);
		if (equals) {
			if (l.next == null && o.next == null) {
				return true;
			}
			return l.next.equals(o.next);
		}
	}

	return equals;
};


@Override
public int hashCode() {
	return this.toString().hashCode();
};


@Override
public String toString() {
	StringBuffer s = new StringBuffer();
	s.append("[");
	LinkedList<T> l = this;
	s.append(this.data);
	l = l.next;
	while (l != null) {
		s.append(",");
		s.append(l.data);
		l = l.next;
	}
	s.append("]");
	return s.toString();
}


// algorithm to find the nth to last element of a singly linked list
// n=0, [ a ] —> [ a ]
// n=0, [ a b ] —> [ b ]
// n=1, [ a ] —> null
// n=1, [ a b ] —> [ a ]
public static LinkedList<?> nthToLast(int n, LinkedList<?> l) {

	if (l == null) {
		return null;
	}

	return LinkedList.nthToLast_(l, l, 1, n, l); // O(N)

}


//
public static LinkedList<?> nthToLast_(LinkedList<?> nthCandidate, LinkedList<?> lb, int lbLength, int n,
		LinkedList<?> l) {

	if (lbLength < n + 1) {
		// buffer too small
		if (l.next == null) { // base case, reached the end and the buffer is not the right size
			return null;
		} else { // recursive case, increase buffer and advance
			return nthToLast_(nthCandidate, lb.next, lbLength + 1, n, l.next); // induction (smaller
																				// l)
		}
	} else {
		// our buffer has the right size
		if (l.next == null) { // base case, reached the end & buffer is the right size, return
								// candidate
			return nthCandidate;
		} else {
			// recursive case, shift candidate and buffer to the right (keeping length) and advance
			return nthToLast_(nthCandidate.next, lb.next, lbLength, n, l.next); // induction
																				// (smaller l)
		}
	}

}


public static <T extends Comparable<? super T>> LinkedList<T> removeDupes(LinkedList<T> l) {

	if (l == null) {
		return null;
	}
	LinkedList<T> uniqueList = null; // empty list
	return removeDupes(uniqueList, uniqueList, l);
}


private static <T extends Comparable<? super T>> LinkedList<T> removeDupes(LinkedList<T> unique, LinkedList<T> uTail,
		LinkedList<T> l) {

	if (l == null) {
		return null;
	}

	// base case
	LinkedList<T> current = l;
	LinkedList<T> i = unique;

	boolean found = false;
	while (!found && i != null) {
		found = i.data == null && current.data == null || i.data.equals(current.data);
		if (!found) {
			i = i.next;
		}
	} // while

	// notice that if the unique list was an empty list (null) it also means found==false
	if (!found) {
		// did not find current in unique list
		if (unique == null) {
			unique = new LinkedList<T>(current.data); // unique list was empty
			uTail = unique;
		} else {
			uTail.next = new LinkedList<T>(current.data);
			uTail = uTail.next; // uTail points to new end
		}
	}

	// recursive case
	if (l.next != null) {
		unique = removeDupes(unique, uTail, l.next);
	}

	return unique;

}


public static <T extends Comparable<? super T>> Optional<T> kthElement(LinkedList<T> l, int k) {
	return kthElementRec(l, k).right;
}


private static <T extends Comparable<? super T>> Pair<Integer, Optional<T>> kthElementRec(LinkedList<T> l, int k) {

	if (l == null) {
		return new Pair<Integer, Optional<T>>(k, Optional.empty());
	}
	// recursive call, valid by induction
	Pair<Integer, Optional<T>> pair = kthElementRec(l.next, k);
	// now pair either has the value or it has zero or it has counter >0
	if (pair.right.isPresent()) { // found!
		return pair;
	} else if (pair.left == 0) {
		return new Pair<Integer, Optional<T>>(0, Optional.of(l.data)); // found!
	} else {
		return new Pair<Integer, Optional<T>>(--pair.left, pair.right); // not found, continue
																		// search
	}

}


// for each element, we find out which of the two lists we need to place the item
// we concatenate the first element from the left to the right list and return
// edge case, all elements are on the right list
// we assume the partition input does belong to the list so the right list is never empty
// but we do not know how many of the partition items there will be


public static <T extends Comparable<? super T>> LinkedList<T> partition(LinkedList<T> l, T partition) {

	if (l == null) {
		throw new NullPointerException("Null list cannot be partitioned");
	}

	LinkedList<T> left = null;
	LinkedList<T> firstLeft = null;
	LinkedList<T> right = null;

	LinkedList<T> current = l;
	while (current != null) {

		if (current.data.compareTo(partition) < 0) { // element goes to the left
			LinkedList<T> leftHead = new LinkedList<T>(current.data);
			if (left == null) {
				left = leftHead;
				firstLeft = left;
			} else {
				leftHead.next = left;
				left = leftHead;
			}
		} else { // element goes to the right
			LinkedList<T> rightHead = new LinkedList<T>(current.data);
			if (right == null) {
				right = rightHead;
			} else {
				rightHead.next = right;
				right = rightHead;
			}
		}

		current = current.next;

	} // while

	if (firstLeft != null) {
		firstLeft.next = right; // guaranteed to have at least one element
		return left;
	} else {
		return right; // no left elements
	}

}


// 2nd version
public static <T extends Comparable<? super T>> LinkedList<T> partition2(LinkedList<T> l, T partition) {

	LinkedList<T> left = null;
	LinkedList<T> start = null;
	LinkedList<T> right = null;
	LinkedList<T> middle = null;

	while (l != null) {
		T v = l.data;
		int comparison = v.compareTo(partition);
		if (comparison < 0) {
			// smaller than partition
			left = LinkedList.createOrAppend(left, v);
			if (start == null) {
				start = left;
			}
		} else {
			// equal or bigger than partition
			right = createOrAppend(right, v);
			if (middle == null) {
				middle = right;
			}
		}
		l = l.next;
	}

	LinkedList<T> out = null;
	if (left == null) {
		out = middle;
	} else if (right == null) {
		out = start;
	} else {
		left.next = middle;
		out = start;
	}

	return out;
}


public static <T extends Comparable<? super T>> LinkedList<T> createOrAppend(LinkedList<T> l, T d) {
	var newList = new LinkedList<T>(d);
	if (l != null) {
		l.next = newList;
	}
	return newList;
}


public LinkedList<T> reverse() {

	LinkedList<T> reverse = null;
	LinkedList<T> head = null;
	Stack<T> stack = new Stack<T>();
	LinkedList<T> current = this;
	while (current != null) {
		stack.push(current.data);
		current = current.next;
	}
	while (!stack.isEmpty()) {
		LinkedList<T> newHead = new LinkedList<T>(stack.pop());
		if (reverse == null) {
			reverse = newHead;
		} else {
			head.next = newHead;
		}
		head = newHead;
	}

	return reverse;

}


public static <T extends Comparable<? super T>> LinkedList<T> reverse2(LinkedList<T> l) {

	if (l == null) {
		return null;
	}

	LinkedList<T> rev = new LinkedList<T>(l.data);
	while (l.next != null) {
		l = l.next;
		LinkedList<T> next = new LinkedList<T>(l.data);
		next.next = rev;
		rev = next;
	}

	return rev;

}

}

/**
 * Copyright 2016 Daniel Giribet <dani - calidos.cat>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
