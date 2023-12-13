package gr.charos.christmas.day11;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import gr.charos.christmas.day11.PointInSpace.Combinations;

public class UniverseMap {
	private final List<PointInSpace> points;
	private final List<Integer> rowsToExpand;
	private final List<Integer> columnsToExpand;
	private final int expansionMultiplier;
	
	
	
	
	public UniverseMap(List<PointInSpace> points, List<Integer> rowsToExpand, List<Integer> columnsToExpand, int expansionMultiplier) {
		this.points = points;
		this.rowsToExpand = rowsToExpand;
		this.columnsToExpand = columnsToExpand;
		this.expansionMultiplier = expansionMultiplier;
	}

	public long calculateSumOfDistances() {
		List<PointInSpace> galaxies = points.stream().filter(PointInSpace::isGalaxy).collect(Collectors.toList());
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

		return sumOfDistances;
	}

	private int calculateDistance(int from, int to, List<Integer> expansions) {
		int expansionCount = 0;
		if (from > to ) {
			for (int i = to; i < from; i ++) {
				if (expansions.contains(i)) {
					expansionCount += expansionMultiplier;
				}
			}
			return (from-to) + expansionCount;
			
		} else if (to > from ) {
			for (int i = from; i < to; i ++) {
				if (expansions.contains(i)) {
					expansionCount += expansionMultiplier;
				}
			}
			return (to-from) + expansionCount;
		}
		
		return 0;
	}


	public static UniverseMap from(List<String> lines, int expansionMultiplier) {
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
		
		return new UniverseMap(pisses, rowsToExpand, columnsToExpand,expansionMultiplier);

		
	}

}
