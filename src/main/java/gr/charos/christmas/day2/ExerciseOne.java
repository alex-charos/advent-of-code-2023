package gr.charos.christmas.day2;

import java.util.List;

import gr.charos.christmas.Utils;
import gr.charos.christmas.day2.Game.Colour;

public class ExerciseOne {
	
	public static void main(String args[]) {
		int correct = 2265;
		int calculated = new ExerciseOne(Utils.loadLines(ExerciseOne.class, "input.txt")).calculateAnswer(12, 13, 14);
		
		if (correct !=calculated) {
			System.err.println("Wrong answer!");
		}
	}

	private final List<String> lines;
	
	public ExerciseOne(List<String> lines) {
		this.lines = lines;
	}
	
	public int calculateAnswer(int red, int green, int blue) {
		return this.lines.stream().map(Game::fromLine)
				.filter(g -> g.canHandle(Colour.red, red) 
								&& g.canHandle(Colour.green, green)
								&& g.canHandle(Colour.blue, blue)
						
						).mapToInt(Game::getGameNumber)
				.sum();
	}
	
}
