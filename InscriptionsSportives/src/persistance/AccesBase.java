package persistance;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class AccesBase
{
	private static String url = "jdbc:mysql://localhost/sport?autoReconnect=true&useSSL=false";
	private static String login = "root";
	private static String passwd = "";
	private static Connection cn;
	private static boolean enChargement = false;
	
	public static void setEnChargement(boolean enChargement)
	{
		AccesBase.enChargement = enChargement;
	}
	
	/** Création de la connexion @return */
	private AccesBase()
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
	
	public static void executeUpdate(java.sql.CallableStatement cs) throws SQLException
	{
		if (!enChargement)
			cs.executeUpdate();
	}
	
	/** Retourne notre connexion ou la créé si elle n'existe pas @return */
	public static Connection getInstance(){
		if(cn == null)
		{
			new AccesBase();
		}
	    return cn;
	}   
}