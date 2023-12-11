package gr.charos.christmas.day10;

import java.util.ArrayList;
import java.util.List;

import gr.charos.christmas.Utils;
import gr.charos.christmas.day10.Maze.Position;

public class ExerciseOne {
	
	//7097
	public static void main(String args[]) {
		List<String> lines = Utils.loadLines(ExerciseOne.class);
		
		List<Position> positions = new ArrayList<Maze.Position>();
		for (int i =0; i <lines.size();i++) {
			for (int j =0; j < lines.get(i).length(); j++) {
				Position pos = Position.of(j, i, lines.get(i).substring(j, j+1));
				positions.add(pos);
			}
		
		}
		Maze m = new Maze(positions);
		m.mapByDistance();
		m.positions.sort(null);
		System.out.println(m.positions.get(0).getStepsFromStartingPosition());
		
		
	}

}
