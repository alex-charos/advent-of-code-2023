package gr.charos.christmas.day11;

import java.util.List;

import gr.charos.christmas.Utils;

public class ExerciseTwo {

	private static final int MULTIPLIER = 999999;
	
	//458191688761
	public static void main(String args[]) {
		List<String> lines = Utils.loadLines(ExerciseOne.class);

		System.out.println(UniverseMap.from(lines, MULTIPLIER).calculateSumOfDistances());
		

	}

}
