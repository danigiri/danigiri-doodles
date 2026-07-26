package cat.calidos.doodles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

	class Entry {

		public static String SEPARATOR = "/";
	
		protected Optional<List<Entry>> entries;
		protected String name;

		public Entry(String name, List<Entry> entries) {
			this.entries = Optional.of(entries);
			this.name = name;
		}

		public Entry(String name) {
			this.entries = Optional.empty();
			this.name = name;
		}


		public boolean isDir() {
			return this.entries.isPresent();
		}

		public boolean isFile() {
			return !this.isDir();
		}

		public DirEntry toDirEntry() {
			return DirEntry.fromEntry(this);
		}

		public FileEntry toFileEntry() {
			return FileEntry.fromEntry(this);
		}

		@Override
		public String toString() {
			return this.name;
		}

	}

	class DirEntry extends Entry {

		/*
			/a
			"a"
			/c/
			/a/b
			/c
		*/
		public static Entry fromList(List<String> paths) {
			var rootName = SEPARATOR;
			var root = new DirEntry(rootName);
			for (var p : paths) {
				// skip the root
				if (p.equals(SEPARATOR)) {
					continue;
				}
				var path = p.split(SEPARATOR);
				root.add(1, path, p);

			}
			return root;
		}

		public DirEntry(String name, List<Entry> entries) {
			super(name, entries);
		}
		
		public DirEntry(String name) {
			super(name, new ArrayList<Entry>(0));
		}

		public List<Entry> ls() {
			return entries.orElseThrow();
		}

		public DirEntry cd(String name) throws IllegalArgumentException {
			return this.entries()
					.stream()
					.filter(e -> e.isDir() && e.name.equals(name))
					.findAny()
					.orElseThrow(() -> new IllegalArgumentException("Folder '%s' does not exist".formatted(name)))
					.toDirEntry();
		}


		public Entry add(Entry entry) {
			this.entries().add(entry);
			return this;
		}

		public static DirEntry fromEntry(Entry e) {
			if (!e.isDir()) {
				throw new IllegalArgumentException("Argument '%s' is not a folder".formatted(e));
			}
			return new DirEntry(e.name, e.entries.orElseThrow());
		}

		public Entry toEntry() {
			return new Entry(this.name, this.entries());
		}

		private Entry add(int i, String[] p, String original){
			// invariant: i<p.length
			var name = p[i];
			var length = p.length;
			// base cases
			if (i+1==length && original.endsWith(SEPARATOR)) {
				// we are at the end of the path and it's a folder
				var entry = new DirEntry(name);
				this.add(entry);
			} else if (i+1==length) {
				// we are at the end of the path and it's a file
				var entry = new FileEntry(name);
				this.add(entry);
			} else {
				// recursive cases
				// if folder was not there we add it
				if (this.entries().stream().noneMatch(e -> e.isDir() && e.name.equals(name))) {
					this.add(new DirEntry(name).toEntry());
				}
				// precondition, folder `name` exists
				this.cd(name).add(i+1, p, original); // guaranteed by induction as i increments
			}
			return this;
		} 

		private List<Entry> entries() {
			return this.entries.orElseThrow();
		}

		public Optional<Entry> entry(String name) {
			return this.ls().stream().filter(e -> e.name.equals(name)).findAny();
		}

	}

	class FileEntry extends Entry {

		public FileEntry(String name, List<Entry> entries) {
			super(name,entries);
			if (entries.isEmpty()) {
				this.entries = Optional.empty();
			} else {
				throw new RuntimeException("Cannot create a file with entries (%d)".formatted(entries.size()));
			}
		}

		public FileEntry(String name) {
			super(name);
		}

		public static FileEntry fromEntry(Entry e) {
			if (!e.isFile()) {
				throw new IllegalArgumentException("Argument '%s' is not a file".formatted(e));
			}
			return new FileEntry(e.name);
		}

		public Entry toEntry() {
			return new Entry(this.name);
		}
	}


public class Filesystem {
	
	/*
	tree is a command-line tool for pretty-printing the directory structure at the point of invocation.
		$ tree
		.
		├── uv.lock
		├── pyproject.toml
		├── README.md
		└── src
		└── hs_interviewing_coderpad_automation
		├── __init__.py
		├── coderpad_client.py
		├── main.py
		├── model.py
		├── py.typed
		├── synchronizer.py
		├── testing
		│ ├── __init__.py
		│ ├── __pycache__
		│ │ ├── __init__.cpython-313.pyc
		│ │ └── __init__.cpython-314.pyc
		│ ├── cxx.py
		│ ├── python.py
		│ └── rust.py
		├── utils.py
		└── utils_test.py
		5 directories, 17 files
		Write a program that performs tree on a mocked file-system, printing your output in the format as
		shown above.

	
	*/
	public static String tree(Entry fs) {
	
		return "";
	}

}
