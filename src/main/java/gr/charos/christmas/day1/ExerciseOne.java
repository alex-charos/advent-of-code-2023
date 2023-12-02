package gr.charos.christmas.day1;


import java.io.IOException;
import java.util.List;

import gr.charos.christmas.Utils;

public class ExerciseOne {

	public static void main(String args[]) throws IOException {
		int correct = 55386;
		int calculated = new ExerciseOne(Utils.loadLines(ExerciseOne.class)).calculateAnswer();
		
		if (correct !=calculated) {
			System.err.println("Wrong answer!");
		}
		
	}


	private final List<String> lines;

	public ExerciseOne(List<String> lines) {

		this.lines = lines;
	}

	public int calculateAnswer() {
		return lines.stream().mapToInt(ExerciseOne::sumFirstAndLast).sum();

	}

	private static int sumFirstAndLast(String l) {
		return Integer.parseInt(String.valueOf(findFirstNumber(l)).concat(String.valueOf(findLastNumber(l))));
	}

	private static int findFirstNumber(String line) {
		int firstNum = line.chars().dropWhile(ExerciseOne::isNotDigit).findFirst().getAsInt();
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
