package persistance;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class lectureBase 
{
	public static String afficher()
	{
	String url = "jdbc:mysql://localhost/sport";
	String login = "root";
	String passwd = "";
	Connection cn = null;
	Statement st = null;
	//ResultSet rs = null;
	String message = "";
	
		try	
		{
			Class.forName("com.mysql.jdbc.Driver");
			cn = (Connection) DriverManager.getConnection(url, login, passwd);
			st = (Statement) cn.createStatement();
			/*String sql = "SELECT * FROM salarie";
			rs = st.executeQuery(sql);*/
			message = "Vous êtes connecté";
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
			message = "Erreur de connexion";
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		/*finallys
		{
			try
			{
				cn.close();
				st.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}*/
		return message;
	}
	
}
