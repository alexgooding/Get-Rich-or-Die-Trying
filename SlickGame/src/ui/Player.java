package ui;
import java.util.ArrayList;
import java.util.Collections;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import dungeon.Location;
import dungeon.Room;

// Version 2.0: Ray: Split from Map class

public class Player {
	private int playerScore;   // v1.4 Tracks the players score
	private int goldCounter;   // For counting the total gold the player collected
	private boolean died;      // died = true when player hit the bot
	private int stepCounter;   // v1.3 Counts the number of steps the player takes.
	private int stepsToGold;   //v1.4 Tracks the amount of steps it takes for a player to get a single piece of gold

	//v1.8 Added win condition.
	private boolean win;		//win = true when player walks on win location. 

	// Tracking player position
	private float playerPosX;
	private float playerPosY;
	private Image playerImg;

	private static ArrayList<Integer> leaderboardScore = new ArrayList<Integer>();

	public Player(float x, float y) throws SlickException {
		this.playerScore = 0;
		this.goldCounter = 0;
		this.died = false;
		this.win = false;
		this.stepCounter = 0;
		this.stepsToGold = 0;

		// Player position
		this.playerPosX = x;
		this.playerPosY = y;

		// Player sprite
		playerImg = new Image("res/Player.png");
		
		// v1.5	Read the leaderboad.txt file
		leaderboardScore = Leaderboard.read("Leaderboard.txt");
		
		// v1.5	Sort the arraylist into descending order
		Collections.sort(leaderboardScore, Collections.reverseOrder());
	}
	
	public void draw() {
		playerImg.draw(this.playerPosX, this.playerPosY);
	}
	
	public void update(int initialOffsetX, int initialOffsetY, boolean boundaryFlag, 
			Input input, ArrayList<Location> dungeonWalls, ArrayList<Location> 
			roomGold, ArrayList<Room> rooms) {
		
		// v1.3 Add a flag for when the player moves.
		boolean playerMoveFlag = false;
		
		// Moving UP
		if (input.isKeyPressed(Input.KEY_UP)) {
			this.playerPosY -= 16f;
			playerMoveFlag = true; //v1.3
			// v1.1 -- check collision of walls, gold and bot with the boundaries from the arrayList

			for (int i=0; i<dungeonWalls.size(); i++) {
				if (this.playerPosX == dungeonWalls.get(i).getX()*16 - initialOffsetX 
						&& this.playerPosY == dungeonWalls.get(i).getY()*16 - initialOffsetY) {
					this.playerPosY +=16f; // v1.2
					playerMoveFlag = false; // v1.3
				}
			}

			for (int i=0; i<roomGold.size(); i++) {
				if (this.playerPosX == roomGold.get(i).getX()*16 - initialOffsetX && 
						this.playerPosY == roomGold.get(i).getY()*16 - initialOffsetY) {
					roomGold.remove(i);	// Gold disappear
					this.goldCounter++;
					// v1.4 -- calculates the amount of steps taken for a gold piece
					this.playerScore = (this.playerScore + (1000 - (this.stepsToGold*50)));
					this.stepsToGold = 0; // resets the steps to 0 
				}
			}
		}

		// Moving DOWN
		if (input.isKeyPressed(Input.KEY_DOWN)) {
			this.playerPosY += 16f;
			playerMoveFlag = true; //v1.3
			// v1.1 -- check collision of walls, gold and bot with the boundaries from the arrayList

			for (int i=0; i<dungeonWalls.size(); i++) {
				if(this.playerPosX == dungeonWalls.get(i).getX()*16 - initialOffsetX && 
						this.playerPosY == dungeonWalls.get(i).getY()*16 - initialOffsetY) {
					this.playerPosY -=16f;	//v1.2
					playerMoveFlag = false; //v1.3
				}
			}

			for (int i=0; i<roomGold.size(); i++) {
				if(this.playerPosX == roomGold.get(i).getX()*16 - initialOffsetX 
						&& this.playerPosY == roomGold.get(i).getY()*16- initialOffsetY) {
					roomGold.remove(i);	// Gold disappear
					this.goldCounter++;
					this.playerScore = (this.playerScore + (1000 - (this.stepsToGold*50))); // v1.4 -- calculates the amount of steps taken for a gold piece
					this.stepsToGold = 0; // resets the steps to 0 
				}
			}
		}

		// Moving LEFT
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			this.playerPosX -= 16f;
			playerMoveFlag = true; //v1.3

			// v1.1 -- check collision of walls, gold and bot with the boundaries from the arrayList

			for(int i=0; i<dungeonWalls.size(); i++) {
				if(this.playerPosX == dungeonWalls.get(i).getX()*16 - initialOffsetX 
						&& this.playerPosY == dungeonWalls.get(i).getY()*16 - initialOffsetY) {
					this.playerPosX += 16f;	// v1.2
					playerMoveFlag = false; // v1.3
				}
			}

			for (int i=0; i<roomGold.size(); i++) {
				if(this.playerPosX == roomGold.get(i).getX()*16 - initialOffsetX 
						&& this.playerPosY == roomGold.get(i).getY()*16 - initialOffsetY) {
					roomGold.remove(i);	// Gold disappear
					this.goldCounter++;
					 // v1.4 -- calculates the amount of steps taken for a gold piece
					this.playerScore = (this.playerScore + (1000 - (this.stepsToGold*50)));
					this.stepsToGold = 0; // resets the steps to 0 
				}
			}
		}

