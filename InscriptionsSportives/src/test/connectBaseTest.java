package test;

import static org.junit.Assert.*;

import org.junit.Test;

import persistance.connectBase;


public class connectBaseTest {

	@Test
	public void testAfficher() 
	{
		connectBase CO = new connectBase();
		assertEquals("Vous �tes connect�",CO.bddConnexion());
	}
}
