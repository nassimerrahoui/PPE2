package persistance;

import java.sql.SQLException;
import java.util.SortedSet;
import java.util.TreeSet;

import metier.Candidat;
import metier.Inscriptions;

public class candidatData
{
	public static SortedSet<Candidat> select(Inscriptions inscriptions) 
	{
		SortedSet<Candidat> Candidats = new TreeSet<>();

		for (Candidat p : personneData.select(inscriptions))
		{
			Candidats.add(p);
		}
		
		for (Candidat p : equipeData.select(inscriptions))
		{
			Candidats.add(p);
		}
		
		return Candidats;
	}
	
	public static void delete(Candidat obj)
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
