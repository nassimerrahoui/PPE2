package persistance;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class connectBase
{
	private static String url;
	private static String login;
	private static String passwd;
	private static Connection cn;
	
	private connectBase()
	{
		url = "jdbc:mysql://localhost/sport?autoReconnect=true&useSSL=false";
		login = "root";
		passwd = "";
		cn = null;
	}
	
	/** Création de la connexion @return */
	private static void bddConnexion()
	{
		try	
		{
			Class.forName("com.mysql.jdbc.Driver");
			cn = (Connection) DriverManager.getConnection(url, login, passwd);
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	/** Retourne notre connexion et la créer si elle n'existe pas @return */
	public Connection getConnexion(){
		if(cn == null){
			bddConnexion();
		}
	    return cn;   
	}   
	
}
