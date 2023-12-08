package gr.charos.christmas.day8;

import java.util.List;

import gr.charos.christmas.Utils;

public class ExerciseOne {
	
	
	//15989
	public static void main(String args[]) {
		
		List<String> lines = Utils.loadLines(ExerciseOne.class);
		Instructions instr = Instructions.from(lines);
		
		String currentName = "AAA";
		int count = 0;
		while (!currentName.equals("ZZZ")) {
			currentName = (instr.executeStartingAt(currentName).getName());
			System.out.println("Current name:" + currentName);
			count++;
		}
		
		System.out.println(count* instr.getMoves().size());
		
		
	}
	
	
	

}
