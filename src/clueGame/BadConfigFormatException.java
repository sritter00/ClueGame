package clueGame;

/**
 * BadConfigFormatException: Exception for bad config file.
 * 
 * author: Shane Ritter
 * author: Carter Gorling
 */

public class BadConfigFormatException extends Exception {

	BadConfigFormatException(){
		System.out.println("Error in the format of file, please check file format and try again");
	}
	BadConfigFormatException(String message){
		System.out.println(message);
	}
}
