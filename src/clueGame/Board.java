package clueGame;


import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import clueGame.BoardCell;

import java.util.HashSet;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.io.FileReader;


//Authors: Amelia Atiles and Connor Price
public class Board {
	// variable used for singleton pattern

	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private HashSet<BoardCell> visited;
	private HashSet<BoardCell> targets;
	private Map<Character, String> legend;
	private BoardCell[][] grid;
	private static Board theInstance = new Board();

	private int numRows,numCols;
	final static int MAX_BOARD_SIZE = 50;
	public static final int NUM_ROWS = 23;
	public static final int NUM_COLUMNS = 22;


	private String boardConfigFile;
	private String roomConfigFile;


	// constructor is private to ensure only one can be created
	private Board() 
	{


	}

	// this method returns the only Board
	public static Board getInstance() 
	{
		return theInstance;
	}


	public Map<Character, String> getLegend() {
		// TODO Auto-generated method stub
		return legend;
	}

	public int getNumRows() {
		// TODO Auto-generated method stub
		return numRows;
	}

	public int getNumColumns() {
		// TODO Auto-generated method stub
		return numCols;
	}


	//Set the config files correctly.
	public void setConfigFiles(String boardConfig, String roomConfig) {
		boardConfigFile = boardConfig;
		roomConfigFile = roomConfig;

	}
	
