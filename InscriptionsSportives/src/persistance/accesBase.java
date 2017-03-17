package persistance;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class accesBase
{
	private static String url = "jdbc:mysql://localhost/sport?autoReconnect=true&useSSL=false";
	private static String login = "root";
	private static String passwd = "";
	private static Connection cn;
	
	/** Création de la connexion @return */
	private accesBase()
	{
		try	
		{
			cn = (Connection) DriverManager.getConnection(url, login, passwd);
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/** Retourne notre connexion ou la créé si elle n'existe pas @return */
	public static Connection getInstance(){
		if(cn == null){
			synchronized(accesBase.class)
			{
				new accesBase();
			}
		}
	    return cn;
	}   
}