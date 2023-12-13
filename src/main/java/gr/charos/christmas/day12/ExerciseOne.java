package gr.charos.christmas.day12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import gr.charos.christmas.Utils;

public class ExerciseOne {

	//7939
	public static void main(String args[]) {
		List<String> lines = Utils.loadLines(ExerciseOne.class);
		long count = lines.stream().map(RecordEntry::new).map(RecordEntry::countEligiblePermutations).mapToLong(Long::valueOf).sum();
		System.out.println(count);

	}

	public static class RecordEntry {
		private final List<Integer> contiguousGroups = new ArrayList<Integer>();
		private final BrokenHotSpringMapEntry brokenMap;

		public RecordEntry(String hotSpringsMap) {
			String[] parts = hotSpringsMap.split(" ");
			this.brokenMap = new BrokenHotSpringMapEntry(parts[0].trim());
			String[] nums = parts[1].trim().split(",");
			
			for (int i = 0; i < nums.length;i++) {
				contiguousGroups.add(Integer.parseInt(nums[i]));
				
			}
			
		}
		
		public long countEligiblePermutations() {
			return brokenMap.evaluated.stream().filter(p->p.evaluated.equals(contiguousGroups)).count();
		}
		
	}

	public static class BrokenHotSpringMapEntry {
		private final String entry;
		private List<FullHotSpringMapEntry> evaluated = new ArrayList<FullHotSpringMapEntry>();

		public BrokenHotSpringMapEntry(String entry) {
			this.entry = entry;
			evaluate();
		}

		private void evaluate() {
			List<Integer> missingPositions = new ArrayList<Integer>();
			for (int i = 0; i < entry.length(); i++) {
				if (Utils.isQuestionMark(entry.charAt(i))) {
					missingPositions.add(i);
				}
			}

			
			
				List<String> permutations = new ArrayList<String>();
			permutations.add(entry);
			
			boolean stillToRun = permutations.stream().anyMatch(ss->ss.indexOf("?")>-1);
			while (stillToRun ) {
				permutations = permutations.stream().map(ExerciseOne::generatePermutations).flatMap(Collection::stream).collect(Collectors.toList());
				stillToRun = permutations.stream().anyMatch(ss->ss.indexOf("?")>-1);
			}


			evaluated = permutations.stream().map(FullHotSpringMapEntry::new).collect(Collectors.toList());
			
		}
	}
	
	private static List<String> generatePermutations(String entry) {
		List<String> perms = new ArrayList<String>();
		StringBuilder s = new StringBuilder(entry);
		int pos = s.indexOf("?");
		for (char c : Arrays.asList('.', '#')) {
			s.setCharAt(pos, c);
			perms.add(s.toString());
		}
		return perms;
	}

	public static class FullHotSpringMapEntry {
		private final String entry;
		private final List<Integer> evaluated = new ArrayList<Integer>();

		public FullHotSpringMapEntry(String entry) {
			this.entry = entry;
			evaluate();
		}

		@Override
		public String toString() {
			return "FullHotSpringMapEntry [entry=" + entry + ", evaluated=" + evaluated + "]";
		}

		private void evaluate() {
			int position = 0;
			while (position < entry.length()) {
				char item = entry.charAt(position);
				int contiguous = 0;

				while (Utils.isPound(item) && position < entry.length()) {
					contiguous++;
					position++;
					if (position < entry.length()) {
						item = entry.charAt(position);
					}

				}
				if (contiguous > 0) {
					evaluated.add(contiguous);
				}
				position++;

			}

		}

	}

}
