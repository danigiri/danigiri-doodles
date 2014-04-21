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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

import javax.sound.midi.SysexMessage;

import org.junit.Ignore;
import org.junit.Test;

public class SorterTest {

@Test(expected = NullPointerException.class)
public void mergeSortedListsTestNull() {
	Sorter.mergeSortedLists(null, null);
}


@Test(expected = NullPointerException.class)
public void mergeSortedListsTestNull2() {

	List<Integer> a = ListFrom.ints(1);	
	Sorter.mergeSortedLists(a, null);

}


@Test(expected = NullPointerException.class)
public void mergeSortedListsTestNull3() {
	
	List<Integer> a = ListFrom.ints(1);	
	Sorter.mergeSortedLists(null, a);

}


@Test
public void mergeSortedListsTest() {

	List<Integer> a = ListFrom.ints(1,2,3);
	List<Integer> b = ListFrom.ints(4,5,6);

	List<Integer> c = ListFrom.ints(1,2,3,4,5,6);
	assertEquals(c, Sorter.mergeSortedLists(a, b));
 	assertEquals(c, Sorter.mergeSortedLists(b, a));

	a = ListFrom.ints(1,6);
	b = ListFrom.ints(2,3,4,5);
	assertEquals(c, Sorter.mergeSortedLists(a, b));

	a = ListFrom.ints(1,2,3);
	c = ListFrom.ints(1,1,2,2,3,3);
	assertEquals(c, Sorter.mergeSortedLists(a, a));
	
	a = ListFrom.ints(1, 4, 5, 8, 9);
	b = ListFrom.ints(2, 4, 5, 6, 7);
	c = ListFrom.ints(1, 2, 4, 4, 5, 5, 6, 7, 8, 9);
	assertEquals(c, Sorter.mergeSortedLists(a, b));
	
}

@Test
public void sortListTest() {
	
	assertNull(Sorter.sortList(null));

	List<Integer> a = ListFrom.ints(3, 2, 1);
	List<Integer> c = ListFrom.ints(1, 2, 3);
	assertEquals(c, Sorter.sortList(a));

	a = ListFrom.ints(1, 2, 3);
	c = ListFrom.ints(1, 2, 3);
	assertEquals(c, Sorter.sortList(a));

	a = ListFrom.ints(3, 2, 1, 4, 6, 5);
	c = ListFrom.ints(1, 2, 3, 4, 5, 6);
	assertEquals(c, Sorter.sortList(a));
	
	a = ListFrom.ints(1, 1, 10, 2, 2, 11);
	c = ListFrom.ints(1, 1, 2, 2, 10, 11);
	assertEquals(c, Sorter.sortList(a));
	
	a = ListFrom.ints(	844, 860, 791, 613, 963, 365, 374, 272, 575, 684, 883, 
						387, 64, 349, 407, 37, 864, 611, 189, 646, 564, 888, 
						422, 90, 635, 874, 909, 745, 373, 403, 586, 637, 692, 
						938, 606, 351, 718, 297, 857, 132, 337, 998, 164, 809, 
						109, 169, 454, 170, 144, 752, 805, 255, 262, 358, 877, 
						129, 80, 971, 812, 357, 440, 125, 872, 41, 414, 973, 
						201, 828, 626, 601, 219, 112, 724, 764, 1, 350, 253, 54, 
						298, 216, 706, 289, 307, 865, 15, 115, 324, 188, 672, 
						319, 944, 370, 160, 11, 861, 190, 999, 57, 277, 823, 562, 
						244, 871, 310, 222, 936, 578, 520, 566, 354, 343, 802, 
						776, 730, 399, 868, 526, 923, 294, 518, 48, 686, 154, 
						313, 339, 850, 88, 478, 881, 763, 700, 242, 972, 662, 
						933, 591, 589, 937, 940, 368, 475, 898, 830, 195, 631, 
						598, 889, 532, 49, 202, 681, 184, 2, 985, 568, 282, 746, 
						423, 663, 361, 381, 338, 991, 574, 839, 259, 27, 428, 
						573, 274, 957, 924, 633, 508, 411, 743, 20, 908, 451, 
						682, 462, 217, 12, 113, 404, 840, 699, 185, 744, 980, 
						366, 660, 516, 885, 207, 685, 491, 705, 735, 903, 23, 
						634, 925, 570, 895, 21, 94, 401, 834, 182, 92, 408, 935, 
						316, 76, 320, 482, 17, 490, 93, 996, 477, 719, 36, 410, 
						318, 107, 843, 870, 698, 473, 47, 856, 95, 415, 657, 
						118, 50, 299, 979, 842, 158, 557, 588, 146, 717, 964, 
						226, 913, 430, 398, 315, 583, 174, 709, 45, 101, 149, 
						554, 523, 224, 910, 525, 656, 156, 810, 409, 638, 59, 
						571, 470, 540, 640, 487, 792, 579, 191, 333, 85, 251, 
						134, 775, 628, 498, 767, 103, 55, 239, 62, 51, 896, 
						838, 486, 234, 558, 33, 286, 533, 790, 208, 715, 449, 
						352, 894, 212, 196, 380, 186, 689, 406, 464, 653, 79, 
						680, 825, 130, 237, 137, 547, 986, 450, 951, 553, 484, 
						954, 447, 593, 328, 651, 143, 899, 168, 675, 731, 280, 
						436, 550, 997, 688, 301, 804, 500, 667, 905, 897, 131, 
						161, 983, 295, 187, 306, 920, 644, 602, 629, 610, 405, 
						901, 150, 934, 309, 609, 512, 488, 893, 172, 426, 849, 
						690, 643, 238, 106, 254, 528, 517, 624, 232, 78, 733, 
						402, 81, 288, 457, 116, 26, 252, 612, 140, 72, 736, 100, 
						778, 514, 340, 884, 376, 278, 677, 529, 330, 678, 283, 
						183, 621, 495, 534, 989, 918, 371, 741, 173, 393, 835, 
						551, 138, 341, 364, 515, 841, 524, 89, 762, 916, 650, 
						539, 504, 347, 425, 945, 580, 619, 795, 396, 902, 214, 
						694, 673, 755, 620, 231, 915, 608, 453, 769, 604, 943, 
						614, 459, 77, 808, 177, 912, 308, 223, 783, 649, 4, 555, 
						236, 293, 104, 761, 265, 356, 563, 907, 377, 687, 995, 
						271, 427, 845, 851, 786, 463, 531, 148, 379, 22, 740, 
						82, 948, 287, 136, 976, 931, 696, 852, 433, 479, 260, 
						114, 151, 771, 73, 878, 74, 264, 432, 28);	
	long t = System.currentTimeMillis();
	System.err.println("----------------------------------");
	Sorter.sortList(a);
	System.err.println("t="+(System.currentTimeMillis()-t));
	System.err.println("----------------------------------");

}


@Test
public void bTreeTest() {
	assertNull(Sorter.bTree(null));

	List<String> a = ListFrom.strings("hot", "dot", "dog", "lot", "log");	
	System.err.println(Sorter.bTree(a));

	List<Integer> b = ListFrom.ints(6,20,3,4,10,12);
	System.err.println(Sorter.bTree(b));

	a = ListFrom.strings("Zot", "Zog");	
	System.err.println(Sorter.bTree(a));

	
}

}
