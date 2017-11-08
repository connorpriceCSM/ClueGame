package tests;

import clueGame.Board;

import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.ComputerPlayer;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class gameActionTests {

	private static Board board;
	ComputerPlayer player =  new ComputerPlayer();
	private static Card bondCard;
	private static Card croftCard;
	private static Card gandalfCard;
	private static Card kenobiCard;
	private static Card spockCard;
	private static Card ramboCard;
	private static Card lightsaberCard;
	private static Card handgunsCard;
	private static Card staffCard;
	private static Card machinegunCard;
	private static Card phaserCard;
	private static Card stunwatchCard;
	private static Card libraryCard;
	private static Card bathroomCard;
	private static Card observatoryCard;
	private static Card foyerCard;
	private static Card saunaCard;
	private static Card kitchenCard;
	private static Card gardenCard;
	private static Card bedroomCard;
	private static Card entertainmentCard;


	// set up the board as we've done every other time.
	// initialize will call methods to load people and weapons
	@BeforeClass
	public static void setUp()
	{
		board = Board.getInstance();
		board.setConfigFiles("ClueMap.csv", "ClueLegend.txt");		
		board.initialize();
		board.dealCards();

		bondCard = new Card("James Bond", CardType.PERSON);
		croftCard = new Card("Lara Croft", CardType.PERSON);
		gandalfCard = new Card("Gandalf", CardType.PERSON);
		kenobiCard = new Card("Obi-Wan Kenobi", CardType.PERSON);
		spockCard = new Card("Spock", CardType.PERSON);
		ramboCard = new Card("Rambo", CardType.PERSON);

		libraryCard = new Card("Library", CardType.ROOM);
		bathroomCard = new Card("Bathroom", CardType.ROOM);
		observatoryCard = new Card("Observatory", CardType.ROOM);
		foyerCard = new Card("Foyer", CardType.ROOM);
		saunaCard = new Card("Sauna", CardType.ROOM);
		kitchenCard = new Card("Kitchen", CardType.ROOM);
		gardenCard = new Card("Garden", CardType.ROOM);
		bedroomCard = new Card("Bedroom", CardType.ROOM);
		entertainmentCard =  new Card("Entertainment room", CardType.ROOM);

		lightsaberCard = new Card("Lightsaber", CardType.WEAPON);
		handgunsCard = new Card("Handguns", CardType.WEAPON);
		staffCard = new Card("Staff", CardType.WEAPON);
		machinegunCard = new Card("Machine Gun", CardType.WEAPON);
		phaserCard = new Card("Phaser",CardType.WEAPON);
		stunwatchCard = new Card("Stun Watch", CardType.WEAPON);
	}


	@Test
	public void testTargetsNoRooms()
	{

		board.calcTargets(6, 13, 1);
		player =  new ComputerPlayer();
		// 5, 13
		boolean northCell = false;
		// 7,13
		boolean southCell = false;
		// 6, 14
		boolean eastCell = false;
		// 6, 12
		boolean westCell  = false;
		for(int i = 0; i < 100; i++)
		{
			BoardCell selectedCell = player.pickLocation(board.getTargets());
			if(selectedCell == board.getCellAt(5, 13))
			{
				northCell = true;
			}
			else if(selectedCell == board.getCellAt(7, 13))
			{
				southCell = true;
			}
			else if(selectedCell == board.getCellAt(6, 14))
			{
				eastCell = true;
			}
			else if(selectedCell == board.getCellAt(6, 12))
			{
				westCell = true;
			}
			else
			{
				System.out.println("There are no valid targets");
			}
		}
		assertTrue(northCell);
		assertTrue(southCell);
		assertTrue(eastCell);
		assertTrue(westCell);

	}

	@Test
	public void testTargetsRoomNotVisted()
	{
		board.calcTargets(2, 13, 2);
		player =  new ComputerPlayer();
		//doorway cell for the Lounge
		BoardCell idealCell = board.getCellAt(1, 12);
		BoardCell selectedCell = player.pickLocation(board.getTargets());
		assertEquals(idealCell, selectedCell);

		board.calcTargets(19, 19, 2);
		player =  new ComputerPlayer();
		//doorway cell for the Garden
		BoardCell idealCell2 = board.getCellAt(20, 18);
		BoardCell selectedCell2 = player.pickLocation(board.getTargets());
		assertEquals(idealCell2, selectedCell2);


		board.calcTargets(22, 10, 2);
		player =  new ComputerPlayer();
		//doorway cell for the D Room I don't remember
		BoardCell idealCell3 = board.getCellAt(21, 11);
		BoardCell selectedCell3 = player.pickLocation(board.getTargets());
		assertEquals(idealCell3, selectedCell3);

		board.calcTargets(10, 14, 1);
		player =  new ComputerPlayer();
		//doorway cell for the Sauna
		BoardCell idealCell4 = board.getCellAt(10, 15);
		BoardCell selectedCell4 = player.pickLocation(board.getTargets());
		assertEquals(idealCell4, selectedCell4);

		board.calcTargets(6, 1, 1);
		player =  new ComputerPlayer();
		//doorway cell for the kitchen
		BoardCell idealCell5 = board.getCellAt(5, 1);
		BoardCell selectedCell5 = player.pickLocation(board.getTargets());
		assertEquals(idealCell5, selectedCell5);



	}
	@Test
	public void testTargetsRoomVisited()
	{
		// get targets near Entertainment Room
		board.calcTargets(20, 3, 2);
		player = new ComputerPlayer();
		player.setLastVisitedRoom('E');
		boolean roomCell = false;
		boolean walkwayCell = false;
		for(int i = 0; i < 100; i++)
		{
			BoardCell selectedCell = player.pickLocation(board.getTargets());
		    if(selectedCell == board.getCellAt(22, 3))
			{
				walkwayCell = true;
			}
		    else if(selectedCell == board.getCellAt(19, 2));
			{
				roomCell = true;
			}
			
		}
		//both cells should be visited
		assertTrue(roomCell);
		assertTrue(walkwayCell);
	}
	// This computer player has not seen the Cards for Spock and the Phaser
	// Every other weapon and player card has been seen
	 @Test
	  public void testMakeSuggestionOneMissing()
	  {
		 // make computer player appear in lower right board cell (Garden)
	    ComputerPlayer testPlayer = new ComputerPlayer("Lara Croft", 22, 21, Color.blue);
	    // the computer has seen 10 out of the 12 cards.
	    testPlayer.addSeenCard(bondCard);
	    testPlayer.addSeenCard(croftCard);
	    testPlayer.addSeenCard(gandalfCard);
	    testPlayer.addSeenCard(kenobiCard);
	    testPlayer.addSeenCard(ramboCard);
	    testPlayer.addSeenCard(lightsaberCard);
	    testPlayer.addSeenCard(handgunsCard);
	    testPlayer.addSeenCard(staffCard);
	    testPlayer.addSeenCard(machinegunCard);
	    testPlayer.addSeenCard(stunwatchCard);
	    
	    testPlayer.createSuggestion("Garden");
	    Assert.assertEquals("Garden", testPlayer.getSuggestion().room);
	    Assert.assertEquals("Spock", testPlayer.getSuggestion().person);
	    Assert.assertEquals("Phaser", testPlayer.getSuggestion().weapon);
	  }
	  
	 
}
