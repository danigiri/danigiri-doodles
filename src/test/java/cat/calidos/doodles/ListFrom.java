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

import java.util.ArrayList;
import java.util.List;


public class ListFrom {

public static List<Integer> ints(int... v) {
	
	List<Integer> a = new ArrayList<Integer>();
	for (int i : v) {
		a.add(Integer.valueOf(i));
	}
	return a;

}

	
public static List<String> strings(String... v) {
	
	List<String> a = new ArrayList<String>();
	for (String i : v) {
		a.add(i);
	}
	return a;
	
}

}
