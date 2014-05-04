package cat.calidos.doodles;

import java.util.HashSet;
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


}
