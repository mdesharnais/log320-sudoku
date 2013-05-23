package sudoku;

import java.util.Map;
import java.util.TreeMap;

// http://www.mots-croises.ch/Manuels/Sudoku/candidat_unique.htm
public class UniqueCandidateColumnTechnique extends Technique {
	@Override
	public boolean[][][] apply(boolean[][][] candidates) {
		boolean[][][] lastPass;
		do {
			lastPass = candidates;
			for (int column = 0; column < candidates[0].length; ++column) {
				Map<Integer,Integer> numberPosition = new TreeMap<Integer,Integer>();
				
				for (int line = 0; line < candidates.length; ++line) {
				
					for (int num = 0; num < candidates[line][column].length; ++num) {
						if (candidates[line][column][num]) {
							if (numberPosition.containsKey(num + 1)) {
								numberPosition.put(num + 1, null);
							} else {
								numberPosition.put(num + 1, line);
							}
						}
					}
				}
				
				for (Map.Entry<Integer,Integer> entry : numberPosition.entrySet()) {
					if (entry.getValue() != null)
						candidates = promoteCandidate(entry.getValue(), column, entry.getKey(), candidates);
				}
			}
		} while (!Utils.equals(lastPass,candidates));
		
		return candidates;
	}
}
