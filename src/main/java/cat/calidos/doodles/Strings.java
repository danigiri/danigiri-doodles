/**
 Copyright 2016 Daniel Giribet <dani - calidos.cat>

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
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class Strings {

public static boolean unique(String s) {

	Set<Integer> chars = new HashSet<Integer>();

	return stringNotInSet(s, chars);	

}


private static boolean stringNotInSet(String s, Set<Integer>set) {	// cost O(N)

	// base cases
	if (s==null || set==null) {
		throw new NullPointerException("Null data");
	}
	int sLength = s.length();
	
	if (sLength==0) {
		return true;
	}

	// recursive case, reduce size, 
	Integer c = (int)s.charAt(0); 					// always>0, protected by base case
	if (set.contains(c)) {							//  already found - cost O(K)
		return false;
	} else {
		set.add(c);									// cost O(log N)
		String substring = s.substring(1, sLength);	// (inclusive, exclusive) - cost O(K)
		return stringNotInSet(substring, set);		// recursion is linear - cost O(N)
	}

}


// find the longest substring containing only 3 distinct characters of a given string:
//
// abcda  → abc
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

	if (s==null) {
		return null;
	}
	if (s.length()==0) {
		return "";
	}

	List<Map<String, Integer>> sub = new ArrayList<Map<String, Integer>>(3);
	Map<String, Integer> firstCharCount = Strings.newCounter(Character.toString(s.charAt(0))); 
	sub.add(firstCharCount);

	sub = Strings.subThree_(s.substring(1, s.length()), sub);
	if (sub.size()<3) {
		return "";
	}
	StringBuffer subBuffer = new StringBuffer();
	while (sub.size()>0) {
		Map<String, Integer> char_ = sub.get(0);
		String c = char_.keySet().iterator().next();
		int n = char_.get(c);
		for (int i=0; i<n; i++) {
			subBuffer.append(c);
		}
		sub.remove(0);
	}
	return subBuffer.toString();
}


private static List<Map<String, Integer>> subThree_(String s, List<Map<String, Integer>> max) {

	// base cases
	int sLength = s.length();
	if (sLength==0) {
		return max;
	}

	// recursive cases
	Map<String, Integer> char_ = max.get(max.size()-1);
	String current = char_.keySet().iterator().next();
	int currentCount = char_.get(current);

	String c = Character.toString(s.charAt(0));

	if (c.equals(current)) {
		char_.put(c, ++currentCount);
		return subThree_(s.substring(1, sLength), max);	// induction, ’s’ is smaller
	}

	Map<String, Integer> newCount = Strings.newCounter(c);
	// moving into a new char
	if (max.size()<3) {
			max.add(newCount);
			return subThree_(s.substring(1, sLength), max);		// induction
	}

	List<Map<String, Integer>> nextMax = new ArrayList<Map<String, Integer>>(3);
	nextMax.add(max.get(1));
	nextMax.add(max.get(2));
	nextMax.add(newCount);
	
	nextMax = Strings.subThree_(s.substring(1, sLength), nextMax);	// induction
	
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
	for (Map<String, Integer> c : l ) {
		count += c.get(c.keySet().iterator().next());
	}
	return count;
}

// given a set of character deltas from two words and a char, compute the new delta set
//	words: "abc", "bd" --> a: -1, b: 0, c: -1, d: +1
private static void countDeltaForChar(Map<String, Integer> deltas, char c, int deltaForChar) {
	String sc = String.valueOf(c);
	if (deltas.containsKey(sc)) {
		int charCount = deltas.get(sc);
		deltas.put(sc, charCount+deltaForChar);
	} else {
		deltas.put(sc, deltaForChar);
	}
}

/* Return true if the two words are anagrams
 *//////////////////////////////////////////////////////////////////////////////////////////////////
