import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;


//version v1.0: Ray: Initial Create
//version v1.1: Ray: Add collision logic with walls, gold and bot. 
//version v1.2: Ray: Fixed bug for moving towards wall
//version v1.3: Alex and Jess: Added random bot movement for each time player successfully moves. 
//version v1.4: David: Added a way of measuring player score, current level and a minor bug fix.
//version v1.5: Ray and David: Added the leaderboard
//version v1.6: Ray: Handle to bugs that will go off screen

public class Map extends BasicGameState {

	Image tiles, door, wall, player, gold, bot;	//Images

	//v1.1
	private int goldCounter;	//For counting the total gold the player collected
	private static boolean died;		//died = true when player hit the bot
	private int stepCounter;      //v1.3 Counts the number of steps the player takes.
	private int stepsToGold; 		//v1.4 Tracks the amount of steps it takes for a player to get a single piece of gold
	private static int playerScore;			//v1.4 Tracks the players score
	private static int playerCurrentLevel = 1; 	//v1.4 Shows current player level (initial level is 1)
	private static ArrayList<Integer> leaderboardScore = new ArrayList<Integer>();
	
	//v1.5	Added a flag to escape the loop in update
	private boolean addScoreFlag;
	
	//Getting Boundaries
	private ArrayList<Location> dungeonWalls = new ArrayList<Location>();
	private ArrayList<Location> dungeonDoorLocations = new ArrayList<Location>();
	private ArrayList<Location> roomGold = new ArrayList<Location>();
	private ArrayList<Location> dungeonBotLocations = new ArrayList<Location>();
	private ArrayList<Room> rooms = new ArrayList<Room>(); //v1.3
	
	// Tracking player position
	float playerPosX;
	float playerPosY;
	
	// v1.6 declaring the offset variables for the render
	int initialOffsetX;
	int initialOffsetY;
	
	// v1.6 declaring the camera offset variables for the render 
	int cameraOffsetX;
	int cameraOffsetY;
	
