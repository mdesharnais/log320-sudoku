package sudoku;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class Sudoku {
	public static void main(String[] args) {
		/*
		byte[][] puzzle = new byte[][] {
			new byte[] { 5, 3, 0, 0, 7, 0, 0, 0, 0 },
			new byte[] { 6, 0, 0, 1, 9, 5, 0, 0, 0 },
			new byte[] { 0, 9, 8, 0, 0, 0, 0, 6, 0 },
			new byte[] { 8, 0, 0, 0, 6, 0, 0, 0, 3 },
			new byte[] { 4, 0, 0, 8, 0, 3, 0, 0, 1 },
			new byte[] { 7, 0, 0, 0, 2, 0, 0, 0, 6 },
			new byte[] { 0, 6, 0, 0, 0, 0, 2, 8, 0 },
			new byte[] { 0, 0, 0, 4, 1, 9, 0, 0, 5 },
			new byte[] { 0, 0, 0, 0, 8, 0, 0, 7, 9 }
		};
		*/
		//*
		// Hard, difficulty rating 0.69
		byte[][] puzzle = new byte[][] {
			new byte[] { 2, 1, 0, 0, 0, 0, 0, 7, 0 },
			new byte[] { 4, 0, 0, 0, 0, 7, 9, 0, 2 },
			new byte[] { 0, 6, 0, 8, 3, 0, 0, 0, 0 },
			new byte[] { 0, 5, 9, 7, 8, 0, 0, 0, 0 },
			new byte[] { 0, 0, 0, 2, 0, 3, 0, 0, 0 },
			new byte[] { 0, 0, 0, 0, 5, 6, 4, 9, 0 },
			new byte[] { 0, 0, 0, 0, 6, 5, 0, 4, 0 },
			new byte[] { 3, 0, 4, 9, 0, 0, 0, 0, 8 },
			new byte[] { 0, 9, 0, 0, 0, 0, 0, 3, 1 }
		};
		byte[][] solution = new byte[][] {
			new byte[] { 2, 1, 8, 5, 4, 9, 3, 7, 6 },
			new byte[] { 4, 3, 5, 6, 1, 7, 9, 8, 2 },
			new byte[] { 9, 6, 7, 8, 3, 2, 5, 1, 4 },
			new byte[] { 6, 5, 9, 7, 8, 4, 1, 2, 3 },
			new byte[] { 7, 4, 1, 2, 9, 3, 8, 6, 5 },
			new byte[] { 8, 2, 3, 1, 5, 6, 4, 9, 7 },
			new byte[] { 1, 8, 2, 3, 6, 5, 7, 4, 9 },
			new byte[] { 3, 7, 4, 9, 2, 1, 6, 5, 8 },
			new byte[] { 5, 9, 6, 4, 7, 8, 2, 3, 1 }
		};
		//*/
		/*
		// Very hard, difficulty rating 0.87
		byte[][] puzzle = new byte[][] {
			new byte[] { 0, 1, 0, 0, 0, 5, 9, 0, 0 },
			new byte[] { 0, 0, 3, 0, 2, 1, 0, 8, 5 },
			new byte[] { 8, 5, 0, 3, 0, 0, 0, 0, 0 },
			new byte[] { 0, 0, 0, 0, 3, 0, 0, 0, 2 },
			new byte[] { 5, 4, 0, 0, 0, 0, 0, 3, 6 },
			new byte[] { 3, 0, 0, 0, 4, 0, 0, 0, 0 },
			new byte[] { 0, 0, 0, 0, 0, 6, 0, 1, 9 },
			new byte[] { 1, 7, 0, 8, 9, 0, 6, 0, 0 },
			new byte[] { 0, 0, 6, 1, 0, 0, 0, 7, 0 }
		};
		byte[][] solution = new byte[][] {
			new byte[] { 2, 1, 4, 7, 8, 5, 9, 6, 3 },
			new byte[] { 6, 9, 3, 4, 2, 1, 7, 8, 5 },
			new byte[] { 8, 5, 7, 3, 6, 9, 2, 4, 1 },
			new byte[] { 7, 6, 1, 5, 3, 8, 4, 9, 2 },
			new byte[] { 5, 4, 2, 9, 1, 7, 8, 3, 6 },
			new byte[] { 3, 8, 9, 6, 4, 2, 1, 5, 7 },
			new byte[] { 4, 3, 8, 2, 7, 6, 5, 1, 9 },
			new byte[] { 1, 7, 5, 8, 9, 3, 6, 2, 4 },
			new byte[] { 9, 2, 6, 1, 5, 4, 3, 7, 8 }
		};
		//*/
		
		System.out.println(printSudoku(puzzle));
		System.out.println(printCandidates(findCandidates(puzzle)));
	}
	
	private static String printSudoku(byte[][] puzzle) {
		StringBuilder builder = new StringBuilder();
		
		for (int line = 0; line < puzzle.length; ++line) {
			for (int row = 0; row < puzzle[line].length; ++row) {
				if (puzzle[line][row] == 0)
					builder.append("  ");
				else
					builder.append(puzzle[line][row] + " ");
				
				if ((row + 1) % 3 == 0 && row < puzzle[line].length - 1)
					builder.append("| ");
			}
			builder.append("\n");
			if ((line + 1) % 3 == 0 && line < puzzle.length - 1)
				builder.append("------+-------+------\n");
		}
		
		return builder.toString();
	}
	
	private static String printCandidates(boolean[][][] candidates) {
		StringBuilder builder = new StringBuilder();
		
		for (int line = 0; line < candidates.length; ++line) {
			for (int column = 0; column < candidates[line].length; ++column) {
				for (int i = 0; i < candidates[line][column].length; ++i) {
					if (candidates[line][column][i])
						builder.append((i + 1) + " ");
					else
						builder.append("  ");
				}
				if (column < candidates[line].length - 1) {
					if ((column + 1) % 3 == 0)
						builder.append("|| ");
					else
						builder.append("| ");
				}
				
			}
			builder.append("\n");
			if ((line + 1) % 3 == 0 && line < candidates.length - 1)
				builder.append("----------------------------------------------------------++-----------------------------------------------------------++-----------------------------------------------------------\n");
		}
		
		return builder.toString();
	}
	
	private enum SubGrid {
		NORTH_WEST, NORTH,  NORTH_EST,
		WEST,       CENTER, EST,
		SOUTH_WEST, SOUTH,  SOUTH_EST
	}
	
	private static final SubGrid[][] subGridPosition = new SubGrid[][] {
		new SubGrid[] { SubGrid.NORTH_WEST, SubGrid.NORTH,  SubGrid.NORTH_EST },
		new SubGrid[] { SubGrid.WEST,       SubGrid.CENTER, SubGrid.EST       },
		new SubGrid[] { SubGrid.SOUTH_WEST, SubGrid.SOUTH,  SubGrid.SOUTH_EST },
	};
	
	private static SubGrid getSubGrid(int line, int column) {
		return subGridPosition[line / 3][column / 3];
	}
	
	private static boolean[][][] promoteCandidate(int x, int y, int number, boolean[][][] candidates) {
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
	
	private static boolean[][][] findCandidates(byte[][] puzzle) {
		boolean[][][] candidates = new boolean[9][9][9];
		
		// By default, every number is a candidate 
		for (boolean[][] line : candidates) {
			for (boolean[] row : line) {
				Utils.memset(row, true);
			}
		}
		
		// Next, we remove every but one candidate when we already have the anwser and remove the number from the row/col
		for (int line = 0; line < puzzle.length; ++line) {
			for (int column = 0; column < puzzle[line].length; ++column) {
				if (puzzle[line][column] != 0) {
					candidates = promoteCandidate(line, column, puzzle[line][column], candidates);
				}
			}
		}
		
		// Now, we can check each sub-grid for a number that have only one possible position
		boolean[][][] lastPass;
		
		do {
			lastPass = candidates;
			for (int lineFirst = 0, lineLast = 3; lineLast <= puzzle.length; lineFirst += 3, lineLast += 3) {
				for (int columnFirst = 0, columnLast = 3; columnLast <= puzzle.length; columnFirst += 3, columnLast += 3) {
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
