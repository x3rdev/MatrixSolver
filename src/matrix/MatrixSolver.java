package matrix;

import java.util.ArrayList;
import java.util.List;

public class MatrixSolver {
	
	private MatrixSolver() {
		
	}

	// positions not within m0 will be voided
	public static Matrix add(Matrix m0, Matrix m1) {
//		checkMatrix(m0, m1);
		Matrix returnMatrix = new Matrix(new double[m0.getHeight()][m0.getWidth()]);
		for(int i = 0; i < m0.getHeight(); i++) {
			List<Double> matrix0Row = m0.getRow(i);
			List<Double> matrix1Row = m1.getRow(i);
			for(int j = 0; j < m0.getWidth(); j++) {
				returnMatrix.getArray()[i][j] = (matrix0Row.get(j) + matrix1Row.get(j));
			}
		}
		return returnMatrix;
	}
	
	public static Matrix subtract(Matrix m0, Matrix m1)  {
		checkMatrix(m0, m1);
		Matrix returnMatrix = new Matrix(new double[m0.getHeight()][m0.getWidth()]);
		for(int i = 0; i < m0.getHeight(); i++) {
			List<Double> matrix0Row = m0.getRow(i);
			List<Double> matrix1Row = m1.getRow(i);
			for(int j = 0; j < m0.getWidth(); j++) {
				returnMatrix.getArray()[i][j] = (matrix0Row.get(j) - matrix1Row.get(j));
			}
		}
		return returnMatrix;
	}
	
	public static Matrix multiplyByConstant(Matrix matrix, double constant) {
		checkMatrix(matrix);
		Matrix returnMatrix = new Matrix(new double[matrix.getHeight()][matrix.getWidth()]);
		for(int i = 0; i < matrix.getHeight(); i++) {
			List<Double> matrix0Row = matrix.getRow(i);
			for(int j = 0; j < matrix.getWidth(); j++) {
				returnMatrix.getArray()[i][j] = (matrix0Row.get(j) * constant);
			}
		}
	
		return returnMatrix;
		
	}
	
	public static Matrix multiplyByMatrix(Matrix m0, Matrix m1) {
		checkMatrix(m0, m1);
		Matrix returnMatrix = new Matrix(new double[m0.getHeight()][m0.getWidth()]);
		for(int i = 0; i < m0.getHeight(); i++) {
			List<Double> matrix0Row = m0.getRow(i);
			for(int j = 0; j < m0.getWidth(); j++) {
				List<Double> matrix1Column = m1.getColumn(j);
				returnMatrix.getArray()[i][j] = multiplyRowByColumn(matrix0Row, matrix1Column);
			}
		}
		
		return returnMatrix;
	}
	
	public static double multiplyRowByColumn(List<Double> row, List<Double> column) {
		double rVal = 0;
		List<Double> workList = new ArrayList<>();
		for(int i = 0; i < row.size(); i++) {
			workList.add(row.get(i) * column.get(i));
		}
		for(Double d : workList) {
			rVal += d;
		}
		return rVal;
	}
	
	public static void checkMatrix(Matrix... matrices) {
		for(Matrix matrix : matrices) {
			if(matrix.getHeight() != matrix.getWidth()) {
				throw new MatrixException("invalid matrix");
			}
		}
	}
	
	public static Matrix cofactor(Matrix matrix, int index) {
		checkMatrix(matrix);
		if(matrix.getHeight() < 3) throw new MatrixException("Matrix is too small");
		Matrix returnMatrix = new Matrix(new double[matrix.getHeight() - 1][matrix.getWidth() - 1]);
	    
		for(int i = 1; i < matrix.getHeight(); i++) {
			List<Double> rowList = new ArrayList<>();
			for(int j = 0; j < matrix.getWidth(); j++) {
				if(j != index) {
					rowList.add(matrix.getArray()[i][j]);
				}
			}
		
			for(int k = 0; k < rowList.size(); k++) {
				returnMatrix.getArray()[i - 1][k] = rowList.get(k);
			}
		}
		
		return returnMatrix;
	}
	
