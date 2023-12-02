package gr.charos.christmas.day2;

import java.util.List;

import gr.charos.christmas.Utils;


public class ExerciseTwo {

	public static void main(String args[]) {
		int correct = 64097;
		int calculated = new ExerciseTwo(Utils.loadLines(ExerciseTwo.class, "input.txt")).calculateAnswer();
		
		if (correct !=calculated) {
			System.err.println("Wrong answer!");
		}
	}
	

	private final List<String> lines;
	
	public ExerciseTwo(List<String> lines) {
		this.lines = lines;
	}
	
	public int calculateAnswer() {
		return this.lines.stream().map(Game::fromLine)
				.mapToInt(Game::minPower)
				.sum();
	}
	
}
