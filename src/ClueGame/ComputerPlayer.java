package clueGame;

import java.awt.Color;
import java.util.Random;
import java.util.ArrayList;
import java.util.Set;

public class ComputerPlayer extends Player {

	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> myCards = new ArrayList<Card>();
	private char lastVisitedRoom;
	private Random rand = new Random();
	

	//inherited constructor
	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		// TODO Auto-generated constructor stub
	}

	// basic constructor
	public ComputerPlayer()
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
	//important getters and setters for the computer player
	public char getLastVisitedRoom()
	{
		return lastVisitedRoom;
	}
	public void setLastVisitedRoom(char roomCharacter)
	{
		lastVisitedRoom = roomCharacter;
	}
	
	// This code will send the computer player to a door whenever possible.
	public BoardCell pickLocation(Set<BoardCell> targets)
	  {
	   return null;
	  }
	
}
