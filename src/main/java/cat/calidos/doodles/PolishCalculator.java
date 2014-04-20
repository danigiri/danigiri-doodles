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
import java.util.Stack;


public class PolishCalculator {


private static final String	PLUS	= "+";
private static final String	INCR	= "++";
private static final String	MINUS	= "-";
private static final String	DECR	= "--";
private static final String	MULT	= "*";
private static final String	DIVI	= "/";

public static long calc(final List<String>exp) {
	
	if (exp == null) {
		throw new NullPointerException("Null expression");
	}
	Stack<Long> stack = new Stack<Long>();
	return PolishCalculator.calcOnStack(stack, new ArrayList<String>(exp));

}

private static long calcOnStack(Stack<Long> stack, List<String>exp) {

	int size = exp.size();
	if (size == 0) {
		if (stack.isEmpty()) {
			return (long)0;
		}
		return stack.pop();
	}
	
	String top = exp.get(0);
	exp.remove(0);

	if (top.equals(PLUS)) {
		PolishCalculator.checkStackForOperands(stack, 2, "Insufficient number of operands on +");
		long a = stack.pop();
		long b = stack.pop();
		stack.push(b+a);
	} else if (top.equals(INCR)) {
		PolishCalculator.checkStackForOperands(stack, 1, "No operand on ++");
		long a = stack.pop();
		stack.push(++a);
	} else if (top.equals(MINUS)) {
		PolishCalculator.checkStackForOperands(stack, 2, "Insufficient number of operands on -");
		long a = stack.pop();
		long b = stack.pop();
		stack.push(b-a);
	} else if (top.equals(DECR)) {
		PolishCalculator.checkStackForOperands(stack, 1, "No operand on --");
		long a = stack.pop();
		stack.push(--a);
	} else if (top.equals(MULT)) {
		PolishCalculator.checkStackForOperands(stack, 2, "Insufficient number of operands on *");
		long a = stack.pop();
		long b = stack.pop();
		stack.push(b*a);
	} else if (top.equals(DIVI)) {
		PolishCalculator.checkStackForOperands(stack, 2, "Insufficient number of operands on /");
		long a = stack.pop();
		long b = stack.pop();
		if (b==(long)0) {
			throw new ArithmeticException("Divide by zero, cowboy");
		}
		stack.push(b/a);
	} else {
		try {
			Long v = Long.parseLong(top);
			stack.push(v);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("Wrong number or unimplemented op ("+e.getMessage()+")");
		}
	}
	
	// by induction call recursive function on smaller input
	return PolishCalculator.calcOnStack(stack, exp);
	
}


private static void checkStackForOperands(Stack<Long> stack, int operands, String message) {

	if (stack.size()<operands) {
		throw new IndexOutOfBoundsException(message);
	}

}

}
