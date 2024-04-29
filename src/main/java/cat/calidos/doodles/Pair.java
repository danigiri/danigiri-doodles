package cat.calidos.doodles;


/**
*	@author daniel giribet
*///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public class Pair<X , Y> {

public X left;
public Y right;


Pair(X l, Y r) {

	this.left = l;
	this.right = r;	

}


@Override
public boolean equals(Object obj) {

	if (obj==null) {
		return false;
	}
	try {
		@SuppressWarnings("unchecked")
		Pair<X, Y> p = (Pair<X, Y>)obj;
		return (this.left==null && p.left==null) || (this.left.equals(p.left)) && 
				(this.right==null && p.right==null) || (this.right.equals(p.right));
	} catch (ClassCastException e) {
		return false;
	}
}


@Override
public String toString() {
	return "<"+left+","+right+">";
}


@Override
public int hashCode() {
	return this.toString().hashCode();
}


}

/*
 *    Copyright 2024 Daniel Giribet
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

