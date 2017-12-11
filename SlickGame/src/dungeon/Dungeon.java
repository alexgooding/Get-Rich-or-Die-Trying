package dungeon;
import java.util.Random;
import java.util.ArrayList;


// Version 1.1: Ray: Add Location arrayList for gold and bot
// Version 1.2: Jess and Alex: Add Room locations array list and Door array list.
// 				Added checkDoors function to check if a door is redundant or not.

public class Dungeon {

	/*
	* numberOfRooms is an integer representing how many rooms we want to generate.
	* difficulty is an integer from 1-3 representing the difficulty of the dungeon.
	* rooms is a list of all the rooms stored in the dungeon.
	* walls is a list of all the room boundaries in the dungeon
	* doors is a list of all the door locations in the dungeon
	* roomLocations is a list of all the room locations in the dungeon
	* doorLocations is a list of all the door locations in the dungeon
	* goldLocations is a list of all gold locations in the dungeon
	* botLocations is a list of all bot locations in the dungeon
	* dungeonDoorLocations is a list of all the door locations in the dungeon.
	*/
	private int numberOfRooms;
	private int difficulty;
	private ArrayList<Room> rooms = new ArrayList<Room>();
	private ArrayList<Location> walls = new ArrayList<Location>();
	private ArrayList<Door> doors = new ArrayList<Door>(); // v1.2
	private ArrayList<Location> roomLocations = new ArrayList<Location>(); // v1.2
	private ArrayList<Location> doorLocations = new ArrayList<Location>();
	private ArrayList<Location> goldLocations = new ArrayList<Location>(); // v1.1
	private ArrayList<Location> botLocations = new ArrayList<Location>(); // v1.1
	private int roomCounter = 1;

	// =========================
	// Constructors
	// =========================

	/**
	* Default constructor - Initialises numberOfRooms and difficulty to (10, 1).
	*/
	public Dungeon() {
		setDungeon(10, 1);
	}

	/**
	* Four-parameter version of the constructor. 
	* Initialiases (numberOfRooms, difficulty) to a dungeon, which is 
	* supplied to the function.
	*
	* @param numberOfRooms     - the number of rooms in the dungeon.
	* @param difficulty - the difficulty of the dungeon.
	*/
	public Dungeon(int numberOfRooms, int dungeonDifficulty) {
		setDungeon(numberOfRooms, dungeonDifficulty);
	}

	// =========================
	// Mutators and Accessors
	// =========================

	/**
	* Mutator for instance variables - sets the room parameters.
	*
	* @param numberOfRooms     - new number of rooms.
	* @param difficulty - new difficulty of dungeon.
	*/
	public void setDungeon(int numberOfRooms, int dungeonDifficulty) {
		this.numberOfRooms = numberOfRooms;
		this.difficulty = dungeonDifficulty;

		Room firstRoom = new Room();
		rooms.add(firstRoom);
		generateRooms(firstRoom);
		storeDungeonDoors(); // v1.2
		storeDungeonRoomLocations(); // v1.2
		checkDoors(); // v1.2
		storeDungeonDoorLocations(); // v1.2
		storeDungeonWalls();
		storeDungeonGold();
		storeDungeonBot();
	}


	/**
	* Accessor for numberOfRooms. 
	*
	* @param  none.
	* @return The number of rooms.
	*/   
	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	/**
	* Accessor for difficulty. 
	*
	* @param  none.
	* @return The difficulty of the dungeon.
	*/   
	public int getDungeonDifficulty() {
		return difficulty;
	}

	/**
	* Accessor for rooms. 
	*
	* @param  none.
	* @return The list of rooms.
	*/   
	public ArrayList<Room> getRooms() {
		return rooms;
	}

	/**
	* Accessor for walls. 
	*
	* @param  none.
	* @return The list of dungeon boundaries.
	*/  
	public ArrayList<Location> getWalls() {
		return walls;
	}

	/**
	* Accessor for doorLocations. 
	*
	* @param  none.
	* @return The list of the locations of all the doors in the dungeon.
	*/  
	public ArrayList<Location> getDoorLocations() {
		return doorLocations;
	}	
	
	//v1.1
	/**
	* Accessor for goldLocations. 
	*
	* @param  none.
	* @return The list of the locations of all the gold in the dungeon.
	*/  
	public ArrayList<Location> getGoldLocations() {
		return goldLocations;
	}	
	
	//v1.1
	/**
	* Accessor for botLocations. 
	*
	* @param  none.
	* @return The list of the locations of all the bot in the dungeon.
	*/
	public ArrayList<Location> getBotLocations() {
		return botLocations;
	}

	// =========================
	// Additional Methods
	// =========================

