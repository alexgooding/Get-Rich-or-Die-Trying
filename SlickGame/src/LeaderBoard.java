import java.io.*;
import java.util.ArrayList;

public class LeaderBoard {
	
	//version v1.0: Ray and David: Initial Create -- combine the writeFile.java and readFile.java to this

	public static void write (String fileName, int highestScore, int secondScore, int thirdScore) 
	{
		//sets the filename

		try {
			
			FileWriter fileWriter = new FileWriter(fileName);
			
			//buffers the file writer
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			//writes to file
			bufferedWriter.write(String.valueOf(highestScore));
			bufferedWriter.newLine();
			bufferedWriter.write(String.valueOf(secondScore));
			bufferedWriter.newLine();
			bufferedWriter.write(String.valueOf(thirdScore));
			bufferedWriter.newLine();
			bufferedWriter.close();
					
		}
		
		catch (IOException ex) {
			//error message
			System.out.println("Error writing to file" + fileName);
		}
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
