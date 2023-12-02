package gr.charos.christmas.day2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

	static class Game {
		private final List<SetOfCubes> setOfCubes;
		private final int gameNumber;

		public Game(List<SetOfCubes> setOfCubes, int gameNumber) {
			this.setOfCubes = setOfCubes;
			this.gameNumber = gameNumber;
		}
		public int getGameNumber() {
			return gameNumber;
		}
		
		
		public boolean canHandle(Colour c, int count) {
			return this.setOfCubes.stream().map(SetOfCubes::getCombos).flatMap(List::stream)
			.filter(p->p.getColour().equals(c)).allMatch(p->p.canHandle(c, count));
		}
		
		public static Game fromLine(String line) {
			//Line starts with "Game ${num}:"
			int gameNum = Integer.parseInt(line.substring(5, line.indexOf(":")));
			List<SetOfCubes> sets = SetOfCubes.fromLine(line.substring(line.indexOf(":")+1));
			
			return new Game(sets, gameNum);
		}
	
	}
	static class SetOfCubes {
		private  final List<Combo> combos;

		public SetOfCubes(List<Combo> combos) {
			this.combos = combos;
		}
		
		
		public List<Combo> getCombos() {
			return combos;
		}


		public static List<SetOfCubes> fromLine(String line) {
			return Arrays.asList(line.split(";")).stream()
					.map(Combo::fromLine).map(SetOfCubes::new).collect(Collectors.toList());
			
			
		}
		
	}
	static class Combo {
		private final Colour colour;
		private final int count;
		public Combo(Colour colour, int count) {
			super();
			this.colour = colour;
			this.count = count;
		}
		
		public boolean canHandle(Colour c, int count ) {
			boolean s = this.colour.equals(c) && count >= this.count;
			//System.out.println("I am :" + this.toString() + " and can hanadle " + c + " " + count + ":" + s);
			return s; 
		}
		public Colour getColour() {
			return colour;
		}

		public int getCount() {
			return count;
		}

		//e.g. 1 green, 2 red, 6 blue
		public static List<Combo> fromLine(String l) {
			List<Combo> combos = new ArrayList<>();
			String[] items = l.trim().split(",");
			for (int i =0; i < items.length; i++) {
				String[] parts = items[i].trim().split(" ");
				int count = Integer.parseInt(parts[0]);
				Colour c = Colour.valueOf(parts[1]);
				combos.add(new Combo(c,count));
			}
			return combos;
		}

		@Override
		public String toString() {
			return "Combo [colour=" + colour + ", count=" + count + "]";
		}
		
	}
	
	static enum Colour {
		red(),
		green(),
		blue();
		 
		
	
	}

	private static List<String> loadLines() {
		InputStream resource = ExerciseOne.class.getResourceAsStream("input.txt");
		List<String> doc = new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8)).lines()
				.collect(Collectors.toList());
		return doc;

		
	}
	
}
