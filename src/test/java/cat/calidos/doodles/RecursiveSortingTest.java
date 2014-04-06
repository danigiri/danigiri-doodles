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

public class RecursiveSortingTest {

@Test
public void mergeSortedListsTest() {
	
	assertNull(RecursiveSorting.mergeSortedLists(null, null));
	List<Integer> a = Arrays.asList(1,2,3);
	
	assertEquals(a, RecursiveSorting.mergeSortedLists(a, null));
	assertEquals(a, RecursiveSorting.mergeSortedLists(null, a));
	
	List<Integer> b = Arrays.asList(4,5,6);
	
	List<Integer> c = Arrays.asList(1,2,3,4,5,6);
	assertEquals(c, RecursiveSorting.mergeSortedLists(a, b));
	assertEquals(c, RecursiveSorting.mergeSortedLists(b, a));

	a = new ArrayList<Integer>(Arrays.asList(1,6));
	b = new ArrayList<Integer>(Arrays.asList(2,3,4,5));
	assertEquals(c, RecursiveSorting.mergeSortedLists(a, b));

	a = Arrays.asList(1,2,3);
	c = Arrays.asList(1,1,2,2,3,3);
	assertEquals(c, RecursiveSorting.mergeSortedLists(a, a));

}


@Test
public void sortListTest() {
	
//	assertNull(RecursiveSorting.sortList(null));
//
	List<Integer> a = Arrays.asList(3, 2, 1);
	List<Integer> c = Arrays.asList(1, 2, 3);
//	assertEquals(c, RecursiveSorting.sortList(a));
//
//	a = Arrays.asList(1, 2, 3);
//	c = Arrays.asList(1, 2, 3);
//	assertEquals(c, RecursiveSorting.sortList(a));
//
//	a = Arrays.asList(3, 2, 1, 4, 6, 5);
//	c = Arrays.asList(1, 2, 3, 4, 5, 6);
//	assertEquals(c, RecursiveSorting.sortList(a));
//	
	a = Arrays.asList(1, 1, 10, 2, 2, 11);
	c = Arrays.asList(1, 1, 2, 2, 10, 11);
	assertEquals(c, RecursiveSorting.sortList(a));
	
}

}
