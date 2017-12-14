package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import dungeon.Bot;
import dungeon.Door;
import dungeon.Location;

public class DoorTest {

	@Test
	public void test() {
		// Testing constructor
		Door doorTest = new Door();
		assertEquals(doorTest.getDoorLocation().getX(),0);
		assertEquals(doorTest.getDoorLocation().getY(),0);
		assertEquals(doorTest.getDoorLocation().getRoomNumber(),1);
		assertEquals(doorTest.getDirection(),'n');
		

	}

}
