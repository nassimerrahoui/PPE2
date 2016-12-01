package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;

public class CandidatTest {


	@Test
	public void testGetNom() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		String i = testeur.getPrenom();
		assertEquals("testeur",i);
		
	}

	@Test
	public void testSetNom() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne t = inscriptions.createPersonne("test", "testeur", "azerty");
		t.setNom("NewName");
		String i = t.getNom();
		assertEquals("NewName",i);
	}

	@Test
	public void testGetCompetitions() {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		Competition CompetTest = inscriptions.createCompetition("Mondial de test", null, false);
		Competition CompetTest2 = inscriptions.createCompetition("Mondial de test", null, false);
		CompetTest.add(testeur);
		CompetTest2.add(testeur);
		int size = inscriptions.getCompetitions().size();
		inscriptions.getCompetitions().contains(CompetTest) ;
		inscriptions.getCompetitions().contains(CompetTest2);
		
		assertEquals(size,inscriptions.getCompetitions().size());
	}


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
	public void testCompareTo() {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne a = inscriptions.createPersonne("test", "testeur", "mail.com");
		Personne b = inscriptions.createPersonne("test", "testeur", "mail.com");
		
		assertEquals(0,a.compareTo(b));
		//TODOO > Tester quand c'est !=
		

	}

	@Test
	public void testToString() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition flechettes = inscriptions.createCompetition("Mondial de flechettes", null, false);
		Personne boris = inscriptions.createPersonne("Boris", "le Hachoir", "ytreza");
		flechettes.add(boris);
		Equipe lesManouches = inscriptions.createEquipe("Les Manouches");
		lesManouches.add(boris);
		
		assertNotNull(boris.toString());
	}

}
