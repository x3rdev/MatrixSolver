package main;

import matrix.Matrix;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

public class MatrixSolverApp implements Runnable {

	DefaultTableModel m0Model = initTableModel();
	DefaultTableModel m1Model = initTableModel();
	DefaultTableModel resultModel = initResultModel();
	MatrixCellRenderer cellRenderer = initCellRenderer();
	JTable matrix0 = initMatrix0();
	JTable matrix1 = initMatrix1();
	JTable resultMatrix = initResultMatrix();
	JTextField errorField = initErrorField();
	JTextField dimensionField = initDimensionField();
	JDialog resultFrame = initResultFrame();
	JFrame frame = initFrame();

	
	List<Component> componentList = new ArrayList<>();
	Matrix returnMatrix;

	public static MatrixSolverApp INSTANCE = new MatrixSolverApp();

	public static void main(String[] args) {
		SwingUtilities.invokeLater(INSTANCE);
	}

	@Override
	public void run() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setPreferredSize(new Dimension(600, 600));
		frame.setLayout(null);
		frame.setResizable(false);
		if(frame.isAlwaysOnTopSupported()) {
			frame.setAlwaysOnTop(true);
		}
		frame.setTitle("Matrix Solver App");

		JButton incRow = initIncRow();
		JButton decRow = initDecRow();
		JButton incCol = initIncCol();
		JButton decCol = initDecCol();
		JButton reset = initResetButton();
		JButton clear0 = initClear0Button();
		JButton clear1 = initClear1Button();
		
		addComponent(matrix0);
		addComponent(matrix1);
		addComponent(incRow);
		addComponent(decRow);
		addComponent(incCol);
		addComponent(decCol);
		addComponent(reset);
		addComponent(clear0);
		addComponent(clear1);
		addComponent(errorField);
		addComponent(dimensionField);

		for (JButton button : OperationButtons.getButtons()) {
			addComponent(button);
		}
		
