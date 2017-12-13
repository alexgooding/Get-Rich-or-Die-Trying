package dungeon;
import java.util.*;
import java.util.Random;

import items.Gold;

import java.util.ArrayList;

public class Room {

	/**
	* roomSize is the N in the NxN dimensions of a square room (NxN tiles).
	* roomLocation is the location of the bottom left corner of the room.
	* numberOfDoors is the amount of doors in the room, including the door entered through.
	* difficulty is an integer from 1-3 that can be used as a multiplier within methods to make rooms more complex.
	* doorLocations is a list of the door locations of the room. 
	* botFlag indicates whether a bot is present in the room or not.
	* roomGold is a list of the Gold objects within the room.
	*/
	private int roomSize, numberOfDoors, difficulty;
	private Location roomLocation = new Location();
	private boolean botFlag;
	private ArrayList<Door> doorLocations = new ArrayList<Door>();
	private ArrayList<Gold> roomGold = new ArrayList<Gold>();
	Bot roomBot = new Bot(); 

	// =========================
	// Constructors
	// =========================

	/**
	* Default constructor - Initialises x, y and roomNumber to the point (0, 0, 1).
	*/
	public Room() {
		Location defaultLocation = new Location(50, 50 ,1);
		setRoom(4, defaultLocation, 4, 1, true);
	}

	/**
	* Four-parameter version of the constructor.
	* Initialiases (roomSize, roomLocation, numberOfDoors, roomDifficulty) to a room, 
	* which is supplied to the function.
	*
	* @param roomSize       - the N in the NxN dimensions of a square room.
	* @param roomLocation   - the location of the bottom left corner of the room.
	* @param numberOfDoors  - the amount of doors in the room, including the door entered through.
	* @param difficulty     - the complexity of the room.
	* @param botFlag        - whether a bot is in the room or not.
	*/
	public Room(int roomSize, Location roomLocation, int numberOfDoors, int difficulty, boolean botFlag){
		setRoom(roomSize, roomLocation, numberOfDoors, difficulty, botFlag);
	}

	// =========================
	// Mutators and Accessors
	// =========================

	/**
	* Mutator for instance variables - sets the room parameters.
	*
	* @param roomSize       - new dimension N for this location. Player can only move within dimensions N-2.
	* @param roomLocation   - new location of the room.
	* @param numberOfDoors  - new number of doors for this room.
	* @param difficulty - new room difficulty for this room.
	* @param botFlag        - new flag for whether the bot is in the room or not.
	*/
	public void setRoom(int roomSize, Location roomLocation, int numberOfDoors, int difficulty, boolean botFlag) {
		this.roomSize = roomSize;
		this.roomLocation = roomLocation;
		this.numberOfDoors = numberOfDoors;
		this.difficulty = difficulty;
		this.botFlag = botFlag;
		// Door locations are randomly chosen out of center coordinates of walls.
		Door[] posDoorLocations = new Door[4]; // All four possible door locations.
		//South
		posDoorLocations[0] = new Door(new Location(roomLocation.getX()+(roomSize/2), 
				roomLocation.getY(), roomLocation.getRoomNumber()), 's');
		//East
		posDoorLocations[1] = new Door(new Location(roomLocation.getX()+roomSize, 
				roomLocation.getY()+(roomSize/2), roomLocation.getRoomNumber()), 'e');
		//North 
		posDoorLocations[2] = new Door(new Location(roomLocation.getX()+(roomSize/2), 
				roomLocation.getY()+roomSize, roomLocation.getRoomNumber()), 'n');
		//West
		posDoorLocations[3] = new Door(new Location(roomLocation.getX(), 
				roomLocation.getY()+(roomSize/2), roomLocation.getRoomNumber()), 'w');  
		final int[] randIndex = new Random().ints(0, 4).distinct().limit(4).toArray();
		for(int i=0; i<numberOfDoors; i++){
			doorLocations.add(posDoorLocations[randIndex[i]]);
		}

		// Generate random gold locations. Amount of gold is relative to roomDifficulty.
		int goldAmount;
		if (roomSize-4<=0) {
			goldAmount = 1;
		}
		else {
			goldAmount = (int) Math.sqrt((roomSize-4)*6/difficulty);
		}
		setRandomGold(goldAmount);

		// Generate a bot within the room if botFlag is true.
		if (botFlag == true) {
			Location newBotLocation = new Location();
			newBotLocation = randomRoomLocation();
			roomBot.setBot(newBotLocation);
		}
		else {
			roomBot = null;
		}
  }

	/**
	* Accessor for the roomSize. 
	*
	* @param  none.
	* @return The size of this room.
	*/   
	public int getRoomSize() {
		return roomSize;
	}

	/**
	* Accessor for the roomLocation. 
	*
	* @param  none.
	* @return The location of the room.
	*/   
	public Location getRoomLocation() {
		return roomLocation;
	}
	
	/**
	* Accessor for numberOfDoors. 
	*
	* @param  none.
	* @return The number of doors in the room.
	*/   
	public int getNumberOfDoors() {
		return numberOfDoors;
	}

	/**
	* Accessor for difficulty. 
	*
	* @param  none.
	* @return The difficulty of the room.
	*/   
	public int getRoomDifficulty() {
		return difficulty;
	}

