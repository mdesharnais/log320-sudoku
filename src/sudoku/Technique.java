package sudoku;

public abstract class Technique {
	public abstract boolean[][][] apply(boolean[][][] candidates);
	
	protected enum SubGrid {
		NORTH_WEST, NORTH,  NORTH_EST,
		WEST,       CENTER, EST,
		SOUTH_WEST, SOUTH,  SOUTH_EST
	}
	
	protected static final SubGrid[][] subGridPosition = new SubGrid[][] {
		new SubGrid[] { SubGrid.NORTH_WEST, SubGrid.NORTH,  SubGrid.NORTH_EST },
		new SubGrid[] { SubGrid.WEST,       SubGrid.CENTER, SubGrid.EST       },
		new SubGrid[] { SubGrid.SOUTH_WEST, SubGrid.SOUTH,  SubGrid.SOUTH_EST },
	};
	
	protected static SubGrid getSubGrid(int line, int column) {
		return subGridPosition[line / 3][column / 3];
	}
	
	protected static boolean[][][] promoteCandidate(int x, int y, int number, boolean[][][] candidates) {
		boolean[][][] newCandidates = new boolean[9][9][9];
		SubGrid subGrid = getSubGrid(x, y);
		
		for (int i = 0; i < newCandidates.length; ++i) {
			for (int j = 0; j < newCandidates[i].length; ++j) {
				boolean sameSubGrid = getSubGrid(i, j) == subGrid;
				boolean sameLine = i == x;
				boolean sameColumn = j == y;
				for (int k = 0; k < newCandidates[i][j].length; ++k) {
					boolean sameNumber = k == number - 1;
					
					if (sameLine && sameColumn && sameNumber && sameSubGrid)
						newCandidates[i][j][k] = true;
					else if ((sameLine && sameColumn && !sameNumber && sameSubGrid)
							|| (sameLine && !sameColumn && sameNumber)
							|| (!sameLine && sameColumn && sameNumber)
							|| (!sameLine && sameNumber && sameSubGrid))
						newCandidates[i][j][k] = false;
					else
						newCandidates[i][j][k] = candidates[i][j][k];
				}
			}
		}
		
		return newCandidates;
	}
	
	protected static boolean[][][] removeCandidate(int x, int y, int num, boolean[][][] candidates) {
		boolean[][][] newCandidates = new boolean[9][9][9];
		
		for (int i = 0; i < newCandidates.length; ++i) {
			for (int j = 0; j < newCandidates[i].length; ++j) {
				for (int k = 0; k < newCandidates[i][j].length; ++k) {
					if (i == x && j == y && k == num - 1)
						newCandidates[i][j][k] = false;
					else
						newCandidates[i][j][k] = candidates[i][j][k];
				}
			}
		}
		
		return newCandidates;
	}
}
