package dungeon;
public class Location {
	
	/**
	* x and y coordinates of the point on the plane. 
	*/
	private int x, y, roomNumber;

	// =========================
	// Constructors
	// =========================

	/**
	* Default constructor - this initialises x, y and roomNumber to the point (0, 0, 1).
	*/
	public Location() {
		setLocation(0, 0, 1);
	}

	/**
	* Three-parameter version of the constructor. Initialiases (x, y, roomNumber) to
	* a location, which is supplied to the function.
	*
	* @param x - x-coordinate of the location.
	* @param y - y-coordinate of the location.
	* @param roomNumber - the room number of the location.
	*/
	public Location(int x, int y, int roomNumber) {
		setLocation(x, y, roomNumber);
	}

	// =========================
	// Mutators and Accessors
	// =========================

	/**
	* Mutator for instance variables - sets the coordinates of the location.
	*
	* @param x - new x-coordinate for this location.
	* @param y - new y-coordinate for this location.
	* @param roomNumber - new room number for this location.
	*/
	public void setLocation(int x, int y, int roomNumber) {
	  	this.x = x;
	  	this.y = y;
		this.roomNumber = roomNumber;
  	}

	/**
	* Mutator for x.
	*
	* @param x - new x-coordinate for this location.
	*/
	public void setX(int x) {
    	this.x = x;
	}

	/**
	* Mutator for y.
	* @param y - new y-coordinate for this location.
	*/
    public void setY(int y) {
    	this.y = y;
    }

	/**
	* Mutator for roomNumber.
	* @param roomNumber - new room number for this location.
	*/
  	public void setRoomNumber(int roomNumber) {
    	this.roomNumber = roomNumber;
  	}

	/**
	* Accessor for x coordinate. 
	*
	* @param  none.
	* @return The x coordinate of this location.
	*/   
	public int getX() {
		return x;
	}

	/**
	* Accessor for y coordinate. 
	*
	* @param  none.
	* @return The y coordinate of this location.
	*/   
	public int getY() {
		return y;
	}

	/**
	* Accessor for roomNumber. 
	*
	* @param  none.
	* @return The room number of this location.
	*/   
	public int getRoomNumber() {
		return roomNumber;
	}

	// =========================
	// Additional Methods
	// =========================

	/**
	* Prints a location out as a string.
	*
	* @param  none.
	* @return void.
	*/ 
	public void printLocation() {
		System.out.printf("(%d, %d, %d)\n", x, y, roomNumber);
	}

	/**
	* Prints an array of locations out as a string.
	*
	* @param  locationArray - an array containing objects of type Location.
	* @return void.
	*/ 
	public void printLocationArray(Location[] locationArray) {
    	int arrayLength = locationArray.length;
    	for(int i=0; i<arrayLength; i++){
      		System.out.printf("(%d, %d, %d)\n", locationArray[i].getX(), locationArray[i].getY(), locationArray[i].getRoomNumber());
    	}
  	}

	/**
	* Returns the distance from a location to another location.
	*
	* @param  l - the location we want to calculate the distance from. 
	* @return The distance from location l as a double.
	*/ 
  	public double distanceFrom(Location l) {
    	double distance = Math.sqrt((Math.pow(x-l.getX(),2))*Math.pow(y-l.getY(),2));
    	return distance; 
  	}

	/**
	* Returns whether a location is equal to another location.
	*
	* @param  l - the location we want to compare. 
	* @return a boolean indicating whether the two locations are equal.
	*/ 
  	public boolean equals(Location l) {
    	if(x==l.getX() && y==l.getY()) {
      	return true;
    	}
    	else {
      		return false;
    	}
  	}

}