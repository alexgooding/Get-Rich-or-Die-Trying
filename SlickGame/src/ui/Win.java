package ui;

import java.awt.Font;
import java.io.InputStream;
import java.util.ArrayList;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.util.ResourceLoader;

import main.Main;

public class Win extends BasicGameState {
	Player player;
	// Media
	private static Image player0, player1;
	private static Image[] players;
	private static Animation dancingBoy;
	private static int[] duration;

	// Fonts
	private TrueTypeFont textFont;
	private boolean antiAlias;

	public static int playerScore;
	
	// Leaderboard
	private static ArrayList<Integer> leaderboardScore = new ArrayList<Integer>();

	public Win(int state) {
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// Read the leaderboard
		leaderboardScore = Leaderboard.read("Leaderboard.txt");
		// Retrieve resources
		try {
			player0 = new Image("res/Player0.png");
			player0.setFilter(Image.FILTER_NEAREST);
			player1 = new Image("res/Player1.png");
			player1.setFilter(Image.FILTER_NEAREST);
			players = new Image[]{player0.getScaledCopy(4),player1.getScaledCopy(4)};
			duration = new int[]{300,300};
			dancingBoy = new Animation(players, duration, true);
			dancingBoy.setPingPong(true);
			InputStream dungeonFont = ResourceLoader.getResourceAsStream("res/Dungeon.ttf");
			Font awFont = Font.createFont(Font.TRUETYPE_FONT, dungeonFont);
			awFont = awFont.deriveFont(30f);
			antiAlias = true;
			textFont = new TrueTypeFont(awFont, antiAlias);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// Message on win screen
		dancingBoy.draw((Main.winWidth - dancingBoy.getWidth()) / 2, 100);
		String message1 = "YOU WIN!";
		String message2 = "(Press ENTER to restart)";
		String message3 = "(Press ESC to return to Main Menu)";
		textFont.drawString((Main.halfWidth - textFont.getWidth(message1) / 2), 200, message1);
		textFont.drawString((Main.halfWidth - textFont.getWidth(message2) / 2), 250, message2);
		textFont.drawString((Main.halfWidth - textFont.getWidth(message3) / 2), 300, message3);
		
		// v1.7 Leaderboard
		g.drawString("Player Score: " + playerScore, Main.halfWidth - 100, 350);

		// v1.7 Border
		g.drawRect(Main.halfWidth - 100, 390, 200, 200);
		textFont.drawString((Main.halfWidth - 
				textFont.getWidth("Leaderboard") / 2 ), 400, "Leaderboard");
		g.drawString("1st " + leaderboardScore.get(0), 
				Main.halfWidth - 50, 450);
		g.drawString("2nd " + leaderboardScore.get(1), 
				Main.halfWidth - 50, 500);
		g.drawString("3rd " + leaderboardScore.get(2), 
				Main.halfWidth - 50, 550);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		// Restart the game if player presses Enter
		if (input.isKeyDown(Input.KEY_ENTER)) {
			sbg.init(gc);
			input.clearKeyPressedRecord();
			sbg.enterState(1);
			// Reset the stats
			Game.playerCurrentLevel = 1;
		}
		// Return to main menu if player presses Escape
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			sbg.init(gc);
			input.clearKeyPressedRecord();
			sbg.enterState(0);
		}
	}

	public int getID() {
		return 2;
	}

}
