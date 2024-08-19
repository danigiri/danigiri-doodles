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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

public class Words {


/** Return true if 's' can be segmented into words present in 'dict'
 * 	@param s (holaaaab)
 * 	@param dict (a, ab, hol)
 * 	@return true if s can be segmented using dictionary elements
 */
public static boolean seg(final String s, final Set<String>dict) {
	
	if (s==null || dict==null) {
		throw new NullPointerException("Null doesn't break");
	}
	int sLength = s.length();
	if (sLength==0) {
		return true;
	}

	// sort the dictionary and create a tree of prefixes (N*LOG(N))
	List<String>sortedDict = Sorter.mergeSort(new ArrayList<String>(dict));
	Tree<String>keyTree = Words.buildKeyTree("", sortedDict);

	String matched = seg(s, keyTree);
	return matched.length()==sLength;
	
}


/** Segmentation using a tree of prefixes, returns the matched string until no
 * 	more keys match
 * 	@param s string to be segmented
 * 	@param t prefix tree
 * 	@return matched (sub)string
*/
public static String seg(final String s, final Tree<String>t) {
	
	// base cases
	String matched = "";
	String remaining = s;
	int sLength = s.length();
	if (sLength==0) {
		return matched;
	}
	if (s.startsWith(t.data)) {
		matched = s.substring(0, t.data.length());
		remaining = s.substring(t.data.length());
	} else {
		return matched;
	}
	
	String candidate = "";
	int candidateLength = 0;
	// recursive case
	// establish a candidate substring char by char and look it up on the tree's children
	// if found, increase the matched string, reduce the remaining to match and do a recursive
	// call to see if there are any submatches
	while (candidateLength<=t.maxKeyLength && candidateLength<remaining.length()) {
		candidate = remaining.substring(0,++candidateLength);
		if (t.hasChild(candidate)) {
			String subMatched = seg(remaining, t.getChild(candidate));
			remaining = remaining.substring(subMatched.length());
			matched +=subMatched;
			candidate = "";
			candidateLength = 0;
		}
	}
	return matched;
	
}


public static Tree<String> buildKeyTree(String prefix, List<String>keys) {

	Tree<String> t = new Tree<String>(prefix);

	// base cases
	int keysSize = keys.size();
	if (keysSize==0) {
		return t;
	}

	// recursive cases 
	while (keys.size()>0) {
		List<String> subKeys = Words.mmatch(keys);	// note keys has been consumed and is now smaller
		String k = subKeys.get(0);
		subKeys.remove(0);
		t.addChild(k, buildKeyTree(k, subKeys));		// induction
	}
	return t;

}


/** ['a', 'ab', 'ac', 'ad'] and returns ['a', 'c', 'd']
 * 	['abc', 'abd'] returns ['ab', 'c', 'd']
 * 	Note it consumes the 'list' parameters, leaving non-processed elements
 * @param list
 * @return
 */
public static List<String> mmatch(List<String> list) {	
	
	if (list==null) {
		return null;
	} 
	List<String> l = list;
	if (l.size()<2) {
		return l;
	}
	
	// (l.size()>2)
	// Get the first two elemenys and find out the longest common prefix between the two
	// Ensure any reminder of the first element (substracting prefix from element) gets added
	// and then, starting from the 2nd element, while the elements start from the prefix add them
	// to the list (substracting the prefix)
	List<String> mm = new ArrayList<String>();
	String k = l.get(0);
	l.remove(0);
	String nk = l.get(0);
	String cp = commonPrefix(k, nk);
	int cpLength = cp.length();
	if (cpLength==0) {	//no commonality
		mm.add(k);	
	} else {
		mm.add(k.substring(0,cpLength));	// add common prefix (ie [ab]c)
		if (cpLength<k.length()) {			// check if there is anything left of first key (ie ab[c])
			mm.add(k.substring(cpLength));
		}
		boolean nkHasPrefixInCommon = true;
		while (l.size()>0 && nkHasPrefixInCommon) {
			nk = l.get(0);
			if (nk.startsWith(cp)) {
				mm.add(nk.substring(cpLength));
				l.remove(0);				// only remove if matching
			} else {
				nkHasPrefixInCommon = false;
			}
		}
	}
	return mm;
	
}


/** Given two strings, return the longest common prefix substring or empty string otherwise
 * @param a first string (eg. 'abc'
 * @param b second string (eg. 'abd'
 * @return return 'ab' in this case
*/
public static String commonPrefix(String a, String b) {
	
	int aLength = a.length();
	int bLength = b.length();
	int l = (aLength<bLength) ? aLength : bLength; // longest common prefix is as long as the smallest
	int i = 0;
	StringBuffer s = new StringBuffer(l);
	boolean similar = true;
	while (i<l && similar) {
		if (a.charAt(i)==b.charAt(i)) {
			s.append(a.charAt(i));
		} else {
			similar = false;
		}
		i++;
	}
	return s.toString();
	
}


/*
Given a string s and a dictionary of strings wordDict, add spaces in s to construct 
a sentence where each word is a valid dictionary word. Return all such possible sentences in any order.

Note that the same word in the dictionary may be reused multiple times in the segmentation.

if sentence is not valid, return null or empty set


Example 1:

Input: s = "catsanddog", wordDict = ["cat","cats","and","sand","dog"]
Output: ["cats and dog","cat sand dog"]
Example 2:

Input: s = "pineapplepenapple", wordDict = ["apple","pen","applepen","pine","pineapple"]
Output: ["pine apple pen apple","pineapple pen apple","pine applepen apple"]
Explanation: Note that you are allowed to reuse a dictionary word.
Example 3:

Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
Output: []


naive approach 1:
iterate over characters and keep a list of dict candidates, once match found, create output candidate, recurse, continue


less naive approach 2:

have ongoing candidate as input

look at current index in input and current index in dictionary


return a set of candidates


approach 3:

input: catsanddoc
ongoing candidate "cats "
current word "an"
index: 6
look for current word in map
if exists --> append to ongoing -> recurse
if not exists increment index


solution: ""
current word: "cat"
index 3
two branches --> recurse with increased index and no change to candidate
the other branch is add word to solution, clear current word, and increase index
return both branches as a list

c --> match 0 match 1, no match 2, 3, 4

output = ""
c --> "c" + f(1,)


c->a->t->END
       ->s
a->n->d->END
s->a-n->d->END

 */

/*"
ex 1:
input: "A", dict: []

ex 2:
"AB", ["A", "B"] --> ["A B"]

 */


public static List<String> sentenceFromDict(String s, List<String> dict) {
	var dictSet = new HashSet<String>(dict.size());
	dictSet.addAll(dict);
	return Words.sentenceFromDictTestRec("", "", 0, 0, s, dictSet);

}

// may not be correct, see tests
private static List<String> sentenceFromDictTestRec(String sol, String current, int i, int matched, String s, Set<String> dict) {

	// end of string, no solution, double check again for 'correct solutions'
	if (i > s.length() - 1) { //
		var phrase = new ArrayList<String>();
		if (matched==s.length()) {
			phrase.add(sol);
		}
		return phrase;
	}
	current += s.charAt(i); //
	List<String> candidatesMatch = new ArrayList<String>();
	if (dict.contains(current)) { // current word is candidate for solution
		var solMatch = sol.isEmpty() ? current : sol + " " + current; //
		candidatesMatch = Words.sentenceFromDictTestRec(solMatch, "", i + 1, matched+(current.length()), s, dict);
	}
	var candidatesNoMatch = Words.sentenceFromDictTestRec(sol, current, i + 1, matched, s, dict);
	candidatesNoMatch.addAll(candidatesMatch);

	return candidatesNoMatch;
}


/*
 * 
 * Complete the function scramble(str1, str2) that returns true if a portion of str1 characters can
 * be rearranged to match str2, otherwise returns false.
 * 
 * Notes:
 * 
 * Only lower case letters will be used (a-z). No punctuation or digits will be included.
 * Performance needs to be considered. Examples
 * 
 * scramble('rkqodlw', 'world') ==> True scramble('cedewaraaossoqqyt', 'codewars') ==> True
 * scramble('katas', 'steak') ==> False
 * 
 * given a-z, we will use char ints in an array count the number of occurences in str2 go through
 * str1, decrement, we should have no >0's in the count
 * 
 * 
 */


public static boolean scramble(String str1, String str2) {
	var counts = new int[26];
	for (int i = 0; i < 26; i++) {
		counts[i] = 0;
	}
	for (int i = 0; i < str2.length(); i++) {
		counts[(int) str2.charAt(i) - (int) 'a'] += 1;
	}
	for (int i = 0; i < str1.length(); i++) {
		counts[(int) str1.charAt(i) - (int) 'a'] -= 1;
	}
	var i = 0;
	var missing = false;
	while (i < 26 && !missing) {
		missing = counts[i++] > 0;
	}
	return !missing;
}


/*

Given strings s1, s2, and s3, find whether s3 is formed by an interleaving of s1 and s2.

An interleaving of two strings s and t is a configuration where s and t are divided into n and m 
substrings
 respectively, such that:

s = s1 + s2 + ... + sn
t = t1 + t2 + ... + tm
|n - m| <= 1
The interleaving is s1 + t1 + s2 + t2 + s3 + t3 + ... or t1 + s1 + t2 + s2 + t3 + s3 + ...
Note: a + b is the concatenation of strings a and b.

 */
public static boolean interleaving(String s1, String s2, String s3) {
	return interleavingR(s1, s2, 0, 0, s3, 0, s1.length(), s2.length(), s3.length());
}


public static boolean interleavingR(String s1, String s2, int index1, int index2, String s3, int index3, int s1Length, int s2Length, int s3Length) {
	// base cases
	if (s1Length - index1 + s2Length - index2 != s3Length - index3) { // invariant, remaining lengths
		return false;
	}
	if (index3 == s3Length) { // we reached the end
		return true;
	}
	// recursive cases
	var left = false;
	var right = false;
	if (index1<s1Length && s1.charAt(index1) == s3.charAt(index3)) {
		left = interleavingR(s1, s2, index1 + 1, index2, s3, index3 + 1, s1Length, s2Length, s3Length);
	}
	if (!left && index2<s2Length && s2.charAt(index2) == s3.charAt(index3)) {
		right = interleavingR(s1, s2, index1, index2 + 1, s3, index3 + 1, s1Length, s2Length, s3Length);
	}
	return left || right;
}


/*
 * given a string of text (possibly with punctuation and line-breaks), returns an array of the top-3
 * most occurring words, in descending order of the number of occurrences.
 * 
 * Assumptions:
 * 
 * - A word is a string of letters (A to Z) optionally containing one or more apostrophes (') in
 * ASCII. - Apostrophes can appear at the start, middle or end of a word ('abc, abc', 'abc', ab'c
 * are all valid) - Any other characters (e.g. #, \, / , . ...) are not part of a word and should be
 * treated as whitespace. - Matches should be case-insensitive, and the words in the result should
 * be lowercased. - Ties may be broken arbitrarily. - If a text contains fewer than three unique
 * words, then either the top-2 or top-1 words should be returned, or an empty array if a text
 * contains no words.
 * 
 * we keep a map of word --> count we accumulate the current word we sort it and return the topmost
 * words
 * 
 */


public static List<String> top3(String s) {
	var counts = new java.util.HashMap<String, Integer>();
	var i = 0;
	while (i < s.length()) {
		var b = new StringBuffer();
		i = readWord(s, b, i);
		if (!b.isEmpty()) {
			var current = b.toString().toLowerCase();
			if (!isAllQuotes(current)) {
				var count = counts.get(current);
				counts.put(current, count == null ? 1 : count + 1);
			}
		}
	}
	String[] words = counts.keySet().toArray(new String[0]);
	java.util.Arrays.sort(words, (a,b) -> (counts.get(b)).compareTo(counts.get(a)));
	var top = new java.util.ArrayList<String>(3);
	i = 0;
	for (var j = 0; j<Math.min(3, words.length);j++) {
		top.add(words[j]);
	}
	return top;
}


// returns the index and the word is stored in the stringBuffer
private static int readWord(String s, StringBuffer b, int i) {
	var isWhitespace = true;
	char c = ' ';
	while (isWhitespace && i < s.length()) {
		c = s.charAt(i);
		isWhitespace = !isWordChar(c);
		i++;
	}
	while (!isWhitespace && i < s.length()) {
		b.append(c);
		c = s.charAt(i);
		isWhitespace = !isWordChar(c);
		i++;
	}
	if (!isWhitespace && i == s.length()) { // last char
		b.append(c);
	}
	return i;
}


private static boolean isAllQuotes(String s) {
	for (var i = 0; i<s.length();i++) {
		if (s.charAt(i)!='\'') {
			return false;
		}
	}
	return true;
}


private static boolean isWordChar(char c) {
	var cInt = (int) c;
	return (c == '\'' || ((int) 'a' <= cInt && cInt <= (int) 'z') || ((int) 'A' <= cInt && cInt <= (int) 'Z'));
}


// 16.20 implement an algorithm to return a list of matching words, given a sequence of digits
// you are provided a list of valid words in any way you like
/*

	1	2	3
		abc	def
	4	5	6
	ghi	jkl	mno
	7	8	9
	pqrs	tuv	wxyz
		0

*/
// input 8733
// output: tree, used
/*
tuv
pqrs
def
def
*/
//
// for each initial digit:
// get word tree and call it with starting letter
// given current root of tree
// if we have no next elements
// 	if current root has boolean to signal end of word, return word until then
// if we have children
// if we have next elements
// recurse call with the intersection of possible letters and children
// if we have no next elements, check boolean

/*
public static Set<String> keypad(Map<Character, Tree<Pair<Character, Boolean>>>w, DLinkedList<Integer> keys) {

	var options = new HashSet<String>();
	int firstKey = keys.data;
	translateKeypad(firstKey).forEach(c -> keypadWords(c, w.get(c), keys, new DLinkedList<Character>(c), options));

	return options;

} 

private static List<Character> translateKeypad(int k) {
	
var l = new ArrayList<Character>();
		char[][] chars = {	{}, 
							{},
							{'a', 'b', 'c'},
							{'d', 'e', 'f'},
							{'g', 'h', 'i'},
							{'j', 'k', 'l'},
							{'m', 'n', 'o'},
							{'p', 'q', 'r', 's'},
							{'t', 'u', 'v'},
							{'x', 'y', 'z'}
	};
	for (int i=0; i<chars[k].length; i++) {
		l.add(chars[k][i]);
	}

return l;

}

private static void keypadWords(char c, Tree<Pair<Character, Boolean>>w, DLinkedList<Integer> keys, DLinkedList<Character> word, Set<String> options) {

	if (keys==null) {	// we have no keys to process, check current word
		if (w.data.right) {
			var candidate = new StringBuffer();
			//word.stream().forEach(wordChar -> candidate.append(wordChar));
			for (Iterator<Character> i =word.iterator(); i.hasNext(); ) {
				candidate.append(i.next());
			}
			options.add(candidate.toString());
		}
	} else {		// keys left to process
		var children = w.children;
		if (!children.isEmpty()) {	// we cannot continue unless we have word children
			int key = keys.data;
			translateKeypad(key).forEach(ch ->{	// match children and chars
				var child = children.entrySet().stream().filter(e -> e.getKey().equals(ch)).findAny();
				if (child.isPresent()) {
					word.append(ch);
					keypadWords(ch, child.get().getValue(), keys.next, word, options);
					word.removeLast();
				}
			});
		}
	}	

}
*/

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


