package User;
import java.util.*;
import java.sql.*;
import java.io.*;
public class User implements ExpenseManager{
	static Validation v = new Validation();
	static DBConnect db = new DBConnect();
	static User u = new User();
	static Driver d = new Driver();
	static Scanner input = new Scanner(System.in);
	public String login() throws SQLException{
		String g_uname = "";
		//boolean log = false;
		int flag = 0;
		do {
			System.out.println("----------------------------------------------------");
			System.out.println("           You are in User LOGIN Page");
			System.out.println("----------------------------------------------------");
			System.out.println("Enter UserName : ");
			String u_name = input.nextLine();
			System.out.println("Enter Phone Number : ");
			String phone_num = input.nextLine();
			System.out.println("Enter Your Password : ");
			String psw = input.nextLine();
			System.out.println("Enter 1 to Login : ");
			String choice = input.nextLine();
			if(choice.equals("1")) {
				boolean log_valid = v.validateLogin(u_name, phone_num, psw);
				if(log_valid) {
					System.out.println("Welcome User "+u_name);
					flag = 0;
					//log = true;
					g_uname = u_name;
				}
				else {
					System.err.println("Login Invalid,Retry again");
					flag = 1;
				}
			}
			else {
				flag = 1;
			}
		}while(flag == 1);
		return g_uname;
	}
	
	//sign up methods
	public void signup() throws Exception {
		//boolean signup_valid = false;
		//String g_uname = "";
		int flag = 0;
		do {
			System.out.println("-----------------------------------");
			System.out.println("You are in User SIGNUP Page");
			System.out.println("-----------------------------------");
			System.out.println("Enter your name : ");
			String name = input.nextLine();
			System.out.println("Enter UserName : ");
			System.out.println("Note : Should only have digits and alphabets only");
			System.out.println("Minimum one Upper and lowerCase and a digit");
			String u_name = input.nextLine();
			boolean checked_uname = v.checkUser(u_name);
			if(!checked_uname) {
				System.out.println("Enter Phone Number : ");
				String phone_num = input.nextLine();
				System.out.println("Enter Email Address : ");
				String email = input.nextLine();
				System.out.println("Enter Your Password : (min length = 8)");
				String psw = input.nextLine();
				System.out.println("Confirm Your Password : ");
				String c_psw = input.nextLine();
				System.out.println("Enter 1 to signup : ");
				String choice = input.nextLine();
				if(choice.equals("1")) {
					String ans = v.signupValidate(name,u_name,phone_num,email,psw,c_psw);
					if(ans.length() == 0) {
						flag = 0;
						System.out.println("Welcome new User "+name);
						//int rows = db.signupConnect(name, u_name, phone_num, flag, phone_num, psw, choice, ans, email, c_psw);
						//g_uname = u_name;
						u.setUserProfile(name,u_name,phone_num,email,psw);
					}
					else {
						System.out.println(ans);
						flag = 1;
					}
				}
				else {
					System.err.println("Invalid choice");
					flag = 1;
				}
			}
			else {
				System.err.println("User Name already exist,try with a new name");
				flag = 1;
			}
		}while(flag == 1);
	}
	
