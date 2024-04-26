// BITS . JAVA

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


public static String prettyPrint(int[] v) {

	StringBuffer s = new StringBuffer();
	for (int i=0; i<v.length; i++) {
		s.append(Bits.prettyPrint(v[i]));
	}
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


//same with recursive option, no loop and no mask variables, only parameters
public static Map<String, Integer> swap2(int a, int b) {
	return _swap2(a, b, 1);
}


private static Map<String, Integer> _swap2(int a, int b, int mask) {

	// base case
	if (mask==0) {
		Map<String, Integer> output = new HashMap<String, Integer>(2);
		output.put("a", a);
		output.put("b", b);
		return output;
	}
	if (((mask&a)^(mask&b))==0) {
		return _swap2(a, b, mask<<1);
	} else if ((mask&a)!=0) {
		return _swap2(a&(~mask), b|mask, mask<<1);
	} else {
		return _swap2(a|mask, b&(~mask), mask<<1);
	}

}


//implement a bloom filter
//define a set of k hash functions, let’s say three, that map to the size of the bloom filter
//number of ints x 32
//calculate and set/lookup for each
public static boolean existsInBloom(String k, int[] bloom) {

	int m = bloom.length*SIZE_INT;

	return bitSet(k1(k, m), bloom, m) && bitSet(k2(k ,m), bloom, m) && bitSet(k3(k,m),bloom, m);

}


private static boolean bitSet(int i, int[] bloom, int m) {

	int mask = 1;
	int intPos = i/SIZE_INT;
	int bitPos = i-intPos;

	return (bloom[intPos] & mask<<bitPos)!=0;

}

private static int SIZE_INT = 32;

public static int[] setBloom(String k, int[] bloom) {

	int m = bloom.length*SIZE_INT;

	int k1 = k1(k, m);
	int k2 = k2(k, m);
	int k3 = k3(k, m);

	bloom = setBloomBit(k1, bloom, m);
	bloom = setBloomBit(k2, bloom, m);
	bloom = setBloomBit(k3, bloom, m);

	return bloom;

}

private static int[] setBloomBit(int i, int[] bloom, int m) {

	int mask = 1;

	int intPos = i/SIZE_INT;
	int bitPos = i-intPos;

	bloom[intPos] |= mask<<bitPos;

	return bloom;

}

private static int k1(String k, int m) {

	int h = k.length()*m;
	for (int i=0; i<k.length();i++) {
		h += Character.getNumericValue(k.charAt(i)) * k.length();
	}

	return h % m;
}


private static int k2(String k, int m) {

	int h = k.length()*(m-1);
	int mask = 1;

	for (int i=0; i<k.length(); i++) {
		h += mask << (Character.getNumericValue(k.charAt(i)) % m);
}

return h % m;

}


private static int k3(String k, int m) {

	int h = k.length()*(m-2);

	for (int i=0; i<k.length(); i++) {
		int incr = Character.getNumericValue(k.charAt(i)) * k.length();
		h = i % k.length()==0 ? h+incr : h-incr;
	}

	h = h<0 ? -h : h;

	return h % m;

}


//5.3 you have an integer and you can flip exactly one bit from 0 to 1, find out what is the 
//longest sequence of 1’s you can make
//example 1775,  11011101111, output → 8
//as we can flip 110111x1111
//bear in mind that we could be in a position where we have 1101100010, so we have to be 
//exhaustive
//we also could have 101101101, where the answer is 5 and not 4
//we keep the longest 1 count
//we keep the longest pair of 1 counts separated only by one 0
//we keep the previous 1 count
//we keep the current 1 count


public static int longestOneSequenceWithFlip(int number) {

	int longestCount = 0;
	int longestPair = 0;
	int previousCount = 0;
	int currentCount = 0;
	int zeroCount = 0;
	int previousValue = -1;
	int mask = 1;

	for (int i = 0; i < SIZE_INT; i++) {
		// update current status
		int currentValue = number & mask;
		if (currentValue == 0) {
			zeroCount++;
			if (zeroCount > 1) {
				previousCount = 0; // we cannot match anymore
			} else {
				previousCount = currentCount; // we can still match
			}
			if (previousValue != 0) {
				if (currentCount > longestCount) {
					longestCount = currentCount;
				}
				currentCount = 0; // we stop counting current
				// update global status
			}
		} else {
			currentCount++;
			if (zeroCount == 1 && previousCount > 0) { // candidate for pair
				int candidateForPair = currentCount + previousCount;
				if (candidateForPair > longestPair) { // update global status
					longestPair = candidateForPair;
				}
			}
		}
		previousValue = currentValue;
		mask = mask << 1;
	}

	if (longestPair == 0) { // no pairs :sadface:
		return longestCount + 1;
	}
	return longestPair + 1;	// longest pair will always be longer than the longestCount

}



//5.4 next number, given a positive integer, print the next smallest and next largest number
//that have the same number of 1’s in their binary representation

//11100 → smallest bigger number is 11010, as 01110 is even bigger
//okay, so you find the first zero, set it to 1, set the previous to zero
//digit (MAX_INT)
//0001100 → 0001010, we are in fact looking for the last zero in the first batch
//11000 → 10100 is the next biggest number
//10000 → 01000 is correct
//if we start with zero, look for the first 1
//then look for the first zero, set it to 1, previous<-zero
//smallest
//11100 → does not exist
//01100 → 10100
//001110 → 010110
//okay, you find the first 1, if it’s position 0, no solution exists
//if it’s position >0, set it to zero, set previous to 1



public static String nextNumbersInBinary(int n) {

	if (n<=0) {
		return "[]";
	}

	StringBuffer s = new StringBuffer();
	s.append("[");
	int maskSmaller = 1;
	boolean smallerHasSolution = (n & maskSmaller)==0;		// if the first digit is zero we have a solution
	while (maskSmaller!=0 && ((n & maskSmaller) != 0)) {	// look for first 0
		maskSmaller = maskSmaller << 1;
		smallerHasSolution = true;							// if after 111's we have at least a zero we have a solution
	}
	while (maskSmaller!=0 && (n & maskSmaller) == 0) {						// look for first 1
		maskSmaller = maskSmaller << 1;
	}
	if (smallerHasSolution) { // we have a solution
		int smaller = n & ~maskSmaller; 			// last bit to 0
		smaller = smaller | (maskSmaller >> 1); // previous bit to 1
		s.append(smaller);
	}
	s.append(",");
	// next bigger number
	if (n > 0 && n < Integer.MAX_VALUE) {
		int maskBigger = 1;
		if ((n & maskBigger) == 0) { // we start with zero, look for first 1
			while ((n & maskBigger) == 0) {
				maskBigger = maskBigger << 1;
			}
		}
		while ((n & maskBigger) != 0) { // look for next zero now
			maskBigger = maskBigger << 1;
		}
		int bigger = n | maskBigger; // last bit to 1
		bigger = bigger & ~(maskBigger >> 1); // previous bit to 0
		s.append(bigger);
	}
	s.append("]");

	return s.toString();

}


//5.6 write code to return the number of bits you would need to flip to convert
//one integer another
//29, 15, the result is 2

//ok, 	11100
//	10101
//	 x  x → 2

//we can go through all the bits of the two and do an xor, if the xor==1 we need to flip
//otherwise we skip

public static int flipsToConvert(int a, int b) {

	int flips = 0;
	int mask = 1;
	while (mask!=0) {
		int bitA = a & mask;
		int bitB = b & mask;
		if ((bitA ^ bitB) != 0) {
			flips++;
}
		mask = mask<<1;
}

	return flips;

}

// another version
public static int bitDistance(int a, int b) {

var d = 0;

while (a!=0 || b!=0) {
	d += ((a & 1) - (b & 1))!=0 ? 1 : 0;
	a = a >>1;
	b = b >>1;
}

return d;

}


}

/**
Copyright 2024 Daniel Giribet <dani - calidos.cat>

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
