import java.util.Random;
import java.util.ArrayList;

public class Room{

	/**
   * roomSize is the N in the NxN dimensions of a square room (NxN tiles).
   * roomLocation is the location of the bottom left corner of the room.
   * numberOfDoors is the amount of doors in the room, including the door entered through.
   * roomDifficulty is an integer from 1-3 that can be used as a multiplier within methods to make rooms more complex.
   * doorLocations is a list of the door locations of the room. 
   */
	private int roomSize, numberOfDoors, roomDifficulty;
	private Location roomLocation = new Location();
	private ArrayList<Door> doorLocations = new ArrayList<Door>(); 

	// =========================
  // Constructors
  // =========================

  /**
   * Default constructor - this initialises x, y and roomNumber to the point (0, 0, 1).
   */
	public Room(){
		Location defaultLocation = new Location(50, 50 ,1);
		setRoom(4, defaultLocation, 4, 1);
	}

	/**
   * Four-parameter version of the constructor. Initialiases (roomSize, roomLocation, numberOfDoors, roomDifficulty) to
   * a room, which is supplied to the function.
   *
   * @param roomSize       - the N in the NxN dimensions of a square room.
   * @param roomLocation   - the location of the bottom left corner of the room.
	 * @param numberOfDoors  - the amount of doors in the room, including the door entered through.
	 * @param roomDifficulty - the complexity of the room.
   */
	public Room(int roomSize, Location roomLocation, int numberOfDoors, int roomDifficulty){
		setRoom(roomSize, roomLocation, numberOfDoors, roomDifficulty);
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
	 * @param roomDifficulty - new room difficulty for this room.
   */
  public void setRoom(int roomSize, Location roomLocation, int numberOfDoors, int roomDifficulty){
  	this.roomSize = roomSize;
  	this.roomLocation = roomLocation;
		this.numberOfDoors = numberOfDoors;
		this.roomDifficulty = roomDifficulty;
		//Door locations are randomly chosen out of center coordinates of walls.
		Door[] posDoorLocations = new Door[4]; //All four possible door locations.
		//South
		posDoorLocations[0] = new Door(new Location(roomLocation.getX()+(roomSize/2), roomLocation.getY(), roomLocation.getRoomNumber()), 's');
		//East
		posDoorLocations[1] = new Door(new Location(roomLocation.getX()+roomSize, roomLocation.getY()+(roomSize/2), roomLocation.getRoomNumber()), 'e');
		//North 
		posDoorLocations[2] = new Door(new Location(roomLocation.getX()+(roomSize/2), roomLocation.getY()+roomSize, roomLocation.getRoomNumber()), 'n');
		//West
		posDoorLocations[3] = new Door(new Location(roomLocation.getX(), roomLocation.getY()+(roomSize/2), roomLocation.getRoomNumber()), 'w');  
		final int[] randIndex = new Random().ints(0, 4).distinct().limit(4).toArray();
		for(int i=0; i<numberOfDoors; i++){
			doorLocations.add(posDoorLocations[randIndex[i]]);
		}
  }

  /**
   * Accessor for the roomSize. 
   *
   * @param  none.
   * @return The size of this room.
   */   
	public int getRoomSize(){
		return roomSize;
	}

  /**
   * Accessor for the roomLocation. 
   *
   * @param  none.
   * @return The location of the room.
   */   
	public Location getRoomLocation(){
		return roomLocation;
	}
	
	/**
   * Accessor for numberOfDoors. 
   *
   * @param  none.
   * @return The number of doors in the room.
   */   
	public int getNumberOfDoors(){
		return numberOfDoors;
	}

	/**
   * Accessor for roomDifficulty. 
   *
   * @param  none.
   * @return The difficulty of the room.
   */   
	public int getRoomDifficulty(){
		return roomDifficulty;
	}

	/**
   * Accessor for doorLocations. 
   *
   * @param  none.
   * @return The locations of the doors in the room.
   */   
	public ArrayList<Door> getDoorLocations(){
		return doorLocations;
	}

	/**
   * Calculates and accesses boundaries. 
   *
   * @param  roomSize    - the size of the room.
   * @param roomLocation - the location of the room.
   * @return The boundaries of the room in an array in an anti-clockwise fashion.
   */ 
	public Location[] getRoomBoundaries(){
		Location[] boundaries = new Location[4*roomSize];

		for(int i=0; i<=roomSize-1; i++){
			Location nextLocation = new Location(roomLocation.getX()+i, roomLocation.getY(), roomLocation.getRoomNumber());
			boundaries[i] = nextLocation;
		} 
		for(int i=0; i<=roomSize-1; i++){
			Location nextLocation = new Location(roomLocation.getX()+roomSize, roomLocation.getY()+i, roomLocation.getRoomNumber());
			boundaries[i+roomSize] = nextLocation;
		}
		for(int i=0; i<=roomSize-1; i++){
			Location nextLocation = new Location(roomLocation.getX()+roomSize-i, roomLocation.getY()+roomSize, roomLocation.getRoomNumber());
			boundaries[i+(2*roomSize)] = nextLocation;
		}
		for(int i=0; i<=roomSize-1; i++){
			Location nextLocation = new Location(roomLocation.getX(), roomLocation.getY()+roomSize-i, roomLocation.getRoomNumber());
			boundaries[i+(3*roomSize)] = nextLocation;
		} 
		return boundaries;
	}

	// =========================
  // Additional Methods
  // =========================

	/**
 	 * Randomly generates a location within a certain room. 
   *
   * @return A random location within the room.
   */

  public Location randomRoomLocation(){
		Random rand = new Random();
		int x1 = rand.nextInt(roomSize);
		int y1 = rand.nextInt(roomSize);
		Location rLocation = new Location(x1+roomLocation.getX(), y1+roomLocation.getY(), roomLocation.getRoomNumber());
		return rLocation;
	}

}
