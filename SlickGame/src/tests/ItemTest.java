package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import items.Gold;
import items.Item;

public class ItemTest {

	@Test
	public void test() {
		// Testing constructor
		Item itemTest = new Item();
		assertEquals(itemTest.getItemLocation().getX(),0);
		assertEquals(itemTest.getItemLocation().getY(),0);
		assertEquals(itemTest.getItemLocation().getRoomNumber(),1);
	}

}
