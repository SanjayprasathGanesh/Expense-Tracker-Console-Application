package User;
import java.sql.*;
public class DB {
	static Connection con = null;
	private DB() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/expensetracker";
		String userName = "root";
		String passWord = "";
		con = DriverManager.getConnection(url,userName,passWord);
	}
	public static Connection getConnection() throws SQLException {
		if(con == null) {
			new DB();
		}
		return con;
	}
	public static void closeConnection() throws SQLException{
		con.close();
	}
}
