export class Strings {

	// reverses all words
	public static reverseWords(s: string): string {
		return s.split(" ").map(w => Strings.reverseWord(w)).join(' ');
	}

	// reverse one word
	public static reverseWord(w: string): string {
		let r = "";
		w.split('').forEach((c, i) => r = c.concat(r));
		return r;
	}

	public static mask(s: string, visible: number): string {
		if (s.length <= visible) {
			return s;
		}
		let m = '#'.repeat(s.length - visible);
		m = m.concat(s.substring(s.length - visible));
		return m;
	}

	public static vowelCount(s: string): number {
		return s.split('').map(c => 'aeiou'.includes(c) ? 1 : 0).reduce((sum: number, curr: number) => sum + curr,
			0);
	}

	public static filterByLength(list: string[], n: number) {
		return list.filter(e => e.length === n);
	}

	// return the duplicate chars count (aa --> 1, ab -> 0, abbcc -> 2)
	public static duplicateCharCount(s: string): number {
		const counts = s.split('')
			.map((c: string) => c.toLowerCase())
			.reduce<Map<string, number>>((existing, c: string) => {
				const count = existing.get(c);
				existing.set(c, count === undefined || count === null ? 1 : count + 1);
				return existing;
			}, new Map());
		return Array.from(counts.entries())
			.map((e:[string,number])=>e[1])
			.filter((count:number) => count > 1).length;
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

