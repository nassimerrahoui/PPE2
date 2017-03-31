package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

import metier.Competition;
import metier.Competition.addCloseException;
import metier.Competition.enEquipeException;
import metier.Equipe;
import metier.Inscriptions;
import metier.Personne;

public class PersonneTest {
	
	@Test
	public void testDelete() throws enEquipeException, addCloseException {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		Competition c= inscriptions.createCompetition("test", LocalDate.now().plusDays(10),false);
		try{
			c.add(testeur); 
		}
		catch (Exception ex){
			
			System.out.println(ex);
		}
		testeur.delete();
		assertTrue(!inscriptions.getCandidats().contains(testeur));
		assertTrue(!c.getCandidats().contains(testeur));
		
	}

	@Test
	public void testGetPrenom() throws enEquipeException, addCloseException {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		String i = testeur.getPrenom();
		assertEquals("testeur",i);
		}

	@Test
	public void testSetPrenom() throws enEquipeException, addCloseException {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		testeur.setPrenom("setters");
		String i = testeur.getPrenom();
		assertEquals("setters",i);
	}

	@Test
	public void testGetMail() throws enEquipeException, addCloseException {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		String i = testeur.getMail();
		assertEquals("azerty",i);
	}

	@Test
	public void testSetMail() throws enEquipeException, addCloseException {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		testeur.setMail("MailTest");
		String i = testeur.getMail();
		assertEquals("MailTest",i);
	}

	@Test
	public void testToString() throws enEquipeException, addCloseException {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("testeur", "Dent de plomb", "azerty");
		assertNotNull(testeur.toString());

				
	}

	
	@Test
	public void testGetEquipes() throws enEquipeException, addCloseException {
		
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