	//Step 2 from signup
	public void setUserProfile(String name,String u_name,String phone_num,String email,String psw) throws Exception {
		int flag = 0;
		do {
			flag = 0;
			System.out.println("--------------------------------------");
			System.out.println("You are in User SetUp Page");
			System.out.println("--------------------------------------");
			System.out.println("Enter Your age : (On-Going)");
			String u_age = input.nextLine();
			System.out.println("Enter Your DOB : (YYYY/MM/DD)");
			String dob = input.nextLine();
			System.out.println("Enter Your Gender : ");
			String gender = input.nextLine();
			System.out.println("Enter Your Marital Status : (Yes/No)");
			String marital_status = input.nextLine();
			int age = Integer.parseInt(u_age);
			String res = v.ageAndDobValidate(age,dob);
			String type = "";
			if(res.length() <= 1) {
				flag = 0;
				if(age <= 18 && marital_status.equalsIgnoreCase("No")) {
					type = "Student";
				}
				else if(age >= 19 && marital_status.equalsIgnoreCase("No")) {
					type = "Bachelor";
				}
				else if(age >= 24 || marital_status.equalsIgnoreCase("Yes")  || age >= 19 && marital_status.equalsIgnoreCase("Yes")){
					if(gender.equalsIgnoreCase("Male")) {
						type = "Family Man";
					}
					else if(gender.equalsIgnoreCase("Female")) {
						type = "Family Women";
					}
					else {
						type = "Family Person";
					}
				}
				else {
					System.err.println("Enter a valid Input for Marital status");
					flag = 1;
				}
				if(flag == 0) {
					flag = 0;
					System.out.println("As of your age,you ll be categorized under "+type);
					System.out.println("Do you want to change your type : (Yes/No)");
					String choice1 = input.nextLine();
					if(choice1.equalsIgnoreCase("Yes")) {
						flag = 0;
						System.out.println("Which type you need to be changed : ");
						if(gender.equalsIgnoreCase("Male")) {
							System.out.println("Enter 1 for Student , 2 for Bachelor , 3 for FamilyMan ");
							String choice2 = input.nextLine();
							if(choice2.equals("1")) {
								type = "Student";
							}
							else if(choice2.equals("2")) {
								type = "Bachelor";
							}
							else if(choice2.equals("3")) {
								type = "Family Man";
							}
							else {
								System.err.println("Enter a valid choice ");
								flag = 1;
							}
						}
						else if(gender.equalsIgnoreCase("Female")) {
							flag = 0;
							System.out.println("Enter 1 for Student , 2 for Bachelor , 3 for Family Women ");
							String choice2 = input.nextLine();
							if(choice2.equals("1")) {
								type = "Student";
							}
							else if(choice2.equals("2")) {
								type = "Bachelor";
							}
							else if(choice2.equals("3")) {
								type = "Family Women";
							}
							else {
								System.err.println("Please Enter a valid choice ");
								flag = 1;
							}
						}
						else {
							flag = 0;
							System.out.println("Enter 1 for Student , 2 for Bachelor , 3 for Family Person ");
							String choice2 = input.nextLine();
							if(choice2.equals("1")) {
								type = "Student";
							}
							else if(choice2.equals("2")) {
								type = "Bachelor";
							}
							else if(choice2.equals("3")) {
								type = "Family Person";
							}
							else {
								System.err.println("Please Enter a valid choice ");
								flag = 1;
							}
						}
						System.out.println("Your Type has been finaly set to "+type+" successfully");
						int rows = db.signupConnect(name,u_name,phone_num,age,gender,dob,marital_status,type,email,psw);
						if(rows == 1) {
							System.out.println(" Your User Profile setted successfully");
							flag = 0;
							System.out.println("--------------------------------------");
						}
						else {
							System.err.println("ERROR : Please do re-enter Correctly");
							flag = 1;
						}
					}
					else {
						System.out.println("Your Type has been finaly set to "+type+" successfully");
						flag = 0;
						int rows = db.signupConnect(name,u_name,phone_num,age,gender,dob,marital_status,type,email,psw);
						if(rows == 1) {
							//setUser = true;
							System.out.println(" Your User Profile setted successfully");
							flag = 0;
							System.out.println("--------------------------------------");
						}
						else {
							System.err.println("ERROR : Please do re-enter Correctly");
							flag = 1;
						}
					}
				}
				else {
					flag = 1;
				}
			}
			else {
				System.out.println(res);
				flag = 1;
			}
		}while(flag == 1);
		System.out.println("Last step of User Profile Set");
		u.userFixed(u_name);
	}
	
