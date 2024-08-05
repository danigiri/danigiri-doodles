package cat.calidos.doodles;

import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

public class Dijkstra {


/*
 * 
 * For example, let's consider the following problem. Imagine that you have a pyramid built of
 * numbers, like this one here:
 * 
 *   /3/
 *   \7\ 4
 *  2 \4\ 6 
 * 8 5 \9\ 3 

 * Let's say that the 'slide down' is the maximum sum of consecutive numbers from the top to the
 * bottom of the pyramid. As you can see, the longest 'slide down' is 3 + 7 + 4 + 9 = 23
 * 
 * Your task is to write a function that takes a pyramid representation as an argument and returns
 * its largest 'slide down'. For example:
 * 
 * With the input `[[3], [7, 4], [2, 4, 6], [8, 5, 9, 3]]` Your function should return `23`.
 * 
 * write some examples \2\ --> max 2 1 \4\ --> max 4 1 1 \4\ --> max 4 10 1 1 \9\ --> max 10
 * 
 * also get an ordered map of rows per max value max 10 --> r3 max 4 --> r2 max 4 --> r1 max 2 -->
 * r0
 * 
 * 
 * \2\ --> max 2 1 \4\ --> max 4 1 /1/ 4 --> max 4 /10/ 1 1 6 --> max 10
 * 
 * Let's build a directed graph first

 */

class WeightedGraph {

Map<String, Set<String>>	edges		= new HashMap<String, Set<String>>();
Map<String, Integer>		edgeValues	= new HashMap<String, Integer>();

public void addVertex(String id) {
	edges.put(id, (Set<String>) new HashSet<String>());
}


public boolean containsVertex(String id) {
	return edges.containsKey(id);
}


public Set<String> getVertexs() {
	return edges.keySet();
}


public void addEdge(String id, String dest, Integer v) {
	var previousEdges = edges.get(id);
	previousEdges.add(dest);
	edges.put(id, previousEdges);
	edgeValues.put(id + "," + dest, v);
}


public Set<String> getAdjacentVertexs(String id) {
	return edges.get(id);
}


public boolean hasAdjacentVertex(String id, String dest) {
	return getAdjacentVertexs(id).contains(dest);
}


public int getEdgeValue(String id, String dest) {
	return edgeValues.get(id + ',' + dest);
}


@Override
public String toString() {
	return edges.keySet().toString()+";\n"+edges.toString()+";\n"+edgeValues.toString();
}


}


public static int longestSlideDown(int[][] pyramid) {

	if (pyramid.length == 0) {
		return 0;
	}

	// first we build the graph
	var g = (new Dijkstra()).new WeightedGraph();
	var numElems = 0;
	var maxValue = 0;
	var minValue = Integer.MAX_VALUE;

	for (int level = 0; level < pyramid.length; level++) {
		for (int i = 0; i < pyramid[level].length; i++) {
			var id = level + "x" + i;
			if (!g.containsVertex(id)) {
				var w = pyramid[level][i];
				maxValue = Math.max(maxValue, w);
				minValue = Math.min(minValue, w);
				numElems++;
				g.addVertex(id);
			}
			// add it's descendants edges
			for (int d = i - 1; d < i + 2; d++) {
				if (level + 1 < pyramid.length && d >= 0 && d < pyramid[level + 1].length) {
					g.addEdge(id, level + 1 + "x" + d, pyramid[level + 1][d]);
				}
			}
		}
	}

	// setup dijkstra's structures, distance and prev
	var dist = new HashMap<String, Integer>(numElems); // distance from source to source
	var prev = new HashMap<String, String>(numElems); // previous node in optimal path
	dist.put("0x0", 0);
	prev.put("0x0", null);
	for (String vertex : g.getVertexs()) {
		if (!vertex.equals("0x0")) {
			dist.put(vertex, Integer.MAX_VALUE); //
			prev.put(vertex, null);
		}
	}
	// next we need a priority queue,suboptimal but still better than brute force
	var q = new PriorityQueue<String>((a, b) ->{
		var bValue = dist.get(b);
		var aValue = dist.get(a);
		var diff = -bValue+aValue;
		return diff;
	});
	g.getVertexs().stream().forEach(v -> {
		q.add(v);	
	});

	// work with the heap
	while (!q.isEmpty()) {
		var u = q.remove();
		var adjacent = g.getAdjacentVertexs(u);
		for (String v : adjacent) {
			var alt = dist.get(u) + (maxValue-g.getEdgeValue(u, v)-minValue); //min 1: dist=10, max:10, dist=1  
			if (alt < dist.get(v)) {
				dist.put(v, alt);
				prev.put(v, u);
				decreaseKey(q, v);
			}
		}
	}

	// now we find which one of the final vertexs has the best path
	var bestPath = 0;
	for (int j = 0; j < pyramid[pyramid.length - 1].length; j++) {
		var currentPath = 0;
		var v = (pyramid.length - 1) + "x" + j;
		for (int level = pyramid.length - 1; level >= 0; level--) {
			currentPath += pyramid[level][rowFromId(v)];
			v = prev.get(v);
		}
		bestPath = Math.max(bestPath, currentPath);
	}
	return bestPath;
}


private static void decreaseKey(PriorityQueue<String> q, String id) {
	var removed = new ArrayList<String>();
	while (!q.isEmpty() && !q.peek().equals(id)) {
		removed.add(q.remove());
	}
	// found, we remove and reinsert
	if (!q.isEmpty()) {
		q.remove();
	} 
	q.add(id);// this will use the new distance
	removed.stream().forEach(v -> q.add(v));
}


private static int rowFromId(String id) {
	var i = id.indexOf("x")+1;
	return Integer.valueOf(id.substring(i));
}

}


/*
 * Copyright 2024 Daniel Giribet <dani - calidos.cat>
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
