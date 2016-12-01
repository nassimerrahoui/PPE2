package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;

public class EquipeTest {

	@Test
	public void testDelete() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		Competition testCompet = inscriptions.createCompetition("testCompet", null, false);
		testCompet.add(testeur);
		Equipe e = inscriptions.createEquipe("test");
		e.add(testeur);
		inscriptions.getPersonnes().contains(testeur);
		int before = inscriptions.getPersonnes().size();
		testeur.delete();
		int after = inscriptions.getPersonnes().size();
		assertEquals(before-1, after);
		
	}

	@Test
	public void testToString() {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("testeur", "Dent de plomb", "azerty");
		Competition flechettes = inscriptions.createCompetition("Mondial de flechettes", null, false);
		flechettes.add(testeur);
		Equipe TestTeam = inscriptions.createEquipe("TestTeam");
		TestTeam.add(testeur);
		assertNotNull(testeur.toString());
	}


	@Test
	public void testGetMembres() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		Personne testeur2 = inscriptions.createPersonne("test", "testeur", "azerty");
		Equipe e = inscriptions.createEquipe("test");
		e.add(testeur);
		e.add(testeur2);
		int size = e.getMembres().size();
		if(e.getMembres().contains(testeur) && e.getMembres().contains(testeur2))
		{
		assertEquals(size,e.getMembres().size());
		}
	}

	@Test
	public void testAddPersonne() {

		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		Personne testeur2 = inscriptions.createPersonne("test2", "testeur2", "azerty2");
		Competition testCompet = inscriptions.createCompetition("testCompet", null, false);
		testCompet.add(testeur);
		testCompet.add(testeur2);
		Equipe e = inscriptions.createEquipe("test");
		e.add(testeur);
		int before = e.getMembres().size();
		e.add(testeur2);
		e.getMembres().contains(testeur2);
		int after = e.getMembres().size();
		assertEquals(before+1, after);
		
	}

	@Test
	public void testRemovePersonne() {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		Personne testeur2 = inscriptions.createPersonne("test2", "testeur2", "azerty2");
		Competition testCompet = inscriptions.createCompetition("testCompet", null, false);
		testCompet.add(testeur);
		testCompet.add(testeur2);
		Equipe e = inscriptions.createEquipe("test");
		e.add(testeur);
		e.add(testeur2);
		
		e.getMembres().contains(testeur);
		int before = e.getMembres().size();
		testeur.delete();
		int after = e.getMembres().size();
		assertEquals(before-1, after);
	}

}
