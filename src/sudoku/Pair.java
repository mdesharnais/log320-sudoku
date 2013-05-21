package sudoku;

public class Pair<T1,T2> {
	
	public T1 item1;
	public T2 item2;
	
	public Pair(T1 t1, T2 t2) {
		item1 = t1;
		item2 = t2;
	}
	
	public String toString() {
		return "(" + item1 + "," + item2 + ")";
	}
}
