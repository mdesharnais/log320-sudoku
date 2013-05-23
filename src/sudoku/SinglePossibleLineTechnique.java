package sudoku;

import java.util.Map;
import java.util.TreeMap;

// http://www.mots-croises.ch/Manuels/Sudoku/jumeaux.htm
public class SinglePossibleLineTechnique extends Technique {
	@Override
	public boolean[][][] apply(boolean[][][] candidates) {
		boolean[][][] lastPass;
		do {
			lastPass = candidates;
			for (int lineFirst = 0, lineLast = 3; lineLast <= candidates.length; lineFirst += 3, lineLast += 3) {
				for (int columnFirst = 0, columnLast = 3; columnLast <= candidates.length; columnFirst += 3, columnLast += 3) {
					Map<Integer,Integer> numberPosition = new TreeMap<Integer,Integer>();
					
					for (int line = lineFirst; line < lineLast; ++line) {
						for (int column = columnFirst; column < columnLast; ++column) {
							for (int i = 0; i < candidates[line][column].length; ++i) {
								if (candidates[line][column][i]) {
									if (numberPosition.containsKey(i + 1)) {
										Integer pos = numberPosition.get(i + 1);
										if (pos != null && pos != line)
											numberPosition.put(i + 1, null);
									} else {
										numberPosition.put(i + 1, line);
									}
								}
							}
						}
					}
					
					for (Map.Entry<Integer,Integer> entry : numberPosition.entrySet()) {
						if (entry.getValue() != null) {
							for (int column = 0; column < 9; ++column) {
								if (column < columnFirst || columnLast <= column) {
									candidates = Technique.removeCandidate(entry.getValue(), column, entry.getKey(), candidates);
								}
							}
						}
					}
				}
			}
		} while (!Utils.equals(lastPass,candidates));
		
		return candidates;
	}
}
