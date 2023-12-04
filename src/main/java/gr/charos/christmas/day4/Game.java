package gr.charos.christmas.day4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Game {
	private final String name;
	private final int number;
	private final List<Integer> pickedNumbers;
	private final List<Integer> winningNumbers;
	private final int matchingNumbers;
	
	

	public int getNumber() {
		return number;
	}

	public Game(String name, int number, List<Integer> pickedNumbers, List<Integer> winningNumbers) {
		this.name = name;
		this.number = number;
		this.pickedNumbers = pickedNumbers;
		this.winningNumbers = winningNumbers;
		matchingNumbers = calculateMatchingNumbers();
	}
	
	private int calculateMatchingNumbers() {
		int count = 0;
		for (Integer i : pickedNumbers) {
			if (winningNumbers.contains(i)) {
				count++;
			}
		}
		return count;

	}

	public List<Game> calculateWinningCards(List<Game> totalCards) {
		List<Game> addedGames = new ArrayList<>();
		if (matchingNumbers > 0) {
			for (int i = number + 1; i <= number + matchingNumbers; i++) {
				int numberToFind = i;
				totalCards.stream().filter(g->g.number==numberToFind)
										.findFirst()
									.ifPresent(addedGames::add);
				
			}
		}

		return addedGames;
	}

	public int calculatePoints() {
		return (int) Math.pow(2, matchingNumbers - 1);
	}

	public static Game fromLine(String s) {
		String[] parts = s.split(":");

		String name = parts[0];
		String[] nameParts = name.split(" ");
		int number = Integer.parseInt(nameParts[nameParts.length - 1].trim());

		String[] numbers = parts[1].trim().split(Pattern.quote("|"));

		List<Integer> winningNumbers = Arrays.asList(numbers[0].trim().split(" ")).stream().filter(p -> p.length() > 0)
				.mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
		List<Integer> pickedNumbers = Arrays.asList(numbers[1].trim().split(" ")).stream().filter(p -> p.length() > 0)
				.mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());

		return new Game(name, number, pickedNumbers, winningNumbers);
	}

	@Override
	public String toString() {
		return "Game [name=" + name + ", number=" + number + ", pickedNumbers=" + pickedNumbers + ", winningNumbers="
				+ winningNumbers + "]";
	}

}
