package test;

import static org.junit.Assert.*;


import org.junit.Test;

import metier.Equipe;
import metier.Inscriptions;
import metier.Personne;

public class EquipeTest {

	@Test
	public void testDelete() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		Equipe e = inscriptions.createEquipe("test");
		e.add(testeur);
		
		assertTrue(inscriptions.getPersonnes().contains(testeur));
		
		testeur.delete();
		assertTrue(!e.getMembres().contains(testeur));
		assertTrue(!inscriptions.getPersonnes().contains(testeur));
		assertTrue(!inscriptions.getCandidats().contains(testeur));
		
	}

	@Test
	public void testToString() {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("testeur", "Dent de plomb", "azerty");
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
		assertTrue(e.getMembres().contains(testeur));
		assertTrue(e.getMembres().contains(testeur2));
		
	
		assertEquals(size,e.getMembres().size());
		
	}

	@Test
	public void testAddPersonne() {

		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		Personne testeur2 = inscriptions.createPersonne("test2", "testeur2", "azerty2");
		Equipe e = inscriptions.createEquipe("test");
		e.add(testeur);
		e.add(testeur2);
		assertTrue(e.getMembres().contains(testeur2));
		assertTrue(e.getMembres().contains(testeur));
		
	}

	@Test
	public void testRemovePersonne() {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		Equipe e = inscriptions.createEquipe("test");
		e.add(testeur);
		
		
		assertTrue(e.getMembres().contains(testeur));
		testeur.delete();
		assertTrue(!e.getMembres().contains(testeur));
	}

}
