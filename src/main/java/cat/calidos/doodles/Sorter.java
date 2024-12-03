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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
* @author daniel giribet
*///////////////////////////////////////////////////////////////////////////////
public class Sorter {
	

	public static <T extends Comparable<? super T>> List<T> mergeSort(final List<T>a) {
		
		// base cases
		if (a==null) {
			return null;
		}
		int size = a.size();
		if (size<=1) {
			return new ArrayList<T>(a);
		}
		// proven: size >=2
		
		int halfSize = size/2;
				
		List<T> left = Sorter.mergeSort(a.subList(0, halfSize));
		List<T> right = Sorter.mergeSort(a.subList(halfSize, size));
		
		return Sorter._mergeSortedLists(left, right);
		
	}


	public static <T extends Comparable<? super T>> List<T> mergeSortedLists(final List<T> a, final List<T> b) {
		
		if ((a==null || b==null)) {
			throw new NullPointerException("Not sorting null lists");
		}
		return Sorter._mergeSortedLists(new ArrayList<T>(a), new ArrayList<T>(b));
		
	}

	
	private static <T extends Comparable<? super T>> List<T> _mergeSortedLists(List<T> a, List<T> b) {

		// base case
		if (a.size()==0) {
			return b;
		} else if (b.size()==0) {
			return a;
		}
		
		// recursive case
		T firstFromA = a.get(0);
		T firstFromB = b.get(0);
		List<T> sorted = new ArrayList<T>();
		if (firstFromA.compareTo(firstFromB)<=0) {
			sorted.add(firstFromA);
			a.remove(0);
		} else {
			sorted.add(firstFromB);					
			b.remove(0);
		}
		// a or b are smaller so we can call recursively
		// moreover, the trivially sorted array contains one element that is smaller
		// than any element in the two remaining arrays so if we concatenate
		// the result of the induction call we trivially have a sorted array
		sorted.addAll(_mergeSortedLists(a, b));	// induction

		return sorted;
		
	}

	
	/**	Basic elegant quicksort, middle pivot and not in place
	* 	@param l unsorted list
	* 	@return
	*///////////////////////////////////////////////////////////////////////////
	public static <T extends Comparable<? super T>> List<T> quickSort(List<T> l) {

		// base cases
		if (l==null) {
			return null;
		}
		int size = l.size();
		if (size<=1) {
			return l;
		}

		// recursive cases
		List<T> left = new ArrayList<T>(size/2);
		List<T> right = new ArrayList<T>(size/2);
		int pivot = size/2;
		T pivotValue = l.get(pivot);
	 	for (int i=0; i<size; i++) {
	 		if (i!=pivot) {
	 			T v = l.get(i);
	 			if (v.compareTo(pivotValue)<=0) {
	 				left.add(v);
	 			} else {
	 				right.add(v);
	 			}
	 		}
		}
		left = Sorter.quickSort(left);		// induction, smaller lists
		right = Sorter.quickSort(right);
		left.add(pivotValue);
		left.addAll(right);
	
		return left;	

	}
	
	
	public static <X extends Comparable<? super X>> Tree<X> bTree(final List<X>l) {
		if (l==null) {
			return null;
		}
		int lSize = l.size();
		if (lSize==0) {
			return null;
		}
		return bTree(new ArrayList<X>(l.subList(1, l.size())), new Tree<X>(l.get(0)));
		
	}
	
