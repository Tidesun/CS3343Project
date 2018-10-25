// Warning: space consumption
package util;

import java.util.Arrays;

public class Matrix {
	private int row;
	private int col;
	private double[][] elements;
	
	public Matrix(double[][] ele) {
		this.elements = ele;
		this.row = ele.length;
		if (this.row != 0) {
			this.col = ele[0].length;
		} else {
			this.col = 0;
		}
	}
	
	public void update(int x, int y, double val) {
		this.elements[x][y] = val;
	}
	
	public double get(int x, int y) {
		return this.elements[x][y];
	}
	
	public double[][] getEles() {
		return this.elements;
	}
	
	public int[] shape() {
		return new int[]{row, col};
	}
	
	public Matrix mul(Matrix anotherMatrix) throws MatrixMultiplyException {
		
		if (this.shape()[1] != anotherMatrix.shape()[0]) {
			throw new MatrixMultiplyException();
		}
		
		
		double[][] result = new double[this.shape()[0]][anotherMatrix.shape()[1]];
		Arrays.fill(result, 0);
		
		for (int i=0; i<this.shape()[0]; i++) {
			for (int j=0; j<anotherMatrix.shape()[1]; j++) {
				for (int x=0; x<this.shape()[1]; x++) {
					result[i][j] += this.get(i, x)*anotherMatrix.get(x, j);
				}
			}
		}
		
		return new Matrix(result);
	}
	
	public Matrix mul(double scalar) {
		double[][] result = new double[this.row][this.col];
		for (int i=0; i<this.row; i++) {
			for (int j=0; j<this.col; j++) {
				result[i][j] = this.elements[i][j]*scalar;
			}
		}
		
		return new Matrix(result);
	}
	
	public Matrix add(double scalar) {
		double[][] result = new double[this.row][this.col];
		for (int i=0; i<this.row; i++) {
			for (int j=0; j<this.col; j++) {
				result[i][j] = this.elements[i][j]+scalar;
			}
		}
		
		return new Matrix(result);
	}
	
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