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


public class LinkedList<T> {

public T data;
protected LinkedList<T> next;

public LinkedList(T d) {
	this.data = d;
	this.next = null;
}


public int length() {
	if (next==null) {
		return 1;	
	}
	return 1+next.length();
}


// algorithm to find the nth to last element of a singly linked list 
//n=0, [ a ] —> [ a ]
//n=0, [ a b ] —> [ b ]
//n=1, [ a ] —> null
//n=1, [ a b ] —> [ a ]
public static LinkedList<?> nthToLast(int n, LinkedList<?> l) {

	if (l==null) {
		return null;	
	}

	return LinkedList.nthToLast_(l, l, 1, n, l);	// O(N)

}

//
public static LinkedList<?> nthToLast_(LinkedList<?> nthCandidate, LinkedList<?> lb, int lbLength, int n, LinkedList<?> l) {

	// our buffer has the right size
	if (lbLength==n+1) {
		if (l.next==null) {	// base case, reached the end & buffer is the right size, return candidate
			return nthCandidate;
		} else {
							// recursive case, shift buffer and advance
			return nthToLast_(nthCandidate.next, lb.next, lbLength, n, l.next); // induction (smaller l)
		}
	} 
	// buffer too small
	if (l.next==null) { // base case, reached the end and buffer is not the right size
		return null;
	} else {			// recursive case, increase buffer and advance
		return nthToLast_(nthCandidate, lb.next, lbLength+1, n, l.next); // induction (smaller l)
	}

}



}