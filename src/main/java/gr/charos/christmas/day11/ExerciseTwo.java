package gr.charos.christmas.day11;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import gr.charos.christmas.Utils;
import gr.charos.christmas.day11.PointInSpace.Combinations;

public class ExerciseTwo {

	private static final int MULTIPLIER = 999999;
	
	//458191688761

	public static void main(String args[]) {
		List<String> lines = Utils.loadLines(ExerciseOne.class);

		List<Integer> rowsToExpand = new ArrayList<Integer>();
		extLoop: for (int i = 0; i < lines.get(0).length(); i++) {

			for (int j = 0; j < lines.size(); j++) {
				String s = lines.get(j);
				PointInSpace pis = new PointInSpace(i, j, s.substring(i, i + 1));
				if (pis.isGalaxy()) {
					continue extLoop;
				}
			}
			rowsToExpand.add(i);
		}

		List<Integer> columnsToExpand = new ArrayList<Integer>();
		extLoop: for (int i = 0; i < lines.size(); i++) {
			String s = lines.get(i);
			for (int j = 0; j < s.length(); j++) {
				PointInSpace pis = new PointInSpace(i, j, s.substring(j, j + 1));
				if (pis.isGalaxy()) {
					continue extLoop;
				}
			}
			columnsToExpand.add(i);
		}

	
		List<PointInSpace> pisses = new ArrayList<PointInSpace>();
		for (int i = 0; i < lines.size(); i++) {
			String s = lines.get(i);
			for (int j = 0; j < s.length(); j++) {
				PointInSpace pis = new PointInSpace(j, i, s.substring(j, j + 1));
				pisses.add(pis);
			}
		}

		List<PointInSpace> galaxies = pisses.stream().filter(PointInSpace::isGalaxy).collect(Collectors.toList());
		List<PointInSpace> galaxiesCopy = new ArrayList<PointInSpace>(galaxies);

		Long sumOfDistances = 0l;
		for (PointInSpace from : galaxies) {

			for (PointInSpace to : galaxiesCopy) {
				if (!from.equals(to) && !Combinations.hasCombination(from, to)) {
					int disx = calculateDistance(to.point.x, from.point.x, rowsToExpand);
					
					int disy =calculateDistance(to.point.y, from.point.y, columnsToExpand);
					int dis = disx + disy;
					sumOfDistances += dis;
					Combinations.addCombination(from, to);
				}

			}

		}

		System.out.println(sumOfDistances);

	}

	public static int calculateDistance(int from, int to, List<Integer> expansions) {
		int expansionCount = 0;
		if (from > to ) {
			for (int i = to; i < from; i ++) {
				if (expansions.contains(i)) {
					expansionCount += MULTIPLIER;
				}
			}
			return (from-to) + expansionCount;
			
		} else if (to > from ) {
			for (int i = from; i < to; i ++) {
				if (expansions.contains(i)) {
					expansionCount += MULTIPLIER;
				}
			}
			return (to-from) + expansionCount;
		}
		
		return 0;
	}
	
}
