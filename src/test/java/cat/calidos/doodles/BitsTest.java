package cat.calidos.doodles;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BitsTest {


@Test @DisplayName("sub bits test")
public void subBitsTest() {

	int n = Bits.subBits(0x0, 0x0, 0, 31);
	assertEquals(0x0, n);
	n = Bits.subBits(0x0, 0x1, 0, 31);
	assertEquals(0x1, n);
	n = Bits.subBits(0x0, 0x1, 0, 0);
	assertEquals(0x1, n);
	n = Bits.subBits(0x1, 0x0, 0, 0);
	assertEquals(0x0, n);
	n = Bits.subBits(0x0, 0x3, 0, 0);
	assertEquals(0x1, n);
	n = Bits.subBits(0x0, 0x7, 0, 1);
	assertEquals(0x3, n);
	n = Bits.subBits(0x0, 0x7, 1, 2);
	assertEquals(0x6, n);
	n = Bits.subBits(0x0, 0x7, 2, 1);
	assertEquals(0x6, n);
	
//	assertEquals(IntegerFrom.bitString("00000000000000000000000000000000"), n);
}


@Test @DisplayName("pretty print test")
public void prettyPrintTest() {

	String s = Bits.prettyPrint(4);
	assertAll("basic pretty print",
		() -> assertNotNull(s),
		() -> assertEquals(3, s.split("\\n").length)
	);

	String[] lines = s.split("\\n");
	assertAll("check values for 4 pretty print",
		() -> assertEquals(Bits.PRETTY_HEADER0, lines[0]),
		() -> assertEquals(Bits.PRETTY_HEADER1, lines[1]),
		() -> assertEquals("00000000000000000000000000000100", lines[2])
	);

	String s2 = Bits.prettyPrint(16+4);
	assertAll("basic pretty print 2",
		() -> assertNotNull(s2),
		() -> assertEquals(3, s.split("\\n").length)
	);

	String[] lines2 = s2.split("\\n");
	assertAll("check values for 20 pretty print",
		() -> assertEquals(Bits.PRETTY_HEADER0, lines2[0]),
		() -> assertEquals(Bits.PRETTY_HEADER1, lines2[1]),
		() -> assertEquals("00000000000000000000000000010100", lines2[2])
	);

}


@Test @DisplayName("insert test")
public void insertTest() {

	int inserted = Bits.insert(512, 128+1, 0, 7);
	String[] lines = Bits.prettyPrint(inserted).split("\\n");
	assertEquals("00000000000000000000001010000001", lines[2]);

	int inserted2 = Bits.insert(512+1, 128+0, 0, 7);
	String[] lines2 = Bits.prettyPrint(inserted2).split("\\n");
	assertEquals("00000000000000000000001010000000", lines2[2]);

}


@Test @DisplayName("swap test")
public void swapTest() {

	Map<String, Integer> swap = Bits.swap(129, 77);
	int a = swap.get("a");
	int b = swap.get("b");

	assertAll("basic swap tests",
			() -> assertNotNull(a),
			() -> assertNotNull(b),
			() -> assertEquals(77, a),
			() -> assertEquals(129, b)
	);

	Map<String, Integer> swap2 = Bits.swap(111, 111);
	int a2 = swap2.get("a");
	int b2 = swap2.get("b");

	assertAll("basic swap tests",
			() -> assertNotNull(a2),
			() -> assertNotNull(b2),
			() -> assertEquals(111, a2),
			() -> assertEquals(111, b2)
	);

}


}
