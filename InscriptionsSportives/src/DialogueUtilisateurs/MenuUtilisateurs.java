package DialogueUtilisateurs;

import java.util.List;

import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;
import utilitaires.ligneDeCommande.ActionListe;
import utilitaires.ligneDeCommande.Liste;
import utilitaires.ligneDeCommande.Menu;
import utilitaires.ligneDeCommande.Option;

public class MenuUtilisateurs extends OptionUtilisateurs
{

	public static void main(String[] args)
	{
		Menu menu = getMenuPrincipal(Inscriptions.getInscriptions());
		menu.start();
	}

	static Menu getMenuPrincipal(Inscriptions inscriptions)
	{
		Menu menuPrincipal = new Menu ("Menu Principal");
		menuPrincipal.ajoute(getMenuPersonnes(inscriptions));
		menuPrincipal.ajoute(getMenuEquipe(inscriptions));
		menuPrincipal.ajoute(getMenuCompetition(inscriptions));
		menuPrincipal.ajouteQuitter("q");
		return menuPrincipal;
	}
	
	static Menu getMenuPersonnes(Inscriptions inscriptions)
	{
		Menu menuPersonne = new Menu ("Menu Personne","p");
		menuPersonne.ajoute(getOptionAjoutPersonne(inscriptions));
		menuPersonne.ajoute(getMenuListePersonnes(inscriptions));
		menuPersonne.ajouteRevenir("r");
        menuPersonne.setRetourAuto(true);
		return menuPersonne;
	}
	
	static Menu getMenuListePersonnes(Inscriptions inscriptions)
	{
		Liste<Personne> menu = new Liste<>("Liste des Personnes","l", new ActionListe<Personne>()
		{

			@Override
			public List<Personne> getListe(inscriptions)
			{
				return Personne;
			}

			@Override
			public void elementSelectionne(int indice, Personne element)
			{
				Menu menu = getMenuSelectionPersonnes(Inscriptions.getInscriptions(), element);
				menu.start();
			}
		});
	}
	
	static Menu getMenuSelectionPersonnes(Inscriptions inscriptions, Personne personne)
	{
		Menu menuSelectionPersonnes = new Menu ("Menu Selection Personnes","s");
		menuSelectionPersonnes.ajoute(getOptionModifPersonne(inscriptions));
		menuSelectionPersonnes.ajoute(getOptionSupprPersonne(inscriptions));
		menuSelectionPersonnes.ajoute(getOptionAjoutEquipe(inscriptions));
		menuSelectionPersonnes.ajoute(getOptionSupprEquipe(inscriptions));
		menuSelectionPersonnes.ajouteRevenir("r");
		return menuSelectionPersonnes;
	}
	
	static Menu getMenuEquipe(Inscriptions inscriptions)
	{
		Menu menuEquipe = new Menu ("Menu Equipe","e");
		menuEquipe.ajoute(getOptionAjoutEquipe(inscriptions));
		menuEquipe.ajoute(getMenuListeEquipes(inscriptions));
		menuEquipe.ajouteRevenir("r");
        menuEquipe.setRetourAuto(true);
		return menuEquipe;
	}
	
	static Menu getMenuListeEquipes(Inscriptions inscriptions)
	{
		Liste<Equipe> menu = new Liste<>("Liste des Equipes","l", new ActionListe<Equipe>()
		{

			@Override
			public List<Equipe> getListe(inscriptions)
			{
				return getEquipes();
			}

			@Override
			public void elementSelectionne(int indice, Equipe element)
			{
				Menu menu = getMenuSelectionEquipes(Inscriptions.getInscriptions(), element);
				menu.start();
			}
		});
	}
	
	static Menu getMenuSelectionEquipes(Inscriptions inscriptions, Equipe equipe)
	{
		Menu menuSelectionPersonnes = new Menu ("Menu Selection Equipes","s");
		menuSelectionPersonnes.ajoute(getOptionModifEquipe(inscriptions));
		menuSelectionPersonnes.ajoute(getOptionSupprEquipe(inscriptions));
		menuSelectionPersonnes.ajouteRevenir("r");
		return menuSelectionPersonnes;
	}
	
	static Menu getMenuCompetition(Inscriptions inscriptions)
	{
		Menu menuCompetition = new Menu ("Menu Compétition","c");
		menuCompetition.ajoute(getOptionAjoutCompetition(inscriptions));
		menuCompetition.ajoute(getMenuListeCompetitions(inscriptions));
		menuCompetition.ajouteRevenir("r");
        menuCompetition.setRetourAuto(true);
		return menuCompetition;
	}
	
	static Menu getMenuListeCompetitions(Inscriptions inscriptions)
	{
		Liste<Competition> menu = new Liste<>("Liste des Equipes","l", new ActionListe<Competition>()
		{

			@Override
			public List<Competition> getListe(inscriptions)
			{
				return getCompetitions();
			}

			@Override
			public void elementSelectionne(int indice, Competition element)
			{
				Menu menu = getMenuSelectionCompetitions(Inscriptions.getInscriptions(), element);
				menu.start();
			}

		});
	}
	
	static Menu getMenuSelectionCompetitions(Inscriptions inscriptions, Competition competition)
	{
		Menu menuSelectionCompetitions = new Menu ("Menu Selection Competitions","s");
		menuSelectionCompetitions.ajoute(getOptionModifCompetition(inscriptions));
		menuSelectionCompetitions.ajoute(getOptionSupprCompetition(inscriptions));
		menuSelectionCompetitions.ajouteRevenir("r");
		return menuSelectionCompetitions;
	}
	
}
