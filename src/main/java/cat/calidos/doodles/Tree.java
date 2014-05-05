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

import java.util.HashMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Tree<T> {

	public T	data;
	public Map<String, Tree<T>>	children;
	public Tree<T> left;
	public Tree<T> right;
	public int	maxKeyLength;

	public Tree(T d) {
		
		data = d;
		children = new HashMap<String, Tree<T>>();
		maxKeyLength = 0;
		
	}

	
	public void addChild(String k, Tree<T> c) {
		
		children.put(k, c);
		if (k.length()>maxKeyLength) {
			maxKeyLength = k.length();
		}
		
	}
	
	
	public boolean hasChild(String k) {
		return getChild(k)!=null;
	}
	
	
	public Tree<T> getChild(String k){
		return children.get(k);
	}

	
	public List<T> preorder() {
		return preorder_(new ArrayList<T>());
	}

	private List<T> preorder_(List<T> l) {
		l.add(data);
		if (left!=null) {
			l = left.preorder_(l);
		}
		if (right!=null) {
			l = right.preorder_(l);
		}
		return l;
	} 

	public List<T> inorder() {
		return inorder_(new ArrayList<T>());
	}

	private List<T> inorder_(List<T> l) {
		if (left!=null) {
			l = left.inorder_(l);
		}
		l.add(data);
		if (right!=null) {
			l = right.inorder_(l);
		}
		return l;
	} 

	public List<T> postorder() {
		return postorder_(new ArrayList<T>());
	}

	
	private List<T> postorder_(List<T> l) {
		if (left!=null) {
			l = left.postorder_(l);
		}
		if (right!=null) {
			l = right.postorder_(l);
		}
		l.add(data);
		return l;
	} 
	
	
	public boolean insertSorted(T d) {	// O(logN)

		if (data==null) {
			data = d;
			return true;
		}
		int compare = ((Comparable)d).compareTo(data);
		// base case
		if (compare==0) {
			return false;
		}
		if (compare<0) {
			if (left==null) {
				// base case
				left = new Tree(d);
				return true;
			} else {
				return left.insertSorted(d);	// induction, left tree is smaller
			}
		} else {
			if (right==null) {
				right = new Tree(d);
				return true;
			} else {
				return right.insertSorted(d);	// induction, right tree is smaller
			}
		}
		
	}
	
	
	@Override
	public String toString() {
		return toString(new StringBuffer(), new StringBuffer());
	}

	
	protected String toString(StringBuffer s, StringBuffer tab) {		
		
		tab.append(" ");
		s.append(tab);
		s.append("[");
		s.append(data);
		
		if (left==null && right==null) {
			if (children!=null && !children.isEmpty()) {
				s.append(",\n");
				for (Iterator<String> keys = children.keySet().iterator(); keys.hasNext();) {
					String k = keys.next();
					Tree<T> t = children.get(k);
					if (t!=null) { 
						s.append(t.toString(new StringBuffer(), new StringBuffer(tab)));
					} else {
						s.append(tab);
						s.append(tab);
						s.append("[");
						s.append(k);
						s.append("]");
						s.append(",\n");
					}
				}
			}
		} else {
			s.append(",\n");
			if (left!=null) {
				s.append("l:"+left.toString(new StringBuffer(), new StringBuffer(tab)));
			}
			if (right!=null) {
				s.append("r:"+right.toString(new StringBuffer(), new StringBuffer(tab)));
			}
		}
		
		s.append(tab);
		s.append("]\n");

		return s.toString();
		
	}
}