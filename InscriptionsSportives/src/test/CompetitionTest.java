package test;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import metier.Competition;
import metier.Equipe;
import metier.Inscriptions;
import metier.Personne;

public class CompetitionTest {


	@Test
	public void testGetNom() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition test = inscriptions.createCompetition("testCompet", LocalDate.now().plusDays(20), false);	
		assertEquals("testCompet",test.getNom());
	}

	@Test
	public void testSetNom() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition test = inscriptions.createCompetition("testCompet", LocalDate.now().plusDays(20), false);	
		test.setNom("NewName");
		assertEquals("NewName",test.getNom());
	}

	@Test
	public void testInscriptionsOuvertes() {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition c = inscriptions.createCompetition("test", LocalDate.now().plusDays(10), true);
		assertTrue(c.inscriptionsOuvertes());
	}
	
	public void testInscriptionsOuvertesFalse() {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition c = inscriptions.createCompetition("test", LocalDate.now(),true);
		assertFalse(c.inscriptionsOuvertes());
	}
	@Test
	public void testGetDateCloture() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition test = inscriptions.createCompetition("test", LocalDate.now(), false);	
		assertEquals(LocalDate.now(),test.getDateCloture());
	}

	@Test
	public void testEstEnEquipe() {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition test = inscriptions.createCompetition("testCompet", LocalDate.now().plusDays(20), true);	
		assertEquals(true,test.estEnEquipe());
		
	}

	@Test
	public void testSetDateCloture() {
		LocalDate test = LocalDate.now().plusDays(10);
		LocalDate test2 = LocalDate.now().plusDays(20);
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition c = inscriptions.createCompetition("test", test, true);
		c.setDateCloture(test2);
		assertEquals(test2,c.getDateCloture());
	}

	@Test
	public void testGetCandidats() {
		

		Inscriptions i = Inscriptions.getInscriptions();
		Competition c = i.createCompetition("testCompet", LocalDate.now().plusDays(20), false);
		Personne p = i.createPersonne("test", "test", "test");
		Personne pp = i.createPersonne("test", "test", "test");
		c.add(p);
		c.add(pp);
		assertTrue(c.getCandidats().contains(p));
		assertTrue(c.getCandidats().contains(pp));
		
		
		
	}

	@Test
	public void testAddPersonne() {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		LocalDate date = LocalDate.now().plusDays(20);
		Competition c = inscriptions.createCompetition("test", date, false);
		Personne p = inscriptions.createPersonne("test", "prenom", "mail");
		int sizeBefore = c.getCandidats().size();
		c.add(p);
		int sizeAfter = c.getCandidats().size();
		assertTrue(c.getCandidats().contains(p));
		assertEquals(sizeBefore+1,sizeAfter);
		
	}

	@Test
	public void testAddEquipe() {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		LocalDate date = LocalDate.now().plusDays(20);
		Competition c = inscriptions.createCompetition("test", date, true);
		
		Personne p = inscriptions.createPersonne("test", "prenom", "mail");
		Personne pp = inscriptions.createPersonne("test", "prenom", "mail");
		Personne ppp = inscriptions.createPersonne("test", "prenom", "mail");
		Equipe e = inscriptions.createEquipe("testTeam");
		Equipe ee = inscriptions.createEquipe("testTeam");

		e.add(p);
		e.add(pp);
		ee.add(ppp);
		
		c.add(e);
		c.add(ee);
		int sizeBefore = inscriptions.getCandidats().size();
		Equipe eee = inscriptions.createEquipe("testTeam2");
		Personne test = inscriptions.createPersonne("test", "test", "test@mail");
		eee.add(test);
		c.add(eee);
		assertTrue(inscriptions.getCandidats().contains(eee));
		int sizeAfter = inscriptions.getCandidats().size();
		assertEquals(sizeBefore+1,sizeAfter);

	}

	@Test
	public void testRemove() {
		Inscriptions i = Inscriptions.getInscriptions();
		LocalDate date = LocalDate.now().plusDays(20);
		Competition c = i.createCompetition("test", date,false);
		Personne p = i.createPersonne("nom", "prenom", "mail");
		Personne pp = i.createPersonne("nom", "prenom", "mail");
		c.add(p);
		c.add(pp);
		int sizeBefore = c.getCandidats().size();
		c.remove(p);
		int sizeAfter = c.getCandidats().size();
		assertEquals(sizeBefore-1,sizeAfter);
	}

	@Test
	public void testDelete() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition c = inscriptions.createCompetition("testCompet", LocalDate.now().plusDays(20), false);
		int size = inscriptions.getCompetitions().size();
		c.delete();
		assertEquals(size-1,inscriptions.getCompetitions().size());
	}

	@Test
	public void testCompareTo() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition c = inscriptions.createCompetition("testCompet", LocalDate.now().plusDays(20), false);
		Competition cc = inscriptions.createCompetition("testCompet", LocalDate.now().plusDays(20), false);

		assertEquals(0,c.compareTo(cc));
	}

	@Test
	public void testToString() {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition test = inscriptions.createCompetition("testCompet", LocalDate.now().plusDays(20), false);
		assertNotNull(test.toString());
	}
	
	@Test
	public void testExceptionAddPers()
	{
		LocalDate date = LocalDate.of(2015, 12, 12);
		Inscriptions i = Inscriptions.getInscriptions();
		Competition t = i.createCompetition("test", date, true);
		Personne p = i.createPersonne("test", "test", "test");

	}

}
