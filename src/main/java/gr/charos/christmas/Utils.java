package gr.charos.christmas;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;


public class Utils {
	
	public  static List<String> loadLines(Class c, String filename) {
		
		InputStream resource = c.getResourceAsStream(filename);
		List<String> doc = new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).lines()
				.collect(Collectors.toList());
		return doc;

	}
	
	public  static List<String> loadLines(Class c) {
		return loadLines(c, "input.txt");

	}


}
