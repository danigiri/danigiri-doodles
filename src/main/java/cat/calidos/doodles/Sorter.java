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
	

	public static <T> List<T> mergeSortedLists(final List<T> a, final List<T> b) {
		
		if ((a==null || b==null)) {
			throw new NullPointerException("Not sorting null lists");
		}
		return Sorter._mergeSortedLists(new ArrayList<T>(a), new ArrayList<T>(b));
		
	}

	
	private static <T> List<T> _mergeSortedLists( List<T> a, List<T> b) {

		// base cases
		if (a.size()==0) {
			return b;
		} else if (b.size()==0) {
			return a;
		}

		int lastFromAIndex = a.size()-1;
		T lastFromA  = a.get(lastFromAIndex);
		T firstFromB = b.get(0);
		if (((Comparable)lastFromA).compareTo((Comparable)firstFromB)<=0) {	// sorry
			a.addAll(b);
			return a;
			
		} else {

			a.remove(lastFromAIndex);
			b.remove(0);
			
			// x) list of (firstFromB, lastFromA) is trivially sorted due to if condition
			// y) a list minus one element is still sorted
			// by 'x' and 'y' precondition is fulfilled
			// therefore, we call recursively using the smaller value
			List<T> trivialList = new ArrayList<T>();
			trivialList.add(firstFromB);
			trivialList.add(lastFromA);
			List<T> inductionSorted = Sorter._mergeSortedLists(trivialList, b);
			// by induction, we have a completely sorted list
			
			// notice we can use induction again as the new 'a' is smaller
			return Sorter._mergeSortedLists(a, inductionSorted);

		}			
				
	}
	
	
	public static <T> List<T> sortList(final List<T>a) {
		
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
				
		List<T> left = Sorter.sortList(new ArrayList<T>(a.subList(0, halfSize)));
		List<T> right = Sorter.sortList(new ArrayList<T>(a.subList(halfSize, size)));
		return Sorter.mergeSortedLists(left, right);
	}
	
	
}