		resetTables();
		frame.pack();
		frame.setVisible(true);
	}
	
	private JFrame initFrame() {
		JFrame fr = new JFrame();
		fr.addWindowListener(new WindowAdapter() {
			@Override
            public void windowClosing(WindowEvent e)
            {
                resultFrame.dispose();
            }
		});
		return fr;
	}
	
	private void addComponent(Component component) {
		this.componentList.add(component);
		frame.add(component);
	}

	public Matrix getMatrix0() {
		Matrix returnMatrix = new Matrix(new double[matrix0.getRowCount()][matrix0.getColumnCount()]);
		for (int i = 0; i < matrix0.getRowCount(); i++) {
			for (int j = 0; j < matrix0.getColumnCount(); j++) {
				returnMatrix.getArray()[i][j] = Double.parseDouble(matrix0.getValueAt(i, j).toString());
			}
		}
		return returnMatrix;
	}

	public Matrix getMatrix1() {
		Matrix returnMatrix = new Matrix(new double[matrix1.getRowCount()][matrix1.getColumnCount()]);
		for (int i = 0; i < matrix1.getRowCount(); i++) {
			for (int j = 0; j < matrix1.getColumnCount(); j++) {
				Double val = 0.0;
				if (matrix1.getValueAt(i, j) != null && matrix1.getValueAt(i, j) != "") {
					val = Double.parseDouble(matrix1.getValueAt(i, j).toString());
				}
				returnMatrix.getArray()[i][j] = val;
			}
		}
		return returnMatrix;
	}

	private JTable initResultMatrix() {
		JTable jt = new JTable(resultModel) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean editCellAt(int row, int column, EventObject e) {
				return false;
			}
		};
		jt.setBounds(10, 10, 280, 250);
		jt.setFocusable(false);
		jt.setBorder(BorderFactory.createLineBorder(Color.black));
		return jt;
	}

	private JTable initMatrix0() {
		JTable jt = new JTable(m0Model);
		jt.setBounds(10, 10, 280, 280);
		jt.setBorder(BorderFactory.createLineBorder(Color.black));
		jt.setDefaultRenderer(Object.class, cellRenderer);
		jt.setFocusable(true);
		jt.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				int row = jt.rowAtPoint(e.getPoint());
				int col = jt.columnAtPoint(e.getPoint());
				for (int i = 0; i < jt.getRowCount(); i++) {
					for (int j = 0; j < jt.getColumnCount(); j++) {
						if (jt.getValueAt(i, j).toString().strip() == "") {
							jt.setValueAt(0.0, i, j);
						}
					}
				}
				if (row >= 0 && col >= 0) {
					jt.setValueAt("", row, col);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
		return jt;
	}

	private JTable initMatrix1() {
		JTable jt = new JTable(m1Model);
		jt.setBounds(295, 10, 280, 280);
		jt.setBorder(BorderFactory.createLineBorder(Color.black));
		jt.setDefaultRenderer(Object.class, cellRenderer);
		jt.setFocusable(true);
		jt.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				int row = jt.rowAtPoint(e.getPoint());
				int col = jt.columnAtPoint(e.getPoint());
				for (int i = 0; i < jt.getRowCount(); i++) {
					for (int j = 0; j < jt.getColumnCount(); j++) {
						if (jt.getValueAt(i, j).toString().strip() == "") {
							jt.setValueAt(0.0, i, j);
						}
					}
				}
				if (row >= 0 && col >= 0) {
					jt.setValueAt("", row, col);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
		return jt;
	}

	private JDialog initResultFrame() {
		JDialog frame = new JDialog();
		frame.setSize(315, 350);
		frame.setLocation(150, 150);
		frame.setLayout(null);
		frame.setTitle("Result");
		frame.add(resultMatrix);
		frame.add(initToClipboard());
		frame.add(initToMatrix0());
		frame.add(initToMatrix1());
		frame.setVisible(false);
		frame.setFocusable(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		if(frame.isAlwaysOnTopSupported()) {
			frame.setAlwaysOnTop(true);
		}
		frame.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				int height = Math.round(resultMatrix.getHeight() / resultMatrix.getRowCount());
				resultMatrix.setRowHeight(height);
				resultMatrix.setRowHeight(resultMatrix.getRowCount() - 1, height + (resultMatrix.getHeight() - height * resultMatrix.getRowCount()));
			}

			@Override
			public void focusLost(FocusEvent e) {
			}
		});
		return frame;
	}

	private JButton initToClipboard() {
		JButton button = new JButton("Clipboard");
		button.setFocusable(false);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resultFrame.setVisible(false);
				if (returnMatrix != null) {
					StringSelection selection = new StringSelection(returnMatrix.toString());
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					clipboard.setContents(selection, selection);
				}
			}
		});
		button.setBounds(105, 265, 90, 20);
		return button;
	}

	private JButton initToMatrix0() {
		JButton button = new JButton("To A");
		button.setFocusable(false);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resultFrame.setVisible(false);
				for (int i = 0; i < matrix0.getRowCount(); i++) {
					for (int j = 0; j < matrix0.getColumnCount(); j++) {
						matrix0.setValueAt(resultMatrix.getValueAt(i, j), i, j);
					}
				}
			}
		});
		button.setBounds(10, 265, 85, 20);
		return button;
	}

	private JButton initToMatrix1() {
		JButton button = new JButton("To B");
		button.setFocusable(false);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resultFrame.setVisible(false);
				for (int i = 0; i < matrix1.getRowCount(); i++) {
					for (int j = 0; j < matrix1.getColumnCount(); j++) {
						matrix1.setValueAt(resultMatrix.getValueAt(i, j), i, j);
					}
				}
			}
		});
		button.setBounds(205, 265, 85, 20);
		return button;
	}

	private DefaultTableModel initTableModel() {
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnCount(3);
		model.setRowCount(3);
		model.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				int row = e.getFirstRow();
				int col = e.getColumn();
				if (row > -1 && col > -1) {
					Object value = model.getValueAt(row, col);
					if (!isValidEntry(value)) {
						printError("Invalid object \"" + value + "\" at: " + row + ", " + col);
					} else {
						clearErrorField();
					}
				}
			}
		});

		return model;
	}
	
	private DefaultTableModel initResultModel() {
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnCount(3);
		model.setRowCount(3);
		return model;
	}
	
	public void clearErrorField() {
		errorField.setText("Enter values");
		errorField.setForeground(Color.black);
	}

	public void printError(String error) {
		errorField.setText(error);
		errorField.setForeground(Color.red);
	}

	private MatrixCellRenderer initCellRenderer() {
		MatrixCellRenderer render = new MatrixCellRenderer();
		render.setHorizontalAlignment(SwingConstants.CENTER);
		return render;
	}

	private JTextField initErrorField() {
		JTextField field = new JTextField();
		field.setEditable(false);
		field.setForeground(Color.black);
//		field.setFont(new Font("Dialog", Font.PLAIN, 12));
		field.setText("Enter values");
		field.setBounds(10, 535, 560, 20);
		return field;
	}

	private JTextField initDimensionField() {
		JTextField field = new JTextField();
		field.setEditable(false);
		field.setForeground(Color.black);
		field.setFont(new Font("Dialog", Font.PLAIN, 15));
		field.setBounds(265, 470, 50, 20);
		field.setHorizontalAlignment(SwingConstants.CENTER);
		return field;
	}

	private JButton initIncRow() {
		JButton button = new JButton("▼");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				m0Model.setRowCount(m0Model.getRowCount() + 1);
				m1Model.setRowCount(m1Model.getRowCount() + 1);
				resultModel.setRowCount(resultModel.getRowCount() + 1);
				updateTables();
			}
		});
		button.setBounds(10, 500, 100, 25);
		return button;
	}

	private JButton initDecRow() {
		JButton button = new JButton("▲");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (m0Model.getRowCount() > 1) {
					m0Model.setRowCount(m0Model.getRowCount() - 1);
					m1Model.setRowCount(m1Model.getRowCount() - 1);
					resultModel.setRowCount(resultModel.getRowCount() - 1);
				}
				updateTables();
			}
		});
		button.setBounds(120, 500, 100, 25);
		return button;
	}

	private JButton initIncCol() {
		JButton button = new JButton("▶");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				m0Model.setColumnCount(m0Model.getColumnCount() + 1);
				m1Model.setColumnCount(m1Model.getColumnCount() + 1);
				resultModel.setColumnCount(resultModel.getColumnCount() + 1);
				updateTables();
			}
		});
		button.setBounds(360, 500, 100, 25);
		return button;
	}

	private JButton initDecCol() {
		JButton button = new JButton("◀");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (m0Model.getColumnCount() > 1) {
					m0Model.setColumnCount(m0Model.getColumnCount() - 1);
					m1Model.setColumnCount(m1Model.getColumnCount() - 1);
					resultModel.setColumnCount(resultModel.getColumnCount() - 1);
				}
				updateTables();
			}
		});
		button.setBounds(470, 500, 100, 25);
		return button;
	}

	private JButton initResetButton() {
		JButton button = new JButton("Reset");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetTables();
				updateTables();
			}
		});
		button.setBounds(240, 500, 100, 25);
		return button;
	}

	private JButton initClear0Button() {
		JButton button = new JButton("Clear");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < matrix0.getRowCount(); i++) {
					for (int j = 0; j < matrix0.getColumnCount(); j++) {
						matrix0.setValueAt(0.0, i, j);
					}
				}
			}
		});
		button.setBounds(100, 300, 100, 25);
		return button;
	}

	private JButton initClear1Button() {
		JButton button = new JButton("Clear");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < matrix1.getRowCount(); i++) {
					for (int j = 0; j < matrix1.getColumnCount(); j++) {
						matrix1.setValueAt(0.0, i, j);
					}
				}
			}
		});
		button.setBounds(385, 300, 100, 25);
		return button;
	}

	private void resetTables() {
		List<JTable> tabList = new ArrayList<JTable>();
		tabList.add(matrix0);
		tabList.add(matrix1);
		for (JTable table : tabList) {
			((DefaultTableModel) table.getModel()).setColumnCount(3);
			((DefaultTableModel) table.getModel()).setRowCount(3);
			for (int i = 0; i < table.getRowCount(); i++) {
				for (int j = 0; j < table.getColumnCount(); j++) {
					table.setValueAt(0.0, i, j);
				}
			}
		}
		updateTables();
	}

	private void updateTables() {
		List<JTable> tabList = new ArrayList<JTable>();
		tabList.add(matrix0);
		tabList.add(matrix1);
		for (JTable table : tabList) {
			if (table.getRowCount() > 0) {
				int height = Math.round(table.getHeight() / table.getRowCount());
				table.setRowHeight(height);
				table.setRowHeight(table.getRowCount() - 1,
						height + (table.getHeight() - height * table.getRowCount()));
				for (int i = 0; i < table.getRowCount(); i++) {
					for (int j = 0; j < table.getColumnCount(); j++) {
						if (table.getValueAt(i, j) == null) {
							table.setValueAt(0.0, i, j);
						}
					}
				}
			}

		}
		dimensionField.setText(matrix0.getRowCount() + " X " + matrix0.getColumnCount());

		if (matrix0.getRowCount() != matrix0.getColumnCount()) {
			errorField
					.setText("Warning: most operations require the matrix to have the same number of rows and columns");
			errorField.setForeground(Color.orange.darker());
		} else {
			clearErrorField();
		}
	}

	public static boolean isValidEntry(Object object) {
		boolean flag;
		try {
			Double.parseDouble(object.toString());
			flag = true;
		} catch (NumberFormatException e) {
			flag = false;
		}
		return flag;
	}

}
