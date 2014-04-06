package cat.calidos.doodles;

import java.util.ArrayList;
import java.util.List;

public class RecursiveSorting  {
	
	public static List<Integer> mergeSortedLists(final List<Integer> a, final List<Integer> b) {

		// base cases
		if ((a==null && b==null)) {
			return null;
		}
		if (a==null) {
			return new ArrayList<Integer>(b);
		}
		if (b==null) {
			return new ArrayList<Integer>(a);
		}
		if (a.size()==0) {
			return new ArrayList<Integer>(b);
		} else if (b.size()==0) {
			return new ArrayList<Integer>(a);
		}

		int lastFromAIndex = a.size()-1;
		int lastFromA  = a.get(lastFromAIndex);
		int firstFromB = b.get(0);
		List<Integer> aSorted = new ArrayList<Integer>(a);
		if (lastFromA <= firstFromB) {

			aSorted.addAll(b);
			return aSorted;
			
		} else {

			aSorted.remove(lastFromAIndex);
			List<Integer> bSorted = new ArrayList<Integer>(b);
			bSorted.remove(0);
			
			// x) list of (firstFromB, lastFromA) is trivially sorted due to if condition
			// y) a list minus one element is still sorted
			// by 'x' and 'y' precondition is fulfilled
			// therefore, we call recursively using the smaller data
			List<Integer> trivialList = new ArrayList<Integer>(2);
			trivialList.add(firstFromB);
			trivialList.add(lastFromA);
			List<Integer> inductionSorted = RecursiveSorting.mergeSortedLists(trivialList, bSorted);
			// by induction, we have a completely sorted list
			
			// notice we can use induction again as 'aSorted' is smaller
			return RecursiveSorting.mergeSortedLists(aSorted, inductionSorted);

		}			
				
	}
	
	
	public static List<Integer> sortList(final List<Integer>a) {
		
		// base cases
		if (a==null) {
			return null;
		}
		int size = a.size();
		if (size<=1) {
			return new ArrayList<Integer>(a);
		}
		// proven: size >=2
		
		int halfSize = size/2;
				
		List<Integer> left = RecursiveSorting.sortList(new ArrayList<Integer>(a.subList(0, halfSize)));
		List<Integer> right = RecursiveSorting.sortList(new ArrayList<Integer>(a.subList(halfSize, size)));
		return RecursiveSorting.mergeSortedLists(left, right);
	}
	
	
}
