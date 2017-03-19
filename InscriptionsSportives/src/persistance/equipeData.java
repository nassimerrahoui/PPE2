package persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedSet;
import java.util.TreeSet;

import metier.Candidat;
import metier.Equipe;
import metier.Inscriptions;
import metier.Personne;


public class equipeData extends Equipe
{

	private static final long serialVersionUID = -3393260894899407673L;

	protected equipeData(Inscriptions inscriptions, String nom)
	{
		super(inscriptions, nom);
	}

	@SuppressWarnings("static-access")
	public static void create(Equipe obj)
	{
		try	
		{
			String sql = "{call createEquipe( ? )}";
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
	
	public static void update(Candidat obj)
	{
		try 
		{
			String sql = "{call setEquipeCarac( ? , ?)}";
        	java.sql.CallableStatement cs = accesBase.getInstance().prepareCall(sql);
        	cs.setInt(1, obj.getId());
        	cs.setString(2, obj.getNom());
        	cs.executeUpdate();
		}
        						
        catch (SQLException e)
        {
        	e.printStackTrace();
        	System.out.println("L'équipe n'a pas été mise à jour.");
        }
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
			System.out.println("L'équipe n'a pas été supprimé.");
	    }
	}
	
	public static void remove(Personne membre, Equipe equipe)
	{
		try 
		{
			String sql = "{call removePersonneEquipe( ? , ? )}";
			java.sql.CallableStatement cs = accesBase.getInstance().prepareCall(sql);
			cs.setInt(1,equipe.getId());
			cs.setInt(2,membre.getId());
			cs.executeUpdate(); 	
	    } 
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("La personne n'a pas été enlevé de l'équipe.");
	    }
	}
	
	/* TODO fonctionne mais essaye d'ajouter les des personnes deja présentes dans les équipes */ 
	public static void addMembre(Personne membre, Equipe equipe)
	{
		try	
		{
			String sql = "{call addMembreEquipe( ? , ? )}";
			java.sql.CallableStatement cs = accesBase.getInstance().prepareCall(sql);
        	cs.setObject(1,membre.getId());
        	cs.setInt(2,equipe.getId());
			cs.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("La personne n'a pas été ajoutée dans l'équipe.");
		}
	}
	
	public static SortedSet<Candidat> select(Inscriptions inscriptions) 
	{
		SortedSet<Candidat> Candidats = new TreeSet<>();

		try 
		{
			String sql = "{call getEquipes()}";
			java.sql.Statement cs = accesBase.getInstance().createStatement();
			ResultSet result = cs.executeQuery(sql);
			
			// boucle pour ajouter une equipe dans la couche métier
            while(result.next())
            {
            	Candidat unCandidat = new equipeData(inscriptions, result.getString("nom_candidat"));
    			unCandidat.setId(result.getInt("id_candidat"));
        		Candidats.add(unCandidat);
            }    
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Il n'y a pas de candidats");
		}
		return Candidats;
	}
	
	/** Charger les membres d'une ou plusieures équipes */
	public static void selectMembre(Inscriptions inscriptions)
	{
		try 
		{
			for (Equipe e : inscriptions.getEquipes()) 
			{
				String sql = "{call getMembreEquipe( ? )}";
				java.sql.CallableStatement cs = accesBase.getInstance().prepareCall(sql);
				cs.setInt(1, e.getId());
				ResultSet result = cs.executeQuery();
		        while(result.next())
		        {
		            for (Personne membre : inscriptions.getPersonnes()) 
		            {
						if(result.getInt("id_candidat") == membre.getId())
						{
							e.add(membre);
							break;
						}
					}
		        } 
			}   
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Il n'y a aucun candidat appartenant à une équipe");
		}
		
	}
}