	//method for daily Expense
	public void addExpense(String u_name) throws Exception {
		//String u_name = u.login();
		//String cat[] = {"food","fuel", "fees", "rent","fruits","Vegetables", "recharge","maintenence" ,"transportation", "stationary" ,"dress" ,"medicine","entertainment","groceries","personal","outfoods","snacks","outing","emi","others"};
		int flag = 0;
		String tran = "";
		System.out.println("---------------------------------------------");
		System.out.println("You are Currently in DAILY EXPENSES PAGE ");
		System.out.println("---------------------------------------------");
		//String u_name = getName();
		do {
			System.out.println("Enter date : ");
			System.out.println("Enter 1 for today's date,2 for another date");
			String choice = input.nextLine();
			String date = "";
			if(choice.equals("1")) {
				date = v.todaysDate();
				System.out.println(date);
				flag = 0;
			}
			else if(choice.equals("2")){
				flag = 0;
				System.out.println("Enter date : (YYYY/MM/DD)");
				date = input.nextLine();
				String check = v.validateDate2(date);
				if(check.equals("1")) {
					flag = 1;
				}
				else {
					flag = 0;
				}
			}
			else {
				flag = 1;
			}
			if(flag == 0) {
				System.out.println("Enter Expense Name : ");
				String exp_name = input.nextLine();
				System.out.println("Enter its Category : ");
				System.out.println("food,fuel,fees,rent(room/hostel),recharge,transportation,stationary,dress");
				System.out.println("Snacks,OutFoods,Entertainment,Groceries,fruits,Vegetables,Personal,Outfoods,Outing,EMI,Maintenence,Others");
				String category = input.nextLine().toLowerCase();
				if(category.equalsIgnoreCase("Others")) {
					System.out.println("Enter its category : ");
					category = input.nextLine();
					flag = 0;
				}
				else {
					boolean ans = v.validateCategory(category);
					if(ans) {
						flag = 0;
					}
					else {
						System.err.println("Error : Invalid category");
						flag = 1;
					}
				}
				if(flag == 0) {
					String mode = "cash upi card other";
					System.out.println("Enter Mode of Transaction : (Cash,UPI,Card)");
					String trans = input.nextLine().toLowerCase();
					if(mode.contains(trans)) {
						flag = 0;
						tran = trans;
					}
					else if(!mode.contains(trans)) {
						System.err.println("Error : Enter a Valid Mode of Transaction");
						flag = 1;
					}
				}
				if(flag == 0) {
					System.out.println("Enter total amount for expense "+exp_name);
					String g_amt = input.nextLine();
					boolean test = v.ValidAmount(g_amt);
					if(test) {
						int amt = Integer.parseInt(g_amt);
						if(amt <= 200000 && amt > 0) {
							flag = 0;
							String month = v.getMonth();
							//DailyExpConnect dc = new DailyExpConnect(date,month,exp_name,category,tran,amt,u_name);
							int rows = db.expConnect(date, month, exp_name, category, tran, amt, u_name);
							if(rows == 1) {
								System.out.println("Expense "+exp_name+" with amount "+amt+" on date "+date+" has been added to your daily expense successfully");
								flag = 0;
								System.out.println("Do you want to continue : (Yes/No)");
								String choice4 = input.nextLine();
								if(choice4.equalsIgnoreCase("Yes")) {
									flag = 1;
								}
								else {
									System.out.println("Moving to index Page");
									d.index(u_name);
									flag = 0;
								}
							}
							else {
								flag = 1;
							}
						}
						else if(amt >= 200000 || amt <= 0){
							System.err.println("Error : Daily Expense would not lead too much");
							flag = 1;
						}
					}
				}
			}
			else {
				flag = 1;
			}
		}while(flag == 1);
	}
	
