package sudoku;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Sudoku {
	
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

}
