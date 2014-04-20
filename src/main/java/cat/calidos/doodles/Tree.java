package cat.calidos.doodles;

import java.util.HashMap;

import java.util.Iterator;
import java.util.Map;


public class Tree<S> {

	public S	value;
	public Map<String, Tree<S>>	children;
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
		
		s.append(tab);
		s.append("]\n");

		return s.toString();
		
	}
}