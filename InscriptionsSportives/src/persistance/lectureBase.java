package persistance;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public class lectureBase 
{
	public ResultSet Result;
	public String message;
	public static SortedSet<String> candidatsBdd = new TreeSet<>();
	public static SortedSet<Collections> competitionsBdd = new TreeSet<>();
	public PreparedStatement st;
	
	/** Affiche les Caract�ristiques d'un candidat @ return */
	public String getCandidatCarac(int pID)
	{	
		try
		{
			// V�rifie si le candidat est une personne ou une equipe
			String query = "SELECT COUNT(id_candidat) as estUnePersonne FROM personne WHERE id_candidat = " + pID;
			st = connectBase.getInstance().prepareStatement(query);
			Result = st.executeQuery(query);
			int estUnePersonne = 0;
			
			while(Result.next())
			{
				estUnePersonne = Result.getInt("estUnePersonne");
			}
						
			String sql = "{call getCandidatCarac("+ pID +")}";
			st = connectBase.getInstance().prepareStatement(sql);
			Result = st.executeQuery(sql);
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
	
	/** Affiche la liste des candidats d'une comp�tition @return */
	public void getCandidatCompetition(int idCompetition)
	{
		try	
		{
			// V�rifie si la comp�tition est en �quipe
			String query = "SELECT nom_competition, estEnEquipe FROM Competition WHERE id_competition = " + idCompetition;
			st = connectBase.getInstance().prepareStatement(query);
			Result = st.executeQuery(query);
			int enEquipe = 0;
			String nomCompetition = "";
			
			while(Result.next())
			{
				enEquipe = Result.getInt("estEnEquipe");
				nomCompetition = Result.getString("nom_competition");
			}
			
			// Affiche la liste avec les pr�noms et mails si ce sont des personnes sinon juste le nom des �quipes
			String sql = "{call getCandidatCompetition("+ idCompetition +")}";
			st = connectBase.getInstance().prepareStatement(sql);
			Result = st.executeQuery(sql);
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
	
	/** Affiche tout les candidats (equipes et personnes) @return */
	public SortedSet<String> getCandidats()
	{
		try	
		{
			String sql = "{call getCandidats()}";
			st = connectBase.getInstance().prepareStatement(sql);
			Result = st.executeQuery(sql);
			while (Result.next()) {
					String nomCandidatBdd = Result.getString("nom_candidat");
					candidatsBdd.add(nomCandidatBdd);
		        }
		}
		
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Il n'y a pas de candidat.");
		}
		return candidatsBdd;
		
	}
	
	// Affiche les Caract�ristiques d'une comp�tition
	public void getCompetitionCarac(int idCompetition)
	{
		try	
		{
			String sql = "{call getCompetitionCarac("+ idCompetition +")}";
			st = connectBase.getInstance().prepareStatement(sql);
			Result = st.executeQuery(sql);
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
	
	/** Affiche tout les candidats (equipes et personnes) @retrun */
	public SortedSet<Collections> getCompetitions()
	{
		try	
		{
			String sql = "{call getCompetitions()}";
			st = connectBase.getInstance().prepareStatement(sql);
			Result = st.executeQuery(sql);
			while (Result.next()) {
				//int idCompetitionBdd = Result.getInt("id_competition");
				//String nomCompetitionBdd = Result.getString("nom_competition");
				//Date dateClotureBdd = Result.getDate("dateCloture");
				//Date dateOuvertureBdd = Result.getDate("dateOuverture");
				//boolean estEnEquipe = Result.getBoolean("estEnEquipe");
					
				/*competitionsBdd.add(inscriptions + ", " + nomCompetitionBdd + ", " +
						dateClotureBdd + ", " + dateOuvertureBdd + ", " + estEnEquipe);*/
			}
		}
			
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Il n'y a pas de candidat.");
		}
		return competitionsBdd;
			
	}
		
	
	/** Affiche les �quipes auxquelles une personne appartient @return */
	public void getEquipeOfPersonne(int idPersonne)
	{
		try	
		{
			String sql = "{call getEquipeOfPersonne("+ idPersonne +")}";
			st = connectBase.getInstance().prepareStatement(sql);
			Result = st.executeQuery(sql);
			
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
	
	/** Affiche toutes les �quipes @return */
	public void getEquipes()
	{
		try	
		{
			String sql = "{call getEquipes()}";
			st = connectBase.getInstance().prepareStatement(sql);
			Result = st.executeQuery(sql);
			
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
	
	/** Affiche les comp�titions d'un candidat @return */
	public void getInscriptionCandidat(int idCandidat)
	{
		try	
		{
			// V�rifie si le candidat est une personne ou une equipe
			String query = "SELECT COUNT(id_candidat) as estUnePersonne FROM personne WHERE id_candidat = " + idCandidat;
			st = connectBase.getInstance().prepareStatement(query);
			Result = st.executeQuery(query);
			int estUnePersonne = 0;
			
			while(Result.next())
			{
				estUnePersonne = Result.getInt("estUnePersonne");
			}
			
			if(estUnePersonne == 0) // le candidat est une �quipe
			{
				String sql = "{call getInscriptionCandidat("+ idCandidat +")}";
				st = connectBase.getInstance().prepareStatement(sql);
				Result = st.executeQuery(sql);
					
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
				st = connectBase.getInstance().prepareStatement(sql);
				Result = st.executeQuery(sql);
					
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
	
	/** Affiche les personnes d'une �quipe @return */
	public void getMembreEquipe(int idEquipe)
	{
		try	
		{
			String sql = "{call getMembreEquipe("+ idEquipe +")}";
			st = connectBase.getInstance().prepareStatement(sql);
			Result = st.executeQuery(sql);
				
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
		
	/** Affiche toutes les personnes @return */
	public void getPersonnes()
	{				
		try	
		{
			String sql = "{call getPersonnes()}";
			st = connectBase.getInstance().prepareStatement(sql);
			Result = st.executeQuery(sql);
				
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
