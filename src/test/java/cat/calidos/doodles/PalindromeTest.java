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


public class PalindromeTest {

@Test(expected = NullPointerException.class)
public void longestTestNull() {
	Palindrome.longest(null);	
}


@Test
public void longestTest() {
	
	assertEquals("", Palindrome.longest(""));
	assertEquals("a", Palindrome.longest("a"));
	assertEquals("a", Palindrome.longest("ab"));
	assertEquals("aaa", Palindrome.longest("aaa"));
	assertEquals("aba", Palindrome.longest("aba"));
	assertEquals("aba", Palindrome.longest("zaba"));
	assertEquals("aba", Palindrome.longest("abaz"));
	assertEquals("aaaa", Palindrome.longest("aaaa"));
	assertEquals("aba", Palindrome.longest("abazz"));
	assertEquals("abba", Palindrome.longest("rwwwabbazz"));
	assertEquals("xyx", Palindrome.longest("xyxbab"));
	
}


}
