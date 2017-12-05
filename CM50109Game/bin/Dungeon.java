import java.util.*;
import java.util.Random;
import java.util.ArrayList;


//version 1.1: Ray: Add Location arrayList for gold and bot
//version 1.2: Jess and Alex: Add Room locations array list and Door array list. Added a checkDoors function to 
// 			   check if a door is redundant or not.

public class Dungeon{

	/*
	* numberOfRooms is an integer representing how many rooms we want to generate.
	* dungeonDifficulty is an integer from 1-3 representing the difficulty of the dungeon.
	* rooms is a list of all the rooms stored in the dungeon.
	* dungeonBoundaries is a list of all the boundaries of the dungeon without doors.
	* dungeonDoorLocations is a list of all the door locations in the dungeon.
	*/
	private int numberOfRooms;
	private int dungeonDifficulty;
	private ArrayList<Room> rooms = new ArrayList<Room>();
	private ArrayList<Location> dungeonWalls = new ArrayList<Location>();
	private ArrayList<Door> dungeonDoors = new ArrayList<Door>(); //v1.2
	private ArrayList<Location> dungeonRoomLocations = new ArrayList<Location>(); //v1.2
	private ArrayList<Location> dungeonDoorLocations = new ArrayList<Location>();
	private ArrayList<Location> dungeonGoldLocations = new ArrayList<Location>();	//v1.1
	private ArrayList<Location> dungeonBotLocations = new ArrayList<Location>();	//v1.1
	private int roomCounter = 1;								

	// =========================
	// Constructors
	// =========================

	/**
	* Default constructor - this initialises numberOfRooms and dugeonDifficulty to (10, 1).
	*/
	public Dungeon(){
		setDungeon(10, 1);
	}

	/**
	* Four-parameter version of the constructor. Initialiases (numberOfRooms, dungeonDifficulty) to
	* a dungeon, which is supplied to the function.
	*
	* @param numberOfRooms     - the number of rooms in the dungeon.
	* @param dungeonDifficulty - the difficulty of the dungeon.
	*/
	public Dungeon(int numberOfRooms, int dungeonDifficulty){
		setDungeon(numberOfRooms, dungeonDifficulty);
	}

	// =========================
	// Mutators and Accessors
	// =========================

