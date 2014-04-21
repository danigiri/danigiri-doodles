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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;


public class WordBreakTest {

@Test(expected = NullPointerException.class)
public void WordBreakTestNull() {
	WordBreak.seg(null, new HashSet<String>());
}


@Test(expected = NullPointerException.class)
public void WordBreakTestNull2() {
	WordBreak.seg("", (Set<String>)null);
}


@Test
public void WordBreakBasicTest() {
	
	HashSet<String> dict = new HashSet<String>(5);
	dict.add("a");
	dict.add("ab");
	dict.add("xy");
	dict.add("zw");
	assertTrue(WordBreak.seg("aabxy", dict));
	assertTrue(WordBreak.seg("xy", dict));
	assertTrue(WordBreak.seg("xyaba", dict));
	assertTrue(WordBreak.seg("aaaaaaa", dict));
	assertFalse(WordBreak.seg("aaxaa", dict));
	
	dict = new HashSet<String>(5);
	dict.add("abc");
	dict.add("abd");
	assertTrue(WordBreak.seg("abcabdabd", dict));
	
}


@Test
public void buildKeyTreeTest() {
	
	List<String> keys = ListFrom.strings();
	Tree<String> t = WordBreak.buildKeyTree("", keys);
	assertEquals("", t.value);
	assertEquals(0, t.children.size());
	
	keys = ListFrom.strings("a", "b", "c");
	t = WordBreak.buildKeyTree("", keys);
	assertEquals("", t.value);
	assertEquals(3, t.children.size());
	assertTrue(t.hasChild("a"));
	assertTrue(t.hasChild("b"));
	assertTrue(t.hasChild("c"));

	keys = ListFrom.strings("a", "ab", "c");
	t = WordBreak.buildKeyTree("", keys);
	assertEquals("", t.value);
	assertEquals(2, t.children.size());
	assertTrue(t.hasChild("a"));
	assertTrue(t.getChild("a").hasChild("b"));
	assertTrue(t.hasChild("c"));

	keys = ListFrom.strings("a", "abc", "abd","e");
	t = WordBreak.buildKeyTree("", keys);
	assertEquals("", t.value);
	assertEquals(2, t.children.size());
	assertTrue(t.hasChild("a"));
	assertTrue(t.getChild("a").hasChild("b"));
	assertTrue(t.getChild("a").getChild("b").hasChild("c"));
	assertTrue(t.getChild("a").getChild("b").hasChild("d"));
	assertTrue(t.hasChild("e"));
	
	keys = ListFrom.strings(	"a", "ab", "ad", 
									"c", "cde", "cdf",
									"xy",
									"zz");
	t = WordBreak.buildKeyTree("", keys);
	assertEquals(4, t.children.size());
	assertTrue(t.hasChild("a"));
	assertTrue( t.getChild("a").hasChild("b"));
	assertTrue( t.getChild("a").hasChild("d"));
	assertTrue(t.hasChild("c"));
	assertTrue(t.getChild("c").hasChild("d"));
	assertTrue(t.getChild("c").getChild("d").hasChild("e"));
	assertTrue(t.getChild("c").getChild("d").hasChild("f"));
	assertTrue(t.hasChild("xy"));
	assertTrue(t.hasChild("zz"));
	
}


@Test
public void testMmatch() {
	
	assertNull(WordBreak.mmatch(null));
	
	List<String> l = ListFrom.strings("a");
	List<String> r = ListFrom.strings("a");
	assertEquals(r, WordBreak.mmatch(l));

	
	l = ListFrom.strings("a", "c");
	r = ListFrom.strings("a");
	assertEquals(r, WordBreak.mmatch(l));

	l = ListFrom.strings("a", "ab");
	r = ListFrom.strings("a", "b");
	assertEquals(r, WordBreak.mmatch(l));

	l = ListFrom.strings("ab", "ac");
	r = ListFrom.strings("a", "b", "c");
	assertEquals(r, WordBreak.mmatch(l));	
	
	l = ListFrom.strings("a", "ab", "ac");
	r = ListFrom.strings("a", "b", "c");
	assertEquals(r, WordBreak.mmatch(l));

	l = ListFrom.strings("a", "ab", "xc");
	r = ListFrom.strings("a", "b");
	assertEquals(r, WordBreak.mmatch(l));

	l = ListFrom.strings("ab", "abc", "abd");
	r = ListFrom.strings("ab", "c", "d");
	assertEquals(r, WordBreak.mmatch(l));	

}


@Test
public void testCommonPrefix() {
	
	assertEquals("", WordBreak.commonPrefix("", ""));
	assertEquals("", WordBreak.commonPrefix("a", ""));
	assertEquals("", WordBreak.commonPrefix("", "a"));
	
	assertEquals("a", WordBreak.commonPrefix("a", "a"));
	assertEquals("a", WordBreak.commonPrefix("a", "ab"));
	assertEquals("a", WordBreak.commonPrefix("ab", "a"));

	assertEquals("ab", WordBreak.commonPrefix("ab", "ab"));
	assertEquals("ab", WordBreak.commonPrefix("abc", "ab"));
	assertEquals("ab", WordBreak.commonPrefix("ab", "abc"));

	assertEquals("ab", WordBreak.commonPrefix("abx", "abc"));
	assertEquals("ab", WordBreak.commonPrefix("abc", "abx"));

	assertEquals("", WordBreak.commonPrefix("ab", "xy"));
	assertEquals("", WordBreak.commonPrefix("ab", "xab"));
	
}

}
