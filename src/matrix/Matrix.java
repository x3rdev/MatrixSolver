package matrix;

import java.util.ArrayList;
import java.util.List;

public class Matrix {
	
	private final double[][] matrixArray;

	public Matrix(double[][] array) {
		this.matrixArray = array;
	}

	public double[][] getArray() {
		return matrixArray;
	}
	
	public int getWidth() {
		return this.matrixArray[0].length;
	}
	
	public int getHeight() {
		return this.matrixArray.length;
	}
	
	public int getSize() {
		if(this.getHeight() != this.getWidth()) throw new MatrixException("Invalid matrix " + this);
		return this.getHeight();
	}
	
	public List<Double> getRow(int index) {
		List<Double> row = new ArrayList<>();
		for(int j = 0; j < this.getWidth(); j++) {
			row.add(this.getArray()[index][j]);
		}
		return row;
	}
	
	public List<Double> getColumn(int index) {
		List<Double> row = new ArrayList<>();
		for(int j = 0; j < this.getHeight(); j++) {
			row.add(this.getArray()[j][index]);
		}
		return row;
	}
	
	
	public void printMatrix() {
		for(int i = 0; i < matrixArray.length; i++) {
			StringBuilder s = new StringBuilder("{ ");
			for(int j = 0; j < matrixArray[0].length; j++) {
				s.append(String.format("%.3f", matrixArray[i][j])).append(" ");
			}
			s.append("}");
			if(i != matrixArray.length - 1) {
				s.append(",");
			}
			System.out.println(s);
		}
		System.out.println("");
	}
	
	public String toString() {
		StringBuilder returnString = new StringBuilder();
		for (double[] doubles : matrixArray) {
			for (int j = 0; j < matrixArray[0].length; j++) {
				returnString.append(doubles[j]).append(" ");
			}
			returnString.append("\n");
		}
		return returnString.toString();
	}
}
