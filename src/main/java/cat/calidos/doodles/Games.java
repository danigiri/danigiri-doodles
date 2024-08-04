package cat.calidos.doodles;


public class Games {

/*
 * 
 * Greed is a dice game played with five six-sided dice. Score a throw according to these rules. You
 * will always be given an array with five six-sided dice values.
 * 
 * Three 1's => 1000 points Three 6's => 600 points Three 5's => 500 points Three 4's => 400 points
 * Three 3's => 300 points Three 2's => 200 points One 1 => 100 points One 5 => 50 point
 * 
 * A single die can only be counted once in each roll. For example, a given "5" can only count as
 * part of a triplet (contributing to the 500 points) or as a single 50 points, but not both in the
 * same roll. Example scores:
 * 
 * Throw Score --------- ------------------
 *  5 1 3 4 1 250: 50 (for the 5) + 2 * 100 (for the 1s)
 *  1 1 1 3 1 1100: 1000 (for three 1s) + 100 (for the other 1) 
 *  2 4 4 5 4 450: 400 (for three 4s) + 50 (for the 5)
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


private static int scoreGreedyBucket(int[] triples, int index) {
	var scores = new int[] {0, 1000, 200, 300, 400, 500, 600};
	var v = triples[index];
	if (v >= 3) {
		return scores[index];
	}
	return 0;
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

