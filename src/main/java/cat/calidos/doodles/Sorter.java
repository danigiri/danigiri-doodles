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
		List<T> left = new ArrayList<T>();
		List<T> right = new ArrayList<T>();
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
	
	
	public static <X> Tree<X> bTree(final List<X>l) {
		if (l==null) {
			return null;
		}
		int lSize = l.size();
		if (lSize==0) {
			return null;
		}
		return bTree(new ArrayList<X>(l.subList(1, l.size())), new Tree<X>(l.get(0)));
		
	}
	
	public static <X> Tree<X> bTree(List<X>l, Tree<X> t) {

		// base cases
		int lSize = l.size();
		if (lSize==0) {
			return t;
		}

		X v = l.get(0);
		l.remove(0);
		
		// recursive case
		if (((Comparable<Comparable<?>>)v).compareTo((Comparable<?>)t.data)<=0) {	// sorry
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
	
	// TODO: simulate finer grained
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
}
