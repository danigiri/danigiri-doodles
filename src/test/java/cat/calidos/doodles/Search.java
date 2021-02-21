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


}

/*
 *    Copyright 2020 Daniel Giribet
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

