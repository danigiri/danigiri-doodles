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


public boolean isEmpty() {
	return vertexCount()==0;
}


public Queue<T> depthFirstFind(T s, T d) {
	if (!hasVertex(s) || !hasVertex(d)) {
		return null;
	}
	return depthFirstFind_(new HashSet<T>(), s, d);
}


protected Queue<T> depthFirstFind_(Set<T> p, T s, T d) {
	
	Queue<T> q = null;	// if we don't find it we will return null
	
	// base cases
	if (s.equals(d)) {
		q = new Queue<T>(s);
	} else if (areConnected(s, d)) {
		q = new Queue<T>(s);
		q.enqueue(d);
	} else {

		// recursive cases
		p.add(s);
		boolean found = false;
		Iterator<T> edges = getEdges(s).iterator();		
		while (edges.hasNext() && !found) {
			T e = edges.next();
			if (!p.contains(e)) {
				q = depthFirstFind_(p, e, d);
				if (q!=null && q.length()>0 && q.tail().equals(d)) {
					q.insert(s);
					found = true;
				}
			}
		}	// while
		
	}
	
	return q;
}


protected Graph<T> depthFirstSearch() {
	return depthFirstSearch_(new Graph<T>(), new HashSet<T>(), new HashSet<T>(getVertices()), null);
}


/**
 * @param g minimal depth first graph
 * @param orphans processed edges that aren't connected yet
 * @param v edges to be visited
 * @param c current edge
 * @return
 */
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
			// FIXME: this looks odd
			if (v.contains(e)) {			// recursive case, not an orphan & unprocessed
				g = depthFirstSearch_(g, orphans, v, e);	// induction: v has decreased
			}
		}
	}
	
	// there might still be unprocessed nodes
	return depthFirstSearch_(g, orphans, v, null);	// induction: v has decreased
	
}


public Queue<T> breadthFirstFind(T s, T d) {

	if (!hasVertex(s) || !hasVertex(d)) {
		return null;
	}
	return breadthFirstFind_(new HashSet<T>(getVertices()), new Queue<T>(s), d);
}


/**
 * @param v vertices to be visited
 * @param p processing queue (head of queue is current location)
 * @param d destination
 * @return
 */
public Queue<T> breadthFirstFind_(Set<T> v, Queue<T> p, T d) {

	Queue<T> q = null;
	
	// base cases
	T c = p.dequeue();	// funnily enough, queue is guaranteed to be nonempty
	if (c.equals(d)) {
		q = new Queue<T>(c);
	} else if (areConnected(c, d)) {
		q = new Queue<T>(c);
		q.enqueue(d);
	} else {

		// recursive cases
		v.remove(c);
		Set<T> edges = getEdges(c);
		if (edges.size()==0) {
			q = breadthFirstFind_(v, p, d);	// induction, v is smaller
		} else {
			for (T e : edges) {
				if (v.contains(e)) {
					p.enqueue(e);
				}
			}
			q = breadthFirstFind_(v, p, d);	// induction, v is smaller
			if (areConnected(c, q.head())) {	// if head of queue includes this path
				q.insert(c);
			}
		}
		
	}
	
	return q;
	
}


public Graph<T> breadthFirstSearch() {

	Graph<T> g = new Graph<T>();
	if (isEmpty()) {
		return g;
	}
	
	Set<T> v = new HashSet<T>(getVertices());
	Map<T,T> parents = new HashMap<T,T>(v.size());
		
	while (!v.isEmpty()) {
		T c = v.iterator().next();
		v.remove(c);
		g.addVertex(c);	
		Set<T> edges = getEdges(c);
		for (T e : edges) {

			if (!parents.containsKey(e)) {
				parents.put(e, c);
			} else {
				// nontrivial, if one of c's 'children' was a 'middleman'
				// of one of our children and we encountered it before then we
				// overwrite it. If, on the other hand, come to the middleman later
				// we do not want to overwrite the already minimal parent
				//	a ——> b
				//	 \——> x —- => d
				// 	  \———————/
				// c = a, edges = [b, d, x], e = d, parents = [ d:x ]
				// we overwrite [ d:x ] with [ d:a ]
				// otherwise
				// c = x, edges = [d], e = d, parents [ x:a, b:a, d:a ]
				// we do not overwrite
				if (edges.contains(parents.get(e))) {
					parents.put(e, c);
				}
			}
			
		}
	}
	for (T t : parents.keySet()) {
		g.addEdge(parents.get(t), t);
	}	

	return g;
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
