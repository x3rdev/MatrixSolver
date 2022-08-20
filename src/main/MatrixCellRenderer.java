package main;

import java.awt.Color;
import java.awt.Component;
import java.io.Serial;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class MatrixCellRenderer extends DefaultTableCellRenderer {
	
	@Serial
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

	    //Cells are by default rendered as a JLabel.
	    JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
	    boolean flag = MatrixSolverApp.isValidEntry(l.getText());
	    if(!flag) {
	    	l.setBackground(Color.pink);
	    } else {
	    	l.setBackground(Color.white);
	    }
	  return l;
	}
}
