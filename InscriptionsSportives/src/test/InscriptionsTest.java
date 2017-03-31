package test;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import metier.Competition;
import metier.Competition.addCloseException;
import metier.Competition.enEquipeException;
import metier.Equipe;
import metier.Inscriptions;
import metier.Personne;

public class InscriptionsTest {

	@Test
	public void testGetCompetitions() throws enEquipeException, addCloseException {
		Inscriptions i = Inscriptions.getInscriptions();
		Competition c = i.createCompetition("tes", LocalDate.now().plusDays(10), true);
		Competition cc = i.createCompetition("tes", LocalDate.now().plusDays(10), true);
		int size = i.getCompetitions().size();
		assertTrue(i.getCompetitions().contains(c));
		assertTrue(i.getCompetitions().contains(cc));
		assertEquals(size, i.getCompetitions().size());
	}

	@Test
	public void testGetCandidats() throws enEquipeException, addCloseException {
		
		Inscriptions i = Inscriptions.getInscriptions();
		Personne p = i.createPersonne("test", "test", "test");
		Personne pp = i.createPersonne("test", "test", "test");
		Personne ppp = i.createPersonne("test", "test", "test");
		Equipe e = i.createEquipe("testTeam");
		e.add(pp);
		e.add(ppp);
		
		int size = i.getCandidats().size();
		assertTrue(i.getCandidats().contains(p));
		assertTrue(i.getCandidats().contains(e));
		assertEquals(size,i.getCandidats().size());
		
	}

	@Test
	public void testGetPersonnes() throws enEquipeException, addCloseException {
		Inscriptions i = Inscriptions.getInscriptions();
		Personne p = i.createPersonne("test","test", "test");
		Personne pp = i.createPersonne("test", "test", "test");
		int size = i.getPersonnes().size();
		assertTrue(i.getPersonnes().contains(p));
		assertTrue(i.getPersonnes().contains(pp));
		assertEquals(size, i.getPersonnes().size());
			
	}

	@Test
	public void testGetEquipes() throws enEquipeException, addCloseException {
		Inscriptions i = Inscriptions.getInscriptions();
		Equipe e = i.createEquipe("testTeam");
		Equipe ee = i.createEquipe("TestTeam2");
		int size = i.getEquipes().size();
		assertTrue(i.getEquipes().contains(e));
		assertTrue(i.getEquipes().contains(ee));
		assertEquals(size,i.getEquipes().size());


	}

	@Test
	public void testCreateCompetition() throws enEquipeException, addCloseException {
		
		Inscriptions i = Inscriptions.getInscriptions();
		Competition c = i.createCompetition("test", LocalDate.now().plusDays(10), true);
		assertTrue(i.getCompetitions().contains(c));
	}

	@Test
	public void testCreatePersonne() throws enEquipeException, addCloseException {
		
		Inscriptions i = Inscriptions.getInscriptions();
		Personne p = i.createPersonne("test","test", "test");
		assertTrue(i.getCandidats().contains(p));


	}

	@Test
	public void testCreateEquipe() throws enEquipeException, addCloseException {
		
		Inscriptions i = Inscriptions.getInscriptions();
		Equipe e = i.createEquipe("test");
		assertTrue(i.getCandidats().contains(e));
		
	}

	

	@Test
	public void testGetInscriptions() throws enEquipeException, addCloseException {
		Inscriptions i = Inscriptions.getInscriptions();
		assertNotNull(i);
	}



	@Test
	public void testToString() throws enEquipeException, addCloseException {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		assertNotNull(inscriptions.toString());
	}


}
