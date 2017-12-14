package tests;
import java.util.ArrayList;

import dungeon.Dungeon;

public class DungeonTest{

	public static void main(String[] args){
		
		//Create a new dungeon.
		Dungeon testDungeon = new Dungeon(30, 1);

		//Prints out the room locations.
		/*for(int i=0; i < testDungeon.getRooms().size(); i++){
			testDungeon.getRooms().get(i).getRoomLocation().printLocation();
		}*/

		//Makes an array that represents a map to visualise the dungeon.
		String[][] map = new String[100][75];
		for(int i=0; i<100; i++){
			for(int j=0; j<75; j++){
				map[i][j] = " ";
			}
		}


		//Stores the boundaries of the rooms as dots in the map array.
		for(int i=0; i<testDungeon.getRooms().size(); i++){
			for(int j=0; j<testDungeon.getRooms().get(i).getRoomBoundaries().length; j++){
				map[testDungeon.getRooms().get(i).getRoomBoundaries()[j].getX()][testDungeon.getRooms().get(i).getRoomBoundaries()[j].getY()] = ".";
			}
		}

		//Stores the door locations of the rooms as circles in the map array.
		for(int i=0; i<testDungeon.getRooms().size(); i++){
			for(int j=0; j<testDungeon.getRooms().get(i).getDoorLocations().size(); j++){
				map[testDungeon.getRooms().get(i).getDoorLocations().get(j).getDoorLocation().getX()][testDungeon.getRooms().get(i).getDoorLocations().get(j).getDoorLocation().getY()]= "o";
			}
		}

		map[50][50] = "x"; //x represents the first rooms location.

		//Stores the locations of gold in the rooms as a ¤.
		for(int i=0; i<testDungeon.getRooms().size(); i++){
			for(int j=0; j<testDungeon.getRooms().get(i).getRoomGold().size(); j++){
				map[testDungeon.getRooms().get(i).getRoomGold().get(j).getItemLocation().getX()][testDungeon.getRooms().get(i).getRoomGold().get(j).getItemLocation().getY()]= "¤";
			}
		}
		
		//Allows you to see what order the rooms where generated in.
		/*for(int i=0; i<testDungeon.getRooms().size(); i++){
			map[testDungeon.getRooms().get(i).getRoomLocation().getX()+testDungeon.getRooms().get(i).getRoomSize()/2][testDungeon.getRooms().get(i).getRoomLocation().getY()+testDungeon.getRooms().get(i).getRoomSize()/2] = "" + testDungeon.getRooms().get(i).getRoomLocation().getRoomNumber();
		}*/

		//Prints out the map array.
		for(int i=99; i>=0; i--){
			for(int j=0; j<75; j++){
				System.out.printf("%s", map[i][j]);
			}
			System.out.printf("\n");		
		}
	
		//Prints out the number of rooms in the dungeon
		System.out.println("The number of rooms in the dungeon is: " + testDungeon.getRooms().size());

	}

}