package persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;

import metier.Competition;
import metier.Inscriptions;


public class competitionData
{
	@SuppressWarnings("static-access")
	public void create(Competition obj)
	{
		try	
		{
			String sql = "{call createCompetition( ? , ? , ? , ?)}";
			java.sql.CallableStatement cs = accesBase.getInstance().prepareCall(sql);
        	cs.setString(1,obj.getNom());
        	
        	Date dateClotureJava = Date.from(obj.getDateCloture().atStartOfDay(ZoneId.systemDefault()).toInstant());
        	java.sql.Date dateClotureSql = new java.sql.Date(dateClotureJava.getTime());
        	cs.setDate(2,dateClotureSql);
        	
        	Date dateOuvertureJava = new Date();
        	java.sql.Date dateOuvertureSql = new java.sql.Date(dateOuvertureJava.getTime());
        	cs.setDate(3, dateOuvertureSql);
        	
        	cs.setBoolean(4,obj.estEnEquipe());
			cs.executeUpdate();
			obj.setId(cs.RETURN_GENERATED_KEYS);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("La compétition n'a pas été créée.");
		}
	}
	
	public Competition update(Competition obj)
	{
		try 
		{
			String sql = "{call setCompetitionCarac(? )}";
        	java.sql.CallableStatement cs = accesBase.getInstance().prepareCall(sql);
        	cs.setLong(1,obj.getId());
        	cs.setString(2,obj.getNom());
        	cs.setDate(3, null);
        	Date dateClotureSql = Date.from(obj.getDateCloture().atStartOfDay(ZoneId.systemDefault()).toInstant());
        	cs.setDate(4,(java.sql.Date) dateClotureSql);
        	cs.setBoolean(5,obj.estEnEquipe());
        	cs.executeUpdate();
		}
        						
        catch (SQLException e)
        {
        	e.printStackTrace();
        	System.out.println("La compétition n'a pas été mise à jour.");
        }
	    return obj;
	}

	public void delete(Competition obj)
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
		
	public static SortedSet<Competition> select(Inscriptions inscriptions) 
	{
		SortedSet<Competition> Competitions = new TreeSet<>();
		try 
		{
			Competition uneCompetition;
			String sql = "{call getCompetitions()}";
			java.sql.Statement cs = accesBase.getInstance().createStatement();
			ResultSet result = cs.executeQuery(sql);
	        while(result.next())
	        {
	            LocalDate dateClotureJava = result.getDate("dateCloture").toLocalDate();
	            uneCompetition = Inscriptions.createCompetition(result.getString("nom_competition"),
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
	
	//TO DO comment charger les candidats inscrits
}