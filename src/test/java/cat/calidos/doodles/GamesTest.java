package cat.calidos.doodles;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cat.calidos.doodles.builders.ListFrom;


public class GamesTest {

@Test @DisplayName("Subtree test")
public void greedTest() {
	assertEquals(250, Games.greedy(new int[] { 5, 1, 3, 4, 1 }));
	assertEquals(1100, Games.greedy(new int[] { 1, 1, 1, 3, 1 }));
	assertEquals(450, Games.greedy(new int[] { 2, 4, 4, 5, 4 }));
}


@Test @DisplayName("Connect 4 test x1")
public void connectTest1() {
	List<String> myList = ListFrom
			.strings(
					"C_Yellow",
					"E_Red",
					"G_Yellow",
					"B_Red",
					"D_Yellow",
					"B_Red",
					"B_Yellow",
					"G_Red",
					"C_Yellow",
					"C_Red",
					"D_Yellow",
					"F_Red",
					"E_Yellow",
					"A_Red",
					"A_Yellow",
					"G_Red",
					"A_Yellow",
					"F_Red",
					"F_Yellow",
					"D_Red",
					"B_Yellow",
					"E_Red",
					"D_Yellow",
					"A_Red",
					"G_Yellow",
					"D_Red",
					"D_Yellow",
					"C_Red");
	//assertEquals("it should return Yellow", "Yellow", Games.whoIsWinner(myList));
}


@Test @DisplayName("Connect 4 test x2")
public void connectTes2t() {
	List<String> myList = ListFrom.strings(
					"A_Yellow",
					"B_Red",
					"B_Yellow",
					"C_Red",
					"G_Yellow",
					"C_Red",
					"C_Yellow",
					"D_Red",
					"G_Yellow",
					"D_Red",
					"G_Yellow",
					"D_Red",
					"F_Yellow",
					"E_Red",
					"D_Yellow");
	//assertEquals("it should return Red", "Red", Games.whoIsWinner(myList));
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
