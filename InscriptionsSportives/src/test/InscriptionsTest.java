package test;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import metier.Competition;
import metier.Equipe;
import metier.Inscriptions;
import metier.Personne;

public class InscriptionsTest {

	@Test
	public void testGetCompetitions() {
		Inscriptions i = Inscriptions.getInscriptions();
		Competition c = i.createCompetition("tes", LocalDate.now().plusDays(10), true);
		Competition cc = i.createCompetition("tes", LocalDate.now().plusDays(10), true);
		int size = i.getCompetitions().size();
		assertTrue(i.getCompetitions().contains(c));
		assertTrue(i.getCompetitions().contains(cc));
		assertEquals(size, i.getCompetitions().size());
	}

	@Test
	public void testGetCandidats() {
		
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
	public void testGetPersonnes() {
		Inscriptions i = Inscriptions.getInscriptions();
		Personne p = i.createPersonne("test","test", "test");
		Personne pp = i.createPersonne("test", "test", "test");
		int size = i.getPersonnes().size();
		assertTrue(i.getPersonnes().contains(p));
		assertTrue(i.getPersonnes().contains(pp));
		assertEquals(size, i.getPersonnes().size());
			
	}

	@Test
	public void testGetEquipes() {
		Inscriptions i = Inscriptions.getInscriptions();
		Equipe e = i.createEquipe("testTeam");
		Equipe ee = i.createEquipe("TestTeam2");
		int size = i.getEquipes().size();
		assertTrue(i.getEquipes().contains(e));
		assertTrue(i.getEquipes().contains(ee));
		assertEquals(size,i.getEquipes().size());


	}

	@Test
	public void testCreateCompetition() {
		
		Inscriptions i = Inscriptions.getInscriptions();
		int before = i.getCompetitions().size();
		Competition c = i.createCompetition("test", LocalDate.now().plusDays(10), true);
		int after = i.getCompetitions().size();
		assertTrue(i.getCompetitions().contains(c));
		assertEquals(before+1,after);


	}

	@Test
	public void testCreatePersonne() {
		
		Inscriptions i = Inscriptions.getInscriptions();
		Personne p = i.createPersonne("test","test", "test");
		assertTrue(i.getCandidats().contains(p));


	}

	@Test
	public void testCreateEquipe() {
		
		Inscriptions i = Inscriptions.getInscriptions();
		Equipe e = i.createEquipe("test");
		assertTrue(i.getCandidats().contains(e));
		
	}

	

	@Test
	public void testGetInscriptions() {
		Inscriptions i = Inscriptions.getInscriptions();
		assertNotNull(i);
	}



	@Test
	public void testToString() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		assertNotNull(inscriptions.toString());
	}


}
