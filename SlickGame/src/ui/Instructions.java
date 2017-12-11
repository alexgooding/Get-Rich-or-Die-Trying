package ui;
import java.awt.Font;
import java.io.InputStream;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.Music;

import main.Main;

// Version 1.0: Kimberley: Initial commit
public class Instructions extends BasicGameState {
	// Font variables
	private TrueTypeFont headingFont;
	private boolean antiAlias = true;

	// Media
	Image instructions;
	Music music;

	public Instructions(int state) {
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		try {
			// Load music
			music = new Music("res/ThemeMusic.ogg");
			music.loop();
			// Load font
			InputStream dungeonFont	= ResourceLoader.getResourceAsStream("res/Dungeon.ttf");
			Font awFont = Font.createFont(Font.TRUETYPE_FONT, dungeonFont);
			awFont = awFont.deriveFont(36f);
			headingFont = new TrueTypeFont(awFont, antiAlias);
			instructions = new Image("res/Instructions.png");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// Set game title on the screen
		String heading = "Instructions";
		String text = 
				"Controls: Use the arrow keys to move up, down, left, and right. \n"
				+ "Objective: Collect " + Game.getGOLDREQUIREMENT() + 
				" golds in each of " + Game.getLEVELREQUIREMENT() + " levels. \n";
		headingFont.drawString(headingFont.getWidth(heading), 100, heading, Color.white);
		g.drawString(text, 50, 150);
		instructions.draw((Main.winWidth - instructions.getWidth()) / 2, 300);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// Listen for user input
		Input input = gc.getInput();
		
	}

	public int getID() {
		return 1;
	}

}