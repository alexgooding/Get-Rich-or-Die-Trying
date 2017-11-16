public class RoomTest{

	public static void main(String[] args){

		//Testing Room and Location classes.

		//Generate a new location and a new room.
		Location testLocation = new Location();
		Room testRoom = new Room(4, testLocation, 2, 1);
		//Update testLocation to a random location within testRoom
		testLocation = testRoom.randomRoomLocation();
		System.out.print("The coordinates of the test location within testRoom are ");
		testLocation.printLocation();
		
		//Testing the boundaries of a room.

		System.out.print("The coordinates of the bottom left corner of the room are ");
		testRoom.getRoomLocation().printLocation();
		//Generate the array of boundary locations.
		Location[] boundaries = testRoom.getRoomBoundaries();
		System.out.println("The boundaries of testRoom are:");
		testLocation.printLocationArray(boundaries);
		//Print door locations.
		System.out.println("The door locations of testRoom are:");
		for(int i=0; i<testRoom.getNumberOfDoors(); i++){
			testRoom.getDoorLocations().get(i).printLocation();
		}
	}

}
