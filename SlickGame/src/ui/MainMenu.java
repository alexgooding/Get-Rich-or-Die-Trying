package ui;
import java.awt.Font;
import java.io.InputStream;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.util.ResourceLoader;
import org.newdawn.slick.Music;	// v1.1
import main.Main;

// Version 1.0: Ray: Initial commit
// Version 1.1: Ray and David: Added Music
public class MainMenu extends BasicGameState {
	// Font variables
	private TrueTypeFont titleFont;
	private boolean antiAlias = true;
	
	// Media
	Image startGameButton, quitButton, htpButton;
	Music music; // v1.1

	public MainMenu(int state) {
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		try {
			// v1.1 Load music file
			music = new Music("res/ThemeMusic.ogg");
			music.loop();
			// Load font
			InputStream dungeonFont	= ResourceLoader.getResourceAsStream("res/Dungeon.ttf");
			Font awFont = Font.createFont(Font.TRUETYPE_FONT, dungeonFont);
			awFont = awFont.deriveFont(70f);
			titleFont = new TrueTypeFont(awFont, antiAlias);
			// Load image files
			startGameButton = new Image("res/StartGame.png");
			quitButton = new Image("res/Quit.png");
			htpButton = new Image("res/HowToPlay.png");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// Set game title on the screen
		float titleWidth = titleFont.getWidth(Main.gameName);
		titleFont.drawString((Main.winWidth - titleWidth) / 2, 100, Main.gameName, Color.white);

		// Set buttons on the screen
		float buttonCenter = (Main.winWidth - 300) / 2;
		startGameButton.draw(buttonCenter, 250);
		htpButton.draw(buttonCenter, 350);
		quitButton.draw(buttonCenter, 450);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// Listen for user input
		Input input = gc.getInput();
		int mousePosX = input.getMouseX();
		int mousePosY = input.getMouseY();

		// Get the coordinates for the centering of buttons for accuracy of mouse clicks
		float buttonCenter = (Main.winWidth - 300) / 2;
		// System.out.println("X: " + mousePosX + " Y: " + mousePosY);
		
		// If Start Game button is pressed, start the game
		if ((mousePosX > buttonCenter) && (mousePosX < buttonCenter +  300) 
				&& (mousePosY >  250 && mousePosY < 300)) {
			if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				sbg.enterState(2);
			}
		}
		// If How To Play button is pressed, show instructions
		if ((mousePosX > buttonCenter) && (mousePosX < buttonCenter + 300)
				&& (mousePosY > 350 && mousePosY < 400)) {
				if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
					sbg.enterState(1);
				}
			}
		// If Quit button is pressed, the game window is closed
		if ((mousePosX > buttonCenter && mousePosX < buttonCenter + 300) 
				&& (mousePosY > 450 && mousePosY < 500)) {
			if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				System.exit(0);
			}
		}
		// Enters the game if Enter key on keyboard is pressed
		if (input.isKeyDown(Input.KEY_ENTER)) {
			input.clearKeyPressedRecord();
			sbg.enterState(2);
		}
	}

	public int getID() {
		return 0;
	}

}