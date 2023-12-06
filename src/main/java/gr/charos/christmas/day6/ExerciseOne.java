package gr.charos.christmas.day6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import gr.charos.christmas.Utils;

public class ExerciseOne {
	
	//1195150
	public static void main(String args[]) {
		List<String> lines = Utils.loadLines(ExerciseOne.class);
		
		List<TimeAndDistance> tnds = TimeAndDistance.from(lines);
		
		System.out.println( tnds.stream().map(TimeAndDistance::calculateWins).mapToInt(List::size).reduce(1, (a, b) -> a * b));
		
	}
	
	
	
	
	public static class TimeAndDistance {
		private static final int SPEED_MS = 1;
		private final int time;
		private final int recordDistance;
		public TimeAndDistance(int time, int recordDistance) {
			this.time = time;
			this.recordDistance = recordDistance;
		}
		
		public List<Integer> calculateWins() {
			List<Integer> distances = new ArrayList<Integer>();
			for (int i =0; i <= time; i++) {
				int speedGathered = SPEED_MS*i;
				int timeLeft = time-i;
				
				if (speedGathered * timeLeft > recordDistance) {
					distances.add(i);
				}
			}
			return distances;
			
		}
		
		
		public static List<TimeAndDistance> from(List<String> lines){
			String timesTrimmed = lines.get(0).split(":")[1].trim();
			String distancesTrimmed = lines.get(1).split(":")[1].trim();
			List<Integer> times=  Arrays.asList(timesTrimmed.split(" ")).stream().map(String::trim).filter(p->p.length()>0).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
			List<Integer> distances=  Arrays.asList(distancesTrimmed.split(" ")).stream().map(String::trim).filter(p->p.length()>0).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());

			List<TimeAndDistance> tnd = new ArrayList<ExerciseOne.TimeAndDistance>();
			for (int i =0; i<times.size(); i++) {
				tnd.add(new TimeAndDistance(times.get(i), distances.get(i)));
			}
			
			return tnd; 
		}
		

		
	}

}
