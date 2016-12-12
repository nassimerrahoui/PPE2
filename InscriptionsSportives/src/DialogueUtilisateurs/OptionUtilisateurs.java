package DialogueUtilisateurs;

import java.time.LocalDate;

import inscriptions.Candidat;
import inscriptions.Inscriptions;
import utilitaires.ligneDeCommande.Action;
import utilitaires.ligneDeCommande.Option;

public class OptionUtilisateurs
{
	
	static Option getOptionAjoutPersonne(Inscriptions inscriptions)
	{
		Option ajoutPersonne = new Option("Ajouter une Personne","a",getActionAjoutPersonne(inscriptions));
		return ajoutPersonne;
	}
	
	static Option getOptionSupprPersonne(Inscriptions inscriptions)
	{
		Option SupprPersonne = new Option("Supprimer une Personne","s",getActionSupprPersonne(inscriptions));
		return SupprPersonne;
	}
	
	static Option getOptionAjoutEquipe(Inscriptions inscriptions)
	{
		Option ajoutEquipe = new Option("Ajouter une Equipe","a",getActionAjoutEquipe(inscriptions));
		return ajoutEquipe;
	}
	
	static Option getOptionSupprEquipe(Inscriptions inscriptions)
	{
		Option SupprEquipe = new Option("Supprimer une Equipee","s",getActionSupprEquipe(inscriptions));
		return SupprEquipe;
	}
	
	static Option getOptionAjoutCompetition()
	{
		Option ajoutCompetition = new Option("Ajouter une Compétition","a",getActionAjoutCompetition());
		return ajoutCompetition;
	}
	

	static Action getActionAjoutPersonne(final Inscriptions inscriptions)
	{
		return new Action()
				{
					@Override
					public void optionSelectionnee()
					{
						String prenom = utilitaires.EntreesSorties.getString("Saisissez votre Prenom :"),
								nom = utilitaires.EntreesSorties.getString("Saisissez votre Nom : "),
								mail = utilitaires.EntreesSorties.getString("Saisissez votre Mail : ");
						inscriptions.createPersonne(prenom,nom,mail); 
					}
				};
	}
	
	static Action getActionSupprPersonne (final Inscriptions inscriptions)
	{
		return new Action ()
				{
					@Override
					public void optionSelectionnee()
					{
						Personne.remove();
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
	
	static Action getActionSupprEquipe (final Inscriptions inscriptions)
	{
		return new Action ()
				{
					@Override
					public void optionSelectionnee()
					{
						Equipe.remove();
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
								mois = utilitaires.EntreesSorties.getInt("Saisissez un moi :"),
								annee = utilitaires.EntreesSorties.getInt("Saisissez une Année:");
						LocalDate dateCloture ;
						Boolean enEquipe ;
								inscriptions.createCompetition(nom, dateCloture, enEquipe);
					}
				};
	}
	
	

}