	public static  <X extends Comparable<? super X>> Tree<X> bTree(List<X>l, Tree<X> t) {

		// base cases
		int lSize = l.size();
		if (lSize==0) {
			return t;
		}

		X v = l.get(0);
		l.remove(0);
		
		// recursive case
		if (v.compareTo(t.data)<=0) {	// sorry
			// left
			if (t.left==null) {
				t.left = new Tree<X>(v);
			} else {
				ArrayList<X> vList = new ArrayList<X>(1);
				vList.add(v);
				t.left = bTree(vList, t.left);
			}
		} else {
			// right
			if (t.right==null) {
				t.right = new Tree<X>(v);
			} else {
				ArrayList<X> vList = new ArrayList<X>(1);
				vList.add(v);
				t.right = bTree(vList, t.right);
			}
			
		}
		
		return bTree(l, t);
	}
	
	
	/** One level general bucket sort of numbers (positive integers)
	* 	@param l list to be sorted
	* 	@return sorted list
	*///////////////////////////////////////////////////////////////////////////
	public static <T extends Number> List<T> bucketSort(final List<T> l) {

		// Number bucket sorting algorithm using a direct access array, optimised for
		// lists with values that are evenly distributed (age, etc.).
		// The general idea is that we do a first iteration where we group elements in 
		// ‘buckets’ that are sorted themselves and once that is done, we sort each 
		// bucket individually (using whatever method, brute force even), sequentially listing 
		// sorted buckets results in a sorted list 
		
		// Options:
		// 	- generalised algorithm, were each bucket is recursively sorted using bucketSort
		//	- different levels, for instance, to sort people in ages, we do bucket sort on the
		//	  years of age, and then we do a different sort (quicksort or whatever) using month & day 
		//
		// We will implement the generalised option, using numbers as elements
		// (as elements we extend Number so we will check for negative values for sanity)
		// Direct integer implementation has an issue where as potentially large sparse
		// structures are created so use with care

		// base cases
		if (l==null) {
			return null;
		}
		if (l.size()<2) {
			return l;
		}
		
		// next we partially order into a sorted bucket list of unordered stuff
		List<List<T>> buckets = new ArrayList<List<T>>();
		for (T e : l) {
			
			int bucketIndex = e.intValue();
			if (bucketIndex>=0) {
				List<T> bucket = null;
				int bucketsSize = buckets.size();
				if (bucketIndex>=bucketsSize) {	// grow array by index minus size (slow!)
					bucket = new ArrayList<T>();
					for (int i=bucketsSize; i<bucketIndex; i++) {
						buckets.add(null);
					}
					buckets.add(bucket);
				} else {						// bucket existed (empty or not)
					bucket = buckets.get(bucketIndex);
					if (bucket==null) {					
						bucket = new ArrayList<T>();
						buckets.set(bucketIndex, bucket);
					}
				}
				bucket.add(e);					// add element to bucket
			} else {
				throw new IndexOutOfBoundsException("Cannot sort negative values");
			}

		}

		// recursive case (not really)
		// Bucket list is sorted itself and we would recursively sort the buckets 
		// themselves, but as we do not have several levels of sorting refinement
		// (year, month-day) then the buckets have only one element or repeats
		// We add them sequentially, creating a completely sorted list
		List<T> sortedList = new ArrayList<T>(l.size());
		for (List<T> b : buckets) {
			if (b!=null) {
				sortedList.addAll(b);
				// sortedList.addAll(bucketSortByAnotherCriteria(b));
			}
		}
		return sortedList;
	}
	

	public static <T extends Number> List<T> bucketSort(final List<T> l, int mask) {

		// base cases (trivial cases)
		if (l==null) {
			return null;
		}
		if (l.size()<2) {
			return l;
		}
				
		// base cases
		// firstly we partially order into a sorted bucket list of unordered stuff
		List<List<T>> buckets = new ArrayList<List<T>>();
		for (T e : l) {
					
			int bucketIndex = e.intValue() & mask;
			if (bucketIndex>=0) {
				List<T> bucket = null;
				int bucketsSize = buckets.size();
				if (bucketIndex>=bucketsSize) {	// grow array by index minus size (slow!)
					bucket = new ArrayList<T>();
					for (int i=bucketsSize; i<bucketIndex; i++) {
						buckets.add(null);
					}
					buckets.add(bucket);
				} else {						// bucket existed (empty or not)
					bucket = buckets.get(bucketIndex);
					if (bucket==null) {
						bucket = new ArrayList<T>();
						buckets.set(bucketIndex, bucket);
					}
				}
				bucket.add(e);					// add element to bucket
			} else {
					throw new IndexOutOfBoundsException("Cannot sort negative values");
			}

		}

		// recursive case
		// Bucket list is sorted itself and we would recursively sort the buckets 
		// (with more precision on each call until we run out of 'precision',
		// an example would be when we do not have seconds on a date or whatever)
		// Then we add them sequentially, creating a completely sorted list
		// NOTE: this could be further optimised with a mask that goes like 0x0ff --> 0x00f
		//		 in a way that zeroes are not called recursively any further but
		// 		 we're assuming a mask coherent with the dataset otherwise use some
		//		 other sorting algorithm =)
		List<T> sortedList = new ArrayList<T>(l.size());
		for (List<T> b : buckets) {
			if (b!=null) {
				int refinedMask = mask >> 4;	// shift one byte
				if (refinedMask==0) {			// only recurse if further refinement is possible
					sortedList.addAll(b);		// otherwise assume sorted
				} else {
					sortedList.addAll(bucketSort(b, refinedMask));
				}
			}
		}
		return sortedList;
		
	}
	
