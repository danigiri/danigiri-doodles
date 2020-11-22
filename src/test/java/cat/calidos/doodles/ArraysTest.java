package cat.calidos.doodles;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cat.calidos.doodles.builders.ArrayFrom;

public class ArraysTest {


@Test
public void testRotateInPlace() {

	List<Integer> a = ArrayFrom.ints(1, 2, 3, 4);
	Arrays.rotateInPlace(ArrayFrom.ints(1, 2, 3, 4), 0);
	assertEquals(a, a);

	a = ArrayFrom.ints(1, 2, 3, 4);
	Arrays.rotateInPlace(a, 1);
	assertEquals(ArrayFrom.ints(2, 3, 4, 1), a);

	a = ArrayFrom.ints(1, 2, 3, 4);
	Arrays.rotateInPlace(a, 4);
	assertEquals(ArrayFrom.ints(1, 2, 3, 4),  a);

	a = ArrayFrom.ints(1, 2, 3, 4);
	Arrays.rotateInPlace(a, 2);
	assertEquals(ArrayFrom.ints(3, 4, 1, 2), a);

}




}


