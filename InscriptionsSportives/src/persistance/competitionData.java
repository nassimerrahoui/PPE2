package persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.SortedSet;
import java.util.TreeSet;

import metier.Candidat;
import metier.Competition;
import metier.Competition.addCloseException;
import metier.Competition.enEquipeException;
import metier.Equipe;
import metier.Inscriptions;
import metier.Personne;


public class competitionData 
{
	
	@SuppressWarnings("static-access")
	public static void create(Competition obj)
	{
		try	
		{
			String sql = "{call createCompetition( ? , ? , ? )}";
			java.sql.CallableStatement cs = AccesBase.getInstance().prepareCall(sql);
        	cs.setString(1,obj.getNom());
        	
        	java.sql.Date dateClotureSql = java.sql.Date.valueOf(obj.getDateCloture());
        	cs.setDate(2,dateClotureSql);
        	
        	cs.setBoolean(3,obj.getEnEquipe());
			AccesBase.executeUpdate(cs);
			obj.setId(cs.RETURN_GENERATED_KEYS);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("La compétition n'a pas été créée.");
		}
	}
	
	// TODO ne fonctionne pas mais retourne aucune erreur alors que la procédure fonctionne dans la console SGBD
	public static void update(Competition obj)
	{
		try 
		{
			String sql = "{call setCompetitionCarac( ? , ? , ? , ? )}";
        	java.sql.CallableStatement cs = AccesBase.getInstance().prepareCall(sql);
        	
        	cs.setString(1,obj.getNom());
        	java.sql.Date dateClotureSql = java.sql.Date.valueOf(obj.getDateCloture());
        	cs.setDate(2,dateClotureSql);
        	cs.setBoolean(3,obj.getEnEquipe());
        	cs.setInt(4,obj.getId());
        	
        	AccesBase.executeUpdate(cs);
		}
        						
        catch (SQLException e)
        {
        	e.printStackTrace();
        	System.out.println("La compétition n'a pas été mise à jour.");
        }
	}

	public static void delete(Competition obj)
	{
		try 
		{
			String sql = "{call deleteCompetition( ? )}";
			java.sql.CallableStatement cs = AccesBase.getInstance().prepareCall(sql);
			cs.setLong(1,obj.getId());
			AccesBase.executeUpdate(cs); 	
	    } 
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("La compétition n'a pas été supprimé.");
	    }
	}
	
	public static void remove(Competition competition, Candidat candidat)
	{
		try 
		{
			String sql = "{call removeCandidatCompetition( ? , ? )}";
			java.sql.CallableStatement cs = AccesBase.getInstance().prepareCall(sql);
			cs.setInt(1,competition.getId());
			cs.setInt(2,candidat.getId());
			AccesBase.executeUpdate(cs); 	
	    } 
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Le candidat n'a pas été enlevé de la compétition.");
	    }
	}
	
	// TODO remplacer SortedSet par void
	public static SortedSet<Competition> select(Inscriptions inscriptions) 
	{
		SortedSet<Competition> Competitions = new TreeSet<>();
		try 
		{
			String sql = "{call getCompetitions()}";
			java.sql.Statement cs = AccesBase.getInstance().createStatement();
			ResultSet result = cs.executeQuery(sql);
	        while(result.next())
	        {
	            LocalDate dateClotureJava = result.getDate("dateCloture").toLocalDate();
	            Competition uneCompetition = inscriptions.createCompetition(result.getString("nom_competition"),
						dateClotureJava,
						result.getBoolean("estEnEquipe"));
	            uneCompetition.setId(result.getInt("id_competition"));
	            Competitions.add(uneCompetition);
	        }    
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Cette compétition n'existe pas encore dans l'application");
		}
		
		return Competitions;
	}
	
	
	/** Charger les candidats inscrits dans une ou plusieurs compétitions 
	 * @throws addCloseException 
	 * @throws enEquipeException */
	public static void selectInscrit(Inscriptions inscriptions) throws enEquipeException, addCloseException
	{
		try 
		{
			for (Competition c : inscriptions.getCompetitions()) 
			{
				String sql = "{call getCandidatCompetition( ? )}";
				java.sql.CallableStatement cs = AccesBase.getInstance().prepareCall(sql);
				cs.setInt(1, c.getId());
				ResultSet result = cs.executeQuery();
		        while(result.next())
		        {
		        	// TODO remplacer les deux for par une boucle utilisant MAP
		            for (Personne p : inscriptions.getPersonnes()) 
						if(result.getInt("id_candidat") == p.getId())
						{
							c.add(p);
						}
		            for (Equipe e : inscriptions.getEquipes()) 
		            {
						if(result.getInt("id_candidat") == e.getId())
						{
							c.add(e);
						}
					}
		        } 
			}   
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Il n'y a aucun candidat inscrit dans une compétition");
		}
		
	}
}