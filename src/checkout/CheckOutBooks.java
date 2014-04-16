package checkout;

import javax.swing.*;

import searchBook.SearchBookAvailability;
import util.AlertUtil;
import util.DisplayUtil;
import util.GUIConstants;
import dao.DMLOperations;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *@author Dany
 */
public class CheckOutBooks extends JPanel {

    //    Using your GUI, be able to check out a book, given the combination of BOOK_COPIES(book_id, branch_id) and BORROWER(Card_no),
// i.e. create a new tuple in BOOK_LOANS. The due_date should be 14 days after the date_out.
//    Each BORROWER is permitted a maximum of 3 BOOK_LOANS. If a BORROWER already has 3 BOOK_LOANS, then the checkout
// (i.e. create new BOOK_LOANS tuple) should fail and return a useful error message.
//    If the number of BOOK_LOANS for a given book at a branch already equals the No_of_copies (i.e. There are no more book copies
// available at your library_branch), then the checkout should fail and return a useful error message.
    public JLabel bookIdLabel;
    public JLabel branchIdLabel;
    public JLabel cardNumberLabel;
    public JTextField bookIdTextField;
    public JTextField branchIdTextField;
    public JTextField cardNumberTextField;
    public JLabel resultLabel;
    public JButton checkOutButton;
    
    public JLabel infoLabel;
    
    String bookId="", branchId="", cardNo="";

    public CheckOutBooks() {
    	
    	
    	infoLabel=DisplayUtil.addInfoLabel("Tool Tip : Check-Out book section helps to checkout the new books  " +
    			"from the libraray. Each borrower will have an unique borrower ID. The book ID will be mapped the borrower ID. " +
    			"A borrower can borrow a maximum of three books at the same time");
    	

        bookIdLabel = new JLabel("Book ID :");
        bookIdLabel.setBounds(GUIConstants._XPOS_LEFT, GUIConstants._YPOS, 100, 20);
        bookIdTextField = new JTextField(20);
        bookIdTextField.setBounds(GUIConstants._XPOS_RIGHT, GUIConstants._YPOS, 200, 20);

        branchIdLabel = new JLabel("Branch ID :");
        branchIdLabel.setBounds(GUIConstants._XPOS_LEFT, GUIConstants._YPOS+30, 100, 20);
        branchIdTextField = new JTextField(20);
        branchIdTextField.setBounds(GUIConstants._XPOS_RIGHT, GUIConstants._YPOS+30, 200, 20);

        cardNumberLabel = new JLabel("Card Number :");
        cardNumberLabel.setBounds(GUIConstants._XPOS_LEFT, GUIConstants._YPOS+60, 100, 20);
        cardNumberTextField = new JTextField(20);
        cardNumberTextField.setBounds(GUIConstants._XPOS_RIGHT, GUIConstants._YPOS+60, 200, 20);

        resultLabel = new JLabel("");
        resultLabel.setBounds(GUIConstants._XPOS_LEFT+50, GUIConstants._YPOS+130, 450, 30);
        //resultLabel.setForeground(Color.red);
        //resultLabel.setFont(new Font("Calibri", Font.BOLD, 15));

        checkOutButton = new JButton("Check Out");
        checkOutButton.setBounds(GUIConstants._XPOS_LEFT+50, GUIConstants._YPOS+110, 100, 20);

        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	bookId=bookIdTextField.getText();
            	branchId=branchIdTextField.getText();
            	cardNo=cardNumberTextField.getText();
            	
            	if(bookId.equals("")||branchId.equals("")||cardNo.equals(""))
            	{
            	if(bookId.equals(""))
            	{
            		AlertUtil.msgbox("Please enter the Book ID");
            		bookIdTextField.requestFocusInWindow();
            	}else if(branchId.equals(""))
            	{
            		AlertUtil.msgbox("Please enter the Branch ID");
            		branchIdTextField.requestFocusInWindow();
            	}else if(cardNo.equals(""))
            	{
            		AlertUtil.msgbox("Please enter the borrower Card No");
            		cardNumberTextField.requestFocusInWindow();
            	}
            	
            	}else
            	{
            	
                CheckOutFields checkOutFields = new CheckOutFields();
                checkOutFields.setCardNumber(cardNo);
                checkOutFields.setBranchId(branchId);
                checkOutFields.setBookId(bookId);
                CheckOutResults checkOutResults = new CheckOutResults();
                try {
                	boolean isBookExist=DMLOperations.isEntryExistInBook(bookId);
                	if(isBookExist)
                	{
                    checkOutResults.checkOutBooks(checkOutFields);
                    resultLabel.setText("The Book has been issued successfully.");
                	}else
                	{
                		AlertUtil.msgbox("Please enter valid Book ID");
                		bookIdTextField.setText("");
                		bookIdTextField.requestFocusInWindow();

                	}
                } catch (GeneralException ex) {
                    JOptionPane.showMessageDialog(new JFrame(),
                            ex.getMessage());
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            }
        });
        setLayout(null);

        add(infoLabel);
        add(bookIdLabel);
        add(bookIdTextField);
        add(branchIdLabel);
        add(branchIdTextField);
        add(cardNumberLabel);
        add(cardNumberTextField);
        add(checkOutButton);
        add(resultLabel);
        setVisible(true);
    }

    public static void main(String args[]) {
        new SearchBookAvailability();
    }
}
