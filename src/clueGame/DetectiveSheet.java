package clueGame;

import java.awt.Dimension;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveSheet extends JDialog {


	//use the board to get names, weapons, and rooms!!
	private Board board;

	//constructor to create a Detective Sheetfor a human to keep track of what cards they've seen.
	public DetectiveSheet()
	{
		board = Board.getInstance();
		createDetectiveSheet();
		setTitle("Detective Sheet");
		setSize(500, 550);
		setLayout(new GridLayout(3,2));
	}


	// create our sheet. There should be 6 panels in total
	private void createDetectiveSheet()
	{
		this.add(createBoxes("Players", board.getPlayerNames()));
		this.add(createBoxes("Weapons", board.getWeaponNames()));
		this.add(createBoxes("Rooms", board.getRoomNames()));
		this.add(createCombo("Player Guess", board.getPlayerNames()));
		this.add(createCombo("Weapon Guess", board.getWeaponNames()));
		this.add(createCombo("Room Guess", board.getRoomNames()));

	}

	private JPanel createBoxes(String label, ArrayList<String> cards )
	{
		// create the panel and set the layout + size
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panel.setPreferredSize(new Dimension(200, 50));
		panel.setBorder(new TitledBorder(new EtchedBorder(), label));
		// go through the list of our items and make checkboxes for them!
		for(String card: cards)
		{
			JCheckBox checkBox = new JCheckBox(card);
			panel.add(checkBox);
		}

		return panel;
	}
// JComboBox allows for a drop down box.
// create suggestion panels for keeping track of 
	private JPanel createCombo(String label, ArrayList<String> cards)
	{
		// create the panel and set the layout + size
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 3));
		panel.setPreferredSize(new Dimension(100, 50));
		panel.setBorder(new TitledBorder(new EtchedBorder(), label));
		// create our drop down boxes and add values to them,
		// in this case, the values are our cards that we read from the cards list.
		JComboBox<String> combo = new JComboBox();
		combo.addItem("N/A");
		for (String card : cards) 
		{
			combo.addItem(card);
		}
		// add our combo panel
		panel.add(combo);
		return panel;
	}
}
