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

import java.util.List;

import org.junit.Test;

import cat.calidos.doodles.builders.ListFrom;


public class TreeTest {

@Test
public void preorderTest() {
	
	Tree<Integer> t = new Tree<Integer>(1);
	t.left = new Tree<Integer>(2);					
	t.left.left = new Tree<Integer>(3);
	t.left.right = new Tree<Integer>(4);			
	t.right = new Tree<Integer>(5);
	t.right.left = new Tree<Integer>(6);
	t.right.right = new Tree<Integer>(7);
	
	List<Integer> list = ListFrom.ints(1, 2, 3, 4, 5, 6, 7);
	assertEquals(list, t.preorder());

}


@Test
public void inorderTest() {
	
	Tree<Integer> t = new Tree<Integer>(1);
	t.left = new Tree<Integer>(2);
	t.left.left = new Tree<Integer>(3);
	t.left.right = new Tree<Integer>(4);
	t.right = new Tree<Integer>(5);
	t.right.left = new Tree<Integer>(6);
	t.right.right = new Tree<Integer>(7);
	
	List<Integer> list = ListFrom.ints(3, 2, 4, 1, 6, 5, 7);
	assertEquals(list, t.inorder());

}


@Test
public void postorderTest() {
	
	Tree<Integer> t = new Tree<Integer>(1);
	t.left = new Tree<Integer>(2);
	t.left.left = new Tree<Integer>(3);
	t.left.right = new Tree<Integer>(4);
	t.right = new Tree<Integer>(5);
	t.right.left = new Tree<Integer>(6);
	t.right.right = new Tree<Integer>(7);
	
	List<Integer> list = ListFrom.ints(3, 4, 2, 6, 7, 5, 1);
	assertEquals(list, t.postorder());

}


@Test
public void insertSortedTest() {
	
	Tree<Integer> t = new Tree<Integer>(5);
	assertFalse(t.insertSorted(5));
	assertTrue(t.insertSorted(3));
	assertTrue(t.insertSorted(2));
	assertTrue(t.insertSorted(4));
	assertTrue(t.insertSorted(8));
	assertTrue(t.insertSorted(6));
	assertTrue(t.insertSorted(9));
	
	List<Integer> list = ListFrom.ints(5, 3, 2, 4, 8, 6, 9);
	assertEquals(list, t.preorder());

	// Big yawn :)
	t = new Tree<Integer>(10);
	for (int i=9; i>=0;i--) { 
		t.insertSorted(i);
	}
	list = ListFrom.ints(10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
	assertEquals(list, t.preorder());
}

}
