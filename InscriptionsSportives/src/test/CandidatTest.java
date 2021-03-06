package test;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import metier.Competition;
import metier.Competition.addCloseException;
import metier.Competition.enEquipeException;
import metier.Inscriptions;
import metier.Personne;

public class CandidatTest {


	@Test
	public void testGetNom() throws enEquipeException, addCloseException {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		String i = testeur.getPrenom();
		assertEquals("testeur",i);
	}

	@Test
	public void testSetNom() throws enEquipeException, addCloseException {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne t = inscriptions.createPersonne("test", "testeur", "azerty");
		t.setNom("NewName");
		String i = t.getNom();
		assertEquals("NewName",i);
	}

	@Test
	public void testGetCompetitions() throws enEquipeException, addCloseException {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		Competition CompetTest = inscriptions.createCompetition("Mondial de test", LocalDate.now().plusDays(20), false);
		Competition CompetTest2 = inscriptions.createCompetition("Mondial de test", LocalDate.now().plusDays(20), false);
		try{
			CompetTest.add(testeur);
			CompetTest2.add(testeur);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}

		assertTrue(testeur.getCompetitions().contains(CompetTest));
		assertTrue(testeur.getCompetitions().contains(CompetTest2));
		
		
	}


	@Test
	public void testDelete() throws enEquipeException, addCloseException {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		testeur.delete();
		assertTrue(!inscriptions.getCandidats().contains(testeur));
	}
	

	@Test
	public void testCompareTo() throws enEquipeException, addCloseException {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne a = inscriptions.createPersonne("test", "testeur", "mail.com");
		Personne b = inscriptions.createPersonne("test", "testeur", "mail.com");
		
		assertEquals(0,a.compareTo(b));
		
		//Teste  quand c'est !=
		Inscriptions i = Inscriptions.getInscriptions();
		Personne aa = i.createPersonne("test", "testeur", "mail.com");
		Personne bb = i.createPersonne("testrf", "testeur", "mail.com");
		assertNotEquals(0,aa.compareTo(bb));
	}

	@Test
	public void testToString() throws enEquipeException, addCloseException {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne boris = inscriptions.createPersonne("Boris", "le Hachoir", "ytreza");
		
		assertNotNull(boris.toString());
	}
	


}
