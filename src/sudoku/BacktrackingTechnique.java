package sudoku;

public class BacktrackingTechnique {

	private byte[][] puzzle;
	private byte[][] solution;
	boolean found = false;

	public BacktrackingTechnique(byte[][] puzzle) {
		this.puzzle = puzzle.clone();
	}

	public byte[][] getSolution() {
		return solution;
	}

	public byte[][] getPuzzle() {
		return puzzle;
	}

	private void findCandidate(int line, int col) throws Exception {
		for (byte candidate = 1; candidate < 10; ++candidate)
			if (checkLine(line, candidate) && checkCol(col, candidate) && checkSubgrid(line, col, candidate)) {
				puzzle[line][col] = candidate;
				next(line, col);
			}
	}

	private boolean checkLine(int line, int num) {
		for (int col = 0; col < 9; ++col)
			if (puzzle[line][col] == num)
				return false;

		return true;
	}

	private boolean checkCol(int col, int num) {
		for (int line = 0; line < 9; ++line)
			if (puzzle[line][col] == num)
				return false;

		return true;
	}

	private boolean checkSubgrid(int line, int col, int num) {
		line = (line / 3) * 3;
		col = (col / 3) * 3;

		for (int i = 0; i < 3; ++i)
			for (int j = 0; j < 3; ++j)
				if (puzzle[line + i][col + j] == num)
					return false;

		return true;
	}

	private static boolean isPuzzleComplete(byte[][] solutionToCheck) {
		for (int line = 0; line < 9; ++line) {
			for (int column = 0; column < 9; ++column) {
				if (solutionToCheck[line][column] == 0)
					return false;
			}
		}
		
		return true;
	}

	public void solve() throws Exception {
		solve(0, 0);
		
		if (!isPuzzleComplete(puzzle))
			throw new SolutionNotFoundException();
	}

	private void solve(int line, int col) throws Exception {

		if (line > 8) {
			solution = puzzle;
			throw new SolutionFoundException();
		}

		if (puzzle[line][col] != 0) {
			next(line, col);
		} else {

			findCandidate(line, col);
			puzzle[line][col] = 0;
		}

	}

	public void next(int line, int col) throws Exception {
		if (col < 8)
			solve(line, col + 1);
		else
			solve(line + 1, 0);
	}

}