	// load the rooms and boards.
	// both methods have to throw exceptions so try/catch blocks are used
	public void initialize() {
		try
		{

			loadRoomConfig();
			loadBoardConfig();
		}
		//our FileNotFound and BadConfig Exceptions!
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}


	}


	// read the legend file
	// make sure that the format (including commas and spaces)  of each entry is correct
	// throw errors otherwise
	// if the entries are good, load them into the legend (our Map)
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException
	{
		FileReader fileReader = new FileReader(roomConfigFile);
		Scanner legendReader = new Scanner(fileReader);
		// NECESSARY FOR EXCEPTION TESTS BECAUSE INITIALIZE ISNT CALLED IN THEM
		legend = new HashMap<Character,String>();
		while(legendReader.hasNextLine())
		{
			String entry = legendReader.nextLine();
			//BIG BIG THING TO SEPARATE VALUES
			//Does it wherever it finds a comma
			//Should be exactly three parts
			//[character, roomName, cardType]
			//Otherwise. Throw dat Error!
			String[] parts = entry.split(",");
			if(parts.length != 3)
			{   
				legendReader.close();
				throw new BadConfigFormatException("The  general room file format is incorrect for "+ entry); 
			}
			//get parts[0] which is our character
			//charAt(0) is our character
			char character =   new Character(parts[0].charAt(0));
			//Get parts[1] and parts[2] 
			// Check this one to be careful.
			// trim eliminates whitespace which is big given the layout of the legend file.
			String roomName =   new String(parts[1].trim());
			String cardName =  new String(parts[2].trim());
			// Must check card name to make sure it isn't anything other than "Other" or "Card"
			if ((!cardName.equals("Card")) && (!cardName.equals("Other")))
			{
				legendReader.close();
				throw new BadConfigFormatException("The card format file is incorrect for " + entry);
			}
			legend.put(character, roomName);


		}
		legendReader.close();



	}
	// Read the board layout file
	// Put each string (character) into  it's own little board cell spot
	//  Check to make sure each spot is filled and that each character actually corresponds to a room/
	//throw exceptions otherwise
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException
	{
		//
		FileReader fileReader = new FileReader(boardConfigFile);
		Scanner boardReader = new Scanner(fileReader);
		int currentRow = 0;
		// NECESSARY FOR EXCEPTION TESTS BECAUSE INITIALIZE ISNT CALLED IN THEM
		grid  = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		while(boardReader.hasNextLine())
		{
			// Like loadRoomConfig, we'll divide all our entries by commas!
			// THIS IS WHY WE USED CSV FILES, it inserts the commas automatically!
			// With entries, we're going to read the row, then read the column across with nextLine()
			String entries = boardReader.nextLine();
			String[] parts = entries.split(",");
			int currentColumn = 0;

			// One time if statement to set our column length after the entries are read into an array
			//i.e 22 or 23.
			if(currentRow == 0)
			{
				numCols = parts.length;
			}
			// Checking to make sure the grid is full and every row has the same number of columns
			// If not, throw the error.
			if(numCols != parts.length)
			{
				boardReader.close();
				throw new BadConfigFormatException("The board structure is incorrect for " + entries );
			}

			//currentColumn is our variable here
			while(currentColumn < numCols)
			{
				//getting the character from the created array
				Character roomChar = (Character)parts[currentColumn].charAt(0);
				//  room check
				String room = legend.get(roomChar);
				//Check to make sure you are getting a room that acutally exists!!
				//Otherwise, (you guessed it), error!
				if(room == null)
				{
					boardReader.close();
					throw new BadConfigFormatException("No room exists for " + roomChar);
				}
				// Create the cell and add it to the grid
				BoardCell cell = new BoardCell(currentRow, currentColumn, parts[currentColumn]);
				grid[currentRow][currentColumn] = cell;
				// increment the column
				currentColumn++;
			}
			// once all the cells in the row have been filled
			// i.e currentColumn = numCols
			// increment the row  and start the loop over!
			currentRow++;
		}
		// when we reach the last row of the board layout, set the number of rows 
		// close the scanner
		numRows = currentRow++;
		boardReader.close();
	}

	/*
	 * An older method that I tried earlier w/out exception handling.
	 * Might do more with it later
	 * 
	// Method to read and load rooms!!
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException
	{

      FileReader fileReader = new FileReader(roomConfigFile);
      // very important to use space for useDelimiter
      Scanner legendReader =  new Scanner(fileReader).useDelimiter(", ");
      while(legendReader.hasNext())
      {
    	  //reads the character first
    	  char character = legendReader.next().charAt(0);
    	  //Reads the room name
    	  String roomName = legendReader.next();
    	  //Puts the combination into the map and moves on to the next line
    	  legend.put(character, roomName);
    	  legendReader.nextLine();

      }
      legendReader.close();
	}

	// This is a tricky one...
	// In short, we're using double while loops to read the rows and columns.
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException
	{
		FileReader fileReader = new FileReader(boardConfigFile);
		Scanner rowReader = new Scanner(fileReader);
		Scanner colReader;
		int currentRow = 0;
		// hasNextLine is an important method here because we're reading form a set square grid
		while(rowReader.hasNextLine())
		{
			int currentColumn = 0;
			colReader = new Scanner(rowReader.nextLine()).useDelimiter(",");
			while(colReader.hasNext())
			{
				//AGAIN VERY IMPORTANT TO USE A STRING HERE
				String entry = colReader.next();
				// create cell and enter it in our grid. We have solid values now.
				BoardCell cell =  new BoardCell(currentRow,currentColumn,entry);
				grid[currentRow][currentColumn] = cell;
				currentColumn++;
			}
			if(numCols < currentColumn)
			{
				numCols = currentColumn;
			}
			// once the columns are out, increment the row.
			currentRow++;
		}
		numRows = currentRow;
		// cant close colReader because of the declaration...whoops
		rowReader.close();
	}
	 */

	//PART III Skeleton methods to fail tests!
	// Get the boardcell;
	public BoardCell getCellAt(int row, int col)
	{
			BoardCell cell =  grid[row][col];
			return cell;

	}
	public Set<BoardCell> getAdjList(int row, int col)
	{
		Set<BoardCell> set =  new HashSet<BoardCell>();
		// adding 1 cell to ensure all tests fail for part III
		set.add(new BoardCell(0,0));
		return set;
	}
	public void calcAdjacencies()
	{

	}
	public void calcTargets(int row, int col, int pathLength)
	{

	}
	public void findAllTargets(int row, int col, int pathLength)
	{
		
	}
	public Set<BoardCell> getTargets()
	{
		Set<BoardCell> set =  new HashSet<BoardCell>();
		return set;
	}
}
