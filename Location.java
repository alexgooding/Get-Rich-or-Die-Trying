import java.util.Random;

public class Location{
	
	/**
   * x and y co-ordinates of the point on the plane. 
   */
	private int x, y, roomNumber;

	// =========================
  // Constructors
  // =========================

  /**
   * Default constructor - this initialises x, y and roomNumber to the point (0, 0, 1).
   */
	public Location(){
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
	public Location(int x, int y, int roomNumber){
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
  public void setLocation(int x, int y, int roomNumber){
  	this.x = x;
  	this.y = y;
		this.roomNumber = roomNumber;
  }

  /**
   * Accessor for x co-ordinate. 
   *
   * @param  none.
   * @return The x co-ordinate of this location.
   */   
	public int getX(){
		return x;
	}

  /**
   * Accessor for y co-ordinate. 
   *
   * @param  none.
   * @return The y co-ordinate of this location.
   */   
	public int getY(){
		return y;
	}
	
	/**
   * Accessor for roomNumber. 
   *
   * @param  none.
   * @return The room number of this location.
   */   
	public int getRoomNumber(){
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
  public void printLocation(){
    System.out.printf("(%d, %d, %d)\n", x, y, roomNumber);
  }

  /**
   * Prints an array of locations out as a string.
   *
   * @param  locationArray - an array containing objects of type Location.
   * @return void.
   */ 
  public void printLocationArray(Location[] locationArray){
    int arrayLength = locationArray.length;
    for(int i=0; i<arrayLength; i++){
      System.out.printf("(%d, %d, %d)\n", locationArray[i].getX(), locationArray[i].getY(), locationArray[i].getRoomNumber());
    }
  }
	
  public double distanceFrom(Location a){
    double distance = Math.sqrt((Math.pow(x-a.getX(),2))*Math.pow(y-a.getY(),2));
    return distance; 
  }

}
