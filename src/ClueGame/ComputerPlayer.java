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
	private Suggestion suggestion = new Suggestion();
	
	//inherited constructor
	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		// TODO Auto-generated constructor stub
	}

	// basic constructor
	public ComputerPlayer()
	{}



	// This code will send the computer player to a door whenever possible.
	public BoardCell pickLocation(Set<BoardCell> targets)
	{

		if (targets.size() == 0) {
			System.out.println("no targets!");
		}
		//pick a random Target
		int select = this.rand.nextInt(targets.size());
		int index = 0;
		BoardCell result = null;
		for (BoardCell piece : targets)
		{
			// check to see if the piece is a door and also that it hasn't previously been visited.
			// if both conditions are true, return that cell, otherwise keep scrolling until a random location is selected
			if ((piece.isDoorway()) && (piece.getInitial() != this.lastVisitedRoom))
			{
				this.lastVisitedRoom = piece.getInitial();
				return piece;
			}
			if (index == select) {
				result = piece;
			}
			index++;
		}
		return result;
	}

	// the suggestion room will by default  be the room the  Computer player is in
	// The person and weapon need to be selected, (randomly) from a list that DOESNT
	// contain the cards that the computer player already has seen or has, (seen cards)
	//  A random weapon or room will be put into the suggestion
	public void createSuggestion(String theRoom)
	{
		suggestion.setRoom(theRoom);

		ArrayList<String> possibleWeapons =  new ArrayList();
		ArrayList<String> weaponNames = Board.getInstance().getWeaponNames();
		for(String s : weaponNames )
		{
			if((seenCards.contains(s)))
			{
				
			}
			else
			{
				possibleWeapons.add(s);
			}
		}
		ArrayList<String> possiblePlayers =  new ArrayList();
		ArrayList<String> playerNames = Board.getInstance().getPlayerNames();
		for(String s : playerNames)
		{
			if((seenCards.contains(s)))
			{
				
			}
			else
			{
				possiblePlayers.add(s);
			}
		}
		
		int selectedWeapon = rand.nextInt(possibleWeapons.size());
		suggestion.setWeapon(possibleWeapons.get(selectedWeapon));
		int selectedPerson = rand.nextInt(possiblePlayers.size());
		suggestion.setPerson(possiblePlayers.get(selectedPerson));
	}

	// crucial getter
	public Suggestion getSuggestion() {
		return suggestion;
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
	//important getters and setters for the computer player
	public char getLastVisitedRoom()
	{
		return lastVisitedRoom;
	}
	public void setLastVisitedRoom(char roomCharacter)
	{
		lastVisitedRoom = roomCharacter;
	}
	

}
