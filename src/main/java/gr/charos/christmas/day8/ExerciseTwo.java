package gr.charos.christmas.day8;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import gr.charos.christmas.Utils;
import gr.charos.christmas.day8.Instructions.Node;

public class ExerciseTwo {
	
	//13830919117339
	public static void main(String args[]) {
		
		List<String> lines = Utils.loadLines(ExerciseTwo.class);
		Instructions instr = Instructions.from(lines);
		
		List<Node> currentNodes = instr.nodesEndingAt("A");
		
		boolean allZ = false;
		int count = 0;
		Map<Node, Integer> nodePerCount = new HashMap<Node, Integer>();
		while(!allZ) {
			currentNodes = currentNodes.stream().map(instr::executeStartingAt).collect(Collectors.toList());
			count++;
			List<Node> finished =  currentNodes.stream().filter(p->p.getName().endsWith("Z")).collect(Collectors.toList());
			if (!finished.isEmpty()) {
				int finshedAt = count;
				finished.forEach(p->nodePerCount.put(p, finshedAt));
				currentNodes.removeAll(finished);
			}
			
			if (currentNodes.isEmpty()) {
				allZ = true;
			}

			
		}
		List<Long> counts = nodePerCount.values().stream().map(Integer::longValue).collect(Collectors.toList());
		
		Long commonMultiple =  counts.stream().mapToLong(Long::valueOf).reduce(1, (a, b) -> a * b);
		
		System.out.println(commonMultiple* instr.getMoves().size());
		
	}
	

}
