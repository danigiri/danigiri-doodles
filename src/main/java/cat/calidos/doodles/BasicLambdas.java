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
package cat.calidos.doodles;

import java.util.List;

public class BasicLambdas {

	int min = Integer.MAX_VALUE;

public int minLambda(List<Integer> l) {
	// notice: not a pure function
	l.stream().forEach(i -> {if (i<min) { min = i; }});
	return min;
}

}
