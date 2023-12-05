package gr.charos.christmas.day5;

import java.util.ArrayList;
import java.util.List;

public class RangedSeedTableau  extends Tableau{

	public RangedSeedTableau(Tableau base) {
		super(		base.seeds, 
					base.seedToSoilMap, 
					base.soilToFertilizerMap, 
					base.fertilizerToWaterMap, 
					base.waterToLightMap, 
					base.lightToTemperatureMap,
					base.temperatureToHumidityMap, 
					base.humidityToLocationMap);

		
	
	}
	/*
	private void reprocessSeeds() {
		List<Long> reprocessedSeeds = new ArrayList<>();
		for (int i = 0; i < seeds.size()-1; i++) {
			for (int j = 0; j < seeds.get(i+1).intValue(); j++ ) {
				reprocessedSeeds.add(seeds.get(i)+j);
			}
			System.out.println("completed: " + i);
			i++;
		}
		this.seeds = reprocessedSeeds;
	}
	*/
 
}
