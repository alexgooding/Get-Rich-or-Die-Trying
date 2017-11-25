public class LocationTest{ 
    
  	//Tester for the location class 
  	public static void main(String[] args){
		//Can we define a location?
		Location testLocation1 = new Location(1,3,1);

		//Can the location be printed?
		System.out.println("The testLocation1 is: ");
		testLocation1.printLocation();

		//Can we return the co-ordinates of a room?
		System.out.println("The x co-ordinate of testLocation1 is: " + testLocation1.getX());
		System.out.println("The y co-ordinate of testLocation1 is: " + testLocation1.getY());

		//Can the room number be returned?
		System.out.println("The room number testLocation1 is: " + testLocation1.getRoomNumber());

		//Can the location be updated - i.e. step to the right?
		testLocation1 = new Location((testLocation1.getX() + 1), testLocation1.getY(), testLocation1.getRoomNumber());
		System.out.println("We move one step to the right along the x axis, the new location is: "); 
		testLocation1.printLocation();

		//Can negative numbers be stored as a room number?
		Location testLocation2 = new Location(1,6,-1);
		System.out.println("The testLocation2 is: "); 
		testLocation2.printLocation();

		//Can we return the distance between two locations?
		System.out.println("The distance between testLocation1 and testLocation2 is : " + testLocation1.distanceFrom(testLocation2));
	}

} 