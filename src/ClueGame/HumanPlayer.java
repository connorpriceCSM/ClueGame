package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public class HumanPlayer extends Player 
{
	private String playerName;
	//private int row;
	//private int column;
	//private Color color;
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
	/*public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
		
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}*/
}
