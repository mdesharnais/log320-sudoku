package sudoku;

import java.util.Map;
import java.util.TreeMap;

// http://www.mots-croises.ch/Manuels/Sudoku/candidat_unique.htm
public class UniqueCandidateSubGridTechnique extends Technique {
	@Override
	public boolean[][][] apply(boolean[][][] candidates) {
		boolean[][][] lastPass;
		do {
			lastPass = candidates;
			for (int lineFirst = 0, lineLast = 3; lineLast <= candidates.length; lineFirst += 3, lineLast += 3) {
				for (int columnFirst = 0, columnLast = 3; columnLast <= candidates.length; columnFirst += 3, columnLast += 3) {
					Map<Integer,Pair<Integer,Integer>> numberPosition = new TreeMap<Integer,Pair<Integer,Integer>>();
					
					for (int line = lineFirst; line < lineLast; ++line) {
						for (int column = columnFirst; column < columnLast; ++column) {
							for (int i = 0; i < candidates[line][column].length; ++i) {
								if (candidates[line][column][i]) {
									if (numberPosition.containsKey(i + 1)) {
										numberPosition.put(i + 1, null);
									} else {
										numberPosition.put(i + 1, new Pair<Integer,Integer>(line, column));
									}
								}
							}
						}
					}
					
					for (Map.Entry<Integer,Pair<Integer,Integer>> entry : numberPosition.entrySet()) {
						if (entry.getValue() != null) {
							int line = entry.getValue().item1;
							int column = entry.getValue().item2;
							candidates = promoteCandidate(line, column, entry.getKey(), candidates);
						}
					}
				}
			}
		} while (!Utils.equals(lastPass,candidates));
		
		return candidates;
	}
}
