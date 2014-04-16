package gui;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *@author Dany
 */

public class ResultSetTableModel extends AbstractTableModel {
    private final ResultSetMetaData resultSetMetaData;
    private final String[] searchBookCols = new String[]{"Book ID", "Branch ID", "Total Copies", "Available Copies"};
    private final String[] checkInCols = new String[]{"Book ID", "Branch ID", "Card No", "Out Date", "Due Date"};

    private List<Record> records;

    private String sourceScreen = null;

    public ResultSetTableModel(ResultSetMetaData resultSetMetaData, List<Record> records) {
        this.resultSetMetaData = resultSetMetaData;
        if (records != null) {
            this.records = records;
        } else {
            this.records = new ArrayList<Record>();
        }

    }

    public int getRowCount() {
        return records.size();
    }

    public int getColumnCount() {
        try {
            return resultSetMetaData.getColumnCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Object getValue(int row, int column) {
        return records.get(row).getValue(column);
    }

    public String getColumnName(int col) {
        if("SearchBooks".equals(getSourceScreen()))
        {
            return searchBookCols[col];
        }
        return checkInCols[col];
    }

    public Class<?> getColumnClass(int col) {
        String className = "";
        try {
            className = resultSetMetaData.getColumnClassName(col + 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return className.getClass();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex > records.size()) {
            return null;
        }
        return records.get(rowIndex).getValue(columnIndex);
    }

    public List<Record> getRecords() {
        return this.records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public String getSourceScreen() {
        return sourceScreen;
    }

    public void setSourceScreen(String sourceScreen) {
        this.sourceScreen = sourceScreen;
    }

}
