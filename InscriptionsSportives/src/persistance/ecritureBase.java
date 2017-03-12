
package persistance;

import java.sql.SQLException;

public class ecritureBase 
{
	int Result;
	String message;
	
	/** Ajoute un membre dans une équipe @return */
	public String addMembre(int idPersonne, int idEquipe)
	{
		
		try	
		{
			String sql = "{call addMembreEquipe("+ idPersonne + "," + idEquipe +")}";
			java.sql.CallableStatement cs = connectBase.getInstance().prepareCall(sql); 
		    cs.executeUpdate();
			message = Result + " Membre ajouté";
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
			message = "Erreur d'ajout de membre";
		}
		
		return message;
		
	}
	
	/** Créer une compétition @return */
	public String createCompetition(String nomCompetition, String Cloture, String Ouverture, int EnEquipe)
	{	
		try	
		{
			String sql = "{call createCompetition(" +
					"\"" + nomCompetition + "\"" + "," +
					"\"" + Cloture +  "\"" + "," + 
					 "\"" + Ouverture +  "\""  + "," + EnEquipe + ")}";
			java.sql.CallableStatement cs = connectBase.getInstance().prepareCall(sql); 
			cs.executeUpdate();
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
			message = "La compétition n'a pas été créée.";
		}
		
		return message;
		
	}
	
	/** Créer une équipe @return */
	public String createEquipe(String pCandidat)
	{	
		try	
		{
			String sql = "{call createEquipe("+ "\"" + pCandidat + "\"" + ")}";
			java.sql.CallableStatement cs = connectBase.getInstance().prepareCall(sql); 
			Result = cs.executeUpdate(); 
			message = Result + " equipe(s) créée(s)";
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
			message = "L'équipe n'a pas été créée.";
		}
		
		return message;
		
	}
	
	/** Créer une personne @return */
	public void createPersonne(String pCandidat, String pPrenom, String pMail)
	{
		try	
		{
			String sql = "{call createPersonne("+ "\"" + pCandidat + "\"" + "," + "\"" + pPrenom + "\"" + "," + "\"" + pMail + "\"" +")}";
			java.sql.CallableStatement cs = connectBase.getInstance().prepareCall(sql); 
			cs.executeUpdate();
			System.out.println("OK !");
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("La personne n'a pas été créée.");
		}
		
	}
	
	/** Effacer une candidat @return */
	public String deleteCandidat(int idCandidat)
	{
		try	
		{
			String sql = "{call deleteCandidat("+idCandidat+")}";
			java.sql.CallableStatement cs = connectBase.getInstance().prepareCall(sql); 
			Result = cs.executeUpdate(); 
			message = Result + " candidat(s) supprimé(s)";
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
			message = "Le candidat n'a pas été supprimé.";
		}
		
		return message;
		
	}
	
	/** Effacer une compétition @return */
	public String deleteCompetition(int pID)
	{
		try	
		{
			String sql = "{call deleteCompetition("+ pID +")}";
			java.sql.CallableStatement cs = connectBase.getInstance().prepareCall(sql); 
			Result = cs.executeUpdate(); 
			message = Result + " compétition(s) supprimé(s)";
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
			message = "La compétition n'a pas été supprimé.";
		}
		
		return message;
		
	}
	
	/** Inscrit un candidat à une compétition @return */
	public String inscriptionCandidat(int idCandidat, int idCompetition)
	{						
		try	
		{
			String sql = "{call inscriptionCandidat("+ idCandidat + "," + idCompetition +")}";
			java.sql.CallableStatement cs = connectBase.getInstance().prepareCall(sql); 
			Result = cs.executeUpdate(); 
			message = Result + " candidat est inscrit";
		}
							
		catch (SQLException e)
		{
			e.printStackTrace();
			message = "Le candidat et/ou la compétition n'existe pas";
		}	
		
		return message;
	}
	
	/** Supprime un candidat d'une compétition @return */
	public String removeCandidatCompetition(int idCandidat, int idCompetition)
	{
		try	
		{
			String sql = "{call removeCandidatCompetition("+ idCompetition + "," + idCandidat +")}";
			java.sql.CallableStatement cs = connectBase.getInstance().prepareCall(sql); 
			Result = cs.executeUpdate(); 
			message = Result + " candidat désinscrit";
		}
							
		catch (SQLException e)
		{
			e.printStackTrace();
			message = "Le candidat et/ou la compétition n'existe pas";
		}	
		
		return message;
	}
	
	/** Supprime un candidat d'une équipe @return */
	public String removePersonneEquipe(int idEquipe, int idPersonne)
	{
		try	
		{
			String sql = "{call removePersonneEquipe("+ idEquipe + "," + idPersonne +")}";
			java.sql.CallableStatement cs = connectBase.getInstance().prepareCall(sql); 
			Result = cs.executeUpdate(); 
			message = Result + " personne a été enlevé de l'équipe";
		}
							
		catch (SQLException e)
		{
			e.printStackTrace();
			message = "L'équipe et/ou la personne n'existe pas";
		}	
		
		return message;
	}
	
	/** Modifier un candidat @return */
	public String setCandidatCarac(int pID, String nomCandidat, String prenomPersonne, String email)
	{
		try	
		{
			String sql = "{call setCandidatCarac("+ pID + "," + 
					"\"" + nomCandidat + "\"" + "," + 
					"\"" + prenomPersonne + "\"" + "," + 
					"\"" + email + "\"" + ")}";
			java.sql.CallableStatement cs = connectBase.getInstance().prepareCall(sql); 
			Result = cs.executeUpdate(); 
			message = Result + " candidat a été modifié";
		}
							
		catch (SQLException e)
		{
			e.printStackTrace();
			message = "Le candidat n'existe pas";
		}	
		
		return message;
	}
	
	/** Modifier une compétition @return */
	public String setCompetitionCarac(String Competition, String Ouverture, String Cloture, int EnEquipe, int pID)
	{
		try	
		{
			String sql = "{call setCompetitionCarac(" +
					"\"" + Competition + "\"" + "," +
					"\"" + Cloture +  "\"" + "," + 
					 "\"" + Ouverture +  "\""  + "," + EnEquipe + ")}";
			java.sql.CallableStatement cs = connectBase.getInstance().prepareCall(sql); 
			Result = cs.executeUpdate(); 
			message = Result + " compétition a été modifié";
		}
							
		catch (SQLException e)
		{
			e.printStackTrace();
			message = "La compétition n'existe pas";
		}	
		
		return message;
	}
}