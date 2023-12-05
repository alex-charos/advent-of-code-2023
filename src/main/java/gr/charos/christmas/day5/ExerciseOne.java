package gr.charos.christmas.day5;

import java.util.List;

import gr.charos.christmas.Utils;

public class ExerciseOne {
	
	
	public static void main(String args[]) {
		List<String> lines = Utils.loadLines(ExerciseOne.class);
		
		Tableau t = Tableau.from(lines);
		
		System.out.println(t.seeds);
		System.out.println(t.minLocation());
		
	}
	

	
	
}
