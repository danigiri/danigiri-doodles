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

// How would you design a stack which, in addition to push and pop, also has a 
// function min which returns the minimum element? Push, pop and min should 
// all operate in O(1) time 
public class MinStack<T> extends Stack<T> {

protected Stack<T> min;

public MinStack() {
	super();
	min = new Stack<T>();
}

public MinStack(T d) {
	this();
	push(d);
}

public void push(T d) {		// O(K)
	if (isEmpty()) {
		super.push(d);
		min.push(d);
	} else {
		if (((Comparable) d).compareTo(min.peek())<0) {
			min.push(d);	// new minimum!
		}
		super.push(d);
	}
}

public T pop() {		// O(K)
	T d = super.pop();
	if (!isEmpty()) {	// if the super call has not thrown exception, there must be at least one element
		if (((Comparable) d).compareTo(min.peek())==0) {
			// this means the top was the old minimum, therefore we pop
			min.pop();
		}		
	}
	return d;
}

public T min() {		// O(K)
	if (min.isEmpty()) {
		throw new IndexOutOfBoundsException("No minimum on empty stack");
	}
	return min.peek();
}

}