	/**
	* Mutator for instance variables - sets the room parameters.
	*
	* @param numberOfRooms     - new number of rooms.
	* @param dungeonDifficulty - new difficulty of dungeon.
	*/
	public void setDungeon(int numberOfRooms, int dungeonDifficulty){
		this.numberOfRooms = numberOfRooms;
		this.dungeonDifficulty = dungeonDifficulty;

		Room firstRoom = new Room();
		rooms.add(firstRoom);
		generateRooms(firstRoom);
		storeDungeonDoors(); //v1.2
		storeDungeonRoomLocations(); //v1.2
		checkDoors(); //v1.2
		storeDungeonDoorLocations(); //v1.2
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
	public int getNumberOfRooms(){
		return numberOfRooms;
	}

	/**
	* Accessor for dungeonDifficulty. 
	*
	* @param  none.
	* @return The difficulty of the dungeon.
	*/   
	public int getDungeonDifficulty(){
		return dungeonDifficulty;
	}

	/**
	* Accessor for rooms. 
	*
	* @param  none.
	* @return The list of rooms.
	*/   
	public ArrayList<Room> getRooms(){
		return rooms;
	}

	/**
	* Accessor for dungeonWalls. 
	*
	* @param  none.
	* @return The list of dungeon boundaries.
	*/  
	public ArrayList<Location> getDungeonWalls(){
		return dungeonWalls;
	}

	/**
	* Accessor for dungeonDoorLocations. 
	*
	* @param  none.
	* @return The list of the locations of all the doors in the dungeon.
	*/  
	public ArrayList<Location> getDungeonDoorLocations(){
		return dungeonDoorLocations;
	}	
	
	//v1.1
	/**
	* Accessor for dungeonGoldLocations. 
	*
	* @param  none.
	* @return The list of the locations of all the gold in the dungeon.
	*/  
	public ArrayList<Location> getDungeonGoldLocations(){
		return dungeonGoldLocations;
	}	
	
	//v1.1
	/**
	* Accessor for dungeonBotLocations. 
	*
	* @param  none.
	* @return The list of the locations of all the bot in the dungeon.
	*/
	public ArrayList<Location> getDungeonBotLocations(){
		return dungeonBotLocations;
	}

	// =========================
	// Additional Methods
	// =========================

	/**
	* Generates rooms of the same size with a random amount of doors and in a random pattern. 
	* The rooms are stored in the rooms list. 
	*
	* @param  initialRoom - the previous room in the dungeon complex.
	* @return void.
	*/  
	public void generateRooms(Room initialRoom){
		for(int i=0; i<initialRoom.getNumberOfDoors(); i++){
			Location newLocation = new Location();
			switch(initialRoom.getDoorLocations().get(i).getDirection()){
				case 's':
					newLocation = new Location(initialRoom.getRoomLocation().getX(), initialRoom.getRoomLocation().getY()-initialRoom.getRoomSize(), 1);
					break;
				case 'e':
					newLocation = new Location(initialRoom.getRoomLocation().getX()+initialRoom.getRoomSize(), initialRoom.getRoomLocation().getY(), 1);
					break;
				case 'n':
					newLocation = new Location(initialRoom.getRoomLocation().getX(), initialRoom.getRoomLocation().getY()+initialRoom.getRoomSize(), 1);
					break;	
				case 'w':
					newLocation = new Location(initialRoom.getRoomLocation().getX()-initialRoom.getRoomSize(), initialRoom.getRoomLocation().getY(), 1);
					break;
			}
			boolean repeatFlag = false; //Flags whether the room already exists in the rooms list.
			for(int j=0; j<rooms.size(); j++){
				repeatFlag = newLocation.equals(rooms.get(j).getRoomLocation());
				if(repeatFlag == true){
					j = rooms.size();
				}
			}
			if(repeatFlag==false){
				Random rand = new Random();
				//int randomDoorNumber = rand.nextInt(4);
				double nextDoorNumber = rand.nextGaussian()*1+1.5;
				int randomDoorNumber; 
				randomDoorNumber = (int) nextDoorNumber;
				if(randomDoorNumber>4){
					randomDoorNumber = 4;
				}
				if(randomDoorNumber<0){
					randomDoorNumber = 0;
				}
				Room newRoom = new Room(initialRoom.getRoomSize(), newLocation, randomDoorNumber, 1, true);
				if(roomCounter>=numberOfRooms){ //Limits the number of rooms to the correct amount.
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
		    	dungeonWalls.add(getRooms().get(i).getRoomBoundaries()[j]);
		    }
		} 

		for(int i=0; i<dungeonWalls.size(); i++){
		  	for(int j=0; j<dungeonDoorLocations.size(); j++){
		    	if(dungeonWalls.get(i).equals(dungeonDoorLocations.get(j)) == true){
		      		dungeonWalls.remove(i);
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
		    	dungeonGoldLocations.add(getRooms().get(i).getRoomGold().get(j).getItemLocation());
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
		    	dungeonBotLocations.add(getRooms().get(i).getRoomBotLocation());
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
		    	dungeonDoors.add(getRooms().get(i).getDoorLocations().get(j));
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
		for(int i=0; i<dungeonDoors.size(); i++){
		    dungeonDoorLocations.add(dungeonDoors.get(i).getDoorLocation());
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
			dungeonRoomLocations.add(rooms.get(i).getRoomLocation());
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
		int n = dungeonDoors.size();
		for(int i=0; i<n; i++){
			switch(dungeonDoors.get(i).getDirection()){
				//Check if a new room was created from this door.
				case 's':
					for(int j=0;j<dungeonRoomLocations.size();j++){
						if(new Location(dungeonDoors.get(i).getDoorLocation().getX()-rooms.get(0).getRoomSize()/2, 
								dungeonDoors.get(i).getDoorLocation().getY()-rooms.get(0).getRoomSize(),1).equals(dungeonRoomLocations.get(j))){
							flag = true;
							break;
						} else {
							flag = false;
						}
					}
					break;		
				case 'e':
					for(int j=0;j<dungeonRoomLocations.size();j++){
						if(new Location(dungeonDoors.get(i).getDoorLocation().getX(), 
								dungeonDoors.get(i).getDoorLocation().getY()-rooms.get(0).getRoomSize()/2,1).equals(dungeonRoomLocations.get(j))){
							flag = true;
							break;
						} else {
							flag = false;
						}
					}
					break;
				case 'n':
					for(int j=0;j<dungeonRoomLocations.size();j++){
						if(new Location(dungeonDoors.get(i).getDoorLocation().getX()-rooms.get(0).getRoomSize()/2, 
								dungeonDoors.get(i).getDoorLocation().getY(),1).equals(dungeonRoomLocations.get(j))){
							flag = true;
							break;
						} else {
							flag = false;
						}
					}
					break;
				case 'w':
					for(int j=0;j<dungeonRoomLocations.size();j++){
						if(new Location(dungeonDoors.get(i).getDoorLocation().getX()-rooms.get(0).getRoomSize(), 
								dungeonDoors.get(i).getDoorLocation().getY()-rooms.get(0).getRoomSize()/2 ,1).equals(dungeonRoomLocations.get(j))){
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
				dungeonDoors.remove(i);
				n = n-1;
				i = i-1;
			}
		}
		
	}
	
}