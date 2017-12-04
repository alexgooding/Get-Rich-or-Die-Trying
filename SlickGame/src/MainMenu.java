import java.awt.Font;
import java.io.InputStream;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.util.ResourceLoader;

//version v1.0: Ray: Initial Create

public class MainMenu extends BasicGameState {
	// Font variables
	private TrueTypeFont titleFont;
	private boolean antiAlias = true;
	
	public MainMenu(int state) {
		
	}
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		try {
			// Set font of game title
			InputStream dungeonFont	= ResourceLoader.getResourceAsStream("res/Dungeon.ttf");
			Font awFont = Font.createFont(Font.TRUETYPE_FONT, dungeonFont);
			awFont = awFont.deriveFont(72f);
			titleFont = new TrueTypeFont(awFont, antiAlias);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.translate(-520,-320);
		// Set title on the screen
		float titleWidth = titleFont.getWidth(Main.gameName);
		titleFont.drawString((Main.winWidth - titleWidth) / 2, 200, Main.gameName, Color.white);
		
		// Set buttons on the screen
		Image startGameButton = new Image("res/StartGame.png");
		Image quitButton = new Image("res/Quit.png");
		float buttonCenter = (Main.winWidth - 300) / 2;
		startGameButton.draw(buttonCenter, Main.halfHeight + 20);
		quitButton.draw(buttonCenter, Main.halfHeight + 170);
		g.resetTransform();
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		int mousePosX = input.getMouseX();
		// Y position usually starts from bottom but this starts from the top
		int mousePosY = input.getMouseY();
		// Get the coordinates for the centering of buttons for accuracy of mouse clicks
		float buttonCenter = (Main.winWidth - 300) / 2;
		System.out.println("X: " + mousePosX + " Y: " + mousePosY);
		// If Start Game button is pressed, start the game.
		if((mousePosX > buttonCenter) && (mousePosX < buttonCenter +  300) && (mousePosY >  Main.halfHeight + 20 && mousePosY < Main.halfHeight + 500)) {
			if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				sbg.enterState(1);
			}
		}
		// If Quit button is pressed, the window is closed
		if((mousePosX > buttonCenter && mousePosX < buttonCenter + 300) && (mousePosY > Main.halfHeight + 170 && mousePosY < 650)) {
			if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				System.exit(0);
			}
		}
		// Enters the game if Enter key is pressed
		if (input.isKeyDown(Input.KEY_ENTER)) {
			sbg.enterState(1);
		}
	}
	
	public int getID() {
		return 0;
	}
}