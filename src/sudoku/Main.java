package sudoku;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

public class Main {
	public static boolean isDebugging = false;
	
	public static void main(String[] args) {
		//*
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
		//*/
        
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
		final int n = 10;
		byte[][] puzzles = new byte[n][];
		for (int i = 0; i < n; ++i) {
			puzzles[i] = Arrays.copyOf(puzzle, puzzle.length);
		}
		
		byte[] solution = null;
		
		long start = System.nanoTime();
		for (int i = 0; i < n; ++i)
			solution = solve(puzzles[i]);
		long stop = System.nanoTime();
		
		if (solution != null) {
			System.out.println(Sudoku.printSudoku(solution));
		} else {
			System.err.println("No solution found. Sorry, eh!");
		}
		
		System.out.println("Temps moyen : " + ((double) (stop - start)) / 1000000 / n + "ms");
	}
	
	private static byte[] solve(byte[] puzzle) {
		boolean[] candidates = new boolean[729];

		// By default, every number is a candidate
		Arrays.fill(candidates, true);
		
		// Next, we remove every but one candidate when we already have the
		// answer and remove the number from the row/col
		int x;
		int y;
		for (x = 0; x < 9; ++x) {
			for (y = 0; y < 9; ++y) {
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
		
		return solveBacktracking(puzzle, candidates);
			
	}
	
	private enum PromotionSatus {
		SUCCESS, // The promotion have been successfully completed
		FAILURE, // The promotion have caused one or more cell to remove any remaining candidate
		SOLVED // The promotion have completed the grid
	}
	
	private static PromotionSatus promoteCandidate(byte[] puzzle, boolean[] candidates, int x, int y, int z) {
		final int subGridLine = x / 3;
		final int subGridCol = y / 3;
		boolean isSolved = true;
		boolean sameSubGridLine;
		boolean sameSubGrid;
		boolean sameLine;
		boolean sameColumn;
		boolean sameNumber;
		int candidatesCount;
		int winner;
		int i;
		int j;
		int k;
		
		for (i = 0; i < 9; ++i) {
			sameSubGridLine = i/3 == subGridLine;
			for (j = 0; j < 9; ++j) {
				sameSubGrid = sameSubGridLine && j/3 == subGridCol;
				sameLine = i == x;
				sameColumn = j == y;
				candidatesCount = 0;
				winner = 0;
				for (k = 0; k < 9; ++k) {
					sameNumber = k == z;
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
		
		////if (isDebugging) System.out.println(serializeCandidates(candidates) + "\n");
		return isSolved ? PromotionSatus.SOLVED : PromotionSatus.SUCCESS;
	}

	private static byte[] solveBacktracking(byte[] puzzle, boolean[] candidates) {
		checkUniqueCandidateSubGrid(puzzle, candidates);
		checkUniqueCandidateLine(puzzle, candidates);
		checkUniqueCandidateColumn(puzzle, candidates);
		checkSinglePossibleLine(puzzle, candidates);
		checkSinglePossibleColumn(puzzle, candidates);
		
		int xoffset;
		int yoffset;
		int offset;
		boolean[] tempCandidates;
		byte[] tempPuzzle;
		int x;
		int y;
		int z;
		for (x = 0; x < 9; ++x) {
			xoffset = x*9*9;
			for (y = 0; y < 9; ++y) {
				yoffset = y*9;
				offset = xoffset + yoffset;
				if (puzzle[x*9 + y] == 0) {
					for (z = 0; z < 9; ++z) {
						if (candidates[offset + z]) {
							// Allocate temporary arrays
							tempCandidates = booleanArrayPool[arrayPoolIndice];
							tempPuzzle = byteArrayPool[arrayPoolIndice];
							++arrayPoolIndice;
							System.arraycopy(candidates, 0, tempCandidates, 0, 729);
							System.arraycopy(puzzle, 0, tempPuzzle, 0, 81);
							
							// Try to backtrack
							switch (promoteCandidate(tempPuzzle, tempCandidates, x, y, z)) {
								case FAILURE:
									// Just try the next one.
									--arrayPoolIndice;
									break;
								case SOLVED:
									--arrayPoolIndice;
									return tempPuzzle;
								case SUCCESS:
									byte[] solution = solveBacktracking(tempPuzzle, tempCandidates);
									--arrayPoolIndice;
									if (solution != null) {
										return solution;
									}

									////if (isDebugging) System.out.println(serializeCandidates(candidates) + "\n");
									break;
							}
						}
					}
					
					return null;
				}
			}
		}
		
		return puzzle;
	}
	
	private static String serializeCandidates(boolean[] candidates) {
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
	private static byte[][] byteArrayPool = new byte[81][81];
	private static boolean[][] booleanArrayPool = new boolean[81][729];
	
	private static int arrayPoolIndice = 0;
	
	private static byte[] allocateByteArray81() {
		return byteArrayPool[arrayPoolIndice++];
	}
	
	private static void deallocateByteArray81() {
		--arrayPoolIndice;
	}
	
	private static boolean[] allocateBooleanArray729() {
		return booleanArrayPool[arrayPoolIndice++];
	}
	
	private static void deallocateBooleanArray729() {
		--arrayPoolIndice;
	}
	
	private static byte[] reverse(byte[] array) {
		byte[] copy = Arrays.copyOf(array, array.length);
		for (int i = 0; i < copy.length / 2; ++i) {
			byte temp = copy[i];
			copy[i] = copy[copy.length - i - 1];
			copy[copy.length - i - 1] = temp;
		}
		
		return copy;
	}
	
	private static void checkSinglePossibleLine(byte[] puzzle, boolean[] candidates) {
		for (byte xFirst = 0, xLast = 3; xLast <= 9; xFirst += 3, xLast += 3) {
			for (byte yFirst = 0, yLast = 3; yLast <= 9; yFirst += 3, yLast += 3) {
				// -1 : you can override
				// -2 : do not touch
				byte[] numberPosition = { -1, -1, -1, -1, -1, -1, -1, -1, -1 };
				
				for (byte x = xFirst; x < xLast; ++x) {
					final int xoffset = x*9*9;
					for (byte y = yFirst; y < yLast; ++y) {
						final int yoffset = y*9;
						if (puzzle[x*9 + y] == 0) {
							for (int z = 0; z < 9; ++z) {
								if (candidates[xoffset + yoffset + z]) {
									switch (numberPosition[z]) {
									case -1:
										numberPosition[z] = x;
										break;
									case -2:
										// Do not touch
										break;
									default:
										if (numberPosition[z] != x) {
											numberPosition[z] = -2;
										}
										break;
									}
								}
							}
						}
					}
				}
				
				for (byte z = 0; z < 9; ++z) {
					if (numberPosition[z] >= 0) {
						for (int y = 0; y < 9; ++y) {
							int x = numberPosition[z];
							if ((y < yFirst || yLast <= y) && candidates[x*9*9 + y*9 + z]) {
								demoteCandidate(puzzle, candidates, x, y, z);
							}
						}
					}
				}
			}
		}
	}
	
	private static void checkSinglePossibleColumn(byte[] puzzle, boolean[] candidates) {
		for (byte xFirst = 0, xLast = 3; xLast <= 9; xFirst += 3, xLast += 3) {
			for (byte yFirst = 0, yLast = 3; yLast <= 9; yFirst += 3, yLast += 3) {
				// -1 : you can override
				// -2 : do not touch
				byte[] numberPosition = { -1, -1, -1, -1, -1, -1, -1, -1, -1 };
				
				for (byte x = xFirst; x < xLast; ++x) {
					final int xoffset = x*9*9;
					for (byte y = yFirst; y < yLast; ++y) {
						final int yoffset = y*9;
						if (puzzle[x*9 + y] == 0) {
							for (int z = 0; z < 9; ++z) {
								if (candidates[xoffset + yoffset + z]) {
									switch (numberPosition[z]) {
									case -1:
										numberPosition[z] = y;
										break;
									case -2:
										// Do not touch
										break;
									default:
										if (numberPosition[z] != y) {
											numberPosition[z] = -2;
										}
										break;
									}
								}
							}
						}
					}
				}
				
				for (byte z = 0; z < 9; ++z) {
					if (numberPosition[z] >= 0) {
						for (int x = 0; x < 9; ++x) {
							int y = numberPosition[z];
							if ((x < xFirst || xLast <= x) && candidates[x*9*9 + y*9 + z]) {
								demoteCandidate(puzzle, candidates, x, y, z);
							}
						}
					}
				}
			}
		}
	}
    
	private static void checkUniqueCandidateSubGrid(byte[] puzzle, boolean[] candidates) {
		for (int xFirst = 0, xLast = 3; xLast <= 9; xFirst += 3, xLast += 3) {
			for (int yFirst = 0, yLast = 3; yLast <= 9; yFirst += 3, yLast += 3) {
				HashMap<Integer,Pair<Integer,Integer>> numberPosition = new HashMap<Integer,Pair<Integer,Integer>>();
					
				for (int x = xFirst; x < xLast; ++x) {
					final int xoffset = x*9*9;
					for (int y = yFirst; y < yLast; ++y) {
						final int yoffset = y*9;
						if (puzzle[x*9 + y] == 0) {
							for (int z = 0; z < 9; ++z) {
								if (candidates[xoffset + yoffset + z]) {
									if (numberPosition.containsKey(z)) {
										numberPosition.put(z, null);
									} else {
										numberPosition.put(z, new Pair<Integer,Integer>(x, y));
									}
								}
							}
						}
					}
				}
				
				for (Map.Entry<Integer,Pair<Integer,Integer>> entry : numberPosition.entrySet()) {
					if (entry.getValue() != null) {
						int line = entry.getValue().item1;
						int column = entry.getValue().item2;
						promoteCandidate(puzzle, candidates, line, column, entry.getKey());
					}
				}
			}
		}
	}
	
	private static void checkUniqueCandidateLine(byte[] puzzle, boolean[] candidates) {
		for (byte x = 0; x < 9; ++x) {
			final int xoffset = x*9*9;
			// -1 : you can override
			// -2 : do not touch
			byte[] numberPosition = { -1, -1, -1, -1, -1, -1, -1, -1, -1 };
			
			for (byte y = 0; y < 9; ++y) {
				final int yoffset = y*9;
				if (puzzle[x*9 + y] == 0) {
					for (byte z = 0; z < 9; ++z) {
						if (candidates[xoffset + yoffset + z]) {
							switch (numberPosition[z]) {
							case -1:
								numberPosition[z] = y;
								break;
							case -2:
								// Nothing to do
								break;
							default:
								numberPosition[z] = -2;
								break;
							}
						}
					}
				}
			}
			
			for (byte z = 0; z < 9; ++z) {
				if (numberPosition[z] >= 0) {
					promoteCandidate(puzzle, candidates, x, numberPosition[z], z);
				}
			}
		}
	}
	
	private static void checkUniqueCandidateColumn(byte[] puzzle, boolean[] candidates) {
		for (byte y = 0; y < 9; ++y) {
			final int yoffset = y*9;
			// -1 : you can override
			// -2 : do not touch
			byte[] numberPosition = { -1, -1, -1, -1, -1, -1, -1, -1, -1 };
			
			for (byte x = 0; x < 9; ++x) {
				final int xoffset = x*9*9;
				for (byte z = 0; z < 9; ++z) {
					if (candidates[xoffset + yoffset + z]) {
						switch (numberPosition[z]) {
						case -1:
							numberPosition[z] = x;
							break;
						case -2:
						default:
							numberPosition[z] = -2;
							break;
						}
					}
				}
			}
			
			for (byte z = 0; z < 9; ++z) {
				if (numberPosition[z] >= 0) {
					promoteCandidate(puzzle, candidates, numberPosition[z], y, z);
				}
			}
		}
	}
	
    private static PromotionSatus demoteCandidate(byte[] puzzle, boolean[] candidates, int x, int y, int z) {
    	final int offset = x*9*9 + y*9;
		int candidatesCount = 0;
		int winner = 0;
		for (int k = 0; k < 9; ++k) {
			if (k == z) {
				candidates[offset + k] = false;
			} else if (candidates[offset + k]) {
				++candidatesCount;
				winner = k;
			}
		}
		
		switch (candidatesCount) {
		case 0:
			return PromotionSatus.FAILURE;
		case 1:
			switch (promoteCandidate(puzzle, candidates, x, y, winner)) {
			case FAILURE:
				return PromotionSatus.FAILURE;
			case SOLVED:
				return PromotionSatus.SOLVED;
			case SUCCESS:
				// Just continue
				break;
			}
			break;
		default:
			// Just continue
			break;
		}
		
		//if (isDebugging) System.out.println(serializeCandidates(candidates) + "\n");
		
		return PromotionSatus.SUCCESS;
    }
}
