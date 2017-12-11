package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import dungeon.Bot;
import dungeon.Location;

public class BotTest {

	@Test
	public void test() {
		
		// Testing constructor
		Bot botTest = new Bot();
		assertEquals(botTest.getBotLocation().getX(),0);
		assertEquals(botTest.getBotLocation().getY(),0);
		assertEquals(botTest.getBotLocation().getRoomNumber(),1);
		
		Location locTest = new Location(10,10,1);
		
		// Testing setBot function
		botTest.setBot(locTest);
		assertEquals(botTest.getBotLocation().getX(),10);
		assertEquals(botTest.getBotLocation().getY(),10);
		assertEquals(botTest.getBotLocation().getRoomNumber(),1);
		
		botTest.updateBotLocation('n');
		assertEquals(botTest.getBotLocation().getY(),11);
	
		botTest.updateBotLocation('e');
		assertEquals(botTest.getBotLocation().getX(),11);
		
		botTest.setBot(new Location(10,10,1));
		botTest.updateBotLocation('s');
		assertEquals(botTest.getBotLocation().getY(),9);
		
		botTest.updateBotLocation('w');
		assertEquals(botTest.getBotLocation().getY(),9);
		
	}

}

