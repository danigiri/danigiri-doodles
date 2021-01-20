package cat.calidos.doodles;


/**
*	@author daniel giribet
*///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class StackQueue <X> {

private Stack<X>	queue	= new Stack<X>();
private Stack<X>	buffer	= new Stack<X>();

public void push(X element) {
	queue.push(element);
}


public X pop() {

	if (queue.isEmpty()) {
		throw new NullPointerException("Cannot pop an empty stack");
	}

	X candidate = null; // queue is guaranteed to have at least one element
	while (!queue.isEmpty()) {
		candidate = queue.pop();
		if (!queue.isEmpty()) {
			buffer.push(candidate);
		}
	}
	while (!buffer.isEmpty()) {
		queue.push(buffer.pop());
	}

	return candidate;

}

public boolean isEmpty() {
	return queue.isEmpty();
}


}

/*
 *    Copyright 2021 Daniel Giribet
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

