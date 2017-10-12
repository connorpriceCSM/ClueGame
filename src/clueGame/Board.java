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
	// Get
	public BoardCell getCellAt(int row, int col)
	{
		BoardCell cell =  grid[row][col];
		return cell;

	}
	// create lists to keep track of targets, rooms, etc
	// load the rooms and boards. Give it a max size of 50 as reccomended.
	public void initialize() {
		
		legend = new HashMap<Character,String>();
		grid  = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		
		try
		{
			loadRoomConfig();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		try
		{
			loadBoardConfig();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}


	}
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
	public void calcAdjacencies()
	{

	}
	public void calcTargets(BoardCell startCell, int pathLength)
	{

	}
}
