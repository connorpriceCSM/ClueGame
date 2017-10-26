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

	private HashMap<BoardCell, Set<BoardCell>> adjMtx;
	private HashSet<BoardCell> visited;
	private HashSet<BoardCell> targets;
	private HashMap<Character, String> legend;
	private BoardCell[][] grid;
	private static Board theInstance = new Board();

	private int numRows,numCols;
	final static int MAX_BOARD_SIZE = 50;
	public static final int NUM_ROWS = 23;
	public static final int NUM_COLUMNS = 22;
	public static final char WALKWAY_CHAR = 'W';
	public static final char CLOSET_CHAR = 'X';


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
	public void initialize() 
	{
		legend = new HashMap<Character, String>();
		grid = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();

		try
		{
			loadRoomConfig();
			loadBoardConfig();
			calcAdjacencies();
			System.out.print("Files loaded.");
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

	// Get the boardcell;
	public BoardCell getCellAt(int row, int col)
	{
		BoardCell cell =  grid[row][col];
		return cell;

	}
	// Method to get the neighbors of any cell only after they've been calculated at initalize
	public Set<BoardCell> getAdjList(int row, int col)
	{
		BoardCell cell = grid[row][col];
		return adjMtx.get(cell);
	}
	// Method that calculates adjancies for each board cell in the game.
	public void calcAdjacencies()
	{
		
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
		
		for( int i =  0; i < numRows; i++ )
		{
			for( int j = 0; j < numCols; j++)
			{
				calcSingleAdjacencyList(i,j);
			}
		}

		System.out.println("This should be working!");	
		}


	// calc list for only one cell
	public void calcSingleAdjacencyList(int row, int col)
	{
		Set<BoardCell> adjacentCells = new HashSet<BoardCell>();
		BoardCell startCell = grid[row][col];
		// if the cell is a room, there will be no adjacency list
	
		
		// if the cell is a doorway, we need to check the exit paths.
	     if(startCell.isDoorway())
		{
			setDoorWayList(row, col, startCell.getDoorDirection(), adjacentCells);
		}
		// if the cell is a pathway, there's quite a bit to do.
		else if(startCell.isPathway())
		{
			// CHECK every cell around the cell, door direction included to make sure proper doors are fit in
			checkCell(row, col - 1, adjacentCells, DoorDirection.RIGHT);
			checkCell(row, col + 1, adjacentCells, DoorDirection.LEFT);
			checkCell(row - 1, col, adjacentCells, DoorDirection.DOWN);
			checkCell(row + 1, col, adjacentCells, DoorDirection.UP);

			
		}

		adjMtx.put(startCell, adjacentCells);

	}

	// Check a door of a cell and make sure that there is a pathway next to it that can be reached
	// Add it to the adjacent list
	public void setDoorWayList(int row, int col, DoorDirection direction, Set<BoardCell> adjacentCells)
	{
		// Direction DOWN
		if((direction == DoorDirection.DOWN) && row + 1 < numRows &&  grid[row +1][col].isPathway())
		{
			adjacentCells.add(grid[row+1][col]);
		}
		// Direction UP
		else if((direction == DoorDirection.UP) && row - 1 >= 0 &&  grid[row -1][col].isPathway())
		{
			adjacentCells.add(grid[row-1][col]);
		}
		// Direction RIGHT
		else if ((direction == DoorDirection.RIGHT) && col + 1 < numCols  &&  grid[row][col +1 ].isPathway())
		{
			adjacentCells.add(grid[row][col+1]);
		}
		// Direction LEFT
		else if((direction == DoorDirection.LEFT) && col - 1 >= 0  &&  grid[row][col -1 ].isPathway())
		{
			adjacentCells.add(grid[row][col-1]);
		}


	}

	// Check any cell at all and make sure that it's a valid pathway
	// If the cell happens to fall on a door, make sure the door is reachable given it's door direction
	// Ex: You can't go upwards into a doorway that's facing right, up, or left.
	public void checkCell(int row, int col,  Set<BoardCell> adjacentCells, DoorDirection direction)
	{
		// accounts for bounds of the grid
		// There is no valid cell if any of these conditions are true
		if((row < 0) || (col < 0 ) || (row >= numRows || (col >= numCols)))
		{
			return;
		}
		BoardCell cell = grid[row][col];
		// If the cell is a room, do nothing, no reason to add this one.
		if(cell.isRoom())
		{
			return;
		}
		// if the cell is a valid pathway, it's a valid target and spot to move
		if(cell.isPathway())
		{
			adjacentCells.add(cell);
		}
		//accounts for doorways
		if(cell.isDoorway())
		{
			DoorDirection doorDirection = cell.getDoorDirection();
			if(doorDirection == direction)

			{
				adjacentCells.add(cell);
			}
		}		
	}


	// find all the targets for any given cell
	public void calcTargets(int row, int col, int pathLength)
	{
		BoardCell startCell = grid[row][col];
		targets =  new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
		visited.add(startCell);
		findAllTargets(startCell,pathLength);

	}
	// method is just like intboard only isDoorway is implemented to make sure it's read
	public void findAllTargets(BoardCell startCell, int pathLength)
	{
		Set<BoardCell> adjCells = adjMtx.get(startCell);
		for (BoardCell adjCell: adjCells) {
			if (visited.contains(adjCell)) {

			} else {
				visited.add(adjCell);
				if(adjCell.isDoorway())
				{
					targets.add(adjCell);
				}
				else if (pathLength ==1) {
					targets.add(adjCell);
				} else {
					findAllTargets(adjCell, pathLength-1);
				}
				visited.remove(adjCell);
			}

		}
	}

	// Getter method for Targets
	public Set<BoardCell> getTargets()
	{
		return targets;
	}
	public BoardCell[][] getGrid()
	{
		return grid;
	}
}
