package ui;
import java.awt.Font;
import java.io.InputStream;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.util.ResourceLoader;

import main.Main;

// Version 1.0: Kimberley: Initial commit
public class Instructions extends BasicGameState {
	// Font variables
	private TrueTypeFont headingFont;
	private boolean antiAlias = true;

	// Media
	Image instructions, back;
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
			back = new Image("res/Back.png");
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
				" golds in each of " + Game.getLEVELREQUIREMENT() + " levels. \n"
						+ "Quit: You can press Escape to quit the game!";
		headingFont.drawString(50, 70, heading, Color.white);
		// Instructions
		g.drawString(text, 50, 120);
		// Graphical instruction
		instructions.draw((Main.winWidth - instructions.getWidth()) / 2, 200);
		// Back to main menu button
		back.draw((Main.winWidth - back.getWidth()) / 2, 620);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// Listen for user input
		Input input = gc.getInput();
		int mousePosX = input.getMouseX();
		int mousePosY = input.getMouseY();
		
		// If Back to Main Menu button is pressed, return to main menu
		if ((mousePosX > (Main.winWidth - back.getWidth()) / 2 && mousePosX < (Main.winWidth - back.getWidth()) / 2 + 300) 
				&& (mousePosY > 620 && mousePosY < 670)) {
			if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				sbg.enterState(0);
			}
		}
	}

	public int getID() {
		return 1;
	}

}