package User;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Validation {
	static Connection con = null;
	static User u = new User();
	static DBConnect db = new DBConnect();
	static Validation v = new Validation();
	public void getConnection() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/expensetracker"; //change it finally
		String userName = "root";
		String passWord = "";
		con = DriverManager.getConnection(url,userName,passWord);
	}
	public boolean validateLogin(String u_name,String phone_num,String psw) throws SQLException {
		v.getConnection();
		String query = "select u_name,phone_num,psw from userlogin where u_name='"+u_name+"' and phone_num='"+phone_num+"' and psw='"+psw+"';";
		Statement st = (Statement)con.createStatement();
		ResultSet rs = st.executeQuery(query);
		boolean ans = false;
		while(rs.next()) {
			ans = true;
		}
		con.close();
		return ans;
	}
	//Validate method for signup
	public String signupValidate(String name,String u_name,String phone_num,String email,String psw,String c_psw){
		String ans = "";
		String a4 = v.validateName(name);
		if(a4.length() != 0) {
			//System.out.println(a4);
			ans += a4;
		}
		String a1 = v.validateUserName(u_name);
		if(a1.length() != 0) {
			//System.out.println(a1);
			ans += a1;
		}
		String a2 = v.validatePhoneNum(phone_num);
		if(a2.length() != 0) {
			//System.out.println(a2);
			ans += a2;
		}
		String a3 = v.validatePassword(psw,c_psw);
		if(a3.length() != 0) {
			//System.out.println(a3);
			ans += a3;
		}
		return ans;
	}
	public String validateName(String name) {
		String ans = "";
		char ch[] = name.toCharArray();
		int c = 0;
		for(int i = 0;i < ch.length;i++) {
			if(Character.isAlphabetic(ch[i])) {
				c++;
			}
		}
		if(c == name.length()) {
			ans = "";
		}
		else {
			ans = "Enter a Valid Name";
		}
		return ans;
	}
	public String validateUserName(String u_name){
		String ans = "";
		char ch[] = u_name.toCharArray();
		int c = 0;
		if(Character.isDigit(ch[0])) {
			ans += "Username must start with an alphabet";
		}
		else {
			for(int i = 0;i < ch.length;i++) {
				if(Character.isAlphabetic(ch[i]) || Character.isDigit(ch[i]) && ch[i] != ' ') {
					c++;
				}
			}
			if(c != u_name.length()) {
				ans += "Invalid UserName";
			}
		}
		return ans;
	}
		
	public String validatePhoneNum(String phone_num){
		String ans = "";
		if(phone_num.length() != 10) {
			ans += "Enter a Valid 10 digit Phone Number";
		}
		else {
			char ch[] = phone_num.toCharArray();
			int c = 0;
			for(int i = 0;i < ch.length;i++) {
				if(Character.isDigit(ch[i])) {
					c++;
				}
			}
			if(c != phone_num.length()) {
				ans += "Enter a Valid Phone Number";
			}
		}
		return ans;
	}
		
	/*public String validateEmail(String email) {
		String ans = "";
		char ch1 = ans.charAt(0);
		if(!Character.isAlphabetic(ch1)) {
			ans = "First Letter of an email will not be in special character";
		}
		else {		
		}
	}*/
		
	public String validatePassword(String psw,String c_psw) {
		String ans = "";
		boolean s1 = false,s2 = false,s3 = false,s4 = false;
		//Password Length
		if(psw.length() >= 8) {
			s1 = true;
		}
		else {
			ans = "Password length must be more than 8";
		}
		//For Special Character
		int c1 = 0;
		char ch[] = psw.toCharArray();
		if(!Character.isAlphabetic(ch[0])) {
			ans = "Password must start only with an alphabet";
		}
		else {
			for(int i = 0;i < ch.length;i++) {
				if(!Character.isAlphabetic(ch[i]) || Character.isDigit(ch[i])) {
					c1++;
				}
			}
			if(c1 >= 1) {
				s2 = true;
			}
			else {
				ans = "Pasword must contain atleast one special character";
			}
		}
		//For Number & alphabet
		int c2 = 0,c3 = 0;
		for(int i = 0;i < ch.length;i++) {
			if(Character.isUpperCase(ch[i]) || Character.isLowerCase(ch[i])) {
				c3++;
			}
			else if(Character.isDigit(ch[i])) {
				c2++;
			}
		}
		if(c2 >= 1) {
			s3 = true;
		}
		else {
			ans = "Pasword must contain atleast one number";
		}
		if(c3 >= 2) {
			s4 = true;
		}
		else {
			ans = "Password must contain atleast one lower and uppercase letter";
		}
		if(s1 && s2 && s3 && s4) {
			if(!psw.equals(c_psw)){
				ans = "Please check Pasword and Confirm password";
			}
		}
		return ans;
	}
	
	//method under signup ,where we check whether the u_name already exits in our db
	public boolean checkUser(String u_name) throws Exception {
		v.getConnection();
		String query = "select u_name from userlogin where u_name='"+u_name+"';";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(query);
		boolean ans = false;
		while(rs.next()) {
			ans = true;
		}
		con.close();
		return ans;
	}
	
	//to validate age and dob for ser user profile method
	public String ageAndDobValidate(int age,String dob) {
		String ans;
		SimpleDateFormat f = new SimpleDateFormat("YYYY/MM/DD");
		Date d = new Date();
		String date = f.format(d);
		String st1[] = dob.split("/"); //12/06/2003
		String l1 = st1[0];
		String st2[] = date.split("/"); //28/01/2023
		String l2 = st2[0];
		int n1 = Integer.parseInt(l1);
		int n2 = Integer.parseInt(l2);
		if(n2-n1 == age) {
			ans = "";
		}
		else {
			ans = "Enter a Valid age and dob";
		}
		return ans;
	}
	//used in daily expense page
	public boolean ValidAmount(String amt) {
		boolean answ = false;
		int count = 0;
		char ch[] = amt.toCharArray();
		for(int i = 0;i < ch.length;i++) {
			if(Character.isDigit(ch[i])) {
				count++;
			}
		}
		if(count == ch.length) {
			answ = true;
		}
		return answ;
	}
	public String todaysDate() {
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
		Date d = new Date();
		String t_date = f.format(d);
		return t_date;
	}
	public String getMonth() {
		String date = todaysDate();
		String mon[] = {"","January","February","March","April","May","June","July","August","September","October","November","December"};
		String st[] = date.split("/");
		int month = Integer.parseInt(st[1]);
		return mon[month];
	}
	
	public String date(String date) {
		String st[] = date.split("/");
		String year = st[0];
		int y = Integer.parseInt(year);
		String month = st[1];
		int m = Integer.parseInt(month);
		String ms = "";
		if(m >= 1 && m <= 9) {
			ms = "0"+m;
		}
		else {
			ms = Integer.toString(m);
		}
		String start,end,res = "";
		if(m % 2 == 1 || m == 8) {
			start = year+"/"+ms+"/01";
			end = year+"/"+ms+"/"+31;
			res = start + " "+ end;
		}
		else if(m % 2 == 0 || m != 8 || m != 2){
			start = year+"/"+ms+"/01";
			end = year+"/"+ms+"/"+30;
			res = start + " "+ end;
		}
		else if(m == 2) {
			if(y % 4 == 0) {
				start = year+"/"+ms+"/01";
				end = year+"/"+ms+"/"+29;
				res = start + " "+ end;
			}
			else {
				start = year+"/"+ms+"/01";
				end = year+"/"+ms+"/"+28;
				res = start + " "+ end;
			}
		}
		return res;
	}
	public String month(String date) {
		String mon[] = {"","January","February","March","April","May","June","July","August","September","October","November","December"};
		String st[] = date.split("/");
		int m = Integer.parseInt(st[1]);
		String ans = mon[m];
		return ans;
	}
	
	public int validateDate(String date) {
		int flag = 0;
		String regex = "[0-9]{4}/[0-1]{1}[1-12]{1}/[0-3]{1}[0-9]{1}";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(date);
		boolean res = m.matches();
		if(res) {
			flag = 0;
			String d[] = date.split("/");
			String year = d[0];
			String month = d[1];
			String datee = d[2];
			int y1 = Integer.parseInt(year);
			if(y1 != 2023) {
				System.out.println("Error : Invalid year,enter a valid date in this current year");
				flag = 1;
			}
			else {
				flag = 0;
			}
			int mon = Integer.parseInt(month);
			if(mon >= 1 && mon <= 12) {
				flag = 0;
			}
			else {
				System.out.println("Error : Invalid month");
				flag = 1;
			}
			int da = Integer.parseInt(datee);
			if(da >= 1 && da <= 31) {
				flag = 0;
			}
			else {
				System.out.println("Error : Invalid day");
				flag = 1;
			}
		}
		else {
			System.out.println("Error : Invalid date,enter date in (YYYY/MM/DD) Format");
			flag = 1;
		}
		return flag;
		
	}
	
	public boolean validateCategory(String category) {
		boolean ans = false;
		int flag = 0;
		String cat[] = {"food","fuel", "fees", "rent","fruits","Vegetables", "recharge","maintenence" ,"transportation", "stationary" ,"dress" ,"medicine","entertainment","groceries","personal","outfoods","snacks","outing","emi","others"};
		for(int i = 0;i < cat.length;i++) {
			if(cat[i].equalsIgnoreCase(category)) {
				flag = 1;
			}
		}
		if(flag == 1) {
			ans = true;
		}
		return ans;
	}
	
	public boolean IncomeChoiceValid(String value) {
		boolean ans = false;
		char ch[] = value.toCharArray();
		int count = 0;
		for(int i = 0;i < ch.length;i++) {
			if(Character.isDigit(ch[i])) {
				int n = Character.getNumericValue(ch[i]);
				if(n >= 1 && n <= 5) {
					count++;
				}
			}
		}
		if(count == ch.length) {
			ans = true;
		}
		return ans;
	}
	
	public boolean checkForNumber(String value) {
		boolean ans = false;
		char ch[] = value.toCharArray();
		int count = 0;
		for(int i = 0;i < ch.length;i++) {
			if(Character.isDigit(ch[i])) {
				count++;
			}
		}
		if(count == ch.length) {
			ans = true;
		}
		return ans;
	}
	
	public String validateDate2(String d) {
		String ans = "";
		String st[] = d.split("/");
		String y = st[0];
		String m = st[1];
		String dd = st[2];
		if(y.length() == 4 && m.length() == 2 && dd.length() == 2) {
			int mm = Integer.parseInt(m);
			int ddd = Integer.parseInt(dd);
			if(mm >= 01 && mm <= 12) {
				if(ddd >= 01 && ddd <= 31) {
					ans = "";
				}
				else {
					ans += "Invalid date";
				}
			}
			else {
				ans += "Invalid Month";
			}
		}
		else {
			ans += "Invalid format of date";
		}
		return ans;
	}
	
	public String ValidateFromAndTo(String f,String t) {
		String ans = "";
		String st[] = f.split("/");
		String st2[] = t.split("/");
		int y1 = Integer.parseInt(st[0]);
		int m1 = Integer.parseInt(st[1]);
		int d1 = Integer.parseInt(st[2]);
		int y2 = Integer.parseInt(st2[0]);
		int m2 = Integer.parseInt(st2[1]);
		int d2 = Integer.parseInt(st2[2]);
		if(y2 >= y1 && m2 >= m1 && d2 >= d1) {
			ans = "";
		}
		else {
			ans = "Invalid from and to date.";
		}
		return ans;
	}
	
	public long ttl_days(String fd,String td) throws ParseException {
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
		Date d1 = f.parse(fd);
		Date d2 = f.parse(td);
		long difference_In_Time = d2.getTime() - d1.getTime();
		long difference_In_Days	= (difference_In_Time/ (1000 * 60 * 60 * 24)) % 365;
		long ttl = difference_In_Days;
		return ttl;
	}	
}
