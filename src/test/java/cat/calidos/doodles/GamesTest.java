package cat.calidos.doodles;

import static org.junit.Assert.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GamesTest {


@Test
@DisplayName("Subtree test")
public void greedTest() {
	assertEquals(250, Games.greedy(new int[] {5, 1, 3, 4, 1}));
	assertEquals(1100, Games.greedy(new int[] {1, 1, 1, 3, 1}));
	assertEquals(450, Games.greedy(new int[] {2, 4, 4, 5, 4}));
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
