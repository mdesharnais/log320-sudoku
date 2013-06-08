package sudoku;

import java.util.Arrays;

public class Main {
	public static boolean isDebugging = false;
	
	public static void main(String[] args) {
        try {
    		solve(new byte[] {
    			0, 0, 0, 0, 0, 0, 0, 0, 0,
    			0, 0, 0, 0, 0, 0, 0, 0, 0,
    			0, 0, 0, 0, 0, 0, 0, 0, 0,
    			0, 0, 0, 0, 0, 0, 0, 0, 0,
    			0, 0, 0, 0, 0, 0, 0, 0, 0,
    			0, 0, 0, 0, 0, 0, 0, 0, 0,
    			0, 0, 0, 0, 0, 0, 0, 0, 0,
    			0, 0, 0, 0, 0, 0, 0, 0, 0,
    			0, 0, 0, 0, 0, 0, 0, 0, 0,
    		});
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
        
		byte[] puzzle = new byte[] {
			1, 0, 7, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 2, 5, 0, 4, 0,
			0, 0, 0, 0, 0, 3, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 7,
			0, 3, 0, 0, 4, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 8, 0, 1,
			0, 5, 0, 0, 0, 0, 0, 2, 0,
			0, 0, 8, 1, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 2, 0, 0, 6
		};
        
		long start = System.nanoTime();
		byte[] solution = solve(puzzle);
		long stop = System.nanoTime();
		
		if (solution != null) {
			System.out.println(Sudoku.printSudoku(solution));
		} else {
			System.err.println("No solution found. Sorry, eh!");
		}
		
		System.out.println("Temps moyen : " + ((double) (stop - start)) / 1000000 + "ms");
		
		
		
	}
	
	private static byte[] solve(byte[] puzzle) {
		boolean[] candidates = new boolean[729];

		// By default, every number is a candidate
		Arrays.fill(candidates, true);
		
		// Next, we remove every but one candidate when we already have the
		// answer and remove the number from the row/col
		for (int x = 0; x < 9; ++x) {
			for (int y = 0; y < 9; ++y) {
				if (puzzle[x*9 + y] != 0) {
					switch (promoteCandidate(puzzle, candidates, x, y, puzzle[x*9 + y] - 1)) {
					case FAILURE:
						return null;
					case SOLVED:
						return puzzle;
					case SUCCESS:
						// Just continue
						break;
					}
				}
			}
		}
		//System.out.println(serializeCandidates(candidates) + "\n");
		return solveBacktracking(puzzle, candidates);
			
	}
	
	private enum PromotionSatus {
		SUCCESS, // The promotion have been successfully completed
		FAILURE, // The promotion have caused one or more cell to remove any remaining candidate
		SOLVED // The promotion have completed the grid
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
	
	private static PromotionSatus promoteCandidate(byte[] puzzle, boolean[] candidates, int x, int y, int z) {
		SubGrid subGrid = subGridPosition[x / 3][y / 3];
		boolean isSolved = true;
		for (int i = 0; i < 9; ++i) {
			for (int j = 0; j < 9; ++j) {
				boolean sameSubGrid = subGridPosition[i / 3][j / 3] == subGrid;
				boolean sameLine = i == x;
				boolean sameColumn = j == y;
				int candidatesCount = 0;
				int winner = 0;
				for (int k = 0; k < 9; ++k) {
					boolean sameNumber = k == z;
					//boolean old = candidates[i*9*9 + j*9 + k];
					
					if (sameLine && sameColumn && sameNumber && sameSubGrid) {
						candidates[i*9*9 + j*9 + k] = true;
						++candidatesCount;
						winner = k;
					} else if ((sameLine && sameColumn && !sameNumber && sameSubGrid)
							|| (sameLine && !sameColumn && sameNumber)
							|| (!sameLine && sameColumn && sameNumber)
							|| (!sameLine && sameNumber && sameSubGrid)) {
						candidates[i*9*9 + j*9 + k] = false;
					} else if (candidates[i*9*9 + j*9 + k]) {
						++candidatesCount;
						winner = k;
					}
					/*
					if (isDebugging && old != candidates[i*9*9 + j*9 + k])
						System.out.println(serializeCandidates(candidates) + "\n");
					*/
				}
				
				switch (candidatesCount) {
				case 0:
					return PromotionSatus.FAILURE;
				case 1:
					if (puzzle[i*9 + j] == 0 && (i != x || j != y)) {
						switch (promoteCandidate(puzzle, candidates, i, j, winner)) {
						case FAILURE:
							return PromotionSatus.FAILURE;
						case SOLVED:
							return PromotionSatus.SOLVED;
						case SUCCESS:
							// Just continue
							break;
						}
					} else {
						puzzle[i*9 + j] = (byte) (winner + 1);
					}
					break;
				default:
					isSolved = false;
					break;
				}
			}
		}
		
		//if (isDebugging) System.out.println(serializeCandidates(candidates) + "\n");
		
		return isSolved ? PromotionSatus.SOLVED : PromotionSatus.SUCCESS;
	}

	private static byte[] solveBacktracking(byte[] puzzle, boolean[] candidates) {
		for (int x = 0; x < 9; ++x) {
			int xoffset = x*9*9;
			for (int y = 0; y < 9; ++y) {
				int yoffset = y*9;
				if (puzzle[x*9 + y] == 0) {
					for (int z = 0; z < 9; ++z) {
						if (candidates[xoffset + yoffset + z]) {
							//if (isDebugging) System.out.println("(" + (x+1) + "," + (y+1) + "," + (z+1) + ")");
							boolean[] tempCandidates = Arrays.copyOf(candidates, 729);
							byte[] tempPuzzle = Arrays.copyOf(puzzle, puzzle.length);
							switch (promoteCandidate(tempPuzzle, tempCandidates, x, y, z)) {
								case FAILURE:
									// Just try the next one.
									break;
								case SOLVED:
									return tempPuzzle;
								case SUCCESS:
									if (solveBacktracking(tempPuzzle, tempCandidates) != null) {
										return tempPuzzle;
									}

									//if (isDebugging) System.out.println(serializeCandidates(candidates) + "\n");
									break;
							}

							//if (isDebugging) System.out.println("-----------");
						}
					}
					
					return null;
				}
			}
		}
		
		return null;
	}
	
	public static String serializeCandidates(boolean[] candidates) {
		StringBuilder builder = new StringBuilder();

		for (int x = 0; x < 9; ++x) {
			for (int y = 0; y < 9; ++y) {
				for (int z = 0; z < 9; ++z) {
					if (candidates[x*9*9 + y*9 + z])
						builder.append((z + 1));
					else
						builder.append(" ");
				}
				if (y < 8) {
					if ((y + 1) % 3 == 0)
						builder.append(" || ");
					else
						builder.append(" | ");
				}

			}
			if (x < 8) {
				builder.append("\n");
				if ((x + 1) % 3 == 0)
					builder.append("----------------------------------++----------------------------------++----------------------------------\n");
			}
		}

		return builder.toString();
	}
}
