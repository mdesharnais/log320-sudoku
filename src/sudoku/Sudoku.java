package sudoku;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Sudoku {
	
	public static void main(String[] args) {
		
		/*
		// Hardest bitchin' puzzle
		byte[][] puzzle = new byte[][] { 
			new byte[] { 7, 0, 8, 0, 0, 0, 3, 0, 0 },
			new byte[] { 0, 0, 0, 2, 0, 1, 0, 0, 0 }, 
			new byte[] { 5, 0, 0, 0, 0, 0, 0, 0, 0 },
			new byte[] { 0, 4, 0, 0, 0, 0, 0, 2, 6 }, 
			new byte[] { 3, 0, 0, 0, 8, 0, 0, 0, 0 },
			new byte[] { 0, 0, 0, 1, 0, 0, 0, 9, 0 }, 
			new byte[] { 0, 9, 0, 6, 0, 0, 0, 0, 4 },
			new byte[] { 0, 0, 0, 0, 7, 0, 5, 0, 0 }, 
			new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 } 
		};
		//*/
		/*
		// Hard, difficulty rating 0.64
		byte[][] puzzle = new byte[][] {
			new byte[] { 5, 7, 4, 0, 0, 0, 1, 0, 6 },
			new byte[] { 6, 2, 0, 0, 0, 5, 0, 0, 0 },
			new byte[] { 1, 0, 9, 0, 6, 0, 0, 0, 0 },
			new byte[] { 0, 0, 0, 6, 7, 0, 0, 0, 2 },
			new byte[] { 8, 0, 0, 0, 0, 0, 0, 0, 4 },
			new byte[] { 7, 0, 0, 0, 9, 8, 0, 0, 0 },
			new byte[] { 0, 0, 0, 0, 5, 0, 9, 0, 1 },
			new byte[] { 0, 0, 0, 3, 0, 0, 0, 8, 7 },
			new byte[] { 2, 0, 7, 0, 0, 0, 4, 6, 3 }
		};
		byte[][] solution = new byte[][] {
			new byte[] { 5, 7, 4, 8, 2, 9, 1, 3, 6 },
			new byte[] { 6, 2, 8, 1, 3, 5, 7, 4, 9 },
			new byte[] { 1, 3, 9, 4, 6, 7, 2, 5, 8 },
			new byte[] { 3, 1, 5, 6, 7, 4, 8, 9, 2 },
			new byte[] { 8, 9, 2, 5, 1, 3, 6, 7, 4 },
			new byte[] { 7, 4, 6, 2, 9, 8, 3, 1, 5 },
			new byte[] { 4, 8, 3, 7, 5, 6, 9, 2, 1 },
			new byte[] { 9, 6, 1, 3, 4, 2, 5, 8, 7 },
			new byte[] { 2, 5, 7, 9, 8, 1, 4, 6, 3 }
		};
		//*/
		/*
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
		// *
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

		// System.out.println(printSudoku(puzzle));
		// System.out.println(printCandidates(findCandidates(puzzle)));

		
		SudoView view = new SudoView();
		
		if (args.length > 0) 
			view.setN(Integer.parseInt(args[0]));
		
		view.setVisible(true);
	}
	
	public static byte[] LoadSudokuFromFile(String filePath) {
		
		BufferedReader fileReader = null;
		byte[] puzzle = new byte[81];
		
		try {
			fileReader = new BufferedReader(new FileReader(filePath));

			for(int i = 0; i < 9; ++i) {
				String line = fileReader.readLine();

				for (int j = 0; j < 9; ++j) {
					puzzle[i*9 + j] = (byte)Integer.parseInt("" + line.charAt(j));
				}
			}
			
		} catch (FileNotFoundException e) {
			System.err.println("File not found.");
			return null;
		} catch (IOException e) {
			System.err.println("Error reading file line.");
			return null;
		} finally {
			if (fileReader != null)
				try {
					fileReader.close();
				} catch (IOException e) {
					System.err.println("Error closing file.");
				}
		}
		
		return puzzle;
	}

	public static String printSudoku(byte[][] puzzle) {
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
	
	public static String printSudoku(byte[] puzzle) {
		StringBuilder builder = new StringBuilder();

		for (int x = 0; x < 9; ++x) {
			for (int y = 0; y < 9; ++y) {
				if (puzzle[x*9 + y] == 0)
					builder.append("  ");
				else
					builder.append(puzzle[x*9 + y] + " ");

				if ((y + 1) % 3 == 0 && y < 8)
					builder.append("| ");
			}
			builder.append("\n");
			if ((x + 1) % 3 == 0 && x < 8)
				builder.append("------+-------+------\n");
		}

		return builder.toString();
	}

	public static String printCandidates(boolean[][][] candidates) {
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
			if (line < candidates.length - 1) {
				builder.append("\n");
				if ((line + 1) % 3 == 0)
					builder
						.append("----------------------------------------------------------++-----------------------------------------------------------++-----------------------------------------------------------\n");
			}
		}

		return builder.toString();
	}

	/*
	private static boolean[][][] findCandidates(byte[][] puzzle) {
		boolean[][][] candidates = new boolean[9][9][9];

		// By default, every number is a candidate
		for (boolean[][] line : candidates) {
			for (boolean[] row : line) {
				Utils.memset(row, true);
			}
		}

		// Next, we remove every but one candidate when we already have the
		// answer and remove the number from the row/col
		for (int line = 0; line < puzzle.length; ++line) {
			for (int column = 0; column < puzzle[line].length; ++column) {
				if (puzzle[line][column] != 0) {
					candidates = Technique.promoteCandidate(line, column, puzzle[line][column], candidates);
				}
			}
		}

		System.out.println(printCandidates(candidates));
		System.out.println("==========");

		List<Technique> techniques = Arrays.asList(new UniqueCandidateSubGridTechnique(),
			new UniqueCandidateLineTechnique(), new UniqueCandidateColumnTechnique(),
			new SinglePossibleLineTechnique(), new AlreadyFoundTechnique());

		boolean[][][] lastPass;
		do {
			lastPass = candidates;
			for (Technique t : techniques) {
				candidates = t.apply(candidates);
				System.out.println(printCandidates(candidates));
				System.out.println("==========");
			}
		} while (!Utils.equals(lastPass, candidates));

		return candidates;
	}
	*/
}
