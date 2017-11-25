import java.util.*;
import java.util.Random;
import java.util.ArrayList;

public class Dungeon{

	/**
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
	private ArrayList<Location> dungeonDoorLocations = new ArrayList<Location>();
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
		storeDungeonWalls();
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
				Room newRoom = new Room(initialRoom.getRoomSize(), newLocation, randomDoorNumber, 1);
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

		for(int i=0; i<getRooms().size(); i++){
		  	for(int j=0; j<getRooms().get(i).getDoorLocations().size(); j++){
		    	dungeonDoorLocations.add(getRooms().get(i).getDoorLocations().get(j).getDoorLocation());
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
}


