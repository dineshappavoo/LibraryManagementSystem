package checkIn;

import javax.swing.*;

import util.AlertUtil;
import util.DisplayUtil;
import util.GUIConstants;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *@author Dany
 */
public class CheckInScreen extends JPanel {

    public JLabel resultLabel;
    public JLabel bookIdLabel;
    public JLabel cardNumberLabel;
    public JLabel borrowerNameLabel;
    public JTextField bookIdTextField;
    public JTextField cardNumberTextField;
    public JTextField borrowerNameTextField;
    public JButton searchButton;
    public JButton deleteButton;
    
    public JLabel infoLabel;
    
    String bookId="", borrowerName="", cardNo="";

    
    public final JTable table;

    public CheckInScreen() {

/*        super("LIBRARY MANAGEMENT SYSTEM");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 620);
        setMinimumSize(new Dimension(800, 620));*/
    	
    	infoLabel=DisplayUtil.addInfoLabel("Tool Tip : Check-In book section helps to checkin the borrowed books  " +
    			"in the respective branch. Book and branch information will be displayed in the table." +
    			"User can choose a row and checkin a book ");
    	
        bookIdLabel = new JLabel("Book ID :");
        bookIdLabel.setBounds(GUIConstants._XPOS_LEFT, GUIConstants._YPOS, 100, 20);
        bookIdTextField = new JTextField(20);
        bookIdTextField.setBounds(GUIConstants._XPOS_RIGHT, GUIConstants._YPOS, 200, 20);

        cardNumberLabel = new JLabel("Card Number :");
        cardNumberLabel.setBounds(GUIConstants._XPOS_LEFT, GUIConstants._YPOS+30, 100, 20);
        cardNumberTextField = new JTextField(20);
        cardNumberTextField.setBounds(GUIConstants._XPOS_RIGHT, GUIConstants._YPOS+30, 200, 20);

        borrowerNameLabel = new JLabel("Borrower Name :");
        borrowerNameLabel.setBounds(GUIConstants._XPOS_LEFT, GUIConstants._YPOS+60, 120, 20);
        borrowerNameTextField = new JTextField(20);
        borrowerNameTextField.setBounds(GUIConstants._XPOS_RIGHT, GUIConstants._YPOS+60, 200, 20);

        searchButton = new JButton("Search");
        searchButton.setBounds(GUIConstants._XPOS_LEFT+60, GUIConstants._YPOS+120, 100, 20);

        deleteButton = new JButton("Check-In Book");
        deleteButton.setBounds(GUIConstants._XPOS_LEFT, GUIConstants._YPOS+580, 100, 20);
        deleteButton.setVisible(false);

        resultLabel = new JLabel("");
        resultLabel.setBounds(GUIConstants._XPOS_LEFT, GUIConstants._YPOS+150, 450, 30);
        resultLabel.setFont(new Font("Calibri", Font.BOLD, 18));

        table = new JTable();

        table.setVisible(true);
        table.setCellSelectionEnabled(false);
        table.setRowSelectionAllowed(true);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(GUIConstants._XPOS_LEFT, GUIConstants._YPOS+200, 600, 300);
        add(scrollPane);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                //int column = table.getSelectedColumn();

                String[] columnValue = new String[3];
                for(int index=0; index<3; index++)
                {
                    columnValue[index] = (String)table.getValueAt(row, index);
                }
//                CheckInSearchResult checkInSearchResult = new CheckInSearchResult(table, checkInSearchFields);
                try {
                    CheckInSearchResult.removeRecord(columnValue);
                    searchAction();

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	
            	searchAction();
            	
            }
        });
        setLayout(null);

        add(infoLabel);
        add(bookIdLabel);
        add(bookIdTextField);
        add(cardNumberLabel);
        add(cardNumberTextField);
        add(borrowerNameLabel);
        add(borrowerNameTextField);
        add(searchButton);
        add(resultLabel);
        add(deleteButton);
        setVisible(true);
    }

    public void searchAction()
    {
    	
    	bookId=bookIdTextField.getText();
    	cardNo=cardNumberTextField.getText();
    	borrowerName=borrowerNameTextField.getText();
    	
    	if(bookId.equals("")&&cardNo.equals("")&&borrowerName.equals(""))
    	{
    	/*if((bookId.equals("")&&cardNo.equals("")))
    	{*/
    		AlertUtil.msgbox("Please enter the Book ID or card no or borrower name");
    		bookIdTextField.requestFocusInWindow();
    	/*}else if((bookId.equals("")&&(borrowerName.equals(""))))
    	{
    		AlertUtil.msgbox("Please enter the card no or borrower name");
    		borrowerNameTextField.requestFocusInWindow();
    	}*/
    	
    	}else
    	{

        resultLabel.setText("Results");
        table.clearSelection();
        CheckInSearchFields checkInSearchFields = new CheckInSearchFields();
        checkInSearchFields.setBorrowerName(borrowerName);
        checkInSearchFields.setCardNumber(cardNo);
        checkInSearchFields.setBookId(bookId);
        CheckInSearchResult checkInSearchResult = new CheckInSearchResult(table, checkInSearchFields);
        try {
            checkInSearchResult.bindResultsetWithTable();
            deleteButton.setVisible(true);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    
    	}
    }
    
    public static void main(String args[]) {
        new CheckInScreen();
    }
}

