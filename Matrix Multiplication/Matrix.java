package sjsu.yu.cs146.project1.part1;

public class Matrix {

	public Matrix(double[][] arrays) {
		if (arrays.length != arrays[0].length) throw new IllegalArgumentException("Invalid dimensions");
		contents = arrays;
		n = arrays.length;
	}

	private double[][] get() {
		return contents;
	}

	private int getSize() {
		return n;
	}

	private double getAtIndex(int i, int j) {
		return contents[i][j];
	}

	public Matrix multiply(Matrix m) {
		if (m.getSize() != n) throw new IllegalArgumentException("Invalid dimensions");
		double[][] result = new double[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++) {
					result[i][j] += contents[i][k] * m.getAtIndex(k, j);
				}
			}
		}
		return new Matrix(result);
	}

	public Matrix multiplyStrassen(Matrix m) {
		if (m.getSize() != n) throw new IllegalArgumentException("Invalid dimensions");
		double[][] b = m.get();
		double[][] c = recursiveStrassen(contents, b);
		return new Matrix(c);
	}
	
	private double[][] recursiveStrassen(double[][] a, double[][] b) {
		int size = a.length;
		double[][] c = new double[size][size];
		
		double[][] a11, a12, a21, a22, b11, b12, b21, b22;
		double[][] s1, s2, s3, s4, s5, s6, s7, s8, s9, s10;
		double[][] p1, p2, p3, p4, p5, p6, p7;
		double[][] c11, c12, c21, c22;

		/*
		 * Base case: multiplying two 1 x 1 matrices
		 */
		if (size == 1) { c[0][0] = a[0][0] * b[0][0]; }
		else {
			
			size /= 2;

			/*
			 * Step 1 of Strassen: divide input matrices into n/2 x n/2 matrices
			 */
			a11 = part(a, size, 0, 0);
			a12 = part(a, size, 0, size);
			a21 = part(a, size, size, 0);
			a22 = part(a, size, size, size);
			b11 = part(b, size, 0, 0);
			b12 = part(b, size, 0, size);
			b21 = part(b, size, size, 0);
			b22 = part(b, size, size, size);

			/*
			 * Step 2 of Strassen: add/subtract step 1 matrices into 10
			 * matrices S_1, S_2, ... S_10
			 */
			s1 = subtract(b12, b22);
			s2 = add(a11, a12);
			s3 = add(a21, a22);
			s4 = subtract(b21, b11);
			s5 = add(a11, a22);
			s6 = add(b11, b22);
			s7 = subtract(a12, a22);
			s8 = add(b21, b22);
			s9 = subtract(a11, a21);
			s10 = add(b11, b12);

			/* 
			 * Step 3 of Strassen: recursively compute 7 matrix products of size n/2 x n/2
			 */
			p1 = recursiveStrassen(a11, s1);
			p2 = recursiveStrassen(s2, b22);
			p3 = recursiveStrassen(s3, b11);
			p4 = recursiveStrassen(a22, s4);
			p5 = recursiveStrassen(s5, s6);
			p6 = recursiveStrassen(s7, s8);
			p7 = recursiveStrassen(s9, s10);

			/*
			 * Step 4 of Strassen: add/subtract step 3 matrices into submatrices of
			 * the resultant matrix
			 */
			c11 = add(subtract(add(p5, p4), p2), p6);
			c12 = add(p1, p2);
			c21 = add(p3, p4);
			c22 = subtract(subtract(add(p5, p1), p3), p7);

			/*
			 * Combine the submatrices of step 4 into the resultant matrix
			 */
			combine(c11, c, 0, 0);
			combine(c12, c, 0, size);
			combine(c21, c, size, 0);
			combine(c22, c, size, size);

		}

		return c;
	}

	private double[][] add(double[][] a, double[][] b) {
		int size = a.length;
		double[][] c = new double[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				c[i][j] = a[i][j] + b[i][j];
			}
		}
		return c;
	}

	private double[][] subtract(double[][] a, double[][] b) {
		int size = a.length;
		double[][] c = new double[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				c[i][j] = a[i][j] - b[i][j];
			}
		}
		return c;
	}

	/*
	 * Takes values from a parent matrix and writes them into a child
	 * matrix of size n
	 */
	private double[][] part(double[][] parent, int n, int x, int y) {
		double[][] child = new double[n][n];
		for (int i1 = 0, i2 = x; i1 < n; i1++, i2++) {
			for (int j1 = 0, j2 = y; j1 < n; j1++, j2++) {
				child[i1][j1] = parent[i2][j2];
			}
		}
		return child;
	}

	/*
	 * Writes values from a child matrix onto a parent matrix
	 */
	private void combine(double[][] child, double[][] parent, int x, int y) {
		int size = child.length;
		for (int i1 = 0, i2 = x; i1 < size; i1++, i2++) {
			for (int j1 = 0, j2 = y; j1 < size; j1++, j2++) {
				parent[i2][j2] = child[i1][j1];
			}
		}
	}

	public boolean equals(Matrix m) {
		if (m.getSize() != n) return false;
		final double EPSILON = 1E-6;
		double a, b;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				a = contents[i][j];
				b = m.getAtIndex(i, j);
				// Compares only up to the 6th decimal place to compensate for precision issues.
				if (Math.abs(a - b) > EPSILON) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public String toString() {
		String result = "";
		for (int i = 0; i < n; i++) {
			// Tab instead of space for neater formatting.
			result += "[" + "\t";
			for (int j = 0; j < n; j++) {
				result += contents[i][j] + "\t";
			}
			result += "]" + "\n";
		}
		return result;
	}

	private double[][] contents;
	private int n;
}
