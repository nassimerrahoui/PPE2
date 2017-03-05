package persistance;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class ecritureBase 
{
	connectBase CO;
	int Result;
	String message;
	
	// Ajoute un membre dans une équipe
	public String addMembre(int idPersonne, int idEquipe)
	{
		CO = new connectBase();
		
		try	
		{
			CO.bddConnexion();
			String sql = "{call addMembreEquipe("+ idPersonne + "," + idEquipe +")}";
			java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
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
	
	// Créer une compétition
	public String createCompetition(String nomCompetition, String Cloture, String Ouverture, int EnEquipe)
	{
		CO = new connectBase();
		
		try	
		{
			CO.bddConnexion();
			String sql = "{call createCompetition(" +
					"\"" + nomCompetition + "\"" + "," +
					"\"" + Cloture +  "\"" + "," + 
					 "\"" + Ouverture +  "\""  + "," + EnEquipe + ")}";
			java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
			cs.executeUpdate();
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
			message = "La compétition n'a pas été créée.";
		}
		
		return message;
		
	}
	
	// Créer une équipe
	public String createEquipe(String pCandidat)
	{
		CO = new connectBase();
		
		try	
		{
			CO.bddConnexion();
			String sql = "{call createEquipe("+ "\"" + pCandidat + "\"" + ")}";
			java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
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
	
	// Créer une personne
	public String createPersonne(String pCandidat, String pPrenom, String pMail)
	{
		CO = new connectBase();
		
		try	
		{
			CO.bddConnexion();
			String sql = "{call createPersonne("+ "\"" + pCandidat + "\"" + "," + "\"" + pPrenom + "\"" + "," + "\"" + pMail + "\"" +")}";
			java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
			Result = cs.executeUpdate(); 
			message = Result + " personne(s) créée(s)";
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
			message = "La personne n'a pas été créée.";
		}
		
		return message;
		
	}
	
	// Effacer une candidat
	public String deleteCandidat(int idCandidat)
	{
		CO = new connectBase();
		
		try	
		{
			CO.bddConnexion();
			String sql = "{call deleteCandidat("+idCandidat+")}";
			java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
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
	
	// Effacer une compétition
	public String deleteCompetition(int pID)
	{
		CO = new connectBase();
		
		try	
		{
			CO.bddConnexion();
			String sql = "{call deleteCompetition("+ pID +")}";
			java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
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
	
	// Inscrit un candidat à une compétition
	public String inscriptionCandidat(int idCandidat, int idCompetition)
	{
		CO = new connectBase();
							
		try	
		{
			CO.bddConnexion();
			String sql = "{call inscriptionCandidat("+ idCandidat + "," + idCompetition +")}";
			java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
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
	
	public String removeCandidatCompetition(int idCandidat, int idCompetition)
	{
		CO = new connectBase();
							
		try	
		{
			CO.bddConnexion();
			String sql = "{call removeCandidatCompetition("+ idCompetition + "," + idCandidat +")}";
			java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
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
	
	public String removePersonneEquipe(int idEquipe, int idPersonne)
	{
		CO = new connectBase();
							
		try	
		{
			CO.bddConnexion();
			String sql = "{call removePersonneEquipe("+ idEquipe + "," + idPersonne +")}";
			java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
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
	
	public String setCandidatCarac(int pID, String nomCandidat, String prenomPersonne, String email)
	{
		CO = new connectBase();
							
		try	
		{
			CO.bddConnexion();
			String sql = "{call setCandidatCarac("+ pID + "," + 
					"\"" + nomCandidat + "\"" + "," + 
					"\"" + prenomPersonne + "\"" + "," + 
					"\"" + email + "\"" + ")}";
			java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
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
	
	public String setCompetitionCarac(String Competition, String Ouverture, String Cloture, int EnEquipe, int pID)
	{
		CO = new connectBase();
							
		try	
		{
			CO.bddConnexion();
			String sql = "{call setCompetitionCarac(" +
					"\"" + Competition + "\"" + "," +
					"\"" + Cloture +  "\"" + "," + 
					 "\"" + Ouverture +  "\""  + "," + EnEquipe + ")}";
			java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
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
