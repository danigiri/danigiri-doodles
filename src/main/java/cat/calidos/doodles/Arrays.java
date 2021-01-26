package cat.calidos.doodles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
*	@author daniel giribet
*///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class Arrays<X> {

//16.4, design an algorithm to find out if somebody has won a game of tic tac toe

//for each left row I check the whole row, no need to right row
//for each top col, I check the col, no need to do the bottom col	
//I check the two diagonals manually
//solution would work with any size, can trivially change to hardcoded expressions

public static <X> Optional<X> ticTacToeWinner(X[][] board) {

	Optional<X> winner = Optional.empty();

	if (board == null || board.length != SIZE) {
		throw new IllegalArgumentException("bad argos");
	}

	// first check horizontal and vertical
	int i = 0;
	while (i < SIZE && winner.isEmpty()) {
		winner = rowWinner(board[i++]);
	}
	if (!winner.isEmpty()) {
		return winner;
	}
	i = 0;
	while (i < SIZE && winner.isEmpty()) {
		winner = colWinner(board, i++);
	}
	if (!winner.isEmpty()) {
		return winner;
	}

	// lastly, check diagonals
	return diagonalWinner(board);

}


private static int SIZE = 3;

private static <X> Optional<X> rowWinner(X[] row) {

	X candidate = row[0];
	int i = 1;
	while (i < SIZE && candidate != null && candidate.equals(row[i++])) {}

	return i == SIZE  && candidate.equals(row[i-1])? Optional.of(candidate) : Optional.empty();

}


private static <X> Optional<X> colWinner(X[][] board, int col) {

	X candidate = board[0][col];
	int i = 1;
	while (i < SIZE && candidate != null && candidate.equals(board[i++][col])) {}

	return i == SIZE && candidate.equals(board[i-1][col]) ? Optional.of(candidate) : Optional.empty();

}


private static <X> Optional<X> diagonalWinner(X[][] board) {

	X candidate = board[0][0];
	int i = 1;
	int j = 1;
	while (i < SIZE && j < SIZE && candidate != null && candidate.equals(board[i++][j++])) {}
	if (i == SIZE && j == SIZE) {
		return Optional.of(candidate);
	}

	candidate = board[0][SIZE-1];
	i = 1;
	j = SIZE - 2;
	while (i < SIZE && j >= 0 && candidate != null && candidate == board[i++][j--]) {}

	return i == SIZE && j < 0 ? Optional.of(candidate) : Optional.empty();

}


public static <X> void rotateInPlace(List<X> v, int pos) {

	if (v==null || pos==0 || v.size()<2 || v.size()==pos) {
		return;
	}

	for (int i=0; i<pos; i++) {
		shiftOne(v);
	}

}

private static <X> void shiftOne(List<X> v) {
	
	X first = v.get(0);
	int size = v.size();
	for (int i=1; i<size; i++) {
		v.set(i-1, v.get(i));
	}
	v.set(size-1, first);

}


//16.6 smallest difference, given two arrays of integers compute the pair of values (one value // in each array) with the smallest difference (non-negative). Return the difference
//a: [1, 2, 3, 4, 66]
//b: [9, 6, 7]
//returns 2, which is 4-6
//arrays can be different size
//duplicates can exist
//approach one, naive implementation
//sort the two arrays 2 x O(n log n)
//merge them O(n), keeping track of which is which
//[1, 2, 3, 4, 6, 7, 9, 66]
//[a, a, a, a, b, b, b, a]
//look for adjacent pairs, keep the smallest, return, O(n)
//currentValue=c[i], currentArray=lookup[i],
//while lookup[i++]==currentArray[i], continue
//minCandidate ← min calculation
//new candidate? swap
//we will assume sort is implemented
//if one of the arrays is empty, we consider error


public static int smallestDiff(List<Integer> a, List<Integer> b) {

	if (a == null || b == null) {
		throw new NullPointerException("bad params, null value(s)");
	}

	int aSize = a.size();
	int bSize = b.size();
	if (aSize == 0 || bSize == 0) {
		throw new IllegalArgumentException("bad params, empty array(s)");
	}

	Collections.sort(a);
	Collections.sort(b);


	// now we merge
	List<Integer> merged = new ArrayList<Integer>(aSize + bSize);
	List<Character> lookup = new ArrayList<Character>(aSize + bSize);

	int i = 0;
	int j = 0;
	while (i < aSize || j < bSize) {
		if (i >= aSize) {
			merged.add(b.get(j++));
			lookup.add('B');
		} else if (j >= bSize) {
			merged.add(a.get(i++));
			lookup.add('A');
		} else {
			int aCurrent = a.get(i);
			int bCurrent = b.get(j);
			if (aCurrent < bCurrent) {
				merged.add(a.get(i++));
				lookup.add('A');
			} else {
				merged.add(b.get(j++));
				lookup.add('B');
			}
		}
	}

	int c = 0;
	int min = -1;
	boolean lackMin = true;
	// we look for transitions from one array to the next, and keep min distance
	while (c<merged.size()) {
		char currentArray = lookup.get(c);
		int currentValue = merged.get(c);
		while (++c<merged.size() && lookup.get(c)==currentArray) {
			currentValue = merged.get(c);
		}
		// we are either at the end of we have found a transition
		if (c<merged.size()) {
			int minCandidate = Math.abs(currentValue-merged.get(c));
			if (lackMin || minCandidate<min) {
				min = minCandidate;
				lackMin = false;
			}
		}
	}

	return min;

}

