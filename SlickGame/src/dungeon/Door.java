package dungeon;
public class Door {

	/**
	* doorLocation stores the coordinates of the door and the room number the door is in.
	* direction is the cardinal direction of the door.
	*/
	private Location doorLocation = new Location();
	private char direction;

	// =========================
	// Constructors
	// =========================

	/**
	* Default constructor - Initialises doorLocation to ((0, 0, 1), 'n').
	*/
	public Door() {
		Location defaultLocation = new Location(0, 0 ,1);
		setDoor(defaultLocation, 'n');
	}

	/**
	* Four-parameter version of the constructor. 
	* Initialiases (roomSize, roomLocation, numberOfDoors, roomDifficulty) to
	* a room, which is supplied to the function.
	*
	* @param doorLocation - the location of the door.
	* @param direction    - indicates the wall the door is located on in a room.
	*/
	public Door(Location doorLocation, char direction){
		setDoor(doorLocation, direction);
	}

	// =========================
	// Mutators and Accessors
	// =========================
    
	/**
	* Mutator for instance variables - sets the room parameters.
	*
	* @param doorLocation - new location of the door.
	* @param direction    - new direction of the door.
	*/
	public void setDoor(Location doorLocation, char direction){
		this.doorLocation = doorLocation;
		this.direction = direction;
	}

	/**
	* Accessor for doorLocation. 
	*
	* @param  none.
	* @return The location of the door.
	*/   
	public Location getDoorLocation(){
		return doorLocation;
	}

	/**
	* Accessor for direction. 
	*
	* @param  none.
	* @return The direction of the door.
	*/   
	public char getDirection(){
		return direction;
	}

}