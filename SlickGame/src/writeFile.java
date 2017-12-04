import java.io.*;

//v1.5 used in leaderboard function

public class writeFile {
	 
	public static void main (String [] args) 
	{
		//sets the filename
		String fileName = "leaderboard.txt";
		
		//gives test data to check 
		int test1 = 0;
		int test2 = 3;
		int test3 = 1;
		
		
		try {
			
			FileWriter fileWriter = new FileWriter(fileName);
			
			//buffers the file writer
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			//writes to file
			bufferedWriter.write(String.valueOf(test1));
			bufferedWriter.newLine();
			bufferedWriter.write(String.valueOf(test2));
			bufferedWriter.newLine();
			bufferedWriter.write(String.valueOf(test3));
			bufferedWriter.newLine();
			bufferedWriter.close();
					
		}
		
		catch (IOException ex) {
			//error message
			System.out.println("Error writing to file" + fileName);
		}
	}
}

