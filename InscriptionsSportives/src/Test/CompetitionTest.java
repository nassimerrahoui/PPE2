package Test;

import static org.junit.Assert.*;

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
		fail("Not yet implemented");
	}

	@Test
	public void testGetDateCloture() {
		fail("Not yet implemented");
	}

	@Test
	public void testEstEnEquipe() {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition test = inscriptions.createCompetition("test", null, true);	
		assertEquals(true,test.estEnEquipe());
		
	}

	@Test
	public void testSetDateCloture() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCandidats() {
		fail("Not yet implemented");
		/*Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition c = inscriptions.createCompetition("test", null, true);
		Personne p = inscriptions.createPersonne("test", "testeur", "mail.com");
		Personne pp = inscriptions.createPersonne("test", "testeur", "mail.com");
		c.add(p);
		c.add(pp);
		assertEquals(1,c.getCandidats().size());*/
	}

	@Test
	public void testAddPersonne() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition c = inscriptions.createCompetition("test", null, true);
		Personne p = inscriptions.createPersonne("test", "testeur", "mail.com");
		int size = c.getCandidats().size();
		c.add(p);
		assertEquals(size+1,c.getCandidats().size());
	}

	@Test
	public void testAddEquipe() {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition c = inscriptions.createCompetition("test", null, true);
		Equipe e = inscriptions.createEquipe("tessst");
		Personne p = inscriptions.createPersonne("test", "testeur", "mail.com");
		e.add(p);
		c.add(e);
		int size = c.getCandidats().size();
		c.add(p);
		assertEquals(size+1,c.getCandidats().size());
	}

	@Test
	public void testRemove() {
		fail("Not yet implemented");
		/*Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition c = inscriptions.createCompetition("test", null, true);
		Personne p = inscriptions.createPersonne("test", "testeur", "mail.com");
		Personne pp = inscriptions.createPersonne("test", "testeur", "mail.com");
		c.add(p);
		c.add(pp);
		int before = c.getCandidats().size();
		c.remove(pp);
		int after = c.getCandidats().size();
		assertEquals(before-1,after);*/
		
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
