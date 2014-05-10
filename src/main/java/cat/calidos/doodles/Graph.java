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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


//tagged, directed graph, T equals ops are considered constant, T are considered unique within the graph
//no null tags
public class Graph<T> {

protected Map<T, Set<T>> vertices;

public Graph() {
	vertices = new HashMap<T, Set<T>>();

}

public Graph(T t) {
	vertices = new HashMap<T, Set<T>>();
	addVertex(t);
}


public boolean addVertex(T t) {
	
	boolean added = false;
	
	if (!hasVertex(t)) {
		vertices.put(t, new HashSet<T>());
	}
	return added;
	
}


public boolean areConnected(T a, T b) {
	
	boolean areConnected;

	if (!hasVertex(a)) {
		areConnected = false;
	} else {
		areConnected = vertices.get(a).contains(b);
	}
		
	return areConnected;
}


public boolean addEdge(T a, T b) {
	if (a==null || b==null) {
		throw new NullPointerException("Null tags not allowed");
	}

	addVertex(a);
	addVertex(b);

	boolean newVertex;
	if (!areConnected(a, b)) {
		newVertex = true;
		vertices.get(a).add(b);
	} else {
		newVertex = false;
	}

	return newVertex;

}


public Set<T> getEdges(T t) {
	if (!hasVertex(t)) {
		throw new IndexOutOfBoundsException("");
	}
	return vertices.get(t);
}

public Set<T> getVertices() {
	return vertices.keySet();
}


public boolean hasVertex(T t) {
	return vertices.containsKey(t);
}


public int vertexCount() {
	return vertices.keySet().size();
}

public Queue<T> depthFirstFind(T s, T d) {
	if (!hasVertex(s) || !hasVertex(d)) {
		return null;
	}
	return depthFirstFind_(new HashSet<T>(), s, d);
}

protected Queue<T> depthFirstFind_(Set<T> p, T s, T d) {
	
	// base cases
	if (s.equals(d)) {
		Queue<T> q = new Queue<T>();
		q.enqueue(s);
		return q;
	}
	if (areConnected(s, d)) {
		Queue<T> l = new Queue<T>();
		l.enqueue(s);
		l.enqueue(d);
		return l;
	}

	// recursive cases
	p.add(s);
	Set<T> edges = getEdges(s);
	for (T e : edges) {
		if (!p.contains(e)) {
			Queue<T> ePath = depthFirstFind_(p, e, d);
			if (ePath.length()>0 && ePath.tail().equals(d)) {
				ePath.insert(s);
				return ePath;
			}
		}
	}
	return null;
}



protected Graph<T> depthFirstSearch() {
	return depthFirstSearch_(new Graph<T>(), new HashSet<T>(), new HashSet<T>(getVertices()), null);
}


protected Graph<T> depthFirstSearch_(Graph<T> g, Set<T> orphans, Set<T> v, T c) {
	
	// base case
	if (v.size()==0) {
		return g;
	}	

	if (c==null) {
		c = v.iterator().next();
	}
	v.remove(c);
	g.addVertex(c);
	orphans.add(c);							// c is in the graph, but as an orphan

	Set<T> edges = getEdges(c);
	for (T e : edges) {
		if (orphans.contains(e)) {			// base case
			g.addEdge(c, e);				// orphan is now reconnected to the graph, yay!
			orphans.remove(e);
		} else {
			if (v.contains(e)) {			// recursive case, not an orphan & unprocessed
				g = depthFirstSearch_(g, orphans, v, e);	// induction: v has decreased
			}
		}
	}
	// there might still be unprocessed nodes
	return depthFirstSearch_(g, orphans, v, null);	// induction: v has decreased
}



@Override
public boolean equals(Object obj) {		// O(N)

    if (obj == null) {
        return false;
    }
    if (obj == this) {
        return true;
    }
    if (!(obj instanceof Graph)) {
        return false;
    }
    
    @SuppressWarnings("unchecked")
    Graph<T> g = (Graph<T>)obj;
    
    if (vertexCount()!=g.vertexCount()) {
    	return false;
    }
    
    boolean equals = true;
    Iterator<T> gVerticesIterator = getVertices().iterator();
    while (gVerticesIterator.hasNext() && equals) {
    	T gVertex = gVerticesIterator.next();
    	equals = vertices.containsKey(gVertex) && 
    				vertices.get(gVertex).equals(g.getEdges(gVertex));
    }
    
    return equals;

}


@Override
public String toString() {
	StringBuffer s = new StringBuffer();
	s.append("[");
	for (T v : vertices.keySet()) {
			s.append("\t");
			s.append(v);
			s.append(":\t");
			s.append(getEdges(v));
			s.append("\n");
	}
	s.append("]");
	return s.toString();
}

//returns list of nodes only if graph maps strictly to a linked list, no loops allowed
//otherwise it throws a runtime exception
//public List<T> asList() {
//
//	// weaken precondition by creating processed set
//	return asList_(new ArrayList<T>(), new HashSet<T>());
//}
//
//private List<T> asList_(List<T> l, Set<T> processed) {
//
//	// base cases
//	int edgeCount = vertices.size();						
//	if (edgeCount==0) {
//		l.add(tag);
//		return l;
//	} else if (edgeCount>1) {
//		throw new RuntimeException("Can’t map to list to to more generic graph structure");
//	} else {
//		// recursive case, edge count==1
//		T adjacentTag = getEdges().iterator().next();		// guaranteed to be one elem
//		Graph<T> g = getConnectedNode(adjacentTag);			
//		if (processed.add(tag)) {
//			l.add(tag);
//			g.asList_(l, processed);			// O(N), induction call on smaller graph
//		} else {
//				throw new RuntimeException("Loop, sorry!");			
//		}
//	}
//	return l;
//
//}

}
