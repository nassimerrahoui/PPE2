package test;

import static org.junit.Assert.*;

import org.junit.Test;

import persistance.appartenir;

public class appartenirPersistanceTest {

	@Test
	public void testAddMembre() 
	{
		appartenir AP = new appartenir();
		assertEquals("Membre ajout�",AP.addMembre(8, 5));
	}

}