	// First we do a merge sort in place and then we copy remaining b at the end
	// We just copy ‘b’ at the end of ‘a’ at the beginning which complexity-wise wouldn’t 
	// modify O(N^2), the algorithm is a bit clearer this way
	// If we could modify ‘b’ we could do the swapping in place at ‘b’ and it’d be faster
	// The aim of the exercise is that given a memory size of a+b+1 and no stack, sort the two arrays
	// in place of ‘a’
	// worst case:
	// a: 20,21,22,23,24,25,_,_,_,_,_,_
	// b: 1,2,3,4,5,26
	// for each element in a we do all b which is N^2, when every element in ‘a’ is bigger than every element in b
	// obviously if we had a third buffer or a stack we could just merge sort the two in O(N)
	// Example
	// while a[i] is lower than b[0], i++ 
	// 1,3,5,6
	// 2,4 
	// we find that a[1]=3 is higher than b[0]=2, we swap them and find out if we have to move it up in b
	// it seems not, we leave it in its new place in b:
	// 1,2,5,6
	// 3,4
	// now it happens again a[2]=5 is higher than b[0] and then we move it
	// 1,2,3,6
	// 4,5
	//
	// 1,2,3,4
	// 5,6
	//
	// Another option, start at end of a which is max(a) and at end of b which is max(b)
	// until you find a b that is higher than a[i] shift and copy everything after that
	// continue until the whole of b has been processed
	// 1,3,5,6,_,_,_,_
	// 2,4,7,8
	// 
	// 1,3,5,6,7,8,_,_
	// 2,4
	//
	// 1,3,4,5,6,7,8,_
	// 2
	// 
	// 1,2,3,4,5,6,7,8
	// 
	// Third option, start at max(a) and middle(b), if middle(b) is higher than max(a), copy everything
	// from middle(b) after a, otherwise… (this is becoming too complex…)

	/** (N^2) simple implementation of merging two sorted lists 'a' and 'b' 
	*	operating always within a (that has space for the two lists)
	*	with a total space consumption of (a+b)+1+b
	*	(after all this thinking we have ended up with a crappy bubblesort scheme)
	* @param a
	* @param b
	*///////////////////////////////////////////////////////////////////////////
	public static <T extends Comparable<? super T>> void bubbleSortIntoFirstList(List<T> a, List<T> b) {

		// error checking
		if (a==null || b==null) {
			return;
		}

		// we assume we can’t really modify b so we make a copy at the end
		int bStart = a.size();
		int bEnd = bStart+b.size();
		for (T e : b) {
			a.add(e);
		}

		for (int i = 0; i<bStart; i++) {
			T currentA = a.get(i);
			int j = bStart;
			T currentB = a.get(j);
			if (currentA.compareTo(currentB)>0) {
				// a[i] > b[j], swap
				a.set(i, currentB);
				currentB = currentA;
				a.set(j, currentB);
				j++;
				while (j<bEnd && currentB.compareTo(a.get(j))>0) {
					T lower = a.get(j);
					a.set(j, currentB);
					a.set(j-1, lower);
					j++;
				}
			}
		}	// for

	}

