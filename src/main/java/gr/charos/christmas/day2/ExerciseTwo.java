package gr.charos.christmas.day2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import gr.charos.christmas.day2.Game.Colour;

public class ExerciseTwo {

	public static void main(String args[]) {
		String line = "Game 1: 1 green, 2 red, 6 blue; 4 red, 1 green, 3 blue; 7 blue, 5 green; 6 blue, 2 red, 1 green";
		Game g = Game.fromLine(line);
		
		System.out.println( g.minPower());
		System.out.println( g.maxInColour(Colour.red));
		System.out.println( g.maxInColour(Colour.green));
		System.out.println( g.maxInColour(Colour.blue));
		System.out.println(new ExerciseTwo(loadLines()).calculateAnswer());
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

	

	private static List<String> loadLines() {
		InputStream resource = ExerciseOne.class.getResourceAsStream("input.txt");
		List<String> doc = new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).lines()
				.collect(Collectors.toList());
		return doc;

		
	}
	
}
