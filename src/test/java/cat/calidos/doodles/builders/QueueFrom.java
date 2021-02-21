// QUEUE FROM . JAVA

package cat.calidos.doodles.builders;

import cat.calidos.doodles.Queue;

public class QueueFrom {


public static Queue<String> strings(String... v) {

	Queue<String> a = new Queue<String>();
	for (String i : v) {
		a.enqueue(i);
	}

	return a;

}


public static Queue<Integer> ints(Integer... v) {

	Queue<Integer> a = new Queue<Integer>();
	for (Integer i : v) {
		a.enqueue(i);
	}

	return a;

}


}

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
