package cat.calidos.doodles.builders;

import java.util.ArrayList;

public class ArrayFrom {


public static ArrayList<Integer> ints(int... v) {

	ArrayList<Integer> a = new ArrayList<Integer>(v.length);
	for (int i : v) {
		a.add(i);
	}

	return a;

}


}
