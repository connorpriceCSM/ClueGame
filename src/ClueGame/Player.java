package clueGame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

public class Player {

	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> playerCards = new ArrayList<Card>();
	private Random rand = new Random();
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

	//Disprove Suggestion

	public Card disproveSuggestion(Suggestion sug) {
		//much like createSuggestion, we need a list of possible cards to present
		// could range from 0 to 3
		ArrayList<Card> possibleCards = new ArrayList<Card>();
		// if the card is there, add it to the list!
		for (Card curCard: playerCards) 
		{
			if (curCard.getCardName().equals(sug.person))
				possibleCards.add(curCard);
			if (curCard.getCardName().equals(sug.room))
				possibleCards.add(curCard);
			if (curCard.getCardName().equals(sug.weapon))
				possibleCards.add(curCard);
		}
		// provided there is a card in the list that can be shown, show a random one!
		if(possibleCards.size() > 0)
		{
			int randomCardIndex = rand.nextInt(possibleCards.size());
			Card chosenCard = possibleCards.get(randomCardIndex);
			return chosenCard;
		}
		return null;
	}
	// draw the circle player
	public void draw(Graphics2D g, Board board)
	{
		int playerSize = BoardCell.PIECE_SIZE;
		int x = playerSize * column;
		int y = playerSize * row;
		
		g.setColor(color);
		g.fillOval(x, y, playerSize, playerSize);
		g.setColor(color.BLACK);
		g.drawOval(x, y, playerSize, playerSize);
		
	}
}
