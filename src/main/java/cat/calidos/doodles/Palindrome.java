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


public class Palindrome {

public static String longest(final String s) {
	
	String pal;
	if (s==null) {
		throw new NullPointerException("Null doesn't have palindromes");
	}

	// base cases
	int size = s.length();
	if (size <= 1) {
		pal = s;
	} else {
		
		// size>=2
		char first = s.charAt(0);
		char last = s.charAt(size-1);
		String rest = s.substring(1, size-1);

		// recursive cases
		if (first==last) {
			String candidate = Palindrome.longest(rest); // induction
			if (candidate.length()+2==size) {
				pal = s;	// x + palindrome + x == palindrome)
			} else {
				pal = first+"";	// left by convention
			}
		} else {
			String palLeft = Palindrome.longest(s.substring(0, size-1));	// induction again
			String palRight = Palindrome.longest(s.substring(1, size));
			if (palLeft.length()>=palRight.length()) {	// if equal, left by convention
				pal = palLeft;
			} else {
				pal = palRight;
			}
		}
		
	}
	return pal;

}

}
