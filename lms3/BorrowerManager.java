package lms3;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
public class BorrowerManager {
	String queryAdd = "INSERT INTO BORROWERS VALUES(?,?,?,?)";
	String queryUpdate = "UPDATE BORROWERS SET hasBorrowed = ? WHERE BORROWERID = ?";
	String queryDisplay = "SELECT * FROM BORROWERS";
	Random random = new Random();
	public Object[] registerBorrower(String name ) {
		try {
		PreparedStatement st = DBConnector.con.prepareStatement(queryAdd);
		int borrowerID = 100000 + random.nextInt(900000);
		st.setInt(1, borrowerID);
		st.setString(2,name);
		st.setBoolean(3, false);
		int ok = st.executeUpdate();
		if (ok>0) {
			return new Object[] {true,"Success"};
		}else {
			return new Object[] {};
		}
		}catch(Exception e) {
			return new Object[] {false,"Some error has occurred occurrred"+e};
		}
		
	}
	public Object[] updateBorrower(int borrowerID, boolean hasBorrowed  ) {
		try {
		PreparedStatement st = DBConnector.con.prepareStatement(queryUpdate);
		
		
		st.setInt(2,borrowerID);
		st.setBoolean(1, hasBorrowed);
		
		
		int ok = st.executeUpdate();
		if (ok>0) {
			return new Object[] {true,"Success"};
			}else {
			return new Object[] {};
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
		System.out.printf("BORROWERID\tNAME\tHASBORROWED\n");
		while(rs.next()) {
			String name= rs.getString("name");
			int borrowerID = rs.getInt("borrowerID");
			boolean hasBorrowed = rs.getBoolean("hasBorrowed");
			System.out.printf("%d\t%s\t%b\t%s\n",borrowerID, name, hasBorrowed);
					
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
