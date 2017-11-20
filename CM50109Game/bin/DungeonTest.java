public class DungeonTest{

	public static void main(String[] args){

		//Create a new dungeon.
		Dungeon testDungeon = new Dungeon(10, 1);

		//Prints out the room locations.
		for(int i=0; i < testDungeon.getRooms().size(); i++){
			testDungeon.getRooms().get(i).getRoomLocation().printLocation();
		}

		//Makes an array that represents a map to visualise the dungeon.
		String[][] map = new String[100][100];
		for(int i=0; i<100; i++){
			for(int j=0; j<100; j++){
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

		//Prints out the map array.
		for(int i=0; i<100; i++){
			for(int j=0; j<100; j++){
				System.out.printf("%s", map[i][j]);
			}
			System.out.printf("\n");		
		}
	}

}