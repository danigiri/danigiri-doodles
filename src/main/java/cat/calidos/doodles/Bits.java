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

import java.util.HashMap;
import java.util.Map;

public class Bits {

public static final String PRETTY_HEADER1 = "|------|*------|*------|*------|";
public static final String PRETTY_HEADER0 = "31------23------15------7------0";

private static int INT_SIZE = 32;


/** 'substring' from i to j from M into N
 * @param n integer
 * @param m integer
 * @param i start index
 * @param j end index
 * @return return modified N that includes the subsection form M
 */
public static int subBits(int n, int m, int i, int j) {

	if (i<0 || j<0 || i>31 || j>31) {
		throw new IndexOutOfBoundsException("Indices out of bounds");
	}

	// internally, i is always the smaller index
	if (i>j) {
		int s = i;
		i = j;
		j = s;
	}

	for (int x = i; x<=j; x++) {
		int mask = 0x1 << x;
		int v = m & mask;
		if (v==0x0) {	// m bit is set to zero
			n &= ~(mask);
		} else {		// m bit is set to one
			n |= mask;
		}
	}

	return n;
}


//pretty print a binary number as a string

public static String prettyPrint(int n) {

	StringBuffer s = new StringBuffer(33*3);
	s.append(PRETTY_HEADER0);
	s.append("\n");
	s.append(PRETTY_HEADER1);
	s.append("\n");
	int mask = 1 << 31;
	//ints are 32 bits long
	for (int i=0; i<32; i++) {
		s.append((n & mask)==mask ? "1" : "0");
		n = n<<1;
	}
	s.append("\n");

	return s.toString();

}


public static int insert(int n, int m, int i, int j) {

	if (j<i || i>=INT_SIZE || j>=INT_SIZE) {
		throw new ArrayIndexOutOfBoundsException("bad indexes");
	}

	int output = 0;
	int writerMask = 1;	// we use this mask to slide through the integer
	int insertionMask = 1;
	for (int x=0; x<INT_SIZE; x++) {
		if (x<i || x>j) {			// normal writing operation
			output |= n & writerMask;
		} else {					// insert M into it
			output |= m & insertionMask;
			insertionMask = insertionMask << 1;
		}
		writerMask = writerMask << 1; // logical shift, not arithmetic one, but arithmetic works given mask is positive
	}

	return output;

}



//write an algorithm to swap to numbers in place without a temporary variable
//numbers are a and b
//let’s do it bit by bit, for each bit
//a) if xor == 0 , get any of the two values
//b) if a & 1 ==1 we set 1 → b and 0 → a
//c) otherwise we set 1 -> a and 0 -> b

public static Map<String, Integer> swap(int a, int b) {

	int mask = 1;
	Map<String, Integer> output = new HashMap<String, Integer>(2);

	for (int i=0; i<INT_SIZE; i++) {
		if (((mask&a)^(mask&b))==0) {
			// no op
		} else if ((mask&a)!=0) {
			a = a&(~mask);
			b = b|mask;
		} else {
			a = a|mask;
			b = b&(~mask);
	}
		mask = mask << 1;
	}
	output.put("a", a);
	output.put("b", b);

	return output;

}



}
