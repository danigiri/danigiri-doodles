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


public class Bits {

/** 'substring' from i to j from M into N
 * @param n integer
 * @param m integer
 * @param i start index
 * @param j end index
 * @return return modified N that includes the subsection form M
 */
public static int subBits(int n, int m, int i, int j) {

	if (i<0 || j<0 || i>31 || j>31) {
		throw new IndexOutOfBoundsException("Indices out of bounds");
	}

	// internally, i is always the smaller index
	if (i>j) {
		int s = i;
		i = j;
		j = s;
	}
	
	for (int x = i; x<=j; x++) {
		int mask = 0x1 << x;
		int v = m & mask;
		if (v==0x0) {	// m bit is set to zero
			n &= ~(mask);
		} else {		// m bit is set to one
			n |= mask;
		}
	}

	return n;
}


}
