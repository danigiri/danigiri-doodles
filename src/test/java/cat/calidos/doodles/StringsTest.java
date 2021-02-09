// STRINGS TEST . JAVA

package cat.calidos.doodles;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cat.calidos.doodles.builders.ListFrom;


public class StringsTest {


@Test @DisplayName("Test unique chars")
public void uniqueTest() {

	assertTrue(Strings.unique("a"));
	assertTrue(Strings.unique("abc"));
	assertFalse(Strings.unique("abca"));

}


@Test @DisplayName("Longest three repeated chars")
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


@Test @DisplayName("Anagram test")
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


@Test @DisplayName("Reverse C string")
public void reverseCStringTest() {

	assertEquals("", Strings.reverseCString(""));
	assertEquals("a", Strings.reverseCString("a"));

	assertEquals("aaa", Strings.reverseCString("aaa"));
	assertEquals("ba", Strings.reverseCString("ab"));
	assertEquals("dcba", Strings.reverseCString("abcd"));

}


@Test @DisplayName("Remove duplicates in place")
public void removeDuplicatesInPlaceTest() {

	assertEquals("", Strings.removeDuplicatesInPlace(""));
	assertEquals("a", Strings.removeDuplicatesInPlace("a"));

	assertEquals("a", Strings.removeDuplicatesInPlace("aaa"));
	assertEquals("ba", Strings.removeDuplicatesInPlace("ba"));
	assertEquals("abcd", Strings.removeDuplicatesInPlace("abcdaa"));

	assertEquals("abc", Strings.removeDuplicatesInPlace("aabc"));
	assertEquals("abcd", Strings.removeDuplicatesInPlace("aabbcccdc"));

}


@Test @DisplayName("Suffix tree")
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


@Test @DisplayName("Test if all chars are unique")
public void allCharsAreUniqueTest() {

	assertTrue(Strings.allCharsAreUnique(""));
	assertTrue(Strings.allCharsAreUnique("a"));
	assertTrue(Strings.allCharsAreUnique("abc"));
	assertFalse(Strings.allCharsAreUnique("abca"));
	assertFalse(Strings.allCharsAreUnique("aaaa"));

	assertTrue(Strings.allCharsAreUniqueInPlace(""));
	assertTrue(Strings.allCharsAreUniqueInPlace("a"));
	assertTrue(Strings.allCharsAreUniqueInPlace("abc"));
	assertFalse(Strings.allCharsAreUniqueInPlace("abca"));
	assertFalse(Strings.allCharsAreUniqueInPlace("aaaa"));

}


@Test @DisplayName("Test count number of occurrences of word")
public void testTrivialCount() {

	assertEquals(0, Strings.trivialCount("a", ""));
	assertEquals(0, Strings.trivialCount("a", "bx"));
	assertEquals(1, Strings.trivialCount("ab", "aaabbx"));
	assertEquals(2, Strings.trivialCount("ab", "aaaabaabbx"));
	assertEquals(0, Strings.trivialCount("abba", "abb"));

}


@Test @DisplayName("Test count number all word occurrences")
public void testCountWords() {

	Map<String, Integer> counts = new Strings().countWords("a ab a,d,x ee, d,  f");
	assertAll("Check basic word counts", 
		() -> assertNotNull(counts),
		() -> assertEquals(6, counts.size()),
		() -> assertTrue(counts.containsKey("a")),
		() -> assertTrue(counts.containsKey("ab")),
		() -> assertTrue(counts.containsKey("d")),
		() -> assertTrue(counts.containsKey("x")),
		() -> assertTrue(counts.containsKey("ee")),
		() -> assertTrue(counts.containsKey("f"))
	);

	assertAll("Check  word counts", 
		() -> assertEquals(2, counts.get("a")),
		() -> assertEquals(1, counts.get("ab")),
		() -> assertEquals(2, counts.get("d")),
		() -> assertEquals(1, counts.get("x")),
		() -> assertEquals(1, counts.get("ee")),
		() -> assertEquals(1, counts.get("f"))
	);

}


@Test @DisplayName("Test checking if two strings are permutations of each other")
public void arePermutationTest() {

	assertTrue(Strings.arePermutation("", ""));
	assertTrue(Strings.arePermutation("a", "a"));
	assertTrue(Strings.arePermutation("ab", "ba"));
	assertTrue(Strings.arePermutation("ab", "ab"));
	assertTrue(Strings.arePermutation("accbcc", "abcccc"));

	assertFalse(Strings.arePermutation("a", "ab"));
	assertFalse(Strings.arePermutation("", "ab"));
	assertFalse(Strings.arePermutation("accbxc", "abcccc"));

}


@Test @DisplayName("Test URL expansion")
public void expandURLTest() {

	char[] input	 = "abcd efg hi    ".toCharArray();
	char[] expected  = "abcd%20efg%20hi".toCharArray();
	assertEquals(new String(expected), new String(Strings.expandURL(input)));

	input	 = " abcd  ".toCharArray();
	expected = "%20abcd".toCharArray();
	assertEquals(new String(expected), new String(Strings.expandURL(input)));

	input	 = "  abcd    ".toCharArray();
	expected = "%20%20abcd".toCharArray();
	assertEquals(new String(expected), new String(Strings.expandURL(input)));

}


@Test @DisplayName("Test string palindrome permutations")
public void palindromePermutationTest() {

	assertTrue(Strings.isPalindromePermutation(""));
	assertTrue(Strings.isPalindromePermutation("a"));
	assertTrue(Strings.isPalindromePermutation("tat"));
	assertTrue(Strings.isPalindromePermutation("tta"));
	assertTrue(Strings.isPalindromePermutation("tact coa"));
	assertFalse(Strings.isPalindromePermutation("tact coat"));
	assertFalse(Strings.isPalindromePermutation("xxyz"));

}


@Test @DisplayName("Test max one change character in two strings")
public void maxOneChangeTest() {
	
	assertFalse(Strings.maxOneChange("aaa", "a"));
	assertTrue(Strings.maxOneChange("aaa", "aaa"));
	assertTrue(Strings.maxOneChange("aaaa", "aaa"));
	assertTrue(Strings.maxOneChange("aaa", "aaaa"));
	assertTrue(Strings.maxOneChange("aaa", "aaab"));
	assertTrue(Strings.maxOneChange("aaab", "aaa"));
	assertTrue(Strings.maxOneChange("abcd", "axcd"));
	assertTrue(Strings.maxOneChange("axcd", "abcd"));

	assertTrue(Strings.maxOneChange("abcd", "abcx"));
	assertTrue(Strings.maxOneChange("abcd", "abxcd"));
	assertTrue(Strings.maxOneChange("abxcd", "abcd"));

	assertTrue(Strings.maxOneChange("abcd", "acd"));
	assertTrue(Strings.maxOneChange("acd", "abcd"));
	assertTrue(Strings.maxOneChange("abcd", "bcd"));
	assertTrue(Strings.maxOneChange("bcd", "abcd"));

	assertTrue(Strings.maxOneChange("abcd", "abxcd"));
	assertTrue(Strings.maxOneChange("abxcd", "abcd"));
	assertTrue(Strings.maxOneChange("abcd", "xabcd"));
	assertTrue(Strings.maxOneChange("xabcd", "abcd"));

}



}

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
