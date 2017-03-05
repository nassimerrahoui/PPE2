package persistance;

import java.sql.ResultSet;
import java.sql.SQLException;

public class lectureBase 
{
	connectBase CO;
	ResultSet Result;
	String message;
	
	// Affiche les Caractéristiques d'un candidat
	public String getCandidatCarac(int pID)
	{	
		CO = new connectBase();
		String message = "";
		
		try
		{
			CO.bddConnexion();
			// Vérifie si le candidat est une personne ou une equipe
			String query = "SELECT COUNT(id_candidat) as estUnePersonne FROM personne WHERE id_candidat = " + pID;
			Result = CO.st.executeQuery(query);
			int estUnePersonne = 0;
			
			while(Result.next())
			{
				estUnePersonne = Result.getInt("estUnePersonne");
			}
						
			String sql = "{call getCandidatCarac("+ pID +")}";
			java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
			Result = cs.executeQuery();
			while (Result.next())
			{
				String nomCandidat = Result.getString("nom_candidat");
				if(estUnePersonne == 1) // le candidat est une équipe
				{
					System.out.println("hahaha");
					String prenomCandidat = Result.getString("prenom_personne");
					String mail = Result.getString("mail");
					message = nomCandidat + "," + prenomCandidat + "," + mail;
				}
				else
				{
					message = nomCandidat;
				}
			}
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
			message = "Le candidat n'existe pas.";
		}
		
		return message;
		
	}
	
	// Affiche la liste des candidats d'une compétition
	public void getCandidatCompetition(int idCompetition)
	{
		CO = new connectBase();
		
		try	
		{
			// Vérifie si la compétition est en équipe
			CO.bddConnexion();
			String query = "SELECT nom_competition, estEnEquipe FROM Competition WHERE id_competition = " + idCompetition;
			Result = CO.st.executeQuery(query);
			int enEquipe = 0;
			String nomCompetition = "";
			
			while(Result.next())
			{
				enEquipe = Result.getInt("estEnEquipe");
				nomCompetition = Result.getString("nom_competition");
			}
			
			// Affiche la liste avec les prénoms et mails si ce sont des personnes sinon juste le nom des équipes
			String sql = "{call getCandidatCompetition("+ idCompetition +")}";
			java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
			Result = cs.executeQuery();
			System.out.println("Candidats inscrit pour la compétition " + nomCompetition + " :");
			while (Result.next()) 
			{
				String nomCandidat = Result.getString("nom_candidat");
			    if(enEquipe == 0) 
			    {
			    	String prenomCandidat = Result.getString("prenom_personne");
				    String mail = Result.getString("mail");
				    System.out.println(nomCandidat + "," + prenomCandidat + "," + mail);
			    }
			    else
			    {
			    	System.out.println(nomCandidat);
			    }
			}
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Il n'y a pas de candidat dans cette compétition.");
		}
		
	}
	
	// Affiche tout les candidats (equipes et personnes)
	public void getCandidats()
	{
		CO = new connectBase();
		
		try	
		{
			CO.bddConnexion();
			String sql = "{call getCandidats()}";
			java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
			Result = cs.executeQuery();
			while (Result.next()) {
		            String nomCandidat = Result.getString("nom_candidat");
		            String prenomCandidat = Result.getString("prenom_personne");
		            String mail = Result.getString("mail");
		            
		            System.out.println(nomCandidat + "," + prenomCandidat + "," + mail);
		        }
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Il n'y a pas de candidat.");
		}
		
	}
	
