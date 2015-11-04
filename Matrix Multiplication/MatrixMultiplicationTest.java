package sjsu.yu.cs146.project1.part1;

public class MatrixMultiplicationTest {
	
	public static double[][] generator(int n) {
		double[][] m = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                m[i][j] = Math.random()*10;
                // Generates random numbers from 0 to 10
            }
        }
        return m;
	}

	public static void main(String[] args) {
		double[][] a = generator(4);
		double[][] b = generator(4);
		Matrix m1 = new Matrix(a);
		Matrix m2 = new Matrix(b);
		System.out.println(m1);
		Matrix productRegular = m1.multiply(m2);
		Matrix productStrassen = m1.multiplyStrassen(m2);
		System.out.println("Are the matrices the same? " + productStrassen.equals(productRegular));
		System.out.println(productStrassen);

		double[][] c = generator(512);
		double[][] d = generator(512);
		Matrix m3 = new Matrix(c);
		Matrix m4 = new Matrix(d);
		long before = System.currentTimeMillis();
		productRegular = m3.multiply(m4);
		long after = System.currentTimeMillis();
		System.out.println("Brute force algorithm complete. Took " + (after - before) + " ms.");
		before = System.currentTimeMillis();
		productStrassen = m3.multiplyStrassen(m4);
		after = System.currentTimeMillis();
		System.out.println("Strassen algorithm complete. Took " + (after - before) + " ms.");
		System.out.println("Are the matrices the same? " + productStrassen.equals(productRegular));
	}
}