import java.io.*;

//v1.5 used in leaderboard function

public class writeFile {
	 
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
}

