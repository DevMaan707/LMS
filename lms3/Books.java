package lms3;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;
public class Books {
	String queryAdd = "INSERT INTO BOOKS VALUES(?,?,?,?,?,?)";
	String queryDelete = "DELETE FROM BOOKS WHERE bookID = ?";
	String queryUpdate = "UPDATE BOOKS SET inventory = ?";
	String queryDisplay = "SELECT * FROM BOOKS";
	Random random = new Random();
	public Object[] addBook(String name , String publisher , String author , String genre , int inventory ) {
		try {
		PreparedStatement st = DBConnector.con.prepareStatement(queryAdd);
		int bookID = 100000 + random.nextInt(900000);
		st.setInt(1, bookID);
		st.setString(2,name);
		st.setString(3, author);
		st.setString(4,publisher);
		st.setString(5, genre);
		st.setInt(6, inventory);
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
	public Object[] updateBook(String name , String publisher , String author , String genre , int inventory ) {
		try {
		PreparedStatement st = DBConnector.con.prepareStatement(queryUpdate);
		
		
		st.setString(1,name);
		st.setString(2, author);
		st.setString(3,publisher);
		st.setString(4, genre);
		st.setInt(5, inventory);
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
	public Object[] updateLendingBook( int bookid ) {
		try {
			PreparedStatement st = DBConnector.con.prepareStatement("SELECT INVENTORY FROM BOOKS WHERE BOOK_ID = ?");
			st.setInt(1, bookid);
			ResultSet rs = st.executeQuery();
			int inventory = -1;
			while(rs.next()) {
				inventory = rs.getInt("inventory")-1;
			}
		st = DBConnector.con.prepareStatement(queryUpdate);
		st.setInt(1, inventory);
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
	public Object[] deleteBook(int bookID ) {
		try {
		PreparedStatement st = DBConnector.con.prepareStatement(queryDelete);
		st.setInt(1,bookID);
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
		System.out.printf("BOOKID\tNAME\tAUTHOR\tPUBLISHER\tGENRE\tINVENTORY\n");
		while(rs.next()) {
			String name= rs.getString("name");
			int bookID = rs.getInt("bookID");
			String author = rs.getString("author");
			String genre = rs.getString("genre");
			int inventory = rs.getInt("inventory");
			String publisher= rs.getString("publisher");
			// Print the retrieved data
            System.out.printf("%d\t%s\t%s\t%s\t%s\t%d\n", 
                bookID, name, author, publisher, genre, inventory);
					
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
