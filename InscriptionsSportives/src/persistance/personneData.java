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
	public static void create(Personne obj)
	{
		try	
		{
			String sql = "{call createPersonne(?, ?, ?)}";
			java.sql.CallableStatement cs = AccesBase.getInstance().prepareCall(sql);
        	cs.setString(1,obj.getNom());
        	cs.setString(2,obj.getPrenom());
        	cs.setString(3,obj.getMail());
			AccesBase.executeUpdate(cs);
			obj.setId(cs.RETURN_GENERATED_KEYS);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("La personne n'a pas été créée.");
		}
	}
	
	
	public static void update(Personne obj)
	{
		try 
		{
			String sql = "{call setPersonneCarac(?, ?, ?, ?)}";
        	java.sql.CallableStatement cs = AccesBase.getInstance().prepareCall(sql);
        	cs.setInt(1,obj.getId());
        	cs.setString(2,obj.getNom());
        	cs.setString(3,obj.getPrenom());
        	cs.setString(4,obj.getMail());
        	AccesBase.executeUpdate(cs);
		}
        						
        catch (SQLException e)
        {
        	e.printStackTrace();
        }
	}

	public static void delete(Personne obj)
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
			System.out.println("La personne n'a pas été supprimé.");
	    }
	}
	
	public static SortedSet<Candidat> select(Inscriptions inscriptions) 
	{
		SortedSet<Candidat> Candidats = new TreeSet<>();
		try 
		{
			String sql = "{call getPersonnes()}";
			java.sql.Statement cs = AccesBase.getInstance().createStatement();
			ResultSet result = cs.executeQuery(sql);
			
			// boucle pour ajouter une personne dans la couche métier
            while(result.next())
            {
    			Candidat unCandidat = inscriptions.createPersonne(result.getString("nom_candidat"),
    												result.getString("prenom_personne"),
    												result.getString("mail"));
    			unCandidat.setId(result.getInt("id_candidat"));
    			Candidats.add(unCandidat);
            }    
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Il n'y a pas de personnes");
		}
		return Candidats;
	}
}