	/**
	* Accessor for botFlag. 
	*
	* @param  none.
	* @return The boolean that indicates whether a bot is in the room or not.
	*/   
	public boolean getBotFlag() {
		return botFlag;
	}

	/**
	* Accessor for doorLocations. 
	*
	* @param  none.
	* @return The locations of the doors in the room.
	*/   
	public ArrayList<Door> getDoorLocations() {
		return doorLocations;
	}

	/**
	* Accessor for roomGold. 
	*
	* @param  none.
	* @return The roomGold list.
	*/   
	public ArrayList<Gold> getRoomGold() {
		return roomGold;
	}

	/**
	* Calculates and accesses boundaries. 
	*
	* @param  roomSize    - the size of the room.
	* @param roomLocation - the location of the room.
	* @return The boundaries of the room in an array in an anti-clockwise fashion.
	*/ 
	public Location[] getRoomBoundaries() {
		Location[] boundaries = new Location[4*roomSize];

		//South boundaries
		for (int i=0; i<=roomSize-1; i++) {
			Location nextLocation = new Location(roomLocation.getX()+i, 
					roomLocation.getY(), roomLocation.getRoomNumber());
			boundaries[i] = nextLocation;
		} 
		//East boundaries
		for (int i=0; i<=roomSize-1; i++) {
			Location nextLocation = new Location(roomLocation.getX()+roomSize, 
					roomLocation.getY()+i, roomLocation.getRoomNumber());
			boundaries[i+roomSize] = nextLocation;
		}
		//North boundaries
		for (int i=0; i<=roomSize-1; i++) {
			Location nextLocation = new Location(roomLocation.getX()+roomSize-i, 
					roomLocation.getY()+roomSize, roomLocation.getRoomNumber());
			boundaries[i+(2*roomSize)] = nextLocation;
		}
		//West boundaries
		for (int i=0; i<=roomSize-1; i++) {
			Location nextLocation = new Location(roomLocation.getX(), 
					roomLocation.getY()+roomSize-i, roomLocation.getRoomNumber());
			boundaries[i+(3*roomSize)] = nextLocation;
		} 
		return boundaries;
	}

	/**
	* Accessor for roomBot's location. 
	*
	* @return The location of roomBot.
	*/ 
	public Location getRoomBotLocation() {
		return roomBot.getBotLocation();
	}

	// =========================
	// Additional Methods
	// =========================

	/**
	* Randomly generates a location within a certain room. 
	*
	* @return A random location within the room.
	*/
  	public Location randomRoomLocation() {
		Random rand = new Random();
		int x1 = rand.nextInt(roomSize-1)+1;
		int y1 = rand.nextInt(roomSize-1)+1;
		Location rLocation = new Location(x1+roomLocation.getX(), y1+roomLocation.getY(), roomLocation.getRoomNumber());
		return rLocation;
	}

	/**
	* Randomly generates gold in a room. 
	*
	* @param goldBound - the number of locations in the room gold will be generated in.
	* @return void.
	*/
	public void setRandomGold(int goldBound) {
		Random rand = new Random();
		goldBound = Math.abs(rand.nextInt(goldBound)+goldBound-2);
		if (goldBound==0) {
			goldBound=1;
		}
		for (int i=0; i<goldBound; i++) {
			Location newLocation = new Location();
			newLocation = randomRoomLocation();
			roomGold.add(new Gold(newLocation, 1));
		}
		Set<Gold> removeDuplicates = new HashSet<>();
		removeDuplicates.addAll(roomGold);
		roomGold.clear();
		roomGold.addAll(removeDuplicates);	
	}

	/**
	* Randomly moves the roomBot one tile taking into account the room boundaries.
	*
	* @param boundaryFlag - flags whether the bot should conform to the boundaries of its room.
	* @param altBoundaries - The alternative boundaries the bot will conform to.
	* @return void.
	*/
	public void moveBotRandomly(boolean boundaryFlag, ArrayList<Location> altBoundaries) {
		String s = "nesw ";
		boolean moveSuccess;
		int index;
		do {
			moveSuccess = true;
			Random random = new Random();
    		index = random.nextInt(s.length());
    		for(int i=0; i<getRoomBoundaries().length; i++) {
    			if(updateLocation(getRoomBotLocation(), 
    					s.charAt(index)).equals(getRoomBoundaries()[i]) && boundaryFlag==true){
    				moveSuccess = false;
    			}
    		}
    		for (int i=0; i<altBoundaries.size(); i++){
    			if (updateLocation(getRoomBotLocation(), 
    					s.charAt(index)).equals(altBoundaries.get(i)) && boundaryFlag==false){
    				moveSuccess = false;
    			}
    		}
		} while (moveSuccess == false);
		roomBot.updateBotLocation(s.charAt(index));
	}

	/**
	* Updates a given location based on the direction of movement.
	*
	* @param l - the given location.
	* @param c - the given direction of movement.
	* @return the new location.
	*/
	public Location updateLocation(Location l, char c) {
		Location newLocation = new Location(l.getX(), l.getY(), l.getRoomNumber());
		switch(c){
			case 'n': newLocation.setY(l.getY()+1); break;
			case 'e': newLocation.setX(l.getX()+1); break;
			case 's': newLocation.setY(l.getY()-1); break;
			case 'w': newLocation.setX(l.getX()-1); break;
			default : break;
		}
		return newLocation;
	}

}
