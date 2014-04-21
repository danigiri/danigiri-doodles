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
import java.util.List;
import java.util.Set;

public class WordBreak {


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
	List<String>sortedDict = Sorter.sortList(new ArrayList<String>(dict));
	Tree<String>keyTree = WordBreak.buildKeyTree("", sortedDict);

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
	if (s.startsWith(t.value)) {
		matched = s.substring(0, t.value.length());
		remaining = s.substring(t.value.length());
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
		List<String> subKeys = WordBreak.mmatch(keys);	// note keys has been consumed and is now smaller
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

}


