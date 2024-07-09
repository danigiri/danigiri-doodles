// WORDS TEST . JAVA

package cat.calidos.doodles;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;

import cat.calidos.doodles.builders.ListFrom;


public class WordsTest {

@Test(expected = NullPointerException.class)
public void WordBreakTestNull() {
	Words.seg(null, new HashSet<String>());
}


@Test(expected = NullPointerException.class)
public void WordBreakTestNull2() {
	Words.seg("", (Set<String>)null);
}


@Test
public void WordBreakBasicTest() {
	
	HashSet<String> dict = new HashSet<String>(5);
	dict.add("a");
	dict.add("ab");
	dict.add("xy");
	dict.add("zw");
	assertTrue(Words.seg("aabxy", dict));
	assertTrue(Words.seg("xy", dict));
	assertTrue(Words.seg("xyaba", dict));
	assertTrue(Words.seg("aaaaaaa", dict));
	assertFalse(Words.seg("aaxaa", dict));
	
	dict = new HashSet<String>(5);
	dict.add("abc");
	dict.add("abd");
	assertTrue(Words.seg("abcabdabd", dict));
	
}


@Test
public void buildKeyTreeTest() {

	List<String> keys = ListFrom.strings();
	Tree<String> t = Words.buildKeyTree("", keys);
	assertEquals("", t.data);
	assertEquals(0, t.children.size());

	keys = ListFrom.strings("a", "b", "c");
	t = Words.buildKeyTree("", keys);
	assertEquals("", t.data);
	assertEquals(3, t.children.size());
	assertTrue(t.hasChild("a"));
	assertTrue(t.hasChild("b"));
	assertTrue(t.hasChild("c"));

	keys = ListFrom.strings("a", "ab", "c");
	t = Words.buildKeyTree("", keys);
	assertEquals("", t.data);
	assertEquals(2, t.children.size());
	assertTrue(t.hasChild("a"));
	assertTrue(t.getChild("a").hasChild("b"));
	assertTrue(t.hasChild("c"));

	keys = ListFrom.strings("a", "abc", "abd","e");
	t = Words.buildKeyTree("", keys);
	assertEquals("", t.data);
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
	t = Words.buildKeyTree("", keys);
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
	
	assertNull(Words.mmatch(null));
	
	List<String> l = ListFrom.strings("a");
	List<String> r = ListFrom.strings("a");
	assertEquals(r, Words.mmatch(l));

	
	l = ListFrom.strings("a", "c");
	r = ListFrom.strings("a");
	assertEquals(r, Words.mmatch(l));

	l = ListFrom.strings("a", "ab");
	r = ListFrom.strings("a", "b");
	assertEquals(r, Words.mmatch(l));

	l = ListFrom.strings("ab", "ac");
	r = ListFrom.strings("a", "b", "c");
	assertEquals(r, Words.mmatch(l));	
	
	l = ListFrom.strings("a", "ab", "ac");
	r = ListFrom.strings("a", "b", "c");
	assertEquals(r, Words.mmatch(l));

	l = ListFrom.strings("a", "ab", "xc");
	r = ListFrom.strings("a", "b");
	assertEquals(r, Words.mmatch(l));

	l = ListFrom.strings("ab", "abc", "abd");
	r = ListFrom.strings("ab", "c", "d");
	assertEquals(r, Words.mmatch(l));	

}


@Test
public void testCommonPrefix() {
	
	assertEquals("", Words.commonPrefix("", ""));
	assertEquals("", Words.commonPrefix("a", ""));
	assertEquals("", Words.commonPrefix("", "a"));
	
	assertEquals("a", Words.commonPrefix("a", "a"));
	assertEquals("a", Words.commonPrefix("a", "ab"));
	assertEquals("a", Words.commonPrefix("ab", "a"));

	assertEquals("ab", Words.commonPrefix("ab", "ab"));
	assertEquals("ab", Words.commonPrefix("abc", "ab"));
	assertEquals("ab", Words.commonPrefix("ab", "abc"));

	assertEquals("ab", Words.commonPrefix("abx", "abc"));
	assertEquals("ab", Words.commonPrefix("abc", "abx"));

	assertEquals("", Words.commonPrefix("ab", "xy"));
	assertEquals("", Words.commonPrefix("ab", "xab"));

}


@Test
public void sentenceFromDictTest() {

	List<String> dict0 = ListFrom.strings();
	assertEquals(ListFrom.strings(), Words.sentenceFromDict("A", dict0));

	List<String> dict1 = ListFrom.strings("A", "B");
	assertEquals(ListFrom.strings("A B"), Words.sentenceFromDict("AB", dict1));

	List<String> dict2 = ListFrom.strings("cat","cats","and","sand","dog");
	assertEquals(ListFrom.strings("cats and dog","cat sand dog"), Words.sentenceFromDict("catsanddog", dict2));

//	List<String> dict3 = ListFrom.strings("apple","pen","applepen","pine","pineapple");
//	List<String> res = Words.sentenceFromDict("pineapplepenapple", dict3);
//	assertEquals(ListFrom.strings("pine apple pen apple","pineapple pen apple","pine applepen apple"), res);

	
}

}

/**
 Copyright 2024 Daniel Giribet <dani - calidos.cat>

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