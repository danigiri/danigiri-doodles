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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import org.junit.Ignore;
import org.junit.Test;

public class SorterTest {

@Test(expected = NullPointerException.class)
public void mergeSortedListsTestNull() {
	Sorter.mergeSortedLists(null, null);
}


@Test(expected = NullPointerException.class)
public void mergeSortedListsTestNull2() {

	List<Object> a = SorterTest.testList(1);	
	Sorter.mergeSortedLists(a, null);

}


@Test(expected = NullPointerException.class)
public void mergeSortedListsTestNull3() {
	
	List<Object> a = SorterTest.testList(1);	
	Sorter.mergeSortedLists(null, a);

}


@Test
public void mergeSortedListsTest() {

	List<Object> a = SorterTest.testList(1,2,3);
	List<Object> b = SorterTest.testList(4,5,6);

	List<Object> c = SorterTest.testList(1,2,3,4,5,6);
	assertEquals(c, Sorter.mergeSortedLists(a, b));
	assertEquals(c, Sorter.mergeSortedLists(b, a));

	a = SorterTest.testList(1,6);
	b = SorterTest.testList(2,3,4,5);
	assertEquals(c, Sorter.mergeSortedLists(a, b));

	a = SorterTest.testList(1,2,3);
	c = SorterTest.testList(1,1,2,2,3,3);
	assertEquals(c, Sorter.mergeSortedLists(a, a));
	
}


@Test
public void sortListTest() {
	
	assertNull(Sorter.sortList(null));

	List<Object> a = SorterTest.testList(3, 2, 1);
	List<Object> c = SorterTest.testList(1, 2, 3);
	assertEquals(c, Sorter.sortList(a));

	a = SorterTest.testList(1, 2, 3);
	c = SorterTest.testList(1, 2, 3);
	assertEquals(c, Sorter.sortList(a));

	a = SorterTest.testList(3, 2, 1, 4, 6, 5);
	c = SorterTest.testList(1, 2, 3, 4, 5, 6);
	assertEquals(c, Sorter.sortList(a));
	
	a = SorterTest.testList(1, 1, 10, 2, 2, 11);
	c = SorterTest.testList(1, 1, 2, 2, 10, 11);
	assertEquals(c, Sorter.sortList(a));
	
}


public static List<Object> testList(int... v) {
	
	List<Object> a = new ArrayList<Object>(3);
	for (int i : v) {
		a.add(Integer.valueOf(i));
	}
	return a;

}

}
