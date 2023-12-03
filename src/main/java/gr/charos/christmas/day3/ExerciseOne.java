package gr.charos.christmas.day3;

import java.util.ArrayList;
import java.util.List;

import gr.charos.christmas.Utils;

public class ExerciseOne {

	
	//539590 is correct 
	public static void main(String args[]) {
		List<String> lines =  Utils.loadLines(ExerciseOne.class);
		
		List<String> numbers = new ArrayList<String>();
		for (int i = 0; i < lines.size(); i++ ) {
			
			String lineHandled = lines.get(i);
			for (int j =0; j < lineHandled.length(); j++) {
				//find number
				String number = "";
				boolean reachedEnd = false;
				while ( !reachedEnd && isDigit(lineHandled.charAt(j))) {
					number+=lineHandled.charAt(j);
					j++;
					if (j == lineHandled.length()) {
						reachedEnd = true;
					}
					
				}
				if (number.length() >0) {
					//We found one!
					//line = i
					// char from = j-length
					// jar to = j
					boolean isAtEdgeLeft = j-number.length()==0;
					boolean isAtEdgeRight = j==lineHandled.length();
					boolean isFirstRow = i==0;
					boolean isLastRow = i == lines.size()-1;
					
					boolean hasAdjacentLeft = false;
					boolean hasAdjacentTopLeft = false;
					boolean hasAdjacentBottomLeft = false;

					boolean hasAdjacentRight = false;
					boolean hasAdjacentTopRight = false;
					boolean hasAdjacentBottomRight = false;
					
					boolean hasAdjacentTop = false;
					boolean hasAdjacentBottom = false;

					if (!isAtEdgeLeft) {
						char adjacentLeftChar = lineHandled.charAt(j-number.length()-1);
						hasAdjacentLeft = isSymbol(adjacentLeftChar);
						if (!isFirstRow) {
							char adjacentTopLeftChar = lines.get(i-1).charAt(j-number.length()-1);
							hasAdjacentTopLeft = isSymbol(adjacentTopLeftChar);
						}
						if (!isLastRow) {
							char adjacentBottomLeftChar = lines.get(i+1).charAt(j-number.length()-1);
							hasAdjacentBottomLeft = isSymbol(adjacentBottomLeftChar);
						}
					}
					if (!isAtEdgeRight) {
						char adjacentRightChar = lineHandled.charAt(j);
						hasAdjacentRight = isSymbol(adjacentRightChar);
						if (!isFirstRow) {
							char adjacentTopRightChar = lines.get(i-1).charAt(j);
							hasAdjacentTopRight = isSymbol(adjacentTopRightChar);
						}
						if (!isLastRow) {
							char adjacentBottomRightChar = lines.get(i+1).charAt(j);
							hasAdjacentBottomRight = isSymbol(adjacentBottomRightChar);
						}
					}
					
					if (!isFirstRow) {
						for (int k = j-number.length(); k<j;k++) {
							if (isSymbol(lines.get(i-1).charAt(k))) {
								hasAdjacentTop = true;
							}
						}
					}
					if (!isLastRow) {
						for (int k = j-number.length(); k<j;k++) {
							if (isSymbol(lines.get(i+1).charAt(k))) {
								hasAdjacentBottom = true;
							}
						}
					}
					
					if (hasAdjacentBottom || hasAdjacentBottomLeft || hasAdjacentBottomRight 
							|| hasAdjacentLeft || hasAdjacentRight  
						|| hasAdjacentTop || hasAdjacentTopLeft || hasAdjacentTopRight) {
						numbers.add(number);
					} 
					
				}
				
			}

		}
		
		System.out.println( numbers.stream().mapToInt(Integer::parseInt).sum());
		
	}
	
	//Non letter (a-z,A-Z), non period (.) ascii char defined as symbol
	private static boolean isSymbol(char c ) {
		return (c > 33  && c<=126) && (c != 46) && ( c <65 || c >91) && (c <97 || c >122);
	}
	
	
	private static boolean isDigit(char c) {
		return  c >= 48 && c <= 57;
	} 
}
