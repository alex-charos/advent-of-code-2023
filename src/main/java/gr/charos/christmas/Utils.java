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
	
	public static String addChar(String str, String ch, int position) {
	    return str.substring(0, position) + ch + str.substring(position);
	}
 
	
	
	public static boolean isPound(char c) {
		return c == 35;
	}
	public static boolean isPeriod(char c) {
		return c == 46;
	}
	
	public static boolean isQuestionMark(char c) {
		return c == 63;
	}
	
	public  static List<String> loadLines(Class c) {
		return loadLines(c, "input.txt");

	}
	
	public static boolean isDigit(char c) {
		return c >= 48 && c <= 57;
	}
	
	// Non letter (a-z,A-Z), non period (.) ascii char defined as symbol
	public static boolean isSymbol(char c) {
		return (c > 33 && c <= 126) && (c != 46) && (c < 65 || c > 91) && (c < 97 || c > 122);
	}


}
