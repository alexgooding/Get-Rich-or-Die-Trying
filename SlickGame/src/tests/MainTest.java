package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import main.Main;

public class MainTest {

	@Test	//Unit testing of the Main Class
	public void test() {
		
		//Creates a test main("ABC")
		Main mainTest = new Main("ABC");
		
		// test the gameNames
		String outputName = mainTest.getTitle();
		assertEquals(outputName, "ABC");
		
	}

}
