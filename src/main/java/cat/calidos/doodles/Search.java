// SEARCH . JAVA

package cat.calidos.doodles;

import java.util.List;

/**
*	@author daniel giribet
*///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class Search {

//10.4 sorted search, no size: given a structure called Listy, which lacks a size method
//elementAt(i) returns either the element or -1 if we are out of bounds
//given a listy of positive sorted integers, find the position of a given element x
//if there are more than one instance of x, any position will do

//we do a recursive binary search, keeping a lower and up upper bound
//start at zero, a random number, upper bound is the number + a random number
//if -1, jump to half of the position, upper bound is current
//if current value <x, lower=current, jump in the middle of current and upper bound,
//if current value >x, jump in the middle of lower, upper<-current
//if current value==x, return
//check for lower==current==upper, return -1 for not found



private static int elementAt(List<Integer> list, int index) {
	return (index>=list.size())? -1 : list.get(index);
}



public static int findIn(List<Integer> l, int x) {
	return findInRec(l, x, 0, 10, 20);
}


private static int findInRec(List<Integer> l, int target, int lower, int current, int upper) {

	if (lower == current && current == upper) {
		return -1;
	}

	int value = Search.elementAt(l, current);
	if (value == -1) {
		return findInRec(l, target, lower, lower+((current - lower)/2), current);
	}
	if (value < target) {
		return findInRec(l, target, current, current+((upper - current)/2), upper);
	}
	if (value > target) {
		return findInRec(l, target, lower, lower+((current - lower)/2), current);
	}
	return current;

}


public static int locateSparse(List<String> a, String s) {
	return locateSparse(a, s, 0, a.size());
}


// end non inclusive
private static int locateSparse(List<String> a, String s, int start, int end) {

	if (end <= start) {
		return -1;
	}
	if (Math.abs(start - end) == 1) {
		if (a.get(start).equals(s)) {
			return start;
		}
		return -1;
	}
	// look for the middle value that is not empty
	int middle = ((end - start) / 2) + start;
	while (middle < end && a.get(middle).equals("")) {
		middle++;
	}
	if (middle == end) { // nothing on the right hand side of the search space
		middle--;
		while (middle > start && a.get(middle).equals("")) {
			middle--;
		}
		return locateSparse(a, s, start, middle+1);
	}
	// we found a value middle element
	String value = a.get(middle);
	if (value.equals(s)) {
		return middle;
	}
	if (s.compareTo(value) < 0) {
		return locateSparse(a, s, start, middle);
	} else {
		return locateSparse(a, s, middle, end);
	}

}


/*

sparse search

given a sparse sorted array of strings, write a method to find a given string and return the location

["", "a", "", "", "b", "", "c","d"], "c" → 6

we need to implement a binary search that takes into account the sparse behaviour
when we land in a given place, if we are empty, we do not know if we have to go up or down
can we always go in a given direction?

size of example = 8
middle = 4
value of middle = b
b is smaller than c
middle = 8-4-=4, 4/2 + 4 = 6
found

let's add more sparsity

["", "", "a", "", "", "", "b", "", "", "", "c","d"]
size = 12
range 0, 11

middle = 11-0 /2 = 5+0 → 5
value = a[5] = ""
let's always go down
i = 2, a[2] = "a"
which is lower than 'c'
new middle = 11-2 / 2 = 4 + 2 = 6
which is by chance 'b', but it could well be "" and we're stuck in an infinite loop

different strategy, look for both and decide which one is the best

upper = size -1 or (size?)
lower = 0
middle = 11-0 /2 = 5+0 → 5
value = a[5] = ""
if we land on a value, we assume it's the down one, otherwise we look of it
down a[2] = "a"
up [6] = "b"

we know for sure we are not in between these two, so we're either below down or above up
if looking for<down → new range is lower ←→ down
otherwise → new range is up ←→ upper


let's see if we have ["", "", "a", "b"]
size = 4
lower = 0
upper = 3
middle will be 3-0 /1
find a
lower = 3
upper = 3
middle 3-3 / 2 = 0 +3 = 3 which is correct, we go for size

*/


public static <T extends Comparable<? super T>> int sparseFind(List<T> l, T v) {
	if (v==null) {
		return -1;
}
	return sparseFindRange(l, v, 0, l.size()-1);
}

public static <T extends Comparable<? super T>> int sparseFindRange(List<T> l, T v, int lower, int upper) {

	if (upper <lower) {
		// not found in this
		return -1;
}
	int middle = ((upper-lower)/2) + lower;
	int i = middle+1;
	while (i>lower && l.get(--i)==null);
	T lowerVal = l.get(i);

	int j = middle-1;
	while (j<upper && l.get(++j)==null);
	T higherVal = l.get(j);
	// now lower and higher value are either "" or have a value

	// base cases first
	int compareWithLow =  lowerVal == null ? 1 :  v.compareTo(lowerVal);
	int compareWithHigh = higherVal == null ? -1 : v.compareTo(higherVal);
	if (compareWithLow==0) {
		return i;
} else if (compareWithHigh==0) {
	return j;
} else if (compareWithLow>0) { // can be in higher range
	return sparseFindRange(l, v, j+1, upper);
} else { // can be in lower range
	return sparseFindRange(l, v, lower,i-1);
}

}


}

/*
 *    Copyright 2024 Daniel Giribet
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

