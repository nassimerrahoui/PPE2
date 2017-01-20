package DialogueUtilisateurs;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Metier.Competition;
import Metier.Equipe;
import Metier.Inscriptions;
import Metier.Personne;
import utilitaires.ligneDeCommande.Action;
import utilitaires.ligneDeCommande.ActionListe;
import utilitaires.ligneDeCommande.Liste;
import utilitaires.ligneDeCommande.Menu;
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
	

	static Option getOptionAjoutPersonneEquipe(Personne personne, Inscriptions inscriptions)
	{
		Option ajoutpEquipe = new Option ("Ajouter une Personne a une Equipe","ape",getActionAjoutPersonneEquipe(personne, inscriptions));
		return ajoutpEquipe;
	}
	
	static Option getOptionSupprPersonneEquipe(Personne personne, Inscriptions inscriptions)
	{
		Option supprpEquipe = new Option ("Supprimer une Personne d'une Equipe","spe",getActionAjoutPersonneEquipe(personne, inscriptions));
		return supprpEquipe;
	}
	
	static Option getOptionAjoutEquipe(Inscriptions inscriptions)
	{
		Option ajoutEquipe = new Option("Ajouter une Equipe","ae",getActionAjoutEquipe(inscriptions));
		return ajoutEquipe;
	}
	
	static Option getOptionSupprEquipe(Equipe equipe)
	{
		Option SupprEquipe = new Option("Supprimer une Equipe","se",getActionSupprEquipe(equipe));
		return SupprEquipe;
	}
	
	static Option getOptionModifEquipe(Equipe equipe)
	{
		Option ModifEquipe = new Option("Modifier une Equipe","me",getActionModifEquipe(equipe));
		return ModifEquipe;
	}
	
	static Option getOptionAjoutCompetition(Inscriptions inscriptions)
	{
		Option ajoutCompetition = new Option("Ajouter une Compétition","ac",getActionAjoutCompetition(inscriptions));
		return ajoutCompetition;
	}
	
	static Option getOptionSupprCompetition(Competition competition)
	{
		Option SupprCompetition = new Option("Supprimer une Compétition","sc",getActionSupprCompetition(competition));
		return SupprCompetition;
	}
	
	static Option getOptionModifCompetition(Competition competition)
	{
		Option ModifCompetition = new Option("Modifier une Compétition","mc",getActionModifCompetition(competition));
		return ModifCompetition;
	}
	
	static Option getOptionAjoutPersonneCompetition(Personne personne, Inscriptions inscriptions)
	{
		Option ajoutpCompetition = new Option ("Ajouter une Equipe a une Competition","aec",getActionAjoutPersonneCompetition(personne, inscriptions));
		return ajoutpCompetition;
	}
	
	static Option getOptionSupprPersonneCompetition(Personne personne, Inscriptions inscriptions)
	{
		Option supprpCompetition = new Option ("Supprimer une Equipe d'une Competition","sec",getActionSupprPersonneCompetition(personne, inscriptions));
		return supprpCompetition;
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
	
	static Action getActionSupprPersonne (final Personne personne)
	{
		return new Action ()
				{
					@Override
					public void optionSelectionnee()
					{
						personne.delete();
					}
				};
	}
	
	static Action getActionModifPersonne (final Personne personne)
	{
		return new Action ()
				{
					@Override
					public void optionSelectionnee()
					{
						String nouveauNom = utilitaires.EntreesSorties.getString("Saisissez un nouveau Nom: "),
								nouveauPrenom = utilitaires.EntreesSorties.getString("Saisissez un nouveau Prénom: "),
								nouveauMail = utilitaires.EntreesSorties.getString("Saisissez un nouveau Mail : ");
						personne.setNom(nouveauNom);
						personne.setPrenom(nouveauPrenom);
						personne.setMail(nouveauMail);
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
	
	static Action getActionSupprEquipe (final Equipe equipe)
	{
		return new Action ()
				{
					@Override
					public void optionSelectionnee()
					{
						equipe.delete();
					}
				};
	}
	
	static Action getActionModifEquipe (final Equipe equipe)
	{
		return new Action ()
				{
					@Override
					public void optionSelectionnee()
					{
						String nouveauNom = utilitaires.EntreesSorties.getString("Saisissez un nouveau nom d'équipe ");
						equipe.setNom(nouveauNom);
					}
				};
	}
	
	static Action getActionAjoutPersonneEquipe (final Personne personne, Inscriptions inscriptions)
	{
		return new Action ()
				{
					@Override
					public void optionSelectionnee()
					{
						Liste<Equipe> menu = new Liste<>("Selectionnez une Equipe","s", new ActionListe<Equipe>()
						{
							@Override
							public List<Equipe> getListe()
							{
								return new ArrayList<>(inscriptions.getEquipes());
							}

							@Override
							public void elementSelectionne(int indice, Equipe element)
							{
								element.add(personne);	
							}
						});
					}
				};
	}
	
	static Action getActionSupprPersonneEquipe (final Personne personne, Inscriptions inscriptions)
	{
		return new Action ()
				{
					@Override
					public void optionSelectionnee()
					{ 
						Liste<Equipe> menu = new Liste<>("Selectionnez une Equipe","s", new ActionListe<Equipe>()
						{
							@Override
							public List<Equipe> getListe()
							{
								return new ArrayList<>(inscriptions.getEquipes());
							}

							@Override
							public void elementSelectionne(int indice, Equipe element)
							{
								element.remove(personne);	
							}
						});
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
	
	static Action getActionSupprCompetition (final Competition competition)
	{
		return new Action ()
				{
					@Override
					public void optionSelectionnee()
					{
						competition.delete();
					}
				};
	}
	
	static Action getActionModifCompetition (final Competition competition)
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
						
						competition.setNom(nouveauNom);
						competition.setDateCloture(nouvelledateCloture);
					}
				};
	}
	
	static Action getActionAjoutPersonneCompetition (final Personne personne, Inscriptions inscriptions)
	{
		return new Action ()
				{
					@Override
					public void optionSelectionnee()
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
								element.add(personne);
							}

						});
					}
				};
	}
	
	static Action getActionSupprPersonneCompetition (final Personne personne, Inscriptions inscriptions)
	{
		return new Action ()
				{
					@Override
					public void optionSelectionnee()
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
								element.remove(personne);
							}

						});
					}
				};
	}
	

}
