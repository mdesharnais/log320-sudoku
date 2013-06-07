package sudoku;

public class SolutionFoundException extends Exception {
	public byte[] solution;
	
	public SolutionFoundException(byte[] s) {
		solution = s;
	}
}