	//search expense method
	public void searchExp(String u_name) throws Exception {
		int flag = 0;
		System.out.println("---------------------------------------------");
		System.out.println("You are currently in SEARCH EXPENSES Page");
		System.out.println("---------------------------------------------");
		do {
			flag = 0;
			System.out.println("Enter an attribute with which you want to search : ");
			System.out.println("Date,Month,Expense Name,Category,Transaction,Amount");
			String choice = input.nextLine();
			if(choice.equalsIgnoreCase("Date")) {
				flag = 0;
				System.out.println("Enter the date : (YYYY/MM/DD)");
				String date = input.nextLine();
				ResultSet rs = db.withDate(date, u_name);
				System.out.println("Your Expenses Dated on "+date+" are ");
				db.display(rs);
			}
			else if(choice.equalsIgnoreCase("Expense Name")) {
				flag = 0;
				System.out.println("Enter the Expense Name : ");
				String exp_name = input.nextLine();
				ResultSet rs = db.withExpName(exp_name, u_name);
				System.out.println("Your expenses on "+exp_name);
				db.display(rs);
			}
			else if(choice.equalsIgnoreCase("Category")) {
				flag = 0;
				System.out.println("Enter the Category Type : ");
				String category = input.nextLine();
				ResultSet rs = db.withCategory(category, u_name);
				System.out.println("Your expenses on Category "+category);
				db.display(rs);
			}
			else if(choice.equalsIgnoreCase("Transaction")) {
				flag = 0;
				System.out.println("Enter the Transaction Type : (Cash/UPI/Card)");
				String trans = input.nextLine();
				ResultSet rs = db.withTransaction(trans, u_name);
				System.out.println("Your expenses on Transaction Type "+trans);
				db.display(rs);
			}
			else if(choice.equalsIgnoreCase("Month")) {
				flag = 0;
				System.out.println("Enter the Month Name : ");
				String month = input.nextLine();
				ResultSet rs = db.withMonth(month, u_name);
				System.out.println("Your expenses on Month "+month);
				db.display(rs);
			}
			else if(choice.equalsIgnoreCase("Amount")) {
				flag = 0;
				System.out.println("Enter the Amount : ");
				int amt = input.nextInt();
				input.nextLine();
				ResultSet rs = db.withAmount(amt, u_name);
				System.out.println("Your expenses on Amount "+amt);
				db.display(rs);
			}
			else {
				System.out.println("Invalid Category");
				flag = 1;
			}
		}while(flag == 1);
		System.out.println("Do You want to move to INDEX Page : (Yes/No)");
		String move = input.nextLine();
		if(move.equalsIgnoreCase("Yes")) {
			d.index(u_name);
		}
		else {
			u.searchExp(u_name);
		}
	}
	
	//new method for editing our expenses
	public void editExpense(String u_name) throws Exception {
		System.out.println("---------------------------------------------");
		System.out.println("You are in Edit Expense Page ");
		System.out.println("---------------------------------------------");
		int flag = 0;
		do {
			System.out.println("Enter the date on which to be changed : ");
			String c_date = input.nextLine();
			System.out.println("Enter the expense name on which to be changed ");
			String exp_name = input.nextLine();
			System.out.println("Enter which category to be changed : ");
			System.out.println("Date,Expense Name,Category,Transaction,Amount");
			String change = input.nextLine();
			System.out.println("The Expense of the given date "+c_date+" and expense is "+exp_name);
			boolean ans = db.specificSearchPrint(c_date, exp_name, u_name);
			if(ans) {
				flag = 0;
				int rows = 0;
				if(change.equalsIgnoreCase("Date")) {
					flag = 0;
					System.out.println("Enter the date to be changed : ");
					String date = input.nextLine();
					int check = v.validateDate(date);
					if(check == 0) {
						rows = db.updateExpwithDate(c_date, date, u_name, exp_name);
						if(rows >= 1) {
							System.out.println("Expense edited successfully");
							flag = 0;
						}
						else {
							System.err.println("Sorry,Please Re-try Again");
							flag = 1;
						}
					}
					else {
						System.err.println("Invalid Format of Date,Please Enter in YYYY/MM/DD");
						flag = 1;
					}
				}
				if(change.equalsIgnoreCase(exp_name)) {
					flag = 0;
					System.out.println("Enter the expense Name to be changed : ");
					String c_expname = input.nextLine();
					rows = db.updateExpwithName(c_date, exp_name, c_expname, u_name);
					if(rows >= 1) {
						System.out.println("Expense edited successfully");
						flag = 0;
					}
					else {
						System.out.println("Sorry,Please Re-try Again");
						flag = 1;
					}
				}
				if(change.equalsIgnoreCase("Category")) {
					flag = 0;
					System.out.println("Enter the Category to be changed : ");
					System.out.println("food,fuel,fees,rent(room/hostel),recharge,transportation,stationary,dress");
					System.out.println("Snacks,OutFoods,Entertainment,Groceries,fruits,Vegetables,Personal,Outfoods,Outing,EMI,Maintenence,Others");
					String category = input.nextLine();
					boolean res = v.validateCategory(category);
					if(res) {
						rows = db.updateExpwithCategory(c_date,change, u_name, exp_name);
						if(rows >= 1) {
							System.out.println("Expense edited successfully");
							flag = 0;
						}
						else {
							System.out.println("Sorry,Please Re-try Again");
							flag = 1;
						}
					}
					else {
						System.out.println("Sorry,Please Re-try Again");
						flag = 1;
					}
				}
				if(change.equalsIgnoreCase("Transaction")) {
					flag = 0;
					System.out.println("Enter the transaction type : (Cash/UPI/Card)");
					String trans = input.nextLine();
					if(trans.equalsIgnoreCase("Cash") || trans.equalsIgnoreCase("Card") || trans.equalsIgnoreCase("UPI")) {
						rows = db.updateExpwithTransaction(c_date, exp_name, trans, u_name);
						if(rows >= 1) {
							System.out.println("Expense edited successfully");
							flag = 0;
						}
						else {
							System.out.println("Sorry,Please Re-try Again");
							flag = 1;
						}
					}
				}
				if(change.equalsIgnoreCase("Amount")) {
					flag = 0;
					System.out.println("Enter the amount : ");
					String s_amt = input.nextLine();
					boolean res = v.ValidAmount(s_amt);
					if(res) {
						int amt = Integer.parseInt(s_amt);
						rows = db.updateExpwithAmount(c_date, u_name, amt, u_name);
						if(rows >= 1) {
							System.out.println("Expense edited successfully");
							flag = 0;
						}
						else {
							System.out.println("Sorry,Please Re-try Again");
							flag = 1;
						}
					}
					else {
						System.out.println("Invalid amount,Please do re-enter");
						flag = 1;
					}
				}
			}
			else {
				System.out.println("No Records found on the given date and expense Name");
				System.out.println("Please enter a valid details");
				flag = 1;
			}
		}while(flag == 1);
		System.out.println("Do you want to move to INDEX Page : (Yes/No)");
		String move = input.nextLine();
		if(move.equalsIgnoreCase("Yes")) {
			d.index(u_name);
		}
		else {
			u.editExpense(u_name);
		}
	}
	
