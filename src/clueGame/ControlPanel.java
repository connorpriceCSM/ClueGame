package clueGame;

import java.awt.BorderLayout;

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
		buttonPanel.setLayout(new GridLayout(2,1));
		ButtonListener listerner =  new ButtonListener();
		
		nextPlayer =  new JButton("Next Player (End Turn");
		nextPlayer.addActionListener(listerner);
		accusePlayer =  new JButton("Accuse a player");
		accusePlayer.addActionListener(listerner);
		buttonPanel.add(nextPlayer);
		buttonPanel.add(accusePlayer);
		add(buttonPanel);
	}
	public JPanel  createRollDisplay()
	{
        JLabel rollLabel = new JLabel("Roll");
        dieRoll = new JTextField(2);
        dieRoll.setEditable(false);
        JPanel rollPanel = new JPanel();
        rollPanel.setLayout(new GridLayout(1, 2));
        rollPanel.add(rollLabel);
        rollPanel.add(dieRoll);
        rollPanel.setBorder(new TitledBorder(new EtchedBorder(), "Die Roll"));
        return rollPanel;
	}

	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("GUI Example");
		frame.setSize(250, 150);	
		// Create the JPanel and add it to the JFrame
		ControlPanel panel = new ControlPanel();
		frame.add(panel, BorderLayout.CENTER);
		// Now let's view it
		frame.setVisible(true);
	}
}