//8.3 a magic index in an array A[0...n-1] is defined as A[i]==i
//given a sorted array of distinct integers, find the magic index, if one exists
//brute force it would be if we go element by element, but given it’s sorted, can we
//optimize and go for log(n)? yes

//go to the middle element
//is it magic?
//if so, return
// is it smaller than the index, discard all left elements
// is it bigger than the index, can call from the max(current, index) to end of range
// [-1, -1, -1, 2, 3, 5 ]
// [-1, 1, 5, 6, 7, 8, 9]

public static Optional<Integer> magicIndex(java.util.List<Integer> a) {
	return (a==null || a.size()==0) ? Optional.empty() : magicIndexRange(a, 0, a.size());
}


private static Optional<Integer> magicIndexRange(java.util.List<Integer> a, int start, int end) {

	if (start >= end) { 		// base case, out of bounds
		return Optional.empty();
	}

	int index = start + ((end - start) / 2);
	int current = a.get(index);
	if (current == index) {			 // base case, magic found
		return Optional.of(index);
	} else if (current < index) {	// recursive case, cannot discard anything
		Optional<Integer> right = magicIndexRange(a, index + 1, end);
		if (right.isPresent()) {
			return right;
		}
		return magicIndexRange(a, start, index);
	} else { 						// recursive case, can discard right hand side, as all will be bigger
		return magicIndexRange(a, start, index);
	}

}


//10.3 given a sorted array of n integers that has been rotated an unknown number of times, 
//write the code to find an element in the array, original sorting is increased order
//what we can do is find the place where the order is reversed, we should be able to find it
//given log(n) time
//if we find the element during that search, return (lucky)
//once we know the point where the cut off point is we have two sub arrays
//establish where in the two the element may be and then perform a basic log(n) search

//cut off point
//get element in the middle, is the next element (mod size) smaller?
//if so, found!
//if not, the cut off point is either left or right of the middle element, guaranteed
//take middle element on the left,
//is it bigger? it means the cut off is on the left, between middle element and current
//is it smaller? we do not know
//if we do not know
//take middle element on the right, is it smaller? cutoff is right, between current and end
//if it’s neither of the two
//[1, 2, 3, 4, 5, 6, 7, 8, 9]
//[2, 3, 4, 5, 6, 7, 8, 9, 1]
//[      M     x     M      ]
//this means the cut off point is either in [start, left middle] or in [right middle, end]
//make recursive call, yay, log(n)
//
//alternative, we have an algorithm that returns the two sorted ranges
//if range size==1
//return [start,end] and []
//if range size==2
//if a[start]<a[start+1]
//return [start, end], []


public static Optional<Integer> findInRotatedArray(List<Integer> a, int target) {

	if (a == null || a.size() == 0) {
		return Optional.empty();
	}
	int c = cutOffPoint(a, 0, a.size()).get(); // worst case is cutoff is at the end
	// find out in which of the two segments may have the element, there are three cases
	// we rotated to original position, equivalent to being in the second segment
	// or we are in the first one
	if (target <= a.get(a.size() - 1)) { // it’s on the second segment
		return findInSortedRange(a, target, c, a.size());
	} else { // it’s on the first segment
		return findInSortedRange(a, target, 0, c - 1);
	}

}


// return first element that is smaller, start of logical array
private static Optional<Integer> cutOffPoint(List<Integer> a, int start, int end) {

	int size = end - start;
	if (size <= 1) {
		return Optional.empty();
	}
	if (size == 2) {
		if (a.get(start) > a.get(start + 1)) {
			return Optional.of(start + 1); // found
		} else {
			return Optional.empty(); // not here
		}
	}

	// we have at least 3 elements
	int	middle	= start + size/2;
	int	middleValue	= a.get(middle);
	int leftPos = start + (middle-start)/2;
	if(a.get(leftPos)>middleValue) { // guaranteed to be here
		return cutOffPoint(a, leftPos, middle + 1);
	}
	int rightPos = middle + (end-middle)/2;
	if (a.get(rightPos)<middleValue) { // guaranteed to be here
		return cutOffPoint(a, middle, rightPos + 1);

	}
	// it’s on either left of leftPos or right of rightPos
	Optional<Integer> leftCandidate = cutOffPoint(a, start, middle + 1);
	return leftCandidate.isPresent() ? leftCandidate : cutOffPoint(a, rightPos, end);

}


private static Optional<Integer> findInSortedRange(List<Integer> a, int e, int start, int end) {

	// base cases
	if (start >= end) {
		return Optional.empty();
	}
	if (start == end - 1) {
		return a.get(start) == e ? Optional.of(start) : Optional.empty();
	}
	int middle = start + (end - start / 2);
	int middleValue = a.get(middle);
	if (middleValue == e) {
		return Optional.of(middle);
	}
	// recursive cases, log(n)
	if (e < middleValue) {
		return findInSortedRange(a, e, start, middle);
	} else {
		return findInSortedRange(a, e, middle + 1, end);
	}

}


}

/*
 *    Copyright 2019 Daniel Giribet
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

