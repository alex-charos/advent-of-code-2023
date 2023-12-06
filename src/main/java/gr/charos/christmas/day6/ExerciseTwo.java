package gr.charos.christmas.day6;

import java.util.List;

import gr.charos.christmas.Utils;

public class ExerciseTwo {
	


	public static void main(String args[]) {
		List<String> lines = Utils.loadLines(ExerciseTwo.class);
		
		List<TimeAndDistance> tnds = TimeAndDistance.fromSingle(lines);
		
		System.out.println( tnds.stream()
					.map(TimeAndDistance::calculateWins)
					.mapToInt(List::size)
				.reduce(1, (a, b) -> a * b));
		
	}
	

}
