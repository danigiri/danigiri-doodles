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
	
}
