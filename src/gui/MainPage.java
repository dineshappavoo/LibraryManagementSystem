package gui;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import searchBook.SearchBookAvailability;
import checkIn.CheckInScreen;
import checkout.CheckOutBooks;
import addBook.AddBook;
import addBorrower.AddNewBorrower;
import addBranch.AddBranch;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MainPage extends JPanel {
	
        public MainPage() {
                super(new GridLayout(1,1));

                JTabbedPane tabbedPane = new JTabbedPane();
                tabbedPane.setBackground(Color.white);
                tabbedPane.setAlignmentX(LEFT_ALIGNMENT);
                tabbedPane.setAlignmentY(LEFT_ALIGNMENT);
                
                SearchBookAvailability searchPanel=new SearchBookAvailability();
                searchPanel.setBackground(Color.white);
                tabbedPane.addTab("SEARCH BOOKS", searchPanel);
                
                //searchPanel.setPreferredSize(new Dimension(800, 600));

                

                CheckInScreen checkinPanel = new CheckInScreen();
                checkinPanel.setBackground(Color.white);
                tabbedPane.addTab("CHECKIN BOOKS", checkinPanel);
                
                CheckOutBooks checkoutpanel=new CheckOutBooks();
                checkoutpanel.setBackground(Color.white);
                tabbedPane.addTab("CHECKOUT BOOKS", checkoutpanel);
                
                AddNewBorrower addBorrower = new AddNewBorrower();
                addBorrower.setBackground(Color.white);
                tabbedPane.addTab("ADD BORROWER", addBorrower);
                
                AddBook addbook=new AddBook();
                addbook.setBackground(Color.white);
                tabbedPane.addTab("ADD BOOK", addbook);
                
                AddBranch addBranch=new AddBranch();
                addBranch.setBackground(Color.white);
                tabbedPane.addTab("ADD BRANCH", addBranch);

                add(tabbedPane);

                tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);           
        }

        protected JComponent makeTextPanel (String text) {
                JPanel panel = new JPanel(false);
                JLabel filler = new JLabel(text);
                filler.setHorizontalAlignment(JLabel.CENTER);
                panel.setLayout (new GridLayout(1,1));
                panel.add(filler);
                return panel;	
        }

        private static void createAndShowGUI() {
            JFrame frame = new JFrame ("LIBRARY SYSTEM");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new GridLayout(1, 0));
            frame.setMinimumSize(new Dimension(1000, 800));
            frame.setLocationRelativeTo(null);
            frame.add(new MainPage());
            frame.validate();
            frame.pack();
            frame.setVisible(true);
        }

        public static void main(String[] args) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        UIManager.put("swing.boldmetal", Boolean.FALSE);
                        createAndShowGUI();
                    }

                });
        }
}