package persistance;

import java.sql.ResultSet;
import java.sql.SQLException;

import metier.Candidat;
import metier.Inscriptions;


public class candidatDAO extends DAO<Candidat>
{

	@Override
	public Candidat create(Candidat obj)
	{
		try	
		{
			String sql = "{call createEquipe("+ "\" ? \"" + ")}";
			java.sql.CallableStatement cs = connectBase.getInstance().prepareCall(sql);
			cs.setString(1, obj.getNom());
			cs.executeUpdate();
			obj.setId(cs.RETURN_GENERATED_KEYS);
			obj = this.find(obj.getId());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("L'équipe n'a pas été créée.");
		}
	    return obj;
	}
	
	@Override
	public Candidat find(int id) 
	{
		Candidat equipe = null;
		try 
		{
			String sql = "{call getCandidatCarac( ? )}";
			java.sql.CallableStatement cs = connectBase.getInstance().prepareCall(sql);
			cs.setInt(1, id);
			ResultSet result = cs.executeQuery();
            if(result.first())
            {
            	equipe = Inscriptions.createEquipe(result.getString("nom_candidat"));
            	equipe.setId(id);
            }
            
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Cette équipe n'existe pas encore dans l'application");
		}
		return equipe;
	}
	
	@Override
	public Candidat update(Candidat obj)
	{
		try 
		{
			String sql = "{call setCandidatCarac( ? , \" ? \" )}";
        	java.sql.CallableStatement cs = this.connect.prepareCall(sql);
        	cs.setInt(1, obj.getId());
        	cs.setString(2, obj.getNom());
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
	public void delete(Candidat obj)
	{
		try 
		{
			String sql = "{call deleteCandidat( ? )}";
			java.sql.CallableStatement cs = this.connect.prepareCall(sql);
			cs.setInt(1,obj.getId());
			cs.executeUpdate(); 	
	    } 
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("L'équipe n'a pas été supprimé.");
	    }
	}	
}