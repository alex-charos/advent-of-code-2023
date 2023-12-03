package gr.charos.christmas.day3;

import java.util.ArrayList;
import java.util.List;


import static gr.charos.christmas.Utils.*;

public class ExerciseTwo {

	public static void main(String args[]) {
		List<String> lines = loadLines(ExerciseOne.class);

		int correct = 80703636;
		int calculated = new ExerciseTwo(lines).calculateAnswer();
		
		if (correct !=calculated) {
			System.err.println("Wrong answer!");
		}
		

	}
	
	private final List<String> lines;
	

	public ExerciseTwo(List<String> lines) {
		super();
		this.lines = lines;
	}

	public int calculateAnswer() {
		List<Integer> ratios = new ArrayList<Integer>();
		for (int i = 0; i < lines.size(); i++) {

			String lineHandled = lines.get(i);
			for (int j = 0; j < lineHandled.length(); j++) {
				if (isGear(lineHandled.charAt(j))) {
					List<String> adjacentNumbers = new ArrayList<String>();
					boolean isAtEdgeLeft = j == 0;
					boolean isAtEdgeRight = j == lineHandled.length() - 1;
					boolean isFirstRow = i == 0;
					boolean isLastRow = i == lines.size() - 1;

					if (!isAtEdgeLeft) {
						// adjacent to its left
						String number = findNumberEndingAtCharIfExists(lineHandled, j - 1);
						if (number.length() > 0) {
							adjacentNumbers.add(number);
						}
					}
					if (!isAtEdgeRight) {
						// adjacent to its left
						String number = findNumberBeginningAtCharIfExists(lineHandled, j + 1);
						if (number.length() > 0) {
							adjacentNumbers.add(number);
						}
					}

					if (!isFirstRow) {
						/*
						 * ...678.. ....*...
						 * 
						 * ....678. ....*...
						 * 
						 * ..678... ....*...
						 * 
						 * 
						 * .678.... ....*...
						 * 
						 * .....678 ....*...
						 * 
						 * 
						 */

						String numberAtTop = findNumberWithCharAtIfExists(lines.get(i - 1), j);
						if (numberAtTop.length() > 0) {
							adjacentNumbers.add(numberAtTop);
							// if there is on top, no adjacent diagonal may exist
						} else {
							if (!isAtEdgeLeft) {
								String diagonalTopLeft = findNumberEndingAtCharIfExists(lines.get(i - 1), j - 1);
								if (diagonalTopLeft.length() > 0) {
									adjacentNumbers.add(diagonalTopLeft);
								}
							}
							if (!isAtEdgeRight) {
								String diagonalTopRight = findNumberBeginningAtCharIfExists(lines.get(i - 1), j + 1);
								if (diagonalTopRight.length() > 0) {
									adjacentNumbers.add(diagonalTopRight);
								}
							}
						}

					}
					if (!isLastRow) {
						String numberAtBottom = findNumberWithCharAtIfExists(lines.get(i + 1), j);
						if (numberAtBottom.length() > 0) {
							adjacentNumbers.add(numberAtBottom);
							// if there is on bottom, no adjacent diagonal may exist
						} else {
							if (!isAtEdgeLeft) {
								String diagonalBottomLeft = findNumberEndingAtCharIfExists(lines.get(i + 1), j - 1);
								if (diagonalBottomLeft.length() > 0) {
									adjacentNumbers.add(diagonalBottomLeft);
								}
							}
							if (!isAtEdgeRight) {
								String diagonalBottomRight = findNumberBeginningAtCharIfExists(lines.get(i + 1), j + 1);
								if (diagonalBottomRight.length() > 0) {
									adjacentNumbers.add(diagonalBottomRight);
								}
							}
						}

					}

					if (adjacentNumbers.size() == 2) {
						ratios.add(Integer.parseInt(adjacentNumbers.get(0)) * Integer.parseInt(adjacentNumbers.get(1)));
					}

				}

			}

		}

		 return (ratios.stream().mapToInt(Integer::intValue).sum());
	}
	
	private static boolean isGear(char c) {
		return c == 42;
	}

	private static String findNumberWithCharAtIfExists(String line, int pos) {
		String resp = "";
		if (isDigit(line.charAt(pos))) {
			resp = "" + line.charAt(pos);
			pos--;
			// go backwards
			while (isDigit(line.charAt(pos))) {
				resp = line.charAt(pos) + resp;
				pos--;
			}

			pos += resp.length();
			// go forward
			pos++;
			while (isDigit(line.charAt(pos))) {
				resp = resp + line.charAt(pos);
				pos++;
			}

		}

		return resp;
	}

	private static String findNumberBeginningAtCharIfExists(String line, int start) {
		String resp = "";
		while (start < line.length() && isDigit(line.charAt(start))) {
			resp = resp + line.charAt(start);
			start++;
		}

		return resp;
	}

	private static String findNumberEndingAtCharIfExists(String line, int end) {
		String resp = "";
		while (end >= 0 && isDigit(line.charAt(end))) {
			resp = line.charAt(end) + resp;
			end--;
		}

		return resp;
	}




}
