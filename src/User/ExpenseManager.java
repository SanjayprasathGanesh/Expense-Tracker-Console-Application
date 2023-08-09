package User;
import java.sql.SQLException;
public interface ExpenseManager {
	String login() throws SQLException;
	void signup() throws Exception;
	
}
