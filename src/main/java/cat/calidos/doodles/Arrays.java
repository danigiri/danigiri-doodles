package cat.calidos.doodles;

import java.util.List;

/**
*	@author daniel giribet
*///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class Arrays<X> {


public void rotateInPlace(List<X> v, int pos) {

	if (v==null || pos==0 || v.size()<2 || v.size()==pos) {
		return;
	}

	for (int i=0; i<pos; i++) {
		shiftOne(v);
	}

}

private void shiftOne(List<X> v) {
	
	X first = v.get(0);
	int size = v.size();
	for (int i=1; i<size; i++) {
		v.set(i-1, v.get(i));
	}
	v.set(size-1, first);

}


}


/*
 *    Copyright 2019 Daniel Giribet
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

