package cat.calidos.doodles;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Geometry2Test {

@Test
@DisplayName("Count points test")
public void rectangleRotationTest() {
	assertEquals(23, Geometry2.rectangleRotation(6, 4));
    assertEquals(65, Geometry2.rectangleRotation(30,2));
    assertEquals(49, Geometry2.rectangleRotation(8,6));
    assertEquals(333,Geometry2.rectangleRotation(16,20));
    /*
*/
}
}


/**
 * Copyright 2024 Daniel Giribet <dani - calidos.cat>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
