package persistance;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class ecritureBase 
{
	connectBase CO;
	int Result;
	String message;
	
	// Ajoute un membre dans une �quipe
	public String addMembre(int idPersonne, int idEquipe)
	{
		CO = new connectBase();
		
		try	
		{
			CO.bddConnexion();
			String sql = "{call addMembreEquipe("+ idPersonne + "," + idEquipe +")}";
			java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
		    cs.executeUpdate();
			message = Result + " Membre ajout�";
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
			message = "Erreur d'ajout de membre";
		}
		
		return message;
		
	}
	
	// Cr�er une comp�tition
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
			message = "La comp�tition n'a pas �t� cr��e.";
		}
		
		return message;
		
	}
	
	// Cr�er une �quipe
	public String createEquipe(String pCandidat)
	{
		CO = new connectBase();
		
		try	
		{
			CO.bddConnexion();
			String sql = "{call createEquipe("+ "\"" + pCandidat + "\"" + ")}";
			java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
			Result = cs.executeUpdate(); 
			message = Result + " equipe(s) cr��e(s)";
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
			message = "L'�quipe n'a pas �t� cr��e.";
		}
		
		return message;
		
	}
	
	// Cr�er une personne
	public String createPersonne(String pCandidat, String pPrenom, String pMail)
	{
		CO = new connectBase();
		
		try	
		{
			CO.bddConnexion();
			String sql = "{call createPersonne("+ "\"" + pCandidat + "\"" + "," + "\"" + pPrenom + "\"" + "," + "\"" + pMail + "\"" +")}";
			java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
			Result = cs.executeUpdate(); 
			message = Result + " personne(s) cr��e(s)";
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
			message = "La personne n'a pas �t� cr��e.";
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
			message = Result + " candidat(s) supprim�(s)";
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
			message = "Le candidat n'a pas �t� supprim�.";
		}
		
		return message;
		
	}
	
	// Effacer une comp�tition
	public String deleteCompetition(int pID)
	{
		CO = new connectBase();
		
		try	
		{
			CO.bddConnexion();
			String sql = "{call deleteCompetition("+ pID +")}";
			java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
			Result = cs.executeUpdate(); 
			message = Result + " comp�tition(s) supprim�(s)";
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
			message = "La comp�tition n'a pas �t� supprim�.";
		}
		
		return message;
		
	}
	
	// Inscrit un candidat � une comp�tition
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
			message = "Le candidat et/ou la comp�tition n'existe pas";
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
			message = Result + " candidat d�sinscrit";
		}
							
		catch (SQLException e)
		{
			e.printStackTrace();
			message = "Le candidat et/ou la comp�tition n'existe pas";
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
			message = Result + " personne a �t� enlev� de l'�quipe";
		}
							
		catch (SQLException e)
		{
			e.printStackTrace();
			message = "L'�quipe et/ou la personne n'existe pas";
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
			message = Result + " candidat a �t� modifi�";
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
			message = Result + " comp�tition a �t� modifi�";
		}
							
		catch (SQLException e)
		{
			e.printStackTrace();
			message = "La comp�tition n'existe pas";
		}	
		
		return message;
	}
}
