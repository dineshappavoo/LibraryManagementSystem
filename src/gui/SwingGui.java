/**
 * 
 */
package gui;

/**
 * @author Dany
 *
 */
	import javax.swing.JFrame;
	import javax.swing.SwingUtilities;

	public class SwingGui extends JFrame {

	    public SwingGui() {
	        
	       setTitle("Library System");
	       setSize(600, 600);
	       setLocationRelativeTo(null);
	       setDefaultCloseOperation(EXIT_ON_CLOSE);        
	    }
	    

	    public static void main(String[] args) {
	        
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	            	SwingGui ex = new SwingGui();
	                ex.setVisible(true);
	            }
	        });
	    }
	}


