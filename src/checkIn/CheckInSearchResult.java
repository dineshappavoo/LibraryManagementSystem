package checkIn;


import gui.Record;
import gui.ResultSetTableModel;

import javax.swing.*;

import dao.DataConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *@author Dinesh Appavoo
 */
public class CheckInSearchResult {
    JTable table = null;
    ResultSet resultSet = null;
    public static Connection con = null;

    public CheckInSearchResult(JTable table, CheckInSearchFields checkInSearchFields) {
        this.table = table;
        fetchSearchResults(checkInSearchFields);
    }

    public void fetchSearchResults(CheckInSearchFields checkInSearchFields) {
        try {

            con=DataConnection.getConnection();

            StringBuilder query = new StringBuilder("select l.book_id, l.branch_id, l.card_no, l.date_out, l.due_date " +
                    "from book_loans l , borrower b " +
                    "where l.card_no = b.card_no ");
            boolean isPrevCondition = false;
            int index = 0;
            if (null != checkInSearchFields.getBookId() && !"".equals(checkInSearchFields.getBookId())) {
                query.append("and l.book_id = ? ");
                index++;
            }
            if (null != checkInSearchFields.getCardNumber() && !"".equals(checkInSearchFields.getCardNumber())) {
                query.append("and l.card_no=? ");
                isPrevCondition = true;
                index++;
            }
            if (null != checkInSearchFields.getBorrowerName() && !"".equals(checkInSearchFields.getBorrowerName())) {
                query.append("and b.fname like ?");
                index++;
            }
            PreparedStatement st = con.prepareStatement(query.toString());
            if (null != checkInSearchFields.getBookId() && !"".equals(checkInSearchFields.getBookId())) {
                st.setString(1, checkInSearchFields.getBookId());
                isPrevCondition = true;
            }
            if (null != checkInSearchFields.getCardNumber() && !"".equals(checkInSearchFields.getCardNumber())) {
                if (isPrevCondition) {
                    st.setString(2, checkInSearchFields.getCardNumber());
                } else {
                    st.setString(1, checkInSearchFields.getCardNumber());
                }
            }
            if (null != checkInSearchFields.getBorrowerName() && !"".equals(checkInSearchFields.getBorrowerName())) {
                st.setString(index, "%"+ checkInSearchFields.getBorrowerName()+"%");
            }

            resultSet = st.executeQuery();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    protected JTable bindResultsetWithTable() throws Exception {
        List<Record> records = new ArrayList<Record>();
        Object[] values;
        while (resultSet.next()) {
            values = new Object[5];
            values[0] = resultSet.getString("book_Id");
            values[1] = resultSet.getString("branch_id");
            values[2] = resultSet.getString("card_no");
            values[3] = resultSet.getDate("date_out");
            values[4] = resultSet.getDate("due_date");
            Record record = new Record(values);
            records.add(record);
        }
        process(records);
        return this.table;
    }

    protected void process(List<Record> chunks) {
        ResultSetTableModel tableModel = (this.table.getModel() instanceof ResultSetTableModel ? (ResultSetTableModel) this.table.getModel() : null);
        if (tableModel == null) {
            try {
                tableModel = new ResultSetTableModel(this.resultSet.getMetaData(), chunks);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            tableModel.setSourceScreen("CheckIn");
            this.table.setModel(tableModel);
        } else {
            tableModel.getRecords().clear();
            tableModel.getRecords().addAll(chunks);
        }
        tableModel.fireTableDataChanged();
    }


    public static int removeRecord(String[] parameters) throws SQLException {
        String deleteQuery = "delete from book_loans where book_id=? and branch_id=? and card_no=?";
        PreparedStatement statement = con.prepareStatement(deleteQuery);
        statement.setString(1, parameters[0]);
        statement.setString(2, parameters[1]);
        statement.setString(3, parameters[2]);
        return statement.executeUpdate();
    }
}
