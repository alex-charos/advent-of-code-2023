package gr.charos.christmas.day6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class TimeAndDistance {
	private static final int SPEED_MS = 1;
	
	private final int time;
	private final long recordDistance;
	public TimeAndDistance(int time, long recordDistance) {
		this.time = time;
		this.recordDistance = recordDistance;
	}
	
	public List<Long> calculateWins() {
		List<Long> distances = new ArrayList<Long>();
		int notCovered = 0;
		long speedOfWinner = recordDistance/time; // 5498932
		
		
		for (long i =speedOfWinner-1; i <= time; i++) {
			long speedGathered = SPEED_MS*i;
			long timeLeft = time-i;
			
			if (speedGathered * timeLeft > recordDistance) {
				distances.add(i);
				notCovered =0;
			} else {
				notCovered++;
				if (notCovered>10000000) {
					return distances;
				}
				
			}
		}
		return distances;
		
	}
	
	
	public static List<TimeAndDistance> from(List<String> lines){
		String timesTrimmed = lines.get(0).split(":")[1].trim();
		String distancesTrimmed = lines.get(1).split(":")[1].trim();
		List<Integer> times=  Arrays.asList(timesTrimmed.split(" ")).stream().map(String::trim).filter(p->p.length()>0).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
		List<Integer> distances=  Arrays.asList(distancesTrimmed.split(" ")).stream().map(String::trim).filter(p->p.length()>0).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());

		List<TimeAndDistance> tnd = new ArrayList<TimeAndDistance>();
		for (int i =0; i<times.size(); i++) {
			tnd.add(new TimeAndDistance(times.get(i), distances.get(i)));
		}
		
		return tnd; 
	}
	
	public static List<TimeAndDistance> fromSingle(List<String> lines){
		String timesTrimmed = lines.get(0).split(":")[1].trim();
		String distancesTrimmed = lines.get(1).split(":")[1].trim();
		String time=  Arrays.asList(timesTrimmed.split(" ")).stream()
							.map(String::trim)
							.filter(p->p.length()>0)
							.mapToInt(Integer::parseInt)
							.boxed().map(String::valueOf)
					    .collect(Collectors.joining(""));
		String distance=  Arrays.asList(distancesTrimmed.split(" ")).stream()
				.map(String::trim)
				.filter(p->p.length()>0)
				.mapToInt(Integer::parseInt)
				.boxed().map(String::valueOf)
		    .collect(Collectors.joining(""));
	 
		List<TimeAndDistance> tnd = new ArrayList<TimeAndDistance>();
		tnd.add(new TimeAndDistance(Integer.valueOf(time), Long.valueOf(distance)));
		
		return tnd; 
	}
	
}