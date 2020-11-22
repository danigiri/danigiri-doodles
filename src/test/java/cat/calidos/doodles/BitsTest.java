package cat.calidos.doodles;

import static org.junit.Assert.*;

import org.junit.Test;


public class BitsTest {


@Test
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

}
