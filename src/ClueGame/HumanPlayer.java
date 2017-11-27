package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public class HumanPlayer extends Player 
{
	private String playerName;
	private boolean needfinishedTurn;
	
	private ArrayList<Card> myCards = new ArrayList<Card>();

	//inherited constructor
	public HumanPlayer(String playerName, int row, int column, Color color) 
	{
		super(playerName, row, column, color);
		// TODO Auto-generated constructor stub
	}
	// basic constructor
	public HumanPlayer()
	{}


	// For tests
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	// highlight the cells and begin the process
	@Override
	public void makeMove(Board board) {
		needfinishedTurn = true;
		board.highlightTargetCells(true);
		
	}
	
	// set the status of our boolean to true
	public void finished()
	{
		needfinishedTurn = true;
	}
	// return the human player status 
	public boolean finishedStatus()
	{
		return needfinishedTurn;
	}
	
	// the clicked cell comes from mouseclicked method
	// the player will move!
	public void finishTurn(BoardCell clickedCell)
	{
		needfinishedTurn = false;
		setRow(clickedCell.getRow());
		setColumn(clickedCell.getColumn());
		
	}
}
