package test;

import static org.junit.Assert.*;

import org.junit.Test;

import persistance.lectureBase;

public class lectureBaseTest {

	@Test
	public void testGetCandidatCarac() {
		lectureBase LB = new lectureBase();
		String Test = "TeamR";
		assertEquals(Test,LB.getCandidatCarac(8));
	}
}
