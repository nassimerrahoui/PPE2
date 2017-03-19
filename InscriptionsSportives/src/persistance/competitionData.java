package persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.SortedSet;
import java.util.TreeSet;

import metier.Candidat;
import metier.Competition;
import metier.Equipe;
import metier.Inscriptions;
import metier.Personne;


public class competitionData extends Competition
{
	private static final long serialVersionUID = -9162867045729982345L;

	competitionData(Inscriptions inscriptions, String nom, LocalDate dateCloture, boolean enEquipe)
	{
		super(inscriptions, nom, dateCloture, enEquipe);
	}

	@SuppressWarnings("static-access")
	public static void create(Competition obj)
	{
		try	
		{
			String sql = "{call createCompetition( ? , ? , ? )}";
			java.sql.CallableStatement cs = accesBase.getInstance().prepareCall(sql);
        	cs.setString(1,obj.getNom());
        	
        	java.sql.Date dateClotureSql = java.sql.Date.valueOf(obj.getDateCloture());
        	cs.setDate(2,dateClotureSql);
        	
        	cs.setBoolean(3,obj.estEnEquipe());
			cs.executeUpdate();
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
        	java.sql.CallableStatement cs = accesBase.getInstance().prepareCall(sql);
        	
        	cs.setString(1,obj.getNom());
        	System.out.println(obj.getNom());
        	
        	java.sql.Date dateClotureSql = java.sql.Date.valueOf(obj.getDateCloture());
        	cs.setDate(2,dateClotureSql);
        	System.out.println(obj.getDateCloture());
        	
        	cs.setBoolean(3,obj.estEnEquipe());
        	System.out.println(obj.estEnEquipe());
        	
        	cs.setInt(4,obj.getId());
        	System.out.println(obj.getId());
        	
        	cs.executeUpdate();
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
			java.sql.CallableStatement cs = accesBase.getInstance().prepareCall(sql);
			cs.setLong(1,obj.getId());
			cs.executeUpdate(); 	
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
			java.sql.CallableStatement cs = accesBase.getInstance().prepareCall(sql);
			cs.setInt(1,competition.getId());
			cs.setInt(2,candidat.getId());
			cs.executeUpdate(); 	
	    } 
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Le candidat n'a pas été enlevé de la compétition.");
	    }
	}
		
	public static SortedSet<Competition> select(Inscriptions inscriptions) 
	{
		SortedSet<Competition> Competitions = new TreeSet<>();
		try 
		{
			String sql = "{call getCompetitions()}";
			java.sql.Statement cs = accesBase.getInstance().createStatement();
			ResultSet result = cs.executeQuery(sql);
	        while(result.next())
	        {
	            LocalDate dateClotureJava = result.getDate("dateCloture").toLocalDate();
	            Competition uneCompetition = new competitionData(inscriptions, result.getString("nom_competition"),
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
	
	
	/** Charger les candidats inscrits dans une ou plusieurs compétitions */
	public static void selectInscrit(Inscriptions inscriptions)
	{
		try 
		{
			for (Competition c : inscriptions.getCompetitions()) 
			{
				String sql = "{call getCandidatCompetition( ? )}";
				java.sql.CallableStatement cs = accesBase.getInstance().prepareCall(sql);
				cs.setInt(1, c.getId());
				ResultSet result = cs.executeQuery();
		        while(result.next())
		        {
		            for (Personne p : inscriptions.getPersonnes()) 
		            {
						if(result.getInt("id_candidat") == p.getId())
						{
							try
							{
								c.add(p);
							} 
							catch (addCloseException | enEquipeException e1) 
							{
									e1.printStackTrace();
							}
							
							
							break;
						}
					}
		            
		            for (Equipe e : inscriptions.getEquipes()) 
		            {
						if(result.getInt("id_candidat") == e.getId())
						{
							try 
							{
								c.add(e);
							} catch (addCloseException | enEquipeException e1)
							{
								e1.printStackTrace();
							}
							break;
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