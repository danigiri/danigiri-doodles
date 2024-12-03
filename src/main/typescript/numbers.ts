export class Numbers {

	// return the sum of the numbers between the two, not ordered
	public static sumBetween(a: number, b: number): number {
		if (a === b) {
			return a;
		}
		const start = a < b ? a : b;
		const end = a < b ? b : a;
		let s = 0;
		for (let i = start; i <= end; i++) {
			s += i;
		}
		return s;
	}

	public static seriesSumRecursive(n: number): String {
		const out = Numbers.seriesSumRecursiveImpl(n - 1);
		return out.toFixed(2);
	}

	private static seriesSumRecursiveImpl(n: number): number {
		return n === 0 ? 1 : Numbers.seriesSumRecursiveImpl(n - 1) + 1 / (n * 3 + 1);
	}

	public static seriesSum(n: number): String {
		let out = 0;
		for (let i = 0; i < n; i++) {
			out += 1 / (i * 3 + 1)
		}
		return out.toFixed(2);
	}


	// given n, return the multiplication table 
	// n=3
	// 1 2 3
	// 2 4 6
	// 3 6 9
	// 
	public static multiplicationTable(n: number): number[][] {
		const table: number[][] = [];
		for (let j = 1; j <= n; j++) {
			table.push(this.multiplicationRow(j, n));
		}
		return table;
	}

	private static multiplicationRow(j: number, n: number): number[] {
		const r: number[] = [];
		for (let i = 1; i <= n; i++) {
			r.push(j * i);
		}
		return r;
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

