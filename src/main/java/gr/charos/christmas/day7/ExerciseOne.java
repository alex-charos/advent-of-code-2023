package gr.charos.christmas.day7;

import java.util.List;
import java.util.stream.Collectors;

import gr.charos.christmas.Utils;

public class ExerciseOne {
	public static void main(String args[]) {
		List<String> lines = Utils.loadLines(ExerciseOne.class);
		List<Hand> hands = lines.stream().map(Hand::of).collect(Collectors.toList());
		hands.sort(null);
		int sum = 0;
		for (int i =1; i <=hands.size(); i++) {
			
			sum += hands.get(i-1).bid() * i; 
		}
		
		System.out.println(sum);
	}
}
