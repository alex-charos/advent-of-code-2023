package gr.charos.christmas.day10;

import java.util.ArrayList;
import java.util.List;

import gr.charos.christmas.Utils;
import gr.charos.christmas.day10.Maze.Position;

public class ExerciseTwo {
	
	//355 
	public static void main(String args[]) {
		List<String> lines = Utils.loadLines(ExerciseOne.class);
		
		List<Position> positions = new ArrayList<Maze.Position>();
		for (int i =0; i <lines.size();i++) {
			for (int j =0; j < lines.get(i).length(); j++) {
				Position pos = Position.of(j, i, lines.get(i).substring(j, j+1));
				positions.add(pos);
			}
		
		}
		Maze m = new Maze(positions, lines.get(0).length()-1, lines.size()-1);
		m.calculateMainLoop();
		
		m.onLoopsRight.forEach(System.out::println);
		System.out.println("----------");
		m.onLoopsLeft.forEach(System.out::println);
		
		m.onLoopsLeft.removeIf(m.onLoopsRight::contains);
		m.onLoopsRight.removeIf(m.onLoopsLeft::contains);
		
		boolean rightIsOut = m.onLoopsRight.stream().anyMatch(p->m.isEdge(p));
		
		if (rightIsOut) {
			System.out.println(m.onLoopsLeft.size());	
		} else {
			System.out.println(m.onLoopsRight.size());
		}
		
		
		
		
		

		
	}

}
