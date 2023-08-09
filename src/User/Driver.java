package User;
import java.util.*;
public class Driver {
	static User u = new User();
	static Driver d = new Driver();
	static DBConnect db = new DBConnect();
	static Validation v = new Validation();
	static Scanner input = new Scanner(System.in);
	public static void main(String[] args) throws Exception {
		String log = "";
		System.out.println("----------------------------------------------------");
		System.out.println("           Welcome to Expense Tracker");
		System.out.println("----------------------------------------------------");
		int flag = 0;
		do {
			System.out.println("Enter 1 to Login");
			System.out.println("If New User Enter 2 to SignUp ");
			System.out.println("Enter your choice : ");
			String choice = input.next();
			if(choice.equals("1")) {
				log = u.login();
				//System.out.println(log);
				if(log.length() > 1) {
					//System.out.println(log);
					d.index(log);
					flag = 0;
				}
				else {
					flag = 1;
				}
			}
			else if(choice.equals("2")) {
				u.signup();
				flag = 0;
			}
			else {
				System.err.println("Invalid Choice,Please re enter");
				flag = 1;
			}
		}while(flag == 1);
	}
	
	public void index(String u_name) throws Exception {
		System.out.println("------------------------------------");
		System.out.println("Your are Currently in INDEX PAGE ");
		System.out.println("------------------------------------");
		System.out.println("Enter 1 for Daily Expense");
		System.out.println("Enter 2 for Search Expense");
		System.out.println("Enter 3 for View Expense");
		System.out.println("Enter 4 to  Edit Expense");
		System.out.println("Enter 5 for Monthly Report");
		System.out.println("Enter 6 for User Profile");
		System.out.println("Enter 7 to  Change Password");
		System.out.println("Enter 8 for Personal Assistant");
		System.out.println("Enter 9 to  View your Personal");
		System.out.println("Enter 10 to Download the reports : ");
		System.out.println("Enter 11 for Log Out");
		System.out.println("------------------------------------");
		System.out.println("Enter you choice : ");
		String move = input.next();
		if(move.equals("1")) {
			u.addExpense(u_name);
		}
		if(move.equals("2")) {
			System.out.println("---------------------------------------------");
			System.out.println("You are currently in SEARCH EXPENSES Page");
			System.out.println("---------------------------------------------");
			u.searchExp(u_name);
		}
		if(move.equals("3")) {
			db.viewExpense(u_name);
		}
		//bug reported under update query,status : failed
		if(move.equals("4")) {
			u.editExpense(u_name);
		}
		if(move.equals("5")) {
			u.monthlyReport(u_name);
		}
		if(move.equals("6")) {
			u.userProfile(u_name);
		}
		if(move.equals("7")) {
			u.changePsw(u_name);
		}
		if(move.equals("8")) {
			u.personalAssist(u_name);
		}
		if(move.equals("9")) {
			u.viewPersonalAssist(u_name);
		}
		if(move.equals("10")) {
			u.downloadReport(u_name);
		}
		if(move.equals("11")) {
			System.out.println("Do You want to Log out : (Yes/No)");
			String choice = input.nextLine();
			if(choice.equalsIgnoreCase("Yes")) {
				System.out.println("Logging out from your account");
				main(null);
			}
			else {
				d.index(u_name);
			}
		}
		else {
			System.out.println("Invalid choice");
			d.index(u_name);
		}
	}
}
