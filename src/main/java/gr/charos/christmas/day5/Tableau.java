package gr.charos.christmas.day5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Tableau {
	List<Long> seeds; 
	List<MapEntry> seedToSoilMap;
	List<MapEntry> soilToFertilizerMap;
	List<MapEntry> fertilizerToWaterMap;
	List<MapEntry> waterToLightMap;
	List<MapEntry> lightToTemperatureMap;
	List<MapEntry> temperatureToHumidityMap;
	List<MapEntry> humidityToLocationMap;
	
	
	
	public Tableau(List<Long> seeds, List<MapEntry> seedToSoilMap, List<MapEntry> soilToFertilizerMap,
			List<MapEntry> fertilizerToWaterMap, List<MapEntry> waterToLightMap, List<MapEntry> lightToTemperatureMap,
			List<MapEntry> temperatureToHumidityMap, List<MapEntry> humidityToLocationMap) {
		this.seeds = seeds;
		this.seedToSoilMap = seedToSoilMap;
		this.soilToFertilizerMap = soilToFertilizerMap;
		this.fertilizerToWaterMap = fertilizerToWaterMap;
		this.waterToLightMap = waterToLightMap;
		this.lightToTemperatureMap = lightToTemperatureMap;
		this.temperatureToHumidityMap = temperatureToHumidityMap;
		this.humidityToLocationMap = humidityToLocationMap;
	}

	public Long minLocation() {
		return seedsToLocation().stream().mapToLong(Long::valueOf).min().orElseThrow();
	}
	
	private List<Long> seedsToLocation() {
		return seeds.stream()
		/* to soil */	.map(p -> seedToSoilMap.stream().map(soil -> soil.calculateDestination(p)).filter(pp-> pp>-1).findFirst().orElse(p))
		/* to fertilizer */	.map(p -> soilToFertilizerMap.stream().map(soil -> soil.calculateDestination(p)).filter(pp-> pp>-1).findFirst().orElse(p))
		/* to water */	.map(p -> fertilizerToWaterMap.stream().map(soil -> soil.calculateDestination(p)).filter(pp-> pp>-1).findFirst().orElse(p))
		/* to light */	.map(p -> waterToLightMap.stream().map(soil -> soil.calculateDestination(p)).filter(pp-> pp>-1).findFirst().orElse(p))
		/* to temperature */	.map(p -> lightToTemperatureMap.stream().map(soil -> soil.calculateDestination(p)).filter(pp-> pp>-1).findFirst().orElse(p))
		/* to humidity */	.map(p -> temperatureToHumidityMap.stream().map(soil -> soil.calculateDestination(p)).filter(pp-> pp>-1).findFirst().orElse(p))
		/* to location */	.map(p -> humidityToLocationMap.stream().map(soil -> soil.calculateDestination(p)).filter(pp-> pp>-1).findFirst().orElse(p))
		.collect(Collectors.toList());
		
	}
	
	
	public static Tableau from(List<String> lines) {
		List<Long> seeds = null;
		List<MapEntry> seedToSoilMap= null;
		List<MapEntry> soilToFertilizerMap= null;
		List<MapEntry> fertilizerToWaterMap= null;
		List<MapEntry> waterToLightMap= null;
		List<MapEntry> lightToTemperatureMap= null;
		List<MapEntry> temperatureToHumidityMap= null;
		List<MapEntry> humidityToLocationMap= null;
		
		for (int i = 0; i<lines.size(); i++) {
			String line = lines.get(i);
			if (line.startsWith("seeds:")) {
				seeds = Arrays.asList( line.split(Pattern.quote(":"))[1].trim().split(" ")).stream().mapToLong(Long::parseLong).boxed().collect(Collectors.toList());
			}
			
			if (line.startsWith("seed-to-soil")) {
				RetrievalResult rr = retrieve(lines, i);
				i = rr.i;
				seedToSoilMap = rr.mapEntries;
			}
			if (line.startsWith("soil-to-fertilizer")) {
				RetrievalResult rr = retrieve(lines, i);
				i = rr.i;
				soilToFertilizerMap = rr.mapEntries;
			}
			if (line.startsWith("fertilizer-to-water")) {
				RetrievalResult rr = retrieve(lines, i);
				i = rr.i;
				fertilizerToWaterMap = rr.mapEntries;
			}
			if (line.startsWith("water-to-light")) {
				RetrievalResult rr = retrieve(lines, i);
				i = rr.i;
				waterToLightMap = rr.mapEntries;
			}
			if (line.startsWith("light-to-temperature")) {
				RetrievalResult rr = retrieve(lines, i);
				i = rr.i;
				lightToTemperatureMap = rr.mapEntries;
			}
			if (line.startsWith("temperature-to-humidity")) {
				RetrievalResult rr = retrieve(lines, i);
				i = rr.i;
				temperatureToHumidityMap = rr.mapEntries;
			}
			if (line.startsWith("humidity-to-location")) {
				RetrievalResult rr = retrieve(lines, i);
				i = rr.i;
				humidityToLocationMap = rr.mapEntries;
			}
			
			
		}
		
		return new Tableau( seeds, seedToSoilMap, soilToFertilizerMap,
				 fertilizerToWaterMap, waterToLightMap, lightToTemperatureMap,
				temperatureToHumidityMap,humidityToLocationMap);
	}
	
	private static  RetrievalResult retrieve(List<String> lines, int i) {
		List<MapEntry> map = new ArrayList();
		i++;
		String line = lines.get(i);
		boolean eof = false;
		do {
			
			List<Long> mapValues = Arrays.asList(line.split(" ")).stream().mapToLong(Long::parseLong).boxed().collect(Collectors.toList());
			MapEntry me = new MapEntry(mapValues.get(0), mapValues.get(1),mapValues.get(2) );
			map.add(me);
			if (i<lines.size()-1) {
				i++;
				line = lines.get(i);
			} else {
				eof = true;
			}
			
		}
		while (!eof && line.length()>0);
		return new RetrievalResult(i, map);
	}
	
	public static class RetrievalResult {
		int i;
		List<MapEntry> mapEntries;
		public RetrievalResult(int i, List<MapEntry> mapEntries) {
			super();
			this.i = i;
			this.mapEntries = mapEntries;
		}
		
		
	}
	
	public static class MapEntry {
		private final long destinationRangeStart;
		private final long sourceRangeStart;
		private final long rangeLength;
		public MapEntry(long destinationRangeStart, long sourceRangeStart, long rangeLength) {
			super();
			this.destinationRangeStart = destinationRangeStart;
			this.sourceRangeStart = sourceRangeStart;
			this.rangeLength = rangeLength;
		}
		
		public long calculateDestination(long source) {
			if (source >= sourceRangeStart && source < (sourceRangeStart+rangeLength) ) {
				return destinationRangeStart +  source - sourceRangeStart;
			}
			return -1;
		}
		
		@Override
		public String toString() {
			return "MapEntry [destinationRangeStart=" + destinationRangeStart + ", sourceRangeStart=" + sourceRangeStart
					+ ", rangeLength=" + rangeLength + "]";
		}
		
		
	}

}
