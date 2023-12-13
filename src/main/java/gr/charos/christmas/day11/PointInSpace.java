package gr.charos.christmas.day11;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public  class PointInSpace {
	public final Point point;
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
	
	
	public static class Combinations {
		static List<List<PointInSpace>> combis = new ArrayList<>();
		public  static void addCombination(PointInSpace from, PointInSpace to) {
			List<PointInSpace> combi = new ArrayList<PointInSpace>();
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
	

}