	// faster implementation ideas?
	// 1,5,9
	// 4,7,8
	// merge into one and set the pivot ‘p’ to beginning of b
	// 1,5,9,[4],7,8
	// go through a and compare with pivot, if lower, swap
	// 1,4,9,[5],7,8
	// 1,4,5,[9],7,8
	// when consumed the whole of a, put pivot in right place by iterating over b
	// 1,4,5,7,[9],8
	// 1,4,5,7,8,[9]
	//
	// another example, which shows we need to move the pivot and reiterate O(N^2)
	// 1,5,7,[2],3,4,8
	// 1,2,7,[5],3,4,8
	// 1,2,5,[7],3,4,8 (now we need to move the pivot to the right and so forth)
	// ...
	//
	// Basically, we could also implement mergesort by shifting the one of the arrays 
	// but this is still O(N^2).
	// (Otherwise we just quicksort using the middle pivot between a and b)
	// OK, if we have strict constraints to not to use additional buffers
	// or a call stack (which is a LIFO buffer anyway) then we're stuck in N^2-land
	// It would make sense in specific constrained environments or where datasize
	// is small / strict limited memory, etc.
	//
	// So let's do quicksort in place by using the call stack:
	
	
	
	/** QuickSort in place by swapping, pivot at end of array
	* 	@param a
	*///////////////////////////////////////////////////////////////////////////
	public static <T extends Comparable<? super T>> void quickSortInPlace(List<T> a) {

		if (a==null) {
			return;
		}
		int sizeA = a.size();
		quickSortInPlace(a, 0, sizeA-1, sizeA);
	}
	
	private static <T extends Comparable<? super T>> void quickSortInPlace(List<T> a, int low, int p, int high) {
		// error checking

		if (a==null) {
			return;
		}
		if (low<0 || p>=high || high>a.size()) {
			throw new IndexOutOfBoundsException("Index out of range of the array");
		}

		// base case (one or zero-sized array is trivially sorted)
		if (high-low<=1) {
			return;
		}

		// divide the array in two partially sorted segments (firstHigh is the divider)
		// notice: firstHigh points to space immediately to the right of lower segment
		int firstHigh = low;
		for (int i = low; i<high; i++) {
			T current = a.get(i);
			T pivotValue = a.get(p);
			if (current.compareTo(pivotValue)<0) {
				a.set(i, a.get(firstHigh));	// swap current with firstHigh value
				a.set(firstHigh, current);
				firstHigh++;
			}
		}
		// switch current pivot and firstHigh (note that pivot value could have been swapped)
		T pivotValue = a.get(p);
		a.set(p, a.get(firstHigh));
		a.set(firstHigh, pivotValue);
		// now the pivot is at first high and to the left of it everything is lower or equal
		// and on the right it’s higher or equal, we can recursively call on each half and we’re done

		// 
		quickSortInPlace(a, low, firstHigh-1, firstHigh);
		quickSortInPlace(a, firstHigh+1, high-1, high);

	}

	
	/** Quicksort in place no extra memory besides split tree indexes on stack
	* @param a
	*///////////////////////////////////////////////////////////////////////////
	public static <T extends Comparable<? super T>> void quickSortInPlaceSplit(List<T> a) {

		if (a==null) {
			return;
		}

		int sizeA = a.size();
		quickSortInPlace(a, 0, sizeA/2, sizeA);
	}
	
	
	/** Given a list of words, sort the anagrams together
	* @param l
	* @return
	*////////////////////////////////////////////////////////////////////////////////
	public static List<String> sortByAnagramsN2(List<String> l) {

		// brute force approach, but grouping the strings of the same size
		// to make it faster, repetitions of the same word are ‘merged’
		
		if (l==null) {
			return null;
		}
		
		Map<Integer, Set<String>> groupedStrings = new HashMap<Integer,Set<String>>();
		for (String s: l) {
			int c = s.length();
			Set<String> stringsOfSizeC = null;
			if (groupedStrings.containsKey(c)) {
				stringsOfSizeC = groupedStrings.get(c);
			} else {
				stringsOfSizeC = new HashSet<String>();
			}
			stringsOfSizeC.add(s);					// merges identical words
			groupedStrings.put(c, stringsOfSizeC); 
		}

		List<String> anagramSorted = new ArrayList<String>(l.size());

		for (Set<String> stringGroup : groupedStrings.values()) {
			while (!stringGroup.isEmpty()) {
				List<String> ks = new ArrayList<String>(stringGroup);
				String currentString = ks.get(0);
				stringGroup.remove(currentString);
				anagramSorted.add(currentString);
				for (int i=1; i<ks.size(); i++) {
					String anagramCandidate = ks.get(i);
					if (Strings.isAnagramOf(currentString, anagramCandidate)) {
						anagramSorted.add(anagramCandidate);
						stringGroup.remove(anagramCandidate);
					}
				}
			} // while
		}
		return anagramSorted;
	}

	
	public static <T extends Comparable<? super T>> List<T> yetAnotherMergeSort(List<T> l) {

		if (l==null) {
			return null;
		}
		int size = l.size();
		if (size<=1) {
			return l;
		}

		int halfSize = size/2;		// size>=2, so worst case halfSize==1
		List<T> leftSortedChunk = yetAnotherMergeSort(l.subList(0, halfSize));
		List<T> rightSortedChunk = yetAnotherMergeSort(l.subList(halfSize, size));

		int leftIndex = 0;	
		int rightIndex = 0;
		int leftSize = leftSortedChunk.size();
		int rightSize = rightSortedChunk.size();
		List<T> sortedList = new ArrayList<T>(size);
		while (leftIndex < leftSize && rightIndex < rightSize) {
			T leftS = leftSortedChunk.get(leftIndex);
			T rightS = rightSortedChunk.get(rightIndex);
			if (leftS.compareTo(rightS)<0) {
				sortedList.add(leftS);
				leftIndex++;
			} else {
				sortedList.add(rightS);
				rightIndex++;
			}
		}
		if (leftIndex<leftSize) {
			sortedList.addAll(leftSortedChunk.subList(leftIndex, leftSize));
		}
		if (rightIndex<rightSize) {
			sortedList.addAll(rightSortedChunk.subList(rightIndex, rightSize));
		}

		return sortedList;
	}
	