	public void userProfile(String u_name) throws Exception {
		System.out.println("--------------------------------------------------");
		System.out.println("You are in USER PROFILE PAGE ");
		System.out.println("--------------------------------------------------");
		System.out.println("Your User Profile ");
		System.out.println("--------------------------------------------------");
		db.dispName(u_name);
		System.out.println("--------------------------------------------------");
		System.out.println("Do you want to edit your profile : (Yes/No)");
		String choice = input.nextLine();
		if(choice.equalsIgnoreCase("Yes")) {
			System.out.println("Enter the record to be edited : ");
			String edit[] = {"Name","Phone Number","Age","Gender","Date of Birth","Marital Status","Type","Email"};
			System.out.println("Name,Phone Number,Age,Gender,Date of Birth,Marital Status,Type,Email");
			String choice2 = input.nextLine().toLowerCase();
			int count = 0;
				for(int i = 0;i < edit.length;i++) {
				if(choice2.equalsIgnoreCase(edit[i])) {
					System.out.println("Enter the value for "+choice2+" to be changed");
					String to_change = input.nextLine();
					int rows = db.editUserProf(choice2, to_change, u_name);
					if(rows >= 1) {
						System.out.println(choice2+" has been updated to "+to_change+" successfully");
						System.out.println("After updation");
						db.dispName(u_name);
						System.out.println("Do You want to move to INDEX PAGE : (Yes/No)");
						String choice3 = input.nextLine();
						if(choice3.equalsIgnoreCase("Yes")) {
							d.index(u_name);
						}
						else {
							u.userProfile(u_name);
						}
					}
				}
				else {
					count++;
				}
			}
			if(count == edit.length) {
				u.userProfile(u_name);
			}
		}
		else {
			System.out.println("Do you want to move to INDEX PAGE : (YES/NO)");
			String choice2 = input.nextLine();
			if(choice2.equalsIgnoreCase("Yes")) {
				d.index(u_name);
			}
			else {
				u.userProfile(u_name);
			}
		}
	}
	
