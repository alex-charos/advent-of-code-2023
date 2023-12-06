package gr.charos.christmas.day6;

import java.util.List;

import gr.charos.christmas.Utils;

public class ExerciseOne {
	
	//1195150
	public static void main(String args[]) {
		List<String> lines = Utils.loadLines(ExerciseOne.class);
		
		List<TimeAndDistance> tnds = TimeAndDistance.from(lines);
		
		System.out.println( tnds.stream().map(TimeAndDistance::calculateWins).mapToInt(List::size).reduce(1, (a, b) -> a * b));
		
	}
	
	
	
	
	

}
