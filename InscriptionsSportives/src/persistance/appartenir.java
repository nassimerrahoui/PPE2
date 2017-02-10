package persistance;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class appartenir
{	
	lectureBase CO;
	ResultSet rs;
	
	public String addMembre(int idPersonne, int idEquipe)
	{
		CO = new lectureBase();
		
		try	
		{
			CO.bddConnexion();
			java.sql.CallableStatement cs = CO.cn.prepareCall("{call addMembre("+idPersonne+","+idEquipe+")}"); 
		    cs.execute();
			CO.message = "Membre ajouté";
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
			CO.message = "Erreur d'ajout de membre";
		}
		return CO.message;
		
	}

}
