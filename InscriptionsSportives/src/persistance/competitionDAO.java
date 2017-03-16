package persistance;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.Statement;

import metier.Competition;
import metier.Inscriptions;


public class competitionDAO extends DAO<Competition>
{

	@Override
	public Competition create(Competition obj)
	{
		try	
		{
			String sql = "{call createCompetition( ? , ? , ? , ?)}";
			java.sql.CallableStatement cs = connectBase.getInstance().prepareCall(sql);
        	cs.setString(1,obj.getNom());
        	
        	Date dateClotureJava = Date.from(obj.getDateCloture().atStartOfDay(ZoneId.systemDefault()).toInstant());
        	java.sql.Date dateClotureSql = new java.sql.Date(dateClotureJava.getTime());
        	cs.setDate(2,dateClotureSql);
        	
        	Date dateOuvertureJava = new Date();
        	java.sql.Date dateOuvertureSql = new java.sql.Date(dateOuvertureJava.getTime());
        	cs.setDate(3, dateOuvertureSql);
        	
        	cs.setBoolean(4,obj.estEnEquipe());
			cs.executeUpdate();
			obj = this.find(cs.RETURN_GENERATED_KEYS);
			obj.setId(cs.RETURN_GENERATED_KEYS);
			
			System.out.println(obj.getId());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("La compétition n'a pas été créée.");
		}
	    return obj;
	}
	
	public Competition find(int id) 
	{
		Competition competition = null;
		try 
		{
			String sql = "{call getCompetitionCarac(?)}";
			java.sql.CallableStatement cs = connectBase.getInstance().prepareCall(sql);
			cs.setInt(1,id);
			ResultSet result = cs.executeQuery();
            if(result.first())
            {
            	LocalDate dateClotureJava = result.getDate("dateCloture").toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            	competition = Inscriptions.createCompetition(result.getString("nom_competition"),
            												dateClotureJava,
            												result.getBoolean("estEnEquipe"));
            	competition.setId(id);
            }    
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Cette compétition n'existe pas encore dans l'application");
		}
		return competition;
	}
	
	@Override
	public Competition update(Competition obj)
	{
		try 
		{
			String sql = "{call setCompetitionCarac(? )}";
        	java.sql.CallableStatement cs = this.connect.prepareCall(sql);
        	cs.setLong(1,obj.getId());
        	cs.setString(2,obj.getNom());
        	cs.setDate(3, null);
        	Date dateClotureSql = Date.from(obj.getDateCloture().atStartOfDay(ZoneId.systemDefault()).toInstant());
        	cs.setDate(4,(java.sql.Date) dateClotureSql);
        	cs.setBoolean(5,obj.estEnEquipe());
        	cs.executeUpdate();
        	obj = this.find(obj.getId());
		}
        						
        catch (SQLException e)
        {
        	e.printStackTrace();
        }
	    return obj;
	}

	@Override
	public void delete(Competition obj)
	{
		try 
		{
			String sql = "{call deleteCompetition( ? )}";
			java.sql.CallableStatement cs = this.connect.prepareCall(sql);
			cs.setLong(1,obj.getId());
			cs.executeUpdate(); 	
	    } 
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("La compétition n'a pas été supprimé.");
	    }
	}	
}