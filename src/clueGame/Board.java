package clueGame;


import java.util.Map;

import java.util.Scanner;
import java.util.Set;

import javax.swing.JPanel;

import com.sun.prism.Graphics;

import clueGame.BoardCell;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;


//Authors: Amelia Atiles and Connor Price
public class Board extends JPanel {
	// variable used for singleton pattern

	private HashMap<BoardCell, Set<BoardCell>> adjMtx;
	private HashSet<BoardCell> visited;
	private HashSet<BoardCell> targets;
	private HashMap<Character, String> legend;
	// these need to be initialized up here because several config methods will add to cards/players and they can't be reset in between.
	private ArrayList<Player> players =  new ArrayList<Player>();
	private ArrayList<Card> cards = new ArrayList<Card>();
	private ArrayList<String> weaponNames;
	private ArrayList<String> roomNames;
	private ArrayList<String> playerNames;
	private HumanPlayer humanPlayer;
	private BoardCell[][] grid = new BoardCell[100][100];
	private static Board theInstance = new Board();
	private Suggestion winningSolution;
	private ControlPanel controlPanel;


	private int numRows,numCols;
	final static int MAX_BOARD_SIZE = 50;
	public static final int NUMBER_OF_ROWS = 23;
	public static final int NUMBER_OF_COLUMNS = 22;
	public static final char WALKWAY_CHARACTER = 'W';
	public static final char CLOSET_CHARACTER = 'X';


	private String boardConfigFile;
	private String roomConfigFile;
	private String weaponConfigFile = "ClueWeapons.txt";
	private String playerConfigFile = "CluePlayers.txt";


	// constructor is private to ensure only one can be created
	private Board() 
	{}

	// this method returns the only Board
	public static Board getInstance() 
	{
		return theInstance;
	}

	//Set the config files correctly.
	public void setConfigFiles(String boardConfig, String roomConfig)
	{
		boardConfigFile = boardConfig;
		roomConfigFile = roomConfig;
	}

	// reinitialize our sets, map, and grid to clear it.
	public void clearHolders()
	{
		legend = new HashMap<Character, String>();
		grid = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		weaponNames = new ArrayList<String>();
		roomNames = new ArrayList<String>();
		playerNames = new ArrayList<String>();
		humanPlayer =  new HumanPlayer();


	}
	// load the rooms and boards.
	// both methods have to throw exceptions so try/catch blocks are used
	public void initialize() 
	{
		clearHolders();
		try
		{
			//load the board, room,weapons, players, and calculate all possible adjacencies.
			loadRoomConfig();
			loadBoardConfig();
			calcAdjacencies();
			loadWeaponConfig();
			loadPlayersConfig();
			System.out.print("Files loaded.");
		}
		//our FileNotFound and BadConfig Exceptions!
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	/* read the legend file
	// make sure that the format (including commas and spaces)  of each entry is correct
	// throw errors otherwise
	 if the entries are good, load them into the legend (our Map)*/
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException
	{
		roomNames = new ArrayList<String>();
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
			String roomType =  new String(parts[2].trim());
			// Must check card name to make sure it isn't anything other than "Other" or "Card"
			if ((!roomType.equals("Card")) && (!roomType.equals("Other")))
			{
				legendReader.close();
				throw new BadConfigFormatException("The card format file is incorrect for " + entry);
			}
			// If the room should be a card, create a new card with the room name and the ROOM type.
			// also add it to room names for testing purposes, convenience, etc.
			if(roomType.equals("Card"))
			{
				cards.add(new Card(roomName, CardType.ROOM));
				roomNames.add(roomName);
			}
			// put the  found character and the room name in the legend.
			legend.put(character, roomName);
		}
		// close the scanner
		legendReader.close();
	}

