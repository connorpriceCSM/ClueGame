package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public class Player {

	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> playerCards = new ArrayList<Card>();
	// needs to be protected because every player will have a unique set of cards
	protected ArrayList<String> seenCards  = new ArrayList<String>();

	// constructor for player. name, location, and color.
	public Player(String playerName, int row, int column, Color color) {
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
	public ArrayList<Card> getCards() {
		return playerCards;
	}
	public void setLocation(BoardCell cell) {
		this.row = cell.getRow();
		this.column = cell.getColumn();
	}
	public void addCard(Card card) {
		 playerCards.add(card);
		 seenCards.add(card.getCardName());
	}
	public void addSeenCard(Card card)
	{
		seenCards.add(card.getCardName());
	}
	public ArrayList<String> getSeenCards()
	{
		return seenCards;
	}
	//Make suggestion to the board
	public Suggestion makeSuggestion(String room, String weapon, String currentRoom) {
		return new Suggestion(room, weapon, currentRoom);
	}
	//Disprove Suggestion
		public Card disproveSuggestion(Suggestion sug) {
			for (Card curCard: playerCards) {
				if (curCard.getCardName().equals(sug.person))
					return curCard;
				if (curCard.getCardName().equals(sug.room))
					return curCard;
				if (curCard.getCardName().equals(sug.weapon))
					return curCard;
			}
			return null;
		}
}
