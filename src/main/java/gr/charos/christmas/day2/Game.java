package gr.charos.christmas.day2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Game {
	private final List<SetOfCubes> setOfCubes;
	private final int gameNumber;

	public Game(List<SetOfCubes> setOfCubes, int gameNumber) {
		this.setOfCubes = setOfCubes;
		this.gameNumber = gameNumber;
	}

	public int getGameNumber() {
		return gameNumber;
	}
	
	public int minPower() {
		return Arrays.asList(Colour.values()).stream().mapToInt(this::maxInColour).reduce(1, (a, b) -> a * b);
		
	}

	//Maximum numbers of balls in specific colour
	public int maxInColour(Colour c) {
		return this.setOfCubes.stream().map(SetOfCubes::getCombos).flatMap(List::stream)
				.filter(p -> p.getColour().equals(c)).mapToInt(Combo::getCount).max().orElse(0);

	}

	public boolean canHandle(Colour c, int count) {
		return this.setOfCubes.stream().map(SetOfCubes::getCombos).flatMap(List::stream)
				.filter(p -> p.getColour().equals(c)).allMatch(p -> p.canHandle(c, count));
	}

	public static Game fromLine(String line) {
		// Line starts with "Game ${num}:"
		int gameNum = Integer.parseInt(line.substring(5, line.indexOf(":")));
		List<SetOfCubes> sets = SetOfCubes.fromLine(line.substring(line.indexOf(":") + 1));

		return new Game(sets, gameNum);
	}

	public static class SetOfCubes {
		private final List<Combo> combos;

		public SetOfCubes(List<Combo> combos) {
			this.combos = combos;
		}

		public List<Combo> getCombos() {
			return combos;
		}

		public static List<SetOfCubes> fromLine(String line) {
			return Arrays.asList(line.split(";")).stream().map(Combo::fromLine).map(SetOfCubes::new)
					.collect(Collectors.toList());

		}

	}

	public static class Combo {
		private final Colour colour;
		private final int count;

		public Combo(Colour colour, int count) {
			super();
			this.colour = colour;
			this.count = count;
		}

		public boolean canHandle(Colour c, int count) {
			return this.colour.equals(c) && count >= this.count;
		}

		public Colour getColour() {
			return colour;
		}

		public int getCount() {
			return count;
		}

		// e.g. 1 green, 2 red, 6 blue
		public static List<Combo> fromLine(String l) {
			List<Combo> combos = new ArrayList<>();
			String[] items = l.trim().split(",");
			for (int i = 0; i < items.length; i++) {
				String[] parts = items[i].trim().split(" ");
				int count = Integer.parseInt(parts[0]);
				Colour c = Colour.valueOf(parts[1]);
				combos.add(new Combo(c, count));
			}
			return combos;
		}

		@Override
		public String toString() {
			return "Combo [colour=" + colour + ", count=" + count + "]";
		}

	}

	public static enum Colour {
		red(), green(), blue();

	}
}
