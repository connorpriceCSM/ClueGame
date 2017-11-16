package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public class HumanPlayer extends Player 
{
	private String playerName;
	
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
	
}
