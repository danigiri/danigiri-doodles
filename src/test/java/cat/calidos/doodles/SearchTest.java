// SEARCH TEST . JAVA

package cat.calidos.doodles;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cat.calidos.doodles.builders.ListFrom;

/**
*	@author daniel giribet
*///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class SearchTest {


@Test @DisplayName("Find in Listy test")
public void findInListyTest() {

	var l = ListFrom.ints(1, 2, 3, 4, 5, 5, 6, 7, 10);
	assertEquals(3, Search.findIn(l, 4));

}

@Test @DisplayName("Search in sparse array")
public void locateSparseTest() {

	//                        0    1   2   3   4    5   6   7    8    9   10
	var l = ListFrom.strings("a", "", "", "", "b", "", "", "c", "d", "", "ee");
	assertEquals(-1, Search.locateSparse(l, "x"));
	assertEquals(0, Search.locateSparse(l, "a"));
	assertEquals(4, Search.locateSparse(l, "b"));
	assertEquals(8, Search.locateSparse(l, "d"));
	assertEquals(10, Search.locateSparse(l, "ee"));

}


@Test @DisplayName("Search in sparse array")
public void sparseFindTest() {

	//
	var l0 = ListFrom.strings("a", null, null, null, null, null);
	assertEquals(0, Search.sparseFind(l0, "a"));

	//                        0    1     2     3      4   5     6      7    8   9      10
	var l1 = ListFrom.strings("a", null, null, null, "b", null, null, "c", "d", null, "ee");
	assertEquals(-1, Search.sparseFind(l1, "x"));
	assertEquals(0, Search.sparseFind(l1, "a"));
	assertEquals(4, Search.sparseFind(l1, "b"));
	assertEquals(8, Search.sparseFind(l1, "d"));
	assertEquals(10, Search.sparseFind(l1, "ee"));

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

