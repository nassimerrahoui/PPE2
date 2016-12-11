package DialogueUtilisateurs;

import java.time.LocalDate;

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
	
//	static Option getOptionSupprPersonne()
//	{
//		
//	}
	
	static Option getOptionAjoutEquipe(Inscriptions inscriptions)
	{
		Option ajoutEquipe = new Option("Ajouter une Equipe","a",getActionAjoutEquipe(inscriptions));
		return ajoutEquipe;
	}
	
	static Option getOptionAjoutCompetition()
	{
		Option ajoutCompetition = new Option("Ajouter une Comp�tition","a",getActionAjoutCompetition());
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
	
//	static Action getActionSupprPersonne ()
//	{
//		return new Action ()
//				{
//					@Override
//					public void optionSelectionnee()
//					{
//						
//					}
//				};
//	}
	
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
	
	static Action getActionAjoutCompetition(final Inscriptions inscriptions)
	{
		return new Action()
				{
					@Override
					public void optionSelectionnee()
					{
						String nom = utilitaires.EntreesSorties.getString("Saisissez le nom de la Comp�tition :");
						int jour = utilitaires.EntreesSorties.getInt("Saisissez un jour :"),
								mois = utilitaires.EntreesSorties.getInt("Saisissez un moi :"),
								annee = utilitaires.EntreesSorties.getInt("Saisissez une Ann�e:");
						LocalDate dateCloture = utilitaires.EntreesSorties.getInt("La date de cloture de la comp�tition est le :"+jour+"/"+mois+"/"+annee+"");
						Boolean enEquipe = utilitaires.EntreesSorties.getInt("La comp�tition ce d�roule en Equipe :");
								inscriptions.createCompetition(nom, dateCloture, enEquipe);

					}
				};
	}

}
