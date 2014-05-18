package cat.calidos.doodles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


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
	sub.add(
			Strings.newCounter(Character.toString(s.charAt(0)))
			);

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
