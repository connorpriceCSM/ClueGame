package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class PlayerCardPanel extends JPanel {

	public PlayerCardPanel(ArrayList<Card> playerCards)
	{
		// set a title layout for this panel
		setLayout(new GridLayout(7, 1));
		// create the beautiful title and add it
		TitledBorder title = BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(0), "Player Cards");
		title.setTitleJustification(2);
		
		setBorder(title);
		//add all of our player Panels inside the title border like the great Ryder does.
		createPanel("Players", CardType.PERSON, playerCards);
		createPanel("Rooms", CardType.ROOM, playerCards);
		createPanel("Weapons", CardType.WEAPON, playerCards);

	}

	public void createPanel(String label, CardType cardType, ArrayList<Card> cards  )
	{
		JPanel playerCardsPanel = new JPanel();
		// set our label (will be a cardType)
		JLabel playerCardsLabel = new JLabel(label);
		// ensure we get a basic square for the card,
		playerCardsPanel.setLayout(new GridLayout(0,1));
		// add our label to the panel
		playerCardsPanel.add(playerCardsLabel);
		// set the border
		playerCardsPanel.setBorder(new EtchedBorder());
		// go through the players cards
		// if a card matches the weapon type of the panel, then add a text field showing it
		for(Card card : cards)
		{
			if(card.getCardType() == cardType)
			{
				JTextField textField = new JTextField(card.getCardName());
				playerCardsPanel.add(textField);
			}
		}
		add(playerCardsPanel);
	}
}
