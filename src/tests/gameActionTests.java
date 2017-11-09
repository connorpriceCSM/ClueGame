package tests;

import clueGame.Board;

import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.ComputerPlayer;
import clueGame.Suggestion;

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

		// loading all the cards that will be needed for the tests!
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
	// This computer player has not seen the Cards for Spock, Rambo, stunwatch, or phaser
	// Every other weapon and player card has been seen
	@Test
	public void testMakeSuggestionTwoMissing()
	{
		// make computer player appear in lower right board cell (Garden)
		ComputerPlayer testPlayer = new ComputerPlayer("Lara Croft", 22, 21, Color.blue);
		// the computer has seen 8 out of the 12 cards minus Spock and Rambo
		testPlayer.addSeenCard(bondCard);
		testPlayer.addSeenCard(croftCard);
		testPlayer.addSeenCard(gandalfCard);
		testPlayer.addSeenCard(kenobiCard);
		testPlayer.addSeenCard(lightsaberCard);
		testPlayer.addSeenCard(handgunsCard);
		testPlayer.addSeenCard(staffCard);
		testPlayer.addSeenCard(machinegunCard);
		boolean seenSpock  = false;
		boolean seenRambo = false;
		boolean seenPhaser = false;
		boolean seenWatch = false;
		for( int i =  0; i < 100; i++)	
		{

			testPlayer.createSuggestion("Garden");
			String playerGuess = testPlayer.getSuggestion().getPerson();
			if(playerGuess.equals("Rambo"))
			{
				seenRambo = true;
			}
			else if(playerGuess.equals("Spock"))
			{
				seenSpock = true;
			}
			else
			{
				System.out.println("The computer made no guess!");
			}
			String weaponGuess = testPlayer.getSuggestion().getWeapon();
			if(weaponGuess.equals("Stun Watch"))
			{
				seenWatch = true;
			}
			else if(weaponGuess.equals("Phaser"))
			{
				seenPhaser = true;
			}
			else
			{
				System.out.println("The computer made no guess!");
			}
		}
		Assert.assertEquals("Garden", testPlayer.getSuggestion().room);
		Assert.assertTrue(seenSpock);
		Assert.assertTrue(seenRambo);
		Assert.assertTrue(seenWatch);
		Assert.assertTrue(seenPhaser);
	}
	@Test
	public void testAccusation()
	{

		board.setWinningSolution("Bathroom", "Lightsaber", "Gandalf");
		boolean allRight = false;
		boolean wrongRoom = false;
		boolean wrongWeapon = false;
		boolean wrongPerson = false;
		Suggestion allCorrect = new Suggestion("Bathroom", "Lightsaber", "Gandalf");
		Suggestion incorrectRoom = new Suggestion("Library", "Lightsaber", "Gandalf");
		Suggestion incorrectWeapon = new Suggestion("Bathroom", "Staff", "Gandalf");
		Suggestion incorrectPlayer = new Suggestion("Bathroom", "Lightsaber", "James Bond");
		//all correct!
		if(board.checkAccusation(allCorrect))
		{
			allRight = true;
		}
		// wrong Room
		if(!(board.checkAccusation(incorrectRoom)));
		{
			wrongRoom = true;
		}
		// wrong Weapon
		if(!(board.checkAccusation(incorrectWeapon)))
		{
			wrongWeapon = true;
		}
		// wrong Person
		if(!(board.checkAccusation(incorrectPlayer)))
		{
			wrongPerson = true;
		}
		// ensure all are true!
		Assert.assertTrue(allRight);
		Assert.assertTrue(wrongRoom);
		Assert.assertTrue(wrongPerson);
		Assert.assertTrue(wrongWeapon);

	}
	// has to return null;
	@Test 
	public void disproveSolutionNoMatches()
	{
		ComputerPlayer testPlayer = new ComputerPlayer("Lara Croft", 22, 21, Color.blue);
		testPlayer.addCard(gandalfCard);
		testPlayer.addCard(kenobiCard);
		testPlayer.addCard(lightsaberCard);
		testPlayer.addCard(handgunsCard);
		testPlayer.addCard(bedroomCard);
		//should all return null with 3 different tests of 9 values!
		Suggestion suggestion = new Suggestion("Garden", "Phaser", "Rambo");
		Card chosenCard = testPlayer.disproveSuggestion(suggestion);
		assertEquals(chosenCard, null);
		Suggestion suggestion2 = new Suggestion("Kitchen", "Staff", "James Bond");
		Card chosenCard2 = testPlayer.disproveSuggestion(suggestion2);
		assertEquals(chosenCard2, null);
		Suggestion suggestion3 = new Suggestion("Sauna", "Machine Gun", "Spock");
		Card chosenCard3 = testPlayer.disproveSuggestion(suggestion3);
		assertEquals(chosenCard3, null);

	}
	public void disproveSolutionOneMatch()
	{
		ComputerPlayer testPlayer = new ComputerPlayer("Lara Croft", 22, 21, Color.blue);
		testPlayer.addCard(gandalfCard);
		testPlayer.addCard(kenobiCard);
		testPlayer.addCard(lightsaberCard);
		testPlayer.addCard(handgunsCard);
		testPlayer.addCard(bedroomCard);
		//should return bedroom!
		Suggestion suggestion = new Suggestion("Bedroom", "Phaser", "Rambo");
		Card chosenCard = testPlayer.disproveSuggestion(suggestion);
		assertEquals(chosenCard, bedroomCard );
		// should return handguns
		Suggestion suggestion2 = new Suggestion("Kitchen", "Handguns", "James Bond");
		Card chosenCard2 = testPlayer.disproveSuggestion(suggestion2);
		assertEquals(chosenCard2, handgunsCard);
		// should return gandalf
		Suggestion suggestion3 = new Suggestion("Garden", "Machine Gun", "Gandalf");
		Card chosenCard3 = testPlayer.disproveSuggestion(suggestion3);
		assertEquals(chosenCard3, gandalfCard);

	}
	public void disproveSolutionTwoMatches()
	{
		ComputerPlayer testPlayer = new ComputerPlayer("Lara Croft", 22, 21, Color.blue);
		testPlayer.addCard(gandalfCard);
		testPlayer.addCard(kenobiCard);
		testPlayer.addCard(lightsaberCard);
		testPlayer.addCard(handgunsCard);
		testPlayer.addCard(bedroomCard);
		boolean handgunsSeen = false;
		boolean bedroomSeen = false;
		//should return bedroom or handguns
		for( int i = 0; i < 100; i++)
		{
			Suggestion suggestion = new Suggestion("Bedroom", "Handguns", "Rambo");
			Card chosenCard = testPlayer.disproveSuggestion(suggestion);
			if(chosenCard == bedroomCard)
			{
				bedroomSeen = true;
			}
			else if(chosenCard == handgunsCard)
			{
				handgunsSeen = true;
			}
		}

		
	}
}


