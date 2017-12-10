package ui;
import java.io.*;
import java.util.ArrayList;

public class Leaderboard {
	
	// v1.0: Ray and David: Initial commit -- combine the writeFile.java and readFile.java to this

	public static void write (String fileName, int highestScore, int secondScore, int thirdScore) {
		// Sets the file name
		try {
			FileWriter fileWriter = new FileWriter(fileName);

			// Buffers the file writer
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			// Writes to file
			bufferedWriter.write(String.valueOf(highestScore));
			bufferedWriter.newLine();
			bufferedWriter.write(String.valueOf(secondScore));
			bufferedWriter.newLine();
			bufferedWriter.write(String.valueOf(thirdScore));
			bufferedWriter.newLine();
			bufferedWriter.close();
		}

		catch (IOException ex) {
			// Error message
			System.out.println("Error writing to file" + fileName);
		}
	}

	public static ArrayList<Integer> read(String fileName) {
        // This will reference one line at a time
        String line = null;
        ArrayList<Integer> leaderboardScore = new ArrayList<Integer>();

        try {
            // FileReader reads text files in the default encoding
            FileReader fileReader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
            	leaderboardScore.add(Integer.parseInt(line));
            }   

            // Close files upon reading
            bufferedReader.close();
        }
        
        // File not found
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");                
        }
        
        // Error reading file
        catch(IOException ex) {
            System.out.println("Error reading file '"  + fileName + "'");
        }
        return leaderboardScore;
    }

}
