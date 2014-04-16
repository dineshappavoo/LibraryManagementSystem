/**
 * 
 */
package dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import addBook.BookBean;
import addBranch.BranchBean;

/**
 * @author Dany
 *
 */
public class DMLOperations {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//new DMLOperations().insertBook("0324355435", "Web Programming");
		//new DMLOperations().deleteBook("0324355435");
		new DMLOperations().updateBook("0324355435", "Web Project1");

		
	}
	
	public static String[] getBranches()
	{

		// TODO Auto-generated method stub
		Connection con=null;
		String[] branches=null;
		int size=0;
		String sBranchID="";
		String sBranchName="";
		
		try {  
            con = DataConnection.getConnection();
            
            String sql = "SELECT * FROM library_branch";
            PreparedStatement pstt=con.prepareStatement(sql);

            ResultSet resultset=pstt.executeQuery();
            
            if(resultset.last()){
            	size = resultset.getRow(); 
            	resultset.beforeFirst();
            }
            //int size=resultset.getFetchSize();
            
            branches=new String[size];
            int j=0;
            while(resultset.next())
            {
            	 sBranchID=resultset.getString("branch_id");
            	 sBranchName=resultset.getString("branch_name");
            	 branches[j++]=sBranchID+" - "+sBranchName;
            }

        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{
        	
        	try {
        		
				con.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
        }
        return branches;

	}
	
	public String insertBranch(BranchBean bBean)
	{
		Connection con=null;
		PreparedStatement stt=null;
		PreparedStatement stt2=null;
		Statement cardNoStt=null;
		boolean existCheck=false;
		String existingID="";
		String returnStr="";
		String bName="";
		String bAddress="";
		String bID="";
		try {
			con=DataConnection.getConnection();
			String sql="select * from library_branch where branch_name=? and address=?";
			stt = con.prepareStatement(sql);
			bName=bBean.getBranchName();
			bAddress=bBean.getBranchAddress();
			stt.setString(1, bName);
			stt.setString(2, bAddress);

	        ResultSet resultSet = stt.executeQuery();
	        
	        while(resultSet.next())
	        {
	        	//System.out.println("Exists in system");
	        	existingID=resultSet.getString("branch_id");
	        	existCheck=true;
	        }
	        
	        if(existCheck)
	        {
	        	returnStr= "Exists. ID : "+existingID;
	        }else
	        {
	        	//System.out.println("Not Exists in system");

	        	String branchIDQuery="SELECT MAX(branch_id) as branch_id FROM library_branch";
	        	String insertBorrowerQuery="INSERT INTO library_branch VALUES(?,?,?)";
	        	
	        	cardNoStt = con.createStatement();
	        	ResultSet resultset1 = cardNoStt.executeQuery(branchIDQuery);
	        	while(resultset1.next())
	        	{
	        		bID = resultset1.getString("branch_id");
	        	}
	        	int branch_no=Integer.parseInt(bID);
	        	branch_no++;
	        	
	        	stt2=con.prepareStatement(insertBorrowerQuery);
	        	stt2.setString(1, Integer.toString(branch_no));
	        	stt2.setString(2, bName);
	        	stt2.setString(3, bAddress);
	        	stt2.executeUpdate();

	        	returnStr = Integer.toString(branch_no);
	        	
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

	public String insertBook(BookBean bBean)
	{

		// TODO Auto-generated method stub
		Connection con=null;
		 String bookId="";
		 String sTitle="";
		 String sAuthor="";
		 String noOfCopies="";
		 String branchId="";
		try {  
            con = DataConnection.getConnection();
            
            bookId=bBean.getBookId();
            sTitle=bBean.getsTitle();
            sAuthor=bBean.getsAuthor();
            noOfCopies=bBean.getNoOfCopies();
            branchId=bBean.getBrachId();
            
            System.out.println("bookId : "+bookId+" sTitle : "+sTitle+" noOfCopies : "+noOfCopies+ " brachId : "+branchId);
            
            if(!isEntryExistInBook(bookId))
            {
            String sqlBook = "INSERT INTO book VALUES (?, ?)";
            PreparedStatement psttBook=con.prepareStatement(sqlBook);
            
            psttBook.setString(1, bookId);
            psttBook.setString(2, sTitle);
            psttBook.executeUpdate();
            
            }
            
            
            if(!isExistInBookAuthor(bookId))
            {
            String sqlAuthor = "INSERT INTO book_authors VALUES (?, ?)";
            PreparedStatement psttBookAuthor=con.prepareStatement(sqlAuthor);
            
            psttBookAuthor.setString(1, bookId);
            psttBookAuthor.setString(2, sAuthor);
            psttBookAuthor.executeUpdate();
            
            }
            if(isEntryInBookCopies(bookId, branchId))
            {
            
            	String sqlBookCopies = "UPDATE book_copies SET no_of_copies=? where book_id=? and branch_id=?";
                PreparedStatement psttBookCopies=con.prepareStatement(sqlBookCopies);
                int totalCopies=getNoOfCopies(bookId, branchId)+Integer.parseInt(noOfCopies);
                psttBookCopies.setString(1, Integer.toString(totalCopies));
                psttBookCopies.setString(2, bookId);
                psttBookCopies.setString(3, branchId);

                psttBookCopies.executeUpdate();
            }else
            {
            String sqlBookCopies = "INSERT INTO book_copies VALUES (?, ?, ?)";
            PreparedStatement psttBookCopies=con.prepareStatement(sqlBookCopies);
            
            psttBookCopies.setString(1, bookId);
            psttBookCopies.setString(2, branchId);
            psttBookCopies.setString(3, noOfCopies);

            psttBookCopies.executeUpdate();
            }
            
            
            
            
            return "success";

             
        } catch (Exception e) {  
            e.printStackTrace();  
            return "failure";
        }finally{
        	
        	try {
        		
				con.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
        }
		

	
	}
	
	public static boolean isEntryExistInBook(String bookId)
	{

		boolean isExist=false;
		Connection con=null;
		try {  
            con = DataConnection.getConnection();
            
            String sql = "SELECT * FROM book WHERE book_id=?";
            PreparedStatement pstt=con.prepareStatement(sql);
            
            pstt.setString(1, bookId);
            ResultSet resultset=pstt.executeQuery();
            
            while(resultset.next())
	        {
	        	isExist=true;
	        }
	        
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{
        	
        	try {
        		
				con.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
        }
	
		return isExist;
	
	}
	
	public static boolean isExistInBookAuthor(String bookId)
	{

		boolean isExist=false;
		Connection con=null;
		try {  
            con = DataConnection.getConnection();
            
            String sql = "SELECT * FROM book_authors WHERE book_id=?";
            PreparedStatement pstt=con.prepareStatement(sql);
            
            pstt.setString(1, bookId);
            ResultSet resultset=pstt.executeQuery();
            
            while(resultset.next())
	        {
	        	isExist=true;
	        }
	        
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{
        	
        	try {
        		
				con.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
        }
	
		return isExist;
	
	}
	
	public static boolean isEntryInBookCopies(String sBookId, String sBranchId)
	{
		boolean isExist=false;
		Connection con=null;
		try {  
            con = DataConnection.getConnection();
            
            String sql = "SELECT * FROM book_copies WHERE book_id=? and branch_id=?";
            PreparedStatement pstt=con.prepareStatement(sql);
            
            pstt.setString(1, sBookId);
            pstt.setString(2, sBranchId);
            ResultSet resultset=pstt.executeQuery();
            
            while(resultset.next())
	        {
	        	isExist=true;
	        }
	        
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{
        	
        	try {
        		
				con.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
        }
	
		return isExist;
	
	
		
	}
	
	public static int getNoOfCopies(String bookId, String branchId)
	{

		int noOfCopies=0;
		Connection con=null;
		try {  
            con = DataConnection.getConnection();
            
            String sql = "SELECT * FROM book_copies WHERE book_id=? and branch_id=?";
            PreparedStatement pstt=con.prepareStatement(sql);
            
            pstt.setString(1, bookId);
            pstt.setString(2, branchId);
            ResultSet resultset=pstt.executeQuery();
            
            while(resultset.next())
	        {
            	noOfCopies=Integer.parseInt(resultset.getString("no_of_copies"));
            	break;
	        }
	        
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{
        	
        	try {
        		
				con.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
        }
	
		return noOfCopies;
	
	
		
	
		
	}
	
	public void deleteBook(String sBook_id)
	{
		Connection con=null;
		try {  
            con = DataConnection.getConnection();
            
            String sql = "DELETE FROM book WHERE book_id=?";
            PreparedStatement pstt=con.prepareStatement(sql);
            
            pstt.setString(1, sBook_id);
            pstt.executeUpdate();

             
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{
        	
        	try {
        		
				con.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
        }
		
	}
	
	public void updateBook(String sBook_id, String sTitle)
	{

		Connection con=null;
		try {  
            con = DataConnection.getConnection();
            
            String sql = "UPDATE book SET title=? WHERE book_id=?";
            PreparedStatement pstt=con.prepareStatement(sql);
            
            pstt.setString(1, sTitle);
            pstt.setString(2, sBook_id);

            pstt.executeUpdate();

             
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{
        	
        	try {
        		
				con.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
        }
		
	
	}

}
