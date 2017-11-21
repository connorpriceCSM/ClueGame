package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
public class ControlPanel extends JPanel {

	private JButton nextPlayer;
	private JButton accusePlayer;
	private JTextField dieRoll;
	private JTextField whoseTurn;
	private JTextField guessMade;
	private JTextField guessResult;

	public ControlPanel()
	{
		createBothButtons();
		createRollDisplay();
		createTurnDisplay();
		createGuessStatusDisplay();
		createGuessResultDisplay();
	}

	// needed listener class for buttons.
	// other stuff will be implemented later
	private class ButtonListener
	implements ActionListener
	{
		private ButtonListener()
		{


		}

		@Override
		public void actionPerformed(ActionEvent action) {
			// NOTHING!

		}
	}

	public void createBothButtons() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1,0));
		ButtonListener listerner =  new ButtonListener();

		nextPlayer =  new JButton("Next Player (End Turn)");
		nextPlayer.addActionListener(listerner);
		accusePlayer =  new JButton("Accuse a player");
		accusePlayer.addActionListener(listerner);
		buttonPanel.add(nextPlayer);
		buttonPanel.add(accusePlayer);
		buttonPanel.setPreferredSize(new Dimension(500,50));
		add(buttonPanel);
	}
	public void createRollDisplay()
	{
		JLabel rollLabel = new JLabel("Roll");
		dieRoll = new JTextField(4);
		dieRoll.setEditable(false);
		JPanel rollPanel = new JPanel();
		rollPanel.setLayout(new GridLayout(1, 0));
		rollPanel.add(rollLabel);
		rollPanel.add(dieRoll);
		rollPanel.setBorder(new TitledBorder(new EtchedBorder(), "Die Roll"));
		rollPanel.setPreferredSize(new Dimension(200,50));
		add(rollPanel);


	}
	private void createTurnDisplay()
	{
		JPanel turnPanel = new JPanel();
		JLabel turnLabel = new JLabel("Player Moving :");
		turnPanel.add(turnLabel);
		//turnPanel.setPreferredSize(new Dimension(200,100));
		this.whoseTurn = new JTextField(10);
		this.whoseTurn.setEditable(false);

		turnPanel.add(this.whoseTurn);
		add(turnPanel);

	}


	private void createGuessStatusDisplay()
	{
		JLabel guessLabel = new JLabel("Guess Made ");
		this.guessMade = new JTextField(20);
		this.guessMade.setEditable(false);
		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(2, 0));
		guessPanel.add(guessLabel);
		guessPanel.add(this.guessMade);
		guessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Status"));
		guessPanel.setPreferredSize(new Dimension(400,100));
		add(guessPanel);
	}

	private void createGuessResultDisplay()
	{
		JLabel guessLabel = new JLabel("Guess Response");
		this.guessResult = new JTextField(10);
		this.guessResult.setEditable(false);
		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(1, 2));
		guessPanel.add(guessLabel);
		guessPanel.add(this.guessResult);
		guessPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result"));
		guessPanel.setPreferredSize(new Dimension(400,50));
		add(guessPanel);
	}


	public static void main(String[] args) 
	{
	    Board board = Board.getInstance();
	    //board.initialize();
	    ControlPanel panel = new ControlPanel();
	    JFrame frame = new JFrame("Clue Game");
	    frame.setContentPane(panel);
	    frame.setSize(750, 900);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //frame.add(board);
	    frame.setVisible(true);
	}
	


}
