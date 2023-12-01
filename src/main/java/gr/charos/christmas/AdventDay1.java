package gr.charos.christmas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class AdventDay1 {

	public static void main(String args[]) throws IOException {

		System.out.println(new AdventDay1(loadLines()).calculateAnswer());
	}

	private static List<String> loadLines() {
		InputStream resource = AdventDay1Exercise2.class.getResourceAsStream("day1.txt");
		List<String> doc = new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).lines()
				.collect(Collectors.toList());
		return doc;

	}

	private final List<String> lines;

	public AdventDay1(List<String> lines) {

		this.lines = lines;
	}

	public int calculateAnswer() {
		return lines.stream().mapToInt(AdventDay1::sumFirstAndLast).sum();

	}

	private static int sumFirstAndLast(String l) {
		return Integer.parseInt(String.valueOf(findFirstNumber(l)).concat(String.valueOf(findLastNumber(l))));
	}

	private static int findFirstNumber(String line) {
		int firstNum = line.chars().dropWhile(AdventDay1::isNotDigit).findFirst().getAsInt();
		return Integer.parseInt(String.valueOf((char) firstNum));

	}

	private static int findLastNumber(String line) {
		return findFirstNumber(reverse(line));
	}

	private static String reverse(String line) {
		char[] string = line.toCharArray();
		int start = 0;
		int end = string.length - 1;
		String reversed = "";
		for (int i = end; i >= start; i--) {
			reversed = reversed + string[i];
		}
		return reversed;
	}

	public static boolean isNotDigit(int c) {
		return c < 48 || c > 57;
	}

}
