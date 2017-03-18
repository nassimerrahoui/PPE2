package persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedSet;
import java.util.TreeSet;

import metier.Candidat;
import metier.Inscriptions;
import metier.Personne;


public class personneData extends Personne
{
	
	private static final long serialVersionUID = -3493675220278815873L;

	protected personneData(Inscriptions inscriptions, String nom, String prenom, String mail)
	{
		super(inscriptions, nom, prenom, mail);
	}


	@SuppressWarnings("static-access")
	public static void create(Personne obj)
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
			System.out.println("La personne n'a pas �t� cr��e.");
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
			System.out.println("Le candidat n'a pas �t� supprim�.");
	    }
	}
	
	public static SortedSet<Candidat> select(Inscriptions inscriptions) 
	{
		SortedSet<Candidat> Candidats = new TreeSet<>();

		try 
		{
			String sql = "{call getPersonnes()}";
			java.sql.Statement cs = accesBase.getInstance().createStatement();
			ResultSet result = cs.executeQuery(sql);
			
			// boucle pour ajouter une personne dans la couche m�tier
            while(result.next())
            {
    			Candidat unCandidat = new personneData(inscriptions, result.getString("nom_candidat"),
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