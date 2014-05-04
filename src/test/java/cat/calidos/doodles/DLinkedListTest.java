package cat.calidos.doodles;

import static org.junit.Assert.*;

import org.junit.Test;


public class DLinkedListTest {

@Test
public void testDLinkedListTest() {
	
	DLinkedList<String> l0 = new DLinkedList<String>("a");
	assertEquals("a", l0.data);
	
	l0.add("b");
	assertEquals(2, l0.length());
	l0.insert("c");
	assertEquals(3, l0.length());	
}

}
