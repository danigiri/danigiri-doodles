package cat.calidos.doodles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


// tagged, directed graph, T equals ops are considered constant, T are considered unique within the
// graph
// no null tags
public class Graph<T extends Comparable<? super T>> {

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
		added = true;
	}

	return added;

}


public boolean removeVertex(T leaf) {
	return vertices.remove(leaf) != null;
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

	if (a == null || b == null) {
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


public boolean removeEdge(T a, T b) {

	if (a == null || b == null) {
		throw new NullPointerException("Null tags not allowed");
	}
	if (!hasVertex(a) || !hasVertex(b) || !areConnected(a, b)) {
		return false;
	}

	getEdges(a).remove(b);

	return true;

}


public Set<T> getEdges(T t) {
	if (!hasVertex(t)) {
		throw new IndexOutOfBoundsException("Graph does not have vertex " + t);
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
	return vertexCount() == 0;
}


public Queue<T> depthFirstFind(T s, T d) {
	if (!hasVertex(s) || !hasVertex(d)) {
		return null;
	}
	return depthFirstFind_(new HashSet<T>(), s, d);
}


protected Queue<T> depthFirstFind_(Set<T> p, T s, T d) {

	Queue<T> q = null; // if we don't find it we will return null

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
				if (q != null && q.length() > 0 && q.tail().equals(d)) {
					q.insert(s);
					found = true;
				}
			}
		} // while

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
	if (v.size() == 0) {
		return g;
	}

	if (c == null) {
		c = v.iterator().next();
	}
	v.remove(c);
	g.addVertex(c);
	orphans.add(c); // c is in the graph, but as an orphan

	Set<T> edges = getEdges(c);
	for (T e : edges) {
		if (orphans.contains(e)) { // base case
			g.addEdge(c, e); // orphan is now reconnected to the graph, yay!
			orphans.remove(e);
		} else {
			// FIXME: this looks odd
			if (v.contains(e)) { // recursive case, not an orphan & unprocessed
				g = depthFirstSearch_(g, orphans, v, e); // induction: v has decreased
			}
		}
	}

	// there might still be unprocessed nodes
	return depthFirstSearch_(g, orphans, v, null); // induction: v has decreased

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
	T c = p.dequeue(); // funnily enough, queue is guaranteed to be nonempty
	if (c.equals(d)) {
		q = new Queue<T>(c);
	} else if (areConnected(c, d)) {
		q = new Queue<T>(c);
		q.enqueue(d);
	} else {

		// recursive cases
		v.remove(c);
		Set<T> edges = getEdges(c);
		if (edges.size() == 0) {
			q = breadthFirstFind_(v, p, d); // induction, v is smaller
		} else {
			for (T e : edges) {
				if (v.contains(e)) {
					p.enqueue(e);
				}
			}
			q = breadthFirstFind_(v, p, d); // induction, v is smaller
			if (areConnected(c, q.head())) { // if head of queue includes this path
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
	Map<T, T> parents = new HashMap<T, T>(v.size());

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
				// a ——> b
				// \——> x —- => d
				// \———————/
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


public boolean areConnected2(T s, T e) {

	if (this.vertexCount() == 0 || !this.hasVertex(s) || !this.hasVertex(e)) {
		return false;
	}

	if (s == e) {
		return true;
	}

	boolean connected = false;
	List<T> pending = getEdges(s).stream().collect(Collectors.toList());

	Set<T> visited = new HashSet<T>();
	while (pending.size() > 0 && !connected) {
		List<T> nextPending = new ArrayList<T>();
		while (pending.size() > 0 && !connected) {
			T current = pending.remove(pending.size() - 1);
			if (!current.equals(e)) {
				getEdges(current).stream().filter(v -> !visited.contains(v)).forEach(v -> nextPending.add(v));
			} else {
				connected = true;
			}
		}
		if (!connected) {
			pending = nextPending;
		}
	}

	return connected;

}


// given a list of artifacts and dependencies, build a build order list
// artifact dependencies form a graph
// we need to keep a list of targets that have no parent, those will be the starting points for a
// search tree that will give us the list of dependencies for each target
// for each of those
// once we have the search tree for each, we start a graph recursive traversal, for current node
// skip if artifact has been built, otherwise:
// if artifact is a leaf
// a) add artifact to build list in that order
// b) add artifact to already built hash table, so we don’t rebuild it keeping O() low
// if artifact has children
// recursive call for each, by induction, when that returns we assume the artifact has been built
// add current node to built
// add current node to build list, return build list
// done!
// Warning: to avoid loops, we keep a visited hash (notice !=built list), if we find a child in the
// visited list we throw exception


public List<T> buildOrder(List<T> artifacts, List<T> sources, List<T> destinations) {

	if (artifacts == null || sources == null || destinations == null || sources.size() != destinations.size()) {
		throw new NullPointerException("Bad parameters");
	}

	// create dependency graph and potential targets list
	int artifactCount = artifacts.size();
	Graph<T> dependencies = new Graph<T>();
	Set<T> targets = new HashSet<T>(artifactCount);
	artifacts.stream().forEach(a -> targets.add(a));

	// build graph from dependencies lists
	for (int i = 0; i < sources.size(); i++) {
		T source = sources.get(i);
		T dest = destinations.get(i);
		if (targets.contains(dest)) { // no longer a target, now a dependency
			targets.remove(dest);
		}
		if (!dependencies.hasVertex(source)) {
			dependencies.addVertex(source); // O(k)
		}
		if (!dependencies.hasVertex(dest)) {
			dependencies.addVertex(dest); // O(k)
		}
		dependencies.addEdge(source, dest); // O(K)
	} // for

	// we have now a dependency graph and a list of root targets for each target we do rec call
	List<T> buildList = new ArrayList<T>(artifactCount);
	Set<T> builtSet = new HashSet<T>(artifactCount);
	targets.stream().forEach(a -> {
		Set<T> visited = new HashSet<T>();
		_buildList(a, visited, dependencies, builtSet).forEach(e -> buildList.add(e));
	});

	return buildList;

}


private List<T> _buildList(T artifact, Set<T> visited, Graph<T> dependencies, Set<T> builtSet) {

	List<T> buildList = new ArrayList<T>();
	// if the artifact is not in dependencies, it means that it is a standalone artifact that can be
	// built alone
	Set<T> edges = dependencies.hasVertex(artifact) ? dependencies.getEdges(artifact) : new HashSet<T>(0);
	if (edges.size() > 0) { // complex case
		edges.stream().forEach(child -> {
			if (visited.contains(child)) {
				throw new StackOverflowError("Loop detected by " + child + " dep");
			}
			if (!builtSet.contains(child)) {
				visited.add(artifact); // avoid loops
				_buildList(child, visited, dependencies, builtSet).forEach(e -> buildList.add(e)); // induction
				visited.remove(artifact); // when we go through a different branch, visited!=built
			}
		});
	}

	buildList.add(artifact);
	builtSet.add(artifact);

	return buildList;

}


// we use a different algorithm
// order in the graph is reversed, edges point to the projects that depend on a given artifact
// we build a graph of dependencies
// we ignore dependencies that point to themselves
// we maintain a list of nodes that have no edges incoming to those (leafs), which we will use
// to start the build list


public List<T> buildOrder2(List<T> artifacts, List<T> sources, List<T> destinations) {

	if (artifacts == null || sources == null || destinations == null) {
		throw new IndexOutOfBoundsException("Null params");
	}

	int artifactsCount = artifacts.size();
	int sourcesCount = sources.size();
	int destinationsCount = destinations.size();
	if (artifactsCount == 0 || sourcesCount != destinationsCount) {
		throw new IllegalArgumentException("Problematic arguments");
	}

	Graph<T> deps = new Graph<T>();
	artifacts.stream().forEach(a -> deps.addVertex(a));
	Set<T> leaves = artifacts.stream().collect(Collectors.toSet());
	for (int i = 0; i < sourcesCount; i++) {
		T source = sources.get(i);
		T dest = destinations.get(i);
		if (source == null || dest == null) {
			throw new NullPointerException("null dep at position " + i);
		}
		if (!source.equals(dest)) { // skip self references
			deps.addEdge(source, dest);
			if (leaves.contains(dest)) {
				leaves.remove(dest);
			}
		}
	}
	// graph is built
	if (leaves.size() == 0) {
		throw new RuntimeException("Cannot build");
	}

	// we create empty build list
	// once we have the graph and the leaf list
	// while leaf list is not empty
	// for each leaf,
	// add to build list
	// find the dependencies and for each dep
	// check dep is not in graph → loop error
	// add them to a temporary dep set
	// for each leaf,
	// remove its dep edges
	// remove leaf node
	// for all the dep set
	// add to leaf list

	List<T> build = new ArrayList<T>(artifactsCount);

	while (leaves.size() > 0) {
		Set<T> nextLeaves = new HashSet<T>();
		leaves.stream().forEach(leaf -> {
			Set<T> leafDeps = deps.getEdges(leaf);
			leafDeps.stream().forEach(leafDep -> {
				if (!deps.hasVertex(leafDep)) {
					throw new RuntimeException("Loop at " + leaf + "->" + leafDep);
				}
				nextLeaves.add(leafDep);
			});
			deps.removeVertex(leaf); // this will remove all the edges for this leaf
			build.add(leaf);
		});
		leaves = nextLeaves;
	}

	return build;

}


/*
 * given a directed graph, return true if there is a route between nodes A and B
 * 
 * let's do depth first search
 * 
 * check if current node is destination, if so return true if not, keep a visited list, if visited,
 * skip if not visited, recurse
 * 
 */


public static <T extends Comparable<? super T>> boolean hasRouteDFS(Graph<T> g, T source, T dest) {
	return hasRouteVisited(g, new HashSet<T>(), source, dest);
}


public static <T extends Comparable<? super T>> boolean hasRouteVisited(Graph<T> g, Set<T> visited, T current, T dest) {
	visited.add(current);
	// base case
	if (current.equals(dest)) {
		return true;
	}
	var found = false;
	Iterator<T> targets = g.getEdges(current).stream().filter(e -> !visited.contains(e)).collect(Collectors.toSet())
			.iterator();
	;
	while (!found && targets.hasNext()) {
		found = hasRouteVisited(g, visited, targets.next(), dest);
	}
	return found;
}

/*
 * let's do breadth first search
 * 
 * let's get a queue of nodes while queue is not empty check if node is target, if target → return
 * true if not target, get adjacent nodes and append to queue → return false
 */


public static <T extends Comparable<? super T>> boolean hasRouteBFS(Graph<T> g, T source, T dest) {
	var pending = new DLinkedListQueue<T>();
	pending.enqueue(source);
	while (!pending.isEmpty()) {
		T head = pending.head();
		pending.removeFirst();
		if (head.equals(dest)) {
			return true;
		}
		g.getEdges(head).forEach(e -> pending.enqueue(e));
	}
	return false;
}


@Override
public boolean equals(Object obj) { // O(N)

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
	Graph<T> g = (Graph<T>) obj;

	if (vertexCount() != g.vertexCount()) {
		return false;
	}

	boolean equals = true;
	Iterator<T> gVerticesIterator = getVertices().iterator();
	while (gVerticesIterator.hasNext() && equals) {
		T gVertex = gVerticesIterator.next();
		equals = vertices.containsKey(gVertex) && vertices.get(gVertex).equals(g.getEdges(gVertex));
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

// returns list of nodes only if graph maps strictly to a linked list, no loops allowed
// otherwise it throws a runtime exception
// public List<T> asList() {
//
// // weaken precondition by creating processed set
// return asList_(new ArrayList<T>(), new HashSet<T>());
// }
//
// private List<T> asList_(List<T> l, Set<T> processed) {
//
// // base cases
// int edgeCount = vertices.size();
// if (edgeCount==0) {
// l.add(tag);
// return l;
// } else if (edgeCount>1) {
// throw new RuntimeException("Can’t map to list to to more generic graph structure");
// } else {
// // recursive case, edge count==1
// T adjacentTag = getEdges().iterator().next(); // guaranteed to be one elem
// Graph<T> g = getConnectedNode(adjacentTag);
// if (processed.add(tag)) {
// l.add(tag);
// g.asList_(l, processed); // O(N), induction call on smaller graph
// } else {
// throw new RuntimeException("Loop, sorry!");
// }
// }
// return l;
//
// }

}

/*
 * Copyright 2024 Daniel Giribet
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
