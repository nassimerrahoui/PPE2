package persistance;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class connectBase
{
	private static String url = "jdbc:mysql://localhost/sport?autoReconnect=true&useSSL=false";
	private static String login = "root";
	private static String passwd = "";
	private static Connection cn;
	
	/** Création de la connexion @return */
	private connectBase()
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
			synchronized(connectBase.class)
			{
				new connectBase();
				System.out.println("Nouvelle Connexion !");
			}
		}
		else
		{
			System.out.println("Connexion existante !");
		}
	    return cn;
	}   
}