	public void userFixed(String u_name) throws Exception {
		int flag = 0,rows = 0;
		ArrayList<String> list = new ArrayList<>();
		ArrayList<String> amt_list = new ArrayList<>();
		ArrayList<String> fixedexp_name = new ArrayList<>();
		ArrayList<String> fixedexp_amt = new ArrayList<>();
		ArrayList<String> fixedexp_time = new ArrayList<>();
		do {
			flag = 0;
			System.out.println("Enter your Income types : ");
			System.out.println("Enter 1 for Salary Income : ");
			System.out.println("Enter 2 for House/Land Rent Income : ");
			System.out.println("Enter 3 for Fixed Deposit Interest Income : ");
			System.out.println("Enter 4 for Bussiness Income : ");
			System.out.println("Enter 4 for Capital Assests Income : ");
			System.out.println("Enter 5 for Other Income : ");
			System.out.println("Note : If you have more than one income,please enter the numbers one by one as input ");
			String income_choice = input.nextLine();
			list.add(income_choice);
			boolean ans = v.IncomeChoiceValid(income_choice);
			if(ans) {
				System.out.println("Enter the amount for your Income : ");
				String income_amount = input.nextLine();
				amt_list.add(income_amount);
				boolean amount_check = v.checkForNumber(income_amount);
				if(amount_check) {
					flag = 0;
					System.out.println("Do you want to continue by adding it : (Yes/No)");
					String choice = input.nextLine();
					if(choice.equalsIgnoreCase("Yes")) {
						flag = 1;
					}
					else {
						flag = 0;
						int flag2 = 0;
						do {
							flag = 0;
							flag2 = 0;
							System.out.println("Enter your Fixed Expenses Type : ");
							System.out.println("Note : Please enter one by one");
							String fixedexp_choice = input.nextLine();
							System.out.println("Enter the Amount for "+fixedexp_choice);
							String amount = input.nextLine();
							System.out.println("Enter a Time Period for "+fixedexp_choice);
							System.out.println("Note : Enter the count of Months");
							String time_period = input.nextLine();
							boolean check1 = v.checkForNumber(amount);
							boolean check2 = v.checkForNumber(time_period);
							fixedexp_name.add(fixedexp_choice);
							fixedexp_amt.add(amount);
							fixedexp_time.add(time_period);
							System.out.println("Do you want to continue by adding it : (Yes/No)");
							String choice2 = input.nextLine();
							if(choice2.equalsIgnoreCase("Yes")) {
								flag2 = 1;
							}
							else {
								flag2 = 0;
								if(check1 && check2) {
									flag2 = 0;
									rows = db.saveUserFixedExpIncome(list, amt_list,fixedexp_name,fixedexp_amt,fixedexp_time,u_name);
									if(rows >= 1) {
										System.out.println("Income And Fixed Expenses Setted Successfully");
										flag = 0;
									}
									else {
										System.out.println("ERROR : Please do Re-Enter All");
										flag = 1;
									}
								}
								else {
									flag2 = 1;
								}
							}
						}while(flag2 == 1);
					}
				}
				else {
					System.out.println("Invalid Amount,Please Enter a Valid Amount");
					flag = 1;
				}
			}
			else {
				System.out.println("Please enter a Valid Choice");
				flag = 1;
			}
		}while(flag == 1);
		System.out.println("Moving to INDEX Page");
		d.index(u_name);
	}
	
