package gr.charos.christmas.day1;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import gr.charos.christmas.Utils;

public class ExerciseTwo {
	private static final List<Number> LITERAL_NUMBERS = Arrays.asList(	
			new Number("zero", 0, 48, "0"),
			new Number("one", 1, 49, "1"), 
			new Number("two", 2, 50, "2"), 
			new Number("three", 3, 51, "3"),  
			new Number("four", 4, 52, "4"), 
			new Number("five", 5, 53, "5"), 
			new Number("six", 6, 54, "6"),
			new Number("seven", 7, 55, "7"),
			new Number("eight", 8, 56, "8"),						
			new Number("nine", 9, 57, "9")
		);	
	
	public static void main(String args[]) throws IOException {
		int correct = 54824;
		int calculated =  new ExerciseTwo(Utils.loadLines(ExerciseTwo.class)).calculateAnswer();
		
		if (correct !=calculated) {
			System.err.println("Wrong answer!");
		}
		
	}
	private final List<String> lines;

	public ExerciseTwo(List<String> lines) {
	
		this.lines = lines;
	}
	public int calculateAnswer() {
		return lines.stream().mapToInt(this::sumFirstAndLast).sum();		

	}
	private  int sumFirstAndLast(String l) {
		return Integer.parseInt ( findFirstNumber( l)
										.concat (
												findLastNumber(l)
												)
										
									);
	}
	
	
	//scans for a number forward (literal or numeric value)
	private String findFirstNumber(String line) {
		int pos =0;
		while (pos < line.length()) {
			for (Number n : LITERAL_NUMBERS) {
				int np = n.isAtStartOf(line.substring(pos));
				if (np>0) {
					pos+=np;
					return n.asString;
				} 
			}
			pos+=1;
			
		}
		return "0";
	}

	//scans for a number backwards (literal or numeric value)
	private String findLastNumber(String line) {
		int pos =line.length();
		while (pos > 0) {
			for (Number n : LITERAL_NUMBERS) {
				int np = n.isAtEndOf(line.substring(0, pos));
				if (np>0) {
					pos-=np;
					return n.asString;
				} 
			}
			pos-=1;
		}
		return "0";
		
	}

	
	public static class Number {
		private final String literal;
	
		private final int charNum;
		private final String asString;
		
		public Number(String literal, int number, int charNum, String asString) {
			
			this.literal = literal;
			this.charNum = charNum;
			this.asString = asString;
		}
		
		
		//will return how much to move index forward if present, 0 if not
		public  int isAtStartOf(String s) {
			if ( s.charAt(0) == charNum) {
				return  1;
			}
			if (s.startsWith(literal)) {
				return   literal.length();
			}
			return 0;
		}
		
		//will return how much to move index backwards if present, 0 if not
		public  int isAtEndOf(String s) {
			if ( s.charAt(s.length()-1) == charNum) {
				return  1;
			}
			if (s.endsWith(literal)) {
				return literal.length();
			}
			return 0;
		}
	}
	


}
