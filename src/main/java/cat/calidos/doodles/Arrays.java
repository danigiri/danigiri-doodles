package cat.calidos.doodles;

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

	return i == SIZE ? Optional.of(candidate) : Optional.empty();

}


private static <X> Optional<X> colWinner(X[][] board, int col) {

	X candidate = board[0][col];
	int i = 1;
	while (i < SIZE && candidate != null && candidate.equals(board[i++][col])) {}

	return i == SIZE ? Optional.of(candidate) : Optional.empty();

}


private static <X> Optional<X> diagonalWinner(X[][] board) {

	X candidate = board[0][0];
	int i = 1;
	int j = 1;
	while (i < SIZE && j < SIZE && candidate != null && candidate.equals(board[i++][j++])) {}
	if (i == SIZE && j == SIZE) {
		return Optional.of(candidate);
	}

	i = 1;
	j = SIZE - 1;
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

