package searchBook;

/**
 * 
 * User: Dinesh Appavoo
 */

import gui.Record;
import gui.ResultSetTableModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

import dao.DataConnection;

public class BookAvailabilityResults {

    JTable table = null;
    ResultSet resultSet = null;

    public BookAvailabilityResults(JTable table, BookSearchFields bookSearchFields) {
        this.table = table;
        fetchSearchResults(bookSearchFields);
    }

    public void fetchSearchResults(BookSearchFields bookSearchFields) {
        try {

            Connection con = DataConnection.getConnection();

            StringBuilder query = new StringBuilder("SELECT a.book_id, a.branch_id, no_of_copies," +
                    " (no_of_copies - IFNULL(b.num_out, 0)) AS num_avail " +
                    "from (SELECT bc.book_id, bc.branch_id, no_of_copies FROM book_copies bc " +
                    "LEFT OUTER JOIN book_loans bl ON bl.book_id = bc.book_id AND bl.branch_id = bc.branch_id " +
                    "GROUP BY bc.book_id , bc.branch_id) a LEFT JOIN (SELECT bl.book_id, bl.branch_id, " +
                    "COUNT(*) AS num_out FROM book_loans bl JOIN book_copies bc ON bl.book_id = bc.book_id " +
                    "AND bl.branch_id = bc.branch_id GROUP BY bl.book_id , bl.branch_id) b ON a.book_id = b.book_id " +
                    "AND a.branch_id = b.branch_id JOIN book c ON c.book_id = a.book_id JOIN book_authors d " +
                    "ON a.book_id=d.book_id where ");
            boolean isPrevCondition = false;
            int index = 0;
            if (null != bookSearchFields.getBookId() && !"".equals(bookSearchFields.getBookId())) {
                query.append("a.book_id = ? ");
                isPrevCondition = true;
                index++;
            }
            if (null != bookSearchFields.getAuthorName() && !"".equals(bookSearchFields.getAuthorName())) {
                if (isPrevCondition) {
                    query.append("and ");
                }
                query.append("d.author_name=? ");
                isPrevCondition = true;
                index++;
            }
            if (null != bookSearchFields.getTitle() && !"".equals(bookSearchFields.getTitle())) {
                if (isPrevCondition) {
                    query.append("and ");
                }
                query.append("c.title = ? ");
                index++;
            }
            PreparedStatement st = con.prepareStatement(query.toString());
            isPrevCondition = false;
            if (null != bookSearchFields.getBookId() && !"".equals(bookSearchFields.getBookId())) {
                st.setString(1, bookSearchFields.getBookId());
                isPrevCondition = true;
            }
            if (null != bookSearchFields.getAuthorName() && !"".equals(bookSearchFields.getAuthorName())) {
                if (isPrevCondition) {
                    st.setString(2, bookSearchFields.getAuthorName());
                } else {
                    st.setString(1, bookSearchFields.getAuthorName());
                }
            }
            if (null != bookSearchFields.getTitle() && !"".equals(bookSearchFields.getTitle())) {
                st.setString(index, bookSearchFields.getTitle());
            }

            resultSet = st.executeQuery();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    protected JTable doInBackground() throws Exception {
        List<Record> records = new ArrayList<Record>();
        Object[] values;
        while (resultSet.next()) {
            values = new Object[4];
            values[0] = resultSet.getString("book_Id");
            values[1] = resultSet.getString("branch_id");
            values[2] = resultSet.getString("no_of_copies");
            values[3] = resultSet.getString("num_avail");
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
            tableModel.setSourceScreen("SearchBooks");
            this.table.setModel(tableModel);
        } else {
            tableModel.getRecords().clear();
            tableModel.getRecords().addAll(chunks);
        }
        tableModel.fireTableDataChanged();
    }
    
}
