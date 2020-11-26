package cat.calidos.doodles;

import java.util.ArrayList;
import java.util.List;

/**
 * @author daniel giribet
 *///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class MultipleStack<E> {

private static final int SIZE = 3;
private List<E> stack;


MultipleStack() {

	this.stack = new ArrayList<E>();
}


public void push0(E element) {
	pushN(element, 0);
}


public void push1(E element) {
	pushN(element, 1);
}


public void push2(E element) {
	pushN(element, 2);
}


public E pop0() {

	E pop = popN(0);
	garbageCollect();

	return pop;

}


public E pop1() {

	E pop = popN(1);
	garbageCollect();

	return pop;
}


public E pop2() {

	E pop = popN(2);
	garbageCollect();

	return pop;

}


private void pushN(E element, int whichStack) {

	int size = this.stack.size();
	int i = size - whichStack - 1; // size is always multiple of 3
	if (i<0 || this.stack.get(i) != null) { // grow case
		for (int j = SIZE-1; j >= 0; j--) {
			if (j == whichStack) {
				this.stack.add(element);
			} else {
				this.stack.add(null);
			}
		}
	} else { // we have at least 1 triplet
		boolean found = false;
		while (!found) { // no grow case
			found =  i<0 || this.stack.get(i) != null;
			if (!found) {
				i = i-SIZE;
			}
		}
		// at the end of the loop, next element is where we set the value
		this.stack.set(i+SIZE, element);
	}

}


private E popN(int whichStack) {

	int i = this.stack.size() - whichStack - 1;
	if (i < 0) {
		throw new IndexOutOfBoundsException("Cannot pop empty stack");
	}
	E current = null;
	while (i >= 0 && current == null) {
		current = this.stack.get(i);
		if (current==null) {
			i -= SIZE;
		}
	}
	if (i<=0 && current == null) {
		throw new IndexOutOfBoundsException("Cannot pop empty stack");
	}

	this.stack.set(i, null);

	return current;

}


private void garbageCollect() {

	int size = this.stack.size()-1;
	boolean allNull = true;
	int i = size;
	int stop = size-SIZE;
	while(i>stop && allNull) {
		allNull = this.stack.get(i)==null;
		i--;
	}

	if (allNull) {
		for (int j=size;j>stop;j--) {
			this.stack.remove(j);
		}
	}
 
}


}


/*
 * Copyright 2020 Daniel Giribet
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

