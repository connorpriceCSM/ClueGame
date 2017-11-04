package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public class Player {

	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> playerCards = new ArrayList<Card>();

	// constructor for player. name, location, and color.
	public Player( String playerName, int row, int column, Color color)
	{
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
	}
	//basic constructor
	public Player()
	{}


	// For tests
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public int getRow() {
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
	}
	public ArrayList<Card> getCards()
	{
		return playerCards;
	}
	public void setLocation(BoardCell cell)
	{
		this.row = cell.getRow();
		this.column = cell.getColumn();
	}
	public void addCard(Card card) 
	{
		 playerCards.add(card);
	}

}
