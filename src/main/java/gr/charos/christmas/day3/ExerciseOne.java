package gr.charos.christmas.day3;

import static gr.charos.christmas.Utils.isDigit;
import static gr.charos.christmas.Utils.isSymbol;
import static gr.charos.christmas.Utils.loadLines;

import java.util.ArrayList;
import java.util.List;


public class ExerciseOne {

	
		public static void main(String args[]) {
		List<String> lines =  loadLines(ExerciseOne.class);
		
		int correct =539590;
		int calculated = new ExerciseOne(lines).calculateAnswer() ;
		
		
		if (correct !=calculated) {
			System.err.println("Wrong answer!");
		}
		
	}
	
	private final List<String> lines;
	
	
	public ExerciseOne(List<String> lines) {
		this.lines = lines;
	}
	
	public int calculateAnswer() {
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
		
		return numbers.stream().mapToInt(Integer::parseInt).sum();
	}


}
