// TREE TEST . JAVA
package cat.calidos.doodles;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cat.calidos.doodles.builders.ArrayFrom;
import cat.calidos.doodles.builders.ListFrom;

public class TreeTest {


@Test
public void preorderTest() {

	var t = new Tree<Integer>(1);
	t.addLeft(new Tree<Integer>(2));
	t.getLeft().addLeft(new Tree<Integer>(3));
	t.getLeft().addRight(new Tree<Integer>(4));

	t.addRight(new Tree<Integer>(5));
	t.getRight().addLeft(new Tree<Integer>(6));
	t.getRight().addRight(new Tree<Integer>(7));

	List<Integer> list = ListFrom.ints(1, 2, 3, 4, 5, 6, 7);
	assertEquals(list, t.preorder());

}


@Test
public void inorderTest() {

	var t = new Tree<Integer>(1);
	t.addLeft(new Tree<Integer>(2));
	t.getLeft().addLeft(new Tree<Integer>(3));
	t.getLeft().addRight(new Tree<Integer>(4));

	t.addRight(new Tree<Integer>(5));
	t.getRight().addLeft(new Tree<Integer>(6));
	t.getRight().addRight(new Tree<Integer>(7));

	List<Integer> list = ListFrom.ints(3, 2, 4, 1, 6, 5, 7);
	assertEquals(list, t.inorder());

}


@Test
public void postorderTest() {

	var t = new Tree<Integer>(1);
	t.addLeft(new Tree<Integer>(2));
	t.getLeft().addLeft(new Tree<Integer>(3));
	t.getLeft().addRight(new Tree<Integer>(4));

	t.addRight(new Tree<Integer>(5));
	t.getRight().addLeft(new Tree<Integer>(6));
	t.getRight().addRight(new Tree<Integer>(7));

	List<Integer> list = ListFrom.ints(3, 4, 2, 6, 7, 5, 1);
	assertEquals(list, t.postorder());

}


@Test
public void insertSortedTest() {

	var t = new Tree<Integer>(5);
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
	for (int i = 9; i >= 0; i--) {
		t.insertSorted(i);
	}
	list = ListFrom.ints(10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
	assertEquals(list, t.preorder());
}


@Test
public void equalsTest() {

	var t = new Tree<Integer>(1);
	t.left = new Tree<Integer>(2);
	t.left.left = new Tree<Integer>(3);
	t.left.right = new Tree<Integer>(4);
	t.right = new Tree<Integer>(5);
	t.right.left = new Tree<Integer>(6);
	t.right.right = new Tree<Integer>(7);

	assertFalse(t.equals(null));

	var t1 = new Tree<Integer>(1);

	assertFalse(t.equals(t1));

	t1.left = new Tree<Integer>(2);
	t1.left.left = new Tree<Integer>(3);
	t1.left.right = new Tree<Integer>(4);
	t1.right = new Tree<Integer>(5);
	t1.right.left = new Tree<Integer>(6);

	assertFalse(t.equals(t1));

	t1.right.right = new Tree<Integer>(7);

	assertTrue(t.equals(t1));

	t = new Tree<Integer>(1);
	t.addChild("2", new Tree<Integer>(2));
	t.addChild("3", new Tree<Integer>(2));

	t1 = new Tree<Integer>(1);
	assertFalse(t.equals(t1));

	t1.addChild("2", new Tree<Integer>(2));
	assertFalse(t.equals(t1));

	t1.addChild("3", new Tree<Integer>(2));
	assertTrue(t.equals(t1));

}


@Test
@DisplayName("Balanced search tree from sorted array")
public void balancedSearchTreeTest() {

	assertNull(Tree.balancedSearchTree(new ArrayList<Integer>()));
	assertThrows(NullPointerException.class, () -> Tree.balancedSearchTree(null));

	List<Integer> a = ArrayFrom.ints(1);
	var expected = new Tree<Integer>(1);
	assertEquals(expected, Tree.balancedSearchTree(a));

	a = ArrayFrom.ints(1, 2);
	expected = new Tree<Integer>(2);
	expected.left = new Tree<Integer>(1);
	assertEquals(expected, Tree.balancedSearchTree(a));

	a = ArrayFrom.ints(1, 2, 3);
	expected = new Tree<Integer>(2);
	expected.left = new Tree<Integer>(1);
	expected.right = new Tree<Integer>(3);
	assertEquals(expected, Tree.balancedSearchTree(a));

	a = ArrayFrom.ints(1, 2, 3, 4, 5, 6, 7);
	a = ArrayFrom.ints(1, 2, 3, 4, 5, 6, 7);
	expected = new Tree<Integer>(4);
	expected.left = new Tree<Integer>(2);
	expected.left.left = new Tree<Integer>(1);
	expected.left.right = new Tree<Integer>(3);
	expected.right = new Tree<Integer>(6);
	expected.right.left = new Tree<Integer>(5);
	expected.right.right = new Tree<Integer>(7);

	assertEquals(expected, Tree.balancedSearchTree(a));

}


@Test
@DisplayName("List of nodes for each depth")
public void nodesInDepthTest() {

	var t = new Tree<Integer>(4);
	t.left = new Tree<Integer>(2);
	t.left.left = new Tree<Integer>(1);
	t.left.right = new Tree<Integer>(3);
	t.right = new Tree<Integer>(6);
	t.right.left = new Tree<Integer>(5);
	t.right.right = new Tree<Integer>(7);

	List<LinkedList<Integer>> expected = new ArrayList<LinkedList<Integer>>(3);
	expected.add(new LinkedList<Integer>(4));
	var level2 = new LinkedList<Integer>(2);
	level2.next = new LinkedList<Integer>(6);
	expected.add(level2);
	var level3 = new LinkedList<Integer>(1);
	level3.next = new LinkedList<Integer>(3);
	level3.next.next = new LinkedList<Integer>(5);
	level3.next.next.next = new LinkedList<Integer>(7);
	expected.add(level3);

	assertEquals(expected, Tree.nodesInDepth(t));

}


@Test
@DisplayName("Is the tree balanced")
public void isBalancedTest() {

	var t = new Tree<Integer>(4);
	assertTrue(Tree.isBalanced(t));

	t.left = new Tree<Integer>(2);
	assertTrue(Tree.isBalanced(t));

	t.left.left = new Tree<Integer>(1);
	t.left.right = new Tree<Integer>(3);
	assertFalse(Tree.isBalanced(t));

	t.right = new Tree<Integer>(6);
	assertTrue(Tree.isBalanced(t));

}


@Test
@DisplayName("Is the tree strictly balanced")
public void isBalancedStrictTest() {

	var t = new Tree<Integer>(4);
	assertTrue(Tree.isBalancedStrict(t));

	t.left = new Tree<Integer>(2);
	assertTrue(Tree.isBalancedStrict(t));

	t.left.left = new Tree<Integer>(1);
	t.left.right = new Tree<Integer>(3);
	assertFalse(Tree.isBalancedStrict(t));

	t.right = new Tree<Integer>(6);
	assertTrue(Tree.isBalancedStrict(t));

	t = new Tree<Integer>(4);
	t.left = new Tree<Integer>(2);
	t.left.left = new Tree<Integer>(1);
	t.left.right = new Tree<Integer>(3);
	t.right = new Tree<Integer>(6);
	t.right.left = new Tree<Integer>(5);
	t.right.right = new Tree<Integer>(7);
	assertTrue(Tree.isBalancedStrict(t));

}


@Test
@DisplayName("Find common ancestor test")
public void commonAncestorTest() {

	var t = new Tree<Integer>(4);
	t.left = new Tree<Integer>(2);
	t.left.left = new Tree<Integer>(1);
	t.left.right = new Tree<Integer>(3);
	t.right = new Tree<Integer>(6);
	t.right.left = new Tree<Integer>(5);
	t.right.right = new Tree<Integer>(7);
	assertTrue(Tree.isBalancedStrict(t));

	var a = Tree.commonAncestor(t, t.right.left, t.right.right);
	assertAll("check common ancestor", () -> assertTrue(a.isPresent()), () -> assertEquals(t.right, a.get()));
	var b = Tree.commonAncestor(t, t.left.left, t.right.right);
	assertAll("check common ancestor", () -> assertTrue(b.isPresent()), () -> assertEquals(t, b.get()));
}


@Test
@DisplayName("Subtree test")
public void subtreeTest() {
	var t = new Tree<Integer>(1);
	t.left = new Tree<Integer>(2);

	var s = new Tree<Integer>(1);
	assertFalse(Tree.subtree(t, s));

	t.left.left = new Tree<Integer>(3);
	t.left.right = new Tree<Integer>(4);
	t.right = new Tree<Integer>(5);
	t.right.left = new Tree<Integer>(6);
	t.right.right = new Tree<Integer>(7);

	s = new Tree<Integer>(2);
	s.left = new Tree<Integer>(3);
	s.right = new Tree<Integer>(4);
	assertTrue(Tree.subtree(t, s));

	s = new Tree<Integer>(2);
	s.left = new Tree<Integer>(3);
	s.right = new Tree<Integer>(10);
	assertFalse(Tree.subtree(t, s));

	t.right.right.right = new Tree<Integer>(10);
	s = new Tree<Integer>(10);
	assertTrue(Tree.subtree(t, s));
}

}

/**
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
