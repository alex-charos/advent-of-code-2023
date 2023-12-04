package gr.charos.christmas.day4;

import java.util.List;

import gr.charos.christmas.Utils;

public class ExerciseOne {

	
	public static void main(String args[]) {
		List<String> lines = Utils.loadLines(ExerciseOne.class);
		int correct = 15268;
		int calculated = new ExerciseOne(lines).calculateAnswer();
		if (correct !=calculated) {
			System.err.println("Wrong answer!");
		}
	}

	private final List<String> lines;

	public ExerciseOne(List<String> lines) {
		this.lines = lines;
		
	}
	public int calculateAnswer() {
		return lines.stream().map(Game::fromLine).mapToInt(Game::calculatePoints).sum();
	}

	

}
