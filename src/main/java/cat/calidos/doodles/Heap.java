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

import java.util.ArrayList;
import java.util.List;


public class Heap<T extends Comparable<? super T>> {

	protected List<T> data;

	public Heap() {
		data = new ArrayList<T>();
		data.add(null);
	}

	public Heap(T d) {
		data = new ArrayList<T>();
		data.add(null);
		data.add(d);
	}


	public boolean isEmpty() {
		return data.size()==1;
	}

	public int size() {
		return data.size()-1;
	}

	
	public T peek() {
		if (isEmpty()) {
			throw new IndexOutOfBoundsException();
		}
		return data.get(1);
	}

	
	public T popMax() {
		T v = peek();
		T tempRootValue = removeLast();
		if (!isEmpty()) {
			data.set(1, tempRootValue);
			// we need to restore the heap property
			bubbleDown(1);
		}	
		// if empty, v contains the last value so no need to bubble down
		return v;

	}

	
	public void addMax(T v) {
		int pos = add(v);
		bubbleUp(pos);
	}

	public static <T extends Comparable<? super T>> Queue<T> heapSort(List<T> l) {	// O(N logN)
	
		Heap<T> h = new Heap<T>();
		for (T e : l) {
			h.addMax(e);				// O(logN)
		}
		
		Queue<T> q = new Queue<T>();
		while (!h.isEmpty()) {
			q.insert(h.popMax());		// O(log N) 
		}
		
		return q;
		
	}

	List<T> toList() {
		List<T> l = new ArrayList<T>(data);
		return l.subList(1, l.size());
	}

	protected void bubbleDown(int pos) {
		
		// get current & exchange it with the larger of its children
		T v = data.get(pos);
		if (hasLeft(pos)) {
			T left = getLeft(pos);
				if (hasRight(pos)) {
					T right = getRight(pos);
					if (left.compareTo(right)<=0) { 
						// right is bigger
						if (v.compareTo(right)<0) {
							// right is bigger than current, we need to swap and recurse
							setRight(pos, v);
							data.set(pos, right);
							bubbleDown(rightIndexFrom(pos));
						}
					} else { 
						// left is bigger
						if (v.compareTo(left)<0) {
							// left is bigger than current, we need to swap and recurse
							setLeft(pos, v);
							data.set(pos, left);
							bubbleDown(leftIndexFrom(pos));
						}
					}
				} else {
					// we only have a left
					if (v.compareTo(left)<0) {
						// left is bigger than current, we need to swap and recurse
						setLeft(pos, v);
						data.set(pos, left);
						bubbleDown(leftIndexFrom(pos));
					}
				}
	
		}
	}

	protected void bubbleUp(int pos) {

		T v = valueAt(pos);

		if (hasParent(pos)) {
			T parent = getParent(pos);
			if (parent.compareTo(v)<0) {
				setParent(pos, v);
				data.set(pos, parent);
				bubbleUp(parentIndexFrom(pos));
			}
		}

	}


	/** returns the added index **/
	protected int add(T v) {
		data.add(v);
		return data.size()-1;
	}
	
	
	protected T removeLast() {
		if (isEmpty()) {
			throw new IndexOutOfBoundsException();
		}
		int lastPos = data.size()-1;
		T last = data.get(lastPos);
		data.remove(lastPos);
		return last;
	}

	
	protected T valueAt(int p) {
		if (p>=data.size()) {
			throw new IndexOutOfBoundsException();
		}
		return data.get(p);
	}

	
	protected int leftIndexFrom(int p) {
		return p*2;
	}

	protected int rightIndexFrom(int p) {
		return (p*2)+1;
	}

	protected int parentIndexFrom(int p) {
		return p/2;
	}

	protected void setLeft(int p, T v) {
		data.set(leftIndexFrom(p), v);
	}

	protected void setRight(int p, T v) {
		data.set(rightIndexFrom(p), v);
	}

	protected void setParent(int p, T v) {
		data.set(parentIndexFrom(p), v);
	}

	protected T getLeft(int p) {
		return data.get(leftIndexFrom(p));
	}

	protected T getRight(int p) {
		return data.get(rightIndexFrom(p));
	}

	protected T getParent(int p) {
		return data.get(parentIndexFrom(p));
	}

	protected boolean hasLeft(int p) {
		return leftIndexFrom(p)<data.size();
	}

	protected boolean hasRight(int p) {
		return rightIndexFrom(p)<data.size();
	}

	protected boolean hasParent(int p) {
		return (p>1);
	}
}
