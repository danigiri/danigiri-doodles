// TREE . JAVA

package cat.calidos.doodles;

import java.util.HashMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import cat.calidos.doodles.Stack;


public class Tree<T extends Comparable<? super T>> {

public T						data;
protected Map<String, Tree<T>>	children;
protected Tree<T>				left;
protected Tree<T>				right;
public int						maxKeyLength;

public Tree(T d) {

	data = d;
	clearChildren();
	maxKeyLength = 0;

}


public void addChild(String k, Tree<T> c) {

	children.put(k, c);
	if (k.length() > maxKeyLength) {
		maxKeyLength = k.length();
	}

}


public void addAll(Map<String, Tree<T>> c) {
	c.keySet().stream().forEach(k -> {
		addChild(k, c.get(k));
	});
}


public Tree<T> addLeft(T d) {
	return addLeft(new Tree<T>(d));
}


public Tree<T> addLeft(Tree<T> t) {
	this.left = t;
	return t;
}

public Tree<T> addRight(T d) {
	return addRight(new Tree<T>(d));
}

public Tree<T> addRight(Tree<T> t) {
	this.right = t;
	return t;
}


public boolean hasChild(String k) {
	return getChild(k) != null;
}


public boolean hasLeft() {
	return left != null;
}


public boolean hasRight() {
	return right != null;
}


public Tree<T> getChild(String k) {
	return children.get(k);
}


public Tree<T> getLeft() {
	return left;
}


public Tree<T> getRight() {
	return right;
}


public int childrenCount() {
	int c = children.size();
	if (hasLeft()) {
		c++;
	}
	;
	if (hasRight()) {
		c++;
	}
	;
	return c;
}


public Map<String, Tree<T>> clearChildren() {
	Map<String, Tree<T>> c = children;
	children = new HashMap<String, Tree<T>>();
	return c;
}


public Tree<T> clearChild(String k) {
	return children.remove(k);
}


public List<T> preorder() {
	return preorder_(new ArrayList<T>());
}


private List<T> preorder_(List<T> l) {
	l.add(data);
	if (left != null) {
		l = left.preorder_(l);
	}
	if (right != null) {
		l = right.preorder_(l);
	}
	return l;
}


public List<T> inorder() {
	return inorder_(new ArrayList<T>());
}


private List<T> inorder_(List<T> l) {
	if (left != null) {
		l = left.inorder_(l);
	}
	l.add(data);
	if (right != null) {
		l = right.inorder_(l);
	}
	return l;
}


public List<T> postorder() {
	return postorder_(new ArrayList<T>());
}


private List<T> postorder_(List<T> l) {
	if (left != null) {
		l = left.postorder_(l);
	}
	if (right != null) {
		l = right.postorder_(l);
	}
	l.add(data);
	return l;
}


public boolean insertSorted(T d) { // O(logN)

	if (data == null) {
		data = d;
		return true;
	}
	int compare = d.compareTo(data);
	// base case
	if (compare == 0) {
		return false;
	}
	if (compare < 0) {
		if (left == null) {
			// base case
			left = new Tree(d);
			return true;
		} else {
			return left.insertSorted(d); // induction, left tree is smaller
		}
	} else {
		if (right == null) {
			right = new Tree(d);
			return true;
		} else {
			return right.insertSorted(d); // induction, right tree is smaller
		}
	}

}


// 4.2 minimal tree, given a sorted increasing order array with unique integer elements, write an
// algorithm to create a minimal height binary search tree

// [1, 2, 3]
// [2]
// [1], [3]
//


public static <T extends Comparable<? super T>> Tree<T> balancedSearchTree(List<T> a) {

	if (a == null) {
		throw new NullPointerException("bad parameter");
	}
	if (a.isEmpty()) {
		return null;
	}

	return balancedSearchTreeRange(a, 0, a.size());
}


private static <T extends Comparable<? super T>> Tree<T> balancedSearchTreeRange(List<T> a, int start, int end) {

	int size = end - start;
	if (size == 1) {

		return new Tree<T>(a.get(start));

	}
	if (size == 2) {
		Tree<T> root = new Tree<T>(a.get(end - start - 1));
		root.left = balancedSearchTreeRange(a, end - start - 2, end - start - 1);

		return root;

	}
	int middle = start + (size / 2);
	Tree<T> root = new Tree<T>(a.get(middle));
	root.left = balancedSearchTreeRange(a, start, middle);
	root.right = balancedSearchTreeRange(a, middle + 1, end);

	return root;

}

// 4.3 list of depths: write an algorithm to create a linked list of all the nodes of each depth
// we have a list of all depths and each one has a list
// add root to the pending list
// set height = 0
// have a pending list
// while pending is not empty
// for each node,
// we add the children to the nextHeight list
// we add the node to the current height linked list
// we add the list to the output
// pending = nextHeight
// increment height
// return map


public static <T extends Comparable<? super T>> List<LinkedList<T>> nodesInDepth(Tree<T> t) {

	List<LinkedList<T>> lists = new ArrayList<LinkedList<T>>();
	List<Tree<T>> pending = new ArrayList<Tree<T>>();
	pending.add(t);
	while (!pending.isEmpty()) {

		List<Tree<T>> nextPending = new ArrayList<Tree<T>>();
		LinkedList<T> currentDepth = null;
		LinkedList<T> currentDepthHead = null;
		for (Tree<T> node : pending) {
			if (node.left != null) {
				nextPending.add(node.left);
			}
			if (node.right != null) {
				nextPending.add(node.right);
			}
			LinkedList<T> currentNode = new LinkedList<T>(node.data);
			if (currentDepthHead == null) {
				currentDepth = currentNode;
				currentDepthHead = currentNode;
			} else {
				currentDepthHead.next = currentNode;
				currentDepthHead = currentNode;
			}
		}
		;

		pending = nextPending;
		lists.add(currentDepth);

	}

	return lists;

}


// 4.4 check balanced, implement a function to check if a binary tree is balanced
// a balanced tree is defined as a tree that both subtrees are equal or differ by one in height


// let's see
// single node: →
// left and/or right
// calculate depth for each
// get the max of the two, as we could have a degenerate that is still balanced
// increment one
// return
// the root call is special, checks the two heights and returns true or false


public static <T extends Comparable<? super T>> boolean isBalanced(Tree<T> t) {

	int leftHeight = treeHeight(t.left);
	int rightHeight = treeHeight(t.right);

	return Math.abs(leftHeight - rightHeight) <= 1;

}


private static <T extends Comparable<? super T>> int treeHeight(Tree<T> t) {

	// base cases
	if (t == null) {
		return 0;
	}
	// recursive case
	int leftHeight = treeHeight(t.left);
	int rightHeight = treeHeight(t.right);
	int height = (leftHeight > rightHeight) ? leftHeight : rightHeight;

	return height + 1;

}


// this works but the exercise mentions any node, we can make this more straightforward
// we calculate the height and return -1 if we are unbalanced
public static <T extends Comparable<? super T>> boolean isBalancedStrict(Tree<T> t) {
	return isBalancedStrictRec(t) != -1;
}


// calculate height of left and right
// if any of them is -1, return -1
// otherwise, calculate difference between them, if diff is<=1, return the max of
// the two, otherwise return -1
private static <T extends Comparable<? super T>> int isBalancedStrictRec(Tree<T> t) {

	// base cases
	if (t == null) {
		return 0;
	}
	int leftHeight = treeHeight(t.left);
	if (leftHeight == -1) {
		return -1;
	}
	int rightHeight = treeHeight(t.right);
	if (rightHeight == -1) {
		return -1;
	}
	int diff = Math.abs(leftHeight - rightHeight);
	if (diff > 1) {
		return -1;
	}

	return Math.max(leftHeight, rightHeight) + 1;

}


@Override
public String toString() {
	return toString(new StringBuffer(), new StringBuffer());
}


protected String toString(StringBuffer s, StringBuffer tab) {

	tab.append(" ");
	s.append(tab);
	s.append("[");
	s.append(data);

	if (left == null && right == null) {
		if (children != null && !children.isEmpty()) {
			s.append(",\n");
			for (Iterator<String> keys = children.keySet().iterator(); keys.hasNext();) {
				String k = keys.next();
				Tree<T> t = children.get(k);
				if (t != null) {
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
	} else {
		s.append(",\n");
		if (left != null) {
			s.append("l:" + left.toString(new StringBuffer(), new StringBuffer(tab)));
		}
		if (right != null) {
			s.append("r:" + right.toString(new StringBuffer(), new StringBuffer(tab)));
		}
	}

	s.append(tab);
	s.append("]\n");

	return s.toString();

}


// FIXME: this doesn't seem to work for complex trees (see string prefixes test)
@Override
public boolean equals(Object o) {

	if (o == null) {
		return false;
	}
	if (!(o instanceof Tree<?>)) {
		return false;
	}
	@SuppressWarnings("unchecked")
	final Tree<T> t = (Tree<T>) o;
	boolean equals = this.childrenCount() == t.childrenCount()
			&& (this.data == null && t.data == null || this.data.equals(t.data));
	Iterator<String> keys = this.children.keySet().iterator();
	while (equals && keys.hasNext()) {
		String childKey = keys.next();
		Tree<T> child = getChild(childKey);
		Tree<T> childT = t.getChild(childKey);
		equals = child.equals(childT);
	}
	if (equals && hasLeft()) {
		if (!t.hasLeft()) {
			return false;
		}
		equals = getLeft().equals(t.getLeft());
	}
	if (equals && hasRight()) {
		if (!t.hasRight()) {
			return false;
		}
		equals = getRight().equals(t.getRight());
	}
	return equals;

}


@Override
public int hashCode() {
	return this.toString().hashCode();
}


public static <T extends Comparable<? super T>> Optional<Tree<T>> commonAncestor(Tree<T> t, Tree<T> a, Tree<T> b) {

	if (t == null) {
		return Optional.empty();
	}
	if (t == a) {
		return Optional.of(a);
	}
	if (t == b) {
		return Optional.of(b);
	}
	var leftS = commonAncestor(t.left, a, b);
	if (leftS.isPresent()) { // one found in the left
		if (leftS.get() != a && leftS.get() != b) { // solved
			return leftS;
		}
		if ((leftS.get() == a && findNode(t.right, b)) || (leftS.get() == b && findNode(t.right, a))) {
			return Optional.of(t); // this is the common ancestor
		}
		return leftS; // common ancestor not found yet
	}
	var rightS = commonAncestor(t.right, a, b);
	if (rightS.isPresent()) { // one found in the right
		if (rightS.get() != a && rightS.get() != b) { // solved
			return rightS;
		}
		if ((rightS.get() == a && findNode(t.left, b)) || (rightS.get() == b && findNode(t.left, a))) {
			return Optional.of(t); // this is the common ancestor
		}
		return rightS; // common ancestor not found yet
	}
	return Optional.empty();

}


private static <T extends Comparable<? super T>> boolean findNode(Tree<T> t, Tree<T> n) {
	if (t == null) {
		return false;
	}
	if (n == t) {
		return true;
	}
	return findNode(t.left, n) || findNode(t.right, n);
}


// subtree: tree a is much bigger than tree b, get a function that returns true if tree b
// can be found in tree a
public static <T extends Comparable<? super T>> boolean subtree(Tree<T> t, Tree<T> s) {
	return subtree2(t, s, s);
}


public static <T extends Comparable<? super T>> boolean subtree2(Tree<T> t, Tree<T> s, Tree<T> top) {
	// base cases
	if (t == null && s == null) {
		return true;
	}
	if (t == null && s != null) {
		return false;
	}
	if (t != null && s == null) {
		return subtree2(t.left, top, top) || subtree2(t.right, top, top);
	}
	// neither of the trees are null
	if (!t.data.equals(s.data)) {
		return subtree2(t.left, top, top) || subtree2(t.right, top, top); // induction
	}
	// neither are null, and current node data is equal
	boolean isSubtree = subtree2(t.left, s.left, top) && subtree2(t.right, s.right, top); // induction
	if (isSubtree) {
		return true;
	}
	// no subtree from current node, but some of the children may be entire subtrees
	return subtree2(t.left, top, top) || subtree2(t.right, top, top); // induction
}


// do a post-order traversal of a binary tree with a singly linked list


public static <T extends Comparable<? super T>> LinkedList<T> postorder2(Tree<T> t) {
	if (t == null) {
		return null;
	}
	return postorder2WithList(t, null, null).left;
}


public static <T extends Comparable<? super T>> Pair<LinkedList<T>, LinkedList<T>> postorder2WithList(Tree<T> t,
		LinkedList<T> head, LinkedList<T> tail) {
	if (t.left != null) {
		Pair<LinkedList<T>, LinkedList<T>> p = postorder2WithList(t.left, head, tail);
		head = p.left;
		tail = p.right;
	}
	if (t.right != null) {
		Pair<LinkedList<T>, LinkedList<T>> p = postorder2WithList(t.right, head, tail);
		head = p.left;
		tail = p.right;
	}
	var c = new LinkedList<T>(t.data);
	if (head == null) {
		head = c;
		tail = c;
	} else {
		tail.next = c;
		tail = c;
	}
	return new Pair<LinkedList<T>, LinkedList<T>>(head, tail);
}


/*
 * non recursive version of post-order with a single linked list, destroys the source tree
 * 
 * a / \ b c
 * 
 * we need a stack (dlinkedlist here)
 * 
 * a / \ b c —---------------
 * 
 * take the node remove left from the top node and push left on top
 * 
 * b/ – a \c —---------------
 * 
 * a \c —--------------- [b]
 * 
 * …
 * 
 * \c – a —--------------- [b, c, a]
 * 
 */


public static <T extends Comparable<? super T>> LinkedList<T> postorder3(Tree<T> t) {
	var s = new DLinkedList<Tree<T>>(t);
	return Tree.postorder3WithStack(s, null).left;
}


public static <T extends Comparable<? super T>> Pair<LinkedList<T>, LinkedList<T>> postorder3WithStack(
		DLinkedList<Tree<T>> s, Pair<LinkedList<T>, LinkedList<T>> l) {
	while (s != null) {
		Tree<T> top = s.data; // peek()
		if (top.left == null && top.right == null) { // base case
			var tail = new LinkedList<T>(top.data);
			if (l != null) {
				l.right.next = tail;
			} else {
				l = new Pair<LinkedList<T>, LinkedList<T>>(tail, tail);
			}
			l.right = tail;
			s = s.prev; // pop()
			if (s != null) {
				s.next = null;
			}
		} else if (top.left != null) { // recursive cases
			var newTop = new DLinkedList<Tree<T>>(top.left);
			newTop.prev = s; // push()
			s.next = newTop;
			s = newTop;
			top.left = null;
		} else {
			var newTop = new DLinkedList<Tree<T>>(top.right);
			newTop.prev = s; // push()
			s.next = newTop;
			s = newTop;
			top.right = null;
		}
	}
	return l;
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
