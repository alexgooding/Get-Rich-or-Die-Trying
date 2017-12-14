package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.Test;

import ui.Leaderboard;

public class LeaderboardTest {

	@Test
	public void test() {
		
		String path = "LeaderBoardTest.txt";
		Leaderboard.write(path, 1000, 100, 10);
		ArrayList<Integer> testResult = new ArrayList<Integer>();
		
		// Test if file exist
		File file = new File(path);
		assertTrue(file.exists());
		
		// Test if data correct
		for(int i=0; i<Leaderboard.read(path).size(); i++) {
			testResult.add(Leaderboard.read(path).get(i));
		}
		
		assertEquals(testResult.get(0).intValue(), 1000);
		assertEquals(testResult.get(1).intValue(), 100);
		assertEquals(testResult.get(2).intValue(), 10);
		
	}

}
