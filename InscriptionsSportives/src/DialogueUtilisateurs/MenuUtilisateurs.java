package DialogueUtilisateurs;

import java.util.List;

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
//		menuPersonne.ajoute(getOptionSupprPersonne());
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
				
				return inscriptions;
			}

			@Override
			public void elementSelectionne(int indice, Personne element)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	static Menu getMenuEquipe(Inscriptions inscriptions)
	{
		Menu menuEquipe = new Menu ("Menu Equipe","e");
		menuEquipe.ajoute(getOptionAjoutEquipe(inscriptions));
		menuEquipe.ajouteRevenir("r");
        menuEquipe.setRetourAuto(true);
		return menuEquipe;
	}
	
	static Menu getMenuCompetition(Inscriptions inscriptions)
	{
		Menu menuCompetition = new Menu ("Menu Compétition","c");
		menuCompetition.ajoute(getOptionAjoutCompetition(inscriptions));
		menuCompetition.ajouteRevenir("r");
        menuCompetition.setRetourAuto(true);
		return menuCompetition;
	}
}
