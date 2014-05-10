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

import static org.junit.Assert.*;

import org.junit.Test;

public class GraphTest {

@Test
public void depthFirstFindTest() {
	Graph<String> g = new Graph<String>("a");
	Queue<String> p = QueueFrom.strings("a");
	assertEquals(p, g.depthFirstFind("a", "a"));
	
	g.addEdge("a", "b");
	p = QueueFrom.strings("a","b");
	assertEquals(p, g.depthFirstFind("a", "b"));

	g.addEdge("b", "c");
	p = QueueFrom.strings("a", "b", "c");
	assertEquals(p, g.depthFirstFind("a", "c"));

	g = new Graph<String>("a");
	g.addEdge("a", "b");
	g.addEdge("a", "c");
	g.addEdge("b", "s");
	p = QueueFrom.strings("a", "b", "s");
	assertEquals(p, g.depthFirstFind("a", "s"));
	
}

@Test
public void depthFirstSearchTest() {
	Graph<String> g = new Graph<String>("a");
	Graph<String> s = g.depthFirstSearch();
	assertEquals(g, s);

	g.addEdge("a", "b");
	s = g.depthFirstSearch();
	assertEquals(g, s);

	g.addEdge("a", "c");
	s = g.depthFirstSearch();
	assertEquals(g, s);

	g.addEdge("c", "d");
	s = g.depthFirstSearch();
	assertEquals(g, s);

	g.addEdge("a", "d");
	// g:
	//		a ->b
	//		| ->c -\
	//		|       => d
	//		\ -----/
	
	Graph<String> expectedX = new Graph<String>("a");
	expectedX.addEdge("a", "b");
	expectedX.addEdge("a", "c");
	expectedX.addEdge("c", "d");
	// expectedX:
	//		a ->b
	//		  ->c -\
	//		        -> d
	
	Graph<String> expectedY = new Graph<String>("a");
	expectedY.addEdge("a", "b");
	expectedY.addEdge("a", "c");
	expectedY.addEdge("a", "d");
	// expectedY:
	//		a ->b
	//		| ->c
	//		|       -> d
	//		\ -----/
	
	s = g.depthFirstSearch();
	assertTrue(s.equals(expectedX) || s.equals(expectedY));
}

//@Test
//public void asListTest() {
//	
//	Graph<String> g = new Graph<String>("a");
//	List<String> l  = ListFrom.strings("a");
//	assertEquals(l, g.asList());
//
//	g.addEdge("a","b");
//	l  = ListFrom.strings("a", "b");
//	assertEquals(l, g.asList());
//	
//	g.getConnectedNode("b").connectTo("c");
//	l  = ListFrom.strings("a", "b", "c");
//	assertEquals(l, g.asList());
//	
//}
//
//@Test(expected = RuntimeException.class)
//public void asListTestCycle() {
//	Graph<String> g = new Graph<String>("a");
//	g.connectTo("b");
//	g.getConnectedNode("b").connectTo(g);
//	g.asList();
//
//}

}
