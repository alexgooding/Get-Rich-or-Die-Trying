import java.awt.Font;
import java.io.InputStream;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
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
		// Set title on the screen
		float titleWidth = titleFont.getWidth(Main.gameName);
		titleFont.drawString((Main.winWidth - titleWidth) / 2, 200, Main.gameName, Color.white);
		
		// Set buttons on the screen
		Image startGameButton = new Image("res/StartGame.png");
		Image quitButton = new Image("res/Quit.png");
		float buttonCenter = (Main.winWidth - 300) / 2;
		startGameButton.draw(buttonCenter, Main.halfHeight + 20);
		quitButton.draw(buttonCenter, Main.halfHeight + 170);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		int mousePosX = Mouse.getX();
		// Y position usually starts from bottom but this starts from the top
		int mousePosY = (Main.winHeight - Mouse.getY());
		// Get the coordinates for the centering of buttons for accuracy of mouse clicks
		float buttonCenter = (Main.winWidth - 300) / 2;
		System.out.println("X: " + mousePosX + " Y: " + mousePosY);
		// If Start Game button is pressed, start the game.
		if((mousePosX > buttonCenter) && (mousePosX < buttonCenter +  300) && (mousePosY >  Main.halfHeight + 20 && mousePosY < Main.halfHeight + 500)) {
			if(Mouse.isButtonDown(0)) {
				sbg.enterState(1);
			}
		}
		// If Quit button is pressed, the window is closed.
		if((mousePosX > buttonCenter && mousePosX < buttonCenter + 300) && (mousePosY > Main.halfHeight + 170 && mousePosY < 650)) {
			if(Mouse.isButtonDown(0)) {
				System.exit(0);
			}
		}
	}
	
	public int getID() {
		return 0;
	}
}