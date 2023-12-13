package gr.charos.christmas.day11;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import gr.charos.christmas.Utils;

public class ExerciseOne {

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

		System.out.println(rowsToExpand);
		System.out.println(columnsToExpand);

		for (int i = columnsToExpand.size() - 1; i >= 0; i--) {
			int pos = columnsToExpand.get(i);
			lines.add(pos, lines.get(pos));
		}
		lines.stream().forEach(System.out::println);

		List<String> newLines = new ArrayList<String>();

		for (int x = 0; x < lines.size(); x++) {
			String s = lines.get(x);
			for (int i = rowsToExpand.size() - 1; i >= 0; i--) {
				int pos = rowsToExpand.get(i);

				s = Utils.addChar(s, ".", pos);
			}
			newLines.add(s);
		}

		newLines.stream().forEach(System.out::println);

		List<PointInSpace> pisses = new ArrayList<PointInSpace>();
		for (int i = 0; i < newLines.size(); i++) {
			String s = newLines.get(i);
			for (int j = 0; j < s.length(); j++) {
				PointInSpace pis = new PointInSpace(j, i, s.substring(j, j + 1));
				pisses.add(pis);
			}
		}

		List<PointInSpace> galaxies = pisses.stream().filter(PointInSpace::isGalaxy).collect(Collectors.toList());
		List<PointInSpace> galaxiesCopy = new ArrayList<ExerciseOne.PointInSpace>(galaxies);

		Integer sumOfDistances = 0;
		for (PointInSpace from : galaxies) {
			
			for (PointInSpace to : galaxiesCopy) {
				if (!from.equals(to) &&  !Combinations.hasCombination(from, to)) {
					int disx = Math.abs(to.point.x - from.point.x);
					int disy = Math.abs(to.point.y - from.point.y);
					int dis = disx + disy ;
					sumOfDistances+=dis;
					Combinations.addCombination(from, to);
				}

			}
			
		}

		System.out.println(sumOfDistances);

	}
	
	public static class Combinations {
		static List<List<PointInSpace>> combis = new ArrayList<>();
		public  static void addCombination(PointInSpace from, PointInSpace to) {
			List<PointInSpace> combi = new ArrayList<ExerciseOne.PointInSpace>();
			combi.add(from);
			combi.add(to);
			combis.add(combi);
		}
		
		public static boolean hasCombination(PointInSpace from, PointInSpace to) {
			for (List<PointInSpace> l : combis) {
				if (l.contains(from) && l.contains(to)) {
					return true;
				}
			}
			return false;
		}
		
		
	}
	

	public enum Type {
		EMPTY_SPACE("."), GALAXY("#");

		private String sign;

		Type(String sign) {
			this.sign = sign;
		}

		public static Type of(String character) {
			return Arrays.asList(Type.values()).stream().filter(p -> character.equals(p.sign)).findFirst().get();
		}

	}

	public static class PointInSpace {
		private final Point point;
		private final Type type;

		public PointInSpace(int x, int y, String character) {
			this.point = new Point(x, y);
			this.type = Type.of(character);
		}

		public boolean isGalaxy() {
			return Type.GALAXY.equals(this.type);
		}

		@Override
		public String toString() {
			return "PointInSpace [point=" + point + ", type=" + type + "]";
		}
		
		

	}

}
