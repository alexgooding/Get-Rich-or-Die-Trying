import java.io.*;

//v1.5 used in leaderboard function - read/write works but is not implemented with program yet

public class readFile {
    public static void main(String [] args) {

        // The file containing the leaderboard information
        String fileName = "leaderboard.txt";

        // This will reference one line at a time
        String line = null;
        
        

        try {
        	
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
            	System.out.print(line);
                
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
    }
}