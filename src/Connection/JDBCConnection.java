package Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JDBCConnection
{
	private Connection con=null;
	private Statement stmt=null;
	public Statement confg()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/parsing","root","rat");
			stmt=con.createStatement();
		}	
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return stmt;
	}
}
