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

import java.util.ArrayList;
import java.util.List;


public class WordLadder {

public static int climb(String start, String end, List<String> dict) {
	
	// sort the dictionary and create a tree of prefixes (N*LOG(N))
	List<String>sortedDict = Sorter.mergeSort(new ArrayList<String>(dict));
	Tree<String>keyTree = Words.buildKeyTree("", sortedDict);

	// next create a graph of adjacencies
	
	// use the key tree to efficiently look for a start node
	// for each child, of the key tree, do a substring on 'start' of the length
	// ot the key, check lax commonality with laxity==1, recursively
	
	// if not found, return 0
	
	// use the key tree to efficiently look for an end node
	
	// if not found, return 0
	
	// to a breadth first search on the graph starting from 'start' and 'end' node
	
	return 0;
}

}
