package main;

import matrix.Matrix;
import matrix.MatrixException;
import matrix.MatrixSolver;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class OperationButtons {
	
	private static final List<JButton> buttonList = new ArrayList<>();
	
	public static List<JButton> getButtons() {
		buttonList.add(addTwoMatrices());
		buttonList.add(subtractTwoMatrices());
		buttonList.add(negativeMatrix());
		buttonList.add(matrixByMatrix());
		buttonList.add(constantByMatrix());
		buttonList.add(inverseOfMatrix());
		buttonList.add(transposeMatrix());
		buttonList.add(minorMatrix());
		return buttonList;
	}
	
	private static JButton addTwoMatrices() {
		JButton button = new JButton("A + B");
		button.addActionListener(e -> {
			processOperation();
			Matrix matrix0 = MatrixSolverApp.INSTANCE.getMatrix0();
			Matrix matrix1 = MatrixSolverApp.INSTANCE.getMatrix1();
			try {
				Matrix mat = MatrixSolver.add(matrix0, matrix1);
				showResults(mat);
			} catch(MatrixException exception) {
				System.err.println(exception);
				MatrixSolverApp.INSTANCE.printError(exception.toString());
			}
		});
		button = initButton(button, "Add the two matrices together");
		button.setBounds(10, 350, 100, 25);
		return button;
	}
	
	private static JButton subtractTwoMatrices() {
		JButton button = new JButton("A - B");
		button.addActionListener(e -> {
			processOperation();
			Matrix matrix0 = MatrixSolverApp.INSTANCE.getMatrix0();
			Matrix matrix1 = MatrixSolverApp.INSTANCE.getMatrix1();
			try {
				Matrix mat = MatrixSolver.subtract(matrix0, matrix1);
				showResults(mat);
			} catch(Exception exception) {
				System.err.println(exception);
				MatrixSolverApp.INSTANCE.printError(exception.toString());
			}
		});
		button = initButton(button, "Subtract matrix B from matrix A");
		button.setBounds(120, 350, 100, 25);
		return button;
	}
	
	private static JButton negativeMatrix() {
		JButton button = new JButton("-A");
		button.addActionListener(e -> {
			processOperation();
			Matrix matrix0 = MatrixSolverApp.INSTANCE.getMatrix0();
			try {
				Matrix mat = MatrixSolver.multiplyByConstant(matrix0, -1);
				showResults(mat);
			} catch(Exception exception) {
				System.err.println(exception);
				MatrixSolverApp.INSTANCE.printError(exception.toString());
			}
		});
		button = initButton(button, "Multiply every number of matrix A with -1");
		button.setBounds(230, 350, 100, 25);
		return button;
	}
	
	private static JButton matrixByMatrix() {
		JButton button = new JButton("A * B");
		button.addActionListener(e -> {
			processOperation();
			Matrix matrix0 = MatrixSolverApp.INSTANCE.getMatrix0();
			Matrix matrix1 = MatrixSolverApp.INSTANCE.getMatrix1();
			try {
				Matrix mat = MatrixSolver.multiplyByMatrix(matrix0, matrix1);
				showResults(mat);
			} catch(Exception exception) {
				System.err.println(exception);
				MatrixSolverApp.INSTANCE.printError(exception.toString());
			}
		});
		button = initButton(button, "Multiply two matrices together");
		button.setBounds(340, 350, 100, 25);
		return button;
	}
	
	private static JButton constantByMatrix() {
		JButton button = new JButton("A * K");
		button.addActionListener(e -> {
			processOperation();
			Matrix matrix0 = MatrixSolverApp.INSTANCE.getMatrix0();
			try {
				Matrix mat = MatrixSolver.multiplyByConstant(matrix0, Double.parseDouble(JOptionPane.showInputDialog("Input constant")));
				showResults(mat);
			} catch(Exception exception) {
				System.err.println(exception);
				MatrixSolverApp.INSTANCE.printError(exception.toString());
			}
		});
		button = initButton(button, "Multiply every number of matrix A with a constant");
		button.setBounds(450, 350, 100, 25);
		return button;
	}
	
	private static JButton inverseOfMatrix() {
		JButton button = new JButton("A^-1");
		button.addActionListener(e -> {
			Long t0 = System.nanoTime();

			processOperation();
			Matrix matrix0 = MatrixSolverApp.INSTANCE.getMatrix0();
			try {
				Matrix mat = MatrixSolver.inverseMatrix(matrix0);
				showResults(mat);
			} catch(Exception exception) {
				System.err.println(exception);
				MatrixSolverApp.INSTANCE.printError(exception.toString());
			}

			System.out.println(System.nanoTime() - t0);

		});
		button = initButton(button, "Inverse of matrix");
		button.setBounds(10, 400, 100, 25);
		return button;
	}
	
	
	private static JButton transposeMatrix() {
		JButton button = new JButton("A^T");
		button.addActionListener(e -> {
			processOperation();
			Matrix matrix0 = MatrixSolverApp.INSTANCE.getMatrix0();
			try {
				Matrix mat = MatrixSolver.transposeMatrix(matrix0);
				showResults(mat);
			} catch(Exception exception) {
				System.err.println(exception);
				MatrixSolverApp.INSTANCE.printError(exception.toString());
			}
		});
		button = initButton(button, "Transpose matrix");
		button.setBounds(120, 400, 100, 25);
		return button;
	}
	
	private static JButton minorMatrix() {
		JButton button = new JButton("A^M");
		button.addActionListener(e -> {
			processOperation();
			Matrix matrix0 = MatrixSolverApp.INSTANCE.getMatrix0();
			try {
				Matrix mat = MatrixSolver.minorsMatrix(matrix0);
				showResults(mat);
			} catch(Exception exception) {
				System.err.println(exception);
				MatrixSolverApp.INSTANCE.printError(exception.toString());
			}
		});
		button = initButton(button, "Minors matrix");
		button.setBounds(230, 400, 100, 25);
		return button;
	}
	
	//Stuff
	
	private static JButton initButton(JButton button, String description) {
		button.createToolTip();
		button.setToolTipText(description);
		return button;
	}
	
	private static void matrixToJTable(Matrix matrix, JTable table) {
		fixRows(matrix, table);

		for(int i = 0; i < matrix.getArray().length; i++) {
			for(int j = 0; j < matrix.getArray()[0].length; j++) {
				table.setValueAt(matrix.getArray()[i][j], i, j);
			}
		}
	}
	
	private static void fixRows(Matrix matrix, JTable table) {
		if(table.getModel() instanceof DefaultTableModel defaultTableModel) {
			defaultTableModel.setRowCount(matrix.getHeight());
			defaultTableModel.setColumnCount(matrix.getWidth());
		}
	}
	
	private static void showResults(Matrix mat) {
		MatrixSolverApp app = MatrixSolverApp.INSTANCE;
		matrixToJTable(mat, MatrixSolverApp.INSTANCE.resultMatrix);		
		app.returnMatrix = mat;
		app.matrix0.transferFocus();
		app.matrix1.transferFocus();
		for(Component comp : app.componentList) {
			if(comp != app.resultFrame) {
				comp.requestFocusInWindow();
			}
		}
		app.resultFrame.setVisible(true);
		app.resultFrame.requestFocus();
		app.clearErrorField();
	}
	
	private static void processOperation() {
		MatrixSolverApp.INSTANCE.printError("Processing, this may take a while");
		JTable matrix0 = MatrixSolverApp.INSTANCE.matrix0;
		JTable matrix1 = MatrixSolverApp.INSTANCE.matrix1;
		CellEditor m0Editor = matrix0.getCellEditor();
		CellEditor m1Editor = matrix1.getCellEditor();
		if(m0Editor != null) {
			m0Editor.stopCellEditing();
		}
		if(m1Editor != null) {
			m1Editor.stopCellEditing();
		}
		
		List<JTable> tabList = new ArrayList<>();
		tabList.add(matrix0);
		tabList.add(matrix1);
		for (JTable table : tabList) {
			for (int i = 0; i < table.getRowCount(); i++) {
				for (int j = 0; j < table.getColumnCount(); j++) {
					Object value = table.getValueAt(i, j);
					if(!MatrixSolverApp.isValidEntry(value)) {
						value = 0.0;
						table.setValueAt(value, i, j);
					}
				}
			}
		}
	}
	

}
