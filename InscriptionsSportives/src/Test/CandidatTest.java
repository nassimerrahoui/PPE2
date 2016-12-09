package Test;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import inscriptions.Competition;
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
		Competition CompetTest = inscriptions.createCompetition("Mondial de test", LocalDate.now().plusDays(20), false);
		Competition CompetTest2 = inscriptions.createCompetition("Mondial de test", LocalDate.now().plusDays(20), false);
		CompetTest.add(testeur);
		CompetTest2.add(testeur);
		assertTrue(testeur.getCompetitions().contains(CompetTest));
		assertTrue(testeur.getCompetitions().contains(CompetTest2));
		
		
	}


	@Test
	public void testDelete() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		testeur.delete();
		assertTrue(!inscriptions.getCandidats().contains(testeur));
	}
	

	@Test
	public void testCompareTo() {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne a = inscriptions.createPersonne("test", "testeur", "mail.com");
		Personne b = inscriptions.createPersonne("test", "testeur", "mail.com");
		
		assertEquals(0,a.compareTo(b));
		//TODO > Tester quand c'est !=
		

	}

	@Test
	public void testToString() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne boris = inscriptions.createPersonne("Boris", "le Hachoir", "ytreza");
		
		assertNotNull(boris.toString());
	}
	


}
