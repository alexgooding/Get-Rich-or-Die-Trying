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
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;

//version v1.0: Ray: Initial Create
//version v1.1: Ray: Add collision logic with walls, gold and bot. 
//version v1.2: Ray: Fixed bug for moving towards wall
//version v1.3: Alex and Jess: Added random bot movement for each time player successfully moves. 

public class Map extends BasicGameState {

	Image tiles, door, wall, player, gold, bot;	//Images
	
	//v1.1
	private int goldCounter;	//For counting the total gold the player collected
	private boolean died;		//died = true when player hit the bot
	private int stepCounter;      //v1.3 Counts the number of steps the player takes.
	
	//Getting Boundaries
	private ArrayList<Location> dungeonWalls = new ArrayList<Location>();
	private ArrayList<Location> dungeonDoorLocations = new ArrayList<Location>();
	private ArrayList<Location> roomGold = new ArrayList<Location>();
	private ArrayList<Location> dungeonBotLocations = new ArrayList<Location>();
	private ArrayList<Room> rooms = new ArrayList<Room>(); //v1.3
	
	// Tracking player position
	float playerPosX;
	float playerPosY;
	
	public Map(int map) {
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
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
		

		// Set Player position to the first room first wall created, (-500)=just want to make it visible, (*16)=image resolution, (+16)=next tile of the wall
		playerPosX = dungeonWalls.get(0).getX()*16-500+16;
		playerPosY = dungeonWalls.get(0).getY()*16-600+16;
		
		goldCounter = 0;
		died = false;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics graphics) throws SlickException {
		
		// v1.1 -- check if player died, if died then end game
		if(died == false) {
			
			// v1.1 -- if player collected 5 golds then move to next level
			if(goldCounter == 5) {
				this.init(gc, sbg);	//Re-initiate the game
			}
			else {

				//Render the dungeon

				for(int i=0; i<dungeonWalls.size(); i++) {
					float wallsPosX = dungeonWalls.get(i).getX()*16;
					float wallsPosY = dungeonWalls.get(i).getY()*16;
					//System.out.println("WallX: " + wallsPosX);
					//System.out.println("WallY: " + wallsPosY);
					wall.draw(wallsPosX-500, wallsPosY-600);
				}


				for(int i=0; i<dungeonDoorLocations.size(); i++) {
					float doorPosX = dungeonDoorLocations.get(i).getX()*16;
					float doorPosY = dungeonDoorLocations.get(i).getY()*16;
					//System.out.println("DoorX: " + doorPosX);
					//System.out.println("DoorY: " + doorPosY);
					door.draw(doorPosX-500, doorPosY-600);
				}

				for(int i=0; i<roomGold.size(); i++) {
					float goldPosX = roomGold.get(i).getX()*16;
					float goldPosY = roomGold.get(i).getY()*16;
					//System.out.println("GoldX: " + goldPosX);
					//System.out.println("GoldY: " + goldPosY);
					gold.draw(goldPosX-500, goldPosY-600);
				}

				for(int i=0; i<dungeonBotLocations.size(); i++) {
					float botPosX = dungeonBotLocations.get(i).getX()*16;
					float botPosY = dungeonBotLocations.get(i).getY()*16;
					//System.out.println("BotX: " + botPosX);
					//System.out.println("BotY: " + botPosY);
					bot.draw(botPosX-500, botPosY-600);
				}

				// Draw the player
				player.draw(playerPosX,playerPosY);

				//Track player position
				graphics.drawString("X: " + playerPosX + " Y: " + playerPosY, 500, 20);
				
				//v1.1 -- Display of how much gold the player collected
				graphics.drawString("Gold Collected: " + goldCounter, 500, 500);
				
				//v1.3 -- Display of how many steps the player has taken
				graphics.drawString("Steps Taken: " + stepCounter, 500, 520);
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
				if(gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
					this.init(gc, sbg);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		
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
				if(playerPosX == dungeonWalls.get(i).getX()*16-500 && playerPosY == dungeonWalls.get(i).getY()*16-600) {
					playerPosY +=16f;//v1.2
					playerMoveFlag = false; //v1.3
				}
			}
			
			for(int i=0; i<roomGold.size(); i++) {
				if(playerPosX == roomGold.get(i).getX()*16-500 && playerPosY == roomGold.get(i).getY()*16-600) {
					roomGold.remove(i);	//Gold disappear
					goldCounter++;
				}
			}
			
		}
		
		//Moving DOWN
		if(input.isKeyPressed(Input.KEY_DOWN)) {
			playerPosY += 16f;
			playerMoveFlag = true; //v1.3
			//v1.1 -- check collision of walls, gold and bot with the boundaries from the arrayList
			
			for(int i=0; i<dungeonWalls.size(); i++) {
				if(playerPosX == dungeonWalls.get(i).getX()*16-500 && playerPosY == dungeonWalls.get(i).getY()*16-600) {
					playerPosY -=16f;	//v1.2
					playerMoveFlag = false; //v1.3
				}
			}
			
			for(int i=0; i<roomGold.size(); i++) {
				if(playerPosX == roomGold.get(i).getX()*16-500 && playerPosY == roomGold.get(i).getY()*16-600) {
					roomGold.remove(i);	//Gold disappear
					goldCounter++;
				}
			}
			
		}
		
		//Moving LEFT
		if(input.isKeyPressed(Input.KEY_LEFT)) {
			playerPosX -= 16f;
			playerMoveFlag = true; //v1.3
			
//v1.1 -- check collision of walls, gold and bot with the boundaries from the arrayList
			
			for(int i=0; i<dungeonWalls.size(); i++) {
				if(playerPosX == dungeonWalls.get(i).getX()*16-500 && playerPosY == dungeonWalls.get(i).getY()*16-600) {
					playerPosX +=16f;	//v1.2
					playerMoveFlag = false; //v1.3
				}
			}
			
			for(int i=0; i<roomGold.size(); i++) {
				if(playerPosX == roomGold.get(i).getX()*16-500 && playerPosY == roomGold.get(i).getY()*16-600) {
					roomGold.remove(i);	//Gold disappear
					goldCounter++;
				}
			}
			
		}
		
		//Moving RIGHT
		if(input.isKeyPressed(Input.KEY_RIGHT)) {
			playerPosX += 16f;
			playerMoveFlag = true; //v1.3
			
//v1.1 -- check collision of walls, gold and bot with the boundaries from the arrayList
			
			for(int i=0; i<dungeonWalls.size(); i++) {
				if(playerPosX == dungeonWalls.get(i).getX()*16-500 && playerPosY == dungeonWalls.get(i).getY()*16-600) {
					playerPosX -=16f;	//v1.2
					playerMoveFlag = false; //v1.3
				}
			}
			
			for(int i=0; i<roomGold.size(); i++) {
				if(playerPosX == roomGold.get(i).getX()*16-500 && playerPosY == roomGold.get(i).getY()*16-600) {
					roomGold.remove(i);	//Gold disappear
					goldCounter++;
				}
			}
		}
		
		//v1.3
		if(playerMoveFlag == true) {
			stepCounter += 1; //v1.3 Add step to count.
			for(int i=0; i<rooms.size(); i++) {
				rooms.get(i).moveBotRandomly();
			}
		}
		for(int i=0; i<dungeonBotLocations.size(); i++) {                                                                            
			//v1.3 Added extra condition to stop player dying upon spawn.
			if(playerPosX == dungeonBotLocations.get(i).getX()*16-500 && playerPosY == dungeonBotLocations.get(i).getY()*16-600 && stepCounter != 0) {
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