	public void changePsw(String u_name) throws Exception {
		System.out.println("---------------------------------------------");
		System.out.println("  You are currently in CHANGE PASSWORD Page ");
		System.out.println("---------------------------------------------");
		int flag = 0;
		do {
			System.out.println("Do you want to change your Password : (Yes/No)");
			String choice = input.nextLine();
			if(choice.equalsIgnoreCase("Yes")) {
				System.out.println("Enter your old password : ");
				String old_psw = input.nextLine();
				String curr_psw = db.checkPsw(u_name);
				if(old_psw.equals(curr_psw)) {
					System.out.println("Enter the new Password to change : (Minimum of Length : 8)");
					String new_psw = input.nextLine();
					//PswValidation pv = new PswValidation(new_psw,change_psw);
					String ans = v.validatePassword(new_psw,new_psw);
					if(ans.length() >= 1) {
						System.out.println(ans);
						flag = 1;
					}
					else {
						flag = 0;
						int rows = db.updatePsw(new_psw, u_name);
						if(rows >= 1) {
							flag = 0;
							System.out.println("Password has been updated successfully");
							System.out.println("Do you want to move to INDEX PAGE : (Yes/No)");
							String move = input.nextLine();
							if(move.equalsIgnoreCase("Yes")) {
								d.index(u_name);
							}
							else {
								flag = 1;
							}	
						}
						else {
							System.out.println("Sorry,please retry again");
							flag = 1;
						}
					}
				}
				else {
					System.out.println("Invalid Old Password");
					flag = 1;
				}
			}
			else if(choice.equalsIgnoreCase("No")) {
				System.out.println("Moving to Index Page");
				d.index(u_name);
			}
			else {
				System.out.println("ERROR : Enter a valid Choice");
				flag = 1;
			}
		}while(flag == 1);
	}
	
	public void personalAssist(String u_name) throws Exception {
		System.out.println("-------------- You are Currently in Personal Assistant Page --------------");
		int flag = 0;
		do {
			System.out.println("Enter 1 to open your personal Diary : ");
			System.out.println("Enter 2 to record your leave days : ");
			String choice = input.nextLine();
			//DateAndDay dd = new DateAndDay();
			if(choice.equals("1")) {
				flag = 0;
				String date,about = "";
				System.out.println("Enter 1 for today's Date : ");
				System.out.println("Enter 2 for another Date : ");
				String date_choice = input.nextLine();
				if(date_choice.equals("1")) {
					flag = 0;
					date = v.todaysDate();
					System.out.println("Your Experiences on Today : "+date);
					System.out.println("Type your today's experiences : ");
					about = input.nextLine();
					int rows = db.connectDairy(u_name,date, about);
					if(rows >= 1) {
						flag = 0;
						System.out.println("Your today's Experiences has been added successfully");
						System.out.println("Enter 1 to move to Index Page : ");
						int move = input.nextInt();
						if(move == 1) {
							d.index(u_name);
							flag = 0;
						}
						else {
							flag = 1;
						}
					}
					else {
						flag = 1;
					}
				}
				else if(date_choice.equals("2")) {
					flag = 0;
					System.out.println("Enter the date : ");
					date = input.nextLine();
					String ans = v.validateDate2(date);
					if(ans.length() == 0) {
						System.out.println("Your Experiences on date : "+date);
						System.out.println("Type your today's experiences : ");
						about = input.nextLine();
						int rows = db.connectDairy(u_name,date, about);
						if(rows >= 1) {
							System.out.println("Your today's Experiences has been added successfully");
							System.out.println("Enter 1 to move to Index");
							int move = input.nextInt();
							if(move == 1) {
								flag = 0;
								d.index(u_name);
							}
							else {
								flag = 1;
							}
						}
						else {
							flag = 1;
						}
					}
					else {
						System.out.println("Error :"+ans);
						flag = 1;
					}
				}
			}
			else if(choice.equals("2")) {
				flag = 0;
				System.out.println("Enter From Date : (YYYY/MM/DD)");
				String from_date = input.nextLine();
				System.out.println("Enter To Date : (YYYY/MM/DD)");
				String to_date = input.nextLine();
				String ans1 = v.validateDate2(from_date);
				String ans2 = v.validateDate2(to_date);
				if(ans1.length() == 0 && ans2.length() == 0) {
					String ans3 = v.ValidateFromAndTo(from_date, to_date);
					if(ans3.length() == 0) {
						System.out.println("Enter the reason : ");
						String reason = input.nextLine();
						long ttl_days = v.ttl_days(from_date, to_date);
						int rows = db.connectLeave(u_name,from_date, to_date, reason,ttl_days);
						if(rows >= 1) {
							System.out.println("Your leave days has been recorded Successfully");
							System.out.println("Enter 1 to move to Index");
							int move = input.nextInt();
							if(move == 1) {
								flag = 0;
								d.index(u_name);
							}
							else {
								flag = 1;
							}
						}
						else {
							System.out.println("Error,Please Re-enter");
							flag = 1;
						}
					}
					else {
						System.out.println("Error : "+ans3);
						flag = 1;
					}
				}
				else {
					System.out.println("Error : "+ans1);
					System.out.println("Error : "+ans2);
					flag = 1;
				}
			}
			else {
				System.out.println("Error : Please Enter a valid choice");
				flag = 1;
			}
		}while(flag == 1);
	}
	
