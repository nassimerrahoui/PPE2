package metier;
import java.io.Serializable;
import java.util.Collections;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

import persistance.candidatData;
import persistance.competitionData;
/**
 * Représente une compétition, c'est-à-dire un ensemble de candidats inscrits
 * à un événement, les inscriptions sont closes à la date dateCloture.
 *
 */

public class Competition implements Comparable<Competition>, Serializable {

	private static final long serialVersionUID = -2882150118573759729L;
	private Inscriptions inscriptions;
	private String nom;
	private Set<Candidat> candidats;
	private LocalDate dateCloture;
	private boolean enEquipe = false;
	private LocalDate today = LocalDate.now();
	private int id;

	protected Competition(Inscriptions inscriptions, String nom, LocalDate dateCloture, boolean enEquipe)
	{
		this.inscriptions = inscriptions;
		this.nom = nom;
		this.dateCloture = dateCloture;
		this.enEquipe = enEquipe;
		candidats = new TreeSet<>();
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id) 
	{
		this.id = id;
		// TODO Exception runtime ou une classe fille de runtime bricoler
	}

	/**
	 * Retourne le nom de la compétition.
	 * 
	 * @return
	 */

	public String getNom() {
		return nom;
	}

	/**
	 * Modifie le nom de la compétition.
	 */

	public void setNom(String nom) {
		this.nom = nom;
		competitionData.update(this);
	}

	/**
	 * Retourne vrai si les inscriptions sont encore ouvertes, faux si les
	 * inscriptions sont closes.
	 * 
	 * @return
	 */

	public boolean inscriptionsOuvertes() {
		// TODO retourner vrai si et seulement si la date système est
		// antérieure à la date de clôture.	
		
		return (today.isBefore(dateCloture));
	}

	/**
	 * Retourne la date de cloture des inscriptions.
	 * 
	 * @return
	 */

	public LocalDate getDateCloture() {
		return dateCloture;
	}
	
	/**
	 * Est vrai si et seulement si les inscriptions sont réservées aux
	 * équipes.
	 * 
	 * @return
	 */

	public boolean getEnEquipe() {
		return enEquipe;
	}
	
	public void setEnEquipe(boolean enEquipe) {
		this.enEquipe = enEquipe;
		competitionData.update(this);
	}

	/**
	 * Modifie la date de cloture des inscriptions. Il est possible de la
	 * reculer mais pas de l'avancer.
	 * 
	 * @param dateCloture
	 */

	public void setDateCloture(LocalDate dateCloture) throws setDateClotureException {
		// TODO v�rifier que l'on avance pas la date.Modifie la date de cloture
		// des inscriptions.
		// Il est possible de la reculer mais pas de l'avancer.
		//TODO ajouter exceptions dans le cas ou �a ne passe pas
		
		if(dateCloture.isBefore(this.dateCloture))
			throw new setDateClotureException(dateCloture);
			this.dateCloture = dateCloture;
	}
	
	/**
	 * Retourne l'ensemble des candidats inscrits.
	 * 
	 * @return
	 */

	public Set<Candidat> getCandidats() {
		return Collections.unmodifiableSet(candidats);
	}

	/**
	 * Inscrit un candidat de type Personne à la compétition. Provoque une
	 * exception si la compétition est réservée aux équipes ou que les
	 * inscriptions sont closes.
	 * 
	 * @param personne
	 * @return
	 */

	public boolean add(Personne personne) throws addCloseException,enEquipeException {
		// TODO vérifier que la date de clôture n'est pas passée
		
			if(this.dateCloture.isBefore(today))
				throw new addCloseException(personne);
			if (enEquipe)
				throw new enEquipeException(personne);
			
				personne.add(this);
				candidatData.inscriptionCandidat(personne, this);
		return candidats.add(personne);
	}

	/**
	 * Inscrit un candidat de type Equipe à la compétition. Provoque une
	 * exception si la compétition est réservée aux personnes ou que les
	 * inscriptions sont closes.
	 * 
	 * @param personne
	 * @return
	 */

