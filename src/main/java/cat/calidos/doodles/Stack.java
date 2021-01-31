package cat.calidos.doodles;

import java.util.ArrayList;
import java.util.List;

public class Stack<T extends Comparable<? super T>> {

protected LinkedList<T> elements;

public Stack() {
	elements = null;
}


public Stack(T d) {
	elements = null;
	push(d);
}


public boolean isEmpty() {
	return elements==null;
}


public int height() {
	if (isEmpty()) {
		return 0;
	}
	return elements.length();
}


public void push(T d) {
	LinkedList<T> newTop = new LinkedList<T>(d);
	newTop.next = elements;
	elements = newTop;
}

public T pop() {
	if (isEmpty()) {
		throw new IndexOutOfBoundsException("Can’t pop empty stack");
	}
	T top = elements.data;
	elements = elements.next;
	return top;
}

public T peek() {
	if (isEmpty()) {
		throw new IndexOutOfBoundsException("Can’t peek empty stack");
	}
	return elements.data;
	
}

/** Check for parentheses balance, return true if parentheses are balanced, return false otherwise
*	Empty string is true
*/

private static Character LEFTPAR = '(';
private static Character RIGHTPAR = ')';

public static boolean areParenthesesBalanced(String expr) {

	// '' --> true
	// '()' -->true
	// '(())' -->true
	// '(()(()))' -->true
	// '(()-->false
	// '(()))' -->false
	// ')' --> false
	// (((()) --> false

	int length = expr.length();
	List<Character> stack = new ArrayList<Character>();
	int i = 0;
	boolean ok = true;
	while (ok && i<length) {
		Character current = expr.charAt(i++);
		if (current.equals(LEFTPAR)) {
			stack.add(LEFTPAR);
		} else if (current.equals(RIGHTPAR)) {
			if (stack.size()>0) {
				stack.remove(stack.size()-1);
			} else {
				ok = false;
			}
		} else {
			throw new IllegalArgumentException("Invalid expression '"+expr+"'");
		}
	}

	return ok && stack.size()==0;

}



}