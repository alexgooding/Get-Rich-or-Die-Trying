package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BotTest.class, DoorTest.class, GoldTest.class, ItemTest.class, LeaderboardTest.class,
		LocationTest.class, MainTest.class, RoomTest.class })
public class AllTests {

}
