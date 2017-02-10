package dialogueUtilisateurs;
//Changement pour commit 13/01/2017
import java.util.ArrayList;
import java.util.List;

import metier.Competition;
import metier.Equipe;
import metier.Inscriptions;
import metier.Personne;
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
        menuPersonne.setRetourAuto(false);
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
			{}

			@Override
			public Option getOption(Personne element)
			{
				return getMenuSelectionPersonnes(Inscriptions.getInscriptions(), element);
			}
		});
		menu.ajouteRevenir("r");
		return menu;
	}
	
	static Menu getMenuSelectionPersonnes(Inscriptions inscriptions, Personne personne)
	{
		Menu menuSelectionPersonnes = new Menu (personne.getPrenom()+ " "+personne.getNom(),"s");
		menuSelectionPersonnes.ajoute(getOptionModifPersonne(personne));
		menuSelectionPersonnes.ajoute(getOptionSupprPersonne(personne));
		menuSelectionPersonnes.ajoute(getMenuGestionPersonneEquipes(inscriptions, personne));
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
	

	static Menu getMenuGestionPersonneEquipes(Inscriptions inscriptions, Personne personne)
	{
		Menu menuGestionPersonneEquipes = new Menu ("Menu gestion des Equipes de la personne","g");
		menuGestionPersonneEquipes.ajoute(getOptionAjoutPersonneEquipe(personne, inscriptions));
		menuGestionPersonneEquipes.ajoute(getOptionSupprPersonneEquipe(personne, inscriptions));
		menuGestionPersonneEquipes.ajouteRevenir("r");
		return menuGestionPersonneEquipes;
	}
	

	static Menu getMenuGestionPersonneCompetitions(Inscriptions inscriptions, Personne personne)
	{
		Menu menuGestioPersonneCompetitions = new Menu ("Menu gestion Personne dans une Compétition","g");
		menuGestioPersonneCompetitions.ajoute(getOptionAjoutPersonneCompetition(personne, inscriptions));
		menuGestioPersonneCompetitions.ajoute(getOptionSupprPersonneCompetition(personne, inscriptions));
		menuGestioPersonneCompetitions.ajouteRevenir("r");
		return menuGestioPersonneCompetitions;
	}
	
	static Menu getMenuEquipe(Inscriptions inscriptions)
	{
		Menu menuEquipe = new Menu ("Menu Equipe","e");
		menuEquipe.ajoute(getOptionAjoutEquipe(inscriptions));
		menuEquipe.ajoute(getMenuListeEquipes(inscriptions));
		menuEquipe.ajouteRevenir("r");
        menuEquipe.setRetourAuto(false);
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
			{}
			
			@Override
			public Option getOption(Equipe element)
			{
				return getMenuSelectionEquipes(Inscriptions.getInscriptions(), element);
			}
		});
		menu.ajouteRevenir("r");
		return menu;
	}
	
	static Menu getMenuSelectionEquipes(Inscriptions inscriptions, Equipe equipe)
	{
		Menu menuSelectionPersonnes = new Menu (equipe.getNom()+ "","s");
		menuSelectionPersonnes.ajoute(getOptionModifEquipe(equipe));
		menuSelectionPersonnes.ajoute(getOptionSupprEquipe(equipe));
		menuSelectionPersonnes.ajouteRevenir("r");
		return menuSelectionPersonnes;
	}
	
	static Menu getMenuCompetition(Inscriptions inscriptions)
	{
		Menu menuCompetition = new Menu ("Menu Compétition","c");
		menuCompetition.ajoute(getOptionAjoutCompetition(inscriptions));
		menuCompetition.ajoute(getMenuListeCompetitions(inscriptions));
		menuCompetition.ajouteRevenir("r");
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
			{}
			
			@Override
			public Option getOption(Competition element)
			{
				return getMenuSelectionCompetitions(Inscriptions.getInscriptions(), element);
			}

		});
		menu.ajouteRevenir("r");
		return menu;
	}
	
	static Menu getMenuSelectionCompetitions(Inscriptions inscriptions, Competition competition)
	{
		Menu menuSelectionCompetitions = new Menu (competition.getNom()+""+" se terminant le "+competition.getDateCloture(),"s");
		menuSelectionCompetitions.ajoute(getOptionModifCompetition(competition));
		menuSelectionCompetitions.ajoute(getOptionSupprCompetition(competition));
		menuSelectionCompetitions.ajouteRevenir("r");
		return menuSelectionCompetitions;
	}
	
}
