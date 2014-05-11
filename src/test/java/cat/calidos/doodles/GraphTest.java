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

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class GraphTest {


private static Graph<String>	expectedX;
private static Graph<String>	expectedY;


@Before
public void setup() {
	
	expectedX = new Graph<String>("a");
	expectedX.addEdge("a", "b");
	expectedX.addEdge("a", "c");
	expectedX.addEdge("c", "d");
	// expectedX:
	//		a ->b
	//		  ->c -\
	//		        -> d
	
	expectedY = new Graph<String>("a");
	expectedY.addEdge("a", "b");
	expectedY.addEdge("a", "c");
	expectedY.addEdge("a", "d");
	// expectedY:
	//		a ->b
	//		| ->c
	//		|       -> d
	//		\ -----/
	
}


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
	
	s = g.depthFirstSearch();
	assertTrue(s.equals(expectedX) || s.equals(expectedY));
}


@Test
public void breadthFirstFindTest() {
	
	Graph<String> g = new Graph<String>("a");
	Queue<String> p = QueueFrom.strings("a");
	assertEquals(p, g.breadthFirstFind("a", "a"));

	g.addEdge("a", "b");
	p = QueueFrom.strings("a", "b");
	assertEquals(p, g.breadthFirstFind("a", "b"));

	g.addEdge("a", "c");
	assertEquals(p, g.breadthFirstFind("a", "b"));

	g.addEdge("c", "d");
	p = QueueFrom.strings("a", "c", "d");
	assertEquals(p, g.breadthFirstFind("a", "d"));

	// now we do two variants and regardless of path taken we always find 
	// breadth-first route
	g = new Graph<String>("a");
	g.addEdge("a", "b");
		g.addEdge("b", "d");
		g.addEdge("b", "e");
		g.addEdge("b", "h");		// route 1 (breadth-first)
	g.addEdge("a", "c");
		g.addEdge("c", "f");
		g.addEdge("c", "g");
			g.addEdge("g", "h");	// route 2
	p = QueueFrom.strings("a", "b", "h");
	assertEquals(p, g.breadthFirstFind("a", "h"));

	g = new Graph<String>("a");
	g.addEdge("a", "b");
		g.addEdge("b", "d");
		g.addEdge("b", "e");
			g.addEdge("e", "h");	// route 1
	g.addEdge("a", "c");
		g.addEdge("c", "f");
		g.addEdge("c", "g");
			g.addEdge("c", "h");	// route 2 (breadth-first)
	p = QueueFrom.strings("a", "c", "h");
	assertEquals(p, g.breadthFirstFind("a", "h"));

}


@Test
public void breadthFirstSearchTest() {
	
	Graph<String> g = new Graph<String>("a");
	Graph<String> s = g.breadthFirstSearch();
	assertEquals(g, s);

	g.addEdge("a", "b");
	s = g.breadthFirstSearch();
	assertEquals(g, s);

	g.addEdge("a", "c");
	s = g.breadthFirstSearch();
	assertEquals(g, s);

	g.addEdge("c", "d");
	s = g.breadthFirstSearch();
	assertEquals(g, s);

	g.addEdge("a", "d");
	// g:
	//		a ->b
	//		| ->c -\
	//		|       => d
	//		\ -----/
	
	s = g.breadthFirstSearch();
	assertEquals(expectedY, s);
	
	g.addEdge("b", "e");
	expectedY.addEdge("b", "e");
	s = g.breadthFirstSearch();
	assertEquals(expectedY, s);

	g.addEdge("d", "f");
	g.addEdge("a", "f");
	expectedY.addEdge("a", "f");
	s = g.breadthFirstSearch();
	assertEquals(expectedY, s);

	g.addEdge("a", "x");
	g.addEdge("x", "d");
	expectedY.addEdge("a", "x");
	s = g.breadthFirstSearch();
	assertEquals(expectedY, s);

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
