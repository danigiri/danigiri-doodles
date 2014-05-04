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

import java.util.Iterator;
import java.util.Map;


public class Tree<S> {

	public S	value;
	public Map<String, Tree<S>>	children;
	public Tree<S> left;
	public Tree<S> right;
	public int	maxKeyLength;

	public Tree(S data) {
		
		value = data;
		children = new HashMap<String, Tree<S>>();
		maxKeyLength = 0;
		
	}

	
	public void addChild(String k, Tree<S> c) {
		
		children.put(k, c);
		if (k.length()>maxKeyLength) {
			maxKeyLength = k.length();
		}
		
	}
	
	
	public boolean hasChild(String k) {
		return getChild(k)!=null;
	}
	
	
	public Tree<S> getChild(String k){
		return children.get(k);
	}

	
	@Override
	public String toString() {
		return toString(new StringBuffer(), new StringBuffer());
	}

	
	protected String toString(StringBuffer s, StringBuffer tab) {		
		
		tab.append(" ");
		s.append(tab);
		s.append("[");
		s.append(value);
		
		if (left==null && right==null) {
			if (children!=null && !children.isEmpty()) {
				s.append(",\n");
				for (Iterator<String> keys = children.keySet().iterator(); keys.hasNext();) {
					String k = keys.next();
					Tree<S> t = children.get(k);
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