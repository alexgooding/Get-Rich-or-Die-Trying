package tests;
import static org.junit.Assert.*;

import org.junit.Test;

import dungeon.Location;
import dungeon.Room;

public class RoomTest {

	@Test
	public void test() {
		
		//creates a new test location to insert into the room
		Location locTest = new Location(10,10,1);
		
		//creates a test room
		Room roomTest = new Room(4,locTest, 3, 1, true);
				
		//testing room size, number of doors, room difficulty and bot presence
		int outputSize = roomTest.getRoomSize();
		int outputDoors = roomTest.getNumberOfDoors();
		int outputDifficulty = roomTest.getRoomDifficulty();
		boolean outputBot = roomTest.getBotFlag();
		
		//Checks the values are correct and passes the test
		assertEquals(4, outputSize);
		assertEquals(3, outputDoors);
		assertEquals(1, outputDifficulty);
		assertEquals(true, outputBot);
				
	}
			
}
