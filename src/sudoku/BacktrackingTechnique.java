package sudoku;

public class BacktrackingTechnique {

	private static byte[][] puzzle;
	private static byte[][] solution;
	boolean found = false;

	public BacktrackingTechnique(byte[][] p) {
		puzzle = p;
	}

	public byte[][] getSolution() {
		return solution;
	}

	private final void findCandidate(int line, int col) throws Exception {
		int subGridLine = (line / 3) * 3;
		int subGridCol = (col / 3) * 3;
		byte[] currentLine = puzzle[line];
		
		for (byte candidate = 1; candidate < 10; ++candidate) {
			if (currentLine[0] == candidate) continue;
			if (currentLine[1] == candidate) continue;
			if (currentLine[2] == candidate) continue;
			if (currentLine[3] == candidate) continue;
			if (currentLine[4] == candidate) continue;
			if (currentLine[5] == candidate) continue;
			if (currentLine[6] == candidate) continue;
			if (currentLine[7] == candidate) continue;
			if (currentLine[8] == candidate) continue;
			
			if (puzzle[0][col] == candidate) continue;
			if (puzzle[1][col] == candidate) continue;
			if (puzzle[2][col] == candidate) continue;
			if (puzzle[3][col] == candidate) continue;
			if (puzzle[4][col] == candidate) continue;
			if (puzzle[5][col] == candidate) continue;
			if (puzzle[6][col] == candidate) continue;
			if (puzzle[7][col] == candidate) continue;
			if (puzzle[8][col] == candidate) continue;
			
			byte[] subGridLine0 = puzzle[subGridLine + 0];
			if (subGridLine0[subGridCol + 0] == candidate) continue;
			if (subGridLine0[subGridCol + 1] == candidate) continue;
			if (subGridLine0[subGridCol + 2] == candidate) continue;
			
			byte[] subGridLine1 = puzzle[subGridLine + 1];
			if (subGridLine1[subGridCol + 0] == candidate) continue;
			if (subGridLine1[subGridCol + 1] == candidate) continue;
			if (subGridLine1[subGridCol + 2] == candidate) continue;
			
			byte[] subGridLine2 = puzzle[subGridLine + 1];
			if (subGridLine2[subGridCol + 0] == candidate) continue;
			if (subGridLine2[subGridCol + 1] == candidate) continue;
			if (subGridLine2[subGridCol + 2] == candidate) continue;
			
			currentLine[col] = candidate;
			
			if (col < 8)
				solve(line, col + 1);
			else
				solve(line + 1, 0);
		}
	}

	private final static boolean isPuzzleComplete(byte[][] solutionToCheck) {
		for (int line = 0; line < 9; ++line) {
			for (int column = 0; column < 9; ++column) {
				if (solutionToCheck[line][column] == 0)
					return false;
			}
		}
		
		return true;
	}

	public final void solve() throws Exception {
		solve(0, 0);
		
		if (!isPuzzleComplete(puzzle))
			throw new SolutionNotFoundException();
	}

	private final void solve(int line, int col) throws Exception {
		if (line > 8) {
			solution = puzzle;
			throw new SolutionFoundException();
		}

		if (puzzle[line][col] != 0) {
			if (col < 8)
				solve(line, col + 1);
			else
				solve(line + 1, 0);
		} else {

			findCandidate(line, col);
			puzzle[line][col] = 0;
		}

	}
}
