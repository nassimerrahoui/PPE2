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
	
	/** Affiche les Caractéristiques d'un candidat @ return */
	public String getCandidatCarac(int pID)
	{	
		try
		{
			// Vérifie si le candidat est une personne ou une equipe
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
	
	/** Affiche la liste des candidats d'une compétition @return */
	public void getCandidatCompetition(int idCompetition)
	{
		try	
		{
			// Vérifie si la compétition est en équipe
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
			
			// Affiche la liste avec les prénoms et mails si ce sont des personnes sinon juste le nom des équipes
			String sql = "{call getCandidatCompetition("+ idCompetition +")}";
			st = connectBase.getInstance().prepareStatement(sql);
			Result = st.executeQuery(sql);
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
	
	// Affiche les Caractéristiques d'une compétition
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
		
	
	/** Affiche les équipes auxquelles une personne appartient @return */
	public void getEquipeOfPersonne(int idPersonne)
	{
		try	
		{
			String sql = "{call getEquipeOfPersonne("+ idPersonne +")}";
			st = connectBase.getInstance().prepareStatement(sql);
			Result = st.executeQuery(sql);
			
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
	
	/** Affiche toutes les équipes @return */
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
			System.out.println("Il n'y a pas d'équipes.");
		}	
	}
	
	/** Affiche les compétitions d'un candidat @return */
	public void getInscriptionCandidat(int idCandidat)
	{
		try	
		{
			// Vérifie si le candidat est une personne ou une equipe
			String query = "SELECT COUNT(id_candidat) as estUnePersonne FROM personne WHERE id_candidat = " + idCandidat;
			st = connectBase.getInstance().prepareStatement(query);
			Result = st.executeQuery(query);
			int estUnePersonne = 0;
			
			while(Result.next())
			{
				estUnePersonne = Result.getInt("estUnePersonne");
			}
			
			if(estUnePersonne == 0) // le candidat est une équipe
			{
				String sql = "{call getInscriptionCandidat("+ idCandidat +")}";
				st = connectBase.getInstance().prepareStatement(sql);
				Result = st.executeQuery(sql);
					
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
				st = connectBase.getInstance().prepareStatement(sql);
				Result = st.executeQuery(sql);
					
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
	
	/** Affiche les personnes d'une équipe @return */
	public void getMembreEquipe(int idEquipe)
	{
		try	
		{
			String sql = "{call getMembreEquipe("+ idEquipe +")}";
			st = connectBase.getInstance().prepareStatement(sql);
			Result = st.executeQuery(sql);
				
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
