package dialogueUtilisateurs;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import commandLine.*;

import metier.Competition;
import metier.Competition.addCloseException;
import metier.Competition.enEquipeException;
import metier.Competition.setDateClotureException;
import metier.Equipe;
import metier.Inscriptions;
import metier.Personne;

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
		Liste<Equipe> ajoutpEquipe = new Liste<> ("Ajouter une Personne a une Equipe","ape",getActionListeAjoutPersonneEquipe(personne, inscriptions));
		return ajoutpEquipe;
	}
	
	static Option getOptionSupprPersonneEquipe(Personne personne, Inscriptions inscriptions)

	{
		Liste<Equipe> supprpEquipe = new Liste<> ("Supprimer une Personne d'une Equipe","spe",getActionListeSupprPersonneEquipe(personne, inscriptions));
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
		Liste<Competition> ajoutpCompetition = new Liste<> ("Ajouter une Personne a une Competition","apc",getActionListeAjoutPersonneCompetition(personne, inscriptions));
		return ajoutpCompetition;
	}
	
	static Option getOptionSupprPersonneCompetition(Personne personne, Inscriptions inscriptions)
	{
		Liste<Competition> supprpCompetition = new Liste<> ("Supprimer une Personne d'une Competition","spc",getActionListeSupprPersonneCompetition(personne, inscriptions));
		return supprpCompetition;
	}
	
	static Option getOptionAjoutEquipeCompetition(Equipe equipe, Inscriptions inscriptions)
	{
		Liste<Competition> ajouteECompetition = new Liste<> ("Ajouter une Equipe a une Competition","aec",getActionListeAjoutEquipeCompetition(equipe, inscriptions));
		return ajouteECompetition;
	}
	
	static Option getOptionSupprEquipeCompetition(Equipe equipe, Inscriptions inscriptions)
	{
		Liste<Competition> supprECompetition = new Liste<> ("Supprimer une Equipe d'une Competition","sec",getActionListeSupprEquipeCompetition(equipe, inscriptions));
		return supprECompetition;
	}

	static Action getActionAjoutPersonne(final Inscriptions inscriptions)

	{
		return new Action()

				{
					@Override
					public void optionSelectionnee()

					{
						String nom = commandLine.util.InOut.getString("Saisissez votre Nom : "),
								prenom = commandLine.util.InOut.getString("Saisissez votre Prenom :"),
								mail = commandLine.util.InOut.getString("Saisissez votre Mail : ");
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
						String nouveauNom = commandLine.util.InOut.getString("Saisissez un nouveau Nom: "),
								nouveauPrenom = commandLine.util.InOut.getString("Saisissez un nouveau Prénom: "),
								nouveauMail = commandLine.util.InOut.getString("Saisissez un nouveau Mail : ");
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
						String nom = commandLine.util.InOut.getString("Saisissez le nom de L'Equipe:");
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
						String nouveauNom = commandLine.util.InOut.getString("Saisissez un nouveau nom d'équipe ");
						equipe.setNom(nouveauNom);
					}
				};
	}

	static ActionListe<Equipe> getActionListeAjoutPersonneEquipe (Personne personne, Inscriptions inscriptions)
	{
		return new ActionListe<Equipe>()
				{
							public List<Equipe> getListe()
			                {
			                        return new ArrayList<>(inscriptions.getEquipes());
			                }
							
							public void elementSelectionne(int indice, Equipe element)
							{
								element.add(personne);
							}

							public Option getOption(Equipe element)
							{
								return null;
							}
				};
	}
	
	static ActionListe<Equipe> getActionListeSupprPersonneEquipe (Personne personne, Inscriptions inscriptions)

	{
		return new ActionListe<Equipe> ()

				{
							public List<Equipe> getListe()
							{
									return new ArrayList<>(inscriptions.getEquipes());
							}
							
							public void elementSelectionne(int indice, Equipe element)
							{
								element.remove(personne);
							}
							
							public Option getOption(Equipe element)
							{
								return null;
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
						String nom = commandLine.util.InOut.getString("Saisissez le nom de la Compétition :");
						int jour = commandLine.util.InOut.getInt("Saisissez un jour :"),
								mois = commandLine.util.InOut.getInt("Saisissez un mois :"),
								annee = commandLine.util.InOut.getInt("Saisissez une Année:");
						LocalDate dateCloture = LocalDate.of (annee,mois,jour);
						String equipe = commandLine.util.InOut.getString("est il en equipe: o:oui	n:non");
						
						Boolean enEquipe;
						if (equipe.equals("n")) {
							enEquipe = false;
						}
						else if (equipe.equals("o")){
							enEquipe = true;
						}
						else{
							enEquipe = null;
						}
								inscriptions.createCompetition(nom, dateCloture,enEquipe);
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
						String nouveauNom = commandLine.util.InOut.getString("Saisissez un nouveau nom: ");
						int jour = commandLine.util.InOut.getInt("Saisissez un nouveau jour :"),
								mois = commandLine.util.InOut.getInt("Saisissez un nouveau mois :"),
								annee = commandLine.util.InOut.getInt("Saisissez une nouvelle Année:");
						LocalDate nouvelledateCloture = LocalDate.of (annee,mois,jour);
						
						competition.setNom(nouveauNom);
						try
						{
							competition.setDateCloture(nouvelledateCloture);
						} 
						catch (setDateClotureException e)
						{
							System.out.println(e);
						}
					}
				};
	}
	
	static ActionListe<Competition> getActionListeAjoutPersonneCompetition (Personne personne, Inscriptions inscriptions)
	{
		return new ActionListe<Competition> ()
				{
							public List<Competition> getListe()
							{
								return new ArrayList<>(inscriptions.getCompetitions());
							}

							public void elementSelectionne(int indice, Competition element)
							{
								try
								{
									element.add(personne);
								} 
								catch (addCloseException e)
								{
									System.out.println(e);
								} 
								catch (enEquipeException e)
								{
									System.out.println(e);
								}
							}

							public Option getOption(Competition element)
							{
								return null;
							}
					};
	}
	
	static ActionListe<Competition> getActionListeSupprPersonneCompetition (Personne personne, Inscriptions inscriptions)
	{
		return new ActionListe<Competition> ()
				{
							public List<Competition> getListe()
							{
								return new ArrayList<>(inscriptions.getCompetitions());
							}

							public void elementSelectionne(int indice, Competition element)
							{
								element.remove(personne);
							}

							public Option getOption(Competition element)
							{
								return null;
							}
					};
	}
	
	static ActionListe<Competition> getActionListeAjoutEquipeCompetition (Equipe equipe, Inscriptions inscriptions)
	{
		return new ActionListe<Competition>()
				{
					public List<Competition> getListe()
					{
						return new ArrayList<>(inscriptions.getCompetitions());
					}
					
					public void elementSelectionne(int indice, Competition element)
					{
						try
						{
							element.add(equipe);
						}
						catch (addCloseException e)
						{
							System.out.println(e);
						} 
						catch (enEquipeException e)
						{
							System.out.println(e);
						}
					}
					public Option getOption (Competition element)
					{
						return null;
					}
				};
	}
	
	static ActionListe<Competition> getActionListeSupprEquipeCompetition(Equipe equipe, Inscriptions inscriptions)
	{
		return new ActionListe<Competition>()
		{	
			@Override
			public List<Competition> getListe()
			{
				return new ArrayList<>(inscriptions.getCompetitions());
			}
			
			@Override
			public void elementSelectionne(int indice, Competition element)
			{
				element.remove(equipe);
			}
			
			@Override
			public Option getOption(Competition element)
			{
				return null;
			}
		};
	}
	
}
