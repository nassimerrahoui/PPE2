package DialogueUtilisateurs;

import java.util.ArrayList;
import java.util.List;

import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;
import utilitaires.ligneDeCommande.ActionListe;
import utilitaires.ligneDeCommande.Liste;
import utilitaires.ligneDeCommande.Menu;

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
			public List<Personne> getListe()
			{
				return new ArrayList<>(inscriptions.getPersonnes());
			}

			@Override
			public void elementSelectionne(int indice, Personne element)
			{
				Menu menu = getMenuSelectionPersonnes(Inscriptions.getInscriptions(), element);
				System.out.println("Vous avez sélectionné "+ element+ ", qui a l'indice " + indice);
				menu.start();
			}
		});
		menu.ajouteRevenir("r");
		return menu;
	}
	
	static Menu getMenuSelectionPersonnes(Inscriptions inscriptions, Personne personne)
	{
		Menu menuSelectionPersonnes = new Menu ("Menu Selection Personnes","s");
		menuSelectionPersonnes.ajoute(getOptionModifPersonne(personne));
		menuSelectionPersonnes.ajoute(getOptionSupprPersonne(personne));
//		menuSelectionPersonnes.ajoute(getMenuGestionEquipes());
		menuSelectionPersonnes.ajouteRevenir("r");
		return menuSelectionPersonnes;
	}
	
	static Menu getMenuGestionPersonnes(Inscriptions inscriptions, Personne personne)
	{
		Menu menuGestionPersonnes = new Menu ("Menu gestion Personnes","g");
		menuGestionPersonnes.ajoute(getOptionAjoutPersonne(inscriptions));
		menuGestionPersonnes.ajoute(getOptionModifPersonne(personne));
		menuGestionPersonnes.ajoute(getOptionSupprPersonne(personne));
		menuGestionPersonnes.ajouteRevenir("r");
		return menuGestionPersonnes;
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
			public List<Equipe> getListe()
			{
				return new ArrayList<>(inscriptions.getEquipes());
			}

			@Override
			public void elementSelectionne(int indice, Equipe element)
			{
				Menu menu = getMenuSelectionEquipes(Inscriptions.getInscriptions(), element);
				menu.start();
			}
		});
		menu.ajouteRevenir("r");
		return menu;
	}
	
	static Menu getMenuSelectionEquipes(Inscriptions inscriptions, Equipe equipe)
	{
		Menu menuSelectionPersonnes = new Menu ("Menu Selection Equipes","s");
		menuSelectionPersonnes.ajoute(getOptionModifEquipe(equipe));
		menuSelectionPersonnes.ajoute(getOptionSupprEquipe(equipe));
		menuSelectionPersonnes.ajouteRevenir("r");
		return menuSelectionPersonnes;
	}
	
	static Menu getMenuGestionEquipes(Inscriptions inscriptions, Equipe equipe)
	{
		Menu menuGestionEquipes = new Menu ("Menu gestion Equipes","g");
		menuGestionEquipes.ajoute(getOptionAjoutEquipe(inscriptions));
		menuGestionEquipes.ajoute(getOptionModifEquipe(equipe));
		menuGestionEquipes.ajoute(getOptionSupprEquipe(equipe));
		menuGestionEquipes.ajouteRevenir("r");
		return menuGestionEquipes;
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
		Liste<Competition> menu = new Liste<>("Liste des Competitions","l", new ActionListe<Competition>()
		{

			@Override
			public List<Competition> getListe()
			{
				return new ArrayList<>(inscriptions.getCompetitions());
			}

			@Override
			public void elementSelectionne(int indice, Competition element)
			{
				Menu menu = getMenuSelectionCompetitions(Inscriptions.getInscriptions(), element);
				menu.start();
			}

		});
		menu.ajouteRevenir("r");
		return menu;
	}
	
	static Menu getMenuSelectionCompetitions(Inscriptions inscriptions, Competition competition)
	{
		Menu menuSelectionCompetitions = new Menu ("Menu Selection Competitions","s");
		menuSelectionCompetitions.ajoute(getOptionModifCompetition(competition));
		menuSelectionCompetitions.ajoute(getOptionSupprCompetition(competition));
		menuSelectionCompetitions.ajouteRevenir("r");
		return menuSelectionCompetitions;
	}
	
	static Menu getMenuGestionCompetitions(Inscriptions inscriptions, Competition competition)
	{
		Menu menuGestionCompetitions = new Menu ("Menu gestion Equipes","g");
		menuGestionCompetitions.ajoute(getOptionAjoutCompetition(inscriptions));
		menuGestionCompetitions.ajoute(getOptionModifCompetition(competition));
		menuGestionCompetitions.ajoute(getOptionSupprCompetition(competition));
		menuGestionCompetitions.ajouteRevenir("r");
		return menuGestionCompetitions;
	}
	
}