public static boolean isAnagramOf(String a, String b) {

	int aSize = a.length();
	if (aSize != b.length()) {
		return false;
	}
	
	// TODO: optimise for max delta or abs(minimum delta) > chars left to process :)
	Map<String, Integer> deltas = new HashMap<String, Integer>(aSize);
	for (int i=0; i<aSize; i++) {
		char aChar = a.charAt(i);
		char bChar = b.charAt(i);
		if (aChar != bChar) {
			countDeltaForChar(deltas, aChar, 1);
			countDeltaForChar(deltas, bChar, -1);
		}
	}

	return !deltas.values().stream().filter(d -> d!=0).findAny().isPresent();

}


// Write code to reverse a C-Style String (C-String means that “abcd” is 
// represented as ve characters, including the null character )
public static String reverseCString(String s) {
	if (s==null) {
		throw new NullPointerException("Null s to reverse");
	}

	int sl = s.length();
	if (sl<=1) {
		return s;
	}
	return reverseCString(s.substring(1,sl))+s.substring(0,1);
}


//Design an algorithm and write code to remove the duplicate characters in a string without using any additional buffer 
//NOTE: One or two additional variables are ok but an extra copy of the array is not!

//OK, let’s implement an algorithm in place based on the idea that we hold a substring of the unique
//characters at the beginning in the order supplied in the original string, no expensive
// linear replacing ops
//abcad [a]xcda, [ax]cda, [axcd]a —> axcd
public static String removeDuplicatesInPlace(String s) {
	if (s==null) {
		return null;
	}
	if (s.length()<=1) {
		return s;
	}
	// using a string builder as we have to modify it to use it in place
	// but we're only using one buffer in the algorithm itself
	// a char array could also be used in the same way
	StringBuilder sb = new StringBuilder(s);
	int finalLength = findUniqueCharsStringInPlace(0, sb);
	return sb.substring(0, finalLength+1);
}

// technically, this is O(N), as the checking on each will be limited to the maximum
// number of characters we have in our character space (unicode is quite large though)
//this can be really fast if we had only ASCII chars or a similar space
private static int findUniqueCharsStringInPlace(int boundary, StringBuilder s) {

	int n = s.length();
	for (int i = boundary+1;i<n;i++) {
		char c = s.charAt(i);
		boolean found = false;
		int j = boundary;
		while (!found && j>=0) {
			found = s.charAt(j)==c;
			j--;
		}
		if (!found) {
			boundary++;
			s.setCharAt(boundary, c);
		}
	}
	
	return boundary;
	
} 


//from a list of strings return a suffix tree
//abc,abd,c,cd —>
//“”
//ab c
//d    d

public static Tree<String> suffixTree(List<String> strings) {

	if (strings==null) {
		return null;
	}
	return suffixTree_(strings, new Tree<String>(""));
	
}


private static Tree<String> suffixTree_(List<String> strings, Tree<String>t) {
	
	// base cases
	if (strings.size()==0) {
		return t;
	}
	//strings.stream().forEach(s -> {		
	for (String s : strings) {
		boolean found = false;
		Iterator<String> children = t.children.keySet().iterator();
		while (!found && children.hasNext()) {	// while
			String childK = children.next();
			if (!s.equals(childK)) {
				String pref = commonPrefix(childK, s);
				if (pref.length()>0) {
					// found a prefix, handle recursive case:
					String sPostfix = s.substring(pref.length());
					List<String> postfixes = new ArrayList<String>(1);
					postfixes.add(sPostfix);
					Tree<String> child = t.getChild(childK);
					if (pref.length()<childK.length()) {					 	// child='aab', s='aa', sprout
						String newChildPref = childK.substring(pref.length());	// so child='aa' -> 'b' with its
						Tree<String> nc = new Tree<String>(newChildPref);		// old children assigned to it
						nc.addAll(child.children);
						child.data = pref;										// set child to 'aa' and
						child.clearChildren();									// have a subtree which is 'b'
						child.addChild(newChildPref, nc);
					}
					child = suffixTree_(postfixes, child);
					t.addChild(childK, child);
					found = true;
				}
			} else {
				found = true;	// duplicate, we skip it (base case)
			}
		}										// end while
		if (!found) {
			// base case, new child
			t.addChild(s, new Tree<String>(s));	// brand new child of the tree
		}
	//});
	}
	
	return t;

}