	public void viewPersonalAssist(String u_name) throws Exception {
		int flag = 0;
		System.out.println("----------------------------------------------");
		System.out.println("You are Currently in Personal View Page");
		System.out.println("----------------------------------------------");
		do {
			System.out.println("Enter 1 to view your personal diary");
			System.out.println("Enter 2 to view your leave days");
			System.out.println("Enter 3 to move to Index page");
			String choice = input.nextLine();
			if(choice.equals("1")) {
				flag = 0;
				System.out.println("You have entered 1 to view your Personal Dairy");
				db.viewDairy(u_name);
				System.out.println("Enter 1 to move to Index Page");
				int move = input.nextInt();
				if(move == 1) {
					d.index(u_name);
				}
				else {
					flag = 1;
				}
			}
			else if(choice.equals("2")) {
				flag = 0;
				System.out.println("You have entered 2 to view your Leave days");
				db.viewLeave(u_name);
				System.out.println("Enter 1 to move to Index Page");
				int move = input.nextInt();
				if(move == 1) {
					d.index(u_name);
				}
				else {
					flag = 1;
				}
			}
			else if(choice.equals("3")) {
				System.out.println("You have entered 3,moving to Index Page");
				d.index(u_name);
				flag = 0;
			}
			else {
				System.out.println("Error : Invalid choice,Please enter a Valid one");
				flag = 1;
			}
		}while(flag == 1);
	}
	
	public void monthlyReport(String u_name) throws Exception {
		System.out.println("---------------------------------------------");
		System.out.println("You are Currently in MONTHLY REPORT PAGE");
		System.out.println("---------------------------------------------");
		//to get todays Date
		String t_date = v.todaysDate();
		System.out.println("Todays Date : "+t_date);
		String st[] = t_date.split("/");
		String s = st[2];
		int date = Integer.parseInt(s);
		//Check end of the month
		if(date >= 01 && date <= 31) {
			System.out.println("Your Monthly Analysis : ");
			db.displayExp(u_name);
		}
		else {
			System.out.println("Sorry user You can only able to edit this after 25th of this month");
		}
		System.out.println("---------------------------------");
		System.out.println("Do you want to INDEX Page : (Yes/No)");
		String choice = input.nextLine();
		if(choice.equalsIgnoreCase("Yes")) {
			System.out.println("Moving to INDEX Page");
			d.index(u_name);
			System.out.println("---------------------------------");
		}
		else {
			u.monthlyReport(u_name);
		}
	}
	
	public void downloadReport(String u_name) throws Exception {
		System.out.println("You can able to Download Your Reports Here : ");
		System.out.println("Enter 1 to Download Your Monthly Expenses");
		System.out.println("Enter 2 to Download Your Monthly **");
		String choice = input.nextLine();
		if(choice.equals("1")) {
			String month = v.getMonth();
			System.out.println("Enter The Drive Name to Save it : ");
			String drive_name = input.nextLine().toUpperCase();
			File f;
			//Run Time PolyMorphism -> OverRidding
			char c = drive_name.charAt(0);
			if(drive_name.length() == 1 && Character.isAlphabetic(c)) {
				String f_name = c + ":\\" + month +".txt";
				f = new File(f_name);
				FileWriter fw = new FileWriter(f);
				String msg = db.getMonthlyRep(u_name);
				String st[] = msg.split(",");
				for(int i = 0;i < st.length;i++) {
					fw.write(st[i]);
					fw.write("\n");
				}
				System.out.println("Your Monthly Report Has been downloaded Successfully in "+c+" Drive with File name "+ month + ".doc");
				fw.close();
			}
			else {
				System.err.println("Please enter a Valid Drive Name");
			}
		}
	}
}