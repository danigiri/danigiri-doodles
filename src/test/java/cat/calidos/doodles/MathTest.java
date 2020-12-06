// MATH TEST . JAVA
package cat.calidos.doodles;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;


public class MathTest {

@Test @DisplayName("multiply with additions test")
public void multiplyWithPlusTest() {

	int v = Math.multiplyWithPlus(0, 0);
	assertEquals(0, v);

	v = Math.multiplyWithPlus(1, 0);
	assertEquals(0, v);

	v = Math.multiplyWithPlus(0, 1);
	assertEquals(0, v);

	v = Math.multiplyWithPlus(1, 1);
	assertEquals(1, v);

	v = Math.multiplyWithPlus(1, 5);
	assertEquals(5, v);

	v = Math.multiplyWithPlus(5, 1);
	assertEquals(5, v);

	v = Math.multiplyWithPlus(5, 5);
	assertEquals(25, v);

	v = Math.multiplyWithPlus(2, 5);
	assertEquals(10, v);

}


@Test @DisplayName("divide with substractions test")
public void divideWithSubstractTest() {

	int v = Math.divideWithSubstract(0.0, 1.0);
	assertEquals(0, v);

	v = Math.divideWithSubstract(1.0, 0.5);
	assertEquals(2, v);

	v = Math.divideWithSubstract(0.5, 1.0);
	assertEquals(0, v);

}


@Test  @DisplayName("multiply with additions test")
public void stepTest() {

	assertEquals(0, Math.steps(0));
	assertEquals(1, Math.steps(1));
	assertEquals(2, Math.steps(2));
	assertEquals(4, Math.steps(3));

	// 4= 1+steps(3):4+1+steps(2):2+1+steps(1):1 = 10
	assertEquals(10, Math.steps(4));

}


}

/**
Copyright 2016 Daniel Giribet <dani - calidos.cat>

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
