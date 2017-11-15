package clueGame;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame {
	
	
	private DetectiveSheet sheet;
	private Board board;
	private String weaponConfigFile = "ClueWeapons.txt";
	private String playerConfigFile = "CluePlayers.txt";
	private ControlPanel controlPanel;


	public ClueGame()
	{}
}
