package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

//Authors: Amelia Atiles and Connor Price
public class BadConfigFormatException extends Exception {
	
// Format exception errors!
public BadConfigFormatException()
{
	super("Error: Bad Format Configuration");
}
public BadConfigFormatException(String message) throws FileNotFoundException
{
	super("Error: Bad Configuration on file: " + message);
	PrintWriter logFile = new PrintWriter("logfile.txt");
	logFile.println("Error: Bad Configuration on file: " + message);
	logFile.close();
}
}
