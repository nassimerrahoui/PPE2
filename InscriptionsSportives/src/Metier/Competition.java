package Metier;
import java.io.Serializable;
import java.util.Collections;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

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
	private LocalDate today =LocalDate.now();

	Competition(Inscriptions inscriptions, String nom, LocalDate dateCloture, boolean enEquipe) {
		this.enEquipe = enEquipe;
		this.inscriptions = inscriptions;
		this.nom = nom;
		this.dateCloture = dateCloture;
		candidats = new TreeSet<>();
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

	public boolean estEnEquipe() {
		return enEquipe;
	}

	/**
	 * Modifie la date de cloture des inscriptions. Il est possible de la
	 * reculer mais pas de l'avancer.
	 * 
	 * @param dateCloture
	 */

	public void setDateCloture(LocalDate dateCloture) {
		// TODO v�rifier que l'on avance pas la date.Modifie la date de cloture
		// des inscriptions.
		// Il est possible de la reculer mais pas de l'avancer.
		//TODO ajouter exceptions dans le cas ou �a ne passe pas

		if(dateCloture.isAfter(this.dateCloture))
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

	public boolean add(Personne personne)  {
		// TODO vérifier que la date de clôture n'est pas pass�
		if(this.dateCloture.isAfter(today))
		if (enEquipe)
			throw new RuntimeException();
		personne.add(this);
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

	public boolean add(Equipe equipe) {
		// TODO vérifier que la date de clôture n'est pas passée
		
		if(this.dateCloture.isAfter(today))
		if (!enEquipe)
			throw new RuntimeException();
		equipe.add(this);
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
		return candidats.remove(candidat);
	}

	/**
	 * Supprime la compétition de l'application.
	 */

	public void delete() {
		for (Candidat candidat : candidats)
			remove(candidat);
		inscriptions.remove(this);
	}

	@Override
	public int compareTo(Competition o) {
		return getNom().compareTo(o.getNom());
	}

	@Override
	public String toString() {
		return getNom();
	}

	
public class addCloseException extends RuntimeException {
		
		LocalDate date,dateCloture ;
		String nom,prenom,libelleCompet;
		public addCloseException(Candidat c)
	
		{
			date = LocalDate.now();
			nom= c.getNom();
			dateCloture = Competition.this.dateCloture;
			libelleCompet = Competition.this.getNom();
		}
		

		@Override
		public String toString() 
		{
			
			return super.toString();
		
		}
		
			
	}	
	}
	


