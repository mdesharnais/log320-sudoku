package sudoku;

public class Utils {
	public static void memset(byte[] array, byte val) {
		for (int i = 0; i < array.length; ++i)
			array[i] = val;
	}
	
	public static void memset(short[] array, short val) {
		for (int i = 0; i < array.length; ++i)
			array[i] = val;
	}
	
	public static void memset(int[] array, int val) {
		for (int i = 0; i < array.length; ++i)
			array[i] = val;
	}
	
	public static void memset(long[] array, long val) {
		for (int i = 0; i < array.length; ++i)
			array[i] = val;
	}
	
	public static void memset(boolean[] array, boolean val) {
		for (int i = 0; i < array.length; ++i)
			array[i] = val;
	}
	
	public static void memset(float[] array, float val) {
		for (int i = 0; i < array.length; ++i)
			array[i] = val;
	}
	
	public static void memset(double[] array, double val) {
		for (int i = 0; i < array.length; ++i)
			array[i] = val;
	}
	
	public static boolean equals(boolean[] a, boolean[] b) {
		if (a.length != b.length)
			return false;
		
		for (int i = 0; i < a.length; ++i) {
			if (a[i] != b[i])
				return false;
		}
		
		return true;
	}
	
	public static boolean equals(boolean[][][] a, boolean[][][] b) {
		if (a.length != b.length)
			return false;
		
		for (int i = 0; i < a.length; ++i) {
			if (!equals(a[i], b[i]))
				return false;
		}
		
		return true;
	}
	
	public static boolean equals(boolean[][] a, boolean[][] b) {
		if (a.length != b.length)
			return false;
		
		for (int i = 0; i < a.length; ++i) {
			if (!equals(a[i], b[i]))
				return false;
		}
		
		return true;
	}
	
	public static Ordering compare(int[] a, int[] b) {
		if (a.length < b.length)
			return Ordering.LESS_THAN;
		
		if (a.length > b.length)
			return Ordering.GREATER_THAN;
		
		for (int i = 0; i < a.length; ++i) {
			if (a[i] < b[i])
				return Ordering.LESS_THAN;
			if (a[i] > b[i])
				return Ordering.GREATER_THAN;
		}

		return Ordering.EQUALS;
	}
}
