package ui;

import java.awt.Font;
import java.io.InputStream;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.util.ResourceLoader;

import main.Main;

public class Win extends BasicGameState {
	// Media
	private static Image player0, player1;
	private static Image[] players;
	private static Animation dancingBoy;
	private static int[] duration;
	// Fonts
	private TrueTypeFont textFont;
	private boolean antiAlias = true;

	public Win(int state) {
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
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
			textFont = new TrueTypeFont(awFont, antiAlias);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		dancingBoy.draw((Main.winWidth - dancingBoy.getWidth()) / 2, 150);
		String message1 = "YOU WIN!";
		String message2 = "(Press ENTER to restart)";
		String message3 = "(Press ESC to return to Main Menu)";
		textFont.drawString((Main.halfWidth - textFont.getWidth(message1) / 2), 250, message1);
		textFont.drawString((Main.halfWidth - textFont.getWidth(message2) / 2), 350, message2);
		textFont.drawString((Main.halfWidth - textFont.getWidth(message3) / 2), 400, message3);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		// Restart the game if player presses Enter
		if (input.isKeyDown(Input.KEY_ENTER)) {
			input.clearKeyPressedRecord();
			sbg.enterState(1);
		}
		// Return to main menu if player presses Escape
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			input.clearKeyPressedRecord();
			sbg.enterState(0);
		}
	}

	public int getID() {
		return 2;
	}

}
