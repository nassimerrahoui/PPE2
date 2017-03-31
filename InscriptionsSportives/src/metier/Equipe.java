
package metier;


import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import persistance.equipeData;


/**
 * Représente une Equipe. C'est-à-dire un ensemble de personnes pouvant 
 * s'inscrire à une compétition.
 * 
 */
public class Equipe extends Candidat
{
	private static final long serialVersionUID = 4147819927233466035L;
	private SortedSet<Personne> membres = new TreeSet<>();
	private int id;
	
	protected Equipe(Inscriptions inscriptions, String nom)
	{
		super(inscriptions, nom);
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * Retourne l'ensemble des personnes formant l'équipe.
	 */
	
	public SortedSet<Personne> getMembres()
	{
		return Collections.unmodifiableSortedSet(membres);
	}
	
	/**
	 * Ajoute une personne dans l'equipe.
	 * @param membre
	 * @return
	 */

	public boolean add(Personne membre)
	{
		membre.add(this);
		equipeData.addMembre(membre,this);
		return membres.add(membre);
	}

	/**
	 * Supprime une personne de l'equipe. 
	 * @param membre
	 * @return
	 */
	
	public boolean remove(Personne membre)
	{
		membre.remove(this);
		equipeData.remove(membre,this);
		return membres.remove(membre);
	}

	@Override
	public void delete()
	{
		super.delete();
		for (Personne p : membres)
			this.remove(p);
		equipeData.delete(this);
	}
	
	@Override
	public String toString()
	{
		return "\n" + "Equipe " + super.toString();
	}
}
