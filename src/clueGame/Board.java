package clueGame;

import java.util.Map;
import java.util.Set;

import clueGame.BoardCell;

import java.util.HashSet;
import java.util.HashMap;
import java.io.FileNotFoundException;


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

	private String boardConfigFile;
	private String roomConfigFile;


	// constructor is private to ensure only one can be created
	// board is initialized to 
	private Board() 
	{
		visited = new  HashSet<BoardCell>();
		targets=  new HashSet<BoardCell>();
		legend = new HashMap<Character,String>();
		grid = new BoardCell[numRows][numCols];
		adjMtx = new HashMap<BoardCell,Set<BoardCell>>();

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
		return 0;
	}

	public int getNumColumns() {
		// TODO Auto-generated method stub
		return 0;
	}


	public void setConfigFiles(String boardConfig, String roomConfig) {

	}
	public BoardCell getCellAt(int row, int col)
	{
		BoardCell cell =  new BoardCell(row,col);
		return cell;

	}
	// Not needed  thus far
	public void initialize() {


	}
	public void loadRoomConfig()
	{

	}
	public void loadBoardConfig()
	{

	}
	public void calcAdjacencies()
	{

	}
	public void calcTargets(BoardCell startCell, int pathLength)
	{

	}
}
