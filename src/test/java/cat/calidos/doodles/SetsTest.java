// SETS TEST . JAVA

package cat.calidos.doodles;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
*	@author daniel giribet
*///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class SetsTest {


@Test @DisplayName("All subsets test")
public void allSubsetsTest() {

	Set<String> set = new HashSet<String>(3);
	set.add("a");
	set.add("b");
	set.add("c");

	Set<Set<String>> expected = new HashSet<Set<String>>(8);
	Set<String> s = new HashSet<String>();
	s.add("a");
	s.add("b");
	s.add("c");
	expected.add(s);
	s = new HashSet<String>();
	s.add("a");
	s.add("b");
	expected.add(s);
	s = new HashSet<String>();
	s.add("a");
	s.add("c");
	expected.add(s);
	s = new HashSet<String>();
	s.add("b");
	s.add("c");
	expected.add(s);
	s = new HashSet<String>();
	s.add("a");
	expected.add(s);
	s = new HashSet<String>();
	s.add("b");
	expected.add(s);
	s = new HashSet<String>();
	s.add("c");
	expected.add(s);
	s = new HashSet<String>();
	expected.add(s);

	assertEquals(expected, Sets.allSubsets(set));

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

