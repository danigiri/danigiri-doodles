package cat.calidos.doodles;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import cat.calidos.doodles.builders.ListFrom;

public class FileSystemTest {
	
@Test @DisplayName("DirEntry from list test")
public void DirEntryFromListTest() {

	var paths = ListFrom.strings("/");
	var root = DirEntry.fromList(paths);
	assertEquals("/", root.name);
	assertTrue(root.isDir());
	
	paths = ListFrom.strings("/", "/a");
	root = DirEntry.fromList(paths);
	assertEquals("/", root.name);
	assertTrue(root.isDir());
	var a = root.toDirEntry().entry("a").orElseThrow();
	assertEquals("a", a.name);
	assertTrue(a.isFile());

	paths = ListFrom.strings("/", "/a/");
	root = DirEntry.fromList(paths);
	assertEquals("/", root.name);
	a = root.toDirEntry().entry("a").orElseThrow();
	assertEquals("a", a.name);
	assertTrue(a.isDir());

	paths = ListFrom.strings("/", "/a/", "/a/b");
	root = DirEntry.fromList(paths);
	assertEquals("/", root.name);
	a = root.toDirEntry().entry("a").orElseThrow();
	assertEquals("a", a.name);
	assertTrue(a.isDir());
	var b = a.toDirEntry().entry("b").orElseThrow();
	assertEquals("b", b.name);
	assertTrue(b.isFile());

	paths = ListFrom.strings("/", "/a/", "/a/b/c");
	root = DirEntry.fromList(paths);
	assertEquals("/", root.name);
	a = root.toDirEntry().entry("a").orElseThrow();
	assertEquals("a", a.name);
	assertTrue(a.isDir());
	b = a.toDirEntry().entry("b").orElseThrow();
	assertEquals("b", b.name);
	assertTrue(b.isDir());
	var c = b.toDirEntry().entry("c").orElseThrow();
	assertEquals("c", c.name);
	assertTrue(c.isFile());

	paths = ListFrom.strings("/", "/a/", "/a/b/c", "/d");
	root = DirEntry.fromList(paths);
	assertEquals("/", root.name);
	a = root.toDirEntry().entry("a").orElseThrow();
	assertEquals("a", a.name);
	assertTrue(a.isDir());
	b = a.toDirEntry().entry("b").orElseThrow();
	assertEquals("b", b.name);
	assertTrue(b.isDir());
	c = b.toDirEntry().entry("c").orElseThrow();
	assertEquals("c", c.name);
	assertTrue(c.isFile());

	var d = root.toDirEntry().entry("d").orElseThrow();
	assertEquals("d", d.name);
	assertTrue(d.isFile());

}

}