	public Map(int map) {
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		// v1.6 Ideal case should be initialOffsetX = 500, initialOffsetY = 500
		initialOffsetX = 500;
		initialOffsetY = 500;
		
		// v1.6 Can adjust it if you want -> bigger = shift more
		cameraOffsetX = 100;
		cameraOffsetY = 100;
		
		// Loading images
		door = new Image("res/door.png");
		wall = new Image("res/wall.png");
		player = new Image("res/player.png");
		gold = new Image("res/gold.png");
		bot = new Image("res/ghost.png");
		
		
		// Create Dungeon
		Dungeon testDungeon = new Dungeon(30, 1);
		
		// Insert all locations into arrayList
		dungeonDoorLocations = testDungeon.getDungeonDoorLocations();
		dungeonWalls = testDungeon.getDungeonWalls();
		roomGold = testDungeon.getDungeonGoldLocations();
		dungeonBotLocations = testDungeon.getDungeonBotLocations();
		rooms = testDungeon.getRooms(); //v1.3
		
		//v1.5	Read the leaderboad.txt file
		leaderboardScore = leaderBoard.read("leaderboard.txt");
		//v1.5	Sort the arraylist into descending order
		Collections.sort(leaderboardScore, Collections.reverseOrder());

		//v1.6 Set Player position to the first room first wall created, initialOffsetX =make it to be with, (*16)=image resolution, (+16)=next tile of the wall
		playerPosX = dungeonWalls.get(0).getX()*16- initialOffsetX+16;
		playerPosY = dungeonWalls.get(0).getY()*16- initialOffsetY+16;
		
		stepsToGold = 0;
		stepCounter = 0; //v1.4 - bugFix, resets stepCounter upon new level
		goldCounter = 0;
		
		died = false;
		
		//v1.5 Flag initialization
		addScoreFlag = false;
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics graphics) throws SlickException {
		if(died == false) {
			// v1.1 -- if player collected 5 golds then move to next level
			if(goldCounter == 5) {
				this.init(gc, sbg);	//Re-initiate the game
				playerCurrentLevel = playerCurrentLevel + 1; //v1.4 -- new level is added
				
			}
			else {
				
				//Track player position
				graphics.drawString("X: " + playerPosX + " Y: " + playerPosY, 800, 20);
				
				//v1.1 -- Display of how much gold the player collected
				graphics.drawString("Gold Collected: " + goldCounter, 800, 500);
				
				//v1.3 -- Display of how many steps the player has taken
				graphics.drawString("Steps Taken: " + stepCounter, 800, 520);
				
				//v1.4 -- Display of player score
				graphics.drawString("Player Score: " + playerScore, 800, 540);
				
				//v1.4 -- Display of player level
				graphics.drawString("Current level is: " + playerCurrentLevel, 800, 560);
				
				//v1.6 -- Check if the player is out of the screen -> translate the graphics if yes
				if(playerPosX < 32) {
					graphics.translate(cameraOffsetX, 0);
				}
				
				if(playerPosX > Main.winWidth - 32) {
					graphics.translate(-cameraOffsetX, 0);
				}
				
				if(playerPosY > Main.winHeight - 32) {
					graphics.translate(0, -cameraOffsetY);
				}
				
				if(playerPosY < 32) {
					graphics.translate(0, cameraOffsetY);
				}
				
				
				//Render the dungeon
				for(int i=0; i<dungeonWalls.size(); i++) {
					float wallsPosX = dungeonWalls.get(i).getX()*16-initialOffsetX;
					float wallsPosY = dungeonWalls.get(i).getY()*16- initialOffsetY;
					//System.out.println("WallX: " + wallsPosX);
					//System.out.println("WallY: " + wallsPosY);
					wall.draw(wallsPosX, wallsPosY);
				}

				for(int i=0; i<dungeonDoorLocations.size(); i++) {
					float doorPosX = dungeonDoorLocations.get(i).getX()*16-initialOffsetX;
					float doorPosY = dungeonDoorLocations.get(i).getY()*16- initialOffsetY;
					//System.out.println("DoorX: " + doorPosX);
					//System.out.println("DoorY: " + doorPosY);
					door.draw(doorPosX, doorPosY);
				}

				for(int i=0; i<roomGold.size(); i++) {
					float goldPosX = roomGold.get(i).getX()*16- initialOffsetX;
					float goldPosY = roomGold.get(i).getY()*16- initialOffsetY;
					//System.out.println("GoldX: " + goldPosX);
					//System.out.println("GoldY: " + goldPosY);
					gold.draw(goldPosX, goldPosY);
				}

				for(int i=0; i<dungeonBotLocations.size(); i++) {
					float botPosX = dungeonBotLocations.get(i).getX()*16-initialOffsetX;
					float botPosY = dungeonBotLocations.get(i).getY()*16- initialOffsetY;
					//System.out.println("BotX: " + botPosX);
					//System.out.println("BotY: " + botPosY);
					bot.draw(botPosX, botPosY);
				}

				// Draw the player
				player.draw(playerPosX,playerPosY);				
			}
		
		}
		else {
			//v1.5	LeaderBoard Logic
			if(addScoreFlag == false){
				//v1.5 Check if the player score is greater than the 3rd score
				if(playerScore> leaderboardScore.get(2)) {
					
					//v1.5 Check if the player score is greater than the 2nd score
					if(playerScore > leaderboardScore.get(1)) {
						
						//v1.5 Check if the player score is greater than the 1st score
						if(playerScore > leaderboardScore.get(0)) {
							
							// v1.5 Swap the player score with the 1st score 
							int tempHighest = leaderboardScore.get(0);
							int tempSecHighest = leaderboardScore.get(1);
							leaderboardScore.set(0, playerScore);
							leaderboardScore.set(1, tempHighest);
							leaderboardScore.set(2, tempSecHighest);
						}
						else {
							// v1.5 Swap the player score with the 2nd score 
							int tempSecHighest = leaderboardScore.get(1);
							leaderboardScore.set(1, playerScore);
							leaderboardScore.set(2, tempSecHighest);
						}
					}
					else {
						// v1.5 Swap the player score with the 3rd score 
						leaderboardScore.set(2, playerScore);
					}		
				}
				// v1.5 Write the arraylist into the leaderboard.txt file, (filename, 1st score, 2nd score, 3rd score)
				leaderBoard.write("leaderboard.txt", leaderboardScore.get(0), leaderboardScore.get(1), leaderboardScore.get(2));
				
				// v1.5 set the flag to true to escape this loop
				addScoreFlag = true;
				
				// v1.5 update the arraylist
				leaderboardScore = leaderBoard.read("leaderboard.txt");
				
				//v1.1 -- if died then do the following...
				try {
					// Set font for game ended
					InputStream dungeonFont	= ResourceLoader.getResourceAsStream("res/Dungeon.ttf");
					Font awFont = Font.createFont(Font.TRUETYPE_FONT, dungeonFont);
					awFont = awFont.deriveFont(32f);
					boolean antiAlias = true;
					TrueTypeFont textFont = new TrueTypeFont(awFont, antiAlias);
					textFont.drawString((Main.halfWidth - textFont.getWidth("YOU DIED!") / 2 ), Main.halfHeight - 50, "YOU DIED!");
					textFont.drawString((Main.halfWidth - textFont.getWidth("(Press ENTER to restart)") / 2 ), Main.halfHeight + 50, "(Press ENTER to restart)");
					if(gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
						this.init(gc, sbg);
						playerScore = 0; //v1.4 -- resets the player score upon death
						playerCurrentLevel = 1; //v1.4 -- resets the players current level upon death
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			else {
				//v1.1 -- if died then do the following...
				try {
					// Set font for game ended
					InputStream dungeonFont	= ResourceLoader.getResourceAsStream("res/Dungeon.ttf");
					Font awFont = Font.createFont(Font.TRUETYPE_FONT, dungeonFont);
					awFont = awFont.deriveFont(32f);
					boolean antiAlias = true;
					TrueTypeFont textFont = new TrueTypeFont(awFont, antiAlias);
					textFont.drawString((Main.halfWidth - textFont.getWidth("YOU DIED!") / 2 ), Main.halfHeight - 50, "YOU DIED!");
					textFont.drawString((Main.halfWidth - textFont.getWidth("(Press ENTER to restart)") / 2 ), Main.halfHeight + 50, "(Press ENTER to restart)");
					graphics.drawString("LeaderBoard",0,50);
					graphics.drawString("1st " + leaderboardScore.get(0),0,100);
					graphics.drawString("2nd " + leaderboardScore.get(1),0,150);
					graphics.drawString("3rd " + leaderboardScore.get(2),0,200);
					
					if(gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
						this.init(gc, sbg);
						playerScore = 0; //v1.4 -- resets the player score upon death
						playerCurrentLevel = 1; //v1.4 -- resets the players current level upon death
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
		// v1.1 -- check if player died, if died then end game
		
			//v1.3 Add a flag for when the player moves.
			boolean playerMoveFlag = false;

			// Getting input from player
			Input input = gc.getInput();

			//Moving UP
			if(input.isKeyPressed(Input.KEY_UP)) {
				playerPosY -= 16f;
				playerMoveFlag = true; //v1.3
				//v1.1 -- check collision of walls, gold and bot with the boundaries from the arrayList

				for(int i=0; i<dungeonWalls.size(); i++) {
					if(playerPosX == dungeonWalls.get(i).getX()*16 - initialOffsetX && playerPosY == dungeonWalls.get(i).getY()*16- initialOffsetY) {
						playerPosY +=16f;//v1.2
						playerMoveFlag = false; //v1.3
					}
				}

				for(int i=0; i<roomGold.size(); i++) {
					if(playerPosX == roomGold.get(i).getX()*16- initialOffsetX && playerPosY == roomGold.get(i).getY()*16- initialOffsetY) {
						roomGold.remove(i);	//Gold disappear
						goldCounter++;
						playerScore = (playerScore + (1000 - (stepsToGold*50))); //v1.4 -- calculates the amount of steps taken for a gold piece
						stepsToGold = 0; //resets the steps to 0 
					}
				}

			}

			//Moving DOWN
			if(input.isKeyPressed(Input.KEY_DOWN)) {
				playerPosY += 16f;
				playerMoveFlag = true; //v1.3
				//v1.1 -- check collision of walls, gold and bot with the boundaries from the arrayList

				for(int i=0; i<dungeonWalls.size(); i++) {
					if(playerPosX == dungeonWalls.get(i).getX()*16- initialOffsetX && playerPosY == dungeonWalls.get(i).getY()*16- initialOffsetY) {
						playerPosY -=16f;	//v1.2
						playerMoveFlag = false; //v1.3
					}
				}

				for(int i=0; i<roomGold.size(); i++) {
					if(playerPosX == roomGold.get(i).getX()*16- initialOffsetX && playerPosY == roomGold.get(i).getY()*16- initialOffsetY) {
						roomGold.remove(i);	//Gold disappear
						goldCounter++;
						playerScore = (playerScore + (1000 - (stepsToGold*50))); //v1.4 -- calculates the amount of steps taken for a gold piece
						stepsToGold = 0; //resets the steps to 0 
					}
				}

			}

			//Moving LEFT
			if(input.isKeyPressed(Input.KEY_LEFT)) {
				playerPosX -= 16f;
				playerMoveFlag = true; //v1.3

				//v1.1 -- check collision of walls, gold and bot with the boundaries from the arrayList

				for(int i=0; i<dungeonWalls.size(); i++) {
					if(playerPosX == dungeonWalls.get(i).getX()*16- initialOffsetX && playerPosY == dungeonWalls.get(i).getY()*16- initialOffsetY) {
						playerPosX +=16f;	//v1.2
						playerMoveFlag = false; //v1.3
					}
				}

				for(int i=0; i<roomGold.size(); i++) {
					if(playerPosX == roomGold.get(i).getX()*16- initialOffsetX && playerPosY == roomGold.get(i).getY()*16- initialOffsetY) {
						roomGold.remove(i);	//Gold disappear
						goldCounter++;
						playerScore = (playerScore + (1000 - (stepsToGold*100))); //v1.4 -- calculates the amount of steps taken for a gold piece
						stepsToGold = 0; //resets the steps to 0 
					}
				}

			}

			//Moving RIGHT
			if(input.isKeyPressed(Input.KEY_RIGHT)) {
				playerPosX += 16f;
				playerMoveFlag = true; //v1.3

				//v1.1 -- check collision of walls, gold and bot with the boundaries from the arrayList

				for(int i=0; i<dungeonWalls.size(); i++) {
					if(playerPosX == dungeonWalls.get(i).getX()*16- initialOffsetX && playerPosY == dungeonWalls.get(i).getY()*16- initialOffsetY) {
						playerPosX -=16f;	//v1.2
						playerMoveFlag = false; //v1.3
					}
				}

				for(int i=0; i<roomGold.size(); i++) {
					if(playerPosX == roomGold.get(i).getX()*16- initialOffsetX && playerPosY == roomGold.get(i).getY()*16- initialOffsetY) {
						roomGold.remove(i);	//Gold disappear
						goldCounter++;
						playerScore = (playerScore + (1000 - (stepsToGold*50))); //v1.4 -- calculates the amount of steps taken for a gold piece
						stepsToGold = 0; //resets the steps to 0 
					}
				}
			}

			//v1.3
			if(playerMoveFlag == true) {
				stepCounter += 1; //v1.3 Add step to count.
				stepsToGold ++; //v1.4 tracks the amount of steps it takes to get a gold piece

				for(int i=0; i<rooms.size(); i++) {
					rooms.get(i).moveBotRandomly();
				}
			}
			for(int i=0; i<dungeonBotLocations.size(); i++) {                                                                            
				//v1.3 Added extra condition to stop player dying upon spawn.
				if(playerPosX == dungeonBotLocations.get(i).getX()*16- initialOffsetX && playerPosY == dungeonBotLocations.get(i).getY()*16- initialOffsetY && stepCounter != 0) {
					died = true;	//Set flag value
				}
			}
			if(input.isKeyPressed(Input.KEY_ESCAPE)) {
				System.exit(0);
			}
	}	

	@Override
	public int getID() {
		return 1;
	}
}