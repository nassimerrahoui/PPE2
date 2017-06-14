

package persistance;

import java.sql.SQLException;
import metier.Candidat;
import metier.Competition;


public class candidatData
{
	public static void delete(Candidat obj)
	{
		try 
		{
			String sql = "{call deleteCandidat( ? )}";
			java.sql.CallableStatement cs = AccesBase.getInstance().prepareCall(sql);
			cs.setInt(1,obj.getId());
			AccesBase.executeUpdate(cs); 	
	    } 
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Le candidat n'a pas été supprimé.");
	    }
	}
	
	public static void update(Candidat obj)
	{
		try 
		{
			String sql = "{call setCandidatCarac( ? , ?)}";
        	java.sql.CallableStatement cs = AccesBase.getInstance().prepareCall(sql);
        	cs.setInt(1, obj.getId());
        	cs.setString(2, obj.getNom());
        	AccesBase.executeUpdate(cs);
		}
        						
        catch (SQLException e)
        {
        	e.printStackTrace();
        	System.out.println("Le candidat n'a pas été mis à jour.");
        }
	}
	
	public static void inscriptionCandidat(Candidat candidat, Competition competition)
	{
		try	
		{
			String sql = "{call inscriptionCandidat( ? , ? )}";
			java.sql.CallableStatement cs = AccesBase.getInstance().prepareCall(sql);
        	cs.setObject(1,candidat.getId());
        	cs.setInt(2,competition.getId());
			AccesBase.executeUpdate(cs);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Le candidat n'a pas été ajoutée dans la compétition.");
		}
	}
}
