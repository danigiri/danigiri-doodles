package cat.calidos.doodles;


public class DLinkedListStack<T> {

public DLinkedList<T> top = null;


public T peek() {
	return top.data;
}


public DLinkedListStack<T> pop() {
	DLinkedList<T> head = top;
	top = top.prev;
	head.prev = null;
	return this;
}


public DLinkedListStack<T> push(T data) {
	var head = new DLinkedList<T>(data);
	if (top != null) {
		top.next = head;
	}
	top = head;
	return this;
}


public boolean isEmpty() {
	return top == null;
}


public T pook() {
	var v = peek();
	pop();
	return v;
}


}

/*
 * Copyright 2024 Daniel Giribet
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */