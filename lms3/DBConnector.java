package lms3;

import java.sql.*;


public class DBConnector {

	static Connection con;
	public  Object[] dbConnect() {
		String className = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/lms";
		String user = "root";
		String password = "root";
		try {
			Class.forName(className);
			con = DriverManager.getConnection(url,user,password);
		}catch(ClassNotFoundException e){
			return new Object[] {false,"Class was not found"};
		}catch(Exception e) {
			return new Object[]{false,"There was an unknown error:"+e};
		}
		return new Object[] {true,""};
		
	}
	
}