	// Read the board layout file
	// Put each string (character) into  it's own little board cell spot
	//  Check to make sure each spot is filled and that each character actually corresponds to a room/
	//throw exceptions otherwise
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException
	{
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
		numRows = currentRow++;
		// close the scanner
		boardReader.close();
	}
	// Load the weapons. This is very simple compared to the other  loaders
	public void loadWeaponConfig() throws FileNotFoundException, BadConfigFormatException
	{
		// start up scanners and file readers
		weaponNames = new ArrayList<String>();
		FileReader filereader = new FileReader(weaponConfigFile);
		Scanner weaponReader =  new Scanner(filereader);
		// read and create a card for the weapon
		while(weaponReader.hasNextLine())
		{
			String weapon  = weaponReader.nextLine();
			cards.add(new Card(weapon.trim(), CardType.WEAPON));
			weaponNames.add(weapon.trim());
		}

	}
	// Read and load the players, their names, and their cards, into their respective arrays.
	public void loadPlayersConfig() throws FileNotFoundException, BadConfigFormatException
	{
		//start up scanners and file readers
		playerNames = new ArrayList<String>();
		FileReader fileReader  = new FileReader(playerConfigFile);		
		Scanner playerReader = new Scanner(fileReader);
		// read and load the player
		while(playerReader.hasNextLine())
		{
			String player =  playerReader.nextLine();
			Player currentPlayer;
			// The first player read will be the human player!
			// This if statement should only be called once to create ONE human player
			if(players.size() == 0)
			{
				humanPlayer = new HumanPlayer();
				currentPlayer =  humanPlayer;
			}
			// the rest will be computer players!
			else
			{
				currentPlayer =  new ComputerPlayer();
			}

			// dividing up the name, row, col, and color of the player using the split method just like we did  with loadBoardConfig() and loadRoomConfig() methods.
			String[] tokens =  player.split(",");
			String playerName =  tokens[0];
			int row = Integer.parseInt(tokens[1].trim());
			int col = Integer.parseInt(tokens[2].trim());
			String colorName = tokens[3].trim();

			//Found code on to convert string to color! :D
			// Credit to Stack Overflow for this one.
			Color color;
			try {
				Field field = Class.forName("java.awt.Color").getField(colorName);
				color = (Color)field.get(null);
			} catch (Exception e) {
				color = null; // Not defined
			}

			//setting the fields for our player based off of what we've read from file.
			currentPlayer.setPlayerName(playerName);
			currentPlayer.setRow(row);
			currentPlayer.setColumn(col);
			currentPlayer.setColor(color);
			// adding the player card to our cards list as well as to two separate list keeping track of 
			// player names and overall players.
			cards.add(new Card(currentPlayer.getPlayerName(), CardType.PERSON));
			playerNames.add(currentPlayer.getPlayerName());
			players.add(currentPlayer);
		}
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
		// set the matrix that will be filled with board cells
		adjMtx = new HashMap<BoardCell, Set<BoardCell>>();

		for( int i =  0; i < numRows; i++ )
		{
			for( int j = 0; j < numCols; j++)
			{
				//calc list for each cell
				calcSingleAdjacencyList(i,j);
			}
		}

		// confirmation message.
		//System.out.println("This should be working!");	
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
	// method is just like int board only isDoorway is implemented to make sure it's read
	public void findAllTargets(BoardCell startCell, int pathLength)
	{
		Set<BoardCell> adjCells = adjMtx.get(startCell);
		for (BoardCell adjCell: adjCells) 
		{
			if (visited.contains(adjCell)) 
			{} 
			else 
			{
				visited.add(adjCell);
				if(adjCell.isDoorway())
				{
					targets.add(adjCell);
				}
				else if (pathLength ==1) 
				{

					targets.add(adjCell);
				} 
				else 
				{
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
	// Getter method for the grid
	public BoardCell[][] getGrid()
	{
		return grid;
	}
	// return the Human player!
	public  HumanPlayer getHumanPlayer()
	{
		return humanPlayer;
	}
	// return the list of players
	public ArrayList<Player> getPlayers() 
	{
		return players;
	}
	// return the list of cards
	public ArrayList<Card> getCards() 
	{
		return cards;
	}
	// changing the cards if that is ever needed.
	public void setCards(ArrayList<Card> cards) 
	{
		this.cards = cards;
	}
	// setting the players for the game
	public void setPlayers(ArrayList<Player> players)
	{
		this.players = players;
	}
	// setting the human Player for the game
	public void setHumanPlayer(HumanPlayer humanPlayer)
	{
		this.humanPlayer = humanPlayer;
	}
	// return the weapon names 
	public ArrayList<String> getWeaponNames() 
	{
		return weaponNames;
	}

	// return the rooms of all the players
	public ArrayList<String> getRoomNames()
	{
		return roomNames;
	}
	// return the room corresponding to the character parameter in our Legend set
	public String getSpecificRoom(Character initial)
	{
		return legend.get(initial);
	}
	
	// return the names of all the players
	public ArrayList<String> getPlayerNames()
	{
		return playerNames;
	}

	// return the legend
	public Map<Character, String> getLegend() 
	{
		return legend;
	}

	// return the number of rows of the grid
	public int getNumRows() 
	{
		return numRows;
	}

	// return the number of columns of the grid.
	public int getNumColumns() 
	{
		return numCols;
	}
	public void setControlPanel(ControlPanel panel)
	{
		this.controlPanel = panel;
	}

	// Deal all the cards in the deck to each player.
	// playerCount will increment every time a card is dealt.
	// Each player should get one card at a time until the end
	// End result is that half of the players half of the players have 3 cards and half of them have 4
	// This is temporary until solution is implemented later.
	public void dealCards()
	{
		// player count keeps track of which player is getting which card.
		int playerCount = 0;
		// temporary cardRack so our original arraylist isn't messed up
		ArrayList<Card> cardRack = cards;
		for(Card card : cardRack) {
			// go back to player 0 after the last player gets his/her card
			if(playerCount == players.size()) {
				playerCount  = 0;
			}
			// give player the next card in the array!
			Player player = players.get(playerCount);
			player.addCard(card);
			playerCount++;
		}
	}


	// set the overall solution of the game
	public void setWinningSolution(String room, String weapon, String  person)
	{
		winningSolution  = new Suggestion();
		winningSolution.setPerson(person);
		winningSolution.setRoom(room);
		winningSolution.setWeapon(weapon);
	}

	//check if the accusation matches the board's answers. 
	// Room, person, and weapon all must match for this to be valid
	public boolean checkAccusation(Suggestion accusation)
	{
		if((accusation.getPerson() == winningSolution.getPerson()) &&
				(accusation.getRoom() == winningSolution.getRoom()) &&
				(accusation.getWeapon() == winningSolution.getWeapon()))
		{
			return true;
		}
		return false;
	}
	//Handle suggestions from any player.
	//Checks to see if suggestion is correct
	public Card handleSuggestion(Suggestion sug, Player suggester) {
		// find the index of the accuser, the first person to be asked will go right after him
		// at index + 1, then index + 2, and so on.
		int playaIndex = players.indexOf(suggester);
		int count = 0;
		// go through the players
		while( count < players.size())
		{
			// really cool remainder method using modulus to cycle through an array starting at any point
			// Taken from stackOverflow!
			playaIndex = (playaIndex + 1) % this.players.size();
			Player playa = players.get(playaIndex);
			// if the player at hand isn't the accuser, they will show a card or do nothing
			if (!(playa == suggester)) {
				Card chosenCard = playa.disproveSuggestion(sug);
				if (playa.disproveSuggestion(sug)!= null)
				{
					// Vital to ensure every player does see a card if one is presented
					// to disprove a solution
					for(Player p : players)
					{
						p.addSeenCard(playa.disproveSuggestion(sug));

					}
					// return the card for all to see.
					return chosenCard;
				}
			}
			//increment count
			count++;
		}
		// if no Cards can be shown, return null
		return null;
	}
	

	public void paintComponent(Graphics graphics)
	{
		super.paintComponent((java.awt.Graphics) graphics);
		 Graphics2D g2 = (Graphics2D)graphics;
		drawGrid(g2);
		drawPlayers(g2);
	}

	public void drawGrid(Graphics2D g)
	{
		for (int row = 0; row < this.numRows; row++) 
		{
			for (int col = 0; col < this.numCols; col++)
			{
				grid[row][col].draw(g);
			}
		}
	}

	public void drawPlayers(Graphics2D g)
	{
		for (Player p : this.players) 
		{
			p.draw(g, this);
		}
	}


}

