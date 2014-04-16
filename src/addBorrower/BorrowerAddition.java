/**
 * 
 */
package addBorrower;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import dao.DataConnection;

/**
 * @author Dany
 *
 */
public class BorrowerAddition {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public String addBorrower(BorrowerBean bBean)
	{
		Connection con=null;
		PreparedStatement stt=null;
		PreparedStatement stt2=null;
		Statement cardNoStt=null;
		boolean existCheck=false;
		String existingID="";
		String cardNo="";
		String returnStr="";
		String fName="";
		String sName="";
		String sAddress="";
		String sphone="";
		try {
			con=DataConnection.getConnection();
			String sql="SELECT * FROM borrower WHERE fname=? AND lname=? AND address=?";
			stt = con.prepareStatement(sql);
			fName=bBean.getfName();
			sName=bBean.getlName();
			sAddress=bBean.getsAddress();
			sphone=bBean.getsPhone();
			stt.setString(1, fName);
			stt.setString(2, sName);
			stt.setString(3, sAddress);

	        ResultSet resultSet = stt.executeQuery();
	        
	        while(resultSet.next())
	        {
	        	//System.out.println("Exists in system");
	        	existingID=resultSet.getString("card_no");
	        	existCheck=true;
	        }
	        
	        if(existCheck)
	        {
	        	returnStr= "Exists. ID : "+existingID;
	        }else
	        {
	        	//System.out.println("Not Exists in system");

	        	String cardNoQuery="SELECT MAX(card_no) as card_no FROM borrower";
	        	String insertBorrowerQuery="INSERT INTO borrower VALUES(?,?,?,?,?)";
	        	
	        	cardNoStt = con.createStatement();
	        	ResultSet resultset1 = cardNoStt.executeQuery(cardNoQuery);
	        	while(resultset1.next())
	        	{
	        		cardNo = resultset1.getString("card_no");
	        	}
	        	int card_no=Integer.parseInt(cardNo);
	        	card_no++;
	        	
	        	stt2=con.prepareStatement(insertBorrowerQuery);
	        	stt2.setString(1, Integer.toString(card_no));
	        	stt2.setString(2, fName);
	        	stt2.setString(3, sName);
	        	stt2.setString(4, sAddress);
	        	stt2.setString(5, sphone);
	        	stt2.executeUpdate();

	        	returnStr = Integer.toString(card_no);
	        	
	        }

	        

		} catch (SQLException e) {
			e.printStackTrace();
		}finally
		{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


		return returnStr;
	}

}