	public static <T extends Comparable<? super T>> List<T> streamQuickSort(List<T> l) {
		
		if (l==null) {
			return null;
		}
		
		int size = l.size();
		if (size<=1) {
			return l;
		}

		int p = size/2;
		T pivotValue = l.get(p);
		Map<Boolean,List<T>> g = IntStream.iterate(0, i -> i+1)
				.limit(size)
				.filter(i -> i!=p)
				//.parallel()
				 .mapToObj(i -> l.get(i))
			    //.collect(Collectors.groupingByConcurrent(e -> e.compareTo(pivotValue)>0 ));
				.collect(Collectors.groupingBy(e -> e.compareTo(pivotValue)>0 ));

		// g.get(false) should be lower than pivot and true is higher or equal ^_^
		// but apparently is the other way around (hence the > in the collector)

		List<T> left = null;
		if (g.containsKey(false)){
			left = streamQuickSort(g.get(false));
		} else {
			left = new ArrayList<T>();
		}
		left.add(pivotValue);
		
		if (g.containsKey(true)) {
			List<T> right = streamQuickSort(g.get(true));
			left.addAll(right);
		}

		return left;
	}

	// 3.5: sort a stack so the smallest items are on the top, you can use an auxiliary temporary 
	// stack but no other structures, operations are push, pop, peek, isEmpty

	// one way would be to use recursivity, which implictly uses a stack
	// if the stack is empty: return stack
	// if the stack has only one element: return stack
	// if the stack has more than one element
	// remove top of the stack
	// recursive call on the rest, by induction, what is returned is sorted
	// if the item I have is smaller than the top, I push to stack and return
	// if the item is bigger, I push onto secondary stack until the item is smaller or I reach the 
	// end, then push the item, then push secondary stack into primary, return stack

//		return sortRecReversed(s);
	
