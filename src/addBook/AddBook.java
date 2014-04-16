package addBook;

/**
 * 
 * author: Dinesh Appavoo
 * 
 */

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import util.AlertUtil;
import util.DisplayUtil;
import util.GUIConstants;
import dao.DMLOperations;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class AddBook extends JPanel {

    public JLabel bookId;
    public JLabel title;
    public JTextField sBookIdText;
    public JTextField sTitleText;
    public JLabel successMessage;
    public JLabel branchIdLabel;
    public JComboBox branchesCombo;
    public JLabel noOfCopies;
    public JTextField noOfCopiesText;
    public JLabel author;
    public JTextField authorText;

    public JLabel infoLabel;
    
    public JButton addBook;
    String sBookId="";
    
    public String[] branchNos=null;//{"1","2","3","4","5"};
    
    //public int xPosLeft=50,yPos=20, xPosRight=160;



    public AddBook() {

    	
    	infoLabel=DisplayUtil.addInfoLabel("Tool Tip : Add book section helps to add a new book " +
    			"to the library system. Every branch will be updated with the no of copies." +
    			"If it is a new book to the library system , it will be inserted to the book relation and the book authors relation." +
    			"And new entry will be made in the book copies relation. If it is an existing book and no of copies are getting increased" +
    			" then book copies will be updated with the addition of books");
    	
    	bookId = new JLabel("Book ID :");
    	bookId.setBounds(GUIConstants._XPOS_LEFT, GUIConstants._YPOS, 100, 20);
    	sBookIdText = new JTextField(20);
    	sBookIdText.setBounds(GUIConstants._XPOS_RIGHT, GUIConstants._YPOS, 200, 20);
    	

    	title = new JLabel("Title :");
    	title.setBounds(GUIConstants._XPOS_LEFT, GUIConstants._YPOS+30, 100, 20);
    	sTitleText = new JTextField(20);
    	sTitleText.setBounds(GUIConstants._XPOS_RIGHT, GUIConstants._YPOS+30, 200, 20);
    	

    	author = new JLabel("Author Name :");
    	author.setBounds(GUIConstants._XPOS_LEFT, GUIConstants._YPOS+60, 100, 20);
    	authorText = new JTextField(20);
    	authorText.setBounds(GUIConstants._XPOS_RIGHT, GUIConstants._YPOS+60, 200, 20);
    	

    	
    	branchIdLabel = new JLabel("Branch ID :");
    	branchIdLabel.setBounds(GUIConstants._XPOS_LEFT, GUIConstants._YPOS+90, 100, 20);
    	branchNos=DMLOperations.getBranches();
    	branchesCombo=new JComboBox(branchNos);
    	branchesCombo.setBounds(GUIConstants._XPOS_RIGHT, GUIConstants._YPOS+90, 200, 20);
    	

    	noOfCopies = new JLabel("No. of Copies :");
    	noOfCopies.setBounds(GUIConstants._XPOS_LEFT, GUIConstants._YPOS+120, 100, 20);
    	noOfCopiesText = new JTextField(20);
    	noOfCopiesText.setBounds(GUIConstants._XPOS_RIGHT, GUIConstants._YPOS+120, 200, 20);

    	
    	addBook = new JButton("Add Book");
    	addBook.setBounds(GUIConstants._XPOS_LEFT+50, GUIConstants._YPOS+150, 100, 20);
    	
    	
    	successMessage = new JLabel("");
    	successMessage.setBounds(GUIConstants._XPOS_LEFT, GUIConstants._YPOS+180, 500, 20);


        /*resultLabel = new JLabel("");
        resultLabel.setBounds(30, 190, 450, 30);
        //resultLabel.setForeground(Color.red);
        resultLabel.setFont(new Font("Calibri", Font.BOLD, 25));*/

       // final JTable table = new JTable();
//        table.setMinimumSize(new Dimension(300,300));
//        table.setMaximumSize(new Dimension(300,300));
//        table.setBounds(30,170,300,300);
        //table.setVisible(true);
        //table.setCellSelectionEnabled(false);
        //JScrollPane scrollPane = new JScrollPane(table);
        //scrollPane.setBounds(30, 250, 600, 300);
        //add(scrollPane);
    	addBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	
            	if((sBookId=sBookIdText.getText()).equals(""))
            	{
            		AlertUtil.msgbox("Please enter Book ID");
                    //JOptionPane.showMessageDialog(null,"Please enter your name.");
                    	sBookIdText.requestFocusInWindow();
            	}else
            	{
            	
            	BookBean bookBean = new BookBean();
            	bookBean.setBookId(sBookIdText.getText());
            	bookBean.setsTitle(sTitleText.getText());
            	bookBean.setBrachId(branchesCombo.getSelectedItem().toString().substring(0, 1));
            	bookBean.setsAuthor(authorText.getText());
            	bookBean.setNoOfCopies(noOfCopiesText.getText());
                try {
                    String res=new DMLOperations().insertBook(bookBean);
                    if(res.contains("success"))
                    {
                    	String message = "Book successfully added";
                    	successMessage.setText(message);
                    }else
                    {
                    	successMessage.setText("We are finding issue in adding the book. Please contact the admin");
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
                
            }
        });
        setLayout(null);

        add(infoLabel);
        add(bookId);
        add(title);
        add(author);
        add(authorText);
        add(noOfCopies);
        add(noOfCopiesText);
        add(sBookIdText);
        add(sTitleText);
        add(branchIdLabel);
        add(branchesCombo);
        add(addBook);
        add(successMessage);


        setVisible(true);
    }

    public static void main(String args[]) {
        new AddBook();
    }
    
    public static void addComboBoxes()
    {
    	
    }
}
