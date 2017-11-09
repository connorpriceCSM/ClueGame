package clueGame;

public class Card {

	private String cardName;
	private CardType cardType;

	public Card(String cardName, CardType cardType)
	{
		this.cardName = cardName;
		this.cardType = cardType;
	}


	//setters and getters
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public CardType getCardType() {
		return cardType;
	}
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	// comparison method for later.
	public boolean equals(Card otherCard)
	{
		if(this == otherCard) {
			return true;
		}
		if(this.cardName.equals(otherCard.cardName) && this.cardType == otherCard.cardType) {
			return true;
		}
		return false;
	}




}