	public static Matrix cofactor3d(Matrix matrix, int heightIndex, int widthIndex) {
		checkMatrix(matrix);
		Matrix returnMatrix = new Matrix(new double[matrix.getHeight() - 1][matrix.getWidth() - 1]);
//		Thread thread = new Thread() {
//			public void run() {
//				int collectionIndex = 0;
//				for(int i = 0; i < matrix.getHeight(); i++) {
//					if(i != heightIndex) {
//						List<Double> rowList = new ArrayList<Double>();
//						for(int j = 0; j < matrix.getWidth(); j++) {
//							if(j != widthIndex) {
//								rowList.add(matrix.getArray()[i][j]);
//							}
//						}
//						for(int k = 0; k < rowList.size(); k++) {
//							returnMatrix.getArray()[collectionIndex][k] = rowList.get(k);
//						}
//						collectionIndex++;
//					}
//					
//				}
//	    	}
//		};
//		thread.start();
		
		
		int collectionIndex = 0;
		for(int i = 0; i < matrix.getHeight(); i++) {
			if(i != heightIndex) {
				List<Double> rowList = new ArrayList<Double>();
				for(int j = 0; j < matrix.getWidth(); j++) {
					if(j != widthIndex) {
						rowList.add(matrix.getArray()[i][j]);
					}
				}
				for(int k = 0; k < rowList.size(); k++) {
					returnMatrix.getArray()[collectionIndex][k] = rowList.get(k);
				}
				collectionIndex++;
			}
			
		}
		return returnMatrix;
	}
	
	public static double determinant(Matrix matrix) {
		checkMatrix(matrix);
		if(matrix.getSize() < 3) return determinant2(matrix);
		List<Double> sumList = new ArrayList<Double>();
		for(int i = 0; i < matrix.getWidth(); i++) {
			if(cofactor(matrix, i).getHeight() == 2) {
				sumList.add(matrix.getArray()[0][i] * determinant2(cofactor(matrix, i)));
			} else {
				sumList.add(matrix.getArray()[0][i] * determinant(cofactor(matrix, i)));
			}
		}
		
		return additionFlip(sumList);

	}
	
	// for a 2x2 Matrix
	public static double determinant2(Matrix matrix) {
		if(matrix.getHeight() != 2) throw new MatrixException("Matrix is not 2x2");
		double[][] array = matrix.getArray();
		return (array[0][0] * array[1][1]) - (array[0][1] * array[1][0]);
	}
	
	private static double additionFlip(List<Double> sumList) {
		double returnDouble = 0;
		boolean b = true;
		for(Double d : sumList) {
			if(b) {
				b = false;
				returnDouble += d;
			} else {
				b = true;
				returnDouble -= d;
			}
		}
		return returnDouble;	
	}
	
	
	
	public static Matrix minorsMatrix(Matrix matrix) {
		checkMatrix(matrix);
		Matrix returnMatrix = new Matrix(new double[matrix.getHeight()][matrix.getWidth()]);
		for(int i = 0; i < matrix.getHeight(); i++) {

			int iFin = i;
			Thread thread = new Thread(() -> {
				for(int j = 0; j < matrix.getWidth(); j++) {
					returnMatrix.getArray()[iFin][j] = determinant(cofactor3d(matrix, iFin, j));
				}
			});
			thread.start();
			
//			for(int j = 0; j < matrix.getWidth(); j++) {
//				returnMatrix.getArray()[i][j] = determinant(cofactor3d(matrix, i, j));
//			}
		}
		return returnMatrix;
	}
	
	public static Matrix cofactorsMatrix(Matrix matrix) {
		checkMatrix(matrix);
		Matrix returnMatrix = matrix;
		for(int i = 0; i < matrix.getHeight(); i++) {
			for(int j = 0; j < matrix.getWidth(); j++) {
				if((i + j) % 2 != 0) {
					returnMatrix.getArray()[i][j] *= -1;
				}
			}
		}
		return returnMatrix;
	}
	
	public static Matrix transposeMatrix(Matrix matrix) {
		Matrix returnMatrix = new Matrix(new double[matrix.getHeight()][matrix.getWidth()]);
		for(int i = 0; i < matrix.getHeight(); i++) {
			for(int j = 0; j < matrix.getWidth(); j++) {
				returnMatrix.getArray()[i][j] = matrix.getArray()[j][i];
			}
		}
		return returnMatrix;
	}
	
	public static Matrix inverseMatrix(Matrix matrix) {
		checkMatrix(matrix);
		double overDet = 1/determinant(matrix);
		Matrix adjugateMatrix = transposeMatrix(cofactorsMatrix(minorsMatrix(matrix)));
		return multiplyByConstant(adjugateMatrix, overDet);
	}
}
