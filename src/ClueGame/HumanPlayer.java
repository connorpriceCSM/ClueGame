package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public class HumanPlayer extends Player 
{
	private String playerName;
	private boolean needtoFinishTurn;
	
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
		// set our boolean to true to indicate we need to finish our turn.
		needtoFinishTurn = true;
		board.highlightTargetCells(true);
		
	}
	
	// set the status of our boolean to false
	public void finished()
	{
		needtoFinishTurn = false;
	}
	// return the human player status 
	public boolean finishedStatus()
	{
		return needtoFinishTurn;
	}
	
	// the clicked cell comes from mouseclicked method
	// the player will move!
	public void finishTurn(BoardCell clickedCell)
	{
		needtoFinishTurn = false;
		setRow(clickedCell.getRow());
		setColumn(clickedCell.getColumn());
		
	}
}
