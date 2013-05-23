package sudoku;

// If a cell contain only on candidate, it must necessarily be the good one.
public class AlreadyFoundTechnique extends Technique {
	@Override
	public boolean[][][] apply(boolean[][][] candidates) {
		boolean[][][] lastPass;
		do {
			lastPass = candidates;
			for (int lineFirst = 0, lineLast = 3; lineLast <= candidates.length; lineFirst += 3, lineLast += 3) {
				for (int columnFirst = 0, columnLast = 3; columnLast <= candidates.length; columnFirst += 3, columnLast += 3) {
					for (int line = lineFirst; line < lineLast; ++line) {
						for (int column = columnFirst; column < columnLast; ++column) {
							Integer num = null;
							for (int i = 0; i < candidates[line][column].length; ++i) {
								if (candidates[line][column][i]) {
									if (num == null) {
										num = i + 1;
									} else {
										num = null;
										break;
									}
								}
							}
								
							if (num != null)
								candidates = Technique.promoteCandidate(line, column, num, candidates);
						}
					}
				}
			}
		} while (!Utils.equals(lastPass,candidates));
		
		return candidates;
	}
}
