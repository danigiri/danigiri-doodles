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

