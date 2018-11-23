package util;

import java.util.Arrays;

// TODO: Auto-generated Javadoc
/**
 * Helper class: Matrix.
 */
public class Matrix {
	
	/** The row. */
	private int row;
	
	/** The col. */
	private int col;
	
	/** The elements. */
	private double[][] elements;
	
	/**
	 * Instantiates a new matrix.
	 *
	 * @param ele the ele
	 */
	public Matrix(double[][] ele) {
		this.elements = ele;
		this.row = ele.length;
		if (this.row != 0) {
			this.col = ele[0].length;
		} else {
			this.col = 0;
		}
	}
	
	/**
	 * Update.
	 *
	 * @param x the x
	 * @param y the y
	 * @param val the val
	 */
	public void update(int x, int y, double val) {
		this.elements[x][y] = val;
	}
	
	/**
	 * Gets the.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the double
	 */
	public double get(int x, int y) {
		return this.elements[x][y];
	}
	
	/**
	 * Gets the eles.
	 *
	 * @return the eles
	 */
	public double[][] getEles() {
		return this.elements;
	}
	
	/**
	 * Shape.
	 *
	 * @return the int[]
	 */
	public int[] shape() {
		return new int[]{row, col};
	}
	
	/**
	 * Mul.
	 *
	 * @param anotherMatrix the another matrix
	 * @return the matrix
	 * @throws MatrixMultiplyException the matrix multiply exception
	 */
	public Matrix mul(Matrix anotherMatrix) throws MatrixMultiplyException {
		
		if (this.shape()[1] != anotherMatrix.shape()[0]) {
			throw new MatrixMultiplyException();
		}
		
		
		double[][] result = new double[this.shape()[0]][anotherMatrix.shape()[1]];
		for (double[] row: result)
		    Arrays.fill(row, 0.0);
		
		for (int i=0; i<this.shape()[0]; i++) {
			for (int j=0; j<anotherMatrix.shape()[1]; j++) {
				for (int x=0; x<this.shape()[1]; x++) {
					result[i][j] += this.get(i, x)*anotherMatrix.get(x, j);
				}
			}
		}
		
		return new Matrix(result);
	}
	
	/**
	 * Mul.
	 *
	 * @param scalar the scalar
	 * @return the matrix
	 */
	public Matrix mul(double scalar) {
		double[][] result = new double[this.row][this.col];
		for (int i=0; i<this.row; i++) {
			for (int j=0; j<this.col; j++) {
				result[i][j] = this.elements[i][j]*scalar;
			}
		}
		
		return new Matrix(result);
	}
	
	/**
	 * Adds the.
	 *
	 * @param scalar the scalar
	 * @return the matrix
	 */
	public Matrix add(double scalar) {
		double[][] result = new double[this.row][this.col];
		for (int i=0; i<this.row; i++) {
			for (int j=0; j<this.col; j++) {
				result[i][j] = this.elements[i][j]+scalar;
			}
		}
		
		return new Matrix(result);
	}
	
	/**
	 * Sub.
	 *
	 * @param anotherMatrix the another matrix
	 * @return the matrix
	 * @throws MatrixSubException the matrix sub exception
	 */
	public Matrix sub(Matrix anotherMatrix) throws MatrixSubException {
		if (anotherMatrix.shape()[0] != this.row || anotherMatrix.shape()[1] != this.col) {
			throw new MatrixSubException();
		}
		
		double[][] result = new double[this.row][this.col];
		
		for (int i=0; i<this.row; i++) {
			for (int j=0; j<this.col; j++) {
				result[i][j] = this.elements[i][j] - anotherMatrix.getEles()[i][j];
			}
		}
		
		return new Matrix(result);
	}
}