import java.io.*;
import java.util.ArrayList;

//v1.5 used in leaderboard function - read/write works but is not implemented with program yet

public class readFile {
	
	public void readFile() {
		
	}
	
    public static ArrayList<Integer> read(String fileName) {

        // The file containing the leaderboard information
        //String fileName = "leaderboard.txt";

        // This will reference one line at a time
        String line = null;
        ArrayList<Integer> leaderboardScore = new ArrayList<Integer>();

        try {
        	
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
            	leaderboardScore.add(Integer.parseInt(line));
            }   

            // Close files upon reading
            bufferedReader.close();         
        }
        //File not found 
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + fileName + "'");                
        }
        //Error reading file
        catch(IOException ex) {
            System.out.println("Error reading file '"  + fileName + "'");                  
           
        }
        return leaderboardScore;
    }
}