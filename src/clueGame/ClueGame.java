package clueGame;

import javax.swing.JFrame;


import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClueGame extends JFrame {


	private DetectiveSheet sheet;
	private Board board;
	private String boardConfigFile = "ClueMap.csv";
	private String roomConfigFile = "ClueLegend.txt";
	private ControlPanel controlPanel;


	public ClueGame(String boardFile, String roomFile )
	{
		boardConfigFile = boardFile;
		roomConfigFile = roomFile;
		setUpGame();

	}
	public ClueGame()
	{ 
		setUpGame();

	}

	public void setUpGame()
	{
		setTitle("Clue Game");
		board = Board.getInstance();
		// set files so the board is actually read properly!
		board.setConfigFiles(boardConfigFile, roomConfigFile);
		try
		{
			// initialize!!!
			board.initialize();
		}
		catch (Exception e)
		{
			System.out.println("Error: Board Failed to Initialize!");
		}
		setGUI();
	}

	//set up the consoles
	public void setGUI()
	{

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Our Control Panel will be at the bottom of the frame
		{
			// set the panel, board, and menu
			setControlPanel();
			add(this.board);
			createMenuBar();
			sheet = new DetectiveSheet();

		}
	}
	// set the control panel at the bottom of the Frame
	public void setControlPanel()
	{

		controlPanel =  new ControlPanel();
		board.setControlPanel(controlPanel);
		add(controlPanel, "South");
	}

	// set the grid up
	
	// create the menu bar on the top of the Frame!
	public JMenuBar createMenuBar()
	{
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		// add the file Tab to the bar
		menuBar.add(createMenuFileTab());
		return menuBar;
	}
	public JMenu createMenuFileTab()
	{
		// set tab title
		JMenu menuTab = new JMenu("File");
		// add the exit and detective sheet tabs to the file tab!
		menuTab.add(createFileExitTab());
		menuTab.add(createFileDetectiveSheetTab());
		return menuTab;
	}

	public JMenuItem createFileExitTab()
	{
		// set tab title
		JMenuItem exitTab = new JMenuItem("Exit Program");

		// needs a returnable value so it can be put in our menu bar!
		return exitTab;
	}
	public JMenuItem createFileDetectiveSheetTab()
	{
		JMenuItem sheetTab = new JMenuItem("Detective Sheet");
		sheetTab.addActionListener(new ActionListener()
		{
			// if the Detective Sheet tab is clicked, open the sheet!
			public void actionPerformed(ActionEvent e)
			{
				sheet.setVisible(true);
			}
		});
		return sheetTab;
	}
	
	// Main method to create our frame
	public static void main(String[] args)
	{
		ClueGame game = new ClueGame();
		game.setVisible(true);
		game.setSize(800,800);
	}
}

