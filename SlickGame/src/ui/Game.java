package ui;
import java.awt.Font;
import java.io.InputStream;
import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

import main.Main;
import dungeon.Dungeon;
import dungeon.Location;
import dungeon.Room;

// Version 1.0: Ray: Initial commit
// Version 1.1: Ray: Added collision logic with walls, gold and bot. 
// Version 1.2: Ray: Fixed bug for moving towards wall
// Version 1.3: Alex and Jess: Added random bot movement for each time player successfully moves. 
// Version 1.4: David: Added a way of measuring player score, current level and a minor bug fix.
// Version 1.5: Ray and David: Added the leaderboard
// Version 1.6: Ray: Handle to bugs that will go off screen
// Version 1.7: Kimberley: Added dancing mummy, adjusted leaderboad UI
// Version 1.8: Alex and Jess: Added a win condition with a gold representing the win location on the last level when gold requirement is met. Win screen is needed.
// Version 1.9: Alex: Bots will move through doors once the gold condition is met on the final level. Added GOLDREQUIREMENT AND LEVELREQUIREMENT constants for ease of testing.
// Version 2.0: Ray: Split the player stuff to Player class, When move to new level renew dungeon only but not the whole game -> variables will not reset
// Version 2.1: Kimberley: Added win screen
// Version 2.2: Ray: Added back the addScoreFlag
// Version 2.3: Ray: Fixed Leaderboard bug.
// Version 2.4: Ray: Fixed a bug when the portal is generated but he died. It will show win page if the player move to the portal position.

public class Game extends BasicGameState {
	// Images
	private static Image door, wall, gold, bot, mummy0, mummy1, exit0, exit1;
	private static Animation dancingMummy, exit;
	private static Image[] mummies, exits;
	private static int[] duration;

	Player player;	// v2.0

	public static int playerCurrentLevel = 1; 	// v1.4 Shows current player level (initial level is 1)

	// v1.9 Added a flag to tell whether the bots should keep to their boundaries or not and fixed gold requirements and level requirements.
	private static boolean boundaryFlag;
	private static int GOLDREQUIREMENT;
	private static int LEVELREQUIREMENT;

	// v2.0 Get locations
	private static ArrayList<Location> dungeonWalls = new ArrayList<Location>();
	private static ArrayList<Location> dungeonDoorLocations = new ArrayList<Location>();
	private static ArrayList<Location> roomGold = new ArrayList<Location>();
	private static ArrayList<Location> dungeonBotLocations = new ArrayList<Location>();
	private static ArrayList<Room> rooms = new ArrayList<Room>(); //v1.3
	private static Location exitLocation = new Location(); //v1.8

	// v2.1 Exit locations
	private static float exitPosX;
	private static float exitPosY;

	// v1.6 declaring the offset variables for the render
	int initialOffsetX;
	int initialOffsetY;

	// v1.6 declaring the camera offset variables for the render 
	int cameraOffsetX;
	int cameraOffsetY;
	
	private static boolean addScoreFlag;	//v2.2
	
	private static ArrayList<Integer> leaderboardScore = new ArrayList<Integer>();

