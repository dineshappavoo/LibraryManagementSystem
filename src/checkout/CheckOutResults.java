package checkout;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import dao.DataConnection;

/**
 *@author Dany.
 */
public class CheckOutResults {

    //    Each BORROWER is permitted a maximum of 3 BOOK_LOANS. If a BORROWER already has 3 BOOK_LOANS, then the checkout
// (i.e. create new BOOK_LOANS tuple) should fail and return a useful error message.
//    If the number of BOOK_LOANS for a given book at a branch already equals the No_of_copies (i.e. There are no more book copies
// available at your library_branch), then the checkout should fail and return a useful error message.

    public String checkOutBooks(CheckOutFields checkOutFields) throws GeneralException {
        Connection con = null;
        PreparedStatement chkBorrowedBooks = null;
        PreparedStatement chkBookAvailability = null;
        String returnStr = "";
        try {
            con = DataConnection.getConnection();
            String queryForLoanCount = "Select count(*) as loan_count from book_loans where card_no = ?";
            chkBorrowedBooks = con.prepareStatement(queryForLoanCount);
            chkBorrowedBooks.setString(1, checkOutFields.getCardNumber());

            ResultSet loanedBookCount = chkBorrowedBooks.executeQuery();

            if (loanedBookCount.next() && loanedBookCount.getInt("loan_count") >= 3) {
                throw new GeneralException("This card has already reached the limit for books issued");
            }

            String queryForAvailability = "SELECT (no_of_copies - IFNULL(b.num_out, 0)) AS num_avail from " +
                    "(SELECT bc.book_id, bc.branch_id, no_of_copies FROM book_copies bc LEFT OUTER JOIN book_loans bl " +
                    "ON bl.book_id = bc.book_id AND bl.branch_id = bc.branch_id " +
                    "GROUP BY bc.book_id, bc.branch_id) a " +
                    "LEFT JOIN (SELECT bl.book_id, bl.branch_id, COUNT(*) AS num_out FROM book_loans bl JOIN book_copies bc " +
                    "ON bl.book_id = bc.book_id AND bl.branch_id = bc.branch_id " +
                    "GROUP BY bl.book_id, bl.branch_id) b " +
                    "ON a.book_id = b.book_id AND a.branch_id = b.branch_id " +
                    "WHERE a.book_id = ? " +
                    "and a.branch_id = ?";
            chkBookAvailability = con.prepareStatement(queryForAvailability);
            chkBookAvailability.setString(1, checkOutFields.getBookId());
            chkBookAvailability.setString(2, checkOutFields.getBranchId());

            ResultSet availableBookCount = chkBookAvailability.executeQuery();

            if (availableBookCount.next() && availableBookCount.getInt("num_avail") < 1) {
                throw new GeneralException("Requested book is not available in this branch");
            }
            String insertBookLoanQuery = "INSERT INTO book_loans VALUES(?,?,?,?,?)";

            chkBookAvailability = con.prepareStatement(insertBookLoanQuery);
            chkBookAvailability.setString(1, checkOutFields.getBookId());
            chkBookAvailability.setString(2, checkOutFields.getBranchId());
            chkBookAvailability.setString(3, checkOutFields.getCardNumber());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            Calendar c = Calendar.getInstance();

            c.add(Calendar.DATE,14);
            java.util.Date tempDate = c.getTime();
            Date dueDate = new Date(tempDate.getTime());

            chkBookAvailability.setString(4, sdf.format(date));
            chkBookAvailability.setString(5, sdf.format(dueDate) );
            returnStr = Integer.toString(chkBookAvailability.executeUpdate());


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        return returnStr;
    }
}
