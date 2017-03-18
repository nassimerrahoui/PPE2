package persistance;

import java.util.SortedSet;
import java.util.TreeSet;

import metier.Candidat;
import metier.Inscriptions;

public class candidatData
{
	public static SortedSet<Candidat> select(Inscriptions inscriptions) 
	{
		SortedSet<Candidat> Candidats = new TreeSet<>();

		for (Candidat p : personneData.select(inscriptions))
		{
			Candidats.add(p);
		}
		
		for (Candidat p : equipeData.select(inscriptions))
		{
			Candidats.add(p);
		}
		
		return Candidats;
	}
}