	public Game(int game) {
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// v1.9 Set gold and level requirements
		GOLDREQUIREMENT = 3;
		LEVELREQUIREMENT = 3;

		// v1.6 Ideal case should be initialOffsetX = 500, initialOffsetY = 500
		initialOffsetX = 500;
		initialOffsetY = 500;

		// v1.6 Can adjust it if you want -> bigger = shift more
		cameraOffsetX = 100;
		cameraOffsetY = 100;

		// Loading images
		door = new Image("res/Door.png");
		wall = new Image("res/Wall.png");
		gold = new Image("res/Gold.png");
		bot = new Image("res/Ghost.png");

		// v1.7 Animation for dancing mummy if player dies
		mummy0 = new Image("res/Mummy0.png");
		mummy0.setFilter(Image.FILTER_NEAREST);
		mummy1 = new Image("res/Mummy1.png");
		mummy1.setFilter(Image.FILTER_NEAREST);
		mummies = new Image[]{mummy0.getScaledCopy(2),mummy1.getScaledCopy(2)};
		duration = new int[]{300,300};
		dancingMummy = new Animation(mummies, duration, true);
		dancingMummy.setPingPong(true);

		// Animation for exit portal if player wins
		exit0 = new Image("res/Exit0.png");
		exit0.setFilter(Image.FILTER_NEAREST);
		exit1 = new Image("res/Exit1.png");
		exit1.setFilter(Image.FILTER_NEAREST);
		exits = new Image[]{exit0,exit1};
		exit = new Animation(exits, duration, true);

		// v2.0 Encapsulate the create dungeon code to function
		this.createDungeon();

		// v1.6 Set player position to the first room created
		// initialOffsetX = set player within screen, (*16) = image resolution, (+16) = next tile of the wall
		// v2.0 Constructor initiate with staring position
		player = new Player(dungeonWalls.get(0).getX()*16 - initialOffsetX+16, 
				dungeonWalls.get(0).getY()*16- initialOffsetY+16);

		boundaryFlag = true; // v1.9
		addScoreFlag = false;

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics graphics) throws SlickException {
		// v2.0 Get variables from player class
		if (player.isDied() == false) {
			// v1.1 -- if player collected 5 golds then move to next level
			if (player.getGoldCounter() == GOLDREQUIREMENT && 
					playerCurrentLevel < LEVELREQUIREMENT) { //v1.8
				this.createDungeon();	//v2.0 regenerate the dungeon instead of re-initiate the whole game
				player.setPlayerPosX(dungeonWalls.get(0).getX()*16 - initialOffsetX+16);
				player.setPlayerPosY(dungeonWalls.get(0).getY()*16 - initialOffsetY+16);
				player.setGoldCounter(0);
				player.setStepsToGold(0);
				player.setStepCounter(0);
				playerCurrentLevel = playerCurrentLevel + 1; //v1.4 -- new level is added

			}

			//v1.9 Change the boundaryFlag if it's the last level with gold count met.
			if (player.getGoldCounter() >= GOLDREQUIREMENT && 
					playerCurrentLevel == LEVELREQUIREMENT) {
				boundaryFlag = false;
			}

			// Track player position
			// graphics.drawString("X: " + player.getPlayerPosX() + 
			//		" Y: " + player.getPlayerPosY(), 800, 20);

			// v1.1 -- Display of how much gold the player collected
			graphics.drawString("Gold Collected: " + 
			player.getGoldCounter(), 900, 50);

			// v1.3 -- Display of how many steps the player has taken
			graphics.drawString("Steps Taken: " + 
			player.getStepCounter(), 900, 70);

			// v1.4 -- Display of player score
			graphics.drawString("Score: " + 
			player.getPlayerScore(), 900, 90);

			// v1.4 -- Display of player level
			graphics.drawString("Level: " + 
			playerCurrentLevel, 900, 110);

			// v1.6 -- Check if the player is out of the screen -> translate the graphics if yes
			if(player.getPlayerPosX() < 32) {
				graphics.translate(cameraOffsetX, 0);
			}

			if (player.getPlayerPosX() > Main.winWidth - 32) {
				graphics.translate(-cameraOffsetX, 0);
			}

			if (player.getPlayerPosY() > Main.winHeight - 32) {
				graphics.translate(0, -cameraOffsetY);
			}

			if (player.getPlayerPosY() < 32) {
				graphics.translate(0, cameraOffsetY);
			}


			// Render the dungeon

			// v1.8 -- Create an exit location in the middle of the final room.
			if (player.getGoldCounter() >= GOLDREQUIREMENT && 
			playerCurrentLevel == LEVELREQUIREMENT) {
				exitLocation = new Location(rooms.get(rooms.size()-1).
						getRoomLocation().getX()+rooms.get(rooms.size()-1).getRoomSize()/2, 
						rooms.get(rooms.size()-1).getRoomLocation().getY()+rooms.get(rooms.size()-1).
						getRoomSize()/2,rooms.get(rooms.size()-1).getRoomLocation().getRoomNumber());
				exitPosX = exitLocation.getX()*16-initialOffsetX;
				exitPosY = exitLocation.getY()*16-initialOffsetY;
				exit.draw(exitPosX, exitPosY);
			}
			
			// v2.1 -- If player goes to the exit
			if (player.getPlayerPosX() == exitPosX && player.getPlayerPosY() == exitPosY && player.isWin() == true) { //v2.4 added the winflag to go to the win page if he win
				// Check player's score with the leaderboard 
				// v2.3 -- check the leaderboard first  
				player.checkLeaderboard();
				leaderboardScore = Leaderboard.read("Leaderboard.txt");
				Win.playerScore = player.getPlayerScore();
				// Go to win screen
				sbg.enterState(3);
			}

			for (int i=0; i<dungeonWalls.size(); i++) {
				float wallsPosX = dungeonWalls.get(i).getX()*16-initialOffsetX;
				float wallsPosY = dungeonWalls.get(i).getY()*16-initialOffsetY;
				//System.out.println("WallX: " + wallsPosX);
				//System.out.println("WallY: " + wallsPosY);
				wall.draw(wallsPosX, wallsPosY);
			}

			for (int i=0; i<dungeonDoorLocations.size(); i++) {
				float doorPosX = dungeonDoorLocations.get(i).getX()*16-initialOffsetX;
				float doorPosY = dungeonDoorLocations.get(i).getY()*16-initialOffsetY;
				//System.out.println("DoorX: " + doorPosX);
				//System.out.println("DoorY: " + doorPosY);
				door.draw(doorPosX, doorPosY);
			}

			for (int i=0; i<roomGold.size(); i++) {
				float goldPosX = roomGold.get(i).getX()*16-initialOffsetX;
				float goldPosY = roomGold.get(i).getY()*16-initialOffsetY;
				//System.out.println("GoldX: " + goldPosX);
				//System.out.println("GoldY: " + goldPosY);
				gold.draw(goldPosX, goldPosY);
			}

			for (int i=0; i<dungeonBotLocations.size(); i++) {
				float botPosX = dungeonBotLocations.get(i).getX()*16-initialOffsetX;
				float botPosY = dungeonBotLocations.get(i).getY()*16-initialOffsetY;
				//System.out.println("BotX: " + botPosX);
				//System.out.println("BotY: " + botPosY);
				bot.draw(botPosX, botPosY);
			}

			// v2.0 Draw the player in player class	
			player.draw();				
		}
		else {
			// v1.1 -- if player died then do the following...
			
			// v2.2
			if(addScoreFlag == false) {
				player.checkLeaderboard();
				// v1.5 Update the arraylist
				leaderboardScore = Leaderboard.read("Leaderboard.txt");
				addScoreFlag = true;
			}
			
			try {
				// Set font for game ended
				InputStream dungeonFont	= ResourceLoader.getResourceAsStream("res/Dungeon.ttf");
				Font awFont = Font.createFont(Font.TRUETYPE_FONT, dungeonFont);
				awFont = awFont.deriveFont(32f);
				boolean antiAlias = true;
				TrueTypeFont textFont = new TrueTypeFont(awFont, antiAlias);
				String message1 = "YOU DIED!";
				String message2 = "(Press ENTER to restart)";
				textFont.drawString((Main.halfWidth - 
						textFont.getWidth(message1) / 2 ), 150, message1);
				textFont.drawString((Main.halfWidth - 
						textFont.getWidth(message2) / 2 ), 200, message2);

				// v1.7 Animation
				dancingMummy.draw(Main.halfWidth, 250);

				// v1.7 Leaderboard
				graphics.drawString("Player Score: " + player.getPlayerScore(), Main.halfWidth - 100, 300);

				// v1.7 Border
				graphics.drawRect(Main.halfWidth - 100, 340, 200, 200);
				textFont.drawString((Main.halfWidth - 
						textFont.getWidth("Leaderboard") / 2 ), 350, "Leaderboard");
				graphics.drawString("1st " + leaderboardScore.get(0), 
						Main.halfWidth - 50, 400);
				graphics.drawString("2nd " + leaderboardScore.get(1), 
						Main.halfWidth - 50, 450);
				graphics.drawString("3rd " + leaderboardScore.get(2), 
						Main.halfWidth - 50, 500);

				if (gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
					this.init(gc, sbg);
					player.setPlayerScore(0); // v1.4 -- resets the player score upon death
					playerCurrentLevel = 1; // v1.4 -- resets the players current level upon death
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static ArrayList<Integer> getLeaderboardScore() {
		return leaderboardScore;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {		
		// Getting input from player
		Input input = gc.getInput();

		// v2.0 Check keyboard input and update variables
		player.update(initialOffsetX, initialOffsetY, boundaryFlag, input, 
				dungeonWalls, roomGold, rooms);

		// v1.8 Win condition made true.
		if (player.getGoldCounter() >= GOLDREQUIREMENT && 
				playerCurrentLevel == LEVELREQUIREMENT && 
				player.getPlayerPosX() == exitLocation.getX()*16-initialOffsetX && 
				player.getPlayerPosY() == exitLocation.getY()*16-initialOffsetY) {
			player.setWin(true);
		}

		for (int i=0; i<dungeonBotLocations.size(); i++) {                                                                            
			// v1.3 Added extra condition to stop player dying upon spawn.
			if (player.getPlayerPosX() == dungeonBotLocations.get(i).getX()*16 - initialOffsetX 
					&& player.getPlayerPosY() == dungeonBotLocations.get(i).getY()*16 - 
					initialOffsetY && player.getStepCounter() != 0) {
				player.setDied(true);	// Set flag value
			}
		}
		if(input.isKeyPressed(Input.KEY_ESCAPE)) {
			System.exit(0);
		}
	}

	// v2.0 Create a new Dungeon
	public void createDungeon() {
		// Create Dungeon
		Dungeon testDungeon = new Dungeon(30, 1);
		// Insert all locations into arrayList
		dungeonDoorLocations = testDungeon.getDoorLocations();
		dungeonWalls = testDungeon.getWalls();
		roomGold = testDungeon.getGoldLocations();
		dungeonBotLocations = testDungeon.getBotLocations();
		rooms = testDungeon.getRooms(); //v1.3
	}
	
	public static int getGOLDREQUIREMENT() {
		return GOLDREQUIREMENT;
	}

	public static int getLEVELREQUIREMENT() {
		return LEVELREQUIREMENT;
	}

	@Override
	public int getID() {
		return 2;
	}

}