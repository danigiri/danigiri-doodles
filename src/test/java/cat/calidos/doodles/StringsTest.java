/**
 Copyright 2014 Daniel Giribet <dani - calidos.cat>

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
*/
package cat.calidos.doodles;

import static org.junit.Assert.*;

import org.junit.Test;


public class StringsTest {

@Test
public void uniqueTest() {

	assertTrue(Strings.unique("a"));
	assertTrue(Strings.unique("abc"));
	assertFalse(Strings.unique("abca"));

}


@Test
public void longestSubThreeTest() {
	
	assertEquals(null, Strings.longestSubThree(null));
	assertEquals("", Strings.longestSubThree(""));
	assertEquals("", Strings.longestSubThree("a"));
	assertEquals("", Strings.longestSubThree("aaa"));
	assertEquals("", Strings.longestSubThree("ab"));
	assertEquals("", Strings.longestSubThree("aabb"));
	assertEquals("abc", Strings.longestSubThree("abc"));
	assertEquals("bcdd", Strings.longestSubThree("abcdd"));
	assertEquals("ddefff", Strings.longestSubThree("abbcddefff"));

}


}
