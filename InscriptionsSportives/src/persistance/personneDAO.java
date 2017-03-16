package persistance;

import java.sql.ResultSet;
import java.sql.SQLException;

import metier.Inscriptions;
import metier.Personne;


public class personneDAO extends DAO<Personne>
{

	@Override
	public Personne create(Personne obj)
	{
		try	
		{
			String sql = "{call createPersonne("+ "\" ? \"" + "," + "\" ? \"" + "," + "\" ? \"" +")}";
			java.sql.CallableStatement cs = connectBase.getInstance().prepareCall(sql);
        	cs.setString(1,obj.getNom());
        	cs.setString(2,obj.getPrenom());
        	cs.setString(3,obj.getMail());
			cs.executeUpdate();
			obj = this.find(cs.RETURN_GENERATED_KEYS);
			obj.setId(cs.RETURN_GENERATED_KEYS);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("La personne n'a pas été créée.");
		}
	    return obj;
	}
	
	public Personne find(int id) 
	{
		Personne personne = null;
		try 
		{
			String sql = "{call getCandidatCarac(?)}";
			java.sql.CallableStatement cs = connectBase.getInstance().prepareCall(sql);
			cs.setInt(1,id);
			ResultSet result = cs.executeQuery();
            if(result.first())
            {
            	personne = Inscriptions.createPersonne(result.getString("nom_candidat"), 
            													result.getString("prenom_personne"), 
            													result.getString("mail"));
            	personne.setId(id);
            }    
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Cette personne n'existe pas encore dans l'application");
		}
		return personne;
	}
	
	@Override
	public Personne update(Personne obj)
	{
		try 
		{
			String sql = "{call setCandidatCarac(?," + 
        					"\" ? \"" + "," + 
        					"\" ? \"" + "," + 
        					"\" ? "
        					+ "\"" + ")}";
        	java.sql.CallableStatement cs = this.connect.prepareCall(sql);
        	cs.setLong(1,obj.getId());
        	cs.setString(2,obj.getNom());
        	cs.setString(3,obj.getPrenom());
        	cs.setString(4,obj.getMail());
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
	public void delete(Personne obj)
	{
		try 
		{
			String sql = "{call deleteCandidat( ? )}";
			java.sql.CallableStatement cs = this.connect.prepareCall(sql);
			cs.setLong(1,obj.getId());
			cs.executeUpdate(); 	
	    } 
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Le candidat n'a pas été supprimé.");
	    }
	}	
}