package searchBook;

/**
 * 
 * author: Dinesh Appavoo
 */

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import util.AlertUtil;
import util.DisplayUtil;
import util.GUIConstants;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class SearchBookAvailability extends JPanel {

    public JLabel resultLabel;
    public JLabel bookIdLabel;
    public JLabel titleLabel;
    public JLabel authorNameLabel;
    public JTextField bookIdTextField;
    public JTextField titleTextField;
    public JTextField authorNameTextField;
    public JButton searchButton;
    
    public JLabel infoLabel;
    String sBookId="";

    public SearchBookAvailability() {

       /* super("LIBRARY MANAGEMENT SYSTEM");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);   
        setSize(800, 620);
        setMinimumSize(new Dimension(800, 620));*/
    	
    	
    	infoLabel=DisplayUtil.addInfoLabel("Tool Tip : Search book section helps to search books  " +
    			"available in the library system. Books available in all" +
    			" branches will be displayed seperately in the table");
    	
        bookIdLabel = new JLabel("Book ID :");
        bookIdLabel.setBounds(GUIConstants._XPOS_LEFT, GUIConstants._YPOS, 100, 20);
        bookIdTextField = new JTextField(20);
        bookIdTextField.setBounds(GUIConstants._XPOS_RIGHT, GUIConstants._YPOS, 200, 20);

        titleLabel = new JLabel("Title :");
        titleLabel.setBounds(GUIConstants._XPOS_LEFT, GUIConstants._YPOS+30, 100, 20);
        titleTextField = new JTextField(20);
        titleTextField.setBounds(GUIConstants._XPOS_RIGHT, GUIConstants._YPOS+30, 200, 20);

        authorNameLabel = new JLabel("Author Name :");
        authorNameLabel.setBounds(GUIConstants._XPOS_LEFT, GUIConstants._YPOS+60, 100, 20);
        authorNameTextField = new JTextField(20);
        authorNameTextField.setBounds(GUIConstants._XPOS_RIGHT, GUIConstants._YPOS+60, 200, 20);

        searchButton = new JButton("Search");
        searchButton.setBounds(GUIConstants._XPOS_LEFT+60, GUIConstants._YPOS+100, 100, 20);


        resultLabel = new JLabel("");
        resultLabel.setBounds(GUIConstants._XPOS_LEFT, GUIConstants._YPOS+150, 450, 30);
        resultLabel.setFont(new Font("", Font.BOLD, 18));

        final JTable table = new JTable();
        table.setVisible(true);
        table.setCellSelectionEnabled(false);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(GUIConstants._XPOS_LEFT, GUIConstants._YPOS+200, 600, 300);
        add(scrollPane);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if((sBookId=bookIdTextField.getText()).equals(""))
            	{
            		AlertUtil.msgbox("Please enter Book ID");
                    //JOptionPane.showMessageDialog(null,"P	lease enter your name.");
            		bookIdTextField.requestFocusInWindow();
            	}else
            	{
            	
                resultLabel.setText("Results ");
                table.clearSelection();
                BookSearchFields bookSearchFields = new BookSearchFields();
                bookSearchFields.setAuthorName(authorNameTextField.getText());
                bookSearchFields.setTitle(titleTextField.getText());
                bookSearchFields.setBookId(bookIdTextField.getText());
                BookAvailabilityResults bookAvailabilityResults = new BookAvailabilityResults(table, bookSearchFields);
                try {
                    bookAvailabilityResults.doInBackground();
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
        add(titleLabel);
        add(titleTextField);
        add(authorNameLabel);
        add(authorNameTextField);
        add(searchButton);
        add(resultLabel);
        setVisible(true);
    }

    public static void main(String args[]) {
        new SearchBookAvailability();
    }
}
