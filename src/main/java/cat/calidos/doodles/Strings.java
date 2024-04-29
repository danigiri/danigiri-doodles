// STRINGS . JAVA

package cat.calidos.doodles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Strings {

public static boolean unique(String s) {

	Set<Integer> chars = new HashSet<Integer>();

	return stringNotInSet(s, chars);

}


private static boolean stringNotInSet(String s, Set<Integer> set) { // cost O(N)

	// base cases
	if (s == null || set == null) {
		throw new NullPointerException("Null data");
	}
	int sLength = s.length();

	if (sLength == 0) {
		return true;
	}

	// recursive case, reduce size,
	Integer c = (int) s.charAt(0); // always>0, protected by base case
	if (set.contains(c)) { // already found - cost O(K)
		return false;
	} else {
		set.add(c); // cost O(log N)
		String substring = s.substring(1, sLength); // (inclusive, exclusive) - cost O(K)
		return stringNotInSet(substring, set); // recursion is linear - cost O(N)
	}

}


// find the longest substring containing only 3 distinct characters of a given string:
//
// abcda → abc
// xabcda → xab
// xabbcda → xabb
// xabbcccda → abbccc
//
// Other examples
// null → null
// “” → “”
// “ab” → “”
// “abc” → “abc”
// “aaaa” → “”
// “aaaabbb” → “”


public static String longestSubThree(String s) {

	if (s == null) {
		return null;
	}
	if (s.length() == 0) {
		return "";
	}

	List<Map<String, Integer>> sub = new ArrayList<Map<String, Integer>>(3);
	Map<String, Integer> firstCharCount = Strings.newCounter(Character.toString(s.charAt(0)));
	sub.add(firstCharCount);

	sub = Strings.subThree_(s.substring(1, s.length()), sub);
	if (sub.size() < 3) {
		return "";
	}
	StringBuffer subBuffer = new StringBuffer();
	while (sub.size() > 0) {
		Map<String, Integer> char_ = sub.get(0);
		String c = char_.keySet().iterator().next();
		int n = char_.get(c);
		for (int i = 0; i < n; i++) {
			subBuffer.append(c);
		}
		sub.remove(0);
	}
	return subBuffer.toString();
}


private static List<Map<String, Integer>> subThree_(String s, List<Map<String, Integer>> max) {

	// base cases
	int sLength = s.length();
	if (sLength == 0) {
		return max;
	}

	// recursive cases
	Map<String, Integer> char_ = max.get(max.size() - 1);
	String current = char_.keySet().iterator().next();
	int currentCount = char_.get(current);

	String c = Character.toString(s.charAt(0));

	if (c.equals(current)) {
		char_.put(c, ++currentCount);
		return subThree_(s.substring(1, sLength), max); // induction, ’s’ is smaller
	}

	Map<String, Integer> newCount = Strings.newCounter(c);
	// moving into a new char
	if (max.size() < 3) {
		max.add(newCount);
		return subThree_(s.substring(1, sLength), max); // induction
	}

	List<Map<String, Integer>> nextMax = new ArrayList<Map<String, Integer>>(3);
	nextMax.add(max.get(1));
	nextMax.add(max.get(2));
	nextMax.add(newCount);

	nextMax = Strings.subThree_(s.substring(1, sLength), nextMax); // induction

	int currentTotal = Strings.countTotal(max);
	int nextTotal = Strings.countTotal(nextMax);
	if (currentTotal >= nextTotal) {
		return max;
	} else {
		return nextMax;
	}

}


private static Map<String, Integer> newCounter(String c) {
	Map<String, Integer> m = new HashMap<String, Integer>(1);
	m.put(c, 1);
	return m;
}


private static int countTotal(List<Map<String, Integer>> l) {
	int count = 0;
	for (Map<String, Integer> c : l) {
		count += c.get(c.keySet().iterator().next());
	}
	return count;
}


// given a set of character deltas from two words and a char, compute the new delta set
// words: "abc", "bd" --> a: -1, b: 0, c: -1, d: +1
private static void countDeltaForChar(Map<String, Integer> deltas, char c, int deltaForChar) {
	String sc = String.valueOf(c);
	if (deltas.containsKey(sc)) {
		int charCount = deltas.get(sc);
		deltas.put(sc, charCount + deltaForChar);
	} else {
		deltas.put(sc, deltaForChar);
	}
}


/*
 * Return true if the two words are anagrams
 *//////////////////////////////////////////////////////////////////////////////////////////////////
public static boolean isAnagramOf(String a, String b) {

	int aSize = a.length();
	if (aSize != b.length()) {
		return false;
	}

	// TODO: optimise for max delta or abs(minimum delta) > chars left to process :)
	Map<String, Integer> deltas = new HashMap<String, Integer>(aSize);
	for (int i = 0; i < aSize; i++) {
		char aChar = a.charAt(i);
		char bChar = b.charAt(i);
		if (aChar != bChar) {
			countDeltaForChar(deltas, aChar, 1);
			countDeltaForChar(deltas, bChar, -1);
		}
	}

	return !deltas.values().stream().filter(d -> d != 0).findAny().isPresent();

}


// Write code to reverse a C-Style String (C-String means that “abcd” is
// represented as ve characters, including the null character )
public static String reverseCString(String s) {
	if (s == null) {
		throw new NullPointerException("Null s to reverse");
	}

	int sl = s.length();
	if (sl <= 1) {
		return s;
	}
	return reverseCString(s.substring(1, sl)) + s.substring(0, 1);
}


// Design an algorithm and write code to remove the duplicate characters in a string without using
// any additional buffer
// NOTE: One or two additional variables are ok but an extra copy of the array is not!


// OK, let’s implement an algorithm in place based on the idea that we hold a substring of the
// unique
// characters at the beginning in the order supplied in the original string, no expensive
// linear replacing ops
// abcad [a]xcda, [ax]cda, [axcd]a —> axcd
public static String removeDuplicatesInPlace(String s) {
	if (s == null) {
		return null;
	}
	if (s.length() <= 1) {
		return s;
	}
	// using a string builder as we have to modify it to use it in place
	// but we're only using one buffer in the algorithm itself
	// a char array could also be used in the same way
	StringBuilder sb = new StringBuilder(s);
	int finalLength = findUniqueCharsStringInPlace(0, sb);
	return sb.substring(0, finalLength + 1);
}


// technically, this is O(N), as the checking on each will be limited to the maximum
// number of characters we have in our character space (unicode is quite large though)
// this can be really fast if we had only ASCII chars or a similar space
private static int findUniqueCharsStringInPlace(int boundary, StringBuilder s) {

	int n = s.length();
	for (int i = boundary + 1; i < n; i++) {
		char c = s.charAt(i);
		boolean found = false;
		int j = boundary;
		while (!found && j >= 0) {
			found = s.charAt(j) == c;
			j--;
		}
		if (!found) {
			boundary++;
			s.setCharAt(boundary, c);
		}
	}

	return boundary;

}


// from a list of strings return a suffix tree
// abc,abd,c,cd —>
// “”
// ab c
// d d


public static Tree<String> suffixTree(List<String> strings) {

	if (strings == null) {
		return null;
	}
	return suffixTree_(strings, new Tree<String>(""));

}


private static Tree<String> suffixTree_(List<String> strings, Tree<String> t) {

	// base cases
	if (strings.size() == 0) {
		return t;
	}
	// strings.stream().forEach(s -> {
	for (String s : strings) {
		boolean found = false;
		Iterator<String> children = t.children.keySet().iterator();
		while (!found && children.hasNext()) { // while
			String childK = children.next();
			if (!s.equals(childK)) {
				String pref = commonPrefix(childK, s);
				if (pref.length() > 0) {
					// found a prefix, handle recursive case:
					String sPostfix = s.substring(pref.length());
					List<String> postfixes = new ArrayList<String>(1);
					postfixes.add(sPostfix);
					Tree<String> child = t.getChild(childK);
					if (pref.length() < childK.length()) { // child='aab', s='aa', sprout
						String newChildPref = childK.substring(pref.length()); // so child='aa' ->
																				// 'b' with its
						Tree<String> nc = new Tree<String>(newChildPref); // old children assigned
																			// to it
						nc.addAll(child.children);
						child.data = pref; // set child to 'aa' and
						child.clearChildren(); // have a subtree which is 'b'
						child.addChild(newChildPref, nc);
					}
					child = suffixTree_(postfixes, child);
					t.addChild(childK, child);
					found = true;
				}
			} else {
				found = true; // duplicate, we skip it (base case)
			}
		} // end while
		if (!found) {
			// base case, new child
			t.addChild(s, new Tree<String>(s)); // brand new child of the tree
		}
		// });
	}

	return t;

}


private static String commonPrefix(String a, String b) {

	if (a == null || b == null) {
		return null;
	}
	StringBuilder prefix = new StringBuilder();
	boolean equals = true;
	int i = 0;
	int al = a.length();
	int bl = b.length();
	while (equals && i < al && i < bl) {
		char ac = a.charAt(i);
		char bc = b.charAt(i);
		equals = ac == bc;
		if (equals) {
			prefix.append(ac);
		}
		i++;
	}

	return prefix.toString();

}


// Brute force version, also non extra structures O(N^2)
// @return true if all the characters are unique
public static boolean allCharsAreUniqueInPlace(String s) {

	if (s == null) {
		throw new NullPointerException("Cannot distinguish uniqueness of null string");
	}

	boolean noRepeats = true;
	int i = 0;
	while (noRepeats && i < s.length()) {
		char current = s.charAt(i);
		int j = i + 1;
		while (noRepeats && j < s.length()) {
			noRepeats = current != s.charAt(j);
			j++;
		}
		i++;
	}

	return noRepeats;

}


// Non brute force, using structures
// @return true if all the characters are unique, O(N) <== O(N)*O(k)
public static boolean allCharsAreUnique(String s) {

	if (s == null) {
		throw new NullPointerException("Cannot distinguish uniqueness of null string");
	}

	Set<Character> foundChars = new HashSet<Character>();
	boolean noRepeats = true;
	int i = 0;
	while (noRepeats && i < s.length()) {
		char current = s.charAt(i);
		noRepeats = !foundChars.contains(current);
		if (noRepeats) {
			foundChars.add(current);
			i++;
		}
	}

	return noRepeats;

}


public static int trivialCount(String w, String t) {

	// we assume String lookups are constant, that String is equivalent to a char array
	if (w == null || t == null) {
		throw new NullPointerException("Bad parameters");
	}

	int count = 0;
	int i = 0;
	int length = t.length();
	while (i < length) {
		if (t.charAt(i) == w.charAt(0)) { // partial match
			int matchIndex = 0;
			while (matchIndex < w.length() && i < length && t.charAt(i) == w.charAt(matchIndex)) {
				matchIndex++;
				i++;
			}
			if (matchIndex == w.length()) {
				count++;
			}
		} else { // no partial, continue
			i++;
		}

	}

	return count;

}


// 16.2 word frequencies, for all words in a book. What if we were running this algorithm
// multiple times?
// we assume spaces are the only word separators, but we add it to the skip chars, that also
// mark (, “ etc.)
// algo:
// setup skip chars set, can use a sparse table, which means we have O(k)
// while not end of text
// skip blank chars and skip chars, when first char found
// select root char tree, if root char tree does not exist, create from scratch
// loop until blank or skip
// get next char
// if next char is in tree, continue
// if next char is not in tree, add node
// if looped, add +1 to last node
// continue with main loop

// second part, for each tree:
// do a depth first search for numerically tagged nodes, keeping a list of chars visited so far
// recursive will be easier

private class Node {

public char					c;
public int					count;
public Map<Character, Node>	children;


Node(char s) {
	this.c = s;
	this.count = 0;
	this.children = new HashMap<Character, Node>();
}


@Override
public String toString() {
	return _toString(0).toString();
}


private StringBuffer _toString(int tab) {

	StringBuffer buffer = new StringBuffer();
	for (int i = 0; i < tab; i++) {
		buffer.append("\t");
	}
	buffer.append("[");
	buffer.append(c);
	if (count > 0) {
		buffer.append(":");
		buffer.append(count);
	}
	buffer.append("]");
	if (!this.children.isEmpty()) {
		buffer.append("\n");
		children.values().forEach(c -> buffer.append(c._toString(tab + 1)));
	}

	return buffer;

}

}


public Map<String, Integer> countWords(String t) {

	//// first part, build the tree ////
	if (t == null) {
		throw new NullPointerException("Bad input string");
	}

	Set<Character> stop = new HashSet<Character>(4);
	stop.add(' ');
	stop.add(',');
	stop.add('.');

	Map<Character, Node> trees = new HashMap<Character, Node>();
	int i = 0;
	int length = t.length();
	while (i < length) {

		char c = t.charAt(i++); // skip stop chars
		while (i < length && stop.contains(c)) {
			c = t.charAt(i++);
		}
		if (!trees.containsKey(c)) {
			trees.put(c, new Node(c));
		}
		Node node = trees.get(c);
		while (i < length && !stop.contains(c)) { // current word until end||stop char
			c = t.charAt(i++);
			if (!stop.contains(c)) { // word continues
				if (!node.children.containsKey(c)) {
					node.children.put(c, new Node(c));
				}
				node = node.children.get(c);
			}
		}
		node.count++;
		// at this moment we are either at end or at a stop char, skip loop will iterate

	}


	//// second part, parse the tree to build output ////
	Map<String, Integer> counts = new HashMap<String, Integer>();
	trees.values().stream().forEach(tree -> countWordTree(tree, new StringBuffer(), counts));

	return counts;

}


private static void countWordTree(Node t, StringBuffer w, Map<String, Integer> counts) {

	// base case
	w.append(t.c);
	if (t.count > 0) {
		String word = w.toString();
		counts.put(word, t.count);
	}

	// recursive cases, by induction, child tree is smaller than t,
	// and the post condition leaves the w buffer constant
	t.children.values().forEach(c -> countWordTree(c, w, counts));

	// post condition: we do not modify the w buffer, notice w is at least 1 characters long
	int length = w.length();
	w.delete(length - 1, length);

}


// 1.2 check permutations, fivent two strings, write a method to decide if one is a permutation
// of the other
// we treat the string as char arrays
// [‘a’, ‘b’, ‘c’,’c’,’e’]
// [‘a’,’c’,’e’,’c’,’b’] → true
/// “aa” → false, etc.
// we check the length first, if it’s different, no permutation
// if it’s the same, we create an array or map of counts of each character for the first string
// and on the second we decrement
// at the point we reach zero, we remove from the map
// if the map is empty at the end, it’s a permutation
// it’s logically equivalent to an array of chars and then doing a sweep to check all is zeros


public static boolean arePermutation(String a, String b) {

	if (a.length() != b.length()) {
		return false;
	}

	Map<Character, Integer> counts = new HashMap<Character, Integer>();
	a.chars().mapToObj(c -> (char) c).forEach(c -> {
		if (counts.containsKey(c)) {
			counts.put(c, counts.get(c) + 1);
		} else {
			counts.put(c, 1);
		}
	});

	boolean finished = false;
	int i = 0;
	while (!finished && !counts.isEmpty()) {
		char currentChar = b.charAt(i++);
		if (counts.containsKey(currentChar)) {
			int currentCharCount = counts.get(currentChar);
			if (currentCharCount > 1) {
				counts.put(currentChar, currentCharCount - 1);
			} else {
				counts.remove(currentChar);
			}
		} else {
			finished = true;
		}
	}

	return counts.isEmpty();

}


// 1.3 given a string (array of chars in java), replace all occurrences of a space with %20
// assume you have enough space at the end, do the replacement in place
// we assume the space at the end is actually the exact one to hold everything in place
// okay
// “abcd efg hi ” → “abcd%20efg%20hi”
// we can iterate and count the number of spaces O(n)
// then start from the end and start ‘moving’ + nspacesleft*2 into the end
// once we find a space, we append %20 to the end, decrement nspacesleft, skip the space
// continue until nspacesleft==0, we do not need to modify the rest of array
// O(n+n) ⇒ O(n) voila!
// this one would work if the space is not exact, as you do not know how many positions
// you have to move
// we can make it faster if we build onto the assumption that we have exact space
// start from the end, copy chars, when space is found, expand, continue until start
// we assume that we have spaces at the end to make room
// we also assume that the last space is not expandable
// an space-only array is considered empty
// O(n) voila!


public static char[] expandURL(char[] str) {

	int length = str.length;
	int reader = length;
	while (reader > 0 && str[--reader] == ' ') {
	}
	if (reader == 0) {
		return str;
	}
	// reader currently points to last char of string
	int writer = length - 1;
	// note we want to process until very first char, could be that we have to expand it
	while (reader >= 0) {

		char current = str[reader--];
		if (current == ' ') {
			str[writer--] = '0';
			str[writer--] = '2';
			str[writer--] = '%';
		} else {
			str[writer--] = current;
		}

	}

	return str;

}


// 1.4 given a string, write a function to check if it is a permutation of a palindrome
// you can ignore casing and non letter chars
// example ‘tact coa’, output: true (permutations: taco cat, acto cta

// basically the number of instances for a given letter has to be even except for one single
// letter that can be uneven (as it could be on the middle of the palindrome)
// we build a map of letters and count them
// at the end, we look for uneven numbers, if we find more than one, return false
// otherwise return true

private static char[] ALLOWED_CHARS = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
		'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

public static boolean isPalindromePermutation(String s) {

	Set<Character> allowed = new HashSet<Character>(ALLOWED_CHARS.length);
	for (int i = 0; i < ALLOWED_CHARS.length; i++) {
		allowed.add(ALLOWED_CHARS[i]);
	}

	Map<Character, Integer> counts = new HashMap<Character, Integer>(ALLOWED_CHARS.length);

	s.chars().filter(c -> allowed.contains((char) c)).forEach(c -> {
		int count = counts.containsKey((char) c) ? counts.get((char) c) : 0;
		counts.put((char) c, ++count);
	});

	int evenCount = 0;
	Iterator<Character> keys = counts.keySet().iterator();
	while (evenCount <= 1 && keys.hasNext()) {
		if (counts.get(keys.next()) % 2 != 0) {
			evenCount++;
		}
	}

	return evenCount <= 1;

}


// 1.5 one away, given a two strings, write a function to check if they are one edit or zero
// edits away, edits are, char insert, replace insert or replace a char
// hola, hola → true
// hola, hogla → true
// hola, ola → true
// hola, thola → true
// hola, hulo → false, two replaces
// hola, hoa → true

// we have two indexes and a difference counter
// we iterate over them until the end or the difference counter >1
// when we find a difference, we increment the counter
// if we find a different char, we do not know if it's an insert, replace or delete
// look at the following item in the OK string, is it the same as the different char?
// assume delete and continue, otherwise
// look at the following item in the faulty string:
// if the following char is the same as the following one in the OK string, replace
// if the following char is the same as the current char in the OK string, insert
// ensure we check for out of bounds
// we assume string length is O(k)
// subsequent errors will stop the loop

// case A: are both next chars equal? replace
// hola
// hoba
// edge cases
// last char? → we're good
// hola
// hob → return false, two changes

// B: (del) are one of the current equal to the next one of the other? check both cases
// hola
// hoa
// edge cases
// hola
// hol, will be out of the loop

// C: (insert) the same as B, as current l is the same as next
// hola
// hoxla
// edge cases
// hola
// holax, will be out of the loop

//


public static boolean maxOneChange(String a, String b) {

	int lengthA = a.length();
	int lengthB = b.length();

	if (Math.abs(lengthA - lengthB) > 1) {
		return false;
	}

	int changes = 0;
	int i = 0;
	int j = 0;
	while (changes <= 1 && i < lengthA && j < lengthB) {

		char currentA = a.charAt(i++);
		char currentB = b.charAt(j++);
		if (currentA != currentB) { // check the cases:
			// last char
			if (i == lengthA || j == lengthB) { // a) diff is the last char
				changes++;
			} else {
				// test for replace case, guaranteed to have at least one char
				// for both strings
				char nextA = a.charAt(i);
				char nextB = b.charAt(j);
				if (nextA == nextB) { // b) replace, increment but good to go
					changes++;
				} else { // check for delete or insert, good
					if (currentA == nextB) { // c) delete or insert on a
						changes++;
						i--;
					} else if (currentB == nextA) { // d) delete or insert on b
						changes++;
						j--;
					} else {
						changes += 2; // e) two changes, will trigger exit
					}
				}
			}
		}
	}

	return changes <= 1;

}


/*
 * 
 * TEST hello
 * 
 * find the longest substring containing only 3 distinct characters of a given string:
 * 
 * abcda → abc xabcda → xab xabbcda → xabb xabbcccda → abbccc
 * 
 * Other examples null → null “” → “” “ab” → “” “abc” → “abc” “aaaa” → “” “aaaabbb” → “”
 * 
 * public String longest3Substring(String s) {
 * 
 * String candidate = “”; boolean finished = false;
 * 
 * // base cases if (s==null) { return null; } int length = s.length(); if (length<3) { return “”; }
 * if (length==3) { char s0 = s.charAt(0); char s1 = s.charAt(1); char s2 = s.charAt(2); if (s0!=s1
 * && s1!=s2 && s0!=s2) { // aba ?? return s; } }
 * 
 * // recursive cases // run through the string accumulating character counts
 * 
 * String firstSequence = s.charAt(0); int i = 1; while (i<s.length()) {
 * 
 * }
 * 
 * 
 * 
 * 
 * public String longest3Substring(String s) { List<S> candidatesQueue = ArrayList<String>(3);
 * List<Integer candidatesCount = ArrayList<Integer>(3); return longest3Substr_(candidatesQueue,
 * candidatesCount, s); } private String longest3Substr_(List<S> candidatesQueue, List<Integer
 * candidatesCount, String s){ // try queue structure to accumulate candidates, each candidate is a
 * sequence of equal characters // later I could optimise so I only store character count
 * 
 * boolean finished = false; int i = 0; int length = s.length(); while (!finished && i<length) {
 * char c = s.charAt(i); if (candidatesQueue.isEmpty()) { candidatesQueue.add(c); // current
 * candidate is ‘c’ candidatesCount.add(1); } else { // if not empty, either c is equal to a
 * candidate or // a new one int lastCandidateIndex = candidatesQueue.size()-1; if
 * (c==candidates.get(lastCandidateIndex)) { // we keep accumulating the candidate count
 * candidatesCount.set( lastCandidateIndex, candidatesCount.get(lastCandidateIndex)+1); } else { //
 * if current char is not like the last element of the queue if (lastCandidateIndex+1<3) {
 * candidatesQueue.add(c); //candidates ‘a’:3, and c==’b’ candidatesCount.add(1); //or candidates
 * ‘a’:3, ‘b’:2 & c=’c’ } else { // choose if the candidates I’ve got now are bigger than the //
 * sequence that is going to be ‘a’: 3 [ ‘b’: 2, ‘c’:4, new candidate ] // recursive case by
 * induction call on smaller string String candidate = longest3Substr_(candidatesQueue,
 * candidatesCount, s.substring(i+1, s.length)); // Need to check the candidates as the invariant of
 * candidates==3 // is not true // [ ‘a’:3, ‘b’:2, ‘c’:3, ‘d’:1 ] → ‘a’:3, ‘b’:2, ‘c’:3 // [ ‘a’:3,
 * ‘b’:2, ‘c’:3, ‘d’: 4 ] → ‘b’:2, ‘c’:3, ‘d’: 4 } } } i++; }
 * 
 * if (candidatesQueue.size()<3) { return “”; } }
 * 
 * 
 * 
 */


public static String compress(String s) {

	boolean returnOriginal = true;
	StringBuffer compressed = new StringBuffer();
	char c;
	int i = 0;
	while (i < s.length()) {
		c = s.charAt(i++);
		compressed.append(c);
		int same = 1;
		while (i < s.length() && s.charAt(i) == c) {
			same++;
			i++;
		}
		if (same > 1) {
			compressed.append(same);
		}
		if (returnOriginal && same > 2) {
			returnOriginal = false;
		}

	}

	return returnOriginal ? s : compressed.toString();

}


// abbb --> a1b3
// ab --> b
public static String compress2(String s) {
	if (s == null) {
	}
	var n = s.length();
	if (n == 0) {
		return s;
	}
	var out = new StringBuffer();
	var outSize = 0;
	var i = 0;
	while (i < n && outSize < n) {
		var c = s.charAt(i);
		var z = 1;
		while (++i < n && s.charAt(i) == c && outSize < n) {
			z++;
		}
		out.append(c + ("" + z));
		outSize += 1 + Strings.digits(z);
	}

	if (i == n && outSize < n) {
		return out.toString();
	}
	return s;
}


public static int digits(int n) {
	var size = 0;
	while (n != 0) {
		n = (int) Math.floor((n / 10));
		size++;
	}
	return size;
}


// erase any character from a string

/*
 * 
 * "abc", "a" → "bc" "bc", "a" → "bc" "aaaaa" → ""
 * 
 * let's try the recursive version first
 * 
 * 
 * if current char is == return empty string if not return this char concat (recursive function)
 * 
 * this is very inefficient unless we use a charbuffer, let's go for that solution
 * 
 */


public static String remove(String s, char c) {
	if (s == null) {
		return s;
	}
	var b = new StringBuffer();
	return removeWithBuffer(b, s, c, s.length(), 0).toString();
}


public static StringBuffer removeWithBuffer(StringBuffer b, String s, char c, int n, int i) {
	if (i >= n) {
		return b;
	}
	char cc = s.charAt(i++);
	if (cc != c) {
		b.append(cc);
	}
	return removeWithBuffer(b, s, c, n, i);
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
