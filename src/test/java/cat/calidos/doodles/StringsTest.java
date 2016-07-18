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

import java.util.List;

import org.junit.Test;

import cat.calidos.doodles.builders.ListFrom;


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


@Test
public void anagramTest() {
	
	assertTrue(Strings.isAnagramOf("", ""));
	assertTrue(Strings.isAnagramOf("a", "a"));

	assertFalse(Strings.isAnagramOf("a", "b"));
	assertFalse(Strings.isAnagramOf("a", ""));
	
	assertTrue(Strings.isAnagramOf("ab", "ba"));
	assertTrue(Strings.isAnagramOf("abc", "cba"));
	assertTrue(Strings.isAnagramOf("abc", "bca"));
	assertTrue(Strings.isAnagramOf("abc", "abc"));
	assertTrue(Strings.isAnagramOf("abc", "cba"));

	assertFalse(Strings.isAnagramOf("abcd", "abc"));
	assertFalse(Strings.isAnagramOf("abcd", "abxd"));
	assertFalse(Strings.isAnagramOf("aaa", "a"));

	assertTrue(Strings.isAnagramOf("arrozx", "zorrxa"));

}


@Test
public void reverseCStringTest() {

	assertEquals("", Strings.reverseCString(""));
	assertEquals("a", Strings.reverseCString("a"));

	assertEquals("aaa", Strings.reverseCString("aaa"));
	assertEquals("ba", Strings.reverseCString("ab"));
	assertEquals("dcba", Strings.reverseCString("abcd"));

}

@Test
public void removeDuplicatesInPlaceTest() {

	assertEquals("", Strings.removeDuplicatesInPlace(""));
	assertEquals("a", Strings.removeDuplicatesInPlace("a"));

	assertEquals("a", Strings.removeDuplicatesInPlace("aaa"));
	assertEquals("ba", Strings.removeDuplicatesInPlace("ba"));
	assertEquals("abcd", Strings.removeDuplicatesInPlace("abcdaa"));

	assertEquals("abc", Strings.removeDuplicatesInPlace("aabc"));
	assertEquals("abcd", Strings.removeDuplicatesInPlace("aabbcccdc"));

}


@Test
public void suffixTreeTest() {
	
	List<String> l = ListFrom.strings("abb", "abb", "abbc", "abbd", "abbe", "az", "x");
	
	Tree<String> t = new Tree<String>("");

	Tree<String> bb = new Tree<String>("bb");
	bb.addChild("c", new Tree<String>("c"));
	bb.addChild("d", new Tree<String>("d"));
	bb.addChild("e", new Tree<String>("e"));
	
	Tree<String> a = new Tree<String>("a");
	a.addChild("bb", bb);
	a.addChild("z", new Tree<String>("z"));
	t.addChild("a", a);
	t.addChild("x", new Tree<String>("x"));
	
	Tree<String> t1 = Strings.suffixTree(l);
	assertEquals(t.toString(), t1.toString());
	
}





}
