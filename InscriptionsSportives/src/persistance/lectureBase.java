package persistance;

import java.sql.ResultSet;
import java.sql.SQLException;

public class lectureBase 
{
	connectBase CO;
	ResultSet Result;
	String message;
	
	// Affiche les Caract�ristiques d'un candidat
	public String getCandidatCarac(int pID)
	{	
		CO = new connectBase();
		String message = "";
		
		try
		{
			CO.bddConnexion();
			// V�rifie si le candidat est une personne ou une equipe
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
				if(estUnePersonne == 1) // le candidat est une �quipe
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
	
	// Affiche la liste des candidats d'une comp�tition
	public void getCandidatCompetition(int idCompetition)
	{
		CO = new connectBase();
		
		try	
		{
			// V�rifie si la comp�tition est en �quipe
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
			
			// Affiche la liste avec les pr�noms et mails si ce sont des personnes sinon juste le nom des �quipes
			String sql = "{call getCandidatCompetition("+ idCompetition +")}";
			java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
			Result = cs.executeQuery();
			System.out.println("Candidats inscrit pour la comp�tition " + nomCompetition + " :");
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
			System.out.println("Il n'y a pas de candidat dans cette comp�tition.");
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
	
	// Affiche les Caract�ristiques d'une comp�tition
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
	
	// Affiche les �quipes auxquelles une personne appartient
	public void getEquipeOfPersonne(int idPersonne)
	{
		CO = new connectBase();
			
		try	
		{
			CO.bddConnexion();
			String sql = "{call getEquipeOfPersonne("+ idPersonne +")}";
			java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
			Result = cs.executeQuery();
			
			// afficher la personne et la premi�re �quipe � laquelle elle appartient
			while (Result.next())
			{
				String nomPersonne = Result.getString("nomPersonne");
				String prenomPersonne = Result.getString("prenomPersonne");
				System.out.println(nomPersonne + " " + prenomPersonne + " appartient aux �quipes suivantes : ");
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
	
	// Affiche toutes les �quipes
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
			System.out.println("Il n'y a pas d'�quipes.");
		}	
	}
	
	// Affiche les comp�titions d'un candidat
	public void getInscriptionCandidat(int idCandidat)
	{
		CO = new connectBase();
				
		try	
		{
			CO.bddConnexion();
			
			// V�rifie si le candidat est une personne ou une equipe
			String query = "SELECT COUNT(id_candidat) as estUnePersonne FROM personne WHERE id_candidat = " + idCandidat;
			Result = CO.st.executeQuery(query);
			int estUnePersonne = 0;
			
			while(Result.next())
			{
				estUnePersonne = Result.getInt("estUnePersonne");
			}
			
			if(estUnePersonne == 0) // le candidat est une �quipe
			{
				String sql = "{call getInscriptionCandidat("+ idCandidat +")}";
				java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
				Result = cs.executeQuery();
					
				// affiche l'�quipe et la premi�re comp�tition � laquelle elle est inscrite
				while (Result.next())
				{
					String nomCandidat = Result.getString("nom_candidat");
					String nomCompetition = Result.getString("nom_competition");
					String Ouverture = Result.getString("dateOuverture");
					String Cloture = Result.getString("dateCloture");
					System.out.println("L'�quipe " + nomCandidat + " est inscrite aux comp�titions suivantes : " 
										+ nomCompetition + " Ouvre : " + Ouverture + " Ferme : " + Cloture);
					break;
				}
				
				// affiche les autres comp�titions auxquelles l'�quipe est inscrite
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
					
				// affiche la personne et la premi�re comp�tition � laquelle elle est inscrite
				while (Result.next())
				{
					String nomCandidat = Result.getString("nom_candidat");
					String prenomCandidat = Result.getString("prenom_personne");
					String mailCandidat = Result.getString("mail");
					String nomCompetition = Result.getString("nom_competition");
					String Ouverture = Result.getString("dateOuverture");
					String Cloture = Result.getString("dateCloture");
					System.out.println(nomCandidat + " " + prenomCandidat + " est inscrit aux comp�titions suivantes : " 
							+ nomCompetition + " Ouvre : " + Ouverture + " Ferme : " + Cloture);
					System.out.println("Vous pouvez contacter cette personne � cette adresse : " + mailCandidat);
					break;
				}
				
				// affiche les autres comp�titions auxquelles la personne est inscrite
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
	
	// Affiche les personnes d'une �quipe
	public void getMembreEquipe(int idEquipe)
	{
		CO = new connectBase();
				
		try	
		{
			CO.bddConnexion();
			String sql = "{call getMembreEquipe("+ idEquipe +")}";
			java.sql.CallableStatement cs = CO.cn.prepareCall(sql); 
			Result = cs.executeQuery();
				
			// afficher le nom de l'�quipe et la premi�re personne dans l'�quipe
			while (Result.next())
			{
				String nomEquipe = Result.getString("nameTeam");
				String nomPersonne = Result.getString("nom_candidat");
				String prenomPersonne = Result.getString("prenom_personne");
				String mailPersonne = Result.getString("mail");
				System.out.println("L'�quipe " + nomEquipe + " est compos� de : " 
									+ nomPersonne + prenomPersonne + mailPersonne);
				break;
			}
				
			// affiche le reste de la composition de l'�quipe
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
			System.out.println("L'�quipe n'existe pas.");
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
