package tests;
import static org.junit.Assert.*;
import org.junit.Test;

import dungeon.Location;

public class LocationTest {

	@Test // Unit testing of the Location class.
	public void test() {
		
		
		//creates a test location (x, y, roomNumber)
		Location test = new Location(45,55,3);
		
		//testing original location
		int outputX = test.getX();
		int outputY = test.getY();
		int outputRoomNo = test.getRoomNumber();
		
		//tests the location can move north
		int outputNorth = (test.getY() + 1);
		
		//tests the location can move east
		int outputEast = (test.getX()+1);
		
		//tests the location can move south
		int outputSouth = (test.getY()-1);
		
		//tests the location can move west 
		int outputWest = (test.getX()-1);
		
		//Checks the original location is correct and pasts the test
		assertEquals(45, outputX);
		assertEquals(55, outputY);
		assertEquals(3, outputRoomNo);	
		
		//Checks the new location has been successfully added and then passes the test
		assertEquals(56, outputNorth);
		assertEquals(46, outputEast);
		assertEquals(54, outputSouth);
		assertEquals(44, outputWest);
				
		;	
	}
		
}
