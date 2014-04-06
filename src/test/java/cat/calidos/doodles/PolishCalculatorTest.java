package cat.calidos.doodles;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;


public class PolishCalculatorTest {


@Test(expected = NullPointerException.class)
public void calcTestNull() {
	PolishCalculator.calc(null);
}

@Test(expected = ArithmeticException.class)
public void calcTestDivideByZero() {
	List<String> exp = Arrays.asList("2", "0", "/");
	assertEquals((long)3, PolishCalculator.calc(exp));
}

@Test(expected = NumberFormatException.class)
public void calcTestGarbageNumber() {
	List<String> exp = Arrays.asList("1", "a", "+");
	assertEquals((long)3, PolishCalculator.calc(exp));
}

@Test
public void calcTest() {
	
	assertEquals((long)0, PolishCalculator.calc(new ArrayList<String>(0)));

	List<String> exp = Arrays.asList("1", "2", "+");
	assertEquals((long)3, PolishCalculator.calc(exp));

	exp = Arrays.asList("0", "++");
	assertEquals((long)1, PolishCalculator.calc(exp));

	exp = Arrays.asList("1", "--");
	assertEquals((long)0, PolishCalculator.calc(exp));

	exp = Arrays.asList("1","2", "+", "--");
	assertEquals((long)2, PolishCalculator.calc(exp));

	exp = Arrays.asList("0", "1", "-");
	assertEquals((long)-1, PolishCalculator.calc(exp));

	exp = Arrays.asList("2", "3", "*");
	assertEquals((long)6, PolishCalculator.calc(exp));

	exp = Arrays.asList("4", "2", "/");
	assertEquals((long)2, PolishCalculator.calc(exp));
	
}

}
