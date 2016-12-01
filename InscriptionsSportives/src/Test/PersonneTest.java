package Test;

import static org.junit.Assert.*;  

import org.junit.Test;
import inscriptions.Competition;
import inscriptions.Inscriptions;
import inscriptions.Personne;
import inscriptions.Equipe;

public class PersonneTest {
	
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
	public void testGetPrenom() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		String i = testeur.getPrenom();
		assertEquals("testeur",i);
		}

	@Test
	public void testSetPrenom() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		testeur.setPrenom("setters");
		String i = testeur.getPrenom();
		assertEquals("setters",i);
	}

	@Test
	public void testGetMail() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		String i = testeur.getMail();
		assertEquals("azerty",i);
	}

	@Test
	public void testSetMail() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		testeur.setMail("MailTest");
		String i = testeur.getMail();
		assertEquals("MailTest",i);
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
	public void testGetEquipes() {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		Equipe e = inscriptions.createEquipe("test");
		Equipe b = inscriptions.createEquipe("test2");
		e.add(testeur);
		b.add(testeur);
		int size = inscriptions.getEquipes().size();
		if(inscriptions.getEquipes().contains(e) && inscriptions.getEquipes().contains(b))
		{
		assertEquals(size,inscriptions.getEquipes().size());
		}

	}
	




}
