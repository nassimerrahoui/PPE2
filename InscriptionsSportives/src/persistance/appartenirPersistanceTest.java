package persistance;

import static org.junit.Assert.*;

import org.junit.Test;

public class appartenirPersistanceTest {

	@Test
	public void testAddMembre() 
	{
		appartenir AP = new appartenir();
		assertEquals("Membre ajouté",AP.addMembre(8, 5));
	}

}