	//		return Stack.reverse(sortRecReversed(s));
//	}
	
//	private static <T extends Comparable<? super T>> Stack<T> sortRecReversed(Stack<T> s) {
	public static <T extends Comparable<? super T>> Stack<T> sortRec(Stack<T> s) {

		// base cases
		if (s.isEmpty()) {
			return s;
		}
		T top = s.pop();
		if (s.isEmpty()) {
			s.push(top);
			return s;
		}

		Stack<T> sorted = sortRec(s); // recursive call, by induction we are sorted now, but not the ‘top’ item
		Stack<T> aux = new Stack<T>();
		while (!sorted.isEmpty() && top.compareTo(s.peek()) > 0) {
			aux.push(sorted.pop());
		}
		sorted.push(top);
		while (!aux.isEmpty()) {
			sorted.push(aux.pop());
		}

		return sorted;

	}


	// do we have an iterative method?
	// yes, outer loop
	// while stack is not empty
//		current element is top of the stack
//		if we are at the end, finish, otherwise:
//		if the top is smaller than the new top, continue
//		if the top is bigger than the new top, swap out until we find the location in the aux
	// at the end, move all elements from the aux to the main stack, return ^^

	// [3, 1, 4, 9, 2, 7]
	// [3], [1, 4, 9, 2, 7] SWAP
	// [1], [3, 4, 9, 2, 7]
	// [1, 3] [4, 9, 2, 7]
	// [1, 3, 4] [9, 2, 7]
	// [1, 3, 4, 9] [2, 7] SWAP and bubble down
	// [1, 2, 3, 4, 9] [7], take 7, we have to bubble it down
	// [1, 2, 3, 4, 7, 9] , move from aux and return

	public static <T extends Comparable<? super T>> Stack<T> sort(Stack<T> s) {

		Stack<T> aux = new Stack<T>();

		while (!s.isEmpty()) { 	//// main loop ////
			T current = s.pop(); // we always bubble
			while (!aux.isEmpty() && current.compareTo(aux.peek()) < 0) {
				s.push(aux.pop());
			}
			// we can add to aux the current element, aux is now sorted
			aux.push(current);
			// at this moment we could have an optimization that stores the state, namely the
			// number of bubbles and we restore it, we will save comparisons but still
			// O(n^2), and without it we are more functional (this is actually equivalent to the state stored in the
			// recursive call 'stack')
		} 						//// end main ////
		// now we reverse and return the sorted stack
		while (!aux.isEmpty()) {
			s.push(aux.pop());
		}

		return s;

	}


	// we sort elements given their frequency in the array
	// if same frequency, we sort by increasing value
	// In: {2, 3, 5, 3, 7, 9, 5, 3, 7}
	// Out: {3, 3, 3, 5, 5, 7, 7, 2, 9}
	// we go over elements into a map of id -> freq
	// we sort using a lambda
	public static int[] sortByFrequency(int[] array) {

		var n = array.length;
		var freq = new HashMap<Integer, Integer>();
		for (var i = 0; i < n; i++) {
			var v = array[i];
			var count = freq.get(v);
			freq.put(v, count == null ? 1 : count + 1);
		}
		var sorted = freq
				.entrySet()
				.stream()
				.sorted(
						(	e0,
							e1) -> {
							Integer e0f = e0.getValue();
							Integer e1f = e1.getValue();
							return (e0f.equals(e1f)) ? e0.getKey().compareTo(e1.getKey())
									: e1f.compareTo(e0f);
						})
				.map(entry -> entry.getKey())
				.collect(Collectors.toUnmodifiableList());
		var out = new int[n];
		var index = 0;
		for (var i = 0;i<sorted.size();i++) {
			var v = sorted.get(i);
			for (var j=0;j<freq.get(v);j++) {
				out[index++] = v;
			}
		}
		return out;
	}
	
}

/*
 * Copyright 2024 Daniel Giribet
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
