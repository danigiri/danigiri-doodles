export class Lists<T> {

	public substractFromList(a: T[], b: T[]): T[] {
		// given a, output a and skip any elements in b
		const elements = b.reduce((set: Set<T>, e: T) => set.add(e), new Set<T>());
		return a.filter(e => !elements.has(e));
	}

	public uniqueInOrder(values:  T[]): T[] {
		// aaabbc -->abc
		if (values.length === 0) {
			return [];
		}
		let prev = values[0];
		const result: T[] = [];
		result.push(prev);
		for (let i = 1; i < values.length; i++) {
			const c = values[i];
			if (c !== prev) {
				result.push(c);
				prev = c;
			}
		}
		return result;
	}

}


/*
 * Copyright 2024 Daniel Giribet
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

