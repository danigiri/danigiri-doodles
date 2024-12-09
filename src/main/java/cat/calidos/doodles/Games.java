package cat.calidos.doodles;

import java.util.List;


public class Games {

/*
 * 
 * Greed is a dice game played with five six-sided dice. Score a throw according to these rules. You
 * will always be given an array with five six-sided dice values.
 * //@formatter:off
 * Three 1's => 1000 points 
 * Three 6's => 600 points 
 * Three 5's => 500 points 
 * Three 4's => 400 points
 * Three 3's => 300 points 
 * Three 2's => 200 points 
 * One 1 => 100 points 
 * One 5 => 50 points
 * //@formatter.on
 * 
 * A single die can only be counted once in each roll. For example, a given "5" can only count as
 * part of a triplet (contributing to the 500 points) or as a single 50 points, but not both in the
 * same roll. Example scores:
 * 
 * Throw Score --------- ------------------ 5 1 3 4 1 250: 50 (for the 5) + 2 * 100 (for the 1s) 1 1
 * 1 3 1 1100: 1000 (for three 1s) + 100 (for the other 1) 2 4 4 5 4 450: 400 (for three 4s) + 50
 * (for the 5)
 * 
 * The input cannot be mutated
 * 
 * we can have a bucket for each score, increase and then reconcile 3x1: 1,2,3,4 3x6: 3x5: 3x4:
 * 3x3:1 3x2: 1x1: 1,1,1,1,1 1x5:
 * 
 * to simplify, we have two arrays
 * 
 */

public static int greedy(int[] dice) {

	var triples = new int[] { -1, 0, 0, 0, 0, 0, 0 };
	var singles = new int[] { -1, 0, -1, -1, -1, 0, -1 };

	for (int i = 0; i < 5; i++) {
		var v = dice[i];
		triples[v]++;
		singles[v]++;
	}

	var score = 0;
	for (int j = 6; j >= 1; j--) {
		var previousScore = score;
		score += scoreGreedyBucket(triples, j);
		if (score != previousScore) {
			singles[j] -= 3;
		}
	}
	score = score + singles[1] * 100;
	score = score + singles[5] * 50;

	return score;
}


private static int scoreGreedyBucket(	int[] triples,
										int index) {
	var scores = new int[] { 0, 1000, 200, 300, 400, 500, 600 };
	var v = triples[index];
	if (v >= 3) {
		return scores[index];
	}
	return 0;
}

// connect four: 7 rows (A->G), 6 cols
// given a list of moves like 'A_Red', 'G_Yellow', ..., return 'Yellow', 'Red' or 'Draf'
// max 42 moves
// option A:
// 1) given a state, we know if there is a winner
// 2) we update state
// 3) check winner
// continue until win or exhaust moves
// option B:
// 0) update board
// 1) given recent update, check if if creates a winner move
// continue until win or exhaust
//

public record Cell(boolean taken, String color, int r, int col) {
}

public static String whoIsWinner(List<String> piecesPositionList) {
	// initialise the gaming board
	var board = new Cell[7][6];
	for (var i = 0; i < 7; i++) {
		for (var j = 0; j < 6; j++) {
			board[i][j] = new Cell(false, null, i, j);
		}
	}
	String winner = null;
	var i = 0;
	while (winner == null && i < piecesPositionList.size()) {
		var play = piecesPositionList.get(i++);
		var row = getRow(play);
		var color = play.substring(2);
		var col = updateBoard(board, color, row);
		System.out.println(play);
		System.out.println(paint(board));
		winner = isWin(board, color, row, col) ? color : null;
	}

	return winner == null ? "Draw" : winner;
}


private static int getRow(String play) {
	return (int) play.charAt(0) - (int) 'A';
}


private static int updateBoard(	Cell[][] board,
								String color,
								int r) {
	var row = board[r];
	var dest = 5;
	while (row[dest--].taken) {
	}
	dest++;
	row[dest] = new Cell(true, color, r, dest);
	board[r] = row;
	return dest;
}


private static boolean isWin(	Cell[][] board,
								String color,
								int r,
								int c) {
	return (adj(board, color, r, c, 1, 0) + adj(board, color, r, c, -1, 0) - 1) >= 4 || // horizontal
			(adj(board, color, r, c, 0, 1) + adj(board, color, r, c, 0, -1) - 1) >= 4 || // vertical
			(adj(board, color, r, c, -1, -1) + adj(board, color, r, c, 1, 1) - 1) >= 4 || // diag 1
			(adj(board, color, r, c, -1, 1) + adj(board, color, r, c, 1, -1) - 1) >= 4; // diag 2
}


private static int adj(	Cell[][] board,
						String color,
						int r,
						int c,
						int rDir,
						int cDir) {
	var stop = false;
	var ri = r;
	var cj = c;
	var steps = 0;
	while (!stop && ri >= 0 && ri < 7 && cj >= 0 && cj < 6) {
		var cell = board[ri][cj];
		if (cell.taken && cell.color.equals(color)) {
			steps++;
			if (steps == 4) {
				stop = true;
			}
		} else {
			stop = true;
		}
		ri = ri + rDir;
		cj = cj + cDir;
	}
	return steps;
}


private static String paint(Cell[][] board) {
	var out = new StringBuffer();
	out.append(" 012345\n");
	for (var i = 0; i < 7; i++) {
		var row = new StringBuffer(8);
		row.append(i);
		for (var j = 0; j < 6; j++) {
			var cell = board[i][j];
			row.append(cell.taken ? cell.color.charAt(0) : ' ');
		}
		row.append("\n");
		out.append(row);
	}
	out.append("\n");
	return out.toString();
}

}

/**
 * Copyright 2024 Daniel Giribet <dani - calidos.cat>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
