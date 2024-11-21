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
	    return list.filter(e => e.length===n);
	}
}
