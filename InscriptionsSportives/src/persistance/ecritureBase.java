package persistance;

import java.sql.SQLException;
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
	public String createCompetition(String nomCompetition, LocalDate Cloture, LocalDate Ouverture, int EnEquipe)
	{
		CO = new connectBase();
		
		try	
		{
			CO.bddConnexion();
			String sql = "{call createCompetition(" + nomCompetition + "," + Cloture + "," + Ouverture + "," + EnEquipe + ")}";
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
			java.sql.CallableStatement cs = CO.cn.prepareCall("{call createEquipe("+ pCandidat + ")}"); 
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
			java.sql.CallableStatement cs = CO.cn.prepareCall("{call createPersonne("+ pCandidat + "," + pPrenom + "," + pMail +")}"); 
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
			java.sql.CallableStatement cs = CO.cn.prepareCall("{call deleteCandidat("+idCandidat+")}"); 
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
			java.sql.CallableStatement cs = CO.cn.prepareCall("{call deleteCompetition("+ pID +")}"); 
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
			java.sql.CallableStatement cs = CO.cn.prepareCall("{call inscriptionCandidat("+ idCandidat + "," + idCompetition +")}"); 
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
	
}
