package User;
import java.sql.*;
import java.util.*;
public class DBConnect {
	static Scanner input = new Scanner(System.in);
	static DBConnect db = new DBConnect();
	static Validation v = new Validation();
	static User u = new User();
	static Driver d = new Driver();
	static Connection con = null;
	public Connection getConnection() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/expensetracker";
		String userName = "root";
		String passWord = "";
		return con = DriverManager.getConnection(url,userName,passWord);
	}
	
	public int signupConnect(String name,String u_name,String phone_num,int age,String gender,String dob,String marital_status,String category,String email,String psw) throws SQLException {
		db.getConnection();
		String query = "INSERT INTO userlogin VALUES(?,?,?,?,?,?,?,?,?,?);";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, name);
		ps.setString(2, u_name);
		ps.setString(3, phone_num);
		ps.setInt(4, age);
		ps.setString(5, gender);
		ps.setString(6, dob);
		ps.setString(7, marital_status);
		ps.setString(8, category);
		ps.setString(9, email);
		ps.setString(10, psw);
		int rows = ps.executeUpdate();
		return rows;
	}
	
	public int expConnect(String date,String month,String exp_name,String category,String trans,int amt,String u_name) throws Exception {
		db.getConnection();
		String query = "INSERT INTO daily_exp VALUES(?,?,?,?,?,?,?);";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, date);
		ps.setString(2, month);
		ps.setString(3, exp_name);
		ps.setString(4, category);
		ps.setString(5, trans);
		ps.setInt(6, amt);
		ps.setString(7, u_name);
		int rows = ps.executeUpdate();
		return rows;
	}
	
	//search expense method
	public ResultSet withDate(String date,String u_name) throws Exception {
		db.getConnection();
		String query = "SELECT * FROM daily_exp WHERE u_name = '"+u_name+"' AND date = '"+date+"';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}
	public ResultSet withExpName(String exp_name,String u_name) throws Exception {
		db.getConnection();
		String query = "SELECT * FROM daily_exp WHERE u_name = '"+u_name+"' AND exp_name = '"+exp_name+"';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}
	public ResultSet withCategory(String category,String u_name) throws Exception {
		db.getConnection();
		String query = "SELECT * FROM daily_exp WHERE u_name = '"+u_name+"' AND category = '"+category+"';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}
	public ResultSet withTransaction(String trans,String u_name) throws Exception {
		db.getConnection();
		String query = "SELECT * FROM daily_exp WHERE u_name = '"+u_name+"' AND trans = '"+trans+"';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}
	public ResultSet withMonth(String month,String u_name) throws Exception {
		db.getConnection();
		String query = "SELECT * FROM daily_exp WHERE u_name = '"+u_name+"' AND month = '"+month+"';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}
	public ResultSet withAmount(int amt,String u_name) throws Exception{
		db.getConnection();
		String query = "SELECT * FROM daily_exp WHERE u_name = '"+u_name+"' AND amt = '"+amt+"';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		return rs;
	}
	public void display(ResultSet rs) throws Exception {
		db.getConnection();
		System.out.println("Search found the belows records");
		System.out.println("--------------------------------------------------------------------------------------");
		System.out.printf("%1s %5s %14s %18s %11s %13s %12s","Sno","Date","Month","Expense Name","Category","Transaction","Amount");
		System.out.println();
		System.out.println("--------------------------------------------------------------------------------------");
		int i = 1;
		while(rs.next()) {
			System.out.printf("%1s %12s %10s %13s %13s %12s %12s",i++,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6));
			System.out.println();
		}
		System.out.println("--------------------------------------------------------------------------------------");
	}
	
	//view expense method
	//Change the date statements by accessing it from validation class
	public void viewExpense(String u_name) throws Exception {
		db.getConnection();
		String date = v.todaysDate();
		//to separate data
		String s = v.date(date);
		String str[] = s.split(" ");
		String start = str[0];
		String end = str[1];
		String query = "SELECT date,month,exp_name,category,trans,amt FROM daily_exp WHERE u_name = '"+u_name+"' AND date BETWEEN '"+start+"' AND '"+end+"' ORDER BY date;";
		String query2 = "SELECT SUM(amt) FROM daily_exp WHERE u_name = '"+u_name+"' AND date BETWEEN '"+start+"' AND '"+end+"' ORDER BY date;";
		String query3 = "SELECT SUM(amt) FROM daily_exp WHERE u_name = '"+u_name+"';";
		Statement st = con.createStatement();
		Statement st2 = con.createStatement();
		Statement st3 = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		ResultSet rs2 = st2.executeQuery(query2);
		ResultSet rs3 = st3.executeQuery(query3);
		System.out.println("---------------------------Your daily expenses of "+ v.month(date)+" month---------------------------");
		System.out.println("------------------------------------------------------------------------------------------");
		System.out.printf("%5s %5s %12s %18s %13s %10s %12s","SNo ","Date ","Month"," Expense Name "," Category "," Transaction ","Amount ");
		System.out.println();
		System.out.println("------------------------------------------------------------------------------------------");
		int i = 1;
		while(rs.next()) {
			System.out.printf("%1s %12s %10s %13s %13s %12s %14s",i++,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6));
			System.out.println();
			System.out.println("------------------------------------------------------------------------------------------");
		}
		while(rs2.next()) {
			System.out.println("Total amount of this Month Expenses : "+rs2.getInt(1));
		}
		while(rs3.next()) {
			System.out.println("Total amount of expenses till date "+date+" is "+rs3.getInt(1));
		}
		con.close();
		System.out.println("------------------------------------------------------------------------------------------");
		System.out.println("Do you want to move to INDEX Page : (YES/NO)");
		String move = input.nextLine();
		if(move.equalsIgnoreCase("Yes")){
			d.index(u_name);
		}
		else {
			db.viewExpense(u_name);
		}
	}
	
	public boolean specificSearch(String date,String exp_name,String change,String u_name) throws SQLException {
		db.getConnection();
		boolean ans = false;
		String query = "SELECT * FROM daily_exp WHERE date = '"+date+"',exp_name = '"+exp_name+"', u_name = '"+u_name+"';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		System.out.println("------------------------------------------------------------------------------------------");
		System.out.printf("%5s %5s %12s %18s %13s %10s %12s","SNo ","Date ","Month"," Expense Name "," Category "," Transaction ","Amount ");
		System.out.println();
		System.out.println("------------------------------------------------------------------------------------------");
		int i = 1;
		while(rs.next()) {
			ans = true;
			System.out.printf("%1s %12s %10s %13s %13s %12s %14s",i++,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6));
			System.out.println();
			System.out.println("------------------------------------------------------------------------------------------");
		}
		return ans;
	}
	
	public boolean specificSearchPrint(String date,String exp_name,String u_name) throws SQLException {
		db.getConnection();
		boolean ans = false;
		String query = "SELECT * FROM daily_exp WHERE date = '"+date+"' AND exp_name = '"+exp_name+"' AND u_name = '"+u_name+"';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		System.out.println("------------------------------------------------------------------------------------------");
		System.out.printf("%5s %5s %12s %18s %13s %10s %12s","SNo ","Date ","Month"," Expense Name "," Category "," Transaction ","Amount ");
		System.out.println();
		System.out.println("------------------------------------------------------------------------------------------");
		int i = 1;
		while(rs.next()) {
			ans = true;
			System.out.printf("%1s %12s %10s %13s %13s %12s %14s",i++,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6));
			System.out.println();
			System.out.println("------------------------------------------------------------------------------------------");
		}
		return ans;
	}
	
	public int updateExpwithDate(String old_date,String new_date,String u_name,String exp_name) throws SQLException {
		db.getConnection();
		String query = "UPDATE daily_exp SET date = '"+new_date+"' WHERE date = '"+old_date+"' AND exp_name = '"+exp_name+"'";
		Statement st = con.createStatement();
		int rows = st.executeUpdate(query);
		return rows;
	}

	public int updateExpwithName(String date,String old_expname,String new_expname,String u_name) throws SQLException {
		db.getConnection();
		String query = "UPDATE daily_exp SET exp_name = "+new_expname+" WHERE exp_name = "+old_expname+" AND date = "+date+" AND u_name = "+u_name+";";
		Statement st = con.createStatement();
		int rows = st.executeUpdate(query);
		return rows;
	}
	
	public int updateExpwithCategory(String date,String new_cat,String u_name,String exp_name) throws SQLException {
		db.getConnection();
		String query = "UPDATE daily_exp SET category = "+new_cat+" WHERE exp_name = "+exp_name+" AND date = "+date+" AND u_name = "+u_name+";";
		Statement st = con.createStatement();
		int rows = st.executeUpdate(query);
		return rows;
	}
	
	public int updateExpwithTransaction(String date,String expname,String new_trans,String u_name) throws SQLException {
		db.getConnection();
		String query = "UPDATE daily_exp SET trans = "+new_trans+" WHERE exp_name = "+expname+" AND date = "+date+" AND u_name = "+u_name+";";
		Statement st = con.createStatement();
		int rows = st.executeUpdate(query);
		return rows;
	}
	
	public int updateExpwithAmount(String date,String expname,int new_amt,String u_name) throws SQLException {
		db.getConnection();
		String query = "UPDATE daily_exp SET amt = ? WHERE exp_name = ? AND date = ? AND u_name = ?";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, new_amt);
		ps.setString(2,expname);
		ps.setString(3,date);
		ps.setString(4,u_name);
		int rows = ps.executeUpdate();
		return rows;
	}
	
	public void dispName(String u_name) throws SQLException {
		db.getConnection();
		String query = "SELECT * FROM userlogin WHERE u_name = '"+u_name+"';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		while(rs.next()) {
			System.out.println("Name            : "+rs.getString(1));
			System.out.println("User Name       : "+rs.getString(2));
			System.out.println("Phone Number    : "+rs.getString(3));
			System.out.println("Age             : "+rs.getString(4));
			System.out.println("Gender          : "+rs.getString(5));
			System.out.println("Date Of Birth   : "+rs.getString(6));
			System.out.println("Marital Status  : "+rs.getString(7));
			System.out.println("Type            : "+rs.getString(8));
			System.out.println("Email Id        : "+rs.getString(9));
			System.out.println("Password        : "+"*************");
			break;
		}
	}
	
	public int editUserProf(String choice,String to_change,String u_name) throws SQLException {
		db.getConnection();
		String query = "UPDATE userlogin SET "+choice+" = '"+to_change+"' WHERE u_name = '"+u_name+"';";
		Statement st = con.createStatement();
		int rows = st.executeUpdate(query);
		return rows;
	}
	
	public String checkPsw(String u_name) throws SQLException {
		db.getConnection();
		String query = "SELECT psw FROM userlogin WHERE u_name = '"+u_name+"';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		String ans = "";
		while(rs.next()) {
			ans = rs.getString(1);
		}
		return ans;
	}
	public int updatePsw(String n_psw,String u_name) throws SQLException {
		db.getConnection();
		String query = "UPDATE userlogin SET psw = '"+n_psw+"' WHERE u_name = '"+u_name+"';";
		Statement st = con.createStatement();
		int rows = st.executeUpdate(query);
		return rows;
	}
	
	public int saveUserFixedExpIncome(ArrayList<String> income,ArrayList<String> amt_list,ArrayList<String> fixedexp_name,ArrayList<String> fixedexp_amt,ArrayList<String> fixedexp_time,String u_name) throws SQLException {
		db.getConnection();
		String a[] = {"","Salary Income","House/Land Rent Income","Fixed Deposit Interest Income","Bussiness Profit Income","Other Income"};
		String s1 = "",s2 = "",s3 = "",s4 = "",s5 = "";
		for(int i = 0;i < income.size();i++) {
			int index = Integer.parseInt(income.get(i));
			s1 += a[index] + ",";
			s2 += amt_list.get(i)+",";
		}
		for(int i = 0;i < fixedexp_name.size();i++) {
			s3 += fixedexp_name.get(i)+",";
			s4 += fixedexp_amt.get(i)+",";
			s5 += fixedexp_time.get(i)+",";
		}
		String query = "INSERT INTO user_fixed VALUES(?,?,?,?,?,?);";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, s1);
		ps.setString(2, s2);
		ps.setString(3, s3);
		ps.setString(4, s4);
		ps.setString(5, s5);
		ps.setString(6, u_name);
		int rows = ps.executeUpdate();
		return rows;
	}
	
	public int connectDairy(String u_name,String date,String about) throws SQLException {
		db.getConnection();
		String query = "INSERT INTO personal_ass VALUES(?,?,?);";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, u_name);
		ps.setString(2, date);
		ps.setString(3, about);
		int rows = ps.executeUpdate();
		return rows;
	}
	
	public int connectLeave(String u_name,String from_date,String to_date,String reason,long ttl_days) throws SQLException {
		db.getConnection();
		String query = "INSERT INTO leave_days VALUES(?,?,?,?,?);";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, u_name);
		ps.setString(2, from_date);
		ps.setString(3, to_date);
		ps.setString(4, reason);
		ps.setLong(5, ttl_days);
		int rows = ps.executeUpdate();
		return rows;
	}
	
	public void viewDairy(String u_name) throws SQLException {
		db.getConnection();
		Statement st = con.createStatement();
		String query = "SELECT date,about FROM personal_ass WHERE u_name = '"+u_name+"';";
		ResultSet rs = st.executeQuery(query);
		System.out.println("-----------------------------------------------------");
		System.out.printf("%5s %10s %15s","S No","Date","About");
		System.out.println();
		System.out.println("-----------------------------------------------------");
		int index = 1;
		while(rs.next()) {
			System.out.printf("%5s %15s %14s",index++,rs.getString(1),rs.getString(2));
			System.out.println();
			System.out.println("-----------------------------------------------------");
		}
	}
	
	public void viewLeave(String u_name) throws SQLException {
		db.getConnection();
		Statement st = con.createStatement();
		String query = "SELECT from_date,to_date,reason,ttl_days FROM leave_days WHERE u_name = '"+u_name+"';";
		ResultSet rs = st.executeQuery(query);
		System.out.println("---------------------------------------------------------------------");
		System.out.printf("%5s %10s %11s %12s %18s","S No","From Date","To Date","Reason","Total Leave Days");
		System.out.println();
		System.out.println("---------------------------------------------------------------------");
		int index = 1;
		while(rs.next()) {
			System.out.printf("%3s %13s %13s %9s %10s",index++,rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
			System.out.println();
			System.out.println("---------------------------------------------------------------------");
		}
	}
	
	public void displayExp(String u_name) throws Exception {
		db.getConnection();
		String n[] = {"fuel","food","fees","fruits/Vegetables","rent","recharge","transportation","stationary","dress","medicine","maintenence"};
		String un[] = {"entertainment","outfoods","snacks","outing","emi","subscriptions","Others"};
		//String date = v.todaysDate();
		String month = v.getMonth();
		int ness = 0;
		int un_ness = 0;
		int savings = 0;
		int income = (int) db.getIncome(u_name);
		int fixed = (int)db.getFixedAmount(u_name);
		for(int i = 0;i < n.length;i++) {
			String query = "SELECT SUM(amt) FROM daily_exp WHERE category = '"+n[i]+"' AND u_name = '"+u_name+"' AND  month = '"+month+"';";  
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				ness += rs.getInt(1);
			}
		}
		System.out.println(ness);
		for(int i = 0;i < un.length;i++) {
			String query = "SELECT SUM(amt) FROM daily_exp WHERE u_name = '"+u_name+"' AND category = '"+un[i]+"' AND month = '"+month+"';";  
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				un_ness += rs.getInt(1);
			}
		}
		System.out.println("Your Income : "+income);
		System.out.println("Your Necessary Expenses : "+ness);
		System.out.println("Your Unnecessary Expenses : "+un_ness);
		System.out.println("Your Fixed Expenses : "+fixed);
		savings = income + (ness - un_ness - fixed);
		System.out.println("Your savings of "+ month +" month is "+ (income - (ness + un_ness + fixed)));
		int rows = db.saveMonthlyExp(u_name,month,income,fixed,ness,un_ness,savings);
		if(rows >= 1) {
			System.out.println(month+" expenses & savings are recorded");
		}
	}
	public int getIncome(String u_name) throws SQLException {
		db.getConnection();
		int income = 0;
		String query = "SELECT amount FROM user_fixed WHERE u_name = '"+u_name+"';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		String s1 = "";
		while(rs.next()) {
			s1 += rs.getString(1);
		}
		String str[] = s1.split(",");
		for(int i = 0;i < str.length;i++) {
			income += Integer.parseInt(str[i]);
		}
		return income;
	}
	
	public int getFixedAmount(String u_name) throws SQLException {
		db.getConnection();
		int amount = 0;
		String query = "SELECT fixedexp_amt FROM user_fixed WHERE u_name = '"+u_name+"';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		String s1 = "";
		while(rs.next()) {
			s1 += rs.getString(1);
		}
		String str[] = s1.split(",");
		for(int i = 0;i < str.length;i++) {
			 amount += Integer.parseInt(str[i]);
		}
		return amount;
	}
	
	public int saveMonthlyExp(String u_name,String month,int income,int fixed,int ness,int un_ness,int savings) throws SQLException {
		db.getConnection();
		String query = "INSERT INTO monthly_report VALUES(?,?,?,?,?,?,?);";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, u_name);
		ps.setString(2, month);
		ps.setInt(3, income);
		ps.setInt(4, fixed);
		ps.setInt(5, ness);
		ps.setInt(6, un_ness);
		ps.setInt(7, savings);
		int rows = ps.executeUpdate();
		con.close();
		return rows;
	}
	
	public String getMonthlyRep(String u_name) throws Exception {
		db.getConnection();
		String msg = "";
		String query = "SELECT * FROM daily_exp WHERE u_name = '"+u_name+"';";
		msg += " +------------------+--+-----------+--+----------------------------+---+--------------------+--+---------------+---+----------+,";
		msg += " |       Date       |  |   Month   |  |        Expense Name        |   |      Category      |  |  Transaction  |   |  Amount  |,";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		while(rs.next()) {
			msg += " +------------------+--+-----------+--+----------------------------+---+--------------------+--+---------------+---+----------+,";
			msg += " |     " + rs.getString(1) + "   |  |   " + rs.getString(2) + "   |  |      " + rs.getString(3) + "       |   |     " + rs.getString(4) + "     |  |     " + rs.getString(5) + "      |   |    " + rs.getInt(6) + "   |,";
			//msg += " +------------------+--+-----------+--+----------------------------+---+--------------------+--+---------------+---+----------+,";
		}
		msg += " +------------------+--+-----------+--+----------------------------+---+--------------------+--+---------------+---+----------+,";
		return msg;
	}
}