private static String commonPrefix(String a, String b) {
	
	if (a==null || b==null) {
		return null;
	} 
	StringBuilder prefix = new StringBuilder();
	boolean equals = true;
	int i = 0;
	int al = a.length();
	int bl = b.length();
	while (equals && i<al && i<bl) {
		char ac = a.charAt(i);
		char bc = b.charAt(i);
		equals = ac==bc;
		if (equals) {
			prefix.append(ac);
		}
		i++;
	}
	
	return prefix.toString();
	
}


/*
 * 
 * TEST hello

find the longest substring containing only 3 distinct characters of a given string:

abcda  → abc
xabcda → xab
xabbcda → xabb
xabbcccda → abbccc

Other examples
null → null
“” → “”
“ab” → “”
“abc” → “abc”
“aaaa” → “”
“aaaabbb” → “”

public String longest3Substring(String s) {

String candidate = “”;
	boolean finished = false;
	
// base cases 
if (s==null) {
	return null;
}
int length = s.length();
if (length<3) {
	return “”;
}	
if (length==3) {
	char s0 = s.charAt(0);	
char s1 = s.charAt(1);
	char s2 = s.charAt(2);
	if (s0!=s1 && s1!=s2 && s0!=s2) { // aba ??
	return s;
}
}

	// recursive cases
	// run through the string accumulating character counts

	String firstSequence = s.charAt(0);
	int i = 1;
	while (i<s.length()) {
		
}




public String longest3Substring(String s) {
List<S> candidatesQueue = ArrayList<String>(3);
List<Integer candidatesCount = ArrayList<Integer>(3);
	return longest3Substr_(candidatesQueue, candidatesCount, s);
}
private String longest3Substr_(List<S> candidatesQueue, List<Integer candidatesCount, String s){
// try queue structure to accumulate candidates, each candidate is a sequence of equal characters
// later I could optimise so I only store character count

boolean finished = false;
int i = 0;
int length = s.length();
while (!finished && i<length) {
	char c = s.charAt(i);
	if (candidatesQueue.isEmpty()) {
		candidatesQueue.add(c);	// current candidate is ‘c’
		candidatesCount.add(1);
} else {					// if not empty, either c is equal to a candidate or
// a new one 
	int lastCandidateIndex = candidatesQueue.size()-1;
if (c==candidates.get(lastCandidateIndex)) {
		// we keep accumulating the candidate count
		candidatesCount.set(
lastCandidateIndex, 
candidatesCount.get(lastCandidateIndex)+1);
} else {
	// if current char is not like the last element of the queue
	if (lastCandidateIndex+1<3) {
		candidatesQueue.add(c);	//candidates ‘a’:3, and c==’b’
		candidatesCount.add(1);		//or candidates ‘a’:3, ‘b’:2 & c=’c’
} else {
	// choose if the candidates I’ve got now are bigger than the
	// sequence that is going to be ‘a’: 3 [ ‘b’: 2, ‘c’:4, new candidate ]
	// recursive case by induction call on smaller string
String candidate = longest3Substr_(candidatesQueue, 
       	    candidatesCount, 
       	    s.substring(i+1, s.length));
					// Need to check the candidates as the invariant of candidates==3
					// is not true
					// [ ‘a’:3, ‘b’:2, ‘c’:3, ‘d’:1 ] → ‘a’:3, ‘b’:2, ‘c’:3
					// [ ‘a’:3, ‘b’:2, ‘c’:3, ‘d’: 4 ] → ‘b’:2, ‘c’:3, ‘d’: 4
}
}
}
i++;
}

if (candidatesQueue.size()<3) {
	return “”;
}
}



*/

}
