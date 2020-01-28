package LCS;

/**
 * LCS - Longest Common Subsequence Time Complexity: O(n*m), where n = x.length,
 * m = y.length. Implemnetion uses dynamic programming.
 * 
 * @author alonp
 *
 */
public class LCS {

	/**
	 * Build the dynamic programming matrix.
	 * 
	 * @param x - String
	 * @param y - String
	 * @return dynamic programming matrix
	 */
	private static int[][] LCSMatrix(String x, String y) {
		int[][] mat = new int[y.length() + 1][x.length() + 1];

		for (int i = 0; i < mat.length; i++) { // O(m)
			mat[i][0] = 0;
		}

		for (int j = 0; j < mat[0].length; j++) { // O(n)
			mat[0][j] = 0;
		}

		// O(m*n)
		for (int i = 1; i < mat.length; i++) {
			for (int j = 1; j < mat[i].length; j++) {
				char curr_x = x.charAt(j - 1);
				char curr_y = y.charAt(i - 1);

				if (curr_x == curr_y) {
					mat[i][j] = mat[i - 1][j - 1] + 1;
				} else {
					mat[i][j] = Math.max(mat[i][j - 1], mat[i - 1][j]);
				}
			}

		}
		return mat;
	}

	/**
	 * Return the length of the LCS.
	 * 
	 * @param x - String
	 * @param y - String
	 * @return LCS
	 */
	public static int LCSCalc(String x, String y) {
		int[][] mat = LCSMatrix(x, y);
		return mat[y.length()][x.length()];
	}

	/**
	 * Returns an example of one LCS from given strings.
	 * 
	 * @param x - String
	 * @param y - String
	 * @return LCS example
	 */
	public static String sequenceExample(String x, String y) {
		int[][] mat = LCSMatrix(x, y);
		String ans = "";
		int i = mat.length - 1, j = mat[0].length - 1;
		int count = mat[i][j];

		while (count > 0) {
			char curr_x = x.charAt(j - 1);
			char curr_y = y.charAt(i - 1);

			if (curr_x == curr_y) {
				ans = curr_x + ans;
				i--;
				j--;
				count--;
			} else if (mat[i][j] == mat[i - 1][j]) {
				i--;
			} else {
				j--;
			}
		}

		return ans;
	}

}
