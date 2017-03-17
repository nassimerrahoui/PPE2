package persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedSet;
import java.util.TreeSet;

import metier.Candidat;
import metier.Inscriptions;
import metier.Personne;


public class personneData
{
	
	@SuppressWarnings("static-access")
	public void create(Personne obj)
	{
		try	
		{
			String sql = "{call createPersonne(?, ?, ?)}";
			java.sql.CallableStatement cs = accesBase.getInstance().prepareCall(sql);
        	cs.setString(1,obj.getNom());
        	cs.setString(2,obj.getPrenom());
        	cs.setString(3,obj.getMail());
			cs.executeUpdate();
			obj.setId(cs.RETURN_GENERATED_KEYS);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("La personne n'a pas été créée.");
		}
	}
	
	
	public void update(Personne obj)
	{
		try 
		{
			String sql = "{call setCandidatCarac(?, ?, ?, ?)}";
        	java.sql.CallableStatement cs = accesBase.getInstance().prepareCall(sql);
        	cs.setInt(1,obj.getId());
        	cs.setString(2,obj.getNom());
        	cs.setString(3,obj.getPrenom());
        	cs.setString(4,obj.getMail());
        	cs.executeUpdate();
		}
        						
        catch (SQLException e)
        {
        	e.printStackTrace();
        }
	}

	public void delete(Personne obj)
	{
		try 
		{
			String sql = "{call deleteCandidat( ? )}";
			java.sql.CallableStatement cs = accesBase.getInstance().prepareCall(sql);
			cs.setInt(1,obj.getId());
			cs.executeUpdate(); 	
	    } 
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Le candidat n'a pas été supprimé.");
	    }
	}	
}