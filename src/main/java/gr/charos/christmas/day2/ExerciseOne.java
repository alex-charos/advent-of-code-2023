package gr.charos.christmas.day2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import gr.charos.christmas.day2.Game.Colour;

public class ExerciseOne {
	
	public static void main(String args[]) {
		System.out.println(new ExerciseOne(loadLines()).calculateAnswer(12, 13, 14));
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

	

	private static List<String> loadLines() {
		InputStream resource = ExerciseOne.class.getResourceAsStream("input.txt");
		List<String> doc = new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).lines()
				.collect(Collectors.toList());
		return doc;

		
	}
	
}
