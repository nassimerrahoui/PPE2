package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

import metier.Competition;
import metier.Competition.addCloseException;
import metier.Competition.enEquipeException;
import metier.Competition.setDateClotureException;
import metier.Equipe;
import metier.Inscriptions;
import metier.Personne;


public class CompetitionTest {


	@Test
	public void testGetNom() throws enEquipeException, addCloseException {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition test = inscriptions.createCompetition("testCompet", LocalDate.now().plusDays(20), false);	
		assertEquals("testCompet",test.getNom());
	}

	@Test
	public void testSetNom() throws enEquipeException, addCloseException {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition test = inscriptions.createCompetition("testCompet", LocalDate.now().plusDays(20), false);	
		test.setNom("NewName");
		assertEquals("NewName",test.getNom());
	}

	@Test
	public void testInscriptionsOuvertes() throws enEquipeException, addCloseException {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition c = inscriptions.createCompetition("test", LocalDate.now().plusDays(10), true);
		assertTrue(c.inscriptionsOuvertes());
	}
	
	public void testInscriptionsOuvertesFalse() throws enEquipeException, addCloseException {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition c = inscriptions.createCompetition("test", LocalDate.now(),true);
		assertFalse(c.inscriptionsOuvertes());
	}
	@Test
	public void testGetDateCloture() throws enEquipeException, addCloseException {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition test = inscriptions.createCompetition("test", LocalDate.now(), false);	
		assertEquals(LocalDate.now(),test.getDateCloture());
	}

	@Test
	public void testEstEnEquipe() throws enEquipeException, addCloseException {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition test = inscriptions.createCompetition("testCompet", LocalDate.now().plusDays(20), true);	
		assertEquals(true,test.estEnEquipe());
		
	}

	@Test
	public void testSetDateCloture() throws enEquipeException, addCloseException {
		LocalDate test = LocalDate.now().plusDays(10);
		LocalDate test2 = LocalDate.now().plusDays(20);
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition c = inscriptions.createCompetition("test", test, true);
		try{
		c.setDateCloture(test2);
		}
		catch (Exception e){
			
			System.out.println(e);
		}
		assertEquals(test2,c.getDateCloture());
	}

	@Test
	public void testGetCandidats() throws enEquipeException, addCloseException {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition c = inscriptions.createCompetition("test", LocalDate.now().plusDays(10), false);
		Personne testeur = inscriptions.createPersonne("test", "testeur", "azerty");
		Personne testeur2 = inscriptions.createPersonne("test", "testeur", "azerty");
		try {
			c.add(testeur);
			c.add(testeur2);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		assertTrue(c.getCandidats().contains(testeur));
		
	}

	@Test
	public void testAddPersonne() throws enEquipeException, addCloseException {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		LocalDate date = LocalDate.now().plusDays(20);
		Competition c = inscriptions.createCompetition("test", date, false);
		
		Personne p = inscriptions.createPersonne("test", "prenom", "mail");
		Personne pp = inscriptions.createPersonne("test", "prenom", "mail");

		try{
		c.add(p);
		c.add(pp);
		}
		catch (Exception a){
			
			System.out.println(a);
		}
		
		assertTrue(c.getCandidats().contains(p));
		assertTrue(c.getCandidats().contains(pp));
	}

	@Test
	public void testAddEquipe() throws enEquipeException, addCloseException {
		
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
		try{
		c.add(e);
		c.add(ee);
		}
		catch (Exception a){
			
			System.out.println(a);
		}
		assertTrue(c.getCandidats().contains(e));
		assertTrue(c.getCandidats().contains(ee));
	}

	@Test
	public void testRemove() throws enEquipeException, addCloseException {
		Inscriptions i = Inscriptions.getInscriptions();
		LocalDate date = LocalDate.now().plusDays(20);
		Competition c = i.createCompetition("test", date,false);
		Personne p = i.createPersonne("nom", "prenom", "mail");
		Personne pp = i.createPersonne("nom", "prenom", "mail");
		try{
			c.add(p);
			c.add(pp);
		}
		catch (Exception ex){
			
			System.out.println(ex);
		}

		c.remove(p);
		assertTrue(!c.getCandidats().contains(p));
	}

	@Test
	public void testDelete() throws enEquipeException, addCloseException {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition c = inscriptions.createCompetition("testCompet", LocalDate.now().plusDays(20), false);
		int size = inscriptions.getCompetitions().size();
		c.delete();
		assertEquals(size-1,inscriptions.getCompetitions().size());
	}

	@Test
	public void testCompareTo() throws enEquipeException, addCloseException {
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition c = inscriptions.createCompetition("testCompet", LocalDate.now().plusDays(20), false);
		Competition cc = inscriptions.createCompetition("testCompet", LocalDate.now().plusDays(20), false);

		assertEquals(0,c.compareTo(cc));
	}

	@Test
	public void testToString() throws enEquipeException, addCloseException {
		
		Inscriptions inscriptions = Inscriptions.getInscriptions();
		Competition test = inscriptions.createCompetition("testCompet", LocalDate.now().plusDays(20), false);
		assertNotNull(test.toString());
	}
	
	@Test(expected = enEquipeException.class)  
	public void testEnEquipeExceptionPers() throws addCloseException, metier.Competition.enEquipeException
	{
		Inscriptions i = Inscriptions.getInscriptions();
		Competition c = i.createCompetition("CompetTest",LocalDate.now().plusDays(10),true);
		Personne p = i.createPersonne("test", "test", "test");
		c.add(p);
	}

	@Test(expected = addCloseException.class)  
	public void testAddCloseExceptionPers() throws addCloseException, metier.Competition.enEquipeException
	{
		Inscriptions i = Inscriptions.getInscriptions();
		Competition c = i.createCompetition("CompetTest",LocalDate.now().minusDays(10),false);
		Personne p = i.createPersonne("test", "test", "test");
		c.add(p);
	}
	
	@Test(expected = enEquipeException.class)  
	public void testEnEquipeExceptionEqu() throws addCloseException, metier.Competition.enEquipeException
	{
		Inscriptions i = Inscriptions.getInscriptions();
		Competition c = i.createCompetition("CompetTest",LocalDate.now().plusDays(10),false);
		Equipe e = i.createEquipe("testTeam");
		Personne p = i.createPersonne("test", "test", "test");
		e.add(p);
		c.add(e);
	}
	
	@Test(expected = addCloseException.class)  
	public void testAddCloseExceptionEqu() throws addCloseException, metier.Competition.enEquipeException
	{
		Inscriptions i = Inscriptions.getInscriptions();
		Competition c = i.createCompetition("CompetTest",LocalDate.now().minusDays(10),true);
		Equipe e = i.createEquipe("testTeam");
		Personne p = i.createPersonne("test", "test", "test");
		e.add(p);
		c.add(e);
	}
	
	@Test(expected = setDateClotureException.class)
	public void testSetDateClotureException() throws setDateClotureException, enEquipeException, addCloseException
	{
		Inscriptions i = Inscriptions.getInscriptions();
		Competition c = i.createCompetition("CompetTest",LocalDate.now().plusDays(10),true);
		c.setDateCloture(LocalDate.now().minusDays(10));
	}

}
