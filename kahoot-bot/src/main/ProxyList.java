package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public final class ProxyList extends ThreadSafeList<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5342476908145267180L;
	
	private final String path;
	
	public ProxyList(final String _path) throws IOException {
		path = _path;
		
		final List<String> lines = Files.readAllLines(Paths.get(path));
		super.addAll(lines);
	}
	
	public synchronized final void save() throws IOException {
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(path, false)));
		super.forEach(pw::println);
		pw.close();
	}
}
