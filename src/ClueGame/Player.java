package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {
  
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> myCards = new ArrayList();
	
	public Player( String playerName, int row, int column, Color color)
	{
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
	}
	

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
		return myCards;
	}
	public void setLocation(BoardCell cell)
	{
		this.row = cell.getRow();
		this.column = cell.getColumn();
	}
	public void addCard()
	{
		
	}
	
	
}
