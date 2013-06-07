package sudoku;

import java.util.Arrays;

public class BacktrackingTechnique {

	private static byte[] puzzle = new byte[81];
	private static byte[] solution;
	boolean found = false;

	/*
	public BacktrackingTechnique(byte[] p) {
		System.arraycopy(p, 0, puzzle, 0, 81);
	}

	public byte[] getSolution() {
		return solution;
	}
	*/

	private static void findCandidate(int line, int col) throws Exception {
		int lineOffset = line*9;
		byte temp0 = puzzle[lineOffset];
		byte temp1 = puzzle[lineOffset + 1];
		byte temp2 = puzzle[lineOffset + 2];
		byte temp3 = puzzle[lineOffset + 3];
		byte temp4 = puzzle[lineOffset + 4];
		byte temp5 = puzzle[lineOffset + 5];
		byte temp6 = puzzle[lineOffset + 6];
		byte temp7 = puzzle[lineOffset + 7];
		byte temp8 = puzzle[lineOffset + 8];
		byte temp10 = puzzle[col];
		byte temp11 = puzzle[9 + col];
		byte temp12 = puzzle[18 + col];
		byte temp13 = puzzle[27 + col];
		byte temp14 = puzzle[36 + col];
		byte temp15 = puzzle[45 + col];
		byte temp16 = puzzle[54 + col];
		byte temp17 = puzzle[63 + col];
		byte temp18 = puzzle[72 + col];
		int subGridLine = (line / 3) * 3;
		int subGridCol = (col / 3) * 3;
		int subGridLineOffset0 = subGridLine*9;
		int subGridLineOffset1 = (subGridLine+1)*9;
		int subGridLineOffset2 = (subGridLine+2)*9;
		int subGridColOffset0 = subGridCol;
		int subGridColOffset1 = subGridCol+1;
		int subGridColOffset2 = subGridCol+2;
		byte temp20 = puzzle[subGridLineOffset0 + subGridColOffset0];
		byte temp21 = puzzle[subGridLineOffset0 + subGridColOffset1];
		byte temp22 = puzzle[subGridLineOffset0 + subGridColOffset2];
		byte temp23 = puzzle[subGridLineOffset1 + subGridColOffset0];
		byte temp24 = puzzle[subGridLineOffset1 + subGridColOffset1];
		byte temp25 = puzzle[subGridLineOffset1 + subGridColOffset2];
		byte temp26 = puzzle[subGridLineOffset2 + subGridColOffset0];
		byte temp27 = puzzle[subGridLineOffset2 + subGridColOffset1];
		byte temp28 = puzzle[subGridLineOffset2 + subGridColOffset2];
		
		for (byte candidate = 1; candidate < 10; ++candidate) {
			if (temp0 == candidate) continue;
			if (temp1 == candidate) continue;
			if (temp2 == candidate) continue;
			if (temp3 == candidate) continue;
			if (temp4 == candidate) continue;
			if (temp5 == candidate) continue;
			if (temp6 == candidate) continue;
			if (temp7 == candidate) continue;
			if (temp8 == candidate) continue;
			
			if (temp10 == candidate) continue;
			if (temp11 == candidate) continue;
			if (temp12 == candidate) continue;
			if (temp13 == candidate) continue;
			if (temp14 == candidate) continue;
			if (temp15 == candidate) continue;
			if (temp16 == candidate) continue;
			if (temp17 == candidate) continue;
			if (temp18 == candidate) continue;
			
			if (temp20 == candidate) continue;
			if (temp21 == candidate) continue;
			if (temp22 == candidate) continue;
			if (temp23 == candidate) continue;
			if (temp24 == candidate) continue;
			if (temp25 == candidate) continue;
			if (temp26 == candidate) continue;
			if (temp27 == candidate) continue;
			if (temp28 == candidate) continue;
			
			puzzle[lineOffset + col] = candidate;
			
			if (col < 8)
				solve(line, col + 1);
			else
				solve(line + 1, 0);
		}
	}

	private static boolean isPuzzleComplete(byte[] solutionToCheck) {
		for (int i = 0; i < 81; ++i) {
			if (solutionToCheck[i] == 0)
				return false;
		}
		
		return true;
	}

	public static void solve(byte[] p) throws Exception {
		System.arraycopy(p, 0, puzzle, 0, 81);
		solve(0, 0);
		
		if (!isPuzzleComplete(puzzle))
			throw new SolutionNotFoundException();
	}

	private static void solve(int line, int col) throws Exception {
		if (line > 8) {
			throw new SolutionFoundException(puzzle);
		}

		if (puzzle[line*9 + col] != 0) {
			if (col < 8)
				solve(line, col + 1);
			else
				solve(line + 1, 0);
		} else {

			findCandidate(line, col);
			puzzle[line*9 + col] = 0;
		}

	}
}
