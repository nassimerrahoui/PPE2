package DialogueUtilisateurs;

import java.time.LocalDate;
import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;
import utilitaires.ligneDeCommande.Action;
import utilitaires.ligneDeCommande.Option;

public class OptionUtilisateurs
{
	
	static Option getOptionAjoutPersonne(Inscriptions inscriptions)
	{
		Option ajoutPersonne = new Option("Ajouter une Personne","ap",getActionAjoutPersonne(inscriptions));
		return ajoutPersonne;
	}
	
	static Option getOptionSupprPersonne(Personne inscriptions)
	{
		Option SupprPersonne = new Option("Supprimer une Personne","sp",getActionSupprPersonne(inscriptions));
		return SupprPersonne;
	}
	
	static Option getOptionModifPersonne(Personne inscriptions)
	{
		Option ModifPersonne = new Option("Modifier une Personne","mp",getActionModifPersonne(inscriptions));
		return ModifPersonne;
	}
	
	static Option getOptionAjoutEquipe(Inscriptions inscriptions)
	{
		Option ajoutEquipe = new Option("Ajouter une Equipe","ae",getActionAjoutEquipe(inscriptions));
		return ajoutEquipe;
	}
	
	static Option getOptionSupprEquipe(Equipe inscriptions)
	{
		Option SupprEquipe = new Option("Supprimer une Equipe","se",getActionSupprEquipe(inscriptions));
		return SupprEquipe;
	}
	
	static Option getOptionModifEquipe(Equipe inscriptions)
	{
		Option ModifEquipe = new Option("Modifier une Equipe","me",getActionModifEquipe(inscriptions));
		return ModifEquipe;
	}
	
	static Option getOptionAjoutCompetition(Inscriptions inscriptions)
	{
		Option ajoutCompetition = new Option("Ajouter une Compétition","ac",getActionAjoutCompetition(inscriptions));
		return ajoutCompetition;
	}
	
	static Option getOptionSupprCompetition(Competition inscriptions)
	{
		Option SupprCompetition = new Option("Supprimer une Compétition","sc",getActionSupprCompetition(inscriptions));
		return SupprCompetition;
	}
	
	static Option getOptionModifCompetition(Competition inscriptions)
	{
		Option ModifCompetition = new Option("Modifier une Compétition","mc",getActionModifCompetition(inscriptions));
		return ModifCompetition;
	}

	static Action getActionAjoutPersonne(final Inscriptions inscriptions)
	{
		return new Action()
				{
					@Override
					public void optionSelectionnee()
					{
						String nom = utilitaires.EntreesSorties.getString("Saisissez votre Nom : "),
								prenom = utilitaires.EntreesSorties.getString("Saisissez votre Prenom :"),
								mail = utilitaires.EntreesSorties.getString("Saisissez votre Mail : ");
						inscriptions.createPersonne(nom,prenom,mail);
					}
				};
	}
	
	static Action getActionSupprPersonne (final Personne inscriptions)
	{
		return new Action ()
				{
					@Override
					public void optionSelectionnee()
					{
						inscriptions.delete();
					}
				};
	}
	
	static Action getActionModifPersonne (final Personne inscriptions)
	{
		return new Action ()
				{
					@Override
					public void optionSelectionnee()
					{
						String nouveauNom = utilitaires.EntreesSorties.getString("Saisissez un nouveau Nom: "),
								nouveauPrenom = utilitaires.EntreesSorties.getString("Saisissez un nouveau Prénom: "),
								nouveauMail = utilitaires.EntreesSorties.getString("Saisissez un nouveau Mail : ");
						inscriptions.setNom(nouveauNom);
						inscriptions.setPrenom(nouveauPrenom);
						inscriptions.setMail(nouveauMail);
					}
				};
	}
	
	static Action getActionAjoutEquipe(final Inscriptions inscriptions)
	{
		return new Action()
				{
					@Override
					public void optionSelectionnee()
					{
						String nom = utilitaires.EntreesSorties.getString("Saisissez le nom de L'Equipe:");
								inscriptions.createEquipe(nom);
					}
				};
	}
	
	static Action getActionSupprEquipe (final Equipe inscriptions)
	{
		return new Action ()
				{
					@Override
					public void optionSelectionnee()
					{
						inscriptions.delete();
					}
				};
	}
	
	static Action getActionModifEquipe (final Equipe inscriptions)
	{
		return new Action ()
				{
					@Override
					public void optionSelectionnee()
					{
						String nouveauNom = utilitaires.EntreesSorties.getString("Saisissez un nouveau nom d'équipe ");
						inscriptions.setNom(nouveauNom);
					}
				};
	}
	
	static Action getActionAjoutCompetition(final Inscriptions inscriptions)
	{
		return new Action()
				{
					@Override
					public void optionSelectionnee()
					{
						String nom = utilitaires.EntreesSorties.getString("Saisissez le nom de la Compétition :");
						int jour = utilitaires.EntreesSorties.getInt("Saisissez un jour :"),
								mois = utilitaires.EntreesSorties.getInt("Saisissez un mois :"),
								annee = utilitaires.EntreesSorties.getInt("Saisissez une Année:");
						LocalDate dateCloture = LocalDate.of (annee,mois,jour);
						String equipe = utilitaires.EntreesSorties.getString("est il en equipe: o:oui n:non");
						
						Boolean enEquipe;
						if (equipe == "n") {
								enEquipe = false;
						}
						else if (equipe == "o"){
							enEquipe = true;
						}
						else{
							enEquipe = null;
						}
								inscriptions.createCompetition(nom, dateCloture, enEquipe);
					}
				};
	}
	
	static Action getActionSupprCompetition (final Competition inscriptions)
	{
		return new Action ()
				{
					@Override
					public void optionSelectionnee()
					{
						inscriptions.delete();
					}
				};
	}
	
	static Action getActionModifCompetition (final Competition inscriptions)
	{
		return new Action ()
				{
					@Override
					public void optionSelectionnee()
					{
						String nouveauNom = utilitaires.EntreesSorties.getString("Saisissez un nouveau nom: ");
						int jour = utilitaires.EntreesSorties.getInt("Saisissez un nouveau jour :"),
								mois = utilitaires.EntreesSorties.getInt("Saisissez un nouveau mois :"),
								annee = utilitaires.EntreesSorties.getInt("Saisissez une nouvelle Année:");
						LocalDate nouvelledateCloture = LocalDate.of (annee,mois,jour);
						
						inscriptions.setNom(nouveauNom);
						inscriptions.setDateCloture(nouvelledateCloture);
					}
				};
	}
	

}
