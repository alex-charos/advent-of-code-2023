package gr.charos.christmas.day4;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import gr.charos.christmas.Utils;

public class ExerciseTwo {

	public static void main(String args[]) {
		List<String> lines = Utils.loadLines(ExerciseOne.class);
		int correct = 6283755;
		int calculated = new ExerciseTwo(lines).calculateAnswer();
		if (correct !=calculated) {
			System.err.println("Wrong answer!");
		}
		

	}

	private final List<String> lines;

	public ExerciseTwo(List<String> lines) {
		this.lines = lines;
	}

	public int calculateAnswer() {
		List<Game> allGames = lines.stream().map(Game::fromLine).collect(Collectors.toList());
		Map<Integer, List<Game>> gamesPerNum = allGames.stream().collect(Collectors.groupingBy(Game::getNumber));
		for (Entry<Integer, List<Game>> entrySet : gamesPerNum.entrySet()) {
			List<Game> wonGames = entrySet.getValue().stream().map(p -> p.calculateWinningCards(allGames))
					.flatMap(Collection::stream).collect(Collectors.toList());
			for (Game g : wonGames) {
				gamesPerNum.get(g.getNumber()).add(g);
			}
		}

		return gamesPerNum.values().stream().flatMap(Collection::stream).collect(Collectors.toList()).size();

	}

}