	/**
	* Generates rooms of equal size with random number of doors in a random 
	* pattern. 
	* The rooms are stored in a list. 
	*
	* @param  initialRoom - the previous room in the dungeon complex.
	* @return void.
	*/  
	public void generateRooms(Room initialRoom) {
		for(int i=0; i<initialRoom.getNumberOfDoors(); i++) {
			Location newLocation = new Location();
			switch(initialRoom.getDoorLocations().get(i).getDirection()) {
				case 's':
					newLocation = new Location(initialRoom.getRoomLocation().getX(),
							initialRoom.getRoomLocation().getY()-initialRoom.getRoomSize(), 1);
					break;
				case 'e':
					
					newLocation = new Location(initialRoom.getRoomLocation().getX()+initialRoom.getRoomSize(), 
							initialRoom.getRoomLocation().getY(), 1);
					break;
				case 'n':
					newLocation = new Location(initialRoom.getRoomLocation().getX(), 
							initialRoom.getRoomLocation().getY()+initialRoom.getRoomSize(), 1);
					break;	
				case 'w':
					newLocation = new Location(initialRoom.getRoomLocation().getX()-initialRoom.getRoomSize(), 
							initialRoom.getRoomLocation().getY(), 1);
					break;
			}
			boolean repeatFlag = false; // Flags whether the room already exists in the rooms list.
			for(int j=0; j<rooms.size(); j++) {
				repeatFlag = newLocation.equals(rooms.get(j).getRoomLocation());
				if(repeatFlag == true) {
					j = rooms.size();
				}
			}
			if(repeatFlag==false){
				Random rand = new Random();
				double nextDoorNumber = rand.nextGaussian()*1+1.5;
				int randomDoorNumber; 
				randomDoorNumber = (int) nextDoorNumber;
				if(randomDoorNumber>4) {
					randomDoorNumber = 4;
				}
				if(randomDoorNumber<0) {
					randomDoorNumber = 0;
				}
				Room newRoom = new Room(initialRoom.getRoomSize(), newLocation, randomDoorNumber, 1, true);
				if(roomCounter>=numberOfRooms) { // Limits the number of rooms to the correct amount.
					return;
				}
				rooms.add(newRoom);
				roomCounter++;
				generateRooms(newRoom);
			}
		}
	}

	/**
	* Stores the boundaries of the dungeon without doors.
	* 
	*
	* @return void.
	*/  
	public void storeDungeonWalls(){
		for(int i=0; i<getRooms().size(); i++){
		  	for(int j=0; j<getRooms().get(i).getRoomBoundaries().length; j++){
		    	walls.add(getRooms().get(i).getRoomBoundaries()[j]);
		    }
		} 

		for(int i=0; i<walls.size(); i++){
		  	for(int j=0; j<doorLocations.size(); j++){
		    	if(walls.get(i).equals(doorLocations.get(j)) == true){
		      		walls.remove(i);
		    	}
		  	}
		}
	}
	
	//v1.1
	/**
	* Stores the boundaries of the golds.
	* 
	*
	* @return void.
	*/  
	public void storeDungeonGold(){
		for(int i=0; i<getRooms().size(); i++){
		  	for(int j=0; j<getRooms().get(i).getRoomGold().size(); j++){
		    	goldLocations.add(getRooms().get(i).getRoomGold().get(j).getItemLocation());
		  	}
		}
	}
	
	//v1.1
	/**
	* Stores the boundaries of the bots.
	* 
	*
	* @return void.
	*/  
	public void storeDungeonBot(){
		for(int i=0; i<getRooms().size(); i++){
		    	botLocations.add(getRooms().get(i).getRoomBotLocation());
		}	
	}

	//v1.2
	/**
	* Stores the dungeon doors.
	* 
	*
	* @return void.
	*/  
	public void storeDungeonDoors(){
		for(int i=0; i<getRooms().size(); i++){
		  	for(int j=0; j<getRooms().get(i).getDoorLocations().size(); j++){
		    	doors.add(getRooms().get(i).getDoorLocations().get(j));
		  	}
		}
		
	}
	//v1.2
	/**
	* Stores the the dungeon door locations.
	* 
	*
	* @return void.
	*/ 
	public void storeDungeonDoorLocations(){
		for(int i=0; i<doors.size(); i++){
		    doorLocations.add(doors.get(i).getDoorLocation());
		}
	}



	//v1.2
	/**
	* Stores the dungeon room locations.
	* 
	*
	* @return void.
	*/ 
	public void storeDungeonRoomLocations(){
		for(int i=0; i<rooms.size(); i++){
			roomLocations.add(rooms.get(i).getRoomLocation());
		}
	}

	//v1.2
	/**
	* Checks if the doors are redundant.
	* 
	*
	* @return void.
	*/  
	public void checkDoors(){
		boolean flag = false; //false if a door is redundant and true if the door is needed.
		int n = doors.size();
		for(int i=0; i<n; i++){
			switch(doors.get(i).getDirection()){
				//Check if a new room was created from this door.
				case 's':
					for(int j=0;j<roomLocations.size();j++){
						if(new Location(doors.get(i).getDoorLocation().getX()-rooms.get(0).getRoomSize()/2, 
								doors.get(i).getDoorLocation().getY()-rooms.get(0).getRoomSize(),1).equals(roomLocations.get(j))){
							flag = true;
							break;
						} else {
							flag = false;
						}
					}
					break;		
				case 'e':
					for(int j=0;j<roomLocations.size();j++){
						if(new Location(doors.get(i).getDoorLocation().getX(), 
								doors.get(i).getDoorLocation().getY()-rooms.get(0).getRoomSize()/2,1).equals(roomLocations.get(j))){
							flag = true;
							break;
						} else {
							flag = false;
						}
					}
					break;
				case 'n':
					for(int j=0;j<roomLocations.size();j++){
						if(new Location(doors.get(i).getDoorLocation().getX()-rooms.get(0).getRoomSize()/2, 
								doors.get(i).getDoorLocation().getY(),1).equals(roomLocations.get(j))){
							flag = true;
							break;
						} else {
							flag = false;
						}
					}
					break;
				case 'w':
					for(int j=0;j<roomLocations.size();j++){
						if(new Location(doors.get(i).getDoorLocation().getX()-rooms.get(0).getRoomSize(), 
								doors.get(i).getDoorLocation().getY()-rooms.get(0).getRoomSize()/2 ,1).equals(roomLocations.get(j))){
							flag = true;
							break;
						} else {
							flag = false;
						}
					}
					break;				
			}
			//Remove redundant doors from the door arraylist.
			if(flag == false){
				doors.remove(i);
				n = n-1;
				i = i-1;
			}
		}
		
	}
	
}