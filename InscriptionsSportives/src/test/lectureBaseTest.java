package test;

import static org.junit.Assert.*;

import org.junit.Test;

import persistance.lectureBase;

public class lectureBaseTest {

	@Test
	public void testAfficher() 
	{
		assertEquals("Vous �tes connect�",lectureBase.afficher());
	}
}