		// Moving RIGHT
		if (input.isKeyPressed(Input.KEY_RIGHT)) {
			this.playerPosX += 16f;
			playerMoveFlag = true; //v1.3
			// v1.1 -- check collision of walls, gold and bot with the boundaries from the arrayList
			for (int i=0; i<dungeonWalls.size(); i++) {
				if (this.playerPosX == dungeonWalls.get(i).getX()*16 - initialOffsetX && 
						this.playerPosY == dungeonWalls.get(i).getY()*16- initialOffsetY) {
					this.playerPosX -=16f;	//v1.2
					playerMoveFlag = false; //v1.3
				}
			}
			for (int i=0; i<roomGold.size(); i++) {
				if(this.playerPosX == roomGold.get(i).getX()*16 - initialOffsetX && 
						this.playerPosY == roomGold.get(i).getY()*16 - initialOffsetY) {
					roomGold.remove(i);	// Gold disappear
					this.goldCounter++;
					this.playerScore = (playerScore + (1000 - (stepsToGold*50))); // v1.4 -- calculates the amount of steps taken for a gold piece
					this.stepsToGold = 0; // resets the steps to 0 
				}
			}
		}

		// v1.3
		if (playerMoveFlag == true) {
			this.stepCounter += 1; // v1.3 Add step to count.
			this.stepsToGold ++; // v1.4 tracks the amount of steps it takes to get a gold piece
			for (int i=0; i<rooms.size(); i++) {
				rooms.get(i).moveBotRandomly(boundaryFlag, dungeonWalls);
			}
		}
		// v2.1 Reset score to 0 if it is negative
		if (this.playerScore < 0) {
			this.playerScore = 0;
		}
	}

	public void checkLeaderboard() {
		// v1.5 Check if the player score is greater than the 3rd score
		if (this.getPlayerScore()> leaderboardScore.get(2)) {

			// v1.5 Check if the player score is greater than the 2nd score

			if (this.getPlayerScore() > leaderboardScore.get(1)) {

				// v1.5 Check if the player score is greater than the 1st score
				if (this.getPlayerScore() > leaderboardScore.get(0)) {
					// v1.5 Swap the player score with the 1st score 
					int tempHighest = leaderboardScore.get(0);
					int tempSecHighest = leaderboardScore.get(1);
					leaderboardScore.set(0, this.getPlayerScore());
					leaderboardScore.set(1, tempHighest);
					leaderboardScore.set(2, tempSecHighest);
				}
				else {
					// v1.5 Swap the player score with the 2nd score 
					int tempSecHighest = leaderboardScore.get(1);
					leaderboardScore.set(1, this.getPlayerScore());
					leaderboardScore.set(2, tempSecHighest);
				}
			}
			else {
				// v1.5 Swap the player score with the 3rd score 
				leaderboardScore.set(2, this.getPlayerScore());
			}
		}
		// v1.5 Write the top 3 scores into Leaderboard.txt file
		Leaderboard.write("Leaderboard.txt", leaderboardScore.get(0), 
				leaderboardScore.get(1), leaderboardScore.get(2));
	}

	// Getters and Setters
	public int getPlayerScore() {
		return this.playerScore;
	}

	public void setPlayerScore(int playerScore) {
		this.playerScore = playerScore;
	}

	public int getGoldCounter() {
		return this.goldCounter;
	}

	public void setGoldCounter(int goldCounter) {
		this.goldCounter = goldCounter;
	}

	public boolean isDied() {
		return this.died;
	}

	public void setDied(boolean died) {
		this.died = died;
	}

	public int getStepCounter() {
		return this.stepCounter;
	}

	public void setStepCounter(int stepCounter) {
		this.stepCounter = stepCounter;
	}

	public int getStepsToGold() {
		return this.stepsToGold;
	}

	public void setStepsToGold(int stepsToGold) {
		this.stepsToGold = stepsToGold;
	}

	public float getPlayerPosX() {
		return this.playerPosX;
	}

	public void setPlayerPosX(float playerPosX) {
		this.playerPosX = playerPosX;
	}

	public float getPlayerPosY() {
		return this.playerPosY;
	}

	public void setPlayerPosY(float playerPosY) {
		this.playerPosY = playerPosY;
	}

	public Image getPlayerImg() {
		return this.playerImg;
	}

	public void setPlayerImg(Image playerImg) {
		this.playerImg = playerImg;
	}

	public boolean isWin() {
		return this.win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

}
