package test;

import static org.junit.Assert.*;


import org.junit.Test;

import metier.Competition.addCloseException;
import metier.Competition.enEquipeException;
import metier.Equipe;
import metier.Inscriptions;
import metier.Personne;

public class EquipeTest {

	@Test
	public void testDelete() throws enEquipeException, addCloseException {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		Equipe e = inscriptions.createEquipe("test");
		e.add(testeur);
		assertTrue(e.getMembres().contains(testeur));
		e.delete();
		assertTrue(!e.getMembres().contains(testeur));
		assertTrue(!inscriptions.getPersonnes().contains(testeur));
		assertTrue(!inscriptions.getCandidats().contains(testeur));
		
	}

	@Test
	public void testToString() throws enEquipeException, addCloseException {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("testeur", "Dent de plomb", "azerty");
		Equipe TestTeam = inscriptions.createEquipe("TestTeam");
		TestTeam.add(testeur);
		assertNotNull(testeur.toString());
	}


	@Test
	public void testGetMembres() throws enEquipeException, addCloseException {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		Personne testeur2 = inscriptions.createPersonne("test", "testeur", "azerty");
		Equipe e = inscriptions.createEquipe("test");
		e.add(testeur);
		e.add(testeur2);
		int size = e.getMembres().size();
		assertTrue(e.getMembres().contains(testeur));
		assertTrue(e.getMembres().contains(testeur2));
		
	
		assertEquals(size,e.getMembres().size());
		
	}

	@Test
	public void testAddPersonne() throws enEquipeException, addCloseException {

		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("testeur", "testeur", "azerty");
		Personne testeur2 = inscriptions.createPersonne("testeur2", "testeur2", "azerty2");
		Equipe e = inscriptions.createEquipe("test");
		e.add(testeur);
		e.add(testeur2);
		assertTrue(e.getMembres().contains(testeur2));
		assertTrue(e.getMembres().contains(testeur));
		assertTrue(testeur.getEquipes().contains(e));
		assertTrue(testeur2.getEquipes().contains(e));
	}

	@Test
	public void testRemovePersonne() throws enEquipeException, addCloseException {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		Equipe e = inscriptions.createEquipe("test");
		e.add(testeur);
		assertTrue(e.getMembres().contains(testeur));
		e.delete();
		assertTrue(!e.getMembres().contains(testeur));
	}

}
