package main.tasks.n1.matr;

import java.util.Random;

/**
 *
 * @author piskaryov
 *
 */
public final class Matrix {
    /**
     * 2d array to store matrix.
     */
    private double[][] matr;
    /**
     * creates a matrix with specified length.
     * @param size length of the arrays and their quantity.
     */
    public Matrix(final int size) {
        matr = new double[size][size];
    }
    /**
     * performs addition with another matrix.
     * @param m another matrix, with arbitrary length
     */
    public void sum(Matrix m) {
        double[][]matr2 = m.getMatr();
        for (int i = 0; i < matr.length; i++) {
            for (int j = 0; j < matr.length; j++) {
                matr[i][j] += matr2[i][j];
            }
        }
    }
    /**
     * performs multiplication with another matrix.
     * @param m another matrix, with arbitrary length
     */
    public void multiplicate(Matrix m) {
        double[][]matr2 = m.getMatr();
        double[][] copy = new double[matr.length][matr.length];
        for (int i = 0; i < matr.length; i++) {
            for (int j = 0; j < matr2[0].length; j++) {
                int sum = 0;
                for (int k = 0; k < matr.length; k++) {
                    sum += matr[i][k] * matr2[k][j];
                }
                copy[i][j] = sum;
            }
        }
        matr = copy;
    }
    /**
     * performs transpose of this matrix.
     */
    public void transpose() {
        double[][] copy = new double[matr.length][matr.length];
        for (int i = 0; i < matr.length; i++) {
            for (int j = 0; j < matr.length; j++) {
                copy[j][i] = matr[i][j];
            }
        }
        matr = copy;
    }
    /**
     * output the matrix in default output stream.
     */
    public void printAll() {
        for (int i = 0; i < matr.length; i++) {
            for (int j = 0; j < matr.length; j++) {
                System.out.print(matr[i][j] + " ");
            }
            System.out.println();
        }
    }
    /**
     *
     * @param m substitute another matrix
     */
    public void setMatr(final double[][] m) {
        this.matr = m;
    }
    /**
     *
     * @return retrns the matrix.
     */
    public double[][] getMatr() {
        return matr;
    }
    /**
     *
     * @param args arguments of the function.
     */
    public static void main(final String[] args) {
        final int size = 5;
        final int randomRange = 10;
        Matrix matrix1 = new Matrix(size);
        Matrix matrix2 = new Matrix(size);
        Matrix matrix3 = new Matrix(size);
        double[][]m1 = new double[size][size];
        double[][]m2 = new double[size][size];
        double[][]m3 = new double[size][size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                m1[i][j] = random.nextInt(randomRange);
                m2[i][j] = random.nextInt(randomRange);
                m3[i][j] = random.nextInt(randomRange);
            }
        }
        System.out.println("1");
        matrix1.setMatr(m1);
        matrix1.printAll();
        System.out.println();
        System.out.println("2");
        matrix2.setMatr(m2);
        matrix2.printAll();
        System.out.println();
        System.out.println("3");
        matrix3.setMatr(m3);
        matrix3.printAll();
        System.out.println();
        matrix1.transpose();
        matrix1.printAll();
        System.out.println();
        matrix1.sum(matrix2);
        matrix1.printAll();
        System.out.println();
        matrix1.multiplicate(matrix3);
        matrix1.printAll();
    }

}