	public boolean add(Equipe equipe) throws addCloseException,enEquipeException {
		// TODO vérifier que la date de clôture n'est pas passée
		
			if(this.dateCloture.isBefore(today))
				throw new addCloseException(equipe);
			if (!enEquipe)
				throw new enEquipeException(equipe);
			
				equipe.add(this);
				candidatData.inscriptionCandidat(equipe, this);
		return candidats.add(equipe);
	}

	/**
	 * Désinscrit un candidat.
	 * 
	 * @param candidat
	 * @return
	 */

	public boolean remove(Candidat candidat) {
		candidat.remove(this);
		competitionData.remove(this, candidat);
		return candidats.remove(candidat);
	}

	/**
	 * Supprime la compétition de l'application.
	 */

	public void delete() {
		for (Candidat candidat : candidats)
			this.remove(candidat);
		inscriptions.remove(this);
		competitionData.delete(this);
	}

	@Override
	public int compareTo(Competition o) {
		return getNom().compareTo(o.getNom());
	}

	@Override
	public String toString() {
		return getNom();
	}


	public class enEquipeException extends RuntimeException {
		
	private static final long serialVersionUID = -7303533125444582417L;
	LocalDate date,dateCloture ;
	String nom,libelleCompet,typeCompet,typePers;
	
	//TODO prendre en param Candidat et utiliser l'objet pour recup donn�es
	//TODO enlever les info sur la comp�titions
	public enEquipeException(Personne p){
			date = LocalDate.now();
			nom= p.getNom();
			dateCloture = Competition.this.dateCloture;
			libelleCompet = Competition.this.getNom();
			typePers="personne";
			typeCompet = "collective";		
		}
		
	public enEquipeException(Equipe e){
			date = LocalDate.now();
			nom= e.getNom();
			dateCloture = Competition.this.dateCloture;
			libelleCompet = Competition.this.getNom();
			typePers="equipe";
			typeCompet = "individuelle";
			
			
		}
		
		@Override
		public String toString()
		{
			String result = super.toString();
			result += " ERREUR! Vous tentez d'inscrire le candidat :" + getNom() + "\n"+
					" de type:" + this.typePers + "\n"+
					" le:" + this.date + "\n"+
					" a la comp�tition: "+ this.libelleCompet + "\n"+
					" alors que cette competition est de type :" + this.typeCompet;
			return result;
		}
		
		
		
	}
	
	public class setDateClotureException extends Exception{
		
		private static final long serialVersionUID = 7790420760864054581L;
		String libelleCompet,type;
		LocalDate dateCloture, dateSet;
		
		public setDateClotureException(LocalDate dateSet)
		{
			this.libelleCompet = Competition.this.getNom();
			this.dateSet = dateSet;
			this.dateCloture = Competition.this.dateCloture;
		}
		
		@Override
		public String toString()
		{
			String res = super.toString();
			res += "Impossible de changer la date de cl�ture de la competition :"+this.libelleCompet +"\n" +
			"� une date ant�rieur � celle de la date de cl�ture initiale. \n"+
			"Date saisie :" + this.dateSet + "\n"+
			"Date de cl�ture initial :"+ this.dateCloture; 
			return res;
		}
		
	}
		
	public class addCloseException extends Exception {
			
			private static final long serialVersionUID = 900473083584083912L;
			LocalDate date,dateCloture ;
			String nom,libelleCompet,typeCompet;
			
			public addCloseException(Candidat c)
			{
				date = LocalDate.now();
				nom= c.getNom();
				dateCloture = Competition.this.dateCloture;
				libelleCompet = Competition.this.getNom();
				if (!enEquipe)
				{
					typeCompet = "individuelle";  
				}
				else
				{
					typeCompet = "collective";
				}
			}
			

			@Override
			public String toString() 
			{
				String result = super.toString();
				result += " ERREUR! Vous tentez d'inscrire le candidat :" + this.nom + "\n"+
						" le:" + this.date + "\n"+
						" a la comp�tition: "+ this.libelleCompet + "\n"+
						"de type: " + this.typeCompet +"\n"+
						" alors que la date de cl�ture de celle-ci est d�pass�e"+"\n"+
						"date cl�ture : "+this.dateCloture;
				return result;
			
			}
			
			
		}
	
}