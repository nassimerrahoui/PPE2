package Test;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import inscriptions.Competition;
import inscriptions.Equipe;
import inscriptions.Inscriptions;
import inscriptions.Personne;

public class CompetitionTest {


	@Test
	public void testGetNom() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition test = inscriptions.createCompetition("test", null, false);	
		assertEquals("test",test.getNom());
	}

	@Test
	public void testSetNom() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition test = inscriptions.createCompetition("test", null, false);	
		test.setNom("NewName");
		assertEquals("NewName",test.getNom());
	}

	@Test
	public void testInscriptionsOuvertes() {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition c = inscriptions.createCompetition("test", LocalDate.now().plusDays(10), true);
		boolean test = c.inscriptionsOuvertes();
		assertEquals(test,c.inscriptionsOuvertes());
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
		Competition test = inscriptions.createCompetition("test", null, true);	
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
		
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		Personne testeur2 = inscriptions.createPersonne("test", "testeur", "azerty");
		Competition CompetTest = inscriptions.createCompetition("Mondial de test", null, false);
		CompetTest.add(testeur);
		CompetTest.add(testeur2);
		
		assertTrue(CompetTest.getCandidats().contains(testeur));
		assertTrue(CompetTest.getCandidats().contains(testeur2));
		int size = CompetTest.getCandidats().size();

		assertEquals(size,CompetTest.getCandidats().size());
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
		Competition c = inscriptions.createCompetition("test", null, false);
		int size = inscriptions.getCompetitions().size();
		c.delete();
		assertEquals(size-1,inscriptions.getCompetitions().size());
	}

	@Test
	public void testCompareTo() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition c = inscriptions.createCompetition("test", null, false);
		Competition cc = inscriptions.createCompetition("test", null, false);

		assertEquals(0,c.compareTo(cc));
	}

	@Test
	public void testToString() {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition test = inscriptions.createCompetition("Mondial de flechettes", null, false);
		assertNotNull(test.toString());
	}

}
