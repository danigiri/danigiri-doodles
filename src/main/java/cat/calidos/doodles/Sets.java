// SETS . JAVA

package cat.calidos.doodles;

import java.util.HashSet;
import java.util.Set;

/**
*	@author daniel giribet
*///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class Sets<T> {

//8.4 power set: write a method to return all subsets of a set
//we can do recursivity
//say we take an element out at random
//we do a recursive call and we are returned a list of sets
//we have twice as many sets, the original ones as well as the ones that contain this element
//we return the empty set, we can return a set of sets as opposed to a list
//with empty set we mean that we will also return a set with this element only

//[a, b, c]
//call: [a,b]
//[a], [b], [a,b], []; and we have [c] left
//we return
//[a], [b], [a,b], [] + [a, c], [b, c], [a, b, c], [c], correct!
//in the recursive call:
//[a, b]
//call: [a]
//returns [a], []
//we get: [a], [], + [a, b], [b]
//[a]
//we return itself plus the empty set

public static <T> Set<Set<T>> allSubsets(Set<T> set) {

	// base cases
	Set<Set<T>> sets = new HashSet<Set<T>>();
	int size = set.size();
	if (size==0) {
		sets.add(new HashSet<T>(0));
	} else if (size==1) {
		sets.add(new HashSet<T>(0));
		sets.add(set);
	} else {
		//recursive case, guaranteed to have at least one element
		T element = set.iterator().next();
		set.remove(element);	// O(k)
		Set<Set<T>> recursiveSets = allSubsets(set);
		recursiveSets.forEach(s -> {
			sets.add(s);
			Set<T> copy = new HashSet<T>();	// we have no guarantee an iterator will not have conflicts with add
			copy.addAll(s);
			copy.add(element);
			sets.add(copy);
		});
	}

	return sets;

}


}


/*
 *    Copyright 2021 Daniel Giribet
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

