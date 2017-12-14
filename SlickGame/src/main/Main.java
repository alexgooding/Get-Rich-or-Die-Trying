package main;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import ui.Game;
import ui.Instructions;
import ui.MainMenu;
import ui.Win;
 

// Version 1.0: Ray: Initial commit

public class Main extends StateBasedGame {
	//Sets the size of the game window
	public static final int winWidth = 1080;
    public static final int winHeight = 720;
    //Get the half size for centering UI elements
    public static final int halfWidth = winWidth / 2;
    public static final int halfHeight = winHeight / 2;
    //Sets the name of the game
    public static final String gameName = "Get Rich or Die Trying";
    //Game states
	public static final int startMenu = 0;
	public static final int instructions = 1;
	public static final int game = 2;
	public static final int win = 3;
	
	public Main(String gameName) {
		super(gameName);
		this.addState(new MainMenu(startMenu));
		this.addState(new Game(game));
		this.addState(new Win(win));
		this.addState(new Instructions(instructions));
	}
	
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(startMenu).init(gc, this);
		this.getState(game).init(gc, this);
		this.getState(instructions).init(gc, this);
		this.getState(win).init(gc, this);
		this.enterState(startMenu);	// Starting page / screen
	}
	
	public static void main(String[] args) {
		AppGameContainer appGame;
		try {
			appGame = new AppGameContainer(new Main(gameName));
			appGame.setShowFPS(false);
			appGame.setDisplayMode(winWidth, winHeight, false);	// Window resolution with no fullscreen mode
			appGame.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
