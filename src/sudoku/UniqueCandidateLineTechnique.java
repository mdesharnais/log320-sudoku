package sudoku;

import java.util.Map;
import java.util.TreeMap;

// http://www.mots-croises.ch/Manuels/Sudoku/candidat_unique.htm
public class UniqueCandidateLineTechnique extends Technique {
	@Override
	public boolean[][][] apply(boolean[][][] candidates) {
		boolean[][][] lastPass;
		do {
			lastPass = candidates;
			for (int line = 0; line < candidates.length; ++line) {
				Map<Integer,Integer> numberPosition = new TreeMap<Integer,Integer>();
				
				for (int column = 0; column < candidates[line].length; ++column) {
					for (int num = 0; num < candidates[line][column].length; ++num) {
						if (candidates[line][column][num]) {
							if (numberPosition.containsKey(num + 1)) {
								numberPosition.put(num + 1, null);
							} else {
								numberPosition.put(num + 1, column);
							}
						}
					}
				}
				
				for (Map.Entry<Integer,Integer> entry : numberPosition.entrySet()) {
					if (entry.getValue() != null) {
						int column = entry.getValue();
						candidates = promoteCandidate(line, column, entry.getKey(), candidates);
					}
				}
			}
		} while (!Utils.equals(lastPass,candidates));
		
		return candidates;
	}
}
