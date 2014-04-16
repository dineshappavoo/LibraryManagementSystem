/**
 * 
 */
package dao;
import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.ResultSet;  
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Dany
 *
 */
public class DataConnection {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		Connection con=null;
		try {  
			Statement stt;
			
            con = DataConnection.getConnection();
            stt = con.createStatement();  
            
            ResultSet resultSet = stt.executeQuery("SELECT * FROM book");  
            
            System.out.println("BOOK ID  		TITLE\n");  
            while (resultSet.next()) {  
                System.out.println(resultSet.getString("book_id") +"		"+resultSet.getString("title") );  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{
        	con.close();
        }
		

	}
	
	public static Connection getConnection()
	{
		Connection con=null;
		try {  
            Class.forName("com.mysql.jdbc.Driver");  
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarydb", "mysql", "mysql");  
        }catch (Exception e) {  
            e.printStackTrace();  
        }
		return con;
	}
	
	
	

}
