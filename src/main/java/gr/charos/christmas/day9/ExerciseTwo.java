package gr.charos.christmas.day9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import gr.charos.christmas.Utils;

public class ExerciseTwo {
	
	//1118 
	public static void main(String args[]) {
		List<String> lines =Utils.loadLines(ExerciseTwo.class);
		
		List<Integer> sums = new ArrayList<Integer>();
		for (String s : lines) {
			
			List<Integer>  list = Arrays.asList( s.split(" ")).stream().filter(p->p.length()>0).map(Integer::parseInt).collect(Collectors.toList());
			
			
			List<List<Integer>> history = new ArrayList<List<Integer>>();
			while (!list.stream().allMatch(p->p==0)) {
				history.add(list);
				list = calculateDiffs(list);
			}
			
			int toAddBelow = 0;
			for (int i = history.size()-1; i >=0; i--) {
				List<Integer> comp = history.get(i);
				int toAdd = comp.get(0);
				int tmp = toAdd-toAddBelow;
				comp.add(0, tmp);
				toAddBelow = tmp;
			}
			
			
			sums.add(toAddBelow);
			System.out.println(history);
			
		}
		System.out.println(sums.stream().mapToInt(Integer::valueOf).sum());
		
		
		
	}
	

	static List<Integer> calculateDiffs(List<Integer> list) {
		List<Integer> diffs = new ArrayList<Integer>();
		for (int i = 0; i < list.size()-1; i++) {
			diffs.add(list.get(i+1) - list.get(i));
		}
		return diffs;
	}
}