	// Affiche les Caractéristiques d'une compétition
	public void getCompetitionCarac(int idCompetition)
	{
		CO = new connectBase();
			
		try	
		{
			CO.bddConnexion();
			String sql = "{call getCompetitionCarac("+ idCompetition +")}";
			java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
			Result = cs.executeQuery();
			while (Result.next()) {
				String nomCompetition = Result.getString("nom_competition");
			    String Cloture = Result.getString("dateCloture");
			    String Ouverture = Result.getString("dateOuverture");
			    String enEquipe = Result.getString("result");
			            
			    System.out.println(nomCompetition + ", Ferme le : " + Cloture + ", Ouvre le : " + Ouverture + ", " + enEquipe);
			}
		}
			
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Le candidat n'existe pas.");
		}
			
	}
	
	// Affiche les équipes auxquelles une personne appartient
	public void getEquipeOfPersonne(int idPersonne)
	{
		CO = new connectBase();
			
		try	
		{
			CO.bddConnexion();
			String sql = "{call getEquipeOfPersonne("+ idPersonne +")}";
			java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
			Result = cs.executeQuery();
			
			// afficher la personne et la première équipe à laquelle elle appartient
			while (Result.next())
			{
				String nomPersonne = Result.getString("nomPersonne");
				String prenomPersonne = Result.getString("prenomPersonne");
				System.out.println(nomPersonne + " " + prenomPersonne + " appartient aux équipes suivantes : ");
				String equipe = Result.getString("nom_candidat");          
			    System.out.println(equipe);
				break;
			}
			while (Result.next())
			{
			    String equipe = Result.getString("nom_candidat");          
			    System.out.println(equipe);
			}
		}
			
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Le personne n'existe pas dans l'application.");
		}	
	}
	
	// Affiche toutes les équipes
	public void getEquipes()
	{
		CO = new connectBase();
				
		try	
		{
			CO.bddConnexion();
			String sql = "{call getEquipes()}";
			java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
			Result = cs.executeQuery();
			
			while (Result.next())
			{
				String equipe = Result.getString("nom_candidat");          
			    System.out.println(equipe);
			}
		}
			
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Il n'y a pas d'équipes.");
		}	
	}
	
	// Affiche les compétitions d'un candidat
	public void getInscriptionCandidat(int idCandidat)
	{
		CO = new connectBase();
				
		try	
		{
			CO.bddConnexion();
			
			// Vérifie si le candidat est une personne ou une equipe
			String query = "SELECT COUNT(id_candidat) as estUnePersonne FROM personne WHERE id_candidat = " + idCandidat;
			Result = CO.st.executeQuery(query);
			int estUnePersonne = 0;
			
			while(Result.next())
			{
				estUnePersonne = Result.getInt("estUnePersonne");
			}
			
			if(estUnePersonne == 0) // le candidat est une équipe
			{
				String sql = "{call getInscriptionCandidat("+ idCandidat +")}";
				java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
				Result = cs.executeQuery();
					
				// affiche l'équipe et la première compétition à laquelle elle est inscrite
				while (Result.next())
				{
					String nomCandidat = Result.getString("nom_candidat");
					String nomCompetition = Result.getString("nom_competition");
					String Ouverture = Result.getString("dateOuverture");
					String Cloture = Result.getString("dateCloture");
					System.out.println("L'équipe " + nomCandidat + " est inscrite aux compétitions suivantes : " 
										+ nomCompetition + " Ouvre : " + Ouverture + " Ferme : " + Cloture);
					break;
				}
				
				// affiche les autres compétitions auxquelles l'équipe est inscrite
				while (Result.next())
				{
					String nomCompetition = Result.getString("nom_competition");
					String Ouverture = Result.getString("dateOuverture");
					String Cloture = Result.getString("dateCloture");
					System.out.println(nomCompetition + " Ouvre : " + Ouverture + " Ferme : " + Cloture);
				}
			}
			else
			{
				String sql = "{call getInscriptionCandidat("+ idCandidat +")}";
				java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
				Result = cs.executeQuery();
					
				// affiche la personne et la première compétition à laquelle elle est inscrite
				while (Result.next())
				{
					String nomCandidat = Result.getString("nom_candidat");
					String prenomCandidat = Result.getString("prenom_personne");
					String mailCandidat = Result.getString("mail");
					String nomCompetition = Result.getString("nom_competition");
					String Ouverture = Result.getString("dateOuverture");
					String Cloture = Result.getString("dateCloture");
					System.out.println(nomCandidat + " " + prenomCandidat + " est inscrit aux compétitions suivantes : " 
							+ nomCompetition + " Ouvre : " + Ouverture + " Ferme : " + Cloture);
					System.out.println("Vous pouvez contacter cette personne à cette adresse : " + mailCandidat);
					break;
				}
				
				// affiche les autres compétitions auxquelles la personne est inscrite
				while (Result.next())
				{
					String nomCompetition = Result.getString("nom_competition");
					String Ouverture = Result.getString("dateOuverture");
					String Cloture = Result.getString("dateCloture");
					System.out.println(nomCompetition + " Ouvre : " + Ouverture + " Ferme : " + Cloture);
				}
			}
		}
				
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Le candidat n'existe pas.");
		}	
	}
	
	// Affiche les personnes d'une équipe
	public void getMembreEquipe(int idEquipe)
	{
		CO = new connectBase();
				
		try	
		{
			CO.bddConnexion();
			String sql = "{call getMembreEquipe("+ idEquipe +")}";
			java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
			Result = cs.executeQuery();
				
			// afficher le nom de l'équipe et la première personne dans l'équipe
			while (Result.next())
			{
				String nomEquipe = Result.getString("nameTeam");
				String nomPersonne = Result.getString("nom_candidat");
				String prenomPersonne = Result.getString("prenom_personne");
				String mailPersonne = Result.getString("mail");
				System.out.println("L'équipe " + nomEquipe + " est composé de : " 
									+ nomPersonne + prenomPersonne + mailPersonne);
				break;
			}
				
			// affiche le reste de la composition de l'équipe
			while (Result.next())
			{
				String nomPersonne = Result.getString("nom_candidat");
				String prenomPersonne = Result.getString("prenom_personne");
				String mailPersonne = Result.getString("mail");    
				System.out.println(nomPersonne + prenomPersonne + mailPersonne);
			}
		}
				
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("L'équipe n'existe pas.");
		}	
	}
		
	// Affiche toutes les personnes
	public void getPersonnes()
	{
		CO = new connectBase();
					
		try	
		{
			CO.bddConnexion();
			String sql = "{call getPersonnes()}";
			java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
			Result = cs.executeQuery();
				
			while (Result.next())
			{
				String nomPersonne = Result.getString("nom_candidat");
				String prenomPersonne = Result.getString("prenom_personne");  
				String mail = Result.getString("mail");  
				System.out.println(nomPersonne + prenomPersonne + mail);
			}
		}
				
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Il n'y a pas de personnes dans l'application.");
		}	
	}
	
}
