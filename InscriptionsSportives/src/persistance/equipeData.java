package persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedSet;

import metier.Candidat;
import metier.Equipe;
import metier.Personne;


public class equipeData
{

	@SuppressWarnings("static-access")
	public void create(Equipe obj)
	{
		try	
		{
			String sql = "{call createEquipe("+ "\" ? \"" + ")}";
			java.sql.CallableStatement cs = accesBase.getInstance().prepareCall(sql);
			cs.setString(1, obj.getNom());
			cs.executeUpdate();
			obj.setId(cs.RETURN_GENERATED_KEYS);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("L'équipe n'a pas été créée.");
		}
	}
	
	public void update(Candidat obj)
	{
		try 
		{
			String sql = "{call setCandidatCarac( ? , ? )}";
        	java.sql.CallableStatement cs = accesBase.getInstance().prepareCall(sql);
        	cs.setInt(1, obj.getId());
        	cs.setString(2, obj.getNom());
        	cs.executeUpdate();
		}
        						
        catch (SQLException e)
        {
        	e.printStackTrace();
        }
	}

	public void delete(Candidat obj)
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
			System.out.println("L'équipe n'a pas été supprimé.");
	    }
	}
	
	public void remove(Personne membre, Equipe equipe)
	{
		try 
		{
			String sql = "{call removePersonneEquipe( ? , ? )}";
			java.sql.CallableStatement cs = accesBase.getInstance().prepareCall(sql);
			cs.setInt(1,membre.getId());
			cs.setInt(1,equipe.getId());
			cs.executeUpdate(); 	
	    } 
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("La personne n'a pas été enlevé de l'équipe.");
	    }
	}
	
	public void addMembre(Personne membre, Equipe equipe)
	{
		try	
		{
			String sql = "{call addMembre( ? , ? )}";
			java.sql.CallableStatement cs = accesBase.getInstance().prepareCall(sql);
        	cs.setObject(1,membre.getId());
        	cs.setInt(1,equipe.getId());
			cs.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("La personne n'a pas été ajoutée dans l'équipe.");
		}
	}
	
	
	// TO DO, comment charger tout les membres des équipes
	public SortedSet<Personne> getMembre(int id) 
	{
		SortedSet<Personne> membres = null;
		Personne personne = null;
		try 
		{
			String sql = "{call getMembreEquipe(?)}";
			java.sql.CallableStatement cs = accesBase.getInstance().prepareCall(sql);
			cs.setInt(1,id);
			ResultSet result = cs.executeQuery();
            while(result.next())
            {
            	membres.add(personne);
            }    
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Cette equipe n'existe pas encore dans l'application");
		}
		return membres;
	}
}