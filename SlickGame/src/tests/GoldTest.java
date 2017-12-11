package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import dungeon.Bot;
import items.Gold;

public class GoldTest {

	@Test
	public void test() {
		// Testing constructor
		Gold goldTest = new Gold();
		assertEquals(goldTest.getItemLocation().getX(),0);
		assertEquals(goldTest.getItemLocation().getY(),0);
		assertEquals(goldTest.getItemLocation().getRoomNumber(),1);
		assertEquals(goldTest.getGoldAmount(), 1);
	}

}
