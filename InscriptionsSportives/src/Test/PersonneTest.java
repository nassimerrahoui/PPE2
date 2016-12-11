package Test;

import static org.junit.Assert.*;  

import org.junit.Test;
import inscriptions.Inscriptions;
import inscriptions.Personne;
import inscriptions.Equipe;

public class PersonneTest {
	
	@Test
	public void testDelete() {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		testeur.delete();
		assertTrue(!inscriptions.getCandidats().contains(testeur));
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
		assertTrue(testeur.getEquipes().contains(e));
		assertTrue(testeur.getEquipes().contains(b));
		assertEquals(size,inscriptions.getEquipes().size());
		

	}
	




}
