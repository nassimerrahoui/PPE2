package persistance;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class connectBase 
{
	String url;
	String login;
	String passwd;
	Connection cn;
	Statement st;
	String message;
	
	public connectBase()
	{
		url = "jdbc:mysql://localhost/sport?autoReconnect=true&useSSL=false";
		login = "root";
		passwd = "";
		cn = null;
		st = null;
		message = "";
	}
	
	public String bddConnexion()
	{
		try	
		{
			Class.forName("com.mysql.jdbc.Driver");
			cn = (Connection) DriverManager.getConnection(url, login, passwd);
			st = (Statement) cn.createStatement();
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
		
		return message;
	}
	
}
