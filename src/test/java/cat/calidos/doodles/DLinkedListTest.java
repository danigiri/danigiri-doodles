package cat.calidos.doodles;

import static org.junit.Assert.*;

import org.junit.Test;


public class DLinkedListTest {

@Test
public void testDLinkedListTest() {
	
	DLinkedList<String> l0 = new DLinkedList<String>("a");
	assertEquals("a", l0.data);
	
	l0.append("b");
	assertEquals(2, l0.length());
	l0.append("c");
	assertEquals(3, l0.length());	
	
}

@Test
public void removeTest() {
	DLinkedList<String> l0 = new DLinkedList<String>("a");
	l0.append("b");
	DLinkedList<String> lmiddle = l0.next;
	l0.append("c");
	assertEquals(3, l0.length());	


	l0 = lmiddle.remove();
	assertEquals(2, l0.length());	
	
	assertEquals("a", l0.data);
	assertNull(l0.prev);
	assertNotNull(l0.next);
	assertEquals("c", l0.next.data);
	
}

}
