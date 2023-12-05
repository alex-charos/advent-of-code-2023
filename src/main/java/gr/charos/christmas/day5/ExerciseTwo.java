package gr.charos.christmas.day5;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import gr.charos.christmas.Utils;

public class ExerciseTwo {
	
	
	//1240035 correct
	public static void main(String args[]) throws InterruptedException {
		List<String> lines = Utils.loadLines(ExerciseTwo.class);
		
		Tableau t = Tableau.from(lines);
		
		RangedSeedTableau rst = new RangedSeedTableau(t);
		System.out.println(rst.seeds);
		
		
		
		
		
		ExecutorService executor = Executors.newFixedThreadPool(10);
		Collection<Callable<Long>> callables  = new ArrayList<Callable<Long>>();
		for (int i = 0; i < rst.seeds.size()-1; i++) {
			int tmpc = i;
			Callable<Long> f = ()-> {
				int counter = tmpc;
				long min = Long.MAX_VALUE;
				System.out.println("starting: " + tmpc);
				for (int j = 0; j < rst.seeds.get(counter+1).intValue(); j++ ) {
					Long location = rst.getLocation(rst.seeds.get(counter)+j);
					if (location<min) {
						min = location;
					}
				}
				System.out.println("completed: " + tmpc);
				
				return min;
			};
			callables.add(f);
		
			i++;
			
		}
		Collection<Future<Long>> futures = executor.invokeAll(callables);
		Long min = futures.stream().map(t1 -> {
			try {
				return t1.get();
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}).mapToLong(Long::valueOf).min().orElseThrow();
		
		System.out.println(min);
		
		
	}
	

	
	
}
