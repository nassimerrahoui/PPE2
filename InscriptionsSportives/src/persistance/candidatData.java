package persistance;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedSet;
import java.util.TreeSet;

import metier.Candidat;
import metier.Inscriptions;

public class candidatData
{
	public static SortedSet<Candidat> select(Inscriptions inscriptions) 
	{
		SortedSet<Candidat> Candidats = new TreeSet<>();

		try 
		{
			Candidat unCandidat;
			boolean flag = false;
			String sql = "{call getCandidats()}";
			java.sql.Statement cs = accesBase.getInstance().createStatement();
			ResultSet result = cs.executeQuery(sql);
			
			// boucle pour ajouter une personne ou une equipe dans la couche métier
            while(result.next())
            {
            	String sql2 = "{call getPersonnes()}";
    			java.sql.Statement cs2 = accesBase.getInstance().createStatement();
    			ResultSet result2 = cs2.executeQuery(sql2);
    			
    			while(result2.next())
    			{
    				flag = false;
    				if(result2.getInt("id_candidat") == result.getInt("id_candidat"))
    				{	
    					unCandidat = Inscriptions.createPersonne(result.getString("nom_candidat"),
    												result.getString("prenom_personne"),
    												result.getString("mail"));
    					Candidats.add(unCandidat);
    					flag = true;
    					break;
    				}
    			}
    			
    			if(flag == false)
				{
					unCandidat = Inscriptions.createEquipe(result.getString("nom_candidat"));
    				Candidats.add(unCandidat);
				}
            }    
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Il n'y a pas de candidats");
		}
		return Candidats;
	}
}
