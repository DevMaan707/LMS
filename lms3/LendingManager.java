package lms3;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Random;
public class LendingManager {
	String queryAdd = "INSERT INTO LENDING VALUES(?,?,?,?)";
	String queryUpdate = "UPDATE BOOKS SET returnDate = ?";
	String queryDelete = "DELETE FROM LENDING WHERE lendingID = ?";
	String queryDisplay = "SELECT * FROM LENDING";
	
	Random random = new Random();
	public Object[] addLender(int borrowerID, int bookID , java.sql.Date date) {
		try {
		PreparedStatement st = DBConnector.con.prepareStatement(queryAdd);
		int lenderID = 100000 + random.nextInt(900000);
		st.setInt(1, lenderID);
		st.setInt(2,borrowerID);
		st.setInt(3, bookID);
		st.setDate(4, date); 
		int ok = st.executeUpdate();
		if (ok>0) {
			BorrowerManager borrowerManager = new BorrowerManager();
			Object[] borrowerResult1 = borrowerManager.updateBorrower(borrowerID,true);
	        System.out.println(borrowerResult1[1]);
	        return new Object[] {true,"Success"};
		}else {
			return new Object[] {};
		}
		}catch(Exception e) {
			return new Object[] {false,"Some error has occurred occurrred"+e};
		}
		
	}
	public Object[] deleteLender(int lenderID ) {
		try {
		PreparedStatement st = DBConnector.con.prepareStatement(queryUpdate);
		st.setInt(1,lenderID);
		int ok = st.executeUpdate();
		if (ok>0) {
			String query = "SELECT BORROWERID FROM LENDING WHERE LENDINGID = ?";
			st = DBConnector.con.prepareStatement(query);
			int lendingBorrowerID = -1;
			ResultSet s = st.executeQuery();
			while(s.next()) {
				 lendingBorrowerID = s.getInt("borrowerID");
			}
			 BorrowerManager borrowerManager = new BorrowerManager();
			 Object[] borrowerResult1 = borrowerManager.updateBorrower(lendingBorrowerID,false);
	         System.out.println(borrowerResult1[1]);
	         Books bookManager = new Books();
	         
	         return new Object[] {true,"Success"};
			}
		else {
			return new Object[] {false,"Failed"};
			}
		
		}
		catch(Exception e) {
			return new Object[] {false,"Some error has occurred occurrred"+e};
		}
	}
	public void showRecords() {
		try {
		Statement st = DBConnector.con.createStatement();
		ResultSet rs = st.executeQuery(queryDisplay);
		System.out.printf("LENDINGID\tBORROWERID\tBOOKID\tDUEDATE\n");
		while(rs.next()) {
			int lenderID = rs.getInt("lendingID");
			int borrowerID = rs.getInt("borrowerID");
			int bookID = rs.getInt("bookID");
			java.sql.Date date = rs.getDate("dueDate");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String formattedDate = (date != null) ? sdf.format(date) : "NULL"; 
			System.out.printf("%d\t%d\t%d\t%s\n", lenderID, borrowerID, bookID, formattedDate);
					
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
